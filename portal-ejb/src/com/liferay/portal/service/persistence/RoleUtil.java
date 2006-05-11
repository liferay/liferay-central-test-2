/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="RoleUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class RoleUtil {
	public static final String CLASS_NAME = RoleUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Role"));

	public static com.liferay.portal.model.Role create(java.lang.String roleId) {
		return getPersistence().create(roleId);
	}

	public static com.liferay.portal.model.Role remove(java.lang.String roleId)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(roleId));
		}

		com.liferay.portal.model.Role role = getPersistence().remove(roleId);

		if (listener != null) {
			listener.onAfterRemove(role);
		}

		return role;
	}

	public static com.liferay.portal.model.Role update(
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = role.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(role);
			}
			else {
				listener.onBeforeUpdate(role);
			}
		}

		role = getPersistence().update(role);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(role);
			}
			else {
				listener.onAfterUpdate(role);
			}
		}

		return role;
	}

	public static java.util.List getGroups(java.lang.String pk)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().getGroups(pk);
	}

	public static java.util.List getGroups(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().getGroups(pk, begin, end);
	}

	public static java.util.List getGroups(java.lang.String pk, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().getGroups(pk, begin, end, obc);
	}

	public static int getGroupsSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getGroupsSize(pk);
	}

	public static void setGroups(java.lang.String pk, java.lang.String[] pks)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().setGroups(pk, pks);
	}

	public static void setGroups(java.lang.String pk, java.util.List groups)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().setGroups(pk, groups);
	}

	public static boolean addGroup(java.lang.String pk, java.lang.String groupPK)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().addGroup(pk, groupPK);
	}

	public static boolean addGroup(java.lang.String pk,
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().addGroup(pk, group);
	}

	public static boolean addGroups(java.lang.String pk,
		java.lang.String[] groupPKs)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().addGroups(pk, groupPKs);
	}

	public static boolean addGroups(java.lang.String pk, java.util.List groups)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().addGroups(pk, groups);
	}

	public static void clearGroups(java.lang.String pk)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().clearGroups(pk);
	}

	public static boolean containsGroup(java.lang.String pk,
		java.lang.String groupPK) throws com.liferay.portal.SystemException {
		return getPersistence().containsGroup(pk, groupPK);
	}

	public static boolean containsGroups(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsGroups(pk);
	}

	public static boolean removeGroup(java.lang.String pk,
		java.lang.String groupPK)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeGroup(pk, groupPK);
	}

	public static boolean removeGroup(java.lang.String pk,
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeGroup(pk, group);
	}

	public static boolean removeGroups(java.lang.String pk,
		java.lang.String[] groupPKs)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeGroups(pk, groupPKs);
	}

	public static boolean removeGroups(java.lang.String pk,
		java.util.List groups)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeGroups(pk, groups);
	}

	public static java.util.List getPermissions(java.lang.String pk)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().getPermissions(pk);
	}

	public static java.util.List getPermissions(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().getPermissions(pk, begin, end);
	}

	public static java.util.List getPermissions(java.lang.String pk, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().getPermissions(pk, begin, end, obc);
	}

	public static int getPermissionsSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getPermissionsSize(pk);
	}

	public static void setPermissions(java.lang.String pk,
		java.lang.String[] pks)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().setPermissions(pk, pks);
	}

	public static void setPermissions(java.lang.String pk,
		java.util.List permissions)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().setPermissions(pk, permissions);
	}

	public static boolean addPermission(java.lang.String pk,
		java.lang.String permissionPK)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().addPermission(pk, permissionPK);
	}

	public static boolean addPermission(java.lang.String pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().addPermission(pk, permission);
	}

	public static boolean addPermissions(java.lang.String pk,
		java.lang.String[] permissionPKs)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().addPermissions(pk, permissionPKs);
	}

	public static boolean addPermissions(java.lang.String pk,
		java.util.List permissions)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().addPermissions(pk, permissions);
	}

	public static void clearPermissions(java.lang.String pk)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().clearPermissions(pk);
	}

	public static boolean containsPermission(java.lang.String pk,
		java.lang.String permissionPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsPermission(pk, permissionPK);
	}

	public static boolean containsPermissions(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsPermissions(pk);
	}

	public static boolean removePermission(java.lang.String pk,
		java.lang.String permissionPK)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().removePermission(pk, permissionPK);
	}

	public static boolean removePermission(java.lang.String pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().removePermission(pk, permission);
	}

	public static boolean removePermissions(java.lang.String pk,
		java.lang.String[] permissionPKs)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().removePermissions(pk, permissionPKs);
	}

	public static boolean removePermissions(java.lang.String pk,
		java.util.List permissions)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().removePermissions(pk, permissions);
	}

	public static java.util.List getUsers(java.lang.String pk)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk);
	}

	public static java.util.List getUsers(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk, begin, end);
	}

	public static java.util.List getUsers(java.lang.String pk, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk, begin, end, obc);
	}

	public static int getUsersSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getUsersSize(pk);
	}

	public static void setUsers(java.lang.String pk, java.lang.String[] pks)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().setUsers(pk, pks);
	}

	public static void setUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().setUsers(pk, users);
	}

	public static boolean addUser(java.lang.String pk, java.lang.String userPK)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUser(pk, userPK);
	}

	public static boolean addUser(java.lang.String pk,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUser(pk, user);
	}

	public static boolean addUsers(java.lang.String pk,
		java.lang.String[] userPKs)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUsers(pk, userPKs);
	}

	public static boolean addUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUsers(pk, users);
	}

	public static void clearUsers(java.lang.String pk)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().clearUsers(pk);
	}

	public static boolean containsUser(java.lang.String pk,
		java.lang.String userPK) throws com.liferay.portal.SystemException {
		return getPersistence().containsUser(pk, userPK);
	}

	public static boolean containsUsers(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsUsers(pk);
	}

	public static boolean removeUser(java.lang.String pk,
		java.lang.String userPK)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUser(pk, userPK);
	}

	public static boolean removeUser(java.lang.String pk,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUser(pk, user);
	}

	public static boolean removeUsers(java.lang.String pk,
		java.lang.String[] userPKs)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUsers(pk, userPKs);
	}

	public static boolean removeUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUsers(pk, users);
	}

	public static com.liferay.portal.model.Role findByPrimaryKey(
		java.lang.String roleId)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(roleId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portal.model.Role findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.Role findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Role[] findByCompanyId_PrevAndNext(
		java.lang.String roleId, java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_PrevAndNext(roleId, companyId,
			obc);
	}

	public static com.liferay.portal.model.Role findByC_C_C(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, className, classPK);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().removeByC_C_C(companyId, className, classPK);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C_C(companyId, className, classPK);
	}

	public static RolePersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		RoleUtil util = (RoleUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(RolePersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(RoleUtil.class);
	private RolePersistence _persistence;
}