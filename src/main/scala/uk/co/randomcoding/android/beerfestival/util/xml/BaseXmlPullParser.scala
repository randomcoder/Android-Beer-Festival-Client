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
package uk.co.randomcoding.android.beerfestival.util.xml

import java.io.InputStream
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
//import android.util.Xml

/**
 * Brief description of BaseXmlPullParser
 *
 * @author RandomCoder
 *
 */
abstract class BaseXmlPullParser[T] {

  final val noNs = null

  final def parse(xmlStream: InputStream): Seq[T] = {
    try {
      val factory = XmlPullParserFactory.newInstance()
      factory.setNamespaceAware(false)
      val parser = factory.newPullParser()
      parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
      parser.setInput(xmlStream, null)
      parser.nextTag()
      readEntities(parser)
    } finally { xmlStream.close }
  }

  def readEntities(parser: XmlPullParser): Seq[T]

  def readEntity(parser: XmlPullParser): T

  final def skip(parser: XmlPullParser) {
    if (parser.getEventType() != XmlPullParser.START_TAG) {
      throw new IllegalStateException();
    }
    var depth = 1;
    while (depth != 0) {
      parser.next() match {
        case XmlPullParser.END_TAG => depth -= 1
        case XmlPullParser.START_TAG => depth += 1
      }
    }
  }

  final def readAttribute(parser: XmlPullParser, attributeName: String): String = {
    parser.getAttributeValue(null, attributeName)
  }

  final def valueAttribute(parser: XmlPullParser): String = readAttribute(parser, "value")
}