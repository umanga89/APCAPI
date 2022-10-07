Feature: Get All Memes API Functional Tests

  Scenario: Verify each meme record has id, tags and image parameters in the response with a value
    Given I populate get all memes request
    When I send the get all memes request
    Then I should see response code returned as 200
    And I see following parameters are returned in get all memes response and not empty
      | parameter | value                      |
      | code      | 200                        |
      | data      | has a non empty json array |
      | message   | GET successful             |
      | next      | has a non-empty value      |

  Scenario: Verify each meme record has id, tags and image parameters in the response with a value
    Given I populate get all memes request
    When I send the get all memes request
    Then I should see response code returned as 200
    And I see following parameters are returned in every record of "data" json array in response and not empty
      | parameter |
      | ID        |
      | tags      |
      | image     |