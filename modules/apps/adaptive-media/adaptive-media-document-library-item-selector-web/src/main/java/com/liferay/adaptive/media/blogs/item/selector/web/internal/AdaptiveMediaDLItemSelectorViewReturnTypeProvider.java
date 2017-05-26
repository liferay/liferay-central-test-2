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

package com.liferay.adaptive.media.blogs.item.selector.web.internal;

import com.liferay.adaptive.media.image.item.selector.ImageAdaptiveMediaURLItemSelectorReturnType;
import com.liferay.document.library.item.selector.web.internal.constants.DLItemSelectorViewConstants;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewReturnTypeProvider;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"item.selector.view.key=" + DLItemSelectorViewConstants.DL_AUDIO_ITEM_SELECTOR_VIEW_KEY,
		"item.selector.view.key=" + DLItemSelectorViewConstants.DL_FILE_ITEM_SELECTOR_VIEW_KEY,
		"item.selector.view.key=" + DLItemSelectorViewConstants.DL_IMAGE_ITEM_SELECTOR_VIEW_KEY,
		"item.selector.view.key=" + DLItemSelectorViewConstants.DL_VIDEO_ITEM_SELECTOR_VIEW_KEY
	},
	service = ItemSelectorViewReturnTypeProvider.class
)
public class AdaptiveMediaDLItemSelectorViewReturnTypeProvider
	implements ItemSelectorViewReturnTypeProvider {

	public List<ItemSelectorReturnType>
		populateSupportedItemSelectorReturnTypes(
			List<ItemSelectorReturnType> supportedItemSelectorReturnTypes) {

		supportedItemSelectorReturnTypes.add(
			new ImageAdaptiveMediaURLItemSelectorReturnType());

		return supportedItemSelectorReturnTypes;
	}

}