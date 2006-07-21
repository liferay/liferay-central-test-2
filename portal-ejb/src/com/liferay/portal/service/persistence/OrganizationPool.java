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

import com.liferay.portal.model.Organization;
import com.liferay.portal.util.ClusterPool;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="OrganizationPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrganizationPool {
	public static final String GROUP_NAME = OrganizationPool.class.getName();
	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static void clear() {
		if (_log.isDebugEnabled()) {
			_log.debug("Clear");
		}

		_instance._clear();
	}

	public static String getByC_N(String companyId, String name) {
		String pk = _instance._getByC_N(companyId, name);

		if (_log.isInfoEnabled()) {
			_log.info("Get C_N" + " " + companyId.toString() + " " +
				name.toString() + " is " + ((pk == null) ? "NOT " : "") +
				"in cache");
		}

		return pk;
	}

	public static String putByC_N(String companyId, String name, String pk) {
		if (_log.isInfoEnabled()) {
			_log.info("Put C_N" + " " + companyId.toString() + " " +
				name.toString());
		}

		return _instance._putByC_N(companyId, name, pk, false);
	}

	public static String removeByC_N(String companyId, String name) {
		if (_log.isInfoEnabled()) {
			_log.info("Remove C_N" + " " + companyId.toString() + " " +
				name.toString());
		}

		return _instance._removeByC_N(companyId, name);
	}

	public static String updateByC_N(String companyId, String name, String pk) {
		if (_log.isInfoEnabled()) {
			_log.info("Update C_N" + " " + companyId.toString() + " " +
				name.toString());
		}

		return _instance._putByC_N(companyId, name, pk, true);
	}

	private OrganizationPool() {
		_cacheable = Organization.CACHEABLE;
		_cache = ClusterPool.getCache();
		ClusterPool.registerPool(OrganizationPool.class.getName());
	}

	private void _clear() {
		_cache.flushGroup(GROUP_NAME);
	}

	private String _getByC_N(String companyId, String name) {
		if (!_cacheable) {
			return null;
		}
		else {
			String pk = null;
			String key = _encodeKeyC_N(companyId, name);

			try {
				pk = (String)_cache.getFromCache(key);
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

	private String _encodeKeyC_N(String companyId, String name) {
		String key = GROUP_NAME + StringPool.POUND + companyId.toString() +
			name.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("Key " + key);
		}

		return key;
	}

	private String _putByC_N(String companyId, String name, String pk,
		boolean flush) {
		if (!_cacheable) {
			return null;
		}
		else {
			String key = _encodeKeyC_N(companyId, name);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, pk, GROUP_NAME_ARRAY);
			}

			return pk;
		}
	}

	private String _removeByC_N(String companyId, String name) {
		if (!_cacheable) {
			return null;
		}
		else {
			String pk = null;
			String key = _encodeKeyC_N(companyId, name);

			try {
				pk = (String)_cache.getFromCache(key);
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

	private static Log _log = LogFactory.getLog(OrganizationPool.class);
	private static OrganizationPool _instance = new OrganizationPool();
	private GeneralCacheAdministrator _cache;
	private boolean _cacheable;
}