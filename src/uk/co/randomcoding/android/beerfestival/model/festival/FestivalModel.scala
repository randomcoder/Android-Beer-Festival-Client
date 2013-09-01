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

import uk.co.randomcoding.android.beerfestival.model.brewer.Brewer
import uk.co.randomcoding.android.beerfestival.model.drink.Drink

/**
 * A Model of the available drinks and brewers present at a particular festival
 *
 * @author RandomCoder
 *
 */
case class FestivalModel private (festival: Festival, drinks: Seq[Drink], brewers: Seq[Brewer])

/**
 * Provides access to festival models, using the festival name as key
 */
object FestivalModel {

  private[this] var festivalModels = Map.empty[String, FestivalModel]

  /**
   * (Re)initialise a festival's status from a JSON input
   *
   * The JSON could come from a file (in the case of first load or from http for a live update
   *
   */
  def initialise(festival: Festival, drinks: Seq[Drink], brewers: Seq[Brewer]) {
    festivalModels += (festival.festivalId -> FestivalModel(festival, drinks, brewers))
  }

  /**
   * Get a [[uk.co.randomcoding.android.beerfestival.model.festival.FestivalModel]] by its name
   */
  def apply(festivalId: String): Option[FestivalModel] = festivalModels.get(festivalId)
}
