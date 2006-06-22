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

import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.HibernateUtil;

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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <a href="UserGroupPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserGroupPersistence extends BasePersistence {
	public com.liferay.portal.model.UserGroup create(String userGroupId) {
		UserGroupHBM userGroupHBM = new UserGroupHBM();
		userGroupHBM.setNew(true);
		userGroupHBM.setPrimaryKey(userGroupId);

		return UserGroupHBMUtil.model(userGroupHBM);
	}

	public com.liferay.portal.model.UserGroup remove(String userGroupId)
		throws NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					userGroupId);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					userGroupId.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
					userGroupId.toString());
			}

			session.delete(userGroupHBM);
			session.flush();

			return UserGroupHBMUtil.model(userGroupHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.UserGroup update(
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		Session session = null;

		try {
			if (userGroup.isNew() || userGroup.isModified()) {
				session = openSession();

				if (userGroup.isNew()) {
					UserGroupHBM userGroupHBM = new UserGroupHBM();
					userGroupHBM.setUserGroupId(userGroup.getUserGroupId());
					userGroupHBM.setCompanyId(userGroup.getCompanyId());
					userGroupHBM.setParentUserGroupId(userGroup.getParentUserGroupId());
					userGroupHBM.setName(userGroup.getName());
					userGroupHBM.setDescription(userGroup.getDescription());
					userGroupHBM.setUsers(new HashSet());
					session.save(userGroupHBM);
					session.flush();
				}
				else {
					UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
							userGroup.getPrimaryKey());

					if (userGroupHBM != null) {
						userGroupHBM.setCompanyId(userGroup.getCompanyId());
						userGroupHBM.setParentUserGroupId(userGroup.getParentUserGroupId());
						userGroupHBM.setName(userGroup.getName());
						userGroupHBM.setDescription(userGroup.getDescription());
						session.flush();
					}
					else {
						userGroupHBM = new UserGroupHBM();
						userGroupHBM.setUserGroupId(userGroup.getUserGroupId());
						userGroupHBM.setCompanyId(userGroup.getCompanyId());
						userGroupHBM.setParentUserGroupId(userGroup.getParentUserGroupId());
						userGroupHBM.setName(userGroup.getName());
						userGroupHBM.setDescription(userGroup.getDescription());
						session.save(userGroupHBM);
						session.flush();
					}
				}

				userGroup.setNew(false);
				userGroup.setModified(false);
			}

			return userGroup;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List getUsers(String pk)
		throws NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					pk);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					pk.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
					pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT userHBM FROM ");
			query.append(com.liferay.portal.service.persistence.UserGroupHBM.class.getName());
			query.append(" userGroupHBM ");
			query.append("JOIN userGroupHBM.users AS userHBM ");
			query.append("WHERE userGroupHBM.userGroupId = ? ");

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
		throws NoSuchUserGroupException, SystemException {
		return getUsers(pk, begin, end, null);
	}

	public List getUsers(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					pk);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					pk.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
					pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT userHBM FROM ");
			query.append(com.liferay.portal.service.persistence.UserGroupHBM.class.getName());
			query.append(" userGroupHBM ");
			query.append("JOIN userGroupHBM.users AS userHBM ");
			query.append("WHERE userGroupHBM.userGroupId = ? ");

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
			query.append(com.liferay.portal.service.persistence.UserGroupHBM.class.getName());
			query.append(" userGroupHBM ");
			query.append("JOIN userGroupHBM.users AS userHBM ");
			query.append("WHERE userGroupHBM.userGroupId = ? ");

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
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					pk);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					pk.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
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

			userGroupHBM.setUsers(usersSet);
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
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					pk);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					pk.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
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

			userGroupHBM.setUsers(usersSet);
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
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					pk);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					pk.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
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

			boolean value = userGroupHBM.getUsers().add(userHBM);
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
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					pk);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					pk.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
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

			boolean value = userGroupHBM.getUsers().add(userHBM);
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
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					pk);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					pk.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
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

				if (userGroupHBM.getUsers().add(userHBM)) {
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
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					pk);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					pk.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
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

				if (userGroupHBM.getUsers().add(userHBM)) {
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
		throws NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					pk);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					pk.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
					pk.toString());
			}

			userGroupHBM.getUsers().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userGroupId = ? AND userId = ?";

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

	public static final String SQL_CONTAINSUSERS = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userGroupId = ?";

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
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					pk);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					pk.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
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

			boolean value = userGroupHBM.getUsers().remove(userHBM);
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
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					pk);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					pk.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
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

			boolean value = userGroupHBM.getUsers().remove(userHBM);
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
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					pk);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					pk.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
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

				if (userGroupHBM.getUsers().remove(userHBM)) {
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
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					pk);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					pk.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
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

				if (userGroupHBM.getUsers().remove(userHBM)) {
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

	public com.liferay.portal.model.UserGroup findByPrimaryKey(
		String userGroupId) throws NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroupHBM userGroupHBM = (UserGroupHBM)session.get(UserGroupHBM.class,
					userGroupId);

			if (userGroupHBM == null) {
				_log.warn("No UserGroup exists with the primary key " +
					userGroupId.toString());
				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " +
					userGroupId.toString());
			}

			return UserGroupHBMUtil.model(userGroupHBM);
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
				"FROM UserGroup IN CLASS com.liferay.portal.service.persistence.UserGroupHBM ");
			query.append("ORDER BY ");
			query.append("name ASC");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				UserGroupHBM userGroupHBM = (UserGroupHBM)itr.next();
				list.add(UserGroupHBMUtil.model(userGroupHBM));
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

	private static Log _log = LogFactory.getLog(UserGroupPersistence.class);
}