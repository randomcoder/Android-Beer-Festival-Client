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

import org.scalatest.matchers.ShouldMatchers
import uk.co.randomcoding.android.beerfestival.model.drink.Drink
import uk.co.randomcoding.android.beerfestival.model.brewer.Brewer
import java.io.InputStream
import uk.co.randomcoding.android.beerfestival.model.drink.DrinkType
import scala.io.Source

/**
 * Common data for using with the database access tests
 *
 * @author RandomCoder
 *
 */
trait DbAccessTestData extends ShouldMatchers {
  // Test data and helpers
  val smallDrinkDbFileLoc = "/drinksdb_small.json"
  val smallBrewerDbFileLoc = "/brewersdb_small.json"
  val largeDrinkDbFileLoc = "/drinksdb.json"
  val largeBrewerDbFileLoc = "/brewersdb.json"

  val dorothyGoodbodies = Drink("10001", DrinkType.BEER, "Dorothy Goodbody's Country Ale",
    "Strong, full-bodied ruby ale. A 2011 Suggestabeer from Nick Bracey of Walsall.", 6.0, "Wye Valley",
    List("Dark", "Strong"))

  val bohemiaPilsner = Drink("10002", DrinkType.BEER, "Bohemia Pilsner",
    "Deep golden lager made to a typical Czech recipe with Pilsner yeast. Rich hop character.",
    4.8, "Wylam", List("Lager", "Golden"))

  val astonDark = Drink("10003", DrinkType.BEER, "Aston Dark",
    "Dark tanned and complex ale lightly hopped with Fuggles. Underlying malt gives way to hints of dark chocolate and coffee.",
    3.6, "ABC", List("Mild"))

  val rotundaRed = Drink("10009", DrinkType.BEER, "Rotunda Red",
    "Traditional ruby coloured ale of distinct character.  Lightly hopped with English Fuggles, finished with the distinct aroma of Liberty hops and a toffee aftertaste.",
    4.8, "ABC", List("Brown", "Strong"))

  val wyeValley = Brewer("Wye Valley", "Herefordshire")

  val wylam = Brewer("Wylam", "Place 1")

  val abc = Brewer("ABC", "Place 2")

  implicit def stringToClassInputStream(in: String): InputStream = {
    val stream = getClass.getResourceAsStream(in)
    stream should not be (null)
    stream
  }

  /**
   * (Re)initialise the database access.
   *
   * This can be used to load different data into the db access for each test
   */
  def initialiseDbAccess(drinkDbFileLoc: String, brewerDbFileLoc: String) {
    val drinkSource = Source.fromInputStream(drinkDbFileLoc)
    val brewerSource = Source.fromInputStream(brewerDbFileLoc)

    InMemoryCamraDbAccess.init(drinkSource, brewerSource)
  }
}