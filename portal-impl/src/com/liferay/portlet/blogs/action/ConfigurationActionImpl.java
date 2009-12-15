/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.action;

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
 * @author Jorge Ferrer
 * @author Thiago Moreira
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
		else if (tabs2.equals("blogs-added-email")) {
			updateEmailBlogsAdded(actionRequest, preferences);
		}
		else if (tabs2.equals("blogs-updated-email")) {
			updateEmailBlogsUpdated(actionRequest, preferences);
		}
		else if (tabs2.equals("rss")) {
			updateRSS(actionRequest, preferences);
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

		return "/html/portlet/blogs/configuration.jsp";
	}

	protected void updateDisplaySettings(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		int pageDelta = ParamUtil.getInteger(actionRequest, "pageDelta");
		String pageDisplayStyle = ParamUtil.getString(
			actionRequest, "pageDisplayStyle");
		boolean enableFlags = ParamUtil.getBoolean(
			actionRequest, "enableFlags");
		boolean enableRatings = ParamUtil.getBoolean(
			actionRequest, "enableRatings");
		boolean enableComments = ParamUtil.getBoolean(
			actionRequest, "enableComments");
		boolean enableCommentRatings = ParamUtil.getBoolean(
			actionRequest, "enableCommentRatings");

		preferences.setValue("page-delta", String.valueOf(pageDelta));
		preferences.setValue("page-display-style", pageDisplayStyle);
		preferences.setValue("enable-flags", String.valueOf(enableFlags));
		preferences.setValue("enable-ratings", String.valueOf(enableRatings));
		preferences.setValue("enable-comments", String.valueOf(enableComments));
		preferences.setValue(
			"enable-comment-ratings", String.valueOf(enableCommentRatings));
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
		else if (!Validator.isEmailAddress(emailFromAddress) &&
				 !Validator.isVariableTerm(emailFromAddress)) {

			SessionErrors.add(actionRequest, "emailFromAddress");
		}
		else {
			preferences.setValue("email-from-name", emailFromName);
			preferences.setValue("email-from-address", emailFromAddress);
		}
	}

	protected void updateEmailBlogsAdded(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		boolean emailBlogsAddedEnabled = ParamUtil.getBoolean(
			actionRequest, "emailBlogsAddedEnabled");
		String emailBlogsAddedSubject = ParamUtil.getString(
			actionRequest, "emailBlogsAddedSubject");
		String emailBlogsAddedBody = ParamUtil.getString(
			actionRequest, "emailBlogsAddedBody");

		if (Validator.isNull(emailBlogsAddedSubject)) {
			SessionErrors.add(actionRequest, "emailBlogsAddedSubject");
		}
		else if (Validator.isNull(emailBlogsAddedBody)) {
			SessionErrors.add(actionRequest, "emailBlogsAddedBody");
		}
		else {
			preferences.setValue(
				"email-blogs-added-enabled",
				String.valueOf(emailBlogsAddedEnabled));
			preferences.setValue(
				"email-blogs-added-subject", emailBlogsAddedSubject);
			preferences.setValue("email-blogs-added-body", emailBlogsAddedBody);
		}
	}

	protected void updateEmailBlogsUpdated(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		boolean emailBlogsUpdatedEnabled = ParamUtil.getBoolean(
			actionRequest, "emailBlogsUpdatedEnabled");
		String emailBlogsUpdatedSubject = ParamUtil.getString(
			actionRequest, "emailBlogsUpdatedSubject");
		String emailBlogsUpdatedBody = ParamUtil.getString(
			actionRequest, "emailBlogsUpdatedBody");

		if (Validator.isNull(emailBlogsUpdatedSubject)) {
			SessionErrors.add(actionRequest, "emailBlogsUpdatedSubject");
		}
		else if (Validator.isNull(emailBlogsUpdatedBody)) {
			SessionErrors.add(actionRequest, "emailBlogsUpdatedBody");
		}
		else {
			preferences.setValue(
				"email-blogs-updated-enabled",
				String.valueOf(emailBlogsUpdatedEnabled));
			preferences.setValue(
				"email-blogs-updated-subject", emailBlogsUpdatedSubject);
			preferences.setValue(
				"email-blogs-updated-body", emailBlogsUpdatedBody);
		}
	}

	protected void updateRSS(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		int rssDelta = ParamUtil.getInteger(actionRequest, "rssDelta");
		String rssDisplayStyle = ParamUtil.getString(
			actionRequest, "rssDisplayStyle");
		String rssFormat = ParamUtil.getString(actionRequest, "rssFormat");

		preferences.setValue("rss-delta", String.valueOf(rssDelta));
		preferences.setValue("rss-display-style", rssDisplayStyle);
		preferences.setValue("rss-format", rssFormat);
	}

}