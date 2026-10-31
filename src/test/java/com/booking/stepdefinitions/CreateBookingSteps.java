package com.booking.stepdefinitions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Assertions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateBookingSteps {
	private Response response;

	private final String baseUrl = "https://automationintesting.online/api";

	@When("I create a booking with valid details")
	public void createBooking() {

		String requestBody = """
				{
				  "roomid": 1,
				  "firstname": "John",
				  "lastname": "Doe",
				  "depositpaid": true,
				  "bookingdates": {
				    "checkin": "2027-03-10",
				    "checkout": "2027-03-12"
				  },
				  "email": "john.doe@example.com",
				  "phone": "98765432101"
				}
				""";

		response = given().contentType(ContentType.JSON).body(requestBody).when().post(baseUrl + "/booking");
		System.out.println("Status Code: " + response.statusCode());
		response.prettyPrint();
	}

	@Then("the booking should be created successfully")
	public void verifyStatusCode() {
		response.then().statusCode(201);
	}

	@Then("the response should contain a booking ID")
	public void verifyBookingId() {
		Integer bookingId = response.jsonPath().getInt("bookingid");

		Assertions.assertTrue(bookingId > 0, "Booking ID should be greater than zero");
	}

	@Then("the created booking details should match the request")
	public void verifyBookingDetails() {
		response.then().body("booking.roomid", equalTo(2)).body("booking.firstname", equalTo("John"))
				.body("booking.lastname", equalTo("Doe")).body("booking.depositpaid", equalTo(true))
				.body("booking.email", equalTo("john.doe@example.com")).body("booking.phone", equalTo("98765432101"));
	}

	@When("I create a booking without request body")
	public void createBookingWithoutBody() {
		response = given().contentType(ContentType.JSON).post("https://automationintesting.online/api/booking");

		System.out.println("Missing Body Status: " + response.statusCode());
		System.out.println("Response: " + response.asString());
	}

	@When("I create a booking without firstname")
	public void createBookingWithoutFirstname() {

		String requestBody = """
				{
				  "roomid": 1,
				  "lastname": "Smith",
				  "depositpaid": true,
				  "bookingdates": {
				    "checkin": "2027-03-10",
				    "checkout": "2027-03-12"
				  },
				  "email": "john@example.com",
				  "phone": "9876543210"
				}
				""";

		response = given().contentType(ContentType.JSON).body(requestBody)
				.post("https://automationintesting.online/api/booking");

		System.out.println("Missing Firstname Status: " + response.statusCode());
		System.out.println("Response: " + response.asString());

	}

	@Then("the create booking status code should be {int}")
	public void verifyCreateBookingStatus(int statusCode) {
		response.then().statusCode(statusCode);
	}
}
