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
import com.liferay.portal.kernel.util.MethodParameterNamesResolverUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;

import org.mortbay.jetty.HttpMethods;

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

		_fixDuplicateStartingChunks = fixDuplicateStartingChunks;
	}

	public void setIncludeParameterNames(boolean includeParameterNames) {
		_includeParameterNames = includeParameterNames;
	}

	public void setParameterNameValueSeparator(
		char parameterNameValueSeparator) {

		_parameterNameValueSeparator = parameterNameValueSeparator;
	}

	public void setSmartMethodNames(boolean smartMethodNames) {
		_smartMethodNames = smartMethodNames;
	}

	private String _buildParametersChunks(Method method) {
		String[] parameterNames =
			MethodParameterNamesResolverUtil.resolveParameterNames(method);

		return _buildParametersChunks(parameterNames);
	}

	private String _buildParametersChunks(String[] parameterNames) {
		StringBundler sb = new StringBundler(parameterNames.length * 5 - 1);

		for (int i = 0; i < parameterNames.length; i++) {
			String parameterName = parameterNames[i];

			String chunk = null;

			if (_includeParameterNames) {
				chunk =
					_nameToPathChunk(parameterName) +
						_parameterNameValueSeparator;
			}
			else {
				chunk = StringPool.BLANK;
			}

			if (i != 0) {
				chunk = StringPool.SLASH.concat(chunk);
			}

			sb.append(chunk);
			sb.append("${");
			sb.append(parameterName);
			sb.append('}');
		}

		return sb.toString();
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

		if (prefix.equals("add") || prefix.equals("delete") ||
			prefix.equals("get")) {

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

		String[] parameterNames =
			MethodParameterNamesResolverUtil.resolveParameterNames(method);

		for (String parameterName : parameterNames) {
			parameterName = StringUtil.replace(
				parameterName, "Id", StringPool.BLANK);

			parameterName = jodd.util.StringUtil.capitalize(parameterName);

			int pos = methodName.indexOf(parameterName);

			if (pos != -1) {
				methodName =
					methodName.substring(0, pos) +
						methodName.substring(pos + parameterName.length());
			}
		}

		String path = _nameToPathChunk(methodName);

		if (methodName.length() > 0) {
			path += CharPool.SLASH;
		}

		path += _buildParametersChunks(parameterNames);

		return path;
	}

	private String _nameToPathChunk(String inputName) {
		inputName = jodd.util.StringUtil.camelCaseToWords(inputName);

		String[] names = StringUtil.split(inputName, StringPool.SPACE);

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
		if (prefix.equals("add") || prefix.equals("update")) {
			return HttpMethods.POST;
		}
		else if (prefix.equals("delete") || prefix.equals("unset")) {
			return HttpMethods.DELETE;
		}
		else if (prefix.equals("get")) {
			return HttpMethods.GET;
		}

		return null;
	}

	private String _stripBySuffix(String name) {
		int pos = 0;

		while (true) {
			pos = name.indexOf("By", pos);

			if (pos == -1) {
				break;
			}

			if (pos + 2 < name.length()) {
				if (Character.isUpperCase(name.charAt(pos + 2))) {
					name = name.substring(0, pos);

					break;
				}
			}

			pos++;
		}

		return name;
	}

	private boolean _fixDuplicateStartingChunks = true;
	private boolean _includeParameterNames = true;
	private char _parameterNameValueSeparator = CharPool.SLASH;
	private boolean _smartMethodNames = true;

}