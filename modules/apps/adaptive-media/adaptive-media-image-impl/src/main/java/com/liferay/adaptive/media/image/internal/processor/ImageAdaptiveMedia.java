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

import com.liferay.adaptive.media.image.internal.configuration.ImageAdaptiveMediaPropertyMapping;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMedia;
import com.liferay.adaptive.media.processor.AdaptiveMediaProperty;

import java.io.InputStream;

import java.net.URI;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Adolfo Pérez
 */
public final class ImageAdaptiveMedia
	implements AdaptiveMedia<ImageAdaptiveMediaProcessor> {

	public ImageAdaptiveMedia(
		Supplier<InputStream> supplier,
		ImageAdaptiveMediaPropertyMapping adaptiveImagePropertyMapping,
		URI relativeURI) {

		_supplier = supplier;
		_adaptiveImagePropertyMapping = adaptiveImagePropertyMapping;
		_relativeURI = relativeURI;
	}

	@Override
	public InputStream getInputStream() {
		return _supplier.get();
	}

	@Override
	public <V> Optional<V> getPropertyValue(
		AdaptiveMediaProperty<ImageAdaptiveMediaProcessor, V> mediaProperty) {

		return _adaptiveImagePropertyMapping.getPropertyValue(mediaProperty);
	}

	@Override
	public URI getRelativeURI() {
		return _relativeURI;
	}

	private final ImageAdaptiveMediaPropertyMapping
		_adaptiveImagePropertyMapping;
	private final URI _relativeURI;
	private final Supplier<InputStream> _supplier;

}