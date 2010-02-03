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

import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.ServiceContext;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <a href="LDAPUserHolder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Edward Han
 */
public class LDAPUserHolder {
	public LDAPUserHolder(
			User userData, Contact contactData,
			ServiceContext serviceContext, boolean autoPassword,
			boolean updatePassword, long creatorUserId, boolean passwordReset,
			boolean autoScreenName, long[] groupIds, long[] organizationIds,
			long[] roleIds, List<UserGroupRole> userGroupRoles,
			long[] userGroupIds, boolean sendEmail) {
		this(
			userData, contactData,
			Collections.EMPTY_MAP, Collections.EMPTY_MAP,
			serviceContext, autoPassword,
			updatePassword, creatorUserId, passwordReset, autoScreenName,
			groupIds, organizationIds, roleIds, userGroupRoles,
			userGroupIds, sendEmail);
	}

	public LDAPUserHolder(
			User userData, Contact contactData,
			Map<String, String> userExpandoData,
			Map<String, String> contactExpandoData,
			ServiceContext serviceContext, boolean autoPassword,
			boolean updatePassword, long creatorUserId, boolean passwordReset,
			boolean autoScreenName, long[] groupIds, long[] organizationIds,
			long[] roleIds, List<UserGroupRole> userGroupRoles,
			long[] userGroupIds, boolean sendEmail) {

		_userData = userData;
		_contactData = contactData;
		_userExpandoData = userExpandoData;
		_contactExpandoData = contactExpandoData;
		_serviceContext = serviceContext;
		_autoPassword = autoPassword;
		_updatePassword = updatePassword;
		_creatorUserId = creatorUserId;
		_passwordReset = passwordReset;
		_autoScreenName = autoScreenName;
		_groupIds = groupIds;
		_organizationIds = organizationIds;
		_roleIds = roleIds;
		_userGroupRoles = userGroupRoles;
		_userGroupIds = userGroupIds;
		_sendEmail = sendEmail;
	}

	public User getUserData() {
		return _userData;
	}

	public void setUserData(User userData) {
		_userData = userData;
	}

	public Contact getContactData() {
		return _contactData;
	}

	public void setContactData(Contact contactData) {
		_contactData = contactData;
	}

	public Map<String, String> getUserExpandoData() {
		return _userExpandoData;
	}

	public void setUserExpandoData(Map<String, String> userExpandoData) {
		_userExpandoData = userExpandoData;
	}

	public ServiceContext getServiceContext() {
		return _serviceContext;
	}

	public void setServiceContext(ServiceContext serviceContext) {
		_serviceContext = serviceContext;
	}

	public boolean isAutoPassword() {
		return _autoPassword;
	}

	public void setAutoPassword(boolean autoPassword) {
		_autoPassword = autoPassword;
	}

	public boolean isUpdatePassword() {
		return _updatePassword;
	}

	public void setUpdatePassword(boolean updatePassword) {
		_updatePassword = updatePassword;
	}

	public long getCreatorUserId() {
		return _creatorUserId;
	}

	public void setCreatorUserId(long creatorUserId) {
		_creatorUserId = creatorUserId;
	}

	public boolean isPasswordReset() {
		return _passwordReset;
	}

	public void setPasswordReset(boolean passwordReset) {
		_passwordReset = passwordReset;
	}

	public boolean isAutoScreenName() {
		return _autoScreenName;
	}

	public void setAutoScreenName(boolean autoScreenName) {
		_autoScreenName = autoScreenName;
	}

	public long[] getGroupIds() {
		return _groupIds;
	}

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public long[] getOrganizationIds() {
		return _organizationIds;
	}

	public void setOrganizationIds(long[] organizationIds) {
		_organizationIds = organizationIds;
	}

	public long[] getRoleIds() {
		return _roleIds;
	}

	public void setRoleIds(long[] roleIds) {
		_roleIds = roleIds;
	}

	public List<UserGroupRole> getUserGroupRoles() {
		return _userGroupRoles;
	}

	public void setUserGroupRoles(List<UserGroupRole> userGroupRoles) {
		_userGroupRoles = userGroupRoles;
	}

	public long[] getUserGroupIds() {
		return _userGroupIds;
	}

	public void setUserGroupIds(long[] userGroupIds) {
		_userGroupIds = userGroupIds;
	}

	public boolean isSendEmail() {
		return _sendEmail;
	}

	public void setSendEmail(boolean sendEmail) {
		_sendEmail = sendEmail;
	}

	//	User getters
	public long getUserId() {
		return _userData.getUserId();
	}
	public String getReminderQueryQuestion() {
		return _userData.getReminderQueryQuestion();
	}
	public String getReminderQueryAnswer() {
		return _userData.getReminderQueryAnswer();
	}
	public String getScreenName() {
		return _userData.getScreenName();
	}

	public String getEmailAddress() {
		return _userData.getEmailAddress();
	}

	public String getOpenId() {
		return _userData.getOpenId();
	}

	public String getLanguageId() {
		return _userData.getLanguageId();
	}

	public String getTimeZoneId() {
		return _userData.getTimeZoneId();
	}

	public String getGreeting() {
		return _userData.getGreeting();
	}

	public String getComments() {
		return _userData.getComments();
	}

	public String getFirstName() {
		return _userData.getFirstName();
	}

	public String getMiddleName() {
		return _userData.getMiddleName();
	}

	public String getLastName() {
		return _userData.getLastName();
	}

	public Locale getLocale() {
		return _userData.getLocale();
	}

	public String getJobTitle() {
		return _userData.getJobTitle();
	}

	//	Contact getters
	public int getPrefixId() {
		return _contactData.getPrefixId();
	}

	public int getSuffixId() {
		return _contactData.getSuffixId();
	}

	public boolean getMale() {
		return _contactData.getMale();
	}

	public String getSmsSn() {
		return _contactData.getSmsSn();
	}

	public String getAimSn() {
		return _contactData.getAimSn();
	}

	public String getFacebookSn() {
		return _contactData.getFacebookSn();
	}

	public String getIcqSn() {
		return _contactData.getIcqSn();
	}

	public String getJabberSn() {
		return _contactData.getJabberSn();
	}

	public String getMsnSn() {
		return _contactData.getMsnSn();
	}

	public String getMySpaceSn() {
		return _contactData.getMySpaceSn();
	}

	public String getSkypeSn() {
		return _contactData.getSkypeSn();
	}

	public String getTwitterSn() {
		return _contactData.getTwitterSn();
	}

	public String getYmSn() {
		return _contactData.getYmSn();
	}

	public Date getBirthday() {
		return _contactData.getBirthday();
	}

	private User _userData;
	private Contact _contactData;
	private Map<String, String> _userExpandoData;
	private Map<String, String> _contactExpandoData;

	private ServiceContext _serviceContext;
	private boolean _autoPassword;
	private boolean _updatePassword;
	private long _creatorUserId;
	private boolean _passwordReset;
	private boolean _autoScreenName;
	private long[] _groupIds;
	private long[] _organizationIds;
	private long[] _roleIds;
	private List<UserGroupRole> _userGroupRoles;
	private long[] _userGroupIds;
	private boolean _sendEmail;
}