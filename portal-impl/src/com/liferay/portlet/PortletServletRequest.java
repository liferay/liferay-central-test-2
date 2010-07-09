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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.ProtectedPrincipal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.lang.reflect.Constructor;

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

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 */
public class PortletServletRequest extends HttpServletRequestWrapper {

	public PortletServletRequest(
		HttpServletRequest request, PortletRequestImpl portletRequestImpl,
		String pathInfo, String queryString, String requestURI,
		String servletPath, boolean named, boolean include) {

		super(request);

		_request = request;
		_portletRequestImpl = portletRequestImpl;
		_lifecycle = _portletRequestImpl.getLifecycle();
		_pathInfo = pathInfo;
		_queryString = queryString;
		_requestURI = GetterUtil.getString(requestURI);
		_servletPath = GetterUtil.getString(servletPath);
		_named = named;
		_include = include;

		long userId = PortalUtil.getUserId(request);
		String remoteUser = request.getRemoteUser();

		Portlet portlet = portletRequestImpl.getPortlet();

		String userPrincipalStrategy = portlet.getUserPrincipalStrategy();

		if (userPrincipalStrategy.equals(
				PortletConstants.USER_PRINCIPAL_STRATEGY_SCREEN_NAME)) {

			try {
				User user = PortalUtil.getUser(request);

				if (user != null) {
					_remoteUser = user.getScreenName();
					_userPrincipal = new ProtectedPrincipal(_remoteUser);
				}
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
		else {
			if ((userId > 0) && (remoteUser == null)) {
				_remoteUser = String.valueOf(userId);
				_userPrincipal = new ProtectedPrincipal(_remoteUser);
			}
			else {
				_remoteUser = remoteUser;
				_userPrincipal = request.getUserPrincipal();
			}
		}
	}

	public Object getAttribute(String name) {
		if (_include || (name == null)) {
			return _request.getAttribute(name);
		}

		if (name.equals(JavaConstants.JAVAX_SERVLET_FORWARD_CONTEXT_PATH)) {
			if (_named) {
				return null;
			}
			else {
				return _portletRequestImpl.getContextPath();
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

		return _request.getAttribute(name);
	}

	public Enumeration<String> getAttributeNames() {
		return _request.getAttributeNames();
	}

	public String getAuthType() {
		return _request.getAuthType();
	}

	public String getCharacterEncoding() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _request.getCharacterEncoding();
		}
		else {
			return null;
		}
	}

	public int getContentLength() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _request.getContentLength();
		}
		else {
			return 0;
		}
	}

	public String getContentType() {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _request.getContentType();
		}
		else {
			return null;
		}
	}

	public String getContextPath() {
		return _portletRequestImpl.getContextPath();
	}

	public Cookie[] getCookies() {
		return _request.getCookies();
	}

	public long getDateHeader(String name) {
		return GetterUtil.getLong(getHeader(name), -1);
	}

	public String getHeader(String name) {
		HttpServletRequest request =
			_portletRequestImpl.getHttpServletRequest();

		return request.getHeader(name);
	}

	public Enumeration<String> getHeaderNames() {
		HttpServletRequest request =
			_portletRequestImpl.getHttpServletRequest();

		return request.getHeaderNames();
	}

	public Enumeration<String> getHeaders(String name) {
		HttpServletRequest request =
			_portletRequestImpl.getHttpServletRequest();

		return request.getHeaders(name);
	}

	public ServletInputStream getInputStream() throws IOException {
		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			return _request.getInputStream();
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
		return _portletRequestImpl.getLocale();
	}

	public Enumeration<Locale> getLocales() {
		return _request.getLocales();
	}

	public String getLocalName() {
		return null;
	}

	public int getLocalPort() {
		return 0;
	}

	public String getMethod() {
		if (_lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			return HttpMethods.GET;
		}
		else {
			return _request.getMethod();
		}
	}

	public String getParameter(String name) {
		return _request.getParameter(name);
	}

	public Map<String, String[]> getParameterMap() {
		return _request.getParameterMap();
	}

	public Enumeration<String> getParameterNames() {
		return _request.getParameterNames();
	}

	public String[] getParameterValues(String name) {
		return _request.getParameterValues(name);
	}

	public String getPathInfo() {
		return _pathInfo;
	}

	public String getPathTranslated() {
		return _request.getPathTranslated();
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

			return _request.getReader();
		}
		else {
			return null;
		}
	}

	public String getRealPath(String path) {
		return null;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return _request.getRequestDispatcher(path);
	}

	public String getRequestedSessionId() {
		return _request.getRequestedSessionId();
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
		return _request.getScheme();
	}

	public String getServerName() {
		return _request.getServerName();
	}

	public int getServerPort() {
		return _request.getServerPort();
	}

	public String getServletPath() {
		return _servletPath;
	}

	public HttpSession getSession() {
		HttpSession session = new PortletServletSession(
			_request.getSession(), _portletRequestImpl);

		if (ServerDetector.isJetty()) {
			try {
				session = wrapJettySession(session);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return session;
	}

	public HttpSession getSession(boolean create) {
		HttpSession session = new PortletServletSession(
			_request.getSession(create), _portletRequestImpl);

		if (ServerDetector.isJetty()) {
			try {
				session = wrapJettySession(session);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return session;
	}

	public Principal getUserPrincipal() {
		return _userPrincipal;
	}

	public boolean isRequestedSessionIdFromCookie() {
		return _request.isRequestedSessionIdFromCookie();
	}

	public boolean isRequestedSessionIdFromURL() {
		return _request.isRequestedSessionIdFromURL();
	}

	/**
	 * @deprecated
	 */
	public boolean isRequestedSessionIdFromUrl() {
		return _request.isRequestedSessionIdFromUrl();
	}

	public boolean isRequestedSessionIdValid() {
		return _request.isRequestedSessionIdValid();
	}

	public boolean isSecure() {
		return _request.isSecure();
	}

	public boolean isUserInRole(String role) {
		return _portletRequestImpl.isUserInRole(role);
	}

	public void removeAttribute(String name) {
		_request.removeAttribute(name);
	}

	public void setAttribute(String name, Object obj) {
		_request.setAttribute(name, obj);
	}

	public void setCharacterEncoding(String encoding)
		throws UnsupportedEncodingException {

		if (_lifecycle.equals(PortletRequest.ACTION_PHASE) ||
			_lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			_request.setCharacterEncoding(encoding);
		}
	}

	protected HttpSession wrapJettySession(HttpSession session)
		throws Exception {

		// This must be called through reflection because Resin tries to load
		// org/mortbay/jetty/servlet/AbstractSessionManager$SessionIf

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		Class<?> jettyHttpSessionWrapperClass = classLoader.loadClass(
			"com.liferay.util.servlet.JettyHttpSessionWrapper");

		Constructor<?> constructor =
			jettyHttpSessionWrapperClass.getConstructor(
				new Class[] {HttpSession.class});

		return(HttpSession)constructor.newInstance(new Object[] {session});
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletServletRequest.class);

	private HttpServletRequest _request;
	private PortletRequestImpl _portletRequestImpl;
	private String _lifecycle;
	private String _pathInfo;
	private String _queryString;
	private String _remoteUser;
	private String _requestURI;
	private String _servletPath;
	private Principal _userPrincipal;
	private boolean _named;
	private boolean _include;

}