/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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