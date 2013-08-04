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

import uk.co.randomcoding.android.beerfestival.model.DbAccessTestData
import uk.co.randomcoding.android.beerfestival.model.brewer.Brewer
import uk.co.randomcoding.android.beerfestival.model.drink.Drink
import uk.co.randomcoding.android.beerfestival.test.util.SimpleTestBase

/**
 * Test for the loading of the Festival Model.
 *
 * The model should be able to load the drink, brewer and festival data
 *
 * @author RandomCoder
 *
 */
class FestivalModelTest extends SimpleTestBase with DbAccessTestData {

  val festivalXml = getClass.getResourceAsStream("/festivalinfo.xml")
  val beersXml = getClass.getResourceAsStream("/beers_small.xml")
  val breweriesXml = getClass.getResourceAsStream("/breweries_small.xml")
  val cidersXml = getClass.getResourceAsStream("/ciders.xml")
  val producersXml = getClass.getResourceAsStream("/producers.xml")

  test("A Festival Model can be created from Drink, Brewer and Festival Data") {
    Given("drinks from combined beer and cider Xml sources")
    val drinks = Drink.fromXml(beersXml) ++ Drink.fromXml(cidersXml)
    And("brewers from combined Brewery and Producer Xml sources")
    val brewers = Brewer.fromXml(breweriesXml) ++ Brewer.fromXml(producersXml)
    And("fesitval data from a festival Xml source")
    val festival = Festival.fromXml(festivalXml)(0)

    When("the Festival Model is created")
    val festivalModel = {
      FestivalModel.initialise(festival, drinks, brewers)
      FestivalModel(festival.festivalId).get
    }

    Then("it contains the expected drink, brewer and festival data")
    festivalModel.festival should be(festival)
    festivalModel.drinks should be(drinks)
    festivalModel.brewers should be(brewers)
  }
}