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

package com.liferay.portlet;

import com.liferay.portal.kernel.servlet.ProtectedPrincipal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.Principal;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortletServletRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 *
 */
public class PortletServletRequest extends HttpServletRequestWrapper {

	public PortletServletRequest(
		HttpServletRequest req, PortletRequestImpl portletReq, String pathInfo,
		String queryString, String requestURI, String servletPath,
		boolean named, boolean include) {

		super(req);

		_req = req;
		_portletReq = portletReq;
		_lifecycle = _portletReq.getLifecycle();
		_pathInfo = GetterUtil.getString(pathInfo);
		_queryString = GetterUtil.getString(queryString);
		_requestURI = GetterUtil.getString(requestURI);
		_servletPath = GetterUtil.getString(servletPath);
		_named = named;
		_include = include;

		long userId = PortalUtil.getUserId(req);
		String remoteUser = req.getRemoteUser();

		Portlet portlet = portletReq.getPortlet();

		String userPrincipalStrategy = portlet.getUserPrincipalStrategy();

		if (userPrincipalStrategy.equals(
				PortletConstants.USER_PRINCIPAL_STRATEGY_SCREEN_NAME)) {

			try {
				User user = PortalUtil.getUser(req);

				_remoteUser = user.getScreenName();
				_remoteUserId = user.getUserId();
				_userPrincipal = new ProtectedPrincipal(_remoteUser);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
		else {
			if ((userId > 0) && (remoteUser == null)) {
				_remoteUser = String.valueOf(userId);
				_remoteUserId = userId;
				_userPrincipal = new ProtectedPrincipal(_remoteUser);
			}
			else {
				_remoteUser = remoteUser;
				_remoteUserId = GetterUtil.getLong(remoteUser);
				_userPrincipal = req.getUserPrincipal();
			}
		}
	}

	public Object getAttribute(String name) {
		if (_include || (name == null)) {
			return _req.getAttribute(name);
		}

		if (name.equals(JavaConstants.JAVAX_SERVLET_FORWARD_CONTEXT_PATH)) {
			if (_named) {
				return null;
			}
			else {
				return _portletReq.getContextPath();
			}
		}

		if (name.equals(JavaConstants.JAVAX_SERVLET_FORWARD_PATH_INFO)) {
			if (_named) {
				return null;
			}
			else {
				return _pathInfo;
			}
		}

		if (name.equals(JavaConstants.JAVAX_SERVLET_FORWARD_QUERY_STRING)) {
			if (_named) {
				return null;
			}
			else {
				return _queryString;
			}
		}

		if (name.equals(JavaConstants.JAVAX_SERVLET_FORWARD_REQUEST_URI)) {
			if (_named) {
				return null;
			}
			else {
				return _requestURI;
			}
		}

		if (name.equals(JavaConstants.JAVAX_SERVLET_FORWARD_SERVLET_PATH)) {
			if (_named) {
				return null;
			}
			else {
				return _servletPath;
			}
		}

		return _req.getAttribute(name);
	}

	public Enumeration<String> getAttributeNames() {
		return _req.getAttributeNames();
	}

	public String getAuthType() {
		return _req.getAuthType();
	}

	public String getCharacterEncoding() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _req.getCharacterEncoding();
		}
		else {
			return null;
		}
	}

