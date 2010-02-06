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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.ServiceContext;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * <a href="LDAPUser.java.html"><b><i>View Source</i></b></a>
 *
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class LDAPUser {

	public String getAimSn() {
		return _contact.getAimSn();
	}

	public Date getBirthday() {
		return _contact.getBirthday();
	}

	public String getComments() {
		return _user.getComments();
	}

	public Contact getContact() {
		return _contact;
	}

	public long getCreatorUserId() {
		return _creatorUserId;
	}

	public String getEmailAddress() {
		return _user.getEmailAddress();
	}

	public String getFacebookSn() {
		return _contact.getFacebookSn();
	}

	public String getFirstName() {
		return _contact.getFirstName();
	}

	public String getGreeting() {
		return _user.getGreeting();
	}

	public long[] getGroupIds() {
		return _groupIds;
	}

	public String getIcqSn() {
		return _contact.getIcqSn();
	}

	public String getJabberSn() {
		return _contact.getJabberSn();
	}

	public String getJobTitle() {
		return _contact.getJobTitle();
	}

	public String getLanguageId() {
		return _user.getLanguageId();
	}

	public String getLastName() {
		return _contact.getLastName();
	}

	public Locale getLocale() {
		return _user.getLocale();
	}

	public String getMiddleName() {
		return _contact.getMiddleName();
	}

	public String getMsnSn() {
		return _contact.getMsnSn();
	}

	public String getMySpaceSn() {
		return _contact.getMySpaceSn();
	}

	public String getOpenId() {
		return _user.getOpenId();
	}

	public long[] getOrganizationIds() {
		return _organizationIds;
	}

	public int getPrefixId() {
		return _contact.getPrefixId();
	}

	public String getReminderQueryAnswer() {
		return _user.getReminderQueryAnswer();
	}

	public String getReminderQueryQuestion() {
		return _user.getReminderQueryQuestion();
	}

	public long[] getRoleIds() {
		return _roleIds;
	}

	public String getScreenName() {
		return _user.getScreenName();
	}

	public ServiceContext getServiceContext() {
		return _serviceContext;
	}

	public String getSkypeSn() {
		return _contact.getSkypeSn();
	}

	public String getSmsSn() {
		return _contact.getSmsSn();
	}

	public int getSuffixId() {
		return _contact.getSuffixId();
	}

	public String getTimeZoneId() {
		return _user.getTimeZoneId();
	}

	public String getTwitterSn() {
		return _contact.getTwitterSn();
	}

	public User getUser() {
		return _user;
	}

	public long[] getUserGroupIds() {
		return _userGroupIds;
	}

	public List<UserGroupRole> getUserGroupRoles() {
		return _userGroupRoles;
	}

	public String getYmSn() {
		return _contact.getYmSn();
	}

	public boolean isAutoPassword() {
		return _autoPassword;
	}

	public boolean isAutoScreenName() {
		return _autoScreenName;
	}

	public boolean isMale() {
		return _contact.isMale();
	}

	public void isMale(boolean male) {
		_contact.setMale(male);
	}

	public boolean isPasswordReset() {
		return _passwordReset;
	}

	public boolean isSendEmail() {
		return _sendEmail;
	}

	public boolean isUpdatePassword() {
		return _updatePassword;
	}

	public void setAimSn(String aimSn) {
		_contact.setAimSn(aimSn);
	}

	public void setAutoPassword(boolean autoPassword) {
		_autoPassword = autoPassword;
	}

	public void setAutoScreenName(boolean autoScreenName) {
		_autoScreenName = autoScreenName;
	}

	public void setBirthday(Date birthday) {
		_contact.setBirthday(birthday);
	}

	public void setComments(String comments) {
		_user.setComments(comments);
	}

	public void setContact(Contact contact) {
		_contact = contact;
	}

	public void setCreatorUserId(long creatorUserId) {
		_creatorUserId = creatorUserId;
	}

	public void setEmailAddress(String emailAddress) {
		_user.setEmailAddress(emailAddress);
	}

	public void setFacebookSn(String facebookSn) {
		_contact.setFacebookSn(facebookSn);
	}

	public void setFirstName(String firstName) {
		_contact.setFirstName(firstName);
	}

	public void setGreeting(String greeting) {
		_user.setGreeting(greeting);
	}

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public void setIcqSn(String icqSn) {
		_contact.setIcqSn(icqSn);
	}

	public void setJabberSn(String jabberSn) {
		_contact.setJabberSn(jabberSn);
	}

	public void setJobTitle(String jobTitle) {
		_contact.setJobTitle(jobTitle);
	}

	public void setLanguageId(String languageId) {
		_user.setLanguageId(languageId);
	}

	public void setLastName(String lastName) {
		_contact.setLastName(lastName);
	}

	public void setLocale(Locale locale) {
		_user.setLanguageId(LocaleUtil.toLanguageId(locale));
	}

	public void setMiddleName(String middleName) {
		_contact.setMiddleName(middleName);
	}

	public void setMsnSn(String msnSn) {
		_contact.setMsnSn(msnSn);
	}

	public void setMySpaceSn(String mySpaceSn) {
		_contact.setMySpaceSn(mySpaceSn);
	}

	public void setOpenId(String openId) {
		_user.setOpenId(openId);
	}

	public void setOrganizationIds(long[] organizationIds) {
		_organizationIds = organizationIds;
	}

	public void setPasswordReset(boolean passwordReset) {
		_passwordReset = passwordReset;
	}

	public void setPrefixId(int prefixId) {
		_contact.setPrefixId(prefixId);
	}

	public void setReminderQueryAnswer(String reminderQueryAnswer) {
		_user.setReminderQueryAnswer(reminderQueryAnswer);
	}

	public void setReminderQueryQuestion(String reminderQueryQuestion) {
		_user.setReminderQueryQuestion(reminderQueryQuestion);
	}

	public void setRoleIds(long[] roleIds) {
		_roleIds = roleIds;
	}

	public void setScreenName(String screenName) {
		_user.setScreenName(screenName);
	}

	public void setSendEmail(boolean sendEmail) {
		_sendEmail = sendEmail;
	}

	public void setServiceContext(ServiceContext serviceContext) {
		_serviceContext = serviceContext;
	}

	public void setSkypeSn(String skypeSn) {
		_contact.setSkypeSn(skypeSn);
	}

	public void setSmsSn(String smsSn) {
		_contact.setSmsSn(smsSn);
	}

	public void setSuffixId(int suffixId) {
		_contact.setSuffixId(suffixId);
	}

	public void setTimeZoneId(String timeZoneId) {
		_user.setTimeZoneId(timeZoneId);
	}

	public void setTwitterSn(String twitterSn) {
		_contact.setTwitterSn(twitterSn);
	}

	public void setUpdatePassword(boolean updatePassword) {
		_updatePassword = updatePassword;
	}

	public void setUser(User user) {
		_user = user;
	}

	public void setUserGroupIds(long[] userGroupIds) {
		_userGroupIds = userGroupIds;
	}

	public void setUserGroupRoles(List<UserGroupRole> userGroupRoles) {
		_userGroupRoles = userGroupRoles;
	}

	public void setYmSn(String ymSn) {
		_contact.setYmSn(ymSn);
	}

	private boolean _autoPassword;
	private boolean _autoScreenName;
	private Contact _contact;
	private long _creatorUserId;
	private long[] _groupIds;
	private long[] _organizationIds;
	private boolean _passwordReset;
	private long[] _roleIds;
	private boolean _sendEmail;
	private ServiceContext _serviceContext;
	private boolean _updatePassword;
	private User _user;
	private long[] _userGroupIds;
	private List<UserGroupRole> _userGroupRoles;

}