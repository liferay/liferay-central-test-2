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

import com.liferay.portal.kernel.util.MethodParameterNamesResolverUtil;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public class RESTActionConfig {

	public Class<?> getActionClass() {
		return _actionClass;
	}

	public Method getActionMethod() {
		return _actionMethod;
	}

	public String getMethod() {
		return _method;
	}

	public String[] getParameterNames() {
		return _parameterNames;
	}

	public Class<?>[] getParameterTypes() {
		return _parameterTypes;
	}

	public String getPath() {
		return _path;
	}

	public String[] getPathChunks() {
		return _restActionConfigSet.getPathChunks();
	}

	public PathMacro[] getPathMacros() {
		return _restActionConfigSet.getPathMacros();
	}

	public void setActionClass(Class<?> actionClass) {
		_actionClass = actionClass;
	}

	public void setActionMethod(Method actionMethod) {
		_actionMethod = actionMethod;

		_parameterNames =
			MethodParameterNamesResolverUtil.resolveParameterNames(
				actionMethod);
		_parameterTypes = actionMethod.getParameterTypes();
	}

	public void setMethod(String method) {
		_method = method;
	}

	public void setPath(String path) {
		_path = path;
	}

	public void setRESTActionConfigSet(
		RESTActionConfigSet restActionConfigSet) {

		_restActionConfigSet = restActionConfigSet;
	}

	private Class<?> _actionClass;
	private Method _actionMethod;
	private String _method;
	private String[] _parameterNames;
	private Class<?>[] _parameterTypes;
	private String _path;
	private RESTActionConfigSet _restActionConfigSet;

}