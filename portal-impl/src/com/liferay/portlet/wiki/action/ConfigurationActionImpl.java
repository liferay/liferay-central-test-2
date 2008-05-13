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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionMessages;

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
 *
 */
public class ConfigurationActionImpl implements ConfigurationAction {

	public void processAction(
			PortletConfig config, ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		if (!cmd.equals(Constants.UPDATE)) {
			return;
		}

		String portletResource = ParamUtil.getString(req, "portletResource");

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getPortletSetup(req, portletResource);

		String tabs2 = ParamUtil.getString(req, "tabs2");

		if (tabs2.equals("email-from")) {
			updateEmailFrom(req, prefs);
		}
		else if (tabs2.equals("page-added-email")) {
			updateEmailPageAdded(req, prefs);
		}
		else if (tabs2.equals("page-updated-email")) {
			updateEmailPageUpdated(req, prefs);
		}
		else if (tabs2.equals("ratings")) {
			updateRatings(req, prefs);
		}
		else if (tabs2.equals("rss")) {
			updateRSS(req, prefs);
		}

		if (SessionErrors.isEmpty(req)) {
			prefs.store();

			SessionMessages.add(req, config.getPortletName() + ".doConfigure");
		}
	}

	public String render(
			PortletConfig config, RenderRequest req, RenderResponse res)
		throws Exception {

		return "/html/portlet/wiki/configuration.jsp";
	}

	protected void updateEmailFrom(ActionRequest req, PortletPreferences prefs)
		throws Exception {

		String emailFromName = ParamUtil.getString(req, "emailFromName");
		String emailFromAddress = ParamUtil.getString(req, "emailFromAddress");

		if (Validator.isNull(emailFromName)) {
			SessionErrors.add(req, "emailFromName");
		}
		else if (!Validator.isEmailAddress(emailFromAddress) &&
				 !Validator.isVariableTerm(emailFromAddress)) {

			SessionErrors.add(req, "emailFromAddress");
		}
		else {
			prefs.setValue("email-from-name", emailFromName);
			prefs.setValue("email-from-address", emailFromAddress);
		}
	}

	protected void updateEmailPageAdded(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {

		boolean emailPageAddedEnabled = ParamUtil.getBoolean(
			req, "emailPageAddedEnabled");
		String emailPageAddedSubjectPrefix = ParamUtil.getString(
			req, "emailPageAddedSubjectPrefix");
		String emailPageAddedBody = ParamUtil.getString(
			req, "emailPageAddedBody");
		String emailPageAddedSignature = ParamUtil.getString(
			req, "emailPageAddedSignature");

		if (Validator.isNull(emailPageAddedSubjectPrefix)) {
			SessionErrors.add(req, "emailPageAddedSubjectPrefix");
		}
		else if (Validator.isNull(emailPageAddedBody)) {
			SessionErrors.add(req, "emailPageAddedBody");
		}
		else {
			prefs.setValue(
				"email-page-added-enabled",
				String.valueOf(emailPageAddedEnabled));
			prefs.setValue(
				"email-page-added-subject-prefix", emailPageAddedSubjectPrefix);
			prefs.setValue("email-page-added-body", emailPageAddedBody);
			prefs.setValue(
				"email-page-added-signature", emailPageAddedSignature);
		}
	}

	protected void updateEmailPageUpdated(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {

		boolean emailPageUpdatedEnabled = ParamUtil.getBoolean(
			req, "emailPageUpdatedEnabled");
		String emailPageUpdatedSubjectPrefix = ParamUtil.getString(
			req, "emailPageUpdatedSubjectPrefix");
		String emailPageUpdatedBody = ParamUtil.getString(
			req, "emailPageUpdatedBody");
		String emailPageUpdatedSignature = ParamUtil.getString(
			req, "emailPageUpdatedSignature");

		if (Validator.isNull(emailPageUpdatedSubjectPrefix)) {
			SessionErrors.add(req, "emailPageUpdatedSubjectPrefix");
		}
		else if (Validator.isNull(emailPageUpdatedBody)) {
			SessionErrors.add(req, "emailPageUpdatedBody");
		}
		else {
			prefs.setValue(
				"email-page-updated-enabled",
				String.valueOf(emailPageUpdatedEnabled));
			prefs.setValue(
				"email-page-updated-subject-prefix",
				emailPageUpdatedSubjectPrefix);
			prefs.setValue("email-page-updated-body", emailPageUpdatedBody);
			prefs.setValue(
				"email-page-updated-signature", emailPageUpdatedSignature);
		}
	}

	protected void updateRatings(ActionRequest req, PortletPreferences prefs)
		throws Exception {

		boolean enableComments = ParamUtil.getBoolean(req, "enableComments");
		boolean enableCommentRatings = ParamUtil.getBoolean(
			req, "enableCommentRatings");

		prefs.setValue("enable-comments", String.valueOf(enableComments));
		prefs.setValue(
			"enable-comment-ratings", String.valueOf(enableCommentRatings));
	}

	protected void updateRSS(ActionRequest req, PortletPreferences prefs)
		throws Exception {

		int rssDelta = ParamUtil.getInteger(req, "rssDelta");
		String rssDisplayStyle = ParamUtil.getString(req, "rssDisplayStyle");

		prefs.setValue("rss-delta", String.valueOf(rssDelta));
		prefs.setValue("rss-display-style", rssDisplayStyle);
	}

}