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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.UploadRequestUtil;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.UploadPortletRequest;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditPageAttachmentAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class EditPageAttachmentAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				addAttachment(req);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteAttachment(req);
			}

			sendRedirect(req, res);
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof NoSuchPageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.wiki.error");
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		try {
			ActionUtil.getNode(req);
			ActionUtil.getPage(req);
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof NoSuchPageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.wiki.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.wiki.edit_page_attachment"));
	}

	protected void deleteAttachment(ActionRequest req) throws Exception {
		long nodeId = ParamUtil.getLong(req, "nodeId");
		String title = ParamUtil.getString(req, "title");
		String attachment = ParamUtil.getString(req, "fileName");

		WikiPageServiceUtil.deletePageAttachment(nodeId, title, attachment);
	}

	protected void addAttachment(ActionRequest req) throws Exception {
		UploadPortletRequest uploadReq =
			UploadRequestUtil.getUploadPortletRequest(req);

		long nodeId = ParamUtil.getLong(req, "nodeId");
		String title = ParamUtil.getString(req, "title");
		int numOfFiles = ParamUtil.getInteger(req, "numOfFiles");

		List<ObjectValuePair<String, byte[]>> files =
			new ArrayList<ObjectValuePair<String, byte[]>>();

		if (numOfFiles == 0) {
			File file = uploadReq.getFile("file");
			String fileName = uploadReq.getFileName("file");

			if (file != null) {
				byte[] bytes = FileUtil.getBytes(file);

				if ((bytes != null) && (bytes.length > 0)) {
					ObjectValuePair<String, byte[]> ovp =
						new ObjectValuePair<String, byte[]>(fileName, bytes);

					files.add(ovp);
				}
			}
		}
		else {
			for (int i = 1; i <= numOfFiles; i++) {
				File file = uploadReq.getFile("file" + i);

				String fileName = uploadReq.getFileName("file" + i);

				if (file != null) {
					byte[] bytes = FileUtil.getBytes(file);

					if ((bytes != null) && (bytes.length > 0)) {
						ObjectValuePair<String, byte[]> ovp =
							new ObjectValuePair<String, byte[]>(
								fileName, bytes);

						files.add(ovp);
					}
				}
			}
		}

		WikiPageServiceUtil.addPageAttachments(nodeId, title, files);
	}

}