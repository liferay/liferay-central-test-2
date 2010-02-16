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