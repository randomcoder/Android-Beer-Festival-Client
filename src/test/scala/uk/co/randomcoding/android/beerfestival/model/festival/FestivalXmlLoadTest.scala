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
 * Brief description of FestivalXmlLoadTest
 *
 * @author RandomCoder
 *
 */
class FestivalXmlLoadTest extends SimpleTestBase {
  test("Festival Data can be loaded from Xml") {
    Given("Xml that describes a festival with two drinks")
    val xml = getClass.getResourceAsStream("/festivalinfo.xml")

    When("the Xml is parsed")
    Then("The correct festivals are loaded")
    Festival.fromXml(xml) should be(Seq(Festival("WOR/2013", "Worcester", "Worcester Beer, Cider and Perry Festival - 2013",
      "Summer festival in a large marquee on the racecourse in the centre of historic Worcester City.")))
  }
}