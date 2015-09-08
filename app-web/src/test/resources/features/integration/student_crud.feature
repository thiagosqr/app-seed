# language:en

Feature: Student CRUD

  Scenario: Insert a new student
    Given that a new student needs to be inserted
    When the student form is filled and submitted to the system
    Then the system responds with a http 303 status
    And response header Locataion containing the URL for the new student

  Scenario: Check for null student name
    Given that there is a student with name missing
    When the student form is filled with name missing and submitted to the system
    Then the system responds with http status 422 for missing student name

  Scenario: Check for date of birth in the future
    Given that there is a student with date of birth in the future
    When the student from is filled with dob in the future and submitted to the system
    Then the system responds with http status 422 for student dob in the future

  Scenario: Check for null sex
    Given there is a student with sex as null
    When the student form is filled with sex as null and submitted to the system
    Then the system responds with http status 422 for student sex as null

  Scenario: Read student
    Given there is a student to be read
    When the URL containing student's id is accessed
    Then the system responds with http status 200

  Scenario: Delete student
    Given there is a student to be deleted
    When the URL related to student deletion is accessed
    Then the system responds with http status 303 for student deletion
    But for new requests with the same URL the system responds with http status 404

  Scenario: Update student
    Given there is a student to be updated
    When student information is updated using student form and submitted to the system
    Then the system responds for student update with http status 303

  Scenario: List students
    Given there is a collection of student
    When the first page of the collection containing 5 students is requested
    Then the system responds with a json object containing the 5 students
    And such json object contains the ajax request id
    And the total number if students requested for the query
    And a list containing 5 students
