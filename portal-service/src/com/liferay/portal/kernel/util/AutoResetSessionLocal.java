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

package com.liferay.portal.kernel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class AutoResetSessionLocal<T> {

	public AutoResetSessionLocal() {
		_sessionLocals.add(this);
	}

	public AutoResetSessionLocal(T initialValue) {
		_sessionLocals.add(this);

		_initialValue = initialValue;
	}

	public static void destroySession(String sessionId) {
		for (AutoResetSessionLocal<?> sessionLocal : _sessionLocals) {
			sessionLocal._map.remove(sessionId);
		}
	}

	public T get() {
		String sessionId = HttpSessionThreadLocal.getSessionId();

		if (_map.containsKey(sessionId)) {
			return _map.get(sessionId);
		}
		else {
			return _initialValue;
		}
	}

	public void remove() {
		String sessionId = HttpSessionThreadLocal.getSessionId();

		_map.remove(sessionId);
	}

	public void set(T value) {
		String sessionId = HttpSessionThreadLocal.getSessionId();

		if (Validator.isNull(sessionId)) {
			throw new IllegalStateException("Session ID is not currently set");
		}

		_map.put(sessionId, value);
	}

	private T _initialValue;
	private Map<String, T> _map = new HashMap<String, T>();

	private static List<AutoResetSessionLocal<?>> _sessionLocals =
		new ArrayList<AutoResetSessionLocal<?>>();

}