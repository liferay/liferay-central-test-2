/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;

/**
 * @author Mika Koivisto
 */
public class FilterRegistry {

	public static void destroy() {
		for (Filter filter : _filterMap.values()) {
			filter.destroy();
		}

		_filterMap.clear();
		_filterMappings.clear();
	}

	public static Filter getFilter(String filterName) {
		return _filterMap.get(filterName);
	}

	public static List<FilterMapping> getFilterMappings() {
		return _filterMappings;
	}

	public static void registerFilter(String filterName, Filter filter) {
		_filterMap.put(filterName, filter);
	}

	public static void registerFilterMapping(FilterMapping filterMapping) {
		_filterMappings.add(filterMapping);
	}

	private static Map<String, Filter> _filterMap =
		new ConcurrentHashMap<String, Filter>();
	private static List<FilterMapping> _filterMappings =
		new ArrayList<FilterMapping>();

}