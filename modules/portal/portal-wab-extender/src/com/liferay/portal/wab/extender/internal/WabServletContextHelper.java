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

package com.liferay.portal.wab.extender.internal;

import java.io.IOException;

import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Raymond Augé
 */
public class WabServletContextHelper extends ServletContextHelper {

	public WabServletContextHelper(Bundle bundle) {
		super(bundle);

		URL url = bundle.getEntry("WEB-INF/");

		if (url == null) {
			_wabShapedBundle = false;
		}
		else {
			_wabShapedBundle = true;
		}

		Class<?> clazz = getClass();

		_string = clazz.getSimpleName() + '[' + bundle.getBundleId() + ']';
	}

	@Override
	public String getRealPath(String path) {
		URL url = getResource(path);

		if (url == null) {
			return null;
		}

		return url.toExternalForm();
	}

	@Override
	public URL getResource(String name) {
		if (name.charAt(0) != '/') {
			name = '/' + name;
		}

		if (!_wabShapedBundle && !name.startsWith("/META-INF/resources")) {
			return super.getResource("/META-INF/resources" + name);
		}

		return super.getResource(name);
	}

	@Override
	public boolean handleSecurity(
		HttpServletRequest request, HttpServletResponse response) {

		String pathInfo = null;

		if (request.getAttribute(
				RequestDispatcher.INCLUDE_REQUEST_URI) != null) {

			pathInfo = (String)request.getAttribute(
				RequestDispatcher.INCLUDE_PATH_INFO);
		}
		else {
			pathInfo = request.getPathInfo();
		}

		if (pathInfo == null) {
			return true;
		}

		if (pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1);
		}

		if (pathInfo.startsWith("META-INF/") ||
			pathInfo.startsWith("OSGI-INF/") ||
			pathInfo.startsWith("OSGI-OPT/") ||
			pathInfo.startsWith("WEB-INF/")) {

			try {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, pathInfo);
			}
			catch (IOException ioe) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}

			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return _string;
	}

	private final String _string;
	private final boolean _wabShapedBundle;

}