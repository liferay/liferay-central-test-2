/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

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
 * <a href="GroupPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GroupPersistence extends BasePersistence {
	public Group create(long groupId) {
		Group group = new GroupImpl();
		group.setNew(true);
		group.setPrimaryKey(groupId);

		return group;
	}

	public Group remove(long groupId)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Group group = (Group)session.get(GroupImpl.class, new Long(groupId));

			if (group == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Group exists with the primary key " +
						groupId);
				}

				throw new NoSuchGroupException(
					"No Group exists with the primary key " + groupId);
			}

			return remove(group);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Group remove(Group group) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(group);
			session.flush();
			clearOrganizations.clear(group.getPrimaryKey());
			clearPermissions.clear(group.getPrimaryKey());
			clearRoles.clear(group.getPrimaryKey());
			clearUserGroups.clear(group.getPrimaryKey());
			clearUsers.clear(group.getPrimaryKey());

			return group;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Group update(
		com.liferay.portal.model.Group group) throws SystemException {
		return update(group, false);
	}

	public com.liferay.portal.model.Group update(
		com.liferay.portal.model.Group group, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(group);
			}
			else {
				if (group.isNew()) {
					session.save(group);
				}
			}

			session.flush();
			group.setNew(false);

			return group;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Group findByPrimaryKey(long groupId)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByPrimaryKey(groupId);

		if (group == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Group exists with the primary key " + groupId);
			}

			throw new NoSuchGroupException(
				"No Group exists with the primary key " + groupId);
		}

		return group;
	}

	public Group fetchByPrimaryKey(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (Group)session.get(GroupImpl.class, new Long(groupId));
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Group findByC_N(String companyId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_N(companyId, name);

		if (group == null) {
			String msg = "No Group exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "name=";
			msg += name;
			msg += StringPool.CLOSE_CURLY_BRACE;

			if (_log.isWarnEnabled()) {
				_log.warn(msg);
			}

			throw new NoSuchGroupException(msg);
		}

		return group;
	}

	public Group fetchByC_N(String companyId, String name)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Group WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			Group group = (Group)list.get(0);

			return group;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Group findByC_F(String companyId, String friendlyURL)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_F(companyId, friendlyURL);

		if (group == null) {
			String msg = "No Group exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "friendlyURL=";
			msg += friendlyURL;
			msg += StringPool.CLOSE_CURLY_BRACE;

			if (_log.isWarnEnabled()) {
				_log.warn(msg);
			}

			throw new NoSuchGroupException(msg);
		}

		return group;
	}

	public Group fetchByC_F(String companyId, String friendlyURL)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Group WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (friendlyURL == null) {
				query.append("friendlyURL IS NULL");
			}
			else {
				query.append("friendlyURL = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (friendlyURL != null) {
				q.setString(queryPos++, friendlyURL);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			Group group = (Group)list.get(0);

			return group;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public Group findByC_C_C(String companyId, String className, String classPK)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_C_C(companyId, className, classPK);

		if (group == null) {
			String msg = "No Group exists with the key ";
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

			if (_log.isWarnEnabled()) {
				_log.warn(msg);
			}

			throw new NoSuchGroupException(msg);
		}

		return group;
	}

	public Group fetchByC_C_C(String companyId, String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Group WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			Group group = (Group)list.get(0);

			return group;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List findAll(int begin, int end) throws SystemException {
		return findAll(begin, end, null);
	}

	public List findAll(int begin, int end, OrderByComparator obc)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("FROM com.liferay.portal.model.Group ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			return QueryUtil.list(q, getDialect(), begin, end);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByC_N(String companyId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_N(companyId, name);
		remove(group);
	}

	public void removeByC_F(String companyId, String friendlyURL)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_F(companyId, friendlyURL);
		remove(group);
	}

	public void removeByC_C_C(String companyId, String className, String classPK)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_C_C(companyId, className, classPK);
		remove(group);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((Group)itr.next());
		}
	}

	public int countByC_N(String companyId, String name)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Group WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (name == null) {
				query.append("name IS NULL");
			}
			else {
				query.append("name = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (name != null) {
				q.setString(queryPos++, name);
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

	public int countByC_F(String companyId, String friendlyURL)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Group WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (friendlyURL == null) {
				query.append("friendlyURL IS NULL");
			}
			else {
				query.append("friendlyURL = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (friendlyURL != null) {
				q.setString(queryPos++, friendlyURL);
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

	public int countByC_C_C(String companyId, String className, String classPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Group WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className IS NULL");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK IS NULL");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (className != null) {
				q.setString(queryPos++, className);
			}

			if (classPK != null) {
				q.setString(queryPos++, classPK);
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

	public int countAll() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.Group");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

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

	public List getOrganizations(long pk)
		throws NoSuchGroupException, SystemException {
		return getOrganizations(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getOrganizations(long pk, int begin, int end)
		throws NoSuchGroupException, SystemException {
		return getOrganizations(pk, begin, end, null);
	}

	public List getOrganizations(long pk, int begin, int end,
		OrderByComparator obc) throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = _SQL_GETORGANIZATIONS;

			if (obc != null) {
				sql += ("ORDER BY " + obc.getOrderBy());
			}
			else {
				sql += "ORDER BY ";
				sql += "Organization_.name ASC";
			}

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
			q.addEntity("Organization_",
				com.liferay.portal.model.impl.OrganizationImpl.class);

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

	public int getOrganizationsSize(long pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(_SQL_GETORGANIZATIONSSIZE);
			q.setCacheable(false);
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

	public boolean containsOrganization(long pk, String organizationPK)
		throws SystemException {
		try {
			return containsOrganization.contains(pk, organizationPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public boolean containsOrganizations(long pk) throws SystemException {
		if (getOrganizationsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addOrganization(long pk, String organizationPK)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		try {
			addOrganization.add(pk, organizationPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		try {
			addOrganization.add(pk, organization.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addOrganizations(long pk, String[] organizationPKs)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		try {
			for (int i = 0; i < organizationPKs.length; i++) {
				addOrganization.add(pk, organizationPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addOrganizations(long pk, List organizations)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		try {
			for (int i = 0; i < organizations.size(); i++) {
				com.liferay.portal.model.Organization organization = (com.liferay.portal.model.Organization)organizations.get(i);
				addOrganization.add(pk, organization.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void clearOrganizations(long pk)
		throws NoSuchGroupException, SystemException {
		try {
			clearOrganizations.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeOrganization(long pk, String organizationPK)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		try {
			removeOrganization.remove(pk, organizationPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		try {
			removeOrganization.remove(pk, organization.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeOrganizations(long pk, String[] organizationPKs)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		try {
			for (int i = 0; i < organizationPKs.length; i++) {
				removeOrganization.remove(pk, organizationPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeOrganizations(long pk, List organizations)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		try {
			for (int i = 0; i < organizations.size(); i++) {
				com.liferay.portal.model.Organization organization = (com.liferay.portal.model.Organization)organizations.get(i);
				removeOrganization.remove(pk, organization.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setOrganizations(long pk, String[] organizationPKs)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		try {
			clearOrganizations.clear(pk);

			for (int i = 0; i < organizationPKs.length; i++) {
				addOrganization.add(pk, organizationPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setOrganizations(long pk, List organizations)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		try {
			clearOrganizations.clear(pk);

			for (int i = 0; i < organizations.size(); i++) {
				com.liferay.portal.model.Organization organization = (com.liferay.portal.model.Organization)organizations.get(i);
				addOrganization.add(pk, organization.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public List getPermissions(long pk)
		throws NoSuchGroupException, SystemException {
		return getPermissions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getPermissions(long pk, int begin, int end)
		throws NoSuchGroupException, SystemException {
		return getPermissions(pk, begin, end, null);
	}

	public List getPermissions(long pk, int begin, int end,
		OrderByComparator obc) throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = _SQL_GETPERMISSIONS;

			if (obc != null) {
				sql += ("ORDER BY " + obc.getOrderBy());
			}

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
			q.addEntity("Permission_",
				com.liferay.portal.model.impl.PermissionImpl.class);

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

	public int getPermissionsSize(long pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(_SQL_GETPERMISSIONSSIZE);
			q.setCacheable(false);
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

	public boolean containsPermission(long pk, long permissionPK)
		throws SystemException {
		try {
			return containsPermission.contains(pk, permissionPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public boolean containsPermissions(long pk) throws SystemException {
		if (getPermissionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addPermission(long pk, long permissionPK)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		try {
			addPermission.add(pk, permissionPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addPermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		try {
			addPermission.add(pk, permission.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addPermissions(long pk, long[] permissionPKs)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		try {
			for (int i = 0; i < permissionPKs.length; i++) {
				addPermission.add(pk, permissionPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addPermissions(long pk, List permissions)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		try {
			for (int i = 0; i < permissions.size(); i++) {
				com.liferay.portal.model.Permission permission = (com.liferay.portal.model.Permission)permissions.get(i);
				addPermission.add(pk, permission.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void clearPermissions(long pk)
		throws NoSuchGroupException, SystemException {
		try {
			clearPermissions.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removePermission(long pk, long permissionPK)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		try {
			removePermission.remove(pk, permissionPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removePermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		try {
			removePermission.remove(pk, permission.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removePermissions(long pk, long[] permissionPKs)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		try {
			for (int i = 0; i < permissionPKs.length; i++) {
				removePermission.remove(pk, permissionPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removePermissions(long pk, List permissions)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		try {
			for (int i = 0; i < permissions.size(); i++) {
				com.liferay.portal.model.Permission permission = (com.liferay.portal.model.Permission)permissions.get(i);
				removePermission.remove(pk, permission.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setPermissions(long pk, long[] permissionPKs)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		try {
			clearPermissions.clear(pk);

			for (int i = 0; i < permissionPKs.length; i++) {
				addPermission.add(pk, permissionPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setPermissions(long pk, List permissions)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		try {
			clearPermissions.clear(pk);

			for (int i = 0; i < permissions.size(); i++) {
				com.liferay.portal.model.Permission permission = (com.liferay.portal.model.Permission)permissions.get(i);
				addPermission.add(pk, permission.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public List getRoles(long pk) throws NoSuchGroupException, SystemException {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getRoles(long pk, int begin, int end)
		throws NoSuchGroupException, SystemException {
		return getRoles(pk, begin, end, null);
	}

	public List getRoles(long pk, int begin, int end, OrderByComparator obc)
		throws NoSuchGroupException, SystemException {
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
			q.setCacheable(false);
			q.addEntity("Role_", com.liferay.portal.model.impl.RoleImpl.class);

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

	public int getRolesSize(long pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(_SQL_GETROLESSIZE);
			q.setCacheable(false);
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

	public boolean containsRole(long pk, String rolePK)
		throws SystemException {
		try {
			return containsRole.contains(pk, rolePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public boolean containsRoles(long pk) throws SystemException {
		if (getRolesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addRole(long pk, String rolePK)
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		try {
			addRole.add(pk, rolePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addRole(long pk, com.liferay.portal.model.Role role)
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		try {
			addRole.add(pk, role.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addRoles(long pk, String[] rolePKs)
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		try {
			for (int i = 0; i < rolePKs.length; i++) {
				addRole.add(pk, rolePKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addRoles(long pk, List roles)
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
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

	public void clearRoles(long pk)
		throws NoSuchGroupException, SystemException {
		try {
			clearRoles.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeRole(long pk, String rolePK)
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		try {
			removeRole.remove(pk, rolePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeRole(long pk, com.liferay.portal.model.Role role)
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		try {
			removeRole.remove(pk, role.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeRoles(long pk, String[] rolePKs)
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		try {
			for (int i = 0; i < rolePKs.length; i++) {
				removeRole.remove(pk, rolePKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeRoles(long pk, List roles)
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
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

	public void setRoles(long pk, String[] rolePKs)
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
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

	public void setRoles(long pk, List roles)
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
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

	public List getUserGroups(long pk)
		throws NoSuchGroupException, SystemException {
		return getUserGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getUserGroups(long pk, int begin, int end)
		throws NoSuchGroupException, SystemException {
		return getUserGroups(pk, begin, end, null);
	}

	public List getUserGroups(long pk, int begin, int end, OrderByComparator obc)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = _SQL_GETUSERGROUPS;

			if (obc != null) {
				sql += ("ORDER BY " + obc.getOrderBy());
			}
			else {
				sql += "ORDER BY ";
				sql += "UserGroup.name ASC";
			}

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
			q.addEntity("UserGroup",
				com.liferay.portal.model.impl.UserGroupImpl.class);

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

	public int getUserGroupsSize(long pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(_SQL_GETUSERGROUPSSIZE);
			q.setCacheable(false);
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

	public boolean containsUserGroup(long pk, String userGroupPK)
		throws SystemException {
		try {
			return containsUserGroup.contains(pk, userGroupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public boolean containsUserGroups(long pk) throws SystemException {
		if (getUserGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addUserGroup(long pk, String userGroupPK)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		try {
			addUserGroup.add(pk, userGroupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		try {
			addUserGroup.add(pk, userGroup.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addUserGroups(long pk, String[] userGroupPKs)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		try {
			for (int i = 0; i < userGroupPKs.length; i++) {
				addUserGroup.add(pk, userGroupPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addUserGroups(long pk, List userGroups)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		try {
			for (int i = 0; i < userGroups.size(); i++) {
				com.liferay.portal.model.UserGroup userGroup = (com.liferay.portal.model.UserGroup)userGroups.get(i);
				addUserGroup.add(pk, userGroup.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void clearUserGroups(long pk)
		throws NoSuchGroupException, SystemException {
		try {
			clearUserGroups.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUserGroup(long pk, String userGroupPK)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		try {
			removeUserGroup.remove(pk, userGroupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		try {
			removeUserGroup.remove(pk, userGroup.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUserGroups(long pk, String[] userGroupPKs)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		try {
			for (int i = 0; i < userGroupPKs.length; i++) {
				removeUserGroup.remove(pk, userGroupPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUserGroups(long pk, List userGroups)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		try {
			for (int i = 0; i < userGroups.size(); i++) {
				com.liferay.portal.model.UserGroup userGroup = (com.liferay.portal.model.UserGroup)userGroups.get(i);
				removeUserGroup.remove(pk, userGroup.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setUserGroups(long pk, String[] userGroupPKs)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		try {
			clearUserGroups.clear(pk);

			for (int i = 0; i < userGroupPKs.length; i++) {
				addUserGroup.add(pk, userGroupPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void setUserGroups(long pk, List userGroups)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		try {
			clearUserGroups.clear(pk);

			for (int i = 0; i < userGroups.size(); i++) {
				com.liferay.portal.model.UserGroup userGroup = (com.liferay.portal.model.UserGroup)userGroups.get(i);
				addUserGroup.add(pk, userGroup.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public List getUsers(long pk) throws NoSuchGroupException, SystemException {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getUsers(long pk, int begin, int end)
		throws NoSuchGroupException, SystemException {
		return getUsers(pk, begin, end, null);
	}

	public List getUsers(long pk, int begin, int end, OrderByComparator obc)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = _SQL_GETUSERS;

			if (obc != null) {
				sql += ("ORDER BY " + obc.getOrderBy());
			}

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
			q.addEntity("User_", com.liferay.portal.model.impl.UserImpl.class);

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

	public int getUsersSize(long pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(_SQL_GETUSERSSIZE);
			q.setCacheable(false);
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

	public boolean containsUser(long pk, String userPK)
		throws SystemException {
		try {
			return containsUser.contains(pk, userPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public boolean containsUsers(long pk) throws SystemException {
		if (getUsersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addUser(long pk, String userPK)
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		try {
			addUser.add(pk, userPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addUser(long pk, com.liferay.portal.model.User user)
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		try {
			addUser.add(pk, user.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addUsers(long pk, String[] userPKs)
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		try {
			for (int i = 0; i < userPKs.length; i++) {
				addUser.add(pk, userPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addUsers(long pk, List users)
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
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

	public void clearUsers(long pk)
		throws NoSuchGroupException, SystemException {
		try {
			clearUsers.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUser(long pk, String userPK)
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		try {
			removeUser.remove(pk, userPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		try {
			removeUser.remove(pk, user.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUsers(long pk, String[] userPKs)
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		try {
			for (int i = 0; i < userPKs.length; i++) {
				removeUser.remove(pk, userPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUsers(long pk, List users)
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
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

	public void setUsers(long pk, String[] userPKs)
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
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

	public void setUsers(long pk, List users)
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
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
		containsOrganization = new ContainsOrganization(this);
		addOrganization = new AddOrganization(this);
		clearOrganizations = new ClearOrganizations(this);
		removeOrganization = new RemoveOrganization(this);
		containsPermission = new ContainsPermission(this);
		addPermission = new AddPermission(this);
		clearPermissions = new ClearPermissions(this);
		removePermission = new RemovePermission(this);
		containsRole = new ContainsRole(this);
		addRole = new AddRole(this);
		clearRoles = new ClearRoles(this);
		removeRole = new RemoveRole(this);
		containsUserGroup = new ContainsUserGroup(this);
		addUserGroup = new AddUserGroup(this);
		clearUserGroups = new ClearUserGroups(this);
		removeUserGroup = new RemoveUserGroup(this);
		containsUser = new ContainsUser(this);
		addUser = new AddUser(this);
		clearUsers = new ClearUsers(this);
		removeUser = new RemoveUser(this);
	}

	protected ContainsOrganization containsOrganization;
	protected AddOrganization addOrganization;
	protected ClearOrganizations clearOrganizations;
	protected RemoveOrganization removeOrganization;
	protected ContainsPermission containsPermission;
	protected AddPermission addPermission;
	protected ClearPermissions clearPermissions;
	protected RemovePermission removePermission;
	protected ContainsRole containsRole;
	protected AddRole addRole;
	protected ClearRoles clearRoles;
	protected RemoveRole removeRole;
	protected ContainsUserGroup containsUserGroup;
	protected AddUserGroup addUserGroup;
	protected ClearUserGroups clearUserGroups;
	protected RemoveUserGroup removeUserGroup;
	protected ContainsUser containsUser;
	protected AddUser addUser;
	protected ClearUsers clearUsers;
	protected RemoveUser removeUser;

	protected class ContainsOrganization extends MappingSqlQuery {
		protected ContainsOrganization(GroupPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSORGANIZATION);
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long groupId, String organizationId) {
			List results = execute(new Object[] {
						new Long(groupId), organizationId
					});

			if (results.size() > 0) {
				Integer count = (Integer)results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddOrganization extends SqlUpdate {
		protected AddOrganization(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO Groups_Orgs (groupId, organizationId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void add(long groupId, String organizationId) {
			if (!_persistence.containsOrganization.contains(groupId,
						organizationId)) {
				update(new Object[] { new Long(groupId), organizationId });
			}
		}

		private GroupPersistence _persistence;
	}

	protected class ClearOrganizations extends SqlUpdate {
		protected ClearOrganizations(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Groups_Orgs WHERE groupId = ?");
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected void clear(long groupId) {
			update(new Object[] { new Long(groupId) });
		}
	}

	protected class RemoveOrganization extends SqlUpdate {
		protected RemoveOrganization(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Groups_Orgs WHERE groupId = ? AND organizationId = ?");
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void remove(long groupId, String organizationId) {
			update(new Object[] { new Long(groupId), organizationId });
		}
	}

	protected class ContainsPermission extends MappingSqlQuery {
		protected ContainsPermission(GroupPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSPERMISSION);
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long groupId, long permissionId) {
			List results = execute(new Object[] {
						new Long(groupId), new Long(permissionId)
					});

			if (results.size() > 0) {
				Integer count = (Integer)results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddPermission extends SqlUpdate {
		protected AddPermission(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO Groups_Permissions (groupId, permissionId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected void add(long groupId, long permissionId) {
			if (!_persistence.containsPermission.contains(groupId, permissionId)) {
				update(new Object[] { new Long(groupId), new Long(permissionId) });
			}
		}

		private GroupPersistence _persistence;
	}

	protected class ClearPermissions extends SqlUpdate {
		protected ClearPermissions(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Groups_Permissions WHERE groupId = ?");
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected void clear(long groupId) {
			update(new Object[] { new Long(groupId) });
		}
	}

	protected class RemovePermission extends SqlUpdate {
		protected RemovePermission(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Groups_Permissions WHERE groupId = ? AND permissionId = ?");
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected void remove(long groupId, long permissionId) {
			update(new Object[] { new Long(groupId), new Long(permissionId) });
		}
	}

	protected class ContainsRole extends MappingSqlQuery {
		protected ContainsRole(GroupPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSROLE);
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long groupId, String roleId) {
			List results = execute(new Object[] { new Long(groupId), roleId });

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
		protected AddRole(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO Groups_Roles (groupId, roleId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void add(long groupId, String roleId) {
			if (!_persistence.containsRole.contains(groupId, roleId)) {
				update(new Object[] { new Long(groupId), roleId });
			}
		}

		private GroupPersistence _persistence;
	}

	protected class ClearRoles extends SqlUpdate {
		protected ClearRoles(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Groups_Roles WHERE groupId = ?");
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected void clear(long groupId) {
			update(new Object[] { new Long(groupId) });
		}
	}

	protected class RemoveRole extends SqlUpdate {
		protected RemoveRole(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Groups_Roles WHERE groupId = ? AND roleId = ?");
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void remove(long groupId, String roleId) {
			update(new Object[] { new Long(groupId), roleId });
		}
	}

	protected class ContainsUserGroup extends MappingSqlQuery {
		protected ContainsUserGroup(GroupPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSUSERGROUP);
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long groupId, String userGroupId) {
			List results = execute(new Object[] { new Long(groupId), userGroupId });

			if (results.size() > 0) {
				Integer count = (Integer)results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddUserGroup extends SqlUpdate {
		protected AddUserGroup(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO Groups_UserGroups (groupId, userGroupId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void add(long groupId, String userGroupId) {
			if (!_persistence.containsUserGroup.contains(groupId, userGroupId)) {
				update(new Object[] { new Long(groupId), userGroupId });
			}
		}

		private GroupPersistence _persistence;
	}

	protected class ClearUserGroups extends SqlUpdate {
		protected ClearUserGroups(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Groups_UserGroups WHERE groupId = ?");
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected void clear(long groupId) {
			update(new Object[] { new Long(groupId) });
		}
	}

	protected class RemoveUserGroup extends SqlUpdate {
		protected RemoveUserGroup(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Groups_UserGroups WHERE groupId = ? AND userGroupId = ?");
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void remove(long groupId, String userGroupId) {
			update(new Object[] { new Long(groupId), userGroupId });
		}
	}

	protected class ContainsUser extends MappingSqlQuery {
		protected ContainsUser(GroupPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSUSER);
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long groupId, String userId) {
			List results = execute(new Object[] { new Long(groupId), userId });

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
		protected AddUser(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO Users_Groups (groupId, userId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void add(long groupId, String userId) {
			if (!_persistence.containsUser.contains(groupId, userId)) {
				update(new Object[] { new Long(groupId), userId });
			}
		}

		private GroupPersistence _persistence;
	}

	protected class ClearUsers extends SqlUpdate {
		protected ClearUsers(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_Groups WHERE groupId = ?");
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected void clear(long groupId) {
			update(new Object[] { new Long(groupId) });
		}
	}

	protected class RemoveUser extends SqlUpdate {
		protected RemoveUser(GroupPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_Groups WHERE groupId = ? AND userId = ?");
			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void remove(long groupId, String userId) {
			update(new Object[] { new Long(groupId), userId });
		}
	}

	private static final String _SQL_GETORGANIZATIONS = "SELECT {Organization_.*} FROM Organization_ INNER JOIN Groups_Orgs ON (Groups_Orgs.organizationId = Organization_.organizationId) WHERE (Groups_Orgs.groupId = ?)";
	private static final String _SQL_GETORGANIZATIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Orgs WHERE groupId = ?";
	private static final String _SQL_CONTAINSORGANIZATION = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Orgs WHERE groupId = ? AND organizationId = ?";
	private static final String _SQL_GETPERMISSIONS = "SELECT {Permission_.*} FROM Permission_ INNER JOIN Groups_Permissions ON (Groups_Permissions.permissionId = Permission_.permissionId) WHERE (Groups_Permissions.groupId = ?)";
	private static final String _SQL_GETPERMISSIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Permissions WHERE groupId = ?";
	private static final String _SQL_CONTAINSPERMISSION = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Permissions WHERE groupId = ? AND permissionId = ?";
	private static final String _SQL_GETROLES = "SELECT {Role_.*} FROM Role_ INNER JOIN Groups_Roles ON (Groups_Roles.roleId = Role_.roleId) WHERE (Groups_Roles.groupId = ?)";
	private static final String _SQL_GETROLESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Roles WHERE groupId = ?";
	private static final String _SQL_CONTAINSROLE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Roles WHERE groupId = ? AND roleId = ?";
	private static final String _SQL_GETUSERGROUPS = "SELECT {UserGroup.*} FROM UserGroup INNER JOIN Groups_UserGroups ON (Groups_UserGroups.userGroupId = UserGroup.userGroupId) WHERE (Groups_UserGroups.groupId = ?)";
	private static final String _SQL_GETUSERGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_UserGroups WHERE groupId = ?";
	private static final String _SQL_CONTAINSUSERGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_UserGroups WHERE groupId = ? AND userGroupId = ?";
	private static final String _SQL_GETUSERS = "SELECT {User_.*} FROM User_ INNER JOIN Users_Groups ON (Users_Groups.userId = User_.userId) WHERE (Users_Groups.groupId = ?)";
	private static final String _SQL_GETUSERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE groupId = ?";
	private static final String _SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE groupId = ? AND userId = ?";
	private static Log _log = LogFactory.getLog(GroupPersistence.class);
}