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

package com.liferay.portlet.documentlibrarydisplay.action;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Repository;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Sergio González
 */
public class ActionUtil {

	public static void getFileEntries(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fileEntryId = ParamUtil.getLong(request, "fileEntryId");

		long groupId = themeDisplay.getScopeGroupId();
		long folderId = ParamUtil.getLong(request, "folderId");
		String title = ParamUtil.getString(request, "title");

		FileEntry fileEntry = null;
		List<FileEntry> fileEntries = null;

		if (fileEntryId > 0) {
			try {
				fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);
			}
			catch (NoSuchFileEntryException nsfee) {
			}
		}
		else if (Validator.isNotNull(title)) {
			try {
				fileEntry = DLAppServiceUtil.getFileEntry(
					groupId, folderId, title);
			}
			catch (NoSuchFileEntryException nsfee) {
			}
		}
		else {
			long[] fileEntryIds = StringUtil.split(
				ParamUtil.getString(request, "fileEntryIds"), 0L);

			fileEntries = new ArrayList<FileEntry>();

			for (int i = 0; i < fileEntryIds.length; i++) {
				try {
					fileEntry = DLAppServiceUtil.getFileEntry(fileEntryIds[i]);

					fileEntries.add(fileEntry);
				}
				catch (NoSuchFileEntryException nsfee) {
				}
			}
		}

		if ((fileEntryId > 0) || Validator.isNotNull(title)) {
			request.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY, fileEntry);
		}
		else {
			request.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_ENTRIES, fileEntries);
		}
	}

	public static void getFileEntries(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFileEntries(request);
	}

	public static void getFileShortcut(HttpServletRequest request)
		throws Exception {

		long fileShortcutId = ParamUtil.getLong(request, "fileShortcutId");

		DLFileShortcut fileShortcut = null;

		if (fileShortcutId > 0) {
			fileShortcut = DLAppServiceUtil.getFileShortcut(fileShortcutId);
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
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(request, "folderId");

		Folder folder = null;

		if ((folderId > 0) &&
			(folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			folder = DLAppServiceUtil.getFolder(folderId);
		}
		else {
			DLPermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.VIEW);
		}

		request.setAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER, folder);
	}

	public static void getFolder(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFolder(request);
	}

	public static void getRepository(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long repositoryId = ParamUtil.getLong(request, "repositoryId");

		Repository repository = null;

		if (repositoryId > 0) {
			repository = RepositoryServiceUtil.getRepository(repositoryId);
		}
		else {
			DLPermission.check(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.VIEW);
		}

		request.setAttribute(WebKeys.DOCUMENT_LIBRARY_REPOSITORY, repository);
	}

	public static void getRepository(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getRepository(request);
	}

}