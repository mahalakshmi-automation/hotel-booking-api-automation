@smoke @health
Feature: Check Booking API availability

  Scenario: Verify the booking service is available
    When I check the booking service health
    Then the booking service should be running