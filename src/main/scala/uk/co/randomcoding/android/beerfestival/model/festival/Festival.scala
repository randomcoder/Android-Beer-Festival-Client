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

import scala.util.parsing.json.JSON
import uk.co.randomcoding.android.beerfestival.util.Convertors._
import uk.co.randomcoding.android.beerfestival.util.XmlHelpers._
import scala.xml.{ Node, NodeSeq }

/**
 * Brief description of Festival
 *
 * @author RandomCoder
 *
 */
case class Festival(festivalId: String, festivalName: String, festivalTitle: String, description: String = "")

object Festival {

  def fromXml(xml: Node): Seq[Festival] = {
    val nodes = festivalNodes(xml)
    nodes.map(festivalFromNode).distinct
  }

  def festivalNodes(xml: Node): NodeSeq = {
    (xml \\ "element").filter(node => (node \ "@name").text == "result")
  }

  def festivalFromNode(node: Node): Festival = {
    val id = elementValue(node, "Id")
    val name = elementValue(node, "Name")
    val title = elementValue(node, "Title")
    val description = elementValue(node, "Description")

    Festival(id, name, title, description)
  }

  def fromJson(jsonData: String): Option[Festival] = JSON.parseFull(jsonData) match {
    case Some(data) => data match {
      case festivalData: Map[_, _] => Some(festivalFromJson(festivalData))
      case _ => None
    }
    case _ => None
  }

  private[this] def festivalFromJson(festivalJson: Map[String, _]): Festival = {
    val festivalName = festivalJson("festivalName").toString

    Festival("", festivalName, "")
  }
}