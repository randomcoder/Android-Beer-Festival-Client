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
 * RandomCoder - initial API and implementation and/or initial documentation
 */
package uk.co.randomcoding.android.beerfestival

import android.app.Activity
import android.content.{Context, Intent}
import android.os.Bundle
import android.util.Log
import android.view.View
import java.io.InputStream
import uk.co.randomcoding.android.beerfestival.dialogue.AlertBuilder._
import uk.co.randomcoding.android.beerfestival.model.brewer.Brewer
import uk.co.randomcoding.android.beerfestival.model.drink.{Drink, DrinkType}
import uk.co.randomcoding.android.beerfestival.model.festival.{FestivalModel, FestivalXmlParser}
import uk.co.randomcoding.android.beerfestival.util.IntentExtras._
import uk.co.randomcoding.android.beerfestival.util.query.QueryHelper._
import uk.co.randomcoding.android.beerfestival.util.stream.StreamHelpers._

/**
 * The main activity of the application.
 *
 * @author RandomCoder
 */
class MainActivity extends Activity {

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
    reloadFestivalModel(worcesterId, fileList().find(_ == festivalXmlFile).isEmpty)
  }

  override def onDestroy() {
    super.onDestroy()
  }

  def showAllDrinks(view: View) {
    val intent = new Intent(this, classOf[DisplayResultsActivity])
    allDrinksIntentExtras foreach {
      case (k, v) => intent.putExtra(k, v)
    }
    startActivity(intent)
  }

  private[this] def allDrinksIntentExtras: Map[String, String] = {
    Map(FESTIVAL_ID_EXTRA -> worcesterId)
  }

  private[this] def drinkTypeSearchIntentExtras(drinkType: DrinkType.drinkType): Map[String, String] = {
    Map(FESTIVAL_ID_EXTRA -> worcesterId, DRINK_TYPE_SEARCH_EXTRA -> drinkType.toString)
  }

  def showAllBeers(view: View) {
    val intent = new Intent(this, classOf[DisplayResultsActivity])
    drinkTypeSearchIntentExtras(DrinkType.BEER) foreach {
      case (k, v) => intent.putExtra(k, v)
    }
    startActivity(intent)
  }

  def showAllCiders(view: View) {
    val intent = new Intent(this, classOf[DisplayResultsActivity])
    drinkTypeSearchIntentExtras(DrinkType.CIDER) foreach {
      case (k, v) => intent.putExtra(k, v)
    }
    startActivity(intent)
  }

  def showAllPerries(view: View) {
    val intent = new Intent(this, classOf[DisplayResultsActivity])
    drinkTypeSearchIntentExtras(DrinkType.PERRY) foreach {
      case (k, v) => intent.putExtra(k, v)
    }
    startActivity(intent)
  }

  /*def showAllBrewers(view: View) {
    /*val intent = new Intent(this, classOf[DisplayBrewersActivity])
    allDrinksIntentExtras foreach { case (k, v) => intent.putExtra(k, v) }
    startActivity(intent)*/
  }*/

  /*private[this] def allBrewersIntentExtras: Map[String, String] = {
    Map.empty
  }*/

  /*def showSearchDrinks(view: View) {
    val intent = new Intent(this, classOf[SearchDrinkActivity])
    startActivity(intent)
  }

  def showSearchBrewers(view: View) {
    /*val intent = new Intent(this, classOf[SearchBrewerActivity])
    startActivity(intent)*/
  }*/

  def updateData(view: View) {
    reloadFestivalModel(worcesterId, true)
  }

  /*def showWishList(view: View) {
    // TODO: Create intent to switch to the wishlist view
    // Not planned at this time
  }*/

  private[this] def reloadFestivalModel(festivalId: String, reloadData: Boolean) {
    val TAG = "MainActivityFestivalInitialise"
    if (reloadData) updateStoredData(festivalId)

    (reloadData, FestivalModel(festivalId)) match {
      case (true, _) | (_, None) => {
        Log.i(TAG, s"Initialising festival model for $festivalId")

        val festival = new FestivalXmlParser().parse(openFileInput(festivalXmlFile)).find(_.festivalId == festivalId).get
        Log.d(TAG, s"Loaded Festival $festival")
        val beersAtFestival = Drink.fromXml(openFileInput(beersXmlFile))
        Log.d(TAG, s"Loaded ${beersAtFestival.size} Beers")
        val brewersAtFestival = Brewer.fromXml(openFileInput(brewersXmlFile))
        Log.d(TAG, s"Loaded ${brewersAtFestival.size} Brewers")
        val cidersAtFestival = Drink.fromXml(openFileInput(cidersXmlFile))
        Log.d(TAG, s"Loaded ${cidersAtFestival.size} Ciders")
        val producersAtFestival = Brewer.fromXml(openFileInput(producersXmlFile))
        Log.d(TAG, s"Loaded ${producersAtFestival.size} Producers")

        // Initialise Model
        FestivalModel.initialise(festival, beersAtFestival ++ cidersAtFestival, brewersAtFestival ++ producersAtFestival)
        Log.i(TAG, s"Initialised Festival Model for ${festival.festivalId}")
      }
      case _ => // already initialised and not updated
    }
  }

  private[this] def updateStoredData(festivalId: String) {
    val TAG = "Main Activity Update Stored Data"

    val writeStream = (fileName: String, stream: InputStream) => {
      copyStream(stream, openFileOutput(fileName, Context.MODE_PRIVATE))
    }

    //implicit val act: Activity = this
    try {
      Log.i(TAG, "Updating Stored Xml Data Files")

      festivalsXml() { writeStream(festivalXmlFile, _) }
      Log.d(TAG, "Updated Festivals Data File")

      beersXml(festivalId) { writeStream(beersXmlFile, _) }
      Log.d(TAG, "Updated Beers Data File")

      cidersXml(festivalId) { writeStream(cidersXmlFile, _) }
      Log.d(TAG, "Updated Ciders & Perries Data File")

      breweriesXml(festivalId) { writeStream(brewersXmlFile, _) }
      Log.d(TAG, "Updated Breweries Data File")

      producersXml(festivalId) { writeStream(producersXmlFile, _) }
      Log.d(TAG, "Updated Producers Data File")

      Log.i(TAG, "Completed Updating Stored Xml Files")
    }
    catch {
      case e: Exception => alert("Failed to Update Festival or Drink Data", e.getMessage)
    }
  }
}
