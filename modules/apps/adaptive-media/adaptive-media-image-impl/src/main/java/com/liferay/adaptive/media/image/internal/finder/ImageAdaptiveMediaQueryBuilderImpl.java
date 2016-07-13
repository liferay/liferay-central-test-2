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

import com.liferay.adaptive.media.finder.AdaptiveMediaQuery;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaQueryBuilder;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMediaAttribute;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaQueryBuilderImpl
	implements ImageAdaptiveMediaQueryBuilder,
			   ImageAdaptiveMediaQueryBuilder.MediaAttributeQueryBuilder {

	public AdaptiveMediaQuery<FileVersion, ImageAdaptiveMediaProcessor>
		allForModel(FileVersion fileVersion) {

		if (fileVersion == null) {
			throw new IllegalArgumentException("File version cannot be null");
		}

		_fileVersion = fileVersion;

		return null;
	}

	@Override
	public AdaptiveMediaQuery<FileVersion, ImageAdaptiveMediaProcessor> done() {
		return null;
	}

	@Override
	public MediaAttributeQueryBuilder forModel(FileVersion fileVersion) {
		if (fileVersion == null) {
			throw new IllegalArgumentException("File version cannot be null");
		}

		_fileVersion = fileVersion;

		return this;
	}

	public Map<AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, Object>
		getAttributes() {

		return _attributes;
	}

	public FileVersion getFileVersion() {
		return _fileVersion;
	}

	@Override
	public <V> MediaAttributeQueryBuilder with(
		AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, V> attribute,
		V value) {

		if (value == null) {
			throw new IllegalArgumentException(
				"Adaptive media attribute value cannot be null");
		}

		_attributes.put(attribute, value);

		return this;
	}

	private Map<AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, Object>
		_attributes = new LinkedHashMap<>();
	private FileVersion _fileVersion;

}