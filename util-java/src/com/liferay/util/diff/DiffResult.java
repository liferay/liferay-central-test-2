/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.util.diff;

import com.liferay.portal.kernel.util.StringBundler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="DiffResult.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * Represents a change between one or several lines. <code>changeType</code>
 * tells if the change happened in source or target. <code>lineNumber</code>
 * holds the line number of the first modified line. This line number refers to
 * a line in source or target, depending on the <code>changeType</code> value.
 * <code>changedLines</code> is a list of strings, each string is a line that is
 * already highlighted, indicating where the changes are.
 * </p>
 *
 * @author	   Bruno Farache
 * @deprecated This class has been repackaged at
 *			   <code>com.liferay.portal.kernel.util</code>.
 */
public class DiffResult {

	public static final String SOURCE = "SOURCE";

	public static final String TARGET = "TARGET";

	public DiffResult(int linePos, List<String> changedLines) {
		_lineNumber = linePos + 1;
		_changedLines = changedLines;
	}

	public DiffResult(int linePos, String changedLine) {
		_lineNumber = linePos + 1;
		_changedLines = new ArrayList<String>();
		_changedLines.add(changedLine);
	}

	public List<String> getChangedLines() {
		return _changedLines;
	}

	public void setChangedLines(List<String> changedLines) {
		_changedLines = changedLines;
	}

	public int getLineNumber() {
		return _lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		_lineNumber = lineNumber;
	}

	public boolean equals(Object obj) {
		DiffResult diffResult = (DiffResult)obj;

		if ((diffResult.getLineNumber() == _lineNumber) &&
			(diffResult.getChangedLines().equals(_changedLines))) {

			return true;
		}

		return false;
	}

	public String toString() {
		StringBundler sb = new StringBundler(_changedLines.size() * 2 + 3);

		sb.append("Line: ");
		sb.append(_lineNumber);
		sb.append("\n");

		Iterator<String> itr = _changedLines.iterator();

		while (itr.hasNext()) {
			sb.append(itr.next());

			if (itr.hasNext()) {
				sb.append("\n");
			}
		}

		return sb.toString();
	}

	private int _lineNumber;
	private List<String> _changedLines;

}