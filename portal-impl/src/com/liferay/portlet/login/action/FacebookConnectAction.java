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

package com.liferay.portlet.login.action;

import java.util.Calendar;
import java.util.Locale;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.FacebookConnectUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

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
 * <a href="FacebookConnectAction.java.html"><b><i>View Source</i></b></a>
 *
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
			processAddUser(actionRequest, actionResponse);
		}
		else {
			String redirect = HttpUtil.addParameter(
				FacebookConnectUtil.getAuthURL(companyId), "client_id",
				FacebookConnectUtil.getAppId(companyId));

			redirect = HttpUtil.addParameter(
				redirect, "redirect_uri",
				FacebookConnectUtil.getRedirectURL(companyId));

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

		String code = ParamUtil.get(request, "code", StringPool.BLANK);

		String token = getAccessToken(companyId, code);

		HttpSession session = request.getSession();

		if (Validator.isNotNull(token)) {
			session.setAttribute(WebKeys.FACEBOOK_ACCESS_TOKEN, token);

			getFacebookCredentials(session, companyId, token);
		}

		response.sendRedirect(getRedirect(request, themeDisplay));

		return null;
	}

	protected String getAccessToken(long companyId, String code)
		throws Exception {

		String url = HttpUtil.addParameter(
			FacebookConnectUtil.getAccessTokenURL(companyId), "client_id",
			FacebookConnectUtil.getAppId(companyId));

		url = HttpUtil.addParameter(url, "redirect_uri",
			FacebookConnectUtil.getRedirectURL(companyId));

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
				int y = content.indexOf(StringPool.AMPERSAND, x);

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

		String url = HttpUtil.addParameter(
			FacebookConnectUtil.getGraphURL(companyId) + "/me", "access_token",
			token);

		url = HttpUtil.addParameter(url, "fields", "email,verified");

		Http.Options options = new Http.Options();

		options.setLocation(url);

		String content = HttpUtil.URLtoString(options);

		if (Validator.isNotNull(content)) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(content);

			if (jsonObject.getBoolean("verified")) {
				String email = jsonObject.getString("email");

				if (Validator.isNotNull(email)) {
					try {
						UserLocalServiceUtil.getUserByEmailAddress(
							companyId, email);

						session.setAttribute(
							WebKeys.FACEBOOK_USER_EMAIL_ADDRESS, email);
					}
					catch (NoSuchUserException nsue) {
						session.removeAttribute(
							WebKeys.FACEBOOK_USER_EMAIL_ADDRESS);
					}
				}
				else {
					session.removeAttribute(
						WebKeys.FACEBOOK_USER_EMAIL_ADDRESS);

					String facebookId = jsonObject.getString("id");

					if (Validator.isNotNull(facebookId)) {
						session.setAttribute(
							WebKeys.FACEBOOK_USER_ID, facebookId);
					}
				}
			}
		}
	}

	protected String getRedirect(
			HttpServletRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		HttpSession session = request.getSession();

		if (session.getAttribute(WebKeys.FACEBOOK_ACCESS_TOKEN) != null &&
			session.getAttribute(WebKeys.FACEBOOK_USER_EMAIL_ADDRESS) != null) {

			return themeDisplay.getPathContext();
		}
		else {
			PortletURL portletURL = PortletURLFactoryUtil.create(
				request, PortletKeys.LOGIN, themeDisplay.getLayout().getPlid(),
				PortletRequest.RENDER_PHASE);

			portletURL.setWindowState(WindowState.MAXIMIZED);
			portletURL.setPortletMode(PortletMode.VIEW);
			portletURL.setParameter("struts_action", "/login/facebook_connect_add_user");

			return portletURL.toString();
		}
	}

	protected void processAddUser(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String token = FacebookConnectUtil.getAccessToken(actionRequest);

		long companyId = PortalUtil.getCompanyId(actionRequest);

		String url = HttpUtil.addParameter(
			FacebookConnectUtil.getGraphURL(companyId) + "/me", "access_token",
			token);

		url = HttpUtil.addParameter(url, "fields",
			"email,first_name,last_name,birthday,gender,verified");

		Http.Options options = new Http.Options();

		options.setLocation(url);

		String content = HttpUtil.URLtoString(options);

		if (Validator.isNotNull(content)) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(content);

			if (jsonObject.getBoolean("verified")) {
				String email = jsonObject.getString("email");
				String firstName = jsonObject.getString("first_name");
				String lastName = jsonObject.getString("last_name");
				String gender = jsonObject.getString("gender");
				long facebookId = jsonObject.getLong("id");

				UserLocalServiceUtil.addUser(
					0l, companyId, true, null, null, true, null, email,
					facebookId, StringPool.BLANK, Locale.getDefault(),
					firstName, StringPool.BLANK, lastName, 0, 0,
					gender.equals("male"), Calendar.JANUARY, 1, 1970,
					StringPool.BLANK, null, null, null, null, true,
					new ServiceContext());

				FacebookConnectUtil.setFacebookEmailInSession(
					actionRequest, email);

				actionResponse.sendRedirect("/");
			}
		}
	}

}