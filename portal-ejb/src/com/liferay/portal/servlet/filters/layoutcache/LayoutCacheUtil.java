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

package com.liferay.portal.servlet.filters.layoutcache;

import com.liferay.portal.util.ClusterPool;
import com.liferay.portal.util.PortalInstances;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LayoutCacheUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class LayoutCacheUtil {

	public static String[] GROUP_NAME_ARRAY;

	public static String GROUP = "_GROUP_";

	public static String LANGUAGE = "_LANGUAGE_";

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

		_log.info("Cleared layout cache for " + companyId);
	}

	public static LayoutCacheResponseData getLayoutCacheResponseData(
		String companyId, String plid, String languageId) {

		LayoutCacheResponseData data = null;

		if (Validator.isNull(plid)) {
			return null;
		}

		plid = plid.trim().toUpperCase();

		String key = _encodeKey(companyId, plid + LANGUAGE + languageId);

		try {
			data = (LayoutCacheResponseData)_cache.getFromCache(key);
		}
		catch (NeedsRefreshException nre) {
			_log.warn("Layout " + plid + " is not cached");
		}
		finally {
			if (data == null) {
				_cache.cancelUpdate(key);
			}
		}

		return data;
	}

	public static boolean isCached(
		String companyId, String plid, String languageId) {

		byte[] byteArray = null;

		if (Validator.isNull(plid)) {
			return false;
		}

		plid = plid.trim().toUpperCase();

		String key = _encodeKey(companyId, plid + LANGUAGE + languageId);

		try {
			byteArray = (byte[])_cache.getFromCache(key);
		}
		catch (NeedsRefreshException nre) {
			return false;
		}
		finally {
			if (byteArray == null) {
				_cache.cancelUpdate(key);

				return false;
			}
		}

		return true;
	}

	public static void putLayoutCacheResponseData(
		String companyId, String plid, String languageId,
		LayoutCacheResponseData data) {

		if (data != null) {
			plid = plid.trim().toUpperCase();

			String key = _encodeKey(companyId, plid + LANGUAGE + languageId);

			_cache.putInCache(key, data, GROUP_NAME_ARRAY);
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