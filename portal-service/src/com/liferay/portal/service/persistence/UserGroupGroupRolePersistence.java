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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.UserGroupGroupRole;

/**
 * The persistence interface for the user group group role service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupGroupRolePersistenceImpl
 * @see UserGroupGroupRoleUtil
 * @generated
 */
public interface UserGroupGroupRolePersistence extends BasePersistence<UserGroupGroupRole> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserGroupGroupRoleUtil} to access the user group group role persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the user group group role in the entity cache if it is enabled.
	*
	* @param userGroupGroupRole the user group group role to cache
	*/
	public void cacheResult(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole);

	/**
	* Caches the user group group roles in the entity cache if it is enabled.
	*
	* @param userGroupGroupRoles the user group group roles to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.UserGroupGroupRole> userGroupGroupRoles);

	/**
	* Creates a new user group group role with the primary key. Does not add the user group group role to the database.
	*
	* @param userGroupGroupRolePK the primary key for the new user group group role
	* @return the new user group group role
	*/
	public com.liferay.portal.model.UserGroupGroupRole create(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK);

	/**
	* Removes the user group group role with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userGroupGroupRolePK the primary key of the user group group role to remove
	* @return the user group group role that was removed
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole remove(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole updateImpl(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user group group role with the primary key or throws a {@link com.liferay.portal.NoSuchUserGroupGroupRoleException} if it could not be found.
	*
	* @param userGroupGroupRolePK the primary key of the user group group role to find
	* @return the user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole findByPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user group group role with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userGroupGroupRolePK the primary key of the user group group role to find
	* @return the user group group role, or <code>null</code> if a user group group role with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole fetchByPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the user group group roles where userGroupId = &#63;.
	*
	* @param userGroupId the user group id to search with
	* @return the matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByUserGroupId(
		long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the user group group roles where userGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupId the user group id to search with
	* @param start the lower bound of the range of user group group roles to return
	* @param end the upper bound of the range of user group group roles to return (not inclusive)
	* @return the range of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByUserGroupId(
		long userGroupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the user group group roles where userGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupId the user group id to search with
	* @param start the lower bound of the range of user group group roles to return
	* @param end the upper bound of the range of user group group roles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByUserGroupId(
		long userGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first user group group role in the ordered set where userGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupId the user group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole findByUserGroupId_First(
		long userGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last user group group role in the ordered set where userGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupId the user group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole findByUserGroupId_Last(
		long userGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user group group roles before and after the current user group group role in the ordered set where userGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupGroupRolePK the primary key of the current user group group role
	* @param userGroupId the user group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole[] findByUserGroupId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long userGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the user group group roles where groupId = &#63;.
	*
	* @param groupId the group id to search with
	* @return the matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the user group group roles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param start the lower bound of the range of user group group roles to return
	* @param end the upper bound of the range of user group group roles to return (not inclusive)
	* @return the range of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the user group group roles where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param start the lower bound of the range of user group group roles to return
	* @param end the upper bound of the range of user group group roles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first user group group role in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last user group group role in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user group group roles before and after the current user group group role in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupGroupRolePK the primary key of the current user group group role
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole[] findByGroupId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the user group group roles where roleId = &#63;.
	*
	* @param roleId the role id to search with
	* @return the matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByRoleId(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the user group group roles where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role id to search with
	* @param start the lower bound of the range of user group group roles to return
	* @param end the upper bound of the range of user group group roles to return (not inclusive)
	* @return the range of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByRoleId(
		long roleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the user group group roles where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role id to search with
	* @param start the lower bound of the range of user group group roles to return
	* @param end the upper bound of the range of user group group roles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first user group group role in the ordered set where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole findByRoleId_First(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last user group group role in the ordered set where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole findByRoleId_Last(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user group group roles before and after the current user group group role in the ordered set where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupGroupRolePK the primary key of the current user group group role
	* @param roleId the role id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole[] findByRoleId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	*
	* @param userGroupId the user group id to search with
	* @param groupId the group id to search with
	* @return the matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByU_G(
		long userGroupId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupId the user group id to search with
	* @param groupId the group id to search with
	* @param start the lower bound of the range of user group group roles to return
	* @param end the upper bound of the range of user group group roles to return (not inclusive)
	* @return the range of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByU_G(
		long userGroupId, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupId the user group id to search with
	* @param groupId the group id to search with
	* @param start the lower bound of the range of user group group roles to return
	* @param end the upper bound of the range of user group group roles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByU_G(
		long userGroupId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupId the user group id to search with
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole findByU_G_First(
		long userGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupId the user group id to search with
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole findByU_G_Last(
		long userGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user group group roles before and after the current user group group role in the ordered set where userGroupId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupGroupRolePK the primary key of the current user group group role
	* @param userGroupId the user group id to search with
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole[] findByU_G_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long userGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the user group group roles where groupId = &#63; and roleId = &#63;.
	*
	* @param groupId the group id to search with
	* @param roleId the role id to search with
	* @return the matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByG_R(
		long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the user group group roles where groupId = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param roleId the role id to search with
	* @param start the lower bound of the range of user group group roles to return
	* @param end the upper bound of the range of user group group roles to return (not inclusive)
	* @return the range of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByG_R(
		long groupId, long roleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the user group group roles where groupId = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param roleId the role id to search with
	* @param start the lower bound of the range of user group group roles to return
	* @param end the upper bound of the range of user group group roles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByG_R(
		long groupId, long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param roleId the role id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole findByG_R_First(
		long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param roleId the role id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a matching user group group role could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole findByG_R_Last(
		long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the user group group roles before and after the current user group group role in the ordered set where groupId = &#63; and roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userGroupGroupRolePK the primary key of the current user group group role
	* @param groupId the group id to search with
	* @param roleId the role id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next user group group role
	* @throws com.liferay.portal.NoSuchUserGroupGroupRoleException if a user group group role with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserGroupGroupRole[] findByG_R_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the user group group roles.
	*
	* @return the user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the user group group roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user group group roles to return
	* @param end the upper bound of the range of user group group roles to return (not inclusive)
	* @return the range of user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the user group group roles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user group group roles to return
	* @param end the upper bound of the range of user group group roles to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user group group roles where userGroupId = &#63; from the database.
	*
	* @param userGroupId the user group id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserGroupId(long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user group group roles where groupId = &#63; from the database.
	*
	* @param groupId the group id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user group group roles where roleId = &#63; from the database.
	*
	* @param roleId the role id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user group group roles where userGroupId = &#63; and groupId = &#63; from the database.
	*
	* @param userGroupId the user group id to search with
	* @param groupId the group id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByU_G(long userGroupId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user group group roles where groupId = &#63; and roleId = &#63; from the database.
	*
	* @param groupId the group id to search with
	* @param roleId the role id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_R(long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user group group roles from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the user group group roles where userGroupId = &#63;.
	*
	* @param userGroupId the user group id to search with
	* @return the number of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserGroupId(long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the user group group roles where groupId = &#63;.
	*
	* @param groupId the group id to search with
	* @return the number of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the user group group roles where roleId = &#63;.
	*
	* @param roleId the role id to search with
	* @return the number of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public int countByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the user group group roles where userGroupId = &#63; and groupId = &#63;.
	*
	* @param userGroupId the user group id to search with
	* @param groupId the group id to search with
	* @return the number of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public int countByU_G(long userGroupId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the user group group roles where groupId = &#63; and roleId = &#63;.
	*
	* @param groupId the group id to search with
	* @param roleId the role id to search with
	* @return the number of matching user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_R(long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the user group group roles.
	*
	* @return the number of user group group roles
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}