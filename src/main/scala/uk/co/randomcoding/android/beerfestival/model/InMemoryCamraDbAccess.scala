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
package uk.co.randomcoding.android.beerfestival.model

import scala.io.Source
import uk.co.randomcoding.android.beerfestival.model.drink.Drink

/**
 * Provides access to an in memory copy of the [[uk.co.randomcoding.android.beerfestival.model.CamraDb]]
 *
 * @author RandomCoder
 *
 */
object InMemoryCamraDbAccess extends CamraDbAccess {
  private[this] var db: InMemoryCamraDb = _

  def init(drinksData: Source, brewersData: Source) {
    db = new InMemoryCamraDb(drinksData, brewersData)
  }

  override def drinks = db.drinks

  override def brewers = db.brewers

  override def drinkNameContains(s: String): Seq[Drink] = s.toLowerCase.trim match {
    case "" => db.drinks
    case subString => drinks.filter(_.name.toLowerCase.contains(subString))
  }

}