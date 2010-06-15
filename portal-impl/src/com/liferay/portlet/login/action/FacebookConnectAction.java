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

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.FacebookConnectUtil;
import com.liferay.portal.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

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

		String redirect = HttpUtil.addParameter(
			FacebookConnectUtil.getAuthURL(companyId), "client_id",
			FacebookConnectUtil.getAppId(companyId));

		redirect = HttpUtil.addParameter(
			redirect, "redirect_uri",
			FacebookConnectUtil.getRedirectURL(companyId));

		redirect = HttpUtil.addParameter(redirect, "scope", "email");

		actionResponse.sendRedirect(redirect);
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

		if (Validator.isNotNull(token)) {
			String emailAddress = getEmailAddress(companyId, token);

			if (Validator.isNotNull(emailAddress)) {
				HttpSession session = request.getSession();

				session.setAttribute(
					WebKeys.FACEBOOK_USER_EMAIL_ADDRESS, emailAddress);
			}
		}

		response.sendRedirect(themeDisplay.getPathContext());

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

	protected String getEmailAddress(long companyId, String token)
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
				return jsonObject.getString("email");
			}
		}

		return null;
	}

}