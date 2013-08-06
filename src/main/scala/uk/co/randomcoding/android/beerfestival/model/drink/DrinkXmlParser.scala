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

import android.util.Log
import uk.co.randomcoding.android.beerfestival.util.xml.BaseXmlPullParser

/**
 * Xml Parser for Drinks
 *
 * @author RandomCoder
 *
 */
class DrinkXmlParser extends BaseXmlPullParser[Drink] {
  val TAG = "Drink Xml Parser"

  override def readEntities(p: XmlPullParser): Seq[Drink] = {
    var drinks = Seq.empty[Drink]
    Stream.continually { p.next }.takeWhile(ev => ev != XmlPullParser.END_DOCUMENT).foreach(ev => {
      if (p.getEventType == XmlPullParser.START_TAG) {
        p.getName match {
          case "item" => drinks = readEntity(p) +: drinks
          case tag => Log.d(TAG, s"Ignore Element: ${p.getName}")
        }
      }
    })

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

    Stream.continually(parser.next).takeWhile(_ != XmlPullParser.END_TAG && parser.getName != "item").foreach(ev => {
      if (ev == XmlPullParser.START_TAG && parser.getName == "element") {
        parser.require(XmlPullParser.START_TAG, null, "element")
        readAttribute(parser, "name") match {
          case "Beer" => {
            drinkName = valueAttribute(parser)
            drinkType = DrinkType.BEER
          }
          case "Cider" => {
            drinkName = valueAttribute(parser)
            drinkType = DrinkType.CIDER
          }
          case "Perry" => {
            drinkName = valueAttribute(parser)
            drinkType = DrinkType.PERRY
          }
          case "Description" => drinkDescription = valueAttribute(parser)
          case "Abv" => drinkAbv = valueAttribute(parser).toDouble
          case "Brewery" | "Producer" => drinkBrewer = valueAttribute(parser)
          case "Style" => drinkFeatures = valueAttribute(parser) +: drinkFeatures
          case "Unusual" => if (valueAttribute(parser).toLowerCase() == "yes") drinkFeatures = "Unusual" +: drinkFeatures
          case n => Log.d(TAG, s"""Unprocessed <element name="$n" value="${valueAttribute(parser)}"/>""")
        }
        parser.next
        parser.require(XmlPullParser.END_TAG, null, "element")
      }
    })
    parser.require(XmlPullParser.END_TAG, null, "item")

    Drink(drinkName, drinkType, drinkName, drinkDescription, drinkAbv, drinkBrewer, drinkFeatures)
  }

}