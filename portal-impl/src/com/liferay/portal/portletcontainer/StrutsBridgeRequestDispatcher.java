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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.portletcontainer;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import com.sun.portal.portletcontainer.portlet.impl.RDRequestWrapper;
import com.sun.portal.portletcontainer.portlet.impl.RDResponseWrapper;

import java.io.IOException;

import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="StrutsBridgeRequestDispatcher.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Deepak Gothe
 *
 */
public class StrutsBridgeRequestDispatcher implements RequestDispatcher {

	public StrutsBridgeRequestDispatcher(
		RequestDispatcher requestDispatcher, String path) {

		_requestDispatcher = requestDispatcher;
		_path = path;
	}

	public void forward(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		if (servletRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST) !=
				null) {

			invoke(servletRequest, servletResponse, false);
		}
		else {
			_requestDispatcher.forward(servletRequest, servletResponse);
		}
	}

	public void include(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		if (servletRequest.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST) !=
				null) {

			invoke(servletRequest, servletResponse, true);
		}
		else {
			_requestDispatcher.include(servletRequest, servletResponse);
		}
	}

	public void invoke(
			ServletRequest servletRequest, ServletResponse servletResponse,
			boolean include)
		throws IOException, ServletException {

		String pathInfo = null;
		String queryString = null;
		String requestURI = null;
		String servletPath = null;

		PortletRequest portletRequest =
			(PortletRequest)servletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse =
			(PortletResponse)servletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (_path != null) {
			String pathNoQueryString = _path;

			int pos = _path.indexOf(StringPool.QUESTION);

			if (pos != -1) {
				pathNoQueryString = _path.substring(0, pos);
				queryString = _path.substring(pos + 1, _path.length());
			}

			Set<String> servletURLPatterns =
				_getServletURLPatterns(servletRequest, portletResponse);

			for (String urlPattern : servletURLPatterns) {
				if (urlPattern.endsWith("/*")) {
					pos = urlPattern.indexOf("/*");

					urlPattern = urlPattern.substring(0, pos);

					if (pathNoQueryString.startsWith(urlPattern)) {
						pathInfo = pathNoQueryString.substring(
							urlPattern.length());
						servletPath = urlPattern;

						break;
					}
				}
			}

			if ((pathInfo == null) && (servletPath == null)) {
				pathInfo = StringPool.BLANK;
				servletPath = pathNoQueryString;
			}

			requestURI = portletRequest.getContextPath() + pathNoQueryString;
		}

		String lifecyclePhase =
			(String)portletRequest.getAttribute(PortletRequest.LIFECYCLE_PHASE);

		RDRequestWrapper rdRequestWrapper =
			new RDRequestWrapper(
			null, (HttpServletRequest)servletRequest, portletRequest,
			requestURI, servletPath, pathInfo, queryString,
			lifecyclePhase, false, include);

		RDResponseWrapper rdResponseWrapper =
			new RDResponseWrapper(
				(HttpServletResponse)servletResponse, portletResponse,
				lifecyclePhase, include);

		if (include) {
			_requestDispatcher.include(
				rdRequestWrapper, rdResponseWrapper);
		}
		else {
			_requestDispatcher.forward(
				rdRequestWrapper, rdResponseWrapper);
		}
	}

	private Set<String> _getServletURLPatterns(
			ServletRequest servletRequest, PortletResponse portletResponse)
		throws ServletException {

		try {
			String portletId = WindowInvokerUtil.getPortletId(
				portletResponse.getNamespace());

			long companyId = PortalUtil.getCompanyId(
				(HttpServletRequest)servletRequest);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, portletId);

			PortletApp portletApp = portlet.getPortletApp();
			return portletApp.getServletURLPatterns();

		}
		catch (SystemException se) {
			throw new ServletException(se);
		}
	}

	private RequestDispatcher _requestDispatcher;
	private String _path;

}