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

import java.awt.Color;

import java.util.List;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * <a href="Lexer.java.html"><b><i>View Source</i></b></a>
 * Interface to basic lexical analyser.
 *
 * @author  Alexander Chow
 *
 */
public abstract class Lexer {

	protected static final SimpleAttributeSet STYLE_TEXT;

	/**
	 * Statically initializes the default text style
	 */
	static {
		STYLE_TEXT = new SimpleAttributeSet();
		StyleConstants.setFontFamily(STYLE_TEXT, "MONOSPACED");
		StyleConstants.setFontSize(STYLE_TEXT, 12);
		StyleConstants.setForeground(STYLE_TEXT, Color.black);
		StyleConstants.setBackground(STYLE_TEXT, Color.white);
		StyleConstants.setItalic(STYLE_TEXT, false);
		StyleConstants.setBold(STYLE_TEXT, false);
		StyleConstants.setUnderline(STYLE_TEXT, false);
	}

	/**
	 * Resets the tokens and parses new string.
	 * @param str String to parse
	 */
	public abstract void parse(String str);

	/**
	 * Retrieves the default style for this text.
	 * @return
	 */
	public static AttributeSet getDefaultStyle() {
		return STYLE_TEXT;
	}

	/**
	 * Retrieves the tokens parsed, if they exist.
	 * @return Parsed tokens; null if no string was parsed.
	 */
	public List getTokens() {
		return _tokens;
	}

	/**
	 * Retrieves a bracketed statement.
	 *
	 * @param str 	String to parse
	 * @param idx 	Index to start searching string
	 * @param left 	Index must point to part of string that has this left bracket
	 * @param right	The right bracket to search for
	 * @param style	Style to place in returned token
	 * @return Token generated from the search
	 */
	protected Token getBracketed(String str, int start, String left, String right,
		SimpleAttributeSet style) {

		int beg = start;
		int end = str.indexOf(right, start + left.length());
		if (end != -1) {
			end += right.length() - 1;
		}
		else {
			end = str.length() - 1;
		}
		return new Token(beg, end, style);
	}

	protected List _tokens = null;

}