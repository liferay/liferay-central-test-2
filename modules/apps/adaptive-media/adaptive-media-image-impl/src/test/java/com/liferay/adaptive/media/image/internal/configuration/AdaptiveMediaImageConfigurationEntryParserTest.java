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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveMediaImageConfigurationEntryParserTest {

	@Test
	public void testDisabledValidString() {
		AdaptiveMediaImageConfigurationEntry configurationEntry =
			_configurationEntryParser.parse(
				"test:12345:max-height=100;max-width=200:enabled=false");

		Assert.assertEquals("test", configurationEntry.getName());
		Assert.assertEquals("12345", configurationEntry.getUUID());
		Assert.assertFalse(configurationEntry.isEnabled());

		Map<String, String> properties = configurationEntry.getProperties();

		Assert.assertEquals("100", properties.get("max-height"));
		Assert.assertEquals("200", properties.get("max-width"));
		Assert.assertEquals(properties.toString(), 2, properties.size());
	}

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

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				"test", "12345", properties);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals(
			"test:12345:max-height=100:enabled=true", configurationString);
	}

	@Test
	public void testGetConfigurationStringWithMaxHeightAndMaxWidth() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				"test", "12345", properties);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals(
			"test:12345:max-height=100;max-width=200:enabled=true",
			configurationString);
	}

	@Test
	public void testGetConfigurationStringWithMaxWidth() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				"test", "12345", properties);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals(
			"test:12345:max-width=200:enabled=true", configurationString);
	}

	@Test
	public void testGetConfigurationStringWithNoProperties() {
		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				"test", "12345", Collections.emptyMap());

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals("test:12345::enabled=true", configurationString);
	}

	@Test
	public void testGetDisabledConfigurationStringWithMaxHeight() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				"test", "12345", properties, false);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals(
			"test:12345:max-height=100:enabled=false", configurationString);
	}

	@Test
	public void testGetDisabledConfigurationStringWithMaxHeightAndMaxWidth() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				"test", "12345", properties, false);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals(
			"test:12345:max-height=100;max-width=200:enabled=false",
			configurationString);
	}

	@Test
	public void testGetDisabledConfigurationStringWithMaxWidth() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				"test", "12345", properties, false);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals(
			"test:12345:max-width=200:enabled=false", configurationString);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidEnabledAttribute() {
		_configurationEntryParser.parse(
			"test:12345:max-height=100;max-width=200:disabled=true");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingAttributesField() {
		_configurationEntryParser.parse("test:12345");
	}

	@Test
	public void testMissingEnabledAttributeDefaultsEnabled() {
		AdaptiveMediaImageConfigurationEntry configurationEntry =
			_configurationEntryParser.parse(
				"test:12345:max-height=100;max-width=200");

		Assert.assertEquals("test", configurationEntry.getName());
		Assert.assertEquals("12345", configurationEntry.getUUID());
		Assert.assertTrue(configurationEntry.isEnabled());

		Map<String, String> properties = configurationEntry.getProperties();

		Assert.assertEquals("100", properties.get("max-height"));
		Assert.assertEquals("200", properties.get("max-width"));
		Assert.assertEquals(properties.toString(), 2, properties.size());
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
		AdaptiveMediaImageConfigurationEntry configurationEntry =
			_configurationEntryParser.parse(
				"test:12345:max-height=100;max-width=200:enabled=true");

		Assert.assertEquals("test", configurationEntry.getName());
		Assert.assertEquals("12345", configurationEntry.getUUID());
		Assert.assertTrue(configurationEntry.isEnabled());

		Map<String, String> properties = configurationEntry.getProperties();

		Assert.assertEquals("100", properties.get("max-height"));
		Assert.assertEquals("200", properties.get("max-width"));
		Assert.assertEquals(properties.toString(), 2, properties.size());
	}

	private final AdaptiveMediaImageConfigurationEntryParser
		_configurationEntryParser =
			new AdaptiveMediaImageConfigurationEntryParser();

}