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
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalFolderPermission;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Alexander Chow
 */
public class JournalFolderAssetRenderer
	extends BaseAssetRenderer implements TrashRenderer {

	public static final String TYPE = "folder";

	public JournalFolderAssetRenderer(JournalFolder folder) {
		_folder = folder;
	}

	@Override
	public String getClassName() {
		return JournalFolder.class.getName();
	}

	@Override
	public long getClassPK() {
		return _folder.getFolderId();
	}

	@Override
	public Date getDisplayDate() {
		return _folder.getModifiedDate();
	}

	@Override
	public long getGroupId() {
		return _folder.getGroupId();
	}

	@Override
	public String getIconCssClass() throws PortalException {
		if (PropsValues.JOURNAL_FOLDER_ICON_CHECK_COUNT &&
			JournalFolderServiceUtil.getFoldersAndArticlesCount(
				_folder.getGroupId(), _folder.getFolderId()) > 0) {

			return "icon-folder-open";
		}

		return super.getIconCssClass();
	}

	@Override
	public String getIconPath(ThemeDisplay themeDisplay) {
		try {
			if (PropsValues.JOURNAL_FOLDER_ICON_CHECK_COUNT &&
				JournalFolderServiceUtil.getFoldersAndArticlesCount(
					_folder.getGroupId(), _folder.getFolderId(),
					WorkflowConstants.STATUS_APPROVED) > 0) {

				return themeDisplay.getPathThemeImages() +
					"/common/folder_full_document.png";
			}
		}
		catch (Exception e) {
		}

		return themeDisplay.getPathThemeImages() + "/common/folder_empty.png";
	}

	@Override
	public String getPortletId() {
		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		return assetRendererFactory.getPortletId();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return _folder.getDescription();
	}

	@Override
	public String getThumbnailPath(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!PropsValues.JOURNAL_FOLDER_ICON_CHECK_COUNT) {
			return themeDisplay.getPathThemeImages() +
				"/file_system/large/folder_empty_article.png";
		}

		int articlesCount = JournalArticleServiceUtil.getArticlesCount(
			_folder.getGroupId(), _folder.getFolderId());
		int foldersCount = JournalFolderServiceUtil.getFoldersCount(
			_folder.getGroupId(), _folder.getFolderId());

		if ((articlesCount > 0) || (foldersCount > 0)) {
			return themeDisplay.getPathThemeImages() +
				"/file_system/large/folder_full_article.png";
		}

		return themeDisplay.getPathThemeImages() +
			"/file_system/large/folder_empty_article.png";
	}

	@Override
	public String getTitle(Locale locale) {
		return TrashUtil.getOriginalTitle(_folder.getName());
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			getControlPanelPlid(liferayPortletRequest), PortletKeys.JOURNAL,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcPath", "/html/portlet/journal/edit_folder.jsp");
		portletURL.setParameter(
			"folderId", String.valueOf(_folder.getFolderId()));

		return portletURL;
	}

	@Override
	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		PortletURL portletURL = assetRendererFactory.getURLView(
			liferayPortletResponse, windowState);

		portletURL.setParameter(
			"mvcPath", "/html/portlet/journal/asset/folder_full_content.jsp");
		portletURL.setParameter(
			"folderId", String.valueOf(_folder.getFolderId()));
		portletURL.setWindowState(windowState);

		return portletURL;
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		try {
			PortletURL viewInContextURL = getURLView(
				liferayPortletResponse, WindowState.MAXIMIZED);

			return viewInContextURL.toString();
		}
		catch (Exception e) {
			return noSuchEntryRedirect;
		}
	}

	@Override
	public long getUserId() {
		return _folder.getUserId();
	}

	@Override
	public String getUserName() {
		return _folder.getUserName();
	}

	@Override
	public String getUuid() {
		return _folder.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return JournalFolderPermission.contains(
			permissionChecker, _folder, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return JournalFolderPermission.contains(
			permissionChecker, _folder, ActionKeys.VIEW);
	}

	@Override
	public String render(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			return "/html/portlet/journal/asset/folder_" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	private final JournalFolder _folder;

}