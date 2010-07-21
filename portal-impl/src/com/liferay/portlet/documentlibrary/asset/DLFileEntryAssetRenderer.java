/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 */
public class DLFileEntryAssetRenderer extends BaseAssetRenderer {

	public DLFileEntryAssetRenderer(
		DLFileEntry fileEntry, DLFileVersion fileVersion) {

		_fileEntry = fileEntry;
		_fileVersion = fileVersion;
	}

	public long getClassPK() {
		if (!_fileVersion.isApproved() &&
			(_fileVersion.getVersion() !=
				DLFileEntryConstants.DEFAULT_VERSION)) {

			return _fileVersion.getFileVersionId();
		}
		else {
			return _fileEntry.getFileEntryId();
		}
	}

	public String getDiscussionPath() {
		if (PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED) {
			return "edit_file_entry_discussion";
		}
		else {
			return null;
		}
	}

	public long getGroupId() {
		return _fileEntry.getGroupId();
	}

	public String getSummary() {
		return HtmlUtil.stripHtml(_fileEntry.getDescription());
	}

	public String getTitle() {
		return _fileEntry.getTitle();
	}

	public PortletURL getURLEdit(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL editPortletURL = liferayPortletResponse.createRenderURL(
			PortletKeys.DOCUMENT_LIBRARY);

		editPortletURL.setParameter(
			"struts_action", "/document_library/edit_file_entry");
		editPortletURL.setParameter(
			"groupId", String.valueOf(_fileEntry.getGroupId()));
		editPortletURL.setParameter(
			"folderId", String.valueOf(_fileEntry.getFolderId()));
		editPortletURL.setParameter(
			"name", String.valueOf(_fileEntry.getName()));

		return editPortletURL;
	}

	public PortletURL getURLExport(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL exportPortletURL = liferayPortletResponse.createActionURL();

		exportPortletURL.setParameter(
			"struts_action", "/asset_publisher/get_file");
		exportPortletURL.setParameter(
			"groupId", String.valueOf(_fileEntry.getGroupId()));
		exportPortletURL.setParameter(
			"folderId", String.valueOf(_fileEntry.getFolderId()));
		exportPortletURL.setParameter(
			"title", String.valueOf(_fileEntry.getTitle()));

		return exportPortletURL;
	}

	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathMain() +
			"/document_library/find_file_entry?p_l_id=" +
				themeDisplay.getPlid() + "&fileEntryId=" +
					_fileEntry.getFileEntryId();
	}

	public long getUserId() {
		return _fileEntry.getUserId();
	}

	public String getUuid() {
		return _fileEntry.getUuid();
	}

	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		return DLFileEntryPermission.contains(
			permissionChecker, _fileEntry, ActionKeys.UPDATE);
	}

	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		return DLFileEntryPermission.contains(
			permissionChecker, _fileEntry, ActionKeys.VIEW);
	}

	public boolean isConvertible() {
		return true;
	}

	public boolean isPrintable() {
		return false;
	}

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY, _fileEntry);
			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, _fileVersion);

			return "/html/portlet/document_library/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/clip.png";
	}

	private DLFileEntry _fileEntry;
	private DLFileVersion _fileVersion;

}