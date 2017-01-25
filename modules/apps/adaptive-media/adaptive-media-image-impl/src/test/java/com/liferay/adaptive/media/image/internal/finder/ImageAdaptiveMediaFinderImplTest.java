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

package com.liferay.adaptive.media.image.internal.finder;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.AdaptiveMediaURIResolver;
import com.liferay.adaptive.media.finder.AdaptiveMediaQuery;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationEntryImpl;
import com.liferay.adaptive.media.image.internal.util.ImageProcessor;
import com.liferay.adaptive.media.image.internal.util.ImageStorage;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImage;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.image.service.AdaptiveMediaImageLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaFinderImplTest {

	@Before
	public void setUp() {
		_finder.setAdaptiveMediaURIResolver(_uriResolver);
		_finder.setImageAdaptiveMediaConfigurationHelper(_configurationHelper);
		_finder.setImageProcessor(_imageProcessor);
		_finder.setImageStorage(_imageStorage);
		_finder.setAdaptiveMediaImageLocalService(_imageLocalService);
	}

	@Test(expected = PortalException.class)
	public void testFileEntryGetLatestFileVersionFails() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				new HashMap<>());

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Mockito.when(
			_fileEntry.getLatestFileVersion()
		).thenThrow(
			PortalException.class
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.allForFileEntry(_fileEntry).done());

		stream.count();
	}

	@Test
	public void testFileEntryGetMediaWithNoAttributes() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				new HashMap<>());

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Mockito.when(
			_fileEntry.getLatestFileVersion()
		).thenReturn(
			_fileVersion
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AdaptiveMediaImage image = _mockImage(800, 900, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.allForFileEntry(_fileEntry).done());

		Assert.assertEquals(1, stream.count());
	}

	@Test
	public void testGetMediaAttributes() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "200"));

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AdaptiveMediaImage image = _mockImage(99, 199, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.allForVersion(_fileVersion).done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMediaList =
			stream.collect(Collectors.toList());

		Assert.assertEquals(1, adaptiveMediaList.size());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia =
			adaptiveMediaList.get(0);

		Assert.assertEquals(
			adaptiveMedia.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT),
			Optional.of(99));
		Assert.assertEquals(
			adaptiveMedia.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT),
			Optional.of(100));

		Assert.assertEquals(
			adaptiveMedia.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(199));
		Assert.assertEquals(
			adaptiveMedia.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH),
			Optional.of(200));
	}

	@Test
	public void testGetMediaAttributesOrderByAsc() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "200"));
		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "800"));
		ImageAdaptiveMediaConfigurationEntry configurationEntry3 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "400"));

		List<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			Arrays.asList(
				configurationEntry1, configurationEntry2, configurationEntry3);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			configurationEntries
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AdaptiveMediaImage image1 = _mockImage(99, 199, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry1.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image1
		);

		AdaptiveMediaImage image2 = _mockImage(99, 799, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry2.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image2
		);

		AdaptiveMediaImage image3 = _mockImage(99, 399, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry3.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image3
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.allForVersion(_fileVersion).orderBy(
						ImageAdaptiveMediaAttribute.IMAGE_WIDTH, true).done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMediaList =
			stream.collect(Collectors.toList());

		Assert.assertEquals(3, adaptiveMediaList.size());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMediaList.get(0);

		Assert.assertEquals(
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(199));
		Assert.assertEquals(
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH),
			Optional.of(200));

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia2 =
			adaptiveMediaList.get(1);

		Assert.assertEquals(
			adaptiveMedia2.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(399));
		Assert.assertEquals(
			adaptiveMedia2.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH),
			Optional.of(400));

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia3 =
			adaptiveMediaList.get(2);

		Assert.assertEquals(
			adaptiveMedia3.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(799));
		Assert.assertEquals(
			adaptiveMedia3.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH),
			Optional.of(800));
	}

	@Test
	public void testGetMediaAttributesOrderByDesc() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "200"));
		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "800"));
		ImageAdaptiveMediaConfigurationEntry configurationEntry3 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "400"));

		List<ImageAdaptiveMediaConfigurationEntry> configurationEntries =
			Arrays.asList(
				configurationEntry1, configurationEntry2, configurationEntry3);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			configurationEntries
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AdaptiveMediaImage image1 = _mockImage(99, 199, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry1.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image1
		);

		AdaptiveMediaImage image2 = _mockImage(99, 799, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry2.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image2
		);

		AdaptiveMediaImage image3 = _mockImage(99, 399, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry3.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image3
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.allForVersion(_fileVersion).orderBy(
						ImageAdaptiveMediaAttribute.IMAGE_WIDTH, false).done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMediaList =
			stream.collect(Collectors.toList());

		Assert.assertEquals(3, adaptiveMediaList.size());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMediaList.get(0);

		Assert.assertEquals(
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(799));
		Assert.assertEquals(
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH),
			Optional.of(800));

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia2 =
			adaptiveMediaList.get(1);

		Assert.assertEquals(
			adaptiveMedia2.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(399));
		Assert.assertEquals(
			adaptiveMedia2.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH),
			Optional.of(400));

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia3 =
			adaptiveMediaList.get(2);

		Assert.assertEquals(
			adaptiveMedia3.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(199));
		Assert.assertEquals(
			adaptiveMedia3.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH),
			Optional.of(200));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMediaAttributesWithNonBuilderQuery() throws Exception {
		_finder.getAdaptiveMedia(
			queryBuilder ->
				new AdaptiveMediaQuery
					<FileVersion, ImageAdaptiveMediaProcessor>() {
				});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMediaAttributesWithNullQuery() throws Exception {
		_finder.getAdaptiveMedia(queryBuilder -> null);
	}

	@Test(expected = AdaptiveMediaRuntimeException.InvalidConfiguration.class)
	public void testGetMediaConfigurationError() throws Exception {
		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenThrow(
			AdaptiveMediaRuntimeException.InvalidConfiguration.class
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		_finder.getAdaptiveMedia(
			queryBuilder -> queryBuilder.allForVersion(_fileVersion).done());
	}

	@Test
	public void testGetMediaInputStream() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AdaptiveMediaImage image = _mockImage(800, 900, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		InputStream inputStream = Mockito.mock(InputStream.class);

		Mockito.when(
			_imageStorage.getContentStream(
				_fileVersion, configurationEntry)
		).thenReturn(
			inputStream
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.allForVersion(_fileVersion).done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMediaList =
			stream.collect(Collectors.toList());

		Assert.assertEquals(1, adaptiveMediaList.size());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia =
			adaptiveMediaList.get(0);

		Assert.assertSame(inputStream, adaptiveMedia.getInputStream());
	}

	@Test
	public void testGetMediaMissingAttribute() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "100"));

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AdaptiveMediaImage image = _mockImage(99, 1000, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.allForVersion(_fileVersion).done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMediaList =
			stream.collect(Collectors.toList());

		Assert.assertEquals(1, adaptiveMediaList.size());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia =
			adaptiveMediaList.get(0);

		Assert.assertEquals(
			adaptiveMedia.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT),
			Optional.of(99));
		Assert.assertEquals(
			adaptiveMedia.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT),
			Optional.of(100));

		Assert.assertEquals(
			adaptiveMedia.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(1000));
		Assert.assertEquals(
			adaptiveMedia.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH),
			Optional.empty());
	}

	@Test
	public void testGetMediaQueryWith100Height() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "200"));

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "200", "max-width", "200"));

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Arrays.asList(configurationEntry1, configurationEntry2)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AdaptiveMediaImage image1 = _mockImage(99, 199, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry1.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image1
		);

		AdaptiveMediaImage image2 = _mockImage(199, 199, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry2.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image2
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.forVersion(_fileVersion).with(
						ImageAdaptiveMediaAttribute.IMAGE_HEIGHT, 100).done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMedias =
			stream.collect(Collectors.toList());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Optional<Integer> adaptiveMedia0HeightOptional =
			adaptiveMedia0.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(99, (int)adaptiveMedia0HeightOptional.get());

		Optional<Integer> adaptiveMedia0MaxHeightOptional =
			adaptiveMedia0.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT);

		Assert.assertEquals(100, (int)adaptiveMedia0MaxHeightOptional.get());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Optional<Integer> adaptiveMedia1HeightOptional =
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(199, (int)adaptiveMedia1HeightOptional.get());

		Optional<Integer> adaptiveMedia1MaxHeightOptional =
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT);

		Assert.assertEquals(200, (int)adaptiveMedia1MaxHeightOptional.get());
	}

	@Test
	public void testGetMediaQueryWith200Height() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "200"));

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "200", "max-width", "200"));

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Arrays.asList(configurationEntry1, configurationEntry2)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AdaptiveMediaImage image1 = _mockImage(99, 199, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry1.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image1
		);

		AdaptiveMediaImage image2 = _mockImage(199, 199, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry2.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image2
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.forVersion(_fileVersion).with(
						ImageAdaptiveMediaAttribute.IMAGE_HEIGHT, 200).done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMedias =
			stream.collect(Collectors.toList());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Optional<Integer> adaptiveMedia0HeightOptional =
			adaptiveMedia0.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(199, (int)adaptiveMedia0HeightOptional.get());

		Optional<Integer> adaptiveMedia0MaxHeightOptional =
			adaptiveMedia0.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT);

		Assert.assertEquals(200, (int)adaptiveMedia0MaxHeightOptional.get());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Optional<Integer> adaptiveMedia1HeightOptional =
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(99, (int)adaptiveMedia1HeightOptional.get());

		Optional<Integer> adaptiveMedia1MaxHeightOptional =
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT);

		Assert.assertEquals(100, (int)adaptiveMedia1MaxHeightOptional.get());
	}

	@Test
	public void testGetMediaQueryWithConfigurationAttribute() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), "small",
				MapUtil.fromArray("max-height", "100", "max-width", "200"));

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), "medium",
				MapUtil.fromArray("max-height", "200", "max-width", "200"));

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Arrays.asList(configurationEntry1, configurationEntry2)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AdaptiveMediaImage image1 = _mockImage(99, 199, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry1.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image1
		);

		AdaptiveMediaImage image2 = _mockImage(199, 199, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry2.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image2
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.forVersion(_fileVersion).forConfiguration(
						"small").done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMedias =
			stream.collect(Collectors.toList());

		Assert.assertEquals(1, adaptiveMedias.size());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Optional<String> adaptiveMedia0Optional =
			adaptiveMedia0.getAttributeValue(
				AdaptiveMediaAttribute.configurationUuid());

		Assert.assertEquals("small", adaptiveMedia0Optional.get());
	}

	@Test
	public void testGetMediaQueryWithNoMatchingAttributes() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "100"));

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "200"));

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Arrays.asList(configurationEntry1, configurationEntry2)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AdaptiveMediaImage image1 = _mockImage(99, 1000, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry1.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image1
		);

		AdaptiveMediaImage image2 = _mockImage(199, 1000, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry2.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image2
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.forVersion(_fileVersion).with(
						ImageAdaptiveMediaAttribute.IMAGE_WIDTH, 100).done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMedias =
			stream.collect(Collectors.toList());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Optional<Integer> adaptiveMedia0HeightOptional =
			adaptiveMedia0.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(99, (int)adaptiveMedia0HeightOptional.get());

		Optional<Integer> adaptiveMedia0MaxHeightOptional =
			adaptiveMedia0.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT);

		Assert.assertEquals(100, (int)adaptiveMedia0MaxHeightOptional.get());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Optional<Integer> adaptiveMedia1HeightOptional =
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(199, (int)adaptiveMedia1HeightOptional.get());

		Optional<Integer> adaptiveMedia1MaxHeightOptional =
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT);

		Assert.assertEquals(200, (int)adaptiveMedia1MaxHeightOptional.get());
	}

	@Test
	public void testGetMediaWhenNotSupported() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			false
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder -> queryBuilder.allForVersion(
					_fileVersion).done());

		Object[] adaptiveMediaArray = stream.toArray();

		Assert.assertEquals(0, adaptiveMediaArray.length);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMediaWithNullFunction() throws Exception {
		_finder.getAdaptiveMedia(null);
	}

	@Test
	public void testMediaLazilyDelegatesOnStorageInputStream()
		throws Exception {

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "200"));

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_fileVersion.getMimeType()
		).thenReturn(
			"image/jpeg"
		);

		AdaptiveMediaImage image = _mockImage(99, 99, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.allForVersion(_fileVersion).done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMediaList =
			stream.collect(Collectors.toList());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia =
			adaptiveMediaList.get(0);

		adaptiveMedia.getInputStream();

		Mockito.verify(
			_imageStorage
		).getContentStream(
			_fileVersion, configurationEntry
		);
	}

	private AdaptiveMediaImage _mockImage(int height, int width, long size) {
		AdaptiveMediaImage image = Mockito.mock(AdaptiveMediaImage.class);

		Mockito.when(
			image.getHeight()
		).thenReturn(
			height
		);

		Mockito.when(
			image.getWidth()
		).thenReturn(
			width
		);

		Mockito.when(
			image.getSize()
		).thenReturn(
			size
		);

		return image;
	}

	private final ImageAdaptiveMediaConfigurationHelper _configurationHelper =
		Mockito.mock(ImageAdaptiveMediaConfigurationHelper.class);
	private final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);
	private final ImageAdaptiveMediaFinderImpl _finder =
		new ImageAdaptiveMediaFinderImpl();
	private final ImageProcessor _imageProcessor = Mockito.mock(
		ImageProcessor.class);
	private final ImageStorage _imageStorage = Mockito.mock(ImageStorage.class);
	private final AdaptiveMediaImageLocalService _imageLocalService =
		Mockito.mock(AdaptiveMediaImageLocalService.class);
	private final AdaptiveMediaURIResolver _uriResolver = Mockito.mock(
		AdaptiveMediaURIResolver.class);

}