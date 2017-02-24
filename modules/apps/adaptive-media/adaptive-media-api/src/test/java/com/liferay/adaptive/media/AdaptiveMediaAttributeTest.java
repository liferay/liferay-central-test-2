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

package com.liferay.adaptive.media;

import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveMediaAttributeTest {

	@Test
	public void testAllPublicAttributesAreSupported() {
		Collection<AdaptiveMediaAttribute<?, ?>> publicAttributes =
			Arrays.asList(
				AdaptiveMediaAttribute.configurationUuid(),
				AdaptiveMediaAttribute.contentLength(),
				AdaptiveMediaAttribute.contentType(),
				AdaptiveMediaAttribute.fileName());

		Map<String, AdaptiveMediaAttribute<?, ?>> allowedAttributesMap =
			AdaptiveMediaAttribute.allowedAttributes();

		Collection<AdaptiveMediaAttribute<?, ?>> allowedAttributes =
			allowedAttributesMap.values();

		Assert.assertTrue(allowedAttributes.containsAll(publicAttributes));
	}

	@Test
	public void testConfigurationUuidRecognizesAnyString() {
		AdaptiveMediaAttribute<?, String> configurationUuid =
			AdaptiveMediaAttribute.configurationUuid();

		String value = StringUtil.randomString();

		Assert.assertEquals(value, configurationUuid.convert(value));
	}

	@Test(
		expected = AdaptiveMediaRuntimeException.AdaptiveMediaAttributeFormatException.class
	)
	public void testContentLengthFailsForNonIntegers() {
		AdaptiveMediaAttribute<?, Integer> contentLength =
			AdaptiveMediaAttribute.contentLength();

		contentLength.convert(StringUtil.randomString());
	}

	@Test
	public void testContentLengthRecognizesIntegers() {
		AdaptiveMediaAttribute<?, Integer> contentLength =
			AdaptiveMediaAttribute.contentLength();

		Integer value = RandomUtil.nextInt(Integer.MAX_VALUE);

		Assert.assertEquals(
			value, contentLength.convert(String.valueOf(value)));
	}

	@Test
	public void testContentTypeRecognizesAnyString() {
		AdaptiveMediaAttribute<?, String> contentType =
			AdaptiveMediaAttribute.contentType();

		String value = StringUtil.randomString();

		Assert.assertEquals(value, contentType.convert(value));
	}

	@Test
	public void testFileNameRecognizesAnyString() {
		AdaptiveMediaAttribute<?, String> fileName =
			AdaptiveMediaAttribute.fileName();

		String value = StringUtil.randomString();

		Assert.assertEquals(value, fileName.convert(value));
	}

}