/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Map;

/**
 * <a href="MethodKey.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MethodKey implements Serializable {

	public MethodKey(String className, String methodName, Class<?>[] types) {
		this(null, null, className, methodName, types);
	}

	public MethodKey(
		Map<String, Class<?>> classesMap, Map<MethodKey, Method> methodsMap,
		String className, String methodName, Class<?>[] types) {

		_classesMap = classesMap;
		_methodsMap = methodsMap;
		_className = className;
		_methodName = methodName;
		_types = types;
	}

	public Map<String, Class<?>> getClassesMap() {
		return _classesMap;
	}

	public Map<MethodKey, Method> getMethodsMap() {
		return _methodsMap;
	}

	public String getClassName() {
		return _className;
	}

	public String getMethodName() {
		return _methodName;
	}

	public Class<?>[] getTypes() {
		return _types;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		MethodKey methodKey = (MethodKey)obj;

		if (toString().equals(methodKey.toString())) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		return _toString();
	}

	private String _toString() {
		if (_toString == null) {
			StringBundler sb = new StringBundler();

			sb.append(_className);
			sb.append(_methodName);

			if ((_types != null) && (_types.length > 0)) {
				sb.append(StringPool.DASH);

				for (Class<?> type : _types) {
					sb.append(type.getName());
				}
			}

			_toString = sb.toString();
		}

		return _toString;
	}

	private Map<String, Class<?>> _classesMap;
	private Map<MethodKey, Method> _methodsMap;
	private String _className;
	private String _methodName;
	private Class<?>[] _types;
	private String _toString;

}