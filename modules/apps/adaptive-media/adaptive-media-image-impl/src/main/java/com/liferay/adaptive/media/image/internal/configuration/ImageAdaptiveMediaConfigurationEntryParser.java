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

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, service = ImageAdaptiveMediaConfigurationEntryParser.class
)
public class ImageAdaptiveMediaConfigurationEntryParser {

	public ImageAdaptiveMediaConfigurationEntry parse(String s) {
		if (Validator.isNull(s)) {
			throw new IllegalArgumentException(
				"Image adaptive media configuration not valid: " + s);
		}

		String[] fields = _FIELD_SEPARATOR_PATTERN.split(s);

		if (fields.length != 3) {
			throw new IllegalArgumentException(
				"Image adaptive media configuration not valid: " + s);
		}

		String name = fields[0];
		String uuid = fields[1];

		if (Validator.isNull(name) || Validator.isNull(uuid)) {
			throw new IllegalArgumentException(
				"Image adaptive media configuration not valid: " + s);
		}

		String[] propertiesString = _PROPERTY_SEPARATOR_PATTERN.split(
			fields[2]);

		Map<String, String> properties = new HashMap<>();

		for (String propertyString : propertiesString) {
			String[] keyValuePair = _KEY_VALUE_SEPARATOR_PATTERN.split(
				propertyString);

			properties.put(keyValuePair[0], keyValuePair[1]);
		}

		return new ImageAdaptiveMediaConfigurationEntry(name, uuid, properties);
	}

	private static final Pattern _FIELD_SEPARATOR_PATTERN = Pattern.compile(
		"\\s*:\\s*");

	private static final Pattern _KEY_VALUE_SEPARATOR_PATTERN = Pattern.compile(
		"\\s*=\\s*");

	private static final Pattern _PROPERTY_SEPARATOR_PATTERN = Pattern.compile(
		"\\s*;\\s*");

}