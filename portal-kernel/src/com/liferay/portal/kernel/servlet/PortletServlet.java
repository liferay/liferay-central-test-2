/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletSession;
import com.liferay.portal.kernel.util.JavaConstants;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.FilterChain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <a href="PortletServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletServlet extends HttpServlet {

	public static final String PORTLET_CLASS_LOADER = "PORTLET_CLASS_LOADER";

	public static final String PORTLET_SERVLET_CONFIG =
		"com.liferay.portal.kernel.servlet.PortletServletConfig";

	public static final String PORTLET_SERVLET_FILTER_CHAIN =
		"com.liferay.portal.kernel.servlet.PortletServletFilterChain";

	public static final String PORTLET_SERVLET_REQUEST =
		"com.liferay.portal.kernel.servlet.PortletServletRequest";

	public static final String PORTLET_SERVLET_RESPONSE =
		"com.liferay.portal.kernel.servlet.PortletServletResponse";

	public void service(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		PortletRequest portletReq = (PortletRequest)req.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletRes = (PortletResponse)req.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		FilterChain filterChain = (FilterChain)req.getAttribute(
			PORTLET_SERVLET_FILTER_CHAIN);

		LiferayPortletSession portletSes =
			(LiferayPortletSession)portletReq.getPortletSession();

		portletReq.setAttribute(PORTLET_SERVLET_CONFIG, getServletConfig());
		portletReq.setAttribute(PORTLET_SERVLET_REQUEST, req);
		portletReq.setAttribute(PORTLET_SERVLET_RESPONSE, res);

		HttpSession ses = req.getSession();

		PortletSessionTracker.add(ses);

		portletSes.setHttpSession(ses);

		try {
			if (portletReq instanceof ActionRequest) {
				ActionRequest actionReq = (ActionRequest)portletReq;
				ActionResponse actionRes = (ActionResponse)portletRes;

				filterChain.doFilter(actionReq, actionRes);
			}
			else if (portletReq instanceof EventRequest) {
				EventRequest eventReq = (EventRequest)portletReq;
				EventResponse eventRes = (EventResponse)portletRes;

				filterChain.doFilter(eventReq, eventRes);
			}
			else if (portletReq instanceof RenderRequest) {
				RenderRequest renderReq = (RenderRequest)portletReq;
				RenderResponse renderRes = (RenderResponse)portletRes;

				filterChain.doFilter(renderReq, renderRes);
			}
			else if (portletReq instanceof ResourceRequest) {
				ResourceRequest resourceReq = (ResourceRequest)portletReq;
				ResourceResponse resourceRes = (ResourceResponse)portletRes;

				filterChain.doFilter(resourceReq, resourceRes);
			}
		}
		catch (PortletException pe) {
			_log.error(pe, pe);

			throw new ServletException(pe);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortletServlet.class);

}