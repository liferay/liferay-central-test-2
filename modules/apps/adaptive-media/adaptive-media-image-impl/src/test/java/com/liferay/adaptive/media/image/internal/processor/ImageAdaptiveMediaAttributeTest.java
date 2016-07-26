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

import com.liferay.adaptive.media.AdaptiveMediaProcessorRuntimeException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaAttributeTest {

	@Test(
		expected = AdaptiveMediaProcessorRuntimeException.AdaptiveMediaAttributeFormatException.class
	)
	public void testImageHeightFailsForNonIntegers() {
		ImageAdaptiveMediaAttribute.IMAGE_HEIGHT.convert("xyz");
	}

	@Test
	public void testImageHeightRecognizesIntegers() {
		int result = ImageAdaptiveMediaAttribute.IMAGE_HEIGHT.convert("42");

		Assert.assertEquals(42, result);
	}

	@Test(
		expected = AdaptiveMediaProcessorRuntimeException.AdaptiveMediaAttributeFormatException.class
	)
	public void testImageWidthFailsForNonIntegers() {
		ImageAdaptiveMediaAttribute.IMAGE_WIDTH.convert("xyz");
	}

	@Test
	public void testImageWidthRecognizesIntegers() {
		int result = ImageAdaptiveMediaAttribute.IMAGE_WIDTH.convert("42");

		Assert.assertEquals(42, result);
	}

}