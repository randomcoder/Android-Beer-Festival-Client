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
package uk.co.randomcoding.android.beerfestival.util

/**
 * Values to use as Keys for passing data in Intent extra bundles
 *
 * @author RandomCoder
 *
 */
object IntentExtras {
  private def extra(key: String): String = s"uk.co.randomcoding.android.drinkfinder.$key"

  final val NAME_SEARCH_EXTRA = extra("NameSearch")
  final val DESCRIPTION_SEARCH_EXTRA = extra("DescriptionSearch")
  final val FESTIVAL_ID_EXTRA = extra("FestivalId")
  final val DISPLAY_DRINK_NAME_EXTRA = extra("display_drink_name")
  final val DRINK_TYPE_SEARCH_EXTRA = extra("DrinkTypeSearch")
}