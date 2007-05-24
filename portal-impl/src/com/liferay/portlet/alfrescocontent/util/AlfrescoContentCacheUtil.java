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

package com.liferay.portlet.alfrescocontent.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.ClusterPool;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.CachePolicy;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="AlfrescoContentCacheUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jerry Niu
 *
 */
public class AlfrescoContentCacheUtil {

	public static final String GROUP_NAME =
		AlfrescoContentCacheUtil.class.getName();

	public static final String[] GROUP_NAME_ARRAY = new String[]{GROUP_NAME};

	public static final long REFRESH_TIME = GetterUtil.getLong(
		PropsUtil.ALFRESCO_CONTENT_CACHE_REFRESH_TIME);

	public static void clearCache() {
		_cache.flushGroup(GROUP_NAME);
	}

	public static String getContent(
			String userId, String password, String uuid, String path,
			boolean maximizeLinks, RenderResponse res)
		throws Exception {

		String content = null;

		if (Validator.isNull(uuid)) {
			return null;
		}

		String key = _encodeKey(uuid);

		try {
			content = (String) _cache.getFromCache(key);
		}
		catch (NeedsRefreshException nre) {
			try {
				content = AlfrescoContentUtil.getContent(
					userId, password, uuid, path, maximizeLinks, res);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e.getMessage());
				}
			}

			if (content != null) {
				_cache.putInCache(
					key, content, GROUP_NAME_ARRAY,
					new CachePolicy(REFRESH_TIME));
			}
		}
		finally {
			if (content == null) {
				_cache.cancelUpdate(key);
			}
		}

		return content;
	}

	private static String _encodeKey(String key) {
		return GROUP_NAME + StringPool.POUND + key;
	}

	private static Log _log = LogFactory.getLog(AlfrescoContentCacheUtil.class);

	private static GeneralCacheAdministrator _cache = ClusterPool.getCache();

}