/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="UserModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserModelImpl extends BaseModelImpl {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User"), XSS_ALLOW);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CONTACTID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User.contactId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PASSWORD = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User.password"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_EMAILADDRESS = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User.emailAddress"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_LANGUAGEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User.languageId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TIMEZONEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User.timeZoneId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_GREETING = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User.greeting"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_RESOLUTION = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User.resolution"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMMENTS = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User.comments"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_LOGINIP = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User.loginIP"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_LASTLOGINIP = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User.lastLoginIP"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserModel"));

	public UserModelImpl() {
	}

	public String getPrimaryKey() {
		return _userId;
	}

	public void setPrimaryKey(String pk) {
		setUserId(pk);
	}

	public String getUserId() {
		return GetterUtil.getString(_userId);
	}

	public void setUserId(String userId) {
		if (((userId == null) && (_userId != null)) ||
				((userId != null) && (_userId == null)) ||
				((userId != null) && (_userId != null) &&
				!userId.equals(_userId))) {
			if (!XSS_ALLOW_USERID) {
				userId = XSSUtil.strip(userId);
			}

			_userId = userId;
		}
	}

	public String getCompanyId() {
		return GetterUtil.getString(_companyId);
	}

	public void setCompanyId(String companyId) {
		if (((companyId == null) && (_companyId != null)) ||
				((companyId != null) && (_companyId == null)) ||
				((companyId != null) && (_companyId != null) &&
				!companyId.equals(_companyId))) {
			if (!XSS_ALLOW_COMPANYID) {
				companyId = XSSUtil.strip(companyId);
			}

			_companyId = companyId;
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if (((createDate == null) && (_createDate != null)) ||
				((createDate != null) && (_createDate == null)) ||
				((createDate != null) && (_createDate != null) &&
				!createDate.equals(_createDate))) {
			_createDate = createDate;
		}
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (((modifiedDate == null) && (_modifiedDate != null)) ||
				((modifiedDate != null) && (_modifiedDate == null)) ||
				((modifiedDate != null) && (_modifiedDate != null) &&
				!modifiedDate.equals(_modifiedDate))) {
			_modifiedDate = modifiedDate;
		}
	}

	public String getContactId() {
		return GetterUtil.getString(_contactId);
	}

	public void setContactId(String contactId) {
		if (((contactId == null) && (_contactId != null)) ||
				((contactId != null) && (_contactId == null)) ||
				((contactId != null) && (_contactId != null) &&
				!contactId.equals(_contactId))) {
			if (!XSS_ALLOW_CONTACTID) {
				contactId = XSSUtil.strip(contactId);
			}

			_contactId = contactId;
		}
	}

	public String getPassword() {
		return GetterUtil.getString(_password);
	}

	public void setPassword(String password) {
		if (((password == null) && (_password != null)) ||
				((password != null) && (_password == null)) ||
				((password != null) && (_password != null) &&
				!password.equals(_password))) {
			if (!XSS_ALLOW_PASSWORD) {
				password = XSSUtil.strip(password);
			}

			_password = password;
		}
	}

	public boolean getPasswordEncrypted() {
		return _passwordEncrypted;
	}

	public boolean isPasswordEncrypted() {
		return _passwordEncrypted;
	}

	public void setPasswordEncrypted(boolean passwordEncrypted) {
		if (passwordEncrypted != _passwordEncrypted) {
			_passwordEncrypted = passwordEncrypted;
		}
	}

	public Date getPasswordExpirationDate() {
		return _passwordExpirationDate;
	}

	public void setPasswordExpirationDate(Date passwordExpirationDate) {
		if (((passwordExpirationDate == null) &&
				(_passwordExpirationDate != null)) ||
				((passwordExpirationDate != null) &&
				(_passwordExpirationDate == null)) ||
				((passwordExpirationDate != null) &&
				(_passwordExpirationDate != null) &&
				!passwordExpirationDate.equals(_passwordExpirationDate))) {
			_passwordExpirationDate = passwordExpirationDate;
		}
	}

	public boolean getPasswordReset() {
		return _passwordReset;
	}

	public boolean isPasswordReset() {
		return _passwordReset;
	}

	public void setPasswordReset(boolean passwordReset) {
		if (passwordReset != _passwordReset) {
			_passwordReset = passwordReset;
		}
	}

	public String getEmailAddress() {
		return GetterUtil.getString(_emailAddress);
	}

	public void setEmailAddress(String emailAddress) {
		if (((emailAddress == null) && (_emailAddress != null)) ||
				((emailAddress != null) && (_emailAddress == null)) ||
				((emailAddress != null) && (_emailAddress != null) &&
				!emailAddress.equals(_emailAddress))) {
			if (!XSS_ALLOW_EMAILADDRESS) {
				emailAddress = XSSUtil.strip(emailAddress);
			}

			_emailAddress = emailAddress;
		}
	}

	public String getLanguageId() {
		return GetterUtil.getString(_languageId);
	}

	public void setLanguageId(String languageId) {
		if (((languageId == null) && (_languageId != null)) ||
				((languageId != null) && (_languageId == null)) ||
				((languageId != null) && (_languageId != null) &&
				!languageId.equals(_languageId))) {
			if (!XSS_ALLOW_LANGUAGEID) {
				languageId = XSSUtil.strip(languageId);
			}

			_languageId = languageId;
		}
	}

	public String getTimeZoneId() {
		return GetterUtil.getString(_timeZoneId);
	}

	public void setTimeZoneId(String timeZoneId) {
		if (((timeZoneId == null) && (_timeZoneId != null)) ||
				((timeZoneId != null) && (_timeZoneId == null)) ||
				((timeZoneId != null) && (_timeZoneId != null) &&
				!timeZoneId.equals(_timeZoneId))) {
			if (!XSS_ALLOW_TIMEZONEID) {
				timeZoneId = XSSUtil.strip(timeZoneId);
			}

			_timeZoneId = timeZoneId;
		}
	}

	public String getGreeting() {
		return GetterUtil.getString(_greeting);
	}

	public void setGreeting(String greeting) {
		if (((greeting == null) && (_greeting != null)) ||
				((greeting != null) && (_greeting == null)) ||
				((greeting != null) && (_greeting != null) &&
				!greeting.equals(_greeting))) {
			if (!XSS_ALLOW_GREETING) {
				greeting = XSSUtil.strip(greeting);
			}

			_greeting = greeting;
		}
	}

	public String getResolution() {
		return GetterUtil.getString(_resolution);
	}

	public void setResolution(String resolution) {
		if (((resolution == null) && (_resolution != null)) ||
				((resolution != null) && (_resolution == null)) ||
				((resolution != null) && (_resolution != null) &&
				!resolution.equals(_resolution))) {
			if (!XSS_ALLOW_RESOLUTION) {
				resolution = XSSUtil.strip(resolution);
			}

			_resolution = resolution;
		}
	}

	public String getComments() {
		return GetterUtil.getString(_comments);
	}

	public void setComments(String comments) {
		if (((comments == null) && (_comments != null)) ||
				((comments != null) && (_comments == null)) ||
				((comments != null) && (_comments != null) &&
				!comments.equals(_comments))) {
			if (!XSS_ALLOW_COMMENTS) {
				comments = XSSUtil.strip(comments);
			}

			_comments = comments;
		}
	}

	public Date getLoginDate() {
		return _loginDate;
	}

	public void setLoginDate(Date loginDate) {
		if (((loginDate == null) && (_loginDate != null)) ||
				((loginDate != null) && (_loginDate == null)) ||
				((loginDate != null) && (_loginDate != null) &&
				!loginDate.equals(_loginDate))) {
			_loginDate = loginDate;
		}
	}

	public String getLoginIP() {
		return GetterUtil.getString(_loginIP);
	}

	public void setLoginIP(String loginIP) {
		if (((loginIP == null) && (_loginIP != null)) ||
				((loginIP != null) && (_loginIP == null)) ||
				((loginIP != null) && (_loginIP != null) &&
				!loginIP.equals(_loginIP))) {
			if (!XSS_ALLOW_LOGINIP) {
				loginIP = XSSUtil.strip(loginIP);
			}

			_loginIP = loginIP;
		}
	}

	public Date getLastLoginDate() {
		return _lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		if (((lastLoginDate == null) && (_lastLoginDate != null)) ||
				((lastLoginDate != null) && (_lastLoginDate == null)) ||
				((lastLoginDate != null) && (_lastLoginDate != null) &&
				!lastLoginDate.equals(_lastLoginDate))) {
			_lastLoginDate = lastLoginDate;
		}
	}

	public String getLastLoginIP() {
		return GetterUtil.getString(_lastLoginIP);
	}

	public void setLastLoginIP(String lastLoginIP) {
		if (((lastLoginIP == null) && (_lastLoginIP != null)) ||
				((lastLoginIP != null) && (_lastLoginIP == null)) ||
				((lastLoginIP != null) && (_lastLoginIP != null) &&
				!lastLoginIP.equals(_lastLoginIP))) {
			if (!XSS_ALLOW_LASTLOGINIP) {
				lastLoginIP = XSSUtil.strip(lastLoginIP);
			}

			_lastLoginIP = lastLoginIP;
		}
	}

	public int getFailedLoginAttempts() {
		return _failedLoginAttempts;
	}

	public void setFailedLoginAttempts(int failedLoginAttempts) {
		if (failedLoginAttempts != _failedLoginAttempts) {
			_failedLoginAttempts = failedLoginAttempts;
		}
	}

	public boolean getAgreedToTermsOfUse() {
		return _agreedToTermsOfUse;
	}

	public boolean isAgreedToTermsOfUse() {
		return _agreedToTermsOfUse;
	}

	public void setAgreedToTermsOfUse(boolean agreedToTermsOfUse) {
		if (agreedToTermsOfUse != _agreedToTermsOfUse) {
			_agreedToTermsOfUse = agreedToTermsOfUse;
		}
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		if (active != _active) {
			_active = active;
		}
	}

	public Object clone() {
		UserImpl clone = new UserImpl();
		clone.setUserId(getUserId());
		clone.setCompanyId(getCompanyId());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setContactId(getContactId());
		clone.setPassword(getPassword());
		clone.setPasswordEncrypted(getPasswordEncrypted());
		clone.setPasswordExpirationDate(getPasswordExpirationDate());
		clone.setPasswordReset(getPasswordReset());
		clone.setEmailAddress(getEmailAddress());
		clone.setLanguageId(getLanguageId());
		clone.setTimeZoneId(getTimeZoneId());
		clone.setGreeting(getGreeting());
		clone.setResolution(getResolution());
		clone.setComments(getComments());
		clone.setLoginDate(getLoginDate());
		clone.setLoginIP(getLoginIP());
		clone.setLastLoginDate(getLastLoginDate());
		clone.setLastLoginIP(getLastLoginIP());
		clone.setFailedLoginAttempts(getFailedLoginAttempts());
		clone.setAgreedToTermsOfUse(getAgreedToTermsOfUse());
		clone.setActive(getActive());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		UserImpl user = (UserImpl)obj;
		String pk = user.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		UserImpl user = null;

		try {
			user = (UserImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = user.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _userId;
	private String _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _contactId;
	private String _password;
	private boolean _passwordEncrypted;
	private Date _passwordExpirationDate;
	private boolean _passwordReset;
	private String _emailAddress;
	private String _languageId;
	private String _timeZoneId;
	private String _greeting;
	private String _resolution;
	private String _comments;
	private Date _loginDate;
	private String _loginIP;
	private Date _lastLoginDate;
	private String _lastLoginIP;
	private int _failedLoginAttempts;
	private boolean _agreedToTermsOfUse;
	private boolean _active;
}