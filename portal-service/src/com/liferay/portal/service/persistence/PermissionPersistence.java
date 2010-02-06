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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.Permission;

/**
 * <a href="PermissionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PermissionPersistenceImpl
 * @see       PermissionUtil
 * @generated
 */
public interface PermissionPersistence extends BasePersistence<Permission> {
	public void cacheResult(com.liferay.portal.model.Permission permission);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Permission> permissions);

	public com.liferay.portal.model.Permission create(long permissionId);

	public com.liferay.portal.model.Permission remove(long permissionId)
		throws com.liferay.portal.NoSuchPermissionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Permission updateImpl(
		com.liferay.portal.model.Permission permission, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Permission findByPrimaryKey(
		long permissionId)
		throws com.liferay.portal.NoSuchPermissionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Permission fetchByPrimaryKey(
		long permissionId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByResourceId(
		long resourceId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByResourceId(
		long resourceId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByResourceId(
		long resourceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Permission findByResourceId_First(
		long resourceId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPermissionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Permission findByResourceId_Last(
		long resourceId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPermissionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Permission[] findByResourceId_PrevAndNext(
		long permissionId, long resourceId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPermissionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Permission findByA_R(
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.NoSuchPermissionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Permission fetchByA_R(
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Permission fetchByA_R(
		java.lang.String actionId, long resourceId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByResourceId(long resourceId)
		throws com.liferay.portal.SystemException;

	public void removeByA_R(java.lang.String actionId, long resourceId)
		throws com.liferay.portal.NoSuchPermissionException,
			com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByResourceId(long resourceId)
		throws com.liferay.portal.SystemException;

	public int countByA_R(java.lang.String actionId, long resourceId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int getGroupsSize(long pk) throws com.liferay.portal.SystemException;

	public boolean containsGroup(long pk, long groupPK)
		throws com.liferay.portal.SystemException;

	public boolean containsGroups(long pk)
		throws com.liferay.portal.SystemException;

	public void addGroup(long pk, long groupPK)
		throws com.liferay.portal.SystemException;

	public void addGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.SystemException;

	public void addGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.SystemException;

	public void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException;

	public void clearGroups(long pk) throws com.liferay.portal.SystemException;

	public void removeGroup(long pk, long groupPK)
		throws com.liferay.portal.SystemException;

	public void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.SystemException;

	public void removeGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.SystemException;

	public void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException;

	public void setGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.SystemException;

	public void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int getRolesSize(long pk) throws com.liferay.portal.SystemException;

	public boolean containsRole(long pk, long rolePK)
		throws com.liferay.portal.SystemException;

	public boolean containsRoles(long pk)
		throws com.liferay.portal.SystemException;

	public void addRole(long pk, long rolePK)
		throws com.liferay.portal.SystemException;

	public void addRole(long pk, com.liferay.portal.model.Role role)
		throws com.liferay.portal.SystemException;

	public void addRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.SystemException;

	public void addRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.SystemException;

	public void clearRoles(long pk) throws com.liferay.portal.SystemException;

	public void removeRole(long pk, long rolePK)
		throws com.liferay.portal.SystemException;

	public void removeRole(long pk, com.liferay.portal.model.Role role)
		throws com.liferay.portal.SystemException;

	public void removeRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.SystemException;

	public void removeRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.SystemException;

	public void setRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.SystemException;

	public void setRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getUsers(long pk)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int getUsersSize(long pk) throws com.liferay.portal.SystemException;

	public boolean containsUser(long pk, long userPK)
		throws com.liferay.portal.SystemException;

	public boolean containsUsers(long pk)
		throws com.liferay.portal.SystemException;

	public void addUser(long pk, long userPK)
		throws com.liferay.portal.SystemException;

	public void addUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException;

	public void addUsers(long pk, long[] userPKs)
		throws com.liferay.portal.SystemException;

	public void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.SystemException;

	public void clearUsers(long pk) throws com.liferay.portal.SystemException;

	public void removeUser(long pk, long userPK)
		throws com.liferay.portal.SystemException;

	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException;

	public void removeUsers(long pk, long[] userPKs)
		throws com.liferay.portal.SystemException;

	public void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.SystemException;

	public void setUsers(long pk, long[] userPKs)
		throws com.liferay.portal.SystemException;

	public void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.SystemException;
}