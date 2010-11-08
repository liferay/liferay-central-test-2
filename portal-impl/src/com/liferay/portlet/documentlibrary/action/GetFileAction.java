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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Charles May
 * @author Bruno Farache
 */
public class GetFileAction extends PortletAction {

	public ActionForward strutsExecute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			long folderId = ParamUtil.getLong(request, "folderId");
			String name = ParamUtil.getString(request, "name");
			String title = ParamUtil.getString(request, "title");
			String version = ParamUtil.getString(request, "version");

			long fileShortcutId = ParamUtil.getLong(request, "fileShortcutId");

			String uuid = ParamUtil.getString(request, "uuid");

			String targetExtension = ParamUtil.getString(
				request, "targetExtension");

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(
				request, "groupId", themeDisplay.getScopeGroupId());

			getFile(
				folderId, name, title, version, fileShortcutId, uuid, groupId,
				targetExtension, themeDisplay, request, response);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			long folderId = ParamUtil.getLong(actionRequest, "folderId");
			String name = ParamUtil.getString(actionRequest, "name");
			String title = ParamUtil.getString(actionRequest, "title");
			String version = ParamUtil.getString(actionRequest, "version");

			long fileShortcutId = ParamUtil.getLong(
				actionRequest, "fileShortcutId");

			String uuid = ParamUtil.getString(actionRequest, "uuid");

			String targetExtension = ParamUtil.getString(
				actionRequest, "targetExtension");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(
				actionRequest, "groupId", themeDisplay.getScopeGroupId());

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			getFile(
				folderId, name, title, version, fileShortcutId, uuid, groupId,
				targetExtension, themeDisplay, request, response);

			setForward(actionRequest, ActionConstants.COMMON_NULL);
		}
		catch (NoSuchFileEntryException nsfee) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, nsfee, actionRequest,
				actionResponse);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, actionRequest, actionResponse);
		}
	}

	protected void getFile(
			long folderId, String name, String title, String version,
			long fileShortcutId, String uuid, long groupId,
			String targetExtension, ThemeDisplay themeDisplay,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		if (name.startsWith("DLFE-")) {
			name = name.substring("DLFE-".length());
		}

		name = FileUtil.stripExtension(name);

		DLFileEntry fileEntry = null;

		if (Validator.isNotNull(uuid) && (groupId > 0)) {
			try {
				fileEntry = DLFileEntryServiceUtil.getFileEntryByUuidAndGroupId(
					uuid, groupId);

				folderId = fileEntry.getFolderId();
				name = fileEntry.getName();
			}
			catch (Exception e) {
			}
		}

		if (fileShortcutId <= 0) {
			if (Validator.isNotNull(name)) {
				fileEntry = DLFileEntryServiceUtil.getFileEntry(
					groupId, folderId, name);

				title = fileEntry.getTitle();
			}
			else if (Validator.isNotNull(title)) {
				fileEntry = DLFileEntryServiceUtil.getFileEntryByTitle(
					groupId, folderId, title);

				name = fileEntry.getName();
			}
		}
		else {
			DLFileShortcut fileShortcut =
				DLFileShortcutServiceUtil.getFileShortcut(fileShortcutId);

			folderId = fileShortcut.getToFolderId();
			name = fileShortcut.getToName();

			fileEntry = DLFileEntryServiceUtil.getFileEntry(
				groupId, folderId, name);
		}

		if (Validator.isNull(version)) {
			if (Validator.isNotNull(fileEntry.getVersion())) {
				version = fileEntry.getVersion();
			}
			else {
				throw new NoSuchFileEntryException();
			}
		}

		InputStream is = DLFileEntryServiceUtil.getFileAsStream(
			groupId, folderId, name, version);

		boolean converted = false;

		String fileName = fileEntry.getTitle();

		if (Validator.isNotNull(targetExtension)) {
			String id = DocumentConversionUtil.getTempFileId(
				fileEntry.getFileEntryId(), version);

			String sourceExtension = FileUtil.getExtension(fileName);

			InputStream convertedIS = DocumentConversionUtil.convert(
				id, is, sourceExtension, targetExtension);

			if ((convertedIS != null) && (convertedIS != is)) {
				fileName = FileUtil.stripExtension(
					fileEntry.getTitle()).concat(StringPool.PERIOD).concat(
						targetExtension);

				is = convertedIS;

				converted = true;
			}
		}

		int contentLength = 0;

		if (!converted) {
			if (DLUtil.compareVersions(version, fileEntry.getVersion()) >= 0) {
				contentLength = (int)fileEntry.getSize();
			}
			else {
				DLFileVersion fileVersion =
					DLFileVersionLocalServiceUtil.getFileVersion(
						groupId, folderId, name, version);

				contentLength = (int)fileVersion.getSize();
			}
		}

		String contentType = MimeTypesUtil.getContentType(fileName);

		ServletResponseUtil.sendFile(
			request, response, fileName, is, contentLength, contentType);
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}