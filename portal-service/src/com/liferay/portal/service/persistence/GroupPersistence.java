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

import com.liferay.portal.model.Group;

/**
 * <a href="GroupPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       GroupPersistenceImpl
 * @see       GroupUtil
 * @generated
 */
public interface GroupPersistence extends BasePersistence<Group> {
	public void cacheResult(com.liferay.portal.model.Group group);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Group> groups);

	public com.liferay.portal.model.Group create(long groupId);

	public com.liferay.portal.model.Group remove(long groupId)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group updateImpl(
		com.liferay.portal.model.Group group, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group findByPrimaryKey(long groupId)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group fetchByPrimaryKey(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Group> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Group> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Group> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group[] findByCompanyId_PrevAndNext(
		long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group findByLiveGroupId(long liveGroupId)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group fetchByLiveGroupId(long liveGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group fetchByLiveGroupId(long liveGroupId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group fetchByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group fetchByC_N(long companyId,
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group findByC_F(long companyId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group fetchByC_F(long companyId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group fetchByC_F(long companyId,
		java.lang.String friendlyURL, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Group> findByT_A(int type,
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Group> findByT_A(int type,
		boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Group> findByT_A(int type,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group findByT_A_First(int type,
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group findByT_A_Last(int type,
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group[] findByT_A_PrevAndNext(
		long groupId, int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group findByC_C_C(long companyId,
		long classNameId, long classPK)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group fetchByC_C_C(long companyId,
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group fetchByC_C_C(long companyId,
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group findByC_L_N(long companyId,
		long liveGroupId, java.lang.String name)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group fetchByC_L_N(long companyId,
		long liveGroupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group fetchByC_L_N(long companyId,
		long liveGroupId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group findByC_C_L_N(long companyId,
		long classNameId, long liveGroupId, java.lang.String name)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group fetchByC_C_L_N(long companyId,
		long classNameId, long liveGroupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group fetchByC_C_L_N(long companyId,
		long classNameId, long liveGroupId, java.lang.String name,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Group> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Group> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Group> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByLiveGroupId(long liveGroupId)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_F(long companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByT_A(int type, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_C_C(long companyId, long classNameId, long classPK)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_L_N(long companyId, long liveGroupId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, java.lang.String name)
		throws com.liferay.portal.NoSuchGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByLiveGroupId(long liveGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_F(long companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByT_A(int type, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_L_N(long companyId, long liveGroupId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int getOrganizationsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsOrganization(long pk, long organizationPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsOrganizations(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addOrganization(long pk, long organizationPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addOrganizations(long pk, long[] organizationPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void clearOrganizations(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeOrganization(long pk, long organizationPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeOrganizations(long pk, long[] organizationPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setOrganizations(long pk, long[] organizationPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int getPermissionsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsPermission(long pk, long permissionPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsPermissions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addPermission(long pk, long permissionPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addPermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addPermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addPermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void clearPermissions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removePermission(long pk, long permissionPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removePermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removePermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removePermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setPermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setPermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int getRolesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsRole(long pk, long rolePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsRoles(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addRole(long pk, long rolePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addRole(long pk, com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void clearRoles(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeRole(long pk, long rolePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeRole(long pk, com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int getUserGroupsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsUserGroup(long pk, long userGroupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsUserGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addUserGroup(long pk, long userGroupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addUserGroups(long pk, long[] userGroupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void clearUserGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeUserGroup(long pk, long userGroupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeUserGroups(long pk, long[] userGroupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setUserGroups(long pk, long[] userGroupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.User> getUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int getUsersSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void clearUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException;
}