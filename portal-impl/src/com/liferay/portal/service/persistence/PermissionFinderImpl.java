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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.impl.PermissionImpl;
import com.liferay.portal.model.impl.PermissionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="PermissionFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PermissionFinderImpl
	extends BasePersistenceImpl<Permission> implements PermissionFinder {

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

	public static String FIND_BY_R_R =
		PermissionFinder.class.getName() + ".findByR_R";

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

	public static final FinderPath FINDER_PATH_COUNT_BY_ROLES_PERMISSIONS =
		new FinderPath(
			PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED, "Roles_Permissions",
			"customCountByRolesPermissions",
			new String[] {
				java.util.List.class.getName(), java.util.List.class.getName()
			});

	public static final FinderPath FINDER_PATH_FIND_BY_A_R = new FinderPath(
		PermissionModelImpl.ENTITY_CACHE_ENABLED,
		PermissionModelImpl.FINDER_CACHE_ENABLED,
		PermissionPersistenceImpl.FINDER_CLASS_NAME_LIST, "customFindByA_R",
		new String[] {
			String.class.getName(), "[L" + Long.class.getName()
		});

	public boolean containsPermissions_2(
			List<Permission> permissions, long userId, List<Group> groups,
			long groupId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = null;

			StringBundler sb = new StringBundler();

			if (groups.size() > 0) {
				sb.append("(");
				sb.append(CustomSQLUtil.get(COUNT_BY_GROUPS_ROLES));
				sb.append(") ");

				sql = sb.toString();

				sql = StringUtil.replace(
					sql, "[$PERMISSION_IDS$]",
					getPermissionIds(permissions, "Roles_Permissions"));
				sql = StringUtil.replace(
					sql, "[$GROUP_IDS$]", getGroupIds(groups, "Groups_Roles"));

				sb.setIndex(0);

				sb.append(sql);

				sb.append("UNION ALL (");
				sb.append(CustomSQLUtil.get(COUNT_BY_GROUPS_PERMISSIONS));
				sb.append(") ");

				sql = sb.toString();

				sql = StringUtil.replace(
					sql, "[$PERMISSION_IDS$]",
					getPermissionIds(permissions, "Groups_Permissions"));
				sql = StringUtil.replace(
					sql, "[$GROUP_IDS$]",
					getGroupIds(groups, "Groups_Permissions"));

				sb.setIndex(0);

				sb.append(sql);

				sb.append("UNION ALL ");
			}

			sb.append("(");
			sb.append(CustomSQLUtil.get(COUNT_BY_USERS_ROLES));
			sb.append(") ");

			sql = sb.toString();

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				getPermissionIds(permissions, "Roles_Permissions"));

			sb.setIndex(0);

			sb.append(sql);

			sb.append("UNION ALL (");
			sb.append(CustomSQLUtil.get(COUNT_BY_USER_GROUP_ROLE));
			sb.append(") ");

			sql = sb.toString();

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				getPermissionIds(permissions, "Roles_Permissions"));

			sb.setIndex(0);

			sb.append(sql);

			sb.append("UNION ALL (");
			sb.append(CustomSQLUtil.get(COUNT_BY_USERS_PERMISSIONS));
			sb.append(") ");

			sql = sb.toString();

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				getPermissionIds(permissions, "Users_Permissions"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groups.size() > 0) {
				setPermissionIds(qPos, permissions);
				setGroupIds(qPos, groups);
				setPermissionIds(qPos, permissions);
				setGroupIds(qPos, groups);
			}

			setPermissionIds(qPos, permissions);
			qPos.add(userId);

			qPos.add(groupId);
			setPermissionIds(qPos, permissions);
			qPos.add(userId);

			setPermissionIds(qPos, permissions);
			qPos.add(userId);

			Iterator<Long> itr = q.list().iterator();

			while (itr.hasNext()) {
				Long count = itr.next();

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
			closeSession(session);
		}
	}

	public boolean containsPermissions_4(
			List<Permission> permissions, long userId, List<Group> groups,
			List<Role> roles)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = null;

			StringBundler sb = new StringBundler();

			if (groups.size() > 0) {
				sb.append("(");
				sb.append(CustomSQLUtil.get(COUNT_BY_GROUPS_PERMISSIONS));
				sb.append(") ");

				sql = sb.toString();

				sql = StringUtil.replace(
					sql, "[$PERMISSION_IDS$]",
					getPermissionIds(permissions, "Groups_Permissions"));
				sql = StringUtil.replace(
					sql, "[$GROUP_IDS$]",
					getGroupIds(groups, "Groups_Permissions"));

				sb.setIndex(0);

				sb.append(sql);

				sb.append("UNION ALL ");
			}

			if (roles.size() > 0) {
				sb.append("(");
				sb.append(CustomSQLUtil.get(COUNT_BY_ROLES_PERMISSIONS));
				sb.append(") ");

				sql = sb.toString();

				sql = StringUtil.replace(
					sql, "[$PERMISSION_IDS$]",
					getPermissionIds(permissions, "Roles_Permissions"));
				sql = StringUtil.replace(
					sql, "[$ROLE_IDS$]",
					getRoleIds(roles, "Roles_Permissions"));

				sb.setIndex(0);

				sb.append(sql);

				sb.append("UNION ALL ");
			}

			sb.append("(");
			sb.append(CustomSQLUtil.get(COUNT_BY_USERS_PERMISSIONS));
			sb.append(") ");

			sql = sb.toString();

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				getPermissionIds(permissions, "Users_Permissions"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groups.size() > 0) {
				setPermissionIds(qPos, permissions);
				setGroupIds(qPos, groups);
			}

			if (roles.size() > 0) {
				setPermissionIds(qPos, permissions);
				setRoleIds(qPos, roles);
			}

			setPermissionIds(qPos, permissions);
			qPos.add(userId);

			Iterator<Long> itr = q.list().iterator();

			while (itr.hasNext()) {
				Long count = itr.next();

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
			closeSession(session);
		}
	}

	public int countByGroupsPermissions(
			List<Permission> permissions, List<Group> groups)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_GROUPS_PERMISSIONS);

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				getPermissionIds(permissions, "Groups_Permissions"));
			sql = StringUtil.replace(
				sql, "[$GROUP_IDS$]",
				getGroupIds(groups, "Groups_Permissions"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setPermissionIds(qPos, permissions);
			setGroupIds(qPos, groups);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

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
			closeSession(session);
		}
	}

	public int countByGroupsRoles(
			List<Permission> permissions, List<Group> groups)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_GROUPS_ROLES);

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				getPermissionIds(permissions, "Roles_Permissions"));
			sql = StringUtil.replace(
				sql, "[$GROUP_IDS$]", getGroupIds(groups, "Groups_Roles"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setPermissionIds(qPos, permissions);
			setGroupIds(qPos, groups);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

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
			closeSession(session);
		}
	}

	public int countByRolesPermissions(
			List<Permission> permissions, List<Role> roles)
		throws SystemException {

		Object finderArgs[] = new Object[] {
			ListUtil.toString(permissions, "permissionId"),
			ListUtil.toString(roles, "roleId")
		};

		Long count = (Long)FinderCacheUtil.getResult(
			FINDER_PATH_COUNT_BY_ROLES_PERMISSIONS, finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = CustomSQLUtil.get(COUNT_BY_ROLES_PERMISSIONS);

				sql = StringUtil.replace(
					sql, "[$PERMISSION_IDS$]",
					getPermissionIds(permissions, "Roles_Permissions"));
				sql = StringUtil.replace(
					sql, "[$ROLE_IDS$]",
					getRoleIds(roles, "Roles_Permissions"));

				SQLQuery q = session.createSQLQuery(sql);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				setPermissionIds(qPos, permissions);
				setRoleIds(qPos, roles);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(
					FINDER_PATH_COUNT_BY_ROLES_PERMISSIONS, finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByUserGroupRole(
			List<Permission> permissions, long userId, long groupId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_USER_GROUP_ROLE);

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				getPermissionIds(permissions, "Roles_Permissions"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			setPermissionIds(qPos, permissions);
			qPos.add(userId);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

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
			closeSession(session);
		}
	}

	public int countByUsersPermissions(
			List<Permission> permissions, long userId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_USERS_PERMISSIONS);

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				getPermissionIds(permissions, "Users_Permissions"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setPermissionIds(qPos, permissions);
			qPos.add(userId);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

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
			closeSession(session);
		}
	}

	public int countByUsersRoles(List<Permission> permissions, long userId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_USERS_ROLES);

			sql = StringUtil.replace(
				sql, "[$PERMISSION_IDS$]",
				getPermissionIds(permissions, "Roles_Permissions"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setPermissionIds(qPos, permissions);
			qPos.add(userId);

			Iterator<Long> itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = itr.next();

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
			closeSession(session);
		}
	}

	public List<Permission> findByA_R(String actionId, long[] resourceIds)
		throws SystemException {

		Object finderArgs[] = new Object[] {
			actionId, StringUtil.merge(ArrayUtil.toArray(resourceIds))
		};

		List<Permission> list = (List<Permission>)FinderCacheUtil.getResult(
			FINDER_PATH_FIND_BY_A_R, finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = CustomSQLUtil.get(FIND_BY_A_R);

				sql = StringUtil.replace(
					sql, "[$RESOURCE_IDS$]", getResourceIds(resourceIds));

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Permission_", PermissionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actionId);
				setResourceIds(qPos, resourceIds);

				list = q.list();
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Permission>();
				}

				FinderCacheUtil.putResult(
					FINDER_PATH_FIND_BY_A_R, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Permission> findByG_R(long groupId, long resourceId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

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
			closeSession(session);
		}
	}

	public List<Permission> findByR_R(
			long roleId, long resourceId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_R_R);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Permission_", PermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(roleId);
			qPos.add(resourceId);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Permission> findByU_R(long userId, long resourceId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

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
			closeSession(session);
		}
	}

	public List<Permission> findByO_G_R(
			long organizationId, long groupId, long resourceId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

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
			closeSession(session);
		}
	}

	public List<Permission> findByU_A_R(
			long userId, String[] actionIds, long resourceId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_U_R);

			sql = StringUtil.replace(
				sql, "[$ACTION_IDS$]", getActionIds(actionIds));

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
			closeSession(session);
		}
	}

	public List<Permission> findByG_C_N_S_P(
			long groupId, long companyId, String name, int scope,
			String primKey)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

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
			closeSession(session);
		}
	}

	public List<Permission> findByU_C_N_S_P(
			long userId, long companyId, String name, int scope, String primKey)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

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
			closeSession(session);
		}
	}

	protected String getActionIds(String[] actionIds) {
		if (actionIds.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(actionIds.length * 2 - 1);

		for (int i = 0; i < actionIds.length; i++) {
			sb.append("Permission_.actionId = ?");

			if ((i + 1) < actionIds.length) {
				sb.append(" OR ");
			}
		}

		return sb.toString();
	}

	protected String getGroupIds(List<Group> groups, String table) {
		if (groups.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(groups.size() * 3 - 1);

		for (int i = 0; i < groups.size(); i++) {
			sb.append(table);
			sb.append(".groupId = ?");

			if ((i + 1) < groups.size()) {
				sb.append(" OR ");
			}
		}

		return sb.toString();
	}

	protected String getPermissionIds(
		List<Permission> permissions, String table) {

		if (permissions.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(permissions.size() * 3 - 1);

		for (int i = 0; i < permissions.size(); i++) {
			sb.append(table);
			sb.append(".permissionId = ?");

			if ((i + 1) < permissions.size()) {
				sb.append(" OR ");
			}
		}

		return sb.toString();
	}

	protected String getResourceIds(long[] resourceIds) {
		if (resourceIds.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(resourceIds.length * 2 - 1);

		for (int i = 0; i < resourceIds.length; i++) {
			sb.append("resourceId = ?");

			if ((i + 1) < resourceIds.length) {
				sb.append(" OR ");
			}
		}

		return sb.toString();
	}

	protected String getRoleIds(List<Role> roles, String table) {
		if (roles.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(roles.size() * 3 - 1);

		for (int i = 0; i < roles.size(); i++) {
			sb.append(table);
			sb.append(".roleId = ?");

			if ((i + 1) < roles.size()) {
				sb.append(" OR ");
			}
		}

		return sb.toString();
	}

	protected void setGroupIds(QueryPos qPos, List<Group> groups) {
		for (Group group : groups) {
			qPos.add(group.getGroupId());
		}
	}

	protected void setPermissionIds(
		QueryPos qPos, List<Permission> permissions) {

		for (Permission permission : permissions) {
			qPos.add(permission.getPermissionId());
		}
	}

	protected void setResourceIds(QueryPos qPos, long[] resourceIds) {
		for (long resourceId : resourceIds) {
			qPos.add(resourceId);
		}
	}

	protected void setRoleIds(QueryPos qPos, List<Role> roles) {
		for (Role role : roles) {
			qPos.add(role.getRoleId());
		}
	}

}