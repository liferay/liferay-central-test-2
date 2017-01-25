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

import com.liferay.adaptive.media.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.exception.DuplicateAdaptiveMediaImageException;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationEntryImpl;
import com.liferay.adaptive.media.image.internal.util.ImageProcessor;
import com.liferay.adaptive.media.image.internal.util.ImageStorage;
import com.liferay.adaptive.media.image.service.AdaptiveMediaImageLocalService;
import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.StringUtil;

import java.awt.image.RenderedImage;

import java.io.InputStream;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaProcessorImplTest {

	@Before
	public void setUp() {
		_processor.setImageLocalService(_imageLocalService);
		_processor.setImageStorage(_imageStorage);
		_processor.setImageProcessor(_imageProcessor);
		_processor.setImageAdaptiveMediaConfigurationHelper(
			_configurationHelper);

		ImageToolUtil imageToolUtil = new ImageToolUtil();

		imageToolUtil.setImageTool(_imageTool);
	}

	@Test
	public void testCleanUpFileVersion() throws Exception {
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

		Mockito.verify(
			_imageLocalService
		).deleteAdaptiveMediaImageFileVersion(
			_fileVersion.getFileVersionId()
		);
	}

	@Test(expected = AdaptiveMediaRuntimeException.IOException.class)
	public void testCleanUpIOException() {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.doThrow(
			AdaptiveMediaRuntimeException.IOException.class
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

	@Test(expected = AdaptiveMediaRuntimeException.IOException.class)
	public void testProcessDuplicateAdaptiveMediaImageExceptionInImageService()
		throws Exception {

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

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

		RenderedImage renderedImage = Mockito.mock(RenderedImage.class);

		Mockito.when(
			_imageProcessor.scaleImage(_fileVersion, configurationEntry)
		).thenReturn(
			renderedImage
		);

		Mockito.when(
			_imageLocalService.addAdaptiveMediaImage(
				Mockito.any(String.class), Mockito.any(Long.class),
				Mockito.any(String.class), Mockito.any(Integer.class),
				Mockito.any(Integer.class), Mockito.any(Integer.class))
		).thenThrow(
			DuplicateAdaptiveMediaImageException.class
		);

		_processor.process(_fileVersion);
	}

	@Test
	public void testProcessFileVersion() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

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

		RenderedImage renderedImage = Mockito.mock(RenderedImage.class);

		Mockito.when(
			_imageProcessor.scaleImage(_fileVersion, configurationEntry)
		).thenReturn(
			renderedImage
		);

		_processor.process(_fileVersion);

		Mockito.verify(
			_imageProcessor
		).scaleImage(
			_fileVersion, configurationEntry
		);

		Mockito.verify(
			_imageStorage
		).save(
			Mockito.eq(_fileVersion), Mockito.eq(configurationEntry),
			Mockito.any(InputStream.class)
		);
	}

	@Test(expected = AdaptiveMediaRuntimeException.InvalidConfiguration.class)
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
			AdaptiveMediaRuntimeException.InvalidConfiguration.class
		);

		_processor.process(_fileVersion);
	}

	@Test(expected = AdaptiveMediaRuntimeException.IOException.class)
	public void testProcessIOExceptionInImageProcessor() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

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
			_imageProcessor.scaleImage(_fileVersion, configurationEntry)
		).thenThrow(
			AdaptiveMediaRuntimeException.IOException.class
		);

		_processor.process(_fileVersion);
	}

	@Test(expected = AdaptiveMediaRuntimeException.IOException.class)
	public void testProcessIOExceptionInStorage() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

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

		RenderedImage renderedImage = Mockito.mock(RenderedImage.class);

		Mockito.when(
			_imageProcessor.scaleImage(_fileVersion, configurationEntry)
		).thenReturn(
			renderedImage
		);

		Mockito.doThrow(
			AdaptiveMediaRuntimeException.IOException.class
		).when(
			_imageStorage
		).save(
			Mockito.any(FileVersion.class),
			Mockito.any(ImageAdaptiveMediaConfigurationEntry.class),
			Mockito.any(InputStream.class)
		);

		_processor.process(_fileVersion);
	}

	@Test
	public void testProcessWhenNoConfigurationEntries() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			true
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.any(long.class))
		).thenReturn(
			Collections.emptyList()
		);

		_processor.process(_fileVersion);

		Mockito.verify(
			_imageProcessor, Mockito.never()
		).scaleImage(
			Mockito.any(FileVersion.class),
			Mockito.any(ImageAdaptiveMediaConfigurationEntry.class));

		Mockito.verify(
			_imageStorage, Mockito.never()
		).save(
			Mockito.any(FileVersion.class),
			Mockito.any(ImageAdaptiveMediaConfigurationEntry.class),
			Mockito.any(InputStream.class));

		Mockito.verify(
			_imageLocalService, Mockito.never()
		).addAdaptiveMediaImage(
			Mockito.any(String.class), Mockito.any(Long.class),
			Mockito.any(String.class), Mockito.any(Integer.class),
			Mockito.any(Integer.class),
			Mockito.any(Integer.class)
		);
	}

	@Test
	public void testProcessWhenNotSupported() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.any(String.class))
		).thenReturn(
			false
		);

		_processor.process(_fileVersion);

		Mockito.verify(
			_imageProcessor, Mockito.never()
		).scaleImage(
			Mockito.any(FileVersion.class),
			Mockito.any(ImageAdaptiveMediaConfigurationEntry.class)
		);

		Mockito.verify(
			_imageStorage, Mockito.never()
		).save(
			Mockito.any(FileVersion.class),
			Mockito.any(ImageAdaptiveMediaConfigurationEntry.class),
			Mockito.any(InputStream.class)
		);

		Mockito.verify(
			_imageLocalService, Mockito.never()
		).addAdaptiveMediaImage(
			Mockito.any(String.class), Mockito.any(Long.class),
			Mockito.any(String.class), Mockito.any(Integer.class),
			Mockito.any(Integer.class), Mockito.any(Integer.class)
		);
	}

	private final ImageAdaptiveMediaConfigurationHelper _configurationHelper =
		Mockito.mock(ImageAdaptiveMediaConfigurationHelper.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);
	private final AdaptiveMediaImageLocalService _imageLocalService =
		Mockito.mock(AdaptiveMediaImageLocalService.class);
	private final ImageProcessor _imageProcessor = Mockito.mock(
		ImageProcessor.class);
	private final ImageStorage _imageStorage = Mockito.mock(ImageStorage.class);
	private final ImageTool _imageTool = Mockito.mock(ImageTool.class);
	private final ImageAdaptiveMediaProcessorImpl _processor =
		new ImageAdaptiveMediaProcessorImpl();

}