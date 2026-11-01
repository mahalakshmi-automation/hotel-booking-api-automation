package com.booking.stepdefinitions;
import com.booking.utils.ApiUtils;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;
public class GetBookingSteps {
    private Response response;
    @When("I request booking ID {int} without authentication")
    public void withoutAuth(int id) { response = ApiUtils.getWithoutAuth("/booking/" + id); }
    @When("I request booking ID {int} with valid authentication")
    public void withAuth(int id) { response = ApiUtils.get("/booking/" + id, ApiUtils.loginToken()); }
    @Then("the get booking status code should be {int}")
    public void status(int expected) { assertEquals(expected, response.statusCode()); }
    @Then("the get booking error should be {string}")
    public void error(String expected) { assertEquals(expected, response.jsonPath().getString("error")); }
    @Then("the get booking response should match the JSON schema")
    public void schema() { response.then().body(matchesJsonSchemaInClasspath("schemas/booking-response-schema.json")); }
}
