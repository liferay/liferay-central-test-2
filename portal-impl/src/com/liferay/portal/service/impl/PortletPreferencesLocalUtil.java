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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.PortletPreferencesImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public class PortletPreferencesLocalUtil {

	public static final String CACHE_NAME =
		PortletPreferencesLocalUtil.class.getName();

	protected static void clearPreferencesPool() {
		_portalCache.removeAll();
	}

	protected static void clearPreferencesPool(long ownerId, int ownerType) {
		String key = _encodeKey(ownerId, ownerType);

		_portalCache.remove(key);
	}

	protected static Map<String, PortletPreferencesImpl> getPreferencesPool(
			long ownerId, int ownerType) {
		String key = _encodeKey(ownerId, ownerType);

		Map<String, PortletPreferencesImpl> preferencesPool =
			(Map<String, PortletPreferencesImpl>)_portalCache.get(key);

		if (preferencesPool == null) {
			preferencesPool =
				new ConcurrentHashMap<String, PortletPreferencesImpl>();

			_portalCache.put(key, preferencesPool);
		}

		return preferencesPool;
	}

	private static String _encodeKey(long ownerId, int ownerType) {
		StringBundler sb = new StringBundler(5);

		sb.append(CACHE_NAME);
		sb.append(StringPool.POUND);
		sb.append(StringUtil.toHexString(ownerId));
		sb.append(StringPool.POUND);
		sb.append(StringUtil.toHexString(ownerType));

		return sb.toString();
	}

	private static PortalCache _portalCache = MultiVMPoolUtil.getCache(
		CACHE_NAME);

}