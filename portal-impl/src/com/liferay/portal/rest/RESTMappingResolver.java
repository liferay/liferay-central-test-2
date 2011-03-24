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

import com.liferay.portal.kernel.rest.REST;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public class RESTMappingResolver {

	public String resolveHttpMethod(Method method) {
		REST restAnnotation = method.getAnnotation(REST.class);

		String httpMethod = null;

		if (restAnnotation != null) {
			httpMethod = restAnnotation.method().trim();
		}

		if (httpMethod != null && httpMethod.length() != 0) {
			return httpMethod;
		}

		String methodName = method.getName();

		String methodNamePrefix = _cutPrefix(methodName);

		return _prefixToHttpMethod(methodNamePrefix);
	}

	public String resolvePath(Class<?> clazz, Method method) {
		REST restAnnotation = method.getAnnotation(REST.class);

		String path = null;

		if (restAnnotation != null) {
			path = restAnnotation.value().trim();
		}

		if (path == null || path.length() == 0) {
			path = jodd.util.StringUtil.camelCaseToWords(
				method.getName(), CharPool.DASH);
		}

		if (!path.startsWith(StringPool.SLASH)) {
			path = StringPool.SLASH + path;

			String pathFromClass = null;

			restAnnotation = clazz.getAnnotation(REST.class);

			if (restAnnotation != null) {
				pathFromClass = restAnnotation.value().trim();
			}

			if (pathFromClass == null || pathFromClass.length() == 0) {
				pathFromClass = _classNameToPath(clazz);
			}

			if (!pathFromClass.startsWith(StringPool.SLASH)) {
				pathFromClass = StringPool.SLASH + pathFromClass;
			}

			path = pathFromClass + path;

		}
		return path;
	}

	private String _classNameToPath(Class<?> clazz) {
		String className = clazz.getSimpleName();

		className = StringUtil.replace(className, "Impl", StringPool.BLANK);
		className = StringUtil.replace(className, "Service", StringPool.BLANK);

		return className.toLowerCase();
	}

	private String _cutPrefix(String methodName) {
		int i = 0;

		while (i < methodName.length()) {
			if (Character.isUpperCase(methodName.charAt(i))) {
				break;
			}

			i++;
		}

		return methodName.substring(0, i);
	}

	private String _prefixToHttpMethod(String prefix) {
		for (String postPrefix : POST_PREFIXES) {
			if (prefix.equals(postPrefix)) {
				return HttpMethods.POST;
			}
		}

		return HttpMethods.GET;
	}

	private static final String[] POST_PREFIXES = new String[] {
		"add", "delete", "set", "unset", "update",
	};

}