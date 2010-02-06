/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.servlet.filters.sso.opensso.OpenSSOUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.PwdGenerator;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="OpenSSOAutoLogin.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Prashant Dighe
 */
public class OpenSSOAutoLogin implements AutoLogin {

	public String[] login(
		HttpServletRequest request, HttpServletResponse response) {

		String[] credentials = null;

		try {
			long companyId = PortalUtil.getCompanyId(request);

			if (!PrefsPropsUtil.getBoolean(
					companyId, PropsKeys.OPEN_SSO_AUTH_ENABLED,
					PropsValues.OPEN_SSO_AUTH_ENABLED)) {

				return credentials;
			}

			String serviceUrl = PrefsPropsUtil.getString(
				companyId, PropsKeys.OPEN_SSO_SERVICE_URL);

			if (!OpenSSOUtil.isAuthenticated(request, serviceUrl)) {
				return credentials;
			}

			String screenNameAttr = PrefsPropsUtil.getString(
				companyId, PropsKeys.OPEN_SSO_SCREEN_NAME_ATTR,
				PropsValues.OPEN_SSO_SCREEN_NAME_ATTR);
			String emailAddressAttr = PrefsPropsUtil.getString(
				companyId, PropsKeys.OPEN_SSO_EMAIL_ADDRESS_ATTR,
				PropsValues.OPEN_SSO_EMAIL_ADDRESS_ATTR);
			String firstNameAttr = PrefsPropsUtil.getString(
				companyId, PropsKeys.OPEN_SSO_FIRST_NAME_ATTR,
				PropsValues.OPEN_SSO_FIRST_NAME_ATTR);
			String lastNameAttr = PrefsPropsUtil.getString(
				companyId, PropsKeys.OPEN_SSO_LAST_NAME_ATTR,
				PropsValues.OPEN_SSO_LAST_NAME_ATTR);

			Map<String, String> nameValues = OpenSSOUtil.getAttributes(
				request, serviceUrl);

			String screenName = nameValues.get(screenNameAttr);
			String emailAddress = nameValues.get(emailAddressAttr);
			String firstName = nameValues.get(firstNameAttr);
			String lastName = nameValues.get(lastNameAttr);

			if (Validator.isNull(emailAddress)) {
				throw new AutoLoginException("Email address is null");
			}

			User user = null;

			try {
				user = UserLocalServiceUtil.getUserByScreenName(
					companyId, screenName);
			}
			catch (NoSuchUserException nsue) {
				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				Locale locale = LocaleUtil.getDefault();

				if (themeDisplay != null) {

					// ThemeDisplay should never be null, but some users
					// complain of this error. Cause is unknown.

					locale = themeDisplay.getLocale();
				}

				user = addUser(
					companyId, firstName, lastName, emailAddress, screenName,
					locale);
			}

			credentials = new String[3];

			credentials[0] = String.valueOf(user.getUserId());
			credentials[1] = user.getPassword();
			credentials[2] = Boolean.TRUE.toString();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return credentials;
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
		String openId = StringPool.BLANK;
		String middleName = StringPool.BLANK;
		int prefixId = 0;
		int suffixId = 0;
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
			autoScreenName, screenName, emailAddress, openId, locale, firstName,
			middleName, lastName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
			roleIds, userGroupIds, sendEmail, serviceContext);
	}

	private static Log _log = LogFactoryUtil.getLog(OpenSSOAutoLogin.class);

}