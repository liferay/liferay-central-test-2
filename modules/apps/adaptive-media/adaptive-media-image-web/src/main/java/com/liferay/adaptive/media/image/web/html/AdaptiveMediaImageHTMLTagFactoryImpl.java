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
import com.liferay.adaptive.media.image.html.AdaptiveMediaImageHTMLTagFactory;
import com.liferay.adaptive.media.image.mediaquery.Condition;
import com.liferay.adaptive.media.image.mediaquery.MediaQuery;
import com.liferay.adaptive.media.image.mediaquery.MediaQueryProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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

		List<String> sourceElements = _getSourceElements(fileEntry);

		if (sourceElements.isEmpty()) {
			return originalImgTag;
		}

		StringBundler sb = new StringBundler(3 + sourceElements.size());

		sb.append("<picture>");
		sourceElements.forEach(sb::append);
		sb.append(_ATTR_PATTERN.matcher(originalImgTag).replaceAll(""));
		sb.append("</picture>");

		return sb.toString();
	}

	@Reference(unbind = "-")
	protected void setMediaQueryProvider(
		MediaQueryProvider mediaQueryProvider) {

		_mediaQueryProvider = mediaQueryProvider;
	}

	private Optional<String> _getMediaQueryString(MediaQuery mediaQuery) {
		List<Condition> conditions = mediaQuery.getConditions();

		if (conditions.isEmpty()) {
			return Optional.empty();
		}

		String[] conditionStrings = new String[conditions.size()];

		for (int i = 0; i < conditionStrings.length; i++) {
			Condition condition = conditions.get(i);

			StringBundler sb = new StringBundler(5);

			sb.append("(");
			sb.append(condition.getAttribute());
			sb.append(":");
			sb.append(condition.getValue());
			sb.append(")");

			conditionStrings[i] = sb.toString();
		}

		return Optional.of(String.join(" and ", conditionStrings));
	}

	private String _getSourceElement(MediaQuery mediaQuery) {
		StringBundler sb = new StringBundler(8);

		sb.append("<source");

		_getMediaQueryString(mediaQuery).ifPresent(
			mediaQueryString -> {
				sb.append(" media=\"");
				sb.append(mediaQueryString);
				sb.append("\"");
			});

		sb.append(" srcset=\"");
		sb.append(mediaQuery.getSrc());
		sb.append("\"");
		sb.append("/>");

		return sb.toString();
	}

	private List<String> _getSourceElements(FileEntry fileEntry)
		throws AdaptiveMediaException, PortalException {

		Stream<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			fileEntry).stream();

		return mediaQueries.map(
			this::_getSourceElement).collect(Collectors.toList());
	}

	private static final Pattern _ATTR_PATTERN = Pattern.compile(
		"(?i)\\s*data-fileEntryId=\"([0-9]*)\"");

	private MediaQueryProvider _mediaQueryProvider;

}