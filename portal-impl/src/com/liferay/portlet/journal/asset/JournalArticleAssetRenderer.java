/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.journal.asset;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 */
public class JournalArticleAssetRenderer extends BaseAssetRenderer {

	public JournalArticleAssetRenderer(JournalArticle article) {
		_article = article;
	}

	public String[] getAvailableLocales() {
		return _article.getAvailableLocales();
	}

	public long getClassPK() {
		if ((_article.isDraft() || _article.isPending()) &&
			(_article.getVersion() !=
				JournalArticleConstants.DEFAULT_VERSION)) {

			return _article.getPrimaryKey();
		}
		else {
			return _article.getResourcePrimKey();
		}
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

	public String getSummary(Locale locale) {
		return HtmlUtil.stripHtml(_article.getContent());
	}

	public String getTitle(Locale locale) {
		return _article.getTitle(locale);
	}

	public PortletURL getURLEdit(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL portletURL = liferayPortletResponse.createRenderURL(
			PortletKeys.JOURNAL);

		portletURL.setParameter("struts_action", "/journal/edit_article");
		portletURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		portletURL.setParameter("articleId", _article.getArticleId());
		portletURL.setParameter(
			"version", String.valueOf(_article.getVersion()));

		return portletURL;
	}

	public PortletURL getURLExport(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(
			"struts_action", "/asset_publisher/export_journal_article");
		portletURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		portletURL.setParameter("articleId", _article.getArticleId());

		return portletURL;
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

		long groupId = _article.getGroupId();
		Group group;

		if (themeDisplay.getScopeGroup().getGroupId() == groupId) {
			group = themeDisplay.getScopeGroup();
		}
		else {
			group = GroupLocalServiceUtil.getGroup(groupId);
		}

		return PortalUtil.getGroupFriendlyURL(group, false, themeDisplay) +
			JournalArticleConstants.CANONICAL_URL_SEPARATOR +
			_article.getUrlTitle();
	}

	public long getUserId() {
		return _article.getUserId();
	}

	public String getUuid() {
		return _article.getUuid();
	}

	public String getViewInContextMessage() {
		return "view";
	}

	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		return JournalArticlePermission.contains(
			permissionChecker,_article, ActionKeys.UPDATE);
	}

	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return JournalArticlePermission.contains(
			permissionChecker,_article, ActionKeys.VIEW);
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

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/history.png";
	}

	private JournalArticle _article;

}