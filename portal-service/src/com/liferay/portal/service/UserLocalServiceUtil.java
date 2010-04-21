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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

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
 * {@link UserLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserLocalService
 * @generated
 */
public class UserLocalServiceUtil {
	public static com.liferay.portal.model.User addUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addUser(user);
	}

	public static com.liferay.portal.model.User createUser(long userId) {
		return getService().createUser(userId);
	}

	public static void deleteUser(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUser(userId);
	}

	public static void deleteUser(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUser(user);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.User getUser(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUser(userId);
	}

	public static java.util.List<com.liferay.portal.model.User> getUsers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUsers(start, end);
	}

	public static int getUsersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUsersCount();
	}

	public static com.liferay.portal.model.User updateUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateUser(user);
	}

	public static com.liferay.portal.model.User updateUser(
		com.liferay.portal.model.User user, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateUser(user, merge);
	}

	public static void addDefaultGroups(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addDefaultGroups(userId);
	}

	public static void addDefaultRoles(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addDefaultRoles(userId);
	}

	public static void addDefaultUserGroups(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addDefaultUserGroups(userId);
	}

	public static void addGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addGroupUsers(groupId, userIds);
	}

	public static void addOrganizationUsers(long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addOrganizationUsers(organizationId, userIds);
	}

	public static void addPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public static void addRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addRoleUsers(roleId, userIds);
	}

	public static void addTeamUsers(long teamId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addTeamUsers(teamId, userIds);
	}

	public static com.liferay.portal.model.User addUser(long creatorUserId,
		long companyId, boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.String openId, java.util.Locale locale,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds, boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addUser(creatorUserId, companyId, autoPassword, password1,
			password2, autoScreenName, screenName, emailAddress, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);
	}

	public static void addUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addUserGroupUsers(userGroupId, userIds);
	}

	public static int authenticateByEmailAddress(long companyId,
		java.lang.String emailAddress, java.lang.String password,
		java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .authenticateByEmailAddress(companyId, emailAddress,
			password, headerMap, parameterMap);
	}

	public static int authenticateByScreenName(long companyId,
		java.lang.String screenName, java.lang.String password,
		java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .authenticateByScreenName(companyId, screenName, password,
			headerMap, parameterMap);
	}

	public static int authenticateByUserId(long companyId, long userId,
		java.lang.String password, java.util.Map<String, String[]> headerMap,
		java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .authenticateByUserId(companyId, userId, password,
			headerMap, parameterMap);
	}

	public static long authenticateForBasic(long companyId,
		java.lang.String authType, java.lang.String login,
		java.lang.String password)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .authenticateForBasic(companyId, authType, login, password);
	}

	public static boolean authenticateForJAAS(long userId,
		java.lang.String encPassword) {
		return getService().authenticateForJAAS(userId, encPassword);
	}

	public static void checkLockout(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkLockout(user);
	}

	public static void checkLoginFailure(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().checkLoginFailure(user);
	}

	public static void checkLoginFailureByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkLoginFailureByEmailAddress(companyId, emailAddress);
	}

	public static void checkLoginFailureById(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkLoginFailureById(userId);
	}

	public static void checkLoginFailureByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkLoginFailureByScreenName(companyId, screenName);
	}

	public static void checkPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkPasswordExpired(user);
	}

	public static com.liferay.portal.kernel.util.KeyValuePair decryptUserId(
		long companyId, java.lang.String name, java.lang.String password)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().decryptUserId(companyId, name, password);
	}

	public static void deletePortrait(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePortrait(userId);
	}

	public static void deleteRoleUser(long roleId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRoleUser(roleId, userId);
	}

	public static java.lang.String encryptUserId(java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().encryptUserId(name);
	}

	public static java.util.List<com.liferay.portal.model.User> getCompanyUsers(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyUsers(companyId, start, end);
	}

	public static int getCompanyUsersCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyUsersCount(companyId);
	}

	public static com.liferay.portal.model.User getDefaultUser(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultUser(companyId);
	}

	public static long getDefaultUserId(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultUserId(companyId);
	}

	public static long[] getGroupUserIds(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupUserIds(groupId);
	}

	public static java.util.List<com.liferay.portal.model.User> getGroupUsers(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupUsers(groupId);
	}

	public static int getGroupUsersCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupUsersCount(groupId);
	}

	public static int getGroupUsersCount(long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupUsersCount(groupId, active);
	}

	public static java.util.List<com.liferay.portal.model.User> getNoAnnouncementsDeliveries(
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNoAnnouncementsDeliveries(type);
	}

	public static java.util.List<com.liferay.portal.model.User> getNoContacts()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNoContacts();
	}

	public static java.util.List<com.liferay.portal.model.User> getNoGroups()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNoGroups();
	}

	public static long[] getOrganizationUserIds(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrganizationUserIds(organizationId);
	}

	public static java.util.List<com.liferay.portal.model.User> getOrganizationUsers(
		long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrganizationUsers(organizationId);
	}

	public static int getOrganizationUsersCount(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrganizationUsersCount(organizationId);
	}

	public static int getOrganizationUsersCount(long organizationId,
		boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrganizationUsersCount(organizationId, active);
	}

	public static long[] getRoleUserIds(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoleUserIds(roleId);
	}

	public static java.util.List<com.liferay.portal.model.User> getRoleUsers(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoleUsers(roleId);
	}

	public static java.util.List<com.liferay.portal.model.User> getRoleUsers(
		long roleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoleUsers(roleId, start, end);
	}

	public static int getRoleUsersCount(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoleUsersCount(roleId);
	}

	public static int getRoleUsersCount(long roleId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoleUsersCount(roleId, active);
	}

	public static java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialUsers(userId, type, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialUsers(userId, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId1, long userId2, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getSocialUsers(userId1, userId2, type, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.model.User> getSocialUsers(
		long userId1, long userId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialUsers(userId1, userId2, start, end, obc);
	}

	public static int getSocialUsersCount(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialUsersCount(userId);
	}

	public static int getSocialUsersCount(long userId, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialUsersCount(userId, type);
	}

	public static int getSocialUsersCount(long userId1, long userId2)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialUsersCount(userId1, userId2);
	}

	public static int getSocialUsersCount(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialUsersCount(userId1, userId2, type);
	}

	public static com.liferay.portal.model.User getUserByContactId(
		long contactId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserByContactId(contactId);
	}

	public static com.liferay.portal.model.User getUserByEmailAddress(
		long companyId, java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserByEmailAddress(companyId, emailAddress);
	}

	public static com.liferay.portal.model.User getUserById(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserById(userId);
	}

	public static com.liferay.portal.model.User getUserById(long companyId,
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserById(companyId, userId);
	}

	public static com.liferay.portal.model.User getUserByOpenId(
		java.lang.String openId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserByOpenId(openId);
	}

	public static com.liferay.portal.model.User getUserByPortraitId(
		long portraitId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserByPortraitId(portraitId);
	}

	public static com.liferay.portal.model.User getUserByScreenName(
		long companyId, java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserByScreenName(companyId, screenName);
	}

	public static com.liferay.portal.model.User getUserByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserByUuid(uuid);
	}

	public static java.util.List<com.liferay.portal.model.User> getUserGroupUsers(
		long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupUsers(userGroupId);
	}

	public static int getUserGroupUsersCount(long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupUsersCount(userGroupId);
	}

	public static int getUserGroupUsersCount(long userGroupId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupUsersCount(userGroupId, active);
	}

	public static long getUserIdByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserIdByEmailAddress(companyId, emailAddress);
	}

	public static long getUserIdByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserIdByScreenName(companyId, screenName);
	}

	public static boolean hasGroupUser(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasGroupUser(groupId, userId);
	}

	public static boolean hasOrganizationUser(long organizationId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasOrganizationUser(organizationId, userId);
	}

	public static boolean hasPasswordPolicyUser(long passwordPolicyId,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasPasswordPolicyUser(passwordPolicyId, userId);
	}

	public static boolean hasRoleUser(long roleId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasRoleUser(roleId, userId);
	}

	public static boolean hasRoleUser(long companyId, java.lang.String name,
		long userId, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().hasRoleUser(companyId, name, userId, inherited);
	}

	public static boolean hasTeamUser(long teamId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasTeamUser(teamId, userId);
	}

	public static boolean hasUserGroupUser(long userGroupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserGroupUser(userGroupId, userId);
	}

	public static boolean isPasswordExpired(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().isPasswordExpired(user);
	}

	public static boolean isPasswordExpiringSoon(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().isPasswordExpiringSoon(user);
	}

	public static java.util.List<com.liferay.portal.model.User> search(
		long companyId, java.lang.String keywords, java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, keywords, active, params, start, end, obc);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String keywords, java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, keywords, active, params, start, end, sort);
	}

	public static java.util.List<com.liferay.portal.model.User> search(
		long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, boolean andSearch,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, firstName, middleName, lastName,
			screenName, emailAddress, active, params, andSearch, start, end, obc);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, boolean andSearch,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, firstName, middleName, lastName,
			screenName, emailAddress, active, params, andSearch, start, end,
			sort);
	}

	public static int searchCount(long companyId, java.lang.String keywords,
		java.lang.Boolean active, java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().searchCount(companyId, keywords, active, params);
	}

	public static int searchCount(long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress,
		java.lang.Boolean active,
		java.util.LinkedHashMap<String, Object> params, boolean andSearch)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, firstName, middleName, lastName,
			screenName, emailAddress, active, params, andSearch);
	}

	public static void sendPassword(long companyId,
		java.lang.String emailAddress, java.lang.String remoteAddr,
		java.lang.String remoteHost, java.lang.String userAgent,
		java.lang.String fromName, java.lang.String fromAddress,
		java.lang.String subject, java.lang.String body)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.sendPassword(companyId, emailAddress, remoteAddr, remoteHost,
			userAgent, fromName, fromAddress, subject, body);
	}

	public static void setRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setRoleUsers(roleId, userIds);
	}

	public static void setUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setUserGroupUsers(userGroupId, userIds);
	}

	public static void unsetGroupUsers(long groupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetGroupUsers(groupId, userIds);
	}

	public static void unsetOrganizationUsers(long organizationId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetOrganizationUsers(organizationId, userIds);
	}

	public static void unsetPasswordPolicyUsers(long passwordPolicyId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().unsetPasswordPolicyUsers(passwordPolicyId, userIds);
	}

	public static void unsetRoleUsers(long roleId,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetRoleUsers(roleId, users);
	}

	public static void unsetRoleUsers(long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetRoleUsers(roleId, userIds);
	}

	public static void unsetTeamUsers(long teamId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetTeamUsers(teamId, userIds);
	}

	public static void unsetUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetUserGroupUsers(userGroupId, userIds);
	}

	public static com.liferay.portal.model.User updateActive(long userId,
		boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateActive(userId, active);
	}

	public static com.liferay.portal.model.User updateAgreedToTermsOfUse(
		long userId, boolean agreedToTermsOfUse)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAgreedToTermsOfUse(userId, agreedToTermsOfUse);
	}

	public static void updateAsset(long userId,
		com.liferay.portal.model.User user, long[] assetCategoryIds,
		java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateAsset(userId, user, assetCategoryIds, assetTagNames);
	}

	public static com.liferay.portal.model.User updateCreateDate(long userId,
		java.util.Date createDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCreateDate(userId, createDate);
	}

	public static com.liferay.portal.model.User updateEmailAddress(
		long userId, java.lang.String password, java.lang.String emailAddress1,
		java.lang.String emailAddress2)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEmailAddress(userId, password, emailAddress1,
			emailAddress2);
	}

	public static void updateGroups(long userId, long[] newGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateGroups(userId, newGroupIds);
	}

	public static com.liferay.portal.model.User updateLastLogin(long userId,
		java.lang.String loginIP)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLastLogin(userId, loginIP);
	}

	public static com.liferay.portal.model.User updateLockout(
		com.liferay.portal.model.User user, boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLockout(user, lockout);
	}

	public static com.liferay.portal.model.User updateLockoutByEmailAddress(
		long companyId, java.lang.String emailAddress, boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLockoutByEmailAddress(companyId, emailAddress, lockout);
	}

	public static com.liferay.portal.model.User updateLockoutById(long userId,
		boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLockoutById(userId, lockout);
	}

	public static com.liferay.portal.model.User updateLockoutByScreenName(
		long companyId, java.lang.String screenName, boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLockoutByScreenName(companyId, screenName, lockout);
	}

	public static com.liferay.portal.model.User updateModifiedDate(
		long userId, java.util.Date modifiedDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateModifiedDate(userId, modifiedDate);
	}

	public static void updateOpenId(long userId, java.lang.String openId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateOpenId(userId, openId);
	}

	public static void updateOrganizations(long userId,
		long[] newOrganizationIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateOrganizations(userId, newOrganizationIds);
	}

	public static com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePassword(userId, password1, password2, passwordReset);
	}

	public static com.liferay.portal.model.User updatePassword(long userId,
		java.lang.String password1, java.lang.String password2,
		boolean passwordReset, boolean silentUpdate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePassword(userId, password1, password2, passwordReset,
			silentUpdate);
	}

	public static com.liferay.portal.model.User updatePasswordManually(
		long userId, java.lang.String password, boolean passwordEncrypted,
		boolean passwordReset, java.util.Date passwordModifiedDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePasswordManually(userId, password, passwordEncrypted,
			passwordReset, passwordModifiedDate);
	}

	public static void updatePasswordReset(long userId, boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updatePasswordReset(userId, passwordReset);
	}

	public static void updatePortrait(long userId, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updatePortrait(userId, bytes);
	}

	public static void updateReminderQuery(long userId,
		java.lang.String question, java.lang.String answer)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateReminderQuery(userId, question, answer);
	}

	public static void updateScreenName(long userId, java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateScreenName(userId, screenName);
	}

	public static com.liferay.portal.model.User updateUser(long userId,
		java.lang.String oldPassword, java.lang.String newPassword1,
		java.lang.String newPassword2, boolean passwordReset,
		java.lang.String reminderQueryQuestion,
		java.lang.String reminderQueryAnswer, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String openId,
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
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds,
		java.util.List<com.liferay.portal.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateUser(userId, oldPassword, newPassword1, newPassword2,
			passwordReset, reminderQueryQuestion, reminderQueryAnswer,
			screenName, emailAddress, openId, languageId, timeZoneId, greeting,
			comments, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn,
			facebookSn, icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn,
			ymSn, jobTitle, groupIds, organizationIds, roleIds, userGroupRoles,
			userGroupIds, serviceContext);
	}

	public static UserLocalService getService() {
		if (_service == null) {
			_service = (UserLocalService)PortalBeanLocatorUtil.locate(UserLocalService.class.getName());
		}

		return _service;
	}

	public void setService(UserLocalService service) {
		_service = service;
	}

	private static UserLocalService _service;
}