package com.booking.stepdefinitions;
import com.booking.utils.ApiUtils;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
public class GetMessages {
    private Response response;
    @When("I want to read the messages") public void messages() { response = ApiUtils.getWithoutAuth("/message"); }
    @Then("I should receive all existing messages") public void verify() {
        response.then().statusCode(200)
            .body("messages.size()", greaterThan(0))
            .body("messages.id", everyItem(notNullValue()))
            .body("messages.name", everyItem(not(isEmptyOrNullString())))
            .body("messages.subject", everyItem(not(isEmptyOrNullString())))
            .body("messages.read", everyItem(notNullValue()));
    }
}
