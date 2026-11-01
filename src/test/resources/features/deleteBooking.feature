@deleteBooking
Feature: Delete Booking API

  Background:
    Given I have a booking ID for deletion

  @positive
  Scenario: Delete an existing booking with valid authentication
    Given I have a valid token for deleting a booking
    When I delete the booking with authentication
    Then the delete booking status code should be 201

  @negative
  Scenario: Delete a booking without authentication
    When I delete the booking without authentication
    Then the delete booking status code should be 401
