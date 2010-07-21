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

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static void getFileEntry(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(
			request, "groupId", themeDisplay.getScopeGroupId());
		long folderId = ParamUtil.getLong(request, "folderId");
		long newFolderId = ParamUtil.getLong(request, "newFolderId");
		String name = ParamUtil.getString(request, "name");

		DLFileEntry fileEntry = null;

		if (Validator.isNotNull(name)) {
			try {
				fileEntry = DLFileEntryServiceUtil.getFileEntry(
					groupId, folderId, name);
			}
			catch (NoSuchFileEntryException nsfe) {

				// This only happens when you're moving a file to a different
				// folder

				fileEntry = DLFileEntryServiceUtil.getFileEntry(
					groupId, newFolderId, name);
			}
		}

		request.setAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY, fileEntry);
	}

	public static void getFileEntry(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFileEntry(request);
	}

	public static void getFileShortcut(HttpServletRequest request)
		throws Exception {

		long fileShortcutId = ParamUtil.getLong(request, "fileShortcutId");

		DLFileShortcut fileShortcut = null;

		if (fileShortcutId > 0) {
			fileShortcut = DLFileShortcutServiceUtil.getFileShortcut(
				fileShortcutId);
		}

		request.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUT, fileShortcut);
	}

	public static void getFileShortcut(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFileShortcut(request);
	}

	public static void getFolder(HttpServletRequest request) throws Exception {
		long folderId = ParamUtil.getLong(request, "folderId");

		DLFolder folder = null;

		if ((folderId > 0) &&
			(folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			folder = DLFolderServiceUtil.getFolder(folderId);
		}

		request.setAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER, folder);
	}

	public static void getFolder(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFolder(request);
	}

}