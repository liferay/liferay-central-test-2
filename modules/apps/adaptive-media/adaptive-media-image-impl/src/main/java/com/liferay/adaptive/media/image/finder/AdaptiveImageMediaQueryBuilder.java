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

import com.liferay.adaptive.media.finder.MediaQuery;
import com.liferay.adaptive.media.finder.MediaQueryBuilder;
import com.liferay.adaptive.media.image.processor.AdaptiveImageMediaProcessor;
import com.liferay.adaptive.media.processor.MediaProperty;
import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * @author Adolfo Pérez
 */
public interface AdaptiveImageMediaQueryBuilder
	extends MediaQueryBuilder<FileVersion, AdaptiveImageMediaProcessor> {

	public MediaQuery<FileVersion, AdaptiveImageMediaProcessor> allForModel(
		FileVersion fileVersion);

	public MediaPropertyQueryBuilder forModel(FileVersion fileVersion);

	public interface MediaPropertyQueryBuilder {

		public MediaQuery<FileVersion, AdaptiveImageMediaProcessor> done();

		public <V> MediaPropertyQueryBuilder with(
			MediaProperty<AdaptiveImageMediaProcessor, V> property, V value);

	}

}