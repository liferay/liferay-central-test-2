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

import com.liferay.portlet.shopping.model.ShoppingCart;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ShoppingCartPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCartPool {
	public static final String GROUP_NAME = ShoppingCartPool.class.getName();
	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static void clear() {
		_log.debug("Clear");
		_instance._clear();
	}

	public static ShoppingCart get(String cartId) {
		ShoppingCart shoppingCart = _instance._get(cartId);
		_log.info("Get " + cartId + " is " +
			((shoppingCart == null) ? "NOT " : "") + "in cache");

		return shoppingCart;
	}

	public static ShoppingCart put(String cartId, ShoppingCart obj) {
		_log.info("Put " + cartId);

		return _instance._put(cartId, obj, false);
	}

	public static ShoppingCart remove(String cartId) {
		_log.info("Remove " + cartId);

		return _instance._remove(cartId);
	}

	public static ShoppingCart update(String cartId, ShoppingCart obj) {
		_log.info("Update " + cartId);

		return _instance._put(cartId, obj, true);
	}

	private ShoppingCartPool() {
		_cacheable = ShoppingCart.CACHEABLE;
		_cache = ClusterPool.getCache();
		ClusterPool.registerPool(ShoppingCartPool.class.getName());
	}

	private void _clear() {
		_cache.flushGroup(GROUP_NAME);
	}

	private ShoppingCart _get(String cartId) {
		if (!_cacheable) {
			return null;
		}
		else if (cartId == null) {
			return null;
		}
		else {
			ShoppingCart obj = null;
			String key = _encodeKey(cartId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingCart)_cache.getFromCache(key);
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

	private String _encodeKey(String cartId) {
		String cartIdString = String.valueOf(cartId);

		if (Validator.isNull(cartIdString)) {
			_log.debug("Key is null");

			return null;
		}
		else {
			String key = GROUP_NAME + StringPool.POUND + cartIdString;
			_log.debug("Key " + key);

			return key;
		}
	}

	private ShoppingCart _put(String cartId, ShoppingCart obj, boolean flush) {
		if (!_cacheable) {
			return obj;
		}
		else if (cartId == null) {
			return obj;
		}
		else {
			String key = _encodeKey(cartId);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, obj, GROUP_NAME_ARRAY);
			}

			return obj;
		}
	}

	private ShoppingCart _remove(String cartId) {
		if (!_cacheable) {
			return null;
		}
		else if (cartId == null) {
			return null;
		}
		else {
			ShoppingCart obj = null;
			String key = _encodeKey(cartId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingCart)_cache.getFromCache(key);
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

	private static Log _log = LogFactory.getLog(ShoppingCartPool.class);
	private static ShoppingCartPool _instance = new ShoppingCartPool();
	private GeneralCacheAdministrator _cache;
	private boolean _cacheable;
}