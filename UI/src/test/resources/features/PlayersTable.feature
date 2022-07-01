Feature: Players table
  Scenario Outline: Sort players by column
    Given "Players" page is opened
    When user clicks on "<column>" column name in Players table
    Then players table is sorted by "<column>"

    Examples:
      | column      |
      | Username    |
      | External ID |
      | Name        |