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
import com.liferay.adaptive.media.finder.AdaptiveMediaQuery;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaQueryBuilder;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.Validator;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Adolfo Pérez
 */
public class ImageAdaptiveMediaQueryBuilderImpl
	implements ImageAdaptiveMediaQueryBuilder,
			   ImageAdaptiveMediaQueryBuilder.ConfigurationStep,
			   ImageAdaptiveMediaQueryBuilder.FuzzySortStep,
			   ImageAdaptiveMediaQueryBuilder.InitialStep,
			   ImageAdaptiveMediaQueryBuilder.StrictSortStep {

	public static final
		AdaptiveMediaQuery<FileVersion, ImageAdaptiveMediaProcessor> QUERY =
			new AdaptiveMediaQuery<FileVersion, ImageAdaptiveMediaProcessor>() {
			};

	@Override
	public InitialStep allForFileEntry(FileEntry fileEntry) {
		if (fileEntry == null) {
			throw new IllegalArgumentException("File entry cannot be null");
		}

		_fileEntry = fileEntry;

		return this;
	}

	@Override
	public InitialStep allForVersion(FileVersion fileVersion) {
		if (fileVersion == null) {
			throw new IllegalArgumentException("File version cannot be null");
		}

		_fileVersion = fileVersion;

		return this;
	}

	@Override
	public AdaptiveMediaQuery<FileVersion, ImageAdaptiveMediaProcessor> done() {
		return QUERY;
	}

	@Override
	public FinalStep forConfiguration(String configurationUuid) {
		if (Validator.isNull(configurationUuid)) {
			throw new IllegalArgumentException(
				"Configuration uuid cannot be null");
		}

		_configurationUuid = configurationUuid;

		return this;
	}

	@Override
	public InitialStep forFileEntry(FileEntry fileEntry) {
		if (fileEntry == null) {
			throw new IllegalArgumentException("File entry cannot be null");
		}

		_fileEntry = fileEntry;

		return this;
	}

	@Override
	public InitialStep forVersion(FileVersion fileVersion) {
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

	public Comparator<AdaptiveMedia<ImageAdaptiveMediaProcessor>>
		getComparator() {

		if (!_sortCriteria.isEmpty()) {
			return new AdaptiveMediaAttributeComparator(_sortCriteria);
		}

		if (!_attributes.isEmpty()) {
			return new AdaptiveMediaPropertyDistanceComparator(_attributes);
		}

		return (v1, v2) -> 0;
	}

	public String getConfigurationUuid() {
		return _configurationUuid;
	}

	public FileVersion getFileVersion() throws PortalException {
		if (_fileVersion != null) {
			return _fileVersion;
		}

		_fileVersion = _fileEntry.getLatestFileVersion();

		return _fileVersion;
	}

	public boolean hasConfiguration() {
		if (Validator.isNotNull(_configurationUuid)) {
			return true;
		}

		return false;
	}

	public boolean hasFileVersion() {
		if (_fileEntry == null) {
			return true;
		}

		return false;
	}

	@Override
	public <V> StrictSortStep orderBy(
		AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, V> attribute,
		boolean asc) {

		if (attribute == null) {
			throw new IllegalArgumentException(
				"Adaptive media attribute cannot be null");
		}

		_sortCriteria.put(attribute, asc);

		return this;
	}

	@Override
	public <V> FuzzySortStep with(
		AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, V> attribute,
		Optional<V> valueOptional) {

		if (valueOptional == null) {
			throw new IllegalArgumentException(
				"Adaptive media attribute value optional cannot be null");
		}

		valueOptional.ifPresent(value -> _attributes.put(attribute, value));

		return this;
	}

	@Override
	public <V> FuzzySortStep with(
		AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, V> attribute,
		V value) {

		if (value == null) {
			throw new IllegalArgumentException(
				"Adaptive media attribute value cannot be null");
		}

		_attributes.put(attribute, value);

		return this;
	}

	private final Map
		<AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, Object>
			_attributes = new LinkedHashMap<>();
	private String _configurationUuid;
	private FileEntry _fileEntry;
	private FileVersion _fileVersion;
	private final Map
		<AdaptiveMediaAttribute<ImageAdaptiveMediaProcessor, ?>, Boolean>
			_sortCriteria = new LinkedHashMap<>();

}