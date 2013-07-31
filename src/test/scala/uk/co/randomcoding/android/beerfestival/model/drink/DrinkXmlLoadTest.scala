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

  test("A Drink can be successfully created from Xml with one drink element") {
    Given("a JSON array than contains a single brewer element")
    val xml = <object>
                <element name="outcome" type="string" value="success"/>
                <element name="result" type="array">
                  <item index="0" type="object">
                    <element name="Brewery" type="string" value="ELLAND"/>
                    <element name="Beer" type="string" value="1872 Porter"/>
                    <element name="ABV" type="number" value="6.5"/>
                    <element name="Style" type="string" value="STOU"/>
                    <element name="Description" type="string" value="Champion Winter Beer of Britain 2013. Rich, complex and dark porter, with an old port nose and coffee and bitter chocolate flavours on the palate. A 2013 Suggestabeer from Daryl Jenkins."/>
                    <element name="State" type="string" value="Waiting"/>
                    <element name="BreweryName" type="string" value="Elland"/>
                    <element name="Unusual" type="string" value="no"/>
                  </item>
                </element>
              </object>

    When("the Xml is parsed")
    Then("the expected Drink object is created")
    Drink.fromXml(xml) should be(Seq(Drink("1872 Porter", DrinkType.BEER, "1872 Porter",
      "Champion Winter Beer of Britain 2013. Rich, complex and dark porter, with an old port nose and coffee and bitter chocolate flavours on the palate. A 2013 Suggestabeer from Daryl Jenkins.",
      6.5, "Elland", List("STOU"))))
  }

  test("Multiple Drinks can be successfully created from a xml with three drink elements") {
    fail("Not Implemented Yet")
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

  test("Duplicate Drinks are not parsed from the Xml") {
    fail("Not Implemented Yet")
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