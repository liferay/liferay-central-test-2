/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.portlet.tracker.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;

/**
 * @author Raymond Augé
 */
public class ServletContextBuilder implements InvocationHandler {

	public ServletContextBuilder(
		ServletContext servletContext, Bundle bundle, ClassLoader classLoader) {

		_servletContext = servletContext;
		_bundle = bundle;
		_classLoader = classLoader;
	}

	public ServletContext build() {
		return (ServletContext)ProxyUtil.newProxyInstance(
			_classLoader, new Class<?>[] {ServletContext.class}, this);
	}

	public Object getAttribute(String name) {
		if (PluginContextListener.PLUGIN_CLASS_LOADER.equals(name)) {
			return _classLoader;
		}

		return _servletContext.getAttribute(name);
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return _servletContext.getRequestDispatcher(path);
	}

	public URL getResource(String name) {
		if (name == null) {
			return null;
		}

		if (name.startsWith(StringPool.SLASH)) {
			name = name.substring(1);
		}

		return _bundle.getResource(name);
	}

	public InputStream getResourceAsStream(String path) {
		try {
			URL url = getResource(path);

			if (url == null) {
				return null;
			}

			return url.openStream();
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		return null;
	}

	public Set<String> getResourcePaths(String path) {
		if (path == null) {
			return null;
		}

		final Enumeration<URL> enumeration = _bundle.findEntries(
			path, null, false);

		if (enumeration == null) {
			return null;
		}

		final Set<String> result = new HashSet<String>();

		while (enumeration.hasMoreElements()) {
			URL nextURL = enumeration.nextElement();

			result.add(nextURL.toExternalForm());
		}

		return result;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		Method mappendMethod = _methods.get(method);

		if (mappendMethod != null) {
			return mappendMethod.invoke(this, args);
		}

		return method.invoke(_servletContext, args);
	}

	private static Map<Method, Method> getMethodsMap() {
		Map<Method, Method> methods = new HashMap<Method, Method>();

		for (Method wrapperMethod :
				ServletContextBuilder.class.getDeclaredMethods()) {

			String name = wrapperMethod.getName();

			Class<?>[] parameterTypes = wrapperMethod.getParameterTypes();

			try {
				Method method = ServletContext.class.getMethod(
					name, parameterTypes);

				methods.put(method, wrapperMethod);
			}
			catch (NoSuchMethodException e) {

				// ignore

			}
		}

		return methods;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ServletContextBuilder.class);
	private static Map<Method, Method> _methods;

	static {
		_methods = getMethodsMap();
	}

	private Bundle _bundle;
	private ClassLoader _classLoader;
	private ServletContext _servletContext;

}