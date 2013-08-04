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

import java.io.{BufferedInputStream, ByteArrayInputStream, InputStream}

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import uk.co.randomcoding.android.beerfestival.model.brewer.Brewer
import uk.co.randomcoding.android.beerfestival.model.drink.Drink
import uk.co.randomcoding.android.beerfestival.model.festival.{Festival, FestivalModel}
import uk.co.randomcoding.android.beerfestival.util.query.QueryHelper._

class MainActivity extends Activity with TypedActivity {

  // This is fixed for now, but will be derived from config at some point.
  private[this] final val worcesterId = "WOR/2013"

  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)
    // Initialise Festival Data
    // Currently only uses Worcester (WOR/2013)
    initialiseFestivalData(worcesterId)
  }

  override def onDestroy() {
    // Write the festival model(s) to storage (GSON?)
  }

  def showAllDrinks(view: View) {
    val intent = new Intent(this, classOf[DisplayResultsActivity])
    allDrinksIntentExtras foreach { case (k, v) => intent.putExtra(k, v) }
    startActivity(intent)
  }

  private[this] def allDrinksIntentExtras: Map[String, String] = {
    Map(SearchDrinkActivity.FESTIVAL_ID_EXTRA -> worcesterId)
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
  /*
  def updateData(view: View) {
    val intent = new Intent(this, classOf[UpdateDataActivity])
    startActivity(intent)
  }*/

  def showWishList(view: View) {
    // TODO: Create intent to switch to the wishlist view
    // Not planned at this time
  }

  private[this] def initialiseFestivalData(festivalId: String) {
    FestivalModel(festivalId) match {
      case None => {
        val TAG = "MainActivityFestivalInitialise"
        // TODO: Check for connectivity and bail if not connected (with a message)
        val festival = Festival.fromXml(festivalInfoXml(festivalId)).find(_.festivalId == festivalId).get
        Log.d(TAG, "Loaded Festival")
        val beersAtFestival = Drink.fromXml(beersXml(festivalId))
        Log.d(TAG, "Loaded Beers")
        val brewersAtFestival = Brewer.fromXml(brewersXml(festivalId))
        Log.d(TAG, "Loaded Brewers")
        val cidersAtFestival = Drink.fromXml(cidersXml(festivalId))
        Log.d(TAG, "Loaded Ciders")
        val producersAtFestival = Brewer.fromXml(producersXml(festivalId))
        Log.d(TAG, "Loaded Producers")

        // Initialise Model
        FestivalModel.initialise(festival, beersAtFestival ++ cidersAtFestival, brewersAtFestival ++ producersAtFestival)
        Log.d(TAG, "Initialised Festival Model")
      }
      case _ => // already initialised
    }
  }

  private implicit def stringToInStream(s: String): InputStream = new BufferedInputStream(new ByteArrayInputStream(s.getBytes()))
}
