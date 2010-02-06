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

package com.liferay.portlet.login.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.BaseConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="ConfigurationActionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class ConfigurationActionImpl extends BaseConfigurationAction {

	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (!cmd.equals(Constants.UPDATE)) {
			return;
		}

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletResource);

		String tabs1 = ParamUtil.getString(actionRequest, "tabs1");

		if (tabs1.equals("general")) {
			updateGeneral(actionRequest, preferences);
		}
		else if (tabs1.equals("email-notifications")) {
			updateEmailNotifications(actionRequest, preferences);
		}

		SessionMessages.add(
			actionRequest, portletConfig.getPortletName() + ".doConfigure");
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/portlet/login/configuration.jsp";
	}

	protected void updateEmailNotifications(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String tabs2 = ParamUtil.getString(actionRequest, "tabs2");

		if (tabs2.equals("password-changed-notification")) {
			String languageId = LanguageUtil.getLanguageId(actionRequest);

			String emailPasswordSentEnabled = ParamUtil.getString(
				actionRequest, "emailPasswordSentEnabled");
			String emailPasswordSentSubject = ParamUtil.getString(
				actionRequest, "emailPasswordSentSubject_" + languageId);
			String emailPasswordSentBody = ParamUtil.getString(
				actionRequest, "emailPasswordSentBody_" + languageId);

			preferences.setValue(
				"emailPasswordSentEnabled", emailPasswordSentEnabled);
			preferences.setValue(
				"emailPasswordSentSubject_" + languageId,
				emailPasswordSentSubject);
			preferences.setValue(
				"emailPasswordSentBody_" + languageId, emailPasswordSentBody);

			preferences.store();
		}
		else {
			String emailFromName = ParamUtil.getString(
				actionRequest, "emailFromName");
			String emailFromAddress = ParamUtil.getString(
				actionRequest, "emailFromAddress");

			preferences.setValue("emailFromName", emailFromName);

			if (Validator.isNotNull(emailFromAddress) &&
				!Validator.isEmailAddress(emailFromAddress)) {

				SessionErrors.add(actionRequest, "emailFromAddress");
			}
			else {
				preferences.setValue("emailFromName", emailFromName);
				preferences.setValue("emailFromAddress", emailFromAddress);

				preferences.store();
			}
		}
	}

	protected void updateGeneral(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String authType = ParamUtil.getString(actionRequest, "authType");

		preferences.setValue("authType", authType);

		preferences.store();
	}

}