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

package com.liferay.adaptive.media.image.jaxrs.internal;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;

import java.util.HashMap;
import java.util.Map;

/**
 * Serves as a proxy for an
 * {@link ImageAdaptiveMediaConfigurationEntry}'s API layer.
 *
 * @author Alejandro Hernández
 *
 */
public class ImageAdaptiveMediaConfigRepr {

	public ImageAdaptiveMediaConfigRepr() {
	}

	public ImageAdaptiveMediaConfigRepr(
		ImageAdaptiveMediaConfigurationEntry configurationEntry) {

		_properties = configurationEntry.getProperties();

		_name = configurationEntry.getName();

		_uuid = configurationEntry.getUUID();
	}

	@JsonAnySetter
	public void addProperty(String key, String value) {
		_properties.put(key, value);
	}

	public String getName() {
		return _name;
	}

	@JsonAnyGetter
	public Map<String, String> getProperties() {
		return _properties;
	}

	@JsonProperty("id")
	public String getUuid() {
		return _uuid;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	private String _name;
	private Map<String, String> _properties = new HashMap<>();
	private String _uuid;

}