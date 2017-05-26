/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.adaptive.media.demo.data.creator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alejandro Hernández
 */
public enum DemoImageAdaptiveMediaConfiguration {

	XS("Extra small demo size", "demo-xsmall", 50, 50),
	S("Small demo size", "demo-small", 100, 100),
	M("Medium size", "demo-medium", 400, 400),
	L("Large demo size", "demo-large", 800, 800),
	XL("Extra large demo size", "demo-xlarge", 1200, 1200);

	public String getName() {
		return _name;
	}

	public Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<>();

		properties.put("height", String.valueOf(_height));
		properties.put("width", String.valueOf(_width));

		return properties;
	}

	public String getUuid() {
		return _uuid;
	}

	private DemoImageAdaptiveMediaConfiguration(
		String name, String uuid, int width, int height) {

		_name = name;
		_uuid = uuid;
		_width = width;
		_height = height;
	}

	private final int _height;
	private final String _name;
	private final String _uuid;
	private final int _width;

}