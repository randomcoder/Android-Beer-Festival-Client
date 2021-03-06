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

import uk.co.randomcoding.android.beerfestival.model.drink.Drink
import uk.co.randomcoding.android.beerfestival.model.brewer.Brewer

/**
 * This provides an interface to access the data stored in a [[uk.co.randomcoding.android.beerfestival.model.CamraDb]]
 *
 * @author RandomCoder
 *
 */
trait CamraDbAccess {
  /**
   * Gets all the drinks in the database
   */
  def drinks: Seq[Drink]

  /**
   * Get all the brewers in the database
   */
  def brewers: Seq[Brewer]

  /**
   * Find drinks who's name contains a certain string (case insensitive)
   *
   * @param subString The string to find somewhere in the name of a drink
   *
   * @return All the drinks that contain the input string in their name
   */
  def drinkNameContains(subString: String): Seq[Drink]
}