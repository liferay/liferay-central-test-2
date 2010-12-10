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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link User}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       User
 * @generated
 */
public class UserWrapper implements User {
	public UserWrapper(User user) {
		_user = user;
	}

	/**
	* Gets the primary key of this user.
	*
	* @return the primary key of this user
	*/
	public long getPrimaryKey() {
		return _user.getPrimaryKey();
	}

	/**
	* Sets the primary key of this user
	*
	* @param pk the primary key of this user
	*/
	public void setPrimaryKey(long pk) {
		_user.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this user.
	*
	* @return the uuid of this user
	*/
	public java.lang.String getUuid() {
		return _user.getUuid();
	}

	/**
	* Sets the uuid of this user.
	*
	* @param uuid the uuid of this user
	*/
	public void setUuid(java.lang.String uuid) {
		_user.setUuid(uuid);
	}

	/**
	* Gets the user id of this user.
	*
	* @return the user id of this user
	*/
	public long getUserId() {
		return _user.getUserId();
	}

	/**
	* Sets the user id of this user.
	*
	* @param userId the user id of this user
	*/
	public void setUserId(long userId) {
		_user.setUserId(userId);
	}

	/**
	* Gets the user uuid of this user.
	*
	* @return the user uuid of this user
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getUserUuid();
	}

	/**
	* Sets the user uuid of this user.
	*
	* @param userUuid the user uuid of this user
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_user.setUserUuid(userUuid);
	}

	/**
	* Gets the company id of this user.
	*
	* @return the company id of this user
	*/
	public long getCompanyId() {
		return _user.getCompanyId();
	}

	/**
	* Sets the company id of this user.
	*
	* @param companyId the company id of this user
	*/
	public void setCompanyId(long companyId) {
		_user.setCompanyId(companyId);
	}

	/**
	* Gets the create date of this user.
	*
	* @return the create date of this user
	*/
	public java.util.Date getCreateDate() {
		return _user.getCreateDate();
	}

	/**
	* Sets the create date of this user.
	*
	* @param createDate the create date of this user
	*/
	public void setCreateDate(java.util.Date createDate) {
		_user.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this user.
	*
	* @return the modified date of this user
	*/
	public java.util.Date getModifiedDate() {
		return _user.getModifiedDate();
	}

	/**
	* Sets the modified date of this user.
	*
	* @param modifiedDate the modified date of this user
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_user.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the default user of this user.
	*
	* @return the default user of this user
	*/
	public boolean getDefaultUser() {
		return _user.getDefaultUser();
	}

	/**
	* Determines if this user is default user.
	*
	* @return <code>true</code> if this user is default user; <code>false</code> otherwise
	*/
	public boolean isDefaultUser() {
		return _user.isDefaultUser();
	}

	/**
	* Sets whether this user is default user.
	*
	* @param defaultUser the default user of this user
	*/
	public void setDefaultUser(boolean defaultUser) {
		_user.setDefaultUser(defaultUser);
	}

	/**
	* Gets the contact id of this user.
	*
	* @return the contact id of this user
	*/
	public long getContactId() {
		return _user.getContactId();
	}

	/**
	* Sets the contact id of this user.
	*
	* @param contactId the contact id of this user
	*/
	public void setContactId(long contactId) {
		_user.setContactId(contactId);
	}

	/**
	* Gets the password of this user.
	*
	* @return the password of this user
	*/
	public java.lang.String getPassword() {
		return _user.getPassword();
	}

	/**
	* Sets the password of this user.
	*
	* @param password the password of this user
	*/
	public void setPassword(java.lang.String password) {
		_user.setPassword(password);
	}

	/**
	* Gets the password encrypted of this user.
	*
	* @return the password encrypted of this user
	*/
	public boolean getPasswordEncrypted() {
		return _user.getPasswordEncrypted();
	}

	/**
	* Determines if this user is password encrypted.
	*
	* @return <code>true</code> if this user is password encrypted; <code>false</code> otherwise
	*/
	public boolean isPasswordEncrypted() {
		return _user.isPasswordEncrypted();
	}

	/**
	* Sets whether this user is password encrypted.
	*
	* @param passwordEncrypted the password encrypted of this user
	*/
	public void setPasswordEncrypted(boolean passwordEncrypted) {
		_user.setPasswordEncrypted(passwordEncrypted);
	}

	/**
	* Gets the password reset of this user.
	*
	* @return the password reset of this user
	*/
	public boolean getPasswordReset() {
		return _user.getPasswordReset();
	}

	/**
	* Determines if this user is password reset.
	*
	* @return <code>true</code> if this user is password reset; <code>false</code> otherwise
	*/
	public boolean isPasswordReset() {
		return _user.isPasswordReset();
	}

	/**
	* Sets whether this user is password reset.
	*
	* @param passwordReset the password reset of this user
	*/
	public void setPasswordReset(boolean passwordReset) {
		_user.setPasswordReset(passwordReset);
	}

	/**
	* Gets the password modified date of this user.
	*
	* @return the password modified date of this user
	*/
	public java.util.Date getPasswordModifiedDate() {
		return _user.getPasswordModifiedDate();
	}

	/**
	* Sets the password modified date of this user.
	*
	* @param passwordModifiedDate the password modified date of this user
	*/
	public void setPasswordModifiedDate(java.util.Date passwordModifiedDate) {
		_user.setPasswordModifiedDate(passwordModifiedDate);
	}

	/**
	* Gets the digest of this user.
	*
	* @return the digest of this user
	*/
	public java.lang.String getDigest() {
		return _user.getDigest();
	}

	/**
	* Sets the digest of this user.
	*
	* @param digest the digest of this user
	*/
	public void setDigest(java.lang.String digest) {
		_user.setDigest(digest);
	}

	/**
	* Gets the reminder query question of this user.
	*
	* @return the reminder query question of this user
	*/
	public java.lang.String getReminderQueryQuestion() {
		return _user.getReminderQueryQuestion();
	}

	/**
	* Sets the reminder query question of this user.
	*
	* @param reminderQueryQuestion the reminder query question of this user
	*/
	public void setReminderQueryQuestion(java.lang.String reminderQueryQuestion) {
		_user.setReminderQueryQuestion(reminderQueryQuestion);
	}

	/**
	* Gets the reminder query answer of this user.
	*
	* @return the reminder query answer of this user
	*/
	public java.lang.String getReminderQueryAnswer() {
		return _user.getReminderQueryAnswer();
	}

	/**
	* Sets the reminder query answer of this user.
	*
	* @param reminderQueryAnswer the reminder query answer of this user
	*/
	public void setReminderQueryAnswer(java.lang.String reminderQueryAnswer) {
		_user.setReminderQueryAnswer(reminderQueryAnswer);
	}

	/**
	* Gets the grace login count of this user.
	*
	* @return the grace login count of this user
	*/
	public int getGraceLoginCount() {
		return _user.getGraceLoginCount();
	}

	/**
	* Sets the grace login count of this user.
	*
	* @param graceLoginCount the grace login count of this user
	*/
	public void setGraceLoginCount(int graceLoginCount) {
		_user.setGraceLoginCount(graceLoginCount);
	}

	/**
	* Gets the screen name of this user.
	*
	* @return the screen name of this user
	*/
	public java.lang.String getScreenName() {
		return _user.getScreenName();
	}

	/**
	* Sets the screen name of this user.
	*
	* @param screenName the screen name of this user
	*/
	public void setScreenName(java.lang.String screenName) {
		_user.setScreenName(screenName);
	}

	/**
	* Gets the email address of this user.
	*
	* @return the email address of this user
	*/
	public java.lang.String getEmailAddress() {
		return _user.getEmailAddress();
	}

	/**
	* Sets the email address of this user.
	*
	* @param emailAddress the email address of this user
	*/
	public void setEmailAddress(java.lang.String emailAddress) {
		_user.setEmailAddress(emailAddress);
	}

	/**
	* Gets the facebook id of this user.
	*
	* @return the facebook id of this user
	*/
	public long getFacebookId() {
		return _user.getFacebookId();
	}

	/**
	* Sets the facebook id of this user.
	*
	* @param facebookId the facebook id of this user
	*/
	public void setFacebookId(long facebookId) {
		_user.setFacebookId(facebookId);
	}

	/**
	* Gets the open id of this user.
	*
	* @return the open id of this user
	*/
	public java.lang.String getOpenId() {
		return _user.getOpenId();
	}

	/**
	* Sets the open id of this user.
	*
	* @param openId the open id of this user
	*/
	public void setOpenId(java.lang.String openId) {
		_user.setOpenId(openId);
	}

	/**
	* Gets the portrait id of this user.
	*
	* @return the portrait id of this user
	*/
	public long getPortraitId() {
		return _user.getPortraitId();
	}

	/**
	* Sets the portrait id of this user.
	*
	* @param portraitId the portrait id of this user
	*/
	public void setPortraitId(long portraitId) {
		_user.setPortraitId(portraitId);
	}

	/**
	* Gets the language id of this user.
	*
	* @return the language id of this user
	*/
	public java.lang.String getLanguageId() {
		return _user.getLanguageId();
	}

	/**
	* Sets the language id of this user.
	*
	* @param languageId the language id of this user
	*/
	public void setLanguageId(java.lang.String languageId) {
		_user.setLanguageId(languageId);
	}

	/**
	* Gets the time zone id of this user.
	*
	* @return the time zone id of this user
	*/
	public java.lang.String getTimeZoneId() {
		return _user.getTimeZoneId();
	}

	/**
	* Sets the time zone id of this user.
	*
	* @param timeZoneId the time zone id of this user
	*/
	public void setTimeZoneId(java.lang.String timeZoneId) {
		_user.setTimeZoneId(timeZoneId);
	}

	/**
	* Gets the greeting of this user.
	*
	* @return the greeting of this user
	*/
	public java.lang.String getGreeting() {
		return _user.getGreeting();
	}

	/**
	* Sets the greeting of this user.
	*
	* @param greeting the greeting of this user
	*/
	public void setGreeting(java.lang.String greeting) {
		_user.setGreeting(greeting);
	}

	/**
	* Gets the comments of this user.
	*
	* @return the comments of this user
	*/
	public java.lang.String getComments() {
		return _user.getComments();
	}

	/**
	* Sets the comments of this user.
	*
	* @param comments the comments of this user
	*/
	public void setComments(java.lang.String comments) {
		_user.setComments(comments);
	}

	/**
	* Gets the first name of this user.
	*
	* @return the first name of this user
	*/
	public java.lang.String getFirstName() {
		return _user.getFirstName();
	}

	/**
	* Sets the first name of this user.
	*
	* @param firstName the first name of this user
	*/
	public void setFirstName(java.lang.String firstName) {
		_user.setFirstName(firstName);
	}

	/**
	* Gets the middle name of this user.
	*
	* @return the middle name of this user
	*/
	public java.lang.String getMiddleName() {
		return _user.getMiddleName();
	}

	/**
	* Sets the middle name of this user.
	*
	* @param middleName the middle name of this user
	*/
	public void setMiddleName(java.lang.String middleName) {
		_user.setMiddleName(middleName);
	}

	/**
	* Gets the last name of this user.
	*
	* @return the last name of this user
	*/
	public java.lang.String getLastName() {
		return _user.getLastName();
	}

	/**
	* Sets the last name of this user.
	*
	* @param lastName the last name of this user
	*/
	public void setLastName(java.lang.String lastName) {
		_user.setLastName(lastName);
	}

	/**
	* Gets the job title of this user.
	*
	* @return the job title of this user
	*/
	public java.lang.String getJobTitle() {
		return _user.getJobTitle();
	}

	/**
	* Sets the job title of this user.
	*
	* @param jobTitle the job title of this user
	*/
	public void setJobTitle(java.lang.String jobTitle) {
		_user.setJobTitle(jobTitle);
	}

	/**
	* Gets the login date of this user.
	*
	* @return the login date of this user
	*/
	public java.util.Date getLoginDate() {
		return _user.getLoginDate();
	}

	/**
	* Sets the login date of this user.
	*
	* @param loginDate the login date of this user
	*/
	public void setLoginDate(java.util.Date loginDate) {
		_user.setLoginDate(loginDate);
	}

	/**
	* Gets the login i p of this user.
	*
	* @return the login i p of this user
	*/
	public java.lang.String getLoginIP() {
		return _user.getLoginIP();
	}

	/**
	* Sets the login i p of this user.
	*
	* @param loginIP the login i p of this user
	*/
	public void setLoginIP(java.lang.String loginIP) {
		_user.setLoginIP(loginIP);
	}

	/**
	* Gets the last login date of this user.
	*
	* @return the last login date of this user
	*/
	public java.util.Date getLastLoginDate() {
		return _user.getLastLoginDate();
	}

	/**
	* Sets the last login date of this user.
	*
	* @param lastLoginDate the last login date of this user
	*/
	public void setLastLoginDate(java.util.Date lastLoginDate) {
		_user.setLastLoginDate(lastLoginDate);
	}

	/**
	* Gets the last login i p of this user.
	*
	* @return the last login i p of this user
	*/
	public java.lang.String getLastLoginIP() {
		return _user.getLastLoginIP();
	}

	/**
	* Sets the last login i p of this user.
	*
	* @param lastLoginIP the last login i p of this user
	*/
	public void setLastLoginIP(java.lang.String lastLoginIP) {
		_user.setLastLoginIP(lastLoginIP);
	}

	/**
	* Gets the last failed login date of this user.
	*
	* @return the last failed login date of this user
	*/
	public java.util.Date getLastFailedLoginDate() {
		return _user.getLastFailedLoginDate();
	}

	/**
	* Sets the last failed login date of this user.
	*
	* @param lastFailedLoginDate the last failed login date of this user
	*/
	public void setLastFailedLoginDate(java.util.Date lastFailedLoginDate) {
		_user.setLastFailedLoginDate(lastFailedLoginDate);
	}

	/**
	* Gets the failed login attempts of this user.
	*
	* @return the failed login attempts of this user
	*/
	public int getFailedLoginAttempts() {
		return _user.getFailedLoginAttempts();
	}

	/**
	* Sets the failed login attempts of this user.
	*
	* @param failedLoginAttempts the failed login attempts of this user
	*/
	public void setFailedLoginAttempts(int failedLoginAttempts) {
		_user.setFailedLoginAttempts(failedLoginAttempts);
	}

	/**
	* Gets the lockout of this user.
	*
	* @return the lockout of this user
	*/
	public boolean getLockout() {
		return _user.getLockout();
	}

	/**
	* Determines if this user is lockout.
	*
	* @return <code>true</code> if this user is lockout; <code>false</code> otherwise
	*/
	public boolean isLockout() {
		return _user.isLockout();
	}

	/**
	* Sets whether this user is lockout.
	*
	* @param lockout the lockout of this user
	*/
	public void setLockout(boolean lockout) {
		_user.setLockout(lockout);
	}

	/**
	* Gets the lockout date of this user.
	*
	* @return the lockout date of this user
	*/
	public java.util.Date getLockoutDate() {
		return _user.getLockoutDate();
	}

	/**
	* Sets the lockout date of this user.
	*
	* @param lockoutDate the lockout date of this user
	*/
	public void setLockoutDate(java.util.Date lockoutDate) {
		_user.setLockoutDate(lockoutDate);
	}

	/**
	* Gets the agreed to terms of use of this user.
	*
	* @return the agreed to terms of use of this user
	*/
	public boolean getAgreedToTermsOfUse() {
		return _user.getAgreedToTermsOfUse();
	}

	/**
	* Determines if this user is agreed to terms of use.
	*
	* @return <code>true</code> if this user is agreed to terms of use; <code>false</code> otherwise
	*/
	public boolean isAgreedToTermsOfUse() {
		return _user.isAgreedToTermsOfUse();
	}

	/**
	* Sets whether this user is agreed to terms of use.
	*
	* @param agreedToTermsOfUse the agreed to terms of use of this user
	*/
	public void setAgreedToTermsOfUse(boolean agreedToTermsOfUse) {
		_user.setAgreedToTermsOfUse(agreedToTermsOfUse);
	}

	/**
	* Gets the active of this user.
	*
	* @return the active of this user
	*/
	public boolean getActive() {
		return _user.getActive();
	}

	/**
	* Determines if this user is active.
	*
	* @return <code>true</code> if this user is active; <code>false</code> otherwise
	*/
	public boolean isActive() {
		return _user.isActive();
	}

	/**
	* Sets whether this user is active.
	*
	* @param active the active of this user
	*/
	public void setActive(boolean active) {
		_user.setActive(active);
	}

	public boolean isNew() {
		return _user.isNew();
	}

	public void setNew(boolean n) {
		_user.setNew(n);
	}

	public boolean isCachedModel() {
		return _user.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_user.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _user.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_user.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _user.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _user.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_user.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new UserWrapper((User)_user.clone());
	}

	public int compareTo(com.liferay.portal.model.User user) {
		return _user.compareTo(user);
	}

	public int hashCode() {
		return _user.hashCode();
	}

	public com.liferay.portal.model.User toEscapedModel() {
		return new UserWrapper(_user.toEscapedModel());
	}

	public java.lang.String toString() {
		return _user.toString();
	}

	public java.lang.String toXmlString() {
		return _user.toXmlString();
	}

	public java.util.Date getBirthday()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getBirthday();
	}

	public java.lang.String getCompanyMx()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getCompanyMx();
	}

