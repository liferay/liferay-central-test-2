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

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.portal.kernel.util.HttpUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adolfo Pérez
 */
@PrepareForTest(HttpUtil.class)
@RunWith(PowerMockRunner.class)
public class AdaptiveMediaImageConfigurationEntryParserTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mockStatic(HttpUtil.class);

		when(
			HttpUtil.encodeURL(Mockito.eq("desc"))
		).thenReturn(
			"desc"
		);

		when(
			HttpUtil.decodeURL(Mockito.eq("desc"))
		).thenReturn(
			"desc"
		);

		when(
			HttpUtil.encodeURL(Mockito.eq("test"))
		).thenReturn(
			"test"
		);

		when(
			HttpUtil.decodeURL(Mockito.eq("test"))
		).thenReturn(
			"test"
		);
	}

	@Test
	public void testDisabledValidString() {
		AdaptiveMediaImageConfigurationEntry configurationEntry =
			_configurationEntryParser.parse(
				"test:desc:12345:max-height=100;max-width=200:enabled=false");

		Assert.assertEquals("test", configurationEntry.getName());
		Assert.assertEquals("desc", configurationEntry.getDescription());
		Assert.assertEquals("12345", configurationEntry.getUUID());
		Assert.assertFalse(configurationEntry.isEnabled());

		Map<String, String> properties = configurationEntry.getProperties();

		Assert.assertEquals("100", properties.get("max-height"));
		Assert.assertEquals("200", properties.get("max-width"));
		Assert.assertEquals(properties.toString(), 2, properties.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyAttributes() {
		_configurationEntryParser.parse("test:desc:12345:");
	}

	@Test
	public void testEmptyDescription() {
		_configurationEntryParser.parse(
			"test::12345:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyName() {
		_configurationEntryParser.parse(
			":desc:12345:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyString() {
		_configurationEntryParser.parse("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyUUID() {
		_configurationEntryParser.parse(
			"test:desc::max-height=100;max-width=200");
	}

	@Test
	public void testEncodedDescription() {
		when(
			HttpUtil.encodeURL(Mockito.eq("desc:;"))
		).thenReturn(
			"desc%3A%3B"
		);

		when(
			HttpUtil.decodeURL(Mockito.eq("desc%3A%3B"))
		).thenReturn(
			"desc:;"
		);

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			_configurationEntryParser.parse(
				"test:desc%3A%3B:12345:max-height=100;max-width=200");

		Assert.assertEquals("test", configurationEntry.getName());
		Assert.assertEquals("desc:;", configurationEntry.getDescription());
		Assert.assertEquals("12345", configurationEntry.getUUID());

		Map<String, String> properties = configurationEntry.getProperties();

		Assert.assertEquals("100", properties.get("max-height"));
		Assert.assertEquals("200", properties.get("max-width"));
		Assert.assertEquals(properties.toString(), 2, properties.size());
	}

	@Test
	public void testEncodedName() {
		when(
			HttpUtil.encodeURL(Mockito.eq("test:;"))
		).thenReturn(
			"test%3A%3B"
		);

		when(
			HttpUtil.decodeURL(Mockito.eq("test%3A%3B"))
		).thenReturn(
			"test:;"
		);

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			_configurationEntryParser.parse(
				"test%3A%3B:desc:12345:max-height=100;max-width=200");

		Assert.assertEquals("test:;", configurationEntry.getName());
		Assert.assertEquals("desc", configurationEntry.getDescription());
		Assert.assertEquals("12345", configurationEntry.getUUID());

		Map<String, String> properties = configurationEntry.getProperties();

		Assert.assertEquals("100", properties.get("max-height"));
		Assert.assertEquals("200", properties.get("max-width"));
		Assert.assertEquals(properties.toString(), 2, properties.size());
	}

	@Test
	public void testGetConfigurationStringWithMaxHeight() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, true);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals(
			"test:desc:12345:max-height=100:enabled=true", configurationString);
	}

	@Test
	public void testGetConfigurationStringWithMaxHeightAndMaxWidth() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, true);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals(
			"test:desc:12345:max-height=100;max-width=200:enabled=true",
			configurationString);
	}

	@Test
	public void testGetConfigurationStringWithMaxWidth() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, true);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals(
			"test:desc:12345:max-width=200:enabled=true", configurationString);
	}

	@Test
	public void testGetConfigurationStringWithNoProperties() {
		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				"test", "desc", "12345", Collections.emptyMap(), true);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals(
			"test:desc:12345::enabled=true", configurationString);
	}

	@Test
	public void testGetDisabledConfigurationStringWithMaxHeight() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, false);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals(
			"test:desc:12345:max-height=100:enabled=false",
			configurationString);
	}

	@Test
	public void testGetDisabledConfigurationStringWithMaxHeightAndMaxWidth() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, false);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals(
			"test:desc:12345:max-height=100;max-width=200:enabled=false",
			configurationString);
	}

	@Test
	public void testGetDisabledConfigurationStringWithMaxWidth() {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-width", "200");

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, false);

		String configurationString =
			_configurationEntryParser.getConfigurationString(
				configurationEntry);

		Assert.assertEquals(
			"test:desc:12345:max-width=200:enabled=false", configurationString);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidEnabledAttribute() {
		_configurationEntryParser.parse(
			"test:desc:12345:max-height=100;max-width=200:disabled=true");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingAttributesField() {
		_configurationEntryParser.parse("test:desc:12345");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingDescription() {
		_configurationEntryParser.parse("12345:max-height=100;max-width=200");
	}

	@Test
	public void testMissingEnabledAttributeDefaultsEnabled() {
		AdaptiveMediaImageConfigurationEntry configurationEntry =
			_configurationEntryParser.parse(
				"test:desc:12345:max-height=100;max-width=200");

		Assert.assertEquals("test", configurationEntry.getName());
		Assert.assertEquals("12345", configurationEntry.getUUID());
		Assert.assertEquals("desc", configurationEntry.getDescription());
		Assert.assertTrue(configurationEntry.isEnabled());

		Map<String, String> properties = configurationEntry.getProperties();

		Assert.assertEquals("100", properties.get("max-height"));
		Assert.assertEquals("200", properties.get("max-width"));
		Assert.assertEquals(properties.toString(), 2, properties.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingName() {
		_configurationEntryParser.parse(
			"12345:desc:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingUUID() {
		_configurationEntryParser.parse(
			"test:desc:max-height=100;max-width=200");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullString() {
		_configurationEntryParser.parse(null);
	}

	@Test
	public void testValidString() {
		AdaptiveMediaImageConfigurationEntry configurationEntry =
			_configurationEntryParser.parse(
				"test:desc:12345:max-height=100;max-width=200:enabled=true");

		Assert.assertEquals("test", configurationEntry.getName());
		Assert.assertEquals("desc", configurationEntry.getDescription());
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