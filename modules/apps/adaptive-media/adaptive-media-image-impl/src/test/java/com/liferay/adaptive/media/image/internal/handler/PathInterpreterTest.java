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

import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationHelper;
import com.liferay.adaptive.media.image.internal.util.Tuple;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorRuntimeException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class PathInterpreterTest {

	@Before
	public void setUp() {
		_pathInterpreter.setDLAppLocalService(_dlAppLocalService);
		_pathInterpreter.setImageAdaptiveMediaConfigurationHelper(
			_configurationHelper);
	}

	@Test
	public void testFileEntryPath() throws Exception {
		Mockito.when(
			_dlAppLocalService.getFileEntry(Mockito.any(long.class))
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_fileEntry.getLatestFileVersion()
		).thenReturn(
			_fileVersion
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.any(long.class), Mockito.eq("x"))
		).thenReturn(
			Optional.of(_configurationEntry)
		);

		_pathInterpreter.interpretPath("image/0/x/foo.jpg");

		Mockito.verify(_dlAppLocalService).getFileEntry(0);
		Mockito.verify(_fileVersion).getCompanyId();
		Mockito.verify(_configurationEntry).getProperties();
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.class)
	public void testFileEntryPathDLAppFailure() throws Exception {
		Mockito.when(
			_dlAppLocalService.getFileEntry(0)
		).thenThrow(
			PortalException.class
		);

		_pathInterpreter.interpretPath("image/0/x/foo.jpg");
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.class)
	public void testFileEntryPathGetLatestFileVersionFailure()
		throws Exception {

		Mockito.when(
			_dlAppLocalService.getFileEntry(0)
		).thenReturn(
			_fileEntry
		);

		Mockito.when(
			_fileEntry.getLatestFileVersion()
		).thenThrow(
			PortalException.class
		);

		_pathInterpreter.interpretPath("image/0/x/foo.jpg");
	}

	@Test
	public void testFileVersionPath() throws Exception {
		Mockito.when(
			_dlAppLocalService.getFileVersion(1)
		).thenReturn(
			_fileVersion
		);

		Mockito.when(
			_configurationHelper.getImageAdaptiveMediaConfigurationEntry(
				Mockito.any(long.class), Mockito.eq("x"))
		).thenReturn(
			Optional.of(_configurationEntry)
		);

		_pathInterpreter.interpretPath("image/0/1/x/foo.jpg");

		Mockito.verify(_dlAppLocalService).getFileEntry(0);
		Mockito.verify(_dlAppLocalService).getFileVersion(1);
		Mockito.verify(_fileVersion).getCompanyId();
		Mockito.verify(_configurationEntry).getProperties();
	}

	@Test(expected = AdaptiveMediaProcessorRuntimeException.class)
	public void testFileVersionPathDLAppFailure() throws Exception {
		Mockito.when(
			_dlAppLocalService.getFileVersion(1)
		).thenThrow(
			PortalException.class
		);

		_pathInterpreter.interpretPath("image/0/1/x/foo.jpg");
	}

	@Test
	public void testNonMatchingPathInfo() {
		Optional<Tuple<FileVersion, Map<String, String>>> resultOptional =
			_pathInterpreter.interpretPath("/" + StringUtil.randomString());

		Assert.assertFalse(resultOptional.isPresent());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPathInfoFails() {
		_pathInterpreter.interpretPath(null);
	}

	private final ImageAdaptiveMediaConfigurationEntry _configurationEntry =
		Mockito.mock(ImageAdaptiveMediaConfigurationEntry.class);
	private final ImageAdaptiveMediaConfigurationHelper _configurationHelper =
		Mockito.mock(ImageAdaptiveMediaConfigurationHelper.class);
	private final DLAppLocalService _dlAppLocalService = Mockito.mock(
		DLAppLocalService.class);
	private final FileEntry _fileEntry = Mockito.mock(FileEntry.class);
	private final FileVersion _fileVersion = Mockito.mock(FileVersion.class);
	private final PathInterpreter _pathInterpreter = new PathInterpreter();

}