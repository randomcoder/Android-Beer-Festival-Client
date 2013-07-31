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

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import uk.co.randomcoding.android.beerfestival.model.CamraDbAccess
import uk.co.randomcoding.android.beerfestival.model.drink.Drink
import uk.co.randomcoding.android.beerfestival.model.brewer.Brewer
import scala.io.Source
import uk.co.randomcoding.android.beerfestival.util.Convertors._
import uk.co.randomcoding.android.beerfestival.model.InMemoryCamraDbAccess
import uk.co.randomcoding.android.beerfestival.model.festival.FestivalModel

class MainActivity extends Activity with TypedActivity {
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)
    // Initialise Festival Data
    // Currently only uses Worcester (WOR/2013)

    // Initialise database
    initialiseFestivalData("WOR/2013")

  }

  def showAllDrinks(view: View) {
    val intent = new Intent(this, classOf[DisplayResultsActivity])
    allDrinksIntentExtras foreach { case (k, v) => intent.putExtra(k, v) }
    startActivity(intent)
  }

  private[this] def allDrinksIntentExtras: Map[String, String] = {
    Map.empty
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
    // get festival info

    // get beers

    // get brewers

    // get ciders

    // get producers

    // Initialise Model
  }
}
