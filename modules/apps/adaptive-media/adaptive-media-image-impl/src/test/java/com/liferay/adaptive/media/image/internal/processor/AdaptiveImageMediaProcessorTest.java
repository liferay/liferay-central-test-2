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

import com.liferay.adaptive.media.image.internal.configuration.AdaptiveImageConfiguration;
import com.liferay.adaptive.media.image.internal.configuration.AdaptiveImageVariantConfiguration;
import com.liferay.adaptive.media.image.internal.image.ImageProcessor;
import com.liferay.adaptive.media.image.internal.image.ImageStorage;
import com.liferay.adaptive.media.image.processor.AdaptiveImageMediaProcessor;
import com.liferay.adaptive.media.processor.Media;
import com.liferay.adaptive.media.processor.MediaProcessorRuntimeException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveImageMediaProcessorTest {

	@Before
	public void setUp() {
		_adaptiveImageMediaProcessor.setImageStorage(_imageMediaStorage);
		_adaptiveImageMediaProcessor.setImageProcessor(_imageProcessor);
		_adaptiveImageMediaProcessor.setAdaptiveImageConfiguration(
			_adaptiveImageConfiguration);
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

	@Test(expected = MediaProcessorRuntimeException.IOException.class)
	public void testCleanUpIOException() {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.doThrow(
			MediaProcessorRuntimeException.IOException.class
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

	@Test(expected = MediaProcessorRuntimeException.InvalidConfiguration.class)
	public void testGetMediaConfigurationError() {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_adaptiveImageConfiguration.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenThrow(
			MediaProcessorRuntimeException.InvalidConfiguration.class
		);

		_adaptiveImageMediaProcessor.getMedia(_fileVersion);
	}

	@Test
	public void testGetMediaInputStream() {
		AdaptiveImageVariantConfiguration configuration =
			new AdaptiveImageVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_adaptiveImageConfiguration.getAdaptiveImageVariantConfigurations(
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

		_adaptiveImageMediaProcessor.getMedia(_fileVersion).
			forEach(
				media ->
					Assert.assertSame(inputStream, media.getInputStream()));
	}

	@Test
	public void testGetMediaMissingProperty() {
		AdaptiveImageVariantConfiguration configuration =
			new AdaptiveImageVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "100"));

		Mockito.when(
			_adaptiveImageConfiguration.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configuration)
		);

		_adaptiveImageMediaProcessor.getMedia(_fileVersion).
			forEach(
				media -> {
					Assert.assertEquals(
						media.getPropertyValue(
							AdaptiveImageMediaProperty.IMAGE_HEIGHT),
						Optional.of(100));

					Assert.assertEquals(
						media.getPropertyValue(
							AdaptiveImageMediaProperty.IMAGE_WIDTH),
						Optional.empty());
				});
	}

	@Test
	public void testGetMediaProperties() {
		AdaptiveImageVariantConfiguration configuration =
			new AdaptiveImageVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				MapUtil.fromArray("height", "100", "width", "200"));

		Mockito.when(
			_adaptiveImageConfiguration.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configuration)
		);

		_adaptiveImageMediaProcessor.getMedia(_fileVersion).
			forEach(
				media -> {
					Assert.assertEquals(
						media.getPropertyValue(
							AdaptiveImageMediaProperty.IMAGE_HEIGHT),
						Optional.of(100));

					Assert.assertEquals(
						media.getPropertyValue(
							AdaptiveImageMediaProperty.IMAGE_WIDTH),
						Optional.of(200));
				});
	}

	@Test
	public void testGetMediaWhenNotSupported() {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			false
		);

		Stream<Media<AdaptiveImageMediaProcessor>> mediaStream =
			_adaptiveImageMediaProcessor.getMedia(_fileVersion);

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

		AdaptiveImageVariantConfiguration configuration =
			new AdaptiveImageVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_adaptiveImageConfiguration.getAdaptiveImageVariantConfigurations(
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

	@Test(expected = MediaProcessorRuntimeException.InvalidConfiguration.class)
	public void testProcessInvalidConfigurationException() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_adaptiveImageConfiguration.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenThrow(
			MediaProcessorRuntimeException.InvalidConfiguration.class
		);

		_adaptiveImageMediaProcessor.process(_fileVersion);
	}

	@Test(expected = MediaProcessorRuntimeException.IOException.class)
	public void testProcessIOExceptionInImageProcessor() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		AdaptiveImageVariantConfiguration configuration =
			new AdaptiveImageVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_adaptiveImageConfiguration.getAdaptiveImageVariantConfigurations(
				Mockito.any(long.class))
		).thenReturn(
			Collections.singleton(configuration)
		);

		Mockito.when(
			_imageProcessor.process(_fileVersion, configuration)
		).thenThrow(
			MediaProcessorRuntimeException.IOException.class
		);

		_adaptiveImageMediaProcessor.process(_fileVersion);
	}

	@Test(expected = MediaProcessorRuntimeException.IOException.class)
	public void testProcessIOExceptionInInputStream() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		AdaptiveImageVariantConfiguration configuration =
			new AdaptiveImageVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_adaptiveImageConfiguration.getAdaptiveImageVariantConfigurations(
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

	@Test(expected = MediaProcessorRuntimeException.IOException.class)
	public void testProcessIOExceptionInStorage() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		AdaptiveImageVariantConfiguration configuration =
			new AdaptiveImageVariantConfiguration(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_adaptiveImageConfiguration.getAdaptiveImageVariantConfigurations(
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
			MediaProcessorRuntimeException.IOException.class
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
			Mockito.any(AdaptiveImageVariantConfiguration.class)
		);
	}

	private final AdaptiveImageConfiguration _adaptiveImageConfiguration =
		Mockito.mock(AdaptiveImageConfiguration.class);
	private final AdaptiveImageMediaProcessorImpl _adaptiveImageMediaProcessor =
		new AdaptiveImageMediaProcessorImpl();
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);
	private final ImageStorage _imageMediaStorage = Mockito.mock(
		ImageStorage.class);
	private final ImageProcessor _imageProcessor = Mockito.mock(
		ImageProcessor.class);

}