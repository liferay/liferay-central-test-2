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

package com.liferay.util.bridges.wai;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <a href="WAIHttpServletRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class WAIHttpServletRequest extends HttpServletRequestWrapper {

	public WAIHttpServletRequest(
		HttpServletRequest request, String contextPath, String pathInfo,
		String queryString, Map<String, String[]> params) {

		super(request);

		_contextPath = contextPath;
		_pathInfo = pathInfo;
		_queryString = queryString;
		_params = params;
	}

	public String getContextPath() {
		return _contextPath;
	}

	public String getPathInfo() {
		return super.getPathInfo();
	}

	public String getQueryString() {
		return _queryString;
	}

	public String getRequestURI() {
		StringBundler sb = new StringBundler(4);

		sb.append(getContextPath());
		sb.append(_pathInfo);

		if (getQueryString().trim().length() > 0) {
			sb.append(StringPool.QUESTION);
			sb.append(getQueryString());
		}

		return sb.toString();
	}

	public StringBuffer getRequestURL() {
		return new StringBuffer(getRequestURI());
	}

	public Map<String, String[]> getParameterMap() {
		return _params;
	}

	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(_params.keySet());
	}

	public String getParameter(String key) {
		String[] values = _params.get(key);

		if (values == null) {
			return null;
		}

		return values[0];
	}

	public String[] getParameterValues(String key) {
		return _params.get(key);
	}

	private String _contextPath;
	private String _pathInfo;
	private String _queryString;
	private Map<String, String[]> _params;

}