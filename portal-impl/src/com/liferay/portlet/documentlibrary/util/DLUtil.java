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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="DLUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class DLUtil {

	public static void addPortletBreadcrumbEntries(
			DLFileEntry fileEntry, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		DLFolder folder = fileEntry.getFolder();

		addPortletBreadcrumbEntries(folder, request, renderResponse);

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter(
			"struts_action", "/document_library/view_file_entry");
		portletURL.setParameter(
			"folderId", String.valueOf(fileEntry.getFolderId()));
		portletURL.setParameter("name", fileEntry.getName());

		PortalUtil.addPortletBreadcrumbEntry(
			request, fileEntry.getTitle(), portletURL.toString());
	}

	public static void addPortletBreadcrumbEntries(
			DLFileShortcut fileShortcut, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		DLFolder folder = fileShortcut.getFolder();

		addPortletBreadcrumbEntries(folder, request, renderResponse);

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter(
			"struts_action", "/document_library/view_file_shortcut");
		portletURL.setParameter(
			"fileShortcutId", String.valueOf(fileShortcut.getFileShortcutId()));

		PortalUtil.addPortletBreadcrumbEntry(
			request, fileShortcut.getToTitle(), portletURL.toString());
	}

	public static void addPortletBreadcrumbEntries(
			long folderId, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		DLFolder folder = DLFolderLocalServiceUtil.getFolder(folderId);

		addPortletBreadcrumbEntries(folder, request, renderResponse);
	}

	public static void addPortletBreadcrumbEntries(
			DLFolder folder, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		String strutsAction = ParamUtil.getString(
			request, "struts_action");

		long groupId = ParamUtil.getLong(request, "groupId");

		PortletURL portletURL = renderResponse.createRenderURL();

		if (strutsAction.equals("/journal/select_document_library") ||
			strutsAction.equals("/document_library/select_file_entry") ||
			strutsAction.equals("/document_library/select_folder")) {

			ThemeDisplay themeDisplay =	(ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			portletURL.setWindowState(LiferayWindowState.POP_UP);

			portletURL.setParameter("struts_action", strutsAction);
			portletURL.setParameter("groupId", String.valueOf(groupId));

			PortalUtil.addPortletBreadcrumbEntry(
				request, themeDisplay.translate("folders"),
				portletURL.toString());
		}
		else {
			portletURL.setParameter("struts_action", "/document_library/view");
		}

		List<DLFolder> ancestorFolders = folder.getAncestors();

		Collections.reverse(ancestorFolders);

		for (DLFolder ancestorFolder : ancestorFolders) {

			portletURL.setParameter(
				"folderId", String.valueOf(ancestorFolder.getFolderId()));

			PortalUtil.addPortletBreadcrumbEntry(
				request, ancestorFolder.getName(), portletURL.toString());
		}

		portletURL.setParameter(
			"folderId", String.valueOf(folder.getFolderId()));

		PortalUtil.addPortletBreadcrumbEntry(
			request, folder.getName(), portletURL.toString());
	}

	public static String getFileExtension(String name) {
		return _instance._getFileExtension(name);
	}

	public static String getLockId(long folderId, String name) {
		StringBuilder sb = new StringBuilder();

		sb.append(folderId);
		sb.append(StringPool.POUND);
		sb.append(name);

		return sb.toString();
	}

	private DLUtil() {
		_fileExtensions = new HashSet<String>();

		String[] fileExtensions = null;

		try {
			fileExtensions = PrefsPropsUtil.getStringArray(
				PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA);
		}
		catch (SystemException se) {
			_log.error(se, se);

			fileExtensions = new String[] {StringPool.BLANK};
		}

		for (int i = 0; i < fileExtensions.length; i++) {

			// Only process non wildcard extensions

			if (!StringPool.STAR.equals(fileExtensions[i])) {

				// Strip starting period

				String extension = fileExtensions[i];
				extension = extension.substring(1, extension.length());

				_fileExtensions.add(extension);
			}
		}
	}

	private String _getFileExtension(String name) {
		String extension = StringPool.BLANK;

		int pos = name.lastIndexOf(StringPool.PERIOD);

		if (pos != -1) {
			extension = name.substring(pos + 1, name.length()).toLowerCase();
		}

		if (!_fileExtensions.contains(extension)) {
			extension = _DEFAULT_FILE_EXTENSION;
		}

		return extension;
	}

	private static final String _DEFAULT_FILE_EXTENSION = "page";

	private static Log _log = LogFactoryUtil.getLog(DLUtil.class);

	private static DLUtil _instance = new DLUtil();

	private Set<String> _fileExtensions;

}