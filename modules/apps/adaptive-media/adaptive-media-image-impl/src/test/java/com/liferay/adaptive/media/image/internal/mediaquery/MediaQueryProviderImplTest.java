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

package com.liferay.adaptive.media.image.internal.mediaquery;

import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.mediaquery.Condition;
import com.liferay.adaptive.media.image.mediaquery.MediaQuery;
import com.liferay.adaptive.media.image.url.AdaptiveMediaImageURLFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.net.URI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Alejandro Tardín
 */
@RunWith(MockitoJUnitRunner.class)
public class MediaQueryProviderImplTest {

	@Before
	public void setUp() throws AdaptiveMediaException, PortalException {
		Mockito.when(
			_fileEntry.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		_mediaQueryProvider.setAdaptiveMediaImageURLFactory(
			_adaptiveMediaURLFactory);

		_mediaQueryProvider.setAdaptiveMediaImageConfigurationHelper(
			_adaptiveMediaImageConfigurationHelper);
	}

	@Test
	public void testCreatesAMediaQuery() throws Exception {
		_addConfigs(_createConfig("small", "uuid", 800, 1989, "adaptiveURL"));

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 1, mediaQueries.size());

		MediaQuery mediaQuery = mediaQueries.get(0);

		List<Condition> conditions = mediaQuery.getConditions();

		Assert.assertEquals("adaptiveURL", mediaQuery.getSrc());

		Assert.assertEquals(conditions.toString(), 1, conditions.size());
		Assert.assertEquals("max-width", conditions.get(0).getAttribute());
		Assert.assertEquals("1989px", conditions.get(0).getValue());
	}

