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
import com.liferay.portal.kernel.rest.RestActionsManager;
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
public class RestConfigurator extends FindClass {

	public RestConfigurator() {
		_restClassSuffix = "Impl";
		_restAnnotationBytes = getTypeSignatureBytes(REST.class);
		_checkBytecodeSignature = true;
		setIncludedJars("*portal-impl.jar");
	}

	public void configure() throws PortalException {

		File portalImplLib =
			new File(PortalUtil.getPortalLibDir(), "portal-impl.jar");

		URL[] scanPath;

		if (portalImplLib.exists() == false) {
			scanPath = ClassLoaderUtil.getFullClassPath(RestConfigurator.class);
		} else {
			scanPath = new URL[1];
			try {
				scanPath[0] = portalImplLib.toURL();
			}
			catch (MalformedURLException e) {
				_log.error(e, e);
			}
		}

		configure(scanPath);
	}

	public void configure(URL... classpath) throws PortalException {
		_elapsed = System.currentTimeMillis();

		_log.info("REST automatic configuration started...");

		try {
			scanUrls(classpath);
		}
		catch (Exception ex) {
			throw new PortalException("Unable to scan classpath for REST.", ex);
		}

		_elapsed = System.currentTimeMillis() - _elapsed;

		_log.info(
			"REST configured in " + _elapsed + " ms. " +
				"Total actions: " + _registeredActionsCount);
	}

	public void setCheckBytecodeSignature(boolean checkBytecodeSignature) {
		this._checkBytecodeSignature = checkBytecodeSignature;
	}

	public void setRestActionsManager(RestActionsManager restActionsManager) {
		this._restActionsManager = restActionsManager;
	}

	@Override
	protected void onEntry(EntryData entryData) throws ClassNotFoundException {
		String entryName = entryData.getName();

		if (entryName.endsWith(_restClassSuffix) == true) {

			if (_checkBytecodeSignature) {

				InputStream inputStream = entryData.openInputStream();

				if (!isTypeSignatureInUse(inputStream, _restAnnotationBytes)) {
					return;
				}
			}

			_onRestClass(entryName);
		}
	}

	private boolean _checkClass(Class clazz) {

		return
			(clazz.isAnonymousClass() == false) &&
			(clazz.isArray() == false) &&
			(clazz.isEnum() == false) &&
			(clazz.isInterface() == false) &&
			(clazz.isLocalClass() == false) &&
			((clazz.isMemberClass() ^
				Modifier.isStatic(clazz.getModifiers())) == false) &&
			(clazz.isPrimitive() == false);
	}

	private void _onRestClass(String className) throws ClassNotFoundException {

		Class actionClass =
			ClassLoaderUtil.loadClass(className, this.getClass());

		if (_checkClass(actionClass) == false) {
			return;
		}

		Method[] allPublicMethods = actionClass.getMethods();

		for (Method method : allPublicMethods) {

			REST restAnnotation = method.getAnnotation(REST.class);

			if (restAnnotation == null) {
				continue;
			}

			_registerRestAction(actionClass, method, restAnnotation);

		}
	}

	private void _registerRestAction(
		Class<?> implementationClass, Method method, REST restAnnotation) {

		_registeredActionsCount++;

		String path = restAnnotation.value().trim();

		String httpMethod = restAnnotation.method().trim();

		if (httpMethod.length() == 0) {
			httpMethod = null;
		}

		// implementation -> util
		String utilClassName = implementationClass.getName();

		if (utilClassName.endsWith("Impl")) {
			utilClassName =
				utilClassName.substring(0, utilClassName.length() - 4);

			utilClassName += "Util";
		}

		utilClassName = StringUtil.replace(utilClassName, ".impl.", ".");

		try {
			Class utilClass =
				ClassLoaderUtil.loadClass(utilClassName, this.getClass());

			method = utilClass.getMethod(
				method.getName(), method.getParameterTypes());
		}
		catch (Exception ex) {
			// ignore
		}

		_restActionsManager.registerRestAction(
			method.getDeclaringClass(),
			method,
			path,
			httpMethod
		);

	}

	private static Log _log = LogFactoryUtil.getLog(RestConfigurator.class);

	private boolean _checkBytecodeSignature;

	private long _elapsed;

	private int _registeredActionsCount;

	private RestActionsManager _restActionsManager;

	private final byte[] _restAnnotationBytes;

	private String _restClassSuffix;

}