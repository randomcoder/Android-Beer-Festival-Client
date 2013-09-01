/*
 * Copyright (C) {year} RandomCoder <randomcoder@randomcoding.co.uk>
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
 * RandomCoder - initial API and implementation and/or initial documentation
 */

package uk.co.randomcoding.android.beerfestival.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Helper functions for system querying
 *
 * Author: RandomCoder
 */
object AndroidSystemHelpers {

  /**
   * Check to see whether the device is connected via wifi or mobile data
   *
   * Required the `android.permission.ACCESS_NETWORK_STATE` permission
   *
   * @param context The activity context to use to check
   * @return `true` is there is an active wifi or mobile data connection, `false` otherwise
   */
  def isNetworkConnected(context: Context): Boolean = {
    val mgr = context.getSystemService(Context.CONNECTIVITY_SERVICE).asInstanceOf[ConnectivityManager]

    val connectTypes = Seq("wifi", "mobile")
    mgr.getAllNetworkInfo.exists(ni => ni.isConnected && connectTypes.contains(ni.getTypeName.toLowerCase))
  }
}
