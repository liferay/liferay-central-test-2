/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.captcha.CaptchaTextException;
import com.liferay.portal.captcha.CaptchaUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;
import com.liferay.portlet.webform.util.WebFormUtil;
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
 * @author Alberto Montero
 *
 */
public class ViewAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		PortletConfigImpl configImpl = (PortletConfigImpl)config;

		String portletId = configImpl.getPortletId();

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getPortletSetup(req, portletId);

		boolean requireCaptcha = GetterUtil.getBoolean(
			prefs.getValue("requireCaptcha", StringPool.BLANK));
		String successURL = GetterUtil.getString(
			prefs.getValue("successURL", StringPool.BLANK));
		boolean sendAsEmail = GetterUtil.getBoolean(
			prefs.getValue("sendAsEmail", StringPool.BLANK), true);
		boolean saveToDatabase = GetterUtil.getBoolean(
			prefs.getValue("saveToDatabase", StringPool.BLANK));
		String databaseTableName = GetterUtil.getString(
			prefs.getValue("databaseTableName", StringPool.BLANK));
		boolean saveToFile = GetterUtil.getBoolean(
			prefs.getValue("saveToFile", StringPool.BLANK));
		String fileName = GetterUtil.getString(
			prefs.getValue("fileName", StringPool.BLANK));

		if (requireCaptcha) {
			try {
				CaptchaUtil.check(req);
			}
			catch (CaptchaTextException cte) {
				SessionErrors.add(req, CaptchaTextException.class.getName());
			}
		}

		List<String> fieldValues = new ArrayList<String>();

		for (int i = 1; i <= WebFormUtil.MAX_FIELDS; i++) {
			fieldValues.add(req.getParameter("field" + i));
		}

		if (validate(fieldValues, prefs)) {
			boolean emailSuccess = true;
			boolean databaseSuccess = true;
			boolean fileSuccess = true;

			if (sendAsEmail) {
				emailSuccess = sendEmail(fieldValues, prefs);
			}

			if (saveToDatabase) {
				databaseSuccess = saveDatabase(
					fieldValues, prefs, databaseTableName);
			}

			if (saveToFile) {
				fileSuccess = saveFile(fieldValues, prefs, fileName);
			}

			if (emailSuccess && databaseSuccess && fileSuccess) {
				SessionMessages.add(req, "success");
			}
			else {
				SessionErrors.add(req, "error");
			}
		}
		else {
			SessionErrors.add(req, "requiredFieldMissing");
		}

		if (SessionErrors.isEmpty(req) && Validator.isNotNull(successURL)) {
	        res.sendRedirect(successURL);
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		return mapping.findForward("portlet.web_form.view");
	}

	protected String getMailBody(
		List<String> fieldValues, PortletPreferences prefs) {

		StringMaker sm = new StringMaker();

		Iterator<String> itr = fieldValues.iterator();

		for (int i = 1; itr.hasNext(); i++) {
			String fieldValue = itr.next();

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

	private boolean saveDatabase(
			List<String> fieldValues, PortletPreferences prefs,
			String databaseTableName)
		throws Exception {

		if (Validator.isNull(databaseTableName)) {
			return false;
		}

		WebFormUtil.checkTable(databaseTableName, prefs);

		long classPK = CounterLocalServiceUtil.increment(
			WebFormUtil.class.getName());

		Iterator<String> itr = fieldValues.iterator();

		try {
			for (int i = 1; itr.hasNext(); i++) {
				String fieldValue = itr.next();

				String fieldLabel = prefs.getValue(
					"fieldLabel" + i, StringPool.BLANK);

				if (Validator.isNotNull(fieldLabel)) {
					ExpandoValueLocalServiceUtil.addValue(
						WebFormUtil.class.getName(), databaseTableName,
						fieldLabel, classPK, fieldValue);
				}
			}

			return true;
		}
		catch (Exception e) {
			_log.error(
				"The web form data could not be saved to the database", e);

			return false;
		}
	}

	protected boolean saveFile(
		List<String> fieldValues, PortletPreferences prefs, String fileName) {

		// Save the file as a standard Excel CSV format. Use ; as a delimiter,
		// quote each entry with double quotes, and escape double quotes in
		// values a two double quotes.

		StringMaker sm = new StringMaker();

		Iterator<String> itr = fieldValues.iterator();

		for (int i = 1; itr.hasNext(); i++) {
			String fieldValue = itr.next();

			String fieldLabel = prefs.getValue(
				"fieldLabel" + i, StringPool.BLANK);

			if (Validator.isNotNull(fieldLabel)) {
				sm.append("\"");
				sm.append(StringUtil.replace(fieldValue, "\"", "\"\""));
				sm.append("\";");
			}
		}

		String s = sm.substring(0, sm.length() - 1) + "\n";

		try {
			FileUtil.write(fileName, s, false, true);

			return true;
		}
		catch (Exception e) {
			_log.error("The web form data could not be saved to a file", e);

			return false;
		}
	}

	protected boolean sendEmail(
		List<String> fieldValues, PortletPreferences prefs) {

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

	protected boolean validate(
		List<String> fieldValues, PortletPreferences prefs) {

		for (int i = 1; i < WebFormUtil.MAX_FIELDS; i++) {
			String fieldValue = fieldValues.get(i - 1);

			String fieldLabel = prefs.getValue(
				"fieldLabel" + i, StringPool.BLANK);
			boolean fieldOptional = GetterUtil.getBoolean(
				prefs.getValue("fieldOptional" + i, StringPool.BLANK));

			if (!fieldOptional && Validator.isNotNull(fieldLabel) &&
				Validator.isNull(fieldValue)) {

				return false;
			}
		}

		return true;
	}

	private static Log _log = LogFactory.getLog(ViewAction.class);

}