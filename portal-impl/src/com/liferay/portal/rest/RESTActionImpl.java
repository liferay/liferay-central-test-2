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
import com.liferay.portal.service.ServiceContext;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import jodd.util.ReflectUtil;

/**
 * @author Igor Spasic
 */
public class RESTActionImpl implements RESTAction {

	public RESTActionImpl(
		RESTActionConfig restActionConfig,
		RESTActionParameters restActionParameters) {

		_restActionConfig = restActionConfig;
		_restActionParameters = restActionParameters;
	}

	public Class<?> getReturnType() {
		Method actionMethod = _restActionConfig.getActionMethod();

		return actionMethod.getReturnType();
	}

	public Object invoke(HttpServletRequest request) throws Exception {
		Class<?> actionClass = _restActionConfig.getActionClass();

		Method actionMethod = _restActionConfig.getActionMethod();

		Object[] parameters = _prepareParameters();

		_injectServiceContext(parameters);

		return actionMethod.invoke(actionClass, parameters);
	}

	private void _injectServiceContext(Object[] parameters) {
		String[] parameterNames = _restActionConfig.getParameterNames();
		Class<?>[] parameterTypes = _restActionConfig.getParameterTypes();

		for (int i = 0; i < parameterNames.length; i++) {
			if (parameters[i] != null) {
				continue;
			}

			String parameterName = parameterNames[i];
			Class<?> parameterType = parameterTypes[i];

			if (parameterName.equals("serviceContext") &&
				parameterType.equals(ServiceContext.class)) {

				parameters[i] = new ServiceContext();
			}
		}
	}

	private Object[] _prepareParameters() {
		String[] parameterNames = _restActionConfig.getParameterNames();
		Class<?>[] parameterTypes = _restActionConfig.getParameterTypes();

		Object[] parameters = new Object[parameterNames.length];

		for (int i = 0; i < parameterNames.length; i++) {
			String parameterName = parameterNames[i];

			Object value = _restActionParameters.getParameter(parameterName);

			Object parameterValue = null;

			if (value != null) {
				Class<?> parameterType = parameterTypes[i];

				parameterValue = ReflectUtil.castType(value, parameterType);
			}

			parameters[i] = parameterValue;
		}

		return parameters;
	}

	private RESTActionConfig _restActionConfig;
	private RESTActionParameters _restActionParameters;

}