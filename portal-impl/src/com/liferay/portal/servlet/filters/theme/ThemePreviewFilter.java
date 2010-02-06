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

package com.liferay.portal.servlet.filters.theme;

import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.servlet.filters.strip.StripFilter;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="ThemePreviewFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ganesh Ram
 */
public class ThemePreviewFilter extends BasePortalFilter {

	protected String getContent(HttpServletRequest request, String content) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Pattern cssPattern = Pattern.compile(
			themeDisplay.getPathThemeCss());

		Matcher cssMatcher = cssPattern.matcher(content);

		content = cssMatcher.replaceAll("css");

		Pattern imagePattern = Pattern.compile(
			themeDisplay.getPathThemeImages());

		Matcher imageMatcher = imagePattern.matcher(content);

		content = imageMatcher.replaceAll("images");

		return content;
	}

	protected boolean isThemePreview(HttpServletRequest request) {
		if (ParamUtil.getBoolean(request, _THEME_PREVIEW)) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		if (isThemePreview(request)) {
			request.setAttribute(StripFilter.SKIP_FILTER, Boolean.TRUE);

			StringServletResponse stringServerResponse =
				new StringServletResponse(response);

			processFilter(
				ThemePreviewFilter.class, request, stringServerResponse,
				filterChain);

			String content = getContent(
				request, stringServerResponse.getString());

			ServletResponseUtil.write(response, content);
		}
		else {
			processFilter(
				ThemePreviewFilter.class, request, response, filterChain);
		}
	}

	private static final String _THEME_PREVIEW = "themePreview";

}