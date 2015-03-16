/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.asset.model.BaseDDMStructureClassTypeReader;
import com.liferay.portlet.asset.model.ClassTypeReader;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMStructurePermission;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.permission.JournalPermission;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Raymond Augé
 * @author Sergio González
 */
public class JournalArticleAssetRendererFactory
	extends BaseAssetRendererFactory {

	public static final String TYPE = "content";

	public JournalArticleAssetRendererFactory() {
		setLinkable(true);
		setSupportsClassTypes(true);
	}

	@Override
	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException {

		JournalArticle article =
			JournalArticleLocalServiceUtil.fetchJournalArticle(classPK);

		if (article == null) {
			JournalArticleResource articleResource =
				JournalArticleResourceLocalServiceUtil.getArticleResource(
					classPK);

			if (type == TYPE_LATEST_APPROVED) {
				article = JournalArticleLocalServiceUtil.fetchDisplayArticle(
					articleResource.getGroupId(),
					articleResource.getArticleId());
			}

			if (article == null) {
				article = JournalArticleLocalServiceUtil.getLatestArticle(
					articleResource.getGroupId(),
					articleResource.getArticleId(),
					WorkflowConstants.STATUS_ANY);
			}
		}

		JournalArticleAssetRenderer journalArticleAssetRenderer =
			new JournalArticleAssetRenderer(article);

		journalArticleAssetRenderer.setAssetRendererType(type);

		return journalArticleAssetRenderer;
	}

	@Override
	public AssetRenderer getAssetRenderer(long groupId, String urlTitle)
		throws PortalException {

		JournalArticle article =
			JournalArticleServiceUtil.getDisplayArticleByUrlTitle(
				groupId, urlTitle);

		return new JournalArticleAssetRenderer(article);
	}

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public ClassTypeReader getClassTypeReader() {
		return new BaseDDMStructureClassTypeReader(getClassName());
	}

	@Override
	public String getIconCssClass() {
		return "icon-file-2";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getTypeName(Locale locale, long subtypeId) {
		try {
			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.getStructure(subtypeId);

			return ddmStructure.getName(locale);
		}
		catch (Exception e) {
			return super.getTypeName(locale, subtypeId);
		}
	}

	@Override
	public PortletURL getURLAdd(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL portletURL = liferayPortletResponse.createRenderURL(
			PortletKeys.JOURNAL);

		portletURL.setParameter(
			"mvcPath", "/html/portlet/journal/edit_article.jsp");

		return portletURL;
	}

	@Override
	public PortletURL getURLView(
		LiferayPortletResponse liferayPortletResponse,
		WindowState windowState) {

		LiferayPortletURL liferayPortletURL =
			liferayPortletResponse.createLiferayPortletURL(
				PortletKeys.JOURNAL, PortletRequest.RENDER_PHASE);

		try {
			liferayPortletURL.setWindowState(windowState);
		}
		catch (WindowStateException wse) {
		}

		return liferayPortletURL;
	}

	@Override
	public boolean hasAddPermission(
			PermissionChecker permissionChecker, long groupId, long classTypeId)
		throws Exception {

		if (classTypeId == 0) {
			return false;
		}

		if (!DDMStructurePermission.contains(
				permissionChecker, classTypeId, ActionKeys.VIEW)) {

			return false;
		}

		return JournalPermission.contains(
			permissionChecker, groupId, ActionKeys.ADD_ARTICLE);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return JournalArticlePermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Override
	public boolean isListable(long classPK) {
		JournalArticle article =
			JournalArticleLocalServiceUtil.fetchLatestArticle(
				classPK, WorkflowConstants.STATUS_APPROVED, true);

		if ((article != null) && article.isIndexable()) {
			return true;
		}

		return false;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/history.png";
	}

}