	public int getContentLength() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _req.getContentLength();
		}
		else {
			return 0;
		}
	}

	public String getContentType() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _req.getContentType();
		}
		else {
			return null;
		}
	}

	public String getContextPath() {
		return _portletReq.getContextPath();
	}

	public Cookie[] getCookies() {
		return _req.getCookies();
	}

	public long getDateHeader(String name) {
		return GetterUtil.getLong(getHeader(name));
	}

	public String getHeader(String name) {
		return _portletReq.getProperty("header." + name);
	}

	public Enumeration<String> getHeaderNames() {
		return _portletReq.getProperties("header.");
	}

	public Enumeration<String> getHeaders(String name) {
		return _portletReq.getProperties("header." + name);
	}

	public ServletInputStream getInputStream() throws IOException {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _req.getInputStream();
		}
		else {
			return null;
		}
	}

	public int getIntHeader(String name) {
		return GetterUtil.getInteger(getHeader(name));
	}

	public String getLocalAddr() {
		return null;
	}

	public Locale getLocale() {
		return _portletReq.getLocale();
	}

	public Enumeration<Locale> getLocales() {
		return _req.getLocales();
	}

	public String getLocalName() {
		return null;
	}

	public int getLocalPort() {
		return 0;
	}

	public String getMethod() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			return "GET";
		}
		else {
			return _req.getMethod();
		}
	}

	public String getParameter(String name) {
		return _req.getParameter(name);
	}

	public Map<String, String[]> getParameterMap() {
		return _req.getParameterMap();
	}

	public Enumeration<String> getParameterNames() {
		return _req.getParameterNames();
	}

	public String[] getParameterValues(String name) {
		return _req.getParameterValues(name);
	}

	public String getPathInfo() {
		return _pathInfo;
	}

	public String getPathTranslated() {
		return _req.getPathTranslated();
	}

	public String getProtocol() {
		return "HTTP/1.1";
	}

	public String getQueryString() {
		return _queryString;
	}

	public BufferedReader getReader() throws IOException {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _req.getReader();
		}
		else {
			return null;
		}
	}

	public String getRealPath(String path) {
		return null;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return _req.getRequestDispatcher(path);
	}

	public String getRequestedSessionId() {
		return _req.getRequestedSessionId();
	}

	public String getRemoteAddr() {
		return null;
	}

	public String getRemoteHost() {
		return null;
	}

	public int getRemotePort() {
		return 0;
	}

	public String getRequestURI() {
		return _requestURI;
	}

	public StringBuffer getRequestURL() {
		return null;
	}

	public String getRemoteUser() {
		return _remoteUser;
	}

	public String getScheme() {
		return _req.getScheme();
	}

	public String getServerName() {
		return _req.getServerName();
	}

	public int getServerPort() {
		return _req.getServerPort();
	}

	public String getServletPath() {
		return _servletPath;
	}

	public HttpSession getSession() {
		return new PortletServletSession(_req.getSession(), _portletReq);
	}

	public HttpSession getSession(boolean create) {
		return new PortletServletSession(_req.getSession(create), _portletReq);
	}

	public Principal getUserPrincipal() {
		return _userPrincipal;
	}

	public boolean isRequestedSessionIdFromCookie() {
		return _req.isRequestedSessionIdFromCookie();
	}

	public boolean isRequestedSessionIdFromURL() {
		return _req.isRequestedSessionIdFromURL();
	}

	/**
	 * @deprecated
	 */
	public boolean isRequestedSessionIdFromUrl() {
		return _req.isRequestedSessionIdFromUrl();
	}

	public boolean isRequestedSessionIdValid() {
		return _req.isRequestedSessionIdValid();
	}

	public boolean isSecure() {
		return _req.isSecure();
	}

	public boolean isUserInRole(String role) {
		if (_remoteUserId <= 0) {
			return false;
		}
		else {
			try {
				long companyId = PortalUtil.getCompanyId(_req);

				return RoleLocalServiceUtil.hasUserRole(
					_remoteUserId, companyId, role, true);
			}
			catch (Exception e) {
				_log.error(e);
			}

			return _req.isUserInRole(role);
		}
	}

	public void removeAttribute(String name) {
		_req.removeAttribute(name);
	}

	public void setAttribute(String name, Object obj) {
		_req.setAttribute(name, obj);
	}

	public void setCharacterEncoding(String encoding)
		throws UnsupportedEncodingException {

		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			_req.setCharacterEncoding(encoding);
		}
	}

	private static Log _log = LogFactory.getLog(PortletServletRequest.class);

	private HttpServletRequest _req;
	private PortletRequestImpl _portletReq;
	private String _lifecycle;
	private String _pathInfo;
	private String _queryString;
	private String _remoteUser;
	private long _remoteUserId;
	private String _requestURI;
	private String _servletPath;
	private Principal _userPrincipal;
	private boolean _named;
	private boolean _include;

}