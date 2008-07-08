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

import com.liferay.portal.NoSuchUserTrackerPathException;
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
import com.liferay.portal.model.UserTrackerPath;
import com.liferay.portal.model.impl.UserTrackerPathImpl;
import com.liferay.portal.model.impl.UserTrackerPathModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="UserTrackerPathPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserTrackerPathPersistenceImpl extends BasePersistenceImpl
	implements UserTrackerPathPersistence {
	public UserTrackerPath create(long userTrackerPathId) {
		UserTrackerPath userTrackerPath = new UserTrackerPathImpl();

		userTrackerPath.setNew(true);
		userTrackerPath.setPrimaryKey(userTrackerPathId);

		return userTrackerPath;
	}

	public UserTrackerPath remove(long userTrackerPathId)
		throws NoSuchUserTrackerPathException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserTrackerPath userTrackerPath = (UserTrackerPath)session.get(UserTrackerPathImpl.class,
					new Long(userTrackerPathId));

			if (userTrackerPath == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No UserTrackerPath exists with the primary key " +
						userTrackerPathId);
				}

				throw new NoSuchUserTrackerPathException(
					"No UserTrackerPath exists with the primary key " +
					userTrackerPathId);
			}

			return remove(userTrackerPath);
		}
		catch (NoSuchUserTrackerPathException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public UserTrackerPath remove(UserTrackerPath userTrackerPath)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(userTrackerPath);
			}
		}

		userTrackerPath = removeImpl(userTrackerPath);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(userTrackerPath);
			}
		}

		return userTrackerPath;
	}

	protected UserTrackerPath removeImpl(UserTrackerPath userTrackerPath)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(userTrackerPath);

			session.flush();

			return userTrackerPath;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(UserTrackerPath.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(UserTrackerPath userTrackerPath, boolean merge)</code>.
	 */
	public UserTrackerPath update(UserTrackerPath userTrackerPath)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(UserTrackerPath userTrackerPath) method. Use update(UserTrackerPath userTrackerPath, boolean merge) instead.");
		}

		return update(userTrackerPath, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        userTrackerPath the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when userTrackerPath is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public UserTrackerPath update(UserTrackerPath userTrackerPath, boolean merge)
		throws SystemException {
		boolean isNew = userTrackerPath.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(userTrackerPath);
				}
				else {
					listener.onBeforeUpdate(userTrackerPath);
				}
			}
		}

		userTrackerPath = updateImpl(userTrackerPath, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(userTrackerPath);
				}
				else {
					listener.onAfterUpdate(userTrackerPath);
				}
			}
		}

		return userTrackerPath;
	}

	public UserTrackerPath updateImpl(
		com.liferay.portal.model.UserTrackerPath userTrackerPath, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(userTrackerPath);
			}
			else {
				if (userTrackerPath.isNew()) {
					session.save(userTrackerPath);
				}
			}

			session.flush();

			userTrackerPath.setNew(false);

			return userTrackerPath;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(UserTrackerPath.class.getName());
		}
	}

	public UserTrackerPath findByPrimaryKey(long userTrackerPathId)
		throws NoSuchUserTrackerPathException, SystemException {
		UserTrackerPath userTrackerPath = fetchByPrimaryKey(userTrackerPathId);

		if (userTrackerPath == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No UserTrackerPath exists with the primary key " +
					userTrackerPathId);
			}

			throw new NoSuchUserTrackerPathException(
				"No UserTrackerPath exists with the primary key " +
				userTrackerPathId);
		}

		return userTrackerPath;
	}

	public UserTrackerPath fetchByPrimaryKey(long userTrackerPathId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (UserTrackerPath)session.get(UserTrackerPathImpl.class,
				new Long(userTrackerPathId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserTrackerPathModelImpl.CACHE_ENABLED;
		String finderClassName = UserTrackerPath.class.getName();
		String finderMethodName = "findByUserTrackerId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userTrackerId) };

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
					"FROM com.liferay.portal.model.UserTrackerPath WHERE ");

				query.append("userTrackerId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userTrackerId);

				List<UserTrackerPath> list = q.list();

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
			return (List<UserTrackerPath>)result;
		}
	}

	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId,
		int start, int end) throws SystemException {
		return findByUserTrackerId(userTrackerId, start, end, null);
	}

	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserTrackerPathModelImpl.CACHE_ENABLED;
		String finderClassName = UserTrackerPath.class.getName();
		String finderMethodName = "findByUserTrackerId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userTrackerId),
				
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
					"FROM com.liferay.portal.model.UserTrackerPath WHERE ");

				query.append("userTrackerId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userTrackerId);

				List<UserTrackerPath> list = (List<UserTrackerPath>)QueryUtil.list(q,
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
			return (List<UserTrackerPath>)result;
		}
	}

	public UserTrackerPath findByUserTrackerId_First(long userTrackerId,
		OrderByComparator obc)
		throws NoSuchUserTrackerPathException, SystemException {
		List<UserTrackerPath> list = findByUserTrackerId(userTrackerId, 0, 1,
				obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserTrackerPath exists with the key {");

			msg.append("userTrackerId=" + userTrackerId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerPathException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTrackerPath findByUserTrackerId_Last(long userTrackerId,
		OrderByComparator obc)
		throws NoSuchUserTrackerPathException, SystemException {
		int count = countByUserTrackerId(userTrackerId);

		List<UserTrackerPath> list = findByUserTrackerId(userTrackerId,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserTrackerPath exists with the key {");

			msg.append("userTrackerId=" + userTrackerId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerPathException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTrackerPath[] findByUserTrackerId_PrevAndNext(
		long userTrackerPathId, long userTrackerId, OrderByComparator obc)
		throws NoSuchUserTrackerPathException, SystemException {
		UserTrackerPath userTrackerPath = findByPrimaryKey(userTrackerPathId);

		int count = countByUserTrackerId(userTrackerId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.UserTrackerPath WHERE ");

			query.append("userTrackerId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userTrackerId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userTrackerPath);

			UserTrackerPath[] array = new UserTrackerPathImpl[3];

			array[0] = (UserTrackerPath)objArray[0];
			array[1] = (UserTrackerPath)objArray[1];
			array[2] = (UserTrackerPath)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
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

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

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

	public List<UserTrackerPath> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<UserTrackerPath> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<UserTrackerPath> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserTrackerPathModelImpl.CACHE_ENABLED;
		String finderClassName = UserTrackerPath.class.getName();
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

				query.append("FROM com.liferay.portal.model.UserTrackerPath ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<UserTrackerPath> list = (List<UserTrackerPath>)QueryUtil.list(q,
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
			return (List<UserTrackerPath>)result;
		}
	}

	public void removeByUserTrackerId(long userTrackerId)
		throws SystemException {
		for (UserTrackerPath userTrackerPath : findByUserTrackerId(
				userTrackerId)) {
			remove(userTrackerPath);
		}
	}

	public void removeAll() throws SystemException {
		for (UserTrackerPath userTrackerPath : findAll()) {
			remove(userTrackerPath);
		}
	}

	public int countByUserTrackerId(long userTrackerId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserTrackerPathModelImpl.CACHE_ENABLED;
		String finderClassName = UserTrackerPath.class.getName();
		String finderMethodName = "countByUserTrackerId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userTrackerId) };

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
					"FROM com.liferay.portal.model.UserTrackerPath WHERE ");

				query.append("userTrackerId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userTrackerId);

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
		boolean finderClassNameCacheEnabled = UserTrackerPathModelImpl.CACHE_ENABLED;
		String finderClassName = UserTrackerPath.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.UserTrackerPath");

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
						"value.object.listener.com.liferay.portal.model.UserTrackerPath")));

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

	private static Log _log = LogFactory.getLog(UserTrackerPathPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}