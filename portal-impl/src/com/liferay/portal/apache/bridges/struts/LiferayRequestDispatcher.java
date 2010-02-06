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

import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portlet.PortletRequestImpl;
import com.liferay.portlet.PortletResponseImpl;
import com.liferay.portlet.PortletServletRequest;
import com.liferay.portlet.PortletServletResponse;

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
 * <a href="LiferayRequestDispatcher.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 * @author Brian Myunghun Kim
 * @author Brian Wing Shun Chan
 * @author Deepak Gothe
 */
public class LiferayRequestDispatcher implements RequestDispatcher {

	public LiferayRequestDispatcher(
		RequestDispatcher requestDispatcher, String path) {

		_requestDispatcher = requestDispatcher;
		_path = path;
	}

	public void forward(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		PortletRequest portletRequest =
			(PortletRequest)servletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest != null) {
			invoke(servletRequest, servletResponse, false);
		}
		else {
			_requestDispatcher.forward(servletRequest, servletResponse);
		}
	}

	public void include(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		PortletRequest portletRequest =
			(PortletRequest)servletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest != null) {
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

			Set<String> servletURLPatterns = getServletURLPatterns(
				servletRequest, portletRequest, portletResponse);

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

		HttpServletRequest portletServletRequest = getPortletServletRequest(
			servletRequest, portletRequest, pathInfo, queryString, requestURI,
			servletPath, include);

		HttpServletResponse portletServletResponse =
			getPortletServletResponse(
				servletResponse, portletRequest, portletResponse, include);

		if (include) {
			_requestDispatcher.include(
				portletServletRequest, portletServletResponse);
		}
		else {
			_requestDispatcher.forward(
				portletServletRequest, portletServletResponse);
		}
	}

	protected HttpServletRequest getPortletServletRequest(
		ServletRequest servletRequest, PortletRequest portletRequest,
		String pathInfo, String queryString, String requestURI,
		String servletPath, boolean include) {

		HttpServletRequest request = (HttpServletRequest)servletRequest;
		boolean named = false;

		PortletRequestImpl portletRequestImpl =
			(PortletRequestImpl)portletRequest;

		return new PortletServletRequest(
			request, portletRequestImpl, pathInfo, queryString, requestURI,
			servletPath, named, include);
	}

	protected HttpServletResponse getPortletServletResponse(
		ServletResponse servletResponse, PortletRequest portletRequest,
		PortletResponse portletResponse, boolean include) {

		HttpServletResponse response = (HttpServletResponse)servletResponse;

		PortletResponseImpl portletResponseImpl =
			(PortletResponseImpl)portletResponse;

		return new PortletServletResponse(
			response, portletResponseImpl, include);
	}

	protected Set<String> getServletURLPatterns(
		ServletRequest servletRequest, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		PortletRequestImpl portletRequestImpl =
			(PortletRequestImpl)portletRequest;

		Portlet portlet = portletRequestImpl.getPortlet();

		PortletApp portletApp = portlet.getPortletApp();

		return portletApp.getServletURLPatterns();
	}

	private RequestDispatcher _requestDispatcher;
	private String _path;

}