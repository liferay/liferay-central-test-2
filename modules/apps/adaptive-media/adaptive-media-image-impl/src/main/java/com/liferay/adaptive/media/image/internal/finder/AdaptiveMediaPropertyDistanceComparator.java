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
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveMediaPropertyDistanceComparator
	implements Comparator<AdaptiveMedia<ImageAdaptiveMediaProcessor>> {

	public AdaptiveMediaPropertyDistanceComparator(
		Map<AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, ?>
			attributes) {

		_attributes = attributes;
	}

	@Override
	public int compare(
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1,
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia2) {

		for (Map.Entry
				<AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, ?>
					entry : _attributes.entrySet()) {

			AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, Object>
				attribute =
					(AdaptiveMediaAttribute
						<ImageAdaptiveMediaProcessor, Object>)entry.getKey();

			Object requestedValue = entry.getValue();

			Optional<?> value1Optional = adaptiveMedia1.getAttributeValue(
				attribute);

			Optional<Integer> value1DistanceOptional = value1Optional.map(
				value1 -> attribute.distance(value1, requestedValue));

			Optional<?> value2Optional = adaptiveMedia2.getAttributeValue(
				attribute);

			Optional<Integer> value2DistanceOptional = value2Optional.map(
				value2 -> attribute.distance(value2, requestedValue));

			Optional<Integer> resultOptional = value1DistanceOptional.flatMap(
				value1 -> value2DistanceOptional.map(
					value2 -> value1 - value2));

			int result = resultOptional.orElse(0);

			if (result != 0) {
				return result;
			}
		}

		return 0;
	}

	private final Map<AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, ?>
		_attributes;

}