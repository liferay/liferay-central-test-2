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

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * <a href="XmlLexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class XmlLexer extends Lexer {

	private static final SimpleAttributeSet STYLE_SCRIPT;
	private static final SimpleAttributeSet STYLE_ELEMENT;
	private static final SimpleAttributeSet STYLE_ATTR_NAME;
	private static final SimpleAttributeSet STYLE_ATTR_VALUE;
	private static final SimpleAttributeSet STYLE_COMMENT;

	/**
	 * Statically initializes the styles to highlight different syntax
	 */
	static {
		STYLE_SCRIPT = (SimpleAttributeSet) STYLE_TEXT.clone();
		StyleConstants.setForeground(STYLE_SCRIPT, Color.orange.darker());
		StyleConstants.setItalic(STYLE_SCRIPT, true);

		STYLE_ELEMENT = (SimpleAttributeSet) STYLE_TEXT.clone();
		StyleConstants.setForeground(STYLE_ELEMENT,
			Color.green.darker().darker());
		StyleConstants.setBold(STYLE_ELEMENT, true);

		STYLE_ATTR_NAME = (SimpleAttributeSet) STYLE_TEXT.clone();
		StyleConstants.setForeground(STYLE_ATTR_NAME, Color.red.darker());

		STYLE_ATTR_VALUE = (SimpleAttributeSet) STYLE_TEXT.clone();
		StyleConstants.setForeground(STYLE_ATTR_VALUE, Color.blue);

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
			if (substr.charAt(0) != '<'){
				int beg = ii;
				int end = str.indexOf("<", ii + 1);
				if (end == -1) {
					end = str.length() - 1;
				}
				else {
					--end;
				}
				_tokens.add(new Token(beg, end, STYLE_TEXT));
			}
			else if (substr.startsWith("<!--")) {
				_tokens.add(
					getBracketed(str, ii, "<!--", "-->", STYLE_COMMENT));
			}
			else if (isScriptTag(substr)) {
				List nest = new ArrayList();
				int beg = ii;
				int end = extractNest(str, ii + 1, nest);
				_tokens.add(new Token(beg, end, STYLE_ELEMENT));
				_tokens.addAll(nest);
				ii = ((Token) _tokens.get(_tokens.size() - 1)).getEnd();

				if (!str.substring(ii - 1).startsWith("/>")) {
					int closeTag =
						str.toLowerCase().indexOf("</script", ii + 1);
					if (closeTag == -1) {
						if (ii + 1 != str.length() - 1) {
							_tokens.add(new Token(
								ii + 1, str.length() - 1, STYLE_TEXT));
						}
						ii = str.length() - 1;
					}
					else {
						_tokens.add(
							new Token(ii + 1, closeTag - 1, STYLE_SCRIPT));

						nest.clear();
						int closeTagEnd = extractNest(str, closeTag + 1, nest);
						_tokens.add(
							new Token(closeTag, closeTagEnd, STYLE_ELEMENT));
						_tokens.addAll(nest);
					}
				}
			}
			else {
				List nest = new ArrayList();
				int beg = ii;
				int end = extractNest(str, ii + 1, nest);
				_tokens.add(new Token(beg, end, STYLE_ELEMENT));
				_tokens.addAll(nest);
			}

			ii = ((Token) _tokens.get(_tokens.size() - 1)).getEnd();
		}
	}

	/**
	 * Extracts the nested contents of an XML element.
	 *
	 * @param str String to be parsing
	 * @param start Index to start finding nested tokens
	 * @param nest List of nested tokens
	 * @return End of first token
	 */
	private int extractNest(String str, int start, List nest) {
		Token tail = null;
		int absEnd = str.indexOf(">", start);
		if (absEnd == -1) {
			absEnd = str.length() - 1;
		}
		else {
			if (str.charAt(absEnd-1) == '?' || str.charAt(absEnd-1) == '/') {
				--absEnd;
				tail = new Token(absEnd, absEnd + 1, STYLE_ELEMENT);
			}
			else {
				tail = new Token(absEnd, absEnd, STYLE_ELEMENT);
			}
			--absEnd;
		}

		boolean hasAtt = false;
		for (++start ; start <= absEnd; ++start) {
			if (Character.isWhitespace(str.charAt(start))) {
				hasAtt = true;
				break;
			}
		}

		if (hasAtt) {
			int beg = start;
			int end = -1;
			for (++start; start <= absEnd; ++start) {
				if (str.charAt(start) == '"') {
					end = start - 1;
					nest.add(new Token(beg, end, STYLE_ATTR_NAME));

					int valBeg = start;
					int valEnd = -1;
					for (++start; start <= absEnd; ++start) {
						if (str.charAt(start) == '"') {
							valEnd = start;
							nest.add(
								new Token(valBeg, valEnd, STYLE_ATTR_VALUE));
							break;
						}
					}
					if (valEnd == -1) {
						nest.add(new Token(valBeg, absEnd, STYLE_ATTR_VALUE));
					}
					else {
						beg = start + 1;
						end = -1;
						continue;
					}
				}
				else if (str.charAt(start) == '\'') {
					end = start - 1;
					nest.add(new Token(beg, end, STYLE_ATTR_NAME));

					int valBeg = start;
					int valEnd = -1;
					for (++start; start <= absEnd; ++start) {
						if (str.charAt(start) == '\'') {
							valEnd = start;
							nest.add(
								new Token(valBeg, valEnd, STYLE_ATTR_VALUE));
							break;
						}
					}
					if (valEnd == -1) {
						nest.add(new Token(valBeg, absEnd, STYLE_ATTR_VALUE));
					}
					else {
						beg = start + 1;
						end = -1;
						continue;
					}
				}
			}
			if (end == -1) {
				nest.add(new Token(beg, absEnd, STYLE_ATTR_NAME));
			}
		}

		if (tail != null)
			nest.add(tail);
		if (nest.size() > 0) {
			absEnd = ((Token) nest.get(0)).getBeg() - 1;
		}
		return absEnd;
	}

	/**
	 * Optimized check to see if a tag is a case-insensitive script.
	 *
	 * @param str String to check
	 * @return Whether a script tag was found
	 */
	private boolean isScriptTag(String str) {
		return str.length() >= "<script".length()
			&& str.charAt(0) == '<'
			&& (str.charAt(1) == 's' || str.charAt(1) == 'S')
			&& (str.charAt(2) == 'c' || str.charAt(2) == 'C')
			&& (str.charAt(3) == 'r' || str.charAt(3) == 'R')
			&& (str.charAt(4) == 'i' || str.charAt(4) == 'I')
			&& (str.charAt(5) == 'p' || str.charAt(5) == 'P')
			&& (str.charAt(6) == 't' || str.charAt(6) == 'T');
	}

}