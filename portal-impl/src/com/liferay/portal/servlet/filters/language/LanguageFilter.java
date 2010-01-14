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
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.util.servlet.filters.CacheResponse;
import com.liferay.util.servlet.filters.CacheResponseData;
import com.liferay.util.servlet.filters.CacheResponseUtil;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="LanguageFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 */
public class LanguageFilter extends BasePortalFilter {

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		CacheResponse cacheResponse = new CacheResponse(
			response, StringPool.UTF8);

		processFilter(
			LanguageFilter.class, request, cacheResponse, filterChain);

		byte[] bytes = translateResponse(
			request, cacheResponse, cacheResponse.unsafeGetData(),
			cacheResponse.getDataLength());

		CacheResponseData cacheResponseData = new CacheResponseData(
			bytes, bytes.length, cacheResponse.getContentType(),
			cacheResponse.getHeaders());

		CacheResponseUtil.write(response, cacheResponseData);
	}

	protected byte[] translateResponse(
		HttpServletRequest request, HttpServletResponse response,
		byte[] bytes, int length) {

		String languageId = LanguageUtil.getLanguageId(request);
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		String content = new String(bytes, 0 ,length);

		Matcher matcher = _pattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group(0);
			String key = matcher.group(1);

			StringBuffer sb = new StringBuffer();

			sb.append(StringPool.APOSTROPHE);
			sb.append(UnicodeLanguageUtil.get(locale, key));
			sb.append(StringPool.APOSTROPHE);

			content = content.replace(match, sb.toString());
		}

		return content.getBytes();
	}

	private static Pattern _pattern = Pattern.compile(
		"Liferay\\.Language\\.get\\([\"']([^)]+)[\"']\\)");

}