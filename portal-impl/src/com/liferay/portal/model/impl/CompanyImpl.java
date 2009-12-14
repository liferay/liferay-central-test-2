/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Shard;
import com.liferay.portal.model.User;
import com.liferay.portal.service.AccountLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ShardLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.Base64;

import java.security.Key;

import java.util.Locale;
import java.util.TimeZone;

/**
 * <a href="CompanyImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CompanyImpl extends CompanyModelImpl implements Company {

	public CompanyImpl() {
	}

	public int compareTo(Company company) {
		String webId1 = getWebId();
		String webId2 = company.getWebId();

		if (webId1.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {
			return -1;
		}
		else if (webId2.equals(PropsValues.COMPANY_DEFAULT_WEB_ID)) {
			return 1;
		}
		else {
			return webId1.compareTo(webId2);
		}
	}

	public Account getAccount() {
		Account account = null;

		try {
			account = AccountLocalServiceUtil.getAccount(
				getCompanyId(), getAccountId());
		}
		catch (Exception e) {
			account = new AccountImpl();

			_log.error(e, e);
		}

		return account;
	}

	public String getAdminName() {
		return "Administrator";
	}

	public String getAuthType() throws SystemException {
		return PrefsPropsUtil.getString(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
			PropsValues.COMPANY_SECURITY_AUTH_TYPE);
	}

	public User getDefaultUser() {
		User defaultUser = null;

		try {
			defaultUser = UserLocalServiceUtil.getDefaultUser(getCompanyId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return defaultUser;
	}

	public String getDefaultWebId() {
		return PropsValues.COMPANY_DEFAULT_WEB_ID;
	}

	public String getEmailAddress() {

		// Primary email address

		return "admin@" + getMx();
	}

	public Group getGroup() {
		if (getCompanyId() > CompanyConstants.SYSTEM) {
			try {
				return GroupLocalServiceUtil.getCompanyGroup(getCompanyId());
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return new GroupImpl();
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

	public Locale getLocale() {
		return getDefaultUser().getLocale();
	}

	public String getName() {
		return getAccount().getName();
	}

	public String getShardName() {
		String shardName = PropsValues.SHARD_DEFAULT_NAME;

		try {
			Shard shard = ShardLocalServiceUtil.getShard(
				Company.class.getName(), getCompanyId());

			shardName = shard.getName();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return shardName;
	}

	public String getShortName() {
		return getName();
	}

	public TimeZone getTimeZone() {
		return getDefaultUser().getTimeZone();
	}

	public boolean hasCompanyMx(String emailAddress) {
		emailAddress = emailAddress.trim().toLowerCase();

		int pos = emailAddress.indexOf(StringPool.AT);

		if (pos == -1) {
			return false;
		}

		String mx = emailAddress.substring(pos + 1, emailAddress.length());

		if (mx.equals(getMx())) {
			return true;
		}

		try {
			String[] mailHostNames = PrefsPropsUtil.getStringArray(
				getCompanyId(), PropsKeys.ADMIN_MAIL_HOST_NAMES,
				StringPool.NEW_LINE, PropsValues.ADMIN_MAIL_HOST_NAMES);

			for (int i = 0; i < mailHostNames.length; i++) {
				if (mx.equalsIgnoreCase(mailHostNames[i])) {
					return true;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	public boolean isAutoLogin() throws SystemException {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_AUTO_LOGIN,
			PropsValues.COMPANY_SECURITY_AUTO_LOGIN);
	}

	public boolean isCommunityLogo() throws SystemException {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_COMMUNITY_LOGO,
			PropsValues.COMPANY_SECURITY_COMMUNITY_LOGO);
	}

	public boolean isSendPassword() throws SystemException {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_SEND_PASSWORD,
			PropsValues.COMPANY_SECURITY_SEND_PASSWORD);
	}

	public boolean isStrangers() throws SystemException {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_STRANGERS,
			PropsValues.COMPANY_SECURITY_STRANGERS);
	}

	public boolean isStrangersVerify() throws SystemException {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_STRANGERS_VERIFY,
			PropsValues.COMPANY_SECURITY_STRANGERS_VERIFY);
	}

	public boolean isStrangersWithMx() throws SystemException {
		return PrefsPropsUtil.getBoolean(
			getCompanyId(), PropsKeys.COMPANY_SECURITY_STRANGERS_WITH_MX,
			PropsValues.COMPANY_SECURITY_STRANGERS_WITH_MX);
	}

	public void setKey(String key) {
		_keyObj = null;

		super.setKey(key);
	}

	public void setKeyObj(Key keyObj) {
		_keyObj = keyObj;

		super.setKey(Base64.objectToString(keyObj));
	}

	private static Log _log = LogFactoryUtil.getLog(CompanyImpl.class);

	private Key _keyObj = null;

}