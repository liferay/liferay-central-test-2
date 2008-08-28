/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

/**
 * <a href="JournalContentUtil_IW.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalContentUtil_IW {
	public static JournalContentUtil_IW getInstance() {
		return _instance;
	}

	public void clearCache() {
		JournalContentUtil.clearCache();
	}

	public void clearCache(long groupId, java.lang.String articleId,
		java.lang.String templateId) {
		JournalContentUtil.clearCache(groupId, articleId, templateId);
	}

	public java.lang.String getContent(long groupId,
		java.lang.String articleId, java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return JournalContentUtil.getContent(groupId, articleId, languageId,
			themeDisplay);
	}

	public java.lang.String getContent(long groupId,
		java.lang.String articleId, java.lang.String templateId,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return JournalContentUtil.getContent(groupId, articleId, templateId,
			languageId, themeDisplay);
	}

	public java.lang.String getContent(long groupId,
		java.lang.String articleId, java.lang.String templateId,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		java.lang.String xmlRequest) {
		return JournalContentUtil.getContent(groupId, articleId, templateId,
			languageId, themeDisplay, xmlRequest);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getDisplay(
		long groupId, java.lang.String articleId, java.lang.String templateId,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay) {
		return JournalContentUtil.getDisplay(groupId, articleId, templateId,
			languageId, themeDisplay);
	}

	public com.liferay.portlet.journal.model.JournalArticleDisplay getDisplay(
		long groupId, java.lang.String articleId, java.lang.String templateId,
		java.lang.String languageId,
		com.liferay.portal.theme.ThemeDisplay themeDisplay, int page,
		java.lang.String xmlRequest) {
		return JournalContentUtil.getDisplay(groupId, articleId, templateId,
			languageId, themeDisplay, page, xmlRequest);
	}

	private JournalContentUtil_IW() {
	}

	private static JournalContentUtil_IW _instance = new JournalContentUtil_IW();
}