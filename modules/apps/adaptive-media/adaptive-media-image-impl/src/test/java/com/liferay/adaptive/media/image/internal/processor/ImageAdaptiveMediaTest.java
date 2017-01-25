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

import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaAttributeMapping;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaAttribute;

import java.io.InputStream;

import java.net.URI;

import java.util.Collections;
import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaTest {

	@Test
	public void testGetAttributeDelegatesOnMapping() {
		ImageAdaptiveMediaAttributeMapping attributeMapping = Mockito.mock(
			ImageAdaptiveMediaAttributeMapping.class);

		ImageAdaptiveMedia adaptiveMedia = new ImageAdaptiveMedia(
			() -> null, attributeMapping, URI.create("/"));

		adaptiveMedia.getAttributeValue(
			ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT);

		Mockito.verify(
			attributeMapping
		).getAttributeValue(
			ImageAdaptiveMediaAttribute.IMAGE_MAX_HEIGHT
		);
	}

	@Test
	public void testGetInputStreamDelegatesOnSupplier() {
		InputStream inputStream = Mockito.mock(InputStream.class);

		Supplier<InputStream> supplier = () -> inputStream;

		ImageAdaptiveMediaAttributeMapping attributeMapping =
			ImageAdaptiveMediaAttributeMapping.fromProperties(
				Collections.emptyMap());

		ImageAdaptiveMedia adaptiveMedia = new ImageAdaptiveMedia(
			supplier, attributeMapping, URI.create("/"));

		Assert.assertEquals(inputStream, adaptiveMedia.getInputStream());
	}

}