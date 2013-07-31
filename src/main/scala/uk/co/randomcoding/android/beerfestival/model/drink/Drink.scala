/**
 * Copyright (C) 2012 - RandomCoder <randomcoder@randomcoding.co.uk>
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

import scala.util.parsing.json.JSON
import uk.co.randomcoding.android.beerfestival.util.Convertors._
import scala.xml.Elem
import scala.xml.NodeSeq
import scala.xml.Node
import uk.co.randomcoding.android.beerfestival.model.drink.DrinkType

/**
 * Describes a drink
 *
 * @constructor Create a new instance of a drink
 * @param uid The unique Id of this drink
 * @param drinkType The type of drink
 * @param description The drink's description
 * @param abv The drink's abv
 * @param brewer The name of the drink's brewer
 * @param features The features of the drink
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 5 Aug 2012
 */
case class Drink(uid: String, drinkType: DrinkType.drinkType, name: String, description: String, abv: Double, brewer: String, features: List[String])

/**
 * Provides parsing of drink JSON data into a `List[Drink]`
 */
object Drink {
  /**
   * Read drinks from a JSON input string.
   *
   * This can read a multiple or a single drink entity from the input
   *
   * @param jsonData The input drink data in JSON format
   *
   * @return all the drinks that were successfully parsed from the input data
   */
  def fromJson(jsonData: String): Seq[Drink] = JSON.parseFull(jsonData) match {
    case Some(data) => data match {
      case jsonDrinks: List[_] => parseDrinks(jsonDrinks).distinct
      case drinkMap: Map[_, _] => Seq(drinkFromJson(drinkMap))
      case _ => Nil
    }
    case failedParse => Nil
  }

  def fromXml(xml: Elem): Seq[Drink] = {
    val nodes = drinkNodes(xml)
    nodes.map(drinkFromXml)
  }

  private[this] def drinkNodes(xml: Elem): NodeSeq = (xml \\ "element" \ "item").filter(isDrinkNode)

  private[this] val drinkNameElemNames = Seq("Beer", "Cider", "Perry")

  private[this] def isDrinkNode(node: Node): Boolean = {
    (node \\ "element").exists(elem => {
      val elemName = (elem \ "@name").text
      drinkNameElemNames.contains(elemName)
    })
  }

  private[this] def drinkFromXml(drinkNode: Node): Drink = {
    val drinkName = elementValue(drinkNode, drinkNameElemNames: _*)
    val drinkDescription = elementValue(drinkNode, "Description")
    val abv = elementValue(drinkNode, "ABV").toDouble
    val brewer = elementValue(drinkNode, "BreweryName")
    val drinkType = (drinkNode \ "element").find(node => drinkNameElemNames.contains((node \ "@name").text)) match {
      case Some(elem) => (elem \ "@name").text
      case _ => "" // Should cause a match error
    }
    val features = {
      val isUnusual = elementValue(drinkNode, "Unusual").toLowerCase() == "yes"
      val style = elementValue(drinkNode, "Style")

      List(style) ++ (if (isUnusual) Seq("Unusual") else Nil)
    }

    Drink(drinkName, drinkType, drinkName, drinkDescription, abv, brewer, features)
  }

  def elementValue(node: Node, elementNameAttrValue: String*) = {
    (node \ "element").find(node => elementNameAttrValue.contains((node \ "@name").text)) match {
      case Some(elem) => (elem \ "@value").text
      case _ => ""
    }
  }

  private[this] def parseDrinks(drinksJsonData: List[_]): Seq[Drink] = {
    val drinks = drinksJsonData.map(_ match {
      case drinkMap: Map[_, _] => Some(drinkFromJson(drinkMap))
      case _ => None
    })

    drinks
  }

  private[this] def drinkFromJson(drinkJsonMap: Map[String, Any]): Drink = {
    val drinkUid = drinkJsonMap("drinkUid").toString
    val drinkType = drinkJsonMap("drinkType").toString
    val drinkName = drinkJsonMap("name").toString
    val description = drinkJsonMap("description").toString
    val abv = drinkJsonMap("abv").toString.toDouble
    val brewer = drinkJsonMap("brewer").toString
    val features = drinkJsonMap("features").asInstanceOf[List[String]]

    Drink(drinkUid, drinkType, drinkName, description, abv, brewer, features)
  }

  private implicit def stringToDrinkType(in: String): DrinkType.drinkType = DrinkType(in)
}

object DrinkType extends Enumeration {
  type drinkType = Value

  val BEER = Value("Beer")
  val CIDER = Value("Cider")
  val PERRY = Value("Perry")
  val WINE = Value("Wine")

  def apply(typeString: String): drinkType = typeString.toLowerCase match {
    case "beer" => BEER
    case "cider" => CIDER
    case "perry" => PERRY
    case "wine" => WINE
  }
}
