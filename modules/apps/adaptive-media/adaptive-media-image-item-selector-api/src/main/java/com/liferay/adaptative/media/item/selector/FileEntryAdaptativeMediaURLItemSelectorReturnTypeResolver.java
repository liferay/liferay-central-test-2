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

package com.liferay.adaptative.media.item.selector;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.finder.ImageAdaptiveMediaFinder;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.processor.ImageAdaptiveMediaProcessor;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringPool;

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
	service = ItemSelectorReturnTypeResolver.class
)
public class FileEntryAdaptativeMediaURLItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<AdaptativeMediaURLItemSelectorReturnType, FileEntry> {

	@Override
	public Class<AdaptativeMediaURLItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return AdaptativeMediaURLItemSelectorReturnType.class;
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

		Stream<AdaptiveMedia<ImageAdaptiveMediaProcessor>> stream =
			_finder.getAdaptiveMedia(
				queryBuilder -> queryBuilder.allForFileEntry(fileEntry));

		List<AdaptiveMedia<ImageAdaptiveMediaProcessor>> adaptiveMediaList =
			stream.collect(Collectors.toList());

		if (adaptiveMediaList.size() > 0) {
			String previousWidth = "";

			for (AdaptiveMedia adaptiveMedia : adaptiveMediaList) {
				Optional width = adaptiveMedia.getAttributeValue(
					ImageAdaptiveMediaAttribute.IMAGE_WIDTH);

				JSONObject sourceJSONObject =
					JSONFactoryUtil.createJSONObject();

				sourceJSONObject.put("src", adaptiveMedia.getURI());

				JSONArray attributesJSONArray =
					JSONFactoryUtil.createJSONArray();

				if (width.isPresent()) {
					String widthValue = width.get().toString();

					JSONObject attributeJSONObject =
						JSONFactoryUtil.createJSONObject();

					attributeJSONObject.put("key", "max-width");
					attributeJSONObject.put("value", widthValue + "px");

					attributesJSONArray.put(attributeJSONObject);

					if (!previousWidth.equals("")) {
						attributeJSONObject =
							JSONFactoryUtil.createJSONObject();

						attributeJSONObject.put("key", "min-width");
						attributeJSONObject.put("value", previousWidth + "px");

						attributesJSONArray.put(attributeJSONObject);
					}

					previousWidth = widthValue;
				}

				sourceJSONObject.put("attributes", attributesJSONArray);

				sourcesArray.put(sourceJSONObject);
			}
		}

		fileEntryJSONObject.put("sources", sourcesArray);

		return fileEntryJSONObject.toString();
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileVersion)"
	)
	private ImageAdaptiveMediaFinder _finder;

}