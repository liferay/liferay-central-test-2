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

package com.liferay.adaptive.media.image.internal.handler;

import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaFinder;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMedia;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorException;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorRuntimeException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaRequestHandlerTest {

	@Before
	public void setUp() {
		_requestHandler.setDLAppLocalService(_dlAppLocalService);
		_requestHandler.setImageAdaptiveMediaConfigurationHelper(
			_configurationHelper);
		_requestHandler.setImageAdaptiveMediaFinder(_finder);
	}

	@Test
	public void testDLAppFailure() throws Exception {
		Mockito.when(
			_request.getPathInfo()
		).thenReturn(
			"/0/0/0/0/1/0/file.png"
		);

		Mockito.when(
			_dlAppLocalService.getFileVersion(Mockito.any(long.class))
		).thenThrow(
			PortalException.class
		);

		Optional<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaOptional =
			_requestHandler.handleRequest(_request);

		Assert.assertFalse(mediaOptional.isPresent());
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.class)
	public void testFinderFailsWithMediapProcessorException() throws Exception {
		Mockito.when(
			_request.getPathInfo()
		).thenReturn(
			"/0/0/0/0/1/0/file.png"
		);

		Mockito.when(
			_dlAppLocalService.getFileVersion(1L)
		).thenReturn(
			_fileVersion
		);

		Mockito.when(
			_fileVersion.getCompanyId()
		).thenReturn(
			0L
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.any(long.class), Mockito.any(String.class))
		).thenReturn(
			Optional.empty()
		);

		Mockito.when(
			_finder.getAdaptiveMedia(Mockito.any(Function.class))
		).thenThrow(
			AdaptiveMediaProcessorException.class
		);

		_requestHandler.handleRequest(_request);
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.class)
	public void testFinderFailsWithPortalException() throws Exception {
		Mockito.when(
			_request.getPathInfo()
		).thenReturn(
			"/0/0/0/0/1/0/file.png"
		);

		Mockito.when(
			_dlAppLocalService.getFileVersion(1L)
		).thenReturn(
			_fileVersion
		);

		Mockito.when(
			_fileVersion.getCompanyId()
		).thenReturn(
			0L
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.any(long.class), Mockito.any(String.class))
		).thenReturn(
			Optional.empty()
		);

		Mockito.when(
			_finder.getAdaptiveMedia(Mockito.any(Function.class))
		).thenThrow(
			PortalException.class
		);

		_requestHandler.handleRequest(_request);
	}

	@Test
	public void testInvalidFileVersionId() throws Exception {
		Mockito.when(
			_request.getPathInfo()
		).thenReturn(
			"/0/0/0/0/a/0/file.png"
		);

		Optional<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaOptional =
			_requestHandler.handleRequest(_request);

		Assert.assertFalse(mediaOptional.isPresent());
	}

	@Test
	public void testInvalidShorterPath() throws Exception {
		Mockito.when(
			_request.getPathInfo()
		).thenReturn(
			"/0/0/0/0/1/0"
		);

		Optional<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaOptional =
			_requestHandler.handleRequest(_request);

		Assert.assertFalse(mediaOptional.isPresent());
	}

	@Test(expected = NullPointerException.class)
	public void testNullRequest() throws Exception {
		_requestHandler.handleRequest(null);
	}

	@Test
	public void testWithNoConfigurationInvokesFinderWithDefaults()
		throws Exception {

		Mockito.when(
			_request.getPathInfo()
		).thenReturn(
			"/0/0/0/0/1/0/file.png"
		);

		Mockito.when(
			_dlAppLocalService.getFileVersion(1L)
		).thenReturn(
			_fileVersion
		);

		Mockito.when(
			_fileVersion.getCompanyId()
		).thenReturn(
			0L
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.any(long.class), Mockito.any(String.class))
		).thenReturn(
			Optional.empty()
		);

		Mockito.when(
			_finder.getAdaptiveMedia(Mockito.any(Function.class))
		).thenReturn(
			Stream.empty()
		);

		_requestHandler.handleRequest(_request);

		Mockito.verify(
			_finder
		).getAdaptiveMedia(Mockito.any(Function.class));
	}

	private final ImageAdaptiveMediaConfigurationHelper _configurationHelper =
		Mockito.mock(ImageAdaptiveMediaConfigurationHelper.class);
	private final DLAppLocalService _dlAppLocalService = Mockito.mock(
		DLAppLocalService.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);
	private final ImageAdaptiveMediaFinder _finder = Mockito.mock(
		ImageAdaptiveMediaFinder.class);
	private final HttpServletRequest _request = Mockito.mock(
		HttpServletRequest.class);
	private final ImageAdaptiveMediaRequestHandler _requestHandler =
		new ImageAdaptiveMediaRequestHandler();

}