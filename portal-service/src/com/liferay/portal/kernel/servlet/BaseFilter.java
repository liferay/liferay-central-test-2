/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 * @author Eduardo Lundgren
 */
public abstract class BaseFilter implements LiferayFilter {

	public void destroy() {
	}

	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		Log log = getLog();

		if (log.isDebugEnabled()) {
			if (isFilterEnabled()) {
				log.debug(_filterClass + " is enabled");
			}
			else {
				log.debug(_filterClass + " is disabled");
			}
		}

		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;

		StringBuffer requestURL = request.getRequestURL();

		boolean filterEnabled = isFilterEnabled();

		if (filterEnabled && (requestURL == null)) {
			filterEnabled = false;
		}

		if (filterEnabled &&
			((_urlRegexPattern != null) ||
			 (_urlRegexIgnorePattern != null))) {

			String url = requestURL.toString();

			String queryString = request.getQueryString();

			if (Validator.isNotNull(queryString)) {
				url = url.concat(StringPool.QUESTION).concat(queryString);
			}

			if (_urlRegexPattern != null) {
				Matcher matcher = _urlRegexPattern.matcher(url);

				filterEnabled = matcher.find();
			}

			if (filterEnabled && (_urlRegexIgnorePattern != null)) {
				Matcher matcher = _urlRegexIgnorePattern.matcher(url);

				filterEnabled = !matcher.find();
			}
		}

		try {
			if (filterEnabled) {
				processFilter(request, response, filterChain);
			}
			else {
				processFilter(_filterClass, request, response, filterChain);
			}
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (ServletException se) {
			throw se;
		}
		catch (Exception e) {
			getLog().error(e, e);
		}
	}

	public FilterConfig getFilterConfig() {
		return _filterConfig;
	}

	public void init(FilterConfig filterConfig) {
		_filterConfig = filterConfig;

		String urlRegexPattern = GetterUtil.getString(
			filterConfig.getInitParameter("url-regex-pattern"));

		if (Validator.isNotNull(urlRegexPattern)) {
			_urlRegexPattern = Pattern.compile(urlRegexPattern);
		}

		String urlRegexIgnorePattern = GetterUtil.getString(
			filterConfig.getInitParameter("url-regex-ignore-pattern"));

		if (Validator.isNotNull(urlRegexIgnorePattern)) {
			_urlRegexIgnorePattern = Pattern.compile(urlRegexIgnorePattern);
		}
	}

	public boolean isFilterEnabled() {
		return _filterEnabled;
	}

	public boolean isFilterEnabled(HttpServletRequest request) {
		return _filterEnabled;
	}

	protected abstract Log getLog();

	protected void processFilter(
			Class<?> filterClass, HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
		throws Exception {

		long startTime = 0;

		String threadName = null;
		String depther = null;
		String path = null;

		Log log = getLog();

		if (log.isDebugEnabled()) {
			startTime = System.currentTimeMillis();

			Thread currentThread = Thread.currentThread();

			threadName = currentThread.getName();

			depther = (String)request.getAttribute(_DEPTHER);

			if (depther == null) {
				depther = StringPool.BLANK;
			}
			else {
				depther += StringPool.EQUAL;
			}

			request.setAttribute(_DEPTHER, depther);

			path = request.getRequestURI();

			log.debug(
				"[" + threadName + "]" + depther + "> " +
					filterClass.getName() + " " + path);
		}

		filterChain.doFilter(request, response);

		if (log.isDebugEnabled()) {
			long endTime = System.currentTimeMillis();

			depther = (String)request.getAttribute(_DEPTHER);

			if (depther == null) {
				return;
			}

			log.debug(
				"[" + threadName + "]" + depther + "< " +
					filterClass.getName() + " " + path + " " +
						(endTime - startTime) + " ms");

			if (depther.length() > 0) {
				depther = depther.substring(1);
			}

			request.setAttribute(_DEPTHER, depther);
		}
	}

	protected abstract void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception;

	private static final String _DEPTHER = "DEPTHER";

	private Class<?> _filterClass = getClass();
	private FilterConfig _filterConfig;
	private boolean _filterEnabled = true;
	private Pattern _urlRegexIgnorePattern;
	private Pattern _urlRegexPattern;

}