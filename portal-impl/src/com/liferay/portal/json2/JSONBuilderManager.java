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

package com.liferay.portal.json2;

import com.liferay.portal.kernel.json2.JSON;
import com.liferay.portal.kernel.json2.JSONBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public class JSONBuilderManager {

	public JSONBuilder lookupBuilder(Class<?> type) {
		JSON jsonAnnotation = type.getAnnotation(JSON.class);

		if (jsonAnnotation == null) {
			return null;
		}

		if (jsonAnnotation.builder() == JSONBuilder.class) {
			return null;
		}

		JSONBuilder jsonBuilder = _builderMap.get(type);

		if (jsonBuilder != null) {
			return jsonBuilder;
		}

		Class<? extends JSONBuilder> builderClass = jsonAnnotation.builder();

		try {
			jsonBuilder = builderClass.newInstance();
		}
		catch (Exception ex) {
			return null;
		}

		_builderMap.put(type, jsonBuilder);

		return jsonBuilder;
	}

	private Map<Class, JSONBuilder> _builderMap
		= new HashMap<Class, JSONBuilder>();

}