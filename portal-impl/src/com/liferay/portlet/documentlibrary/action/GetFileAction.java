/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
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
 * <a href="GetFileAction.java.html"><b><i>View Source</i></b></a>
 *
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
			double version = ParamUtil.getDouble(request, "version");

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
			double version = ParamUtil.getDouble(actionRequest, "version");

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
			long folderId, String name, String title, double version,
			long fileShortcutId, String uuid, long groupId,
			String targetExtension, ThemeDisplay themeDisplay,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long companyId = themeDisplay.getCompanyId();
		long userId = themeDisplay.getUserId();

		if (name.startsWith("DLFE-")) {
			name = name.substring("DLFE-".length());
		}

		name = FileUtil.stripExtension(name);

		DLFileEntry fileEntry = null;

		if (Validator.isNotNull(uuid) && (groupId > 0)) {
			try {
				fileEntry =
					DLFileEntryLocalServiceUtil.getFileEntryByUuidAndGroupId(
						uuid, groupId);

				folderId = fileEntry.getFolderId();
				name = fileEntry.getName();
			}
			catch (Exception e) {
			}
		}

		if (fileShortcutId <= 0) {
			if (Validator.isNotNull(name)) {
				fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
					groupId, folderId, name);

				title = fileEntry.getTitle();
			}
			else if (Validator.isNotNull(title)) {
				fileEntry = DLFileEntryLocalServiceUtil.getFileEntryByTitle(
					groupId, folderId, title);

				name = fileEntry.getName();
			}

			DLFileEntryPermission.check(
				themeDisplay.getPermissionChecker(), fileEntry,
				ActionKeys.VIEW);
		}
		else {
			DLFileShortcut fileShortcut =
				DLFileShortcutServiceUtil.getFileShortcut(fileShortcutId);

			folderId = fileShortcut.getToFolderId();
			name = fileShortcut.getToName();

			fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				groupId, folderId, name);
		}

		if (version == 0) {
			if (fileEntry.getVersion() > 0) {
				version = fileEntry.getVersion();
			}
			else {
				throw new NoSuchFileEntryException();
			}
		}

		InputStream is = DLFileEntryLocalServiceUtil.getFileAsStream(
			companyId, userId, groupId, folderId, name, version);

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
			if (version >= fileEntry.getVersion()) {
				contentLength = fileEntry.getSize();
			}
			else {
				DLFileVersion fileVersion =
					DLFileVersionLocalServiceUtil.getFileVersion(
						groupId, folderId, name, version);

				contentLength = fileVersion.getSize();
			}
		}

		String contentType = MimeTypesUtil.getContentType(fileName);

		ServletResponseUtil.sendFile(
			response, fileName, is, contentLength, contentType);
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}