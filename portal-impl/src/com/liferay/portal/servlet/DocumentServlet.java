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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="DocumentServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class DocumentServlet extends HttpServlet {

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			long companyId = PortalUtil.getCompanyId(request);

			long userId = PortalUtil.getUserId(request);

			if (userId == 0) {
				Company company = CompanyLocalServiceUtil.getCompany(companyId);

				User defaultUser = company.getDefaultUser();

				userId = defaultUser.getUserId();
			}

			PermissionChecker permissionChecker = null;

			PrincipalThreadLocal.setName(userId);

			User user = UserLocalServiceUtil.getUserById(userId);

			permissionChecker = PermissionCheckerFactoryUtil.create(user, true);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			String path = request.getPathInfo();

			if (path.startsWith(StringPool.SLASH)) {
				path = path.substring(1);
			}

			if (path.endsWith(StringPool.SLASH)) {
				path = path.substring(0, path.length() - 1);
			}

			String[] pathArray = StringUtil.split(path, StringPool.SLASH);

			if ((pathArray.length > 0) && (pathArray.length <= 3)) {
				sendFile(request, response, permissionChecker, pathArray);
			}
			else {
				throw new NoSuchFileEntryException();
			}
		}
		catch (NoSuchFileEntryException nsfee) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, nsfee, request, response);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);
		}
	}

	protected void sendFile(
			HttpServletRequest request, HttpServletResponse response,
			PermissionChecker permissionChecker, String[] pathArray)
		throws Exception {

		long groupId = 0;
		long folderId = 0;
		String name = null;
		String fileName = null;

		DLFileEntry fileEntry = null;

		if (pathArray.length == 1) {
			long fileShortcutId = GetterUtil.getLong(pathArray[0]);

			DLFileShortcut fileShortcut =
				DLFileShortcutServiceUtil.getFileShortcut(fileShortcutId);

			groupId = fileShortcut.getGroupId();
			folderId = fileShortcut.getToFolderId();
			name = fileShortcut.getToName();

			fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				groupId, folderId, name);

			fileName = fileEntry.getTitle();
		}
		else if (pathArray.length == 2) {
			groupId = GetterUtil.getLong(pathArray[0]);

			fileEntry =
				DLFileEntryLocalServiceUtil.getFileEntryByUuidAndGroupId(
					pathArray[1], groupId);

			folderId = fileEntry.getFolderId();
			fileName = fileEntry.getTitle();
			name = fileEntry.getName();
		}
		else {
			groupId = GetterUtil.getLong(pathArray[0]);
			folderId = GetterUtil.getLong(pathArray[1]);
			fileName = HttpUtil.decodeURL(pathArray[2], true);

			fileEntry = DLFileEntryServiceUtil.getFileEntryByTitle(
				groupId, folderId, fileName);

			name = fileEntry.getName();
		}

		if (fileEntry == null) {
			throw new NoSuchFileEntryException();
		}

		DLFileEntryPermission.check(
			permissionChecker, fileEntry, ActionKeys.VIEW);

		double version = ParamUtil.getDouble(request, "version");

		String targetExtension = ParamUtil.getString(
			request, "targetExtension");

		if (version == 0) {
			if (fileEntry.getVersion() > 0) {
				version = fileEntry.getVersion();
			}
			else {
				throw new NoSuchFileEntryException();
			}
		}

		InputStream is = DLFileEntryLocalServiceUtil.getFileAsStream(
			permissionChecker.getCompanyId(), permissionChecker.getUserId(),
			groupId, folderId, name, version);

		boolean converted = false;

		if (Validator.isNotNull(targetExtension)) {
			String id = DocumentConversionUtil.getTempFileId(
				fileEntry.getFileEntryId(), version);

			String sourceExtension = FileUtil.getExtension(fileName);

			InputStream convertedIS = DocumentConversionUtil.convert(
				id, is, sourceExtension, targetExtension);

			if ((convertedIS != null) && (convertedIS != is)) {
				StringBuilder sb = new StringBuilder();

				sb.append(FileUtil.stripExtension(fileName));
				sb.append(StringPool.PERIOD);
				sb.append(targetExtension);

				fileName = sb.toString();

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

}