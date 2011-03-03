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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class HttpSessionLocal<T> {

	public static void destroySession(String sessionId) {
		for (HttpSessionLocal<?> httpSessionLocals : _httpSessionLocals) {
			Map<String, ?> values = httpSessionLocals._values;

			values.remove(sessionId);
		}
	}

	public static void reset() {
		_httpSessionLocals.clear();
	}

	public HttpSessionLocal() {
		_httpSessionLocals.add(this);
	}

	public HttpSessionLocal(T initialValue) {
		_httpSessionLocals.add(this);

		_initialValue = initialValue;
	}

	public T get() {
		String sessionId = HttpSessionIdThreadLocal.getSessionId();

		if (_values.containsKey(sessionId)) {
			return _values.get(sessionId);
		}
		else {
			return _initialValue;
		}
	}

	public void remove() {
		String sessionId = HttpSessionIdThreadLocal.getSessionId();

		_values.remove(sessionId);
	}

	public void set(T value) {
		String sessionId = HttpSessionIdThreadLocal.getSessionId();

		if (Validator.isNull(sessionId)) {
			throw new IllegalStateException("Session ID is not currently set");
		}

		_values.put(sessionId, value);
	}

	private static List<HttpSessionLocal<?>> _httpSessionLocals =
		new ArrayList<HttpSessionLocal<?>>();

	private T _initialValue;
	private Map<String, T> _values = new HashMap<String, T>();

}