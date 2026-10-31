package com.booking.stepdefinitions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Assertions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AuthenticationSteps {
	private Response response;
	private final String baseUrl = "https://automationintesting.online/api";

	@Given("the authentication API is available")
	public void authenticationApiAvailable() {
		// Base URL is configured for the authentication requests.
	}

	@When("I login with username {string} and password {string}")
	public void login(String username, String password) {

		String requestBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);

		response = given().contentType(ContentType.JSON).body(requestBody).when().post(baseUrl + "/auth/login");

		System.out.println("Auth Status Code: " + response.statusCode());
		response.prettyPrint();
	}

	@Then("the authentication status code should be {int}")
	public void verifyStatusCode(int expectedStatusCode) {
		response.then().statusCode(expectedStatusCode);
	}

	@Then("the response should contain an authentication token")
	public void verifyToken() {
		String token = response.jsonPath().getString("token");

		Assertions.assertNotNull(token, "Token should not be null");
		Assertions.assertFalse(token.isBlank(), "Token should not be empty");
	}

	@Then("the authentication error should be {string}")
	public void verifyError(String expectedError) {
		response.then().body("error", equalTo(expectedError));
	}

}
