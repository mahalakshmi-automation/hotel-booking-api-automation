package com.booking.stepdefinitions;
import com.booking.utils.ApiUtils;
import com.booking.utils.TestData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;
public class DeleteBookingSteps {
    private Response response;
    private String token;
    private int bookingId;
    @Given("I have a valid token for deleting a booking") public void token() { token = ApiUtils.loginToken(); }
    @Given("I have a booking ID for deletion") public void booking() {
        Response created = ApiUtils.post("/booking", TestData.booking());
        assertEquals(201, created.statusCode());
        bookingId = created.jsonPath().getInt("bookingid");
        assertTrue(bookingId > 0);
    }
    @When("I delete the booking with authentication") public void delete() {
        response = ApiUtils.delete("/booking/" + bookingId, token);
    }
    @When("I delete the booking without authentication") public void withoutAuth() {
        response = ApiUtils.delete("/booking/" + bookingId, null);
    }
    @When("I delete booking {int} without authentication") public void deleteById(int id) {
        response = ApiUtils.delete("/booking/" + id, null);
    }
    @Then("the delete booking status code should be {int}") public void status(int expected) {
        assertEquals(expected, response.statusCode());
    }
}
