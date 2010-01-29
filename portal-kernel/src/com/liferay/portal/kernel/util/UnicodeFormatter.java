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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * <a href="UnicodeFormatter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UnicodeFormatter {

	public static String byteToHex(byte b) {
		char[] array = {_HEX_DIGITS[(b >> 4) & 0x0f], _HEX_DIGITS[b & 0x0f]};

		return new String(array);
	}

	public static String charToHex(char c) {
		byte hi = (byte)(c >>> 8);
		byte lo = (byte)(c & 0xff);

		char[] array = {
			_HEX_DIGITS[(hi >> 4) & 0x0f], _HEX_DIGITS[hi & 0x0f],
			_HEX_DIGITS[(lo >> 4) & 0x0f], _HEX_DIGITS[lo & 0x0f]
		};

		return new String(array);
	}

	public static String parseString(String hexString) {
		StringBuilder sb = new StringBuilder();

		char[] array = hexString.toCharArray();

		if ((array.length % 6) != 0) {
			_log.error("String is not in hex format");

			return hexString;
		}

		for (int i = 2; i < hexString.length(); i = i + 6) {
			String s = hexString.substring(i, i + 4);

			try {
				char c = (char)Integer.parseInt(s, 16);

				sb.append(c);
			}
			catch (Exception e) {
				_log.error(e, e);

				return hexString;
			}
		}

		return sb.toString();
	}

	public static String toString(char[] array) {
		StringBuilder sb = new StringBuilder(array.length * 6);

		char[] hexes = new char[4];

		for (int i = 0; i < array.length; i++) {
			sb.append(_UNICODE_PREFIX);
			sb.append(_charToHex(array[i], hexes));
		}

		return sb.toString();
	}

	public static String toString(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder(s.length() * 6);

		char[] hexes = new char[4];

		for (int i = 0; i < s.length(); i++) {
			sb.append(_UNICODE_PREFIX);
			sb.append(_charToHex(s.charAt(i), hexes));
		}

		return sb.toString();
	}

	private static char[] _charToHex(char c, char[] hexes) {
		byte hi = (byte)(c >>> 8);
		byte lo = (byte)(c & 0xff);

		hexes[0] = _HEX_DIGITS[(hi >> 4) & 0x0f];
		hexes[1] = _HEX_DIGITS[hi & 0x0f];
		hexes[2] = _HEX_DIGITS[(lo >> 4) & 0x0f];
		hexes[3] = _HEX_DIGITS[lo & 0x0f];

		return hexes;
	}

	private static final char[] _HEX_DIGITS = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
		'e', 'f'
	};

	private static final String _UNICODE_PREFIX = "\\u";

	private static Log _log = LogFactoryUtil.getLog(UnicodeFormatter.class);

}