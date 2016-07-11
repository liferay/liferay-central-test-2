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

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.adaptive.media.image.internal.processor.AdaptiveImageMediaProperty;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveImagePropertyMappingTest {

	@Test
	public void testCreateFromEmptyMap() {
		AdaptiveImagePropertyMapping adaptiveImagePropertyMapping =
			AdaptiveImagePropertyMapping.fromProperties(Collections.emptyMap());

		Optional<Integer> height =
			adaptiveImagePropertyMapping.getPropertyValue(
				AdaptiveImageMediaProperty.IMAGE_HEIGHT);

		Assert.assertFalse(height.isPresent());

		Optional<Integer> width = adaptiveImagePropertyMapping.getPropertyValue(
			AdaptiveImageMediaProperty.IMAGE_WIDTH);

		Assert.assertFalse(width.isPresent());
	}

	@Test
	public void testIgnoreUnknownProperties() {
		AdaptiveImagePropertyMapping adaptiveImagePropertyMapping =
			AdaptiveImagePropertyMapping.fromProperties(
				MapUtil.fromArray("foo", StringUtil.randomString()));

		Optional<Integer> height =
			adaptiveImagePropertyMapping.getPropertyValue(
				AdaptiveImageMediaProperty.IMAGE_HEIGHT);

		Assert.assertFalse(height.isPresent());

		Optional<Integer> width = adaptiveImagePropertyMapping.getPropertyValue(
			AdaptiveImageMediaProperty.IMAGE_WIDTH);

		Assert.assertFalse(width.isPresent());
	}

	@Test
	public void testValidProperties() {
		AdaptiveImagePropertyMapping adaptiveImagePropertyMapping =
			AdaptiveImagePropertyMapping.fromProperties(
				MapUtil.fromArray(
					AdaptiveImageMediaProperty.IMAGE_HEIGHT.getName(), "100",
					AdaptiveImageMediaProperty.IMAGE_WIDTH.getName(), "200"));

		Optional<Integer> height =
			adaptiveImagePropertyMapping.getPropertyValue(
				AdaptiveImageMediaProperty.IMAGE_HEIGHT);

		Assert.assertEquals(Integer.valueOf(100), height.get());

		Optional<Integer> width = adaptiveImagePropertyMapping.getPropertyValue(
			AdaptiveImageMediaProperty.IMAGE_WIDTH);

		Assert.assertEquals(Integer.valueOf(200), width.get());
	}

}