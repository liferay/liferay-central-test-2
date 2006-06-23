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

import com.liferay.portal.NoSuchGroupException;
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
 * <a href="GroupPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class GroupPersistence extends BasePersistence {
	public com.liferay.portal.model.Group create(String groupId) {
		GroupHBM groupHBM = new GroupHBM();
		groupHBM.setNew(true);
		groupHBM.setPrimaryKey(groupId);

		return GroupHBMUtil.model(groupHBM);
	}

	public com.liferay.portal.model.Group remove(String groupId)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, groupId);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					groupId.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " +
					groupId.toString());
			}

			session.delete(groupHBM);
			session.flush();

			return GroupHBMUtil.model(groupHBM);
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
		Session session = null;

		try {
			if (group.isNew() || group.isModified()) {
				session = openSession();

				if (group.isNew()) {
					GroupHBM groupHBM = new GroupHBM();
					groupHBM.setGroupId(group.getGroupId());
					groupHBM.setCompanyId(group.getCompanyId());
					groupHBM.setClassName(group.getClassName());
					groupHBM.setClassPK(group.getClassPK());
					groupHBM.setParentGroupId(group.getParentGroupId());
					groupHBM.setName(group.getName());
					groupHBM.setDescription(group.getDescription());
					groupHBM.setType(group.getType());
					groupHBM.setFriendlyURL(group.getFriendlyURL());
					groupHBM.setOrgs(new HashSet());
					groupHBM.setPermissions(new HashSet());
					groupHBM.setRoles(new HashSet());
					groupHBM.setUserGroups(new HashSet());
					groupHBM.setUsers(new HashSet());
					session.save(groupHBM);
					session.flush();
				}
				else {
					GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class,
							group.getPrimaryKey());

					if (groupHBM != null) {
						groupHBM.setCompanyId(group.getCompanyId());
						groupHBM.setClassName(group.getClassName());
						groupHBM.setClassPK(group.getClassPK());
						groupHBM.setParentGroupId(group.getParentGroupId());
						groupHBM.setName(group.getName());
						groupHBM.setDescription(group.getDescription());
						groupHBM.setType(group.getType());
						groupHBM.setFriendlyURL(group.getFriendlyURL());
						session.flush();
					}
					else {
						groupHBM = new GroupHBM();
						groupHBM.setGroupId(group.getGroupId());
						groupHBM.setCompanyId(group.getCompanyId());
						groupHBM.setClassName(group.getClassName());
						groupHBM.setClassPK(group.getClassPK());
						groupHBM.setParentGroupId(group.getParentGroupId());
						groupHBM.setName(group.getName());
						groupHBM.setDescription(group.getDescription());
						groupHBM.setType(group.getType());
						groupHBM.setFriendlyURL(group.getFriendlyURL());
						session.save(groupHBM);
						session.flush();
					}
				}

				group.setNew(false);
				group.setModified(false);
			}

			return group;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List getOrganizations(String pk)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT organizationHBM FROM ");
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.orgs AS organizationHBM ");
			query.append("WHERE groupHBM.groupId = ? ");
			query.append("ORDER BY ");
			query.append("organizationHBM.name ASC");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				com.liferay.portal.service.persistence.OrganizationHBM organizationHBM =
					(com.liferay.portal.service.persistence.OrganizationHBM)itr.next();
				list.add(com.liferay.portal.service.persistence.OrganizationHBMUtil.model(
						organizationHBM));
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

	public List getOrganizations(String pk, int begin, int end)
		throws NoSuchGroupException, SystemException {
		return getOrganizations(pk, begin, end, null);
	}

	public List getOrganizations(String pk, int begin, int end,
		OrderByComparator obc) throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT organizationHBM FROM ");
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.orgs AS organizationHBM ");
			query.append("WHERE groupHBM.groupId = ? ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("organizationHBM.name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				com.liferay.portal.service.persistence.OrganizationHBM organizationHBM =
					(com.liferay.portal.service.persistence.OrganizationHBM)itr.next();
				list.add(com.liferay.portal.service.persistence.OrganizationHBMUtil.model(
						organizationHBM));
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

	public int getOrganizationsSize(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) FROM ");
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.orgs AS organizationHBM ");
			query.append("WHERE groupHBM.groupId = ? ");

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

	public void setOrganizations(String pk, String[] pks)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			Set orgsSet = new HashSet();

			for (int i = 0; (pks != null) && (i < pks.length); i++) {
				com.liferay.portal.service.persistence.OrganizationHBM organizationHBM =
					(com.liferay.portal.service.persistence.OrganizationHBM)session.get(com.liferay.portal.service.persistence.OrganizationHBM.class,
						pks[i]);

				if (organizationHBM == null) {
					_log.warn("No Organization exists with the primary key " +
						pks[i].toString());
					throw new com.liferay.portal.NoSuchOrganizationException(
						"No Organization exists with the primary key " +
						pks[i].toString());
				}

				orgsSet.add(organizationHBM);
			}

			groupHBM.setOrgs(orgsSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void setOrganizations(String pk, List orgs)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			Set orgsSet = new HashSet();
			Iterator itr = orgs.iterator();

			while (itr.hasNext()) {
				com.liferay.portal.model.Organization organization = (com.liferay.portal.model.Organization)itr.next();
				com.liferay.portal.service.persistence.OrganizationHBM organizationHBM =
					(com.liferay.portal.service.persistence.OrganizationHBM)session.get(com.liferay.portal.service.persistence.OrganizationHBM.class,
						organization.getPrimaryKey());

				if (organizationHBM == null) {
					_log.warn("No Organization exists with the primary key " +
						organization.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchOrganizationException(
						"No Organization exists with the primary key " +
						organization.getPrimaryKey().toString());
				}

				orgsSet.add(organizationHBM);
			}

			groupHBM.setOrgs(orgsSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addOrganization(String pk, String organizationPK)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.OrganizationHBM organizationHBM =
				null;
			organizationHBM = (com.liferay.portal.service.persistence.OrganizationHBM)session.get(com.liferay.portal.service.persistence.OrganizationHBM.class,
					organizationPK);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					organizationPK.toString());
				throw new com.liferay.portal.NoSuchOrganizationException(
					"No Organization exists with the primary key " +
					organizationPK.toString());
			}

			boolean value = groupHBM.getOrgs().add(organizationHBM);
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

	public boolean addOrganization(String pk,
		com.liferay.portal.model.Organization organization)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.OrganizationHBM organizationHBM =
				null;
			organizationHBM = (com.liferay.portal.service.persistence.OrganizationHBM)session.get(com.liferay.portal.service.persistence.OrganizationHBM.class,
					organization.getPrimaryKey());

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					organization.getPrimaryKey().toString());
				throw new com.liferay.portal.NoSuchOrganizationException(
					"No Organization exists with the primary key " +
					organization.getPrimaryKey().toString());
			}

			boolean value = groupHBM.getOrgs().add(organizationHBM);
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

	public boolean addOrganizations(String pk, String[] organizationPKs)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < organizationPKs.length; i++) {
				com.liferay.portal.service.persistence.OrganizationHBM organizationHBM =
					null;
				organizationHBM = (com.liferay.portal.service.persistence.OrganizationHBM)session.get(com.liferay.portal.service.persistence.OrganizationHBM.class,
						organizationPKs[i]);

				if (organizationHBM == null) {
					_log.warn("No Organization exists with the primary key " +
						organizationPKs[i].toString());
					throw new com.liferay.portal.NoSuchOrganizationException(
						"No Organization exists with the primary key " +
						organizationPKs[i].toString());
				}

				if (groupHBM.getOrgs().add(organizationHBM)) {
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

	public boolean addOrganizations(String pk, List organizations)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < organizations.size(); i++) {
				com.liferay.portal.model.Organization organization = (com.liferay.portal.model.Organization)organizations.get(i);
				com.liferay.portal.service.persistence.OrganizationHBM organizationHBM =
					(com.liferay.portal.service.persistence.OrganizationHBM)session.get(com.liferay.portal.service.persistence.OrganizationHBM.class,
						organization.getPrimaryKey());

				if (organizationHBM == null) {
					_log.warn("No Organization exists with the primary key " +
						organization.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchOrganizationException(
						"No Organization exists with the primary key " +
						organization.getPrimaryKey().toString());
				}

				if (groupHBM.getOrgs().add(organizationHBM)) {
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

	public void clearOrganizations(String pk)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			groupHBM.getOrgs().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSORGANIZATION = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Orgs WHERE groupId = ? AND organizationId = ?";

	public boolean containsOrganization(String pk, String organizationPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSORGANIZATION);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);
			qPos.add(organizationPK);

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

	public static final String SQL_CONTAINSORGANIZATIONS = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Orgs WHERE groupId = ?";

	public boolean containsOrganizations(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSORGANIZATIONS);
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

	public boolean removeOrganization(String pk, String organizationPK)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.OrganizationHBM organizationHBM =
				null;
			organizationHBM = (com.liferay.portal.service.persistence.OrganizationHBM)session.get(com.liferay.portal.service.persistence.OrganizationHBM.class,
					organizationPK);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					organizationPK.toString());
				throw new com.liferay.portal.NoSuchOrganizationException(
					"No Organization exists with the primary key " +
					organizationPK.toString());
			}

			boolean value = groupHBM.getOrgs().remove(organizationHBM);
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

	public boolean removeOrganization(String pk,
		com.liferay.portal.model.Organization organization)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.OrganizationHBM organizationHBM =
				null;
			organizationHBM = (com.liferay.portal.service.persistence.OrganizationHBM)session.get(com.liferay.portal.service.persistence.OrganizationHBM.class,
					organization.getPrimaryKey());

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					organization.getPrimaryKey().toString());
				throw new com.liferay.portal.NoSuchOrganizationException(
					"No Organization exists with the primary key " +
					organization.getPrimaryKey().toString());
			}

			boolean value = groupHBM.getOrgs().remove(organizationHBM);
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

	public boolean removeOrganizations(String pk, String[] organizationPKs)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < organizationPKs.length; i++) {
				com.liferay.portal.service.persistence.OrganizationHBM organizationHBM =
					null;
				organizationHBM = (com.liferay.portal.service.persistence.OrganizationHBM)session.get(com.liferay.portal.service.persistence.OrganizationHBM.class,
						organizationPKs[i]);

				if (organizationHBM == null) {
					_log.warn("No Organization exists with the primary key " +
						organizationPKs[i].toString());
					throw new com.liferay.portal.NoSuchOrganizationException(
						"No Organization exists with the primary key " +
						organizationPKs[i].toString());
				}

				if (groupHBM.getOrgs().remove(organizationHBM)) {
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

	public boolean removeOrganizations(String pk, List organizations)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < organizations.size(); i++) {
				com.liferay.portal.model.Organization organization = (com.liferay.portal.model.Organization)organizations.get(i);
				com.liferay.portal.service.persistence.OrganizationHBM organizationHBM =
					(com.liferay.portal.service.persistence.OrganizationHBM)session.get(com.liferay.portal.service.persistence.OrganizationHBM.class,
						organization.getPrimaryKey());

				if (organizationHBM == null) {
					_log.warn("No Organization exists with the primary key " +
						organization.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchOrganizationException(
						"No Organization exists with the primary key " +
						organization.getPrimaryKey().toString());
				}

				if (groupHBM.getOrgs().remove(organizationHBM)) {
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
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT permissionHBM FROM ");
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.permissions AS permissionHBM ");
			query.append("WHERE groupHBM.groupId = ? ");

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
		throws NoSuchGroupException, SystemException {
		return getPermissions(pk, begin, end, null);
	}

	public List getPermissions(String pk, int begin, int end,
		OrderByComparator obc) throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT permissionHBM FROM ");
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.permissions AS permissionHBM ");
			query.append("WHERE groupHBM.groupId = ? ");

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
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.permissions AS permissionHBM ");
			query.append("WHERE groupHBM.groupId = ? ");

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
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			groupHBM.setPermissions(permissionsSet);
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
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			groupHBM.setPermissions(permissionsSet);
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
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			boolean value = groupHBM.getPermissions().add(permissionHBM);
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
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			boolean value = groupHBM.getPermissions().add(permissionHBM);
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
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

				if (groupHBM.getPermissions().add(permissionHBM)) {
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
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

				if (groupHBM.getPermissions().add(permissionHBM)) {
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
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			groupHBM.getPermissions().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSPERMISSION = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Permissions WHERE groupId = ? AND permissionId = ?";

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

	public static final String SQL_CONTAINSPERMISSIONS = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Permissions WHERE groupId = ?";

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
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			boolean value = groupHBM.getPermissions().remove(permissionHBM);
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
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			boolean value = groupHBM.getPermissions().remove(permissionHBM);
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
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

				if (groupHBM.getPermissions().remove(permissionHBM)) {
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
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

				if (groupHBM.getPermissions().remove(permissionHBM)) {
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
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT roleHBM FROM ");
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.roles AS roleHBM ");
			query.append("WHERE groupHBM.groupId = ? ");
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
		throws NoSuchGroupException, SystemException {
		return getRoles(pk, begin, end, null);
	}

	public List getRoles(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT roleHBM FROM ");
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.roles AS roleHBM ");
			query.append("WHERE groupHBM.groupId = ? ");

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
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.roles AS roleHBM ");
			query.append("WHERE groupHBM.groupId = ? ");

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

	public void setRoles(String pk, String[] pks)
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			groupHBM.setRoles(rolesSet);
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			groupHBM.setRoles(rolesSet);
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			boolean value = groupHBM.getRoles().add(roleHBM);
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			boolean value = groupHBM.getRoles().add(roleHBM);
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

				if (groupHBM.getRoles().add(roleHBM)) {
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

				if (groupHBM.getRoles().add(roleHBM)) {
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
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			groupHBM.getRoles().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSROLE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Roles WHERE groupId = ? AND roleId = ?";

	public boolean containsRole(String pk, String rolePK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSROLE);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);
			qPos.add(rolePK);

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

	public static final String SQL_CONTAINSROLES = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Roles WHERE groupId = ?";

	public boolean containsRoles(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSROLES);
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

	public boolean removeRole(String pk, String rolePK)
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			boolean value = groupHBM.getRoles().remove(roleHBM);
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			boolean value = groupHBM.getRoles().remove(roleHBM);
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

				if (groupHBM.getRoles().remove(roleHBM)) {
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

				if (groupHBM.getRoles().remove(roleHBM)) {
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

	public List getUserGroups(String pk)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT userGroupHBM FROM ");
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.userGroups AS userGroupHBM ");
			query.append("WHERE groupHBM.groupId = ? ");
			query.append("ORDER BY ");
			query.append("userGroupHBM.name ASC");

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				com.liferay.portal.service.persistence.UserGroupHBM userGroupHBM =
					(com.liferay.portal.service.persistence.UserGroupHBM)itr.next();
				list.add(com.liferay.portal.service.persistence.UserGroupHBMUtil.model(
						userGroupHBM));
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

	public List getUserGroups(String pk, int begin, int end)
		throws NoSuchGroupException, SystemException {
		return getUserGroups(pk, begin, end, null);
	}

	public List getUserGroups(String pk, int begin, int end,
		OrderByComparator obc) throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT userGroupHBM FROM ");
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.userGroups AS userGroupHBM ");
			query.append("WHERE groupHBM.groupId = ? ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}
			else {
				query.append("ORDER BY ");
				query.append("userGroupHBM.name ASC");
			}

			Query q = session.createQuery(query.toString());
			q.setString(0, pk);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				com.liferay.portal.service.persistence.UserGroupHBM userGroupHBM =
					(com.liferay.portal.service.persistence.UserGroupHBM)itr.next();
				list.add(com.liferay.portal.service.persistence.UserGroupHBMUtil.model(
						userGroupHBM));
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

	public int getUserGroupsSize(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) FROM ");
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.userGroups AS userGroupHBM ");
			query.append("WHERE groupHBM.groupId = ? ");

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

	public void setUserGroups(String pk, String[] pks)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			Set userGroupsSet = new HashSet();

			for (int i = 0; (pks != null) && (i < pks.length); i++) {
				com.liferay.portal.service.persistence.UserGroupHBM userGroupHBM =
					(com.liferay.portal.service.persistence.UserGroupHBM)session.get(com.liferay.portal.service.persistence.UserGroupHBM.class,
						pks[i]);

				if (userGroupHBM == null) {
					_log.warn("No UserGroup exists with the primary key " +
						pks[i].toString());
					throw new com.liferay.portal.NoSuchUserGroupException(
						"No UserGroup exists with the primary key " +
						pks[i].toString());
				}

				userGroupsSet.add(userGroupHBM);
			}

			groupHBM.setUserGroups(userGroupsSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public void setUserGroups(String pk, List userGroups)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			Set userGroupsSet = new HashSet();
			Iterator itr = userGroups.iterator();

			while (itr.hasNext()) {
				com.liferay.portal.model.UserGroup userGroup = (com.liferay.portal.model.UserGroup)itr.next();
				com.liferay.portal.service.persistence.UserGroupHBM userGroupHBM =
					(com.liferay.portal.service.persistence.UserGroupHBM)session.get(com.liferay.portal.service.persistence.UserGroupHBM.class,
						userGroup.getPrimaryKey());

				if (userGroupHBM == null) {
					_log.warn("No UserGroup exists with the primary key " +
						userGroup.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchUserGroupException(
						"No UserGroup exists with the primary key " +
						userGroup.getPrimaryKey().toString());
				}

				userGroupsSet.add(userGroupHBM);
			}

			groupHBM.setUserGroups(userGroupsSet);
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public boolean addUserGroup(String pk, String userGroupPK)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.UserGroupHBM userGroupHBM = null;
			userGroupHBM = (com.liferay.portal.service.persistence.UserGroupHBM)session.get(com.liferay.portal.service.persistence.UserGroupHBM.class,
					userGroupPK);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					userGroupPK.toString());
				throw new com.liferay.portal.NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
					userGroupPK.toString());
			}

			boolean value = groupHBM.getUserGroups().add(userGroupHBM);
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

	public boolean addUserGroup(String pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.UserGroupHBM userGroupHBM = null;
			userGroupHBM = (com.liferay.portal.service.persistence.UserGroupHBM)session.get(com.liferay.portal.service.persistence.UserGroupHBM.class,
					userGroup.getPrimaryKey());

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					userGroup.getPrimaryKey().toString());
				throw new com.liferay.portal.NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
					userGroup.getPrimaryKey().toString());
			}

			boolean value = groupHBM.getUserGroups().add(userGroupHBM);
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

	public boolean addUserGroups(String pk, String[] userGroupPKs)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < userGroupPKs.length; i++) {
				com.liferay.portal.service.persistence.UserGroupHBM userGroupHBM =
					null;
				userGroupHBM = (com.liferay.portal.service.persistence.UserGroupHBM)session.get(com.liferay.portal.service.persistence.UserGroupHBM.class,
						userGroupPKs[i]);

				if (userGroupHBM == null) {
					_log.warn("No UserGroup exists with the primary key " +
						userGroupPKs[i].toString());
					throw new com.liferay.portal.NoSuchUserGroupException(
						"No UserGroup exists with the primary key " +
						userGroupPKs[i].toString());
				}

				if (groupHBM.getUserGroups().add(userGroupHBM)) {
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

	public boolean addUserGroups(String pk, List userGroups)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < userGroups.size(); i++) {
				com.liferay.portal.model.UserGroup userGroup = (com.liferay.portal.model.UserGroup)userGroups.get(i);
				com.liferay.portal.service.persistence.UserGroupHBM userGroupHBM =
					(com.liferay.portal.service.persistence.UserGroupHBM)session.get(com.liferay.portal.service.persistence.UserGroupHBM.class,
						userGroup.getPrimaryKey());

				if (userGroupHBM == null) {
					_log.warn("No UserGroup exists with the primary key " +
						userGroup.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchUserGroupException(
						"No UserGroup exists with the primary key " +
						userGroup.getPrimaryKey().toString());
				}

				if (groupHBM.getUserGroups().add(userGroupHBM)) {
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

	public void clearUserGroups(String pk)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			groupHBM.getUserGroups().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSUSERGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_UserGroups WHERE groupId = ? AND userGroupId = ?";

	public boolean containsUserGroup(String pk, String userGroupPK)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSUSERGROUP);
			q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pk);
			qPos.add(userGroupPK);

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

	public static final String SQL_CONTAINSUSERGROUPS = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_UserGroups WHERE groupId = ?";

	public boolean containsUserGroups(String pk) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(SQL_CONTAINSUSERGROUPS);
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

	public boolean removeUserGroup(String pk, String userGroupPK)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.UserGroupHBM userGroupHBM = null;
			userGroupHBM = (com.liferay.portal.service.persistence.UserGroupHBM)session.get(com.liferay.portal.service.persistence.UserGroupHBM.class,
					userGroupPK);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					userGroupPK.toString());
				throw new com.liferay.portal.NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
					userGroupPK.toString());
			}

			boolean value = groupHBM.getUserGroups().remove(userGroupHBM);
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

	public boolean removeUserGroup(String pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			com.liferay.portal.service.persistence.UserGroupHBM userGroupHBM = null;
			userGroupHBM = (com.liferay.portal.service.persistence.UserGroupHBM)session.get(com.liferay.portal.service.persistence.UserGroupHBM.class,
					userGroup.getPrimaryKey());

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					userGroup.getPrimaryKey().toString());
				throw new com.liferay.portal.NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
					userGroup.getPrimaryKey().toString());
			}

			boolean value = groupHBM.getUserGroups().remove(userGroupHBM);
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

	public boolean removeUserGroups(String pk, String[] userGroupPKs)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < userGroupPKs.length; i++) {
				com.liferay.portal.service.persistence.UserGroupHBM userGroupHBM =
					null;
				userGroupHBM = (com.liferay.portal.service.persistence.UserGroupHBM)session.get(com.liferay.portal.service.persistence.UserGroupHBM.class,
						userGroupPKs[i]);

				if (userGroupHBM == null) {
					_log.warn("No UserGroup exists with the primary key " +
						userGroupPKs[i].toString());
					throw new com.liferay.portal.NoSuchUserGroupException(
						"No UserGroup exists with the primary key " +
						userGroupPKs[i].toString());
				}

				if (groupHBM.getUserGroups().remove(userGroupHBM)) {
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

	public boolean removeUserGroups(String pk, List userGroups)
		throws NoSuchGroupException, 
			com.liferay.portal.NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			boolean value = false;

			for (int i = 0; i < userGroups.size(); i++) {
				com.liferay.portal.model.UserGroup userGroup = (com.liferay.portal.model.UserGroup)userGroups.get(i);
				com.liferay.portal.service.persistence.UserGroupHBM userGroupHBM =
					(com.liferay.portal.service.persistence.UserGroupHBM)session.get(com.liferay.portal.service.persistence.UserGroupHBM.class,
						userGroup.getPrimaryKey());

				if (userGroupHBM == null) {
					_log.warn("No UserGroup exists with the primary key " +
						userGroup.getPrimaryKey().toString());
					throw new com.liferay.portal.NoSuchUserGroupException(
						"No UserGroup exists with the primary key " +
						userGroup.getPrimaryKey().toString());
				}

				if (groupHBM.getUserGroups().remove(userGroupHBM)) {
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
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT userHBM FROM ");
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.users AS userHBM ");
			query.append("WHERE groupHBM.groupId = ? ");

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
		throws NoSuchGroupException, SystemException {
		return getUsers(pk, begin, end, null);
	}

	public List getUsers(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT userHBM FROM ");
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.users AS userHBM ");
			query.append("WHERE groupHBM.groupId = ? ");

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
			query.append(com.liferay.portal.service.persistence.GroupHBM.class.getName());
			query.append(" groupHBM ");
			query.append("JOIN groupHBM.users AS userHBM ");
			query.append("WHERE groupHBM.groupId = ? ");

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
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			groupHBM.setUsers(usersSet);
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			groupHBM.setUsers(usersSet);
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			boolean value = groupHBM.getUsers().add(userHBM);
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			boolean value = groupHBM.getUsers().add(userHBM);
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

				if (groupHBM.getUsers().add(userHBM)) {
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

				if (groupHBM.getUsers().add(userHBM)) {
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
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
			}

			groupHBM.getUsers().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE groupId = ? AND userId = ?";

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

	public static final String SQL_CONTAINSUSERS = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE groupId = ?";

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
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			boolean value = groupHBM.getUsers().remove(userHBM);
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

			boolean value = groupHBM.getUsers().remove(userHBM);
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

				if (groupHBM.getUsers().remove(userHBM)) {
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
		throws NoSuchGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, pk);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					pk.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " + pk.toString());
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

				if (groupHBM.getUsers().remove(userHBM)) {
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

	public com.liferay.portal.model.Group findByPrimaryKey(String groupId)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			GroupHBM groupHBM = (GroupHBM)session.get(GroupHBM.class, groupId);

			if (groupHBM == null) {
				_log.warn("No Group exists with the primary key " +
					groupId.toString());
				throw new NoSuchGroupException(
					"No Group exists with the primary key " +
					groupId.toString());
			}

			return GroupHBMUtil.model(groupHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Group findByC_F(String companyId,
		String friendlyURL) throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Group_ IN CLASS com.liferay.portal.service.persistence.GroupHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (friendlyURL == null) {
				query.append("friendlyURL is null");
			}
			else {
				query.append("friendlyURL = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (friendlyURL != null) {
				q.setString(queryPos++, friendlyURL);
			}

			Iterator itr = q.list().iterator();

			if (!itr.hasNext()) {
				String msg = "No Group exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "companyId=";
				msg += companyId;
				msg += ", ";
				msg += "friendlyURL=";
				msg += friendlyURL;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchGroupException(msg);
			}

			GroupHBM groupHBM = (GroupHBM)itr.next();

			return GroupHBMUtil.model(groupHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Group findByC_C_C(String companyId,
		String className, String classPK)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Group_ IN CLASS com.liferay.portal.service.persistence.GroupHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
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

			if (!itr.hasNext()) {
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
				throw new NoSuchGroupException(msg);
			}

			GroupHBM groupHBM = (GroupHBM)itr.next();

			return GroupHBMUtil.model(groupHBM);
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
				"FROM Group_ IN CLASS com.liferay.portal.service.persistence.GroupHBM ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				GroupHBM groupHBM = (GroupHBM)itr.next();
				list.add(GroupHBMUtil.model(groupHBM));
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

	public void removeByC_F(String companyId, String friendlyURL)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Group_ IN CLASS com.liferay.portal.service.persistence.GroupHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (friendlyURL == null) {
				query.append("friendlyURL is null");
			}
			else {
				query.append("friendlyURL = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (friendlyURL != null) {
				q.setString(queryPos++, friendlyURL);
			}

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				GroupHBM groupHBM = (GroupHBM)itr.next();
				session.delete(groupHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
				String msg = "No Group exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "companyId=";
				msg += companyId;
				msg += ", ";
				msg += "friendlyURL=";
				msg += friendlyURL;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchGroupException(msg);
			}
			else {
				throw new SystemException(he);
			}
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByC_C_C(String companyId, String className, String classPK)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Group_ IN CLASS com.liferay.portal.service.persistence.GroupHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
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

			while (itr.hasNext()) {
				GroupHBM groupHBM = (GroupHBM)itr.next();
				session.delete(groupHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
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
				throw new NoSuchGroupException(msg);
			}
			else {
				throw new SystemException(he);
			}
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
			query.append(
				"FROM Group_ IN CLASS com.liferay.portal.service.persistence.GroupHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (friendlyURL == null) {
				query.append("friendlyURL is null");
			}
			else {
				query.append("friendlyURL = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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
			query.append(
				"FROM Group_ IN CLASS com.liferay.portal.service.persistence.GroupHBM WHERE ");

			if (companyId == null) {
				query.append("companyId is null");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (className == null) {
				query.append("className is null");
			}
			else {
				query.append("className = ?");
			}

			query.append(" AND ");

			if (classPK == null) {
				query.append("classPK is null");
			}
			else {
				query.append("classPK = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
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

	private static Log _log = LogFactory.getLog(GroupPersistence.class);
}