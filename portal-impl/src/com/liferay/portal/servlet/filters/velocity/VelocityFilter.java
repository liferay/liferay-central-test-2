/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.velocity;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.velocity.VelocityVariables;
import com.liferay.util.servlet.filters.CacheResponse;
import com.liferay.util.servlet.filters.CacheResponseData;
import com.liferay.util.servlet.filters.CacheResponseUtil;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * <a href="VelocityFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 *
 */
public class VelocityFilter extends BasePortalFilter {

	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		String pattern = filterConfig.getInitParameter("pattern");

		_pattern = Pattern.compile(pattern);
	}

	protected boolean isMatchingURL(String completeURL) {
		Matcher matcher = _pattern.matcher(completeURL);

		return matcher.matches();
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws IOException, ServletException {

		String completeURL = HttpUtil.getCompleteURL(request);

		if (isMatchingURL(completeURL)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processing " + completeURL);
			}

			CacheResponse cacheResponse = new CacheResponse(
				response, StringPool.UTF8);

			processFilter(
				VelocityFilter.class, request, cacheResponse, filterChain);

			VelocityContext context = new VelocityContext();

			StringReader reader = new StringReader(
				new String(cacheResponse.getData()));
			StringWriter writer = new StringWriter();

			ThemeDisplay themeDisplay = null;

			try {

				// Company

				long companyId = ParamUtil.getLong(request, "companyId");

				Company company = CompanyLocalServiceUtil.getCompanyById(
					companyId);

				// Paths

				String contextPath = PortalUtil.getPathContext();

				// Locale

				String languageId = ParamUtil.getString(request, "languageId");

				Locale locale = LocaleUtil.fromLanguageId(languageId);

				// Theme and color scheme

				String themeId = ParamUtil.getString(request, "themeId");
				String colorSchemeId = ParamUtil.getString(
					request, "colorSchemeId");

				boolean wapTheme = BrowserSnifferUtil.is_wap(request);

				Theme theme = ThemeLocalServiceUtil.getTheme(
					companyId, themeId, wapTheme);
				ColorScheme colorScheme = ThemeLocalServiceUtil.getColorScheme(
					companyId, theme.getThemeId(), colorSchemeId, wapTheme);

				// Theme display

				themeDisplay = ThemeDisplayFactory.create();

				themeDisplay.setCompany(company);
				themeDisplay.setLocale(locale);
				themeDisplay.setLookAndFeel(contextPath, theme, colorScheme);
				themeDisplay.setPathContext(contextPath);

				request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

				// Velocity variables

				VelocityVariables.insertVariables(context, request);

				// Evaluate template

				Velocity.evaluate(
					context, writer, VelocityFilter.class.getName(), reader);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
			finally {
				try {
					if (themeDisplay != null) {
						ThemeDisplayFactory.recycle(themeDisplay);
					}
				}
				catch (Exception e) {
				}
			}

			CacheResponseData data = new CacheResponseData(
				writer.toString().getBytes(StringPool.UTF8),
				cacheResponse.getContentType(), cacheResponse.getHeaders());

			CacheResponseUtil.write(response, data);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Not processing " + completeURL);
			}

			processFilter(VelocityFilter.class, request, response, filterChain);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VelocityFilter.class);

	private Pattern _pattern;

}