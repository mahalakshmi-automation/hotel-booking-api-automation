package com.booking.stepdefinitions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class GetBookingSteps {
	private Response response;

	private final String baseUrl = "https://automationintesting.online/api";

	@When("I request booking ID {int} without authentication")
	public void getBookingWithoutAuthentication(int bookingId) {

		response = given().when().get(baseUrl + "/booking/" + bookingId);

		System.out.println("Get Booking Status: " + response.statusCode());
		response.prettyPrint();
	}

	@Then("the get booking status code should be {int}")
	public void verifyStatusCode(int statusCode) {
		response.then().statusCode(statusCode);
	}

	@Then("the get booking error should be {string}")
	public void verifyError(String error) {
		response.then().body("error", equalTo(error));
	}

}
