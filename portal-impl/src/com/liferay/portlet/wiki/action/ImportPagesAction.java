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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.NotificationThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.ProgressTrackerThreadLocal;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.wiki.ImportFilesException;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.service.WikiNodeServiceUtil;
import com.liferay.portlet.wiki.util.WikiCacheThreadLocal;
import com.liferay.portlet.wiki.util.WikiCacheUtil;

import java.io.File;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ImportPagesAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class ImportPagesAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			importPages(actionRequest, actionResponse);

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.wiki.error");
			}
			else if (e instanceof ImportFilesException ||
					 e instanceof NoSuchNodeException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getNode(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.wiki.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.wiki.import_pages"));
	}

	protected void importPages(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
			actionRequest);

		String importProgressId = ParamUtil.getString(
			uploadRequest, "importProgressId");

		ProgressTracker progressTracker = new ProgressTracker(
			actionRequest, importProgressId);

		ProgressTrackerThreadLocal.setProgressTracker(progressTracker);

		progressTracker.start();

		long nodeId = ParamUtil.getLong(uploadRequest, "nodeId");
		String importer = ParamUtil.getString(uploadRequest, "importer");

		int filesCount = ParamUtil.getInteger(uploadRequest, "filesCount", 10);

		File[] files = new File[filesCount];

		for (int i = 0; i < filesCount; i++) {
			files[i] = uploadRequest.getFile("file" + i);
		}

		NotificationThreadLocal.setEnabled(false);
		WikiCacheThreadLocal.setClearCache(false);

		WikiNodeServiceUtil.importPages(
			nodeId, importer, files, actionRequest.getParameterMap());

		WikiCacheUtil.clearCache(nodeId);

		progressTracker.finish();
	}

}