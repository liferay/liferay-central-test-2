/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.servlet;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="PortletServletObjectsFactory.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Deepak Gothe
 */
public class PortletServletObjectsFactory implements ServletObjectsFactory {

	public ServletConfig getServletConfig(
		PortletConfig portletConfig, PortletRequest portletRequest) {

		Object servletConfig = portletConfig.getPortletContext().getAttribute(
			_PORTLET_CONTAINER_SERVLET_CONFIG);

		if (servletConfig == null) {
			servletConfig = portletRequest.getAttribute(
				PortletServlet.PORTLET_SERVLET_CONFIG);
		}

		return (ServletConfig)servletConfig;
	}

	public HttpServletRequest getServletRequest(PortletRequest portletRequest) {
		Object request = portletRequest.getAttribute(
			_PORTLET_CONTAINER_SERVLET_REQUEST);

		if (request == null) {
			request = portletRequest.getAttribute(
				PortletServlet.PORTLET_SERVLET_REQUEST);
		}

		return (HttpServletRequest)request;
	}

	public HttpServletResponse getServletResponse(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Object response = portletRequest.getAttribute(
			_PORTLET_CONTAINER_SERVLET_RESPONSE);

		if (response == null) {
			response = portletRequest.getAttribute(
				PortletServlet.PORTLET_SERVLET_RESPONSE);
		}

		return (HttpServletResponse)response;
	}

	private static final String _PORTLET_CONTAINER_SERVLET_CONFIG =
		"javax.portlet.portletc.servletConfig";

	private static final String _PORTLET_CONTAINER_SERVLET_REQUEST =
		"javax.portlet.portletc.httpServletRequest";

	private static final String _PORTLET_CONTAINER_SERVLET_RESPONSE =
		"javax.portlet.portletc.httpServletResponse";

}