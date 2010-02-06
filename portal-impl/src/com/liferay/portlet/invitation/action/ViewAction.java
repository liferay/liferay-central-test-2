/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.invitation.util.InvitationUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 */
public class ViewAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		List<String> validEmailAddresses = new ArrayList<String>();
		Set<String> invalidEmailAddresses = new HashSet<String>();

		int emailMessageMaxRecipients =
			InvitationUtil.getEmailMessageMaxRecipients();

		for (int i = 0; i < emailMessageMaxRecipients; i++) {
			String emailAddress = ParamUtil.getString(
				actionRequest, "emailAddress" + i);

			if (Validator.isEmailAddress(emailAddress)) {
				if (!validEmailAddresses.contains(emailAddress)) {
					validEmailAddresses.add(emailAddress);
				}
			}
			else if (Validator.isNotNull(emailAddress)) {
				invalidEmailAddresses.add("emailAddress" + i);
			}
		}

		if (validEmailAddresses.isEmpty() && invalidEmailAddresses.isEmpty()) {
			invalidEmailAddresses.add("emailAddress0");
		}

		if (!invalidEmailAddresses.isEmpty()) {
			SessionErrors.add(
				actionRequest, "emailAddresses", invalidEmailAddresses);

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		String fromAddress = user.getEmailAddress();
		String fromName = user.getFullName();

		InternetAddress from = new InternetAddress(fromAddress, fromName);

		Layout layout = themeDisplay.getLayout();

		String portalURL = PortalUtil.getPortalURL(actionRequest);

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			layout, themeDisplay);

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, PortletKeys.INVITATION);

		String subject = InvitationUtil.getEmailMessageSubject(preferences);
		String body = InvitationUtil.getEmailMessageBody(preferences);

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PAGE_URL$]",
				"[$PORTAL_URL$]"
			},
			new String[] {
				fromAddress,
				fromName,
				layoutFullURL,
				portalURL
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PAGE_URL$]",
				"[$PORTAL_URL$]"
			},
			new String[] {
				fromAddress,
				fromName,
				layoutFullURL,
				portalURL
			});

		for (String emailAddress : validEmailAddresses) {
			InternetAddress to = new InternetAddress(emailAddress);

			MailMessage message = new MailMessage(
				from, to, subject, body, true);

			MailServiceUtil.sendEmail(message);
		}

		SessionMessages.add(actionRequest, "invitationSent");

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		actionResponse.sendRedirect(redirect);
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		return mapping.findForward(
			getForward(renderRequest, "portlet.invitation.view"));
	}

}