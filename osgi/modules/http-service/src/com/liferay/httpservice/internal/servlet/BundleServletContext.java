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

import com.liferay.httpservice.servlet.BundleServletConfig;
import com.liferay.portal.apache.bridges.struts.LiferayServletContext;
import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;

import org.osgi.framework.Bundle;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.NamespaceException;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class BundleServletContext extends LiferayServletContext {

	public static String getServletContextName(Bundle bundle) {
		return getServletContextName(bundle, false);
	}

	public static String getServletContextName(
		Bundle bundle, boolean generate) {

		Dictionary<String, String> headers = bundle.getHeaders();

		String webContextPath = headers.get("Web-ContextPath");

		if (Validator.isNotNull(webContextPath)) {
			return webContextPath.substring(1);
		}

		String deploymentContext = null;

		try {
			String pluginPackageXml = HttpUtil.URLtoString(
				bundle.getResource("/WEB-INF/liferay-plugin-package.xml"));

			if (pluginPackageXml != null) {
				Document document = SAXReaderUtil.read(pluginPackageXml);

				Element rootElement = document.getRootElement();

				deploymentContext = GetterUtil.getString(
					rootElement.elementText("recommended-deployment-context"));
			}
			else {
				String pluginPackageProperties = HttpUtil.URLtoString(
					bundle.getResource(
						"/WEB-INF/liferay-plugin-package.properties"));

				if (pluginPackageProperties != null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Reading plugin package from " +
								"liferay-plugin-package.properties");
					}

					Properties properties = PropertiesUtil.load(
						pluginPackageProperties);

					deploymentContext = GetterUtil.getString(
						properties.getProperty(
							"recommended-deployment-context"),
						deploymentContext);
				}
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		if (Validator.isNull(deploymentContext) && generate) {
			deploymentContext = PortalUtil.getJsSafePortletId(
				bundle.getSymbolicName());
		}

		if (Validator.isNotNull(deploymentContext) &&
			deploymentContext.startsWith(StringPool.SLASH)) {

			deploymentContext = deploymentContext.substring(1);
		}

		return deploymentContext;
	}

	public BundleServletContext(
		Bundle bundle, String servletContextName,
		ServletContext servletContext) {

		super(servletContext);

		_bundle = bundle;
		_servletContextName = servletContextName;
	}

	public void close() {
	}

	public Bundle getBundle() {
		return _bundle;
	}

	public ClassLoader getClassLoader() {
		return null;
	}

	public HttpContext getHttpContext() {
		return null;
	}

	@Override
	public String getServletContextName() {
		return _servletContextName;
	}

	public List<ServletRequestAttributeListener>
		getServletRequestAttributeListeners() {

		return null;
	}

	public List<ServletRequestListener> getServletRequestListeners() {
		return null;
	}

	public void open() {
	}

	public void registerFilter(
		String filterName, List<String> urlPatterns, Filter filter,
		Dictionary<String, String> initParameters, HttpContext httpContext) {
	}

	public void registerFilter(
		String filterName, String urlPattern, Filter filter,
		Dictionary<String, String> initParameters, HttpContext httpContext) {

		List<String> urlPatterns = Arrays.asList(urlPattern);

		registerFilter(
			filterName, urlPatterns, filter, initParameters, httpContext);
	}

	public void registerServlet(
			String servletName, List<String> urlPatterns, Servlet servlet,
			Dictionary<String, String> initParameters, HttpContext httpContext)
		throws NamespaceException, ServletException {

		validateServlet(servletName, servlet, urlPatterns, httpContext);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(getClassLoader());

			ServletConfig servletConfig = new BundleServletConfig(
				this, servletName, initParameters, httpContext);

			servlet.init(servletConfig);

			_servletNames.add(servletName);

			for (String urlPattern : urlPatterns) {
				_servlets.put(urlPattern, servlet);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Registered servlet at " + getContextPath() +
							urlPattern);
				}
			}
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void registerServlet(
			String servletName, String urlPattern, Servlet servlet,
			Dictionary<String, String> initParameters, HttpContext httpContext)
		throws NamespaceException, ServletException {

		List<String> urlPatterns = Arrays.asList(urlPattern);

		registerServlet(
			servletName, urlPatterns, servlet, initParameters, httpContext);
	}

	public void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;
	}

	public void unregisterFilter(String filterName) {
	}

	public void unregisterServlet(String servletName) {
	}

	protected void validateServlet(
			String servletName, Servlet servlet, List<String> urlPatterns,
			HttpContext httpContext)
		throws NamespaceException {

		if (_servletNames.contains(servletName)) {
			throw new NamespaceException(
				"A servlet is already registered with the name " + servletName);
		}

		for (String urlPattern : urlPatterns) {
			if (Validator.isNull(urlPattern)) {
				throw new IllegalArgumentException(
					"An empty URL pattern is not allowed");
			}

			if (!urlPattern.startsWith(StringPool.SLASH) ||
				(urlPattern.endsWith(StringPool.SLASH) &&
				 !urlPattern.equals(StringPool.SLASH))) {

				throw new IllegalArgumentException(
					"URL patterns must start with / but cannot end with it");
			}

			if (_servlets.containsKey(urlPattern)) {
				throw new NamespaceException(
					"A servlet is already registered with the URL pattern " +
						urlPattern);
			}
		}

		if (servlet == null) {
			throw new IllegalArgumentException("Servlet must not be null");
		}

		if (_servlets.containsValue(servlet)) {
			throw new IllegalArgumentException("Servlet is already registered");
		}

		if (httpContext == null) {
			throw new IllegalArgumentException("HTTP context cannot be null");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BundleServletContext.class);

	private Bundle _bundle;
	private String _servletContextName;
	private Set<String> _servletNames = new ConcurrentHashSet<String>();
	private Map<String, Servlet> _servlets =
		new ConcurrentHashMap<String, Servlet>();

}