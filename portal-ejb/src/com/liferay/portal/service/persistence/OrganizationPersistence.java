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

import com.liferay.portal.NoSuchOrganizationException;
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
 * <a href="OrganizationPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrganizationPersistence extends BasePersistence {
	public com.liferay.portal.model.Organization create(String organizationId) {
		OrganizationHBM organizationHBM = new OrganizationHBM();
		organizationHBM.setNew(true);
		organizationHBM.setPrimaryKey(organizationId);

		return OrganizationHBMUtil.model(organizationHBM);
	}

	public com.liferay.portal.model.Organization remove(String organizationId)
		throws NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					organizationId);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					organizationId.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
					organizationId.toString());
			}

			session.delete(organizationHBM);
			session.flush();

			return OrganizationHBMUtil.model(organizationHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Organization update(
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		Session session = null;

		try {
			if (organization.isNew() || organization.isModified()) {
				session = openSession();

				if (organization.isNew()) {
					OrganizationHBM organizationHBM = new OrganizationHBM();
					organizationHBM.setOrganizationId(organization.getOrganizationId());
					organizationHBM.setCompanyId(organization.getCompanyId());
					organizationHBM.setParentOrganizationId(organization.getParentOrganizationId());
					organizationHBM.setName(organization.getName());
					organizationHBM.setRecursable(organization.getRecursable());
					organizationHBM.setRegionId(organization.getRegionId());
					organizationHBM.setCountryId(organization.getCountryId());
					organizationHBM.setStatusId(organization.getStatusId());
					organizationHBM.setComments(organization.getComments());
					organizationHBM.setUsers(new HashSet());
					session.save(organizationHBM);
					session.flush();
				}
				else {
					OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
							organization.getPrimaryKey());

					if (organizationHBM != null) {
						organizationHBM.setCompanyId(organization.getCompanyId());
						organizationHBM.setParentOrganizationId(organization.getParentOrganizationId());
						organizationHBM.setName(organization.getName());
						organizationHBM.setRecursable(organization.getRecursable());
						organizationHBM.setRegionId(organization.getRegionId());
						organizationHBM.setCountryId(organization.getCountryId());
						organizationHBM.setStatusId(organization.getStatusId());
						organizationHBM.setComments(organization.getComments());
						session.flush();
					}
					else {
						organizationHBM = new OrganizationHBM();
						organizationHBM.setOrganizationId(organization.getOrganizationId());
						organizationHBM.setCompanyId(organization.getCompanyId());
						organizationHBM.setParentOrganizationId(organization.getParentOrganizationId());
						organizationHBM.setName(organization.getName());
						organizationHBM.setRecursable(organization.getRecursable());
						organizationHBM.setRegionId(organization.getRegionId());
						organizationHBM.setCountryId(organization.getCountryId());
						organizationHBM.setStatusId(organization.getStatusId());
						organizationHBM.setComments(organization.getComments());
						session.save(organizationHBM);
						session.flush();
					}
				}

				organization.setNew(false);
				organization.setModified(false);
			}

			return organization;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List getUsers(String pk)
		throws NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					pk);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					pk.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
					pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT userHBM FROM ");
			query.append(com.liferay.portal.service.persistence.OrganizationHBM.class.getName());
			query.append(" organizationHBM ");
			query.append("JOIN organizationHBM.users AS userHBM ");
			query.append("WHERE organizationHBM.organizationId = ? ");

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
		throws NoSuchOrganizationException, SystemException {
		return getUsers(pk, begin, end, null);
	}

	public List getUsers(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					pk);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					pk.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
					pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT userHBM FROM ");
			query.append(com.liferay.portal.service.persistence.OrganizationHBM.class.getName());
			query.append(" organizationHBM ");
			query.append("JOIN organizationHBM.users AS userHBM ");
			query.append("WHERE organizationHBM.organizationId = ? ");

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
			query.append(com.liferay.portal.service.persistence.OrganizationHBM.class.getName());
			query.append(" organizationHBM ");
			query.append("JOIN organizationHBM.users AS userHBM ");
			query.append("WHERE organizationHBM.organizationId = ? ");

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
		throws NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					pk);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					pk.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
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

			organizationHBM.setUsers(usersSet);
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
		throws NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					pk);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					pk.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
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

			organizationHBM.setUsers(usersSet);
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
		throws NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					pk);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					pk.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
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

			boolean value = organizationHBM.getUsers().add(userHBM);
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
		throws NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					pk);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					pk.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
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

			boolean value = organizationHBM.getUsers().add(userHBM);
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
		throws NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					pk);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					pk.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
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

				if (organizationHBM.getUsers().add(userHBM)) {
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
		throws NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					pk);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					pk.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
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

				if (organizationHBM.getUsers().add(userHBM)) {
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
		throws NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					pk);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					pk.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
					pk.toString());
			}

			organizationHBM.getUsers().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE organizationId = ? AND userId = ?";

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

	public static final String SQL_CONTAINSUSERS = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE organizationId = ?";

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
		throws NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					pk);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					pk.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
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

			boolean value = organizationHBM.getUsers().remove(userHBM);
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
		throws NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					pk);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					pk.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
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

			boolean value = organizationHBM.getUsers().remove(userHBM);
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
		throws NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					pk);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					pk.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
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

				if (organizationHBM.getUsers().remove(userHBM)) {
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
		throws NoSuchOrganizationException, 
			com.liferay.portal.NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					pk);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					pk.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
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

				if (organizationHBM.getUsers().remove(userHBM)) {
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

	public com.liferay.portal.model.Organization findByPrimaryKey(
		String organizationId)
		throws NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrganizationHBM organizationHBM = (OrganizationHBM)session.get(OrganizationHBM.class,
					organizationId);

			if (organizationHBM == null) {
				_log.warn("No Organization exists with the primary key " +
					organizationId.toString());
				throw new NoSuchOrganizationException(
					"No Organization exists with the primary key " +
					organizationId.toString());
			}

			return OrganizationHBMUtil.model(organizationHBM);
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
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
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
				OrganizationHBM organizationHBM = (OrganizationHBM)itr.next();
				list.add(OrganizationHBMUtil.model(organizationHBM));
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
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
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
				OrganizationHBM organizationHBM = (OrganizationHBM)itr.next();
				list.add(OrganizationHBMUtil.model(organizationHBM));
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

	public com.liferay.portal.model.Organization findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Organization exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrganizationException(msg);
		}
		else {
			return (com.liferay.portal.model.Organization)list.get(0);
		}
	}

	public com.liferay.portal.model.Organization findByCompanyId_Last(
		String companyId, OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Organization exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrganizationException(msg);
		}
		else {
			return (com.liferay.portal.model.Organization)list.get(0);
		}
	}

	public com.liferay.portal.model.Organization[] findByCompanyId_PrevAndNext(
		String organizationId, String companyId, OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		com.liferay.portal.model.Organization organization = findByPrimaryKey(organizationId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					organization, OrganizationHBMUtil.getInstance());
			com.liferay.portal.model.Organization[] array = new com.liferay.portal.model.Organization[3];
			array[0] = (com.liferay.portal.model.Organization)objArray[0];
			array[1] = (com.liferay.portal.model.Organization)objArray[1];
			array[2] = (com.liferay.portal.model.Organization)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByLocations(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND parentOrganizationId != '-1' ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				OrganizationHBM organizationHBM = (OrganizationHBM)itr.next();
				list.add(OrganizationHBMUtil.model(organizationHBM));
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

	public List findByLocations(String companyId, int begin, int end)
		throws SystemException {
		return findByLocations(companyId, begin, end, null);
	}

	public List findByLocations(String companyId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND parentOrganizationId != '-1' ");

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
				OrganizationHBM organizationHBM = (OrganizationHBM)itr.next();
				list.add(OrganizationHBMUtil.model(organizationHBM));
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

	public com.liferay.portal.model.Organization findByLocations_First(
		String companyId, OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		List list = findByLocations(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Organization exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrganizationException(msg);
		}
		else {
			return (com.liferay.portal.model.Organization)list.get(0);
		}
	}

	public com.liferay.portal.model.Organization findByLocations_Last(
		String companyId, OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		int count = countByLocations(companyId);
		List list = findByLocations(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No Organization exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrganizationException(msg);
		}
		else {
			return (com.liferay.portal.model.Organization)list.get(0);
		}
	}

	public com.liferay.portal.model.Organization[] findByLocations_PrevAndNext(
		String organizationId, String companyId, OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		com.liferay.portal.model.Organization organization = findByPrimaryKey(organizationId);
		int count = countByLocations(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND parentOrganizationId != '-1' ");

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

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					organization, OrganizationHBMUtil.getInstance());
			com.liferay.portal.model.Organization[] array = new com.liferay.portal.model.Organization[3];
			array[0] = (com.liferay.portal.model.Organization)objArray[0];
			array[1] = (com.liferay.portal.model.Organization)objArray[1];
			array[2] = (com.liferay.portal.model.Organization)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_P(String companyId, String parentOrganizationId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("parentOrganizationId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, parentOrganizationId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				OrganizationHBM organizationHBM = (OrganizationHBM)itr.next();
				list.add(OrganizationHBMUtil.model(organizationHBM));
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

	public List findByC_P(String companyId, String parentOrganizationId,
		int begin, int end) throws SystemException {
		return findByC_P(companyId, parentOrganizationId, begin, end, null);
	}

	public List findByC_P(String companyId, String parentOrganizationId,
		int begin, int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("parentOrganizationId = ?");
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
			q.setString(queryPos++, parentOrganizationId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				OrganizationHBM organizationHBM = (OrganizationHBM)itr.next();
				list.add(OrganizationHBMUtil.model(organizationHBM));
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

	public com.liferay.portal.model.Organization findByC_P_First(
		String companyId, String parentOrganizationId, OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		List list = findByC_P(companyId, parentOrganizationId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No Organization exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "parentOrganizationId=";
			msg += parentOrganizationId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrganizationException(msg);
		}
		else {
			return (com.liferay.portal.model.Organization)list.get(0);
		}
	}

	public com.liferay.portal.model.Organization findByC_P_Last(
		String companyId, String parentOrganizationId, OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		int count = countByC_P(companyId, parentOrganizationId);
		List list = findByC_P(companyId, parentOrganizationId, count - 1,
				count, obc);

		if (list.size() == 0) {
			String msg = "No Organization exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "parentOrganizationId=";
			msg += parentOrganizationId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchOrganizationException(msg);
		}
		else {
			return (com.liferay.portal.model.Organization)list.get(0);
		}
	}

	public com.liferay.portal.model.Organization[] findByC_P_PrevAndNext(
		String organizationId, String companyId, String parentOrganizationId,
		OrderByComparator obc)
		throws NoSuchOrganizationException, SystemException {
		com.liferay.portal.model.Organization organization = findByPrimaryKey(organizationId);
		int count = countByC_P(companyId, parentOrganizationId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("parentOrganizationId = ?");
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
			q.setString(queryPos++, parentOrganizationId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					organization, OrganizationHBMUtil.getInstance());
			com.liferay.portal.model.Organization[] array = new com.liferay.portal.model.Organization[3];
			array[0] = (com.liferay.portal.model.Organization)objArray[0];
			array[1] = (com.liferay.portal.model.Organization)objArray[1];
			array[2] = (com.liferay.portal.model.Organization)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.Organization findByC_N(String companyId,
		String name) throws NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("name = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, name);

			Iterator itr = q.list().iterator();

			if (!itr.hasNext()) {
				String msg = "No Organization exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "companyId=";
				msg += companyId;
				msg += ", ";
				msg += "name=";
				msg += name;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchOrganizationException(msg);
			}

			OrganizationHBM organizationHBM = (OrganizationHBM)itr.next();

			return OrganizationHBMUtil.model(organizationHBM);
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
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				OrganizationHBM organizationHBM = (OrganizationHBM)itr.next();
				list.add(OrganizationHBMUtil.model(organizationHBM));
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
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				OrganizationHBM organizationHBM = (OrganizationHBM)itr.next();
				session.delete(organizationHBM);
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

	public void removeByLocations(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND parentOrganizationId != '-1' ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				OrganizationHBM organizationHBM = (OrganizationHBM)itr.next();
				session.delete(organizationHBM);
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

	public void removeByC_P(String companyId, String parentOrganizationId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("parentOrganizationId = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, parentOrganizationId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				OrganizationHBM organizationHBM = (OrganizationHBM)itr.next();
				session.delete(organizationHBM);
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

	public void removeByC_N(String companyId, String name)
		throws NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("name = ?");
			query.append(" ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, name);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				OrganizationHBM organizationHBM = (OrganizationHBM)itr.next();
				session.delete(organizationHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
				String msg = "No Organization exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "companyId=";
				msg += companyId;
				msg += ", ";
				msg += "name=";
				msg += name;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchOrganizationException(msg);
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
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

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

	public int countByLocations(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND parentOrganizationId != '-1' ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

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

	public int countByC_P(String companyId, String parentOrganizationId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("parentOrganizationId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, parentOrganizationId);

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

	public int countByC_N(String companyId, String name)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM Organization_ IN CLASS com.liferay.portal.service.persistence.OrganizationHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("name = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, name);

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

	private static Log _log = LogFactory.getLog(OrganizationPersistence.class);
}