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
import android.util.Log
import android.widget.ArrayAdapter
import uk.co.randomcoding.android.beerfestival.model.drink.Drink
import uk.co.randomcoding.android.beerfestival.model.drink.DrinkType
import uk.co.randomcoding.android.beerfestival.model.festival.FestivalModel
import uk.co.randomcoding.android.beerfestival.util.IntentExtras._

/**
 * Activity to get and display all search results
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 4 Aug 2012
 */
class DisplayResultsActivity extends ListActivity {

  private[this] def TAG = "Display Results Activity"

  val FAILED_TO_PARSE_DIALOGUE_ID = 1

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

    val intent = getIntent
    val festivalId = intent.getStringExtra(FESTIVAL_ID_EXTRA)

    val matchingDrinks = FestivalModel(festivalId) match {

      case Some(model) => {
        val sortedDrinks = () => model.drinks.sortBy(_.name).filter(_.state match {
          case "Ready" | "Waiting" => true
          case _ => false
        })

        Option(intent.getStringExtra(DRINK_TYPE_SEARCH_EXTRA)) match {
          case Some("Beer") => sortedDrinks().filter(_.drinkType == DrinkType.BEER)
          case Some("Cider") => sortedDrinks().filter(_.drinkType == DrinkType.CIDER)
          case Some("Perry") => sortedDrinks().filter(_.drinkType == DrinkType.PERRY)
          case _ => sortedDrinks()
        }
      }
      case _ => Nil
    }

    Log.d(TAG, s"Got $matchingDrinks from Festival $festivalId")

    displayResults(matchingDrinks)
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
    Log.d(TAG, s"Converting $drink to list text")
    /*val descriptionText = Option(drink.description) match {
      case Some(description) => s"Description: $description\n"
      case None => ""
    }*/

    val featuresEntry = drink.features.mkString(", ")

    // setup variable display entries
    val abvEntry = Option(drink.abv) match {
      case Some(abv) => ("ABV", abv)
      case _ => ("ABV", "")
    }

    val stateEntry = Option(drink.state) match {
      case Some(state) => ("State", state)
      case _ => ("State", "")
    }

    val variableText = Seq(abvEntry, stateEntry).map(_ match {
      case ("", "") => ""
      case (label, text) => s"$label: $text"
    }).mkString("   ")

    Log.d(TAG, s"Converted drink: ${drink.name}")
    s"${drink.name}\n$featuresEntry\n$variableText"
  }
}
