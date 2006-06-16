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
 * <a href="GroupUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GroupUtil {
	public static final String CLASS_NAME = GroupUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Group"));

	public static com.liferay.portal.model.Group create(
		java.lang.String groupId) {
		return getPersistence().create(groupId);
	}

	public static com.liferay.portal.model.Group remove(
		java.lang.String groupId)
		throws com.liferay.portal.NoSuchGroupException, 
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
			listener.onBeforeRemove(findByPrimaryKey(groupId));
		}

		com.liferay.portal.model.Group group = getPersistence().remove(groupId);

		if (listener != null) {
			listener.onAfterRemove(group);
		}

		return group;
	}

	public static com.liferay.portal.model.Group update(
		com.liferay.portal.model.Group group)
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

		boolean isNew = group.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(group);
			}
			else {
				listener.onBeforeUpdate(group);
			}
		}

		group = getPersistence().update(group);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(group);
			}
			else {
				listener.onAfterUpdate(group);
			}
		}

		return group;
	}

	public static java.util.List getOrganizations(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getOrganizations(pk);
	}

	public static java.util.List getOrganizations(java.lang.String pk,
		int begin, int end)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getOrganizations(pk, begin, end);
	}

	public static java.util.List getOrganizations(java.lang.String pk,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getOrganizations(pk, begin, end, obc);
	}

	public static int getOrganizationsSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getOrganizationsSize(pk);
	}

	public static void setOrganizations(java.lang.String pk,
		java.lang.String[] pks)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		getPersistence().setOrganizations(pk, pks);
	}

	public static void setOrganizations(java.lang.String pk, java.util.List orgs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		getPersistence().setOrganizations(pk, orgs);
	}

	public static boolean addOrganization(java.lang.String pk,
		java.lang.String organizationPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		return getPersistence().addOrganization(pk, organizationPK);
	}

	public static boolean addOrganization(java.lang.String pk,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		return getPersistence().addOrganization(pk, organization);
	}

	public static boolean addOrganizations(java.lang.String pk,
		java.lang.String[] organizationPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		return getPersistence().addOrganizations(pk, organizationPKs);
	}

	public static boolean addOrganizations(java.lang.String pk,
		java.util.List organizations)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		return getPersistence().addOrganizations(pk, organizations);
	}

	public static void clearOrganizations(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().clearOrganizations(pk);
	}

	public static boolean containsOrganization(java.lang.String pk,
		java.lang.String organizationPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsOrganization(pk, organizationPK);
	}

	public static boolean containsOrganizations(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsOrganizations(pk);
	}

	public static boolean removeOrganization(java.lang.String pk,
		java.lang.String organizationPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeOrganization(pk, organizationPK);
	}

	public static boolean removeOrganization(java.lang.String pk,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeOrganization(pk, organization);
	}

	public static boolean removeOrganizations(java.lang.String pk,
		java.lang.String[] organizationPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeOrganizations(pk, organizationPKs);
	}

	public static boolean removeOrganizations(java.lang.String pk,
		java.util.List organizations)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeOrganizations(pk, organizations);
	}

	public static java.util.List getPermissions(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getPermissions(pk);
	}

	public static java.util.List getPermissions(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getPermissions(pk, begin, end);
	}

	public static java.util.List getPermissions(java.lang.String pk, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getPermissions(pk, begin, end, obc);
	}

	public static int getPermissionsSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getPermissionsSize(pk);
	}

	public static void setPermissions(java.lang.String pk,
		java.lang.String[] pks)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().setPermissions(pk, pks);
	}

	public static void setPermissions(java.lang.String pk,
		java.util.List permissions)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		getPersistence().setPermissions(pk, permissions);
	}

	public static boolean addPermission(java.lang.String pk,
		java.lang.String permissionPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().addPermission(pk, permissionPK);
	}

	public static boolean addPermission(java.lang.String pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().addPermission(pk, permission);
	}

	public static boolean addPermissions(java.lang.String pk,
		java.lang.String[] permissionPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().addPermissions(pk, permissionPKs);
	}

	public static boolean addPermissions(java.lang.String pk,
		java.util.List permissions)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().addPermissions(pk, permissions);
	}

	public static void clearPermissions(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
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
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().removePermission(pk, permissionPK);
	}

	public static boolean removePermission(java.lang.String pk,
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().removePermission(pk, permission);
	}

	public static boolean removePermissions(java.lang.String pk,
		java.lang.String[] permissionPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().removePermissions(pk, permissionPKs);
	}

	public static boolean removePermissions(java.lang.String pk,
		java.util.List permissions)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, 
			com.liferay.portal.SystemException {
		return getPersistence().removePermissions(pk, permissions);
	}

	public static java.util.List getRoles(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getRoles(pk);
	}

	public static java.util.List getRoles(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getRoles(pk, begin, end);
	}

	public static java.util.List getRoles(java.lang.String pk, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getRoles(pk, begin, end, obc);
	}

	public static int getRolesSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getRolesSize(pk);
	}

	public static void setRoles(java.lang.String pk, java.lang.String[] pks)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().setRoles(pk, pks);
	}

	public static void setRoles(java.lang.String pk, java.util.List roles)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		getPersistence().setRoles(pk, roles);
	}

	public static boolean addRole(java.lang.String pk, java.lang.String rolePK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().addRole(pk, rolePK);
	}

	public static boolean addRole(java.lang.String pk,
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().addRole(pk, role);
	}

	public static boolean addRoles(java.lang.String pk,
		java.lang.String[] rolePKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().addRoles(pk, rolePKs);
	}

	public static boolean addRoles(java.lang.String pk, java.util.List roles)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().addRoles(pk, roles);
	}

	public static void clearRoles(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
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
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeRole(pk, rolePK);
	}

	public static boolean removeRole(java.lang.String pk,
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeRole(pk, role);
	}

	public static boolean removeRoles(java.lang.String pk,
		java.lang.String[] rolePKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeRoles(pk, rolePKs);
	}

	public static boolean removeRoles(java.lang.String pk, java.util.List roles)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchRoleException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeRoles(pk, roles);
	}

	public static java.util.List getUsers(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk);
	}

	public static java.util.List getUsers(java.lang.String pk, int begin,
		int end)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk, begin, end);
	}

	public static java.util.List getUsers(java.lang.String pk, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk, begin, end, obc);
	}

	public static int getUsersSize(java.lang.String pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getUsersSize(pk);
	}

	public static void setUsers(java.lang.String pk, java.lang.String[] pks)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().setUsers(pk, pks);
	}

	public static void setUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		getPersistence().setUsers(pk, users);
	}

	public static boolean addUser(java.lang.String pk, java.lang.String userPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUser(pk, userPK);
	}

	public static boolean addUser(java.lang.String pk,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUser(pk, user);
	}

	public static boolean addUsers(java.lang.String pk,
		java.lang.String[] userPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUsers(pk, userPKs);
	}

	public static boolean addUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().addUsers(pk, users);
	}

	public static void clearUsers(java.lang.String pk)
		throws com.liferay.portal.NoSuchGroupException, 
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
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUser(pk, userPK);
	}

	public static boolean removeUser(java.lang.String pk,
		com.liferay.portal.model.User user)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUser(pk, user);
	}

	public static boolean removeUsers(java.lang.String pk,
		java.lang.String[] userPKs)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUsers(pk, userPKs);
	}

	public static boolean removeUsers(java.lang.String pk, java.util.List users)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.NoSuchUserException, 
			com.liferay.portal.SystemException {
		return getPersistence().removeUsers(pk, users);
	}

	public static com.liferay.portal.model.Group findByPrimaryKey(
		java.lang.String groupId)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(groupId);
	}

	public static com.liferay.portal.model.Group findByC_F(
		java.lang.String companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_F(companyId, friendlyURL);
	}

	public static com.liferay.portal.model.Group findByC_C_C(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, className, classPK);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByC_F(java.lang.String companyId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().removeByC_F(companyId, friendlyURL);
	}

	public static void removeByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.NoSuchGroupException, 
			com.liferay.portal.SystemException {
		getPersistence().removeByC_C_C(companyId, className, classPK);
	}

	public static int countByC_F(java.lang.String companyId,
		java.lang.String friendlyURL) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_F(companyId, friendlyURL);
	}

	public static int countByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C_C(companyId, className, classPK);
	}

	public static GroupPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		GroupUtil util = (GroupUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(GroupPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(GroupUtil.class);
	private GroupPersistence _persistence;
}