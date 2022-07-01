Feature: Going to any page by side buttons
  Scenario: Open players list
    Given "Dashboard" page is opened
    When user clicks on "Users" side button
    And user clicks on "Players" side subbutton
    Then user is redirected to Players page
    And players table is loaded