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

package com.liferay.adaptive.media.image.content.transformer.internal;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.content.transformer.ContentTransformer;
import com.liferay.adaptive.media.content.transformer.ContentTransformerContentType;
import com.liferay.adaptive.media.content.transformer.constants.ContentTransformerContentTypes;
import com.liferay.adaptive.media.image.finder.AdaptiveMediaImageFinder;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageAttribute;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true, property = "content.transformer.content.type=html",
	service = ContentTransformer.class
)
public class HtmlContentTransformerImpl implements ContentTransformer<String> {

	@Override
	public ContentTransformerContentType<String> getContentType() {
		return ContentTransformerContentTypes.HTML;
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileVersion)",
		unbind = "-"
	)
	public void setAdaptiveMediaImageFinder(AdaptiveMediaImageFinder finder) {
		_finder = finder;
	}

	@Reference(unbind = "-")
	public void setDlAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Override
	public String transform(String html)
		throws AdaptiveMediaException, PortalException {

		StringBuffer sb = new StringBuffer(html.length());
		Matcher matcher = _IMG_PATTERN.matcher(html);

		while (matcher.find()) {
			String picture = _getPictureElement(matcher);

			matcher.appendReplacement(sb, Matcher.quoteReplacement(picture));
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private AdaptiveMedia<AdaptiveMediaImageProcessor> _findHDAdaptiveMedia(
		AdaptiveMedia<AdaptiveMediaImageProcessor> originalAdaptiveMedia,
		List<AdaptiveMedia<AdaptiveMediaImageProcessor>> adaptiveMediaList) {

		Optional<Integer> originalWidthOptional =
			originalAdaptiveMedia.getAttributeValue(
				AdaptiveMediaImageAttribute.IMAGE_WIDTH);

		Optional<Integer> originalHeightOptional =
			originalAdaptiveMedia.getAttributeValue(
				AdaptiveMediaImageAttribute.IMAGE_HEIGHT);

		for (AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia :
				adaptiveMediaList) {

			Optional<Integer> widthOptional = adaptiveMedia.getAttributeValue(
				AdaptiveMediaImageAttribute.IMAGE_WIDTH);

			Optional<Integer> heightOptional = adaptiveMedia.getAttributeValue(
				AdaptiveMediaImageAttribute.IMAGE_HEIGHT);

			if (widthOptional.isPresent() && heightOptional.isPresent() &&
				(originalWidthOptional.get() == (widthOptional.get() / 2)) &&
				(originalHeightOptional.get() == (heightOptional.get() / 2))) {

				return adaptiveMedia;
			}
		}

		return null;
	}

	private Stream<AdaptiveMedia<AdaptiveMediaImageProcessor>>
			_getAdaptiveMedias(long fileEntryId)
		throws AdaptiveMediaException, PortalException {

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

		return _finder.getAdaptiveMedia(
			queryBuilder -> queryBuilder.allForFileEntry(fileEntry).orderBy(
				AdaptiveMediaImageAttribute.IMAGE_WIDTH, true).done());
	}

	private Optional<String> _getMediaQuery(
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia,
		AdaptiveMedia<AdaptiveMediaImageProcessor> previousAdaptiveMedia) {

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

	private String _getPictureElement(Matcher matcher)
		throws AdaptiveMediaException, PortalException {

		String img = matcher.group(0);
		Long fileEntryId = Long.valueOf(matcher.group(1));

		List<AdaptiveMedia<AdaptiveMediaImageProcessor>> adaptiveMedias =
			_getAdaptiveMedias(fileEntryId).collect(Collectors.toList());

		if (adaptiveMedias.isEmpty()) {
			return img;
		}

		StringBundler sb = new StringBundler(3 + adaptiveMedias.size());

		sb.append("<picture>");

		_getSourceElements(adaptiveMedias).forEach(sb::append);

		sb.append(img.replaceAll(_ATTR_REGEX, ""));
		sb.append("</picture>");

		return sb.toString();
	}

	private String _getSourceElement(
		AdaptiveMedia<AdaptiveMediaImageProcessor> previousAdaptiveMedia,
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia,
		AdaptiveMedia<AdaptiveMediaImageProcessor> hdAdaptiveMedia) {

		StringBundler sb = new StringBundler(11);

		sb.append("<source");

		_getMediaQuery(adaptiveMedia, previousAdaptiveMedia).ifPresent(
			mediaQuery -> {
				sb.append(" media=\"");
				sb.append(mediaQuery);
				sb.append("\"");
			});

		sb.append(" srcset=\"");
		sb.append(adaptiveMedia.getURI());

		if (hdAdaptiveMedia != null) {
			sb.append(", ");
			sb.append(hdAdaptiveMedia.getURI());
			sb.append(" 2x");
		}

		sb.append("\"");
		sb.append("/>");

		return sb.toString();
	}

	private List<String> _getSourceElements(
			List<AdaptiveMedia<AdaptiveMediaImageProcessor>> adaptiveMedias)
		throws AdaptiveMediaException, PortalException {

		List<String> sourceElements = new ArrayList<>();

		AdaptiveMedia previousAdaptiveMedia = null;

		for (AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia :
				adaptiveMedias) {

			AdaptiveMedia hdAdaptiveMedia = _findHDAdaptiveMedia(
				adaptiveMedia, adaptiveMedias);

			sourceElements.add(
				_getSourceElement(
					previousAdaptiveMedia, adaptiveMedia, hdAdaptiveMedia));

			previousAdaptiveMedia = adaptiveMedia;
		}

		return sourceElements;
	}

	private Optional<Integer> _getWidth(
		AdaptiveMedia<AdaptiveMediaImageProcessor> previousAdaptiveMedia) {

		return previousAdaptiveMedia.getAttributeValue(
			AdaptiveMediaImageAttribute.IMAGE_WIDTH);
	}

	private static final String _ATTR_REGEX =
		"(?i)\\s*data-fileEntryId=\"([0-9]*)\"";

	private static final Pattern _IMG_PATTERN = Pattern.compile(
		"<img .*?" + _ATTR_REGEX + ".*?/>",
		Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private DLAppLocalService _dlAppLocalService;
	private AdaptiveMediaImageFinder _finder;

}