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

package com.liferay.portlet.admin.service.persistence;

import com.liferay.portal.util.ClusterPool;

import com.liferay.portlet.admin.model.AdminConfig;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="AdminConfigPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AdminConfigPool {
	public static final String GROUP_NAME = AdminConfigPool.class.getName();
	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static void clear() {
		_log.debug("Clear");
		_instance._clear();
	}

	public static AdminConfig get(String configId) {
		AdminConfig adminConfig = _instance._get(configId);
		_log.info("Get " + configId + " is " +
			((adminConfig == null) ? "NOT " : "") + "in cache");

		return adminConfig;
	}

	public static AdminConfig put(String configId, AdminConfig obj) {
		_log.info("Put " + configId);

		return _instance._put(configId, obj, false);
	}

	public static AdminConfig remove(String configId) {
		_log.info("Remove " + configId);

		return _instance._remove(configId);
	}

	public static AdminConfig update(String configId, AdminConfig obj) {
		_log.info("Update " + configId);

		return _instance._put(configId, obj, true);
	}

	private AdminConfigPool() {
		_cacheable = AdminConfig.CACHEABLE;
		_cache = ClusterPool.getCache();
		ClusterPool.registerPool(AdminConfigPool.class.getName());
	}

	private void _clear() {
		_cache.flushGroup(GROUP_NAME);
	}

	private AdminConfig _get(String configId) {
		if (!_cacheable) {
			return null;
		}
		else if (configId == null) {
			return null;
		}
		else {
			AdminConfig obj = null;
			String key = _encodeKey(configId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (AdminConfig)_cache.getFromCache(key);
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

	private String _encodeKey(String configId) {
		String configIdString = String.valueOf(configId);

		if (Validator.isNull(configIdString)) {
			_log.debug("Key is null");

			return null;
		}
		else {
			String key = GROUP_NAME + StringPool.POUND + configIdString;
			_log.debug("Key " + key);

			return key;
		}
	}

	private AdminConfig _put(String configId, AdminConfig obj, boolean flush) {
		if (!_cacheable) {
			return obj;
		}
		else if (configId == null) {
			return obj;
		}
		else {
			String key = _encodeKey(configId);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, obj, GROUP_NAME_ARRAY);
			}

			return obj;
		}
	}

	private AdminConfig _remove(String configId) {
		if (!_cacheable) {
			return null;
		}
		else if (configId == null) {
			return null;
		}
		else {
			AdminConfig obj = null;
			String key = _encodeKey(configId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (AdminConfig)_cache.getFromCache(key);
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

	private static Log _log = LogFactory.getLog(AdminConfigPool.class);
	private static AdminConfigPool _instance = new AdminConfigPool();
	private GeneralCacheAdministrator _cache;
	private boolean _cacheable;
}