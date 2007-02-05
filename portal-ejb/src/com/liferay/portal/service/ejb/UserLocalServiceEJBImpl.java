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

package com.liferay.portal.service.ejb;

import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="UserLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserLocalServiceEJBImpl implements UserLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public void addGroupUsers(long groupId, java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalServiceFactory.getTxImpl().addGroupUsers(groupId, userIds);
	}

	public void addRoleUsers(java.lang.String roleId, java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalServiceFactory.getTxImpl().addRoleUsers(roleId, userIds);
	}

	public void addUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalServiceFactory.getTxImpl().addUserGroupUsers(userGroupId,
			userIds);
	}

	public com.liferay.portal.model.User addUser(
		java.lang.String creatorUserId, java.lang.String companyId,
		boolean autoUserId, java.lang.String userId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, java.lang.String emailAddress,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String nickName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, java.lang.String organizationId,
		java.lang.String locationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().addUser(creatorUserId,
			companyId, autoUserId, userId, autoPassword, password1, password2,
			passwordReset, emailAddress, locale, firstName, middleName,
			lastName, nickName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, organizationId, locationId);
	}

	public com.liferay.portal.model.User addUser(
		java.lang.String creatorUserId, java.lang.String companyId,
		boolean autoUserId, java.lang.String userId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, java.lang.String emailAddress,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String nickName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, java.lang.String organizationId,
		java.lang.String locationId, boolean sendEmail)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().addUser(creatorUserId,
			companyId, autoUserId, userId, autoPassword, password1, password2,
			passwordReset, emailAddress, locale, firstName, middleName,
			lastName, nickName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, organizationId, locationId,
			sendEmail);
	}

	public int authenticateByEmailAddress(java.lang.String companyId,
		java.lang.String emailAddress, java.lang.String password,
		java.util.Map headerMap, java.util.Map parameterMap)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().authenticateByEmailAddress(companyId,
			emailAddress, password, headerMap, parameterMap);
	}

	public int authenticateByUserId(java.lang.String companyId,
		java.lang.String userId, java.lang.String password,
		java.util.Map headerMap, java.util.Map parameterMap)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().authenticateByUserId(companyId,
			userId, password, headerMap, parameterMap);
	}

	public boolean authenticateForJAAS(java.lang.String userId,
		java.lang.String encPwd)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().authenticateForJAAS(userId,
			encPwd);
	}

	public com.liferay.portal.kernel.util.KeyValuePair decryptUserId(
		java.lang.String companyId, java.lang.String userId,
		java.lang.String password)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().decryptUserId(companyId,
			userId, password);
	}

	public void deleteRoleUser(java.lang.String roleId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalServiceFactory.getTxImpl().deleteRoleUser(roleId, userId);
	}

	public void deleteUser(java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalServiceFactory.getTxImpl().deleteUser(userId);
	}

	public java.lang.String encryptUserId(java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().encryptUserId(userId);
	}

	public com.liferay.portal.model.User getDefaultUser(
		java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().getDefaultUser(companyId);
	}

	public java.util.List getGroupUsers(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().getGroupUsers(groupId);
	}

	public java.util.List getPermissionUsers(java.lang.String companyId,
		long groupId, java.lang.String name, java.lang.String primKey,
		java.lang.String actionId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator, int begin, int end)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().getPermissionUsers(companyId,
			groupId, name, primKey, actionId, firstName, middleName, lastName,
			emailAddress, andOperator, begin, end);
	}

	public int getPermissionUsersCount(java.lang.String companyId,
		long groupId, java.lang.String name, java.lang.String primKey,
		java.lang.String actionId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().getPermissionUsersCount(companyId,
			groupId, name, primKey, actionId, firstName, middleName, lastName,
			emailAddress, andOperator);
	}

	public java.util.List getRoleUsers(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().getRoleUsers(roleId);
	}

	public com.liferay.portal.model.User getUserByEmailAddress(
		java.lang.String companyId, java.lang.String emailAddress)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().getUserByEmailAddress(companyId,
			emailAddress);
	}

	public com.liferay.portal.model.User getUserById(java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().getUserById(userId);
	}

	public com.liferay.portal.model.User getUserById(
		java.lang.String companyId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().getUserById(companyId, userId);
	}

	public java.lang.String getUserId(java.lang.String companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().getUserId(companyId,
			emailAddress);
	}

	public boolean hasGroupUser(long groupId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().hasGroupUser(groupId, userId);
	}

	public boolean hasRoleUser(java.lang.String roleId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().hasRoleUser(roleId, userId);
	}

	public boolean hasUserGroupUser(java.lang.String userGroupId,
		java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().hasUserGroupUser(userGroupId,
			userId);
	}

	public java.util.List search(java.lang.String companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String emailAddress,
		boolean active, java.util.LinkedHashMap params, boolean andSearch,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().search(companyId, firstName,
			middleName, lastName, emailAddress, active, params, andSearch,
			begin, end, obc);
	}

	public int searchCount(java.lang.String companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String emailAddress,
		boolean active, java.util.LinkedHashMap params, boolean andSearch)
		throws com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().searchCount(companyId,
			firstName, middleName, lastName, emailAddress, active, params,
			andSearch);
	}

	public void sendPassword(java.lang.String companyId,
		java.lang.String emailAddress, java.lang.String remoteAddr,
		java.lang.String remoteHost, java.lang.String userAgent)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalServiceFactory.getTxImpl().sendPassword(companyId,
			emailAddress, remoteAddr, remoteHost, userAgent);
	}

	public void setGroupUsers(long groupId, java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalServiceFactory.getTxImpl().setGroupUsers(groupId, userIds);
	}

	public void setRoleUsers(java.lang.String roleId, java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalServiceFactory.getTxImpl().setRoleUsers(roleId, userIds);
	}

	public void setUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalServiceFactory.getTxImpl().setUserGroupUsers(userGroupId,
			userIds);
	}

	public void unsetGroupUsers(long groupId, java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalServiceFactory.getTxImpl().unsetGroupUsers(groupId, userIds);
	}

	public void unsetRoleUsers(java.lang.String roleId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalServiceFactory.getTxImpl().unsetRoleUsers(roleId, userIds);
	}

	public void unsetUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalServiceFactory.getTxImpl().unsetUserGroupUsers(userGroupId,
			userIds);
	}

	public com.liferay.portal.model.User updateActive(java.lang.String userId,
		boolean active)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().updateActive(userId, active);
	}

	public com.liferay.portal.model.User updateAgreedToTermsOfUse(
		java.lang.String userId, boolean agreedToTermsOfUse)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().updateAgreedToTermsOfUse(userId,
			agreedToTermsOfUse);
	}

	public com.liferay.portal.model.User updateLastLogin(
		java.lang.String userId, java.lang.String loginIP)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().updateLastLogin(userId,
			loginIP);
	}

	public com.liferay.portal.model.User updatePassword(
		java.lang.String userId, java.lang.String password1,
		java.lang.String password2, boolean passwordReset)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().updatePassword(userId,
			password1, password2, passwordReset);
	}

	public void updatePortrait(java.lang.String userId, byte[] bytes)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalServiceFactory.getTxImpl().updatePortrait(userId, bytes);
	}

	public com.liferay.portal.model.User updateUser(java.lang.String userId,
		java.lang.String password, java.lang.String emailAddress,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.lang.String greeting, java.lang.String resolution,
		java.lang.String comments, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String nickName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String smsSn, java.lang.String aimSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String skypeSn, java.lang.String ymSn,
		java.lang.String jobTitle, java.lang.String organizationId,
		java.lang.String locationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return UserLocalServiceFactory.getTxImpl().updateUser(userId, password,
			emailAddress, languageId, timeZoneId, greeting, resolution,
			comments, firstName, middleName, lastName, nickName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear, smsSn,
			aimSn, icqSn, jabberSn, msnSn, skypeSn, ymSn, jobTitle,
			organizationId, locationId);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}