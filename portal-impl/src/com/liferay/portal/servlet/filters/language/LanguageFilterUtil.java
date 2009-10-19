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

package com.liferay.portal.servlet.filters.language;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsUtil;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="LanguageFilterUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 */
public class LanguageFilterUtil {

	public static String replaceLanguage(
		HttpServletRequest request, HttpServletResponse response,
		byte[] bytes) {

		String content = new String(bytes);

		if (!_LANGUAGE_FILTER_ENABLED) {
			return content;
		}

		String languageId = LanguageUtil.getLanguageId(request);
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		Matcher matcher = _languagePattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group(0);
			String key = matcher.group(1);

			StringBuffer language = new StringBuffer();

			language.append(StringPool.APOSTROPHE);
			language.append(LanguageUtil.get(locale, key));
			language.append(StringPool.APOSTROPHE);

			content = content.replace(match, language.toString());
		}

		return content;
	}

	private static Pattern _languagePattern = Pattern.compile(
		"Liferay\\.Language\\.get\\([\"']([^)]+)[\"']\\)");

	private static final boolean _LANGUAGE_FILTER_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(LanguageFilter.class.getName()), true);

}