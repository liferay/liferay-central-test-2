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
import com.liferay.portal.captcha.CaptchaTextException;
import com.liferay.portal.captcha.CaptchaUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionMessages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.InternetAddress;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ViewAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Daniel Weisser
 * @author Jorge Ferrer
 *
 */
public class ViewAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		PortletConfigImpl configImpl = (PortletConfigImpl)config;

		String portletId = configImpl.getPortletId();

		PortletPreferences prefs = PortletPreferencesFactory.getPortletSetup(
			req, portletId, true, true);

		boolean requireCaptcha = GetterUtil.getBoolean(
			prefs.getValue("require-captcha", StringPool.BLANK));

		if (requireCaptcha) {
			try {
				CaptchaUtil.check(req);
			}
			catch (CaptchaTextException cte) {
				SessionErrors.add(req, CaptchaTextException.class.getName());
			}
		}

		List fieldValues = new ArrayList();

		for (int i = 1; i <= 10; i++) {
			fieldValues.add(req.getParameter("field" + i));
		}

		if (validate(fieldValues, prefs)) {
			if (!sendEmail(fieldValues, prefs)) {
				SessionErrors.add(req, "emailNotSent");
			}
			else {
				SessionMessages.add(req, "emailSent");
			}
		}
		else {
			SessionErrors.add(req, "allFieldsRequired");
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		return mapping.findForward("portlet.web_form.view");
	}

	protected String getMailBody(List fieldValues, PortletPreferences prefs) {
		StringMaker sm = new StringMaker();

		Iterator itr = fieldValues.iterator();

		for (int i = 1; itr.hasNext(); i++) {
			String fieldValue = (String)itr.next();
			String fieldLabel = prefs.getValue(
				"fieldLabel" + i, StringPool.BLANK);

			if (Validator.isNotNull(fieldLabel)) {
				sm.append(fieldLabel);
				sm.append(" : ");
				sm.append(fieldValue);
				sm.append("\n");
			}
		}

		return sm.toString();
	}

	protected boolean sendEmail(List fieldValues, PortletPreferences prefs) {
		try {
			String subject = prefs.getValue("subject", StringPool.BLANK);
			String emailAddress = prefs.getValue(
				"emailAddress", StringPool.BLANK);

			if (Validator.isNull(emailAddress)) {
				_log.error(
					"The web form email cannot be sent because no email " +
						"address is configured");

				return false;
			}

			String body = getMailBody(fieldValues, prefs);

			InternetAddress fromAddress = new InternetAddress(emailAddress);
			InternetAddress toAddress = new InternetAddress(emailAddress);

			MailMessage mailMessage = new MailMessage(
				fromAddress, toAddress, subject, body);

			MailServiceUtil.sendEmail(mailMessage);

			return true;
		}
		catch (Exception e) {
			_log.error("The web form email could not be sent", e);

			return false;
		}
	}

	protected boolean validate(List fieldValues, PortletPreferences prefs) {
		for (int i = 1; i < 10; i++) {
			String fieldLabel = prefs.getValue(
				"fieldLabel" + i, StringPool.BLANK);
			boolean fieldOptional = GetterUtil.getBoolean(
					prefs.getValue("fieldOptional" + i, StringPool.BLANK), false);
				
			if ((!fieldOptional) &&
				(Validator.isNotNull(fieldLabel)) &&
				(Validator.isNull((String)fieldValues.get(i - 1)))) {

				return false;
			}
		}

		return true;
	}

	private static Log _log = LogFactory.getLog(ViewAction.class);

}
