	
@booking
Feature: Create Booking API

  As an API tester
  I want to create hotel bookings
  So that I can verify the booking API

  @positive
  Scenario: Create a booking with valid details
    When I create a booking with valid details
    Then the booking should be created successfully
    And the response should contain a booking ID
    And the created booking details should match the request
