/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.spring.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.spring.DLFileShortcutServiceUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="GetFileAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GetFileAction extends PortletAction {

	public ActionForward strutsExecute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		try {
			String folderId = ParamUtil.getString(req, "folderId");
			String name = ParamUtil.getString(req, "name");
			double version = ParamUtil.getDouble(req, "version");

			long fileShortcutId = ParamUtil.getLong(req, "fileShortcutId");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

			getFile(folderId, name, version, fileShortcutId, themeDisplay, res);

			return null;
		}
		catch (Exception e) {
			req.setAttribute(PageContext.EXCEPTION, e);

			return mapping.findForward(Constants.COMMON_ERROR);
		}
	}

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String folderId = ParamUtil.getString(req, "folderId");
		String name = ParamUtil.getString(req, "name");
		double version = ParamUtil.getDouble(req, "version");

		long fileShortcutId = ParamUtil.getLong(req, "fileShortcutId");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		HttpServletResponse httpRes =
			((ActionResponseImpl)res).getHttpServletResponse();

		getFile(folderId, name, version, fileShortcutId, themeDisplay, httpRes);
	}

	protected void getFile(
			String folderId, String name, double version, long fileShortcutId,
			ThemeDisplay themeDisplay, HttpServletResponse res)
		throws Exception {

		String companyId = themeDisplay.getCompanyId();
		String userId = themeDisplay.getUserId();

		if (fileShortcutId == 0) {
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

		InputStream is = null;

		if (version > 0) {
			is = DLFileEntryLocalServiceUtil.getFileAsStream(
				companyId, userId, folderId, name, version);
		}
		else {
			is = DLFileEntryLocalServiceUtil.getFileAsStream(
				companyId, userId, folderId, name);
		}

		ServletResponseUtil.sendFile(res, name, is);
	}

}