/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import java.sql.Types;

import java.util.Date;

/**
 * <a href="UserModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>User_</code> table in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.User
 * @see com.liferay.portal.service.model.UserModel
 * @see com.liferay.portal.service.model.impl.UserImpl
 *
 */
public class UserModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "User_";
	public static Object[][] TABLE_COLUMNS = {
			{ "userId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "defaultUser", new Integer(Types.BOOLEAN) },
			{ "contactId", new Integer(Types.BIGINT) },
			{ "password_", new Integer(Types.VARCHAR) },
			{ "passwordEncrypted", new Integer(Types.BOOLEAN) },
			{ "passwordExpirationDate", new Integer(Types.TIMESTAMP) },
			{ "passwordReset", new Integer(Types.BOOLEAN) },
			{ "passwordModifiedDate", new Integer(Types.TIMESTAMP) },
			{ "graceLoginCount", new Integer(Types.INTEGER) },
			{ "screenName", new Integer(Types.VARCHAR) },
			{ "emailAddress", new Integer(Types.VARCHAR) },
			{ "languageId", new Integer(Types.VARCHAR) },
			{ "timeZoneId", new Integer(Types.VARCHAR) },
			{ "greeting", new Integer(Types.VARCHAR) },
			{ "comments", new Integer(Types.VARCHAR) },
			{ "loginDate", new Integer(Types.TIMESTAMP) },
			{ "loginIP", new Integer(Types.VARCHAR) },
			{ "lastLoginDate", new Integer(Types.TIMESTAMP) },
			{ "lastLoginIP", new Integer(Types.VARCHAR) },
			{ "lastFailedLoginDate", new Integer(Types.TIMESTAMP) },
			{ "failedLoginAttempts", new Integer(Types.INTEGER) },
			{ "lockout", new Integer(Types.BOOLEAN) },
			{ "lockoutDate", new Integer(Types.TIMESTAMP) },
			{ "agreedToTermsOfUse", new Integer(Types.BOOLEAN) },
			{ "active_", new Integer(Types.BOOLEAN) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User"), XSS_ALLOW);
	public static boolean XSS_ALLOW_PASSWORD = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User.password"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SCREENNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.User.screenName"),
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

	public long getPrimaryKey() {
		return _userId;
	}

	public void setPrimaryKey(long pk) {
		setUserId(pk);
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		if (userId != _userId) {
			_userId = userId;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (companyId != _companyId) {
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

	public boolean getDefaultUser() {
		return _defaultUser;
	}

	public boolean isDefaultUser() {
		return _defaultUser;
	}

	public void setDefaultUser(boolean defaultUser) {
		if (defaultUser != _defaultUser) {
			_defaultUser = defaultUser;
		}
	}

	public long getContactId() {
		return _contactId;
	}

	public void setContactId(long contactId) {
		if (contactId != _contactId) {
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

	public Date getPasswordModifiedDate() {
		return _passwordModifiedDate;
	}

	public void setPasswordModifiedDate(Date passwordModifiedDate) {
		if (((passwordModifiedDate == null) && (_passwordModifiedDate != null)) ||
				((passwordModifiedDate != null) &&
				(_passwordModifiedDate == null)) ||
				((passwordModifiedDate != null) &&
				(_passwordModifiedDate != null) &&
				!passwordModifiedDate.equals(_passwordModifiedDate))) {
			_passwordModifiedDate = passwordModifiedDate;
		}
	}

	public int getGraceLoginCount() {
		return _graceLoginCount;
	}

	public void setGraceLoginCount(int graceLoginCount) {
		if (graceLoginCount != _graceLoginCount) {
			_graceLoginCount = graceLoginCount;
		}
	}

	public String getScreenName() {
		return GetterUtil.getString(_screenName);
	}

	public void setScreenName(String screenName) {
		if (((screenName == null) && (_screenName != null)) ||
				((screenName != null) && (_screenName == null)) ||
				((screenName != null) && (_screenName != null) &&
				!screenName.equals(_screenName))) {
			if (!XSS_ALLOW_SCREENNAME) {
				screenName = XSSUtil.strip(screenName);
			}

			_screenName = screenName;
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

	public Date getLastFailedLoginDate() {
		return _lastFailedLoginDate;
	}

	public void setLastFailedLoginDate(Date lastFailedLoginDate) {
		if (((lastFailedLoginDate == null) && (_lastFailedLoginDate != null)) ||
				((lastFailedLoginDate != null) &&
				(_lastFailedLoginDate == null)) ||
				((lastFailedLoginDate != null) &&
				(_lastFailedLoginDate != null) &&
				!lastFailedLoginDate.equals(_lastFailedLoginDate))) {
			_lastFailedLoginDate = lastFailedLoginDate;
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

	public boolean getLockout() {
		return _lockout;
	}

	public boolean isLockout() {
		return _lockout;
	}

	public void setLockout(boolean lockout) {
		if (lockout != _lockout) {
			_lockout = lockout;
		}
	}

	public Date getLockoutDate() {
		return _lockoutDate;
	}

	public void setLockoutDate(Date lockoutDate) {
		if (((lockoutDate == null) && (_lockoutDate != null)) ||
				((lockoutDate != null) && (_lockoutDate == null)) ||
				((lockoutDate != null) && (_lockoutDate != null) &&
				!lockoutDate.equals(_lockoutDate))) {
			_lockoutDate = lockoutDate;
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
		clone.setDefaultUser(getDefaultUser());
		clone.setContactId(getContactId());
		clone.setPassword(getPassword());
		clone.setPasswordEncrypted(getPasswordEncrypted());
		clone.setPasswordExpirationDate(getPasswordExpirationDate());
		clone.setPasswordReset(getPasswordReset());
		clone.setPasswordModifiedDate(getPasswordModifiedDate());
		clone.setGraceLoginCount(getGraceLoginCount());
		clone.setScreenName(getScreenName());
		clone.setEmailAddress(getEmailAddress());
		clone.setLanguageId(getLanguageId());
		clone.setTimeZoneId(getTimeZoneId());
		clone.setGreeting(getGreeting());
		clone.setComments(getComments());
		clone.setLoginDate(getLoginDate());
		clone.setLoginIP(getLoginIP());
		clone.setLastLoginDate(getLastLoginDate());
		clone.setLastLoginIP(getLastLoginIP());
		clone.setLastFailedLoginDate(getLastFailedLoginDate());
		clone.setFailedLoginAttempts(getFailedLoginAttempts());
		clone.setLockout(getLockout());
		clone.setLockoutDate(getLockoutDate());
		clone.setAgreedToTermsOfUse(getAgreedToTermsOfUse());
		clone.setActive(getActive());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		UserImpl user = (UserImpl)obj;
		long pk = user.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
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

		long pk = user.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	private long _userId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _defaultUser;
	private long _contactId;
	private String _password;
	private boolean _passwordEncrypted;
	private Date _passwordExpirationDate;
	private boolean _passwordReset;
	private Date _passwordModifiedDate;
	private int _graceLoginCount;
	private String _screenName;
	private String _emailAddress;
	private String _languageId;
	private String _timeZoneId;
	private String _greeting;
	private String _comments;
	private Date _loginDate;
	private String _loginIP;
	private Date _lastLoginDate;
	private String _lastLoginIP;
	private Date _lastFailedLoginDate;
	private int _failedLoginAttempts;
	private boolean _lockout;
	private Date _lockoutDate;
	private boolean _agreedToTermsOfUse;
	private boolean _active;
}