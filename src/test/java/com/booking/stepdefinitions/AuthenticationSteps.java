package com.booking.stepdefinitions;
import com.booking.models.LoginRequest;
import com.booking.utils.ApiUtils;
import com.booking.utils.TestData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;
public class AuthenticationSteps {
    private Response response;
    @Given("the authentication API is available") public void available() { assertNotNull(ApiUtils.baseUrl()); }
    @When("I login with configured valid credentials")
    public void validLogin() { response = ApiUtils.post("/auth/login", TestData.credentials()); }
    @When("I login with username {string} and password {string}")
    public void login(String username, String password) { response = ApiUtils.post("/auth/login", new LoginRequest(username, password)); }
    @Then("the authentication status code should be {int}")
    public void status(int expected) { assertEquals(expected, response.statusCode()); }
    @Then("the response should contain an authentication token")
    public void token() {
        String token = response.jsonPath().getString("token");
        assertNotNull(token); assertFalse(token.isBlank());
    }
    @Then("the authentication error should be {string}")
    public void error(String expected) { assertEquals(expected, response.jsonPath().getString("error")); }
}
