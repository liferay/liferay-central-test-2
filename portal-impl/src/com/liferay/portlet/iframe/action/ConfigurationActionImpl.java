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

package com.liferay.portlet.iframe.action;

import com.liferay.portal.kernel.portlet.BaseConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
			actionRequest, "htmlAttributes"), "\n");

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

		for (int i = 0; i < htmlAttributes.length; i++) {
			int pos = htmlAttributes[i].indexOf("=");

			if (pos != -1) {
				String key = htmlAttributes[i].substring(0, pos);
				String value = htmlAttributes[i].substring(
					pos + 1, htmlAttributes[i].length());

				preferences.setValue(key, value);
			}
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