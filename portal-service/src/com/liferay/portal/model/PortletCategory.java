/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletCategory implements Serializable {

	public PortletCategory() {
		this("root");
	}

	public PortletCategory(String name) {
		this(name, new HashSet<String>());
	}

	public PortletCategory(String name, Set<String> portletIds) {
		_name = name;
		_categories = new HashMap<String, PortletCategory>();
		_portletIds = portletIds;
	}

	public void addCategory(PortletCategory portletCategory) {
		_categories.put(portletCategory.getName(), portletCategory);
	}

	public Collection<PortletCategory> getCategories() {
		return Collections.unmodifiableCollection(_categories.values());
	}

	public PortletCategory getCategory(String name) {
		return _categories.get(name);
	}

	public String getName() {
		return _name;
	}

	public Set<String> getPortletIds() {
		return _portletIds;
	}

	public boolean isHidden() {
		if (_name.equals(PortletCategoryConstants.NAME_HIDDEN)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void merge(PortletCategory newPortletCategory) {
		_merge(this, newPortletCategory);
	}

	public void separate(Set<String> portletIds) {
		Iterator<PortletCategory> categoriesItr =
			_categories.values().iterator();

		while (categoriesItr.hasNext()) {
			PortletCategory category = categoriesItr.next();

			category.separate(portletIds);
		}

		Iterator<String>portletIdsItr = _portletIds.iterator();

		while (portletIdsItr.hasNext()) {
			String portletId = portletIdsItr.next();

			if (portletIds.contains(portletId)) {
				portletIdsItr.remove();
			}
		}
	}

	public void setPortletIds(Set<String> portletIds) {
		_portletIds = portletIds;
	}

	private void _merge(
		PortletCategory portletCategory1, PortletCategory portletCategory2) {

		Iterator<PortletCategory> itr =
			portletCategory2.getCategories().iterator();

		while (itr.hasNext()) {
			PortletCategory curCategory2 = itr.next();

			PortletCategory curCategory1 =
				portletCategory1.getCategory(curCategory2.getName());

			if (curCategory1 != null) {
				_merge(curCategory1, curCategory2);
			}
			else {
				portletCategory1.addCategory(curCategory2);
			}
		}

		Set<String> portletIds1 = portletCategory1.getPortletIds();
		Set<String> portletIds2 = portletCategory2.getPortletIds();

		portletIds1.addAll(portletIds2);
	}

	private Map<String, PortletCategory> _categories;
	private String _name;
	private Set<String> _portletIds;

}