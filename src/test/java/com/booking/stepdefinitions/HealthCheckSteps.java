package com.booking.stepdefinitions;
import com.booking.utils.ApiUtils;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;
public class HealthCheckSteps {
    private Response response;
    @When("I check the booking service health") public void check() { response = ApiUtils.getWithoutAuth("/booking/actuator/health"); }
    @Then("the booking service should be running") public void running() {
        assertEquals(200, response.statusCode());
        assertEquals("UP", response.jsonPath().getString("status"));
    }
}
