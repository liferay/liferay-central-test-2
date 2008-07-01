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

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.webform.util.WebFormUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="ConfigurationActionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class ConfigurationActionImpl implements ConfigurationAction {

	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (!cmd.equals(Constants.UPDATE)) {
			return;
		}

		String title = ParamUtil.getString(actionRequest, "title");
		String description = ParamUtil.getString(actionRequest, "description");
		boolean requireCaptcha = ParamUtil.getBoolean(
			actionRequest, "requireCaptcha");
		String successURL = ParamUtil.getString(actionRequest, "successURL");

		boolean sendAsEmail = ParamUtil.getBoolean(
			actionRequest, "sendAsEmail");
		String subject = ParamUtil.getString(actionRequest, "subject");
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");

		boolean saveToDatabase = ParamUtil.getBoolean(
			actionRequest, "saveToDatabase");

		boolean saveToFile = ParamUtil.getBoolean(actionRequest, "saveToFile");
		String fileName = ParamUtil.getString(actionRequest, "fileName");

		boolean updateFields = ParamUtil.getBoolean(
			actionRequest, "updateFields");

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletResource);

		if (Validator.isNull(title)) {
			SessionErrors.add(actionRequest, "titleRequired");
		}

		if (Validator.isNull(subject)) {
			SessionErrors.add(actionRequest, "subjectRequired");
		}

		if (!sendAsEmail && !saveToDatabase && !saveToFile) {
			SessionErrors.add(actionRequest, "handlingRequired");
		}

		if (sendAsEmail) {
			if (Validator.isNull(emailAddress)) {
				SessionErrors.add(actionRequest, "emailAddressRequired");
			}
			else if (!Validator.isEmailAddress(emailAddress)) {
				SessionErrors.add(actionRequest, "emailAddressInvalid");
			}
		}

		if (saveToFile) {

			// Check if server can create a file as specified

			try {
				FileOutputStream fos = new FileOutputStream(fileName, true);

				fos.close();
			}
			catch (SecurityException es) {
				SessionErrors.add(actionRequest, "fileNameInvalid");
			}
			catch (FileNotFoundException fnfe) {
				SessionErrors.add(actionRequest, "fileNameInvalid");
			}
		}

		if (!SessionErrors.isEmpty(actionRequest)) {
			return;
		}

		prefs.setValue("title", title);
		prefs.setValue("description", description);
		prefs.setValue("requireCaptcha", String.valueOf(requireCaptcha));
		prefs.setValue("successURL", successURL);
		prefs.setValue("sendAsEmail", String.valueOf(sendAsEmail));
		prefs.setValue("subject", subject);
		prefs.setValue("emailAddress", emailAddress);
		prefs.setValue("saveToDatabase", String.valueOf(saveToDatabase));
		prefs.setValue("saveToFile", String.valueOf(saveToFile));
		prefs.setValue("fileName", fileName);

		if (updateFields) {
			int i = 1;

			String databaseTableName = WebFormUtil.getNewDatabaseTableName(
				portletResource);

			prefs.setValue("databaseTableName", databaseTableName);

			String fieldLabel = ParamUtil.getString(
				actionRequest, "fieldLabel" + i);
			String fieldType = ParamUtil.getString(
				actionRequest, "fieldType" + i);
			boolean fieldOptional = ParamUtil.getBoolean(
				actionRequest, "fieldOptional" + i);
			String fieldOptions = ParamUtil.getString(
				actionRequest, "fieldOptions" + i);

			while ((i == 1) || (Validator.isNotNull(fieldLabel))) {
				prefs.setValue("fieldLabel" + i, fieldLabel);
				prefs.setValue("fieldType" + i, fieldType);
				prefs.setValue(
					"fieldOptional" + i, String.valueOf(fieldOptional));
				prefs.setValue("fieldOptions" + i, fieldOptions);

				i++;

				fieldLabel = ParamUtil.getString(
					actionRequest, "fieldLabel" + i);
				fieldType = ParamUtil.getString(actionRequest, "fieldType" + i);
				fieldOptional = ParamUtil.getBoolean(
					actionRequest, "fieldOptional" + i);
				fieldOptions = ParamUtil.getString(
					actionRequest, "fieldOptions" + i);
			}

			// Clear previous preferences that are now blank

			fieldLabel = prefs.getValue("fieldLabel" + i, StringPool.BLANK);

			while (Validator.isNotNull(fieldLabel)) {
				prefs.setValue("fieldLabel" + i, StringPool.BLANK);
				prefs.setValue("fieldType" + i, StringPool.BLANK);
				prefs.setValue("fieldOptional" + i, StringPool.BLANK);
				prefs.setValue("fieldOptions" + i, StringPool.BLANK);

				i++;

				fieldLabel = prefs.getValue("fieldLabel" + i, StringPool.BLANK);
			}
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			prefs.store();

			SessionMessages.add(
				actionRequest, portletConfig.getPortletName() + ".doConfigure");
		}
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/portlet/web_form/configuration.jsp";
	}

}