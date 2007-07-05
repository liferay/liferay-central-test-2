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

	public void addGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User addUser(long creatorUserId,
		long companyId, boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String jobTitle, long organizationId,
		long locationId, boolean sendEmail)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public int authenticateByEmailAddress(long companyId,
		java.lang.String emailAddress, java.lang.String password,
		java.util.Map headerMap, java.util.Map parameterMap)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public int authenticateByScreenName(long companyId,
		java.lang.String screenName, java.lang.String password,
		java.util.Map headerMap, java.util.Map parameterMap)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public int authenticateByUserId(long companyId, long userId,
		java.lang.String password, java.util.Map headerMap,
		java.util.Map parameterMap)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean authenticateForJAAS(long userId, java.lang.String encPwd)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkLockout(com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkLoginFailure(com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkLoginFailureByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkLoginFailureById(long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkLoginFailureByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.kernel.util.KeyValuePair decryptUserId(
		long companyId, java.lang.String name, java.lang.String password)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deletePasswordPolicyUser(long passwordPolicyId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteRoleUser(long roleId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteUser(long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.lang.String encryptUserId(java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getDefaultUser(long companyId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public long getDefaultUserId(long companyId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getGroupUsers(long groupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getPermissionUsers(long companyId, long groupId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String actionId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator, int begin, int end)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public int getPermissionUsersCount(long companyId, long groupId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String actionId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getRoleUsers(long roleId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserByContactId(long contactId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserById(long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserById(long companyId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User getUserByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public long getUserIdByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public long getUserIdByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasGroupUser(long groupId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasPasswordPolicyUser(long passwordPolicyId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasRoleUser(long roleId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasUserGroupUser(long userGroupId, long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean isPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean isPasswordExpiringSoon(com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List search(long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.Boolean active, java.util.LinkedHashMap params,
		boolean andSearch, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int searchCount(long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.Boolean active, java.util.LinkedHashMap params,
		boolean andSearch) throws com.liferay.portal.SystemException;

	public void sendPassword(long companyId, java.lang.String emailAddress,
		java.lang.String remoteAddr, java.lang.String remoteHost,
		java.lang.String userAgent)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void setGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void setRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void setUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsetGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsetPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsetRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateActive(long userId,
		boolean active)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateAgreedToTermsOfUse(long userId,
		boolean agreedToTermsOfUse)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateLastLogin(long userId,
		java.lang.String loginIP)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateLockout(
		com.liferay.portal.model.User user, boolean lockout)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateLockoutByEmailAddress(
		long companyId, java.lang.String emailAddress, boolean lockout)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateLockoutById(long userId,
		boolean lockout)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateLockoutByScreenName(
		long companyId, java.lang.String screenName, boolean lockout)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void updateOrganizations(long userId, long organizationId,
		long locationId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, boolean isSilentUpdate)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void updatePasswordReset(long userId, boolean passwordReset)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void updatePortrait(long userId, byte[] bytes)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.User updateUser(long userId,
		java.lang.String password, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String comments, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String icqSn, java.lang.String jabberSn,
		java.lang.String msnSn, java.lang.String skypeSn,
		java.lang.String ymSn, java.lang.String jobTitle, long organizationId,
		long locationId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;
}