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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ActionParameters.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class RESTActionParameters {

	public void collectAll(HttpServletRequest request, String pathParameters) {
		collectFromPath(pathParameters);
		collectFromRequestParameters(request);
	}

	public void collectFromPath(String pathParameters) {

		if (pathParameters == null) {
			return;
		}

		if (pathParameters.startsWith(StringPool.SLASH)) {
			pathParameters = pathParameters.substring(1);
		}

		String[] chunks = StringUtil.split(pathParameters, StringPool.SLASH);

		int i = 0;

		int total = (chunks.length / 2) * 2;

		while (i < total) {
			String name = chunks[i];

			name = name.replace('-', ' ');

			name = jodd.util.StringUtil.wordsToCamelCase(name);

			String value = chunks[i + 1];

			_addNameValue(name, value);

			i += 2;
		}
	}

	public void collectFromRequestParameters(HttpServletRequest request) {

		Enumeration<String> paramNamesEnumeration = request.getParameterNames();

		while (paramNamesEnumeration.hasMoreElements()) {

			String parameterName = paramNamesEnumeration.nextElement();

			String[] parameterValues =
				request.getParameterValues(parameterName);

			Object value = null;

			if (parameterValues.length == 1) {
				value = parameterValues[0];
			}
			else {
				value = parameterValues;
			}

			_addNameValue(parameterName, value);
		}
	}

	public String[] extractParameterNames() {
		String[] names = new String[_parameters.size()];

		int i = 0;

		for (NameValue nameValue : _parameters) {

			names[i] = nameValue.getName();

			i++;
		}
		return names;
	}

	public Object lookupParameter(String name) {
		for (NameValue nameValue : _parameters) {
			if (nameValue.getName().equals(name)) {
				return nameValue.getValue();
			}
		}
		return null;
	}

	private void _addNameValue(String name, Object value) {
		_parameters.add(new NameValue<Object>(name, value));
	}

	private final Set<NameValue> _parameters = new HashSet<NameValue>();

}