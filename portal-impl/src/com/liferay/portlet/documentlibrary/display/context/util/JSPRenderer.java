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

package com.liferay.portlet.documentlibrary.display.context.util;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iv�n Zaera
 */
public class JSPRenderer {

	public JSPRenderer(String jspPath) {
		_jspPath = jspPath;
	}

	public void render(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		Map<String, Object> savedAttributes = new HashMap<>();

		for (String key : _attributes.keySet()) {
			savedAttributes.put(key, request.getAttribute(key));

			request.setAttribute(key, _attributes.get(key));
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(
			_jspPath);

		requestDispatcher.include(request, response);

		for (String key : savedAttributes.keySet()) {
			request.setAttribute(key, savedAttributes.get(key));
		}
	}

	public void setAttribute(String key, Object value) {
		_attributes.put(key, value);
	}

	private final Map<String, Object> _attributes = new HashMap<>();
	private final String _jspPath;

}