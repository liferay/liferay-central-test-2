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

package com.liferay.portlet.iframe.action;

import com.liferay.portal.kernel.portlet.BaseConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
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

		String src = ParamUtil.getString(actionRequest, "src");

		if (!src.startsWith("/") &&
			!StringUtil.startsWith(src, "http://") &&
			!StringUtil.startsWith(src, "https://") &&
			!StringUtil.startsWith(src, "mhtml://")) {

			src = HttpUtil.getProtocol(actionRequest) + "://" + src;
		}

		boolean relative = ParamUtil.getBoolean(actionRequest, "relative");

		boolean auth = ParamUtil.getBoolean(actionRequest, "auth");
		String authType = ParamUtil.getString(actionRequest, "authType");
		String formMethod = ParamUtil.getString(actionRequest, "formMethod");
		String userName = ParamUtil.getString(actionRequest, "userName");
		String userNameField = ParamUtil.getString(
			actionRequest, "userNameField");
		String password = ParamUtil.getString(actionRequest, "password");
		String passwordField = ParamUtil.getString(
			actionRequest, "passwordField");
		String hiddenVariables = ParamUtil.getString(
			actionRequest, "hiddenVariables");

		String[] htmlAttributes = StringUtil.split(ParamUtil.getString(
			actionRequest, "htmlAttributes"), StringPool.NEW_LINE);

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletResource);

		preferences.setValue("src", src);
		preferences.setValue("relative", String.valueOf(relative));

		preferences.setValue("auth", String.valueOf(auth));
		preferences.setValue("auth-type", authType);
		preferences.setValue("form-method", formMethod);
		preferences.setValue("user-name", userName);
		preferences.setValue("user-name-field", userNameField);
		preferences.setValue("password", password);
		preferences.setValue("password-field", passwordField);
		preferences.setValue("hidden-variables", hiddenVariables);

		for (String htmlAttribute : htmlAttributes) {
			int pos = htmlAttribute.indexOf(StringPool.EQUAL);

			if (pos == -1) {
				continue;
			}

			String key = htmlAttribute.substring(0, pos);
			String value = htmlAttribute.substring(
				pos + 1, htmlAttribute.length());

			preferences.setValue(key, value);
		}

		preferences.store();

		SessionMessages.add(
			actionRequest, portletConfig.getPortletName() + ".doConfigure");
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/portlet/iframe/configuration.jsp";
	}

}