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

package com.liferay.portlet.bookmarks.action;

import com.liferay.portal.kernel.portlet.BaseConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Sergio González
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

		int entriesPerPage = ParamUtil.getInteger(
			actionRequest, "entriesPerPage");
		String entryColumns = ParamUtil.getString(
			actionRequest, "entryColumns");

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletResource);

		if (rootFolderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			try {
				BookmarksFolderLocalServiceUtil.getFolder(rootFolderId);
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
			"entriesPerPage", String.valueOf(entriesPerPage));
		preferences.setValue("entryColumns", entryColumns);

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

		return "/html/portlet/bookmarks/configuration.jsp";
	}

}