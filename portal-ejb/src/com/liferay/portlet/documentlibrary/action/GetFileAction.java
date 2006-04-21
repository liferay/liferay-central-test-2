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
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.spring.DLFileEntryLocalServiceUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="GetFileAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GetFileAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

			String companyId = themeDisplay.getCompanyId();

			String folderId = ParamUtil.getString(req, "folderId");
			String name = ParamUtil.getString(req, "name");
			double version = ParamUtil.getDouble(req, "version");

			DLFileEntryPermission.check(
				themeDisplay.getPermissionChecker(), folderId, name,
				ActionKeys.VIEW);

			InputStream is = null;

			if (version > 0) {
				is = DLFileEntryLocalServiceUtil.getFileAsStream(
					companyId, req.getRemoteUser(), folderId, name, version);
			}
			else {
				is = DLFileEntryLocalServiceUtil.getFileAsStream(
					companyId, req.getRemoteUser(), folderId, name);
			}

			HttpServletResponse httpRes =
				((ActionResponseImpl)res).getHttpServletResponse();

			ServletResponseUtil.sendFile(httpRes, name, is);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private static Log _log = LogFactory.getLog(GetFileAction.class);

}