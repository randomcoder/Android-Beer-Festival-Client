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
import uk.co.randomcoding.android.beerfestival.util.Convertors._
import uk.co.randomcoding.android.beerfestival.model.drink.TestDrinks._
import scala.xml.XML

/**
 * Brief description of DrinkXmlLoadTest
 *
 * @author RandomCoder
 *
 */
class DrinkXmlLoadTest extends SimpleTestBase {
  val beersXmlStream = getClass.getResourceAsStream("/beers_small.xml")

  test("A Drink can be successfully created from Xml with one drink element (beer)") {
    Given("Xml than contains a single beer element")

    When("the Xml is parsed")
    Then("the expected Drink object is created")
    Drink.fromXml(porter1872Xml) should be(Seq(porter1872))
  }

  test("A Drink can be successfully created from Xml with one drink element (cider)") {
    Given("Xml than contains a single cider element")

    When("the Xml is parsed")
    Then("the expected Drink object is created")
    Drink.fromXml(deadDogXml) should be(Seq(deadDog))
  }

  test("Multiple Drinks can be successfully created from a xml with three drink elements") {
    Given("Xml than contains three single drink elements")

    When("the Xml is parsed")
    Then("the expected Drink objects are created")
    Drink.fromXml(threeDrinksXml) should (have size (3) and
      contain(porter1872) and
      contain(deadDog) and
      contain(alederflower))
  }

  test("Duplicate Drinks are not parsed from the Xml") {
    Given("Xml that contains two identical Drink elements")

    When("the Xml is parsed")
    Then("only the one expected Drink is generated")
    Drink.fromXml(duplicateAlederflowerXml) should be(Seq(alederflower))
  }
}