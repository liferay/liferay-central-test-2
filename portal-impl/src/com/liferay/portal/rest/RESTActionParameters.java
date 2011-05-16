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

import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ObjectValuePair;
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

	public void collectAll(
		HttpServletRequest request, String pathParameters,
		JSONRPCRequest jsonRpcRequest) {

		_collectFromPath(pathParameters);
		_collectFromRequestParameters(request);
		_collectFromJSONRPCRequest(jsonRpcRequest);
	}

	public Object getParameter(String name) {
		for (ObjectValuePair<String, Object> parameter : _parameters) {
			String key = parameter.getKey();

			if (key.equals(name)) {
				return parameter.getValue();
			}
		}

		return null;
	}

	public String[] getParameterNames() {
		String[] names = new String[_parameters.size()];

		int i = 0;

		for (ObjectValuePair<String, Object> parameter : _parameters) {
			names[i] = parameter.getKey();

			i++;
		}

		return names;
	}

	private void _collectFromJSONRPCRequest(JSONRPCRequest jsonRpcRequest) {
		if (jsonRpcRequest == null) {
			return;
		}

		Set<String> parameterNames = jsonRpcRequest.getParameterNames();

		for (String parameterName : parameterNames) {
			String parameterValue = jsonRpcRequest.getParameter(parameterName);

			ObjectValuePair<String, Object> objectValuePair =
				new ObjectValuePair<String, Object>(
					parameterName, parameterValue);

			_parameters.add(objectValuePair);
		}
	}

	private void _collectFromPath(String pathParameters) {
		if (pathParameters == null) {
			return;
		}

		if (pathParameters.startsWith(StringPool.SLASH)) {
			pathParameters = pathParameters.substring(1);
		}

		String[] pathParametersParts = StringUtil.split(
			pathParameters, StringPool.SLASH);

		int length = (pathParametersParts.length / 2) * 2;

		for (int i = 0; i < length;) {
			String name = pathParametersParts[i];

			name = jodd.util.StringUtil.wordsToCamelCase(name, CharPool.DASH);

			String value = pathParametersParts[i + 1];

			ObjectValuePair<String, Object> objectValuePair =
				new ObjectValuePair<String, Object>(name, value);

			_parameters.add(objectValuePair);

			i += 2;
		}
	}

	private void _collectFromRequestParameters(HttpServletRequest request) {
		UploadServletRequest uploadServletRequest = null;

		if (request instanceof UploadServletRequest) {
			uploadServletRequest = (UploadServletRequest)request;
		}

		Enumeration<String> enu = request.getParameterNames();

		while (enu.hasMoreElements()) {
			String parameterName = enu.nextElement();

			Object value = null;

			if ((uploadServletRequest != null) &&
				!uploadServletRequest.isFormField(parameterName)) {

				value = uploadServletRequest.getFile(parameterName);
			}
			else {
				String[] parameterValues = request.getParameterValues(
					parameterName);

				if (parameterValues.length == 1) {
					value = parameterValues[0];
				}
				else {
					value = parameterValues;
				}
			}

			ObjectValuePair<String, Object> objectValuePair =
				new ObjectValuePair<String, Object>(parameterName, value);

			_parameters.add(objectValuePair);
		}
	}

	private Set<ObjectValuePair<String, Object>> _parameters =
		new HashSet<ObjectValuePair<String, Object>>();

}