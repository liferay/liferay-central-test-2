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

import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorRuntimeException;
import com.liferay.adaptive.media.processor.AdaptiveMediaProperty;

/**
 * @author Adolfo Pérez
 */
public final class ImageAdaptiveMediaProperty {

	public static final AdaptiveMediaProperty<
		ImageAdaptiveMediaProcessor, Integer>
			IMAGE_HEIGHT = new AdaptiveMediaProperty<>(
				"height", ImageAdaptiveMediaProperty::_parseInt,
				ImageAdaptiveMediaProperty::_intDistance);

	public static final AdaptiveMediaProperty<
		ImageAdaptiveMediaProcessor, Integer>
			IMAGE_WIDTH = new AdaptiveMediaProperty<>(
				"width", ImageAdaptiveMediaProperty::_parseInt,
				ImageAdaptiveMediaProperty::_intDistance);

	private static int _intDistance(int i1, int i2) {
		return Math.abs(i1 - i2);
	}

	private static int _parseInt(String value) {
		try {
			return Integer.parseInt(value);
		}
		catch (NumberFormatException nfe) {
			throw new AdaptiveMediaProcessorRuntimeException.
				MediaPropertyFormatException(nfe);
		}
	}

	private ImageAdaptiveMediaProperty() {
	}

}