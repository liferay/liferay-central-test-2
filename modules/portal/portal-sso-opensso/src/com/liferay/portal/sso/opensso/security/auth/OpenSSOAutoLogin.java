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

package com.liferay.portal.sso.opensso.security.auth;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.security.auth.BaseAutoLogin;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.exportimport.UserImporterUtil;
import com.liferay.portal.security.sso.OpenSSO;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.sso.opensso.configuration.OpenSSOConfiguration;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Prashant Dighe
 */
@Component(
	configurationPid = "com.liferay.portal.sso.opensso.configuration.OpenSSOConfiguration",
	immediate = true, service = AutoLogin.class
)
public class OpenSSOAutoLogin extends BaseAutoLogin {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_openSSOConfiguration = Configurable.createConfigurable(
			OpenSSOConfiguration.class, properties);
	}

	protected User addUser(
			long companyId, String firstName, String lastName,
			String emailAddress, String screenName, Locale locale)
		throws Exception {

		long creatorUserId = 0;
		boolean autoPassword = false;
		String password1 = PwdGenerator.getPassword();
		String password2 = password1;
		boolean autoScreenName = false;
		long facebookId = 0;
		String openId = StringPool.BLANK;
		String middleName = StringPool.BLANK;
		long prefixId = 0;
		long suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;
		ServiceContext serviceContext = new ServiceContext();

		return UserLocalServiceUtil.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);
	}

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);

		if (!PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.OPEN_SSO_AUTH_ENABLED,
				_openSSOConfiguration.enabled())) {

			return null;
		}

		String serviceUrl = PrefsPropsUtil.getString(
			companyId, PropsKeys.OPEN_SSO_SERVICE_URL);

		if (!_openSSO.isAuthenticated(request, serviceUrl)) {
			return null;
		}

		String screenNameAttr = PrefsPropsUtil.getString(
			companyId, PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR,
			_openSSOConfiguration.screenNameAttr());
		String emailAddressAttr = PrefsPropsUtil.getString(
			companyId, PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR,
			_openSSOConfiguration.emailAddressAttr());
		String firstNameAttr = PrefsPropsUtil.getString(
			companyId, PropsKeys.OPEN_SSO_FIRST_NAME_ATTR,
			_openSSOConfiguration.firstNameAttr());
		String lastNameAttr = PrefsPropsUtil.getString(
			companyId, PropsKeys.OPEN_SSO_LAST_NAME_ATTR,
			_openSSOConfiguration.lastNameAttr());

		Map<String, String> nameValues = _openSSO.getAttributes(
			request, serviceUrl);

		String screenName = nameValues.get(screenNameAttr);
		String emailAddress = nameValues.get(emailAddressAttr);
		String firstName = nameValues.get(firstNameAttr);
		String lastName = nameValues.get(lastNameAttr);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Validating user information for " + firstName + " " +
					lastName + " with screen name " + screenName +
						" and email address " + emailAddress);
		}

		User user = null;

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.USERS_SCREEN_NAME_ALWAYS_AUTOGENERATE)) {

			user = UserLocalServiceUtil.fetchUserByEmailAddress(
				companyId, emailAddress);

			if (user != null) {
				screenName = _screenNameGenerator.generate(
					companyId, user.getUserId(), emailAddress);
			}
		}

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.OPEN_SSO_LDAP_IMPORT_ENABLED,
				_openSSOConfiguration.importFromLDAP())) {

			try {
				String authType = PrefsPropsUtil.getString(
					companyId, PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
					PropsValues.COMPANY_SECURITY_AUTH_TYPE);

				if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					user = UserImporterUtil.importUser(
						companyId, StringPool.BLANK, screenName);
				}
				else {
					user = UserImporterUtil.importUser(
						companyId, emailAddress, StringPool.BLANK);
				}
			}
			catch (SystemException se) {
			}
		}
		else {
			if (Validator.isNull(emailAddress)) {
				return handleException(
					request, response, new Exception("Email address is null"));
			}
		}

		if (user == null) {
			user = UserLocalServiceUtil.fetchUserByScreenName(
				companyId, screenName);
		}

		if (user == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			Locale locale = LocaleUtil.getDefault();

			if (themeDisplay != null) {

				// ThemeDisplay should never be null, but some users complain of
				// this error. Cause is unknown.

				locale = themeDisplay.getLocale();
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Adding user " + screenName);
			}

			user = addUser(
				companyId, firstName, lastName, emailAddress, screenName,
				locale);
		}

		String currentURL = PortalUtil.getCurrentURL(request);

		if (currentURL.contains("/portal/login")) {
			String redirect = ParamUtil.getString(request, "redirect");

			if (Validator.isNotNull(redirect)) {
				redirect = PortalUtil.escapeRedirect(redirect);
			}
			else {
				redirect = PortalUtil.getPathMain();
			}

			request.setAttribute(AutoLogin.AUTO_LOGIN_REDIRECT, redirect);
		}

		String[] credentials = new String[3];

		credentials[0] = String.valueOf(user.getUserId());
		credentials[1] = user.getPassword();
		credentials[2] = Boolean.TRUE.toString();

		return credentials;
	}

	@Reference
	protected void setOpenSSO(OpenSSO openSSO) {
		_openSSO = openSSO;
	}

	@Reference
	protected void setScreenNameGenerator(
		ScreenNameGenerator screenNameGenerator) {

		_screenNameGenerator = screenNameGenerator;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenSSOAutoLogin.class);

	private OpenSSO _openSSO;
	private volatile OpenSSOConfiguration _openSSOConfiguration;
	private ScreenNameGenerator _screenNameGenerator;

}