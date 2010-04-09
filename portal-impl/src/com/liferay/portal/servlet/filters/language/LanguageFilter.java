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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.util.servlet.ServletResponseUtil;

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
 * @author Shuyang Zhou
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

		String content = translateResponse(request, stringResponse);

		ServletResponseUtil.write(response, content);
	}

	protected String translateResponse(
		HttpServletRequest request, StringServletResponse stringResponse) {

		String languageId = LanguageUtil.getLanguageId(request);
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		String content = stringResponse.getString();

		StringBundler newContentSB = new StringBundler();

		Matcher matcher = _pattern.matcher(content);

		int lastIndex = 0;
		while (matcher.find()) {
			int matchStart = matcher.start(0);
			String key = matcher.group(1);

			newContentSB.append(content.substring(lastIndex, matchStart));
			newContentSB.append(StringPool.APOSTROPHE);
			newContentSB.append(UnicodeLanguageUtil.get(locale, key));
			newContentSB.append(StringPool.APOSTROPHE);

			lastIndex = matcher.end(0);
		}
		newContentSB.append(content.substring(lastIndex));
		return newContentSB.toString();
	}

	private static Pattern _pattern = Pattern.compile(
		"Liferay\\.Language\\.get\\([\"']([^)]+)[\"']\\)");

}