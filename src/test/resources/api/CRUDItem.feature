@sanity
Feature: Item

  Background:
    * url 'https://todo.ly'
    * configure headers = { 'Content-Type': 'application/json', 'Authorization':'Basic YXBpMjAyNUBhcGkyMDI1LmNvbToxMjM0NQ==' }

  Scenario: CRUD

    ##CREATE
    Given path '/api/items.json'
    * def body =
      """
    {
      "Content":"Karate_Item"
    }
    """
    * request body
    When method post
    * print response
    Then  status 200
    And match response.Content == 'Karate_Item'
    * def id_item = response.Id
    * def nameProject = response.Content
    * print '******************************'
    * print id_item
    * print nameProject
    * print '******************************'

    ## READ
    Given path '/api/items/' + id_item + '.json'
    When method get
    Then status 200
    * match response.Content == 'Karate_Item'

    ##UPDATE
    Given path '/api/items/' + id_item + '.json'
    * def body =
      """
    {
      "Content":"Karate_Item_Update"
    }
    """
    * request body
    When method put
    * print response
    Then  status 200
    * match response.Content == 'Karate_Item_Update'


    ##DELETE
    Given path '/api/items/' + id_item + '.json'
    When method delete
    * print response
    Then  status 200
    And match response.Content == 'Karate_Item_Update'
    * match response.Deleted == true
