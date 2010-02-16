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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Role;

import java.util.List;

/**
 * <a href="RoleUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RolePersistence
 * @see       RolePersistenceImpl
 * @generated
 */
public class RoleUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static Role remove(Role role) throws SystemException {
		return getPersistence().remove(role);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Role update(Role role, boolean merge)
		throws SystemException {
		return getPersistence().update(role, merge);
	}

	public static void cacheResult(com.liferay.portal.model.Role role) {
		getPersistence().cacheResult(role);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Role> roles) {
		getPersistence().cacheResult(roles);
	}

	public static com.liferay.portal.model.Role create(long roleId) {
		return getPersistence().create(roleId);
	}

	public static com.liferay.portal.model.Role remove(long roleId)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(roleId);
	}

	public static com.liferay.portal.model.Role updateImpl(
		com.liferay.portal.model.Role role, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(role, merge);
	}

	public static com.liferay.portal.model.Role findByPrimaryKey(long roleId)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(roleId);
	}

	public static com.liferay.portal.model.Role fetchByPrimaryKey(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(roleId);
	}

	public static java.util.List<com.liferay.portal.model.Role> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Role> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Role> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.Role findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.Role findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Role[] findByCompanyId_PrevAndNext(
		long roleId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(roleId, companyId, obc);
	}

	public static java.util.List<com.liferay.portal.model.Role> findBySubtype(
		java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySubtype(subtype);
	}

	public static java.util.List<com.liferay.portal.model.Role> findBySubtype(
		java.lang.String subtype, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySubtype(subtype, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Role> findBySubtype(
		java.lang.String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySubtype(subtype, start, end, obc);
	}

	public static com.liferay.portal.model.Role findBySubtype_First(
		java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySubtype_First(subtype, obc);
	}

	public static com.liferay.portal.model.Role findBySubtype_Last(
		java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySubtype_Last(subtype, obc);
	}

	public static com.liferay.portal.model.Role[] findBySubtype_PrevAndNext(
		long roleId, java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySubtype_PrevAndNext(roleId, subtype, obc);
	}

	public static com.liferay.portal.model.Role findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N(companyId, name);
	}

	public static com.liferay.portal.model.Role fetchByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_N(companyId, name);
	}

	public static com.liferay.portal.model.Role fetchByC_N(long companyId,
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_N(companyId, name, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.Role> findByT_S(
		int type, java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_S(type, subtype);
	}

	public static java.util.List<com.liferay.portal.model.Role> findByT_S(
		int type, java.lang.String subtype, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_S(type, subtype, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Role> findByT_S(
		int type, java.lang.String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_S(type, subtype, start, end, obc);
	}

	public static com.liferay.portal.model.Role findByT_S_First(int type,
		java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_S_First(type, subtype, obc);
	}

	public static com.liferay.portal.model.Role findByT_S_Last(int type,
		java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_S_Last(type, subtype, obc);
	}

	public static com.liferay.portal.model.Role[] findByT_S_PrevAndNext(
		long roleId, int type, java.lang.String subtype,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_S_PrevAndNext(roleId, type, subtype, obc);
	}

	public static com.liferay.portal.model.Role findByC_C_C(long companyId,
		long classNameId, long classPK)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C_C(companyId, classNameId, classPK);
	}

	public static com.liferay.portal.model.Role fetchByC_C_C(long companyId,
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_C_C(companyId, classNameId, classPK);
	}

	public static com.liferay.portal.model.Role fetchByC_C_C(long companyId,
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_C(companyId, classNameId, classPK,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.Role> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Role> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Role> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeBySubtype(java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeBySubtype(subtype);
	}

	public static void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_N(companyId, name);
	}

	public static void removeByT_S(int type, java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByT_S(type, subtype);
	}

	public static void removeByC_C_C(long companyId, long classNameId,
		long classPK)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C_C(companyId, classNameId, classPK);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countBySubtype(java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBySubtype(subtype);
	}

	public static int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_N(companyId, name);
	}

	public static int countByT_S(int type, java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_S(type, subtype);
	}

	public static int countByC_C_C(long companyId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C_C(companyId, classNameId, classPK);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk);
	}

	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk, start, end, obc);
	}

	public static int getGroupsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroupsSize(pk);
	}

	public static boolean containsGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsGroup(pk, groupPK);
	}

	public static boolean containsGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsGroups(pk);
	}

	public static void addGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroup(pk, groupPK);
	}

	public static void addGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroup(pk, group);
	}

	public static void addGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroups(pk, groupPKs);
	}

	public static void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroups(pk, groups);
	}

	public static void clearGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearGroups(pk);
	}

	public static void removeGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroup(pk, groupPK);
	}

	public static void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroup(pk, group);
	}

	public static void removeGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroups(pk, groupPKs);
	}

	public static void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroups(pk, groups);
	}

	public static void setGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setGroups(pk, groupPKs);
	}

	public static void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setGroups(pk, groups);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getPermissions(pk);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getPermissions(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getPermissions(pk, start, end, obc);
	}

	public static int getPermissionsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getPermissionsSize(pk);
	}

	public static boolean containsPermission(long pk, long permissionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsPermission(pk, permissionPK);
	}

	public static boolean containsPermissions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsPermissions(pk);
	}

	public static void addPermission(long pk, long permissionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addPermission(pk, permissionPK);
	}

	public static void addPermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addPermission(pk, permission);
	}

	public static void addPermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addPermissions(pk, permissionPKs);
	}

	public static void addPermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addPermissions(pk, permissions);
	}

	public static void clearPermissions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearPermissions(pk);
	}

	public static void removePermission(long pk, long permissionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removePermission(pk, permissionPK);
	}

	public static void removePermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removePermission(pk, permission);
	}

	public static void removePermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removePermissions(pk, permissionPKs);
	}

	public static void removePermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removePermissions(pk, permissions);
	}

	public static void setPermissions(long pk, long[] permissionPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setPermissions(pk, permissionPKs);
	}

	public static void setPermissions(long pk,
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setPermissions(pk, permissions);
	}

	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsers(pk);
	}

	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsers(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsers(pk, start, end, obc);
	}

	public static int getUsersSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsersSize(pk);
	}

	public static boolean containsUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsUser(pk, userPK);
	}

	public static boolean containsUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsUsers(pk);
	}

	public static void addUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUser(pk, userPK);
	}

	public static void addUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUser(pk, user);
	}

	public static void addUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUsers(pk, userPKs);
	}

	public static void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUsers(pk, users);
	}

	public static void clearUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearUsers(pk);
	}

	public static void removeUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUser(pk, userPK);
	}

	public static void removeUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUser(pk, user);
	}

	public static void removeUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUsers(pk, userPKs);
	}

	public static void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUsers(pk, users);
	}

	public static void setUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setUsers(pk, userPKs);
	}

	public static void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setUsers(pk, users);
	}

	public static RolePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (RolePersistence)PortalBeanLocatorUtil.locate(RolePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(RolePersistence persistence) {
		_persistence = persistence;
	}

	private static RolePersistence _persistence;
}