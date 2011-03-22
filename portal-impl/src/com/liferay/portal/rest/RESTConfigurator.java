/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.rest;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.rest.REST;
import com.liferay.portal.kernel.rest.RESTActionsManager;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.File;
import java.io.InputStream;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.net.MalformedURLException;
import java.net.URL;

import jodd.io.findfile.FindClass;

import jodd.util.ClassLoaderUtil;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Igor Spasic
 */
public class RESTConfigurator extends FindClass {

	public RESTConfigurator() {
		setIncludedJars("*portal-impl.jar", "*_wl_cls_gen.jar");
	}

	public void configure(ClassLoader classLoader) throws PortalException {
		URL[] classPathURLs = null;

		if (classLoader != null) {
			URL servicePropertiesURL = classLoader.getResource(
				"service.properties");

			String servicePropertiesPath = servicePropertiesURL.getPath();

			File classPathFile = null;

			int pos = servicePropertiesPath.indexOf("_wl_cls_gen.jar!");

			if (pos != -1) {
				String wlClsGenJarPath =
					servicePropertiesPath.substring(0, pos + 15);

				classPathFile = new File(wlClsGenJarPath);
			}
			else {
				File servicePropertiesFile = new File(servicePropertiesPath);

				File webInfDir = servicePropertiesFile.getParentFile();

				classPathFile = webInfDir;
			}

			classPathURLs = new URL[1];

			try {
				classPathURLs[0] = classPathFile.toURL();
			}
			catch (MalformedURLException murle) {
				_log.error(murle, murle);
			}
		}
		else {
			Thread currentThread = Thread.currentThread();

			classLoader = currentThread.getContextClassLoader();

			File portalImplJarFile = new File(
				PortalUtil.getPortalLibDir(), "portal-impl.jar");

			if (portalImplJarFile.exists()) {
				classPathURLs = new URL[1];

				try {
					classPathURLs[0] = portalImplJarFile.toURL();
				}
				catch (MalformedURLException murle) {
					_log.error(murle, murle);
				}
			}
			else {
				classPathURLs = ClassLoaderUtil.getFullClassPath(classLoader);
			}
		}

		_classLoader = classLoader;

		configure(classPathURLs);
	}

	public void configure(URL... classPathURLs) throws PortalException {
		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			_log.debug("Configure REST actions");

			stopWatch = new StopWatch();

			stopWatch.start();
		}

		try {
			scanUrls(classPathURLs);
		}
		catch (Exception e) {
			throw new PortalException(e.getMessage(), e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Configuring " + _registeredActionsCount +
					" actions in completed " + stopWatch.getTime() + " ms");
		}
	}

	public void setCheckBytecodeSignature(boolean checkBytecodeSignature) {
		_checkBytecodeSignature = checkBytecodeSignature;
	}

	public void setRESTActionsManager(RESTActionsManager restActionsManager) {
		_restActionsManager = restActionsManager;
	}

	protected void onEntry(EntryData entryData) throws Exception {
		String className = entryData.getName();

		if (className.endsWith("Impl")) {
			if (_checkBytecodeSignature) {
				InputStream inputStream = entryData.openInputStream();

				if (!isTypeSignatureInUse(inputStream, _restAnnotationBytes)) {
					return;
				}
			}

			_onRESTClass(className);
		}
	}

	private boolean _isRESTClass(Class<?> clazz) {
		if (!clazz.isAnonymousClass() && !clazz.isArray() && !clazz.isEnum() &&
			!clazz.isInterface() && !clazz.isLocalClass() &&
			!clazz.isPrimitive() &&
			!(clazz.isMemberClass() ^
				Modifier.isStatic(clazz.getModifiers()))) {

			return true;
		}

		return false;
	}

	private void _onRESTClass(String className) throws Exception {
		Class<?> actionClass = _classLoader.loadClass(className);

		if (!_isRESTClass(actionClass)) {
			return;
		}

		Method[] methods = actionClass.getMethods();

		for (Method method : methods) {
			REST restAnnotation = method.getAnnotation(REST.class);

			if (restAnnotation == null) {
				continue;
			}

			_registerRESTAction(actionClass, method);
		}
	}
	private void _registerRESTAction(
			Class<?> implementationClass, Method method)
		throws Exception {

		RESTMappingResolver restMappingResolver =
			new RESTDefaultMappingResolver();

		String path = restMappingResolver.resolvePath(
			implementationClass, method);

		String httpMethod = restMappingResolver.resolveHttpMethod(method);

		String utilClassName = implementationClass.getName();

		if (utilClassName.endsWith("Impl")) {
			utilClassName = utilClassName.substring(
				0, utilClassName.length() - 4);

			utilClassName += "Util";
		}

		utilClassName = StringUtil.replace(utilClassName, ".impl.", ".");

		Class<?> utilClass = _classLoader.loadClass(utilClassName);

		method = utilClass.getMethod(
			method.getName(), method.getParameterTypes());

		_restActionsManager.registerRESTAction(
			method.getDeclaringClass(), method, path, httpMethod);

		_registeredActionsCount++;
	}

	private static Log _log = LogFactoryUtil.getLog(RESTConfigurator.class);

	private boolean _checkBytecodeSignature = true;
	private ClassLoader _classLoader;
	private int _registeredActionsCount;
	private RESTActionsManager _restActionsManager;
	private byte[] _restAnnotationBytes = getTypeSignatureBytes(REST.class);

}