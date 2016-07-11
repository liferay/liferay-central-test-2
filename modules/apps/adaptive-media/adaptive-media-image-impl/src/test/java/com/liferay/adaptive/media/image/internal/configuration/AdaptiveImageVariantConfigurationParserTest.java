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
public class AdaptiveImageVariantConfigurationParserTest {

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyName() {
		_adaptiveImageVariantConfigurationParser.parse(
			":12345:height=100;width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyProperties() {
		_adaptiveImageVariantConfigurationParser.parse("test:12345:");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyString() {
		_adaptiveImageVariantConfigurationParser.parse("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyUUID() {
		_adaptiveImageVariantConfigurationParser.parse(
			"test::height=100;width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingPropertiesField() {
		_adaptiveImageVariantConfigurationParser.parse("test:12345");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullString() {
		_adaptiveImageVariantConfigurationParser.parse(null);
	}

	@Test
	public void testValidString() {
		AdaptiveImageVariantConfiguration adaptiveImageVariantConfiguration =
			_adaptiveImageVariantConfigurationParser.parse(
				"test:12345:height=100;width=200");

		Assert.assertEquals(
			"test", adaptiveImageVariantConfiguration.getName());
		Assert.assertEquals(
			"12345", adaptiveImageVariantConfiguration.getUUID());

		Map<String, String> properties =
			adaptiveImageVariantConfiguration.getProperties();

		Assert.assertEquals("100", properties.get("height"));
		Assert.assertEquals("200", properties.get("width"));
		Assert.assertEquals(2, properties.size());
	}

	private final AdaptiveImageVariantConfigurationParser
		_adaptiveImageVariantConfigurationParser =
			new AdaptiveImageVariantConfigurationParser();

}