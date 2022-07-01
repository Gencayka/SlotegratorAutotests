Feature: Authorization as Admin
  Scenario: Successful authorization as Admin
    Given authorization page is opened
    When user enters valid login
    And user enters valid password
    And user clicks the sign in button
    Then admin panel is loaded
    And user is redirected to Dashboard page

