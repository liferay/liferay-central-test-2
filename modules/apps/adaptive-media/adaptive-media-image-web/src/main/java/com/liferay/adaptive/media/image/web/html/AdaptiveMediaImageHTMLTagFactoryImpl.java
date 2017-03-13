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

package com.liferay.adaptive.media.image.web.html;

import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.html.AdaptiveMediaImageHTMLTagFactory;
import com.liferay.adaptive.media.image.url.AdaptiveMediaImageURLFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringBundler;

import java.net.URI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = AdaptiveMediaImageHTMLTagFactory.class)
public class AdaptiveMediaImageHTMLTagFactoryImpl
	implements AdaptiveMediaImageHTMLTagFactory {

	@Override
	public String create(String originalImgTag, FileEntry fileEntry)
		throws AdaptiveMediaException, PortalException {

		Collection<AdaptiveMediaImageConfigurationEntry> adaptiveMedias =
			_getAdaptiveMedias(fileEntry);

		if (adaptiveMedias.isEmpty()) {
			return originalImgTag;
		}

		StringBundler sb = new StringBundler(3 + adaptiveMedias.size());

		sb.append("<picture>");

		_getSourceElements(fileEntry, adaptiveMedias).forEach(sb::append);

		sb.append(_ATTR_PATTERN.matcher(originalImgTag).replaceAll(""));
		sb.append("</picture>");

		return sb.toString();
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

	private Optional<String> _getMediaQuery(
		AdaptiveMediaImageConfigurationEntry adaptiveMedia,
		AdaptiveMediaImageConfigurationEntry previousAdaptiveMedia) {

		return _getWidth(adaptiveMedia).map(width -> {
			String constraints = "(max-width:" + width + "px)";

			if (previousAdaptiveMedia != null) {
				Optional<Integer> widthOptional = _getWidth(
					previousAdaptiveMedia);

				constraints += widthOptional.map(
					previousWidth ->
						" and (min-width:" + previousWidth + "px)").orElse("");
			}

			return constraints;
		});
	}

	private String _getSourceElement(
			FileEntry fileEntry,
			AdaptiveMediaImageConfigurationEntry adaptiveMedia,
			AdaptiveMediaImageConfigurationEntry previousAdaptiveMedia,
			Optional<AdaptiveMediaImageConfigurationEntry>
				hdAdaptiveMediaOptional)
		throws PortalException {

		StringBundler sb = new StringBundler(11);

		sb.append("<source");

		_getMediaQuery(adaptiveMedia, previousAdaptiveMedia).ifPresent(
			mediaQuery -> {
				sb.append(" media=\"");
				sb.append(mediaQuery);
				sb.append("\"");
			});

		sb.append(" srcset=\"");
		sb.append(_getFileEntryURL(fileEntry, adaptiveMedia));

		hdAdaptiveMediaOptional.ifPresent(
			hdAdaptiveMedia -> {
				sb.append(", ");
				sb.append(_getFileEntryURL(fileEntry, hdAdaptiveMedia));
				sb.append(" 2x");
			});

		sb.append("\"");
		sb.append("/>");

		return sb.toString();
	}

	private List<String> _getSourceElements(
			FileEntry fileEntry,
			Collection<AdaptiveMediaImageConfigurationEntry> adaptiveMedias)
		throws AdaptiveMediaException, PortalException {

		List<String> sourceElements = new ArrayList<>();

		AdaptiveMediaImageConfigurationEntry previousAdaptiveMedia = null;

		for (AdaptiveMediaImageConfigurationEntry adaptiveMedia :
				adaptiveMedias) {

			Optional<AdaptiveMediaImageConfigurationEntry>
				hdAdaptiveMediaOptional = _getHDAdaptiveMedia(
					adaptiveMedia, adaptiveMedias);

			sourceElements.add(
				_getSourceElement(
					fileEntry, adaptiveMedia, previousAdaptiveMedia,
					hdAdaptiveMediaOptional));

			previousAdaptiveMedia = adaptiveMedia;
		}

		return sourceElements;
	}

	private Optional<Integer> _getWidth(
		AdaptiveMediaImageConfigurationEntry adaptiveMedia) {

		return _getAttribute(adaptiveMedia, "max-width");
	}

	private static final Pattern _ATTR_PATTERN = Pattern.compile(
		"(?i)\\s*data-fileEntryId=\"([0-9]*)\"");

	private AdaptiveMediaImageConfigurationHelper
		_adaptiveMediaImageConfigurationHelper;
	private AdaptiveMediaImageURLFactory _adaptiveMediaImageURLFactory;

}