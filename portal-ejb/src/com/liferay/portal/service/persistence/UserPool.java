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

import com.liferay.portal.model.User;
import com.liferay.portal.util.ClusterPool;

import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UserPool.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserPool {
	public static final String GROUP_NAME = UserPool.class.getName();
	public static final String[] GROUP_NAME_ARRAY = new String[] { GROUP_NAME };

	public static void clear() {
		if (_log.isDebugEnabled()) {
			_log.debug("Clear");
		}

		_instance._clear();
	}

	public static String getByC_U(String companyId, String userId) {
		String pk = _instance._getByC_U(companyId, userId);

		if (_log.isInfoEnabled()) {
			_log.info("Get C_U" + " " + companyId.toString() + " " +
				userId.toString() + " is " + ((pk == null) ? "NOT " : "") +
				"in cache");
		}

		return pk;
	}

	public static String putByC_U(String companyId, String userId, String pk) {
		if (_log.isInfoEnabled()) {
			_log.info("Put C_U" + " " + companyId.toString() + " " +
				userId.toString());
		}

		return _instance._putByC_U(companyId, userId, pk, false);
	}

	public static String removeByC_U(String companyId, String userId) {
		if (_log.isInfoEnabled()) {
			_log.info("Remove C_U" + " " + companyId.toString() + " " +
				userId.toString());
		}

		return _instance._removeByC_U(companyId, userId);
	}

	public static String updateByC_U(String companyId, String userId, String pk) {
		if (_log.isInfoEnabled()) {
			_log.info("Update C_U" + " " + companyId.toString() + " " +
				userId.toString());
		}

		return _instance._putByC_U(companyId, userId, pk, true);
	}

	public static String getByC_EA(String companyId, String emailAddress) {
		String pk = _instance._getByC_EA(companyId, emailAddress);

		if (_log.isInfoEnabled()) {
			_log.info("Get C_EA" + " " + companyId.toString() + " " +
				emailAddress.toString() + " is " +
				((pk == null) ? "NOT " : "") + "in cache");
		}

		return pk;
	}

	public static String putByC_EA(String companyId, String emailAddress,
		String pk) {
		if (_log.isInfoEnabled()) {
			_log.info("Put C_EA" + " " + companyId.toString() + " " +
				emailAddress.toString());
		}

		return _instance._putByC_EA(companyId, emailAddress, pk, false);
	}

	public static String removeByC_EA(String companyId, String emailAddress) {
		if (_log.isInfoEnabled()) {
			_log.info("Remove C_EA" + " " + companyId.toString() + " " +
				emailAddress.toString());
		}

		return _instance._removeByC_EA(companyId, emailAddress);
	}

	public static String updateByC_EA(String companyId, String emailAddress,
		String pk) {
		if (_log.isInfoEnabled()) {
			_log.info("Update C_EA" + " " + companyId.toString() + " " +
				emailAddress.toString());
		}

		return _instance._putByC_EA(companyId, emailAddress, pk, true);
	}

	private UserPool() {
		_cacheable = User.CACHEABLE;
		_cache = ClusterPool.getCache();
		ClusterPool.registerPool(UserPool.class.getName());
	}

	private void _clear() {
		_cache.flushGroup(GROUP_NAME);
	}

	private String _getByC_U(String companyId, String userId) {
		if (!_cacheable) {
			return null;
		}
		else {
			String pk = null;
			String key = _encodeKeyC_U(companyId, userId);

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

	private String _encodeKeyC_U(String companyId, String userId) {
		String key = GROUP_NAME + StringPool.POUND + companyId.toString() +
			userId.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("Key " + key);
		}

		return key;
	}

	private String _putByC_U(String companyId, String userId, String pk,
		boolean flush) {
		if (!_cacheable) {
			return null;
		}
		else {
			String key = _encodeKeyC_U(companyId, userId);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, pk, GROUP_NAME_ARRAY);
			}

			return pk;
		}
	}

	private String _removeByC_U(String companyId, String userId) {
		if (!_cacheable) {
			return null;
		}
		else {
			String pk = null;
			String key = _encodeKeyC_U(companyId, userId);

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

	private String _getByC_EA(String companyId, String emailAddress) {
		if (!_cacheable) {
			return null;
		}
		else {
			String pk = null;
			String key = _encodeKeyC_EA(companyId, emailAddress);

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

	private String _encodeKeyC_EA(String companyId, String emailAddress) {
		String key = GROUP_NAME + StringPool.POUND + companyId.toString() +
			emailAddress.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("Key " + key);
		}

		return key;
	}

	private String _putByC_EA(String companyId, String emailAddress, String pk,
		boolean flush) {
		if (!_cacheable) {
			return null;
		}
		else {
			String key = _encodeKeyC_EA(companyId, emailAddress);

			if (Validator.isNotNull(key)) {
				if (flush) {
					_cache.flushEntry(key);
				}

				_cache.putInCache(key, pk, GROUP_NAME_ARRAY);
			}

			return pk;
		}
	}

	private String _removeByC_EA(String companyId, String emailAddress) {
		if (!_cacheable) {
			return null;
		}
		else {
			String pk = null;
			String key = _encodeKeyC_EA(companyId, emailAddress);

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

	private static Log _log = LogFactory.getLog(UserPool.class);
	private static UserPool _instance = new UserPool();
	private GeneralCacheAdministrator _cache;
	private boolean _cacheable;
}