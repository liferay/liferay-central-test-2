/*
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

package com.liferay.portal.model;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

/**
 * <a href="MockUserImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class MockUserImpl extends MockBaseModelImpl<User> implements User {

	public MockUserImpl(
		long userId, String email, String firstName, String middleName,
		String lastName, boolean active) {
		_userId = userId;
		_active = active;
		_emailAddress = email;
		_firstName = firstName;
		_middleName = middleName;
		_lastName = lastName;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public void setOriginalUserId(long originalUserId) {
		_originalUserId = originalUserId;
	}

	public boolean isSetOriginalUserId() {
		return _setOriginalUserId;
	}

	public void setSetOriginalUserId(boolean setOriginalUserId) {
		_setOriginalUserId = setOriginalUserId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	public void setOriginalCompanyId(long originalCompanyId) {
		_originalCompanyId = originalCompanyId;
	}

	public boolean isSetOriginalCompanyId() {
		return _setOriginalCompanyId;
	}

	public void setSetOriginalCompanyId(boolean setOriginalCompanyId) {
		_setOriginalCompanyId = setOriginalCompanyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public boolean isDefaultUser() {
		return _defaultUser;
	}

	public void setDefaultUser(boolean defaultUser) {
		_defaultUser = defaultUser;
	}

	public boolean isOriginalDefaultUser() {
		return _originalDefaultUser;
	}

	public void setOriginalDefaultUser(boolean originalDefaultUser) {
		_originalDefaultUser = originalDefaultUser;
	}

	public boolean isSetOriginalDefaultUser() {
		return _setOriginalDefaultUser;
	}

	public void setSetOriginalDefaultUser(boolean setOriginalDefaultUser) {
		_setOriginalDefaultUser = setOriginalDefaultUser;
	}

	public long getContactId() {
		return _contactId;
	}

	public void setContactId(long contactId) {
		_contactId = contactId;
	}

	public long getOriginalContactId() {
		return _originalContactId;
	}

	public void setOriginalContactId(long originalContactId) {
		_originalContactId = originalContactId;
	}

	public boolean isSetOriginalContactId() {
		return _setOriginalContactId;
	}

	public void setSetOriginalContactId(boolean setOriginalContactId) {
		_setOriginalContactId = setOriginalContactId;
	}

	public String getPassword() {
		return _password;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public boolean isPasswordEncrypted() {
		return _passwordEncrypted;
	}

	public void setPasswordEncrypted(boolean passwordEncrypted) {
		_passwordEncrypted = passwordEncrypted;
	}

	public boolean isPasswordReset() {
		return _passwordReset;
	}

	public void setPasswordReset(boolean passwordReset) {
		_passwordReset = passwordReset;
	}

	public Date getPasswordModifiedDate() {
		return _passwordModifiedDate;
	}

	public void setPasswordModifiedDate(Date passwordModifiedDate) {
		_passwordModifiedDate = passwordModifiedDate;
	}

	public String getReminderQueryQuestion() {
		return _reminderQueryQuestion;
	}

	public void setReminderQueryQuestion(String reminderQueryQuestion) {
		_reminderQueryQuestion = reminderQueryQuestion;
	}

	public String getReminderQueryAnswer() {
		return _reminderQueryAnswer;
	}

	public void setReminderQueryAnswer(String reminderQueryAnswer) {
		_reminderQueryAnswer = reminderQueryAnswer;
	}

	public int getGraceLoginCount() {
		return _graceLoginCount;
	}

	public void setGraceLoginCount(int graceLoginCount) {
		_graceLoginCount = graceLoginCount;
	}

	public String getScreenName() {
		return _screenName;
	}

	public void setScreenName(String screenName) {
		_screenName = screenName;
	}

	public String getOriginalScreenName() {
		return _originalScreenName;
	}

	public void setOriginalScreenName(String originalScreenName) {
		_originalScreenName = originalScreenName;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public String getOriginalEmailAddress() {
		return _originalEmailAddress;
	}

	public void setOriginalEmailAddress(String originalEmailAddress) {
		_originalEmailAddress = originalEmailAddress;
	}

	public String getOpenId() {
		return _openId;
	}

	public void setOpenId(String openId) {
		_openId = openId;
	}

	public String getOriginalOpenId() {
		return _originalOpenId;
	}

	public void setOriginalOpenId(String originalOpenId) {
		_originalOpenId = originalOpenId;
	}

	public long getPortraitId() {
		return _portraitId;
	}

	public void setPortraitId(long portraitId) {
		_portraitId = portraitId;
	}

	public long getOriginalPortraitId() {
		return _originalPortraitId;
	}

	public void setOriginalPortraitId(long originalPortraitId) {
		_originalPortraitId = originalPortraitId;
	}

	public boolean isSetOriginalPortraitId() {
		return _setOriginalPortraitId;
	}

	public void setSetOriginalPortraitId(boolean setOriginalPortraitId) {
		_setOriginalPortraitId = setOriginalPortraitId;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public String getTimeZoneId() {
		return _timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		_timeZoneId = timeZoneId;
	}

	public String getGreeting() {
		return _greeting;
	}

	public void setGreeting(String greeting) {
		_greeting = greeting;
	}

	public String getComments() {
		return _comments;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public String getFirstName() {
		return _firstName;
	}

	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	public String getMiddleName() {
		return _middleName;
	}

	public void setMiddleName(String middleName) {
		_middleName = middleName;
	}

	public String getLastName() {
		return _lastName;
	}

	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	public String getJobTitle() {
		return _jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	public Date getLoginDate() {
		return _loginDate;
	}

	public void setLoginDate(Date loginDate) {
		_loginDate = loginDate;
	}

	public String getLoginIP() {
		return _loginIP;
	}

	public void setLoginIP(String loginIP) {
		_loginIP = loginIP;
	}

	public Date getLastLoginDate() {
		return _lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		_lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIP() {
		return _lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		_lastLoginIP = lastLoginIP;
	}

	public Date getLastFailedLoginDate() {
		return _lastFailedLoginDate;
	}

	public void setLastFailedLoginDate(Date lastFailedLoginDate) {
		_lastFailedLoginDate = lastFailedLoginDate;
	}

	public int getFailedLoginAttempts() {
		return _failedLoginAttempts;
	}

	public void setFailedLoginAttempts(int failedLoginAttempts) {
		_failedLoginAttempts = failedLoginAttempts;
	}

	public boolean isLockout() {
		return _lockout;
	}

	public void setLockout(boolean lockout) {
		_lockout = lockout;
	}

	public Date getLockoutDate() {
		return _lockoutDate;
	}

	public void setLockoutDate(Date lockoutDate) {
		_lockoutDate = lockoutDate;
	}

	public boolean isAgreedToTermsOfUse() {
		return _agreedToTermsOfUse;
	}

	public void setAgreedToTermsOfUse(boolean agreedToTermsOfUse) {
		_agreedToTermsOfUse = agreedToTermsOfUse;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public Date getBirthday() {
		return _contact.getBirthday();
	}

	public String getCompanyMx() {
		return null;
	}

	public Contact getContact() {
		return _contact;
	}

	public String getDisplayURL(
		ThemeDisplay themeDisplay) {
		return null;
	}

	public String getDisplayURL(String portalURL, String mainPath) {
		return null;
	}

	public boolean getFemale() {
		return _contact.isMale();
	}

	public String getFullName() {
		return _firstName + " " + _middleName + " " + _lastName;
	}

	public Group getGroup() {
		return null;
	}

	public long[] getGroupIds() {
		return new long[0];
	}

	public List<Group> getGroups() {
		return null;
	}

	public Locale getLocale() {
		return null;
	}

	public String getLogin() throws PortalException, SystemException {
		return null;
	}

	public boolean getMale() {
		return _contact.isMale();
	}

	public List<Group> getMyPlaces() {
		return null;
	}

	public List<Group> getMyPlaces(int max) {
		return null;
	}

	public long[] getOrganizationIds() {
		return new long[0];
	}

	public List<Organization> getOrganizations() {
		return null;
	}

	public boolean getPasswordModified() {
		return false;
	}

	public PasswordPolicy getPasswordPolicy()
		throws PortalException, SystemException {
		return null;
	}

	public String getPasswordUnencrypted() {
		return null;
	}

	public int getPrivateLayoutsPageCount() {
		return 0;
	}

	public int getPublicLayoutsPageCount() {
		return 0;
	}

	public Set<String> getReminderQueryQuestions()
		throws PortalException, SystemException {
		return null;
	}

	public long[] getRoleIds() {
		return new long[0];
	}

	public List<Role> getRoles() {
		return null;
	}

	public long[] getUserGroupIds() {
		return new long[0];
	}

	public List<UserGroup> getUserGroups() {
		return null;
	}

	public TimeZone getTimeZone() {
		return null;
	}

	public boolean hasCompanyMx() {
		return false;
	}

	public boolean hasCompanyMx(String emailAddress) {
		return false;
	}

	public boolean hasMyPlaces() {
		return false;
	}

	public boolean hasOrganization() {
		return false;
	}

	public boolean hasPrivateLayouts() {
		return false;
	}

	public boolean hasPublicLayouts() {
		return false;
	}

	public boolean isFemale() {
		return !_contact.isMale();
	}

	public boolean isMale() {
		return _contact.isMale();
	}

	public boolean isPasswordModified() {
		return false;
	}

	public void setPasswordModified(boolean passwordModified) {

	}

	public void setPasswordUnencrypted(String passwordUnencrypted) {

	}

	public long getPrimaryKey() {
		return 0;
	}

	public void setPrimaryKey(long pk) {

	}

	public boolean getDefaultUser() {
		return isDefaultUser();
	}

	public boolean getPasswordEncrypted() {
		return isPasswordEncrypted();
	}

	public boolean getPasswordReset() {
		return isPasswordReset();
	}

	public boolean getLockout() {
		return isLockout();
	}

	public boolean getAgreedToTermsOfUse() {
		return isAgreedToTermsOfUse();
	}

	public boolean getActive() {
		return isActive();
	}

	public User toEscapedModel() {
		return null;
	}

	private String _uuid;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _defaultUser;
	private boolean _originalDefaultUser;
	private boolean _setOriginalDefaultUser;
	private long _contactId;
	private long _originalContactId;
	private boolean _setOriginalContactId;
	private String _password;
	private boolean _passwordEncrypted;
	private boolean _passwordReset;
	private Date _passwordModifiedDate;
	private String _reminderQueryQuestion;
	private String _reminderQueryAnswer;
	private int _graceLoginCount;
	private String _screenName;
	private String _originalScreenName;
	private String _emailAddress;
	private String _originalEmailAddress;
	private String _openId;
	private String _originalOpenId;
	private long _portraitId;
	private long _originalPortraitId;
	private boolean _setOriginalPortraitId;
	private String _languageId;
	private String _timeZoneId;
	private String _greeting;
	private String _comments;
	private String _firstName;
	private String _middleName;
	private String _lastName;
	private String _jobTitle;
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
	private Contact _contact;
}


