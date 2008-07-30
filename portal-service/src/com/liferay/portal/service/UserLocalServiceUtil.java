/**
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

package com.liferay.portal.service;


/**
 * <a href="UserLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.UserLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.UserLocalService
 *
 */
public class UserLocalServiceUtil {
	public static com.liferay.portal.model.User addUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException {
		return _service.addUser(user);
	}

	public static void deleteUser(long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteUser(userId);
	}

	public static void deleteUser(com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException {
		_service.deleteUser(user);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.User getUser(long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getUser(userId);
	}

	public static java.util.List<com.liferay.portal.model.User> getUsers(
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.getUsers(start, end);
	}

	public static int getUsersCount() throws com.liferay.portal.SystemException {
		return _service.getUsersCount();
	}

	public static com.liferay.portal.model.User updateUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException {
		return _service.updateUser(user);
	}

	public static void addGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.addGroupUsers(groupId, userIds);
	}

	public static void addOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.addOrganizationUsers(organizationId, userIds);
	}

	public static void addPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds) throws com.liferay.portal.SystemException {
		_service.addPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public static void addRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException {
		_service.addRoleUsers(roleId, userIds);
	}

	public static void addUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.addUserGroupUsers(userGroupId, userIds);
	}

	public static com.liferay.portal.model.User addUser(long creatorUserId,
		long companyId, boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String jobTitle, long[] organizationIds,
		boolean sendEmail)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addUser(creatorUserId, companyId, autoPassword,
			password1, password2, autoScreenName, screenName, emailAddress,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			organizationIds, sendEmail);
	}

	public static int authenticateByEmailAddress(long companyId,
		java.lang.String emailAddress, java.lang.String password,
		java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.authenticateByEmailAddress(companyId, emailAddress,
			password, headerMap, parameterMap);
	}

	public static int authenticateByScreenName(long companyId,
		java.lang.String screenName, java.lang.String password,
		java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.authenticateByScreenName(companyId, screenName,
			password, headerMap, parameterMap);
	}

	public static int authenticateByUserId(long companyId, long userId,
		java.lang.String password, java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.authenticateByUserId(companyId, userId, password,
			headerMap, parameterMap);
	}

	public static long authenticateForBasic(long companyId,
		java.lang.String authType, java.lang.String login,
		java.lang.String password)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.authenticateForBasic(companyId, authType, login,
			password);
	}

	public static boolean authenticateForJAAS(long userId,
		java.lang.String encPassword) {
		return _service.authenticateForJAAS(userId, encPassword);
	}

	public static void checkLockout(com.liferay.portal.model.User user)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.checkLockout(user);
	}

	public static void checkLoginFailure(com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException {
		_service.checkLoginFailure(user);
	}

	public static void checkLoginFailureByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.checkLoginFailureByEmailAddress(companyId, emailAddress);
	}

	public static void checkLoginFailureById(long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.checkLoginFailureById(userId);
	}

	public static void checkLoginFailureByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.checkLoginFailureByScreenName(companyId, screenName);
	}

	public static void checkPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.checkPasswordExpired(user);
	}

	public static void clearOrganizationUsers(long organizationId)
		throws com.liferay.portal.SystemException {
		_service.clearOrganizationUsers(organizationId);
	}

	public static void clearUserGroupUsers(long userGroupId)
		throws com.liferay.portal.SystemException {
		_service.clearUserGroupUsers(userGroupId);
	}

	public static com.liferay.portal.kernel.util.KeyValuePair decryptUserId(
		long companyId, java.lang.String name, java.lang.String password)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.decryptUserId(companyId, name, password);
	}

	public static void deletePasswordPolicyUser(long passwordPolicyId,
		long userId) throws com.liferay.portal.SystemException {
		_service.deletePasswordPolicyUser(passwordPolicyId, userId);
	}

	public static void deleteRoleUser(long roleId, long userId)
		throws com.liferay.portal.SystemException {
		_service.deleteRoleUser(roleId, userId);
	}

	public static java.lang.String encryptUserId(java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.encryptUserId(name);
	}

	public static com.liferay.portal.model.User getDefaultUser(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getDefaultUser(companyId);
	}

	public static long getDefaultUserId(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getDefaultUserId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.User> getGroupUsers(
		long groupId) throws com.liferay.portal.SystemException {
		return _service.getGroupUsers(groupId);
	}

	public static int getGroupUsersCount(long groupId)
		throws com.liferay.portal.SystemException {
		return _service.getGroupUsersCount(groupId);
	}

	public static int getGroupUsersCount(long groupId, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getGroupUsersCount(groupId, active);
	}

	public static java.util.List<com.liferay.portal.model.User> getNoAnnouncementsDeliveries(
		java.lang.String type) throws com.liferay.portal.SystemException {
		return _service.getNoAnnouncementsDeliveries(type);
	}

	public static java.util.List<com.liferay.portal.model.User> getOrganizationUsers(
		long organizationId) throws com.liferay.portal.SystemException {
		return _service.getOrganizationUsers(organizationId);
	}

	public static int getOrganizationUsersCount(long organizationId)
		throws com.liferay.portal.SystemException {
		return _service.getOrganizationUsersCount(organizationId);
	}

	public static int getOrganizationUsersCount(long organizationId,
		boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getOrganizationUsersCount(organizationId, active);
	}

	public static java.util.List<com.liferay.portal.model.User> getPermissionUsers(
		long companyId, long groupId, java.lang.String name,
		java.lang.String primKey, java.lang.String actionId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String emailAddress,
		boolean andOperator, int start, int end)
		throws com.liferay.portal.SystemException {
		return _service.getPermissionUsers(companyId, groupId, name, primKey,
			actionId, firstName, middleName, lastName, emailAddress,
			andOperator, start, end);
	}

	public static int getPermissionUsersCount(long companyId, long groupId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String actionId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String emailAddress, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return _service.getPermissionUsersCount(companyId, groupId, name,
			primKey, actionId, firstName, middleName, lastName, emailAddress,
			andOperator);
	}

	public static java.util.List<com.liferay.portal.model.User> getRoleUsers(
		long roleId) throws com.liferay.portal.SystemException {
		return _service.getRoleUsers(roleId);
	}

	public static int getRoleUsersCount(long roleId)
		throws com.liferay.portal.SystemException {
		return _service.getRoleUsersCount(roleId);
	}

	public static int getRoleUsersCount(long roleId, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getRoleUsersCount(roleId, active);
	}

	public static java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getSocialUsers(userId, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getSocialUsers(userId, type, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId1, long userId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getSocialUsers(userId1, userId2, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId1, long userId2, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getSocialUsers(userId1, userId2, type, start, end, obc);
	}

	public static int getSocialUsersCount(long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getSocialUsersCount(userId);
	}

	public static int getSocialUsersCount(long userId, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getSocialUsersCount(userId, type);
	}

	public static int getSocialUsersCount(long userId1, long userId2)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getSocialUsersCount(userId1, userId2);
	}

	public static int getSocialUsersCount(long userId1, long userId2, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getSocialUsersCount(userId1, userId2, type);
	}

	public static java.util.List<com.liferay.portal.model.User> getUserGroupUsers(
		long userGroupId) throws com.liferay.portal.SystemException {
		return _service.getUserGroupUsers(userGroupId);
	}

	public static int getUserGroupUsersCount(long userGroupId)
		throws com.liferay.portal.SystemException {
		return _service.getUserGroupUsersCount(userGroupId);
	}

	public static int getUserGroupUsersCount(long userGroupId, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getUserGroupUsersCount(userGroupId, active);
	}

	public static com.liferay.portal.model.User getUserByContactId(
		long contactId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getUserByContactId(contactId);
	}

	public static com.liferay.portal.model.User getUserByEmailAddress(
		long companyId, java.lang.String emailAddress)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getUserByEmailAddress(companyId, emailAddress);
	}

	public static com.liferay.portal.model.User getUserById(long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getUserById(userId);
	}

	public static com.liferay.portal.model.User getUserById(long companyId,
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getUserById(companyId, userId);
	}

	public static com.liferay.portal.model.User getUserByOpenId(
		java.lang.String openId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getUserByOpenId(openId);
	}

	public static com.liferay.portal.model.User getUserByPortraitId(
		long portraitId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getUserByPortraitId(portraitId);
	}

	public static com.liferay.portal.model.User getUserByScreenName(
		long companyId, java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getUserByScreenName(companyId, screenName);
	}

	public static long getUserIdByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getUserIdByEmailAddress(companyId, emailAddress);
	}

	public static long getUserIdByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getUserIdByScreenName(companyId, screenName);
	}

	public static boolean hasGroupUser(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return _service.hasGroupUser(groupId, userId);
	}

	public static boolean hasOrganizationUser(long organizationId, long userId)
		throws com.liferay.portal.SystemException {
		return _service.hasOrganizationUser(organizationId, userId);
	}

	public static boolean hasPasswordPolicyUser(long passwordPolicyId,
		long userId) throws com.liferay.portal.SystemException {
		return _service.hasPasswordPolicyUser(passwordPolicyId, userId);
	}

	public static boolean hasRoleUser(long roleId, long userId)
		throws com.liferay.portal.SystemException {
		return _service.hasRoleUser(roleId, userId);
	}

	public static boolean hasUserGroupUser(long userGroupId, long userId)
		throws com.liferay.portal.SystemException {
		return _service.hasUserGroupUser(userGroupId, userId);
	}

	public static boolean isPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.isPasswordExpired(user);
	}

	public static boolean isPasswordExpiringSoon(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.isPasswordExpiringSoon(user);
	}

	public static java.util.List<com.liferay.portal.model.User> search(
		long companyId, java.lang.String keywords, java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _service.search(companyId, keywords, active, params, start, end,
			obc);
	}

	public static java.util.List<com.liferay.portal.model.User> search(
		long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, boolean andSearch,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _service.search(companyId, firstName, middleName, lastName,
			screenName, emailAddress, active, params, andSearch, start, end, obc);
	}

	public static int searchCount(long companyId, java.lang.String keywords,
		java.lang.Boolean active, java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.SystemException {
		return _service.searchCount(companyId, keywords, active, params);
	}

	public static int searchCount(long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, boolean andSearch)
		throws com.liferay.portal.SystemException {
		return _service.searchCount(companyId, firstName, middleName, lastName,
			screenName, emailAddress, active, params, andSearch);
	}

	public static void sendPassword(long companyId,
		java.lang.String emailAddress, java.lang.String remoteAddr,
		java.lang.String remoteHost, java.lang.String userAgent)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.sendPassword(companyId, emailAddress, remoteAddr, remoteHost,
			userAgent);
	}

	public static void setRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException {
		_service.setRoleUsers(roleId, userIds);
	}

	public static void setUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.setUserGroupUsers(userGroupId, userIds);
	}

	public static void unsetGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.SystemException {
		_service.unsetGroupUsers(groupId, userIds);
	}

	public static void unsetOrganizationUsers(long organizationId,
		long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.unsetOrganizationUsers(organizationId, userIds);
	}

	public static void unsetPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds) throws com.liferay.portal.SystemException {
		_service.unsetPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public static void unsetRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.SystemException {
		_service.unsetRoleUsers(roleId, userIds);
	}

	public static void unsetRoleUsers(long roleId,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.SystemException {
		_service.unsetRoleUsers(roleId, users);
	}

	public static void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.SystemException {
		_service.unsetUserGroupUsers(userGroupId, userIds);
	}

	public static com.liferay.portal.model.User updateActive(long userId,
		boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateActive(userId, active);
	}

	public static com.liferay.portal.model.User updateAgreedToTermsOfUse(
		long userId, boolean agreedToTermsOfUse)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateAgreedToTermsOfUse(userId, agreedToTermsOfUse);
	}

	public static com.liferay.portal.model.User updateCreateDate(long userId,
		java.util.Date createDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateCreateDate(userId, createDate);
	}

	public static com.liferay.portal.model.User updateLastLogin(long userId,
		java.lang.String loginIP)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateLastLogin(userId, loginIP);
	}

	public static com.liferay.portal.model.User updateLockout(
		com.liferay.portal.model.User user, boolean lockout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateLockout(user, lockout);
	}

	public static com.liferay.portal.model.User updateLockoutByEmailAddress(
		long companyId, java.lang.String emailAddress, boolean lockout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateLockoutByEmailAddress(companyId, emailAddress,
			lockout);
	}

	public static com.liferay.portal.model.User updateLockoutById(long userId,
		boolean lockout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateLockoutById(userId, lockout);
	}

	public static com.liferay.portal.model.User updateLockoutByScreenName(
		long companyId, java.lang.String screenName, boolean lockout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateLockoutByScreenName(companyId, screenName, lockout);
	}

	public static com.liferay.portal.model.User updateModifiedDate(
		long userId, java.util.Date modifiedDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateModifiedDate(userId, modifiedDate);
	}

	public static void updateOpenId(long userId, java.lang.String openId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.updateOpenId(userId, openId);
	}

	public static void updateOrganizations(long userId,
		long[] newOrganizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.updateOrganizations(userId, newOrganizationIds);
	}

	public static com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updatePassword(userId, password1, password2,
			passwordReset);
	}

	public static com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, boolean silentUpdate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updatePassword(userId, password1, password2,
			passwordReset, silentUpdate);
	}

	public static com.liferay.portal.model.User updatePasswordManually(
		long userId, java.lang.String password, boolean passwordEncrypted,
		boolean passwordReset, java.util.Date passwordModifiedDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updatePasswordManually(userId, password,
			passwordEncrypted, passwordReset, passwordModifiedDate);
	}

	public static void updatePasswordReset(long userId, boolean passwordReset)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.updatePasswordReset(userId, passwordReset);
	}

	public static void updatePortrait(long userId, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.updatePortrait(userId, bytes);
	}

	public static void updateScreenName(long userId, java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.updateScreenName(userId, screenName);
	}

	public static com.liferay.portal.model.User updateUser(long userId,
		java.lang.String oldPassword, boolean passwordReset,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.lang.String greeting, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String facebookSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String mySpaceSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String ymSn,
		java.lang.String jobTitle, long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateUser(userId, oldPassword, passwordReset,
			screenName, emailAddress, languageId, timeZoneId, greeting,
			comments, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn,
			facebookSn, icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn,
			ymSn, jobTitle, organizationIds);
	}

	public static com.liferay.portal.model.User updateUser(long userId,
		java.lang.String oldPassword, java.lang.String newPassword1,
		java.lang.String newPassword2, boolean passwordReset,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.lang.String greeting, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String facebookSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String mySpaceSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String ymSn,
		java.lang.String jobTitle, long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.updateUser(userId, oldPassword, newPassword1,
			newPassword2, passwordReset, screenName, emailAddress, languageId,
			timeZoneId, greeting, comments, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			smsSn, aimSn, facebookSn, icqSn, jabberSn, msnSn, mySpaceSn,
			skypeSn, twitterSn, ymSn, jobTitle, organizationIds);
	}

	public static UserLocalService getService() {
		return _service;
	}

	public void setService(UserLocalService service) {
		_service = service;
	}

	private static UserLocalService _service;
}