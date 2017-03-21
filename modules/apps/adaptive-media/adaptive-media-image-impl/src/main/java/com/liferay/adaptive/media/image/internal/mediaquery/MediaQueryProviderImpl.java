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

package com.liferay.adaptive.media.image.internal.mediaquery;

import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.mediaquery.Condition;
import com.liferay.adaptive.media.image.mediaquery.MediaQuery;
import com.liferay.adaptive.media.image.mediaquery.MediaQueryProvider;
import com.liferay.adaptive.media.image.url.AdaptiveMediaImageURLFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringBundler;

import java.net.URI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component
public class MediaQueryProviderImpl implements MediaQueryProvider {

	@Override
	public List<MediaQuery> getMediaQueries(FileEntry fileEntry)
		throws AdaptiveMediaException, PortalException {

		Collection<AdaptiveMediaImageConfigurationEntry> adaptiveMedias =
			_getAdaptiveMedias(fileEntry);

		List<MediaQuery> mediaQueries = new ArrayList<>();

		AdaptiveMediaImageConfigurationEntry previousAdaptiveMedia = null;

		for (AdaptiveMediaImageConfigurationEntry adaptiveMedia :
				adaptiveMedias) {

			Optional<AdaptiveMediaImageConfigurationEntry>
				hdAdaptiveMediaOptional = _getHDAdaptiveMedia(
					adaptiveMedia, adaptiveMedias);

			mediaQueries.add(
				_getMediaQuery(
					fileEntry, adaptiveMedia, previousAdaptiveMedia,
					hdAdaptiveMediaOptional));

			previousAdaptiveMedia = adaptiveMedia;
		}

		return mediaQueries;
	}

	@Reference(unbind = "-")
	protected void setAdaptiveMediaImageConfigurationHelper(
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper) {

		_adaptiveMediaImageConfigurationHelper =
			adaptiveMediaImageConfigurationHelper;
	}

	@Reference(unbind = "-")
	protected void setAdaptiveMediaImageURLFactory(
		AdaptiveMediaImageURLFactory adaptiveMediaImageURLFactory) {

		_adaptiveMediaImageURLFactory = adaptiveMediaImageURLFactory;
	}

	private int _compareEntries(
		AdaptiveMediaImageConfigurationEntry entry1,
		AdaptiveMediaImageConfigurationEntry entry2) {

		Optional<Integer> widthOptional1 = _getWidth(entry1);
		Optional<Integer> widthOptional2 = _getWidth(entry2);

		return widthOptional1.map(
			width1 -> widthOptional2.map(
				width2 -> width1 - width2).orElse(1)).orElse(-1);
	}

	private Collection<AdaptiveMediaImageConfigurationEntry>
			_getAdaptiveMedias(FileEntry fileEntry)
		throws AdaptiveMediaException, PortalException {

		Stream<AdaptiveMediaImageConfigurationEntry> entries =
			_adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntries(
					fileEntry.getCompanyId()).stream();

		return entries.sorted(this::_compareEntries).collect(
			Collectors.toList());
	}

	private Optional<Integer> _getAttribute(
		AdaptiveMediaImageConfigurationEntry adaptiveMedia, String name) {

		try {
			Integer height = Integer.valueOf(
				adaptiveMedia.getProperties().get(name));

			return Optional.of(height);
		}
		catch (NumberFormatException nfe) {
			return Optional.empty();
		}
	}

	private List<Condition> _getConditions(
		AdaptiveMediaImageConfigurationEntry adaptiveMedia,
		AdaptiveMediaImageConfigurationEntry previousAdaptiveMedia) {

		List<Condition> conditions = new ArrayList<>();

		_getWidth(adaptiveMedia).map(width -> {
			conditions.add(new Condition("max-width", width + "px"));

			if (previousAdaptiveMedia != null) {
				Optional<Integer> widthOptional = _getWidth(
					previousAdaptiveMedia);

				widthOptional.map(
					previousWidth ->
						conditions.add(
							new Condition("min-width", previousWidth + "px")));
			}

			return conditions;
		});

		return conditions;
	}

	private URI _getFileEntryURL(
		FileEntry fileEntry,
		AdaptiveMediaImageConfigurationEntry adaptiveMedia) {

		try {
			return _adaptiveMediaImageURLFactory.createFileEntryURL(
				fileEntry.getFileVersion(), adaptiveMedia);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	private Optional<AdaptiveMediaImageConfigurationEntry> _getHDAdaptiveMedia(
		AdaptiveMediaImageConfigurationEntry originalAdaptiveMedia,
		Collection<AdaptiveMediaImageConfigurationEntry> adaptiveMedias) {

		Optional<Integer> originalWidthOptional = _getWidth(
			originalAdaptiveMedia);

		Optional<Integer> originalHeightOptional = _getHeight(
			originalAdaptiveMedia);

		if (!originalWidthOptional.isPresent() ||
			!originalHeightOptional.isPresent()) {

			return Optional.empty();
		}

		for (AdaptiveMediaImageConfigurationEntry adaptiveMedia :
				adaptiveMedias) {

			Optional<Integer> widthOptional = _getWidth(adaptiveMedia);
			Optional<Integer> heightOptional = _getHeight(adaptiveMedia);

			if (!widthOptional.isPresent() || !heightOptional.isPresent()) {
				continue;
			}

			int originalWidth = originalWidthOptional.get() * 2;
			int originalHeight = originalHeightOptional.get() * 2;

			IntStream widthIntStream = IntStream.range(
				originalWidth - 1, originalWidth + 2);

			boolean widthMatch = widthIntStream.anyMatch(
				value -> value == widthOptional.get());

			IntStream heightIntStream = IntStream.range(
				originalHeight - 1, originalHeight + 2);

			boolean heightMatch = heightIntStream.anyMatch(
				value -> value == heightOptional.get());

			if (widthMatch && heightMatch) {
				return Optional.of(adaptiveMedia);
			}
		}

		return Optional.empty();
	}

	private Optional<Integer> _getHeight(
		AdaptiveMediaImageConfigurationEntry adaptiveMedia) {

		return _getAttribute(adaptiveMedia, "max-height");
	}

	private MediaQuery _getMediaQuery(
			FileEntry fileEntry,
			AdaptiveMediaImageConfigurationEntry adaptiveMedia,
			AdaptiveMediaImageConfigurationEntry previousAdaptiveMedia,
			Optional<AdaptiveMediaImageConfigurationEntry>
				hdAdaptiveMediaOptional)
		throws PortalException {

		StringBundler src = new StringBundler(3);

		List<Condition> conditions = _getConditions(
			adaptiveMedia, previousAdaptiveMedia);

		src.append(_getFileEntryURL(fileEntry, adaptiveMedia));

		hdAdaptiveMediaOptional.ifPresent(
			hdAdaptiveMedia -> {
				src.append(", ");
				src.append(_getFileEntryURL(fileEntry, hdAdaptiveMedia));
				src.append(" 2x");
			});

		return new MediaQuery(conditions, src.toString());
	}

	private Optional<Integer> _getWidth(
		AdaptiveMediaImageConfigurationEntry adaptiveMedia) {

		return _getAttribute(adaptiveMedia, "max-width");
	}

	private AdaptiveMediaImageConfigurationHelper
		_adaptiveMediaImageConfigurationHelper;
	private AdaptiveMediaImageURLFactory _adaptiveMediaImageURLFactory;

}