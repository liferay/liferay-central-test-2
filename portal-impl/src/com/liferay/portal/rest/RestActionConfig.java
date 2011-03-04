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

public class RestActionConfig {

	public RestActionConfig(
		Class actionClass, Method actionMethod, String path, String method) {

		_actionClass = actionClass;
		_actionMethod = actionMethod;
		_parameterTypes = actionMethod.getParameterTypes();
		_actionPath = path;
		_method = method;
		_parameterNames = MethodParameterNamesResolverUtil.
			resolveParamNames(_actionMethod);
	}

	public Class getActionClass() {
		return _actionClass;
	}

	public Method getActionMethod() {
		return _actionMethod;
	}

	public String getActionPath() {
		return _actionPath;
	}

	public String[] getActionPathChunks() {
		return _actionConfigSet.getActionPathChunks();
	}

	public PathMacro[] getActionPathMacros() {
		return _actionConfigSet.getActionPathMacros();
	}

	public String getMethod() {
		return _method;
	}

	public String[] getParameterNames() {
		return _parameterNames;
	}

	public Class[] getParameterTypes() {
		return _parameterTypes;
	}

	public void setActionConfigSet(RestActionConfigSet actionConfigSet) {
		_actionConfigSet = actionConfigSet;
	}

	private final Class _actionClass;

	private RestActionConfigSet _actionConfigSet;

	private final Method _actionMethod;

	private final String _actionPath;

	private final String _method;

	private final String[] _parameterNames;

	private final Class[] _parameterTypes;
}
