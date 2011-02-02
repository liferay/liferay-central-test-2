/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.login.action;

import com.liferay.portal.CompanyMaxUsersException;
import com.liferay.portal.ContactFirstNameException;
import com.liferay.portal.ContactFullNameException;
import com.liferay.portal.ContactLastNameException;
import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.EmailAddressException;
import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.PortletURLImpl;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Sergio González
 */
public class CreateAnonymousAccountAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (actionRequest.getRemoteUser() != null) {
			actionResponse.sendRedirect(themeDisplay.getPathMain());

			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");

		PortletURL portletURL = new PortletURLImpl(
			(ActionRequestImpl)actionRequest, PortletKeys.LOGIN,
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		portletURL.setParameter("struts_action", "/login/login_redirect");
		portletURL.setParameter("emailAddress", emailAddress);

		try {
			if (cmd.equals(Constants.ADD)) {
				addAnonymousUser(actionRequest, actionResponse);

				sendRedirect(
					actionRequest, actionResponse, portletURL.toString());
			}
		}
		catch (Exception e) {
			if (e instanceof DuplicateUserEmailAddressException) {
				User user = UserLocalServiceUtil.getUserByEmailAddress(
					themeDisplay.getCompanyId(), emailAddress);

				if (user.getStatus() != WorkflowConstants.STATUS_INCOMPLETE) {
					SessionErrors.add(actionRequest, e.getClass().getName());
				}
				else {
					sendRedirect(
						actionRequest, actionResponse, portletURL.toString());
				}
			}
			else if (e instanceof CaptchaTextException ||
				e instanceof CompanyMaxUsersException ||
				e instanceof ContactFirstNameException ||
				e instanceof ContactFullNameException ||
				e instanceof ContactLastNameException ||
				e instanceof EmailAddressException ||
				e instanceof ReservedUserEmailAddressException ||
				e instanceof UserEmailAddressException) {

				SessionErrors.add(actionRequest, e.getClass().getName(), e);
			}
			else {
				_log.error("Error creating anonymous account", e);

				PortalUtil.sendError(e, actionRequest, actionResponse);
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		renderResponse.setTitle(
			themeDisplay.translate("anonymous-account"));

		return mapping.findForward("portlet.login.create_anonymous_account");
	}

	protected void addAnonymousUser(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = true;
		String screenName = null;
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");
		long facebookId = 0;
		String openId = StringPool.BLANK;
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = 0;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = null;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		if (PropsValues.CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT) {
			CaptchaUtil.check(actionRequest);
		}

		User user = UserServiceUtil.addUser(
			themeDisplay.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			themeDisplay.getLocale(), firstName, null, lastName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
			serviceContext);

		UserLocalServiceUtil.updateStatus(
			user.getUserId(), WorkflowConstants.STATUS_INCOMPLETE);

		// Session messages

		SessionMessages.add(request, "user_added", user.getEmailAddress());
		SessionMessages.add(
			request, "user_added_password", user.getPasswordUnencrypted());
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

	private static Log _log = LogFactoryUtil.getLog(
		CreateAnonymousAccountAction.class);

}