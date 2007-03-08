/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.layoutcache;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.ClusterPool;
import com.liferay.portal.util.PortalInstances;
import com.liferay.util.CachePolicy;
import com.liferay.util.GetterUtil;
import com.liferay.util.SystemProperties;
import com.liferay.util.Validator;
import com.liferay.util.servlet.filters.CacheResponseData;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LayoutCacheUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class LayoutCacheUtil {

	public static String[] GROUP_NAME_ARRAY;

	public static String GROUP = "_GROUP_";

	public static final long REFRESH_TIME = GetterUtil.getLong(
		SystemProperties.get(
			LayoutCacheFilter.class.getName() + ".refresh.time"));

	static {
		String[] companyIds = PortalInstances.getCompanyIds();

		GROUP_NAME_ARRAY = new String[companyIds.length];

		for (int i = 0; i < companyIds.length; i++) {
			GROUP_NAME_ARRAY[i] = _encodeGroupName(companyIds[i]);
		}
	}

	public static void clearCache() {
		String[] companyIds = PortalInstances.getCompanyIds();

		for (int i = 0; i < companyIds.length; i++) {
			clearCache(companyIds[i]);
		}
	}

	public static void clearCache(String companyId) {
		String groupName = _encodeGroupName(companyId);

		_cache.flushGroup(groupName);

		if (_log.isInfoEnabled()) {
			_log.info("Cleared layout cache for " + companyId);
		}
	}

	public static CacheResponseData getCacheResponseData(
		String companyId, String key) {

		CacheResponseData data = null;

		if (Validator.isNull(key)) {
			return null;
		}

		key = _encodeKey(companyId, key);

		try {
			data = (CacheResponseData)_cache.getFromCache(key);
		}
		catch (NeedsRefreshException nre) {
			if (_log.isWarnEnabled()) {
				_log.warn("Layout " + key + " is not cached");
			}
		}
		finally {
			if (data == null) {
				_cache.cancelUpdate(key);
			}
		}

		return data;
	}

	public static void putCacheResponseData(
		String companyId, String key, CacheResponseData data) {

		if (data != null) {
			key = _encodeKey(companyId, key);

			_cache.putInCache(
				key, data, GROUP_NAME_ARRAY, new CachePolicy(REFRESH_TIME));
		}
	}

	private static String _encodeGroupName(String companyId) {
		return LayoutCacheUtil.class.getName() + GROUP + companyId;
	}

	private static String _encodeKey(String companyId, String key) {
		return _encodeGroupName(companyId) + StringPool.POUND + key;
	}

	private static Log _log = LogFactory.getLog(LayoutCacheUtil.class);

	private static GeneralCacheAdministrator _cache = ClusterPool.getCache();

}