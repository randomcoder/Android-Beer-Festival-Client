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

class MainActivity extends Activity with TypedActivity {
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)

  }

  def showSearchDrinks(view: View) {
    val intent = new Intent(this, classOf[SearchDrinkActivity])
    startActivity(intent)
  }

  def updateData(view: View) {
    val intent = new Intent(this, classOf[UpdateDataActivity])
    startActivity(intent)
  }

  def showWishList(view: View) {
    // TODO: Create intent to switch to the wishlist view
    // Not planned at this time
  }
}
