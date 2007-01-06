/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.calendar.service.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.ClusterPool;
import com.liferay.util.CollectionFactory;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import java.util.Map;

/**
 * <a href="CalEventLocalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CalEventLocalUtil {

	public static final String GROUP_NAME =
		CalEventLocalUtil.class.getName();

	public static final String[] GROUP_NAME_ARRAY = new String[] {GROUP_NAME};

	protected static void clearEventsPool(long groupId) {
		String key = _encodeKey(groupId);

		_cache.flushEntry(key);
	}

	protected static Map getEventsPool(long groupId) {
		String key = _encodeKey(groupId);

		Map eventsPool = null;

		try {
			eventsPool = (Map)_cache.getFromCache(key);
		}
		catch (NeedsRefreshException nfe) {
			eventsPool = CollectionFactory.getSyncHashMap();

			_cache.putInCache(key, eventsPool, GROUP_NAME_ARRAY);
		}
		finally {
			if (eventsPool == null) {
				_cache.cancelUpdate(key);
			}
		}

		return eventsPool;
	}

	private static String _encodeKey(long groupId) {
		return GROUP_NAME + StringPool.POUND + groupId;
	}

	private static GeneralCacheAdministrator _cache = ClusterPool.getCache();

}