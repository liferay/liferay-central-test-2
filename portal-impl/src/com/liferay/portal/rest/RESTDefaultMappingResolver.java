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
import com.liferay.portal.kernel.util.MethodParameterNamesResolverUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public class RESTDefaultMappingResolver implements RESTMappingResolver {

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
			if (_smartMethodNames) {
				path = _methodNameToPath(method);
			}
			else {
				path = _nameToPathChunk(method.getName());
				path += StringPool.SLASH;
				path += _buildParametersChunks(method);
			}
		}

		if (path.endsWith(StringPool.SLASH)) {
			path += _buildParametersChunks(method);
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

				if (_fixDuplicateStartingChunks) {
					if (path.startsWith(pathFromClass + StringPool.SLASH)) {
						pathFromClass = StringPool.BLANK;
					}
				}

				path = pathFromClass + path;
			}
		}
		return path;
	}

	public void setFixDuplicateStartingChunks(
		boolean fixDuplicateStartingChunks) {

		this._fixDuplicateStartingChunks = fixDuplicateStartingChunks;
	}

	public void setIncludeParamNames(boolean includeParamNames) {
		this._includeParamNames = includeParamNames;
	}

	public void setParamNameValueSeparator(char paramNameValueSeparator) {
		this._paramNameValueSeparator = paramNameValueSeparator;
	}

	public void setSmartMethodNames(boolean smartMethodNames) {
		this._smartMethodNames = smartMethodNames;
	}

	private String _buildParametersChunks(Method method) {
		String[] paramNames =
			MethodParameterNamesResolverUtil.resolveParameterNames(method);

		return _buildParametersChunks(paramNames);
	}

	private String _buildParametersChunks(String[] paramNames) {
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < paramNames.length; i++) {
			String paramName = paramNames[i];

			String chunk = null;

			if (_includeParamNames) {
				chunk = _nameToPathChunk(paramName) + _paramNameValueSeparator;
			}
			else {
				chunk = StringPool.BLANK;
			}

			if (i != 0) {
				chunk = StringPool.SLASH + chunk;
			}

			result.append(chunk);
			result.append("${").append(paramName).append('}');
		}

		return result.toString();
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

	private boolean _isRemovablePrefix(String prefix) {
		if (prefix == null) {
			return false;
		}

		if (prefix.equals("add")) {
			return true;
		}

		if (prefix.equals("delete")) {
			return true;
		}

		if (prefix.equals("get")) {
			return true;
		}

		return false;
	}

	private String _methodNameToPath(Method method) {

		String methodName = method.getName();
		String methodNamePrefix = _cutPrefix(methodName);

		if (_isRemovablePrefix(methodNamePrefix)) {
			methodName = methodName.substring(methodNamePrefix.length());

			if (methodNamePrefix.equals("get")) {
				methodName = _stripBySuffix(methodName);
			}
		}

		String[] paramNames =
			MethodParameterNamesResolverUtil.resolveParameterNames(method);

		for (String paramName : paramNames) {

			paramName = StringUtil.replace(paramName, "Id", StringPool.BLANK);

			paramName = jodd.util.StringUtil.capitalize(paramName);

			int index = methodName.indexOf(paramName);

			if (index != -1) {
				methodName = methodName.substring(0, index) +
					methodName.substring(index + paramName.length());
			}
		}

		String path = _nameToPathChunk(methodName);

		if (methodName.length() > 0) {
			path += '/';
		}

		path += _buildParametersChunks(paramNames);

		return path;
	}

	private String _nameToPathChunk(String inputName) {

		inputName = jodd.util.StringUtil.camelCaseToWords(inputName);

		String[] names = StringUtil.split(inputName, StringPool.SPACE);

		StringBuilder chunk = new StringBuilder();

		for (int i = 0; i < names.length; i++) {
			String name = names[i];

			if (i != 0) {
				chunk.append('-');
			}
			chunk.append(name.toLowerCase());
		}

		return chunk.toString();
	}

	private String _prefixToHttpMethod(String prefix) {

		if (prefix.equals("add") || prefix.equals("update")) {
			return "POST";
		}

		if (prefix.equals("delete") || prefix.equals("unset")) {
			return "DELETE";
		}

		if (prefix.equals("get")) {
			return "GET";
		}

		return null;
	}

	private String _stripBySuffix(String name) {
		int index = 0;

		while (true) {
			index = name.indexOf("By", index);

			if (index == -1) {
				break;
			}

			if (index + 2 < name.length()) {
				if (Character.isUpperCase(name.charAt(index + 2))) {
					name = name.substring(0, index);
					break;
				}
			}

			index++;
		}

		return name;
	}

	private boolean _fixDuplicateStartingChunks = true;
	private boolean _includeParamNames = true;
	private char _paramNameValueSeparator = '/';
	private boolean _smartMethodNames = true;

}