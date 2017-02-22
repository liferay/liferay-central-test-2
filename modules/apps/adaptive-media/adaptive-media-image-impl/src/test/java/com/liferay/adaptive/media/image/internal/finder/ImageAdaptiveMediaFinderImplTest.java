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
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaQueryBuilder;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationEntryImpl;
import com.liferay.adaptive.media.image.internal.util.ImageProcessor;
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
import java.util.function.Predicate;
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
		_finder.setAdaptiveMediaImageLocalService(_imageLocalService);
	}

	@Test(expected = PortalException.class)
	public void testFileEntryGetLatestFileVersionFails() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				new HashMap<>());

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			enabledConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileEntry.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Mockito.when(
			_fileEntry.getLatestFileVersion()
		).thenThrow(
			PortalException.class
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
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

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			enabledConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
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
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
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

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			enabledConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
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
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.allForVersion(_fileVersion).done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMediaList =
			stream.collect(Collectors.toList());

		Assert.assertEquals(
			adaptiveMediaList.toString(), 1, adaptiveMediaList.size());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia =
			adaptiveMediaList.get(0);

		Assert.assertEquals(
			adaptiveMedia.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT),
			Optional.of(99));

		Assert.assertEquals(
			adaptiveMedia.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(199));
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

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			enabledConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
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
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
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

		Assert.assertEquals(
			adaptiveMediaList.toString(), 3, adaptiveMediaList.size());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMediaList.get(0);

		Assert.assertEquals(
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(199));

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia2 =
			adaptiveMediaList.get(1);

		Assert.assertEquals(
			adaptiveMedia2.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(399));

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia3 =
			adaptiveMediaList.get(2);

		Assert.assertEquals(
			adaptiveMedia3.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(799));
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

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			enabledConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
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
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
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

		Assert.assertEquals(
			adaptiveMediaList.toString(), 3, adaptiveMediaList.size());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMediaList.get(0);

		Assert.assertEquals(
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(799));

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia2 =
			adaptiveMediaList.get(1);

		Assert.assertEquals(
			adaptiveMedia2.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(399));

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia3 =
			adaptiveMediaList.get(2);

		Assert.assertEquals(
			adaptiveMedia3.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(199));
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
				Mockito.anyLong(), Mockito.any(Predicate.class))
		).thenThrow(
			AdaptiveMediaRuntimeException.InvalidConfiguration.class
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
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

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			enabledConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
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
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		InputStream inputStream = Mockito.mock(InputStream.class);

		Mockito.when(
			_imageLocalService.getAdaptiveMediaImageContentStream(
				configurationEntry, _fileVersion)
		).thenReturn(
			inputStream
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.allForVersion(_fileVersion).done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMediaList =
			stream.collect(Collectors.toList());

		Assert.assertEquals(
			adaptiveMediaList.toString(), 1, adaptiveMediaList.size());

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

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			enabledConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
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
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.allForVersion(_fileVersion).done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMediaList =
			stream.collect(Collectors.toList());

		Assert.assertEquals(
			adaptiveMediaList.toString(), 1, adaptiveMediaList.size());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia =
			adaptiveMediaList.get(0);

		Assert.assertEquals(
			adaptiveMedia.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT),
			Optional.of(99));

		Assert.assertEquals(
			adaptiveMedia.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
			Optional.of(1000));
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

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			enabledConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
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
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
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

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Optional<Integer> adaptiveMedia1HeightOptional =
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(199, (int)adaptiveMedia1HeightOptional.get());
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

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			enabledConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
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
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
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

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Optional<Integer> adaptiveMedia1HeightOptional =
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(99, (int)adaptiveMedia1HeightOptional.get());
	}

	@Test
	public void testGetMediaQueryWith200HeightAspectRatio() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "100", "max-width", "200"));

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("max-height", "200", "max-width", "100"));

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			enabledConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
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

		AdaptiveMediaImage image2 = _mockImage(55, 99, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry2.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image2
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
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

		Assert.assertEquals(99, (int)adaptiveMedia0HeightOptional.get());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Optional<Integer> adaptiveMedia1HeightOptional =
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(55, (int)adaptiveMedia1HeightOptional.get());
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

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			allConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ALL;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				allConfigurationStatus.getPredicate())
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
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
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

		Assert.assertEquals(
			adaptiveMedias.toString(), 1, adaptiveMedias.size());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Optional<String> adaptiveMedia0Optional =
			adaptiveMedia0.getAttributeValue(
				AdaptiveMediaAttribute.configurationUuid());

		Assert.assertEquals("small", adaptiveMedia0Optional.get());
	}

	@Test
	public void
			testGetMediaQueryWithConfigurationStatusAttributeForConfiguration()
		throws Exception {

		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), "small",
				MapUtil.fromArray("max-height", "100", "max-width", "200"));

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), "medium",
				MapUtil.fromArray("max-height", "200", "max-width", "200"),
				false);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ALL.
					getPredicate())
		).thenReturn(
			Arrays.asList(configurationEntry1, configurationEntry2)
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.DISABLED.
					getPredicate())
		).thenReturn(
			Arrays.asList(configurationEntry2)
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ENABLED.
					getPredicate())
		).thenReturn(
			Arrays.asList(configurationEntry1)
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
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.forVersion(_fileVersion).
						withConfigurationStatus(
							ImageAdaptiveMediaQueryBuilder.
								ConfigurationStatus.ENABLED).forConfiguration(
						"small").done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMedias =
			stream.collect(Collectors.toList());

		Assert.assertEquals(
			adaptiveMedias.toString(), 1, adaptiveMedias.size());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Optional<String> adaptiveMedia0Optional =
			adaptiveMedia0.getAttributeValue(
				AdaptiveMediaAttribute.configurationUuid());

		Assert.assertEquals("small", adaptiveMedia0Optional.get());

		stream = _finder.getAdaptiveMedia(
			queryBuilder ->
				queryBuilder.forVersion(_fileVersion).withConfigurationStatus(
					ImageAdaptiveMediaQueryBuilder.
						ConfigurationStatus.ALL).forConfiguration("small").
					done());

		adaptiveMedias = stream.collect(Collectors.toList());

		Assert.assertEquals(
			adaptiveMedias.toString(), 1, adaptiveMedias.size());

		adaptiveMedia0 = adaptiveMedias.get(0);

		adaptiveMedia0Optional = adaptiveMedia0.getAttributeValue(
			AdaptiveMediaAttribute.configurationUuid());

		Assert.assertEquals("small", adaptiveMedia0Optional.get());

		stream = _finder.getAdaptiveMedia(
			queryBuilder ->
				queryBuilder.forVersion(_fileVersion).withConfigurationStatus(
					ImageAdaptiveMediaQueryBuilder.
						ConfigurationStatus.DISABLED).forConfiguration("small").
					done());

		adaptiveMedias = stream.collect(Collectors.toList());

		Assert.assertEquals(
			adaptiveMedias.toString(), 0, adaptiveMedias.size());
	}

	@Test
	public void testGetMediaQueryWithConfigurationStatusAttributeWithWidth()
		throws Exception {

		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), "1",
				MapUtil.fromArray("max-height", "100"));

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), "2",
				MapUtil.fromArray("max-height", "200"), false);

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			enabledConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
		).thenReturn(
			Arrays.asList(configurationEntry1)
		);

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			disabledConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.DISABLED;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				disabledConfigurationStatus.getPredicate())
		).thenReturn(
			Arrays.asList(configurationEntry2)
		);

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			allConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ALL;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				allConfigurationStatus.getPredicate())
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

		AdaptiveMediaImage image1 = _mockImage(100, 1000, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry1.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image1
		);

		AdaptiveMediaImage image2 = _mockImage(200, 1000, 1000L);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				configurationEntry2.getUUID(), _fileVersion.getFileVersionId())
		).thenReturn(
			image2
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.forVersion(_fileVersion).
						withConfigurationStatus(enabledConfigurationStatus).
						with(ImageAdaptiveMediaAttribute.IMAGE_WIDTH, 100).
						done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMedias =
			stream.collect(Collectors.toList());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Optional<String> adaptiveMedia0ConfigurationUuidOptional =
			adaptiveMedia0.getAttributeValue(
				AdaptiveMediaAttribute.configurationUuid());

		Assert.assertEquals("1", adaptiveMedia0ConfigurationUuidOptional.get());

		stream = _finder.getAdaptiveMedia(
			queryBuilder ->
				queryBuilder.forVersion(_fileVersion).withConfigurationStatus(
					disabledConfigurationStatus).with(
					ImageAdaptiveMediaAttribute.IMAGE_WIDTH, 100).done());

		adaptiveMedias = stream.collect(Collectors.toList());

		adaptiveMedia0 = adaptiveMedias.get(0);

		adaptiveMedia0ConfigurationUuidOptional =
			adaptiveMedia0.getAttributeValue(
				AdaptiveMediaAttribute.configurationUuid());

		Assert.assertEquals("2", adaptiveMedia0ConfigurationUuidOptional.get());

		stream = _finder.getAdaptiveMedia(
			queryBuilder ->
				queryBuilder.forVersion(_fileVersion).withConfigurationStatus(
					allConfigurationStatus).with(
					ImageAdaptiveMediaAttribute.IMAGE_WIDTH, 100).done());

		adaptiveMedias = stream.collect(Collectors.toList());

		adaptiveMedia0 = adaptiveMedias.get(0);

		adaptiveMedia0ConfigurationUuidOptional =
			adaptiveMedia0.getAttributeValue(
				AdaptiveMediaAttribute.configurationUuid());

		Assert.assertEquals("1", adaptiveMedia0ConfigurationUuidOptional.get());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Optional<String> adaptiveMedia1ConfigurationUuidOptional =
			adaptiveMedia1.getAttributeValue(
				AdaptiveMediaAttribute.configurationUuid());

		Assert.assertEquals("2", adaptiveMedia1ConfigurationUuidOptional.get());
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

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			enabledConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
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
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
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

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Optional<Integer> adaptiveMedia1HeightOptional =
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(199, (int)adaptiveMedia1HeightOptional.get());
	}

	@Test
	public void testGetMediaWhenNotSupported() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			false
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder -> queryBuilder.allForVersion(
					_fileVersion).done());

		Object[] adaptiveMediaArray = stream.toArray();

		Assert.assertEquals(
			Arrays.toString(adaptiveMediaArray), 0, adaptiveMediaArray.length);
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

		ImageAdaptiveMediaQueryBuilder.ConfigurationStatus
			enabledConfigurationStatus =
				ImageAdaptiveMediaQueryBuilder.ConfigurationStatus.ENABLED;

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				_fileVersion.getCompanyId(),
				enabledConfigurationStatus.getPredicate())
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
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
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
			_imageLocalService
		).getAdaptiveMediaImageContentStream(
			configurationEntry, _fileVersion
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
	private final AdaptiveMediaImageLocalService _imageLocalService =
		Mockito.mock(AdaptiveMediaImageLocalService.class);
	private final ImageProcessor _imageProcessor = Mockito.mock(
		ImageProcessor.class);
	private final AdaptiveMediaURIResolver _uriResolver = Mockito.mock(
		AdaptiveMediaURIResolver.class);

}