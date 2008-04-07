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

import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.service.DLLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.MimeTypesUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
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
 * <a href="GetPageAttachmentAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class GetPageAttachmentAction extends PortletAction {

	public ActionForward strutsExecute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		try {
			long nodeId = ParamUtil.getLong(req, "nodeId");
			String title = ParamUtil.getString(req, "title");
			String fileName = ParamUtil.getString(req, "fileName");

			getFile(nodeId, title, fileName, req, res);

			return null;
		}
		catch (Exception e) {
			req.setAttribute(PageContext.EXCEPTION, e);

			return mapping.findForward(ActionConstants.COMMON_ERROR);
		}
	}

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		long nodeId = ParamUtil.getLong(req, "nodeId");
		String title = ParamUtil.getString(req, "title");
		String fileName = ParamUtil.getString(req, "fileName");

		HttpServletRequest httpReq =
			((ActionRequestImpl)req).getHttpServletRequest();
		HttpServletResponse httpRes =
			((ActionResponseImpl)res).getHttpServletResponse();

		getFile(nodeId, title, fileName, httpReq, httpRes);

		setForward(req, ActionConstants.COMMON_NULL);
	}

	protected void getFile(
			long nodeId, String title, String fileName, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		int pos = fileName.indexOf(StringPool.SLASH);

		if (pos != -1) {
			title = fileName.substring(0, pos);
			fileName = fileName.substring(pos + 1);
		}

		InputStream is = null;

		try {
			WikiPage page = WikiPageServiceUtil.getPage(nodeId, title);

			is = DLLocalServiceUtil.getFileAsStream(
				page.getCompanyId(), CompanyConstants.SYSTEM,
				page.getAttachmentsDir() + "/" + fileName);

			String contentType = MimeTypesUtil.getContentType(fileName);

			ServletResponseUtil.sendFile(res, fileName, is, contentType);
		}
		catch (NoSuchFileException nsfe) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, nsfe, req, res);
		}
		catch (NoSuchPageException nspe) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, nspe, req, res);
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