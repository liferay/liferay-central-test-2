/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
 * <a href="PortletCategory.java.html"><b><i>View Source</i></b></a>
 *
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

	public String getName() {
		return _name;
	}

	public Collection<PortletCategory> getCategories() {
		return Collections.unmodifiableCollection(_categories.values());
	}

	public void addCategory(PortletCategory portletCategory) {
		_categories.put(portletCategory.getName(), portletCategory);
	}

	public PortletCategory getCategory(String name) {
		return _categories.get(name);
	}

	public Set<String> getPortletIds() {
		return _portletIds;
	}

	public void setPortletIds(Set<String> portletIds) {
		_portletIds = portletIds;
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

	private String _name;
	private Map<String, PortletCategory> _categories;
	private Set<String> _portletIds;

}