@userDetails
Feature: User Details
  So that I can check if my name and other details correct on the site
  As an API consumer
  I want the User Details API to provide me with my details

  # NOTE: The system is designed for only a single user
  @wip
  Scenario: Get User Details
    Given The User table has the following entry
      | Name | Doe, John   |
      | DoB  | 01/Jan/1768 |
    When I request the User Details API
    Then I'll get the following user details
      | Surname | GivenName | DoB        |
      | Doe     | John      | 1768-01-01 |

  Scenario: Set Preferred Name
    Given The User table has the following entry
      | Name      | DoB         |
      | Doe, John | 01/Jan/1768 |
    When set the preferred name to "John Doe, first of his name, fictitious in nature, king of the unknown, bane of detectives, master of mysteries."
    And I request the User Details API
    Then I'll get the following user details
      | surname | givenName | dob        | preferredName                                                                                                    |
      | Doe     | John      | 1768-01-01 | John Doe, first of his name, fictitious in nature, king of the unknown, bane of detectives, master of mysteries. |
