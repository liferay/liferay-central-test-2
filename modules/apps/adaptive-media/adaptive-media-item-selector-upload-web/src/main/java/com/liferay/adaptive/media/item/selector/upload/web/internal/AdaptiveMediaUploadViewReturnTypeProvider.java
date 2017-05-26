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

package com.liferay.adaptive.media.item.selector.upload.web.internal;

import com.liferay.adaptive.media.image.item.selector.AdaptiveMediaImageFileEntryItemSelectorReturnType;
import com.liferay.adaptive.media.image.item.selector.AdaptiveMediaImageURLItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewReturnTypeProvider;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {"item.selector.view.key=upload"},
	service = ItemSelectorViewReturnTypeProvider.class
)
public class AdaptiveMediaUploadViewReturnTypeProvider
	implements ItemSelectorViewReturnTypeProvider {

	public List<ItemSelectorReturnType>
		populateSupportedItemSelectorReturnTypes(
			List<ItemSelectorReturnType> supportedItemSelectorReturnTypes) {

		supportedItemSelectorReturnTypes.add(
			new AdaptiveMediaImageFileEntryItemSelectorReturnType());

		supportedItemSelectorReturnTypes.add(
			new AdaptiveMediaImageURLItemSelectorReturnType());

		return supportedItemSelectorReturnTypes;
	}

}