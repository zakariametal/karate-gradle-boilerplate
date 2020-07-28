Feature: karate 'hello world' example
  Scenario: Successful hit dummy API

    Given url 'http://dummy.restapiexample.com/api/v1/employees'
    When method get
    Then status 200
