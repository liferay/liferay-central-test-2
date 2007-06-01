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

package com.liferay.portlet.journalcontent.util;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.ClusterPool;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.util.GetterUtil;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JournalContentUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 *
 */
public class JournalContentUtil {

	public static final String GROUP_NAME = JournalContentUtil.class.getName();

	public static final String[] GROUP_NAME_ARRAY = new String[] {GROUP_NAME};

	public static String ARTICLE_SEPARATOR = "_ARTICLE_";

	public static String TEMPLATE_SEPARATOR = "_TEMPLATE_";

	public static String LANGUAGE_SEPARATOR = "_LANGUAGE_";

	public static void clearCache() {
		_cache.flushGroup(GROUP_NAME);
	}

	public static String getContent(
		long groupId, String articleId, String languageId,
		ThemeDisplay themeDisplay) {

		return getContent(groupId, articleId, null, languageId, themeDisplay);
	}

	public static String getContent(
		long groupId, String articleId, String templateId, String languageId,
		ThemeDisplay themeDisplay) {

		String content = null;

		articleId = GetterUtil.getString(articleId).toUpperCase();
		templateId = GetterUtil.getString(templateId).toUpperCase();

		String key = _encodeKey(groupId, articleId, templateId, languageId);

		try {
			content = (String)_cache.getFromCache(key, _REFRESH_TIME);
		}
		catch (NeedsRefreshException nre) {
			try {
				content = JournalArticleLocalServiceUtil.getArticleContent(
					groupId, articleId, templateId, languageId, themeDisplay);
			}
			catch (Exception e) {
				_log.error(
					"Uanble to get content for " + groupId + " " + articleId +
						" " + languageId,
					e);
			}

			if (content != null) {
				_cache.putInCache(key, content, GROUP_NAME_ARRAY);
			}
		}
		finally {
			if (content == null) {
				_cache.cancelUpdate(key);
			}
		}

		return content;
	}

	private static String _encodeKey(
		long groupId, String articleId, String templateId, String languageId) {

		StringMaker sm = new StringMaker();

		sm.append(GROUP_NAME);
		sm.append(StringPool.POUND);
		sm.append(groupId);
		sm.append(ARTICLE_SEPARATOR);
		sm.append(articleId);
		sm.append(TEMPLATE_SEPARATOR);
		sm.append(templateId);
		sm.append(LANGUAGE_SEPARATOR);
		sm.append(languageId);

		return sm.toString();
	}

	private static final int _REFRESH_TIME = 3600;

	private static Log _log = LogFactory.getLog(JournalContentUtil.class);

	private static GeneralCacheAdministrator _cache = ClusterPool.getCache();

}