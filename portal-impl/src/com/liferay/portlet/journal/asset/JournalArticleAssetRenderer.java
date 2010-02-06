/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.asset;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="JournalArticleAssetRenderer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 */
public class JournalArticleAssetRenderer extends BaseAssetRenderer {

	public JournalArticleAssetRenderer(JournalArticle article) {
		_article = article;
	}

	public String[] getAvailableLocales() {
		return _article.getAvailableLocales();
	}

	public long getClassPK() {
		return _article.getResourcePrimKey();
	}

	public String getDiscussionPath() {
		if (PropsValues.JOURNAL_ARTICLE_COMMENTS_ENABLED) {
			return "edit_article_discussion";
		}
		else {
			return null;
		}
	}

	public long getGroupId() {
		return _article.getGroupId();
	}

	public String getSummary() {
		return HtmlUtil.stripHtml(_article.getContent());
	}

	public String getTitle() {
			return _article.getTitle();
	}

	public PortletURL getURLEdit(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL editPortletURL = null;

		if (JournalPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_ARTICLE)) {

			editPortletURL = liferayPortletResponse.createRenderURL(
				PortletKeys.JOURNAL);

			editPortletURL.setParameter(
				"struts_action", "/journal/edit_article");
			editPortletURL.setParameter(
				"groupId", String.valueOf(_article.getGroupId()));
			editPortletURL.setParameter(
				"articleId", _article.getArticleId());
			editPortletURL.setParameter(
				"version", String.valueOf(_article.getVersion()));
		}

		return editPortletURL;
	}

	public PortletURL getURLExport(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL exportPortletURL = liferayPortletResponse.createActionURL();

		exportPortletURL.setParameter(
			"struts_action", "/asset_publisher/export_journal_article");
		exportPortletURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		exportPortletURL.setParameter("articleId", _article.getArticleId());

		return exportPortletURL;
	}

	public String getUrlTitle() {
		return _article.getUrlTitle();
	}

	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String languageId = LanguageUtil.getLanguageId(liferayPortletRequest);

		JournalArticleDisplay articleDisplay =
			JournalContentUtil.getDisplay(
				_article.getGroupId(), _article.getArticleId(),
				null, null, languageId, themeDisplay);

		String viewURL = StringPool.BLANK;

		if (articleDisplay != null) {

			PortletURL viewPortletURL =
				liferayPortletResponse.createRenderURL();

			viewPortletURL.setParameter(
				"struts_action", "/asset_publisher/view_content");
			viewPortletURL.setParameter("urlTitle", _article.getUrlTitle());
			viewPortletURL.setParameter(
				"type", JournalArticleAssetRendererFactory.TYPE);

			viewURL = viewPortletURL.toString();
		}

		return viewURL;
	}

	public long getUserId() {
		return _article.getUserId();
	}

	public String getViewInContextMessage() {
		return "view";
	}

	public boolean isConvertible() {
		return true;
	}

	public boolean isLocalizable() {
		return true;
	}

	public boolean isPrintable() {
		return true;
	}

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			renderRequest.setAttribute(WebKeys.JOURNAL_ARTICLE, _article);

			return "/html/portlet/journal/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	private JournalArticle _article;

}