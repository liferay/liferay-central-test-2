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

package com.liferay.util;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * <a href="Html.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Clarence Shen
 * @author Harry Mark
 *
 */
public class Html {

	public static final String AMPERSAND = "&amp;";

	public static String escape(String text) {
		return escape(text, true);
	}

	public static String escape(String text, boolean stripBlankSpaces) {
		if (text == null) {
			return null;
		}

		int pos = text.indexOf("& ");

		if (pos != -1) {
			text = StringUtil.replace(text, "& ", "&amp; ");
		}

		StringMaker sm = new StringMaker(text.length());

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			switch (c) {
				case '<':
					sm.append("&lt;");

					break;

				case '>':
					sm.append("&gt;");

					break;

				case '\'':
					sm.append("&#39;");

					break;

				case '\"':
					sm.append("&quot;");

					break;

				case ',':
					sm.append("&#44;");

					break;

				case '\r':
					if (stripBlankSpaces) {
						break;
					}

				case '\n':
					if (stripBlankSpaces) {
						break;
					}

				case '\t':
					if (stripBlankSpaces) {
						break;
					}

				default:
					if (((int)c) > 255) {
						sm.append("&#").append(((int)c)).append(";");
					}
					else {
						sm.append(c);
					}
			}
		}

		return sm.toString();
	}

	public static String formatTo(String text) {
		return Html.escape(text, true);
	}

	public static String formatFrom(String text) {
		if (text == null) {
			return null;
		}

		// Optimize this

		text = StringUtil.replace(text, "&lt;", "<");
		text = StringUtil.replace(text, "&gt;", ">");
		text = StringUtil.replace(text, "&#34;", "\"");
		text = StringUtil.replace(text, "&#38;", "&");
		text = StringUtil.replace(text, "&#42;", "*");
		text = StringUtil.replace(text, "&#47;", "/");
		text = StringUtil.replace(text, "&#58;", ":");
		text = StringUtil.replace(text, "&#63;", "?");

		return text;
	}

	public static String fromInputSafe(String text) {
		return StringUtil.replace(text, "&amp;", "&");
	}

	public static String stripBetween(String text, String tag) {
		return StringUtil.stripBetween(text, "<" + tag, "</" + tag + ">");
	}

	public static String stripComments(String text) {
		return StringUtil.stripBetween(text, "<!--", "-->");
	}

	public static String stripHtml(String text) {
		if (text == null) {
			return null;
		}

		text = stripComments(text);

		StringMaker sm = new StringMaker(text.length());

		int x = 0;
		int y = text.indexOf("<");

		while (y != -1) {
			sm.append(text.substring(x, y));

			// Look for text enclosed by <script></script>

			boolean scriptFound = _isScriptTag(text, y + 1);

			if (scriptFound) {
				int pos = y + _TAG_SCRIPT.length;

				// Find end of the tag

				pos = text.indexOf(">", pos);

				if (pos >= 0) {

					// Check if preceding character is / (i.e. is this instance
					// of <script/>)

					if (text.charAt(pos-1) != '/') {

						// Search for the ending </script> tag

						for (;;) {
							pos = text.indexOf("</", pos);

							if (pos >= 0) {
								if (_isScriptTag(text, pos + 2)) {
									y = pos;

									break;
								}
								else {

									// Skip past "</"

									pos += 2;
								}
							}
							else {
								break;
							}
						}
					}
				}
			}

			x = text.indexOf(">", y) + 1;

			if (x < y) {

				// <b>Hello</b

				break;
			}

			y = text.indexOf("<", x);
		}

		if (y == -1) {
			sm.append(text.substring(x, text.length()));
		}

		return sm.toString();
	}

	public static String toInputSafe(String text) {
		return StringUtil.replace(
			text,
			new String[] {"&", "\""},
			new String[] {"&amp;", "&quot;"});
	}

	private static boolean _isScriptTag(String text, int start) {
		char item;
		int pos = start;

		if (pos + _TAG_SCRIPT.length + 1 <= text.length()) {
			for (int i = 0; i < _TAG_SCRIPT.length; i++) {
				item = text.charAt(pos++);

				if (Character.toLowerCase(item) != _TAG_SCRIPT[i]) {
					return false;
				}
			}

			item = text.charAt(pos);

			// Check that char after "script" is not a letter (i.e. another tag)

			return !Character.isLetter(item);
		}
		else {
			return false;
		}
	}

	private static final char[] _TAG_SCRIPT = {'s', 'c', 'r', 'i', 'p', 't'};

}