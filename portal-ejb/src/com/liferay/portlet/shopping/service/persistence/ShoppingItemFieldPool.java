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

import com.liferay.portlet.shopping.model.ShoppingItemField;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ShoppingItemFieldPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingItemFieldPool {
	public static final String GROUP_NAME = ShoppingItemFieldPool.class.getName();
	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static void clear() {
		_log.debug("Clear");
		_instance._clear();
	}

	public static ShoppingItemField get(String itemFieldId) {
		ShoppingItemField shoppingItemField = _instance._get(itemFieldId);
		_log.info("Get " + itemFieldId + " is " +
			((shoppingItemField == null) ? "NOT " : "") + "in cache");

		return shoppingItemField;
	}

	public static ShoppingItemField put(String itemFieldId,
		ShoppingItemField obj) {
		_log.info("Put " + itemFieldId);

		return _instance._put(itemFieldId, obj, false);
	}

	public static ShoppingItemField remove(String itemFieldId) {
		_log.info("Remove " + itemFieldId);

		return _instance._remove(itemFieldId);
	}

	public static ShoppingItemField update(String itemFieldId,
		ShoppingItemField obj) {
		_log.info("Update " + itemFieldId);

		return _instance._put(itemFieldId, obj, true);
	}

	private ShoppingItemFieldPool() {
		_cacheable = ShoppingItemField.CACHEABLE;
		_cache = ClusterPool.getCache();
		ClusterPool.registerPool(ShoppingItemFieldPool.class.getName());
	}

	private void _clear() {
		_cache.flushGroup(GROUP_NAME);
	}

	private ShoppingItemField _get(String itemFieldId) {
		if (!_cacheable) {
			return null;
		}
		else if (itemFieldId == null) {
			return null;
		}
		else {
			ShoppingItemField obj = null;
			String key = _encodeKey(itemFieldId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingItemField)_cache.getFromCache(key);
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

	private String _encodeKey(String itemFieldId) {
		String itemFieldIdString = String.valueOf(itemFieldId);

		if (Validator.isNull(itemFieldIdString)) {
			_log.debug("Key is null");

			return null;
		}
		else {
			String key = GROUP_NAME + StringPool.POUND + itemFieldIdString;
			_log.debug("Key " + key);

			return key;
		}
	}

	private ShoppingItemField _put(String itemFieldId, ShoppingItemField obj,
		boolean flush) {
		if (!_cacheable) {
			return obj;
		}
		else if (itemFieldId == null) {
			return obj;
		}
		else {
			String key = _encodeKey(itemFieldId);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, obj, GROUP_NAME_ARRAY);
			}

			return obj;
		}
	}

	private ShoppingItemField _remove(String itemFieldId) {
		if (!_cacheable) {
			return null;
		}
		else if (itemFieldId == null) {
			return null;
		}
		else {
			ShoppingItemField obj = null;
			String key = _encodeKey(itemFieldId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ShoppingItemField)_cache.getFromCache(key);
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

	private static Log _log = LogFactory.getLog(ShoppingItemFieldPool.class);
	private static ShoppingItemFieldPool _instance = new ShoppingItemFieldPool();
	private GeneralCacheAdministrator _cache;
	private boolean _cacheable;
}