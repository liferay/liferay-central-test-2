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

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * Parses ConfigAdmin configuration entries.
 *
 * <p>
 * A configuration entry must be a string of the following form:
 * </p>
 *
 * <code>
 * name:uuid:key0=val0;key1=val1;...;keyN=valN
 * </code>
 *
 * <p>
 * Where ...
 * </p>
 *
 * <ul>
 * <li>
 * <code>name</code> is an arbitrary {@link String}
 * </li>
 * <li>
 * <code>uuid</code> is a unique identifier. No two configuration entries should
 * have the same UUID.
 * </li>
 * <li>
 * The key and value pairs can be anything, but consumers of
 * the resulting {@link ImageAdaptiveMediaConfigurationEntry} might
 * require a particular set of attributes.
 * </li>
 * </ul>
 *
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, service = ImageAdaptiveMediaConfigurationEntryParser.class
)
public class ImageAdaptiveMediaConfigurationEntryParser {

	/**
	 * Returns a configuration entry parsed from the configuration line's data.
	 *
	 * @param  s the configuration line to parse
	 * @return a {@link ImageAdaptiveMediaConfigurationEntry} with the line data
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

		return new ImageAdaptiveMediaConfigurationEntryImpl(
			name, uuid, properties);
	}

	public String getConfigurationString(
		ImageAdaptiveMediaConfigurationEntry configurationEntry) {

		StringBundler sb = new StringBundler();

		sb.append(configurationEntry.getName());
		sb.append(StringPool.COLON);
		sb.append(configurationEntry.getUUID());
		sb.append(StringPool.COLON);

		Map<String, String> properties = configurationEntry.getProperties();

		if (properties.get("height") != null) {
			int height = GetterUtil.getInteger(properties.get("height"));

			sb.append(height);

			if (properties.get("width") != null) {
				sb.append(StringPool.SEMICOLON);
			}
		}

		if (properties.get("width") != null) {
			int width = GetterUtil.getInteger(properties.get("width"));

			sb.append(width);
		}

		return sb.toString();
	}

	private static final Pattern _ATTRIBUTE_SEPARATOR_PATTERN = Pattern.compile(
		"\\s*;\\s*");

	private static final Pattern _FIELD_SEPARATOR_PATTERN = Pattern.compile(
		"\\s*:\\s*");

	private static final Pattern _KEY_VALUE_SEPARATOR_PATTERN = Pattern.compile(
		"\\s*=\\s*");

}