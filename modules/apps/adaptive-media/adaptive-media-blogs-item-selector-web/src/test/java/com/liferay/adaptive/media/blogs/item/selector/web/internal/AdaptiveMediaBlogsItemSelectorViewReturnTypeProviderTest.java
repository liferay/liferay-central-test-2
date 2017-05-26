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
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewReturnTypeProvider;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Sergio González
 */
public class AdaptiveMediaBlogsItemSelectorViewReturnTypeProviderTest
	extends PowerMockito {

	@Test
	public void
			testAddImageAdaptiveMediaURLItemSelectorReturnTypeWithEmptyList()
		throws Exception {

		List<ItemSelectorReturnType> supportedItemSelectorReturnTypes =
			new ArrayList<>();

		ItemSelectorViewReturnTypeProvider itemSelectorViewReturnTypeProvider =
			new AdaptiveMediaBlogsItemSelectorViewReturnTypeProvider();

		List<ItemSelectorReturnType> itemSelectorReturnTypes =
			itemSelectorViewReturnTypeProvider.
				populateSupportedItemSelectorReturnTypes(
					supportedItemSelectorReturnTypes);

		Assert.assertEquals(
			itemSelectorReturnTypes.toString(), 1,
			itemSelectorReturnTypes.size());

		ItemSelectorReturnType itemSelectorReturnType =
			itemSelectorReturnTypes.get(0);

		Assert.assertTrue(
			itemSelectorReturnType instanceof
				ImageAdaptiveMediaURLItemSelectorReturnType);
	}

	@Test
	public void
			testAddImageAdaptiveMediaURLItemSelectorReturnTypeWithNonEmptyList()
		throws Exception {

		List<ItemSelectorReturnType> supportedItemSelectorReturnTypes =
			new ArrayList<>();

		supportedItemSelectorReturnTypes.add(
			new FileEntryItemSelectorReturnType());
		supportedItemSelectorReturnTypes.add(new URLItemSelectorReturnType());

		ItemSelectorViewReturnTypeProvider itemSelectorViewReturnTypeProvider =
			new AdaptiveMediaBlogsItemSelectorViewReturnTypeProvider();

		List<ItemSelectorReturnType> itemSelectorReturnTypes =
			itemSelectorViewReturnTypeProvider.
				populateSupportedItemSelectorReturnTypes(
					supportedItemSelectorReturnTypes);

		Assert.assertEquals(
			itemSelectorReturnTypes.toString(), 3,
			itemSelectorReturnTypes.size());

		Assert.assertTrue(
			itemSelectorReturnTypes.get(0) instanceof
				FileEntryItemSelectorReturnType);
		Assert.assertTrue(
			itemSelectorReturnTypes.get(1) instanceof
				URLItemSelectorReturnType);
		Assert.assertTrue(
			itemSelectorReturnTypes.get(2) instanceof
				ImageAdaptiveMediaURLItemSelectorReturnType);
	}

}