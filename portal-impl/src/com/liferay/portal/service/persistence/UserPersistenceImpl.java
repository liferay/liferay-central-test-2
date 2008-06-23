/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.model.impl.UserModelImpl;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Hibernate;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="UserPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserPersistenceImpl extends BasePersistence
	implements UserPersistence {
	public User create(long userId) {
		User user = new UserImpl();

		user.setNew(true);
		user.setPrimaryKey(userId);

		String uuid = PortalUUIDUtil.generate();

		user.setUuid(uuid);

		return user;
	}

	public User remove(long userId) throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			User user = (User)session.get(UserImpl.class, new Long(userId));

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No User exists with the primary key " + userId);
				}

				throw new NoSuchUserException(
					"No User exists with the primary key " + userId);
			}

			return remove(user);
		}
		catch (NoSuchUserException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public User remove(User user) throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(user);
			}
		}

		user = removeImpl(user);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(user);
			}
		}

		return user;
	}

	protected User removeImpl(User user) throws SystemException {
		try {
			clearGroups.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			FinderCache.clearCache("Users_Groups");
		}

		try {
			clearOrganizations.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			FinderCache.clearCache("Users_Orgs");
		}

		try {
			clearPermissions.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			FinderCache.clearCache("Users_Permissions");
		}

		try {
			clearRoles.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			FinderCache.clearCache("Users_Roles");
		}

		try {
			clearUserGroups.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			FinderCache.clearCache("Users_UserGroups");
		}

		Session session = null;

		try {
			session = openSession();

			session.delete(user);

			session.flush();

			return user;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(User.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(User user, boolean merge)</code>.
	 */
	public User update(User user) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(User user) method. Use update(User user, boolean merge) instead.");
		}

		return update(user, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        user the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when user is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public User update(User user, boolean merge) throws SystemException {
		boolean isNew = user.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(user);
				}
				else {
					listener.onBeforeUpdate(user);
				}
			}
		}

		user = updateImpl(user, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(user);
				}
				else {
					listener.onAfterUpdate(user);
				}
			}
		}

		return user;
	}

	public User updateImpl(com.liferay.portal.model.User user, boolean merge)
		throws SystemException {
		FinderCache.clearCache("Users_Groups");
		FinderCache.clearCache("Users_Orgs");
		FinderCache.clearCache("Users_Permissions");
		FinderCache.clearCache("Users_Roles");
		FinderCache.clearCache("Users_UserGroups");

		if (Validator.isNull(user.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			user.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(user);
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
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(User.class.getName());
		}
	}

	public User findByPrimaryKey(long userId)
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

	public User fetchByPrimaryKey(long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (User)session.get(UserImpl.class, new Long(userId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<User> findByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "findByUuid";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { uuid };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<User> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<User>)result;
		}
	}

	public List<User> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<User> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "findByUuid";
		String[] finderParams = new String[] {
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				List<User> list = (List<User>)QueryUtil.list(q, getDialect(),
						start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<User>)result;
		}
	}

	public User findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		List<User> list = findByUuid(uuid, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No User exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		int count = countByUuid(uuid);

		List<User> list = findByUuid(uuid, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No User exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User[] findByUuid_PrevAndNext(long userId, String uuid,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (uuid == null) {
				query.append("uuid_ IS NULL");
			}
			else {
				query.append("uuid_ = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, user);

			User[] array = new UserImpl[3];

			array[0] = (User)objArray[0];
			array[1] = (User)objArray[1];
			array[2] = (User)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<User> findByCompanyId(long companyId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<User> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<User>)result;
		}
	}

	public List<User> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<User> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<User> list = (List<User>)QueryUtil.list(q, getDialect(),
						start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<User>)result;
		}
	}

	public User findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		List<User> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		int count = countByCompanyId(companyId);

		List<User> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User[] findByCompanyId_PrevAndNext(long userId, long companyId,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.User WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, user);

			User[] array = new UserImpl[3];

			array[0] = (User)objArray[0];
			array[1] = (User)objArray[1];
			array[2] = (User)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
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

			msg.append("No User exists with the key {");

			msg.append("contactId=" + contactId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByContactId(long contactId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "fetchByContactId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(contactId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("contactId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(contactId);

				List<User> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<User> list = (List<User>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<User> findByEmailAddress(String emailAddress)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "findByEmailAddress";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { emailAddress };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				List<User> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<User>)result;
		}
	}

	public List<User> findByEmailAddress(String emailAddress, int start, int end)
		throws SystemException {
		return findByEmailAddress(emailAddress, start, end, null);
	}

	public List<User> findByEmailAddress(String emailAddress, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "findByEmailAddress";
		String[] finderParams = new String[] {
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				emailAddress,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				List<User> list = (List<User>)QueryUtil.list(q, getDialect(),
						start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<User>)result;
		}
	}

	public User findByEmailAddress_First(String emailAddress,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		List<User> list = findByEmailAddress(emailAddress, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No User exists with the key {");

			msg.append("emailAddress=" + emailAddress);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User findByEmailAddress_Last(String emailAddress,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		int count = countByEmailAddress(emailAddress);

		List<User> list = findByEmailAddress(emailAddress, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No User exists with the key {");

			msg.append("emailAddress=" + emailAddress);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User[] findByEmailAddress_PrevAndNext(long userId,
		String emailAddress, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		int count = countByEmailAddress(emailAddress);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.User WHERE ");

			if (emailAddress == null) {
				query.append("emailAddress IS NULL");
			}
			else {
				query.append("emailAddress = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (emailAddress != null) {
				qPos.add(emailAddress);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, user);

			User[] array = new UserImpl[3];

			array[0] = (User)objArray[0];
			array[1] = (User)objArray[1];
			array[2] = (User)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public User findByOpenId(String openId)
		throws NoSuchUserException, SystemException {
		User user = fetchByOpenId(openId);

		if (user == null) {
			StringMaker msg = new StringMaker();

			msg.append("No User exists with the key {");

			msg.append("openId=" + openId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByOpenId(String openId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "fetchByOpenId";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { openId };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (openId == null) {
					query.append("openId IS NULL");
				}
				else {
					query.append("openId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (openId != null) {
					qPos.add(openId);
				}

				List<User> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<User> list = (List<User>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public User findByPortraitId(long portraitId)
		throws NoSuchUserException, SystemException {
		User user = fetchByPortraitId(portraitId);

		if (user == null) {
			StringMaker msg = new StringMaker();

			msg.append("No User exists with the key {");

			msg.append("portraitId=" + portraitId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByPortraitId(long portraitId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "fetchByPortraitId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(portraitId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("portraitId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(portraitId);

				List<User> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<User> list = (List<User>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public User findByC_U(long companyId, long userId)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_U(companyId, userId);

		if (user == null) {
			StringMaker msg = new StringMaker();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByC_U(long companyId, long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "fetchByC_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				List<User> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<User> list = (List<User>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public User findByC_DU(long companyId, boolean defaultUser)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_DU(companyId, defaultUser);

		if (user == null) {
			StringMaker msg = new StringMaker();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("defaultUser=" + defaultUser);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByC_DU(long companyId, boolean defaultUser)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "fetchByC_DU";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(defaultUser)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("defaultUser = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultUser);

				List<User> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<User> list = (List<User>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<User> findByC_P(long companyId, String password)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "findByC_P";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), password };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (password == null) {
					query.append("password_ IS NULL");
				}
				else {
					query.append("password_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (password != null) {
					qPos.add(password);
				}

				List<User> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<User>)result;
		}
	}

	public List<User> findByC_P(long companyId, String password, int start,
		int end) throws SystemException {
		return findByC_P(companyId, password, start, end, null);
	}

	public List<User> findByC_P(long companyId, String password, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "findByC_P";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				password,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

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

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (password != null) {
					qPos.add(password);
				}

				List<User> list = (List<User>)QueryUtil.list(q, getDialect(),
						start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<User>)result;
		}
	}

	public User findByC_P_First(long companyId, String password,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		List<User> list = findByC_P(companyId, password, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("password=" + password);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User findByC_P_Last(long companyId, String password,
		OrderByComparator obc) throws NoSuchUserException, SystemException {
		int count = countByC_P(companyId, password);

		List<User> list = findByC_P(companyId, password, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("password=" + password);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public User[] findByC_P_PrevAndNext(long userId, long companyId,
		String password, OrderByComparator obc)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		int count = countByC_P(companyId, password);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.User WHERE ");

			query.append("companyId = ?");

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

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (password != null) {
				qPos.add(password);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, user);

			User[] array = new UserImpl[3];

			array[0] = (User)objArray[0];
			array[1] = (User)objArray[1];
			array[2] = (User)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public User findByC_SN(long companyId, String screenName)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_SN(companyId, screenName);

		if (user == null) {
			StringMaker msg = new StringMaker();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("screenName=" + screenName);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByC_SN(long companyId, String screenName)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "fetchByC_SN";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), screenName };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (screenName == null) {
					query.append("screenName IS NULL");
				}
				else {
					query.append("screenName = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (screenName != null) {
					qPos.add(screenName);
				}

				List<User> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<User> list = (List<User>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public User findByC_EA(long companyId, String emailAddress)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_EA(companyId, emailAddress);

		if (user == null) {
			StringMaker msg = new StringMaker();

			msg.append("No User exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("emailAddress=" + emailAddress);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	public User fetchByC_EA(long companyId, String emailAddress)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "fetchByC_EA";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), emailAddress };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				List<User> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<User> list = (List<User>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<User> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<User> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer, int start, int end)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			query.setLimit(start, end);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<User> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<User> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<User> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
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

				List<User> list = (List<User>)QueryUtil.list(q, getDialect(),
						start, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<User>)result;
		}
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (User user : findByUuid(uuid)) {
			remove(user);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (User user : findByCompanyId(companyId)) {
			remove(user);
		}
	}

	public void removeByContactId(long contactId)
		throws NoSuchUserException, SystemException {
		User user = findByContactId(contactId);

		remove(user);
	}

	public void removeByEmailAddress(String emailAddress)
		throws SystemException {
		for (User user : findByEmailAddress(emailAddress)) {
			remove(user);
		}
	}

	public void removeByOpenId(String openId)
		throws NoSuchUserException, SystemException {
		User user = findByOpenId(openId);

		remove(user);
	}

	public void removeByPortraitId(long portraitId)
		throws NoSuchUserException, SystemException {
		User user = findByPortraitId(portraitId);

		remove(user);
	}

	public void removeByC_U(long companyId, long userId)
		throws NoSuchUserException, SystemException {
		User user = findByC_U(companyId, userId);

		remove(user);
	}

	public void removeByC_DU(long companyId, boolean defaultUser)
		throws NoSuchUserException, SystemException {
		User user = findByC_DU(companyId, defaultUser);

		remove(user);
	}

	public void removeByC_P(long companyId, String password)
		throws SystemException {
		for (User user : findByC_P(companyId, password)) {
			remove(user);
		}
	}

	public void removeByC_SN(long companyId, String screenName)
		throws NoSuchUserException, SystemException {
		User user = findByC_SN(companyId, screenName);

		remove(user);
	}

	public void removeByC_EA(long companyId, String emailAddress)
		throws NoSuchUserException, SystemException {
		User user = findByC_EA(companyId, emailAddress);

		remove(user);
	}

	public void removeAll() throws SystemException {
		for (User user : findAll()) {
			remove(user);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByUuid";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { uuid };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (uuid == null) {
					query.append("uuid_ IS NULL");
				}
				else {
					query.append("uuid_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByContactId(long contactId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByContactId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(contactId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("contactId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(contactId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByEmailAddress(String emailAddress)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByEmailAddress";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { emailAddress };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByOpenId(String openId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByOpenId";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { openId };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				if (openId == null) {
					query.append("openId IS NULL");
				}
				else {
					query.append("openId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (openId != null) {
					qPos.add(openId);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByPortraitId(long portraitId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByPortraitId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(portraitId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("portraitId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(portraitId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByC_U(long companyId, long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByC_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByC_DU(long companyId, boolean defaultUser)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByC_DU";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), Boolean.valueOf(defaultUser)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("defaultUser = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultUser);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByC_P(long companyId, String password)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByC_P";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), password };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (password == null) {
					query.append("password_ IS NULL");
				}
				else {
					query.append("password_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (password != null) {
					qPos.add(password);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByC_SN(long companyId, String screenName)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByC_SN";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), screenName };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (screenName == null) {
					query.append("screenName IS NULL");
				}
				else {
					query.append("screenName = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (screenName != null) {
					qPos.add(screenName);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByC_EA(long companyId, String emailAddress)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countByC_EA";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(companyId), emailAddress };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append("FROM com.liferay.portal.model.User WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				if (emailAddress == null) {
					query.append("emailAddress IS NULL");
				}
				else {
					query.append("emailAddress = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (emailAddress != null) {
					qPos.add(emailAddress);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED;
		String finderClassName = User.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portal.model.User");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public List<com.liferay.portal.model.Group> getGroups(long pk)
		throws SystemException {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end) throws SystemException {
		return getGroups(pk, start, end, null);
	}

	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_GROUPS;

		String finderClassName = "Users_Groups";

		String finderMethodName = "getGroups";
		String[] finderParams = new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
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

				q.addEntity("Group_",
					com.liferay.portal.model.impl.GroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portal.model.Group> list = (List<com.liferay.portal.model.Group>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<com.liferay.portal.model.Group>)result;
		}
	}

	public int getGroupsSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_GROUPS;

		String finderClassName = "Users_Groups";

		String finderMethodName = "getGroupsSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETGROUPSSIZE);

				q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public boolean containsGroup(long pk, long groupPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_GROUPS;

		String finderClassName = "Users_Groups";

		String finderMethodName = "containsGroups";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(pk), new Long(groupPK) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsGroup.contains(pk,
							groupPK));

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (DataAccessException dae) {
				throw new SystemException(dae);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
		}
	}

	public boolean containsGroups(long pk) throws SystemException {
		if (getGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addGroup(long pk, long groupPK) throws SystemException {
		try {
			addGroup.add(pk, groupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Groups");
		}
	}

	public void addGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			addGroup.add(pk, group.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Groups");
		}
	}

	public void addGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Groups");
		}
	}

	public void addGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				addGroup.add(pk, group.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Groups");
		}
	}

	public void clearGroups(long pk) throws SystemException {
		try {
			clearGroups.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Groups");
		}
	}

	public void removeGroup(long pk, long groupPK) throws SystemException {
		try {
			removeGroup.remove(pk, groupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Groups");
		}
	}

	public void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			removeGroup.remove(pk, group.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Groups");
		}
	}

	public void removeGroups(long pk, long[] groupPKs)
		throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				removeGroup.remove(pk, groupPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Groups");
		}
	}

	public void removeGroups(long pk,
		List<com.liferay.portal.model.Group> groups) throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				removeGroup.remove(pk, group.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Groups");
		}
	}

	public void setGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			clearGroups.clear(pk);

			for (long groupPK : groupPKs) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Groups");
		}
	}

	public void setGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			clearGroups.clear(pk);

			for (com.liferay.portal.model.Group group : groups) {
				addGroup.add(pk, group.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Groups");
		}
	}

	public List<com.liferay.portal.model.Organization> getOrganizations(long pk)
		throws SystemException {
		return getOrganizations(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end) throws SystemException {
		return getOrganizations(pk, start, end, null);
	}

	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_ORGS;

		String finderClassName = "Users_Orgs";

		String finderMethodName = "getOrganizations";
		String[] finderParams = new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
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

				q.addEntity("Organization_",
					com.liferay.portal.model.impl.OrganizationImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portal.model.Organization> list = (List<com.liferay.portal.model.Organization>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<com.liferay.portal.model.Organization>)result;
		}
	}

	public int getOrganizationsSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_ORGS;

		String finderClassName = "Users_Orgs";

		String finderMethodName = "getOrganizationsSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETORGANIZATIONSSIZE);

				q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public boolean containsOrganization(long pk, long organizationPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_ORGS;

		String finderClassName = "Users_Orgs";

		String finderMethodName = "containsOrganizations";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(pk),
				
				new Long(organizationPK)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsOrganization.contains(
							pk, organizationPK));

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (DataAccessException dae) {
				throw new SystemException(dae);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
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

	public void addOrganization(long pk, long organizationPK)
		throws SystemException {
		try {
			addOrganization.add(pk, organizationPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Orgs");
		}
	}

	public void addOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		try {
			addOrganization.add(pk, organization.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Orgs");
		}
	}

	public void addOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			for (long organizationPK : organizationPKs) {
				addOrganization.add(pk, organizationPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Orgs");
		}
	}

	public void addOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Organization organization : organizations) {
				addOrganization.add(pk, organization.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Orgs");
		}
	}

	public void clearOrganizations(long pk) throws SystemException {
		try {
			clearOrganizations.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Orgs");
		}
	}

	public void removeOrganization(long pk, long organizationPK)
		throws SystemException {
		try {
			removeOrganization.remove(pk, organizationPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Orgs");
		}
	}

	public void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		try {
			removeOrganization.remove(pk, organization.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Orgs");
		}
	}

	public void removeOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			for (long organizationPK : organizationPKs) {
				removeOrganization.remove(pk, organizationPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Orgs");
		}
	}

	public void removeOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Organization organization : organizations) {
				removeOrganization.remove(pk, organization.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Orgs");
		}
	}

	public void setOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			clearOrganizations.clear(pk);

			for (long organizationPK : organizationPKs) {
				addOrganization.add(pk, organizationPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Orgs");
		}
	}

	public void setOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			clearOrganizations.clear(pk);

			for (com.liferay.portal.model.Organization organization : organizations) {
				addOrganization.add(pk, organization.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Orgs");
		}
	}

	public List<com.liferay.portal.model.Permission> getPermissions(long pk)
		throws SystemException {
		return getPermissions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Permission> getPermissions(long pk,
		int start, int end) throws SystemException {
		return getPermissions(pk, start, end, null);
	}

	public List<com.liferay.portal.model.Permission> getPermissions(long pk,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_PERMISSIONS;

		String finderClassName = "Users_Permissions";

		String finderMethodName = "getPermissions";
		String[] finderParams = new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
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

				q.addEntity("Permission_",
					com.liferay.portal.model.impl.PermissionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portal.model.Permission> list = (List<com.liferay.portal.model.Permission>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<com.liferay.portal.model.Permission>)result;
		}
	}

	public int getPermissionsSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_PERMISSIONS;

		String finderClassName = "Users_Permissions";

		String finderMethodName = "getPermissionsSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETPERMISSIONSSIZE);

				q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public boolean containsPermission(long pk, long permissionPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_PERMISSIONS;

		String finderClassName = "Users_Permissions";

		String finderMethodName = "containsPermissions";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(pk), new Long(permissionPK) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsPermission.contains(
							pk, permissionPK));

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (DataAccessException dae) {
				throw new SystemException(dae);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
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
		throws SystemException {
		try {
			addPermission.add(pk, permissionPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Permissions");
		}
	}

	public void addPermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws SystemException {
		try {
			addPermission.add(pk, permission.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Permissions");
		}
	}

	public void addPermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			for (long permissionPK : permissionPKs) {
				addPermission.add(pk, permissionPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Permissions");
		}
	}

	public void addPermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Permission permission : permissions) {
				addPermission.add(pk, permission.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Permissions");
		}
	}

	public void clearPermissions(long pk) throws SystemException {
		try {
			clearPermissions.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Permissions");
		}
	}

	public void removePermission(long pk, long permissionPK)
		throws SystemException {
		try {
			removePermission.remove(pk, permissionPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Permissions");
		}
	}

	public void removePermission(long pk,
		com.liferay.portal.model.Permission permission)
		throws SystemException {
		try {
			removePermission.remove(pk, permission.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Permissions");
		}
	}

	public void removePermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			for (long permissionPK : permissionPKs) {
				removePermission.remove(pk, permissionPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Permissions");
		}
	}

	public void removePermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Permission permission : permissions) {
				removePermission.remove(pk, permission.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Permissions");
		}
	}

	public void setPermissions(long pk, long[] permissionPKs)
		throws SystemException {
		try {
			clearPermissions.clear(pk);

			for (long permissionPK : permissionPKs) {
				addPermission.add(pk, permissionPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Permissions");
		}
	}

	public void setPermissions(long pk,
		List<com.liferay.portal.model.Permission> permissions)
		throws SystemException {
		try {
			clearPermissions.clear(pk);

			for (com.liferay.portal.model.Permission permission : permissions) {
				addPermission.add(pk, permission.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Permissions");
		}
	}

	public List<com.liferay.portal.model.Role> getRoles(long pk)
		throws SystemException {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end) throws SystemException {
		return getRoles(pk, start, end, null);
	}

	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_ROLES;

		String finderClassName = "Users_Roles";

		String finderMethodName = "getRoles";
		String[] finderParams = new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
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

				q.addEntity("Role_",
					com.liferay.portal.model.impl.RoleImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portal.model.Role> list = (List<com.liferay.portal.model.Role>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<com.liferay.portal.model.Role>)result;
		}
	}

	public int getRolesSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_ROLES;

		String finderClassName = "Users_Roles";

		String finderMethodName = "getRolesSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETROLESSIZE);

				q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public boolean containsRole(long pk, long rolePK) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_ROLES;

		String finderClassName = "Users_Roles";

		String finderMethodName = "containsRoles";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(pk), new Long(rolePK) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsRole.contains(pk, rolePK));

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (DataAccessException dae) {
				throw new SystemException(dae);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
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

	public void addRole(long pk, long rolePK) throws SystemException {
		try {
			addRole.add(pk, rolePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Roles");
		}
	}

	public void addRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			addRole.add(pk, role.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Roles");
		}
	}

	public void addRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				addRole.add(pk, rolePK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Roles");
		}
	}

	public void addRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				addRole.add(pk, role.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Roles");
		}
	}

	public void clearRoles(long pk) throws SystemException {
		try {
			clearRoles.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Roles");
		}
	}

	public void removeRole(long pk, long rolePK) throws SystemException {
		try {
			removeRole.remove(pk, rolePK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Roles");
		}
	}

	public void removeRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			removeRole.remove(pk, role.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Roles");
		}
	}

	public void removeRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				removeRole.remove(pk, rolePK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Roles");
		}
	}

	public void removeRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				removeRole.remove(pk, role.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Roles");
		}
	}

	public void setRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			clearRoles.clear(pk);

			for (long rolePK : rolePKs) {
				addRole.add(pk, rolePK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Roles");
		}
	}

	public void setRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			clearRoles.clear(pk);

			for (com.liferay.portal.model.Role role : roles) {
				addRole.add(pk, role.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_Roles");
		}
	}

	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk)
		throws SystemException {
		return getUserGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end) throws SystemException {
		return getUserGroups(pk, start, end, null);
	}

	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_USERGROUPS;

		String finderClassName = "Users_UserGroups";

		String finderMethodName = "getUserGroups";
		String[] finderParams = new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
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

				q.addEntity("UserGroup",
					com.liferay.portal.model.impl.UserGroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				List<com.liferay.portal.model.UserGroup> list = (List<com.liferay.portal.model.UserGroup>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<com.liferay.portal.model.UserGroup>)result;
		}
	}

	public int getUserGroupsSize(long pk) throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_USERGROUPS;

		String finderClassName = "Users_UserGroups";

		String finderMethodName = "getUserGroupsSize";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(pk) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERGROUPSSIZE);

				q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public boolean containsUserGroup(long pk, long userGroupPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserModelImpl.CACHE_ENABLED_USERS_USERGROUPS;

		String finderClassName = "Users_UserGroups";

		String finderMethodName = "containsUserGroups";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(pk), new Long(userGroupPK) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			try {
				Boolean value = Boolean.valueOf(containsUserGroup.contains(pk,
							userGroupPK));

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, value);

				return value.booleanValue();
			}
			catch (DataAccessException dae) {
				throw new SystemException(dae);
			}
		}
		else {
			return ((Boolean)result).booleanValue();
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

	public void addUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			addUserGroup.add(pk, userGroupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_UserGroups");
		}
	}

	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			addUserGroup.add(pk, userGroup.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_UserGroups");
		}
	}

	public void addUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_UserGroups");
		}
	}

	public void addUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				addUserGroup.add(pk, userGroup.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_UserGroups");
		}
	}

	public void clearUserGroups(long pk) throws SystemException {
		try {
			clearUserGroups.clear(pk);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_UserGroups");
		}
	}

	public void removeUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroupPK);
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_UserGroups");
		}
	}

	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroup.getPrimaryKey());
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_UserGroups");
		}
	}

	public void removeUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				removeUserGroup.remove(pk, userGroupPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_UserGroups");
		}
	}

	public void removeUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				removeUserGroup.remove(pk, userGroup.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_UserGroups");
		}
	}

	public void setUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			clearUserGroups.clear(pk);

			for (long userGroupPK : userGroupPKs) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_UserGroups");
		}
	}

	public void setUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			clearUserGroups.clear(pk);

			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				addUserGroup.add(pk, userGroup.getPrimaryKey());
			}
		}
		catch (DataAccessException dae) {
			throw new SystemException(dae);
		}
		finally {
			FinderCache.clearCache("Users_UserGroups");
		}
	}

	public void registerListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.add(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	public void unregisterListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.remove(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	protected void initDao() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.User")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener> listeners = new ArrayList<ModelListener>();

				for (String listenerClassName : listenerClassNames) {
					listeners.add((ModelListener)Class.forName(
							listenerClassName).newInstance());
				}

				_listeners = listeners.toArray(new ModelListener[listeners.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

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
		protected ContainsGroup(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(), _SQL_CONTAINSGROUP);

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long userId, long groupId) {
			List<Integer> results = execute(new Object[] {
						new Long(userId), new Long(groupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddGroup extends SqlUpdate {
		protected AddGroup(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"INSERT INTO Users_Groups (userId, groupId) VALUES (?, ?)");

			_persistenceImpl = persistenceImpl;

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void add(long userId, long groupId) {
			if (!_persistenceImpl.containsGroup.contains(userId, groupId)) {
				update(new Object[] { new Long(userId), new Long(groupId) });
			}
		}

		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearGroups extends SqlUpdate {
		protected ClearGroups(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM Users_Groups WHERE userId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void clear(long userId) {
			update(new Object[] { new Long(userId) });
		}
	}

	protected class RemoveGroup extends SqlUpdate {
		protected RemoveGroup(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM Users_Groups WHERE userId = ? AND groupId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void remove(long userId, long groupId) {
			update(new Object[] { new Long(userId), new Long(groupId) });
		}
	}

	protected class ContainsOrganization extends MappingSqlQuery {
		protected ContainsOrganization(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(), _SQL_CONTAINSORGANIZATION);

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long userId, long organizationId) {
			List<Integer> results = execute(new Object[] {
						new Long(userId), new Long(organizationId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddOrganization extends SqlUpdate {
		protected AddOrganization(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"INSERT INTO Users_Orgs (userId, organizationId) VALUES (?, ?)");

			_persistenceImpl = persistenceImpl;

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void add(long userId, long organizationId) {
			if (!_persistenceImpl.containsOrganization.contains(userId,
						organizationId)) {
				update(new Object[] { new Long(userId), new Long(organizationId) });
			}
		}

		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearOrganizations extends SqlUpdate {
		protected ClearOrganizations(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM Users_Orgs WHERE userId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void clear(long userId) {
			update(new Object[] { new Long(userId) });
		}
	}

	protected class RemoveOrganization extends SqlUpdate {
		protected RemoveOrganization(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM Users_Orgs WHERE userId = ? AND organizationId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void remove(long userId, long organizationId) {
			update(new Object[] { new Long(userId), new Long(organizationId) });
		}
	}

	protected class ContainsPermission extends MappingSqlQuery {
		protected ContainsPermission(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(), _SQL_CONTAINSPERMISSION);

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long userId, long permissionId) {
			List<Integer> results = execute(new Object[] {
						new Long(userId), new Long(permissionId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddPermission extends SqlUpdate {
		protected AddPermission(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"INSERT INTO Users_Permissions (userId, permissionId) VALUES (?, ?)");

			_persistenceImpl = persistenceImpl;

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void add(long userId, long permissionId) {
			if (!_persistenceImpl.containsPermission.contains(userId,
						permissionId)) {
				update(new Object[] { new Long(userId), new Long(permissionId) });
			}
		}

		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearPermissions extends SqlUpdate {
		protected ClearPermissions(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM Users_Permissions WHERE userId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void clear(long userId) {
			update(new Object[] { new Long(userId) });
		}
	}

	protected class RemovePermission extends SqlUpdate {
		protected RemovePermission(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM Users_Permissions WHERE userId = ? AND permissionId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void remove(long userId, long permissionId) {
			update(new Object[] { new Long(userId), new Long(permissionId) });
		}
	}

	protected class ContainsRole extends MappingSqlQuery {
		protected ContainsRole(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(), _SQL_CONTAINSROLE);

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long userId, long roleId) {
			List<Integer> results = execute(new Object[] {
						new Long(userId), new Long(roleId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddRole extends SqlUpdate {
		protected AddRole(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"INSERT INTO Users_Roles (userId, roleId) VALUES (?, ?)");

			_persistenceImpl = persistenceImpl;

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void add(long userId, long roleId) {
			if (!_persistenceImpl.containsRole.contains(userId, roleId)) {
				update(new Object[] { new Long(userId), new Long(roleId) });
			}
		}

		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearRoles extends SqlUpdate {
		protected ClearRoles(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM Users_Roles WHERE userId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void clear(long userId) {
			update(new Object[] { new Long(userId) });
		}
	}

	protected class RemoveRole extends SqlUpdate {
		protected RemoveRole(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM Users_Roles WHERE userId = ? AND roleId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void remove(long userId, long roleId) {
			update(new Object[] { new Long(userId), new Long(roleId) });
		}
	}

	protected class ContainsUserGroup extends MappingSqlQuery {
		protected ContainsUserGroup(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(), _SQL_CONTAINSUSERGROUP);

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNumber)
			throws SQLException {
			return new Integer(rs.getInt("COUNT_VALUE"));
		}

		protected boolean contains(long userId, long userGroupId) {
			List<Integer> results = execute(new Object[] {
						new Long(userId), new Long(userGroupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}
	}

	protected class AddUserGroup extends SqlUpdate {
		protected AddUserGroup(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"INSERT INTO Users_UserGroups (userId, userGroupId) VALUES (?, ?)");

			_persistenceImpl = persistenceImpl;

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void add(long userId, long userGroupId) {
			if (!_persistenceImpl.containsUserGroup.contains(userId, userGroupId)) {
				update(new Object[] { new Long(userId), new Long(userGroupId) });
			}
		}

		private UserPersistenceImpl _persistenceImpl;
	}

	protected class ClearUserGroups extends SqlUpdate {
		protected ClearUserGroups(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM Users_UserGroups WHERE userId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void clear(long userId) {
			update(new Object[] { new Long(userId) });
		}
	}

	protected class RemoveUserGroup extends SqlUpdate {
		protected RemoveUserGroup(UserPersistenceImpl persistenceImpl) {
			super(persistenceImpl.getDataSource(),
				"DELETE FROM Users_UserGroups WHERE userId = ? AND userGroupId = ?");

			declareParameter(new SqlParameter(Types.BIGINT));
			declareParameter(new SqlParameter(Types.BIGINT));

			compile();
		}

		protected void remove(long userId, long userGroupId) {
			update(new Object[] { new Long(userId), new Long(userGroupId) });
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
	private static Log _log = LogFactory.getLog(UserPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}