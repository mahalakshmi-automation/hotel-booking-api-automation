@e2e
Feature: End-to-End Booking API

  Scenario: Complete booking lifecycle
    Given I create a new booking for the end-to-end test
    When I retrieve the newly created booking
    And I update the newly created booking
    And I delete the newly created booking
    Then the booking should no longer be available