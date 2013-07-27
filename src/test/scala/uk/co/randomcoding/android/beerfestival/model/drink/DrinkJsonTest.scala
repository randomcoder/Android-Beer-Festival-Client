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
package uk.co.randomcoding.android.beerfestival.model.drink

import uk.co.randomcoding.android.beerfestival.test.util.SimpleTestBase
import uk.co.randomcoding.android.beerfestival.model.drink.TestDrinks._
/**
 * Tests for the creation of Drink records from JSON
 *
 * @author RandomCoder
 *
 */
class DrinkJsonTest extends SimpleTestBase {
  test("A Drink can be successfully created from a JSON array with one element") {
    Given("a JSON array than contains a single brewer element")
    val json = s"""
      |[
      | $tangerineDuckJson
      |]""".stripMargin
    When("the JSON is parsed")
    Then("the expected Drink object is created")
    Drink.fromJson(json) should be(Seq(tangerineDuck))
  }

  test("Multiple Drinks can be successfully created from a JSON array with three elements") {
    Given("a JSON array than contains three single brewer elements")
    val json = s"""
      |[
      | $rotundaRedJson,
      | $sevilleJson,
      | $buttermereBeautyJson
      |]
      |""".stripMargin

    When("the JSON is parsed")
    Then("the expected Drink objects are created")
    Drink.fromJson(json) should be(Seq(rotundaRed, seville, buttermereBeauty))
  }

  test("Duplicate Drinks are not parsed from the JSON") {
    Given("a JSON array that contains two identical Drink elements")
    val json = s"""
      |[
      | $sevilleJson,
      | $sevilleJson
      |]""".stripMargin

    When("the JSON is parsed")
    Then("only the one expected Drink is generated")
    Drink.fromJson(json) should be(Seq(seville))
  }
}