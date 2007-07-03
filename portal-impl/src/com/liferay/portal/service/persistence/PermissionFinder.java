/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.impl.PermissionImpl;
import com.liferay.portal.spring.hibernate.CustomSQLUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.dao.hibernate.QueryPos;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * <a href="PermissionFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PermissionFinder {

	public static String COUNT_BY_GROUPS_PERMISSIONS =
		PermissionFinder.class.getName() + ".countByGroupsPermissions";

	public static String COUNT_BY_GROUPS_ROLES =
		PermissionFinder.class.getName() + ".countByGroupsRoles";

	public static String COUNT_BY_ROLES_PERMISSIONS =
		PermissionFinder.class.getName() + ".countByRolesPermissions";

	public static String COUNT_BY_USER_GROUP_ROLE =
		PermissionFinder.class.getName() + ".countByUserGroupRole";

	public static String COUNT_BY_USERS_PERMISSIONS =
		PermissionFinder.class.getName() + ".countByUsersPermissions";

	public static String COUNT_BY_USERS_ROLES =
		PermissionFinder.class.getName() + ".countByUsersRoles";

	public static String FIND_BY_A_R =
		PermissionFinder.class.getName() + ".findByA_R";

	public static String FIND_BY_G_R =
		PermissionFinder.class.getName() + ".findByG_R";

	public static String FIND_BY_U_R =
		PermissionFinder.class.getName() + ".findByU_R";

	public static String FIND_BY_O_G_R =
		PermissionFinder.class.getName() + ".findByO_G_R";

	public static String FIND_BY_U_A_R =
		PermissionFinder.class.getName() + ".findByU_A_R";

	public static String FIND_BY_G_C_N_S_P =
		PermissionFinder.class.getName() + ".findByG_C_N_S_P";

	public static String FIND_BY_U_C_N_S_P =
		PermissionFinder.class.getName() + ".findByU_C_N_S_P";

	public static boolean containsPermissions_2(
			List permissions, long userId, List groups, long groupId)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = null;

			StringMaker sm = new StringMaker();

			if (groups.size() > 0) {
				sm.append("(");
				sm.append(CustomSQLUtil.get(COUNT_BY_GROUPS_ROLES));
				sm.append(") ");

				sql = sm.toString();

				sql = StringUtil.replace(
					sql, "[$PERMISSION_IDS$]",
					_getPermissionIds(permissions, "Roles_Permissions"));
				sql = StringUtil.replace(
					sql, "[$GROUP_IDS$]", _getGroupIds(groups, "Groups_Roles"));

				sm = new StringMaker();

				sm.append(sql);

				sm.append("UNION ALL (");
				sm.append(CustomSQLUtil.get(COUNT_BY_GROUPS_PERMISSIONS));
				sm.append(") ");

				sql = sm.toString();

				sql = StringUtil.replace(
					sql, "[$PERMISSION_IDS$]",
					_getPermissionIds(permissions, "Groups_Permissions"));
				sql = StringUtil.replace(
					sql, "[$GROUP_IDS$]",
					_getGroupIds(groups, "Groups_Permissions"));

				sm = new StringMaker();

				sm.append(sql);

				sm.append("UNION ALL ");
			}

			sm.append("(");
			sm.append(CustomSQLUtil.get(COUNT_BY_USERS_ROLES));
			sm.append(") ");

			sql = sm.toString();

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				_getPermissionIds(permissions, "Roles_Permissions"));

			sm = new StringMaker();

			sm.append(sql);

			sm.append("UNION ALL (");
			sm.append(CustomSQLUtil.get(COUNT_BY_USER_GROUP_ROLE));
			sm.append(") ");

			sql = sm.toString();

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				_getPermissionIds(permissions, "Roles_Permissions"));

			sm = new StringMaker();

			sm.append(sql);

			sm.append("UNION ALL (");
			sm.append(CustomSQLUtil.get(COUNT_BY_USERS_PERMISSIONS));
			sm.append(") ");

			sql = sm.toString();

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				_getPermissionIds(permissions, "Users_Permissions"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groups.size() > 0) {
				_setPermissionIds(qPos, permissions);
				_setGroupIds(qPos, groups);
				_setPermissionIds(qPos, permissions);
				_setGroupIds(qPos, groups);
			}

			_setPermissionIds(qPos, permissions);
			qPos.add(userId);

			qPos.add(groupId);
			_setPermissionIds(qPos, permissions);
			qPos.add(userId);

			_setPermissionIds(qPos, permissions);
			qPos.add(userId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				Long count = (Long)itr.next();

				if ((count != null) && (count.intValue() > 0)) {
					return true;
				}
			}

			return false;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static boolean containsPermissions_4(
			List permissions, long userId, List groups, List roles)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = null;

			StringMaker sm = new StringMaker();

			if (groups.size() > 0) {
				sm.append("(");
				sm.append(CustomSQLUtil.get(COUNT_BY_GROUPS_PERMISSIONS));
				sm.append(") ");

				sql = sm.toString();

				sql = StringUtil.replace(
					sql, "[$PERMISSION_IDS$]",
					_getPermissionIds(permissions, "Groups_Permissions"));
				sql = StringUtil.replace(
					sql, "[$GROUP_IDS$]",
					_getGroupIds(groups, "Groups_Permissions"));

				sm = new StringMaker();

				sm.append(sql);

				sm.append("UNION ALL ");
			}

			if (roles.size() > 0) {
				sm.append("(");
				sm.append(CustomSQLUtil.get(COUNT_BY_ROLES_PERMISSIONS));
				sm.append(") ");

				sql = sm.toString();

				sql = StringUtil.replace(
					sql, "[$PERMISSION_IDS$]",
					_getPermissionIds(permissions, "Roles_Permissions"));
				sql = StringUtil.replace(
					sql, "[$ROLE_IDS$]",
					_getRoleIds(roles, "Roles_Permissions"));

				sm = new StringMaker();

				sm.append(sql);

				sm.append("UNION ALL ");
			}

			sm.append("(");
			sm.append(CustomSQLUtil.get(COUNT_BY_USERS_PERMISSIONS));
			sm.append(") ");

			sql = sm.toString();

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				_getPermissionIds(permissions, "Users_Permissions"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groups.size() > 0) {
				_setPermissionIds(qPos, permissions);
				_setGroupIds(qPos, groups);
			}

			if (roles.size() > 0) {
				_setPermissionIds(qPos, permissions);
				_setRoleIds(qPos, roles);
			}

			_setPermissionIds(qPos, permissions);
			qPos.add(userId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				Long count = (Long)itr.next();

				if ((count != null) && (count.intValue() > 0)) {
					return true;
				}
			}

			return false;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static int countByGroupsPermissions(List permissions, List groups)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_GROUPS_PERMISSIONS);

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				_getPermissionIds(permissions, "Groups_Permissions"));
			sql = StringUtil.replace(
				sql, "[$GROUP_IDS$]",
				_getGroupIds(groups, "Groups_Permissions"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			_setPermissionIds(qPos, permissions);
			_setGroupIds(qPos, groups);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static int countByGroupsRoles(List permissions, List groups)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_GROUPS_ROLES);

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				_getPermissionIds(permissions, "Roles_Permissions"));
			sql = StringUtil.replace(
				sql, "[$GROUP_IDS$]", _getGroupIds(groups, "Groups_Roles"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			_setPermissionIds(qPos, permissions);
			_setGroupIds(qPos, groups);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static int countByRolesPermissions(List permissions, List roles)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_ROLES_PERMISSIONS);

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				_getPermissionIds(permissions, "Roles_Permissions"));
			sql = StringUtil.replace(
				sql, "[$ROLE_IDS$]", _getRoleIds(roles, "Roles_Permissions"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			_setPermissionIds(qPos, permissions);
			_setRoleIds(qPos, roles);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static int countByUserGroupRole(
			List permissions, long userId, long groupId)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_USER_GROUP_ROLE);

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				_getPermissionIds(permissions, "Roles_Permissions"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			_setPermissionIds(qPos, permissions);
			qPos.add(userId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static int countByUsersPermissions(List permissions, long userId)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_USERS_PERMISSIONS);

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				_getPermissionIds(permissions, "Users_Permissions"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			_setPermissionIds(qPos, permissions);
			qPos.add(userId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static int countByUsersRoles(List permissions, long userId)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_USERS_ROLES);

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				_getPermissionIds(permissions, "Roles_Permissions"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			_setPermissionIds(qPos, permissions);
			qPos.add(userId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List findByA_R(String actionId, long[] resourceIds)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_A_R);

			sql = StringUtil.replace(
				sql, "[$RESOURCE_IDS$]", _getResourceIds(resourceIds));

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(true);

			q.addEntity("Permission_", PermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(actionId);
			_setResourceIds(qPos, resourceIds);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List findByG_R(long groupId, long resourceId)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_R);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Permission_", PermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(resourceId);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List findByU_R(long userId, long resourceId)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_U_R);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Permission_", PermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);
			qPos.add(resourceId);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List findByO_G_R(
			long organizationId, long groupId, long resourceId)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_O_G_R);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Permission_", PermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(organizationId);
			qPos.add(groupId);
			qPos.add(resourceId);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List findByU_A_R(
			long userId, String[] actionIds, long resourceId)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_U_R);

			sql = StringUtil.replace(
				sql, "[$ACTION_IDS$]", _getActionIds(actionIds));

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Permission_", PermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);
			qPos.add(resourceId);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List findByG_C_N_S_P(
			long groupId, long companyId, String name, int scope,
			String primKey)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_C_N_S_P);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Permission_", PermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(companyId);
			qPos.add(name);
			qPos.add(scope);
			qPos.add(primKey);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public static List findByU_C_N_S_P(
			long userId, long companyId, String name, int scope, String primKey)
		throws SystemException {

		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = CustomSQLUtil.get(FIND_BY_U_C_N_S_P);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Permission_", PermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);
			qPos.add(companyId);
			qPos.add(name);
			qPos.add(scope);
			qPos.add(primKey);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	private static String _getActionIds(String[] actionIds) {
		StringMaker sm = new StringMaker();

		for (int i = 0; i < actionIds.length; i++) {
			sm.append("Permission_.actionId = ?");

			if ((i + 1) < actionIds.length) {
				sm.append(" OR ");
			}
		}

		return sm.toString();
	}

	private static String _getGroupIds(List groups, String table) {
		StringMaker sm = new StringMaker();

		for (int i = 0; i < groups.size(); i++) {
			sm.append(table);
			sm.append(".groupId = ?");

			if ((i + 1) < groups.size()) {
				sm.append(" OR ");
			}
		}

		return sm.toString();
	}

	private static String _getPermissionIds(List permissions, String table) {
		StringMaker sm = new StringMaker();

		for (int i = 0; i < permissions.size(); i++) {
			sm.append(table);
			sm.append(".permissionId = ?");

			if ((i + 1) < permissions.size()) {
				sm.append(" OR ");
			}
		}

		return sm.toString();
	}

	private static String _getResourceIds(long[] resourceIds) {
		StringMaker sm = new StringMaker();

		for (int i = 0; i < resourceIds.length; i++) {
			sm.append("resourceId = ?");

			if ((i + 1) < resourceIds.length) {
				sm.append(" OR ");
			}
		}

		return sm.toString();
	}

	private static String _getRoleIds(List roles, String table) {
		StringMaker sm = new StringMaker();

		for (int i = 0; i < roles.size(); i++) {
			sm.append(table);
			sm.append(".roleId = ?");

			if ((i + 1) < roles.size()) {
				sm.append(" OR ");
			}
		}

		return sm.toString();
	}

	private static void _setGroupIds(QueryPos qPos, List groups) {
		for (int i = 0; i < groups.size(); i++) {
			Group group = (Group)groups.get(i);

			qPos.add(group.getGroupId());
		}
	}

	private static void _setPermissionIds(QueryPos qPos, List permissions) {
		for (int i = 0; i < permissions.size(); i++) {
			Permission permission = (Permission)permissions.get(i);

			qPos.add(permission.getPermissionId());
		}
	}

	private static void _setResourceIds(QueryPos qPos, long[] resourceIds) {
		for (int i = 0; i < resourceIds.length; i++) {
			long resourceId = resourceIds[i];

			qPos.add(resourceId);
		}
	}

	private static void _setRoleIds(QueryPos qPos, List roles) {
		for (int i = 0; i < roles.size(); i++) {
			Role role = (Role)roles.get(i);

			qPos.add(role.getRoleId());
		}
	}

}