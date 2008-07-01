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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
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

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletResource);

		String tabs2 = ParamUtil.getString(actionRequest, "tabs2");

		if (tabs2.equals("email-from")) {
			updateEmailFrom(actionRequest, prefs);
		}
		else if (tabs2.equals("article-approval-denied-email")) {
			updateEmailArticleApprovalDenied(actionRequest, prefs);
		}
		else if (tabs2.equals("article-approval-granted-email")) {
			updateEmailArticleApprovalGranted(actionRequest, prefs);
		}
		else if (tabs2.equals("article-approval-requested-email")) {
			updateEmailArticleApprovalRequested(actionRequest, prefs);
		}
		else if (tabs2.equals("article-review-email")) {
			updateEmailArticleReview(actionRequest, prefs);
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

		return "/html/portlet/journal/configuration.jsp";
	}

	protected void updateEmailFrom(
			ActionRequest actionRequest, PortletPreferences prefs)
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
			prefs.setValue("email-from-name", emailFromName);
			prefs.setValue("email-from-address", emailFromAddress);
		}
	}

	protected void updateEmailArticleApprovalDenied(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		boolean emailArticleApprovalDeniedEnabled = ParamUtil.getBoolean(
			actionRequest, "emailArticleApprovalDeniedEnabled");
		String emailArticleApprovalDeniedSubject = ParamUtil.getString(
			actionRequest, "emailArticleApprovalDeniedSubject");
		String emailArticleApprovalDeniedBody = ParamUtil.getString(
			actionRequest, "emailArticleApprovalDeniedBody");

		if (Validator.isNull(emailArticleApprovalDeniedSubject)) {
			SessionErrors.add(
				actionRequest, "emailArticleApprovalDeniedSubject");
		}
		else if (Validator.isNull(emailArticleApprovalDeniedBody)) {
			SessionErrors.add(actionRequest, "emailArticleApprovalDeniedBody");
		}
		else {
			prefs.setValue(
				"email-article-approval-denied-enabled",
				String.valueOf(emailArticleApprovalDeniedEnabled));
			prefs.setValue(
				"email-article-approval-denied-subject",
				emailArticleApprovalDeniedSubject);
			prefs.setValue(
				"email-article-approval-denied-body",
				emailArticleApprovalDeniedBody);
		}
	}

	protected void updateEmailArticleApprovalGranted(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		boolean emailArticleApprovalGrantedEnabled = ParamUtil.getBoolean(
			actionRequest, "emailArticleApprovalGrantedEnabled");
		String emailArticleApprovalGrantedSubject = ParamUtil.getString(
			actionRequest, "emailArticleApprovalGrantedSubject");
		String emailArticleApprovalGrantedBody = ParamUtil.getString(
			actionRequest, "emailArticleApprovalGrantedBody");

		if (Validator.isNull(emailArticleApprovalGrantedSubject)) {
			SessionErrors.add(
				actionRequest, "emailArticleApprovalGrantedSubject");
		}
		else if (Validator.isNull(emailArticleApprovalGrantedBody)) {
			SessionErrors.add(actionRequest, "emailArticleApprovalGrantedBody");
		}
		else {
			prefs.setValue(
				"email-article-approval-granted-enabled",
				String.valueOf(emailArticleApprovalGrantedEnabled));
			prefs.setValue(
				"email-article-approval-granted-subject",
				emailArticleApprovalGrantedSubject);
			prefs.setValue(
				"email-article-approval-granted-body",
				emailArticleApprovalGrantedBody);
		}
	}

	protected void updateEmailArticleApprovalRequested(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		boolean emailArticleApprovalRequestedEnabled = ParamUtil.getBoolean(
			actionRequest, "emailArticleApprovalRequestedEnabled");
		String emailArticleApprovalRequestedSubject = ParamUtil.getString(
			actionRequest, "emailArticleApprovalRequestedSubject");
		String emailArticleApprovalRequestedBody = ParamUtil.getString(
			actionRequest, "emailArticleApprovalRequestedBody");

		if (Validator.isNull(emailArticleApprovalRequestedSubject)) {
			SessionErrors.add(
				actionRequest, "emailArticleApprovalRequestedSubject");
		}
		else if (Validator.isNull(emailArticleApprovalRequestedBody)) {
			SessionErrors.add(
				actionRequest, "emailArticleApprovalRequestedBody");
		}
		else {
			prefs.setValue(
				"email-article-approval-requested-enabled",
				String.valueOf(emailArticleApprovalRequestedEnabled));
			prefs.setValue(
				"email-article-approval-requested-subject",
				emailArticleApprovalRequestedSubject);
			prefs.setValue(
				"email-article-approval-requested-body",
				emailArticleApprovalRequestedBody);
		}
	}

	protected void updateEmailArticleReview(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		boolean emailArticleReviewEnabled = ParamUtil.getBoolean(
			actionRequest, "emailArticleReviewEnabled");
		String emailArticleReviewSubject = ParamUtil.getString(
			actionRequest, "emailArticleReviewSubject");
		String emailArticleReviewBody = ParamUtil.getString(
			actionRequest, "emailArticleReviewBody");

		if (Validator.isNull(emailArticleReviewSubject)) {
			SessionErrors.add(actionRequest, "emailArticleReviewSubject");
		}
		else if (Validator.isNull(emailArticleReviewBody)) {
			SessionErrors.add(actionRequest, "emailArticleReviewBody");
		}
		else {
			prefs.setValue(
				"email-article-review-enabled",
				String.valueOf(emailArticleReviewEnabled));
			prefs.setValue(
				"email-article-review-subject", emailArticleReviewSubject);
			prefs.setValue("email-article-review-body", emailArticleReviewBody);
		}
	}

}