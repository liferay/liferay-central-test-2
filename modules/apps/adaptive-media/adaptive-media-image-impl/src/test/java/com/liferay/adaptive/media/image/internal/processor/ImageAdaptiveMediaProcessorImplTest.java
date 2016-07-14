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

package com.liferay.adaptive.media.image.internal.processor;

import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.internal.util.ImageProcessor;
import com.liferay.adaptive.media.image.internal.util.ImageStorage;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMedia;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorRuntimeException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
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
public class ImageAdaptiveMediaProcessorImplTest {

	@Before
	public void setUp() {
		_processor.setImageStorage(_imageStorage);
		_processor.setImageProcessor(_imageProcessor);
		_processor.setImageAdaptiveMediaConfigurationHelper(
			_configurationHelper);
	}

	@Test
	public void testCleanUpFileVersion() {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		_processor.cleanUp(_fileVersion);

		Mockito.verify(
			_imageStorage
		).delete(
			_fileVersion
		);
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.IOException.class)
	public void testCleanUpIOException() {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.doThrow(
			AdaptiveMediaProcessorRuntimeException.IOException.class
		).when(
			_imageStorage
		).delete(
			_fileVersion
		);

		_processor.cleanUp(_fileVersion);
	}

	@Test
	public void testCleanUpWhenNotSupported() {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			false
		);

		_processor.cleanUp(_fileVersion);

		Mockito.verify(
			_imageStorage, Mockito.never()
		).delete(
			_fileVersion
		);
	}

	@Test
	public void testGetMediaAttributes() {
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

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_processor.getMedia(
				queryBuilder -> queryBuilder.allForModel(_fileVersion));

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

	@Test(
		expected = AdaptiveMediaProcessorRuntimeException.InvalidConfiguration.class
	)
	public void testGetMediaConfigurationError() {
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

		_processor.getMedia(
			queryBuilder -> queryBuilder.allForModel(_fileVersion));
	}

	@Test
	public void testGetMediaInputStream() {
		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

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
			_processor.getMedia(
				queryBuilder -> queryBuilder.allForModel(_fileVersion));

		stream.forEach(
			adaptiveMedia ->
				Assert.assertSame(inputStream, adaptiveMedia.getInputStream()));
	}

	@Test
	public void testGetMediaMissingAttribute() {
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
			_processor.getMedia(
				queryBuilder -> queryBuilder.allForModel(_fileVersion));

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
	public void testGetMediaQueryWith100Height() {
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
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_processor.getMedia(
				queryBuilder ->
					queryBuilder.
						forModel(_fileVersion).
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
	public void testGetMediaQueryWith200Height() {
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
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_processor.getMedia(
				queryBuilder ->
					queryBuilder.
						forModel(_fileVersion).
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
	public void testGetMediaQueryWithNoMatchingAttributes() {
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
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_processor.getMedia(
				queryBuilder ->
					queryBuilder.
						forModel(_fileVersion).
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
	public void testGetMediaWhenNotSupported() {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			false
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_processor.getMedia(
				queryBuilder -> queryBuilder.allForModel(_fileVersion));

		Object[] adaptiveMediaArray = stream.toArray();

		Assert.assertEquals(0, adaptiveMediaArray.length);
	}

	@Test
	public void testProcessFileVersion() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		_processor.process(_fileVersion);

		Mockito.verify(
			_imageProcessor
		).process(_fileVersion, configurationEntry);

		Mockito.verify(
			_imageStorage
		).save(
			Mockito.eq(_fileVersion), Mockito.eq(configurationEntry),
			Mockito.any(InputStream.class)
		);
	}

	@Test(
		expected = AdaptiveMediaProcessorRuntimeException.InvalidConfiguration.class
	)
	public void testProcessInvalidConfigurationException() throws Exception {
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

		_processor.process(_fileVersion);
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.IOException.class)
	public void testProcessIOExceptionInImageProcessor() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Mockito.when(
			_imageProcessor.process(_fileVersion, configurationEntry)
		).thenThrow(
			AdaptiveMediaProcessorRuntimeException.IOException.class
		);

		_processor.process(_fileVersion);
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.IOException.class)
	public void testProcessIOExceptionInInputStream() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		InputStream inputStream = Mockito.mock(InputStream.class);

		Mockito.when(
			_imageProcessor.process(_fileVersion, configurationEntry)
		).thenReturn(
			inputStream
		);

		Mockito.doThrow(
			IOException.class
		).when(
			inputStream
		).close();

		_processor.process(_fileVersion);
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.IOException.class)
	public void testProcessIOExceptionInStorage() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		InputStream inputStream = Mockito.mock(InputStream.class);

		Mockito.when(
			_imageProcessor.process(_fileVersion, configurationEntry)
		).thenReturn(
			inputStream
		);

		Mockito.doThrow(
			AdaptiveMediaProcessorRuntimeException.IOException.class
		).when(
			_imageStorage
		).save(
			_fileVersion, configurationEntry, inputStream
		);

		_processor.process(_fileVersion);
	}

	@Test
	public void testProcessWhenNotSupported() {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			false
		);

		_processor.process(_fileVersion);

		Mockito.verify(
			_imageProcessor, Mockito.never()
		).process(
			Mockito.any(FileVersion.class),
			Mockito.any(ImageAdaptiveMediaConfigurationEntry.class)
		);
	}

	private final ImageAdaptiveMediaConfigurationHelper _configurationHelper =
		Mockito.mock(ImageAdaptiveMediaConfigurationHelper.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);
	private final ImageProcessor _imageProcessor = Mockito.mock(
		ImageProcessor.class);
	private final ImageStorage _imageStorage = Mockito.mock(ImageStorage.class);
	private final ImageAdaptiveMediaProcessorImpl _processor =
		new ImageAdaptiveMediaProcessorImpl();

}