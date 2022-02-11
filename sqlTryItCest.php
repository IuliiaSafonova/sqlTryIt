<?php

class SqlTryCest
{
  /**
   * @param ApiTester $I
   * @throws Exception
   */
  public function _before(ApiTester $I) {
    $I->amConnectedToDatabase('sql_tryit');
  }

  // test
  //check that address matches for specific customer
  public function addressForGiovanni(ApiTester $I) {
    $address = $I->grabFromDatabase("Customers", "Address", ['ContactName' => 'Giovanni Rovelli']);
    $I->assertEquals($address, "Via Ludovico il Moro 22");
  }
  //check amount of customer from London matches
  public function cityLondon(ApiTester $I) {
    //grab all Customers from London
    $I->grabFromDatabase('Customers', ['City' => 'London']);
    //assert that amount is 6
    $I->seeNumRecords(6, 'Customers', ['City' => 'London']);
  }

  //insert record to DB and check it was inserted
  public function insertRecord(ApiTester $I) {
    //add record to the DB
    $CustomerID = $I->haveInDatabase('Customers',
        array('CustomerName' => 'Iuliia Safonova', 'ContactName' => 'Victoria Kulevcova', 'Address' => 'Main Av. 123',
            'City' => 'Limassol',
            'PostalCode' => '4528', 'Country' => 'Cyprus'));
    //assert that record was added
    $I->seeInDatabase('Customer', ['CustomerID' => $CustomerID]);
  }

  //updated one of the record and check if it was updated
  public function refreshRecord(ApiTester $I) {
    $I->updateInDatabase('Customers',
        array('CustomerName' => 'Iuliia Safonova', 'ContactName' => 'Victoria Kulevcova', 'Address' => 'Main Av. 123',
            'City' => 'Limassol', 'PostalCode' => '4528', 'Country' => 'Cyprus'), array('CustomerID' => 1));
    //check the record has been updated
    $I->seeInDatabase('Customers', ['CustomerID' => 1, 'CustomerName' => 'Iuliia Safonova', 'ContactName' => 'Victoria Kulevcova',
        'Address' => 'Main Av. 123', 'City' => 'Limassol', 'PostalCode' => '4528', 'Country' => 'Cyprus']);
  }

  //my personal test to delete entity from DB and check it was deleted
  public function myTest(ApiTester $I) {
    //delete entity from Db using API call where $id is CustomerId
    $id=5;
    $I->sendDelete("url/{$id}");
    $I->assertEquals(200, $I->grabResponse());
    //check entity was deleted from DB
    $I->cantSeeInDatabase('Customers', ['CustomerID' => $id]);
  }
}
