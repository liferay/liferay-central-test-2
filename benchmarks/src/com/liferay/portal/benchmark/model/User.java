/*
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.benchmark.model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <a href="User.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class User {

	public User(long companyId, long userId, String screenName,
				String emailAddress, String password, Contact contact) {
		_companyId = companyId;
		_userId = userId;
		_screenName = screenName;
		_emailAddress = emailAddress;
		_contact = contact;
		_agreedToTermsOfUse = true;
		_active = true;
		_password = password;
		_createDate = new Date();
		_modifiedDate = new Date();
		_passwordModifiedDate = new Date();
		_languageId = "en_US";
		_timeZoneId = "UTC";
		_greeting = "Welcome " + contact.getFirstName() + " " +
				contact.getLastName() + "!";
	}

	public String getUuid() {
		return _uuid;
	}

	public long getUserId() {
		return _userId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean isDefaultUser() {
		return _defaultUser;
	}

	public long getContactId() {
		return _contactId;
	}

	public String getPassword() {
		return _password;
	}

	public boolean isPasswordEncrypted() {
		return _passwordEncrypted;
	}

	public boolean isPasswordReset() {
		return _passwordReset;
	}

	public Date getPasswordModifiedDate() {
		return _passwordModifiedDate;
	}

	public String getReminderQueryQuestion() {
		return _reminderQueryQuestion;
	}

	public String getReminderQueryAnswer() {
		return _reminderQueryAnswer;
	}

	public int getGraceLoginCount() {
		return _graceLoginCount;
	}

	public String getScreenName() {
		return _screenName;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public String getOpenId() {
		return _openId;
	}

	public long getPortraitId() {
		return _portraitId;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public String getTimeZoneId() {
		return _timeZoneId;
	}

	public String getGreeting() {
		return _greeting;
	}

	public String getComments() {
		return _comments;
	}

	public Date getLoginDate() {
		return _loginDate;
	}

	public String getLoginIP() {
		return _loginIP;
	}

	public Date getLastLoginDate() {
		return _lastLoginDate;
	}

	public String getLastLoginIP() {
		return _lastLoginIP;
	}

	public Date getLastFailedLoginDate() {
		return _lastFailedLoginDate;
	}

	public int getFailedLoginAttempts() {
		return _failedLoginAttempts;
	}

	public boolean isLockout() {
		return _lockout;
	}

	public Date getLockoutDate() {
		return _lockoutDate;
	}

	public boolean isAgreedToTermsOfUse() {
		return _agreedToTermsOfUse;
	}

	public boolean isActive() {
		return _active;
	}

	public Contact getContact() {
		return _contact;
	}

	public List<GroupRole> getCommunityRoles() {
		return _communityRoles;
	}

	public void addCommunityRole(GroupRole role) {
		_communityRoles.add(role);
	}

	public List<OrganizationRole> getOrganizationRoles() {
		return _organizationRoles;
	}

	public void addOrganizationRole(OrganizationRole role) {
		_organizationRoles.add(role);
	}

	public List<Role> getRoles() {
		return _roles;
	}

	public void addRoles(Collection<Role> roles) {
		for (Role role : roles) {
			addRole(role);
		}
	}

	public void addRole(Role role) {
		if (role.getRoleType() != Scope.COMPANY) {
			throw new IllegalArgumentException("Role is not a portal wide role");
		}
		_roles.add(role);
	}

	public void addOrganization(Organization org) {
		_organizations.add(org);
	}

	public List<Organization> getOrganizations() {
		return _organizations;
	}

	public void addCommunity(Group org) {
		_communities.add(org);
	}

	public List<Group> getCommunities() {
		return _communities;
	}

	public Group getPrivateGroup() {
		return _privateGroup;
	}

	public LayoutSet getPublicLayoutSet() {
		return _publicLayoutSet;
	}

	public LayoutSet getPrivateLayoutSet() {
		return _privateLayoutSet;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public void setDefaultUser(boolean defaultUser) {
		_defaultUser = defaultUser;
	}

	public void setContactId(long contactId) {
		_contactId = contactId;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setPasswordEncrypted(boolean passwordEncrypted) {
		_passwordEncrypted = passwordEncrypted;
	}

	public void setPasswordReset(boolean passwordReset) {
		_passwordReset = passwordReset;
	}

	public void setPasswordModifiedDate(Date passwordModifiedDate) {
		_passwordModifiedDate = passwordModifiedDate;
	}

	public void setReminderQueryQuestion(String reminderQueryQuestion) {
		_reminderQueryQuestion = reminderQueryQuestion;
	}

	public void setReminderQueryAnswer(String reminderQueryAnswer) {
		_reminderQueryAnswer = reminderQueryAnswer;
	}

	public void setGraceLoginCount(int graceLoginCount) {
		_graceLoginCount = graceLoginCount;
	}

	public void setScreenName(String screenName) {
		_screenName = screenName;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setOpenId(String openId) {
		_openId = openId;
	}

	public void setPortraitId(long portraitId) {
		_portraitId = portraitId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setTimeZoneId(String timeZoneId) {
		_timeZoneId = timeZoneId;
	}

	public void setGreeting(String greeting) {
		_greeting = greeting;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public void setLoginDate(Date loginDate) {
		_loginDate = loginDate;
	}

	public void setLoginIP(String loginIP) {
		_loginIP = loginIP;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		_lastLoginDate = lastLoginDate;
	}

	public void setLastLoginIP(String lastLoginIP) {
		_lastLoginIP = lastLoginIP;
	}

	public void setLastFailedLoginDate(Date lastFailedLoginDate) {
		_lastFailedLoginDate = lastFailedLoginDate;
	}

	public void setFailedLoginAttempts(int failedLoginAttempts) {
		_failedLoginAttempts = failedLoginAttempts;
	}

	public void setLockout(boolean lockout) {
		_lockout = lockout;
	}

	public void setLockoutDate(Date lockoutDate) {
		_lockoutDate = lockoutDate;
	}

	public void setAgreedToTermsOfUse(boolean agreedToTermsOfUse) {
		_agreedToTermsOfUse = agreedToTermsOfUse;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setContact(Contact contact) {
		_contact = contact;
	}

	public void setPublicLayoutSet(LayoutSet publicLayoutSet) {
		_publicLayoutSet = publicLayoutSet;
	}

	public void setPrivateLayoutSet(LayoutSet privateLayoutSet) {
		_privateLayoutSet = privateLayoutSet;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setPrivateGroup(Group privateGroup) {
		_privateGroup = privateGroup;
		_privateGroup.setFriendlyURL("/" + _screenName);
	}

	private String _uuid = "";
	private long _userId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _defaultUser;
	private long _contactId;
	private String _password;
	private boolean _passwordEncrypted;
	private boolean _passwordReset;
	private Date _passwordModifiedDate = new Date();
	private String _reminderQueryQuestion = "";
	private String _reminderQueryAnswer = "";
	private int _graceLoginCount;
	private String _screenName;
	private String _emailAddress;
	private String _openId = "";
	private long _portraitId;
	private String _languageId;
	private String _timeZoneId;
	private String _greeting;
	private String _comments = "";
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
	private List<Group> _communities = new ArrayList<Group>();
	private List<GroupRole> _communityRoles = new ArrayList<GroupRole>();
	private List<Role> _roles = new ArrayList<Role>();
	private List<Organization> _organizations = new ArrayList<Organization>();
	private List<OrganizationRole> _organizationRoles = new ArrayList<OrganizationRole>();
	private Group _privateGroup;

	private LayoutSet _publicLayoutSet;
	private LayoutSet _privateLayoutSet;
}
