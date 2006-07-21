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

import com.liferay.portal.NoSuchPermissionException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Permission;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

import com.liferay.util.StringPool;
import com.liferay.util.dao.hibernate.OrderByComparator;
import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import org.springframework.dao.DataAccessException;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="PermissionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PermissionPersistence extends BasePersistence {
	public Permission create(String permissionId) {
		Permission permission = new Permission();
		permission.setNew(true);
		permission.setPrimaryKey(permissionId);

		return permission;
	}

	public Permission remove(String permissionId)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Permission permission = (Permission)session.get(Permission.class,
					permissionId);

			if (permission == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Permission exists with the primary key " +
						permissionId.toString());
				}

				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					permissionId.toString());
			}

			return remove(permission);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Permission remove(Permission permission) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(permission);
			session.flush();
			clearGroups.clear(permission.getPrimaryKey());
			clearRoles.clear(permission.getPrimaryKey());
			clearUsers.clear(permission.getPrimaryKey());
			PermissionPool.removeByA_R(permission.getActionId(),
				permission.getResourceId());

			return permission;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Permission update(
		com.liferay.portal.model.Permission permission)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.saveOrUpdate(permission);
			session.flush();
			permission.setNew(false);

			return permission;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Permission findByPrimaryKey(String permissionId)
		throws NoSuchPermissionException, SystemException {
		Permission permission = fetchByPrimaryKey(permissionId);

		if (permission == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Permission exists with the primary key " +
					permissionId.toString());
			}

			throw new NoSuchPermissionException(
				"No Permission exists with the primary key " +
				permissionId.toString());
		}

		return permission;
	}

	public Permission fetchByPrimaryKey(String permissionId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Permission)session.get(Permission.class, permissionId);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByResourceId(String resourceId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Permission WHERE ");

			if (resourceId == null) {
				query.append("resourceId IS NULL");
			}
			else {
				query.append("resourceId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (resourceId != null) {
				q.setString(queryPos++, resourceId);
			}

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByResourceId(String resourceId, int begin, int end)
		throws SystemException {
		return findByResourceId(resourceId, begin, end, null);
	}

	public List findByResourceId(String resourceId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Permission WHERE ");

			if (resourceId == null) {
				query.append("resourceId IS NULL");
			}
			else {
				query.append("resourceId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (resourceId != null) {
				q.setString(queryPos++, resourceId);
			}

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Permission findByResourceId_First(String resourceId,
		OrderByComparator obc)
		throws NoSuchPermissionException, SystemException {
		List list = findByResourceId(resourceId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Permission exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "resourceId=";
			msg += resourceId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPermissionException(msg);
		}
		else {
			return (Permission)list.get(0);
		}
	}

	public Permission findByResourceId_Last(String resourceId,
		OrderByComparator obc)
		throws NoSuchPermissionException, SystemException {
		int count = countByResourceId(resourceId);
		List list = findByResourceId(resourceId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Permission exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "resourceId=";
			msg += resourceId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchPermissionException(msg);
		}
		else {
			return (Permission)list.get(0);
		}
	}

	public Permission[] findByResourceId_PrevAndNext(String permissionId,
		String resourceId, OrderByComparator obc)
		throws NoSuchPermissionException, SystemException {
		Permission permission = findByPrimaryKey(permissionId);
		int count = countByResourceId(resourceId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Permission WHERE ");

			if (resourceId == null) {
				query.append("resourceId IS NULL");
			}
			else {
				query.append("resourceId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (resourceId != null) {
				q.setString(queryPos++, resourceId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					permission);
			Permission[] array = new Permission[3];
			array[0] = (Permission)objArray[0];
			array[1] = (Permission)objArray[1];
			array[2] = (Permission)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Permission findByA_R(String actionId, String resourceId)
		throws NoSuchPermissionException, SystemException {
		Permission permission = fetchByA_R(actionId, resourceId);

		if (permission == null) {
			String msg = "No Permission exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "actionId=";
			msg += actionId;
			msg += ", ";
			msg += "resourceId=";
			msg += resourceId;
			msg += StringPool.CLOSE_CURLY_BRACE;

			if (_log.isWarnEnabled()) {
				_log.warn(msg);
			}

			throw new NoSuchPermissionException(msg);
		}

		return permission;
	}

	public Permission fetchByA_R(String actionId, String resourceId)
		throws SystemException {
		String pk = PermissionPool.getByA_R(actionId, resourceId);

		if (pk != null) {
			Permission permission = fetchByPrimaryKey(pk);

			if (permission != null) {
				return permission;
			}
		}

		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Permission WHERE ");

			if (actionId == null) {
				query.append("actionId IS NULL");
			}
			else {
				query.append("actionId = ?");
			}

			query.append(" AND ");

			if (resourceId == null) {
				query.append("resourceId IS NULL");
			}
			else {
				query.append("resourceId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (actionId != null) {
				q.setString(queryPos++, actionId);
			}

			if (resourceId != null) {
				q.setString(queryPos++, resourceId);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			Permission permission = (Permission)list.get(0);
			PermissionPool.putByA_R(actionId, resourceId,
				permission.getPrimaryKey());

			return permission;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Permission ");

			Query q = session.createQuery(query.toString());

			return q.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByResourceId(String resourceId) throws SystemException {
		Iterator itr = findByResourceId(resourceId).iterator();

		while (itr.hasNext()) {
			Permission permission = (Permission)itr.next();
			remove(permission);
		}
	}

	public void removeByA_R(String actionId, String resourceId)
		throws NoSuchPermissionException, SystemException {
		Permission permission = findByA_R(actionId, resourceId);
		remove(permission);
	}

	public int countByResourceId(String resourceId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Permission WHERE ");

			if (resourceId == null) {
				query.append("resourceId IS NULL");
			}
			else {
				query.append("resourceId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (resourceId != null) {
				q.setString(queryPos++, resourceId);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByA_R(String actionId, String resourceId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Permission WHERE ");

			if (actionId == null) {
				query.append("actionId IS NULL");
			}
			else {
				query.append("actionId = ?");
			}

			query.append(" AND ");

			if (resourceId == null) {
				query.append("resourceId IS NULL");
			}
			else {
				query.append("resourceId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (actionId != null) {
				q.setString(queryPos++, actionId);
			}

			if (resourceId != null) {
				q.setString(queryPos++, resourceId);
			}

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List getGroups(String pk)
		throws NoSuchPermissionException, SystemException {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getGroups(String pk, int begin, int end)
		throws NoSuchPermissionException, SystemException {
		return getGroups(pk, begin, end, null);
	}

	public List getGroups(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = _SQL_GETGROUPS;

			if (obc != null) {
				sql += ("ORDER BY " + obc.getOrderBy());
			}
			else {
				sql += "ORDER BY ";
				sql += "Group_.name ASC";
			}

			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("Group_", com.liferay.portal.model.Group.class);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			return QueryUtil.list(q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public int getGroupsSize(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(_SQL_GETGROUPSSIZE);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean containsGroup(String pk, String groupPK)
		throws SystemException {
		try {
			return containsGroup.contains(pk, groupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public boolean containsGroups(String pk) throws SystemException {
		if (getGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addGroup(String pk, String groupPK)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		try {
			addGroup.add(pk, groupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addGroup(String pk, com.liferay.portal.model.Group group)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		try {
			addGroup.add(pk, group.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addGroups(String pk, String[] groupPKs)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		try {
			for (int i = 0; i < groupPKs.length; i++) {
				addGroup.add(pk, groupPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addGroups(String pk, List groups)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		try {
			for (int i = 0; i < groups.size(); i++) {
				com.liferay.portal.model.Group group = (com.liferay.portal.model.Group)groups.get(i);
				addGroup.add(pk, group.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void clearGroups(String pk)
		throws NoSuchPermissionException, SystemException {
		try {
			clearGroups.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeGroup(String pk, String groupPK)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		try {
			removeGroup.remove(pk, groupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeGroup(String pk, com.liferay.portal.model.Group group)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		try {
			removeGroup.remove(pk, group.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeGroups(String pk, String[] groupPKs)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		try {
			for (int i = 0; i < groupPKs.length; i++) {
				removeGroup.remove(pk, groupPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeGroups(String pk, List groups)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		try {
			for (int i = 0; i < groups.size(); i++) {
				com.liferay.portal.model.Group group = (com.liferay.portal.model.Group)groups.get(i);
				removeGroup.remove(pk, group.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setGroups(String pk, String[] groupPKs)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		try {
			clearGroups.clear(pk);

			for (int i = 0; i < groupPKs.length; i++) {
				addGroup.add(pk, groupPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setGroups(String pk, List groups)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		try {
			clearGroups.clear(pk);

			for (int i = 0; i < groups.size(); i++) {
				com.liferay.portal.model.Group group = (com.liferay.portal.model.Group)groups.get(i);
				addGroup.add(pk, group.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public List getRoles(String pk)
		throws NoSuchPermissionException, SystemException {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getRoles(String pk, int begin, int end)
		throws NoSuchPermissionException, SystemException {
		return getRoles(pk, begin, end, null);
	}

	public List getRoles(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = _SQL_GETROLES;

			if (obc != null) {
				sql += ("ORDER BY " + obc.getOrderBy());
			}
			else {
				sql += "ORDER BY ";
				sql += "Role_.name ASC";
			}

			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("Role_", com.liferay.portal.model.Role.class);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			return QueryUtil.list(q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public int getRolesSize(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(_SQL_GETROLESSIZE);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean containsRole(String pk, String rolePK)
		throws SystemException {
		try {
			return containsRole.contains(pk, rolePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public boolean containsRoles(String pk) throws SystemException {
		if (getRolesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addRole(String pk, String rolePK)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		try {
			addRole.add(pk, rolePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addRole(String pk, com.liferay.portal.model.Role role)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		try {
			addRole.add(pk, role.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addRoles(String pk, String[] rolePKs)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		try {
			for (int i = 0; i < rolePKs.length; i++) {
				addRole.add(pk, rolePKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addRoles(String pk, List roles)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		try {
			for (int i = 0; i < roles.size(); i++) {
				com.liferay.portal.model.Role role = (com.liferay.portal.model.Role)roles.get(i);
				addRole.add(pk, role.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void clearRoles(String pk)
		throws NoSuchPermissionException, SystemException {
		try {
			clearRoles.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeRole(String pk, String rolePK)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		try {
			removeRole.remove(pk, rolePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeRole(String pk, com.liferay.portal.model.Role role)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		try {
			removeRole.remove(pk, role.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeRoles(String pk, String[] rolePKs)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		try {
			for (int i = 0; i < rolePKs.length; i++) {
				removeRole.remove(pk, rolePKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeRoles(String pk, List roles)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		try {
			for (int i = 0; i < roles.size(); i++) {
				com.liferay.portal.model.Role role = (com.liferay.portal.model.Role)roles.get(i);
				removeRole.remove(pk, role.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setRoles(String pk, String[] rolePKs)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		try {
			clearRoles.clear(pk);

			for (int i = 0; i < rolePKs.length; i++) {
				addRole.add(pk, rolePKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setRoles(String pk, List roles)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		try {
			clearRoles.clear(pk);

			for (int i = 0; i < roles.size(); i++) {
				com.liferay.portal.model.Role role = (com.liferay.portal.model.Role)roles.get(i);
				addRole.add(pk, role.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public List getUsers(String pk)
		throws NoSuchPermissionException, SystemException {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getUsers(String pk, int begin, int end)
		throws NoSuchPermissionException, SystemException {
		return getUsers(pk, begin, end, null);
	}

	public List getUsers(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = _SQL_GETUSERS;

			if (obc != null) {
				sql += ("ORDER BY " + obc.getOrderBy());
			}

			SQLQuery q = session.createSQLQuery(sql);
			q.addEntity("User_", com.liferay.portal.model.User.class);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			return QueryUtil.list(q, HibernateUtil.getDialect(), begin, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			HibernateUtil.closeSession(session);
		}
	}

	public int getUsersSize(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(_SQL_GETUSERSSIZE);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean containsUser(String pk, String userPK)
		throws SystemException {
		try {
			return containsUser.contains(pk, userPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public boolean containsUsers(String pk) throws SystemException {
		if (getUsersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addUser(String pk, String userPK)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		try {
			addUser.add(pk, userPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addUser(String pk, com.liferay.portal.model.User user)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		try {
			addUser.add(pk, user.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addUsers(String pk, String[] userPKs)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		try {
			for (int i = 0; i < userPKs.length; i++) {
				addUser.add(pk, userPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addUsers(String pk, List users)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		try {
			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.model.User user = (com.liferay.portal.model.User)users.get(i);
				addUser.add(pk, user.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void clearUsers(String pk)
		throws NoSuchPermissionException, SystemException {
		try {
			clearUsers.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUser(String pk, String userPK)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		try {
			removeUser.remove(pk, userPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUser(String pk, com.liferay.portal.model.User user)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		try {
			removeUser.remove(pk, user.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUsers(String pk, String[] userPKs)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		try {
			for (int i = 0; i < userPKs.length; i++) {
				removeUser.remove(pk, userPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUsers(String pk, List users)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		try {
			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.model.User user = (com.liferay.portal.model.User)users.get(i);
				removeUser.remove(pk, user.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setUsers(String pk, String[] userPKs)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		try {
			clearUsers.clear(pk);

			for (int i = 0; i < userPKs.length; i++) {
				addUser.add(pk, userPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setUsers(String pk, List users)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		try {
			clearUsers.clear(pk);

			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.model.User user = (com.liferay.portal.model.User)users.get(i);
				addUser.add(pk, user.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	protected void initDao() {
		containsGroup = new ContainsGroup(this);
		addGroup = new AddGroup(this);
		clearGroups = new ClearGroups(this);
		removeGroup = new RemoveGroup(this);
		containsRole = new ContainsRole(this);
		addRole = new AddRole(this);
		clearRoles = new ClearRoles(this);
		removeRole = new RemoveRole(this);
		containsUser = new ContainsUser(this);
		addUser = new AddUser(this);
		clearUsers = new ClearUsers(this);
		removeUser = new RemoveUser(this);
	}

	protected ContainsGroup containsGroup;
	protected AddGroup addGroup;
	protected ClearGroups clearGroups;
	protected RemoveGroup removeGroup;
	protected ContainsRole containsRole;
	protected AddRole addRole;
	protected ClearRoles clearRoles;
	protected RemoveRole removeRole;
	protected ContainsUser containsUser;
	protected AddUser addUser;
	protected ClearUsers clearUsers;
	protected RemoveUser removeUser;

	protected class ContainsGroup extends MappingSqlQuery {
		protected ContainsGroup(PermissionPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSGROUP);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(String permissionId, String groupId) {
			List results = execute(new Object[] { permissionId, groupId });

			if (results.size() > 0) {
				Integer count = (Integer)results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddGroup extends SqlUpdate {
		protected AddGroup(PermissionPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO Groups_Permissions (permissionId, groupId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void add(String permissionId, String groupId) {
			if (!_persistence.containsGroup.contains(permissionId, groupId)) {
				update(new Object[] { permissionId, groupId });
			}
		}

		private PermissionPersistence _persistence;
	}

	protected class ClearGroups extends SqlUpdate {
		protected ClearGroups(PermissionPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Groups_Permissions WHERE permissionId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void clear(String permissionId) {
			update(new Object[] { permissionId });
		}
	}

	protected class RemoveGroup extends SqlUpdate {
		protected RemoveGroup(PermissionPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Groups_Permissions WHERE permissionId = ? AND groupId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void remove(String permissionId, String groupId) {
			update(new Object[] { permissionId, groupId });
		}
	}

	protected class ContainsRole extends MappingSqlQuery {
		protected ContainsRole(PermissionPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSROLE);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(String permissionId, String roleId) {
			List results = execute(new Object[] { permissionId, roleId });

			if (results.size() > 0) {
				Integer count = (Integer)results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddRole extends SqlUpdate {
		protected AddRole(PermissionPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO Roles_Permissions (permissionId, roleId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void add(String permissionId, String roleId) {
			if (!_persistence.containsRole.contains(permissionId, roleId)) {
				update(new Object[] { permissionId, roleId });
			}
		}

		private PermissionPersistence _persistence;
	}

	protected class ClearRoles extends SqlUpdate {
		protected ClearRoles(PermissionPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Roles_Permissions WHERE permissionId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void clear(String permissionId) {
			update(new Object[] { permissionId });
		}
	}

	protected class RemoveRole extends SqlUpdate {
		protected RemoveRole(PermissionPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Roles_Permissions WHERE permissionId = ? AND roleId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void remove(String permissionId, String roleId) {
			update(new Object[] { permissionId, roleId });
		}
	}

	protected class ContainsUser extends MappingSqlQuery {
		protected ContainsUser(PermissionPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSUSER);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(String permissionId, String userId) {
			List results = execute(new Object[] { permissionId, userId });

			if (results.size() > 0) {
				Integer count = (Integer)results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddUser extends SqlUpdate {
		protected AddUser(PermissionPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO Users_Permissions (permissionId, userId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void add(String permissionId, String userId) {
			if (!_persistence.containsUser.contains(permissionId, userId)) {
				update(new Object[] { permissionId, userId });
			}
		}

		private PermissionPersistence _persistence;
	}

	protected class ClearUsers extends SqlUpdate {
		protected ClearUsers(PermissionPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_Permissions WHERE permissionId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void clear(String permissionId) {
			update(new Object[] { permissionId });
		}
	}

	protected class RemoveUser extends SqlUpdate {
		protected RemoveUser(PermissionPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_Permissions WHERE permissionId = ? AND userId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void remove(String permissionId, String userId) {
			update(new Object[] { permissionId, userId });
		}
	}

	private static final String _SQL_GETGROUPS = "SELECT {Group_.*} FROM Group_ INNER JOIN Groups_Permissions ON (Groups_Permissions.permissionId = ?) AND (Groups_Permissions.groupId = Group_.groupId)";
	private static final String _SQL_GETGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Permissions WHERE permissionId = ?";
	private static final String _SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Permissions WHERE permissionId = ? AND groupId = ?";
	private static final String _SQL_GETROLES = "SELECT {Role_.*} FROM Role_ INNER JOIN Roles_Permissions ON (Roles_Permissions.permissionId = ?) AND (Roles_Permissions.roleId = Role_.roleId)";
	private static final String _SQL_GETROLESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Roles_Permissions WHERE permissionId = ?";
	private static final String _SQL_CONTAINSROLE = "SELECT COUNT(*) AS COUNT_VALUE FROM Roles_Permissions WHERE permissionId = ? AND roleId = ?";
	private static final String _SQL_GETUSERS = "SELECT {User_.*} FROM User_ INNER JOIN Users_Permissions ON (Users_Permissions.permissionId = ?) AND (Users_Permissions.userId = User_.userId)";
	private static final String _SQL_GETUSERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE permissionId = ?";
	private static final String _SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE permissionId = ? AND userId = ?";
	private static Log _log = LogFactory.getLog(PermissionPersistence.class);
}