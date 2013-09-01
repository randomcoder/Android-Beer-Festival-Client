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

    Stream.continually(parser.next).takeWhile(_ != XmlPullParser.END_DOCUMENT).foreach(ev => {
      if (parser.getEventType == XmlPullParser.START_TAG) {

        (parser.getName, readAttribute(parser, "name")) match {
          case ("element", "result") => festivals = readEntity(parser) +: festivals
          case tag => //Log.d(TAG, s"Ignore Element: ${parser.getName}")
        }
      }
    })

    festivals.distinct
  }

  override def readEntity(parser: XmlPullParser): Festival = {
    parser.require(XmlPullParser.START_TAG, noNs, "element")
    var festivalId: String = ""
    var festivalName: String = ""
    var festivalTitle: String = ""
    var description: String = ""

    Stream.continually(parser.next).takeWhile(_ != XmlPullParser.END_TAG).foreach(ev => {
      if (ev == XmlPullParser.START_TAG && parser.getName == "element") {
        parser.require(XmlPullParser.START_TAG, null, "element")
        readAttribute(parser, "name") match {
          case "Id" => festivalId = valueAttribute(parser)
          case "Name" => festivalName = valueAttribute(parser)
          case "Title" => festivalTitle = valueAttribute(parser)
          case "Description" => description = valueAttribute(parser)
          case n => //Log.d(TAG, s"""Unprocessed <element name="$n" value="${valueAttribute(parser)}"/>""")
        }
        parser.next
        parser.require(XmlPullParser.END_TAG, null, "element")
      }
    })

    Festival(festivalId, festivalName, festivalTitle, description)
  }
}
