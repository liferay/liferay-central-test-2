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

import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaAttribute;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaAttributeMappingTest {

	@Test
	public void testCreateFromEmptyMap() {
		ImageAdaptiveMediaAttributeMapping attributeMapping =
			ImageAdaptiveMediaAttributeMapping.fromProperties(
				Collections.emptyMap());

		Optional<Integer> heightOptional = attributeMapping.getAttributeValue(
			ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertFalse(heightOptional.isPresent());

		Optional<Integer> maxHeightOptional =
			attributeMapping.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT);

		Assert.assertFalse(maxHeightOptional.isPresent());

		Optional<Integer> widthOptional = attributeMapping.getAttributeValue(
			ImageAdaptiveMediaAttribute.IMAGE_WIDTH);

		Assert.assertFalse(widthOptional.isPresent());

		Optional<Integer> maxWidthOptional = attributeMapping.getAttributeValue(
			ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH);

		Assert.assertFalse(maxWidthOptional.isPresent());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFailWhenCreatingFromNullMap() {
		ImageAdaptiveMediaAttributeMapping.fromProperties(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFailWhenGettingValueOfNullAttribute() {
		ImageAdaptiveMediaAttributeMapping attributeMapping =
			ImageAdaptiveMediaAttributeMapping.fromProperties(
				MapUtil.fromArray(
					ImageAdaptiveMediaAttribute.IMAGE_HEIGHT.getName(), "100",
					ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT.getName(),
					"200", ImageAdaptiveMediaAttribute.IMAGE_WIDTH.getName(),
					"300",
					ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH.getName(),
					"400"));

		attributeMapping.getAttributeValue(null);
	}

	@Test
	public void testIgnoreUnknownAttributes() {
		ImageAdaptiveMediaAttributeMapping attributeMapping =
			ImageAdaptiveMediaAttributeMapping.fromProperties(
				MapUtil.fromArray("foo", StringUtil.randomString()));

		Optional<Integer> heightOptional = attributeMapping.getAttributeValue(
			ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertFalse(heightOptional.isPresent());

		Optional<Integer> maxHeightOptional =
			attributeMapping.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT);

		Assert.assertFalse(maxHeightOptional.isPresent());

		Optional<Integer> maxWidthOptional = attributeMapping.getAttributeValue(
			ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH);

		Assert.assertFalse(maxWidthOptional.isPresent());
	}

	@Test
	public void testValidAttributes() {
		ImageAdaptiveMediaAttributeMapping attributeMapping =
			ImageAdaptiveMediaAttributeMapping.fromProperties(
				MapUtil.fromArray(
					ImageAdaptiveMediaAttribute.IMAGE_HEIGHT.getName(), "100",
					ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT.getName(),
					"200", ImageAdaptiveMediaAttribute.IMAGE_WIDTH.getName(),
					"300",
					ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH.getName(),
					"400"));

		Optional<Integer> heightOptional = attributeMapping.getAttributeValue(
			ImageAdaptiveMediaAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(Integer.valueOf(100), heightOptional.get());

		Optional<Integer> maxHeightOptional =
			attributeMapping.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT);

		Assert.assertEquals(Integer.valueOf(200), maxHeightOptional.get());

		Optional<Integer> widthOptional = attributeMapping.getAttributeValue(
			ImageAdaptiveMediaAttribute.IMAGE_WIDTH);

		Assert.assertEquals(Integer.valueOf(300), widthOptional.get());

		Optional<Integer> maxWidthOptional = attributeMapping.getAttributeValue(
			ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH);

		Assert.assertEquals(Integer.valueOf(400), maxWidthOptional.get());
	}

	@Test
	public void testValidSingleAttribute() {
		ImageAdaptiveMediaAttributeMapping attributeMapping =
			ImageAdaptiveMediaAttributeMapping.fromProperties(
				MapUtil.fromArray(
					ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT.getName(),
					"100"));

		Optional<Integer> maxHeightOptional =
			attributeMapping.getAttributeValue(
				ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT);

		Assert.assertEquals(Integer.valueOf(100), maxHeightOptional.get());

		Optional<Integer> maxWidthOptional = attributeMapping.getAttributeValue(
			ImageAdaptiveMediaAttribute.IMAGE_MAX_WIDTH);

		Assert.assertFalse(maxWidthOptional.isPresent());
	}

}