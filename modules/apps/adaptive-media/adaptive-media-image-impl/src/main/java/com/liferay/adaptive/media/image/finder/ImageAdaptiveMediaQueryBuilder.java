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

import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.finder.AdaptiveMediaQuery;
import com.liferay.adaptive.media.finder.AdaptiveMediaQueryBuilder;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.Optional;

/**
 * @author Adolfo Pérez
 */
public interface ImageAdaptiveMediaQueryBuilder
	extends AdaptiveMediaQueryBuilder
		<FileVersion, ImageAdaptiveMediaProcessor> {

	public InitialStep allForFileEntry(FileEntry fileEntry);

	public InitialStep allForVersion(FileVersion fileVersion);

	public InitialStep forFileEntry(FileEntry fileEntry);

	public InitialStep forVersion(FileVersion fileVersion);

	public interface FinalStep {

		public AdaptiveMediaQuery<FileVersion, ImageAdaptiveMediaProcessor>
			done();

	}

	public interface FuzzySortStep extends FinalStep {

		public <V> FuzzySortStep with(
			AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, V> attribute,
			Optional<V> valueOptional);

		public <V> FuzzySortStep with(
			AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, V> attribute,
			V value);

	}

	public interface InitialStep extends FuzzySortStep, StrictSortStep {
	}

	public interface StrictSortStep extends FinalStep {

		public <V> StrictSortStep orderBy(
			AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, V> attribute,
			boolean asc);

	}

}