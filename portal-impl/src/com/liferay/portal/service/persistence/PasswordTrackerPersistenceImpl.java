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

import com.liferay.portal.NoSuchPasswordTrackerException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PasswordTracker;
import com.liferay.portal.model.impl.PasswordTrackerImpl;
import com.liferay.portal.model.impl.PasswordTrackerModelImpl;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="PasswordTrackerPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PasswordTrackerPersistenceImpl extends BasePersistence
	implements PasswordTrackerPersistence {
	public PasswordTracker create(long passwordTrackerId) {
		PasswordTracker passwordTracker = new PasswordTrackerImpl();

		passwordTracker.setNew(true);
		passwordTracker.setPrimaryKey(passwordTrackerId);

		return passwordTracker;
	}

	public PasswordTracker remove(long passwordTrackerId)
		throws NoSuchPasswordTrackerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PasswordTracker passwordTracker = (PasswordTracker)session.get(PasswordTrackerImpl.class,
					new Long(passwordTrackerId));

			if (passwordTracker == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No PasswordTracker exists with the primary key " +
						passwordTrackerId);
				}

				throw new NoSuchPasswordTrackerException(
					"No PasswordTracker exists with the primary key " +
					passwordTrackerId);
			}

			return remove(passwordTracker);
		}
		catch (NoSuchPasswordTrackerException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PasswordTracker remove(PasswordTracker passwordTracker)
		throws SystemException {
		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(passwordTracker);
			}
		}

		passwordTracker = removeImpl(passwordTracker);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(passwordTracker);
			}
		}

		return passwordTracker;
	}

	protected PasswordTracker removeImpl(PasswordTracker passwordTracker)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(passwordTracker);

			session.flush();

			return passwordTracker;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(PasswordTracker.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(PasswordTracker passwordTracker, boolean merge)</code>.
	 */
	public PasswordTracker update(PasswordTracker passwordTracker)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(PasswordTracker passwordTracker) method. Use update(PasswordTracker passwordTracker, boolean merge) instead.");
		}

		return update(passwordTracker, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        passwordTracker the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when passwordTracker is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public PasswordTracker update(PasswordTracker passwordTracker, boolean merge)
		throws SystemException {
		boolean isNew = passwordTracker.isNew();

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(passwordTracker);
				}
				else {
					listener.onBeforeUpdate(passwordTracker);
				}
			}
		}

		passwordTracker = updateImpl(passwordTracker, merge);

		if (_listeners != null) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(passwordTracker);
				}
				else {
					listener.onAfterUpdate(passwordTracker);
				}
			}
		}

		return passwordTracker;
	}

	public PasswordTracker updateImpl(
		com.liferay.portal.model.PasswordTracker passwordTracker, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(passwordTracker);
			}
			else {
				if (passwordTracker.isNew()) {
					session.save(passwordTracker);
				}
			}

			session.flush();

			passwordTracker.setNew(false);

			return passwordTracker;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(PasswordTracker.class.getName());
		}
	}

	public PasswordTracker findByPrimaryKey(long passwordTrackerId)
		throws NoSuchPasswordTrackerException, SystemException {
		PasswordTracker passwordTracker = fetchByPrimaryKey(passwordTrackerId);

		if (passwordTracker == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No PasswordTracker exists with the primary key " +
					passwordTrackerId);
			}

			throw new NoSuchPasswordTrackerException(
				"No PasswordTracker exists with the primary key " +
				passwordTrackerId);
		}

		return passwordTracker;
	}

	public PasswordTracker fetchByPrimaryKey(long passwordTrackerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (PasswordTracker)session.get(PasswordTrackerImpl.class,
				new Long(passwordTrackerId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PasswordTracker> findByUserId(long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = PasswordTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = PasswordTracker.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

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

				query.append(
					"FROM com.liferay.portal.model.PasswordTracker WHERE ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("userId DESC, ");
				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, userId);

				List<PasswordTracker> list = q.list();

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
			return (List<PasswordTracker>)result;
		}
	}

	public List<PasswordTracker> findByUserId(long userId, int begin, int end)
		throws SystemException {
		return findByUserId(userId, begin, end, null);
	}

	public List<PasswordTracker> findByUserId(long userId, int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = PasswordTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = PasswordTracker.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
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

				query.append(
					"FROM com.liferay.portal.model.PasswordTracker WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("userId DESC, ");
					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, userId);

				List<PasswordTracker> list = (List<PasswordTracker>)QueryUtil.list(q,
						getDialect(), begin, end);

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
			return (List<PasswordTracker>)result;
		}
	}

	public PasswordTracker findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchPasswordTrackerException, SystemException {
		List<PasswordTracker> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No PasswordTracker exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPasswordTrackerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PasswordTracker findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchPasswordTrackerException, SystemException {
		int count = countByUserId(userId);

		List<PasswordTracker> list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No PasswordTracker exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPasswordTrackerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PasswordTracker[] findByUserId_PrevAndNext(long passwordTrackerId,
		long userId, OrderByComparator obc)
		throws NoSuchPasswordTrackerException, SystemException {
		PasswordTracker passwordTracker = findByPrimaryKey(passwordTrackerId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append("FROM com.liferay.portal.model.PasswordTracker WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("userId DESC, ");
				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			int queryPos = 0;

			q.setLong(queryPos++, userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					passwordTracker);

			PasswordTracker[] array = new PasswordTrackerImpl[3];

			array[0] = (PasswordTracker)objArray[0];
			array[1] = (PasswordTracker)objArray[1];
			array[2] = (PasswordTracker)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PasswordTracker> findWithDynamicQuery(
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

	public List<PasswordTracker> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer, int begin, int end)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			query.setLimit(begin, end);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<PasswordTracker> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PasswordTracker> findAll(int begin, int end)
		throws SystemException {
		return findAll(begin, end, null);
	}

	public List<PasswordTracker> findAll(int begin, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = PasswordTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = PasswordTracker.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(begin), String.valueOf(end), String.valueOf(obc)
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

				query.append("FROM com.liferay.portal.model.PasswordTracker ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("userId DESC, ");
					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				List<PasswordTracker> list = (List<PasswordTracker>)QueryUtil.list(q,
						getDialect(), begin, end);

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
			return (List<PasswordTracker>)result;
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (PasswordTracker passwordTracker : findByUserId(userId)) {
			remove(passwordTracker);
		}
	}

	public void removeAll() throws SystemException {
		for (PasswordTracker passwordTracker : findAll()) {
			remove(passwordTracker);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = PasswordTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = PasswordTracker.class.getName();
		String finderMethodName = "countByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

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
				query.append(
					"FROM com.liferay.portal.model.PasswordTracker WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				int queryPos = 0;

				q.setLong(queryPos++, userId);

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
		boolean finderClassNameCacheEnabled = PasswordTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = PasswordTracker.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.PasswordTracker");

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

	protected void initDao() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.PasswordTracker")));

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

	private static Log _log = LogFactory.getLog(PasswordTrackerPersistenceImpl.class);
	private ModelListener[] _listeners;
}