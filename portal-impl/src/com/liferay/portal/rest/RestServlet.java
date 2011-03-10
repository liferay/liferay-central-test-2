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

package com.liferay.portal.rest;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.servlet.PortalAbstractHttpServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
public class RestServlet extends PortalAbstractHttpServlet {

	public void init(ServletConfig servletConfig) {
		super.init(servletConfig);

		ServletContext servletContext = servletConfig.getServletContext();

		ClassLoader classLoader =
			(ClassLoader)servletContext.getAttribute("_ORIGINAL_CLASS_LOADER");

		if (classLoader == null) {
			classLoader = Thread.currentThread().getContextClassLoader();
		}

		JSONRestAction jsonAction = new JSONRestAction(classLoader);

		jsonAction.setServletContext(servletContext);

		_action = jsonAction;
	}

	@Override
	protected void execute(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			_action.execute(null, null, request, response);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private Action _action;

	private static Log _log = LogFactoryUtil.getLog(RestServlet.class);

}