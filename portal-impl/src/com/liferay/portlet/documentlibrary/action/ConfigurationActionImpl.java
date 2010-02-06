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
 * <a href="ConfigurationActionImpl.java.html"><b><i>View Source</i></b></a>
 *
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

		boolean showBreadcrumbs = ParamUtil.getBoolean(
			actionRequest, "showBreadcrumbs");
		boolean showFoldersSearch = ParamUtil.getBoolean(
			actionRequest, "showFoldersSearch");
		boolean showSubfolders = ParamUtil.getBoolean(
			actionRequest, "showSubfolders");
		int foldersPerPage = ParamUtil.getInteger(
			actionRequest, "foldersPerPage");
		String folderColumns = ParamUtil.getString(
			actionRequest, "folderColumns");

		boolean showFileEntriesSearch = ParamUtil.getBoolean(
			actionRequest, "showFileEntriesSearch");
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
			"showBreadcrumbs", String.valueOf(showBreadcrumbs));
		preferences.setValue(
			"showFoldersSearch", String.valueOf(showFoldersSearch));
		preferences.setValue("showSubfolders", String.valueOf(showSubfolders));
		preferences.setValue("foldersPerPage", String.valueOf(foldersPerPage));
		preferences.setValue("folderColumns", folderColumns);

		preferences.setValue(
			"showFileEntriesSearch", String.valueOf(showFileEntriesSearch));
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