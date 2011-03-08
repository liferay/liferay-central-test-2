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

import com.liferay.portal.kernel.rest.RestAction;

import java.lang.reflect.Method;

import jodd.util.ReflectUtil;

/**
 * @author Igor Spasic
 */
public class RestActionImpl implements RestAction {

	public RestActionImpl(
		RestActionConfig restActionConfig, String[] pathChunks) {

		_restActionConfig = restActionConfig;
		_pathChunks = pathChunks;
	}

	public Object invoke() throws Exception {
		Class<?> actionClass = _restActionConfig.getActionClass();

		Method actionMethod = _restActionConfig.getActionMethod();

		Object[] parameters = _prepareParameters();

		return actionMethod.invoke(actionClass, parameters);
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
	private RestActionConfig _restActionConfig;

}