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

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;
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
 * <a href="UserPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserPersistence extends BasePersistence {
	public User create(String userId) {
		User user = new UserImpl();
		user.setNew(true);
		user.setPrimaryKey(userId);

		return user;
	}

	public User remove(String userId)
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			User user = (User)session.get(UserImpl.class, userId);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No User exists with the primary key " + userId);
				}

				throw new NoSuchUserException(
					"No User exists with the primary key " + userId);
			}

			return remove(user);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public User remove(User user) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(user);
			session.flush();
			clearGroups.clear(user.getPrimaryKey());
			clearOrganizations.clear(user.getPrimaryKey());
			clearPermissions.clear(user.getPrimaryKey());
			clearRoles.clear(user.getPrimaryKey());
			clearUserGroups.clear(user.getPrimaryKey());

			return user;
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
		return update(user, false);
	}

	public com.liferay.portal.model.User update(
		com.liferay.portal.model.User user, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(user);
			}
			else {
				if (user.isNew()) {
					session.save(user);
				}
			}

			session.flush();
			user.setNew(false);

			return user;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public User findByPrimaryKey(String userId)
		throws NoSuchUserException, SystemException {
		User user = fetchByPrimaryKey(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No User exists with the primary key " + userId);
			}

			throw new NoSuchUserException(
				"No User exists with the primary key " + userId);
		}

		return user;
	}

	public User fetchByPrimaryKey(String userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (User)session.get(UserImpl.class, userId);
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

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
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

	public List findByCompanyId(String companyId, int begin, int end)
		throws SystemException {
		return findByCompanyId(companyId, begin, end, null);
	}

	public List findByCompanyId(String companyId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
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

	public User findByCompanyId_First(String companyId, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		List list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No User exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchUserException(msg.toString());
		}
		else {
			return (User)list.get(0);
		}
	}

	public User findByCompanyId_Last(String companyId, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		int count = countByCompanyId(companyId);
		List list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No User exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchUserException(msg.toString());
		}
		else {
			return (User)list.get(0);
		}
	}

	public User[] findByCompanyId_PrevAndNext(String userId, String companyId,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);
		int count = countByCompanyId(companyId);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, user);
			User[] array = new UserImpl[3];
			array[0] = (User)objArray[0];
			array[1] = (User)objArray[1];
			array[2] = (User)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public User findByContactId(long contactId)
		throws NoSuchUserException, SystemException {
		User user = fetchByContactId(contactId);

		if (user == null) {
			StringMaker msg = new StringMaker();
			msg.append("No User exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("contactId=");
			msg.append(contactId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByContactId(long contactId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.User WHERE ");
			query.append("contactId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, contactId);

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			User user = (User)list.get(0);

			return user;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public User findByScreenName(String screenName)
		throws NoSuchUserException, SystemException {
		User user = fetchByScreenName(screenName);

		if (user == null) {
			StringMaker msg = new StringMaker();
			msg.append("No User exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("screenName=");
			msg.append(screenName);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByScreenName(String screenName) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (screenName == null) {
				query.append("screenName IS NULL");
			}
			else {
				query.append("screenName = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (screenName != null) {
				q.setString(queryPos++, screenName);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			User user = (User)list.get(0);

			return user;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public User findByC_U(String companyId, String userId)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_U(companyId, userId);

		if (user == null) {
			StringMaker msg = new StringMaker();
			msg.append("No User exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("userId=");
			msg.append(userId);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByC_U(String companyId, String userId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (userId != null) {
				q.setString(queryPos++, userId);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			User user = (User)list.get(0);

			return user;
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

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (password == null) {
				query.append("password_ IS NULL");
			}
			else {
				query.append("password_ = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (password != null) {
				q.setString(queryPos++, password);
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

	public List findByC_P(String companyId, String password, int begin, int end)
		throws SystemException {
		return findByC_P(companyId, password, begin, end, null);
	}

	public List findByC_P(String companyId, String password, int begin,
		int end, OrderByComparator obc) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (password == null) {
				query.append("password_ IS NULL");
			}
			else {
				query.append("password_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (password != null) {
				q.setString(queryPos++, password);
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

	public User findByC_P_First(String companyId, String password,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		List list = findByC_P(companyId, password, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No User exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("password=");
			msg.append(password);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchUserException(msg.toString());
		}
		else {
			return (User)list.get(0);
		}
	}

	public User findByC_P_Last(String companyId, String password,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		int count = countByC_P(companyId, password);
		List list = findByC_P(companyId, password, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();
			msg.append("No User exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("password=");
			msg.append(password);
			msg.append(StringPool.CLOSE_CURLY_BRACE);
			throw new NoSuchUserException(msg.toString());
		}
		else {
			return (User)list.get(0);
		}
	}

	public User[] findByC_P_PrevAndNext(String userId, String companyId,
		String password, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);
		int count = countByC_P(companyId, password);
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (password == null) {
				query.append("password_ IS NULL");
			}
			else {
				query.append("password_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (password != null) {
				q.setString(queryPos++, password);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, user);
			User[] array = new UserImpl[3];
			array[0] = (User)objArray[0];
			array[1] = (User)objArray[1];
			array[2] = (User)objArray[2];

			return array;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public User findByC_EA(String companyId, String emailAddress)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_EA(companyId, emailAddress);

		if (user == null) {
			StringMaker msg = new StringMaker();
			msg.append("No User exists with the key ");
			msg.append(StringPool.OPEN_CURLY_BRACE);
			msg.append("companyId=");
			msg.append(companyId);
			msg.append(", ");
			msg.append("emailAddress=");
			msg.append(emailAddress);
			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByC_EA(String companyId, String emailAddress)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (emailAddress == null) {
				query.append("emailAddress IS NULL");
			}
			else {
				query.append("emailAddress = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (emailAddress != null) {
				q.setString(queryPos++, emailAddress);
			}

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}

			User user = (User)list.get(0);

			return user;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public List findWithDynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);
			query.setLimit(begin, end);

			return query.list();
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

			StringMaker query = new StringMaker();
			query.append("FROM com.liferay.portal.model.User ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
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

	public void removeByCompanyId(String companyId) throws SystemException {
		Iterator itr = findByCompanyId(companyId).iterator();

		while (itr.hasNext()) {
			User user = (User)itr.next();
			remove(user);
		}
	}

	public void removeByContactId(long contactId)
		throws NoSuchUserException, SystemException {
		User user = findByContactId(contactId);
		remove(user);
	}

	public void removeByScreenName(String screenName)
		throws NoSuchUserException, SystemException {
		User user = findByScreenName(screenName);
		remove(user);
	}

	public void removeByC_U(String companyId, String userId)
		throws NoSuchUserException, SystemException {
		User user = findByC_U(companyId, userId);
		remove(user);
	}

	public void removeByC_P(String companyId, String password)
		throws SystemException {
		Iterator itr = findByC_P(companyId, password).iterator();

		while (itr.hasNext()) {
			User user = (User)itr.next();
			remove(user);
		}
	}

	public void removeByC_EA(String companyId, String emailAddress)
		throws NoSuchUserException, SystemException {
		User user = findByC_EA(companyId, emailAddress);
		remove(user);
	}

	public void removeAll() throws SystemException {
		Iterator itr = findAll().iterator();

		while (itr.hasNext()) {
			remove((User)itr.next());
		}
	}

	public int countByCompanyId(String companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
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

	public int countByContactId(long contactId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.User WHERE ");
			query.append("contactId = ?");
			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;
			q.setLong(queryPos++, contactId);

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

	public int countByScreenName(String screenName) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (screenName == null) {
				query.append("screenName IS NULL");
			}
			else {
				query.append("screenName = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (screenName != null) {
				q.setString(queryPos++, screenName);
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

	public int countByC_U(String companyId, String userId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (userId == null) {
				query.append("userId IS NULL");
			}
			else {
				query.append("userId = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (userId != null) {
				q.setString(queryPos++, userId);
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

	public int countByC_P(String companyId, String password)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (password == null) {
				query.append("password_ IS NULL");
			}
			else {
				query.append("password_ = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (password != null) {
				q.setString(queryPos++, password);
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

	public int countByC_EA(String companyId, String emailAddress)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (companyId == null) {
				query.append("companyId IS NULL");
			}
			else {
				query.append("companyId = ?");
			}

			query.append(" AND ");

			if (emailAddress == null) {
				query.append("emailAddress IS NULL");
			}
			else {
				query.append("emailAddress = ?");
			}

			query.append(" ");

			Query q = session.createQuery(query.toString());
			q.setCacheable(true);

			int queryPos = 0;

			if (companyId != null) {
				q.setString(queryPos++, companyId);
			}

			if (emailAddress != null) {
				q.setString(queryPos++, emailAddress);
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

			StringMaker query = new StringMaker();
			query.append("SELECT COUNT(*) ");
			query.append("FROM com.liferay.portal.model.User");

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

	public List getGroups(String pk)
		throws NoSuchUserException, SystemException {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getGroups(String pk, int begin, int end)
		throws NoSuchUserException, SystemException {
		return getGroups(pk, begin, end, null);
	}

	public List getGroups(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringMaker sm = new StringMaker();
			sm.append(_SQL_GETGROUPS);

			if (obc != null) {
				sm.append("ORDER BY ");
				sm.append(obc.getOrderBy());
			}
			else {
				sm.append("ORDER BY ");
				sm.append("Group_.name ASC");
			}

			String sql = sm.toString();
			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
			q.addEntity("Group_", com.liferay.portal.model.impl.GroupImpl.class);

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

	public boolean containsGroup(String pk, long groupPK)
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

	public void addGroup(String pk, long groupPK)
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		try {
			addGroup.add(pk, groupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addGroup(String pk, com.liferay.portal.model.Group group)
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		try {
			addGroup.add(pk, group.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addGroups(String pk, long[] groupPKs)
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
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
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
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
		throws NoSuchUserException, SystemException {
		try {
			clearGroups.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeGroup(String pk, long groupPK)
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		try {
			removeGroup.remove(pk, groupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeGroup(String pk, com.liferay.portal.model.Group group)
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
		try {
			removeGroup.remove(pk, group.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeGroups(String pk, long[] groupPKs)
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
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
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
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

	public void setGroups(String pk, long[] groupPKs)
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
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
		throws NoSuchUserException, com.liferay.portal.NoSuchGroupException, 
			SystemException {
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

	public List getOrganizations(String pk)
		throws NoSuchUserException, SystemException {
		return getOrganizations(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getOrganizations(String pk, int begin, int end)
		throws NoSuchUserException, SystemException {
		return getOrganizations(pk, begin, end, null);
	}

	public List getOrganizations(String pk, int begin, int end,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringMaker sm = new StringMaker();
			sm.append(_SQL_GETORGANIZATIONS);

			if (obc != null) {
				sm.append("ORDER BY ");
				sm.append(obc.getOrderBy());
			}
			else {
				sm.append("ORDER BY ");
				sm.append("Organization_.name ASC");
			}

			String sql = sm.toString();
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

	public int getOrganizationsSize(String pk) throws SystemException {
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

	public boolean containsOrganization(String pk, String organizationPK)
		throws SystemException {
		try {
			return containsOrganization.contains(pk, organizationPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public boolean containsOrganizations(String pk) throws SystemException {
		if (getOrganizationsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addOrganization(String pk, String organizationPK)
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		try {
			addOrganization.add(pk, organizationPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addOrganization(String pk,
		com.liferay.portal.model.Organization organization)
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		try {
			addOrganization.add(pk, organization.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addOrganizations(String pk, String[] organizationPKs)
		throws NoSuchUserException, 
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

	public void addOrganizations(String pk, List organizations)
		throws NoSuchUserException, 
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

	public void clearOrganizations(String pk)
		throws NoSuchUserException, SystemException {
		try {
			clearOrganizations.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeOrganization(String pk, String organizationPK)
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		try {
			removeOrganization.remove(pk, organizationPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeOrganization(String pk,
		com.liferay.portal.model.Organization organization)
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchOrganizationException, SystemException {
		try {
			removeOrganization.remove(pk, organization.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeOrganizations(String pk, String[] organizationPKs)
		throws NoSuchUserException, 
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

	public void removeOrganizations(String pk, List organizations)
		throws NoSuchUserException, 
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

	public void setOrganizations(String pk, String[] organizationPKs)
		throws NoSuchUserException, 
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

	public void setOrganizations(String pk, List organizations)
		throws NoSuchUserException, 
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

	public List getPermissions(String pk)
		throws NoSuchUserException, SystemException {
		return getPermissions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getPermissions(String pk, int begin, int end)
		throws NoSuchUserException, SystemException {
		return getPermissions(pk, begin, end, null);
	}

	public List getPermissions(String pk, int begin, int end,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringMaker sm = new StringMaker();
			sm.append(_SQL_GETPERMISSIONS);

			if (obc != null) {
				sm.append("ORDER BY ");
				sm.append(obc.getOrderBy());
			}

			String sql = sm.toString();
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

	public int getPermissionsSize(String pk) throws SystemException {
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

	public boolean containsPermission(String pk, long permissionPK)
		throws SystemException {
		try {
			return containsPermission.contains(pk, permissionPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public boolean containsPermissions(String pk) throws SystemException {
		if (getPermissionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addPermission(String pk, long permissionPK)
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		try {
			addPermission.add(pk, permissionPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addPermission(String pk,
		com.liferay.portal.model.Permission permission)
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		try {
			addPermission.add(pk, permission.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addPermissions(String pk, long[] permissionPKs)
		throws NoSuchUserException, 
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

	public void addPermissions(String pk, List permissions)
		throws NoSuchUserException, 
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

	public void clearPermissions(String pk)
		throws NoSuchUserException, SystemException {
		try {
			clearPermissions.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removePermission(String pk, long permissionPK)
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		try {
			removePermission.remove(pk, permissionPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removePermission(String pk,
		com.liferay.portal.model.Permission permission)
		throws NoSuchUserException, 
			com.liferay.portal.NoSuchPermissionException, SystemException {
		try {
			removePermission.remove(pk, permission.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removePermissions(String pk, long[] permissionPKs)
		throws NoSuchUserException, 
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

	public void removePermissions(String pk, List permissions)
		throws NoSuchUserException, 
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

	public void setPermissions(String pk, long[] permissionPKs)
		throws NoSuchUserException, 
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

	public void setPermissions(String pk, List permissions)
		throws NoSuchUserException, 
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

	public List getRoles(String pk) throws NoSuchUserException, SystemException {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getRoles(String pk, int begin, int end)
		throws NoSuchUserException, SystemException {
		return getRoles(pk, begin, end, null);
	}

	public List getRoles(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringMaker sm = new StringMaker();
			sm.append(_SQL_GETROLES);

			if (obc != null) {
				sm.append("ORDER BY ");
				sm.append(obc.getOrderBy());
			}
			else {
				sm.append("ORDER BY ");
				sm.append("Role_.name ASC");
			}

			String sql = sm.toString();
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

	public int getRolesSize(String pk) throws SystemException {
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
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		try {
			addRole.add(pk, rolePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addRole(String pk, com.liferay.portal.model.Role role)
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		try {
			addRole.add(pk, role.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addRoles(String pk, String[] rolePKs)
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
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

	public void addRoles(String pk, List roles)
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
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

	public void clearRoles(String pk)
		throws NoSuchUserException, SystemException {
		try {
			clearRoles.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeRole(String pk, String rolePK)
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		try {
			removeRole.remove(pk, rolePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeRole(String pk, com.liferay.portal.model.Role role)
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
			SystemException {
		try {
			removeRole.remove(pk, role.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeRoles(String pk, String[] rolePKs)
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
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

	public void removeRoles(String pk, List roles)
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
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

	public void setRoles(String pk, String[] rolePKs)
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
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

	public void setRoles(String pk, List roles)
		throws NoSuchUserException, com.liferay.portal.NoSuchRoleException, 
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

	public List getUserGroups(String pk)
		throws NoSuchUserException, SystemException {
		return getUserGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getUserGroups(String pk, int begin, int end)
		throws NoSuchUserException, SystemException {
		return getUserGroups(pk, begin, end, null);
	}

	public List getUserGroups(String pk, int begin, int end,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			StringMaker sm = new StringMaker();
			sm.append(_SQL_GETUSERGROUPS);

			if (obc != null) {
				sm.append("ORDER BY ");
				sm.append(obc.getOrderBy());
			}
			else {
				sm.append("ORDER BY ");
				sm.append("UserGroup.name ASC");
			}

			String sql = sm.toString();
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

	public int getUserGroupsSize(String pk) throws SystemException {
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

	public boolean containsUserGroup(String pk, String userGroupPK)
		throws SystemException {
		try {
			return containsUserGroup.contains(pk, userGroupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public boolean containsUserGroups(String pk) throws SystemException {
		if (getUserGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addUserGroup(String pk, String userGroupPK)
		throws NoSuchUserException, com.liferay.portal.NoSuchUserGroupException, 
			SystemException {
		try {
			addUserGroup.add(pk, userGroupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addUserGroup(String pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws NoSuchUserException, com.liferay.portal.NoSuchUserGroupException, 
			SystemException {
		try {
			addUserGroup.add(pk, userGroup.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addUserGroups(String pk, String[] userGroupPKs)
		throws NoSuchUserException, com.liferay.portal.NoSuchUserGroupException, 
			SystemException {
		try {
			for (int i = 0; i < userGroupPKs.length; i++) {
				addUserGroup.add(pk, userGroupPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addUserGroups(String pk, List userGroups)
		throws NoSuchUserException, com.liferay.portal.NoSuchUserGroupException, 
			SystemException {
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

	public void clearUserGroups(String pk)
		throws NoSuchUserException, SystemException {
		try {
			clearUserGroups.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUserGroup(String pk, String userGroupPK)
		throws NoSuchUserException, com.liferay.portal.NoSuchUserGroupException, 
			SystemException {
		try {
			removeUserGroup.remove(pk, userGroupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUserGroup(String pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws NoSuchUserException, com.liferay.portal.NoSuchUserGroupException, 
			SystemException {
		try {
			removeUserGroup.remove(pk, userGroup.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUserGroups(String pk, String[] userGroupPKs)
		throws NoSuchUserException, com.liferay.portal.NoSuchUserGroupException, 
			SystemException {
		try {
			for (int i = 0; i < userGroupPKs.length; i++) {
				removeUserGroup.remove(pk, userGroupPKs[i]);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUserGroups(String pk, List userGroups)
		throws NoSuchUserException, com.liferay.portal.NoSuchUserGroupException, 
			SystemException {
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

	public void setUserGroups(String pk, String[] userGroupPKs)
		throws NoSuchUserException, com.liferay.portal.NoSuchUserGroupException, 
			SystemException {
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

	public void setUserGroups(String pk, List userGroups)
		throws NoSuchUserException, com.liferay.portal.NoSuchUserGroupException, 
			SystemException {
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

	protected void initDao() {
		containsGroup = new ContainsGroup(this);
		addGroup = new AddGroup(this);
		clearGroups = new ClearGroups(this);
		removeGroup = new RemoveGroup(this);
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
	}

	protected ContainsGroup containsGroup;
	protected AddGroup addGroup;
	protected ClearGroups clearGroups;
	protected RemoveGroup removeGroup;
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

	protected class ContainsGroup extends MappingSqlQuery {
		protected ContainsGroup(UserPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSGROUP);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(String userId, long groupId) {
			List results = execute(new Object[] { userId, new Long(groupId) });

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
		protected AddGroup(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO Users_Groups (userId, groupId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected void add(String userId, long groupId) {
			if (!_persistence.containsGroup.contains(userId, groupId)) {
				update(new Object[] { userId, new Long(groupId) });
			}
		}

		private UserPersistence _persistence;
	}

	protected class ClearGroups extends SqlUpdate {
		protected ClearGroups(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_Groups WHERE userId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void clear(String userId) {
			update(new Object[] { userId });
		}
	}

	protected class RemoveGroup extends SqlUpdate {
		protected RemoveGroup(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_Groups WHERE userId = ? AND groupId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected void remove(String userId, long groupId) {
			update(new Object[] { userId, new Long(groupId) });
		}
	}

	protected class ContainsOrganization extends MappingSqlQuery {
		protected ContainsOrganization(UserPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSORGANIZATION);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(String userId, String organizationId) {
			List results = execute(new Object[] { userId, organizationId });

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
		protected AddOrganization(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO Users_Orgs (userId, organizationId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void add(String userId, String organizationId) {
			if (!_persistence.containsOrganization.contains(userId,
						organizationId)) {
				update(new Object[] { userId, organizationId });
			}
		}

		private UserPersistence _persistence;
	}

	protected class ClearOrganizations extends SqlUpdate {
		protected ClearOrganizations(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_Orgs WHERE userId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void clear(String userId) {
			update(new Object[] { userId });
		}
	}

	protected class RemoveOrganization extends SqlUpdate {
		protected RemoveOrganization(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_Orgs WHERE userId = ? AND organizationId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void remove(String userId, String organizationId) {
			update(new Object[] { userId, organizationId });
		}
	}

	protected class ContainsPermission extends MappingSqlQuery {
		protected ContainsPermission(UserPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSPERMISSION);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(String userId, long permissionId) {
			List results = execute(new Object[] { userId, new Long(permissionId) });

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
		protected AddPermission(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO Users_Permissions (userId, permissionId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected void add(String userId, long permissionId) {
			if (!_persistence.containsPermission.contains(userId, permissionId)) {
				update(new Object[] { userId, new Long(permissionId) });
			}
		}

		private UserPersistence _persistence;
	}

	protected class ClearPermissions extends SqlUpdate {
		protected ClearPermissions(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_Permissions WHERE userId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void clear(String userId) {
			update(new Object[] { userId });
		}
	}

	protected class RemovePermission extends SqlUpdate {
		protected RemovePermission(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_Permissions WHERE userId = ? AND permissionId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.BIGINT));
			compile();
		}

		protected void remove(String userId, long permissionId) {
			update(new Object[] { userId, new Long(permissionId) });
		}
	}

	protected class ContainsRole extends MappingSqlQuery {
		protected ContainsRole(UserPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSROLE);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(String userId, String roleId) {
			List results = execute(new Object[] { userId, roleId });

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
		protected AddRole(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO Users_Roles (userId, roleId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void add(String userId, String roleId) {
			if (!_persistence.containsRole.contains(userId, roleId)) {
				update(new Object[] { userId, roleId });
			}
		}

		private UserPersistence _persistence;
	}

	protected class ClearRoles extends SqlUpdate {
		protected ClearRoles(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_Roles WHERE userId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void clear(String userId) {
			update(new Object[] { userId });
		}
	}

	protected class RemoveRole extends SqlUpdate {
		protected RemoveRole(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_Roles WHERE userId = ? AND roleId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void remove(String userId, String roleId) {
			update(new Object[] { userId, roleId });
		}
	}

	protected class ContainsUserGroup extends MappingSqlQuery {
		protected ContainsUserGroup(UserPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSUSERGROUP);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(String userId, String userGroupId) {
			List results = execute(new Object[] { userId, userGroupId });

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
		protected AddUserGroup(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO Users_UserGroups (userId, userGroupId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void add(String userId, String userGroupId) {
			if (!_persistence.containsUserGroup.contains(userId, userGroupId)) {
				update(new Object[] { userId, userGroupId });
			}
		}

		private UserPersistence _persistence;
	}

	protected class ClearUserGroups extends SqlUpdate {
		protected ClearUserGroups(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_UserGroups WHERE userId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void clear(String userId) {
			update(new Object[] { userId });
		}
	}

	protected class RemoveUserGroup extends SqlUpdate {
		protected RemoveUserGroup(UserPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_UserGroups WHERE userId = ? AND userGroupId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void remove(String userId, String userGroupId) {
			update(new Object[] { userId, userGroupId });
		}
	}

	private static final String _SQL_GETGROUPS = "SELECT {Group_.*} FROM Group_ INNER JOIN Users_Groups ON (Users_Groups.groupId = Group_.groupId) WHERE (Users_Groups.userId = ?)";
	private static final String _SQL_GETGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE userId = ?";
	private static final String _SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE userId = ? AND groupId = ?";
	private static final String _SQL_GETORGANIZATIONS = "SELECT {Organization_.*} FROM Organization_ INNER JOIN Users_Orgs ON (Users_Orgs.organizationId = Organization_.organizationId) WHERE (Users_Orgs.userId = ?)";
	private static final String _SQL_GETORGANIZATIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE userId = ?";
	private static final String _SQL_CONTAINSORGANIZATION = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE userId = ? AND organizationId = ?";
	private static final String _SQL_GETPERMISSIONS = "SELECT {Permission_.*} FROM Permission_ INNER JOIN Users_Permissions ON (Users_Permissions.permissionId = Permission_.permissionId) WHERE (Users_Permissions.userId = ?)";
	private static final String _SQL_GETPERMISSIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE userId = ?";
	private static final String _SQL_CONTAINSPERMISSION = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE userId = ? AND permissionId = ?";
	private static final String _SQL_GETROLES = "SELECT {Role_.*} FROM Role_ INNER JOIN Users_Roles ON (Users_Roles.roleId = Role_.roleId) WHERE (Users_Roles.userId = ?)";
	private static final String _SQL_GETROLESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE userId = ?";
	private static final String _SQL_CONTAINSROLE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE userId = ? AND roleId = ?";
	private static final String _SQL_GETUSERGROUPS = "SELECT {UserGroup.*} FROM UserGroup INNER JOIN Users_UserGroups ON (Users_UserGroups.userGroupId = UserGroup.userGroupId) WHERE (Users_UserGroups.userId = ?)";
	private static final String _SQL_GETUSERGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userId = ?";
	private static final String _SQL_CONTAINSUSERGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userId = ? AND userGroupId = ?";
	private static Log _log = LogFactory.getLog(UserPersistence.class);
}