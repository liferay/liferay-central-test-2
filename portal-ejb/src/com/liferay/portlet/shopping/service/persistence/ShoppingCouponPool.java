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

import com.liferay.portlet.shopping.model.ShoppingCoupon;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ShoppingCouponPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCouponPool {
	public static final String GROUP_NAME = ShoppingCouponPool.class.getName();
	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static void clear() {
		_log.debug("Clear");
		_instance._clear();
	}

	public static ShoppingCoupon get(String couponId) {
		ShoppingCoupon shoppingCoupon = _instance._get(couponId);
		_log.info("Get " + couponId + " is " +
			((shoppingCoupon == null) ? "NOT " : "") + "in cache");

		return shoppingCoupon;
	}

	public static ShoppingCoupon put(String couponId, ShoppingCoupon obj) {
		_log.info("Put " + couponId);

		return _instance._put(couponId, obj, false);
	}

	public static ShoppingCoupon remove(String couponId) {
		_log.info("Remove " + couponId);

		return _instance._remove(couponId);
	}

	public static ShoppingCoupon update(String couponId, ShoppingCoupon obj) {
		_log.info("Update " + couponId);

		return _instance._put(couponId, obj, true);
	}

	private ShoppingCouponPool() {
		_cacheable = ShoppingCoupon.CACHEABLE;
		_cache = ClusterPool.getCache();
		ClusterPool.registerPool(ShoppingCouponPool.class.getName());
	}

	private void _clear() {
		_cache.flushGroup(GROUP_NAME);
	}

	private ShoppingCoupon _get(String couponId) {
		if (!_cacheable) {
			return null;
		}
		else if (couponId == null) {
			return null;
		}
		else {
			ShoppingCoupon obj = null;
			String key = _encodeKey(couponId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingCoupon)_cache.getFromCache(key);
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

	private String _encodeKey(String couponId) {
		String couponIdString = String.valueOf(couponId);

		if (Validator.isNull(couponIdString)) {
			_log.debug("Key is null");

			return null;
		}
		else {
			String key = GROUP_NAME + StringPool.POUND + couponIdString;
			_log.debug("Key " + key);

			return key;
		}
	}

	private ShoppingCoupon _put(String couponId, ShoppingCoupon obj,
		boolean flush) {
		if (!_cacheable) {
			return obj;
		}
		else if (couponId == null) {
			return obj;
		}
		else {
			String key = _encodeKey(couponId);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, obj, GROUP_NAME_ARRAY);
			}

			return obj;
		}
	}

	private ShoppingCoupon _remove(String couponId) {
		if (!_cacheable) {
			return null;
		}
		else if (couponId == null) {
			return null;
		}
		else {
			ShoppingCoupon obj = null;
			String key = _encodeKey(couponId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingCoupon)_cache.getFromCache(key);
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

	private static Log _log = LogFactory.getLog(ShoppingCouponPool.class);
	private static ShoppingCouponPool _instance = new ShoppingCouponPool();
	private GeneralCacheAdministrator _cache;
	private boolean _cacheable;
}