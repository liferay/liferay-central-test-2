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

package com.liferay.util.bridges.php;

import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <a href="PHPServletRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class PHPServletRequest extends HttpServletRequestWrapper {

	public PHPServletRequest(
		HttpServletRequest request, ServletConfig servletConfig,
		RenderRequest renderRequest, RenderResponse renderResponse,
		PortletConfig portletConfig, String phpURI, boolean addPortletParams) {

		super(request);

		_servletConfig = servletConfig;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_portletConfig = portletConfig;

		StringBundler sb = new StringBundler();

		int pos = phpURI.indexOf(StringPool.QUESTION);

		if (pos != -1) {
			_path = phpURI.substring(0, pos);

			sb.append(phpURI.substring(pos + 1));
		}
		else {
			_path = phpURI;
		}

		if (addPortletParams) {
			sb.append(StringPool.AMPERSAND);
			sb.append("portlet_namespace");
			sb.append(StringPool.EQUAL);
			sb.append(_renderResponse.getNamespace());
			sb.append(StringPool.AMPERSAND);
			sb.append("portlet_name");
			sb.append(StringPool.EQUAL);
			sb.append(_portletConfig.getPortletName());
		}

		_queryString = sb.toString();

		request.setAttribute(
			JavaConstants.JAVAX_SERVLET_INCLUDE_QUERY_STRING, getQueryString());
		request.setAttribute(
			JavaConstants.JAVAX_SERVLET_INCLUDE_PATH_INFO, getPathInfo());
		request.setAttribute(
			JavaConstants.JAVAX_SERVLET_INCLUDE_REQUEST_URI, getRequestURI());
		request.setAttribute(
			JavaConstants.JAVAX_SERVLET_INCLUDE_SERVLET_PATH, _path);
	}

	public String getContextPath() {
		return StringPool.SLASH;
	}

	public String getParameter(String name) {
		return _renderRequest.getParameter(name);
	}

	public Map<String, String[]> getParameterMap() {
		return _renderRequest.getParameterMap();
	}

	public Enumeration<String> getParameterNames() {
		return _renderRequest.getParameterNames();
	}

	public String[] getParameterValues(String name) {
		return _renderRequest.getParameterValues(name);
	}

	public String getPathInfo() {
		return StringPool.BLANK;
	}

	public String getPathTranslated() {
		return StringPool.BLANK;
	}

	public String getQueryString() {
		return _queryString;
	}

	public String getRealPath(String path) {
		return _servletConfig.getServletContext().getRealPath(path);
	}

	public String getRequestURI() {
		return _path + StringPool.QUESTION + _queryString;
	}

	public StringBuffer getRequestURL() {
		StringBuffer sb = new StringBuffer();

		sb.append(getRequest().getProtocol());
		sb.append("://");
		sb.append(getRequest().getRemoteHost());
		sb.append(StringPool.COLON);
		sb.append(getRequest().getServerPort());
		sb.append(StringPool.SLASH);
		sb.append(getRequestURI());

		return sb;
	}

	public String getServletPath() {
		return _path;
	}

	private ServletConfig _servletConfig;
	private PortletConfig _portletConfig;
	private RenderRequest _renderRequest;
	private RenderResponse _renderResponse;
	private String _queryString;
	private String _path;

}