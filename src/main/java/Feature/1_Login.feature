
Feature: Login feature
  @Loginapplication
  Scenario Outline: Verify the login into Application
    Given The user navigates to the login page
    Then user clicks username and password
    When user enters all the mandatory fields for "<user_type>" user and clicks continue to the home screen
    Then verify if page is navigating to home screen
    Then user clicks on the option

    Examples:
      | user_type |
      | Test_001  |
