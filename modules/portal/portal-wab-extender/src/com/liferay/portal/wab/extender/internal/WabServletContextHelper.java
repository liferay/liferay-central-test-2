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

import java.util.Enumeration;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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

		_bundle = bundle;

		URL url = bundle.getEntry("WEB-INF/");

		if (url == null) {
			_wabShapedBundle = false;
		}
		else {
			_wabShapedBundle = true;
		}

		Class<?> clazz = getClass();

		_string = clazz.getSimpleName() + '[' + bundle + ']';
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
			return _getResourceInBundleOrFragments(
				"/META-INF/resources" + name);
		}

		return _getResourceInBundleOrFragments(name);
	}

	@Override
	public boolean handleSecurity(
		HttpServletRequest request, HttpServletResponse response) {

		String path = null;

		if (request.getDispatcherType() == DispatcherType.INCLUDE) {
			path = (String)request.getAttribute(
				RequestDispatcher.INCLUDE_SERVLET_PATH);

			String pathInfo = (String)request.getAttribute(
				RequestDispatcher.INCLUDE_PATH_INFO);

			if (pathInfo != null) {
				path = path + pathInfo;
			}
		}
		else {
			path = request.getPathInfo();
		}

		if (path == null) {
			return true;
		}

		if (path.indexOf('/') != 0) {
			path = '/' + path;
		}

		if (path.startsWith("/META-INF/") || path.startsWith("/OSGI-INF/") ||
			path.startsWith("/OSGI-OPT/") || path.startsWith("/WEB-INF/")) {

			try {
				ServletContext servletContext = request.getServletContext();

				servletContext.log(
					"[WAB ERROR] Attempt to load illegal path " + path +
						" in " + toString());

				response.sendError(HttpServletResponse.SC_FORBIDDEN, path);
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

	private URL _getResourceInBundleOrFragments(String name) {
		String dirName = "/";
		String fileName = name;

		int index = name.lastIndexOf('/');

		if (index > 0) {
			dirName = name.substring(0, index);
			fileName = name.substring(index + 1);
		}

		Enumeration<URL> enumeration = _bundle.findEntries(
			dirName, fileName, false);

		if ((enumeration == null) || !enumeration.hasMoreElements()) {
			return null;
		}

		return enumeration.nextElement();
	}

	private final Bundle _bundle;
	private final String _string;
	private final boolean _wabShapedBundle;

}