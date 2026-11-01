
@updateBooking
Feature: Update Booking API

  Background:
    Given I have an existing booking ID

  @positive
  Scenario: Update an existing booking
    Given I have a valid authentication token
    When I update the booking details
    Then the update booking status code should be 200

  @positive
  Scenario: Partially update an existing booking
    Given I have a valid authentication token
    When I partially update the booking firstname
    Then the partial update status code should be 200

  @negative
  Scenario: Update booking without authentication
    When I update the booking without authentication
    Then the update booking status code should be 401
