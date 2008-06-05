/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutServiceUtil;
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
 *
 */
public class GetFileAction extends PortletAction {

	public ActionForward strutsExecute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		try {
			long folderId = ParamUtil.getLong(req, "folderId");
			String name = ParamUtil.getString(req, "name");
			double version = ParamUtil.getDouble(req, "version");

			long fileShortcutId = ParamUtil.getLong(req, "fileShortcutId");

			String uuid = ParamUtil.getString(req, "uuid");
			long groupId = ParamUtil.getLong(req, "groupId");

			String targetExtension = ParamUtil.getString(
				req, "targetExtension");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

			getFile(
				folderId, name, version, fileShortcutId, uuid, groupId,
				targetExtension, themeDisplay, req, res);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, req, res);

			return null;
		}
	}

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		try {
			long folderId = ParamUtil.getLong(req, "folderId");
			String name = ParamUtil.getString(req, "name");
			double version = ParamUtil.getDouble(req, "version");

			long fileShortcutId = ParamUtil.getLong(req, "fileShortcutId");

			String uuid = ParamUtil.getString(req, "uuid");
			long groupId = ParamUtil.getLong(req, "groupId");

			String targetExtension = ParamUtil.getString(req, "targetExtension");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

			HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);
			HttpServletResponse httpRes = PortalUtil.getHttpServletResponse(res);

			getFile(
				folderId, name, version, fileShortcutId, uuid, groupId,
				targetExtension, themeDisplay, httpReq, httpRes);

			setForward(req, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, req, res);
		}
	}

	protected void getFile(
			long folderId, String name, double version, long fileShortcutId,
			String uuid, long groupId, String targetExtension,
			ThemeDisplay themeDisplay, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		InputStream is = null;

		try {
			long companyId = themeDisplay.getCompanyId();
			long userId = themeDisplay.getUserId();

			DLFileEntry fileEntry = null;

			if (Validator.isNotNull(uuid) && (groupId > 0)) {
				try {
					fileEntry = DLFileEntryLocalServiceUtil.
						getFileEntryByUuidAndGroupId(
							uuid, groupId);

					folderId = fileEntry.getFolderId();
					name = fileEntry.getName();
				}
				catch (Exception e) {
				}
			}

			if (fileShortcutId <= 0) {
				DLFileEntryPermission.check(
					themeDisplay.getPermissionChecker(), folderId, name,
					ActionKeys.VIEW);
			}
			else {
				DLFileShortcut fileShortcut =
					DLFileShortcutServiceUtil.getFileShortcut(fileShortcutId);

				folderId = fileShortcut.getToFolderId();
				name = fileShortcut.getToName();
			}

			if (fileEntry == null) {
				fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
					folderId, name);
			}

			if (version == 0) {
				version = fileEntry.getVersion();
			}

			is = DLFileEntryLocalServiceUtil.getFileAsStream(
				companyId, userId, folderId, name, version);

			String fileName = fileEntry.getTitleWithExtension();

			if (Validator.isNotNull(targetExtension)) {
				String id = DocumentConversionUtil.getTempFileId(
					fileEntry.getFileEntryId(), version);

				String sourceExtension = FileUtil.getExtension(name);

				InputStream convertedIS = DocumentConversionUtil.convert(
					id, is, sourceExtension, targetExtension);

				if ((convertedIS != null) && (convertedIS != is)) {
					StringMaker sm = new StringMaker();

					sm.append(fileEntry.getTitle());
					sm.append(StringPool.PERIOD);
					sm.append(targetExtension);

					fileName = sm.toString();

					is = convertedIS;
				}
			}

			String contentType = MimeTypesUtil.getContentType(fileName);

			ServletResponseUtil.sendFile(res, fileName, is, contentType);
		}
		finally {
			ServletResponseUtil.cleanUp(is);
		}
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}