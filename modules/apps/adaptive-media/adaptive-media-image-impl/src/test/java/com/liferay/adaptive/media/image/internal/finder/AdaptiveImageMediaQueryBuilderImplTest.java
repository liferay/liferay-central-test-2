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

import com.liferay.adaptive.media.image.internal.processor.AdaptiveImageMediaProperty;
import com.liferay.portal.kernel.repository.model.FileVersion;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveImageMediaQueryBuilderImplTest {

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileVersionFailsWhenQueryingAll() {
		_adaptiveImageMediaQueryBuilder.allForModel(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileVersionFailsWhenQueryingProperties() {
		_adaptiveImageMediaQueryBuilder.forModel(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullValueFailsWhenQueryingProperties() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_adaptiveImageMediaQueryBuilder.
			forModel(fileVersion).
			with(AdaptiveImageMediaProperty.IMAGE_HEIGHT, null);
	}

	private final AdaptiveImageMediaQueryBuilderImpl
		_adaptiveImageMediaQueryBuilder =
			new AdaptiveImageMediaQueryBuilderImpl();

}