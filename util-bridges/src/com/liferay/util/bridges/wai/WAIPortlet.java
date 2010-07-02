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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortlet;
import com.liferay.portal.kernel.servlet.PortletServlet;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="WAIPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Connor McKay
 */
public class WAIPortlet extends LiferayPortlet {

	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		HttpServletRequest request =
			(HttpServletRequest)renderRequest.getAttribute(
				PortletServlet.PORTLET_SERVLET_REQUEST);
		HttpServletResponse response =
			(HttpServletResponse)renderRequest.getAttribute(
				PortletServlet.PORTLET_SERVLET_RESPONSE);

		forward(request, response, _JSP_IFRAME);
	}

	protected void forward(
			HttpServletRequest request, HttpServletResponse response,
			String path)
		throws PortletException {

		RequestDispatcher requestDispatcher =
			request.getRequestDispatcher(path);

		try {
			requestDispatcher.forward(request, response);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
		catch (ServletException se) {
			throw new PortletException(se);
		}
	}

	private static final String _JSP_DIR = "/WEB-INF/jsp/liferay/wai";

	private static final String _JSP_IFRAME = _JSP_DIR + "/iframe.jsp";

	private static Log _log = LogFactoryUtil.getLog(WAIPortlet.class);

}