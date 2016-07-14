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

import com.liferay.adaptive.media.image.internal.processor.ImageAdaptiveMediaAttribute;
import com.liferay.portal.kernel.repository.model.FileVersion;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaQueryBuilderImplTest {

	@Test(expected = IllegalArgumentException.class)
	public void testNullAttributeValueFailsWhenQueryingAttributes() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_queryBuilder.
			forModel(fileVersion).
			with(ImageAdaptiveMediaAttribute.IMAGE_HEIGHT, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileVersionFailsWhenQueryingAll() {
		_queryBuilder.allForModel(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileVersionFailsWhenQueryingAttributes() {
		_queryBuilder.forModel(null);
	}

	private final ImageAdaptiveMediaQueryBuilderImpl _queryBuilder =
		new ImageAdaptiveMediaQueryBuilderImpl();

}