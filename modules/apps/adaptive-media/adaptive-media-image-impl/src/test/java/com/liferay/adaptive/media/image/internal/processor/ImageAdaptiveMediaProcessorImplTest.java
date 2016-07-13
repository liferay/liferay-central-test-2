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

import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaVariantConfiguration;
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
		_adaptiveImageMediaProcessor.setImageStorage(_imageMediaStorage);
		_adaptiveImageMediaProcessor.setImageProcessor(_imageProcessor);
		_adaptiveImageMediaProcessor.setMediaConfigurationHelper(
			_mediaConfigurationHelper);
	}

	@Test
	public void testCleanUpFileVersion() {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		_adaptiveImageMediaProcessor.cleanUp(_fileVersion);

		Mockito.verify(
			_imageMediaStorage
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
			_imageMediaStorage
		).delete(
			_fileVersion
		);

		_adaptiveImageMediaProcessor.cleanUp(_fileVersion);
	}

	@Test
	public void testCleanUpWhenNotSupported() {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			false
		);

		_adaptiveImageMediaProcessor.cleanUp(_fileVersion);

		Mockito.verify(
			_imageMediaStorage, Mockito.never()
		).delete(
			_fileVersion
		);
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.InvalidConfiguration.class)
	public void testGetMediaConfigurationError() {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_mediaConfigurationHelper.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenThrow(
			AdaptiveMediaProcessorRuntimeException.InvalidConfiguration.class
		);

		_adaptiveImageMediaProcessor.getMedia(
			query -> query.allForModel(_fileVersion));
	}

	@Test
	public void testGetMediaInputStream() {
		ImageAdaptiveMediaVariantConfiguration configuration =
			new ImageAdaptiveMediaVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_mediaConfigurationHelper.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configuration)
		);

		InputStream inputStream = Mockito.mock(InputStream.class);

		Mockito.when(
			_imageMediaStorage.getContentStream(_fileVersion, configuration)
		).thenReturn(
			inputStream
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaStream =
			_adaptiveImageMediaProcessor.getMedia(
				query -> query.allForModel(_fileVersion));

		mediaStream.forEach(
			media -> Assert.assertSame(inputStream, media.getInputStream()));
	}

	@Test
	public void testGetMediaMissingProperty() {
		ImageAdaptiveMediaVariantConfiguration configuration =
			new ImageAdaptiveMediaVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "100"));

		Mockito.when(
			_mediaConfigurationHelper.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configuration)
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaStream =
			_adaptiveImageMediaProcessor.getMedia(
				query -> query.allForModel(_fileVersion));

		mediaStream.forEach(
			media -> {
				Assert.assertEquals(
					media.getPropertyValue(
						ImageAdaptiveMediaProperty.IMAGE_HEIGHT),
					Optional.of(100));

				Assert.assertEquals(
					media.getPropertyValue(
						ImageAdaptiveMediaProperty.IMAGE_WIDTH),
					Optional.empty());
			});
	}

	@Test
	public void testGetMediaProperties() {
		ImageAdaptiveMediaVariantConfiguration configuration =
			new ImageAdaptiveMediaVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "100", "width", "200"));

		Mockito.when(
			_mediaConfigurationHelper.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configuration)
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaStream =
			_adaptiveImageMediaProcessor.getMedia(
				query -> query.allForModel(_fileVersion));

		mediaStream.forEach(
			media -> {
				Assert.assertEquals(
					media.getPropertyValue(
						ImageAdaptiveMediaProperty.IMAGE_HEIGHT),
					Optional.of(100));

				Assert.assertEquals(
					media.getPropertyValue(
						ImageAdaptiveMediaProperty.IMAGE_WIDTH),
					Optional.of(200));
			});
	}

	@Test
	public void testGetMediaQueryWith100Height() {
		ImageAdaptiveMediaVariantConfiguration configuration1 =
			new ImageAdaptiveMediaVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "100", "width", "200"));

		ImageAdaptiveMediaVariantConfiguration configuration2 =
			new ImageAdaptiveMediaVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "200", "width", "200"));

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_mediaConfigurationHelper.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenReturn(
			Arrays.asList(configuration1, configuration2)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaStream =
			_adaptiveImageMediaProcessor.getMedia(
				query ->
					query.
						forModel(_fileVersion).
						with(ImageAdaptiveMediaProperty.IMAGE_HEIGHT, 100).
						done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaList =
			mediaStream.collect(Collectors.toList());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> media0 = mediaList.get(0);

		Optional<Integer> media0Optional = media0.getPropertyValue(
			ImageAdaptiveMediaProperty.IMAGE_HEIGHT);

		Assert.assertEquals(100, (int)media0Optional.get());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> media1 = mediaList.get(1);

		Optional<Integer> media1Optional = media1.getPropertyValue(
			ImageAdaptiveMediaProperty.IMAGE_HEIGHT);

		Assert.assertEquals(200, (int)media1Optional.get());
	}

	@Test
	public void testGetMediaQueryWith200Height() {
		ImageAdaptiveMediaVariantConfiguration configuration1 =
			new ImageAdaptiveMediaVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "100", "width", "200"));

		ImageAdaptiveMediaVariantConfiguration configuration2 =
			new ImageAdaptiveMediaVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "200", "width", "200"));

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_mediaConfigurationHelper.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenReturn(
			Arrays.asList(configuration1, configuration2)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaStream =
			_adaptiveImageMediaProcessor.getMedia(
				query ->
					query.
						forModel(_fileVersion).
						with(ImageAdaptiveMediaProperty.IMAGE_HEIGHT, 200).
						done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaList =
			mediaStream.collect(Collectors.toList());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> media0 = mediaList.get(0);

		Optional<Integer> media0Optional = media0.getPropertyValue(
			ImageAdaptiveMediaProperty.IMAGE_HEIGHT);

		Assert.assertEquals(200, (int)media0Optional.get());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> media1 = mediaList.get(1);

		Optional<Integer> media1Optional = media1.getPropertyValue(
			ImageAdaptiveMediaProperty.IMAGE_HEIGHT);

		Assert.assertEquals(100, (int)media1Optional.get());
	}

	@Test
	public void testGetMediaQueryWithNoMatchingProperties() {
		ImageAdaptiveMediaVariantConfiguration configuration1 =
			new ImageAdaptiveMediaVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "100"));

		ImageAdaptiveMediaVariantConfiguration configuration2 =
			new ImageAdaptiveMediaVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "200"));

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_mediaConfigurationHelper.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenReturn(
			Arrays.asList(configuration1, configuration2)
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			StringUtil.randomString()
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaStream =
			_adaptiveImageMediaProcessor.getMedia(
				query ->
					query.
						forModel(_fileVersion).
						with(ImageAdaptiveMediaProperty.IMAGE_WIDTH, 100).
						done());

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaList =
			mediaStream.collect(Collectors.toList());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> media0 = mediaList.get(0);

		Optional<Integer> media0Optional = media0.getPropertyValue(
			ImageAdaptiveMediaProperty.IMAGE_HEIGHT);

		Assert.assertEquals(100, (int)media0Optional.get());

		AdaptiveMedia<ImageAdaptiveMediaProcessor> media1 = mediaList.get(1);

		Optional<Integer> media1Optional = media1.getPropertyValue(
			ImageAdaptiveMediaProperty.IMAGE_HEIGHT);

		Assert.assertEquals(200, (int)media1Optional.get());
	}

	@Test
	public void testGetMediaWhenNotSupported() {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			false
		);

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaStream =
			_adaptiveImageMediaProcessor.getMedia(
				query -> query.allForModel(_fileVersion));

		Object[] mediaArray = mediaStream.toArray();

		Assert.assertEquals(0, mediaArray.length);
	}

	@Test
	public void testProcessFileVersion() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		ImageAdaptiveMediaVariantConfiguration configuration =
			new ImageAdaptiveMediaVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_mediaConfigurationHelper.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configuration)
		);

		_adaptiveImageMediaProcessor.process(_fileVersion);

		Mockito.verify(
			_imageProcessor
		).process(_fileVersion, configuration);

		Mockito.verify(
			_imageMediaStorage
		).save(
			Mockito.eq(_fileVersion), Mockito.eq(configuration),
			Mockito.any(InputStream.class)
		);
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.InvalidConfiguration.class)
	public void testProcessInvalidConfigurationException() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_mediaConfigurationHelper.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenThrow(
			AdaptiveMediaProcessorRuntimeException.InvalidConfiguration.class
		);

		_adaptiveImageMediaProcessor.process(_fileVersion);
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.IOException.class)
	public void testProcessIOExceptionInImageProcessor() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		ImageAdaptiveMediaVariantConfiguration configuration =
			new ImageAdaptiveMediaVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_mediaConfigurationHelper.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configuration)
		);

		Mockito.when(
			_imageProcessor.process(_fileVersion, configuration)
		).thenThrow(
			AdaptiveMediaProcessorRuntimeException.IOException.class
		);

		_adaptiveImageMediaProcessor.process(_fileVersion);
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.IOException.class)
	public void testProcessIOExceptionInInputStream() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		ImageAdaptiveMediaVariantConfiguration configuration =
			new ImageAdaptiveMediaVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_mediaConfigurationHelper.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configuration)
		);

		InputStream inputStream = Mockito.mock(InputStream.class);

		Mockito.when(
			_imageProcessor.process(_fileVersion, configuration)
		).thenReturn(
			inputStream
		);

		Mockito.doThrow(
			IOException.class
		).when(
			inputStream
		).close();

		_adaptiveImageMediaProcessor.process(_fileVersion);
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.IOException.class)
	public void testProcessIOExceptionInStorage() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		ImageAdaptiveMediaVariantConfiguration configuration =
			new ImageAdaptiveMediaVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_mediaConfigurationHelper.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configuration)
		);

		InputStream inputStream = Mockito.mock(InputStream.class);

		Mockito.when(
			_imageProcessor.process(_fileVersion, configuration)
		).thenReturn(
			inputStream
		);

		Mockito.doThrow(
			AdaptiveMediaProcessorRuntimeException.IOException.class
		).when(
			_imageMediaStorage
		).save(
			_fileVersion, configuration, inputStream
		);

		_adaptiveImageMediaProcessor.process(_fileVersion);
	}

	@Test
	public void testProcessWhenNotSupported() {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			false
		);

		_adaptiveImageMediaProcessor.process(_fileVersion);

		Mockito.verify(
			_imageProcessor, Mockito.never()
		).process(
			Mockito.any(FileVersion.class),
			Mockito.any(ImageAdaptiveMediaVariantConfiguration.class)
		);
	}

	private final ImageAdaptiveMediaConfigurationHelper
		_mediaConfigurationHelper = Mockito.mock(
			ImageAdaptiveMediaConfigurationHelper.class);
	private final ImageAdaptiveMediaProcessorImpl _adaptiveImageMediaProcessor =
		new ImageAdaptiveMediaProcessorImpl();
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);
	private final ImageStorage _imageMediaStorage = Mockito.mock(
		ImageStorage.class);
	private final ImageProcessor _imageProcessor = Mockito.mock(
		ImageProcessor.class);

}