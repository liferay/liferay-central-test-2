/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import java.util.ArrayList;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * <a href="VmLexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class VmLexer extends Lexer {

	private static final SimpleAttributeSet STYLE_COMMENT;
	private static final SimpleAttributeSet STYLE_REFERENCE;

	/**
	 * Statically initializes the styles to highlight different syntax
	 */
	static {
		STYLE_REFERENCE = (SimpleAttributeSet) STYLE_TEXT.clone();
		StyleConstants.setForeground(STYLE_REFERENCE,
			Color.green.darker().darker());
		StyleConstants.setBold(STYLE_REFERENCE, true);

		STYLE_COMMENT = (SimpleAttributeSet) STYLE_TEXT.clone();
		StyleConstants.setForeground(STYLE_COMMENT, Color.gray);
		StyleConstants.setItalic(STYLE_COMMENT, true);
	}

	/* (non-Javadoc)
	 * @see com.liferay.applets.editor.Lexer#getTokens()
	 */
	public void parse(String str) {
		_tokens = new ArrayList();

		for (int ii = 0; ii < str.length(); ++ii) {
			String substr = str.substring(ii);
			if (substr.charAt(0) != '#'){
				int beg = ii;
				int end = str.indexOf('#', ii + 1);
				if (end == -1) {
					end = str.length() - 1;
				}
				else {
					--end;
				}
				_tokens.add(new Token(beg, end, STYLE_TEXT));
			}
			else if (substr.startsWith("#*")) {
				_tokens.add(getBracketed(str, ii, "#*", "*#", STYLE_COMMENT));
			}
			else if (substr.startsWith("##")) {
				_tokens.add(getBracketed(str, ii, "##", "\n", STYLE_COMMENT));
			}
			else {
				int beg = ii;
				int end = str.indexOf('\n', ii + 1);
				if (end == -1) {
					end = str.length() - 1;
				}
				_tokens.add(new Token(beg, end, STYLE_REFERENCE));
			}
			ii = ((Token) _tokens.get(_tokens.size() - 1)).getEnd();
		}
	}

}