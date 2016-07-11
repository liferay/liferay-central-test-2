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

import com.liferay.adaptive.media.image.processor.AdaptiveImageMediaProcessor;
import com.liferay.adaptive.media.processor.MediaProcessorRuntimeException;
import com.liferay.adaptive.media.processor.MediaProperty;

/**
 * @author Adolfo Pérez
 */
public final class AdaptiveImageMediaProperty {

	public static final MediaProperty<AdaptiveImageMediaProcessor, Integer>
		IMAGE_HEIGHT = new MediaProperty<>(
		"height", AdaptiveImageMediaProperty::_parseInt);

	public static final MediaProperty<AdaptiveImageMediaProcessor, Integer>
		IMAGE_WIDTH = new MediaProperty<>(
		"width", AdaptiveImageMediaProperty::_parseInt);

	private static int _parseInt(String value) {
		try {
			return Integer.parseInt(value);
		}
		catch (NumberFormatException nfe) {
			throw new MediaProcessorRuntimeException.
				MediaPropertyFormatException(nfe);
		}
	}

	private AdaptiveImageMediaProperty() {
	}

}