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
package uk.co.randomcoding.android.beerfestival.util.query

import scala.xml.XML

import uk.co.randomcoding.android.beerfestival.test.util.SimpleTestBase
import uk.co.randomcoding.android.beerfestival.util.query.QueryHelper._

/**
 * Simple Tests for the Query Helper object
 *
 * @author RandomCoder
 *
 */
class QueryHelpersTest extends SimpleTestBase {
  test("Query Helper can get a list of festivals") {
    Given("a working connection to the internet")
    // TODO: Need to check for this somehow
    When("the 'festivals' query is used")
    Then("the response can be parsed into Xml")
    val response = festivalsXml
    val responseXml = XML.loadString(response)
    And("has nodes for the festival ids")
    val itemNodes = (responseXml \\ "element" \ "item")
    /*val worcNode = itemNodes.filter(node => elementValue(node, "Id") == "WOR/2013")
    worcNode should not be ('empty)*/
    fail("Not Completed Yet")
  }
}