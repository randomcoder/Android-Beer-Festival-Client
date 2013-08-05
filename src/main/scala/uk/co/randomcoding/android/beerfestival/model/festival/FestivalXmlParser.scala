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

import uk.co.randomcoding.android.beerfestival.util.xml.BaseXmlPullParser
import org.xmlpull.v1.XmlPullParser
import android.util.Log

/**
 * Xml Pull Parser for Loading Festivals
 *
 * @author RandomCoder
 *
 */
class FestivalXmlParser extends BaseXmlPullParser[Festival] {
  val TAG = "Festival Xml Parser"

  override def readEntities(parser: XmlPullParser): Seq[Festival] = {
    var festivals = Seq.empty[Festival]

    while (parser.next != XmlPullParser.END_TAG) {
      Log.i(TAG, s"Element: ${parser.getName}, type: ${parser.getEventType}")
      parser.getEventType() match {
        case XmlPullParser.START_TAG => parser.getName match {
          case "element" => readElement(parser) match {
            case Some(festival) => festivals = festival +: festivals
            case _ => Log.i(TAG, s"Skipping tag ${parser.getName}")
          }
          case tag => {
            Log.i(TAG, s"$tag tag in Festival Parser")
            skip(parser)
          }
        }
      }
    }

    festivals
  }

  private[this] def readElement(parser: XmlPullParser): Option[Festival] = {
    parser.require(XmlPullParser.START_TAG, noNs, "element")
    var fest: Option[Festival] = None

    while (parser.next != XmlPullParser.END_TAG) {
      parser.getEventType() match {
        case XmlPullParser.START_TAG => {
          parser.getName match {
            case "item" => fest = Some(readEntity(parser))
            case _ => skip(parser)
          }
        }
        case _ => // next loop
      }
    }

    fest
  }

  override def readEntity(parser: XmlPullParser): Festival = {
    parser.require(XmlPullParser.START_TAG, noNs, "item")
    var festivalId: String = ""
    var festivalName: String = ""
    var festivalTitle: String = ""
    var description: String = ""

    while (parser.next != XmlPullParser.END_TAG) {
      Log.d(TAG, s"Reading element ${parser.getName} with name: ${readAttribute(parser, "name")}")
      parser.getEventType() match {
        case XmlPullParser.START_TAG => (parser.getName, readAttribute(parser, "name")) match {
          case ("element", "Id") => {
            festivalId = valueAttribute(parser)
            Log.d(TAG, s"""Id $festivalId.""")
            parser.nextTag
          }
          case ("element", "Name") => {
            festivalName = valueAttribute(parser)
            Log.d(TAG, s"""Id $festivalName.""")
            parser.nextTag
          }
          case ("element", "Title") => {
            festivalTitle = valueAttribute(parser)
            Log.d(TAG, s"""Id $festivalTitle.""")
            parser.nextTag
          }
          case ("element", "Description") => {
            description = valueAttribute(parser)
            Log.d(TAG, s"""Id $description.""")
            parser.nextTag
          }
          case (elem, name) => {
            Log.i(TAG, s"Skipping element: $elem with name $name")
            skip(parser)
          }
        }
      }
    }

    Festival(festivalId, festivalName, festivalTitle, description)
  }
}