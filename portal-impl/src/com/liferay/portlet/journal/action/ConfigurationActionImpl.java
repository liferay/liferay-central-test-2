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

package com.liferay.portlet.journal.action;

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

		if (tabs2.equals("email-from")) {
			updateEmailFrom(actionRequest, preferences);
		}
		else if (tabs2.equals("web-content-added-email")) {
			updateEmailArticleAdded(actionRequest, preferences);
		}
		else if (tabs2.equals("web-content-approval-denied-email")) {
			updateEmailArticleApprovalDenied(actionRequest, preferences);
		}
		else if (tabs2.equals("web-content-approval-granted-email")) {
			updateEmailArticleApprovalGranted(actionRequest, preferences);
		}
		else if (tabs2.equals("web-content-approval-requested-email")) {
			updateEmailArticleApprovalRequested(actionRequest, preferences);
		}
		else if (tabs2.equals("web-content-review-email")) {
			updateEmailArticleReview(actionRequest, preferences);
		}
		else if (tabs2.equals("web-content-updated-email")) {
			updateEmailArticleUpdated(actionRequest, preferences);
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

		return "/html/portlet/journal/configuration.jsp";
	}

	protected void updateEmailArticleAdded(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		boolean emailArticleAddedEnabled = ParamUtil.getBoolean(
			actionRequest, "emailArticleAddedEnabled");
		String emailArticleAddedSubject = ParamUtil.getString(
			actionRequest, "emailArticleAddedSubject");
		String emailArticleAddedBody = ParamUtil.getString(
			actionRequest, "emailArticleAddedBody");

		if (Validator.isNull(emailArticleAddedSubject)) {
			SessionErrors.add(actionRequest, "emailArticleAddedSubject");
		}
		else if (Validator.isNull(emailArticleAddedBody)) {
			SessionErrors.add(actionRequest, "emailArticleAddedBody");
		}
		else {
			preferences.setValue(
				"email-article-added-enabled",
				String.valueOf(emailArticleAddedEnabled));
			preferences.setValue(
				"email-article-added-subject", emailArticleAddedSubject);
			preferences.setValue(
				"email-article-added-body", emailArticleAddedBody);
		}
	}

	protected void updateEmailArticleApprovalDenied(
			ActionRequest actionRequest, PortletPreferences preferences)
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
			preferences.setValue(
				"email-article-approval-denied-enabled",
				String.valueOf(emailArticleApprovalDeniedEnabled));
			preferences.setValue(
				"email-article-approval-denied-subject",
				emailArticleApprovalDeniedSubject);
			preferences.setValue(
				"email-article-approval-denied-body",
				emailArticleApprovalDeniedBody);
		}
	}

	protected void updateEmailArticleApprovalGranted(
			ActionRequest actionRequest, PortletPreferences preferences)
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
			preferences.setValue(
				"email-article-approval-granted-enabled",
				String.valueOf(emailArticleApprovalGrantedEnabled));
			preferences.setValue(
				"email-article-approval-granted-subject",
				emailArticleApprovalGrantedSubject);
			preferences.setValue(
				"email-article-approval-granted-body",
				emailArticleApprovalGrantedBody);
		}
	}

	protected void updateEmailArticleApprovalRequested(
			ActionRequest actionRequest, PortletPreferences preferences)
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
			preferences.setValue(
				"email-article-approval-requested-enabled",
				String.valueOf(emailArticleApprovalRequestedEnabled));
			preferences.setValue(
				"email-article-approval-requested-subject",
				emailArticleApprovalRequestedSubject);
			preferences.setValue(
				"email-article-approval-requested-body",
				emailArticleApprovalRequestedBody);
		}
	}

	protected void updateEmailArticleReview(
			ActionRequest actionRequest, PortletPreferences preferences)
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
			preferences.setValue(
				"email-article-review-enabled",
				String.valueOf(emailArticleReviewEnabled));
			preferences.setValue(
				"email-article-review-subject", emailArticleReviewSubject);
			preferences.setValue(
				"email-article-review-body", emailArticleReviewBody);
		}
	}

	protected void updateEmailArticleUpdated(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		boolean emailArticleUpdatedEnabled = ParamUtil.getBoolean(
			actionRequest, "emailArticleUpdatedEnabled");
		String emailArticleUpdatedSubject = ParamUtil.getString(
			actionRequest, "emailArticleUpdatedSubject");
		String emailArticleUpdatedBody = ParamUtil.getString(
			actionRequest, "emailArticleUpdatedBody");

		if (Validator.isNull(emailArticleUpdatedSubject)) {
			SessionErrors.add(actionRequest, "emailArticleUpdatedSubject");
		}
		else if (Validator.isNull(emailArticleUpdatedBody)) {
			SessionErrors.add(actionRequest, "emailArticleUpdatedBody");
		}
		else {
			preferences.setValue(
				"email-article-updated-enabled",
				String.valueOf(emailArticleUpdatedEnabled));
			preferences.setValue(
				"email-article-updated-subject", emailArticleUpdatedSubject);
			preferences.setValue(
				"email-article-updated-body", emailArticleUpdatedBody);
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
		else if (!Validator.isEmailAddress(emailFromAddress)) {
			SessionErrors.add(actionRequest, "emailFromAddress");
		}
		else {
			preferences.setValue("email-from-name", emailFromName);
			preferences.setValue("email-from-address", emailFromAddress);
		}
	}

}