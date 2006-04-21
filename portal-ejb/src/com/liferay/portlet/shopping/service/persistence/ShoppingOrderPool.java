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

import com.liferay.portlet.shopping.model.ShoppingOrder;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ShoppingOrderPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingOrderPool {
	public static final String GROUP_NAME = ShoppingOrderPool.class.getName();
	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static void clear() {
		_log.debug("Clear");
		_instance._clear();
	}

	public static ShoppingOrder get(String orderId) {
		ShoppingOrder shoppingOrder = _instance._get(orderId);
		_log.info("Get " + orderId + " is " +
			((shoppingOrder == null) ? "NOT " : "") + "in cache");

		return shoppingOrder;
	}

	public static ShoppingOrder put(String orderId, ShoppingOrder obj) {
		_log.info("Put " + orderId);

		return _instance._put(orderId, obj, false);
	}

	public static ShoppingOrder remove(String orderId) {
		_log.info("Remove " + orderId);

		return _instance._remove(orderId);
	}

	public static ShoppingOrder update(String orderId, ShoppingOrder obj) {
		_log.info("Update " + orderId);

		return _instance._put(orderId, obj, true);
	}

	private ShoppingOrderPool() {
		_cacheable = ShoppingOrder.CACHEABLE;
		_cache = ClusterPool.getCache();
		ClusterPool.registerPool(ShoppingOrderPool.class.getName());
	}

	private void _clear() {
		_cache.flushGroup(GROUP_NAME);
	}

	private ShoppingOrder _get(String orderId) {
		if (!_cacheable) {
			return null;
		}
		else if (orderId == null) {
			return null;
		}
		else {
			ShoppingOrder obj = null;
			String key = _encodeKey(orderId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingOrder)_cache.getFromCache(key);
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

	private String _encodeKey(String orderId) {
		String orderIdString = String.valueOf(orderId);

		if (Validator.isNull(orderIdString)) {
			_log.debug("Key is null");

			return null;
		}
		else {
			String key = GROUP_NAME + StringPool.POUND + orderIdString;
			_log.debug("Key " + key);

			return key;
		}
	}

	private ShoppingOrder _put(String orderId, ShoppingOrder obj, boolean flush) {
		if (!_cacheable) {
			return obj;
		}
		else if (orderId == null) {
			return obj;
		}
		else {
			String key = _encodeKey(orderId);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, obj, GROUP_NAME_ARRAY);
			}

			return obj;
		}
	}

	private ShoppingOrder _remove(String orderId) {
		if (!_cacheable) {
			return null;
		}
		else if (orderId == null) {
			return null;
		}
		else {
			ShoppingOrder obj = null;
			String key = _encodeKey(orderId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingOrder)_cache.getFromCache(key);
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

	private static Log _log = LogFactory.getLog(ShoppingOrderPool.class);
	private static ShoppingOrderPool _instance = new ShoppingOrderPool();
	private GeneralCacheAdministrator _cache;
	private boolean _cacheable;
}