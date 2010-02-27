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

package com.liferay.portlet.calendar.service.impl;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.calendar.model.CalEvent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="CalEventLocalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public class CalEventLocalUtil {

	public static final String CACHE_NAME = CalEventLocalUtil.class.getName();

	protected static void clearEventsPool(long groupId) {
		String key = _encodeKey(groupId);

		_cache.remove(key);
	}

	protected static Map<String, List<CalEvent>> getEventsPool(long groupId) {
		String key = _encodeKey(groupId);

		Map <String, List<CalEvent>> eventsPool =
			(Map<String, List<CalEvent>>)_cache.get(key);

		if (eventsPool == null) {
			eventsPool = new ConcurrentHashMap<String, List<CalEvent>>();

			_cache.put(key, eventsPool);
		}

		return eventsPool;
	}

	private static String _encodeKey(long groupId) {
		return CACHE_NAME.concat(StringPool.POUND).concat(
			String.valueOf(groupId));
	}

	private static PortalCache _cache = MultiVMPoolUtil.getCache(CACHE_NAME);

}