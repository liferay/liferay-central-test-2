/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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