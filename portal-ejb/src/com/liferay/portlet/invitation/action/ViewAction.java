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
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.portlet.invitation.util.InvitationUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionMessages;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ViewAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 *
 */
public class ViewAction extends PortletAction {

	public static final int MAX_EMAILS = 20;

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		List validEmailAddresses = new ArrayList();

		String[] emailAddresses = StringUtil.split(
			ParamUtil.getString(req, "emailAddresses"), "\n");

		for (int i = 0; i < emailAddresses.length; i++) {
			String emailAddress = emailAddresses[i];

			if (Validator.isEmailAddress(emailAddress)) {
				validEmailAddresses.add(emailAddress);
			}
		}

		if (validEmailAddresses.size() == 0) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		String fromAddress = user.getEmailAddress();
		String fromName = user.getFullName();

		InternetAddress from = new InternetAddress(fromAddress, fromName);

		Layout layout = themeDisplay.getLayout();

		String pageURL =
			PortalUtil.getPortalURL(req) +
				PortalUtil.getLayoutURL(layout, themeDisplay);

		PortletPreferences prefs =
			PortletPreferencesFactory.getPortletSetup(
				req, PortletKeys.INVITATION, true, true);

		String subject = InvitationUtil.getEmailMessageSubject(prefs);
		String body = InvitationUtil.getEmailMessageBody(prefs);

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PAGE_URL$]"
			},
			new String[] {
				fromAddress,
				fromName,
				pageURL
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PAGE_URL$]"
			},
			new String[] {
				fromAddress,
				fromName,
				pageURL
			});

		for (int i = 0; i < validEmailAddresses.size(); i++) {
			String emailAddress = (String)validEmailAddresses.get(i);

			InternetAddress to = new InternetAddress(emailAddress);

			MailMessage message = new MailMessage(from, to, subject, body, true);

			MailServiceUtil.sendEmail(message);
		}

		SessionMessages.add(req, "invitationSent");
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		return mapping.findForward(getForward(req, "portlet.invitation.view"));
	}

}