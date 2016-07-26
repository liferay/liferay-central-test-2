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

import com.liferay.adaptive.media.finder.AdaptiveMediaQuery;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.internal.processor.ImageAdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.internal.util.ImageProcessor;
import com.liferay.adaptive.media.image.internal.util.ImageStorage;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMedia;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorRuntimeException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Collections;
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
		_finder.setImageAdaptiveMediaConfigurationHelper(_configurationHelper);
		_finder.setImageProcessor(_imageProcessor);
		_finder.setImageStorage(_imageStorage);
	}

	@Test
	public void testGetMediaAttributes() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "100", "width", "200"));

		Mockito.when(
			_fileVersion.getFileEntry()
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder -> queryBuilder.allForVersion(_fileVersion));

		stream.forEach(
			adaptiveMedia -> {
				Assert.assertEquals(
					adaptiveMedia.getAttributeValue(
						ImageAdaptiveMediaAttribute.IMAGE_HEIGHT),
					Optional.of(100));

				Assert.assertEquals(
					adaptiveMedia.getAttributeValue(
						ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
					Optional.of(200));
			});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMediaAttributesWithNonBuilderQuery() throws Exception {
		_finder.getAdaptiveMedia(
			queryBuilder ->
				new AdaptiveMediaQuery<FileVersion,
				ImageAdaptiveMediaProcessor>() {
				});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMediaAttributesWithNullQuery() throws Exception {
		_finder.getAdaptiveMedia(queryBuilder -> null);
	}

	@Test(
		expected = AdaptiveMediaProcessorRuntimeException.InvalidConfiguration.class
	)
	public void testGetMediaConfigurationError() throws Exception {
		Mockito.when(
			_fileVersion.getFileEntry()
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenThrow(
			AdaptiveMediaProcessorRuntimeException.InvalidConfiguration.class
		);

		_finder.getAdaptiveMedia(
			queryBuilder -> queryBuilder.allForVersion(_fileVersion));
	}

	@Test
	public void testGetMediaInputStream() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_fileVersion.getFileEntry()
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configurationEntry)
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
				queryBuilder -> queryBuilder.allForVersion(_fileVersion));

		stream.forEach(
			adaptiveMedia ->
				Assert.assertSame(inputStream, adaptiveMedia.getInputStream()));
	}

	@Test
	public void testGetMediaMissingAttribute() throws Exception {
		Mockito.when(
			_fileVersion.getFileEntry()
		).thenReturn(
			_fileEntry
		);

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "100"));

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder -> queryBuilder.allForVersion(_fileVersion));

		stream.forEach(
			adaptiveMedia -> {
				Assert.assertEquals(
					adaptiveMedia.getAttributeValue(
						ImageAdaptiveMediaAttribute.IMAGE_HEIGHT),
					Optional.of(100));

				Assert.assertEquals(
					adaptiveMedia.getAttributeValue(
						ImageAdaptiveMediaAttribute.IMAGE_WIDTH),
					Optional.empty());
			});
	}

	@Test
	public void testGetMediaQueryWith100Height() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "100", "width", "200"));

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "200", "width", "200"));

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Arrays.asList(configurationEntry1, configurationEntry2)
		);

		Mockito.when(
			_fileVersion.getFileEntry()
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.
						forVersion(_fileVersion).
						with(ImageAdaptiveMediaAttribute.IMAGE_HEIGHT, 100).
						done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMedias =
			stream.collect(Collectors.toList());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Optional<Integer> adaptiveMedia0Optional =
			adaptiveMedia0.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(100, (int)adaptiveMedia0Optional.get());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Optional<Integer> adaptiveMedia1Optional =
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(200, (int)adaptiveMedia1Optional.get());
	}

	@Test
	public void testGetMediaQueryWith200Height() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "100", "width", "200"));

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "200", "width", "200"));

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Arrays.asList(configurationEntry1, configurationEntry2)
		);

		Mockito.when(
			_fileVersion.getFileEntry()
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.
						forVersion(_fileVersion).
						with(ImageAdaptiveMediaAttribute.IMAGE_HEIGHT, 200).
						done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMedias =
			stream.collect(Collectors.toList());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Optional<Integer> adaptiveMedia0Optional =
			adaptiveMedia0.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(200, (int)adaptiveMedia0Optional.get());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Optional<Integer> adaptiveMedia1Optional =
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(100, (int)adaptiveMedia1Optional.get());
	}

	@Test
	public void testGetMediaQueryWithNoMatchingAttributes() throws Exception {
		ImageAdaptiveMediaConfigurationEntry configurationEntry1 =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "100"));

		ImageAdaptiveMediaConfigurationEntry configurationEntry2 =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "200"));

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Arrays.asList(configurationEntry1, configurationEntry2)
		);

		Mockito.when(
			_fileVersion.getFileEntry()
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.
						forVersion(_fileVersion).
						with(ImageAdaptiveMediaAttribute.IMAGE_WIDTH, 100).
						done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMedias =
			stream.collect(Collectors.toList());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia0 =
			adaptiveMedias.get(0);

		Optional<Integer> adaptiveMedia0Optional =
			adaptiveMedia0.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(100, (int)adaptiveMedia0Optional.get());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			adaptiveMedias.get(1);

		Optional<Integer> adaptiveMedia1Optional =
			adaptiveMedia1.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(200, (int)adaptiveMedia1Optional.get());
	}

	@Test
	public void testGetMediaWhenNotSupported() throws Exception {
		Mockito.when(
			_fileVersion.getFileEntry()
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			false
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder -> queryBuilder.allForVersion(_fileVersion));

		Object[] adaptiveMediaArray = stream.toArray();

		Assert.assertEquals(0, adaptiveMediaArray.length);
	}

	@Test
	public void testMediaLazilyDelegatesOnStorageInputStream()
		throws Exception {

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "100", "width", "200"));

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_fileVersion.getFileEntry()
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaStream =
			_finder.getAdaptiveMedia(
				queryBuilder -> queryBuilder.allForVersion(_fileVersion));

		AdaptiveMedia<ImageAdaptiveMediaProcessor> media =
			mediaStream.findFirst().get();

		media.getInputStream();

		Mockito.verify(
			_imageStorage
		).getContentStream(_fileVersion, configurationEntry);
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

}