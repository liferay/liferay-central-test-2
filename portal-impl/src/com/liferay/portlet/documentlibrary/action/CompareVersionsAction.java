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

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.util.diff.DiffUtil;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="CompareVersionsAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class CompareVersionsAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		long folderId = ParamUtil.getLong(req, "folderId");
		String name = ParamUtil.getString(req, "name");

		double sourceVersion = ParamUtil.getDouble(req, "sourceVersion");
		double targetVersion = ParamUtil.getDouble(req, "targetVersion");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		HttpServletRequest httpReq =
			((ActionRequestImpl)req).getHttpServletRequest();
		HttpServletResponse httpRes =
			((ActionResponseImpl)res).getHttpServletResponse();

		compareVersions(
			folderId, name, sourceVersion, targetVersion, themeDisplay, httpReq,
			httpRes);

	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		return mapping.findForward("portlet.document_library.compare_versions");
	}

	protected void compareVersions(
			long folderId, String name, double sourceVersion,
			double targetVersion,  ThemeDisplay themeDisplay,
			HttpServletRequest req, HttpServletResponse res)
		throws Exception {

		long companyId = themeDisplay.getCompanyId();
		long userId = themeDisplay.getUserId();

		InputStream sourceIs = DLFileEntryLocalServiceUtil.getFileAsStream(
			companyId, userId, folderId, name, sourceVersion);
		InputStream targetIs = DLFileEntryLocalServiceUtil.getFileAsStream(
			companyId, userId, folderId, name, targetVersion);

		List[] diffResults = DiffUtil.diff(
			new InputStreamReader(sourceIs), new InputStreamReader(targetIs));

		req.setAttribute(WebKeys.SOURCE_NAME, name + " " + sourceVersion);
		req.setAttribute(WebKeys.TARGET_NAME, name + " " + targetVersion);
		req.setAttribute(WebKeys.DIFF_RESULTS, diffResults);
	}

}