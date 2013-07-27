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
package uk.co.randomcoding.android.beerfestival.model.brewer

import uk.co.randomcoding.android.beerfestival.test.util.SimpleTestBase

/**
 * Tests for the reading of Brewer Data from Json
 *
 * @author RandomCoder
 */
class BrewerJsonTest extends SimpleTestBase {

  private[this] val auldBrewers = Brewer("Auld Brewers", "Up North", Set("10001", "10002", "10003"))
  private[this] val newBrewers = Brewer("New Brewers", "In Scotland", Set("20001"))
  private[this] val farmBrewer = Brewer("Farm Brewer", "A Farm", Set("30001", "30002"))

  test("A Brewer can be successfully created from a JSON array with one element") {
    Given("a JSON array than contains a single brewer element")
    val json = """
      |[{
      |  "name": "Auld Brewers",
      |  "location": "Up North",
      |  "drinkUids": ["10001", "10002", "10003"]
      | }
      |]""".stripMargin
    When("the JSON is parsed")
    Then("the expected Brewer object is created")
    Brewer.fromJson(json) should be(Seq(auldBrewers))
  }

  test("Multiple Brewers can be successfully created from a JSON array with three elements") {
    Given("a JSON array than contains three single brewer elements")
    val json = """
      |[{
      |  "name": "Auld Brewers",
      |  "location": "Up North",
      |  "drinkUids": ["10001", "10002", "10003"]
      | },
      | {
      |  "name": "New Brewers",
      |  "location": "In Scotland",
      |  "drinkUids": [ "20001" ]
      | },
      | {
      |  "name": "Farm Brewer",
      |  "location": "A Farm",
      |  "drinkUids": [ "30001", "30002" ]
      | }
      |]""".stripMargin

    When("the JSON is parsed")
    Then("the expected Brewer objects are created")
    Brewer.fromJson(json) should be(Seq(auldBrewers, newBrewers, farmBrewer))
  }

  test("Duplicate Brewers are not parsed from the JSON") {
    Given("a JSON array that contains two identical Brewer elements")
    val json = """
      |[{
      |  "name": "Farm Brewer",
      |  "location": "A Farm",
      |  "drinkUids": [ "30001", "30002" ]
      | },
      | {
      |  "name": "Farm Brewer",
      |  "location": "A Farm",
      |  "drinkUids": [ "30001", "30002" ]
      | }
      |]""".stripMargin

    When("the JSON is parsed")
    Then("only the one expected Brewer is generated")
    Brewer.fromJson(json) should be(Seq(farmBrewer))
  }
}