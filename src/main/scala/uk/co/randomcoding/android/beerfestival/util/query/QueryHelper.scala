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
package uk.co.randomcoding.android.beerfestival.util.query

import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import android.net.http.AndroidHttpClient
import java.net.URL
import java.net.HttpURLConnection
import java.io.BufferedOutputStream
import java.io.BufferedInputStream
import scala.collection.mutable.ListBuffer
import scala.xml.Node
import scala.xml.NodeSeq
import scala.xml.XML

/**
 * Helper object to perform queries to the main web service
 *
 * @author RandomCoder
 *
 */
object QueryHelper {

  private[this] val serviceUrl = new URL("http://tickets-test.worcesterbeerfest.org.uk/BeerList/checkAction.php")

  def breweries(festivalId: String): String = doQuery(queryWithFestival("GetBrewers", festivalId))
  def beers(festivalId: String): String = doQuery(queryWithFestival("GetBeers", festivalId))
  def ciders(festivalId: String): String = doQuery(queryWithFestival("GetCiders", festivalId))
  def brewers(festivalId: String): String = doQuery(queryWithFestival("GetBreweries", festivalId))
  def producers(festivalId: String): String = doQuery(queryWithFestival("GetProducers", festivalId))
  def festivalInfo(festivalId: String): String = doQuery(queryWithFestival("GetFestivalInfo", festivalId))
  def festivals(): String = doQuery(queryWithoutFestival("GetFestivals"))

  private[this] def queryWithoutFestival(queryType: String): String = {
    s"""<object>
      |<element name="action" type="string" value="${queryType}" />
	  |<element name="param" type="object">
	  |</element>
	  |</object>""".stripMargin
  }

  private[this] def queryWithFestival(queryType: String, festivalId: String = "WOR/2013"): String = {
    s"""<object>
      |<element name="action" type="string" value="${queryType}" />
      |<element name="param" type="object">
      |<element name="Festival" type="string" value="${festivalId}" />
      |</element>
      |</object>""".stripMargin
  }

  private[this] def doQuery(queryXml: String) = {
    val connection = serviceUrl.openConnection().asInstanceOf[HttpURLConnection]

    try {
      connection.setDoOutput(true)
      connection.setChunkedStreamingMode(0)
      connection.setRequestProperty("Content-Type", "text/xml")
      val oStream = new BufferedOutputStream(connection.getOutputStream())
      oStream.write(queryXml.getBytes())
      oStream.close()

      val inStream = new BufferedInputStream(connection.getInputStream())
      val buffer = new Array[Byte](1024)
      val response = new ListBuffer[Byte]
      Stream.continually(inStream.read(buffer)).takeWhile(_ != -1).foreach(response ++= buffer.take(_))

      inStream.close()
      new String(response.toArray)
    } finally {
      connection.disconnect()
    }

  }
}