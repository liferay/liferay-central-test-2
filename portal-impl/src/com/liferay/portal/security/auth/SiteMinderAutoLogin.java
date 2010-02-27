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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="SiteMinderAutoLogin.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 * @author Wesley Gong
 */
public class SiteMinderAutoLogin extends CASAutoLogin {

	public String[] login(
		HttpServletRequest request, HttpServletResponse response) {

		String[] credentials = null;

		try {
			long companyId = PortalUtil.getCompanyId(request);

			if (!LDAPSettingsUtil.isSiteMinderEnabled(companyId)) {
				return credentials;
			}

			String screenName = request.getHeader(
				PrefsPropsUtil.getString(
					companyId, PropsKeys.SITEMINDER_USER_HEADER,
					PropsValues.SITEMINDER_USER_HEADER));

			if (Validator.isNull(screenName)) {
				return credentials;
			}

			User user = null;

			if (PrefsPropsUtil.getBoolean(
					companyId, PropsKeys.SITEMINDER_IMPORT_FROM_LDAP,
					PropsValues.SITEMINDER_IMPORT_FROM_LDAP)) {

				try {
					user = importLDAPUser(
						companyId, StringPool.BLANK, screenName);
				}
				catch (SystemException se) {
				}
			}

			if (user == null) {
				user = UserLocalServiceUtil.getUserByScreenName(
					companyId, screenName);
			}

			credentials = new String[3];

			credentials[0] = String.valueOf(user.getUserId());
			credentials[1] = user.getPassword();
			credentials[2] = Boolean.TRUE.toString();

			return credentials;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return credentials;
	}

	private static Log _log = LogFactoryUtil.getLog(SiteMinderAutoLogin.class);

}