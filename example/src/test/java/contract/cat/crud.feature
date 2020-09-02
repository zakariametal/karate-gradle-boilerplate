Feature: Contract Test between Cat Producer and Cat consumer
  Scenario: create cat
    * def name = 'Billie'
    Given url 'http://localhost:60999'
    And request { name: '#(name)' }
    When method post
    Then status 200
    And match response.name == name
