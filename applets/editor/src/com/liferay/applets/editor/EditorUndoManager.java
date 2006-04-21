/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.applets.editor;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

/**
 * <a href="EditorUndoManager.java.html"><b><i>View Source</i></b></a>
 * Undo Manager for editor applet.
 *
 * @author  Alexander Chow
 *
 */
public class EditorUndoManager extends UndoManager {

	/* (non-Javadoc)
	 * @see javax.swing.undo.UndoableEdit#undo()
	 */
	public synchronized void undo() throws CannotUndoException {
		boolean last = false;

		// Undo all style change(s) then single addition/deletion
		do {
			UndoableEdit edit = editToBeUndone();
			if (edit != null) {
				String presName = edit.getPresentationName();
				if (presName.toLowerCase().indexOf("style") == -1) {
					last = true;
				}
				super.undo();
			}
			else {
				last = true;
			}
		} while (!last);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.UndoableEdit#redo()
	 */
	public synchronized void redo() throws CannotRedoException {
		boolean stop = false;

		// Redo single addition/deletion then all style change(s)
		super.redo();
		do {
			UndoableEdit edit = editToBeRedone();
			if (edit != null) {
				String presName = edit.getPresentationName();
				if (presName.toLowerCase().indexOf("style") != -1) {
					super.redo();
				}
				else {
					stop = true;
				}
			}
			else {
				stop = true;
			}
		} while (!stop);
	}

}