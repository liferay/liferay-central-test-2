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
import com.liferay.adaptive.media.image.internal.processor.util.TiffOrientationTransformer;
import com.liferay.adaptive.media.image.internal.util.ImageProcessor;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImage;
import com.liferay.adaptive.media.image.service.AdaptiveMediaImageLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.StringUtil;

import java.awt.image.RenderedImage;

import java.io.InputStream;

import java.util.Collections;
import java.util.Optional;

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
		_processor.setImageProcessor(_imageProcessor);
		_processor.setImageAdaptiveMediaConfigurationHelper(
			_configurationHelper);
		_processor.setTiffOrientationTransformer(_tiffOrientationTransformer);

		ImageToolUtil imageToolUtil = new ImageToolUtil();

		imageToolUtil.setImageTool(_imageTool);
	}

	@Test
	public void testCleanUpFileVersion() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		_processor.cleanUp(_fileVersion);

		Mockito.verify(
			_imageLocalService
		).deleteAdaptiveMediaImageFileVersion(
			_fileVersion.getFileVersionId()
		);
	}

	@Test(expected = AdaptiveMediaRuntimeException.IOException.class)
	public void testCleanUpIOException() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		Mockito.doThrow(
			AdaptiveMediaRuntimeException.IOException.class
		).when(
			_imageLocalService
		).deleteAdaptiveMediaImageFileVersion(
			Mockito.anyInt()
		);

		_processor.cleanUp(_fileVersion);
	}

	@Test(expected = AdaptiveMediaRuntimeException.IOException.class)
	public void testCleanUpPortalException() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		Mockito.doThrow(
			PortalException.class
		).when(
			_imageLocalService
		).deleteAdaptiveMediaImageFileVersion(
			Mockito.anyInt()
		);

		_processor.cleanUp(_fileVersion);
	}

	@Test
	public void testCleanUpWhenNotSupported() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			false
		);

		_processor.cleanUp(_fileVersion);

		Mockito.verify(
			_imageLocalService, Mockito.never()
		).deleteAdaptiveMediaImageFileVersion(
			_fileVersion.getFileVersionId()
		);
	}

	@Test
	public void testProcessConfigurationAlwaysClosesInputStream()
		throws Exception {

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(
				new ImageAdaptiveMediaConfigurationEntryImpl(
					StringUtil.randomString(), StringUtil.randomString(),
					Collections.emptyMap()))
		);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			null
		);

		Mockito.when(
			_imageProcessor.scaleImage(
				Mockito.any(FileVersion.class),
				Mockito.any(ImageAdaptiveMediaConfigurationEntry.class))
		).thenReturn(
			Mockito.mock(RenderedImage.class)
		);

		InputStream inputStream = Mockito.mock(InputStream.class);

		Mockito.when(
			_fileVersion.getContentStream(false)
		).thenReturn(
			inputStream
		);

		Mockito.when(
			_tiffOrientationTransformer.getTiffOrientationValue(inputStream)
		).thenReturn(
			Optional.empty()
		);

		_processor.process(_fileVersion, StringUtil.randomString());

		Mockito.verify(
			inputStream
		).close();
	}

	@Test
	public void testProcessConfigurationWhenAdaptiveMediaImageAlreadyExists()
		throws Exception {

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(
				new ImageAdaptiveMediaConfigurationEntryImpl(
					StringUtil.randomString(), StringUtil.randomString(),
					Collections.emptyMap()))
		);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			Mockito.mock(AdaptiveMediaImage.class)
		);

		_processor.process(_fileVersion, StringUtil.randomString());

		Mockito.verify(
			_imageProcessor, Mockito.never()
		).scaleImage(
			Mockito.any(FileVersion.class),
			Mockito.any(ImageAdaptiveMediaConfigurationEntry.class)
		);
	}

	@Test(expected = AdaptiveMediaRuntimeException.IOException.class)
	public void testProcessConfigurationWhenFileVersionThrowsPortalException()
		throws Exception {

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(
				new ImageAdaptiveMediaConfigurationEntryImpl(
					StringUtil.randomString(), StringUtil.randomString(),
					Collections.emptyMap()))
		);

		Mockito.when(
			_imageLocalService.fetchAdaptiveMediaImage(
				Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			null
		);

		Mockito.when(
			_imageProcessor.scaleImage(
				Mockito.any(FileVersion.class),
				Mockito.any(ImageAdaptiveMediaConfigurationEntry.class))
		).thenReturn(
			Mockito.mock(RenderedImage.class)
		);

		Mockito.doThrow(
			PortalException.class
		).when(
			_fileVersion
		).getContentStream(
			false
		);

		_processor.process(_fileVersion, StringUtil.randomString());
	}

	@Test
	public void testProcessConfigurationWhenNoConfigurationEntry()
		throws Exception {

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.empty()
		);

		_processor.process(_fileVersion, StringUtil.randomString());

		Mockito.verify(
			_imageLocalService, Mockito.never()
		).fetchAdaptiveMediaImage(
			Mockito.anyString(), Mockito.anyLong()
		);
	}

	@Test
	public void testProcessConfigurationWhenNotSupported() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			false
		);

		_processor.process(_fileVersion, StringUtil.randomString());

		Mockito.verify(
			_configurationHelper, Mockito.never()
		).getImageAdaptiveMediaConfigurationEntry(
			Mockito.anyLong(), Mockito.anyString()
		);
	}

	@Test(expected = AdaptiveMediaRuntimeException.IOException.class)
	public void testProcessDuplicateAdaptiveMediaImageExceptionInImageService()
		throws Exception {

		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.anyLong())
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(configurationEntry)
		);

		RenderedImage renderedImage = Mockito.mock(RenderedImage.class);

		Mockito.when(
			_imageProcessor.scaleImage(_fileVersion, configurationEntry)
		).thenReturn(
			renderedImage
		);

		Mockito.when(
			_tiffOrientationTransformer.getTiffOrientationValue(
				Mockito.any(InputStream.class))
		).thenReturn(
			Optional.empty()
		);

		Mockito.doThrow(
			DuplicateAdaptiveMediaImageException.class
		).when(
			_imageLocalService
		).addAdaptiveMediaImage(
			Mockito.any(ImageAdaptiveMediaConfigurationEntry.class),
			Mockito.any(FileVersion.class), Mockito.anyInt(), Mockito.anyInt(),
			Mockito.any(InputStream.class), Mockito.anyInt()
		);

		_processor.process(_fileVersion);
	}

	@Test
	public void testProcessFileVersion() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.anyLong())
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(configurationEntry)
		);

		RenderedImage renderedImage = Mockito.mock(RenderedImage.class);

		Mockito.when(
			_imageProcessor.scaleImage(_fileVersion, configurationEntry)
		).thenReturn(
			renderedImage
		);

		Mockito.when(
			_tiffOrientationTransformer.getTiffOrientationValue(
				Mockito.any(InputStream.class))
		).thenReturn(
			Optional.empty()
		);

		_processor.process(_fileVersion);

		Mockito.verify(
			_imageProcessor
		).scaleImage(
			_fileVersion, configurationEntry
		);

		Mockito.verify(
			_imageLocalService
		).addAdaptiveMediaImage(
			Mockito.any(ImageAdaptiveMediaConfigurationEntry.class),
			Mockito.any(FileVersion.class), Mockito.anyInt(), Mockito.anyInt(),
			Mockito.any(InputStream.class), Mockito.anyInt()
		);
	}

	@Test(expected = AdaptiveMediaRuntimeException.InvalidConfiguration.class)
	public void testProcessInvalidConfigurationException() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		Mockito.doThrow(
			AdaptiveMediaRuntimeException.InvalidConfiguration.class
		).when(
			_configurationHelper
		).getImageAdaptiveMediaConfigurationEntries(
			Mockito.anyLong()
		);

		_processor.process(_fileVersion);
	}

	@Test(expected = AdaptiveMediaRuntimeException.IOException.class)
	public void testProcessIOExceptionInImageProcessor() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.anyLong())
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(configurationEntry)
		);

		Mockito.doThrow(
			AdaptiveMediaRuntimeException.IOException.class
		).when(
			_imageProcessor
		).scaleImage(
			_fileVersion, configurationEntry
		);

		_processor.process(_fileVersion);
	}

	@Test(expected = AdaptiveMediaRuntimeException.IOException.class)
	public void testProcessIOExceptionInStorage() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				Collections.emptyMap());

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.anyLong())
		).thenReturn(
			Collections.singleton(configurationEntry)
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(configurationEntry)
		);

		RenderedImage renderedImage = Mockito.mock(RenderedImage.class);

		Mockito.when(
			_imageProcessor.scaleImage(_fileVersion, configurationEntry)
		).thenReturn(
			renderedImage
		);

		Mockito.when(
			_tiffOrientationTransformer.getTiffOrientationValue(
				Mockito.any(InputStream.class))
		).thenReturn(
			Optional.empty()
		);

		Mockito.doThrow(
			AdaptiveMediaRuntimeException.IOException.class
		).when(
			_imageLocalService
		).addAdaptiveMediaImage(
			Mockito.any(ImageAdaptiveMediaConfigurationEntry.class),
			Mockito.any(FileVersion.class), Mockito.anyInt(), Mockito.anyInt(),
			Mockito.any(InputStream.class), Mockito.anyInt()
		);

		_processor.process(_fileVersion);
	}

	@Test
	public void testProcessWhenNoConfigurationEntries() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			true
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntries(
				Mockito.anyLong())
		).thenReturn(
			Collections.emptyList()
		);

		_processor.process(_fileVersion);

		Mockito.verify(
			_imageProcessor, Mockito.never()
		).scaleImage(
			Mockito.any(FileVersion.class),
			Mockito.any(ImageAdaptiveMediaConfigurationEntry.class)
		);

		Mockito.verify(
			_imageLocalService, Mockito.never()
		).addAdaptiveMediaImage(
			Mockito.any(ImageAdaptiveMediaConfigurationEntry.class),
			Mockito.any(FileVersion.class), Mockito.anyInt(),
			Mockito.any(Integer.class), Mockito.any(InputStream.class),
			Mockito.any(Integer.class)
		);
	}

	@Test
	public void testProcessWhenNotSupported() throws Exception {
		Mockito.when(
			_imageProcessor.isMimeTypeSupported(Mockito.anyString())
		).thenReturn(
			false
		);

		_processor.process(_fileVersion);

		Mockito.verify(
			_configurationHelper, Mockito.never()
		).getImageAdaptiveMediaConfigurationEntries(
			Mockito.anyLong()
		);
	}

	private final ImageAdaptiveMediaConfigurationHelper _configurationHelper =
		Mockito.mock(ImageAdaptiveMediaConfigurationHelper.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);
	private final AdaptiveMediaImageLocalService _imageLocalService =
		Mockito.mock(AdaptiveMediaImageLocalService.class);
	private final ImageProcessor _imageProcessor = Mockito.mock(
		ImageProcessor.class);
	private final ImageTool _imageTool = Mockito.mock(ImageTool.class);
	private final ImageAdaptiveMediaProcessorImpl _processor =
		new ImageAdaptiveMediaProcessorImpl();
	private final TiffOrientationTransformer _tiffOrientationTransformer =
		Mockito.mock(TiffOrientationTransformer.class);

}