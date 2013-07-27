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
package uk.co.randomcoding.android.beerfestival.model.festival

import scala.util.parsing.json.JSON
import uk.co.randomcoding.android.beerfestival.util.Convertors._

/**
 * Brief description of Festival
 *
 * @author RandomCoder
 *
 */
case class Festival(festivalName: String, availableDrinks: Seq[FestivalDrinkData])

case class FestivalDrinkData(drinkUid: String, price: Double, status: String)

object Festival {
  def fromJson(jsonData: String): Option[Festival] = JSON.parseFull(jsonData) match {
    case Some(data) => data match {
      case festivalData: Map[_, _] => Some(festivalFromJson(festivalData))
      case _ => None
    }
    case _ => None
  }

  private[this] def festivalFromJson(festivalJson: Map[String, _]): Festival = {
    val festivalName = festivalJson("festivalName").toString

    val drinkData = festivalJson("availableDrinks") match {
      case drinks: List[_] => drinks.map(_ match {
        case drink: Map[_, _] => Some(drinkDataFromJson(drink))
        case _ => None
      })
      case _ => Nil
    }

    Festival(festivalName, drinkData)
  }

  private[this] def drinkDataFromJson(drinkData: Map[String, _]): FestivalDrinkData = {
    val drinkUid = drinkData("drinkUid").toString
    val price = drinkData("price").toString.toDouble
    val status = drinkData("status").toString

    FestivalDrinkData(drinkUid, price, status)
  }
}