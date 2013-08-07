/**
 * Copyright (C) 2012 - RandomCoder <randomcoder@randomcoding.co.uk>
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
package uk.co.randomcoding.android.beerfestival

import android.app.ListActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import uk.co.randomcoding.android.beerfestival.model.drink.Drink
import uk.co.randomcoding.android.beerfestival.util.DrinkSearcher.getMatchingDrinks
import uk.co.randomcoding.android.beerfestival.util.IntentExtras._
import uk.co.randomcoding.android.beerfestival.model.festival.FestivalModel

/**
 * Activity to get and display all search results
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 4 Aug 2012
 */
class DisplayResultsActivity extends ListActivity with TypedActivity {

  private[this] def TAG = "Display Results Activity"

  val FAILED_TO_PARSE_DIALOGUE_ID = 1

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    //setContentView(R.layout.activity_display_results)
    val intent = getIntent
    val festivalId = intent.getStringExtra(FESTIVAL_ID_EXTRA)

    //val searchOptions: Map[String, String] = (intent.getExtras) + (FESTIVAL_ID_EXTRA -> festivalId)

    val matchingDrinks = FestivalModel(festivalId) match {
      case Some(model) => model.drinks
      case _ => Nil
    }

    //val matchingDrinks = getMatchingDrinks(this, searchOptions, FAILED_TO_PARSE_DIALOGUE_ID).toList.filter(_.state == "Ready")

    displayResults(matchingDrinks)
  }

  private[this] implicit def bundleToSearchMap(b: Bundle): Map[String, String] = {
    Map(NAME_SEARCH_EXTRA -> b.getString(NAME_SEARCH_EXTRA),
      DESCRIPTION_SEARCH_EXTRA -> b.getString(DESCRIPTION_SEARCH_EXTRA)).filterNot { case (k, v) => (v == null || v == "null" || v.trim().isEmpty) }
  }

  private[this] def displayResults(drinks: Seq[Drink]) {
    /*val titleText = findView(TR.drinkResultsTitle)
    titleText.setText(s"Search Results: (${drinks.size})")*/

    val drinkTitles = drinks match {
      case Nil => Array("There are no drinks matching your search")
      case _ => drinks.map(drinkToText).toArray
    }

    setListAdapter(new ArrayAdapter[String](this, android.R.layout.simple_list_item_1, drinkTitles))
  }

  private[this] def drinkToText(drink: Drink): String = {
    val descriptionText = drink.description.trim match {
      case "" => ""
      case description => s"Description: $description\n"
    }

    // setup variable display entries
    val abvEntry = drink.abv match {
      case 0.0 => ("", "")
      case abv => ("ABV", f"$abv%.1f%%")
    }

    /*val priceEntry = 0.0 drink.price match {
      case 0.0 => ("", "")
      case price => ("Price", "£%.2f".format(price))
    }*/

    val variableText = Seq(abvEntry, drink.state).map(_ match {
      case ("", "") => ""
      case (label, text) => s"$label: $text"
    }).mkString("   ")

    s"${drink.name}\n$descriptionText $variableText"
  }
}
