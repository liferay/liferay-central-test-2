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

package com.liferay.adaptive.media.image.item.selector.internal.resolver;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.finder.AdaptiveMediaImageFinder;
import com.liferay.adaptive.media.image.item.selector.AdaptiveMediaImageURLItemSelectorReturnType;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageAttribute;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true, property = {"service.ranking:Integer=100"},
	service = {
		ItemSelectorReturnTypeResolver.class,
		FileEntryAdaptiveMediaImageURLItemSelectorReturnTypeResolver.class
	}
)
public class FileEntryAdaptiveMediaImageURLItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<AdaptiveMediaImageURLItemSelectorReturnType, FileEntry> {

	@Override
	public Class<AdaptiveMediaImageURLItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return AdaptiveMediaImageURLItemSelectorReturnType.class;
	}

	@Override
	public Class<FileEntry> getModelClass() {
		return FileEntry.class;
	}

	@Override
	public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		JSONObject fileEntryJSONObject = JSONFactoryUtil.createJSONObject();

		String previewURL = DLUtil.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), themeDisplay,
			StringPool.BLANK, false, false);

		fileEntryJSONObject.put("defaultSource", previewURL);

		JSONArray sourcesArray = JSONFactoryUtil.createJSONArray();

		Stream<AdaptiveMedia<AdaptiveMediaImageProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder ->
					queryBuilder.allForFileEntry(fileEntry).orderBy(
						AdaptiveMediaImageAttribute.IMAGE_WIDTH, true).done());

		List<AdaptiveMedia<AdaptiveMediaImageProcessor>> adaptiveMediaList =
			stream.collect(Collectors.toList());

		AdaptiveMedia previousAdaptiveMedia = null;

		for (AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia :
				adaptiveMediaList) {

			AdaptiveMedia hdAdaptiveMedia = _findHDAdaptiveMedia(
				adaptiveMedia, adaptiveMediaList);

			JSONObject sourceJSONObject = _getSourceJSONObject(
				adaptiveMedia, hdAdaptiveMedia, previousAdaptiveMedia);

			sourcesArray.put(sourceJSONObject);

			previousAdaptiveMedia = adaptiveMedia;
		}

		fileEntryJSONObject.put("sources", sourcesArray);

		return fileEntryJSONObject.toString();
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

	private JSONObject _getSourceJSONObject(
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia,
		AdaptiveMedia<AdaptiveMediaImageProcessor> hdAdaptiveMedia,
		AdaptiveMedia<AdaptiveMediaImageProcessor> previousAdaptiveMedia) {

		Optional<Integer> widthOptional = adaptiveMedia.getAttributeValue(
			AdaptiveMediaImageAttribute.IMAGE_WIDTH);

		JSONObject sourceJSONObject = JSONFactoryUtil.createJSONObject();

		String src = adaptiveMedia.getURI().toString();

		if (Validator.isNotNull(hdAdaptiveMedia)) {
			src =
				adaptiveMedia.getURI() + ", " + hdAdaptiveMedia.getURI() +
					" 2x";
		}

		sourceJSONObject.put("src", src);

		JSONObject attributesJSONObject = JSONFactoryUtil.createJSONObject();

		widthOptional.ifPresent(
			maxWidth -> {
				attributesJSONObject.put("max-width", maxWidth + "px");

				if (previousAdaptiveMedia != null) {
					Optional<Integer> previousWidthOptional =
						previousAdaptiveMedia.getAttributeValue(
							AdaptiveMediaImageAttribute.IMAGE_WIDTH);

					previousWidthOptional.ifPresent(
						previousMaxWidth ->
							attributesJSONObject.put(
								"min-width", previousMaxWidth + "px"));
				}
			});

		return sourceJSONObject.put("attributes", attributesJSONObject);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileVersion)"
	)
	private AdaptiveMediaImageFinder _finder;

}