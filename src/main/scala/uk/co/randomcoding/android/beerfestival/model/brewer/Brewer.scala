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

import java.io.InputStream

import scala.util.parsing.json.JSON

import uk.co.randomcoding.android.beerfestival.util.Convertors._

/**
 * @constructor Create a new instance of a Brewer
 *
 * @param id The unique id of the brewer
 * @param name The brewer's name
 * @param location Where the brewer is located
 * @param drinkUids The `uids` of all the drinks brewed by this brewer
 *
 * @author RandomCoder
 */
case class Brewer(id: String, name: String, location: String, description: String = "")

object Brewer {
  def fromXml(brewersXml: InputStream): Seq[Brewer] = new BrewerXmlParser().parse(brewersXml) //brewerNodes(brewersXml).map(brewerFromNode).distinct

  /*private[this] def brewerNodes(brewersXml: Node): NodeSeq = (brewersXml \\ "element" \ "item")

  private[this] def brewerFromNode(brewerNode: Node): Brewer = {
    val brewerName = elementValue(brewerNode, "Name")
    val brewerDescription = elementValue(brewerNode, "Description")
    val brewerLocation = Seq("Location", "County", "Postcode").map(elementValue(brewerNode, _)).filterNot(_.isEmpty).mkString(", ")

    Brewer(brewerName, brewerLocation, brewerDescription)
  }*/

  /**
   * Read brewers from a JSON input string.
   *
   * This can read a multiple or a single brewer entity from the input
   *
   * @param jsonData The input brewer data in JSON format
   *
   * @return all the brewers that were successfully parsed from the input data
   */
  def fromJson(jsonData: String): Seq[Brewer] = JSON.parseFull(jsonData) match {
    case Some(data) => data match {
      case brewersData: List[_] => convertBrewers(brewersData).distinct
      case brewerData: Map[_, _] => Seq(brewerFromJson(brewerData))
      case _ => Nil
    }
    case failedParse => Nil
  }

  private[this] def brewerFromJson(brewerJson: Map[String, Any]): Brewer = {
    val id = brewerJson("id").toString
    val name = brewerJson("name").toString
    val location = brewerJson("location").toString

    Brewer(id, name, location)
  }

  private[this] def convertBrewers(brewerJsonData: List[_]): List[Brewer] = {
    val brewers = brewerJsonData.map(_ match {
      case brewerDataMap: Map[_, _] => Some(brewerFromJson(brewerDataMap))
      case _ => None
    })

    brewers
  }
}