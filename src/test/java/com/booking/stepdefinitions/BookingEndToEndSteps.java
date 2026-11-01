package com.booking.stepdefinitions;
import com.booking.models.BookingRequest;
import com.booking.utils.ApiUtils;
import com.booking.utils.TestData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;
public class BookingEndToEndSteps {
    private int bookingId;
    private String token;
    private BookingRequest booking;
    @Given("I create a new booking for the end-to-end test") public void create() {
        booking = TestData.booking();
        Response response = ApiUtils.post("/booking", booking);
        assertEquals(201, response.statusCode());
        bookingId = response.jsonPath().getInt("bookingid");
        assertTrue(bookingId > 0);
    }
    @When("I retrieve the newly created booking") public void retrieve() {
        Response response = ApiUtils.getWithoutAuth("/booking/" + bookingId);
        assertEquals(200, response.statusCode());
        assertEquals(booking.getFirstname(), response.jsonPath().getString("firstname"));
    }
    @When("I update the newly created booking") public void update() {
        token = ApiUtils.loginToken();
        booking.setFirstname("David");
        Response response = ApiUtils.put("/booking/" + bookingId, booking, token);
        assertEquals(200, response.statusCode());
        Response verified = ApiUtils.getWithoutAuth("/booking/" + bookingId);
        assertEquals(200, verified.statusCode(), "Updated booking must be retrievable");
        assertEquals("David", verified.jsonPath().getString("firstname"), "Updated firstname must persist");
    }
    @When("I delete the newly created booking") public void delete() {
        Response response = ApiUtils.delete("/booking/" + bookingId, token);
        assertEquals(201, response.statusCode());
    }
    @Then("the booking should no longer be available") public void verifyDeleted() {
        Response response = ApiUtils.getWithoutAuth("/booking/" + bookingId);
        assertEquals(404, response.statusCode());
    }
}
