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
package uk.co.randomcoding.android.beerfestival.model.festival

import uk.co.randomcoding.android.beerfestival.test.util.SimpleTestBase

/**
 * Brief description of FestivalJsonTest
 *
 * @author RandomCoder
 *
 */
class FestivalJsonTest extends SimpleTestBase {
  test("Festival Data can be loaded from JSON") {
    Given("JSON that describes a festival with two drinks")
    val json = """{
	    | "festivalName": "A Festival",
	    | "availableDrinks": [ 
	    |  { "drinkUid": "10001", "price": 2.1, "status": "Plenty" },
	    |  { "drinkUid": "10002", "price": 2.3, "status": "Plenty" }
	    | ] 
	    |}""".stripMargin

    When("the JSON is parsed")
    val festival = Festival.fromJson(json).get

    Then("The festival has the correct name")
    festival.festivalName should be("A Festival")
    And("it has the correct drink data")
    festival.availableDrinks should (have size (2) and
      contain(FestivalDrinkData("10001", 2.1, "Plenty")) and
      contain(FestivalDrinkData("10002", 2.3, "Plenty")))
  }
}