package com.booking.stepdefinitions;

import static io.restassured.RestAssured.given;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.booking.utils.ApiUtils;

public class UpdateBookingSteps {
	private final String baseUrl = "https://automationintesting.online/api";

	private Response response;
	private String token;
	private int bookingId;

	@Given("I have a valid authentication token")
	public void getToken() {

		String body = """
				{
				  "username": "admin",
				  "password": "password"
				}
				""";

		Response authResponse = given().contentType(ContentType.JSON).body(body).post(baseUrl + "/auth/login");
		System.out.println("Authentication Status: " + authResponse.statusCode());
		// System.out.println("Authentication Response: " + authResponse.asString());

		authResponse.then().statusCode(200);

		token = authResponse.jsonPath().getString("token");
	}

	@Given("I have an existing booking ID")
	public void getBookingId() {

		Response bookingResponse = given().header("Cookie", "token=" + token).queryParam("roomid", 1)
				.get(baseUrl + "/booking");

		bookingResponse.then().statusCode(200);

		bookingId = bookingResponse.jsonPath().getInt("bookings[0].bookingid");

		System.out.println("Using Booking ID: " + bookingId);
	}

	@When("I update the booking details")
	public void updateBooking() {

		String body = """
				{
				  "roomid": 1,
				  "firstname": "John",
				  "lastname": "Smith",
				  "depositpaid": true,
				  "bookingdates": {
				    "checkin": "2027-03-10",
				    "checkout": "2027-03-12"
				  },
				  "email": "john.smith@example.com",
				  "phone": "98765432101"
				}
				""";

		response = ApiUtils.put("/booking/" + bookingId, body, token);

		printResponse();
	}

	@When("I partially update the booking firstname")
	public void patchBooking() {

		response = ApiUtils.patch(
			    "/booking/" + bookingId,
			    "{\"firstname\":\"David\"}",
			    token
			);
		printResponse();
	}

	@When("I update the booking without authentication")
	public void updateWithoutToken() {

		String body = """
				{
				  "roomid": 1,
				  "firstname": "John",
				  "lastname": "Smith",
				  "depositpaid": true,
				  "bookingdates": {
				    "checkin": "2027-03-10",
				    "checkout": "2027-03-12"
				  },
				  "email": "john.smith@example.com",
				  "phone": "98765432101"
				}
				""";

		response = given()
		        .contentType(ContentType.JSON)
		        .body(body)
		        .put(baseUrl + "/booking/" + bookingId);
		printResponse();
	}

	@Then("the update booking status code should be {int}")
	public void verifyUpdateStatus(int expectedStatus) {
		response.then().statusCode(expectedStatus);
	}

	@Then("the partial update status code should be {int}")
	public void verifyPatchStatus(int expectedStatus) {
		response.then().statusCode(expectedStatus);
	}

	private void printResponse() {
		System.out.println("Status Code: " + response.statusCode());
		System.out.println("Response: " + response.asString());
	}

}
