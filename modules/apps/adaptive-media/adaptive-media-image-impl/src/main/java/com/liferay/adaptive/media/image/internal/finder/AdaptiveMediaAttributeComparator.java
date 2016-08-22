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
import java.util.Optional;

/**
 * @author Sergio González
 */
public class AdaptiveMediaAttributeComparator
	implements Comparator<AdaptiveMedia<ImageAdaptiveMediaProcessor>> {

	public AdaptiveMediaAttributeComparator(
		AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?> attribute) {

		this(attribute, true);
	}

	public AdaptiveMediaAttributeComparator(
		AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?> attribute,
		boolean ascending) {

		_attribute =
			(AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, Object>)
				attribute;
		_ascending = ascending;
	}

	@Override
	public int compare(
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia1,
		AdaptiveMedia<ImageAdaptiveMediaProcessor> adaptiveMedia2) {

		if (_attribute == null) {
			return 0;
		}

		Optional<?> value1Optional = adaptiveMedia1.getAttributeValue(
			_attribute);
		Optional<?> value2Optional = adaptiveMedia2.getAttributeValue(
			_attribute);

		if (!value1Optional.isPresent()) {
			return 1;
		}
		else if (!value2Optional.isPresent()) {
			return -1;
		}

		int value = _attribute.compare(
			value1Optional.get(), value2Optional.get());

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	private final boolean _ascending;
	private final AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, Object>
		_attribute;

}