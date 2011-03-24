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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import flexjson.JSONDeserializer;
import jodd.servlet.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public class JSONRPCRequest {

	public JSONRPCRequest(HttpServletRequest request) {
		_valid = true;

		try {
			String requestBody = ServletUtil.readRequestBody(request);

			HashMap map = (HashMap) new JSONDeserializer().
				use(null, HashMap.class).use("parameters", HashMap.class).
				deserialize(requestBody);

			_method = (String)map.get("method");

			_parameters = (Map<String, String>)map.get("params");

		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
			_valid = false;
		}
	}

	public String getMethod() {
		return _method;
	}

	public String getParameter(String name) {
		return _parameters.get(name);
	}
	
	public boolean isValid() {
		return _valid;
	}

	public Iterator<Map.Entry<String,String>> parametersIterator() {
		return _parameters.entrySet().iterator();
	}
	private static Log _log = LogFactoryUtil.getLog(JSONRPCRequest.class);
	private String _method;
	private Map<String, String> _parameters;
	private boolean _valid;
}
