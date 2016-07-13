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

package com.liferay.adaptive.media.image.finder;

import com.liferay.adaptive.media.finder.AdaptiveMediaQuery;
import com.liferay.adaptive.media.finder.AdaptiveMediaQueryBuilder;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMediaAttribute;
import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * @author Adolfo Pérez
 */
public interface ImageAdaptiveMediaQueryBuilder
	extends AdaptiveMediaQueryBuilder
		<FileVersion, ImageAdaptiveMediaProcessor> {

	public AdaptiveMediaQuery<FileVersion, ImageAdaptiveMediaProcessor>
		allForModel(FileVersion fileVersion);

	public MediaAttributeQueryBuilder forModel(FileVersion fileVersion);

	public interface MediaAttributeQueryBuilder {

		public AdaptiveMediaQuery<FileVersion, ImageAdaptiveMediaProcessor>
			done();

		public <V> MediaAttributeQueryBuilder with(
			AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, V> attribute,
			V value);

	}

}