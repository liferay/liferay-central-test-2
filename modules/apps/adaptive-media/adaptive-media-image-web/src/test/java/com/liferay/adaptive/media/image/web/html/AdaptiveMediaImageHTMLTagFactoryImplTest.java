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

package com.liferay.adaptive.media.image.web.html;

import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.url.AdaptiveMediaImageURLFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;

import java.net.URI;

import java.util.Arrays;
import java.util.HashMap;
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
public class AdaptiveMediaImageHTMLTagFactoryImplTest {

	@Before
	public void setUp() throws AdaptiveMediaException, PortalException {
		Mockito.when(
			_fileEntry.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);
		_adaptiveMediaImageHTMLTagFactory.setAdaptiveMediaImageURLFactory(
			_adaptiveMediaURLFactory);
		_adaptiveMediaImageHTMLTagFactory.
			setAdaptiveMediaImageConfigurationHelper(
				_adaptiveMediaImageConfigurationHelper);
	}

	@Test
	public void testCreatesAPictureTag() throws Exception {
		_addConfigs(_createConfig("small", "uuid", 800, 1989, "adaptiveURL"));

		String originalImgTag =
			"<img src=\"originalURL\" data-fileEntryId=\"1234\"/>";
		StringBundler expectedSB = new StringBundler(5);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:1989px)\" ");
		expectedSB.append("srcset=\"adaptiveURL\"/>");
		expectedSB.append("<img src=\"originalURL\"/>");
		expectedSB.append("</picture>");

		String pictureTag = _adaptiveMediaImageHTMLTagFactory.create(
			originalImgTag, _fileEntry);

		Assert.assertEquals(expectedSB.toString(), pictureTag);
	}

	@Test
	public void testCreatesAPictureTagWithSeveralConfigs() throws Exception {
		_addConfigs(
			_createConfig("small", "uuid1", 800, 1986, "adaptiveURL1"),
			_createConfig("medium", "uuid2", 800, 1989, "adaptiveURL2"));

		String originalImgTag =
			"<img src=\"originalURL\" data-fileEntryId=\"1234\"/>";
		StringBundler expectedSB = new StringBundler(8);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:1986px)\" ");
		expectedSB.append("srcset=\"adaptiveURL1\"/>");
		expectedSB.append("<source media=\"(max-width:1989px) ");
		expectedSB.append("and (min-width:1986px)\" ");
		expectedSB.append("srcset=\"adaptiveURL2\"/>");
		expectedSB.append("<img src=\"originalURL\"/>");
		expectedSB.append("</picture>");

		String pictureTag = _adaptiveMediaImageHTMLTagFactory.create(
			originalImgTag, _fileEntry);

		Assert.assertEquals(expectedSB.toString(), pictureTag);
	}

	@Test
	public void testCreatesAPictureTagWithSeveralConfigsSortedByWidth()
		throws Exception {

		_addConfigs(
			_createConfig("medium", "uuid2", 800, 1989, "adaptiveURL2"),
			_createConfig("small", "uuid1", 800, 1986, "adaptiveURL1"));

		String originalImgTag =
			"<img src=\"originalURL\" data-fileEntryId=\"1234\"/>";
		StringBundler expectedSB = new StringBundler(8);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:1986px)\" ");
		expectedSB.append("srcset=\"adaptiveURL1\"/>");
		expectedSB.append("<source media=\"(max-width:1989px) ");
		expectedSB.append("and (min-width:1986px)\" ");
		expectedSB.append("srcset=\"adaptiveURL2\"/>");
		expectedSB.append("<img src=\"originalURL\"/>");
		expectedSB.append("</picture>");

		String pictureTag = _adaptiveMediaImageHTMLTagFactory.create(
			originalImgTag, _fileEntry);

		Assert.assertEquals(expectedSB.toString(), pictureTag);
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

		StringBundler expectedSB = new StringBundler(8);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.adaptive.com, ");
		expectedSB.append("http://small.hd.adaptive.com 2x\"/>");
		expectedSB.append("<source media=\"(max-width:1600px) and ");
		expectedSB.append("(min-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.hd.adaptive.com\"/>");
		expectedSB.append("<source media=\"(max-width:2500px) and ");
		expectedSB.append("(min-width:1600px)\" ");
		expectedSB.append("srcset=\"http://big.adaptive.com\"/>");
		expectedSB.append("<img src=\"adaptable\"/>");
		expectedSB.append("</picture>");

		Assert.assertEquals(
			expectedSB.toString(),
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>",
				_fileEntry));
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

		StringBundler expectedSB = new StringBundler(8);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.adaptive.com, ");
		expectedSB.append("http://small.hd.adaptive.com 2x\"/>");
		expectedSB.append("<source media=\"(max-width:1600px) and ");
		expectedSB.append("(min-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.hd.adaptive.com\"/>");
		expectedSB.append("<img src=\"adaptable\"/>");
		expectedSB.append("</picture>");

		Assert.assertEquals(
			expectedSB.toString(),
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>",
				_fileEntry));
	}

	@Test
	public void testHDMediaQueryAppliesWhenHeightHas1PXMoreThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid", 901, 1600, "http://small.hd.adaptive.com"));

		StringBundler expectedSB = new StringBundler(8);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.adaptive.com, ");
		expectedSB.append("http://small.hd.adaptive.com 2x\"/>");
		expectedSB.append("<source media=\"(max-width:1600px) and ");
		expectedSB.append("(min-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.hd.adaptive.com\"/>");
		expectedSB.append("<img src=\"adaptable\"/>");
		expectedSB.append("</picture>");

		Assert.assertEquals(
			expectedSB.toString(),
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>",
				_fileEntry));
	}

	@Test
	public void testHDMediaQueryAppliesWhenWidthHas1PXLessThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid", 900, 1599, "http://small.hd.adaptive.com"));

		StringBundler expectedSB = new StringBundler(8);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.adaptive.com, ");
		expectedSB.append("http://small.hd.adaptive.com 2x\"/>");
		expectedSB.append("<source media=\"(max-width:1599px) and ");
		expectedSB.append("(min-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.hd.adaptive.com\"/>");
		expectedSB.append("<img src=\"adaptable\"/>");
		expectedSB.append("</picture>");

		Assert.assertEquals(
			expectedSB.toString(),
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>",
				_fileEntry));
	}

	@Test
	public void testHDMediaQueryAppliesWhenWidthHas1PXMoreThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid", 900, 1601, "http://small.hd.adaptive.com"));

		StringBundler expectedSB = new StringBundler(8);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.adaptive.com, ");
		expectedSB.append("http://small.hd.adaptive.com 2x\"/>");
		expectedSB.append("<source media=\"(max-width:1601px) and ");
		expectedSB.append("(min-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.hd.adaptive.com\"/>");
		expectedSB.append("<img src=\"adaptable\"/>");
		expectedSB.append("</picture>");

		Assert.assertEquals(
			expectedSB.toString(),
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>",
				_fileEntry));
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenHeightHas2PXLessThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid", 898, 1600, "http://small.hd.adaptive.com"));

		StringBundler expectedSB = new StringBundler(8);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.adaptive.com\"/>");
		expectedSB.append("<source media=\"(max-width:1600px) and ");
		expectedSB.append("(min-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.hd.adaptive.com\"/>");
		expectedSB.append("<img src=\"adaptable\"/>");
		expectedSB.append("</picture>");

		Assert.assertEquals(
			expectedSB.toString(),
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>",
				_fileEntry));
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenHeightHas2PXMoreThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid", 902, 1600, "http://small.hd.adaptive.com"));

		StringBundler expectedSB = new StringBundler(8);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.adaptive.com\"/>");
		expectedSB.append("<source media=\"(max-width:1600px) and ");
		expectedSB.append("(min-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.hd.adaptive.com\"/>");
		expectedSB.append("<img src=\"adaptable\"/>");
		expectedSB.append("</picture>");

		Assert.assertEquals(
			expectedSB.toString(),
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>",
				_fileEntry));
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenWidthHas2PXLessThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid", 900, 1598, "http://small.hd.adaptive.com"));

		StringBundler expectedSB = new StringBundler(8);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.adaptive.com\"/>");
		expectedSB.append("<source media=\"(max-width:1598px) and ");
		expectedSB.append("(min-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.hd.adaptive.com\"/>");
		expectedSB.append("<img src=\"adaptable\"/>");
		expectedSB.append("</picture>");

		Assert.assertEquals(
			expectedSB.toString(),
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>",
				_fileEntry));
	}

	@Test
	public void testHDMediaQueryNotAppliesWhenWidthHas2PXMoreThanExpected()
		throws Exception {

		_addConfigs(
			_createConfig(
				"small", "uuid", 450, 800, "http://small.adaptive.com"),
			_createConfig(
				"small-hd", "uuid", 900, 1602, "http://small.hd.adaptive.com"));

		StringBundler expectedSB = new StringBundler(8);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.adaptive.com\"/>");
		expectedSB.append("<source media=\"(max-width:1602px) and ");
		expectedSB.append("(min-width:800px)\" ");
		expectedSB.append("srcset=\"http://small.hd.adaptive.com\"/>");
		expectedSB.append("<img src=\"adaptable\"/>");
		expectedSB.append("</picture>");

		Assert.assertEquals(
			expectedSB.toString(),
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>",
				_fileEntry));
	}

	@Test
	public void testReturnsTheOriginalImgTagIfThereAreNoConfigs()
		throws Exception {

		_addConfigs();

		String originalImgTag =
			"<img src=\"originalURL\" data-fileEntryId=\"1234\"/>";

		String pictureTag = _adaptiveMediaImageHTMLTagFactory.create(
			originalImgTag, _fileEntry);

		Assert.assertSame(originalImgTag, pictureTag);
	}

	@Test
	public void testSupportsImageTagsWithNewLineCharacters() throws Exception {
		_addConfigs(_createConfig("small", "uuid", 800, 1989, "adaptiveURL"));

		StringBundler originalSB = new StringBundler(3);

		originalSB.append("<img data-fileEntryId=\"1234\" ");
		originalSB.append(CharPool.NEW_LINE);
		originalSB.append("src=\"adaptable\"/>");

		StringBundler expectedSB = new StringBundler(6);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:1989px)\" ");
		expectedSB.append("srcset=\"adaptiveURL\"/><img ");
		expectedSB.append(CharPool.NEW_LINE);
		expectedSB.append("src=\"adaptable\"/>");
		expectedSB.append("</picture>");

		String pictureTag = _adaptiveMediaImageHTMLTagFactory.create(
			originalSB.toString(), _fileEntry);

		Assert.assertEquals(expectedSB.toString(), pictureTag);
	}

	@Test
	public void testTheAttributeIsCaseInsensitive() throws Exception {
		_addConfigs(_createConfig("small", "uuid", 800, 1989, "adaptiveURL"));

		String originalImgTag =
			"<img src=\"originalURL\" datA-fileENTryID=\"1234\"/>";
		StringBundler expectedSB = new StringBundler(5);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:1989px)\" ");
		expectedSB.append("srcset=\"adaptiveURL\"/>");
		expectedSB.append("<img src=\"originalURL\"/>");
		expectedSB.append("</picture>");

		String pictureTag = _adaptiveMediaImageHTMLTagFactory.create(
			originalImgTag, _fileEntry);

		Assert.assertEquals(expectedSB.toString(), pictureTag);
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

	private final AdaptiveMediaImageHTMLTagFactoryImpl
		_adaptiveMediaImageHTMLTagFactory =
			new AdaptiveMediaImageHTMLTagFactoryImpl();

	@Mock
	private AdaptiveMediaImageURLFactory _adaptiveMediaURLFactory;

	@Mock
	private FileEntry _fileEntry;

}