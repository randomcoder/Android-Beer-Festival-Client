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
package uk.co.randomcoding.android.beerfestival.util

import scala.xml.Node
import scala.xml.XML

/**
 * Helpers for use with the Beer Festival JUG Xml
 *
 * @author RandomCoder
 *
 */
object XmlHelpers {

  /**
   * Get the value of an xml node `<element name="n" value="v"/>`
   *
   * @param node The node to use
   * @param elementNameAttrValues The possible values for the `name` attribute on the node
   *
   * @return the value of the `value` attribute or an empty string if not found
   */
  def elementValue(node: Node, elementNameAttrValues: String*) = {
    (node \ "element").find(node => elementNameAttrValues.contains((node \ "@name").text)) match {
      case Some(elem) => (elem \ "@value").text
      case _ => ""
    }
  }

  implicit def stringToXml(xmlString: String): Node = XML.loadString(xmlString)
}