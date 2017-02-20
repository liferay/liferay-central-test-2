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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaConfigurationEntryImpl
	implements ImageAdaptiveMediaConfigurationEntry {

	public ImageAdaptiveMediaConfigurationEntryImpl(
		String name, String uuid, Map<String, String> properties) {

		this(name, uuid, properties, true);
	}

	public ImageAdaptiveMediaConfigurationEntryImpl(
		String name, String uuid, Map<String, String> properties,
		boolean enabled) {

		_name = name;
		_uuid = uuid;
		_properties = properties;
		_enabled = enabled;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Map<String, String> getProperties() {
		return new HashMap<>(_properties);
	}

	@Override
	public String getUUID() {
		return _uuid;
	}

	@Override
	public boolean isEnabled() {
		return _enabled;
	}

	private final boolean _enabled;
	private final String _name;
	private final Map<String, String> _properties;
	private final String _uuid;

}