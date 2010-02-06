/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.apache.bridges.struts;

import com.liferay.portal.kernel.servlet.ServletContextProvider;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletContextImpl;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="LiferayServletContextProvider.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author James Schopp
 * @author Michael Young
 * @author Deepak Gothe
 */
public class LiferayServletContextProvider implements ServletContextProvider {

	public ServletContext getServletContext(GenericPortlet portlet) {
		PortletContext portletContext = portlet.getPortletContext();

		ServletContext servletContext =
			(ServletContext)portletContext.getAttribute(
				JavaConstants.JAVAX_PORTLET_SERVLET_CONTEXT);

		if (servletContext == null) {
			PortletContextImpl portletContextImpl =
				(PortletContextImpl)portlet.getPortletContext();

			servletContext = portletContextImpl.getServletContext();
		}

		return getServletContext(servletContext);
	}

	public ServletContext getServletContext(ServletContext servletContext) {
		return new LiferayServletContext(servletContext);
	}

	public HttpServletRequest getHttpServletRequest(
		GenericPortlet portlet, PortletRequest portletRequest) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return new LiferayStrutsRequestImpl(request);
	}

	public HttpServletResponse getHttpServletResponse(
		GenericPortlet portlet, PortletResponse portletResponse) {

		return PortalUtil.getHttpServletResponse(portletResponse);
	}

}