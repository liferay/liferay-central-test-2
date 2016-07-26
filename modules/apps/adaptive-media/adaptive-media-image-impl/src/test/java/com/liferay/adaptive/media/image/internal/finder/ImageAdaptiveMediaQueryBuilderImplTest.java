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

import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.internal.processor.ImageAdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaQueryBuilderImplTest {

	@Test
	public void testFileEntryQueryReturnsLatestFileVersion() throws Exception {
		FileEntry fileEntry = Mockito.mock(FileEntry.class);

		_queryBuilder.allForFileEntry(fileEntry);

		Assert.assertEquals(
			fileEntry.getLatestFileVersion(), _queryBuilder.getFileVersion());
	}

	@Test
	public void testFileEntryWithAttributesQueryReturnsLatestFileVersion()
		throws Exception {

		FileEntry fileEntry = Mockito.mock(FileEntry.class);

		_queryBuilder.forFileEntry(fileEntry).done();

		Assert.assertEquals(
			fileEntry.getLatestFileVersion(), _queryBuilder.getFileVersion());
	}

	@Test
	public void testNonNullOptionalAttributeQuery() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_queryBuilder.
			forVersion(fileVersion).
			with(ImageAdaptiveMediaAttribute.IMAGE_HEIGHT, Optional.of(100));

		Map<AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, Object>
			attributes = _queryBuilder.getAttributes();

		Assert.assertEquals(
			100, attributes.get(ImageAdaptiveMediaAttribute.IMAGE_HEIGHT));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullAttributeValueFailsWhenQueryingAttributes() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_queryBuilder.
			forVersion(fileVersion).
			with(ImageAdaptiveMediaAttribute.IMAGE_HEIGHT, (Integer)null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileEntryFailsWhenQueryingAll() {
		_queryBuilder.allForFileEntry(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileEntryFailsWhenQueryingAttributes() {
		_queryBuilder.forFileEntry(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileVersionFailsWhenQueryingAll() {
		_queryBuilder.allForVersion(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileVersionFailsWhenQueryingAttributes() {
		_queryBuilder.forVersion(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullOptionalAttributeValueFailsWhenQueryingAttributes() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_queryBuilder.
			forVersion(fileVersion).
			with(
				ImageAdaptiveMediaAttribute.IMAGE_HEIGHT,
				(Optional<Integer>)null);
	}

	private final ImageAdaptiveMediaQueryBuilderImpl _queryBuilder =
		new ImageAdaptiveMediaQueryBuilderImpl();

}