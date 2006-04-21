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

import com.liferay.portlet.shopping.model.ShoppingItemPrice;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ShoppingItemPricePool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingItemPricePool {
	public static final String GROUP_NAME = ShoppingItemPricePool.class.getName();
	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static void clear() {
		_log.debug("Clear");
		_instance._clear();
	}

	public static ShoppingItemPrice get(String itemPriceId) {
		ShoppingItemPrice shoppingItemPrice = _instance._get(itemPriceId);
		_log.info("Get " + itemPriceId + " is " +
			((shoppingItemPrice == null) ? "NOT " : "") + "in cache");

		return shoppingItemPrice;
	}

	public static ShoppingItemPrice put(String itemPriceId,
		ShoppingItemPrice obj) {
		_log.info("Put " + itemPriceId);

		return _instance._put(itemPriceId, obj, false);
	}

	public static ShoppingItemPrice remove(String itemPriceId) {
		_log.info("Remove " + itemPriceId);

		return _instance._remove(itemPriceId);
	}

	public static ShoppingItemPrice update(String itemPriceId,
		ShoppingItemPrice obj) {
		_log.info("Update " + itemPriceId);

		return _instance._put(itemPriceId, obj, true);
	}

	private ShoppingItemPricePool() {
		_cacheable = ShoppingItemPrice.CACHEABLE;
		_cache = ClusterPool.getCache();
		ClusterPool.registerPool(ShoppingItemPricePool.class.getName());
	}

	private void _clear() {
		_cache.flushGroup(GROUP_NAME);
	}

	private ShoppingItemPrice _get(String itemPriceId) {
		if (!_cacheable) {
			return null;
		}
		else if (itemPriceId == null) {
			return null;
		}
		else {
			ShoppingItemPrice obj = null;
			String key = _encodeKey(itemPriceId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingItemPrice)_cache.getFromCache(key);
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

	private String _encodeKey(String itemPriceId) {
		String itemPriceIdString = String.valueOf(itemPriceId);

		if (Validator.isNull(itemPriceIdString)) {
			_log.debug("Key is null");

			return null;
		}
		else {
			String key = GROUP_NAME + StringPool.POUND + itemPriceIdString;
			_log.debug("Key " + key);

			return key;
		}
	}

	private ShoppingItemPrice _put(String itemPriceId, ShoppingItemPrice obj,
		boolean flush) {
		if (!_cacheable) {
			return obj;
		}
		else if (itemPriceId == null) {
			return obj;
		}
		else {
			String key = _encodeKey(itemPriceId);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, obj, GROUP_NAME_ARRAY);
			}

			return obj;
		}
	}

	private ShoppingItemPrice _remove(String itemPriceId) {
		if (!_cacheable) {
			return null;
		}
		else if (itemPriceId == null) {
			return null;
		}
		else {
			ShoppingItemPrice obj = null;
			String key = _encodeKey(itemPriceId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingItemPrice)_cache.getFromCache(key);
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

	private static Log _log = LogFactory.getLog(ShoppingItemPricePool.class);
	private static ShoppingItemPricePool _instance = new ShoppingItemPricePool();
	private GeneralCacheAdministrator _cache;
	private boolean _cacheable;
}