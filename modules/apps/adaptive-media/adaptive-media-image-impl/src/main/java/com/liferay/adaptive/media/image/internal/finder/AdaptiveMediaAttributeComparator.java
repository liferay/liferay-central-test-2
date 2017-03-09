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
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * @author Sergio González
 */
public class AdaptiveMediaAttributeComparator
	implements Comparator<AdaptiveMedia<AdaptiveMediaImageProcessor>> {

	public AdaptiveMediaAttributeComparator(
		AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, ?> attribute) {

		this(Collections.singletonMap(attribute, true));
	}

	public AdaptiveMediaAttributeComparator(
		AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, ?> attribute,
		boolean ascending) {

		this(Collections.singletonMap(attribute, ascending));
	}

	public AdaptiveMediaAttributeComparator(
		Map<AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, ?>, Boolean>
			sortCriteria) {

		_sortCriteria = (Map)sortCriteria;
	}

	@Override
	public int compare(
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia1,
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia2) {

		for (Map.Entry
				<AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, Object>,
					Boolean> sortCriterion : _sortCriteria.entrySet()) {

			AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, Object>
				attribute = sortCriterion.getKey();

			Optional<?> value1Optional = adaptiveMedia1.getAttributeValue(
				attribute);
			Optional<?> value2Optional = adaptiveMedia2.getAttributeValue(
				attribute);

			Optional<Integer> valueOptional = value1Optional.flatMap(
				value1 ->
					value2Optional.map(
						value2 -> attribute.compare(value1, value2)));

			int result = valueOptional.map(
				value -> sortCriterion.getValue() ? value : -value).orElse(0);

			if (result != 0) {
				return result;
			}
		}

		return 0;
	}

	private final Map
		<AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, Object>, Boolean>
			_sortCriteria;

}