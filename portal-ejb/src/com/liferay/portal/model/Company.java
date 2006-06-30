/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.service.spring.AccountLocalServiceUtil;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.Base64;
import com.liferay.util.Validator;

import java.security.Key;

import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="Company.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class Company extends CompanyModel {

	public static final String[] TYPES =
		PropsUtil.getArray(PropsUtil.COMPANY_TYPES);

	public static final String AUTH_TYPE_EA = "emailAddress";

	public static final String AUTH_TYPE_ID = "userId";

	public static final String SYSTEM = "system";

	public Company() {
	}

	public void setKey(String key) {
		_keyObj = null;

		super.setKey(key);
	}

	public Key getKeyObj() {
		if (_keyObj == null) {
			String key = getKey();

			if (Validator.isNotNull(key)) {
				_keyObj = (Key)Base64.stringToObject(key);
			}
		}

		return _keyObj;
	}

	public void setKeyObj(Key keyObj) {
		_keyObj = keyObj;

		super.setKey(Base64.objectToString(keyObj));
	}

	public Account getAccount() {
		Account account = null;

		try {
			account = AccountLocalServiceUtil.getAccount(getCompanyId());
		}
		catch (Exception e) {
			account = new Account();

			_log.error(e);
		}

		return account;
	}

	public String getName() {
		return getAccount().getName();
	}

	public String getShortName() {
		return getName();
	}

	public String getEmailAddress() {

		// Primary email address

		return "admin@" + getMx();
	}

	public User getDefaultUser() {
		User defaultUser = null;

		try {
			defaultUser = UserLocalServiceUtil.getDefaultUser(getCompanyId());
		}
		catch (Exception e) {
			_log.error(e);
		}

		return defaultUser;
	}

	public Locale getLocale() {
		return getDefaultUser().getLocale();
	}

	public TimeZone getTimeZone() {
		return getDefaultUser().getTimeZone();
	}

	public String getResolution() {
		return getDefaultUser().getResolution();
	}

	public String getAdminName() {
		return "Administrator";
	}

	public String getAuthType() throws PortalException, SystemException {
		return PrefsPropsUtil.getString(
			getCompanyId(), PropsUtil.COMPANY_SECURITY_AUTH_TYPE);
	}

	public boolean isAutoLogin() throws PortalException, SystemException {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsUtil.COMPANY_SECURITY_AUTO_LOGIN);
	}

	public boolean isStrangers() throws PortalException, SystemException {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsUtil.COMPANY_SECURITY_STRANGERS);
	}

	private static Log _log = LogFactory.getLog(Company.class);

	private Key _keyObj = null;
	private String _imageURL;
	private Company _company;

}