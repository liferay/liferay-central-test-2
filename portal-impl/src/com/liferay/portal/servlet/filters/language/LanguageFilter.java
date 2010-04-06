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

package com.liferay.portal.servlet.filters.language;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.servlet.filters.BasePortalFilter;
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

		StringServletResponse stringResponse = new StringServletResponse(
			response);

		processFilter(
			LanguageFilter.class, request, stringResponse, filterChain);

		byte[] bytes = translateResponse(request, stringResponse);

		CacheResponseData cacheResponseData = new CacheResponseData(
			bytes, bytes.length, stringResponse.getContentType(),
			stringResponse.getHeaders());

		CacheResponseUtil.write(response, cacheResponseData);
	}

	protected byte[] translateResponse(
		HttpServletRequest request, StringServletResponse stringResponse) {

		String languageId = LanguageUtil.getLanguageId(request);
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		String content = stringResponse.getString();

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