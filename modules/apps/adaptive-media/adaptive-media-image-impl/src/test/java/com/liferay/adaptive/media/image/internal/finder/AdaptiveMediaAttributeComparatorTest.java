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
import com.liferay.adaptive.media.image.internal.configuration.AdaptiveMediaImageAttributeMapping;
import com.liferay.adaptive.media.image.internal.processor.AdaptiveMediaImage;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;

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
		Map<AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, ?>, Boolean>
			attributes = new HashMap<>();

		attributes.put(AdaptiveMediaAttribute.contentLength(), true);
		attributes.put(AdaptiveMediaAttribute.fileName(), false);

		_multiAttributeComparator = new AdaptiveMediaAttributeComparator(
			attributes);
	}

	@Test
	public void testSortDifferentMediaByMultipleAttributes() {
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia1 =
			_createMedia(
				AdaptiveMediaAttribute.contentLength(), 10,
				AdaptiveMediaAttribute.fileName(), "zzz");
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia2 =
			_createMedia(
				AdaptiveMediaAttribute.contentLength(), 10,
				AdaptiveMediaAttribute.fileName(), "aaa");

		int result = _multiAttributeComparator.compare(
			adaptiveMedia1, adaptiveMedia2);

		Assert.assertEquals(-25, result);
	}

	@Test
	public void testSortDifferentMediaByMultipleAttributesInverse() {
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia1 =
			_createMedia(
				AdaptiveMediaAttribute.contentLength(), 10,
				AdaptiveMediaAttribute.fileName(), "zzz");
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia2 =
			_createMedia(
				AdaptiveMediaAttribute.contentLength(), 10,
				AdaptiveMediaAttribute.fileName(), "aaa");

		int result = _multiAttributeComparator.compare(
			adaptiveMedia2, adaptiveMedia1);

		Assert.assertEquals(25, result);
	}

	@Test
	public void testSortDifferentMediaByOneAttribute() {
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia1 =
			_createMedia(AdaptiveMediaAttribute.contentLength(), 10);
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia2 =
			_createMedia(AdaptiveMediaAttribute.contentLength(), 20);

		int result = _singleAttributeComparator.compare(
			adaptiveMedia1, adaptiveMedia2);

		Assert.assertEquals(-10, result);
	}

	@Test
	public void testSortDifferentMediaByOneAttributeInverse() {
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia1 =
			_createMedia(AdaptiveMediaAttribute.contentLength(), 10);
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia2 =
			_createMedia(AdaptiveMediaAttribute.contentLength(), 20);

		int result = _singleAttributeComparator.compare(
			adaptiveMedia2, adaptiveMedia1);

		Assert.assertEquals(10, result);
	}

	@Test
	public void testSortEqualMediaByMultipleAttributes() {
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia1 =
			_createMedia(
				AdaptiveMediaAttribute.contentLength(), 10,
				AdaptiveMediaAttribute.fileName(), "aaa");
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia2 =
			_createMedia(
				AdaptiveMediaAttribute.contentLength(), 10,
				AdaptiveMediaAttribute.fileName(), "aaa");

		int result = _singleAttributeComparator.compare(
			adaptiveMedia1, adaptiveMedia2);

		Assert.assertEquals(0, result);
	}

	@Test
	public void testSortEqualMediaByOneAttribute() {
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia1 =
			_createMedia(AdaptiveMediaAttribute.contentLength(), 10);
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia2 =
			_createMedia(AdaptiveMediaAttribute.contentLength(), 10);

		int result = _singleAttributeComparator.compare(
			adaptiveMedia1, adaptiveMedia2);

		Assert.assertEquals(0, result);
	}

	private <S, T> AdaptiveMedia<AdaptiveMediaImageProcessor> _createMedia(
		AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, S> attribute1,
		S value1,
		AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, T> attribute2,
		T value2) {

		Map<String, String> properties = new HashMap<>();

		properties.put(attribute1.getName(), String.valueOf(value1));
		properties.put(attribute2.getName(), String.valueOf(value2));

		AdaptiveMediaImageAttributeMapping attributeMapping =
			AdaptiveMediaImageAttributeMapping.fromProperties(properties);

		return new AdaptiveMediaImage(() -> null, attributeMapping, null);
	}

	private <T> AdaptiveMedia<AdaptiveMediaImageProcessor> _createMedia(
		AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, T> attribute,
		T value) {

		AdaptiveMediaImageAttributeMapping attributeMapping =
			AdaptiveMediaImageAttributeMapping.fromProperties(
				Collections.singletonMap(
					attribute.getName(), String.valueOf(value)));

		return new AdaptiveMediaImage(() -> null, attributeMapping, null);
	}

	private AdaptiveMediaAttributeComparator _multiAttributeComparator;
	private final AdaptiveMediaAttributeComparator _singleAttributeComparator =
		new AdaptiveMediaAttributeComparator(
			AdaptiveMediaAttribute.contentLength());

}