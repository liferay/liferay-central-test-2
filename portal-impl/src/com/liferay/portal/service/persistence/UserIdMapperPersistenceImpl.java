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

import com.liferay.portal.NoSuchUserIdMapperException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.UserIdMapper;
import com.liferay.portal.model.impl.UserIdMapperImpl;
import com.liferay.portal.model.impl.UserIdMapperModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="UserIdMapperPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserIdMapperPersistenceImpl extends BasePersistenceImpl
	implements UserIdMapperPersistence {
	public UserIdMapper create(long userIdMapperId) {
		UserIdMapper userIdMapper = new UserIdMapperImpl();

		userIdMapper.setNew(true);
		userIdMapper.setPrimaryKey(userIdMapperId);

		return userIdMapper;
	}

	public UserIdMapper remove(long userIdMapperId)
		throws NoSuchUserIdMapperException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserIdMapper userIdMapper = (UserIdMapper)session.get(UserIdMapperImpl.class,
					new Long(userIdMapperId));

			if (userIdMapper == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No UserIdMapper exists with the primary key " +
						userIdMapperId);
				}

				throw new NoSuchUserIdMapperException(
					"No UserIdMapper exists with the primary key " +
					userIdMapperId);
			}

			return remove(userIdMapper);
		}
		catch (NoSuchUserIdMapperException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public UserIdMapper remove(UserIdMapper userIdMapper)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(userIdMapper);
			}
		}

		userIdMapper = removeImpl(userIdMapper);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(userIdMapper);
			}
		}

		return userIdMapper;
	}

	protected UserIdMapper removeImpl(UserIdMapper userIdMapper)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(userIdMapper);

			session.flush();

			return userIdMapper;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(UserIdMapper.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(UserIdMapper userIdMapper, boolean merge)</code>.
	 */
	public UserIdMapper update(UserIdMapper userIdMapper)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(UserIdMapper userIdMapper) method. Use update(UserIdMapper userIdMapper, boolean merge) instead.");
		}

		return update(userIdMapper, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        userIdMapper the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when userIdMapper is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public UserIdMapper update(UserIdMapper userIdMapper, boolean merge)
		throws SystemException {
		boolean isNew = userIdMapper.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(userIdMapper);
				}
				else {
					listener.onBeforeUpdate(userIdMapper);
				}
			}
		}

		userIdMapper = updateImpl(userIdMapper, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(userIdMapper);
				}
				else {
					listener.onAfterUpdate(userIdMapper);
				}
			}
		}

		return userIdMapper;
	}

	public UserIdMapper updateImpl(
		com.liferay.portal.model.UserIdMapper userIdMapper, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(userIdMapper);
			}
			else {
				if (userIdMapper.isNew()) {
					session.save(userIdMapper);
				}
			}

			session.flush();

			userIdMapper.setNew(false);

			return userIdMapper;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(UserIdMapper.class.getName());
		}
	}

	public UserIdMapper findByPrimaryKey(long userIdMapperId)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = fetchByPrimaryKey(userIdMapperId);

		if (userIdMapper == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No UserIdMapper exists with the primary key " +
					userIdMapperId);
			}

			throw new NoSuchUserIdMapperException(
				"No UserIdMapper exists with the primary key " +
				userIdMapperId);
		}

		return userIdMapper;
	}

	public UserIdMapper fetchByPrimaryKey(long userIdMapperId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (UserIdMapper)session.get(UserIdMapperImpl.class,
				new Long(userIdMapperId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserIdMapper> findByUserId(long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserIdMapperModelImpl.CACHE_ENABLED;
		String finderClassName = UserIdMapper.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.UserIdMapper WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<UserIdMapper> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<UserIdMapper>)result;
		}
	}

	public List<UserIdMapper> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<UserIdMapper> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserIdMapperModelImpl.CACHE_ENABLED;
		String finderClassName = UserIdMapper.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.UserIdMapper WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<UserIdMapper> list = (List<UserIdMapper>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<UserIdMapper>)result;
		}
	}

	public UserIdMapper findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchUserIdMapperException, SystemException {
		List<UserIdMapper> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserIdMapper exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserIdMapperException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserIdMapper findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchUserIdMapperException, SystemException {
		int count = countByUserId(userId);

		List<UserIdMapper> list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserIdMapper exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserIdMapperException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserIdMapper[] findByUserId_PrevAndNext(long userIdMapperId,
		long userId, OrderByComparator obc)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = findByPrimaryKey(userIdMapperId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.UserIdMapper WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userIdMapper);

			UserIdMapper[] array = new UserIdMapperImpl[3];

			array[0] = (UserIdMapper)objArray[0];
			array[1] = (UserIdMapper)objArray[1];
			array[2] = (UserIdMapper)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public UserIdMapper findByU_T(long userId, String type)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = fetchByU_T(userId, type);

		if (userIdMapper == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserIdMapper exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserIdMapperException(msg.toString());
		}

		return userIdMapper;
	}

	public UserIdMapper fetchByU_T(long userId, String type)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserIdMapperModelImpl.CACHE_ENABLED;
		String finderClassName = UserIdMapper.class.getName();
		String finderMethodName = "fetchByU_T";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(userId), type };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.UserIdMapper WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				if (type == null) {
					query.append("type_ IS NULL");
				}
				else {
					query.append("type_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (type != null) {
					qPos.add(type);
				}

				List<UserIdMapper> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
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
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<UserIdMapper> list = (List<UserIdMapper>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public UserIdMapper findByT_E(String type, String externalUserId)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = fetchByT_E(type, externalUserId);

		if (userIdMapper == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserIdMapper exists with the key {");

			msg.append("type=" + type);

			msg.append(", ");
			msg.append("externalUserId=" + externalUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserIdMapperException(msg.toString());
		}

		return userIdMapper;
	}

	public UserIdMapper fetchByT_E(String type, String externalUserId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserIdMapperModelImpl.CACHE_ENABLED;
		String finderClassName = UserIdMapper.class.getName();
		String finderMethodName = "fetchByT_E";
		String[] finderParams = new String[] {
				String.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { type, externalUserId };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portal.model.UserIdMapper WHERE ");

				if (type == null) {
					query.append("type_ IS NULL");
				}
				else {
					query.append("type_ = ?");
				}

				query.append(" AND ");

				if (externalUserId == null) {
					query.append("externalUserId IS NULL");
				}
				else {
					query.append("externalUserId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (type != null) {
					qPos.add(type);
				}

				if (externalUserId != null) {
					qPos.add(externalUserId);
				}

				List<UserIdMapper> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
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
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<UserIdMapper> list = (List<UserIdMapper>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<UserIdMapper> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserIdMapper> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			dynamicQuery.setLimit(start, end);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserIdMapper> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<UserIdMapper> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<UserIdMapper> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserIdMapperModelImpl.CACHE_ENABLED;
		String finderClassName = UserIdMapper.class.getName();
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
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("FROM com.liferay.portal.model.UserIdMapper ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<UserIdMapper> list = (List<UserIdMapper>)QueryUtil.list(q,
						getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<UserIdMapper>)result;
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (UserIdMapper userIdMapper : findByUserId(userId)) {
			remove(userIdMapper);
		}
	}

	public void removeByU_T(long userId, String type)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = findByU_T(userId, type);

		remove(userIdMapper);
	}

	public void removeByT_E(String type, String externalUserId)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = findByT_E(type, externalUserId);

		remove(userIdMapper);
	}

	public void removeAll() throws SystemException {
		for (UserIdMapper userIdMapper : findAll()) {
			remove(userIdMapper);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserIdMapperModelImpl.CACHE_ENABLED;
		String finderClassName = UserIdMapper.class.getName();
		String finderMethodName = "countByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.UserIdMapper WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByU_T(long userId, String type) throws SystemException {
		boolean finderClassNameCacheEnabled = UserIdMapperModelImpl.CACHE_ENABLED;
		String finderClassName = UserIdMapper.class.getName();
		String finderMethodName = "countByU_T";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(userId), type };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.UserIdMapper WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				if (type == null) {
					query.append("type_ IS NULL");
				}
				else {
					query.append("type_ = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (type != null) {
					qPos.add(type);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByT_E(String type, String externalUserId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserIdMapperModelImpl.CACHE_ENABLED;
		String finderClassName = UserIdMapper.class.getName();
		String finderMethodName = "countByT_E";
		String[] finderParams = new String[] {
				String.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { type, externalUserId };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portal.model.UserIdMapper WHERE ");

				if (type == null) {
					query.append("type_ IS NULL");
				}
				else {
					query.append("type_ = ?");
				}

				query.append(" AND ");

				if (externalUserId == null) {
					query.append("externalUserId IS NULL");
				}
				else {
					query.append("externalUserId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (type != null) {
					qPos.add(type);
				}

				if (externalUserId != null) {
					qPos.add(externalUserId);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
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
		boolean finderClassNameCacheEnabled = UserIdMapperModelImpl.CACHE_ENABLED;
		String finderClassName = UserIdMapper.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portal.model.UserIdMapper");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
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

	protected void init() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.UserIdMapper")));

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
	}

	private static Log _log = LogFactory.getLog(UserIdMapperPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}