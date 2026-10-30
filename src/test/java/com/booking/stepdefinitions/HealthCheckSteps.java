package com.booking.stepdefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HealthCheckSteps {

	private Response response;

	@When("I check the booking service health")
	public void iCheckTheBookingServiceHealth() {
		response = given().when().get("https://automationintesting.online/api/booking/actuator/health");

		System.out.println("Health API Status Code: " + response.getStatusCode());
		System.out.println("Health API Response: " + response.asString());
	}

	@Then("the booking service should be running")
	public void theBookingServiceShouldBeRunning() {
		response.then().statusCode(200).body("status", equalTo("UP"));
	}
}
