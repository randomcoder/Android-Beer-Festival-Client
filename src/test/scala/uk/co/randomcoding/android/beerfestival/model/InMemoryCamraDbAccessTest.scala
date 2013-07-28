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
}