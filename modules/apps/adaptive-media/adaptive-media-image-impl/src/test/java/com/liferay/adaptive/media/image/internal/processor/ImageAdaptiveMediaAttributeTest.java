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
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaAttribute;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaAttributeTest {

	@Test(
		expected = AdaptiveMediaRuntimeException.AdaptiveMediaAttributeFormatException.class
	)
	public void testImageMaxHeightFailsForNonIntegers() {
		ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT.convert("xyz");
	}

	@Test
	public void testImageMaxHeightRecognizesIntegers() {
		int result = ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT.convert("42");

		Assert.assertEquals(42, result);
	}

	@Test(
		expected = AdaptiveMediaRuntimeException.AdaptiveMediaAttributeFormatException.class
	)
	public void testImageMaxWidthFailsForNonIntegers() {
		ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH.convert("xyz");
	}

	@Test
	public void testImageMaxWidthRecognizesIntegers() {
		int result = ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH.convert("42");

		Assert.assertEquals(42, result);
	}

}