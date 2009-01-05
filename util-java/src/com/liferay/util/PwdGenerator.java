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

package com.liferay.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * <a href="PwdGenerator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PwdGenerator {

	public static String KEY1 = "0123456789";

	public static String KEY2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String KEY3 = "abcdefghijklmnopqrstuvwxyz";

	public static String getPinNumber() {
		return _getPassword(KEY1, 4, true);
	}

	public static String getPassword() {
		return getPassword(8);
	}

	public static String getPassword(int length) {
		return _getPassword(KEY1 + KEY2 + KEY3, length, true);
	}

	public static String getPassword(String key, int length) {
		return getPassword(key, length, true);
	}

	public static String getPassword(
		String key, int length, boolean useAllKeys) {

		return _getPassword(key, length, useAllKeys);
	}

	private static String _getPassword(
		String key, int length, boolean useAllKeys) {

		StringBuilder sb = new StringBuilder();

		boolean checkKey1 = key.contains(KEY1);
		boolean checkKey2 = key.contains(KEY2);
		boolean checkKey3 = key.contains(KEY3);

		boolean usesKey1 = true;
		boolean usesKey2 = true;
		boolean usesKey3 = true;

		String password = StringPool.BLANK;

		do {
			for (int i = 0; i < length; i++) {
				sb.append(key.charAt((int)(Math.random() * key.length())));
			}

			password = sb.toString();

			if (checkKey1) {
				usesKey1 = Validator.isNotNull(
								StringUtil.extractDigits(password));
			}

			if (checkKey2) {
				usesKey2 = !password.equals(password.toLowerCase());
			}

			if (checkKey3) {
				usesKey3 = !password.equals(password.toUpperCase());
			}

			sb.setLength(0);
		} while (useAllKeys && (!usesKey1 || !usesKey2 || !usesKey3));

		return password;
	}

}