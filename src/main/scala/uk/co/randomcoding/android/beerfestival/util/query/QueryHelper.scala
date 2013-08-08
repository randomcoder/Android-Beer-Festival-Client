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

import java.io.{ BufferedInputStream, BufferedOutputStream, InputStream }
import java.net.{ HttpURLConnection, URL }

import uk.co.randomcoding.android.beerfestival.model.brewer.Brewer
import uk.co.randomcoding.android.beerfestival.model.drink.Drink
import uk.co.randomcoding.android.beerfestival.model.festival.Festival

/**
 * Helper object to perform queries to the main web service
 *
 * @author RandomCoder
 *
 */
object QueryHelper {

  private[this] val serviceUrl = new URL("http://www.worcester-beerfest.org.uk/checkAction.php")

  def breweriesXml[A](festivalId: String)(parseFunc: InputStream => A): A = doQuery(queryWithFestival("GetBreweries", festivalId))(parseFunc)
  def beersXml[A](festivalId: String)(parseFunc: InputStream => A): A = doQuery(queryWithFestival("GetBeers", festivalId))(parseFunc)
  def cidersXml[A](festivalId: String)(parseFunc: InputStream => A): A = doQuery(queryWithFestival("GetCiders", festivalId))(parseFunc)
  def producersXml[A](festivalId: String)(parseFunc: InputStream => A): A = doQuery(queryWithFestival("GetProducers", festivalId))(parseFunc)
  def festivalInfoXml[A](festivalId: String)(parseFunc: InputStream => A): A = doQuery(queryWithFestival("GetFestivalInfo", festivalId))(parseFunc)
  def festivalsXml[A]()(parseFunc: InputStream => A): A = doQuery(queryWithoutFestival("GetFestivals"))(parseFunc)

  private[this] def queryWithoutFestival(queryType: String): String = {
    s"""<object><element name="action" type="string" value="${queryType}" /><element name="param" type="object"></element></object>"""
  }

  private[this] def queryWithFestival(queryType: String, festivalId: String = "WOR/2013"): String = {
    s"""<object><element name="action" type="string" value="${queryType}" /><element name="param" type="object"><element name="Festival" type="string" value="${festivalId}" /></element></object>"""
  }

  private[this] def doQuery[A](queryXml: String)(parseFunc: InputStream => A): A = {
    val connection = serviceUrl.openConnection().asInstanceOf[HttpURLConnection]

    try {
      connection.setDoOutput(true)
      connection.setChunkedStreamingMode(0)
      connection.setRequestProperty("Content-Type", "text/xml")
      val oStream = new BufferedOutputStream(connection.getOutputStream())
      oStream.write(queryXml.getBytes())
      oStream.close()

      val inStream = new BufferedInputStream(connection.getInputStream(), 1024)
      val response = parseFunc(inStream)
      inStream.close

      response

    } finally {
      connection.disconnect()
    }

  }
}