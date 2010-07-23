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

	public long getPrimaryKey() {
		return _user.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_user.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _user.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_user.setUuid(uuid);
	}

	public long getUserId() {
		return _user.getUserId();
	}

	public void setUserId(long userId) {
		_user.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_user.setUserUuid(userUuid);
	}

	public long getCompanyId() {
		return _user.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_user.setCompanyId(companyId);
	}

	public java.util.Date getCreateDate() {
		return _user.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_user.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _user.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_user.setModifiedDate(modifiedDate);
	}

	public boolean getDefaultUser() {
		return _user.getDefaultUser();
	}

	public boolean isDefaultUser() {
		return _user.isDefaultUser();
	}

	public void setDefaultUser(boolean defaultUser) {
		_user.setDefaultUser(defaultUser);
	}

	public long getContactId() {
		return _user.getContactId();
	}

	public void setContactId(long contactId) {
		_user.setContactId(contactId);
	}

	public java.lang.String getPassword() {
		return _user.getPassword();
	}

	public void setPassword(java.lang.String password) {
		_user.setPassword(password);
	}

	public boolean getPasswordEncrypted() {
		return _user.getPasswordEncrypted();
	}

	public boolean isPasswordEncrypted() {
		return _user.isPasswordEncrypted();
	}

	public void setPasswordEncrypted(boolean passwordEncrypted) {
		_user.setPasswordEncrypted(passwordEncrypted);
	}

	public boolean getPasswordReset() {
		return _user.getPasswordReset();
	}

	public boolean isPasswordReset() {
		return _user.isPasswordReset();
	}

	public void setPasswordReset(boolean passwordReset) {
		_user.setPasswordReset(passwordReset);
	}

	public java.util.Date getPasswordModifiedDate() {
		return _user.getPasswordModifiedDate();
	}

	public void setPasswordModifiedDate(java.util.Date passwordModifiedDate) {
		_user.setPasswordModifiedDate(passwordModifiedDate);
	}

	public java.lang.String getDigest() {
		return _user.getDigest();
	}

	public void setDigest(java.lang.String digest) {
		_user.setDigest(digest);
	}

	public java.lang.String getReminderQueryQuestion() {
		return _user.getReminderQueryQuestion();
	}

	public void setReminderQueryQuestion(java.lang.String reminderQueryQuestion) {
		_user.setReminderQueryQuestion(reminderQueryQuestion);
	}

	public java.lang.String getReminderQueryAnswer() {
		return _user.getReminderQueryAnswer();
	}

	public void setReminderQueryAnswer(java.lang.String reminderQueryAnswer) {
		_user.setReminderQueryAnswer(reminderQueryAnswer);
	}

	public int getGraceLoginCount() {
		return _user.getGraceLoginCount();
	}

	public void setGraceLoginCount(int graceLoginCount) {
		_user.setGraceLoginCount(graceLoginCount);
	}

	public java.lang.String getScreenName() {
		return _user.getScreenName();
	}

	public void setScreenName(java.lang.String screenName) {
		_user.setScreenName(screenName);
	}

	public java.lang.String getEmailAddress() {
		return _user.getEmailAddress();
	}

	public void setEmailAddress(java.lang.String emailAddress) {
		_user.setEmailAddress(emailAddress);
	}

	public long getFacebookId() {
		return _user.getFacebookId();
	}

	public void setFacebookId(long facebookId) {
		_user.setFacebookId(facebookId);
	}

	public java.lang.String getOpenId() {
		return _user.getOpenId();
	}

	public void setOpenId(java.lang.String openId) {
		_user.setOpenId(openId);
	}

	public long getPortraitId() {
		return _user.getPortraitId();
	}

	public void setPortraitId(long portraitId) {
		_user.setPortraitId(portraitId);
	}

	public java.lang.String getLanguageId() {
		return _user.getLanguageId();
	}

	public void setLanguageId(java.lang.String languageId) {
		_user.setLanguageId(languageId);
	}

	public java.lang.String getTimeZoneId() {
		return _user.getTimeZoneId();
	}

	public void setTimeZoneId(java.lang.String timeZoneId) {
		_user.setTimeZoneId(timeZoneId);
	}

	public java.lang.String getGreeting() {
		return _user.getGreeting();
	}

	public void setGreeting(java.lang.String greeting) {
		_user.setGreeting(greeting);
	}

	public java.lang.String getComments() {
		return _user.getComments();
	}

	public void setComments(java.lang.String comments) {
		_user.setComments(comments);
	}

	public java.lang.String getFirstName() {
		return _user.getFirstName();
	}

	public void setFirstName(java.lang.String firstName) {
		_user.setFirstName(firstName);
	}

	public java.lang.String getMiddleName() {
		return _user.getMiddleName();
	}

	public void setMiddleName(java.lang.String middleName) {
		_user.setMiddleName(middleName);
	}

	public java.lang.String getLastName() {
		return _user.getLastName();
	}

	public void setLastName(java.lang.String lastName) {
		_user.setLastName(lastName);
	}

	public java.lang.String getJobTitle() {
		return _user.getJobTitle();
	}

	public void setJobTitle(java.lang.String jobTitle) {
		_user.setJobTitle(jobTitle);
	}

	public java.util.Date getLoginDate() {
		return _user.getLoginDate();
	}

	public void setLoginDate(java.util.Date loginDate) {
		_user.setLoginDate(loginDate);
	}

	public java.lang.String getLoginIP() {
		return _user.getLoginIP();
	}

	public void setLoginIP(java.lang.String loginIP) {
		_user.setLoginIP(loginIP);
	}

	public java.util.Date getLastLoginDate() {
		return _user.getLastLoginDate();
	}

	public void setLastLoginDate(java.util.Date lastLoginDate) {
		_user.setLastLoginDate(lastLoginDate);
	}

	public java.lang.String getLastLoginIP() {
		return _user.getLastLoginIP();
	}

	public void setLastLoginIP(java.lang.String lastLoginIP) {
		_user.setLastLoginIP(lastLoginIP);
	}

	public java.util.Date getLastFailedLoginDate() {
		return _user.getLastFailedLoginDate();
	}

	public void setLastFailedLoginDate(java.util.Date lastFailedLoginDate) {
		_user.setLastFailedLoginDate(lastFailedLoginDate);
	}

	public int getFailedLoginAttempts() {
		return _user.getFailedLoginAttempts();
	}

	public void setFailedLoginAttempts(int failedLoginAttempts) {
		_user.setFailedLoginAttempts(failedLoginAttempts);
	}

	public boolean getLockout() {
		return _user.getLockout();
	}

	public boolean isLockout() {
		return _user.isLockout();
	}

	public void setLockout(boolean lockout) {
		_user.setLockout(lockout);
	}

	public java.util.Date getLockoutDate() {
		return _user.getLockoutDate();
	}

	public void setLockoutDate(java.util.Date lockoutDate) {
		_user.setLockoutDate(lockoutDate);
	}

	public boolean getAgreedToTermsOfUse() {
		return _user.getAgreedToTermsOfUse();
	}

	public boolean isAgreedToTermsOfUse() {
		return _user.isAgreedToTermsOfUse();
	}

	public void setAgreedToTermsOfUse(boolean agreedToTermsOfUse) {
		_user.setAgreedToTermsOfUse(agreedToTermsOfUse);
	}

	public boolean getActive() {
		return _user.getActive();
	}

	public boolean isActive() {
		return _user.isActive();
	}

	public void setActive(boolean active) {
		_user.setActive(active);
	}

	public com.liferay.portal.model.User toEscapedModel() {
		return _user.toEscapedModel();
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
		return _user.clone();
	}

	public int compareTo(com.liferay.portal.model.User user) {
		return _user.compareTo(user);
	}

	public int hashCode() {
		return _user.hashCode();
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

	public double getSocialParticipationEquity() {
		return _user.getSocialParticipationEquity();
	}

	public double getSocialPersonalEquity() {
		return _user.getSocialPersonalEquity();
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
		throws com.liferay.portal.kernel.exception.SystemException {
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

	public void updateSocialContributionEquity(double value) {
		_user.updateSocialContributionEquity(value);
	}

	public void updateSocialParticipationEquity(double value) {
		_user.updateSocialParticipationEquity(value);
	}

	public User getWrappedUser() {
		return _user;
	}

	private User _user;
}