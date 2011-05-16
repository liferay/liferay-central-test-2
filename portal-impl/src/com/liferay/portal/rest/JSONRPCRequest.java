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

import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jodd.servlet.ServletUtil;

/**
 * @author Igor Spasic
 */
public class JSONRPCRequest {

	public JSONRPCRequest(HttpServletRequest request) {
		try {
			String requestBody = ServletUtil.readRequestBody(request);

			JSONDeserializer<HashMap<Object, Object>> jsonDeserializer =
				JSONFactoryUtil.createJSONDeserializer();

			jsonDeserializer.use(null, HashMap.class);
			jsonDeserializer.use("parameters", HashMap.class);

			HashMap<Object, Object> requestBodyMap =
				jsonDeserializer.deserialize(requestBody);

			_method = (String)requestBodyMap.get("method");

			_parameters = (Map<String, String>)requestBodyMap.get("params");

			_valid = true;
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public String getMethod() {
		return _method;
	}

	public String getParameter(String name) {
		return _parameters.get(name);
	}

	public Set<String> getParameterNames() {
		return _parameters.keySet();
	}

	public boolean isValid() {
		return _valid;
	}

	private static Log _log = LogFactoryUtil.getLog(JSONRPCRequest.class);

	private String _method;
	private Map<String, String> _parameters;
	private boolean _valid;

}