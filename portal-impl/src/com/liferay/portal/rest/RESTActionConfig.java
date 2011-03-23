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
import com.liferay.portal.kernel.util.StringBundler;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public class RESTActionConfig implements Comparable<RESTActionConfig> {

	public RESTActionConfig(
		Class<?> actionClass, Method actionMethod, String path, String method) {

		_actionClass = actionClass;
		_actionMethod = actionMethod;
		_path = path;
		_method = method;

		_parameterNames = MethodParameterNamesResolverUtil.
			resolveParameterNames(actionMethod);
		_parameterTypes = actionMethod.getParameterTypes();

		StringBundler sb = new StringBundler(_parameterNames.length * 2 + 2);

		sb.append(_path);
		sb.append('+');
		sb.append(_parameterNames.length);
		sb.append('+');
		for (String parameterName : _parameterNames) {
			sb.append(parameterName);
			sb.append('+');
		}

		_pathForCompare = sb.toString();
	}

	public int compareTo(RESTActionConfig restActionConfig) {
		return _pathForCompare.compareTo(restActionConfig._pathForCompare);
	}

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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("RESTActionConfig{");
		sb.append(_actionClass.getSimpleName());
		sb.append('#');
		sb.append(_actionMethod.getName());
		sb.append('(');
		for (int i = 0; i < _parameterNames.length; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(_parameterNames[i]);
		}
		sb.append(')');
		sb.append("--->");
		sb.append(_method);
		sb.append(' ');
		sb.append(_path);
		sb.append('}');
		return sb.toString();
	}

	private final Class<?> _actionClass;
	private final Method _actionMethod;
	private final String _method;
	private final String[] _parameterNames;
	private final Class<?>[] _parameterTypes;
	private final String _path;
	private final String _pathForCompare;

}