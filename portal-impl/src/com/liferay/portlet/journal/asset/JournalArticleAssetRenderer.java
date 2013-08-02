/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Sergio González
 * @author Raymond Augé
 */
public class JournalArticleAssetRenderer extends BaseAssetRenderer {

	public JournalArticleAssetRenderer(JournalArticle article) {
		_article = article;
	}

	public JournalArticle getArticle() {
		return _article;
	}

	@Override
	public String[] getAvailableLocales() {
		return _article.getAvailableLocales();
	}

	@Override
	public long getClassPK() {
		if ((_article.isDraft() || _article.isPending()) &&
			(_article.getVersion() !=
				JournalArticleConstants.VERSION_DEFAULT)) {

			return _article.getPrimaryKey();
		}
		else {
			return _article.getResourcePrimKey();
		}
	}

	@Override
	public String getDiscussionPath() {
		if (PropsValues.JOURNAL_ARTICLE_COMMENTS_ENABLED) {
			return "edit_article_discussion";
		}
		else {
			return null;
		}
	}

	@Override
	public long getGroupId() {
		return _article.getGroupId();
	}

	@Override
	public String getSummary(Locale locale) {
		String summary = _article.getDescription(locale);

		if (Validator.isNull(summary)) {
			try {
				JournalArticleDisplay articleDisplay =
					JournalArticleLocalServiceUtil.getArticleDisplay(
						_article, null, null,
						LanguageUtil.getLanguageId(locale), 1, null, null);

				summary = StringUtil.shorten(
					HtmlUtil.stripHtml(articleDisplay.getContent()), 200);
			}
			catch (Exception e) {
			}
		}

		return summary;
	}

	@Override
	public String getTitle(Locale locale) {
		return _article.getTitle(locale);
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			getControlPanelPlid(liferayPortletRequest), PortletKeys.JOURNAL,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/journal/edit_article");
		portletURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		portletURL.setParameter("articleId", _article.getArticleId());
		portletURL.setParameter(
			"version", String.valueOf(_article.getVersion()));

		return portletURL;
	}

	@Override
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

	@Override
	public String getUrlTitle() {
		return _article.getUrlTitle();
	}

	@Override
	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String portletId = (String)liferayPortletRequest.getAttribute(
			WebKeys.PORTLET_ID);

		PortletPreferences portletSetup =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portletId);

		String linkToLayoutUuid = GetterUtil.getString(
			portletSetup.getValue("portletSetupLinkToLayoutUuid", null));

		if (Validator.isNotNull(_article.getLayoutUuid()) &&
			Validator.isNull(linkToLayoutUuid)) {

			Group group = themeDisplay.getScopeGroup();

			if (group.getGroupId() != _article.getGroupId()) {
				group = GroupLocalServiceUtil.getGroup(_article.getGroupId());
			}

			String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(
				group, layout.isPrivateLayout(), themeDisplay);

			return groupFriendlyURL.concat(
				JournalArticleConstants.CANONICAL_URL_SEPARATOR).concat(
					_article.getUrlTitle());
		}

		List<Long> hitLayoutIds =
			JournalContentSearchLocalServiceUtil.getLayoutIds(
				layout.getGroupId(), layout.isPrivateLayout(),
				_article.getArticleId());

		if (!hitLayoutIds.isEmpty()) {
			Long hitLayoutId = hitLayoutIds.get(0);

			Layout hitLayout = LayoutLocalServiceUtil.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				hitLayoutId.longValue());

			return PortalUtil.getLayoutURL(hitLayout, themeDisplay);
		}

		return noSuchEntryRedirect;
	}

	@Override
	public long getUserId() {
		return _article.getUserId();
	}

	@Override
	public String getUserName() {
		return _article.getUserName();
	}

	@Override
	public String getUuid() {
		return _article.getUuid();
	}

	@Override
	public String getViewInContextMessage() {
		return "view";
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		return JournalArticlePermission.contains(
			permissionChecker, _article, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return JournalArticlePermission.contains(
			permissionChecker, _article, ActionKeys.VIEW);
	}

	@Override
	public boolean isConvertible() {
		return true;
	}

	@Override
	public boolean isDisplayable() {
		Date now = new Date();

		Date displayDate = _article.getDisplayDate();

		if ((displayDate != null) && displayDate.after(now)) {
			return false;
		}

		Date expirationDate = _article.getExpirationDate();

		if ((expirationDate != null) && expirationDate.before(now)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isLocalizable() {
		return true;
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	@Override
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

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/history.png";
	}

	private JournalArticle _article;

}