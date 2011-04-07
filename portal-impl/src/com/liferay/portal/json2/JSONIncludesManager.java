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

package com.liferay.portal.json2;

import com.liferay.portal.kernel.json2.JSON;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

/**
 * @author Igor Spasic
 */
public class JSONIncludesManager {

	public String[] lookupExcludes(Class<?> type) {
		String[] excludes = _excludesMap.get(type);

		if (excludes != null) {
			return excludes;
		}

		JSON jsonAnnotation = type.getAnnotation(JSON.class);

		if ((jsonAnnotation != null) && (jsonAnnotation.strict() == true)) {
			excludes = _EXCLUDE_ALL;
		}
		else {
			excludes = _resolveAnnotation(type, false);
		}

		_excludesMap.put(type, excludes);

		return excludes;
	}

	public String[] lookupIncludes(Class type) {
		String[] includes = _includesMap.get(type);

		if (includes != null) {
			return includes;
		}

		includes = _resolveAnnotation(type, true);

		_includesMap.put(type, includes);

		return includes;
	}

	private static String _getPropertyName(Method method) {
		if (method.getParameterTypes().length != 0) {
			return null;
		}

		String methodName = method.getName();

		if (methodName.startsWith("is")) {
			return StringUtil.uncapitalize(methodName.substring(2));
		}

		if (methodName.startsWith("has")) {
			return StringUtil.uncapitalize(methodName.substring(2));
		}

		if (methodName.startsWith("get")) {
			return StringUtil.uncapitalize(methodName.substring(3));
		}

		return null;
	}

	private static String[] _resolveAnnotation(Class type, boolean include) {

		List<String> list = new ArrayList<String>();

		Field[] fields = type.getDeclaredFields();

		for (Field field : fields) {
			JSON jsonAnnotation = field.getAnnotation(JSON.class);

			if (jsonAnnotation != null &&
				jsonAnnotation.include() == include) {

				list.add(field.getName());
			}
		}

		Method[] methods = type.getDeclaredMethods();

		for (Method method : methods) {
			JSON jsonAnnotation = method.getAnnotation(JSON.class);

			if (jsonAnnotation != null &&
				jsonAnnotation.include() == include) {

				String name = _getPropertyName(method);

				if (name != null) {

					if (list.contains(name) == false) {
						list.add(name);
					}
				}
			}
		}

		if (list.isEmpty()) {
			return _EMPTY_STR_ARRAY;
		}
		else {
			return list.toArray(new String[list.size()]);
		}
	}

	private static final String[] _EMPTY_STR_ARRAY = new String[0];

	private static final String[] _EXCLUDE_ALL = new String[] {"*"};

	private Map<Class, String[]> _excludesMap = new HashMap<Class, String[]>();

	private Map<Class, String[]> _includesMap = new HashMap<Class, String[]>();

}