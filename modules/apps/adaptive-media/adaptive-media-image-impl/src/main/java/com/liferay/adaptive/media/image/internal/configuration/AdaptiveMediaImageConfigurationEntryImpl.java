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

import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.portal.kernel.util.StringPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveMediaImageConfigurationEntryImpl
	implements AdaptiveMediaImageConfigurationEntry {

	public AdaptiveMediaImageConfigurationEntryImpl(
		String name, String uuid, Map<String, String> properties) {

		this(name, StringPool.BLANK, uuid, properties, true);
	}

	public AdaptiveMediaImageConfigurationEntryImpl(
		String name, String description, String uuid,
		Map<String, String> properties, boolean enabled) {

		_name = name;
		_description = description;
		_uuid = uuid;
		_properties = properties;
		_enabled = enabled;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AdaptiveMediaImageConfigurationEntryImpl)) {
			return false;
		}

		AdaptiveMediaImageConfigurationEntryImpl other =
			(AdaptiveMediaImageConfigurationEntryImpl)obj;

		if (_name.equals(other._name) && _uuid.equals(other._uuid) &&
			(_enabled == other._enabled) &&
			_properties.equals(other._properties)) {

			return true;
		}

		return false;
	}

	@Override
	public String getDescription() {
		return _description;
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
	public int hashCode() {
		int hash =
			_name.hashCode() ^ _uuid.hashCode() ^ Boolean.hashCode(_enabled);

		for (Map.Entry<String, String> entry : _properties.entrySet()) {
			hash ^= entry.hashCode();
		}

		return hash;
	}

	@Override
	public boolean isEnabled() {
		return _enabled;
	}

	private final String _description;
	private final boolean _enabled;
	private final String _name;
	private final Map<String, String> _properties;
	private final String _uuid;

}