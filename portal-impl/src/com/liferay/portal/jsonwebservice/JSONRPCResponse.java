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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.json.JSONSerializer;

import java.lang.reflect.InvocationTargetException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public class JSONRPCResponse implements JSONSerializable {

	public JSONRPCResponse(
		JSONRPCRequest jsonRpcRequest, Object result, Exception exception) {

		this._id = jsonRpcRequest.getId();
		this._jsonrpc = jsonRpcRequest.getJsonrpc();
		this._result = null;
		this._error = null;

		if (this._jsonrpc != null && !this._jsonrpc.equals("2.0")) {
			result = null;

			this._error =
				new Error(-32700, "Parsing error (wrong JSON RPC version)");
		}

		if (exception == null) {
			this._result = result;
		}
		else {
			int errorCode = -32603;

			String errorMessage = null;

			if (exception instanceof InvocationTargetException) {
				errorMessage = exception.getCause().toString();

				errorCode = -32602;
			}
			else {
				errorMessage = exception.getMessage();
			}

			if (errorMessage == null) {
				errorMessage = exception.toString();
			}

			this._error = new Error(errorCode, errorMessage);
		}
	}

	public String toJSONString() {
		Map<String, Object> response = new HashMap<String, Object>();

		if (_jsonrpc != null) {
			response.put("jsonrpc", "2.0");
		}

		if (_id != null) {
			response.put("id", _id);
		}

		if (_error != null) {
			response.put("error", _error);
		}

		if (_result != null) {
			response.put("result", _result);
		}

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		jsonSerializer.include("error", "result").exclude("*.class");

		return jsonSerializer.serialize(response);
	}

	private Error _error;
	private Integer _id;
	private String _jsonrpc;
	private Object _result;

	public static class Error {

		public Error(int _code, String _message) {
			this._code = _code;
			this._message = _message;
		}

		public int getCode() {
			return _code;
		}

		public String getMessage() {
			return _message;
		}

		private final int _code;
		private final String _message;
	}

}