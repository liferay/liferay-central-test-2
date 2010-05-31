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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.servlet.PipingServletResponse;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

/**
 * <a href="LazyIncluder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class LazyIncluder {

	public LazyIncluder(
		RequestDispatcher requestDispatcher, HttpServletRequest request,
		HttpServletResponse response) {

		this(requestDispatcher, request, response, true);
	}

	public LazyIncluder(
		RequestDispatcher requestDispatcher, HttpServletRequest request,
		HttpServletResponse response, boolean autoCleanUp) {
		_requestDispatcher = requestDispatcher;
		_request = request;
		_response = response;
		_autoCleanUp = autoCleanUp;
	}

	public void include(JspWriter writer) throws ServletException, IOException {
		_requestDispatcher.include(
			_request, new PipingServletResponse(_response, writer));
		if (_autoCleanUp) {
			_requestDispatcher = null;
			_request = null;
			_response = null;
		}
	}

	private RequestDispatcher _requestDispatcher;
	private HttpServletRequest _request;
	private HttpServletResponse _response;
	private boolean _autoCleanUp;

}