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

package com.liferay.item.selector.criteria.url.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

import java.net.URL;

import java.util.Set;
import java.util.UUID;

/**
 * @author Roberto Díaz
 */
public class URLItemSelectorCriterion extends BaseItemSelectorCriterion {

	public URLItemSelectorCriterion() {
		super(_AVAILABLE_RETURN_TYPES);
	}

	private static final Set<Class<?>> _AVAILABLE_RETURN_TYPES =
		getInmutableSet(URL.class, UUID.class);

}