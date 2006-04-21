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
 * <a href="PermissionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PermissionUtil {
	public static final String CLASS_NAME = PermissionUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Permission"));

	public static com.liferay.portal.model.Permission create(
		java.lang.String permissionId) {
		return getPersistence().create(permissionId);
	}

	public static com.liferay.portal.model.Permission remove(
		java.lang.String permissionId)
		throws com.liferay.portal.NoSuchPermissionException, 
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
			listener.onBeforeRemove(findByPrimaryKey(permissionId));
		}

		com.liferay.portal.model.Permission permission = getPersistence()
															 .remove(permissionId);

		if (listener != null) {
			listener.onAfterRemove(permission);
		}

		return permission;
	}

	public static com.liferay.portal.model.Permission update(
		com.liferay.portal.model.Permission permission)
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

		boolean isNew = permission.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(permission);
			}
			else {
				listener.onBeforeUpdate(permission);
			}
		}

		permission = getPersistence().update(permission);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(permission);
			}
			else {
				listener.onAfterUpdate(permission);
			}
		}

		return permission;
	}

	public static java.util.List getGroups(java.lang.String pk)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().getGroups(pk);
	}

	public static java.util.List getGroups(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().getGroups(pk, begin, end);
	}

	public static java.util.List getGroups(java.lang.String pk, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().getGroups(pk, begin, end, obc);
	}

	public static int getGroupsSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getGroupsSize(pk);
	}

	public static void setGroups(java.lang.String pk, java.lang.String[] pks)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().setGroups(pk, pks);
	}

	public static void setGroups(java.lang.String pk, java.util.List groups)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().setGroups(pk, groups);
	}

	public static boolean addGroup(java.lang.String pk, java.lang.String groupPK)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().addGroup(pk, groupPK);
	}

	public static boolean addGroup(java.lang.String pk,
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().addGroup(pk, group);
	}

	public static boolean addGroups(java.lang.String pk,
		java.lang.String[] groupPKs)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().addGroups(pk, groupPKs);
	}

	public static boolean addGroups(java.lang.String pk, java.util.List groups)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().addGroups(pk, groups);
	}

	public static void clearGroups(java.lang.String pk)
		throws com.liferay.portal.NoSuchPermissionException, 
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
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeGroup(pk, groupPK);
	}

	public static boolean removeGroup(java.lang.String pk,
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeGroup(pk, group);
	}

	public static boolean removeGroups(java.lang.String pk,
		java.lang.String[] groupPKs)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeGroups(pk, groupPKs);
	}

	public static boolean removeGroups(java.lang.String pk,
		java.util.List groups)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeGroups(pk, groups);
	}

	public static java.util.List getRoles(java.lang.String pk)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().getRoles(pk);
	}

	public static java.util.List getRoles(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().getRoles(pk, begin, end);
	}

	public static java.util.List getRoles(java.lang.String pk, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().getRoles(pk, begin, end, obc);
	}

	public static int getRolesSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getRolesSize(pk);
	}

	public static void setRoles(java.lang.String pk, java.lang.String[] pks)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().setRoles(pk, pks);
	}

	public static void setRoles(java.lang.String pk, java.util.List roles)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().setRoles(pk, roles);
	}

	public static boolean addRole(java.lang.String pk, java.lang.String rolePK)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().addRole(pk, rolePK);
	}

	public static boolean addRole(java.lang.String pk,
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().addRole(pk, role);
	}

	public static boolean addRoles(java.lang.String pk,
		java.lang.String[] rolePKs)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().addRoles(pk, rolePKs);
	}

	public static boolean addRoles(java.lang.String pk, java.util.List roles)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().addRoles(pk, roles);
	}

	public static void clearRoles(java.lang.String pk)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().clearRoles(pk);
	}

	public static boolean containsRole(java.lang.String pk,
		java.lang.String rolePK) throws com.liferay.portal.SystemException {
		return getPersistence().containsRole(pk, rolePK);
	}

	public static boolean containsRoles(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsRoles(pk);
	}

	public static boolean removeRole(java.lang.String pk,
		java.lang.String rolePK)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeRole(pk, rolePK);
	}

	public static boolean removeRole(java.lang.String pk,
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeRole(pk, role);
	}

	public static boolean removeRoles(java.lang.String pk,
		java.lang.String[] rolePKs)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeRoles(pk, rolePKs);
	}

	public static boolean removeRoles(java.lang.String pk, java.util.List roles)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeRoles(pk, roles);
	}

	public static java.util.List getUsers(java.lang.String pk)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk);
	}

	public static java.util.List getUsers(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk, begin, end);
	}

	public static java.util.List getUsers(java.lang.String pk, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk, begin, end, obc);
	}

	public static int getUsersSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getUsersSize(pk);
	}

	public static void setUsers(java.lang.String pk, java.lang.String[] pks)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().setUsers(pk, pks);
	}

	public static void setUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().setUsers(pk, users);
	}

	public static boolean addUser(java.lang.String pk, java.lang.String userPK)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUser(pk, userPK);
	}

	public static boolean addUser(java.lang.String pk,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUser(pk, user);
	}

	public static boolean addUsers(java.lang.String pk,
		java.lang.String[] userPKs)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUsers(pk, userPKs);
	}

	public static boolean addUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUsers(pk, users);
	}

	public static void clearUsers(java.lang.String pk)
		throws com.liferay.portal.NoSuchPermissionException, 
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
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUser(pk, userPK);
	}

	public static boolean removeUser(java.lang.String pk,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUser(pk, user);
	}

	public static boolean removeUsers(java.lang.String pk,
		java.lang.String[] userPKs)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUsers(pk, userPKs);
	}

	public static boolean removeUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUsers(pk, users);
	}

	public static com.liferay.portal.model.Permission findByPrimaryKey(
		java.lang.String permissionId)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(permissionId);
	}

	public static java.util.List findByResourceId(java.lang.String resourceId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByResourceId(resourceId);
	}

	public static java.util.List findByResourceId(java.lang.String resourceId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByResourceId(resourceId, begin, end);
	}

	public static java.util.List findByResourceId(java.lang.String resourceId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByResourceId(resourceId, begin, end, obc);
	}

	public static com.liferay.portal.model.Permission findByResourceId_First(
		java.lang.String resourceId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByResourceId_First(resourceId, obc);
	}

	public static com.liferay.portal.model.Permission findByResourceId_Last(
		java.lang.String resourceId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByResourceId_Last(resourceId, obc);
	}

	public static com.liferay.portal.model.Permission[] findByResourceId_PrevAndNext(
		java.lang.String permissionId, java.lang.String resourceId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByResourceId_PrevAndNext(permissionId,
			resourceId, obc);
	}

	public static com.liferay.portal.model.Permission findByA_R(
		java.lang.String actionId, java.lang.String resourceId)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByA_R(actionId, resourceId);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByResourceId(java.lang.String resourceId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByResourceId(resourceId);
	}

	public static void removeByA_R(java.lang.String actionId,
		java.lang.String resourceId)
		throws com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().removeByA_R(actionId, resourceId);
	}

	public static int countByResourceId(java.lang.String resourceId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByResourceId(resourceId);
	}

	public static int countByA_R(java.lang.String actionId,
		java.lang.String resourceId) throws com.liferay.portal.SystemException {
		return getPersistence().countByA_R(actionId, resourceId);
	}

	public static PermissionPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		PermissionUtil util = (PermissionUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(PermissionPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(PermissionUtil.class);
	private PermissionPersistence _persistence;
}