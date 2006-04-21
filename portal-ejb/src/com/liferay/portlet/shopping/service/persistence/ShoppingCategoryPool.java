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

import com.liferay.portlet.shopping.model.ShoppingCategory;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ShoppingCategoryPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCategoryPool {
	public static final String GROUP_NAME = ShoppingCategoryPool.class.getName();
	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static void clear() {
		_log.debug("Clear");
		_instance._clear();
	}

	public static ShoppingCategory get(String categoryId) {
		ShoppingCategory shoppingCategory = _instance._get(categoryId);
		_log.info("Get " + categoryId + " is " +
			((shoppingCategory == null) ? "NOT " : "") + "in cache");

		return shoppingCategory;
	}

	public static ShoppingCategory put(String categoryId, ShoppingCategory obj) {
		_log.info("Put " + categoryId);

		return _instance._put(categoryId, obj, false);
	}

	public static ShoppingCategory remove(String categoryId) {
		_log.info("Remove " + categoryId);

		return _instance._remove(categoryId);
	}

	public static ShoppingCategory update(String categoryId,
		ShoppingCategory obj) {
		_log.info("Update " + categoryId);

		return _instance._put(categoryId, obj, true);
	}

	private ShoppingCategoryPool() {
		_cacheable = ShoppingCategory.CACHEABLE;
		_cache = ClusterPool.getCache();
		ClusterPool.registerPool(ShoppingCategoryPool.class.getName());
	}

	private void _clear() {
		_cache.flushGroup(GROUP_NAME);
	}

	private ShoppingCategory _get(String categoryId) {
		if (!_cacheable) {
			return null;
		}
		else if (categoryId == null) {
			return null;
		}
		else {
			ShoppingCategory obj = null;
			String key = _encodeKey(categoryId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingCategory)_cache.getFromCache(key);
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

	private String _encodeKey(String categoryId) {
		String categoryIdString = String.valueOf(categoryId);

		if (Validator.isNull(categoryIdString)) {
			_log.debug("Key is null");

			return null;
		}
		else {
			String key = GROUP_NAME + StringPool.POUND + categoryIdString;
			_log.debug("Key " + key);

			return key;
		}
	}

	private ShoppingCategory _put(String categoryId, ShoppingCategory obj,
		boolean flush) {
		if (!_cacheable) {
			return obj;
		}
		else if (categoryId == null) {
			return obj;
		}
		else {
			String key = _encodeKey(categoryId);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, obj, GROUP_NAME_ARRAY);
			}

			return obj;
		}
	}

	private ShoppingCategory _remove(String categoryId) {
		if (!_cacheable) {
			return null;
		}
		else if (categoryId == null) {
			return null;
		}
		else {
			ShoppingCategory obj = null;
			String key = _encodeKey(categoryId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingCategory)_cache.getFromCache(key);
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

	private static Log _log = LogFactory.getLog(ShoppingCategoryPool.class);
	private static ShoppingCategoryPool _instance = new ShoppingCategoryPool();
	private GeneralCacheAdministrator _cache;
	private boolean _cacheable;
}