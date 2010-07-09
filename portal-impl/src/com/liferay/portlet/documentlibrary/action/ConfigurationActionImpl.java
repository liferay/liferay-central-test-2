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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.kernel.portlet.BaseConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Jorge Ferrer
 */
public class ConfigurationActionImpl extends BaseConfigurationAction {

	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (!cmd.equals(Constants.UPDATE)) {
			return;
		}

		long rootFolderId = ParamUtil.getLong(actionRequest, "rootFolderId");

		boolean showFoldersSearch = ParamUtil.getBoolean(
			actionRequest, "showFoldersSearch");
		boolean showSubfolders = ParamUtil.getBoolean(
			actionRequest, "showSubfolders");
		int foldersPerPage = ParamUtil.getInteger(
			actionRequest, "foldersPerPage");
		String folderColumns = ParamUtil.getString(
			actionRequest, "folderColumns");

		int fileEntriesPerPage = ParamUtil.getInteger(
			actionRequest, "fileEntriesPerPage");
		String fileEntryColumns = ParamUtil.getString(
			actionRequest, "fileEntryColumns");

		boolean enableCommentRatings = ParamUtil.getBoolean(
			actionRequest, "enableCommentRatings");

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletResource);

		if (rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			try {
				DLFolderLocalServiceUtil.getFolder(rootFolderId);
			}
			catch (NoSuchFolderException e) {
				SessionErrors.add(actionRequest, "rootFolderIdInvalid");
			}
		}

		preferences.setValue("rootFolderId", String.valueOf(rootFolderId));

		preferences.setValue(
			"showFoldersSearch", String.valueOf(showFoldersSearch));
		preferences.setValue("showSubfolders", String.valueOf(showSubfolders));
		preferences.setValue("foldersPerPage", String.valueOf(foldersPerPage));
		preferences.setValue("folderColumns", folderColumns);

		preferences.setValue(
			"fileEntriesPerPage", String.valueOf(fileEntriesPerPage));
		preferences.setValue("fileEntryColumns", fileEntryColumns);

		preferences.setValue(
			"enable-comment-ratings", String.valueOf(enableCommentRatings));

		if (SessionErrors.isEmpty(actionRequest)) {
			preferences.store();

			SessionMessages.add(
				actionRequest, portletConfig.getPortletName() + ".doConfigure");
		}
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/portlet/document_library/configuration.jsp";
	}

}