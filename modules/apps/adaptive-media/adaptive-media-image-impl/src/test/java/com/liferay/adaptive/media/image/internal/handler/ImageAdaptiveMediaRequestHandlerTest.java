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

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaFinder;
import com.liferay.adaptive.media.image.internal.util.Tuple;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

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
		_requestHandler.setImageAdaptiveMediaFinder(_finder);
		_requestHandler.setPathInterpreter(_pathInterpreter);
		_requestHandler.setImageAdaptiveMediaConfigurationHelper(
			_configurationHelper);
	}

	@Test(expected = AdaptiveMediaRuntimeException.class)
	public void testFinderFailsWithMediaProcessorException() throws Exception {
		Map<String, String> pathProperties = new HashMap<>();

		pathProperties.put("configuration-uuid", "testUuid");

		Mockito.when(
			_pathInterpreter.interpretPath(Mockito.any(String.class))
		).thenReturn(
			Optional.of(Tuple.of(_fileVersion, pathProperties))
		);

		Mockito.when(
			_finder.getAdaptiveMedia(Mockito.any(Function.class))
		).thenThrow(
			AdaptiveMediaException.class
		);

		ImageAdaptiveMediaConfigurationEntry configurationEntry = Mockito.mock(
			ImageAdaptiveMediaConfigurationEntry.class);

		Map<String, String> configurationEntryProperties = new HashMap<>();

		configurationEntryProperties.put("max-height", "200");
		configurationEntryProperties.put("max-width", "200");

		Mockito.when(
			configurationEntry.getProperties()
		).thenReturn(
			configurationEntryProperties
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(configurationEntry)
		);

		_requestHandler.handleRequest(_request);
	}

	@Test(expected = AdaptiveMediaRuntimeException.class)
	public void testFinderFailsWithPortalException() throws Exception {
		Map<String, String> pathProperties = new HashMap<>();

		pathProperties.put("configuration-uuid", "testUuid");

		Mockito.when(
			_pathInterpreter.interpretPath(Mockito.any(String.class))
		).thenReturn(
			Optional.of(Tuple.of(_fileVersion, pathProperties))
		);

		Mockito.when(
			_finder.getAdaptiveMedia(Mockito.any(Function.class))
		).thenThrow(
			PortalException.class
		);

		ImageAdaptiveMediaConfigurationEntry configurationEntry = Mockito.mock(
			ImageAdaptiveMediaConfigurationEntry.class);

		Map<String, String> configurationEntryProperties = new HashMap<>();

		configurationEntryProperties.put("max-height", "200");
		configurationEntryProperties.put("max-width", "200");

		Mockito.when(
			configurationEntry.getProperties()
		).thenReturn(
			configurationEntryProperties
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Optional.of(configurationEntry)
		);

		_requestHandler.handleRequest(_request);
	}

	@Test
	public void testInvalidPath() throws Exception {
		Mockito.when(
			_pathInterpreter.interpretPath(Mockito.any(String.class))
		).thenReturn(
			Optional.empty()
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
	public void testPathInterpreterFailure() throws Exception {
		Mockito.when(
			_pathInterpreter.interpretPath(Mockito.any(String.class))
		).thenThrow(
			AdaptiveMediaRuntimeException.class
		);

		Optional<AdaptiveMedia<ImageAdaptiveMediaProcessor>> mediaOptional =
			_requestHandler.handleRequest(_request);

		Assert.assertFalse(mediaOptional.isPresent());
	}

	private final ImageAdaptiveMediaConfigurationHelper _configurationHelper =
		Mockito.mock(ImageAdaptiveMediaConfigurationHelper.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);
	private final ImageAdaptiveMediaFinder _finder = Mockito.mock(
		ImageAdaptiveMediaFinder.class);
	private final PathInterpreter _pathInterpreter = Mockito.mock(
		PathInterpreter.class);
	private final HttpServletRequest _request = Mockito.mock(
		HttpServletRequest.class);
	private final ImageAdaptiveMediaRequestHandler _requestHandler =
		new ImageAdaptiveMediaRequestHandler();

}