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

package uk.co.randomcoding.android.beerfestival.dialogue

import android.app.{Activity, AlertDialog}
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener

/**
 * Provides simple creation of Alert Dialogues
 *
 * @author RandomCoder
 */
object AlertBuilder {

  /**
   * Type for dialogue listener functions that can be implicitly converted
   */
  final type dialogueListenerFunction = (DialogInterface, Int) => Unit

  /**
   * A listener functio that is a ''No-Op''
   */
  final val noopListenerFunction = (dialogue: DialogInterface,  id: Int) => ()

  /**
   * Create an alert dialogue with a title, message and Ok button
   *
   * @param title The dialogue's title
   * @param message The message to display in the body of the message
   * @param okFunc The function `(DialogInterface, Int) => ()` to invoke when the alert's '''Ok''' button is pressed
   * @param activity Implicit activity. This is required to be able to create the dialogue
   *
   * @return The new `AlertDialog`
   */
  def alert(title: String, message: String, okFunc: dialogueListenerFunction = noopListenerFunction)(implicit activity: Activity): AlertDialog = {
    val builder = new AlertDialog.Builder(activity)
    builder.setTitle(title).setMessage(message).setPositiveButton("Ok", okFunc).create()
  }

  private[this] implicit def funcToDialogueOnClickListener(f: dialogueListenerFunction): DialogInterface.OnClickListener = new OnClickListener {
    def onClick(dialog: DialogInterface, id: Int): Unit = f(dialog, id)
  }
}
