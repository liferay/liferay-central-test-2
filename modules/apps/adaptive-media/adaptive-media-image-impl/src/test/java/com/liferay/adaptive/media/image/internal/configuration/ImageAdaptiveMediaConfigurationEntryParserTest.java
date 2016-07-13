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

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaConfigurationEntryParserTest {

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyName() {
		_configurationEntryParser.parse(":12345:height=100;width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyProperties() {
		_configurationEntryParser.parse("test:12345:");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyString() {
		_configurationEntryParser.parse("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyUUID() {
		_configurationEntryParser.parse("test::height=100;width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingPropertiesField() {
		_configurationEntryParser.parse("test:12345");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullString() {
		_configurationEntryParser.parse(null);
	}

	@Test
	public void testValidString() {
		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			_configurationEntryParser.parse("test:12345:height=100;width=200");

		Assert.assertEquals("test", configurationEntry.getName());
		Assert.assertEquals("12345", configurationEntry.getUUID());

		Map<String, String> properties = configurationEntry.getProperties();

		Assert.assertEquals("100", properties.get("height"));
		Assert.assertEquals("200", properties.get("width"));
		Assert.assertEquals(2, properties.size());
	}

	private final ImageAdaptiveMediaConfigurationEntryParser
		_configurationEntryParser =
			new ImageAdaptiveMediaConfigurationEntryParser();

}