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

package com.liferay.adaptive.media.image.internal.util;

import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.StringUtil;

import java.nio.file.Path;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class ImageStorageTest {

	@Test
	public void testGetFileVersionPath() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		Mockito.when(
			fileVersion.getGroupId()
		).thenReturn(
			1L
		);

		Mockito.when(
			fileVersion.getRepositoryId()
		).thenReturn(
			2L
		);

		Mockito.when(
			fileVersion.getFileEntryId()
		).thenReturn(
			3L
		);

		Mockito.when(
			fileVersion.getFileVersionId()
		).thenReturn(
			4L
		);

		Path fileVersionPath = _imageStorage.getFileVersionPath(fileVersion);

		Assert.assertEquals("adaptive/1/2/3/4", fileVersionPath.toString());
	}

	@Test
	public void testGetFileVersionVariantPath() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		Mockito.when(
			fileVersion.getGroupId()
		).thenReturn(
			1L
		);

		Mockito.when(
			fileVersion.getRepositoryId()
		).thenReturn(
			2L
		);

		Mockito.when(
			fileVersion.getFileEntryId()
		).thenReturn(
			3L
		);

		Mockito.when(
			fileVersion.getFileVersionId()
		).thenReturn(
			4L
		);

		ImageAdaptiveMediaConfigurationEntry configurationEntry =
			new ImageAdaptiveMediaConfigurationEntry(
				StringUtil.randomString(), "xyz", Collections.emptyMap());

		Path fileVersionVariantPath = _imageStorage.getFileVersionVariantPath(
			fileVersion, configurationEntry);

		Assert.assertEquals(
			"adaptive/1/2/3/4/xyz", fileVersionVariantPath.toString());
	}

	private final ImageStorage _imageStorage = new ImageStorage();

}