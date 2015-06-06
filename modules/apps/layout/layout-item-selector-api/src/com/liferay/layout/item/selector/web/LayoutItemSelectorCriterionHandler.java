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

package com.liferay.layout.item.selector.web;

import com.liferay.item.selector.ItemSelectorCriterionHandler;
import com.liferay.item.selector.ItemSelectorView;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(service = ItemSelectorCriterionHandler.class)
public class LayoutItemSelectorCriterionHandler
	implements ItemSelectorCriterionHandler<LayoutItemSelectorCriterion> {

	@Override
	public Class<LayoutItemSelectorCriterion> getItemSelectorCriterionClass() {
		return LayoutItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorView<LayoutItemSelectorCriterion>>
		getItemSelectorViews(
			LayoutItemSelectorCriterion layoutItemSelectorCriterion) {

		List<ItemSelectorView<LayoutItemSelectorCriterion>> itemSelectorViews =
			new ArrayList<>();

		itemSelectorViews.add(new LayoutItemSelectorView());

		return itemSelectorViews;
	}

}