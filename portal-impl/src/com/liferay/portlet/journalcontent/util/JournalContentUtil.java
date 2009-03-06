/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;

import org.apache.commons.lang.time.StopWatch;

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

	public static String LANGUAGE_SEPARATOR = "_LANGUAGE_";

	public static String PAGE_SEPARATOR = "_PAGE_";

	public static String TEMPLATE_SEPARATOR = "_TEMPLATE_";

	public static String VIEW_MODE_SEPARATOR = "_VIEW_MODE_";

	public static void clearCache() {
		cache.removeAll();
	}

	public static void clearCache(
		long groupId, String articleId, String templateId) {

		clearCache();
	}

	public static String getContent(
		long groupId, String articleId, String viewMode, String languageId,
		String xmlRequest) {

		return getContent(
			groupId, articleId, null, viewMode, languageId, null, xmlRequest);
	}

	public static String getContent(
		long groupId, String articleId, String viewMode, String languageId,
		ThemeDisplay themeDisplay) {

		return getContent(
			groupId, articleId, null, viewMode, languageId, themeDisplay);
	}

	public static String getContent(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, String xmlRequest) {

		return getContent(
			groupId, articleId, templateId, viewMode, languageId, null,
			xmlRequest);
	}

	public static String getContent(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, ThemeDisplay themeDisplay) {

		return getContent(
			groupId, articleId, templateId, viewMode, languageId, themeDisplay,
			null);
	}

	public static String getContent(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, ThemeDisplay themeDisplay, String xmlRequest) {

		JournalArticleDisplay articleDisplay = getDisplay(
			groupId, articleId, templateId, viewMode, languageId, themeDisplay,
			1, xmlRequest);

		if (articleDisplay != null) {
			return articleDisplay.getContent();
		}
		else {
			return null;
		}
	}

	public static JournalArticleDisplay getDisplay(
		long groupId, String articleId, String viewMode, String languageId,
		String xmlRequest) {

		return getDisplay(
			groupId, articleId, null, viewMode, languageId, null, 1,
			xmlRequest);
	}

	public static JournalArticleDisplay getDisplay(
		long groupId, String articleId, String viewMode, String languageId,
		ThemeDisplay themeDisplay) {

		return getDisplay(
			groupId, articleId, viewMode, languageId, themeDisplay, 1);
	}

	public static JournalArticleDisplay getDisplay(
		long groupId, String articleId, String viewMode, String languageId,
		ThemeDisplay themeDisplay, int page) {

		return getDisplay(
			groupId, articleId, null, viewMode, languageId, themeDisplay, page,
			null);
	}

	public static JournalArticleDisplay getDisplay(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, String xmlRequest) {

		return getDisplay(
			groupId, articleId, templateId, viewMode, languageId, null, 1,
			xmlRequest);
	}

	public static JournalArticleDisplay getDisplay(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, ThemeDisplay themeDisplay) {

		return getDisplay(
			groupId, articleId, templateId, viewMode, languageId, themeDisplay,
			1, null);
	}

	public static JournalArticleDisplay getDisplay(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, ThemeDisplay themeDisplay, int page,
		String xmlRequest) {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		articleId = GetterUtil.getString(articleId).toUpperCase();
		templateId = GetterUtil.getString(templateId).toUpperCase();

		String key = encodeKey(
			groupId, articleId, templateId, viewMode, languageId, page);

		JournalArticleDisplay articleDisplay =
			(JournalArticleDisplay)MultiVMPoolUtil.get(cache, key);

		if (_log.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();

			sb.append("Lifecycle {action=");
			sb.append(themeDisplay.isLifecycleAction());
			sb.append(", render=");
			sb.append(themeDisplay.isLifecycleRender());
			sb.append(", resource=");
			sb.append(themeDisplay.isLifecycleResource());
			sb.append("}");

			_log.debug(sb.toString());
		}

		if ((articleDisplay == null) || (themeDisplay.isLifecycleAction()) ||
			(themeDisplay.isLifecycleResource())) {

			if (_log.isDebugEnabled()) {
				_log.debug("Not cached");
			}

			articleDisplay = getArticleDisplay(
				groupId, articleId, templateId, viewMode, languageId, page,
				xmlRequest, themeDisplay);

			if ((articleDisplay != null) && (articleDisplay.isCacheable()) &&
				(!themeDisplay.isLifecycleAction()) &&
				(!themeDisplay.isLifecycleResource())) {

				MultiVMPoolUtil.put(cache, key, articleDisplay);
			}
		}

		try {
			if ((PropsValues.JOURNAL_ARTICLE_VIEW_PERMISSION_CHECK_ENABLED) &&
				(articleDisplay != null) && (themeDisplay != null) &&
				(!JournalArticlePermission.contains(
					themeDisplay.getPermissionChecker(), groupId, articleId,
					ActionKeys.VIEW))) {

				articleDisplay = null;
			}
		}
		catch (Exception e) {
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"getDisplay for {" + groupId + ", " + articleId + ", " +
					templateId + ", " + viewMode + ", " + languageId + ", " +
						page + "} takes " + stopWatch.getTime() + " ms");
		}

		return articleDisplay;
	}

	protected static String encodeKey(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, int page) {

		StringBuilder sb = new StringBuilder();

		sb.append(CACHE_NAME);
		sb.append(StringPool.POUND);
		sb.append(groupId);
		sb.append(ARTICLE_SEPARATOR);
		sb.append(articleId);
		sb.append(TEMPLATE_SEPARATOR);
		sb.append(templateId);

		if (Validator.isNotNull(viewMode)) {
			sb.append(VIEW_MODE_SEPARATOR);
			sb.append(viewMode);
		}

		if (Validator.isNotNull(languageId)) {
			sb.append(LANGUAGE_SEPARATOR);
			sb.append(languageId);
		}

		if (page > 0) {
			sb.append(PAGE_SEPARATOR);
			sb.append(page);
		}

		return sb.toString();
	}

	protected static JournalArticleDisplay getArticleDisplay(
		long groupId, String articleId, String templateId, String viewMode,
		String languageId, int page, String xmlRequest,
		ThemeDisplay themeDisplay) {

		try {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Get article display {" + groupId + ", " + articleId +
						", " + templateId + "}");
			}

			return JournalArticleLocalServiceUtil.getArticleDisplay(
				groupId, articleId, templateId, viewMode, languageId, page,
				xmlRequest, themeDisplay);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get display for " + groupId + " " +
						articleId + " " + languageId);
			}

			return null;
		}
	}

	protected static PortalCache cache = MultiVMPoolUtil.getCache(CACHE_NAME);

	private static Log _log = LogFactoryUtil.getLog(JournalContentUtil.class);

}