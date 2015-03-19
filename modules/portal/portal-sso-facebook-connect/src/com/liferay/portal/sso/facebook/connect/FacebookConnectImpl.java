/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.sso.facebook.connect;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.facebook.FacebookConnect;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.sso.facebook.connect.configuration.FacebookConnectConfiguration;
import com.liferay.portal.sso.facebook.connect.constants.FacebookConnectWebKeys;
import com.liferay.portal.util.PortalUtil;

import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Wilson Man
 * @author Mika Koivisto
 */
@Component(
	configurationPid = "com.liferay.portal.sso.facebook.connect.configuration.FacebookConnectConfiguration",
	immediate = true, service = FacebookConnect.class
)
public class FacebookConnectImpl implements FacebookConnect {

	@Override
	public String getAccessToken(long companyId, String redirect, String code) {
		String url = HttpUtil.addParameter(
			getAccessTokenURL(companyId), "client_id", getAppId(companyId));

		url = HttpUtil.addParameter(
			url, "redirect_uri", getRedirectURL(companyId));

		String facebookConnectRedirectURL = getRedirectURL(companyId);

		facebookConnectRedirectURL = HttpUtil.addParameter(
			facebookConnectRedirectURL, "redirect", redirect);

		url = HttpUtil.addParameter(
			url, "redirect_uri", facebookConnectRedirectURL);
		url = HttpUtil.addParameter(
			url, "client_secret", getAppSecret(companyId));
		url = HttpUtil.addParameter(url, "code", code);

		Http.Options options = new Http.Options();

		options.setLocation(url);
		options.setPost(true);

		try {
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
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to retrieve Facebook access token", e);
		}

		return null;
	}

	@Override
	public String getAccessTokenURL(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_OAUTH_TOKEN_URL,
			_facebookConnectConfiguration.oauthTokenURL());
	}

	@Override
	public String getAppId(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_APP_ID,
			_facebookConnectConfiguration.appId());
	}

	@Override
	public String getAppSecret(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_APP_SECRET,
			_facebookConnectConfiguration.appSecret());
	}

	@Override
	public String getAuthURL(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_OAUTH_AUTH_URL,
			_facebookConnectConfiguration.oauthAuthURL());
	}

	@Override
	public JSONObject getGraphResources(
		long companyId, String path, String accessToken, String fields) {

		try {
			String url = HttpUtil.addParameter(
				getGraphURL(companyId).concat(path), "access_token",
				accessToken);

			if (Validator.isNotNull(fields)) {
				url = HttpUtil.addParameter(url, "fields", fields);
			}

			Http.Options options = new Http.Options();

			options.setLocation(url);

			String json = HttpUtil.URLtoString(options);

			return JSONFactoryUtil.createJSONObject(json);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return null;
	}

	@Override
	public String getGraphURL(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_GRAPH_URL,
			_facebookConnectConfiguration.graphURL());
	}

	@Override
	public String getProfileImageURL(PortletRequest portletRequest) {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		request = PortalUtil.getOriginalServletRequest(request);

		HttpSession session = request.getSession();

		String facebookId = (String)session.getAttribute(
			FacebookConnectWebKeys.FACEBOOK_USER_ID);

		if (Validator.isNull(facebookId)) {
			return null;
		}

		long companyId = PortalUtil.getCompanyId(request);

		String token = (String)session.getAttribute(
			FacebookConnectWebKeys.FACEBOOK_ACCESS_TOKEN);

		JSONObject jsonObject = getGraphResources(
			companyId, "/me", token, "id,picture");

		return jsonObject.getString("picture");
	}

	@Override
	public String getRedirectURL(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_OAUTH_REDIRECT_URL,
			_facebookConnectConfiguration.oauthRedirectURL());
	}

	@Override
	public boolean isEnabled(long companyId) {
		return PrefsPropsUtil.getBoolean(
			companyId, PropsKeys.FACEBOOK_CONNECT_AUTH_ENABLED,
			_facebookConnectConfiguration.enabled());
	}

	@Override
	public boolean isVerifiedAccountRequired(long companyId) {
		return PrefsPropsUtil.getBoolean(
			companyId, PropsKeys.FACEBOOK_CONNECT_VERIFIED_ACCOUNT_REQUIRED,
			_facebookConnectConfiguration.verifiedAccountRequired());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_facebookConnectConfiguration = Configurable.createConfigurable(
			FacebookConnectConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FacebookConnectImpl.class);

	private volatile FacebookConnectConfiguration _facebookConnectConfiguration;

}