package com.booking.stepdefinitions;

import static io.restassured.RestAssured.given;
import com.booking.utils.ApiUtils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteBookingSteps {
	private final String baseUrl = "https://automationintesting.online/api";

	private Response response;
	private String token;
	private int bookingId;

	@Given("I have a valid token for deleting a booking")
	public void getToken() {

		String body = """
				{
				  "username": "admin",
				  "password": "password"
				}
				""";

		Response authResponse = given().contentType(ContentType.JSON).body(body).post(baseUrl + "/auth");

		System.out.println("Authentication Status: " + authResponse.statusCode());

		System.out.println("Authentication Response: " + authResponse.asString());

		authResponse.then().statusCode(200);

		token = authResponse.jsonPath().getString("token");
	}

	@Given("I have a booking ID for deletion")
	public void getBookingId() {
		// Temporary booking ID; replace with a dynamically
		// created booking ID before finalizing the tests.
		bookingId = 1;
	}

	@When("I delete the booking with authentication")
	public void deleteBooking() {

		response = ApiUtils.delete("/booking/" + bookingId, token);
		printResponse();
	}

	@When("I delete the booking without authentication")
	public void deleteWithoutToken() {

		response = given().delete(baseUrl + "/booking/" + bookingId);

		printResponse();
	}

	@Then("the delete booking status code should be {int}")
	public void verifyStatus(int expectedStatus) {
		response.then().statusCode(expectedStatus);
	}

	private void printResponse() {
		System.out.println("Delete Booking Status: " + response.statusCode());

		System.out.println("Delete Booking Response: " + response.asString());
	}

	@When("I delete booking {int} without authentication")
	public void deleteBookingWithoutAuthentication(int bookingId) {

		response = given().delete("https://automationintesting.online/api/booking/" + bookingId);

		System.out.println("Delete Status: " + response.statusCode());
		System.out.println("Delete Response: " + response.asString());
	}
}
