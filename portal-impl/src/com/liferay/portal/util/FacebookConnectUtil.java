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

package com.liferay.portal.util;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;

import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;

/**
 * <a href="FacebookConnectUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Wilson Man
 */
public class FacebookConnectUtil {

	public static String getAccessTokenURL(long companyId)
		throws SystemException {

		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_OAUTH_TOKEN_URL,
			PropsValues.FACEBOOK_CONNECT_OAUTH_TOKEN_URL);
	}

	public static String getAppId(long companyId) throws SystemException {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_APP_ID,
			PropsValues.FACEBOOK_CONNECT_APP_ID);
	}

	public static String getAppSecret(long companyId) throws SystemException {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_APP_SECRET,
			PropsValues.FACEBOOK_CONNECT_APP_SECRET);
	}

	public static String getAuthURL(long companyId) throws SystemException {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_OAUTH_AUTH_URL,
			PropsValues.FACEBOOK_CONNECT_OAUTH_AUTH_URL);
	}

	public static String getGraphURL(long companyId) throws SystemException {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_GRAPH_URL,
			PropsValues.FACEBOOK_CONNECT_GRAPH_URL);
	}

	public static JSONObject getGraphResourcesAsJSON(
		long companyId, String path, String accessToken, String fields) {

		try {
			return JSONFactoryUtil.createJSONObject(
				getGraphResources(companyId, path, accessToken, fields));
		}
		catch (Exception e) {
			return null;
		}
	}

	public static String getGraphResources(
			long companyId, String path, String accessToken, String fields) {

			try {
				String url = HttpUtil.addParameter(
					getGraphURL(companyId) + path, "access_token",
					accessToken);

				if (Validator.isNotNull(fields)) {
					url = HttpUtil.addParameter(url, "fields", fields);
				}

				Http.Options options = new Http.Options();

				options.setLocation(url);

				return HttpUtil.URLtoString(options);
			}
			catch (Exception e) {
			}

			return null;
		}

	public static String getProfileImageURL(PortletRequest portletRequest) {

		HttpServletRequest request = PortalUtil.getOriginalServletRequest(
			((LiferayPortletRequest) portletRequest).getHttpServletRequest());

		String facebookId = (String) request.getSession().getAttribute(
			WebKeys.FACEBOOK_USER_ID);
		String token = (String) request.getSession().getAttribute(
			WebKeys.FACEBOOK_ACCESS_TOKEN);

		if (Validator.isNotNull(facebookId)) {
			JSONObject jsonObject = getGraphResourcesAsJSON(
				PortalUtil.getCompanyId(request), "/me",
				token, "id,picture");

			return jsonObject.getString("picture");
		}

		return null;
	}

	public static String getRedirectURL(long companyId)
		throws SystemException {

		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_OAUTH_REDIRECT_URL,
			PropsValues.FACEBOOK_CONNECT_OAUTH_REDIRECT_URL);
	}

	public static boolean isEnabled(long companyId) throws SystemException {
		return PrefsPropsUtil.getBoolean(
			companyId, PropsKeys.FACEBOOK_CONNECT_AUTH_ENABLED,
			PropsValues.FACEBOOK_CONNECT_AUTH_ENABLED);
	}

}