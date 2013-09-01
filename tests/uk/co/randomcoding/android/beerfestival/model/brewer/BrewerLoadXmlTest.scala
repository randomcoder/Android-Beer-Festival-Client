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
import uk.co.randomcoding.android.beerfestival.model.brewer.TestBrewers._
import scala.xml.XML
import scala.xml.Node
import java.io.InputStream
import java.io.ByteArrayInputStream

/**
 * Brief description of BrewerLoadXmlTest
 *
 * @author RandomCoder
 *
 */
class BrewerLoadXmlTest extends SimpleTestBase {
  test("A Brewer can be successfully created from Xml with one brewer element") {
    Given("Xml than contains a single brewer element")

    When("the Xml is parsed")
    Then("the expected Brewer object is created")
    Brewer.fromXml(surround(brewerAdnamsXml)) should be(Seq(brewerAdnams))
  }

  test("A Brewer can be successfully created from Xml with one producer element") {
    Given("Xml than contains a single producer element")

    When("the Xml is parsed")
    Then("the expected Brewer object is created")
    Brewer.fromXml(surround(producerBulmersXml)) should be(Seq(producerBulmers))
  }

  test("Multiple Brewers can be successfully created from a xml with three Brewer elements") {
    Given("Xml than contains three single brewer elements")
    val xml = surround(brewer4TsXml ++ brewerAbbeydaleXml ++ brewerAdnamsXml)
    When("the Xml is parsed")
    Then("the expected Brewer objects are created")
    Brewer.fromXml(xml) should (have size 3 and
      contain(brewer4Ts) and
      contain(brewerAbbeydale) and
      contain(brewerAdnams))
  }

  test("Duplicate Brewers are not parsed from the Xml") {
    Given("Xml that contains two identical Brewer elements")
    val xml = surround(brewer4TsXml ++ brewer4TsXml)
    When("the Xml is parsed")
    Then("only the one expected Brewer is generated")
    Brewer.fromXml(xml) should be(Seq(brewer4Ts))
  }

  test("Brewers can be loaded from a Brewers input xml source") {
    Given("Brewers Xml created from an external input (sample file)")
    val xml = XML.load(getClass.getResourceAsStream("/breweries.xml"))

    When("the Xml is parsed")
    val brewers = Brewer.fromXml(xml)
    Then("it contains the expected number of brewers")
    brewers should have size 134
    And("at least three of the brewers is correctly loaded")
    brewers should (contain(brewer4Ts) and
      contain(brewerAbbeydale) and
      contain(brewerAdnams))
  }

  test("Brewers can be loaded from a Producers input xml source") {
    Given("Producers Xml created from an external input (sample file)")
    val xml = XML.load(getClass.getResourceAsStream("/producers.xml"))

    When("the Xml is parsed")
    val brewers = Brewer.fromXml(xml)
    Then("it contains the expected number of drinks")
    brewers should have size 1
    And("at least one of the Brewers is correctly loaded")
    brewers should be(Seq(producerBulmers))
  }

  private implicit def xmlToInStream(node: Node): InputStream = new ByteArrayInputStream(node.toString().getBytes)
}
