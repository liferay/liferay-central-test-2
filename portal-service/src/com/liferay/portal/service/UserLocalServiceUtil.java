/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the user local service. This utility wraps {@link com.liferay.portal.service.impl.UserLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserLocalService
 * @see com.liferay.portal.service.base.UserLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.UserLocalServiceImpl
 * @generated
 */
public class UserLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.UserLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the user to the database. Also notifies the appropriate model listeners.
	*
	* @param user the user to add
	* @return the user that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.User addUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addUser(user);
	}

	/**
	* Creates a new user with the primary key. Does not add the user to the database.
	*
	* @param userId the primary key for the new user
	* @return the new user
	*/
	public static com.liferay.portal.model.User createUser(long userId) {
		return getService().createUser(userId);
	}

	/**
	* Deletes the user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userId the primary key of the user to delete
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteUser(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUser(userId);
	}

	/**
	* Deletes the user from the database. Also notifies the appropriate model listeners.
	*
	* @param user the user to delete
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteUser(com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUser(user);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the user with the primary key.
	*
	* @param userId the primary key of the user to get
	* @return the user
	* @throws PortalException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.User getUser(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUser(userId);
	}

	/**
	* Gets a range of all the users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of users to return
	* @param end the upper bound of the range of users to return (not inclusive)
	* @return the range of users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.User> getUsers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUsers(start, end);
	}

	/**
	* Gets the number of users.
	*
	* @return the number of users
	* @throws SystemException if a system exception occurred
	*/
	public static int getUsersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUsersCount();
	}

	/**
	* Updates the user in the database. Also notifies the appropriate model listeners.
	*
	* @param user the user to update
	* @return the user that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.User updateUser(
		com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateUser(user);
	}

	/**
	* Updates the user in the database. Also notifies the appropriate model listeners.
	*
	* @param user the user to update
	* @param merge whether to merge the user with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the user that was updated
	* @throws SystemException if a system exception occurred
	*/
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

	public static com.liferay.portal.model.User addUserBypassWorkflow(
		long creatorUserId, long companyId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean autoScreenName, java.lang.String screenName,
		java.lang.String emailAddress, long facebookId,
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
				   .addUserBypassWorkflow(creatorUserId, companyId,
			autoPassword, password1, password2, autoScreenName, screenName,
			emailAddress, facebookId, openId, locale, firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, jobTitle, groupIds, organizationIds, roleIds,
			userGroupIds, sendEmail, serviceContext);
	}

	/**
	* Adds a user to the database. Also notifies the appropriate model
	* listeners.
	*
	* @param creatorUserId the user ID of the creator
	* @param companyId the company ID the user
	* @param autoPassword whether a password should be automatically generated
	for the user
	* @param password1 the user's password
	* @param password2 the user's password confirmation
	* @param autoScreenName whether a screen name should be automatically
	generated for the user
	* @param screenName the user's screen name
	* @param emailAddress the user's email address
	* @param facebookId the user's facebook ID
	* @param openId the user's OpenID
	* @param locale the user's locale
	* @param firstName the user's first name
	* @param middleName the user's middle name
	* @param lastName the user's last name
	* @param prefixId the user's name prefix ID
	* @param suffixId the user's name suffix ID
	* @param male whether the user is male
	* @param birthdayMonth the user's birthday month (0-based, meaning 0 for
	January)
	* @param birthdayDay the user's birthday day
	* @param birthdayYear the user's birthday year
	* @param jobTitle the user's job title
	* @param groupIds the IDs of the groups this user belongs to
	* @param organizationIds the IDs of the organizations this user belongs to
	* @param roleIds the IDs of the roles this user possesses
	* @param userGroupIds the IDs of the user groups this user belongs to
	* @param sendEmail whether to send the user an email notification about
	their new account
	* @param serviceContext the service context for the user
	* @return the new user
	* @throws PortalException if the user's information is invalid
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.User addUser(long creatorUserId,
		long companyId, boolean autoPassword, java.lang.String password1,
		java.lang.String password2, boolean autoScreenName,
		java.lang.String screenName, java.lang.String emailAddress,
		long facebookId, java.lang.String openId, java.util.Locale locale,
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
			password2, autoScreenName, screenName, emailAddress, facebookId,
			openId, locale, firstName, middleName, lastName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
			serviceContext);
	}

	public static void addUserGroupUsers(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addUserGroupUsers(userGroupId, userIds);
	}

	public static int authenticateByEmailAddress(long companyId,
		java.lang.String emailAddress, java.lang.String password,
		java.util.Map<java.lang.String, java.lang.String[]> headerMap,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Map<java.lang.String, java.lang.Object> resultsMap)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .authenticateByEmailAddress(companyId, emailAddress,
			password, headerMap, parameterMap, resultsMap);
	}

	public static int authenticateByScreenName(long companyId,
		java.lang.String screenName, java.lang.String password,
		java.util.Map<java.lang.String, java.lang.String[]> headerMap,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Map<java.lang.String, java.lang.Object> resultsMap)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .authenticateByScreenName(companyId, screenName, password,
			headerMap, parameterMap, resultsMap);
	}

	public static int authenticateByUserId(long companyId, long userId,
		java.lang.String password,
		java.util.Map<java.lang.String, java.lang.String[]> headerMap,
		java.util.Map<java.lang.String, java.lang.String[]> parameterMap,
		java.util.Map<java.lang.String, java.lang.Object> resultsMap)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .authenticateByUserId(companyId, userId, password,
			headerMap, parameterMap, resultsMap);
	}

	public static long authenticateForBasic(long companyId,
		java.lang.String authType, java.lang.String login,
		java.lang.String password)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .authenticateForBasic(companyId, authType, login, password);
	}

	public static long authenticateForDigest(long companyId,
		java.lang.String username, java.lang.String realm,
		java.lang.String nonce, java.lang.String method, java.lang.String uri,
		java.lang.String response)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .authenticateForDigest(companyId, username, realm, nonce,
			method, uri, response);
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

	public static void clearOrganizationUsers(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clearOrganizationUsers(organizationId);
	}

	public static void clearUserGroupUsers(long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clearUserGroupUsers(userGroupId);
	}

	public static void completeUserRegistration(
		com.liferay.portal.model.User user,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().completeUserRegistration(user, serviceContext);
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

	public static int getGroupUsersCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupUsersCount(groupId, status);
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

	public static int getOrganizationUsersCount(long organizationId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrganizationUsersCount(organizationId, status);
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

	public static int getRoleUsersCount(long roleId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoleUsersCount(roleId, status);
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

	public static com.liferay.portal.model.User getUserByFacebookId(
		long companyId, long facebookId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserByFacebookId(companyId, facebookId);
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
		long companyId, java.lang.String openId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserByOpenId(companyId, openId);
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

	public static int getUserGroupUsersCount(long userGroupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupUsersCount(userGroupId, status);
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

	/**
	* Returns <code>true</code> if the user has the role.
	*
	* @return <code>true</code> if the user has the role
	*/
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
		long companyId, java.lang.String keywords, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, keywords, status, params, start, end, obc);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String keywords, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, keywords, status, params, start, end, sort);
	}

	public static java.util.List<com.liferay.portal.model.User> search(
		long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, firstName, middleName, lastName,
			screenName, emailAddress, status, params, andSearch, start, end, obc);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, java.lang.String screenName,
		java.lang.String emailAddress, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, firstName, middleName, lastName,
			screenName, emailAddress, status, params, andSearch, start, end,
			sort);
	}

	public static int searchCount(long companyId, java.lang.String keywords,
		int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().searchCount(companyId, keywords, status, params);
	}

	public static int searchCount(long companyId, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName,
		java.lang.String screenName, java.lang.String emailAddress, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, firstName, middleName, lastName,
			screenName, emailAddress, status, params, andSearch);
	}

	public static void sendPassword(long companyId,
		java.lang.String emailAddress, java.lang.String remoteAddr,
		java.lang.String remoteHost, java.lang.String userAgent,
		java.lang.String fromName, java.lang.String fromAddress,
		java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.sendPassword(companyId, emailAddress, remoteAddr, remoteHost,
			userAgent, fromName, fromAddress, subject, body, serviceContext);
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

	public static com.liferay.portal.model.User updateFacebookId(long userId,
		long facebookId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateFacebookId(userId, facebookId);
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

	public static com.liferay.portal.model.User updateOpenId(long userId,
		java.lang.String openId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateOpenId(userId, openId);
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

	public static com.liferay.portal.model.User updatePasswordReset(
		long userId, boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePasswordReset(userId, passwordReset);
	}

	public static com.liferay.portal.model.User updatePortrait(long userId,
		byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePortrait(userId, bytes);
	}

	public static com.liferay.portal.model.User updateReminderQuery(
		long userId, java.lang.String question, java.lang.String answer)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateReminderQuery(userId, question, answer);
	}

	public static com.liferay.portal.model.User updateScreenName(long userId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateScreenName(userId, screenName);
	}

	public static com.liferay.portal.model.User updateStatus(long userId,
		int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateStatus(userId, status);
	}

	public static com.liferay.portal.model.User updateUser(long userId,
		java.lang.String oldPassword, java.lang.String newPassword1,
		java.lang.String newPassword2, boolean passwordReset,
		java.lang.String reminderQueryQuestion,
		java.lang.String reminderQueryAnswer, java.lang.String screenName,
		java.lang.String emailAddress, long facebookId,
		java.lang.String openId, java.lang.String languageId,
		java.lang.String timeZoneId, java.lang.String greeting,
		java.lang.String comments, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String smsSn, java.lang.String aimSn,
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
			screenName, emailAddress, facebookId, openId, languageId,
			timeZoneId, greeting, comments, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			smsSn, aimSn, facebookSn, icqSn, jabberSn, msnSn, mySpaceSn,
			skypeSn, twitterSn, ymSn, jobTitle, groupIds, organizationIds,
			roleIds, userGroupRoles, userGroupIds, serviceContext);
	}

	public static UserLocalService getService() {
		if (_service == null) {
			_service = (UserLocalService)PortalBeanLocatorUtil.locate(UserLocalService.class.getName());

			ReferenceRegistry.registerReference(UserLocalServiceUtil.class,
				"_service");
			MethodCache.remove(UserLocalService.class);
		}

		return _service;
	}

	public void setService(UserLocalService service) {
		MethodCache.remove(UserLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(UserLocalServiceUtil.class,
			"_service");
		MethodCache.remove(UserLocalService.class);
	}

	private static UserLocalService _service;
}