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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.util.ClusterPool;

import com.liferay.portlet.shopping.model.ShoppingItem;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ShoppingItemPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingItemPool {
	public static final String GROUP_NAME = ShoppingItemPool.class.getName();
	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static void clear() {
		_log.debug("Clear");
		_instance._clear();
	}

	public static ShoppingItem get(String itemId) {
		ShoppingItem shoppingItem = _instance._get(itemId);
		_log.info("Get " + itemId + " is " +
			((shoppingItem == null) ? "NOT " : "") + "in cache");

		return shoppingItem;
	}

	public static ShoppingItem put(String itemId, ShoppingItem obj) {
		_log.info("Put " + itemId);

		return _instance._put(itemId, obj, false);
	}

	public static ShoppingItem remove(String itemId) {
		_log.info("Remove " + itemId);

		return _instance._remove(itemId);
	}

	public static ShoppingItem update(String itemId, ShoppingItem obj) {
		_log.info("Update " + itemId);

		return _instance._put(itemId, obj, true);
	}

	private ShoppingItemPool() {
		_cacheable = ShoppingItem.CACHEABLE;
		_cache = ClusterPool.getCache();
		ClusterPool.registerPool(ShoppingItemPool.class.getName());
	}

	private void _clear() {
		_cache.flushGroup(GROUP_NAME);
	}

	private ShoppingItem _get(String itemId) {
		if (!_cacheable) {
			return null;
		}
		else if (itemId == null) {
			return null;
		}
		else {
			ShoppingItem obj = null;
			String key = _encodeKey(itemId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingItem)_cache.getFromCache(key);
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

	private String _encodeKey(String itemId) {
		String itemIdString = String.valueOf(itemId);

		if (Validator.isNull(itemIdString)) {
			_log.debug("Key is null");

			return null;
		}
		else {
			String key = GROUP_NAME + StringPool.POUND + itemIdString;
			_log.debug("Key " + key);

			return key;
		}
	}

	private ShoppingItem _put(String itemId, ShoppingItem obj, boolean flush) {
		if (!_cacheable) {
			return obj;
		}
		else if (itemId == null) {
			return obj;
		}
		else {
			String key = _encodeKey(itemId);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, obj, GROUP_NAME_ARRAY);
			}

			return obj;
		}
	}

	private ShoppingItem _remove(String itemId) {
		if (!_cacheable) {
			return null;
		}
		else if (itemId == null) {
			return null;
		}
		else {
			ShoppingItem obj = null;
			String key = _encodeKey(itemId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingItem)_cache.getFromCache(key);
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

	private static Log _log = LogFactory.getLog(ShoppingItemPool.class);
	private static ShoppingItemPool _instance = new ShoppingItemPool();
	private GeneralCacheAdministrator _cache;
	private boolean _cacheable;
}