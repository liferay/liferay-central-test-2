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

package com.liferay.portlet.webproxy.action;

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

		String initUrl = ParamUtil.getString(actionRequest, "initUrl");

		if (!initUrl.startsWith("/") &&
			!StringUtil.startsWith(initUrl, "http://") &&
			!StringUtil.startsWith(initUrl, "https://") &&
			!StringUtil.startsWith(initUrl, "mhtml://")) {

			initUrl = HttpUtil.getProtocol(actionRequest) + "://" + initUrl;
		}

		String scope = ParamUtil.getString(actionRequest, "scope");
		String proxyHost = ParamUtil.getString(actionRequest, "proxyHost");
		String proxyPort = ParamUtil.getString(actionRequest, "proxyPort");
		String proxyAuthentication = ParamUtil.getString(
			actionRequest, "proxyAuthentication");
		String proxyAuthenticationUsername = ParamUtil.getString(
			actionRequest, "proxyAuthenticationUsername");
		String proxyAuthenticationPassword = ParamUtil.getString(
			actionRequest, "proxyAuthenticationPassword");
		String proxyAuthenticationHost = ParamUtil.getString(
			actionRequest, "proxyAuthenticationHost");
		String proxyAuthenticationDomain = ParamUtil.getString(
			actionRequest, "proxyAuthenticationDomain");
		String stylesheet = ParamUtil.getString(actionRequest, "stylesheet");

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletResource);

		preferences.setValue("initUrl", initUrl);
		preferences.setValue("scope", scope);
		preferences.setValue("proxyHost", proxyHost);
		preferences.setValue("proxyPort", proxyPort);
		preferences.setValue("proxyAuthentication", proxyAuthentication);
		preferences.setValue(
			"proxyAuthenticationUsername", proxyAuthenticationUsername);
		preferences.setValue(
			"proxyAuthenticationPassword", proxyAuthenticationPassword);
		preferences.setValue(
			"proxyAuthenticationHost", proxyAuthenticationHost);
		preferences.setValue(
			"proxyAuthenticationDomain", proxyAuthenticationDomain);
		preferences.setValue("stylesheet", stylesheet);

		preferences.store();

		SessionMessages.add(
			actionRequest, portletConfig.getPortletName() + ".doConfigure");
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/portlet/web_proxy/configuration.jsp";
	}

}