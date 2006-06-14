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

import com.liferay.portal.NoSuchUserException;
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
 * <a href="UserPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserPersistence extends BasePersistence {
	public com.liferay.portal.model.User create(String userId) {
		UserHBM userHBM = new UserHBM();
		userHBM.setNew(true);
		userHBM.setPrimaryKey(userId);

		return UserHBMUtil.model(userHBM);
	}

	public com.liferay.portal.model.User remove(String userId)
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, userId);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					userId.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + userId.toString());
			}

			session.delete(userHBM);
			session.flush();

			return UserHBMUtil.model(userHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.User update(
		com.liferay.portal.model.User user) throws SystemException {
		Session session = null;

		try {
			if (user.isNew() || user.isModified()) {
				session = openSession();

				if (user.isNew()) {
					UserHBM userHBM = new UserHBM();
					userHBM.setUserId(user.getUserId());
					userHBM.setCompanyId(user.getCompanyId());
					userHBM.setCreateDate(user.getCreateDate());
					userHBM.setModifiedDate(user.getModifiedDate());
					userHBM.setContactId(user.getContactId());
					userHBM.setPassword(user.getPassword());
					userHBM.setPasswordEncrypted(user.getPasswordEncrypted());
					userHBM.setPasswordExpirationDate(user.getPasswordExpirationDate());
					userHBM.setPasswordReset(user.getPasswordReset());
					userHBM.setEmailAddress(user.getEmailAddress());
					userHBM.setLanguageId(user.getLanguageId());
					userHBM.setTimeZoneId(user.getTimeZoneId());
					userHBM.setGreeting(user.getGreeting());
					userHBM.setResolution(user.getResolution());
					userHBM.setComments(user.getComments());
					userHBM.setLoginDate(user.getLoginDate());
					userHBM.setLoginIP(user.getLoginIP());
					userHBM.setLastLoginDate(user.getLastLoginDate());
					userHBM.setLastLoginIP(user.getLastLoginIP());
					userHBM.setFailedLoginAttempts(user.getFailedLoginAttempts());
					userHBM.setAgreedToTermsOfUse(user.getAgreedToTermsOfUse());
					userHBM.setActive(user.getActive());
					userHBM.setGroups(new HashSet());
					userHBM.setOrgs(new HashSet());
					userHBM.setPermissions(new HashSet());
					userHBM.setRoles(new HashSet());
					session.save(userHBM);
					session.flush();
				}
				else {
					UserHBM userHBM = (UserHBM)session.get(UserHBM.class,
							user.getPrimaryKey());

					if (userHBM != null) {
						userHBM.setCompanyId(user.getCompanyId());
						userHBM.setCreateDate(user.getCreateDate());
						userHBM.setModifiedDate(user.getModifiedDate());
						userHBM.setContactId(user.getContactId());
						userHBM.setPassword(user.getPassword());
						userHBM.setPasswordEncrypted(user.getPasswordEncrypted());
						userHBM.setPasswordExpirationDate(user.getPasswordExpirationDate());
						userHBM.setPasswordReset(user.getPasswordReset());
						userHBM.setEmailAddress(user.getEmailAddress());
						userHBM.setLanguageId(user.getLanguageId());
						userHBM.setTimeZoneId(user.getTimeZoneId());
						userHBM.setGreeting(user.getGreeting());
						userHBM.setResolution(user.getResolution());
						userHBM.setComments(user.getComments());
						userHBM.setLoginDate(user.getLoginDate());
						userHBM.setLoginIP(user.getLoginIP());
						userHBM.setLastLoginDate(user.getLastLoginDate());
						userHBM.setLastLoginIP(user.getLastLoginIP());
						userHBM.setFailedLoginAttempts(user.getFailedLoginAttempts());
						userHBM.setAgreedToTermsOfUse(user.getAgreedToTermsOfUse());
						userHBM.setActive(user.getActive());
						session.flush();
					}
					else {
						userHBM = new UserHBM();
						userHBM.setUserId(user.getUserId());
						userHBM.setCompanyId(user.getCompanyId());
						userHBM.setCreateDate(user.getCreateDate());
						userHBM.setModifiedDate(user.getModifiedDate());
						userHBM.setContactId(user.getContactId());
						userHBM.setPassword(user.getPassword());
						userHBM.setPasswordEncrypted(user.getPasswordEncrypted());
						userHBM.setPasswordExpirationDate(user.getPasswordExpirationDate());
						userHBM.setPasswordReset(user.getPasswordReset());
						userHBM.setEmailAddress(user.getEmailAddress());
						userHBM.setLanguageId(user.getLanguageId());
						userHBM.setTimeZoneId(user.getTimeZoneId());
						userHBM.setGreeting(user.getGreeting());
						userHBM.setResolution(user.getResolution());
						userHBM.setComments(user.getComments());
						userHBM.setLoginDate(user.getLoginDate());
						userHBM.setLoginIP(user.getLoginIP());
						userHBM.setLastLoginDate(user.getLastLoginDate());
						userHBM.setLastLoginIP(user.getLastLoginIP());
						userHBM.setFailedLoginAttempts(user.getFailedLoginAttempts());
						userHBM.setAgreedToTermsOfUse(user.getAgreedToTermsOfUse());
						userHBM.setActive(user.getActive());
						session.save(userHBM);
						session.flush();
					}
				}

				user.setNew(false);
				user.setModified(false);
			}

			return user;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List getGroups(String pk)
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT groupHBM FROM ");
			query.append(com.liferay.portal.service.persistence.UserHBM.class.getName());
			query.append(" userHBM ");
			query.append("JOIN userHBM.groups AS groupHBM ");
			query.append("WHERE userHBM.userId = ? ");
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
		throws NoSuchUserException, SystemException {
		return getGroups(pk, begin, end, null);
	}

	public List getGroups(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT groupHBM FROM ");
			query.append(com.liferay.portal.service.persistence.UserHBM.class.getName());
			query.append(" userHBM ");
			query.append("JOIN userHBM.groups AS groupHBM ");
			query.append("WHERE userHBM.userId = ? ");

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
			query.append(com.liferay.portal.service.persistence.UserHBM.class.getName());
			query.append(" userHBM ");
			query.append("JOIN userHBM.groups AS groupHBM ");
			query.append("WHERE userHBM.userId = ? ");

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
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			userHBM.setGroups(groupsSet);
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
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			userHBM.setGroups(groupsSet);
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
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getGroups().add(groupHBM);
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
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getGroups().add(groupHBM);
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
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getGroups().add(groupHBM)) {
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
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getGroups().add(groupHBM)) {
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
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
			}

			userHBM.getGroups().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE userId = ? AND groupId = ?";

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

	public static final String SQL_CONTAINSGROUPS = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE userId = ?";

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
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getGroups().remove(groupHBM);
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
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getGroups().remove(groupHBM);
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
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getGroups().remove(groupHBM)) {
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
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getGroups().remove(groupHBM)) {
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

	public List getOrganizations(String pk)
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT organizationHBM FROM ");
			query.append(com.liferay.portal.service.persistence.UserHBM.class.getName());
			query.append(" userHBM ");
			query.append("JOIN userHBM.orgs AS organizationHBM ");
			query.append("WHERE userHBM.userId = ? ");
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
		throws NoSuchUserException, SystemException {
		return getOrganizations(pk, begin, end, null);
	}

	public List getOrganizations(String pk, int begin, int end,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT organizationHBM FROM ");
			query.append(com.liferay.portal.service.persistence.UserHBM.class.getName());
			query.append(" userHBM ");
			query.append("JOIN userHBM.orgs AS organizationHBM ");
			query.append("WHERE userHBM.userId = ? ");

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
			query.append(com.liferay.portal.service.persistence.UserHBM.class.getName());
			query.append(" userHBM ");
			query.append("JOIN userHBM.orgs AS organizationHBM ");
			query.append("WHERE userHBM.userId = ? ");

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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			userHBM.setOrgs(orgsSet);
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			userHBM.setOrgs(orgsSet);
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getOrgs().add(organizationHBM);
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getOrgs().add(organizationHBM);
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getOrgs().add(organizationHBM)) {
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getOrgs().add(organizationHBM)) {
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
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
			}

			userHBM.getOrgs().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSORGANIZATION = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE userId = ? AND organizationId = ?";

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

	public static final String SQL_CONTAINSORGANIZATIONS = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE userId = ?";

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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getOrgs().remove(organizationHBM);
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getOrgs().remove(organizationHBM);
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getOrgs().remove(organizationHBM)) {
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getOrgs().remove(organizationHBM)) {
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
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT permissionHBM FROM ");
			query.append(com.liferay.portal.service.persistence.UserHBM.class.getName());
			query.append(" userHBM ");
			query.append("JOIN userHBM.permissions AS permissionHBM ");
			query.append("WHERE userHBM.userId = ? ");

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
		throws NoSuchUserException, SystemException {
		return getPermissions(pk, begin, end, null);
	}

	public List getPermissions(String pk, int begin, int end,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT permissionHBM FROM ");
			query.append(com.liferay.portal.service.persistence.UserHBM.class.getName());
			query.append(" userHBM ");
			query.append("JOIN userHBM.permissions AS permissionHBM ");
			query.append("WHERE userHBM.userId = ? ");

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
			query.append(com.liferay.portal.service.persistence.UserHBM.class.getName());
			query.append(" userHBM ");
			query.append("JOIN userHBM.permissions AS permissionHBM ");
			query.append("WHERE userHBM.userId = ? ");

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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			userHBM.setPermissions(permissionsSet);
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			userHBM.setPermissions(permissionsSet);
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getPermissions().add(permissionHBM);
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getPermissions().add(permissionHBM);
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getPermissions().add(permissionHBM)) {
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getPermissions().add(permissionHBM)) {
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
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
			}

			userHBM.getPermissions().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSPERMISSION = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE userId = ? AND permissionId = ?";

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

	public static final String SQL_CONTAINSPERMISSIONS = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE userId = ?";

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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getPermissions().remove(permissionHBM);
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getPermissions().remove(permissionHBM);
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getPermissions().remove(permissionHBM)) {
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
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getPermissions().remove(permissionHBM)) {
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

	public List getRoles(String pk) throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT roleHBM FROM ");
			query.append(com.liferay.portal.service.persistence.UserHBM.class.getName());
			query.append(" userHBM ");
			query.append("JOIN userHBM.roles AS roleHBM ");
			query.append("WHERE userHBM.userId = ? ");
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
		throws NoSuchUserException, SystemException {
		return getRoles(pk, begin, end, null);
	}

	public List getRoles(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
			}

			StringBuffer query = new StringBuffer();
			query.append("SELECT roleHBM FROM ");
			query.append(com.liferay.portal.service.persistence.UserHBM.class.getName());
			query.append(" userHBM ");
			query.append("JOIN userHBM.roles AS roleHBM ");
			query.append("WHERE userHBM.userId = ? ");

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
			query.append(com.liferay.portal.service.persistence.UserHBM.class.getName());
			query.append(" userHBM ");
			query.append("JOIN userHBM.roles AS roleHBM ");
			query.append("WHERE userHBM.userId = ? ");

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
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			userHBM.setRoles(rolesSet);
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
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			userHBM.setRoles(rolesSet);
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
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getRoles().add(roleHBM);
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
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getRoles().add(roleHBM);
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
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getRoles().add(roleHBM)) {
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
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getRoles().add(roleHBM)) {
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
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
			}

			userHBM.getRoles().clear();
			session.flush();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public static final String SQL_CONTAINSROLE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE userId = ? AND roleId = ?";

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

	public static final String SQL_CONTAINSROLES = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE userId = ?";

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
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getRoles().remove(roleHBM);
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
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

			boolean value = userHBM.getRoles().remove(roleHBM);
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
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getRoles().remove(roleHBM)) {
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
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, pk);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					pk.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + pk.toString());
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

				if (userHBM.getRoles().remove(roleHBM)) {
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

	public com.liferay.portal.model.User findByPrimaryKey(String userId)
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserHBM userHBM = (UserHBM)session.get(UserHBM.class, userId);

			if (userHBM == null) {
				_log.warn("No User exists with the primary key " +
					userId.toString());
				throw new NoSuchUserException(
					"No User exists with the primary key " + userId.toString());
			}

			return UserHBMUtil.model(userHBM);
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
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				UserHBM userHBM = (UserHBM)itr.next();
				list.add(UserHBMUtil.model(userHBM));
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
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				UserHBM userHBM = (UserHBM)itr.next();
				list.add(UserHBMUtil.model(userHBM));
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

	public com.liferay.portal.model.User findByCompanyId_First(
		String companyId, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No User exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchUserException(msg);
		}
		else {
			return (com.liferay.portal.model.User)list.get(0);
		}
	}

	public com.liferay.portal.model.User findByCompanyId_Last(
		String companyId, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No User exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchUserException(msg);
		}
		else {
			return (com.liferay.portal.model.User)list.get(0);
		}
	}

	public com.liferay.portal.model.User[] findByCompanyId_PrevAndNext(
		String userId, String companyId, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		com.liferay.portal.model.User user = findByPrimaryKey(userId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, user,
					UserHBMUtil.getInstance());
			com.liferay.portal.model.User[] array = new com.liferay.portal.model.User[3];
			array[0] = (com.liferay.portal.model.User)objArray[0];
			array[1] = (com.liferay.portal.model.User)objArray[1];
			array[2] = (com.liferay.portal.model.User)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.User findByC_U(String companyId,
		String userId) throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("userId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();

			if (!itr.hasNext()) {
				String msg = "No User exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "companyId=";
				msg += companyId;
				msg += ", ";
				msg += "userId=";
				msg += userId;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchUserException(msg);
			}

			UserHBM userHBM = (UserHBM)itr.next();

			return UserHBMUtil.model(userHBM);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findByC_P(String companyId, String password)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("password_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, password);

			Iterator itr = q.list().iterator();
			List list = new ArrayList();

			while (itr.hasNext()) {
				UserHBM userHBM = (UserHBM)itr.next();
				list.add(UserHBMUtil.model(userHBM));
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

	public List findByC_P(String companyId, String password, int begin, int end)
		throws SystemException {
		return findByC_P(companyId, password, begin, end, null);
	}

	public List findByC_P(String companyId, String password, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("password_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, password);

			List list = new ArrayList();
			Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);

			while (itr.hasNext()) {
				UserHBM userHBM = (UserHBM)itr.next();
				list.add(UserHBMUtil.model(userHBM));
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

	public com.liferay.portal.model.User findByC_P_First(String companyId,
		String password, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		List list = findByC_P(companyId, password, 0, 1, obc);

		if (list.size() == 0) {
			String msg = "No User exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "password=";
			msg += password;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchUserException(msg);
		}
		else {
			return (com.liferay.portal.model.User)list.get(0);
		}
	}

	public com.liferay.portal.model.User findByC_P_Last(String companyId,
		String password, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		int count = countByC_P(companyId, password);
		List list = findByC_P(companyId, password, count - 1, count, obc);

		if (list.size() == 0) {
			String msg = "No User exists with the key ";
			msg += StringPool.OPEN_CURLY_BRACE;
			msg += "companyId=";
			msg += companyId;
			msg += ", ";
			msg += "password=";
			msg += password;
			msg += StringPool.CLOSE_CURLY_BRACE;
			throw new NoSuchUserException(msg);
		}
		else {
			return (com.liferay.portal.model.User)list.get(0);
		}
	}

	public com.liferay.portal.model.User[] findByC_P_PrevAndNext(
		String userId, String companyId, String password, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		com.liferay.portal.model.User user = findByPrimaryKey(userId);
		int count = countByC_P(companyId, password);
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("password_ = ?");
			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY " + obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, password);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, user,
					UserHBMUtil.getInstance());
			com.liferay.portal.model.User[] array = new com.liferay.portal.model.User[3];
			array[0] = (com.liferay.portal.model.User)objArray[0];
			array[1] = (com.liferay.portal.model.User)objArray[1];
			array[2] = (com.liferay.portal.model.User)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public com.liferay.portal.model.User findByC_EA(String companyId,
		String emailAddress) throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("emailAddress = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, emailAddress);

			Iterator itr = q.list().iterator();

			if (!itr.hasNext()) {
				String msg = "No User exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "companyId=";
				msg += companyId;
				msg += ", ";
				msg += "emailAddress=";
				msg += emailAddress;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchUserException(msg);
			}

			UserHBM userHBM = (UserHBM)itr.next();

			return UserHBMUtil.model(userHBM);
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
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM ");

			Query q = session.createQuery(query.toString());
			Iterator itr = q.iterate();
			List list = new ArrayList();

			while (itr.hasNext()) {
				UserHBM userHBM = (UserHBM)itr.next();
				list.add(UserHBMUtil.model(userHBM));
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
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				UserHBM userHBM = (UserHBM)itr.next();
				session.delete(userHBM);
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

	public void removeByC_U(String companyId, String userId)
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("userId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, userId);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				UserHBM userHBM = (UserHBM)itr.next();
				session.delete(userHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
				String msg = "No User exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "companyId=";
				msg += companyId;
				msg += ", ";
				msg += "userId=";
				msg += userId;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchUserException(msg);
			}
			else {
				throw new SystemException(he);
			}
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByC_P(String companyId, String password)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("password_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, password);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				UserHBM userHBM = (UserHBM)itr.next();
				session.delete(userHBM);
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

	public void removeByC_EA(String companyId, String emailAddress)
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append(
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("emailAddress = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, emailAddress);

			Iterator itr = q.list().iterator();

			while (itr.hasNext()) {
				UserHBM userHBM = (UserHBM)itr.next();
				session.delete(userHBM);
			}

			session.flush();
		}
		catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
				String msg = "No User exists with the key ";
				msg += StringPool.OPEN_CURLY_BRACE;
				msg += "companyId=";
				msg += companyId;
				msg += ", ";
				msg += "emailAddress=";
				msg += emailAddress;
				msg += StringPool.CLOSE_CURLY_BRACE;
				throw new NoSuchUserException(msg);
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
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
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

	public int countByC_U(String companyId, String userId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("userId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, userId);

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

	public int countByC_P(String companyId, String password)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("password_ = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, password);

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

	public int countByC_EA(String companyId, String emailAddress)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBuffer query = new StringBuffer();
			query.append("SELECT COUNT(*) ");
			query.append(
				"FROM User_ IN CLASS com.liferay.portal.service.persistence.UserHBM WHERE ");
			query.append("companyId = ?");
			query.append(" AND ");
			query.append("emailAddress = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			int queryPos = 0;
			q.setString(queryPos++, companyId);
			q.setString(queryPos++, emailAddress);

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

	private static Log _log = LogFactory.getLog(UserPersistence.class);
}