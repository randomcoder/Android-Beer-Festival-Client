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
package uk.co.randomcoding.android.beerfestival.model.drink

import uk.co.randomcoding.android.beerfestival.model.drink.DrinkType._

/**
 * Sample drinks to use for tests
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 5 Aug 2012
 */
object TestDrinks {

  val tangerineDuck = Drink("10001", BEER, "Tangerine Duck",
    "Copper coloured beer with a hint of tangerine from the late addition of crystal hops. Named after the Atomic Boys famous duck Puskas at Blackpool FC in the 50s.",
    4.4, "Fuzzy Duck", List("Brown", "Best Bitter"))

  val tangerineDuckJson = """{
    | "drinkUid": "10001",
    | "drinkType": "Beer",
    | "name": "Tangerine Duck",
    | "description": "Copper coloured beer with a hint of tangerine from the late addition of crystal hops. Named after the Atomic Boys famous duck Puskas at Blackpool FC in the 50s.",
    | "abv": 4.4,
    | "brewer": "Fuzzy Duck",
    | "features": ["Brown", "Best Bitter"]
    |}""".stripMargin

  val astonDark = Drink("10002", BEER, "Aston Dark",
    "Dark tanned and complex ale lightly hopped with Fuggles. Underlying malt gives way to hints of dark chocolate and coffee.",
    3.6, "ABC", List("Mild"))

  val astonDarkJson = """{
    | "drinkUid": "10002",
    | "drinkType": "Beer",
    | "name": "Aston Dark",
    | "description": "Dark tanned and complex ale lightly hopped with Fuggles. Underlying malt gives way to hints of dark chocolate and coffee.",
    | "abv": 3.6,
    | "brewer": "ABC",
    | "features": ["Mild"]
    |}""".stripMargin

  val rotundaRed = Drink("10003", BEER, "Rotunda Red",
    "Traditional ruby coloured ale of distinct character.  Lightly hopped with English Fuggles, finished with the distinct aroma of Liberty hops and a toffee aftertaste.",
    4.8, "ABC", List("Brown", "Strong"))

  val rotundaRedJson = """{
    | "drinkUid": "10003",
    | "drinkType": "Beer",
    | "name": "Rotunda Red",
    | "description": "Traditional ruby coloured ale of distinct character.  Lightly hopped with English Fuggles, finished with the distinct aroma of Liberty hops and a toffee aftertaste.",
    | "abv": 4.8,
    | "brewer": "ABC",
    | "features": ["Brown", "Strong"]
    |}""".stripMargin

  val buttermereBeauty = Drink("10004", BEER, "Buttermere Beauty",
    "Golden pilsner style, made from lager malt, Saaz hops and a lager yeast fermented at cool temperature.",
    4.8, "Cumbrian Legendary Ales", List("Lager", "Golden"))

  val buttermereBeautyJson = """{
    | "drinkUid": "10004",
    | "drinkType": "Beer",
    | "name": "Buttermere Beauty",
    | "description": "Golden pilsner style, made from lager malt, Saaz hops and a lager yeast fermented at cool temperature.",
    | "abv": 4.8,
    | "brewer": "Cumbrian Legendary Ales",
    | "features": ["Lager", "Golden"]
    |}""".stripMargin

  val seville = Drink("10005", BEER, "Seville",
    "Dark Star's first fruit beer. Made with malted wheat as well as barley and Eldorado hops, matured with a hint of Spanish bitter oranges.",
    4.0, "Dark Star", List("Stout / Porter", "Speciality"))

  val sevilleJson = """{
    | "drinkUid": "10005",
    | "drinkType": "Beer",
    | "name": "Seville",
    | "description": "Dark Star's first fruit beer. Made with malted wheat as well as barley and Eldorado hops, matured with a hint of Spanish bitter oranges.",
    | "abv": 4.0,
    | "brewer": "Dark Star",
    | "features": ["Stout / Porter", "Speciality"]
    |}""".stripMargin

}