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

import com.liferay.portlet.addressbook.model.ABContact;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ABContactPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ABContactPool {
	public static final String GROUP_NAME = ABContactPool.class.getName();
	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static void clear() {
		_log.debug("Clear");
		_instance._clear();
	}

	public static ABContact get(String contactId) {
		ABContact abContact = _instance._get(contactId);
		_log.info("Get " + contactId + " is " +
			((abContact == null) ? "NOT " : "") + "in cache");

		return abContact;
	}

	public static ABContact put(String contactId, ABContact obj) {
		_log.info("Put " + contactId);

		return _instance._put(contactId, obj, false);
	}

	public static ABContact remove(String contactId) {
		_log.info("Remove " + contactId);

		return _instance._remove(contactId);
	}

	public static ABContact update(String contactId, ABContact obj) {
		_log.info("Update " + contactId);

		return _instance._put(contactId, obj, true);
	}

	private ABContactPool() {
		_cacheable = ABContact.CACHEABLE;
		_cache = ClusterPool.getCache();
		ClusterPool.registerPool(ABContactPool.class.getName());
	}

	private void _clear() {
		_cache.flushGroup(GROUP_NAME);
	}

	private ABContact _get(String contactId) {
		if (!_cacheable) {
			return null;
		}
		else if (contactId == null) {
			return null;
		}
		else {
			ABContact obj = null;
			String key = _encodeKey(contactId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ABContact)_cache.getFromCache(key);
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

	private String _encodeKey(String contactId) {
		String contactIdString = String.valueOf(contactId);

		if (Validator.isNull(contactIdString)) {
			_log.debug("Key is null");

			return null;
		}
		else {
			String key = GROUP_NAME + StringPool.POUND + contactIdString;
			_log.debug("Key " + key);

			return key;
		}
	}

	private ABContact _put(String contactId, ABContact obj, boolean flush) {
		if (!_cacheable) {
			return obj;
		}
		else if (contactId == null) {
			return obj;
		}
		else {
			String key = _encodeKey(contactId);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, obj, GROUP_NAME_ARRAY);
			}

			return obj;
		}
	}

	private ABContact _remove(String contactId) {
		if (!_cacheable) {
			return null;
		}
		else if (contactId == null) {
			return null;
		}
		else {
			ABContact obj = null;
			String key = _encodeKey(contactId);

			if (Validator.isNull(key)) {
				return null;
			}

			try {
				obj = (ABContact)_cache.getFromCache(key);
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

	private static Log _log = LogFactory.getLog(ABContactPool.class);
	private static ABContactPool _instance = new ABContactPool();
	private GeneralCacheAdministrator _cache;
	private boolean _cacheable;
}