# language:en


Feature: Student Crud
  User must be able to insert a student by correctly filling out the form

  Scenario: Insert Student
    Given the user is authorized to insert student
    When name, date of bith, sex and active radio is filled out
    And save button is pressed to insert new student
    Then the system must present a list of student containing the new student inserted

