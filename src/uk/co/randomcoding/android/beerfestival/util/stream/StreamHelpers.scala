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

package uk.co.randomcoding.android.beerfestival.util.stream

import java.io.{OutputStream, InputStream}
import scala.collection.immutable.Stream

/**
 * Helper classes for streams
 *
 * Author: RandomCoder
 */
object StreamHelpers {

  /**
   * Copy and input stream to an output stream
   *
   * The output stream is closed once used, but the input stream needs to be managed outside this method
   *
   * @param in The input stream
   * @param out The output stream
   */
  def copyStream(in: InputStream, out: OutputStream): Unit = {
    val buffer = new Array[Byte](1024)
    try {Stream.continually(in.read(buffer)).takeWhile(_ != -1).foreach(out.write(buffer, 0, _))}
    finally {out.close() }
  }
}
