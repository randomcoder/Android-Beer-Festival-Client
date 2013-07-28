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
import uk.co.randomcoding.android.beerfestival.model.DbAccessTestData
import uk.co.randomcoding.android.beerfestival.util.Convertors._
import uk.co.randomcoding.android.beerfestival.model.InMemoryCamraDbAccess

/**
 * Test for the loading of the Festival Model from Json.
 *
 * The model should be able to load the drink, brewer and festival data
 *
 * @author RandomCoder
 *
 */
class FestivalModelJsonTest extends SimpleTestBase with DbAccessTestData {
  val smallFestivalJson = """{
    | "festivalName": "A Festival",
    | "availableDrinks": [{"drinkUid": "10001", "price": 2.2, "status": "Plenty"},
    |  {"drinkUid": "10003", "price": 2.3, "status": "Not Yet Ready"},
    |  {"drinkUid": "10009", "price": 2.1, "status": "Plenty"}]
    |}""".stripMargin

  test("A Festival Model is correctly initialised with the festival object") {
    Given("valid data that describes a festival")
    val festivalData = smallFestivalJson
    When("the festival model is initialised and retrieved")
    val model = testFestival("A Festival", smallFestivalJson)
    Then("the festival within the model has the expected name")
    model.festival should be(Festival("A Festival",
      Seq(FestivalDrinkData("10001", 2.2, "Plenty"),
        FestivalDrinkData("10003", 2.3, "Not Yet Ready"),
        FestivalDrinkData("10009", 2.1, "Plenty"))))
  }

  test("Festival Model can generate a list of all drinks at the festival") {
    Given("an initialised CamraDb")
    initialiseDbAccess(smallDrinkDbFileLoc, smallBrewerDbFileLoc)
    InMemoryCamraDbAccess.drinks should have size (4)
    InMemoryCamraDbAccess.brewers should have size (3)
    And("a Festival Model")
    val model = testFestival("A Festival", smallFestivalJson)

    When("the festival model is queried for all the drinks at the festival")
    Then("it returns all the expected drinks")
    model.drinksAtFestival should (have size (3) and
      contain(dorothyGoodbodies) and
      contain(astonDark) and
      contain(rotundaRed))
  }

  test("Festival Model can generate a list of all brewers at the festival") {
    Given("an initialised CamraDb")
    initialiseDbAccess(smallDrinkDbFileLoc, smallBrewerDbFileLoc)
    And("a Festival Model")
    val model = testFestival("A Festival", smallFestivalJson)

    When("the festival model is queried for all the brewers at the festival")
    Then("it returns all the expected brewers")
    model.brewersAtFestival should (have size (2) and
      contain(wyeValley) and
      contain(abc))
  }

  def testFestival(festivalName: String, json: String) = {
    FestivalModel.initialiseFromJson(json)
    FestivalModel(festivalName).get
  }
}