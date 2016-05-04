/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.header;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PropsValues;

import java.text.Format;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 */
public class HeaderFilter extends BasePortalFilter {

	protected long getLastModified(HttpServletRequest request) {
		String value = HttpUtil.getParameter(request.getQueryString(), "t");

		if (Validator.isNull(value)) {
			return -1;
		}

		return GetterUtil.getLong(value);
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		FilterConfig filterConfig = getFilterConfig();

		Enumeration<String> enu = filterConfig.getInitParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (_requestHeaderIgnoreInitParams.contains(name)) {
				continue;
			}

			String value = filterConfig.getInitParameter(name);

			if (name.equals(HttpHeaders.EXPIRES) && Validator.isNumber(value)) {
				int seconds = GetterUtil.getInteger(value);

				Calendar cal = new GregorianCalendar();

				cal.add(Calendar.SECOND, seconds);

				value = _dateFormat.format(cal.getTime());
			}

			// LEP-5895 and LPS-15802

			boolean addHeader = true;

			if (StringUtil.equalsIgnoreCase(name, HttpHeaders.CACHE_CONTROL) ||
				StringUtil.equalsIgnoreCase(name, HttpHeaders.EXPIRES)) {

				boolean newSession = false;

				HttpSession session = request.getSession(false);

				if ((session == null) || session.isNew()) {
					newSession = true;
				}

				String contextPath = request.getContextPath();

				if (StringUtil.equalsIgnoreCase(name, HttpHeaders.EXPIRES) &&
					newSession) {

					addHeader = false;
				}
				else if (PropsValues.WEB_SERVER_PROXY_LEGACY_MODE &&
						 newSession &&
						 contextPath.equals(PortalUtil.getPathContext())) {

					addHeader = false;
				}
			}

			if (addHeader) {
				response.addHeader(name, value);
			}
		}

		long lastModified = getLastModified(request);
		long ifModifiedSince = request.getDateHeader(
			HttpHeaders.IF_MODIFIED_SINCE);

		if (lastModified > 0) {
			response.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModified);

			if (lastModified <= ifModifiedSince) {
				response.setDateHeader(
					HttpHeaders.LAST_MODIFIED, ifModifiedSince);
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);

				return;
			}
		}

		processFilter(
			HeaderFilter.class.getName(), request, response, filterChain);
	}

	private static final Format _dateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat(
			Time.RFC822_FORMAT, LocaleUtil.US, TimeZoneUtil.GMT);
	private static final Set<String> _requestHeaderIgnoreInitParams =
		SetUtil.fromArray(PropsValues.REQUEST_HEADER_IGNORE_INIT_PARAMS);

}