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

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.facebook.FacebookConnectUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

import java.util.Calendar;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Wilson Man
 */
public class FacebookConnectAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long companyId = themeDisplay.getCompanyId();

		if (!FacebookConnectUtil.isEnabled(companyId)) {
			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD)) {
			addUser(actionRequest, actionResponse, themeDisplay);
		}
		else {
			String redirect = HttpUtil.addParameter(
				FacebookConnectUtil.getAuthURL(companyId), "client_id",
				FacebookConnectUtil.getAppId(companyId));

			String facebookRedirect = ParamUtil.getString(
				actionRequest, "redirect");

			String redirect_uri = FacebookConnectUtil.getRedirectURL(
				companyId);

			redirect_uri = HttpUtil.addParameter(
				redirect_uri, "redirect", facebookRedirect);

			redirect = HttpUtil.addParameter(
				redirect, "redirect_uri", redirect_uri);

			redirect = HttpUtil.addParameter(redirect, "scope", "email");

			actionResponse.sendRedirect(redirect);
		}
	}

	public ActionForward strutsExecute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long companyId = themeDisplay.getCompanyId();

		String redirect = ParamUtil.getString(request, "redirect");

		if (!FacebookConnectUtil.isEnabled(companyId)) {
			return null;
		}

		String code = ParamUtil.get(request, "code", StringPool.BLANK);

		String token = getAccessToken(companyId, code, redirect);

		if (Validator.isNotNull(token)) {
			HttpSession session = request.getSession();

			session.setAttribute(WebKeys.FACEBOOK_ACCESS_TOKEN, token);

			setFacebookCredentials(session, companyId, token);
		}

		redirect = getRedirect(request, themeDisplay);

		response.sendRedirect(redirect);

		return null;
	}

	protected void addUser(
			ActionRequest actionRequest, ActionResponse actionResponse,
			ThemeDisplay themeDisplay)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		request = PortalUtil.getOriginalServletRequest(request);

		HttpSession session = request.getSession();

		String token = (String)session.getAttribute(
			WebKeys.FACEBOOK_ACCESS_TOKEN);

		long companyId = themeDisplay.getCompanyId();

		String url = HttpUtil.addParameter(
			FacebookConnectUtil.getGraphURL(companyId) + "/me", "access_token",
			token);

		url = HttpUtil.addParameter(
			url, "fields",
			"email,first_name,last_name,birthday,gender,verified");

		Http.Options options = new Http.Options();

		options.setLocation(url);

		String content = HttpUtil.URLtoString(options);

		if (Validator.isNull(content)) {
			return;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(content);

		if (!jsonObject.getBoolean("verified")) {
			return;
		}

		long creatorUserId = 0;
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		String emailAddress = jsonObject.getString("email");
		long facebookId = jsonObject.getLong("id");
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = jsonObject.getString("first_name");
		String middleName = StringPool.BLANK;
		String lastName = jsonObject.getString("last_name");
		int prefixId = 0;
		int suffixId = 0;
		boolean male = Validator.equals(jsonObject.getString("gender"), "male");
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = true;

		ServiceContext serviceContext = new ServiceContext();

		UserLocalServiceUtil.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		session.setAttribute(WebKeys.FACEBOOK_USER_EMAIL_ADDRESS, emailAddress);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNull(redirect)) {
			redirect = StringPool.SLASH;
		}

		actionResponse.sendRedirect(redirect);
	}

	protected String getAccessToken(
			long companyId, String code, String redirect)
		throws Exception {

		String url = HttpUtil.addParameter(
			FacebookConnectUtil.getAccessTokenURL(companyId), "client_id",
			FacebookConnectUtil.getAppId(companyId));

		url = HttpUtil.addParameter(url, "redirect_uri",
			FacebookConnectUtil.getRedirectURL(companyId));

		String redirect_uri = FacebookConnectUtil.getRedirectURL(
			companyId);

		redirect_uri = HttpUtil.addParameter(
			redirect_uri, "redirect", redirect);

		url = HttpUtil.addParameter(
			url, "redirect_uri", redirect_uri);

		url = HttpUtil.addParameter(url, "client_secret",
			FacebookConnectUtil.getAppSecret(companyId));

		url = HttpUtil.addParameter(url, "code", code);

		Http.Options options = new Http.Options();

		options.setLocation(url);
		options.setPost(true);

		String content = HttpUtil.URLtoString(options);

		if (Validator.isNotNull(content)) {
			int x = content.indexOf("access_token=");

			if (x >= 0) {
				int y = content.indexOf(CharPool.AMPERSAND, x);

				if (y < x) {
					y = content.length();
				}

				return content.substring(x + 13, y);
			}
		}

		return null;
	}

	protected void getFacebookCredentials(
			HttpSession session, long companyId, String token)
		throws Exception {

		JSONObject jsonObject = FacebookConnectUtil.getGraphResources(
			companyId, "/me", token, "id,email,verified");

		if ((jsonObject != null) && jsonObject.getBoolean("verified")) {
			String facebookId = jsonObject.getString("id");

			if (Validator.isNotNull(facebookId)) {
				session.setAttribute(WebKeys.FACEBOOK_USER_ID, facebookId);
			}

			String emailAddress = jsonObject.getString("email");

			if (Validator.isNotNull(emailAddress)) {
				try {
					UserLocalServiceUtil.getUserByEmailAddress(
						companyId, emailAddress);

					session.setAttribute(
						WebKeys.FACEBOOK_USER_EMAIL_ADDRESS, emailAddress);
				}
				catch (NoSuchUserException nsue) {
					session.removeAttribute(
						WebKeys.FACEBOOK_USER_EMAIL_ADDRESS);
				}
			}
		}
	}

	protected String getRedirect(
			HttpServletRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		String redirect = ParamUtil.getString(
			request, "redirect", themeDisplay.getPathContext());

		HttpSession session = request.getSession();

		if ((session.getAttribute(WebKeys.FACEBOOK_ACCESS_TOKEN) != null) &&
			(session.getAttribute(WebKeys.FACEBOOK_USER_EMAIL_ADDRESS) !=
				null)) {

			return redirect;
		}
		else {
			PortletURL portletURL = PortletURLFactoryUtil.create(
				request, PortletKeys.LOGIN, themeDisplay.getPlid(),
				PortletRequest.RENDER_PHASE);

			portletURL.setWindowState(WindowState.MAXIMIZED);
			portletURL.setPortletMode(PortletMode.VIEW);

			portletURL.setParameter(
				"struts_action", "/login/facebook_connect_add_user");
			portletURL.setParameter("redirect", redirect);

			return portletURL.toString();
		}
	}

	protected void setFacebookCredentials(
			HttpSession session, long companyId, String token)
		throws Exception {

		String url = HttpUtil.addParameter(
			FacebookConnectUtil.getGraphURL(companyId) + "/me", "access_token",
			token);

		url = HttpUtil.addParameter(url, "fields", "email,id,verified");

		Http.Options options = new Http.Options();

		options.setLocation(url);

		String content = HttpUtil.URLtoString(options);

		if (Validator.isNull(content)) {
			return;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(content);

		if (!jsonObject.getBoolean("verified")) {
			return;
		}

		String facebookId = jsonObject.getString("id");

		if (Validator.isNotNull(facebookId)) {
			session.setAttribute(WebKeys.FACEBOOK_USER_ID, facebookId);
		}

		String emailAddress = jsonObject.getString("email");

		if (Validator.isNotNull(emailAddress)) {
			try {
				UserLocalServiceUtil.getUserByEmailAddress(
					companyId, emailAddress);

				session.setAttribute(
					WebKeys.FACEBOOK_USER_EMAIL_ADDRESS, emailAddress);
			}
			catch (NoSuchUserException nsue) {
				session.removeAttribute(WebKeys.FACEBOOK_USER_EMAIL_ADDRESS);
			}
		}
	}

}