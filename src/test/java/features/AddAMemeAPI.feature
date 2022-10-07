Feature:Add A Meme API Functional Tests

  Scenario: Verify Add A Meme request returns error if postSecret and adminPassword headers are missing
    Given I populate add a meme request
    When I send the add a meme request with following parameters without postSecret and adminPassword headers
    |parameter|value|
    |name     |test |
    |tags     |123  |
    |image    |https://imgflip.com/s/meme/Aint-Nobody-Got-Time-For-That.jpg|
    Then I should see response code returned as 200
    And I see following parameters are returned in response with given values
      | parameter |value|
      | code      |401  |
      | data      |[]   |
      | message   |Unauthorized, you require admin access.|