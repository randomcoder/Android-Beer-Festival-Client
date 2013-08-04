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

import org.xmlpull.v1.XmlPullParser

import uk.co.randomcoding.android.beerfestival.util.xml.BaseXmlPullParser

/**
 * Xml Parser for Drinks
 *
 * @author RandomCoder
 *
 */
class DrinkXmlParser extends BaseXmlPullParser[Drink] {

  private val noNs = null

  override def readEntities(parser: XmlPullParser): Seq[Drink] = {

    var drinks = Seq.empty[Drink]

    while (parser.next != XmlPullParser.END_TAG) {
      parser.getEventType() match {
        case XmlPullParser.START_TAG => parser.getName match {
          case "item" => readEntity(parser) +: drinks
          case _ => // Do Nothing
        }
      }
    }

    drinks
  }

  override def readEntity(parser: XmlPullParser): Drink = {
    parser.require(XmlPullParser.START_TAG, noNs, "item")
    var drinkName = ""
    var drinkDescription = ""
    var drinkType = DrinkType.BEER
    var drinkAbv = 0.0D
    var drinkBrewer = ""
    var drinkFeatures = List.empty[String]

    while (parser.next != XmlPullParser.END_TAG) {
      parser.getEventType() match {
        case XmlPullParser.START_TAG => (parser.getName, readAttribute(parser, "name")) match {
          case ("element", "Name") => drinkName = valueAttribute(parser)
          case ("element", "Description") => drinkDescription = valueAttribute(parser)
          case ("element", "Abv") => drinkAbv = valueAttribute(parser).toDouble
          case ("element", "Cider") => drinkType = DrinkType.CIDER
          case ("element", "Beer") => drinkType = DrinkType.BEER
          case ("element", "Perry") => drinkType = DrinkType.PERRY
          case ("element", "Brewery") | ("element", "Producer") => drinkBrewer = valueAttribute(parser)
          case ("element", "Style") => drinkFeatures = valueAttribute(parser) :: drinkFeatures // TODO: Convert to readable form
          case ("element", "Unusual") => if (valueAttribute(parser).toLowerCase == "yes") drinkFeatures = "Unusual" :: drinkFeatures
          case _ => // Do Nothing
        }
      }
    }

    Drink(drinkName, drinkType, drinkName, drinkDescription, drinkAbv, drinkBrewer, drinkFeatures)
  }

}