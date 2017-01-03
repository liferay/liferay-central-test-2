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

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<>();

		properties.put("height", String.valueOf(_height));
		properties.put("width", String.valueOf(_width));

		return properties;
	}

	private DemoImageAdaptiveMediaConfiguration(
		String name, String id, int width, int height) {

		_name = name;
		_id = id;
		_width = width;
		_height = height;
	}

	private final int _height;
	private final String _id;
	private final String _name;
	private final int _width;

}