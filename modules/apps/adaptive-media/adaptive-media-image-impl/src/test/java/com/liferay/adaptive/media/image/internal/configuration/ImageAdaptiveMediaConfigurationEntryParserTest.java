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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaConfigurationEntryParserTest {

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyAttributes() {
		_configurationEntryParser.parse("test:12345:");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyName() {
		_configurationEntryParser.parse(":12345:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyString() {
		_configurationEntryParser.parse("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyUUID() {
		_configurationEntryParser.parse("test::max-height=100;max-width=200");
	}

	@Test
	public void testGetConfigurationStringWithMaxHeight() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				"test", "12345", properties);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals("test:12345:max-height=100", configurationString);
	}

	@Test
	public void testGetConfigurationStringWithMaxHeightAndMaxWidth() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "200");

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				"test", "12345", properties);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals(
			"test:12345:max-height=100;max-width=200", configurationString);
	}

	@Test
	public void testGetConfigurationStringWithMaxWidth() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-width", "200");

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				"test", "12345", properties);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals("test:12345:max-width=200", configurationString);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingAttributesField() {
		_configurationEntryParser.parse("test:12345");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingName() {
		_configurationEntryParser.parse("12345:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingUUID() {
		_configurationEntryParser.parse("test:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullString() {
		_configurationEntryParser.parse(null);
	}

	@Test
	public void testValidString() {
		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			_configurationEntryParser.parse(
				"test:12345:max-height=100;max-width=200");

		Assert.assertEquals("test", configurationEntry.getName());
		Assert.assertEquals("12345", configurationEntry.getUUID());

		Map<String, String> properties = configurationEntry.getProperties();

		Assert.assertEquals("100", properties.get("max-height"));
		Assert.assertEquals("200", properties.get("max-width"));
		Assert.assertEquals(2, properties.size());
	}

	private final ImageAdaptiveMediaConfigurationEntryParser
		_configurationEntryParser =
			new ImageAdaptiveMediaConfigurationEntryParser();

}