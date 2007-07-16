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
import com.liferay.util.CollectionFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import java.util.Iterator;
import java.util.Map;

import net.sf.ehcache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JournalContentUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Michael Young
 *
 */
public class JournalContentUtil {

	public static final String CACHE_NAME = JournalContentUtil.class.getName();

	public static String ARTICLE_SEPARATOR = "_ARTICLE_";

	public static String TEMPLATE_SEPARATOR = "_TEMPLATE_";

	public static String LANGUAGE_SEPARATOR = "_LANGUAGE_";

	public static void clearCache() {
		_cache.removeAll();
	}

	public static void clearCache(
		long groupId, String articleId, String templateId) {

		articleId = GetterUtil.getString(articleId).toUpperCase();
		templateId = GetterUtil.getString(templateId).toUpperCase();

		String groupKey = _encodeKey(groupId, articleId, templateId);

		if (!_groups.containsKey(groupKey)) {
			return;
		}
		
		Map groupKeys = (Map)_groups.get(groupKey);
		
		Iterator keys = groupKeys.values().iterator();

		while(keys.hasNext()) {
			String key = (String)keys.next();
			
			// The functionality here pretty much mimics OSCache groups. It is 
			// not necessary to remove the keys in dependent groups because they
			// will be cleared when the group itself is cleared, resulting in a 
			// performance boost.

			_cache.remove(key);
		}
		
		groupKeys.clear();
	}

	public static String getContent(
		long groupId, String articleId, String languageId,
		ThemeDisplay themeDisplay) {

		return getContent(groupId, articleId, null, languageId, themeDisplay);
	}

	public static String getContent(
		long groupId, String articleId, String templateId, String languageId,
		ThemeDisplay themeDisplay) {

		return getContent(
			groupId, articleId, null, languageId, themeDisplay, false);
	}

	public static String getContent(
		long groupId, String articleId, String templateId, String languageId,
		ThemeDisplay themeDisplay, boolean disableCaching) {

		articleId = GetterUtil.getString(articleId).toUpperCase();
		templateId = GetterUtil.getString(templateId).toUpperCase();

		if (disableCaching) {
			return _getContent(
				groupId, articleId, templateId, languageId, themeDisplay);
		}

		String content = null;

		String groupKey = _encodeKey(groupId, articleId, templateId);

		String key = _encodeKey(groupId, articleId, templateId, languageId);

		content = (String)ClusterPool.get(_cache, key);

		if (content == null) {
			try {
				content = JournalArticleLocalServiceUtil.getArticleContent(
					groupId, articleId, templateId, languageId, themeDisplay);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get content for " + groupId + " " +
							articleId + " " + languageId);
				}
			}

			if (content != null) {
				_updateGroup(groupKey, key);

				ClusterPool.put(_cache, key, content);
			}
		}

		return content;
	}

	private static String _encodeKey(
		long groupId, String articleId, String templateId) {

		return _encodeKey(groupId, articleId, templateId, null);
	}

	private static String _encodeKey(
		long groupId, String articleId, String templateId, String languageId) {

		StringMaker sm = new StringMaker();

		sm.append(CACHE_NAME);
		sm.append(StringPool.POUND);
		sm.append(groupId);
		sm.append(ARTICLE_SEPARATOR);
		sm.append(articleId);
		sm.append(TEMPLATE_SEPARATOR);
		sm.append(templateId);

		if (Validator.isNotNull(languageId)) {
			sm.append(LANGUAGE_SEPARATOR);
			sm.append(languageId);
		}

		return sm.toString();
	}

	private static String _getContent(
		long groupId, String articleId, String templateId, String languageId,
		ThemeDisplay themeDisplay) {

		articleId = GetterUtil.getString(articleId).toUpperCase();
		templateId = GetterUtil.getString(templateId).toUpperCase();

		try {
			return JournalArticleLocalServiceUtil.getArticleContent(
				groupId, articleId, templateId, languageId, themeDisplay);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get content for " + groupId + " " +
						articleId + " " + languageId);
			}

			return null;
		}
	}

	private static void _updateGroup(String groupKey, String key) {
		Map groupKeys = null;
		
		if (_groups.containsKey(groupKey)) {
			groupKeys = (Map)_groups.get(groupKey);
		}
		else {
			groupKeys = CollectionFactory.getSyncHashMap();
			
			_groups.put(groupKey, groupKeys);
		}
		
		groupKeys.put(key, key);
	}

	private static Log _log = LogFactory.getLog(JournalContentUtil.class);

	private static Cache _cache = ClusterPool.getCache(CACHE_NAME);
	
	private static Map _groups = CollectionFactory.getSyncHashMap();

}