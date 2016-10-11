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
 * An {@link ImageAdaptiveMediaConfigurationEntryParser} is responsible of
 * parsing ConfigAdmin configuration entries.
 *
 * A configuration entry must be a string of the following form:
 * <code>
 *     name:uuid:key0=val0;key1=val1;...;keyN=valN
 * </code>
 * Where_
 * <ul>
 *     <li>
 *         name is an arbitrary {@link String}
 *     </li>
 *     <li>
 *         uuid must be a unique identifier. No two configuration entries should
 *         have the same UUID.
 *     </li>
 *     <li>
 *         The key and value pairs can be anything, but note that consumers of
 *         the resulting {@link ImageAdaptiveMediaConfigurationEntry} may
 *         require a particular set of attributes.
 *     </li>
 * </ul>
 * @author Adolfo Pérez
 *
 * @review
 */
@Component(
	immediate = true, service = ImageAdaptiveMediaConfigurationEntryParser.class
)
public class ImageAdaptiveMediaConfigurationEntryParser {

	/**
	 * Parse the give configuration line, and return a configuration entry with
	 * its data.
	 *
	 * @param s the configuration line to parse
	 * @return A {@link ImageAdaptiveMediaConfigurationEntry} with the line data
	 * @throws IllegalArgumentException is <code>s</code> is null or it is not a
	 *         valid configuration line.
	 *
	 * @review
	 */
	public ImageAdaptiveMediaConfigurationEntry parse(String s) {
		if (Validator.isNull(s)) {
			throw new IllegalArgumentException(
				"Invalid image adaptive media configuration: " + s);
		}

		String[] fields = _FIELD_SEPARATOR_PATTERN.split(s);

		if (fields.length != 3) {
			throw new IllegalArgumentException(
				"Invalid image adaptive media configuration: " + s);
		}

		String name = fields[0];
		String uuid = fields[1];

		if (Validator.isNull(name) || Validator.isNull(uuid)) {
			throw new IllegalArgumentException(
				"Invalid image adaptive media configuration: " + s);
		}

		String[] attributes = _ATTRIBUTE_SEPARATOR_PATTERN.split(fields[2]);

		Map<String, String> properties = new HashMap<>();

		for (String attribute : attributes) {
			String[] keyValuePair = _KEY_VALUE_SEPARATOR_PATTERN.split(
				attribute);

			properties.put(keyValuePair[0], keyValuePair[1]);
		}

		return new ImageAdaptiveMediaConfigurationEntry(name, uuid, properties);
	}

	private static final Pattern _ATTRIBUTE_SEPARATOR_PATTERN = Pattern.compile(
		"\\s*;\\s*");

	private static final Pattern _FIELD_SEPARATOR_PATTERN = Pattern.compile(
		"\\s*:\\s*");

	private static final Pattern _KEY_VALUE_SEPARATOR_PATTERN = Pattern.compile(
		"\\s*=\\s*");

}