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

package com.liferay.portlet.calendar.action;

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
 * @author Arcko Yongming Duan
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

		String tabs2 = ParamUtil.getString(actionRequest, "tabs2");

		if (tabs2.equals("display-settings")) {
			updateDisplaySettings(actionRequest, preferences);
		}
		else if (tabs2.equals("email-from")) {
			updateEmailFrom(actionRequest, preferences);
		}
		else if (tabs2.equals("event-reminder-email")) {
			updateEmailEventReminder(actionRequest, preferences);
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			preferences.store();

			SessionMessages.add(
				actionRequest, portletConfig.getPortletName() + ".doConfigure");
		}
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/portlet/calendar/configuration.jsp";
	}

	protected void updateDisplaySettings(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String tabs1Default = ParamUtil.getString(
			actionRequest, "tabs1Default");
		String summaryTabOrientation = ParamUtil.getString(
			actionRequest, "summaryTabOrientation");
		String summaryTabShowMiniMonth = ParamUtil.getString(
			actionRequest, "summaryTabShowMiniMonth");
		String summaryTabShowTodaysEvents = ParamUtil.getString(
			actionRequest, "summaryTabShowTodaysEvents");

		preferences.setValue("tabs1-default", tabs1Default);
		preferences.setValue("summary-tab-orientation", summaryTabOrientation);
		preferences.setValue(
			"summary-tab-show-mini-month", summaryTabShowMiniMonth);
		preferences.setValue(
			"summary-tab-show-todays-events", summaryTabShowTodaysEvents);
	}

	protected void updateEmailFrom(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String emailFromName = ParamUtil.getString(
			actionRequest, "emailFromName");
		String emailFromAddress = ParamUtil.getString(
			actionRequest, "emailFromAddress");

		if (Validator.isNull(emailFromName)) {
			SessionErrors.add(actionRequest, "emailFromName");
		}
		else if (!Validator.isEmailAddress(emailFromAddress)) {
			SessionErrors.add(actionRequest, "emailFromAddress");
		}
		else {
			preferences.setValue("email-from-name", emailFromName);
			preferences.setValue("email-from-address", emailFromAddress);
		}
	}

	protected void updateEmailEventReminder(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		boolean emailEventReminderEnabled = ParamUtil.getBoolean(
			actionRequest, "emailEventReminderEnabled");
		String emailEventReminderSubject = ParamUtil.getString(
			actionRequest, "emailEventReminderSubject");
		String emailEventReminderBody = ParamUtil.getString(
			actionRequest, "emailEventReminderBody");

		if (Validator.isNull(emailEventReminderSubject)) {
			SessionErrors.add(actionRequest, "emailEventReminderSubject");
		}
		else if (Validator.isNull(emailEventReminderBody)) {
			SessionErrors.add(actionRequest, "emailEventReminderBody");
		}
		else {
			preferences.setValue(
				"email-event-reminder-enabled",
				String.valueOf(emailEventReminderEnabled));
			preferences.setValue(
				"email-event-reminder-subject", emailEventReminderSubject);
			preferences.setValue(
				"email-event-reminder-body", emailEventReminderBody);
		}
	}

}