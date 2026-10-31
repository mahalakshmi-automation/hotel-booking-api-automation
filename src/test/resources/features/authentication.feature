
@auth @authentication
Feature: Authentication API

  Background:
    Given the authentication API is available

  @positive
  Scenario: Login with valid credentials
    When I login with username "admin" and password "password"
    Then the authentication status code should be 200
    And the response should contain an authentication token

  @negative
  Scenario Outline: Login with invalid credentials
    When I login with username "<username>" and password "<password>"
    Then the authentication status code should be 401
    And the authentication error should be "Invalid credentials"

    Examples:
      | username | password  |
      | admin    | wrong123  |
      | invalid  | password  |
      | invalid  | wrong123  |
