package com.booking.stepdefinitions;
import com.booking.models.BookingRequest;
import com.booking.utils.ApiUtils;
import com.booking.utils.TestData;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;
public class CreateBookingSteps {
    private Response response;
    private BookingRequest request;
    @When("I create a booking with valid details")
    public void createBooking() {
        request = TestData.booking();
        response = ApiUtils.post("/booking", request);
    }
    @Then("the booking should be created successfully")
    public void verifyStatusCode() { assertEquals(201, response.statusCode()); }
    @Then("the response should contain a booking ID")
    public void verifyBookingId() { assertTrue(response.jsonPath().getInt("bookingid") > 0); }
    @Then("the created booking details should match the request")
    public void verifyBookingDetails() {
        assertEquals(request.getRoomid(), response.jsonPath().getInt("booking.roomid"));
        assertEquals(request.getFirstname(), response.jsonPath().getString("booking.firstname"));
        assertEquals(request.getLastname(), response.jsonPath().getString("booking.lastname"));
        assertEquals(request.isDepositpaid(), response.jsonPath().getBoolean("booking.depositpaid"));
        assertEquals(request.getEmail(), response.jsonPath().getString("booking.email"));
        assertEquals(request.getPhone(), response.jsonPath().getString("booking.phone"));
        assertEquals(request.getBookingdates().getCheckin(), response.jsonPath().getString("booking.bookingdates.checkin"));
        assertEquals(request.getBookingdates().getCheckout(), response.jsonPath().getString("booking.bookingdates.checkout"));
    }
    @When("I create a booking without request body")
    public void withoutBody() { response = ApiUtils.post("/booking", null); }
    @When("I create a booking without firstname")
    public void withoutFirstname() {
        // Intentionally omit firstname instead of sending null, to validate a required field.
        request = TestData.booking();
        java.util.Map<String,Object> body = new java.util.LinkedHashMap<>();
        body.put("roomid", request.getRoomid());
        body.put("lastname", request.getLastname());
        body.put("depositpaid", request.isDepositpaid());
        body.put("bookingdates", request.getBookingdates());
        body.put("email", request.getEmail());
        body.put("phone", request.getPhone());
        response = ApiUtils.post("/booking", body);
    }
    @Then("the create booking status code should be {int}")
    public void verifyCreateBookingStatus(int expected) { assertEquals(expected, response.statusCode()); }
}
