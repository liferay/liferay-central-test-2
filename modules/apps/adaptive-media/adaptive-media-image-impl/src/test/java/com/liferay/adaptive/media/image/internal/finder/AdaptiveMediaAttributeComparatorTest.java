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

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaAttributeMapping;
import com.liferay.adaptive.media.image.internal.processor.ImageAdaptiveMedia;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveMediaAttributeComparatorTest {

	@Before
	public void setUp() {
		Map<AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, Boolean>
			attributes = new HashMap<>();

		attributes.put(AdaptiveMediaAttribute.contentLength(), true);
		attributes.put(AdaptiveMediaAttribute.fileName(), false);

		_multiAttributeComparator = new AdaptiveMediaAttributeComparator(
			attributes);
	}

	@Test
	public void testSortDifferentMediaByMultipleAttributes() {
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			_createMedia(
				AdaptiveMediaAttribute.contentLength(), 10,
				AdaptiveMediaAttribute.fileName(), "zzz");
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia2 =
			_createMedia(
				AdaptiveMediaAttribute.contentLength(), 10,
				AdaptiveMediaAttribute.fileName(), "aaa");

		int result = _multiAttributeComparator.compare(
			adaptiveMedia1, adaptiveMedia2);

		Assert.assertEquals(-25, result);
	}

	@Test
	public void testSortDifferentMediaByMultipleAttributesInverse() {
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			_createMedia(
				AdaptiveMediaAttribute.contentLength(), 10,
				AdaptiveMediaAttribute.fileName(), "zzz");
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia2 =
			_createMedia(
				AdaptiveMediaAttribute.contentLength(), 10,
				AdaptiveMediaAttribute.fileName(), "aaa");

		int result = _multiAttributeComparator.compare(
			adaptiveMedia2, adaptiveMedia1);

		Assert.assertEquals(25, result);
	}

	@Test
	public void testSortDifferentMediaByOneAttribute() {
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			_createMedia(AdaptiveMediaAttribute.contentLength(), 10);
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia2 =
			_createMedia(AdaptiveMediaAttribute.contentLength(), 20);

		int result = _singleAttributeComparator.compare(
			adaptiveMedia1, adaptiveMedia2);

		Assert.assertEquals(-10, result);
	}

	@Test
	public void testSortDifferentMediaByOneAttributeInverse() {
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			_createMedia(AdaptiveMediaAttribute.contentLength(), 10);
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia2 =
			_createMedia(AdaptiveMediaAttribute.contentLength(), 20);

		int result = _singleAttributeComparator.compare(
			adaptiveMedia2, adaptiveMedia1);

		Assert.assertEquals(10, result);
	}

	@Test
	public void testSortEqualMediaByMultipleAttributes() {
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			_createMedia(
				AdaptiveMediaAttribute.contentLength(), 10,
				AdaptiveMediaAttribute.fileName(), "aaa");
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia2 =
			_createMedia(
				AdaptiveMediaAttribute.contentLength(), 10,
				AdaptiveMediaAttribute.fileName(), "aaa");

		int result = _singleAttributeComparator.compare(
			adaptiveMedia1, adaptiveMedia2);

		Assert.assertEquals(0, result);
	}

	@Test
	public void testSortEqualMediaByOneAttribute() {
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1 =
			_createMedia(AdaptiveMediaAttribute.contentLength(), 10);
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia2 =
			_createMedia(AdaptiveMediaAttribute.contentLength(), 10);

		int result = _singleAttributeComparator.compare(
			adaptiveMedia1, adaptiveMedia2);

		Assert.assertEquals(0, result);
	}

	private <S, T> AdaptiveMedia<ImageAdaptiveMediaProcessor> _createMedia(
		AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, S> attribute1,
		S value1,
		AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, T> attribute2,
		T value2) {

		Map<String, String> properties = new HashMap<>();

		properties.put(attribute1.getName(), String.valueOf(value1));
		properties.put(attribute2.getName(), String.valueOf(value2));

		ImageAdaptiveMediaAttributeMapping attributeMapping =
			ImageAdaptiveMediaAttributeMapping.fromProperties(properties);

		return new ImageAdaptiveMedia(() -> null, attributeMapping, null);
	}

	private <T> AdaptiveMedia<ImageAdaptiveMediaProcessor> _createMedia(
		AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, T> attribute,
		T value) {

		ImageAdaptiveMediaAttributeMapping attributeMapping =
			ImageAdaptiveMediaAttributeMapping.fromProperties(
				Collections.singletonMap(
					attribute.getName(), String.valueOf(value)));

		return new ImageAdaptiveMedia(() -> null, attributeMapping, null);
	}

	private AdaptiveMediaAttributeComparator _multiAttributeComparator;
	private final AdaptiveMediaAttributeComparator _singleAttributeComparator =
		new AdaptiveMediaAttributeComparator(
			AdaptiveMediaAttribute.contentLength());

}