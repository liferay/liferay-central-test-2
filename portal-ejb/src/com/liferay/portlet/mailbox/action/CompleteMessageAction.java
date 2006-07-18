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

import java.util.Map;

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
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;

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
				User user = PortalUtil.getUser(req);
				Address from = new InternetAddress(
					user.getEmailAddress(), user.getFullName());

				String tos = ParamUtil.getString(req, "tos");
				String ccs = ParamUtil.getString(req, "ccs");
				String bccs = ParamUtil.getString(req, "bccs");
				String subject = ParamUtil.getString(req, "subject");
				String body = ParamUtil.getString(req, "body");
				long messageId = ParamUtil.getLong(req, "messageId");
				Map attachments = ActionUtil.getAttachments(
					PortalUtil.getUploadPortletRequest(req));

				HttpSession ses =
					((ActionRequestImpl)req).getHttpServletRequest().
						getSession();

				ActionUtil.completeMessage(from, tos, ccs, bccs, subject, body,
					attachments, ses, cmd.equals(Constants.SEND), messageId);

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

	private static Log _log = LogFactory.getLog(CompleteMessageAction.class);
}