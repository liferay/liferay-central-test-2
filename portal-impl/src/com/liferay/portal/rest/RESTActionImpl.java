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

import com.liferay.portal.kernel.rest.RESTAction;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import jodd.util.ReflectUtil;

/**
 * @author Igor Spasic
 */
public class RESTActionImpl implements RESTAction {

	public RESTActionImpl(
		RESTActionConfig restActionConfig, String[] pathChunks) {

		_restActionConfig = restActionConfig;
		_pathChunks = pathChunks;
	}

	public Class<?> getReturnType() {
		Method actionMethod = _restActionConfig.getActionMethod();

		return actionMethod.getReturnType();
	}

	public Object invoke(HttpServletRequest request) throws Exception {
		Class<?> actionClass = _restActionConfig.getActionClass();

		Method actionMethod = _restActionConfig.getActionMethod();

		Object[] parameters = _prepareParameters();

		_injectRequestParameters(parameters, request);

		_fixNullParameters(parameters);

		return actionMethod.invoke(actionClass, parameters);
	}

	private void _fixNullParameters(Object[] parameters) throws Exception {
		Class<?>[] parameterTypes = _restActionConfig.getParameterTypes();

		for (int i = 0; i < parameters.length; i++) {
			Object value = parameters[i];

			if (value == null) {
				parameters[i] = ReflectUtil.newInstance(parameterTypes[i]);
			}
		}
	}

	private void _injectRequestParameters(
		Object[] parameters, HttpServletRequest request) {

		String[] parameterNames = _restActionConfig.getParameterNames();
		Class<?>[] parameterTypes = _restActionConfig.getParameterTypes();

		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i] != null) {
				continue;
			}

			String parameterName = parameterNames[i];

			String parameterValue = request.getParameter(parameterName);

			if (parameterValue == null) {
				continue;
			}

			Class<?> parameterType = parameterTypes[i];

			Object value = ReflectUtil.castType(parameterValue, parameterType);

			parameters[i] = value;
		}
	}

	private Object[] _prepareParameters() {
		String[] pathChunks = _pathChunks;
		String[] parameterNames = _restActionConfig.getParameterNames();
		Class<?>[] parameterTypes = _restActionConfig.getParameterTypes();
		PathMacro[] pathMacros = _restActionConfig.getPathMacros();

		Object[] parameters = new Object[parameterNames.length];

		for (PathMacro pathMacro : pathMacros) {
			int index = pathMacro.getIndex();

			if (index >= pathChunks.length) {
				continue;
			}

			String value = pathChunks[index];

			for (int i = 0; i < parameterNames.length; i++) {
				String parameterName = parameterNames[i];

				if (!parameterName.equals(pathMacro.getName())) {
					continue;
				}

				Class<?> parameterType = parameterTypes[i];

				Object parameterValue = ReflectUtil.castType(
					value, parameterType);

				parameters[i] = parameterValue;
			}
		}

		return parameters;
	}

	private String[] _pathChunks;
	private RESTActionConfig _restActionConfig;

}