Feature:
  Background:
    * def id = 0
    * def m = {'test':'This is test response!'}
  Scenario: methodIs('post')
    * def c = request
    * def id = ~~(id + 1)
    * c.id = id
    * m[id + ''] = c
    * def response = c
  Scenario: pathMatches('/cats/{id}')
    * def response = m[pathParams.id]
  Scenario:
    * karate.proceed("http://dummy.restapiexample.com")
