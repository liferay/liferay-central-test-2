/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import javax.swing.text.SimpleAttributeSet;

/**
 * <a href="Token.java.html"><b><i>View Source</i></b></a>
 * Structure to maintain tokens.
 *
 * @author  Alexander Chow
 *
 */
public class Token {

	/**
	 * Constructor for token.
	 *
	 * @param beg Beginning index
	 * @param end Ending index
	 * @param style The style to apply to this token
	 */
	public Token(int beg, int end, SimpleAttributeSet style) {
		_dirty = false;
		setBeg(beg);
		setEnd(end);
		setStyle(style);
	}

	/**
	 * Optimized comparison of the styles of each token and their corresponding
	 * substrings.
	 *
	 * @param myStr The string <code>this</code> token comes from.
	 * @param newTok The token to compare against
	 * @param newStr The string of the token to compare against
	 * @return Whether the substrings are the same.
	 */
	public boolean substrEquals(String myStr, Token newTok, String newStr) {
		boolean equals = true;
		if (this.getStyle().equals(newTok.getStyle()) &&
			this.getOffset() == newTok.getOffset()) {

			for (int ii = this.getBeg(), jj = newTok.getBeg();
				ii < this.getEnd() && jj < newTok.getEnd(); ++ii, ++jj) {
				if (myStr.charAt(ii) != newStr.charAt(jj)) {
					equals = false;
					break;
				}
			}
		}
		else {
			// the styles or substring lengths are not the same
			equals = false;
		}

		return equals;
	}

	public void setDirty(boolean dirty) {
		_dirty = dirty;
	}

	public boolean isDirty() {
		return _dirty;
	}

	public void setBeg(int beg) {
		_beg = beg;
	}

	public int getBeg() {
		return _beg;
	}

	public void setEnd(int end) {
		_offset = end - _beg + 1;
	}

	public int getEnd() {
		return _offset + _beg - 1;
	}

	public int getOffset() {
		return _offset;
	}

	public void setStyle(SimpleAttributeSet style) {
		_style = style;
	}

	public SimpleAttributeSet getStyle() {
		return _style;
	}

	private boolean _dirty;
	private int _beg;
	private int _offset;
	private SimpleAttributeSet _style;

}