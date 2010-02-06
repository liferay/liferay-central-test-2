/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.Normalizer;

/**
 * <a href="FriendlyURLNormalizer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class FriendlyURLNormalizer {

	public static String normalize(String friendlyURL) {
		return normalize(friendlyURL, null);
	}

	public static String normalize(String friendlyURL, char[] replaceChars) {
		if (Validator.isNull(friendlyURL)) {
			return friendlyURL;
		}

		friendlyURL = GetterUtil.getString(friendlyURL);
		friendlyURL = friendlyURL.toLowerCase();
		friendlyURL = Normalizer.normalizeToAscii(friendlyURL);

		char[] charArray = friendlyURL.toCharArray();

		for (int i = 0; i < charArray.length; i++) {
			char oldChar = charArray[i];

			char newChar = oldChar;

			if (ArrayUtil.contains(_REPLACE_CHARS, oldChar) ||
				((replaceChars != null) &&
				 ArrayUtil.contains(replaceChars, oldChar))) {

				newChar = CharPool.DASH;
			}

			if (oldChar != newChar) {
				charArray[i] = newChar;
			}
		}

		friendlyURL = new String(charArray);

		while (friendlyURL.contains(StringPool.DASH + StringPool.DASH)) {
			friendlyURL = StringUtil.replace(
				friendlyURL, StringPool.DASH + StringPool.DASH,
				StringPool.DASH);
		}

		if (friendlyURL.startsWith(StringPool.DASH)) {
			friendlyURL = friendlyURL.substring(1, friendlyURL.length());
		}

		if (friendlyURL.endsWith(StringPool.DASH)) {
			friendlyURL = friendlyURL.substring(0, friendlyURL.length() - 1);
		}

		return friendlyURL;
	}

	private static final char[] _REPLACE_CHARS = new char[] {
		' ', ',', '\\', '\'', '\"', '(', ')', '{', '}', '?', '#', '@', '+',
		'~', ';', '$', '%'
	};

}