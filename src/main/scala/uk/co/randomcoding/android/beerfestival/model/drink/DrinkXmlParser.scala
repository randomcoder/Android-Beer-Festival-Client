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
import android.util.Log

/**
 * Xml Parser for Drinks
 *
 * @author RandomCoder
 *
 */
class DrinkXmlParser extends BaseXmlPullParser[Drink] {
  val TAG = "Drink Xml Parser"

  override def readEntities(parser: XmlPullParser): Seq[Drink] = {
    var drinks = Seq.empty[Drink]

    while (parser.next != XmlPullParser.END_TAG) {
      Log.i(TAG, s"Element: ${parser.getName}, type: ${parser.getEventType}")

      parser.getEventType() match {
        case XmlPullParser.START_TAG => parser.getName match {
          case "element" => readElement(parser) match {
            case Some(drink) => drinks = drink +: drinks
            case _ => // Do nothing
          }
          case tag => {
            Log.i(TAG, s"Skipping $tag tag in Drink Parser")
            skip(parser)
          }
        }
      }
    }

    drinks
  }

  private[this] def readElement(parser: XmlPullParser): Option[Drink] = {
    parser.require(XmlPullParser.START_TAG, noNs, "element")
    var drink: Option[Drink] = None

    while (parser.next != XmlPullParser.END_TAG) {
      parser.getEventType() match {
        case XmlPullParser.START_TAG => {
          parser.getName match {
            case "item" => drink = Some(readEntity(parser))
            case _ => skip(parser)
          }
        }
        case _ => // next loop
      }
    }

    drink
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
          case ("element", "Name") => {
            drinkName = valueAttribute(parser)
            Log.d(TAG, s"Drink Name: $drinkName")
            parser.nextTag()
          }
          case ("element", "Description") => {
            drinkDescription = valueAttribute(parser)
            Log.d(TAG, s"Drink Description: $drinkDescription")
            parser.nextTag()
          }
          case ("element", "Abv") => {
            drinkAbv = valueAttribute(parser).toDouble
            Log.d(TAG, s"Drink Abv: $drinkAbv")
            parser.nextTag()
          }
          case ("element", "Cider") => {
            drinkType = DrinkType.CIDER
            Log.d(TAG, s"Drink Type: $drinkType")
            parser.nextTag()
          }
          case ("element", "Beer") => {
            drinkType = DrinkType.BEER
            Log.d(TAG, s"Drink Type: $drinkType")
            parser.nextTag()
          }
          case ("element", "Perry") => {
            drinkType = DrinkType.PERRY
            Log.d(TAG, s"Drink Type: $drinkType")
            parser.nextTag()
          }
          case ("element", "Brewery") | ("element", "Producer") => {
            drinkBrewer = valueAttribute(parser)
            Log.d(TAG, s"Drink Brewer: $drinkBrewer")
            parser.nextTag()
          }
          case ("element", "Style") => {
            drinkFeatures = valueAttribute(parser) :: drinkFeatures // TODO: Convert to readable form
            Log.d(TAG, s"Drink features: $drinkFeatures")
            parser.nextTag()
          }
          case ("element", "Unusual") => {
            if (valueAttribute(parser).toLowerCase == "yes") drinkFeatures = "Unusual" :: drinkFeatures
            Log.d(TAG, s"$drinkName is flagged as Unusual")
          }
          case _ => // Do Nothing
        }
      }
    }

    Drink(drinkName, drinkType, drinkName, drinkDescription, drinkAbv, drinkBrewer, drinkFeatures)
  }

}