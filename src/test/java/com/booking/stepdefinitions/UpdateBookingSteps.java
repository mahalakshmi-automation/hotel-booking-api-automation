package com.booking.stepdefinitions;
import com.booking.models.BookingRequest;
import com.booking.utils.ApiUtils;
import com.booking.utils.TestData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
public class UpdateBookingSteps {
    private Response response;
    private String token;
    private int bookingId;
    private BookingRequest request;
    @Given("I have a valid authentication token") public void getToken() { token = ApiUtils.loginToken(); }
    @Given("I have an existing booking ID") public void getBookingId() {
        // Create test-owned data rather than mutating an unrelated booking.
        request = TestData.booking();
        Response created = ApiUtils.post("/booking", request);
        assertEquals(201, created.statusCode());
        bookingId = created.jsonPath().getInt("bookingid");
        assertTrue(bookingId > 0);
    }
    @When("I update the booking details") public void updateBooking() {
        request.setLastname("Smith");
        response = ApiUtils.put("/booking/" + bookingId, request, token);
    }
    @When("I partially update the booking firstname") public void patchBooking() {
        response = ApiUtils.patch("/booking/" + bookingId, Map.of("firstname", "David"), token);
    }
    @When("I update the booking without authentication") public void withoutAuth() {
        request.setLastname("Smith");
        response = ApiUtils.put("/booking/" + bookingId, request, null);
    }
    @Then("the update booking status code should be {int}") public void status(int expected) {
        assertEquals(expected, response.statusCode());
        if (expected == 200) assertNotNull(response.getBody());
    }
    @Then("the partial update status code should be {int}") public void patchStatus(int expected) {
        assertEquals(expected, response.statusCode());
    }
}
