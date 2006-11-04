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
import com.liferay.portal.model.UserGroup;
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
 * <a href="UserGroupPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UserGroupPersistence extends BasePersistence {
	public UserGroup create(String userGroupId) {
		UserGroup userGroup = new UserGroup();
		userGroup.setNew(true);
		userGroup.setPrimaryKey(userGroupId);

		return userGroup;
	}

	public UserGroup remove(String userGroupId)
		throws NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserGroup userGroup = (UserGroup)session.get(UserGroup.class,
					userGroupId);

			if (userGroup == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No UserGroup exists with the primary key " +
						userGroupId);
				}

				throw new NoSuchUserGroupException(
					"No UserGroup exists with the primary key " + userGroupId);
			}

			return remove(userGroup);
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public UserGroup remove(UserGroup userGroup) throws SystemException {
		Session session = null;

		try {
			session = openSession();
			session.delete(userGroup);
			session.flush();
			clearUsers.clear(userGroup.getPrimaryKey());

			return userGroup;
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
		return update(userGroup, false);
	}

	public com.liferay.portal.model.UserGroup update(
		com.liferay.portal.model.UserGroup userGroup, boolean saveOrUpdate)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (saveOrUpdate) {
				session.saveOrUpdate(userGroup);
			}
			else {
				if (userGroup.isNew()) {
					session.save(userGroup);
				}
			}

			session.flush();
			userGroup.setNew(false);

			return userGroup;
		}
		catch (HibernateException he) {
			throw new SystemException(he);
		}
		finally {
			closeSession(session);
		}
	}

	public UserGroup findByPrimaryKey(String userGroupId)
		throws NoSuchUserGroupException, SystemException {
		UserGroup userGroup = fetchByPrimaryKey(userGroupId);

		if (userGroup == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No UserGroup exists with the primary key " +
					userGroupId);
			}

			throw new NoSuchUserGroupException(
				"No UserGroup exists with the primary key " + userGroupId);
		}

		return userGroup;
	}

	public UserGroup fetchByPrimaryKey(String userGroupId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (UserGroup)session.get(UserGroup.class, userGroupId);
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
			query.append("FROM com.liferay.portal.model.UserGroup ");

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

	public List getUsers(String pk)
		throws NoSuchUserGroupException, SystemException {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List getUsers(String pk, int begin, int end)
		throws NoSuchUserGroupException, SystemException {
		return getUsers(pk, begin, end, null);
	}

	public List getUsers(String pk, int begin, int end, OrderByComparator obc)
		throws NoSuchUserGroupException, SystemException {
		Session session = null;

		try {
			session = HibernateUtil.openSession();

			String sql = _SQL_GETUSERS;

			if (obc != null) {
				sql += ("ORDER BY " + obc.getOrderBy());
			}

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
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
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		try {
			addUser.add(pk, userPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addUser(String pk, com.liferay.portal.model.User user)
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		try {
			addUser.add(pk, user.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void addUsers(String pk, String[] userPKs)
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
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

	public void addUsers(String pk, List users)
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
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

	public void clearUsers(String pk)
		throws NoSuchUserGroupException, SystemException {
		try {
			clearUsers.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUser(String pk, String userPK)
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		try {
			removeUser.remove(pk, userPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUser(String pk, com.liferay.portal.model.User user)
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
			SystemException {
		try {
			removeUser.remove(pk, user.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
	}

	public void removeUsers(String pk, String[] userPKs)
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
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

	public void removeUsers(String pk, List users)
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
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

	public void setUsers(String pk, String[] userPKs)
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
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

	public void setUsers(String pk, List users)
		throws NoSuchUserGroupException, com.liferay.portal.NoSuchUserException, 
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
		containsUser = new ContainsUser(this);
		addUser = new AddUser(this);
		clearUsers = new ClearUsers(this);
		removeUser = new RemoveUser(this);
	}

	protected ContainsUser containsUser;
	protected AddUser addUser;
	protected ClearUsers clearUsers;
	protected RemoveUser removeUser;

	protected class ContainsUser extends MappingSqlQuery {
		protected ContainsUser(UserGroupPersistence persistence) {
			super(persistence.getDataSource(), _SQL_CONTAINSUSER);
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(String userGroupId, String userId) {
			List results = execute(new Object[] { userGroupId, userId });

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
		protected AddUser(UserGroupPersistence persistence) {
			super(persistence.getDataSource(),
				"INSERT INTO Users_UserGroups (userGroupId, userId) VALUES (?, ?)");
			_persistence = persistence;
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void add(String userGroupId, String userId) {
			if (!_persistence.containsUser.contains(userGroupId, userId)) {
				update(new Object[] { userGroupId, userId });
			}
		}

		private UserGroupPersistence _persistence;
	}

	protected class ClearUsers extends SqlUpdate {
		protected ClearUsers(UserGroupPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_UserGroups WHERE userGroupId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void clear(String userGroupId) {
			update(new Object[] { userGroupId });
		}
	}

	protected class RemoveUser extends SqlUpdate {
		protected RemoveUser(UserGroupPersistence persistence) {
			super(persistence.getDataSource(),
				"DELETE FROM Users_UserGroups WHERE userGroupId = ? AND userId = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void remove(String userGroupId, String userId) {
			update(new Object[] { userGroupId, userId });
		}
	}

	private static final String _SQL_GETUSERS = "SELECT {User_.*} FROM User_ INNER JOIN Users_UserGroups ON (Users_UserGroups.userGroupId = ?) AND (Users_UserGroups.userId = User_.userId)";
	private static final String _SQL_GETUSERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userGroupId = ?";
	private static final String _SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userGroupId = ? AND userId = ?";
	private static Log _log = LogFactory.getLog(UserGroupPersistence.class);
}