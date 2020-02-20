#Author: andrei

Feature: Teste simples

@test
Scenario: teste1 simples
Given url 'http://127.0.0.1:8080/'
When method GET
Then status 200

@test
Scenario: teste send data
Given url 'http://127.0.0.1:8080/v0/to-do'
When method PUT
Then status 200
And print 'Response is: ', response
And match response = {"id"} 

