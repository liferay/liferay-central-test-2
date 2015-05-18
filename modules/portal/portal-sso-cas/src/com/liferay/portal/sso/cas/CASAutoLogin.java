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

package com.liferay.portal.sso.cas;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.security.auth.BaseAutoLogin;
import com.liferay.portal.security.exportimport.UserImporterUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.sso.cas.configuration.CASConfiguration;
import com.liferay.portal.sso.cas.constants.CASConstants;
import com.liferay.portal.sso.cas.constants.CASWebKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Wesley Gong
 * @author Daeyoung Song
 */
@Component(
	configurationPid = "com.liferay.portal.sso.cas.configuration.CASConfiguration",
	immediate = true, service = AutoLogin.class
)
public class CASAutoLogin extends BaseAutoLogin {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             UserImporterUtil#importUser(long, String, String)}
	 */
	@Deprecated
	protected User addUser(long companyId, String screenName) throws Exception {
		return UserImporterUtil.importUser(
			companyId, StringPool.BLANK, screenName);
	}

	@Override
	protected String[] doHandleException(
		HttpServletRequest request, HttpServletResponse response, Exception e) {

		HttpSession session = request.getSession();

		if (e instanceof NoSuchUserException) {
			session.removeAttribute(CASWebKeys.CAS_LOGIN);

			session.setAttribute(
				CASWebKeys.CAS_NO_SUCH_USER_EXCEPTION, Boolean.TRUE);
		}

		_log.error(e, e);

		return null;
	}

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		HttpSession session = request.getSession();

		long companyId = PortalUtil.getCompanyId(request);

		CASConfiguration casConfiguration = _settingsFactory.getSettings(
			CASConfiguration.class,
			new CompanyServiceSettingsLocator(
				companyId, CASConstants.SERVICE_NAME));

		if (!casConfiguration.enabled()) {
			return null;
		}

		String login = (String)session.getAttribute(CASWebKeys.CAS_LOGIN);

		if (Validator.isNull(login)) {
			Object noSuchUserException = session.getAttribute(
				CASWebKeys.CAS_NO_SUCH_USER_EXCEPTION);

			if (noSuchUserException == null) {
				return null;
			}

			session.removeAttribute(CASWebKeys.CAS_NO_SUCH_USER_EXCEPTION);

			session.setAttribute(CASWebKeys.CAS_FORCE_LOGOUT, Boolean.TRUE);

			String redirect = casConfiguration.noSuchUserRedirectURL();

			request.setAttribute(AutoLogin.AUTO_LOGIN_REDIRECT, redirect);

			return null;
		}

		User user = null;

		String authType = PrefsPropsUtil.getString(
			companyId, PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
			PropsValues.COMPANY_SECURITY_AUTH_TYPE);

		if (casConfiguration.importFromLDAP()) {
			try {
				if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					user = UserImporterUtil.importUser(
						companyId, StringPool.BLANK, login);
				}
				else {
					user = UserImporterUtil.importUser(
						companyId, login, StringPool.BLANK);
				}
			}
			catch (SystemException se) {
			}
		}

		if (user == null) {
			if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				user = UserLocalServiceUtil.getUserByScreenName(
					companyId, login);
			}
			else {
				user = UserLocalServiceUtil.getUserByEmailAddress(
					companyId, login);
			}
		}

		addRedirect(request);

		String[] credentials = new String[3];

		credentials[0] = String.valueOf(user.getUserId());
		credentials[1] = user.getPassword();
		credentials[2] = Boolean.TRUE.toString();

		return credentials;
	}

	@Reference
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private static final Log _log = LogFactoryUtil.getLog(CASAutoLogin.class);

	private volatile SettingsFactory _settingsFactory;

}