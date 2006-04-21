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

import com.liferay.portlet.shopping.model.ShoppingOrderItem;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ShoppingOrderItemPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingOrderItemPool {
	public static final String GROUP_NAME = ShoppingOrderItemPool.class.getName();
	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static void clear() {
		_log.debug("Clear");
		_instance._clear();
	}

	public static ShoppingOrderItem get(ShoppingOrderItemPK shoppingOrderItemPK) {
		ShoppingOrderItem shoppingOrderItem = _instance._get(shoppingOrderItemPK);
		_log.info("Get " + shoppingOrderItemPK + " is " +
			((shoppingOrderItem == null) ? "NOT " : "") + "in cache");

		return shoppingOrderItem;
	}

	public static ShoppingOrderItem put(
		ShoppingOrderItemPK shoppingOrderItemPK, ShoppingOrderItem obj) {
		_log.info("Put " + shoppingOrderItemPK);

		return _instance._put(shoppingOrderItemPK, obj, false);
	}

	public static ShoppingOrderItem remove(
		ShoppingOrderItemPK shoppingOrderItemPK) {
		_log.info("Remove " + shoppingOrderItemPK);

		return _instance._remove(shoppingOrderItemPK);
	}

	public static ShoppingOrderItem update(
		ShoppingOrderItemPK shoppingOrderItemPK, ShoppingOrderItem obj) {
		_log.info("Update " + shoppingOrderItemPK);

		return _instance._put(shoppingOrderItemPK, obj, true);
	}

	private ShoppingOrderItemPool() {
		_cacheable = ShoppingOrderItem.CACHEABLE;
		_cache = ClusterPool.getCache();
		ClusterPool.registerPool(ShoppingOrderItemPool.class.getName());
	}

	private void _clear() {
		_cache.flushGroup(GROUP_NAME);
	}

	private ShoppingOrderItem _get(ShoppingOrderItemPK shoppingOrderItemPK) {
		if (!_cacheable) {
			return null;
		}
		else if (shoppingOrderItemPK == null) {
			return null;
		}
		else {
			ShoppingOrderItem obj = null;
			String key = _encodeKey(shoppingOrderItemPK);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingOrderItem)_cache.getFromCache(key);
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

	private String _encodeKey(ShoppingOrderItemPK shoppingOrderItemPK) {
		String shoppingOrderItemPKString = String.valueOf(shoppingOrderItemPK);

		if (Validator.isNull(shoppingOrderItemPKString)) {
			_log.debug("Key is null");

			return null;
		}
		else {
			String key = GROUP_NAME + StringPool.POUND +
				shoppingOrderItemPKString;
			_log.debug("Key " + key);

			return key;
		}
	}

	private ShoppingOrderItem _put(ShoppingOrderItemPK shoppingOrderItemPK,
		ShoppingOrderItem obj, boolean flush) {
		if (!_cacheable) {
			return obj;
		}
		else if (shoppingOrderItemPK == null) {
			return obj;
		}
		else {
			String key = _encodeKey(shoppingOrderItemPK);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, obj, GROUP_NAME_ARRAY);
			}

			return obj;
		}
	}

	private ShoppingOrderItem _remove(ShoppingOrderItemPK shoppingOrderItemPK) {
		if (!_cacheable) {
			return null;
		}
		else if (shoppingOrderItemPK == null) {
			return null;
		}
		else {
			ShoppingOrderItem obj = null;
			String key = _encodeKey(shoppingOrderItemPK);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingOrderItem)_cache.getFromCache(key);
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

	private static Log _log = LogFactory.getLog(ShoppingOrderItemPool.class);
	private static ShoppingOrderItemPool _instance = new ShoppingOrderItemPool();
	private GeneralCacheAdministrator _cache;
	private boolean _cacheable;
}