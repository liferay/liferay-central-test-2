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

package com.liferay.portal.service;

/**
 * <a href="UserLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is <code>com.liferay.portal.service.impl.UserLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be accessed
 * from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.UserServiceFactory
 * @see com.liferay.portal.service.UserServiceUtil
 *
 */
public interface UserLocalService {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public void addGroupUsers(long groupId, java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addPasswordPolicyUsers(long passwordPolicyId,
		java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addRoleUsers(java.lang.String roleId, java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User addUser(
		java.lang.String creatorUserId, java.lang.String companyId,
		boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean passwordReset,
		boolean autoScreenName, java.lang.String screenName,
		java.lang.String emailAddress, java.util.Locale locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String nickName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String jobTitle,
		java.lang.String organizationId, java.lang.String locationId,
		boolean sendEmail)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public int authenticateByEmailAddress(java.lang.String companyId,
		java.lang.String emailAddress, java.lang.String password,
		java.util.Map headerMap, java.util.Map parameterMap)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public int authenticateByScreenName(java.lang.String companyId,
		java.lang.String screenName, java.lang.String password,
		java.util.Map headerMap, java.util.Map parameterMap)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public int authenticateByUserId(java.lang.String companyId,
		java.lang.String userId, java.lang.String password,
		java.util.Map headerMap, java.util.Map parameterMap)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean authenticateForJAAS(java.lang.String userId,
		java.lang.String encPwd)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.kernel.util.KeyValuePair decryptUserId(
		java.lang.String companyId, java.lang.String userId,
		java.lang.String password)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deletePasswordPolicyUser(long passwordPolicyId,
		java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteRoleUser(java.lang.String roleId, java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteUser(java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.lang.String encryptUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getDefaultUser(
		java.lang.String companyId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getGroupUsers(long groupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getPermissionUsers(java.lang.String companyId,
		long groupId, java.lang.String name, java.lang.String primKey,
		java.lang.String actionId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator, int begin, int end)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public int getPermissionUsersCount(java.lang.String companyId,
		long groupId, java.lang.String name, java.lang.String primKey,
		java.lang.String actionId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getRoleUsers(java.lang.String roleId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserByContactId(long contactId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserByEmailAddress(
		java.lang.String companyId, java.lang.String emailAddress)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserById(java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserById(
		java.lang.String companyId, java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserByScreenName(
		java.lang.String companyId, java.lang.String screenName)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.lang.String getUserIdByEmailAddress(
		java.lang.String companyId, java.lang.String emailAddress)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.lang.String getUserIdByScreenName(java.lang.String companyId,
		java.lang.String screenName)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasGroupUser(long groupId, java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasPasswordPolicyUser(long passwordPolicyId,
		java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasRoleUser(java.lang.String roleId, java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasUserGroupUser(java.lang.String userGroupId,
		java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List search(java.lang.String companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String screenName,
		java.lang.String emailAddress, boolean active,
		java.util.LinkedHashMap params, boolean andSearch, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int searchCount(java.lang.String companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String screenName,
		java.lang.String emailAddress, boolean active,
		java.util.LinkedHashMap params, boolean andSearch)
		throws com.liferay.portal.SystemException;

	public void sendPassword(java.lang.String companyId,
		java.lang.String emailAddress, java.lang.String remoteAddr,
		java.lang.String remoteHost, java.lang.String userAgent)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void setGroupUsers(long groupId, java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void setRoleUsers(java.lang.String roleId, java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void setUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsetGroupUsers(long groupId, java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsetPasswordPolicyUsers(long passwordPolicyId,
		java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsetRoleUsers(java.lang.String roleId,
		java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsetUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateActive(java.lang.String userId,
		boolean active)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateAgreedToTermsOfUse(
		java.lang.String userId, boolean agreedToTermsOfUse)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateLastLogin(
		java.lang.String userId, java.lang.String loginIP)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updatePassword(
		java.lang.String userId, java.lang.String password1,
		java.lang.String password2, boolean passwordReset)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void updatePortrait(java.lang.String userId, byte[] bytes)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateUser(java.lang.String userId,
		java.lang.String password, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String resolution, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String nickName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String icqSn, java.lang.String jabberSn,
		java.lang.String msnSn, java.lang.String skypeSn,
		java.lang.String ymSn, java.lang.String jobTitle,
		java.lang.String organizationId, java.lang.String locationId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;
}