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

package com.liferay.portal.servlet;

import com.liferay.registry.collections.ServiceTrackerCollections;

import java.util.List;

import javax.servlet.http.HttpServlet;

/**
 * @author Pavel Savinov
 */
public class PrivateGroupFriendlyURLServletAdapter
	extends BaseFriendlyURLServletAdapter {

	@Override
	protected HttpServlet getServlet() {
		if (!_servlets.isEmpty()) {
			return _servlets.get(0);
		}

		return null;
	}

	private final List<HttpServlet> _servlets =
		ServiceTrackerCollections.openList(
			HttpServlet.class,
			"(&(servlet.type=friendly-url)(servlet.init.private=true)" +
				"(servlet.init.user=false))");

}