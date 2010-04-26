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

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="UserModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the User_ table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       User
 * @see       com.liferay.portal.model.impl.UserImpl
 * @see       com.liferay.portal.model.impl.UserModelImpl
 * @generated
 */
public interface UserModel extends BaseModel<User> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	@AutoEscape
	public String getUuid();

	public void setUuid(String uuid);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserUuid() throws SystemException;

	public void setUserUuid(String userUuid);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public boolean getDefaultUser();

	public boolean isDefaultUser();

	public void setDefaultUser(boolean defaultUser);

	public long getContactId();

	public void setContactId(long contactId);

	@AutoEscape
	public String getPassword();

	public void setPassword(String password);

	public boolean getPasswordEncrypted();

	public boolean isPasswordEncrypted();

	public void setPasswordEncrypted(boolean passwordEncrypted);

	public boolean getPasswordReset();

	public boolean isPasswordReset();

	public void setPasswordReset(boolean passwordReset);

	public Date getPasswordModifiedDate();

	public void setPasswordModifiedDate(Date passwordModifiedDate);

	@AutoEscape
	public String getReminderQueryQuestion();

	public void setReminderQueryQuestion(String reminderQueryQuestion);

	@AutoEscape
	public String getReminderQueryAnswer();

	public void setReminderQueryAnswer(String reminderQueryAnswer);

	public int getGraceLoginCount();

	public void setGraceLoginCount(int graceLoginCount);

	@AutoEscape
	public String getScreenName();

	public void setScreenName(String screenName);

	@AutoEscape
	public String getEmailAddress();

	public void setEmailAddress(String emailAddress);

	@AutoEscape
	public String getOpenId();

	public void setOpenId(String openId);

	public long getPortraitId();

	public void setPortraitId(long portraitId);

	@AutoEscape
	public String getLanguageId();

	public void setLanguageId(String languageId);

	@AutoEscape
	public String getTimeZoneId();

	public void setTimeZoneId(String timeZoneId);

	@AutoEscape
	public String getGreeting();

	public void setGreeting(String greeting);

	@AutoEscape
	public String getComments();

	public void setComments(String comments);

	@AutoEscape
	public String getFirstName();

	public void setFirstName(String firstName);

	@AutoEscape
	public String getMiddleName();

	public void setMiddleName(String middleName);

	@AutoEscape
	public String getLastName();

	public void setLastName(String lastName);

	@AutoEscape
	public String getJobTitle();

	public void setJobTitle(String jobTitle);

	public Date getLoginDate();

	public void setLoginDate(Date loginDate);

	@AutoEscape
	public String getLoginIP();

	public void setLoginIP(String loginIP);

	public Date getLastLoginDate();

	public void setLastLoginDate(Date lastLoginDate);

	@AutoEscape
	public String getLastLoginIP();

	public void setLastLoginIP(String lastLoginIP);

	public Date getLastFailedLoginDate();

	public void setLastFailedLoginDate(Date lastFailedLoginDate);

	public int getFailedLoginAttempts();

	public void setFailedLoginAttempts(int failedLoginAttempts);

	public boolean getLockout();

	public boolean isLockout();

	public void setLockout(boolean lockout);

	public Date getLockoutDate();

	public void setLockoutDate(Date lockoutDate);

	public boolean getAgreedToTermsOfUse();

	public boolean isAgreedToTermsOfUse();

	public void setAgreedToTermsOfUse(boolean agreedToTermsOfUse);

	public boolean getActive();

	public boolean isActive();

	public void setActive(boolean active);

	public double getSocialContributionEquity();

	public void setSocialContributionEquity(double socialContributionEquity);

	public double getSocialParticipationK();

	public void setSocialParticipationK(double socialParticipationK);

	public double getSocialParticipationB();

	public void setSocialParticipationB(double socialParticipationB);

	public double getSocialParticipationEquity();

	public void setSocialParticipationEquity(double socialParticipationEquity);

	public double getSocialPersonalEquity();

	public void setSocialPersonalEquity(double socialPersonalEquity);

	public User toEscapedModel();

	public boolean isNew();

	public boolean setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(User user);

	public int hashCode();

	public String toString();

	public String toXmlString();
}