	@Test
	public void testCreatesSeveralMediaQueries() throws Exception {
		_addConfigs(
			_createConfig("small", "uuid1", 800, 1986, "adaptiveURL1"),
			_createConfig("medium", "uuid2", 800, 1989, "adaptiveURL2"));

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals("adaptiveURL1", mediaQuery1.getSrc());

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());
		Assert.assertEquals("max-width", conditions1.get(0).getAttribute());
		Assert.assertEquals("1986px", conditions1.get(0).getValue());

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals("adaptiveURL2", mediaQuery2.getSrc());

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());
		Assert.assertEquals("max-width", conditions2.get(0).getAttribute());
		Assert.assertEquals("1989px", conditions2.get(0).getValue());
		Assert.assertEquals("min-width", conditions2.get(1).getAttribute());
		Assert.assertEquals("1986px", conditions2.get(1).getValue());
	}

	@Test
	public void testCreatesSeveralMediaQueriesSortedByWidth() throws Exception {
		_addConfigs(
			_createConfig("medium", "uuid2", 800, 1989, "adaptiveURL2"),
			_createConfig("small", "uuid1", 800, 1986, "adaptiveURL1"));

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals("adaptiveURL1", mediaQuery1.getSrc());

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());
		Assert.assertEquals("max-width", conditions1.get(0).getAttribute());
		Assert.assertEquals("1986px", conditions1.get(0).getValue());

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals("adaptiveURL2", mediaQuery2.getSrc());

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());
		Assert.assertEquals("max-width", conditions2.get(0).getAttribute());
		Assert.assertEquals("1989px", conditions2.get(0).getValue());
		Assert.assertEquals("min-width", conditions2.get(1).getAttribute());
		Assert.assertEquals("1986px", conditions2.get(1).getValue());
	}

	@Test
	public void testHDMediaQueriesApplies() throws Exception {
		_addConfigs(
			_createConfig(
				"small", "uuid1", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid2", 900, 1600, "http://small.hd.adaptive.com"),
			_createConfig(
				"big", "uuid3", 1900, 2500, "http://big.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 3, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(
			"http://small.adaptive.com, http://small.hd.adaptive.com 2x",
			mediaQuery1.getSrc());

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());
		Assert.assertEquals("max-width", conditions1.get(0).getAttribute());
		Assert.assertEquals("800px", conditions1.get(0).getValue());

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());
		Assert.assertEquals("max-width", conditions2.get(0).getAttribute());
		Assert.assertEquals("1600px", conditions2.get(0).getValue());
		Assert.assertEquals("min-width", conditions2.get(1).getAttribute());
		Assert.assertEquals("800px", conditions2.get(1).getValue());

		MediaQuery mediaQuery3 = mediaQueries.get(2);

		List<Condition> conditions3 = mediaQuery3.getConditions();

		Assert.assertEquals("http://big.adaptive.com", mediaQuery3.getSrc());

		Assert.assertEquals(conditions3.toString(), 2, conditions3.size());
		Assert.assertEquals("max-width", conditions3.get(0).getAttribute());
		Assert.assertEquals("2500px", conditions3.get(0).getValue());
		Assert.assertEquals("min-width", conditions3.get(1).getAttribute());
		Assert.assertEquals("1600px", conditions3.get(1).getValue());
	}

	@Test
	public void testHDMediaQueryAppliesWhenHeightHas1PXLessThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid1", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid2", 899, 1600,
				"http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(
			"http://small.adaptive.com, http://small.hd.adaptive.com 2x",
			mediaQuery1.getSrc());

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());
		Assert.assertEquals("max-width", conditions1.get(0).getAttribute());
		Assert.assertEquals("800px", conditions1.get(0).getValue());

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());
		Assert.assertEquals("max-width", conditions2.get(0).getAttribute());
		Assert.assertEquals("1600px", conditions2.get(0).getValue());
		Assert.assertEquals("min-width", conditions2.get(1).getAttribute());
		Assert.assertEquals("800px", conditions2.get(1).getValue());
	}

	@Test
	public void testHDMediaQueryAppliesWhenHeightHas1PXMoreThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid", 901, 1600, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(
			"http://small.adaptive.com, http://small.hd.adaptive.com 2x",
			mediaQuery1.getSrc());

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());
		Assert.assertEquals("max-width", conditions1.get(0).getAttribute());
		Assert.assertEquals("800px", conditions1.get(0).getValue());

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());
		Assert.assertEquals("max-width", conditions2.get(0).getAttribute());
		Assert.assertEquals("1600px", conditions2.get(0).getValue());
		Assert.assertEquals("min-width", conditions2.get(1).getAttribute());
		Assert.assertEquals("800px", conditions2.get(1).getValue());
	}

	@Test
	public void testHDMediaQueryAppliesWhenWidthHas1PXLessThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid", 900, 1599, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(
			"http://small.adaptive.com, http://small.hd.adaptive.com 2x",
			mediaQuery1.getSrc());

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());
		Assert.assertEquals("max-width", conditions1.get(0).getAttribute());
		Assert.assertEquals("800px", conditions1.get(0).getValue());

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());
		Assert.assertEquals("max-width", conditions2.get(0).getAttribute());
		Assert.assertEquals("1599px", conditions2.get(0).getValue());
		Assert.assertEquals("min-width", conditions2.get(1).getAttribute());
		Assert.assertEquals("800px", conditions2.get(1).getValue());
	}

	@Test
	public void testHDMediaQueryAppliesWhenWidthHas1PXMoreThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid", 900, 1601, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals(
			"http://small.adaptive.com, http://small.hd.adaptive.com 2x",
			mediaQuery1.getSrc());

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());
		Assert.assertEquals("max-width", conditions1.get(0).getAttribute());
		Assert.assertEquals("800px", conditions1.get(0).getValue());

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());
		Assert.assertEquals("max-width", conditions2.get(0).getAttribute());
		Assert.assertEquals("1601px", conditions2.get(0).getValue());
		Assert.assertEquals("min-width", conditions2.get(1).getAttribute());
		Assert.assertEquals("800px", conditions2.get(1).getValue());
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenHeightHas2PXLessThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid", 898, 1600, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals("http://small.adaptive.com", mediaQuery1.getSrc());

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());
		Assert.assertEquals("max-width", conditions1.get(0).getAttribute());
		Assert.assertEquals("800px", conditions1.get(0).getValue());

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());
		Assert.assertEquals("max-width", conditions2.get(0).getAttribute());
		Assert.assertEquals("1600px", conditions2.get(0).getValue());
		Assert.assertEquals("min-width", conditions2.get(1).getAttribute());
		Assert.assertEquals("800px", conditions2.get(1).getValue());
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenHeightHas2PXMoreThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid", 902, 1600, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals("http://small.adaptive.com", mediaQuery1.getSrc());

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());
		Assert.assertEquals("max-width", conditions1.get(0).getAttribute());
		Assert.assertEquals("800px", conditions1.get(0).getValue());

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());
		Assert.assertEquals("max-width", conditions2.get(0).getAttribute());
		Assert.assertEquals("1600px", conditions2.get(0).getValue());
		Assert.assertEquals("min-width", conditions2.get(1).getAttribute());
		Assert.assertEquals("800px", conditions2.get(1).getValue());
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenWidthHas2PXLessThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid", 900, 1598, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals("http://small.adaptive.com", mediaQuery1.getSrc());

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());
		Assert.assertEquals("max-width", conditions1.get(0).getAttribute());
		Assert.assertEquals("800px", conditions1.get(0).getValue());

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());
		Assert.assertEquals("max-width", conditions2.get(0).getAttribute());
		Assert.assertEquals("1598px", conditions2.get(0).getValue());
		Assert.assertEquals("min-width", conditions2.get(1).getAttribute());
		Assert.assertEquals("800px", conditions2.get(1).getValue());
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenWidthHas2PXMoreThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid", 900, 1602, "http://small.hd.adaptive.com"));

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			_fileEntry);

		Assert.assertEquals(mediaQueries.toString(), 2, mediaQueries.size());

		MediaQuery mediaQuery1 = mediaQueries.get(0);

		List<Condition> conditions1 = mediaQuery1.getConditions();

		Assert.assertEquals("http://small.adaptive.com", mediaQuery1.getSrc());

		Assert.assertEquals(conditions1.toString(), 1, conditions1.size());
		Assert.assertEquals("max-width", conditions1.get(0).getAttribute());
		Assert.assertEquals("800px", conditions1.get(0).getValue());

		MediaQuery mediaQuery2 = mediaQueries.get(1);

		List<Condition> conditions2 = mediaQuery2.getConditions();

		Assert.assertEquals(
			"http://small.hd.adaptive.com", mediaQuery2.getSrc());

		Assert.assertEquals(conditions2.toString(), 2, conditions2.size());
		Assert.assertEquals("max-width", conditions2.get(0).getAttribute());
		Assert.assertEquals("1602px", conditions2.get(0).getValue());
		Assert.assertEquals("min-width", conditions2.get(1).getAttribute());
		Assert.assertEquals("800px", conditions2.get(1).getValue());
	}

	@Test
	public void testReturnsNoMediaQueriesIfThereAreNoConfigs()
		throws Exception {

		_addConfigs();

		Assert.assertEquals(
			0, _mediaQueryProvider.getMediaQueries(_fileEntry).size());
	}

	private void _addConfigs(
			AdaptiveMediaImageConfigurationEntry...
				adaptiveMediaImageConfigurationEntries)
		throws Exception {

		Mockito.when(
			_adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntries(_COMPANY_ID)
		).thenReturn(
			Arrays.asList(adaptiveMediaImageConfigurationEntries)
		);
	}

	private AdaptiveMediaImageConfigurationEntry _createConfig(
			final String name, final String uuid, final int height,
			final int width, String url)
		throws Exception {

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntry() {

				@Override
				public String getName() {
					return name;
				}

				@Override
				public Map<String, String> getProperties() {
					Map<String, String> properties = new HashMap<>();

					properties.put("max-height", String.valueOf(height));
					properties.put("max-width", String.valueOf(width));

					return properties;
				}

				@Override
				public String getUUID() {
					return uuid;
				}

				@Override
				public boolean isEnabled() {
					return true;
				}

			};

		Mockito.when(
			_adaptiveMediaURLFactory.createFileEntryURL(
				_fileEntry.getFileVersion(), configurationEntry)
		).thenReturn(
			URI.create(url)
		);

		return configurationEntry;
	}

	private static final long _COMPANY_ID = 1L;

	@Mock
	private AdaptiveMediaImageConfigurationHelper
		_adaptiveMediaImageConfigurationHelper;

	@Mock
	private AdaptiveMediaImageURLFactory _adaptiveMediaURLFactory;

	@Mock
	private FileEntry _fileEntry;

	private final MediaQueryProviderImpl _mediaQueryProvider =
		new MediaQueryProviderImpl();

}