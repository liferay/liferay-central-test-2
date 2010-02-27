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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.util.servlet.DynamicServletRequest;

import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * <a href="ParamAndPropertyAncestorTagImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class ParamAndPropertyAncestorTagImpl
	extends BodyTagSupport implements ParamAncestorTag, PropertyAncestorTag {

	public void addParam(String name, String value) {
		if (_params == null) {
			_params = new LinkedHashMap<String, String[]>();
		}

		String[] values = _params.get(name);

		if (values == null) {
			values = new String[] {value};
		}
		else {
			String[] newValues = new String[values.length + 1];

			System.arraycopy(values, 0, newValues, 0, values.length);

			newValues[newValues.length - 1] = value;

			values = newValues;
		}

		_params.put(name, values);
	}

	public void addProperty(String name, String value) {
		if (_properties == null) {
			_properties = new LinkedHashMap<String, String[]>();
		}

		String[] values = _properties.get(name);

		if (values == null) {
			values = new String[] {value};
		}
		else {
			String[] newValues = new String[values.length + 1];

			System.arraycopy(values, 0, newValues, 0, values.length);

			newValues[newValues.length - 1] = value;

			values = newValues;
		}

		_properties.put(name, values);
	}

	public void clearParams() {
		if (_params != null) {
			_params.clear();
		}
	}

	public void clearProperties() {
		if (_properties != null) {
			_properties.clear();
		}
	}

	public Map<String, String[]> getParams() {
		return _params;
	}

	public Map<String, String[]> getProperties() {
		return _properties;
	}

	public ServletContext getServletContext() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		if (servletContext == null) {
			servletContext = pageContext.getServletContext();
		}

		return servletContext;
	}

	public HttpServletRequest getServletRequest() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (_params != null) {
			request = new DynamicServletRequest(request, _params);
		}

		return request;
	}

	public StringServletResponse getServletResponse() {
		return new StringServletResponse(
			(HttpServletResponse)pageContext.getResponse());
	}

	public void include(String path) throws IOException, ServletException {
		ServletContext servletContext = getServletContext();
		HttpServletRequest request = getServletRequest();
		StringServletResponse stringResponse = getServletResponse();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(path);

		requestDispatcher.include(request, stringResponse);

		pageContext.getOut().print(stringResponse.getString());
	}

	private Map<String, String[]> _params;
	private Map<String, String[]> _properties;

}