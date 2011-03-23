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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;

import org.mortbay.jetty.HttpMethods;

/**
 * @author Igor Spasic
 */
public class RESTMappingResolver {

	public String resolveHttpMethod(Method method) {
		REST restAnnotation = method.getAnnotation(REST.class);

		String httpMethod = restAnnotation.method().trim();

		if (httpMethod.length() != 0) {
			return httpMethod;
		}

		String methodName = method.getName();

		String methodNamePrefix = _cutPrefix(methodName);

		return _prefixToHttpMethod(methodNamePrefix);
	}

	public String resolvePath(Class<?> clazz, Method method) {
		REST restAnnotation = method.getAnnotation(REST.class);

		String path = restAnnotation.value().trim();

		if (path.length() == 0) {
			path = _nameToPathChunk(method.getName());
		}

		if (!path.startsWith(StringPool.SLASH)) {
			path = StringPool.SLASH + path;

			restAnnotation = clazz.getAnnotation(REST.class);

			if (restAnnotation != null) {
				String pathFromClass = restAnnotation.value().trim();

				if (pathFromClass.length() == 0) {
					pathFromClass = _classNameToPath(clazz);
				}

				if (!pathFromClass.startsWith(StringPool.SLASH)) {
					pathFromClass = StringPool.SLASH + pathFromClass;
				}

				path = pathFromClass + path;
			}
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

	private String _nameToPathChunk(String inputName) {
		inputName = jodd.util.StringUtil.camelCaseToWords(inputName);

		String[] names = StringUtil.split(inputName, StringPool.SPACE);

		if (names.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(names.length * 2 - 1);

		for (int i = 0; i < names.length; i++) {
			String name = names[i];

			if (i != 0) {
				sb.append(CharPool.DASH);
			}

			sb.append(name.toLowerCase());
		}

		return sb.toString();
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