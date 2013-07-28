/**
 * Copyright (C) 2013 - RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *    RandomCoder <randomcoder@randomcoding.co.uk> - initial API and implementation and/or initial documentation
 */
package uk.co.randomcoding.android.beerfestival.model

import uk.co.randomcoding.android.beerfestival.test.util.SimpleTestBase
import scala.io.Source
import org.scalatest.BeforeAndAfterAll

/**
 * Tests for the use of the In memory database access class used in the initial implementation of the android client.
 *
 * @author RandomCoder
 *
 */
class InMemoryCamraDbAccessTest extends SimpleTestBase with DbAccessTestData {

  test("The In Memory Camra Database Access can be initialised from valid drink and brewer json files") {
    Given("valid input file data for drinks and brewers")
    val drinkSource = Source.fromInputStream(smallDrinkDbFileLoc)
    val brewerSource = Source.fromInputStream(smallBrewerDbFileLoc)

    When("the database access is initialised from the inputs")
    InMemoryCamraDbAccess.init(drinkSource, brewerSource)
    Then("the database contains the expected drinks")
    InMemoryCamraDbAccess.drinks should (have size (4) and
      contain(dorothyGoodbodies) and
      contain(bohemiaPilsner) and
      contain(astonDark) and
      contain(rotundaRed))
    And("it contains the expected brewers")
    InMemoryCamraDbAccess.brewers should (have size (3) and
      contain(wyeValley) and
      contain(wylam) and
      contain(abc))
  }

  // TODO: extract these tests into a common trait when using a different db implementation
  // Drink Search tests
  test("Database Access can find all drinks by a brewer") {
    fail("Not Implemented Yet")
  }

  test("Database Access can find all drinks that contain a substring in their name") {
    fail("Not Implemented Yet")
  }

  test("Database access will return empty results for a drink name search that matches no drinks") {
    fail("Not Implemented Yet")
  }

  test("Database Access can find all drinks that have a specific abv") {
    fail("Not Implemented Yet")
  }

  test("Database Access can find all drinks that have an abv less than a given value") {
    fail("Not Implemented Yet")
  }

  test("Database Access can find all drinks that have an abv greater than a given value") {
    fail("Not Implemented Yet")
  }

  test("Database access will return empty results for a drink abv search that matches no drinks") {
    fail("Not Implemented Yet")
  }

  test("Database Access can fins all drinks that have a substring in their description") {
    fail("Not Implemented Yet")
  }

  test("Database access will return empty results for a drink description search that matches no drinks") {
    fail("Not Implemented Yet")
  }

  // Brewer Search tests
  test("Database Access can find brewer record from drink data") {
    fail("Not Implemented Yet")
  }

  test("Database Access can find all bewers who's names contain a string") {
    fail("Not Implemented Yet")
  }

  test("Database Access can find all brewers from the same location") {
    fail("Not Implemented Yet")
  }

  test("Database Access will return an empty result for a brewer name search than matches no brewers") {
    fail("Not Implemented Yet")
  }

  test("Database Access will return an empty result for a brewer location search than matches no brewers") {
    fail("Not Implemented Yet")
  }

  /**
   * (Re)initialise the database access.
   *
   * This can be used to load different data into the db access for each test
   */
  private[this] def initialiseDbAccess(drinkDbFileLoc: String, brewerDbFileLoc: String) {
    val drinkSource = Source.fromInputStream(drinkDbFileLoc)
    val brewerSource = Source.fromInputStream(brewerDbFileLoc)

    InMemoryCamraDbAccess.init(drinkSource, brewerSource)
  }
}