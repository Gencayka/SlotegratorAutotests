Feature: Authorization as Admin

  Scenario: Successful authorization as Admin
    Given authorization page is opened
    When user enters valid login and password
    Then user authorizes as Admin successfully