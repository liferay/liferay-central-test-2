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
package com.liferay.portlet.mailbox.action;

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
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.liferay.portal.model.User;
import com.liferay.portal.shared.util.StackTraceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.ContentTypeUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.mailbox.util.MailAttachment;
import com.liferay.portlet.mailbox.util.MailMessage;
import com.liferay.portlet.mailbox.util.MailUtil;
import com.liferay.portlet.mailbox.util.RemoteMailAttachment;
import com.liferay.util.FileUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.UploadPortletRequest;

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

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals(Constants.SEND) || cmd.equals(Constants.SAVE)) {
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
				mm.setMessageUID(ParamUtil.getLong(req, "messageId"));

				Map attachments = _getAttachments(
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

				mm.setRemoteAttachments(_getRemoteAttachments(req));
				
				MailUtil.completeMessage(ses, mm, cmd.equals(Constants.SEND));

				setForward(req, "portlet.mailbox.view");
			}
			else {
				if (Validator.isNotNull(cmd)) {
				}
			}
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));
		}
	}
	
	private static Map _getAttachments(UploadPortletRequest uploadReq) 
		throws Exception {
		
		Map attachments = new HashMap();

		Enumeration enu = uploadReq.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = (String)enu.nextElement();

			if (name.startsWith("attachment")) {
				File file = uploadReq.getFile(name);
				byte[] bytes = FileUtil.getBytes(file);

				if (bytes != null && bytes.length > 0) {
					attachments.put(uploadReq.getFileName(name), bytes);
				}
			}
		}

		return attachments;
	}

	private static List _getRemoteAttachments(ActionRequest req)
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