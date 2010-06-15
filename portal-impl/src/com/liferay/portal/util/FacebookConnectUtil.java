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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsKeys;

/**
 * <a href="FacebookConnectUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Wilson Man
 */
public class FacebookConnectUtil {

	public static String getAccessTokenURL(long companyId)
		throws SystemException {

		return PrefsPropsUtil.getString(
			companyId, "facebook.connect.oauth.token.url");
	}

	public static String getAppId(long companyId) throws SystemException {
		return PrefsPropsUtil.getString(companyId, "facebook.connect.app.id");
	}

	public static String getAppSecret(long companyId) throws SystemException {
		return PrefsPropsUtil.getString(
			companyId, "facebook.connect.app.secret");
	}

	public static String getAuthURL(long companyId) throws SystemException {
		return PrefsPropsUtil.getString(
			companyId, "facebook.connect.oauth.auth.url");
	}

	public static String getGraphURL(long companyId) throws SystemException {

		return PrefsPropsUtil.getString(companyId, "facebook.connect.graph.url");
	}

	public static String getRedirectURL(long companyId)
		throws SystemException {

		return PrefsPropsUtil.getString(
			companyId, "facebook.connect.oauth.redirect.url");
	}

	public static boolean isEnabled(long companyId) throws SystemException {
		return PrefsPropsUtil.getBoolean(
			companyId, PropsKeys.FACEBOOK_CONNECT_AUTH_ENABLED,
			PropsValues.FACEBOOK_CONNECT_AUTH_ENABLED);
	}
}