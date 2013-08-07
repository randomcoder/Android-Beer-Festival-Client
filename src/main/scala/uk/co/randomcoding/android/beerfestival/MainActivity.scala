/**
 * Copyright (C) 2012 RandomCoder <randomcoder@randomcoding.co.uk>
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
 *    RandomCoder - initial API and implementation and/or initial documentation
 */
package uk.co.randomcoding.android.beerfestival

import java.io.InputStream
import scala.annotation.tailrec
import scala.collection.immutable.Stream
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import uk.co.randomcoding.android.beerfestival.model.brewer.Brewer
import uk.co.randomcoding.android.beerfestival.model.drink.Drink
import uk.co.randomcoding.android.beerfestival.model.festival.{ FestivalModel, FestivalXmlParser }
import uk.co.randomcoding.android.beerfestival.util.query.QueryHelper._
import uk.co.randomcoding.android.beerfestival.util.IntentExtras._
import uk.co.randomcoding.android.beerfestival.model.drink.DrinkType
import android.content.Context

class MainActivity extends Activity with TypedActivity {

  private[this] val festivalXmlFile = "festivals.xml"
  private[this] val beersXmlFile = "beers.xml"
  private[this] val cidersXmlFile = "ciders.xml"
  private[this] val brewersXmlFile = "brewers.xml"
  private[this] val producersXmlFile = "producers.xml"

  // This is fixed for now, but will be derived from config at some point.
  private[this] final val worcesterId = "WOR/2013"

  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)
    // Initialise Festival Data
    // Currently only uses Worcester (WOR/2013)
    reloadFestivalModel(worcesterId, !(fileList().exists(_ == festivalXmlFile)))
  }

  override def onDestroy() {
    super.onDestroy
    // Write the festival model(s) to storage (GSON?)
  }

  def showAllDrinks(view: View) {
    val intent = new Intent(this, classOf[DisplayResultsActivity])
    allDrinksIntentExtras foreach { case (k, v) => intent.putExtra(k, v) }
    startActivity(intent)
  }

  private[this] def allDrinksIntentExtras: Map[String, String] = {
    Map(FESTIVAL_ID_EXTRA -> worcesterId)
  }

  private[this] def drinkTypeSearchIntentExtras(drinkType: DrinkType.drinkType): Map[String, String] = {
    Map(FESTIVAL_ID_EXTRA -> worcesterId, DRINK_TYPE_SEARCH_EXTRA -> drinkType.toString())
  }

  def showAllBeers(view: View) {
    val intent = new Intent(this, classOf[DisplayResultsActivity])
    drinkTypeSearchIntentExtras(DrinkType.BEER) foreach { case (k, v) => intent.putExtra(k, v) }
    startActivity(intent)
  }

  def showAllCiders(view: View) {
    val intent = new Intent(this, classOf[DisplayResultsActivity])
    drinkTypeSearchIntentExtras(DrinkType.CIDER) foreach { case (k, v) => intent.putExtra(k, v) }
    startActivity(intent)
  }

  def showAllPerries(view: View) {
    val intent = new Intent(this, classOf[DisplayResultsActivity])
    drinkTypeSearchIntentExtras(DrinkType.PERRY) foreach { case (k, v) => intent.putExtra(k, v) }
    startActivity(intent)
  }

  def showAllBrewers(view: View) {
    /*val intent = new Intent(this, classOf[DisplayBrewersActivity])
    allDrinksIntentExtras foreach { case (k, v) => intent.putExtra(k, v) }
    startActivity(intent)*/
  }

  private[this] def allBrewersIntentExtras: Map[String, String] = {
    Map.empty
  }

  def showSearchDrinks(view: View) {
    val intent = new Intent(this, classOf[SearchDrinkActivity])
    startActivity(intent)
  }

  def showSearchBrewers(view: View) {
    /*val intent = new Intent(this, classOf[SearchBrewerActivity])
    startActivity(intent)*/
  }

  def updateData(view: View) {
    reloadFestivalModel(worcesterId, true)
  }

  def showWishList(view: View) {
    // TODO: Create intent to switch to the wishlist view
    // Not planned at this time
  }

  private[this] def reloadFestivalModel(festivalId: String, reloadData: Boolean) {
    val TAG = "MainActivityFestivalInitialise"
    if (reloadData) updateStoredData(festivalId)

    FestivalModel(festivalId) match {
      case None => {
        Log.i(TAG, s"Initialising festival model for $festivalId")

        val festival = new FestivalXmlParser().parse(openFileInput(festivalXmlFile)).find(_.festivalId == festivalId).get
        //festivalsXml() { stream: InputStream => new FestivalXmlParser().parse(stream) }.find(_.festivalId == festivalId).get
        Log.d(TAG, s"Loaded Festival $festival")
        val beersAtFestival = Drink.fromXml(openFileInput(beersXmlFile))
        //beersXml(festivalId) { stream: InputStream => Drink.fromXml(stream) }
        Log.d(TAG, s"Loaded ${beersAtFestival.size} Beers")
        val brewersAtFestival = Brewer.fromXml(openFileInput(brewersXmlFile))
        //breweriesXml(festivalId) { stream: InputStream => Brewer.fromXml(stream) }
        Log.d(TAG, s"Loaded ${brewersAtFestival.size} Brewers")
        val cidersAtFestival = Drink.fromXml(openFileInput(cidersXmlFile))
        //cidersXml(festivalId) { stream: InputStream => Drink.fromXml(stream) }
        Log.d(TAG, s"Loaded ${cidersAtFestival.size} Ciders")
        val producersAtFestival = Brewer.fromXml(openFileInput(producersXmlFile))
        //producersXml(festivalId) { stream: InputStream => Brewer.fromXml(stream) }
        Log.d(TAG, s"Loaded ${producersAtFestival.size} Producers")

        // Initialise Model
        FestivalModel.initialise(festival, beersAtFestival ++ cidersAtFestival, brewersAtFestival ++ producersAtFestival)
        Log.i(TAG, s"Initialised Festival Model for ${festival.festivalId}")
      }
      case _ => // already initialised
    }
  }

  private[this] def updateStoredData(festivalId: String) {
    val TAG = "Main Activity Update Stored Data"
    Log.i(TAG, "Updating Stored Xml Files")

    val writeStream = (fileName: String, stream: InputStream) => {
      val fileOut = openFileOutput(fileName, Context.MODE_PRIVATE)
      val buffer = new Array[Byte](1024)
      Stream.continually(stream.read(buffer)).takeWhile(_ != -1).foreach(fileOut.write(buffer, 0, _))
      fileOut.close
    }

    festivalsXml() { inStream: InputStream => writeStream(festivalXmlFile, inStream) }
    Log.d(TAG, "Updated Festivals Data")
    beersXml(festivalId) { inStream: InputStream => writeStream(beersXmlFile, inStream) }
    Log.d(TAG, "Updated Beers Data")
    cidersXml(festivalId) { inStream: InputStream => writeStream(cidersXmlFile, inStream) }
    Log.d(TAG, "Updated Ciders & Perries Data")
    breweriesXml(festivalId) { inStream: InputStream => writeStream(brewersXmlFile, inStream) }
    Log.d(TAG, "Updated Breweries Data")
    producersXml(festivalId) { inStream: InputStream => writeStream(producersXmlFile, inStream) }
    Log.d(TAG, "Updated Producers Data")

    Log.i(TAG, "Completed Updating Stored Xml Files")
  }
}
