/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.sample.php.portlet;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import java.util.Enumeration;
import java.util.HashMap;
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

	public static String PATH_INFO = "javax.servlet.include.path_info";
	public static String QUERY_STRING = "javax.servlet.include.query_string";
	public static String REQUEST_URI = "javax.servlet.include.request_uri";
	public static String SERVLET_PATH = "javax.servlet.include.servlet_path";

	public PHPServletRequest(
		HttpServletRequest req, RenderRequest renderReq,
		RenderResponse renderRes, PortletConfig portletConfig,
		ServletConfig config, String phpURI) {
		super(req);
		_renderReq = renderReq;
		_renderRes = renderRes;
		_portletConfig = portletConfig;
		_config = config;

		StringBuffer queryStringBuffer = new StringBuffer();

		int index = phpURI.indexOf(StringPool.QUESTION);

		if (index != -1) {
			_path = phpURI.substring(0, index);
			queryStringBuffer.append(phpURI.substring(index + 1));
		}
		else {
			_path = phpURI;
		}

		if (GetterUtil.getBoolean(
			portletConfig.getInitParameter("add-portlet-params"), true)) {
			queryStringBuffer.append(StringPool.AMPERSAND);
			queryStringBuffer.append("portlet_namespace");
			queryStringBuffer.append(StringPool.EQUAL);
			queryStringBuffer.append(_renderRes.getNamespace());
			queryStringBuffer.append(StringPool.AMPERSAND);
			queryStringBuffer.append("portlet_name");
			queryStringBuffer.append(StringPool.EQUAL);
			queryStringBuffer.append(_portletConfig.getPortletName());
		}

		_queryString = queryStringBuffer.toString();

		req.setAttribute(QUERY_STRING, getQueryString());
		req.setAttribute(PATH_INFO, getPathInfo());
		req.setAttribute(REQUEST_URI, getRequestURI());
		req.setAttribute(SERVLET_PATH, _path);
	}

	public String getRealPath(String path) {
		return _config.getServletContext().getRealPath(path);
	}

	public String getParameter(String name) {
		return _renderReq.getParameter(name);
	}

	public Map getParameterMap() {
		return _renderReq.getParameterMap();
	}

	public Enumeration getParameterNames() {
		return _renderReq.getParameterNames();
	}

	public String[] getParameterValues(String name) {
		return _renderReq.getParameterValues(name);
	}

	protected Map parseQueryString(String queryString) {
		Map params = new HashMap();

		if (Validator.isNull(queryString)) {
			return params;
		}

		int ampersandIndex;
		do {
			ampersandIndex = queryString.indexOf(StringPool.AMPERSAND);

			String nameValuePair;

			if (ampersandIndex == -1) {
				nameValuePair = queryString;
			}
			else {
				nameValuePair = queryString.substring(0, ampersandIndex);
				queryString = queryString.substring(ampersandIndex + 1);
			}

			int equalIndex = nameValuePair.indexOf(StringPool.EQUAL);

			String key;
			String value;

			if (equalIndex == -1) {
				key = nameValuePair;
				value = StringPool.BLANK;
			}
			else {
				key = nameValuePair.substring(0, equalIndex);
				value = nameValuePair.substring(equalIndex + 1);
			}

			params.put(key, value);

		}
		while (ampersandIndex != -1);

		return params;
	}

	public long getDateHeader(String string) {
		return super.getDateHeader(string);
	}

	public String getPathInfo() {
		return StringPool.BLANK;
	}

	public String getPathTranslated() {
		return StringPool.BLANK;
	}

	public String getContextPath() {
		return StringPool.SLASH;
	}

	public String getQueryString() {
		return _queryString;
	}

	public String getRequestURI() {
		return _path + StringPool.QUESTION + _queryString;
	}

	public StringBuffer getRequestURL() {
		return new StringBuffer(
			getRequest().getProtocol() + "://" + getRequest().getRemoteHost()
				+ ":" + getRequest().getServerPort() + "/" + getRequestURI());
	}

	public String getServletPath() {
		return _path;
	}

	private String _queryString;
	private String _path;

	private PortletConfig _portletConfig;
	private RenderRequest _renderReq;
	private RenderResponse _renderRes;
	private ServletConfig _config;
}