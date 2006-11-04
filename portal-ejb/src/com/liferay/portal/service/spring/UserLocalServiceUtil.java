/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.spring;

/**
 * <a href="UserLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserLocalServiceUtil {
	public static void addGroupUsers(java.lang.String groupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.addGroupUsers(groupId, userIds);
	}

	public static void addRoleUsers(java.lang.String roleId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.addRoleUsers(roleId, userIds);
	}

	public static void addUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.addUserGroupUsers(userGroupId, userIds);
	}

	public static com.liferay.portal.model.User addUser(
		java.lang.String creatorUserId, java.lang.String companyId,
		boolean autoUserId, java.lang.String userId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, java.lang.String emailAddress,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String nickName, java.lang.String prefixId,
		java.lang.String suffixId, boolean male, int birthdayMonth,
		int birthdayDay, int birthdayYear, java.lang.String jobTitle,
		java.lang.String organizationId, java.lang.String locationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.addUser(creatorUserId, companyId, autoUserId,
			userId, autoPassword, password1, password2, passwordReset,
			emailAddress, locale, firstName, middleName, lastName, nickName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			jobTitle, organizationId, locationId);
	}

	public static com.liferay.portal.model.User addUser(
		java.lang.String creatorUserId, java.lang.String companyId,
		boolean autoUserId, java.lang.String userId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, java.lang.String emailAddress,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String nickName, java.lang.String prefixId,
		java.lang.String suffixId, boolean male, int birthdayMonth,
		int birthdayDay, int birthdayYear, java.lang.String jobTitle,
		java.lang.String organizationId, java.lang.String locationId,
		boolean sendEmail)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.addUser(creatorUserId, companyId, autoUserId,
			userId, autoPassword, password1, password2, passwordReset,
			emailAddress, locale, firstName, middleName, lastName, nickName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			jobTitle, organizationId, locationId, sendEmail);
	}

	public static int authenticateByEmailAddress(java.lang.String companyId,
		java.lang.String emailAddress, java.lang.String password,
		java.util.Map headerMap, java.util.Map parameterMap)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.authenticateByEmailAddress(companyId,
			emailAddress, password, headerMap, parameterMap);
	}

	public static int authenticateByUserId(java.lang.String companyId,
		java.lang.String userId, java.lang.String password,
		java.util.Map headerMap, java.util.Map parameterMap)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.authenticateByUserId(companyId, userId,
			password, headerMap, parameterMap);
	}

	public static boolean authenticateForJAAS(java.lang.String userId,
		java.lang.String encPwd)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.authenticateForJAAS(userId, encPwd);
	}

	public static com.liferay.util.KeyValuePair decryptUserId(
		java.lang.String companyId, java.lang.String userId,
		java.lang.String password)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.decryptUserId(companyId, userId, password);
	}

	public static void deleteRoleUser(java.lang.String roleId,
		java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.deleteRoleUser(roleId, userId);
	}

	public static void deleteUser(java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.deleteUser(userId);
	}

	public static java.lang.String encryptUserId(java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.encryptUserId(userId);
	}

	public static com.liferay.portal.model.User getDefaultUser(
		java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getDefaultUser(companyId);
	}

	public static java.util.List getGroupUsers(java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getGroupUsers(groupId);
	}

	public static java.util.List getPermissionUsers(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String name, java.lang.String primKey,
		java.lang.String actionId,
		com.liferay.portlet.enterpriseadmin.search.UserSearchTerms searchTerms,
		int begin, int end)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getPermissionUsers(companyId, groupId, name,
			primKey, actionId, searchTerms, begin, end);
	}

	public static int getPermissionUsersCount(java.lang.String companyId,
		java.lang.String groupId, java.lang.String name,
		java.lang.String primKey, java.lang.String actionId,
		com.liferay.portlet.enterpriseadmin.search.UserSearchTerms searchTerms)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getPermissionUsersCount(companyId, groupId,
			name, primKey, actionId, searchTerms);
	}

	public static java.util.List getRoleUsers(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getRoleUsers(roleId);
	}

	public static com.liferay.portal.model.User getUserByEmailAddress(
		java.lang.String companyId, java.lang.String emailAddress)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserByEmailAddress(companyId, emailAddress);
	}

	public static com.liferay.portal.model.User getUserById(
		java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserById(userId);
	}

	public static com.liferay.portal.model.User getUserById(
		java.lang.String companyId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserById(companyId, userId);
	}

	public static java.lang.String getUserId(java.lang.String companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.getUserId(companyId, emailAddress);
	}

	public static boolean hasGroupUser(java.lang.String groupId,
		java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.hasGroupUser(groupId, userId);
	}

	public static boolean hasRoleUser(java.lang.String roleId,
		java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.hasRoleUser(roleId, userId);
	}

	public static boolean hasUserGroupUser(java.lang.String userGroupId,
		java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.hasUserGroupUser(userGroupId, userId);
	}

	public static java.util.List search(java.lang.String companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String emailAddress,
		boolean active, java.util.Map params, boolean andSearch, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.search(companyId, firstName, middleName,
			lastName, emailAddress, active, params, andSearch, begin, end, obc);
	}

	public static int searchCount(java.lang.String companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String emailAddress,
		boolean active, java.util.Map params, boolean andSearch)
		throws com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.searchCount(companyId, firstName, middleName,
			lastName, emailAddress, active, params, andSearch);
	}

	public static void sendPassword(java.lang.String companyId,
		java.lang.String emailAddress, java.lang.String remoteAddr,
		java.lang.String remoteHost, java.lang.String userAgent)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.sendPassword(companyId, emailAddress, remoteAddr,
			remoteHost, userAgent);
	}

	public static void setGroupUsers(java.lang.String groupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.setGroupUsers(groupId, userIds);
	}

	public static void setRoleUsers(java.lang.String roleId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.setRoleUsers(roleId, userIds);
	}

	public static void setUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.setUserGroupUsers(userGroupId, userIds);
	}

	public static void unsetGroupUsers(java.lang.String groupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.unsetGroupUsers(groupId, userIds);
	}

	public static void unsetRoleUsers(java.lang.String roleId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.unsetRoleUsers(roleId, userIds);
	}

	public static void unsetUserGroupUsers(java.lang.String userGroupId,
		java.lang.String[] userIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.unsetUserGroupUsers(userGroupId, userIds);
	}

	public static com.liferay.portal.model.User updateActive(
		java.lang.String userId, boolean active)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateActive(userId, active);
	}

	public static com.liferay.portal.model.User updateAgreedToTermsOfUse(
		java.lang.String userId, boolean agreedToTermsOfUse)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateAgreedToTermsOfUse(userId,
			agreedToTermsOfUse);
	}

	public static com.liferay.portal.model.User updateLastLogin(
		java.lang.String userId, java.lang.String loginIP)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateLastLogin(userId, loginIP);
	}

	public static com.liferay.portal.model.User updatePassword(
		java.lang.String userId, java.lang.String password1,
		java.lang.String password2, boolean passwordReset)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updatePassword(userId, password1, password2,
			passwordReset);
	}

	public static void updatePortrait(java.lang.String userId, byte[] bytes)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();
		userLocalService.updatePortrait(userId, bytes);
	}

	public static com.liferay.portal.model.User updateUser(
		java.lang.String userId, java.lang.String password,
		java.lang.String emailAddress, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String resolution, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String nickName,
		java.lang.String prefixId, java.lang.String suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String smsSn, java.lang.String aimSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String skypeSn, java.lang.String ymSn,
		java.lang.String jobTitle, java.lang.String organizationId,
		java.lang.String locationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserLocalService userLocalService = UserLocalServiceFactory.getService();

		return userLocalService.updateUser(userId, password, emailAddress,
			languageId, timeZoneId, greeting, resolution, comments, firstName,
			middleName, lastName, nickName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn, icqSn,
			jabberSn, msnSn, skypeSn, ymSn, jobTitle, organizationId, locationId);
	}
}