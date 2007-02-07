/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.invitation.action;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditInvitationAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Charles May
 *
 */
public class EditInvitationAction extends PortletAction {

	public static final int MAX_EMAILS = 20;

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals("view_form")) {
				setForward(req, "portlet.invitation.edit");
			}
			else if (cmd.equals("send")) {
				sendInvitations(req, res);
			}
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.invitation.error");
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

		return mapping.findForward(getForward(req, "portlet.invitation.view"));
	}

	public void sendInvitations(ActionRequest req, ActionResponse res)
		throws Exception {

		String redirect = ParamUtil.getString(req, "redirect");
		Map errors = new HashMap();
		List emails = new ArrayList();

		for (int i=0; i < MAX_EMAILS; i++) {
			String email = ParamUtil.getString(req, "email_" + i);

			if (Validator.isEmailAddress(email)) {
				emails.add(email);
			}
			else if (!email.equals("")) {
				errors.put("email_" + i, "invalid-email");
			}
		}

		if (errors.size() > 0) {
			SessionErrors.add(req, EditInvitationAction.class.getName(), errors);

			setForward(req, "portlet.invitation.edit");

			return;
		}
		else if (emails.size() > 0) {
			User user = PortalUtil.getUser(req);
			String userFullName = user.getFullName();
			String userEmailAddress = user.getEmailAddress();

			ActionRequestImpl requestImpl = (ActionRequestImpl)req;
			String portalURL = PortalUtil.getPortalURL(requestImpl.getHttpServletRequest());

			String subject = ContentUtil.get(PropsUtil.get(PropsUtil.INVITATION_EMAIL_SUBJECT));
			subject = StringUtil.replace(
					subject,
					new String[] {
						"[$FROM_ADDRESS$]",
						"[$FROM_NAME$]",
						"[$PORTAL_URL$]",
					},
					new String[] {
						userEmailAddress,
						userFullName,
						portalURL
					});

			String body = ContentUtil.get(PropsUtil.get(PropsUtil.INVITATION_EMAIL_BODY));
			body = StringUtil.replace(
					body,
					new String[] {
						"[$FROM_ADDRESS$]",
						"[$FROM_NAME$]",
						"[$PORTAL_URL$]",
					},
					new String[] {
						userEmailAddress,
						userFullName,
						portalURL
					});

			InternetAddress from = new InternetAddress(userEmailAddress, userFullName);

			for (int j=0; j < emails.size(); j++) {
				String toAddress = (String)emails.get(j);

				InternetAddress to = new InternetAddress(toAddress);

				MailMessage message = new MailMessage(from, to, subject, body, true);

				MailServiceUtil.sendEmail(message);
			}

			redirect += "&success=1";
		}

		res.sendRedirect(redirect);
	}

}