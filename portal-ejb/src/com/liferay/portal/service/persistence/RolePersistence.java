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

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.SystemException;
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
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <a href="RolePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class RolePersistence extends BasePersistence {
	public com.liferay.portal.model.Role create(String roleId) {
		RoleHBM roleHBM = new RoleHBM();
		roleHBM.setNew(true);
		roleHBM.setPrimaryKey(roleId);

		return RoleHBMUtil.model(roleHBM);
	}

	public com.liferay.portal.model.Role remove(String roleId)
		throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, roleId);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					roleId.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + roleId.toString());
			}

			session.delete(roleHBM);
			session.flush();

			return RoleHBMUtil.model(roleHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Role update(
		com.liferay.portal.model.Role role) throws SystemException {
		Session session = null;

		try {
			if (role.isNew() || role.isModified()) {
				session = openSession();

				if (role.isNew()) {
					RoleHBM roleHBM = new RoleHBM();
					roleHBM.setRoleId(role.getRoleId());
					roleHBM.setCompanyId(role.getCompanyId());
					roleHBM.setClassName(role.getClassName());
					roleHBM.setClassPK(role.getClassPK());
					roleHBM.setName(role.getName());
					roleHBM.setGroups(new HashSet());
					roleHBM.setPermissions(new HashSet());
					roleHBM.setUsers(new HashSet());
					session.save(roleHBM);
					session.flush();
				}
				else {
					RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class,
							role.getPrimaryKey());

					if (roleHBM != null) {
						roleHBM.setCompanyId(role.getCompanyId());
						roleHBM.setClassName(role.getClassName());
						roleHBM.setClassPK(role.getClassPK());
						roleHBM.setName(role.getName());
						session.flush();
					}
					else {
						roleHBM = new RoleHBM();
						roleHBM.setRoleId(role.getRoleId());
						roleHBM.setCompanyId(role.getCompanyId());
						roleHBM.setClassName(role.getClassName());
						roleHBM.setClassPK(role.getClassPK());
						roleHBM.setName(role.getName());
						session.save(roleHBM);
						session.flush();
					}
				}

				role.setNew(false);
				role.setModified(false);
			}

			return role;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List getGroups(String pk)
		throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT groupHBM FROM ");
			query.append(com.liferay.portal.service.persistence.RoleHBM.class.getName());
			query.append(" roleHBM ");
			query.append("JOIN roleHBM.groups AS groupHBM ");
			query.append("WHERE roleHBM.roleId = ? ");
			query.append("ORDER BY ");
			query.append("groupHBM.name ASC");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				com.liferay.portal.service.persistence.GroupHBM groupHBM = (com.liferay.portal.service.persistence.GroupHBM)itr.next();
				list.add(com.liferay.portal.service.persistence.GroupHBMUtil.model(
						groupHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List getGroups(String pk, int begin, int end)
		throws NoSuchRoleException, SystemException {
		return getGroups(pk, begin, end, null);
	}

	public List getGroups(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT groupHBM FROM ");
			query.append(com.liferay.portal.service.persistence.RoleHBM.class.getName());
			query.append(" roleHBM ");
			query.append("JOIN roleHBM.groups AS groupHBM ");
			query.append("WHERE roleHBM.roleId = ? ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("groupHBM.name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				com.liferay.portal.service.persistence.GroupHBM groupHBM = (com.liferay.portal.service.persistence.GroupHBM)itr.next();
				list.add(com.liferay.portal.service.persistence.GroupHBMUtil.model(
						groupHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int getGroupsSize(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) FROM ");
			query.append(com.liferay.portal.service.persistence.RoleHBM.class.getName());
			query.append(" roleHBM ");
			query.append("JOIN roleHBM.groups AS groupHBM ");
			query.append("WHERE roleHBM.roleId = ? ");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.iterate();

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

	public void setGroups(String pk, String[] pks)
		throws NoSuchRoleException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			Set groupsSet = new HashSet();

			for (int i = 0; (pks != null) && (i < pks.length); i++) {
				com.liferay.portal.service.persistence.GroupHBM groupHBM = (com.liferay.portal.service.persistence.GroupHBM)session.get(com.liferay.portal.service.persistence.GroupHBM.class,
						pks[i]);

				if (groupHBM == null) {
					_log.warn("No Group exists with the primary key " +
						pks[i].toString());
					throw new com.liferay.portal.NoSuchGroupException(
						"No Group exists with the primary key " +
						pks[i].toString());
				}

				groupsSet.add(groupHBM);
			}

			roleHBM.setGroups(groupsSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void setGroups(String pk, List groups)
		throws NoSuchRoleException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			Set groupsSet = new HashSet();
			Iterator itr = groups.iterator();

			while (itr.hasNext()) {
				com.liferay.portal.model.Group group = (com.liferay.portal.model.Group)itr.next();
				com.liferay.portal.service.persistence.GroupHBM groupHBM = (com.liferay.portal.service.persistence.GroupHBM)session.get(com.liferay.portal.service.persistence.GroupHBM.class,
						group.getPrimaryKey());

				if (groupHBM == null) {
					_log.warn("No Group exists with the primary key " +
						group.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchGroupException(
						"No Group exists with the primary key " +
						group.getPrimaryKey().toString());
				}

				groupsSet.add(groupHBM);
			}

			roleHBM.setGroups(groupsSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addGroup(String pk, String groupPK)
		throws NoSuchRoleException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.GroupHBM groupHBM = null;
			groupHBM = (com.liferay.portal.service.persistence.GroupHBM)session.get(com.liferay.portal.service.persistence.GroupHBM.class,
					groupPK);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					groupPK.toString());
				throw new com.liferay.portal.NoSuchGroupException(
					"No Group exists with the primary key " +
					groupPK.toString());
			}

			boolean value = roleHBM.getGroups().add(groupHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addGroup(String pk, com.liferay.portal.model.Group group)
		throws NoSuchRoleException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.GroupHBM groupHBM = null;
			groupHBM = (com.liferay.portal.service.persistence.GroupHBM)session.get(com.liferay.portal.service.persistence.GroupHBM.class,
					group.getPrimaryKey());

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					group.getPrimaryKey().toString());
				throw new com.liferay.portal.NoSuchGroupException(
					"No Group exists with the primary key " +
					group.getPrimaryKey().toString());
			}

			boolean value = roleHBM.getGroups().add(groupHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addGroups(String pk, String[] groupPKs)
		throws NoSuchRoleException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < groupPKs.length; i++) {
				com.liferay.portal.service.persistence.GroupHBM groupHBM = null;
				groupHBM = (com.liferay.portal.service.persistence.GroupHBM)session.get(com.liferay.portal.service.persistence.GroupHBM.class,
						groupPKs[i]);

				if (groupHBM == null) {
					_log.warn("No Group exists with the primary key " +
						groupPKs[i].toString());
					throw new com.liferay.portal.NoSuchGroupException(
						"No Group exists with the primary key " +
						groupPKs[i].toString());
				}

				if (roleHBM.getGroups().add(groupHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addGroups(String pk, List groups)
		throws NoSuchRoleException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < groups.size(); i++) {
				com.liferay.portal.model.Group group = (com.liferay.portal.model.Group)groups.get(i);
				com.liferay.portal.service.persistence.GroupHBM groupHBM = (com.liferay.portal.service.persistence.GroupHBM)session.get(com.liferay.portal.service.persistence.GroupHBM.class,
						group.getPrimaryKey());

				if (groupHBM == null) {
					_log.warn("No Group exists with the primary key " +
						group.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchGroupException(
						"No Group exists with the primary key " +
						group.getPrimaryKey().toString());
				}

				if (roleHBM.getGroups().add(groupHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void clearGroups(String pk)
		throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			roleHBM.getGroups().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Roles WHERE roleId = ? AND groupId = ?";

	public boolean containsGroup(String pk, String groupPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSGROUP);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);
			qPos.add(groupPK);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if ((count != null) && (count.intValue() > 0)) {
					return true;
				}
			}

			return false;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSGROUPS = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Roles WHERE roleId = ?";

	public boolean containsGroups(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSGROUPS);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if ((count != null) && (count.intValue() > 0)) {
					return true;
				}
			}

			return false;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeGroup(String pk, String groupPK)
		throws NoSuchRoleException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.GroupHBM groupHBM = null;
			groupHBM = (com.liferay.portal.service.persistence.GroupHBM)session.get(com.liferay.portal.service.persistence.GroupHBM.class,
					groupPK);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					groupPK.toString());
				throw new com.liferay.portal.NoSuchGroupException(
					"No Group exists with the primary key " +
					groupPK.toString());
			}

			boolean value = roleHBM.getGroups().remove(groupHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeGroup(String pk, com.liferay.portal.model.Group group)
		throws NoSuchRoleException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.GroupHBM groupHBM = null;
			groupHBM = (com.liferay.portal.service.persistence.GroupHBM)session.get(com.liferay.portal.service.persistence.GroupHBM.class,
					group.getPrimaryKey());

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					group.getPrimaryKey().toString());
				throw new com.liferay.portal.NoSuchGroupException(
					"No Group exists with the primary key " +
					group.getPrimaryKey().toString());
			}

			boolean value = roleHBM.getGroups().remove(groupHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeGroups(String pk, String[] groupPKs)
		throws NoSuchRoleException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < groupPKs.length; i++) {
				com.liferay.portal.service.persistence.GroupHBM groupHBM = null;
				groupHBM = (com.liferay.portal.service.persistence.GroupHBM)session.get(com.liferay.portal.service.persistence.GroupHBM.class,
						groupPKs[i]);

				if (groupHBM == null) {
					_log.warn("No Group exists with the primary key " +
						groupPKs[i].toString());
					throw new com.liferay.portal.NoSuchGroupException(
						"No Group exists with the primary key " +
						groupPKs[i].toString());
				}

				if (roleHBM.getGroups().remove(groupHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeGroups(String pk, List groups)
		throws NoSuchRoleException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < groups.size(); i++) {
				com.liferay.portal.model.Group group = (com.liferay.portal.model.Group)groups.get(i);
				com.liferay.portal.service.persistence.GroupHBM groupHBM = (com.liferay.portal.service.persistence.GroupHBM)session.get(com.liferay.portal.service.persistence.GroupHBM.class,
						group.getPrimaryKey());

				if (groupHBM == null) {
					_log.warn("No Group exists with the primary key " +
						group.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchGroupException(
						"No Group exists with the primary key " +
						group.getPrimaryKey().toString());
				}

				if (roleHBM.getGroups().remove(groupHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List getPermissions(String pk)
		throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT permissionHBM FROM ");
			query.append(com.liferay.portal.service.persistence.RoleHBM.class.getName());
			query.append(" roleHBM ");
			query.append("JOIN roleHBM.permissions AS permissionHBM ");
			query.append("WHERE roleHBM.roleId = ? ");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				com.liferay.portal.service.persistence.PermissionHBM permissionHBM =
					(com.liferay.portal.service.persistence.PermissionHBM)itr.next();
				list.add(com.liferay.portal.service.persistence.PermissionHBMUtil.model(
						permissionHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List getPermissions(String pk, int begin, int end)
		throws NoSuchRoleException, SystemException {
		return getPermissions(pk, begin, end, null);
	}

	public List getPermissions(String pk, int begin, int end,
		OrderByComparator obc) throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT permissionHBM FROM ");
			query.append(com.liferay.portal.service.persistence.RoleHBM.class.getName());
			query.append(" roleHBM ");
			query.append("JOIN roleHBM.permissions AS permissionHBM ");
			query.append("WHERE roleHBM.roleId = ? ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				com.liferay.portal.service.persistence.PermissionHBM permissionHBM =
					(com.liferay.portal.service.persistence.PermissionHBM)itr.next();
				list.add(com.liferay.portal.service.persistence.PermissionHBMUtil.model(
						permissionHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int getPermissionsSize(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) FROM ");
			query.append(com.liferay.portal.service.persistence.RoleHBM.class.getName());
			query.append(" roleHBM ");
			query.append("JOIN roleHBM.permissions AS permissionHBM ");
			query.append("WHERE roleHBM.roleId = ? ");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.iterate();

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

	public void setPermissions(String pk, String[] pks)
		throws NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			Set permissionsSet = new HashSet();

			for (int i = 0; (pks != null) && (i < pks.length); i++) {
				com.liferay.portal.service.persistence.PermissionHBM permissionHBM =
					(com.liferay.portal.service.persistence.PermissionHBM)session.get(com.liferay.portal.service.persistence.PermissionHBM.class,
						pks[i]);

				if (permissionHBM == null) {
					_log.warn("No Permission exists with the primary key " +
						pks[i].toString());
					throw new com.liferay.portal.NoSuchPermissionException(
						"No Permission exists with the primary key " +
						pks[i].toString());
				}

				permissionsSet.add(permissionHBM);
			}

			roleHBM.setPermissions(permissionsSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void setPermissions(String pk, List permissions)
		throws NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			Set permissionsSet = new HashSet();
			Iterator itr = permissions.iterator();

			while (itr.hasNext()) {
				com.liferay.portal.model.Permission permission = (com.liferay.portal.model.Permission)itr.next();
				com.liferay.portal.service.persistence.PermissionHBM permissionHBM =
					(com.liferay.portal.service.persistence.PermissionHBM)session.get(com.liferay.portal.service.persistence.PermissionHBM.class,
						permission.getPrimaryKey());

				if (permissionHBM == null) {
					_log.warn("No Permission exists with the primary key " +
						permission.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchPermissionException(
						"No Permission exists with the primary key " +
						permission.getPrimaryKey().toString());
				}

				permissionsSet.add(permissionHBM);
			}

			roleHBM.setPermissions(permissionsSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addPermission(String pk, String permissionPK)
		throws NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.PermissionHBM permissionHBM = null;
			permissionHBM = (com.liferay.portal.service.persistence.PermissionHBM)session.get(com.liferay.portal.service.persistence.PermissionHBM.class,
					permissionPK);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					permissionPK.toString());
				throw new com.liferay.portal.NoSuchPermissionException(
					"No Permission exists with the primary key " +
					permissionPK.toString());
			}

			boolean value = roleHBM.getPermissions().add(permissionHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addPermission(String pk,
		com.liferay.portal.model.Permission permission)
		throws NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.PermissionHBM permissionHBM = null;
			permissionHBM = (com.liferay.portal.service.persistence.PermissionHBM)session.get(com.liferay.portal.service.persistence.PermissionHBM.class,
					permission.getPrimaryKey());

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					permission.getPrimaryKey().toString());
				throw new com.liferay.portal.NoSuchPermissionException(
					"No Permission exists with the primary key " +
					permission.getPrimaryKey().toString());
			}

			boolean value = roleHBM.getPermissions().add(permissionHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addPermissions(String pk, String[] permissionPKs)
		throws NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < permissionPKs.length; i++) {
				com.liferay.portal.service.persistence.PermissionHBM permissionHBM =
					null;
				permissionHBM = (com.liferay.portal.service.persistence.PermissionHBM)session.get(com.liferay.portal.service.persistence.PermissionHBM.class,
						permissionPKs[i]);

				if (permissionHBM == null) {
					_log.warn("No Permission exists with the primary key " +
						permissionPKs[i].toString());
					throw new com.liferay.portal.NoSuchPermissionException(
						"No Permission exists with the primary key " +
						permissionPKs[i].toString());
				}

				if (roleHBM.getPermissions().add(permissionHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addPermissions(String pk, List permissions)
		throws NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < permissions.size(); i++) {
				com.liferay.portal.model.Permission permission = (com.liferay.portal.model.Permission)permissions.get(i);
				com.liferay.portal.service.persistence.PermissionHBM permissionHBM =
					(com.liferay.portal.service.persistence.PermissionHBM)session.get(com.liferay.portal.service.persistence.PermissionHBM.class,
						permission.getPrimaryKey());

				if (permissionHBM == null) {
					_log.warn("No Permission exists with the primary key " +
						permission.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchPermissionException(
						"No Permission exists with the primary key " +
						permission.getPrimaryKey().toString());
				}

				if (roleHBM.getPermissions().add(permissionHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void clearPermissions(String pk)
		throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			roleHBM.getPermissions().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSPERMISSION = "SELECT COUNT(*) AS COUNT_VALUE FROM Roles_Permissions WHERE roleId = ? AND permissionId = ?";

	public boolean containsPermission(String pk, String permissionPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSPERMISSION);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);
			qPos.add(permissionPK);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if ((count != null) && (count.intValue() > 0)) {
					return true;
				}
			}

			return false;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSPERMISSIONS = "SELECT COUNT(*) AS COUNT_VALUE FROM Roles_Permissions WHERE roleId = ?";

	public boolean containsPermissions(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSPERMISSIONS);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if ((count != null) && (count.intValue() > 0)) {
					return true;
				}
			}

			return false;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removePermission(String pk, String permissionPK)
		throws NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.PermissionHBM permissionHBM = null;
			permissionHBM = (com.liferay.portal.service.persistence.PermissionHBM)session.get(com.liferay.portal.service.persistence.PermissionHBM.class,
					permissionPK);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					permissionPK.toString());
				throw new com.liferay.portal.NoSuchPermissionException(
					"No Permission exists with the primary key " +
					permissionPK.toString());
			}

			boolean value = roleHBM.getPermissions().remove(permissionHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removePermission(String pk,
		com.liferay.portal.model.Permission permission)
		throws NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.PermissionHBM permissionHBM = null;
			permissionHBM = (com.liferay.portal.service.persistence.PermissionHBM)session.get(com.liferay.portal.service.persistence.PermissionHBM.class,
					permission.getPrimaryKey());

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					permission.getPrimaryKey().toString());
				throw new com.liferay.portal.NoSuchPermissionException(
					"No Permission exists with the primary key " +
					permission.getPrimaryKey().toString());
			}

			boolean value = roleHBM.getPermissions().remove(permissionHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removePermissions(String pk, String[] permissionPKs)
		throws NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < permissionPKs.length; i++) {
				com.liferay.portal.service.persistence.PermissionHBM permissionHBM =
					null;
				permissionHBM = (com.liferay.portal.service.persistence.PermissionHBM)session.get(com.liferay.portal.service.persistence.PermissionHBM.class,
						permissionPKs[i]);

				if (permissionHBM == null) {
					_log.warn("No Permission exists with the primary key " +
						permissionPKs[i].toString());
					throw new com.liferay.portal.NoSuchPermissionException(
						"No Permission exists with the primary key " +
						permissionPKs[i].toString());
				}

				if (roleHBM.getPermissions().remove(permissionHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removePermissions(String pk, List permissions)
		throws NoSuchRoleException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < permissions.size(); i++) {
				com.liferay.portal.model.Permission permission = (com.liferay.portal.model.Permission)permissions.get(i);
				com.liferay.portal.service.persistence.PermissionHBM permissionHBM =
					(com.liferay.portal.service.persistence.PermissionHBM)session.get(com.liferay.portal.service.persistence.PermissionHBM.class,
						permission.getPrimaryKey());

				if (permissionHBM == null) {
					_log.warn("No Permission exists with the primary key " +
						permission.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchPermissionException(
						"No Permission exists with the primary key " +
						permission.getPrimaryKey().toString());
				}

				if (roleHBM.getPermissions().remove(permissionHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List getUsers(String pk) throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT userHBM FROM ");
			query.append(com.liferay.portal.service.persistence.RoleHBM.class.getName());
			query.append(" roleHBM ");
			query.append("JOIN roleHBM.users AS userHBM ");
			query.append("WHERE roleHBM.roleId = ? ");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				com.liferay.portal.service.persistence.UserHBM userHBM = (com.liferay.portal.service.persistence.UserHBM)itr.next();
				list.add(com.liferay.portal.service.persistence.UserHBMUtil.model(
						userHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List getUsers(String pk, int begin, int end)
		throws NoSuchRoleException, SystemException {
		return getUsers(pk, begin, end, null);
	}

	public List getUsers(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT userHBM FROM ");
			query.append(com.liferay.portal.service.persistence.RoleHBM.class.getName());
			query.append(" roleHBM ");
			query.append("JOIN roleHBM.users AS userHBM ");
			query.append("WHERE roleHBM.roleId = ? ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				com.liferay.portal.service.persistence.UserHBM userHBM = (com.liferay.portal.service.persistence.UserHBM)itr.next();
				list.add(com.liferay.portal.service.persistence.UserHBMUtil.model(
						userHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public int getUsersSize(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) FROM ");
			query.append(com.liferay.portal.service.persistence.RoleHBM.class.getName());
			query.append(" roleHBM ");
			query.append("JOIN roleHBM.users AS userHBM ");
			query.append("WHERE roleHBM.roleId = ? ");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.iterate();

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

	public void setUsers(String pk, String[] pks)
		throws NoSuchRoleException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			Set usersSet = new HashSet();

			for (int i = 0; (pks != null) && (i < pks.length); i++) {
				com.liferay.portal.service.persistence.UserHBM userHBM = (com.liferay.portal.service.persistence.UserHBM)session.get(com.liferay.portal.service.persistence.UserHBM.class,
						pks[i]);

				if (userHBM == null) {
					_log.warn("No User exists with the primary key " +
						pks[i].toString());
					throw new com.liferay.portal.NoSuchUserException(
						"No User exists with the primary key " +
						pks[i].toString());
				}

				usersSet.add(userHBM);
			}

			roleHBM.setUsers(usersSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void setUsers(String pk, List users)
		throws NoSuchRoleException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			Set usersSet = new HashSet();
			Iterator itr = users.iterator();

			while (itr.hasNext()) {
				com.liferay.portal.model.User user = (com.liferay.portal.model.User)itr.next();
				com.liferay.portal.service.persistence.UserHBM userHBM = (com.liferay.portal.service.persistence.UserHBM)session.get(com.liferay.portal.service.persistence.UserHBM.class,
						user.getPrimaryKey());

				if (userHBM == null) {
					_log.warn("No User exists with the primary key " +
						user.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchUserException(
						"No User exists with the primary key " +
						user.getPrimaryKey().toString());
				}

				usersSet.add(userHBM);
			}

			roleHBM.setUsers(usersSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addUser(String pk, String userPK)
		throws NoSuchRoleException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.UserHBM userHBM = null;
			userHBM = (com.liferay.portal.service.persistence.UserHBM)session.get(com.liferay.portal.service.persistence.UserHBM.class,
					userPK);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					userPK.toString());
				throw new com.liferay.portal.NoSuchUserException(
					"No User exists with the primary key " + userPK.toString());
			}

			boolean value = roleHBM.getUsers().add(userHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addUser(String pk, com.liferay.portal.model.User user)
		throws NoSuchRoleException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.UserHBM userHBM = null;
			userHBM = (com.liferay.portal.service.persistence.UserHBM)session.get(com.liferay.portal.service.persistence.UserHBM.class,
					user.getPrimaryKey());

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					user.getPrimaryKey().toString());
				throw new com.liferay.portal.NoSuchUserException(
					"No User exists with the primary key " +
					user.getPrimaryKey().toString());
			}

			boolean value = roleHBM.getUsers().add(userHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addUsers(String pk, String[] userPKs)
		throws NoSuchRoleException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < userPKs.length; i++) {
				com.liferay.portal.service.persistence.UserHBM userHBM = null;
				userHBM = (com.liferay.portal.service.persistence.UserHBM)session.get(com.liferay.portal.service.persistence.UserHBM.class,
						userPKs[i]);

				if (userHBM == null) {
					_log.warn("No User exists with the primary key " +
						userPKs[i].toString());
					throw new com.liferay.portal.NoSuchUserException(
						"No User exists with the primary key " +
						userPKs[i].toString());
				}

				if (roleHBM.getUsers().add(userHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addUsers(String pk, List users)
		throws NoSuchRoleException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.model.User user = (com.liferay.portal.model.User)users.get(i);
				com.liferay.portal.service.persistence.UserHBM userHBM = (com.liferay.portal.service.persistence.UserHBM)session.get(com.liferay.portal.service.persistence.UserHBM.class,
						user.getPrimaryKey());

				if (userHBM == null) {
					_log.warn("No User exists with the primary key " +
						user.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchUserException(
						"No User exists with the primary key " +
						user.getPrimaryKey().toString());
				}

				if (roleHBM.getUsers().add(userHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void clearUsers(String pk)
		throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			roleHBM.getUsers().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE roleId = ? AND userId = ?";

	public boolean containsUser(String pk, String userPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSUSER);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);
			qPos.add(userPK);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if ((count != null) && (count.intValue() > 0)) {
					return true;
				}
			}

			return false;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSUSERS = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE roleId = ?";

	public boolean containsUsers(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSUSERS);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Long count = (Long)itr.next();

				if ((count != null) && (count.intValue() > 0)) {
					return true;
				}
			}

			return false;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeUser(String pk, String userPK)
		throws NoSuchRoleException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.UserHBM userHBM = null;
			userHBM = (com.liferay.portal.service.persistence.UserHBM)session.get(com.liferay.portal.service.persistence.UserHBM.class,
					userPK);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					userPK.toString());
				throw new com.liferay.portal.NoSuchUserException(
					"No User exists with the primary key " + userPK.toString());
			}

			boolean value = roleHBM.getUsers().remove(userHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeUser(String pk, com.liferay.portal.model.User user)
		throws NoSuchRoleException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.UserHBM userHBM = null;
			userHBM = (com.liferay.portal.service.persistence.UserHBM)session.get(com.liferay.portal.service.persistence.UserHBM.class,
					user.getPrimaryKey());

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					user.getPrimaryKey().toString());
				throw new com.liferay.portal.NoSuchUserException(
					"No User exists with the primary key " +
					user.getPrimaryKey().toString());
			}

			boolean value = roleHBM.getUsers().remove(userHBM);
			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeUsers(String pk, String[] userPKs)
		throws NoSuchRoleException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < userPKs.length; i++) {
				com.liferay.portal.service.persistence.UserHBM userHBM = null;
				userHBM = (com.liferay.portal.service.persistence.UserHBM)session.get(com.liferay.portal.service.persistence.UserHBM.class,
						userPKs[i]);

				if (userHBM == null) {
					_log.warn("No User exists with the primary key " +
						userPKs[i].toString());
					throw new com.liferay.portal.NoSuchUserException(
						"No User exists with the primary key " +
						userPKs[i].toString());
				}

				if (roleHBM.getUsers().remove(userHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean removeUsers(String pk, List users)
		throws NoSuchRoleException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, pk);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					pk.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.model.User user = (com.liferay.portal.model.User)users.get(i);
				com.liferay.portal.service.persistence.UserHBM userHBM = (com.liferay.portal.service.persistence.UserHBM)session.get(com.liferay.portal.service.persistence.UserHBM.class,
						user.getPrimaryKey());

				if (userHBM == null) {
					_log.warn("No User exists with the primary key " +
						user.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchUserException(
						"No User exists with the primary key " +
						user.getPrimaryKey().toString());
				}

				if (roleHBM.getUsers().remove(userHBM)) {
					value = true;
				}
			}

			session.flush();

			return value;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Role findByPrimaryKey(String roleId)
		throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RoleHBM roleHBM = (RoleHBM)session.get(RoleHBM.class, roleId);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					roleId.toString());
				throw new NoSuchRoleException(
					"No Role exists with the primary key " + roleId.toString());
			}

			return RoleHBMUtil.model(roleHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Role_ IN CLASS com.liferay.portal.service.persistence.RoleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				RoleHBM roleHBM = (RoleHBM)itr.next();
				list.add(RoleHBMUtil.model(roleHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByCompanyId(String companyId, int begin, int end)
		throws SystemException {
		return findByCompanyId(companyId, begin, end, null);
	}

	public List findByCompanyId(String companyId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Role_ IN CLASS com.liferay.portal.service.persistence.RoleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				RoleHBM roleHBM = (RoleHBM)itr.next();
				list.add(RoleHBMUtil.model(roleHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Role findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchRoleException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Role exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchRoleException(msg);
		}
		else {
			return (com.liferay.portal.model.Role)list.get(0);
		}
	}

	public com.liferay.portal.model.Role findByCompanyId_Last(
		String companyId, OrderByComparator obc)
		throws NoSuchRoleException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Role exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchRoleException(msg);
		}
		else {
			return (com.liferay.portal.model.Role)list.get(0);
		}
	}

	public com.liferay.portal.model.Role[] findByCompanyId_PrevAndNext(
		String roleId, String companyId, OrderByComparator obc)
		throws NoSuchRoleException, SystemException {
		com.liferay.portal.model.Role role = findByPrimaryKey(roleId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Role_ IN CLASS com.liferay.portal.service.persistence.RoleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, role,
					RoleHBMUtil.getInstance());
			com.liferay.portal.model.Role[] array = new com.liferay.portal.model.Role[3];
			array[0] = (com.liferay.portal.model.Role)objArray[0];
			array[1] = (com.liferay.portal.model.Role)objArray[1];
			array[2] = (com.liferay.portal.model.Role)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Role findByC_C_C(String companyId,
		String className, String classPK)
		throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Role_ IN CLASS com.liferay.portal.service.persistence.RoleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			Iterator itr = q.list().iterator();

			if (!itr.hasNext()) {
				String msg = "No Role exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "companyId=";
				msg += companyId;
				msg += ", ";
				msg += "className=";
				msg += className;
				msg += ", ";
				msg += "classPK=";
				msg += classPK;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchRoleException(msg);
			}

			RoleHBM roleHBM = (RoleHBM)itr.next();

			return RoleHBMUtil.model(roleHBM);
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
			query.append(
				"FROM Role_ IN CLASS com.liferay.portal.service.persistence.RoleHBM ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				RoleHBM roleHBM = (RoleHBM)itr.next();
				list.add(RoleHBMUtil.model(roleHBM));
			}

			return list;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Role_ IN CLASS com.liferay.portal.service.persistence.RoleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				RoleHBM roleHBM = (RoleHBM)itr.next();
				session.delete(roleHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByC_C_C(String companyId, String className, String classPK)
		throws NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Role_ IN CLASS com.liferay.portal.service.persistence.RoleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				RoleHBM roleHBM = (RoleHBM)itr.next();
				session.delete(roleHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
				String msg = "No Role exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "companyId=";
				msg += companyId;
				msg += ", ";
				msg += "className=";
				msg += className;
				msg += ", ";
				msg += "classPK=";
				msg += classPK;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchRoleException(msg);
			}
			else {
				throw new SystemException(he);
			}
		}
		finally {
			closeSession(session);
		}
	}

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Role_ IN CLASS com.liferay.portal.service.persistence.RoleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

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

	public int countByC_C_C(String companyId, String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Role_ IN CLASS com.liferay.portal.service.persistence.RoleHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("className = ?");
			query.append(" AND ");
			query.append("classPK = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, className);
			q.setString(queryPos++, classPK);

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

	private static Log _log = LogFactory.getLog(RolePersistence.class);
}