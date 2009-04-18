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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.servlet.filters.theme;

import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="ThemePreviewFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ganesh P Ram
 *
 */

public class ThemePreviewFilter extends BasePortalFilter {

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws IOException, ServletException {

		boolean isThemePreview = _isThemePreview(request);

		if (isThemePreview){

			request.setAttribute(_STRIP, false);

			StringServletResponse stringServerRes =
				new StringServletResponse(response);

			processFilter(ThemePreviewFilter.class,
					request, stringServerRes, filterChain);

			String s = stringServerRes.getString();

			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(
					WebKeys.THEME_DISPLAY);

			Pattern cssPattern = Pattern.compile(
					themeDisplay.getPathThemeCss());

			Matcher cssMatcher = cssPattern.matcher(s);
			String cssOutput = cssMatcher.replaceAll(_CSS);

			Pattern imagePattern = Pattern.compile(
					themeDisplay.getPathThemeImages());

			Matcher imageMatcher = imagePattern.matcher(cssOutput);

			String imageOutput = imageMatcher.replaceAll(_IMAGES);

			ServletResponseUtil.write(response,imageOutput);

		}else {
			processFilter(ThemePreviewFilter.class,
				request, response, filterChain);
		}
	}

	private boolean _isThemePreview(HttpServletRequest request) {
		if (ParamUtil.getBoolean(request, _THEME_PREVIEW, false)) {
			return true;
		}
		else {
			return false;
		}
	}

	private static final String _CSS = "css";
	private static final String _IMAGES = "images";
	private static final String _STRIP = "strip";
	private static final String _THEME_PREVIEW = "themePreview";

}