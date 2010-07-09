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

package com.liferay.portal.kernel.dao.search;

import com.liferay.portal.kernel.servlet.PipingServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
public class JSPSearchEntry extends SearchEntry {

	public JSPSearchEntry(String align, String valign, String path) {
		this(align, valign, DEFAULT_COLSPAN, path, null, null, null);
	}

	public JSPSearchEntry(
		String align, String valign, int colspan, String path) {

		this(align, valign, colspan, path, null, null, null);
	}

	public JSPSearchEntry(
		String align, String valign, int colspan, String path,
		ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response) {

		super(align, valign, colspan);

		_path = path;
		_servletContext = servletContext;
		_request = request;
		_response = response;
	}

	public String getPath() {
		return _path;
	}

	public void setPath(String path) {
		_path = path;
	}

	public void print(PageContext pageContext) throws Exception {
		if (_servletContext != null) {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(_path);

			requestDispatcher.include(
				_request, new PipingServletResponse(pageContext));
		}
		else {
			pageContext.include(_path);
		}
	}

	public Object clone() {
		return new JSPSearchEntry(
			getAlign(), getValign(), getColspan(), getPath(), _servletContext,
			_request, _response);
	}

	private String _path;
	private ServletContext _servletContext;
	private HttpServletRequest _request;
	private HttpServletResponse _response;

}