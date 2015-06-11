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

package com.liferay.item.selector.web;

import com.liferay.item.selector.BaseItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;

import java.util.Set;

/**
 * @author Iván Zaera
 */
public class FlickrItemSelectorCriterion extends BaseItemSelectorCriterion {

	public FlickrItemSelectorCriterion() {
		super(_availableItemSelectorReturnTypes);
	}

	public String[] getTags() {
		return _tags;
	}

	public String getUser() {
		return _user;
	}

	public void setTags(String[] tags) {
		_tags = tags;
	}

	public void setUser(String user) {
		_user = user;
	}

	private static final Set<ItemSelectorReturnType>
		_availableItemSelectorReturnTypes = getImmutableSet(
			TestItemSelectorReturnType.STRING, TestItemSelectorReturnType.URL);

	private String[] _tags = {"me", "photo", "picture"};
	private String _user = "anonymous";

}