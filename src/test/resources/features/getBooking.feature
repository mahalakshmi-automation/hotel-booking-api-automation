@getbooking
Feature: Get Booking API

  @negative
  Scenario: Get booking without authentication
    When I request booking ID 1 without authentication
    Then the get booking status code should be 401
    And the get booking error should be "Unauthorized"

  @positive
  Scenario: Get booking with valid authentication
    When I request booking ID 1 with valid authentication
    Then the get booking status code should be 200
    And the get booking response should match the JSON schema
