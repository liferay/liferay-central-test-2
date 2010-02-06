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

package com.liferay.portlet.sms;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import javax.mail.internet.InternetAddress;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

/**
 * <a href="SMSPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SMSPortlet extends MVCPortlet {

	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		try {
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			if (cmd.equals(Constants.SEND)) {
				String to = actionRequest.getParameter("to");
				String subject = ParamUtil.getString(actionRequest, "subject");
				String message = ParamUtil.getString(actionRequest, "message");

				if (!Validator.isEmailAddress(to)) {
					SessionErrors.add(actionRequest, "to");
				}
				else if (message.length() > 500) {
					SessionErrors.add(actionRequest, "message");
				}

				if (SessionErrors.isEmpty(actionRequest)) {
					User user = PortalUtil.getUser(actionRequest);

					MailServiceUtil.sendEmail(new MailMessage(
						new InternetAddress(
							user.getEmailAddress(), user.getFullName()),
						new InternetAddress(to), subject, message, false));

					actionResponse.setRenderParameter("to", StringPool.BLANK);
					actionResponse.setRenderParameter(
						"subject", StringPool.BLANK);
					actionResponse.setRenderParameter(
						"message", StringPool.BLANK);

					SessionMessages.add(
						actionRequest,
						getPortletConfig().getPortletName() + ".send", to);
				}
				else {
					actionResponse.setRenderParameter("to", to);
					actionResponse.setRenderParameter("subject", subject);
					actionResponse.setRenderParameter("message", message);
				}
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

}