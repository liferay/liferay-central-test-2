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
 * <a href="PermissionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PermissionPersistence extends BasePersistence {
	public com.liferay.portal.model.Permission create(String permissionId) {
		PermissionHBM permissionHBM = new PermissionHBM();
		permissionHBM.setNew(true);
		permissionHBM.setPrimaryKey(permissionId);

		return PermissionHBMUtil.model(permissionHBM);
	}

	public com.liferay.portal.model.Permission remove(String permissionId)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					permissionId);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					permissionId.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					permissionId.toString());
			}

			session.delete(permissionHBM);
			session.flush();

			return PermissionHBMUtil.model(permissionHBM);
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
			if (permission.isNew() || permission.isModified()) {
				session = openSession();

				if (permission.isNew()) {
					PermissionHBM permissionHBM = new PermissionHBM();
					permissionHBM.setPermissionId(permission.getPermissionId());
					permissionHBM.setCompanyId(permission.getCompanyId());
					permissionHBM.setActionId(permission.getActionId());
					permissionHBM.setResourceId(permission.getResourceId());
					permissionHBM.setGroups(new HashSet());
					permissionHBM.setRoles(new HashSet());
					permissionHBM.setUsers(new HashSet());
					session.save(permissionHBM);
					session.flush();
				}
				else {
					PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
							permission.getPrimaryKey());

					if (permissionHBM != null) {
						permissionHBM.setCompanyId(permission.getCompanyId());
						permissionHBM.setActionId(permission.getActionId());
						permissionHBM.setResourceId(permission.getResourceId());
						session.flush();
					}
					else {
						permissionHBM = new PermissionHBM();
						permissionHBM.setPermissionId(permission.getPermissionId());
						permissionHBM.setCompanyId(permission.getCompanyId());
						permissionHBM.setActionId(permission.getActionId());
						permissionHBM.setResourceId(permission.getResourceId());
						session.save(permissionHBM);
						session.flush();
					}
				}

				permission.setNew(false);
				permission.setModified(false);
			}

			return permission;
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
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT groupHBM FROM ");
			query.append(com.liferay.portal.service.persistence.PermissionHBM.class.getName());
			query.append(" permissionHBM ");
			query.append("JOIN permissionHBM.groups AS groupHBM ");
			query.append("WHERE permissionHBM.permissionId = ? ");
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
		throws NoSuchPermissionException, SystemException {
		return getGroups(pk, begin, end, null);
	}

	public List getGroups(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT groupHBM FROM ");
			query.append(com.liferay.portal.service.persistence.PermissionHBM.class.getName());
			query.append(" permissionHBM ");
			query.append("JOIN permissionHBM.groups AS groupHBM ");
			query.append("WHERE permissionHBM.permissionId = ? ");

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
			query.append(com.liferay.portal.service.persistence.PermissionHBM.class.getName());
			query.append(" permissionHBM ");
			query.append("JOIN permissionHBM.groups AS groupHBM ");
			query.append("WHERE permissionHBM.permissionId = ? ");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.iterate();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

			permissionHBM.setGroups(groupsSet);
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

			permissionHBM.setGroups(groupsSet);
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

			boolean value = permissionHBM.getGroups().add(groupHBM);
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

			boolean value = permissionHBM.getGroups().add(groupHBM);
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

				if (permissionHBM.getGroups().add(groupHBM)) {
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

				if (permissionHBM.getGroups().add(groupHBM)) {
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
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			permissionHBM.getGroups().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Permissions WHERE permissionId = ? AND groupId = ?";

	public boolean containsGroup(String pk, String groupPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSGROUP);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.INTEGER);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);
			qPos.add(groupPK);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	public static final String SQL_CONTAINSGROUPS = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Permissions WHERE permissionId = ?";

	public boolean containsGroups(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSGROUPS);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.INTEGER);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

			boolean value = permissionHBM.getGroups().remove(groupHBM);
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

			boolean value = permissionHBM.getGroups().remove(groupHBM);
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

				if (permissionHBM.getGroups().remove(groupHBM)) {
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

				if (permissionHBM.getGroups().remove(groupHBM)) {
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

	public List getRoles(String pk)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT roleHBM FROM ");
			query.append(com.liferay.portal.service.persistence.PermissionHBM.class.getName());
			query.append(" permissionHBM ");
			query.append("JOIN permissionHBM.roles AS roleHBM ");
			query.append("WHERE permissionHBM.permissionId = ? ");
			query.append("ORDER BY ");
			query.append("roleHBM.name ASC");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				com.liferay.portal.service.persistence.RoleHBM roleHBM = (com.liferay.portal.service.persistence.RoleHBM)itr.next();
				list.add(com.liferay.portal.service.persistence.RoleHBMUtil.model(
						roleHBM));
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

	public List getRoles(String pk, int begin, int end)
		throws NoSuchPermissionException, SystemException {
		return getRoles(pk, begin, end, null);
	}

	public List getRoles(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT roleHBM FROM ");
			query.append(com.liferay.portal.service.persistence.PermissionHBM.class.getName());
			query.append(" permissionHBM ");
			query.append("JOIN permissionHBM.roles AS roleHBM ");
			query.append("WHERE permissionHBM.permissionId = ? ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("roleHBM.name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				com.liferay.portal.service.persistence.RoleHBM roleHBM = (com.liferay.portal.service.persistence.RoleHBM)itr.next();
				list.add(com.liferay.portal.service.persistence.RoleHBMUtil.model(
						roleHBM));
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

	public int getRolesSize(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) FROM ");
			query.append(com.liferay.portal.service.persistence.PermissionHBM.class.getName());
			query.append(" permissionHBM ");
			query.append("JOIN permissionHBM.roles AS roleHBM ");
			query.append("WHERE permissionHBM.permissionId = ? ");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.iterate();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	public void setRoles(String pk, String[] pks)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			Set rolesSet = new HashSet();

			for (int i = 0; (pks != null) && (i < pks.length); i++) {
				com.liferay.portal.service.persistence.RoleHBM roleHBM = (com.liferay.portal.service.persistence.RoleHBM)session.get(com.liferay.portal.service.persistence.RoleHBM.class,
						pks[i]);

				if (roleHBM == null) {
					_log.warn("No Role exists with the primary key " +
						pks[i].toString());
					throw new com.liferay.portal.NoSuchRoleException(
						"No Role exists with the primary key " +
						pks[i].toString());
				}

				rolesSet.add(roleHBM);
			}

			permissionHBM.setRoles(rolesSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void setRoles(String pk, List roles)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			Set rolesSet = new HashSet();
			Iterator itr = roles.iterator();

			while (itr.hasNext()) {
				com.liferay.portal.model.Role role = (com.liferay.portal.model.Role)itr.next();
				com.liferay.portal.service.persistence.RoleHBM roleHBM = (com.liferay.portal.service.persistence.RoleHBM)session.get(com.liferay.portal.service.persistence.RoleHBM.class,
						role.getPrimaryKey());

				if (roleHBM == null) {
					_log.warn("No Role exists with the primary key " +
						role.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchRoleException(
						"No Role exists with the primary key " +
						role.getPrimaryKey().toString());
				}

				rolesSet.add(roleHBM);
			}

			permissionHBM.setRoles(rolesSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addRole(String pk, String rolePK)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			com.liferay.portal.service.persistence.RoleHBM roleHBM = null;
			roleHBM = (com.liferay.portal.service.persistence.RoleHBM)session.get(com.liferay.portal.service.persistence.RoleHBM.class,
					rolePK);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					rolePK.toString());
				throw new com.liferay.portal.NoSuchRoleException(
					"No Role exists with the primary key " + rolePK.toString());
			}

			boolean value = permissionHBM.getRoles().add(roleHBM);
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

	public boolean addRole(String pk, com.liferay.portal.model.Role role)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			com.liferay.portal.service.persistence.RoleHBM roleHBM = null;
			roleHBM = (com.liferay.portal.service.persistence.RoleHBM)session.get(com.liferay.portal.service.persistence.RoleHBM.class,
					role.getPrimaryKey());

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					role.getPrimaryKey().toString());
				throw new com.liferay.portal.NoSuchRoleException(
					"No Role exists with the primary key " +
					role.getPrimaryKey().toString());
			}

			boolean value = permissionHBM.getRoles().add(roleHBM);
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

	public boolean addRoles(String pk, String[] rolePKs)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < rolePKs.length; i++) {
				com.liferay.portal.service.persistence.RoleHBM roleHBM = null;
				roleHBM = (com.liferay.portal.service.persistence.RoleHBM)session.get(com.liferay.portal.service.persistence.RoleHBM.class,
						rolePKs[i]);

				if (roleHBM == null) {
					_log.warn("No Role exists with the primary key " +
						rolePKs[i].toString());
					throw new com.liferay.portal.NoSuchRoleException(
						"No Role exists with the primary key " +
						rolePKs[i].toString());
				}

				if (permissionHBM.getRoles().add(roleHBM)) {
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

	public boolean addRoles(String pk, List roles)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < roles.size(); i++) {
				com.liferay.portal.model.Role role = (com.liferay.portal.model.Role)roles.get(i);
				com.liferay.portal.service.persistence.RoleHBM roleHBM = (com.liferay.portal.service.persistence.RoleHBM)session.get(com.liferay.portal.service.persistence.RoleHBM.class,
						role.getPrimaryKey());

				if (roleHBM == null) {
					_log.warn("No Role exists with the primary key " +
						role.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchRoleException(
						"No Role exists with the primary key " +
						role.getPrimaryKey().toString());
				}

				if (permissionHBM.getRoles().add(roleHBM)) {
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

	public void clearRoles(String pk)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			permissionHBM.getRoles().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSROLE = "SELECT COUNT(*) AS COUNT_VALUE FROM Roles_Permissions WHERE permissionId = ? AND roleId = ?";

	public boolean containsRole(String pk, String rolePK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSROLE);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.INTEGER);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);
			qPos.add(rolePK);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	public static final String SQL_CONTAINSROLES = "SELECT COUNT(*) AS COUNT_VALUE FROM Roles_Permissions WHERE permissionId = ?";

	public boolean containsRoles(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSROLES);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.INTEGER);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	public boolean removeRole(String pk, String rolePK)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			com.liferay.portal.service.persistence.RoleHBM roleHBM = null;
			roleHBM = (com.liferay.portal.service.persistence.RoleHBM)session.get(com.liferay.portal.service.persistence.RoleHBM.class,
					rolePK);

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					rolePK.toString());
				throw new com.liferay.portal.NoSuchRoleException(
					"No Role exists with the primary key " + rolePK.toString());
			}

			boolean value = permissionHBM.getRoles().remove(roleHBM);
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

	public boolean removeRole(String pk, com.liferay.portal.model.Role role)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			com.liferay.portal.service.persistence.RoleHBM roleHBM = null;
			roleHBM = (com.liferay.portal.service.persistence.RoleHBM)session.get(com.liferay.portal.service.persistence.RoleHBM.class,
					role.getPrimaryKey());

			if (roleHBM == null) {
				_log.warn("No Role exists with the primary key " +
					role.getPrimaryKey().toString());
				throw new com.liferay.portal.NoSuchRoleException(
					"No Role exists with the primary key " +
					role.getPrimaryKey().toString());
			}

			boolean value = permissionHBM.getRoles().remove(roleHBM);
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

	public boolean removeRoles(String pk, String[] rolePKs)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < rolePKs.length; i++) {
				com.liferay.portal.service.persistence.RoleHBM roleHBM = null;
				roleHBM = (com.liferay.portal.service.persistence.RoleHBM)session.get(com.liferay.portal.service.persistence.RoleHBM.class,
						rolePKs[i]);

				if (roleHBM == null) {
					_log.warn("No Role exists with the primary key " +
						rolePKs[i].toString());
					throw new com.liferay.portal.NoSuchRoleException(
						"No Role exists with the primary key " +
						rolePKs[i].toString());
				}

				if (permissionHBM.getRoles().remove(roleHBM)) {
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

	public boolean removeRoles(String pk, List roles)
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchRoleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < roles.size(); i++) {
				com.liferay.portal.model.Role role = (com.liferay.portal.model.Role)roles.get(i);
				com.liferay.portal.service.persistence.RoleHBM roleHBM = (com.liferay.portal.service.persistence.RoleHBM)session.get(com.liferay.portal.service.persistence.RoleHBM.class,
						role.getPrimaryKey());

				if (roleHBM == null) {
					_log.warn("No Role exists with the primary key " +
						role.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchRoleException(
						"No Role exists with the primary key " +
						role.getPrimaryKey().toString());
				}

				if (permissionHBM.getRoles().remove(roleHBM)) {
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

	public List getUsers(String pk)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT userHBM FROM ");
			query.append(com.liferay.portal.service.persistence.PermissionHBM.class.getName());
			query.append(" permissionHBM ");
			query.append("JOIN permissionHBM.users AS userHBM ");
			query.append("WHERE permissionHBM.permissionId = ? ");

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
		throws NoSuchPermissionException, SystemException {
		return getUsers(pk, begin, end, null);
	}

	public List getUsers(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT userHBM FROM ");
			query.append(com.liferay.portal.service.persistence.PermissionHBM.class.getName());
			query.append(" permissionHBM ");
			query.append("JOIN permissionHBM.users AS userHBM ");
			query.append("WHERE permissionHBM.permissionId = ? ");

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
			query.append(com.liferay.portal.service.persistence.PermissionHBM.class.getName());
			query.append(" permissionHBM ");
			query.append("JOIN permissionHBM.users AS userHBM ");
			query.append("WHERE permissionHBM.permissionId = ? ");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.iterate();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

			permissionHBM.setUsers(usersSet);
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

			permissionHBM.setUsers(usersSet);
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

			boolean value = permissionHBM.getUsers().add(userHBM);
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

			boolean value = permissionHBM.getUsers().add(userHBM);
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

				if (permissionHBM.getUsers().add(userHBM)) {
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

				if (permissionHBM.getUsers().add(userHBM)) {
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
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
			}

			permissionHBM.getUsers().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE permissionId = ? AND userId = ?";

	public boolean containsUser(String pk, String userPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSUSER);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.INTEGER);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);
			qPos.add(userPK);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	public static final String SQL_CONTAINSUSERS = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE permissionId = ?";

	public boolean containsUsers(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSUSERS);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.INTEGER);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

			boolean value = permissionHBM.getUsers().remove(userHBM);
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

			boolean value = permissionHBM.getUsers().remove(userHBM);
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

				if (permissionHBM.getUsers().remove(userHBM)) {
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
		throws NoSuchPermissionException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					pk);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					pk.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					pk.toString());
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

				if (permissionHBM.getUsers().remove(userHBM)) {
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

	public com.liferay.portal.model.Permission findByPrimaryKey(
		String permissionId) throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PermissionHBM permissionHBM = (PermissionHBM)session.get(PermissionHBM.class,
					permissionId);

			if (permissionHBM == null) {
				_log.warn("No Permission exists with the primary key " +
					permissionId.toString());
				throw new NoSuchPermissionException(
					"No Permission exists with the primary key " +
					permissionId.toString());
			}

			return PermissionHBMUtil.model(permissionHBM);
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
			query.append(
				"FROM Permission_ IN CLASS com.liferay.portal.service.persistence.PermissionHBM WHERE ");
			query.append("resourceId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, resourceId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				PermissionHBM permissionHBM = (PermissionHBM)itr.next();
				list.add(PermissionHBMUtil.model(permissionHBM));
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
			query.append(
				"FROM Permission_ IN CLASS com.liferay.portal.service.persistence.PermissionHBM WHERE ");
			query.append("resourceId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, resourceId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				PermissionHBM permissionHBM = (PermissionHBM)itr.next();
				list.add(PermissionHBMUtil.model(permissionHBM));
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

	public com.liferay.portal.model.Permission findByResourceId_First(
		String resourceId, OrderByComparator obc)
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
			return (com.liferay.portal.model.Permission)list.get(0);
		}
	}

	public com.liferay.portal.model.Permission findByResourceId_Last(
		String resourceId, OrderByComparator obc)
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
			return (com.liferay.portal.model.Permission)list.get(0);
		}
	}

	public com.liferay.portal.model.Permission[] findByResourceId_PrevAndNext(
		String permissionId, String resourceId, OrderByComparator obc)
		throws NoSuchPermissionException, SystemException {
		com.liferay.portal.model.Permission permission = findByPrimaryKey(permissionId);
		int count = countByResourceId(resourceId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Permission_ IN CLASS com.liferay.portal.service.persistence.PermissionHBM WHERE ");
			query.append("resourceId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, resourceId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					permission, PermissionHBMUtil.getInstance());
			com.liferay.portal.model.Permission[] array = new com.liferay.portal.model.Permission[3];
			array[0] = (com.liferay.portal.model.Permission)objArray[0];
			array[1] = (com.liferay.portal.model.Permission)objArray[1];
			array[2] = (com.liferay.portal.model.Permission)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Permission findByA_R(String actionId,
		String resourceId) throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Permission_ IN CLASS com.liferay.portal.service.persistence.PermissionHBM WHERE ");
			query.append("actionId = ?");
			query.append(" AND ");
			query.append("resourceId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, actionId);
			q.setString(queryPos++, resourceId);

			Iterator itr = q.list().iterator();

			if (!itr.hasNext()) {
				String msg = "No Permission exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "actionId=";
				msg += actionId;
				msg += ", ";
				msg += "resourceId=";
				msg += resourceId;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchPermissionException(msg);
			}

			PermissionHBM permissionHBM = (PermissionHBM)itr.next();

			return PermissionHBMUtil.model(permissionHBM);
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
				"FROM Permission_ IN CLASS com.liferay.portal.service.persistence.PermissionHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				PermissionHBM permissionHBM = (PermissionHBM)itr.next();
				list.add(PermissionHBMUtil.model(permissionHBM));
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

	public void removeByResourceId(String resourceId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Permission_ IN CLASS com.liferay.portal.service.persistence.PermissionHBM WHERE ");
			query.append("resourceId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, resourceId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				PermissionHBM permissionHBM = (PermissionHBM)itr.next();
				session.delete(permissionHBM);
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

	public void removeByA_R(String actionId, String resourceId)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Permission_ IN CLASS com.liferay.portal.service.persistence.PermissionHBM WHERE ");
			query.append("actionId = ?");
			query.append(" AND ");
			query.append("resourceId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, actionId);
			q.setString(queryPos++, resourceId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				PermissionHBM permissionHBM = (PermissionHBM)itr.next();
				session.delete(permissionHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
				String msg = "No Permission exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "actionId=";
				msg += actionId;
				msg += ", ";
				msg += "resourceId=";
				msg += resourceId;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchPermissionException(msg);
			}
			else {
				throw new SystemException(he);
			}
		}
		finally {
			closeSession(session);
		}
	}

	public int countByResourceId(String resourceId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Permission_ IN CLASS com.liferay.portal.service.persistence.PermissionHBM WHERE ");
			query.append("resourceId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, resourceId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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
			query.append(
				"FROM Permission_ IN CLASS com.liferay.portal.service.persistence.PermissionHBM WHERE ");
			query.append("actionId = ?");
			query.append(" AND ");
			query.append("resourceId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, actionId);
			q.setString(queryPos++, resourceId);

			Iterator itr = q.list().iterator();

			if (itr.hasNext()) {
				Integer count = (Integer)itr.next();

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

	private static Log _log = LogFactory.getLog(PermissionPersistence.class);
}