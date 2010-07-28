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
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;

import javax.portlet.PortletURL;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Raymond Augé
 */
public class DLFileEntryAssetRendererFactory extends BaseAssetRendererFactory {

	public static final String CLASS_NAME = DLFileEntry.class.getName();

	public static final String TYPE = "document";

	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = null;
		DLFileVersion fileVersion = null;

		try {
			fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(classPK);

			if (type == TYPE_LATEST) {
				fileVersion = fileEntry.getLatestFileVersion();
			}
			else {
				fileVersion = fileEntry.getFileVersion();
			}
		}
		catch (NoSuchFileEntryException nsfee) {
			fileVersion = DLFileVersionLocalServiceUtil.getFileVersion(classPK);
			fileEntry = fileVersion.getFileEntry();
		}

		return new DLFileEntryAssetRenderer(fileEntry, fileVersion);
	}

	public String getClassName() {
		return CLASS_NAME;
	}

	public String getType() {
		return TYPE;
	}

	public PortletURL getURLAdd(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL addAssetURL = null;

		if (DLPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_DOCUMENT)) {

			addAssetURL = liferayPortletResponse.createRenderURL(
				PortletKeys.DOCUMENT_LIBRARY);

			addAssetURL.setParameter(
				"struts_action", "/document_library/edit_file_entry");
			addAssetURL.setParameter(
				"groupId", String.valueOf(themeDisplay.getScopeGroupId()));
			addAssetURL.setParameter(
				"folderId",
				String.valueOf(
					AssetPublisherUtil.getRecentFolderId(
						liferayPortletRequest, CLASS_NAME)));
			addAssetURL.setParameter("uploader", "classic");
		}

		return addAssetURL;
	}

	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return DLFileEntryPermission.contains(
			permissionChecker, classPK, actionId);
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/clip.png";
	}

}