	public com.liferay.portal.model.Contact getContact()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getContact();
	}

	public java.lang.String getDigest(java.lang.String password) {
		return _user.getDigest(password);
	}

	public java.lang.String getDisplayEmailAddress() {
		return _user.getDisplayEmailAddress();
	}

	public java.lang.String getDisplayURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getDisplayURL(themeDisplay);
	}

	public java.lang.String getDisplayURL(java.lang.String portalURL,
		java.lang.String mainPath)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getDisplayURL(portalURL, mainPath);
	}

	public boolean getFemale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getFemale();
	}

	public java.lang.String getFullName() {
		return _user.getFullName();
	}

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getGroup();
	}

	public long[] getGroupIds()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getGroupIds();
	}

	public java.util.List<com.liferay.portal.model.Group> getGroups()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getGroups();
	}

	public java.util.Locale getLocale() {
		return _user.getLocale();
	}

	public java.lang.String getLogin()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getLogin();
	}

	public boolean getMale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMale();
	}

	public java.util.List<com.liferay.portal.model.Group> getMyPlaces()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMyPlaces();
	}

	public java.util.List<com.liferay.portal.model.Group> getMyPlaces(int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMyPlaces(max);
	}

	public java.util.List<com.liferay.portal.model.Group> getMyPlaces(
		java.lang.String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMyPlaces(classNames, max);
	}

	public long[] getOrganizationIds()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getOrganizationIds();
	}

	public java.util.List<com.liferay.portal.model.Organization> getOrganizations()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getOrganizations();
	}

	public boolean getPasswordModified() {
		return _user.getPasswordModified();
	}

	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getPasswordPolicy();
	}

	public java.lang.String getPasswordUnencrypted() {
		return _user.getPasswordUnencrypted();
	}

	public java.lang.String getPortraitURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getPortraitURL(themeDisplay);
	}

	public int getPrivateLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getPrivateLayoutsPageCount();
	}

	public int getPublicLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getPublicLayoutsPageCount();
	}

	public java.util.Set<java.lang.String> getReminderQueryQuestions()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getReminderQueryQuestions();
	}

	public long[] getRoleIds()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getRoleIds();
	}

	public java.util.List<com.liferay.portal.model.Role> getRoles()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getRoles();
	}

	public double getSocialContributionEquity() {
		return _user.getSocialContributionEquity();
	}

	public double getSocialContributionEquity(long groupId) {
		return _user.getSocialContributionEquity(groupId);
	}

	public double getSocialParticipationEquity() {
		return _user.getSocialParticipationEquity();
	}

	public double getSocialParticipationEquity(long groupId) {
		return _user.getSocialParticipationEquity(groupId);
	}

	public double getSocialPersonalEquity() {
		return _user.getSocialPersonalEquity();
	}

	public double getSocialPersonalEquity(long groupId) {
		return _user.getSocialPersonalEquity(groupId);
	}

	public long[] getTeamIds()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getTeamIds();
	}

	public java.util.List<com.liferay.portal.model.Team> getTeams()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getTeams();
	}

	public long[] getUserGroupIds()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getUserGroupIds();
	}

	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getUserGroups();
	}

	public java.util.TimeZone getTimeZone() {
		return _user.getTimeZone();
	}

	public boolean hasCompanyMx()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasCompanyMx();
	}

	public boolean hasCompanyMx(java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasCompanyMx(emailAddress);
	}

	public boolean hasMyPlaces()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasMyPlaces();
	}

	public boolean hasOrganization()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasOrganization();
	}

	public boolean hasPrivateLayouts()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasPrivateLayouts();
	}

	public boolean hasPublicLayouts()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasPublicLayouts();
	}

	public boolean hasReminderQuery() {
		return _user.hasReminderQuery();
	}

	public boolean isFemale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.isFemale();
	}

	public boolean isMale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.isMale();
	}

	public boolean isPasswordModified() {
		return _user.isPasswordModified();
	}

	public void setPasswordModified(boolean passwordModified) {
		_user.setPasswordModified(passwordModified);
	}

	public void setPasswordUnencrypted(java.lang.String passwordUnencrypted) {
		_user.setPasswordUnencrypted(passwordUnencrypted);
	}

	/**
	* @deprecated {@link #updateSocialContributionEquity(long, double)}
	*/
	public void updateSocialContributionEquity(double value) {
		_user.updateSocialContributionEquity(value);
	}

	public void updateSocialContributionEquity(long groupId, double value) {
		_user.updateSocialContributionEquity(groupId, value);
	}

	/**
	* @deprecated {@link #updateSocialParticipationEquity(long, double)}
	*/
	public void updateSocialParticipationEquity(double value) {
		_user.updateSocialParticipationEquity(value);
	}

	public void updateSocialParticipationEquity(long groupId, double value) {
		_user.updateSocialParticipationEquity(groupId, value);
	}

	public User getWrappedUser() {
		return _user;
	}

	private User _user;
}