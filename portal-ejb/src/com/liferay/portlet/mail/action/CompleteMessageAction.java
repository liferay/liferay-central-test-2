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

package com.liferay.portlet.mail.action;

import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.ContentTypeUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.mail.RecipientException;
import com.liferay.portlet.mail.model.MailAttachment;
import com.liferay.portlet.mail.model.MailMessage;
import com.liferay.portlet.mail.model.RemoteMailAttachment;
import com.liferay.portlet.mail.util.MailUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.UploadPortletRequest;

import java.io.File;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="CompleteMessageAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Ming-Gih Lam
 * @author  Alexander Chow
 *
 */
public class CompleteMessageAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		try {
			completeMessage(req);

			sendRedirect(req, res);
		}
		catch (Exception e) {
			if (e instanceof RecipientException) {
				SessionErrors.add(req, e.getClass().getName());
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

		return mapping.findForward("portlet.mail.edit_message");
	}

	protected void completeMessage(ActionRequest req)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		long draftId = ParamUtil.getLong(req, "draftId");

		HttpSession ses =
			((ActionRequestImpl)req).getHttpServletRequest().
				getSession();

		User user = PortalUtil.getUser(req);
		Address from = new InternetAddress(
			user.getEmailAddress(), user.getFullName());

		MailMessage mm = new MailMessage();
		mm.setFrom(from);
		mm.setTo(ParamUtil.getString(req, "tos"));
		mm.setCc(ParamUtil.getString(req, "ccs"));
		mm.setBcc(ParamUtil.getString(req, "bccs"));
		mm.setSubject(ParamUtil.getString(req, "subject"));
		mm.setHtmlBody(ParamUtil.getString(req, "body"));

		Map attachments = getAttachments(
			PortalUtil.getUploadPortletRequest(req));

		Set filenames = attachments.keySet();

		for (Iterator itr = filenames.iterator(); itr.hasNext(); ) {
			String filename = (String)itr.next();
			byte [] attachment = (byte [])attachments.get(filename);

			MailAttachment ma = new MailAttachment();
			ma.setFilename(filename);
			ma.setContentType(ContentTypeUtil.getContentType(filename));
			ma.setContent(attachment);

			mm.appendAttachment(ma);
		}

		mm.setRemoteAttachments(getRemoteAttachments(req));

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		String url =
			themeDisplay.getPathMain() + "/mail/get_attachment?";

		MailUtil.completeMessage(
			ses, mm, cmd.equals(Constants.SEND), draftId, url);
	}

	protected Map getAttachments(UploadPortletRequest uploadReq)
		throws Exception {

		Map attachments = new HashMap();

		Enumeration enu = uploadReq.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = (String)enu.nextElement();

			if (name.startsWith("attachment")) {
				File file = uploadReq.getFile(name);
				byte[] bytes = FileUtil.getBytes(file);

				if ((bytes != null) && (bytes.length > 0)) {
					attachments.put(uploadReq.getFileName(name), bytes);
				}
			}
		}

		return attachments;
	}

	protected List getRemoteAttachments(ActionRequest req)
		throws Exception {

		List list = new ArrayList();

		Enumeration enu = req.getParameterNames();

		String prefix = "remoteAttachment";

		while (enu.hasMoreElements()) {
			String name = (String)enu.nextElement();

			if (name.startsWith(prefix)) {
				RemoteMailAttachment rma = new RemoteMailAttachment();

				rma.setContentPath(ParamUtil.getString(req, name));
				rma.setFilename(name.substring(prefix.length()));

				list.add(rma);
			}
		}

		return list;
	}

	private static Log _log = LogFactory.getLog(CompleteMessageAction.class);

}