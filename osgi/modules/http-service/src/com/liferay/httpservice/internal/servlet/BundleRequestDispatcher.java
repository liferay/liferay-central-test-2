/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal.servlet;

import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicyManager;
import com.liferay.portal.util.ClassLoaderUtil;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class BundleRequestDispatcher implements RequestDispatcher {

	public BundleRequestDispatcher(
		String servletMapping, boolean extensionMapping, String requestURI,
		BundleServletContext bundleServletContext,
		BundleFilterChain bundleFilterChain) {

		_servletMapping = servletMapping;
		_extensionMapping = extensionMapping;
		_bundleFilterChain = bundleFilterChain;
		_bundleServletContext = bundleServletContext;

		_requestURI = StringUtil.replace(
			requestURI, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		_contextPath = _bundleServletContext.getContextPath();

		if (!_extensionMapping) {
			_servletPath = _servletMapping;
		}
		else {
			_servletPath = _requestURI;
		}

		if ((_servletPath != null) &&
			_requestURI.startsWith(_servletPath) &&
			(_requestURI.length() > _servletPath.length())) {

			_pathInfo = _requestURI.substring(_servletPath.length());
		}
	}

	public void doDispatch(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		ClassLoader pluginClassLoader = _bundleServletContext.getClassLoader();

		PACLPolicy paclPolicy =
			PortalSecurityManagerThreadLocal.getPACLPolicy();

		try {
			PACLPolicy pluginPACLPolicy = PACLPolicyManager.getPACLPolicy(
				pluginClassLoader);

			PortalSecurityManagerThreadLocal.setPACLPolicy(pluginPACLPolicy);

			ClassLoaderUtil.setContextClassLoader(pluginClassLoader);

			for (ServletRequestListener servletRequestListener :
					_bundleServletContext.getServletRequestListeners()) {

				servletRequestListener.requestInitialized(
					new ServletRequestEvent(_bundleServletContext, request));
			}

			_bundleFilterChain.doFilter(request, response);

			for (ServletRequestListener servletRequestListener :
					_bundleServletContext.getServletRequestListeners()) {

				servletRequestListener.requestDestroyed(
					new ServletRequestEvent(_bundleServletContext, request));
			}
		}
		finally {
			ClassLoaderUtil.setContextClassLoader(contextClassLoader);

			PortalSecurityManagerThreadLocal.setPACLPolicy(paclPolicy);
		}
	}

	public void forward(ServletRequest request, ServletResponse response)
		throws IOException, ServletException {

		BundleServletRequest bundleServletRequest = new BundleServletRequest(
			this, (HttpServletRequest)request);

		doDispatch(bundleServletRequest, response);
	}

	public void include(ServletRequest request, ServletResponse response)
		throws IOException, ServletException {

		BundleServletRequest bundleServletRequest = new BundleServletRequest(
				this, (HttpServletRequest)request);

		if (_contextPath != null) {
			bundleServletRequest.setAttribute(
				JavaConstants.JAVAX_SERVLET_FORWARD_CONTEXT_PATH, _contextPath);
		}

		if (_pathInfo != null) {
			bundleServletRequest.setAttribute(
				JavaConstants.JAVAX_SERVLET_INCLUDE_PATH_INFO, _pathInfo);
		}

		if (_queryString != null) {
			bundleServletRequest.setAttribute(
				JavaConstants.JAVAX_SERVLET_INCLUDE_QUERY_STRING, _queryString);
		}

		if (_requestURI != null) {
			bundleServletRequest.setAttribute(
				JavaConstants.JAVAX_SERVLET_ERROR_REQUEST_URI,
				_bundleServletContext.getContextPath().concat(_requestURI));
		}

		if (_servletPath != null) {
			bundleServletRequest.setAttribute(
				JavaConstants.JAVAX_SERVLET_FORWARD_SERVLET_PATH, _servletPath);
		}

		doDispatch(bundleServletRequest, response);
	}

	protected BundleServletContext getBundleServletContext() {
		return _bundleServletContext;
	}

	protected String getPathInfo() {
		return _pathInfo;
	}

	protected String getRequestURI() {
		return _requestURI;
	}

	protected String getServletPath() {
		return _servletPath;
	}

	private BundleFilterChain _bundleFilterChain;
	private BundleServletContext _bundleServletContext;
	private String _contextPath;
	private boolean _extensionMapping;
	private String _pathInfo;
	private String _queryString;
	private String _requestURI;
	private String _servletMapping;
	private String _servletPath;

}