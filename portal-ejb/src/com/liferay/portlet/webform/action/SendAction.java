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

package com.liferay.portlet.webform.action;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.portlet.webform.util.WebformUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionMessages;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="SendAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Daniel Weisser
 * @author Jorge Ferrer
 *
 */
public class SendAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		int maxNumOfFields = GetterUtil.getInteger(
			config.getInitParameter("max-num-of-fields"), 10);

		List fieldValues = new ArrayList();

		for (int i = 1; i <= maxNumOfFields; i++) {
			fieldValues.add(req.getParameter("field" + i));
		}

		PortletPreferences prefs =
			PortletPreferencesFactory.getPortletSetup(
				req, config.getPortletName(), true, true);

		boolean formInputOk = validate(fieldValues, prefs, maxNumOfFields);

		if (formInputOk) {
			boolean sent = sendEmail(fieldValues, prefs);

			if (!sent) {
				SessionErrors.add(req, "messageNotSent");
			}
			else {
				SessionMessages.add(req, "messageSent");
			}

		}
		else {
			SessionErrors.add(req, "pleaseFillAllFields");
		}

		sendRedirect(req, res);

	}

	private boolean validate(
		List fieldValues, PortletPreferences prefs, int maxNumOfFields) {

		for (int i = 1; i < maxNumOfFields ; i++) {

			String fieldLabel = prefs.getValue("fieldLabel" + i, null);

			if (Validator.isNotNull(fieldLabel) &&
				(Validator.isNull((String) fieldValues.get(i - 1)))) {
				return false;
			}
		}

		return true;
	}

	protected boolean sendEmail(List fieldValues, PortletPreferences prefs) {
		try {
			String email = prefs.getValue("email", "");
			String subject = prefs.getValue("subject", "");

			if (Validator.isNull(email)) {
				_log.warn(
					"The webform email cannot be sent because no " +
					"address has been configured in the portlet configuration");
			}

			String body = WebformUtil.getMailBody(fieldValues, prefs);

			String from = email;
			InternetAddress fromAdd = new InternetAddress(from);
			InternetAddress toAdd = new InternetAddress(email);

			MailMessage mailMessage =
				new MailMessage(fromAdd, toAdd,subject, body);
			MailServiceUtil.sendEmail(mailMessage);

			return true;

		}
		catch (Exception e) {
			_log.error("The webform message could not be sent", e);
			return false;
		}
	}

	private static Log _log = LogFactory.getLog(SendAction.class);

}