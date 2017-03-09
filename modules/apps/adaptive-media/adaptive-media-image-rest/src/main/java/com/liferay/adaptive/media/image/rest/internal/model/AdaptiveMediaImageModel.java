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

package com.liferay.adaptive.media.image.rest.internal.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageAttribute;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Alejandro Hernández
 */
public class AdaptiveMediaImageModel {

	public AdaptiveMediaImageModel() {
	}

	public AdaptiveMediaImageModel(
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia, String url,
		AdaptiveMediaImageConfigurationEntry configurationEntry) {

		_url = url;

		_configurationEntryModel =
			new AdaptiveMediaImageConfigurationEntryModel(configurationEntry);

		AdaptiveMediaImageAttribute.allowedAttributes().forEach(
			(attributeName, attribute) -> _parseAttributes(
				adaptiveMedia, attributeName, attribute));

		AdaptiveMediaAttribute.allowedAttributes().forEach(
			(attributeName, attribute) -> _parseAttributes(
				adaptiveMedia, attributeName, attribute));
	}

	@JsonAnySetter
	public void addProperty(String key, Object value) {
		_properties.put(key, value);
	}

	@JsonProperty("configuration")
	public AdaptiveMediaImageConfigurationEntryModel
		getConfigurationEntryModel() {

		return _configurationEntryModel;
	}

	@JsonAnyGetter
	public Map<String, Object> getProperties() {
		return _properties;
	}

	public String getUrl() {
		return _url;
	}

	public void setConfigurationEntryModel(
		AdaptiveMediaImageConfigurationEntryModel configurationEntryModel) {

		_configurationEntryModel = configurationEntryModel;
	}

	public void setUrl(String url) {
		_url = url;
	}

	private void _parseAttributes(
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia,
		String attributeName, AdaptiveMediaAttribute attribute) {

		Optional attributeValueOptional = adaptiveMedia.getAttributeValue(
			attribute);

		attributeValueOptional.ifPresent(
			attributeValue -> _properties.put(attributeName, attributeValue));
	}

	private AdaptiveMediaImageConfigurationEntryModel _configurationEntryModel;
	private final Map<String, Object> _properties = new HashMap<>();
	private String _url;

}