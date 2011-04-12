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

package com.liferay.portal.messaging.proxy;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tina Tian
 */
public class MessageValuesThreadLocal {

	public static Object getValue(String key) {
		Map<String, Object> messageValuesMap = _messageValuesThreadLocal.get();

		if (messageValuesMap == null) {
			return null;
		}

		return messageValuesMap.get(key);
	}

	public static Map<String, Object> getValues() {
		Map<String, Object> messageValuesMap = _messageValuesThreadLocal.get();

		if (messageValuesMap == null) {
			return Collections.EMPTY_MAP;
		}

		return messageValuesMap;
	}

	public static void setValue(String key, Object value) {
		Map<String, Object> messageValuesMap = _messageValuesThreadLocal.get();

		if (messageValuesMap == null) {
			messageValuesMap = new HashMap<String, Object>();

			_messageValuesThreadLocal.set(messageValuesMap);
		}

		messageValuesMap.put(key, value);
	}

	private static ThreadLocal<Map<String, Object>> _messageValuesThreadLocal =
		new AutoResetThreadLocal<Map<String, Object>>(
			MessageValuesThreadLocal.class.getName());

}