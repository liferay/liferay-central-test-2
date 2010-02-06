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

package com.liferay.portlet.wiki.action;

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
 * @author Bruno Farache
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
		else if (tabs2.equals("page-added-email")) {
			updateEmailPageAdded(actionRequest, preferences);
		}
		else if (tabs2.equals("page-updated-email")) {
			updateEmailPageUpdated(actionRequest, preferences);
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

		return "/html/portlet/wiki/configuration.jsp";
	}

	protected void updateDisplaySettings(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		boolean enablePageRatings = ParamUtil.getBoolean(
			actionRequest, "enablePageRatings");
		boolean enableComments = ParamUtil.getBoolean(
			actionRequest, "enableComments");
		boolean enableCommentRatings = ParamUtil.getBoolean(
			actionRequest, "enableCommentRatings");
		String visibleNodes = ParamUtil.getString(
			actionRequest, "visibleNodes");
		String hiddenNodes = ParamUtil.getString(actionRequest, "hiddenNodes");

		if (Validator.isNull(visibleNodes)) {
			SessionErrors.add(actionRequest, "visibleNodesCount");
		}
		else {
			preferences.setValue(
				"enable-page-ratings", String.valueOf(enablePageRatings));
			preferences.setValue(
				"enable-comments", String.valueOf(enableComments));
			preferences.setValue(
				"enable-comment-ratings", String.valueOf(enableCommentRatings));
			preferences.setValue("visible-nodes", visibleNodes);
			preferences.setValue("hidden-nodes", hiddenNodes);
		}
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

	protected void updateEmailPageAdded(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		boolean emailPageAddedEnabled = ParamUtil.getBoolean(
			actionRequest, "emailPageAddedEnabled");
		String emailPageAddedSubjectPrefix = ParamUtil.getString(
			actionRequest, "emailPageAddedSubjectPrefix");
		String emailPageAddedBody = ParamUtil.getString(
			actionRequest, "emailPageAddedBody");
		String emailPageAddedSignature = ParamUtil.getString(
			actionRequest, "emailPageAddedSignature");

		if (Validator.isNull(emailPageAddedSubjectPrefix)) {
			SessionErrors.add(actionRequest, "emailPageAddedSubjectPrefix");
		}
		else if (Validator.isNull(emailPageAddedBody)) {
			SessionErrors.add(actionRequest, "emailPageAddedBody");
		}
		else {
			preferences.setValue(
				"email-page-added-enabled",
				String.valueOf(emailPageAddedEnabled));
			preferences.setValue(
				"email-page-added-subject-prefix", emailPageAddedSubjectPrefix);
			preferences.setValue("email-page-added-body", emailPageAddedBody);
			preferences.setValue(
				"email-page-added-signature", emailPageAddedSignature);
		}
	}

	protected void updateEmailPageUpdated(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		boolean emailPageUpdatedEnabled = ParamUtil.getBoolean(
			actionRequest, "emailPageUpdatedEnabled");
		String emailPageUpdatedSubjectPrefix = ParamUtil.getString(
			actionRequest, "emailPageUpdatedSubjectPrefix");
		String emailPageUpdatedBody = ParamUtil.getString(
			actionRequest, "emailPageUpdatedBody");
		String emailPageUpdatedSignature = ParamUtil.getString(
			actionRequest, "emailPageUpdatedSignature");

		if (Validator.isNull(emailPageUpdatedSubjectPrefix)) {
			SessionErrors.add(actionRequest, "emailPageUpdatedSubjectPrefix");
		}
		else if (Validator.isNull(emailPageUpdatedBody)) {
			SessionErrors.add(actionRequest, "emailPageUpdatedBody");
		}
		else {
			preferences.setValue(
				"email-page-updated-enabled",
				String.valueOf(emailPageUpdatedEnabled));
			preferences.setValue(
				"email-page-updated-subject-prefix",
				emailPageUpdatedSubjectPrefix);
			preferences.setValue(
				"email-page-updated-body", emailPageUpdatedBody);
			preferences.setValue(
				"email-page-updated-signature", emailPageUpdatedSignature);
		}
	}

	protected void updateRSS(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		int rssDelta = ParamUtil.getInteger(actionRequest, "rssDelta");
		String rssDisplayStyle = ParamUtil.getString(
			actionRequest, "rssDisplayStyle");

		preferences.setValue("rss-delta", String.valueOf(rssDelta));
		preferences.setValue("rss-display-style", rssDisplayStyle);
	}

}