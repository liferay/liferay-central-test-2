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
import com.liferay.portal.kernel.util.StringPool;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LocaleUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LocaleUtil {

	public static Locale fromLanguageId(String languageId) {
		Locale locale = null;

		try {
			locale = (Locale)_locales.get(languageId);

			if (locale == null) {
				int pos = languageId.indexOf(StringPool.UNDERLINE);

				String languageCode = languageId.substring(0, pos);
				String countryCode = languageId.substring(
					pos + 1, languageId.length());

				locale = new Locale(languageCode, countryCode);

				_locales.put(languageId, locale);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(languageId + " is not a valid language id");
			}
		}

		if (locale == null) {
			locale = Locale.getDefault();
		}

		return locale;
	}

	public static String toLanguageId(Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}

		StringMaker sm = new StringMaker();

		sm.append(locale.getLanguage());
		sm.append(StringPool.UNDERLINE);
		sm.append(locale.getCountry());

		return sm.toString();
	}

	private static Log _log = LogFactory.getLog(LocaleUtil.class);

	private static Map _locales = CollectionFactory.getHashMap();

}