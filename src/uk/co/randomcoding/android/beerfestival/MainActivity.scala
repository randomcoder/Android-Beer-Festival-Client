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
import uk.co.randomcoding.android.beerfestival.util.AndroidSystemHelpers._
import uk.co.randomcoding.android.beerfestival.util.IntentExtras._
import uk.co.randomcoding.android.beerfestival.util.query.QueryHelper._
import uk.co.randomcoding.android.beerfestival.util.stream.StreamHelpers._

/**
 * The main activity of the application.
 *
 * @author RandomCoder
 */
class MainActivity extends Activity {

  // TODO: Move these into xml storage or util object
  private[this] val festivalXmlFile = "festivals.xml"
  private[this] val beersXmlFile = "beers.xml"
  private[this] val cidersXmlFile = "ciders.xml"
  private[this] val brewersXmlFile = "brewers.xml"
  private[this] val producersXmlFile = "producers.xml"

  private[this] implicit val act: Activity = this

  // This is fixed for now, but will be derived from config at some point.
  private[this] final val worcesterId = "WOR/2013"

  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)
    // Initialise Festival Data
    // Currently only uses Worcester (WOR/2013)
    //reloadFestivalModel(worcesterId, fileList().find(_ == festivalXmlFile).isEmpty)
  }

  def showAllDrinks(view: View): Unit =  {
    newActivityIfDataLoaded(classOf[DisplayResultsActivity], allDrinksIntentExtras)
  }

  def showAllBeers(view: View) {
    newActivityIfDataLoaded(classOf[DisplayResultsActivity], drinkTypeSearchIntentExtras(DrinkType.BEER))
  }

  def showAllCiders(view: View) {
    newActivityIfDataLoaded(classOf[DisplayResultsActivity], drinkTypeSearchIntentExtras(DrinkType.BEER))
  }

  def showAllPerries(view: View) {
    newActivityIfDataLoaded(classOf[DisplayResultsActivity], drinkTypeSearchIntentExtras(DrinkType.BEER))
  }

  def updateData(view: View) {
    reloadFestivalModel(worcesterId, true)
  }

  private[this] def dataModelLoaded(festivalId: String = worcesterId) = FestivalModel(festivalId).isDefined

  private[this] def fileTimestamp(fileName: String): Long = {
    getFilesDir.listFiles().find(_.getName == fileName) match {
      case Some(f) => f.lastModified()
      case _ => -1L
    }
  }

  private[this] def dataModelNotLoaded(): Unit = {
    alert("Update of Data Required", "The data for the selected festival is not loaded. Please connect to the internet and update.").show()
  }

  private[this] def newActivity[T](activityClass: Class[T], intentExtras: Map[String, String]): Unit = {
    val intent = new Intent(this, activityClass)
    intentExtras.foreach{case (k, v) => intent.putExtra(k, v)}
    startActivity(intent)
  }

  private[this] def newActivityIfDataLoaded[T](activityClass: Class[T], intentExtras: Map[String, String], festivalId: String = worcesterId): Unit = {
    if (dataModelLoaded(festivalId)) newActivity(activityClass, intentExtras) else dataModelNotLoaded()
  }

  private[this] def reloadFestivalModel(festivalId: String, reloadData: Boolean) {
    val TAG = "MainActivityFestivalInitialise"

    if (reloadData) {
      try {updateStoredData(festivalId)}
      catch {
        case e: Exception => {
          alert("Failed to Update Festival or Drink Data", e.getMessage).show()
        }
      }
    }

    val festivalFileTimestamp = fileTimestamp(festivalXmlFile)
    (reloadData, FestivalModel(festivalId)) match {
      case (true, _) | (_, None) => {
        fileTimestamp(festivalXmlFile) > festivalFileTimestamp match {
          case true => updateModelFromFiles(festivalId)
          case false => Log.i(TAG, "Festival Data file not updated. Not updating internal model")
        }
      }
      case _ => // already initialised and not updated
    }
  }

  private[this] def updateModelFromFiles(festivalId: String): Unit = {
    val TAG = "MainActivityUpdateFromFiles"
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

  private[this] def updateStoredData(festivalId: String) {
    val TAG = "Main Activity Update Stored Data"

    val writeStream = (fileName: String, stream: InputStream) => {
      try {copyStream(stream, openFileOutput(fileName, Context.MODE_PRIVATE))}
      catch {
        case e: Exception => {
          Log.e(TAG, "Failed to write data to %s".format(fileName), e)
          throw e
        }
      }
    }

    if (isNetworkConnected(this)) {
      Log.i(TAG, "Updating Stored Xml Data Files from online service")

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
    else {
      alert("Internet Connection Required", "You need an active WiFi or Mobile Data connection to be able to update").show()
    }
  }
}
