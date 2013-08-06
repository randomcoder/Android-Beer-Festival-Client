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

import org.xmlpull.v1.XmlPullParser

import android.util.Log
import uk.co.randomcoding.android.beerfestival.util.xml.BaseXmlPullParser

/**
 * Xml Pull Parser for reading Brewer Xml
 *
 * @author RandomCoder
 *
 */
class BrewerXmlParser extends BaseXmlPullParser[Brewer] {
  val TAG = "Brewer Xml Pasrser"

  override def readEntities(p: XmlPullParser): Seq[Brewer] = {
    var brewers = Seq.empty[Brewer]

    Stream.continually { p.next }.takeWhile(ev => ev != XmlPullParser.END_DOCUMENT).foreach(ev => {
      if (p.getEventType == XmlPullParser.START_TAG) {

        p.getName match {
          case "item" => brewers = readEntity(p) +: brewers
          case tag => Log.d(TAG, s"Ignore Element: ${p.getName}")
        }
      }
    })

    brewers
  }

  override def readEntity(parser: XmlPullParser): Brewer = {
    var id = ""
    var name: String = ""
    var location: String = ""
    var county = ""
    var postCode = ""
    var description: String = ""

    Stream.continually(parser.next).takeWhile(_ != XmlPullParser.END_TAG && parser.getName != "item").foreach(ev => {
      if (ev == XmlPullParser.START_TAG && parser.getName == "element") {
        parser.require(XmlPullParser.START_TAG, null, "element")
        readAttribute(parser, "name") match {
          case "Id" => id = valueAttribute(parser)
          case "Name" => name = valueAttribute(parser)
          case "Location" => location = valueAttribute(parser)
          case "County" => county = valueAttribute(parser)
          case "Postcode" => postCode = valueAttribute(parser)
          case n => Log.d(TAG, s"""Unprocessed <element name="$n" value="${valueAttribute(parser)}"/>""")
        }
        parser.next
        parser.require(XmlPullParser.END_TAG, null, "element")
      }
    })
    parser.require(XmlPullParser.END_TAG, null, "item")

    Brewer(id, name, s"$location, $county. $postCode", description)
  }
}