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
		boolean enableRatings = ParamUtil.getBoolean(
			actionRequest, "enableRatings");
		boolean enableComments = ParamUtil.getBoolean(
			actionRequest, "enableComments");

		preferences.setValue("tabs1-default", tabs1Default);
		preferences.setValue("summary-tab-orientation", summaryTabOrientation);
		preferences.setValue(
			"summary-tab-show-mini-month", summaryTabShowMiniMonth);
		preferences.setValue(
			"summary-tab-show-todays-events", summaryTabShowTodaysEvents);
		preferences.setValue("enable-comments", String.valueOf(enableComments));
		preferences.setValue("enable-ratings", String.valueOf(enableRatings));
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