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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.Layout;
import com.liferay.portal.util.ClusterPool;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LayoutPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutPool {
	public static final String GROUP_NAME = LayoutPool.class.getName();
	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static void clear() {
		if (_log.isDebugEnabled()) {
			_log.debug("Clear");
		}

		_instance._clear();
	}

	public static LayoutPK getByO_F(String ownerId, String friendlyURL) {
		LayoutPK pk = _instance._getByO_F(ownerId, friendlyURL);

		if (_log.isInfoEnabled()) {
			_log.info("Get O_F" + " " + ownerId.toString() + " " +
				friendlyURL.toString() + " is " + ((pk == null) ? "NOT " : "") +
				"in cache");
		}

		return pk;
	}

	public static LayoutPK putByO_F(String ownerId, String friendlyURL,
		LayoutPK pk) {
		if (_log.isInfoEnabled()) {
			_log.info("Put O_F" + " " + ownerId.toString() + " " +
				friendlyURL.toString());
		}

		return _instance._putByO_F(ownerId, friendlyURL, pk, false);
	}

	public static LayoutPK removeByO_F(String ownerId, String friendlyURL) {
		if (_log.isInfoEnabled()) {
			_log.info("Remove O_F" + " " + ownerId.toString() + " " +
				friendlyURL.toString());
		}

		return _instance._removeByO_F(ownerId, friendlyURL);
	}

	public static LayoutPK updateByO_F(String ownerId, String friendlyURL,
		LayoutPK pk) {
		if (_log.isInfoEnabled()) {
			_log.info("Update O_F" + " " + ownerId.toString() + " " +
				friendlyURL.toString());
		}

		return _instance._putByO_F(ownerId, friendlyURL, pk, true);
	}

	private LayoutPool() {
		_cacheable = Layout.CACHEABLE;
		_cache = ClusterPool.getCache();
		ClusterPool.registerPool(LayoutPool.class.getName());
	}

	private void _clear() {
		_cache.flushGroup(GROUP_NAME);
	}

	private LayoutPK _getByO_F(String ownerId, String friendlyURL) {
		if (!_cacheable) {
			return null;
		}
		else {
			LayoutPK pk = null;
			String key = _encodeKeyO_F(ownerId, friendlyURL);

			try {
				pk = (LayoutPK)_cache.getFromCache(key);
			}
			catch (NeedsRefreshException nfe) {
			}
			finally {
				if (pk == null) {
					_cache.cancelUpdate(key);
				}
			}

			return pk;
		}
	}

	private String _encodeKeyO_F(String ownerId, String friendlyURL) {
		String key = GROUP_NAME + StringPool.POUND + ownerId.toString() +
			friendlyURL.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("Key " + key);
		}

		return key;
	}

	private LayoutPK _putByO_F(String ownerId, String friendlyURL, LayoutPK pk,
		boolean flush) {
		if (!_cacheable) {
			return null;
		}
		else {
			String key = _encodeKeyO_F(ownerId, friendlyURL);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, pk, GROUP_NAME_ARRAY);
			}

			return pk;
		}
	}

	private LayoutPK _removeByO_F(String ownerId, String friendlyURL) {
		if (!_cacheable) {
			return null;
		}
		else {
			LayoutPK pk = null;
			String key = _encodeKeyO_F(ownerId, friendlyURL);

			try {
				pk = (LayoutPK)_cache.getFromCache(key);
				_cache.flushEntry(key);
			}
			catch (NeedsRefreshException nfe) {
			}
			finally {
				if (pk == null) {
					_cache.cancelUpdate(key);
				}
			}

			return pk;
		}
	}

	private static Log _log = LogFactory.getLog(LayoutPool.class);
	private static LayoutPool _instance = new LayoutPool();
	private GeneralCacheAdministrator _cache;
	private boolean _cacheable;
}