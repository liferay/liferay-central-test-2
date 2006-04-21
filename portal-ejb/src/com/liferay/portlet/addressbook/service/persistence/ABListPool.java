/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.addressbook.service.persistence;

import com.liferay.portal.util.ClusterPool;

import com.liferay.portlet.addressbook.model.ABList;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ABListPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ABListPool {
	public static final String GROUP_NAME = ABListPool.class.getName();
	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static void clear() {
		_log.debug("Clear");
		_instance._clear();
	}

	public static ABList get(String listId) {
		ABList abList = _instance._get(listId);
		_log.info("Get " + listId + " is " + ((abList == null) ? "NOT " : "") +
			"in cache");

		return abList;
	}

	public static ABList put(String listId, ABList obj) {
		_log.info("Put " + listId);

		return _instance._put(listId, obj, false);
	}

	public static ABList remove(String listId) {
		_log.info("Remove " + listId);

		return _instance._remove(listId);
	}

	public static ABList update(String listId, ABList obj) {
		_log.info("Update " + listId);

		return _instance._put(listId, obj, true);
	}

	private ABListPool() {
		_cacheable = ABList.CACHEABLE;
		_cache = ClusterPool.getCache();
		ClusterPool.registerPool(ABListPool.class.getName());
	}

	private void _clear() {
		_cache.flushGroup(GROUP_NAME);
	}

	private ABList _get(String listId) {
		if (!_cacheable) {
			return null;
		}
		else if (listId == null) {
			return null;
		}
		else {
			ABList obj = null;
			String key = _encodeKey(listId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ABList)_cache.getFromCache(key);
			}
			catch (NeedsRefreshException nfe) {
			}
			finally {
				if (obj == null) {
					_cache.cancelUpdate(key);
				}
			}

			return obj;
		}
	}

	private String _encodeKey(String listId) {
		String listIdString = String.valueOf(listId);

		if (Validator.isNull(listIdString)) {
			_log.debug("Key is null");

			return null;
		}
		else {
			String key = GROUP_NAME + StringPool.POUND + listIdString;
			_log.debug("Key " + key);

			return key;
		}
	}

	private ABList _put(String listId, ABList obj, boolean flush) {
		if (!_cacheable) {
			return obj;
		}
		else if (listId == null) {
			return obj;
		}
		else {
			String key = _encodeKey(listId);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, obj, GROUP_NAME_ARRAY);
			}

			return obj;
		}
	}

	private ABList _remove(String listId) {
		if (!_cacheable) {
			return null;
		}
		else if (listId == null) {
			return null;
		}
		else {
			ABList obj = null;
			String key = _encodeKey(listId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ABList)_cache.getFromCache(key);
				_cache.flushEntry(key);
			}
			catch (NeedsRefreshException nfe) {
			}
			finally {
				if (obj == null) {
					_cache.cancelUpdate(key);
				}
			}

			return obj;
		}
	}

	private static Log _log = LogFactory.getLog(ABListPool.class);
	private static ABListPool _instance = new ABListPool();
	private GeneralCacheAdministrator _cache;
	private boolean _cacheable;
}