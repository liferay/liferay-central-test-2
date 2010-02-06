/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchUserTrackerException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.model.impl.UserTrackerImpl;
import com.liferay.portal.model.impl.UserTrackerModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="UserTrackerPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerPersistence
 * @see       UserTrackerUtil
 * @generated
 */
public class UserTrackerPersistenceImpl extends BasePersistenceImpl<UserTracker>
	implements UserTrackerPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = UserTrackerImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_SESSIONID = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findBySessionId", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_SESSIONID = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findBySessionId",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_SESSIONID = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countBySessionId", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(UserTracker userTracker) {
		EntityCacheUtil.putResult(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerImpl.class, userTracker.getPrimaryKey(), userTracker);
	}

	public void cacheResult(List<UserTracker> userTrackers) {
		for (UserTracker userTracker : userTrackers) {
			if (EntityCacheUtil.getResult(
						UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
						UserTrackerImpl.class, userTracker.getPrimaryKey(), this) == null) {
				cacheResult(userTracker);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(UserTrackerImpl.class.getName());
		EntityCacheUtil.clearCache(UserTrackerImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public UserTracker create(long userTrackerId) {
		UserTracker userTracker = new UserTrackerImpl();

		userTracker.setNew(true);
		userTracker.setPrimaryKey(userTrackerId);

		return userTracker;
	}

	public UserTracker remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public UserTracker remove(long userTrackerId)
		throws NoSuchUserTrackerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserTracker userTracker = (UserTracker)session.get(UserTrackerImpl.class,
					new Long(userTrackerId));

			if (userTracker == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + userTrackerId);
				}

				throw new NoSuchUserTrackerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					userTrackerId);
			}

			return remove(userTracker);
		}
		catch (NoSuchUserTrackerException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public UserTracker remove(UserTracker userTracker)
		throws SystemException {
		for (ModelListener<UserTracker> listener : listeners) {
			listener.onBeforeRemove(userTracker);
		}

		userTracker = removeImpl(userTracker);

		for (ModelListener<UserTracker> listener : listeners) {
			listener.onAfterRemove(userTracker);
		}

		return userTracker;
	}

	protected UserTracker removeImpl(UserTracker userTracker)
		throws SystemException {
		userTracker = toUnwrappedModel(userTracker);

		Session session = null;

		try {
			session = openSession();

			if (userTracker.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(UserTrackerImpl.class,
						userTracker.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(userTracker);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerImpl.class, userTracker.getPrimaryKey());

		return userTracker;
	}

	public UserTracker updateImpl(
		com.liferay.portal.model.UserTracker userTracker, boolean merge)
		throws SystemException {
		userTracker = toUnwrappedModel(userTracker);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, userTracker, merge);

			userTracker.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerImpl.class, userTracker.getPrimaryKey(), userTracker);

		return userTracker;
	}

	protected UserTracker toUnwrappedModel(UserTracker userTracker) {
		if (userTracker instanceof UserTrackerImpl) {
			return userTracker;
		}

		UserTrackerImpl userTrackerImpl = new UserTrackerImpl();

		userTrackerImpl.setNew(userTracker.isNew());
		userTrackerImpl.setPrimaryKey(userTracker.getPrimaryKey());

		userTrackerImpl.setUserTrackerId(userTracker.getUserTrackerId());
		userTrackerImpl.setCompanyId(userTracker.getCompanyId());
		userTrackerImpl.setUserId(userTracker.getUserId());
		userTrackerImpl.setModifiedDate(userTracker.getModifiedDate());
		userTrackerImpl.setSessionId(userTracker.getSessionId());
		userTrackerImpl.setRemoteAddr(userTracker.getRemoteAddr());
		userTrackerImpl.setRemoteHost(userTracker.getRemoteHost());
		userTrackerImpl.setUserAgent(userTracker.getUserAgent());

		return userTrackerImpl;
	}

	public UserTracker findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public UserTracker findByPrimaryKey(long userTrackerId)
		throws NoSuchUserTrackerException, SystemException {
		UserTracker userTracker = fetchByPrimaryKey(userTrackerId);

		if (userTracker == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + userTrackerId);
			}

			throw new NoSuchUserTrackerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				userTrackerId);
		}

		return userTracker;
	}

	public UserTracker fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public UserTracker fetchByPrimaryKey(long userTrackerId)
		throws SystemException {
		UserTracker userTracker = (UserTracker)EntityCacheUtil.getResult(UserTrackerModelImpl.ENTITY_CACHE_ENABLED,
				UserTrackerImpl.class, userTrackerId, this);

		if (userTracker == null) {
			Session session = null;

			try {
				session = openSession();

				userTracker = (UserTracker)session.get(UserTrackerImpl.class,
						new Long(userTrackerId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (userTracker != null) {
					cacheResult(userTracker);
				}

				closeSession(session);
			}
		}

		return userTracker;
	}

	public List<UserTracker> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<UserTracker> list = (List<UserTracker>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_USERTRACKER_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserTracker>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserTracker> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<UserTracker> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserTracker> list = (List<UserTracker>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_USERTRACKER_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserTracker>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserTracker findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		List<UserTracker> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTracker findByCompanyId_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		int count = countByCompanyId(companyId);

		List<UserTracker> list = findByCompanyId(companyId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTracker[] findByCompanyId_PrevAndNext(long userTrackerId,
		long companyId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		UserTracker userTracker = findByPrimaryKey(userTrackerId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_USERTRACKER_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userTracker);

			UserTracker[] array = new UserTrackerImpl[3];

			array[0] = (UserTracker)objArray[0];
			array[1] = (UserTracker)objArray[1];
			array[2] = (UserTracker)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserTracker> findByUserId(long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<UserTracker> list = (List<UserTracker>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_USERTRACKER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserTracker>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserTracker> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<UserTracker> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserTracker> list = (List<UserTracker>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_USERTRACKER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserTracker>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserTracker findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		List<UserTracker> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTracker findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		int count = countByUserId(userId);

		List<UserTracker> list = findByUserId(userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTracker[] findByUserId_PrevAndNext(long userTrackerId,
		long userId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		UserTracker userTracker = findByPrimaryKey(userTrackerId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_USERTRACKER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userTracker);

			UserTracker[] array = new UserTrackerImpl[3];

			array[0] = (UserTracker)objArray[0];
			array[1] = (UserTracker)objArray[1];
			array[2] = (UserTracker)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserTracker> findBySessionId(String sessionId)
		throws SystemException {
		Object[] finderArgs = new Object[] { sessionId };

		List<UserTracker> list = (List<UserTracker>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_SESSIONID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_USERTRACKER_WHERE);

				if (sessionId == null) {
					query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_1);
				}
				else {
					if (sessionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (sessionId != null) {
					qPos.add(sessionId);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserTracker>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_SESSIONID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserTracker> findBySessionId(String sessionId, int start,
		int end) throws SystemException {
		return findBySessionId(sessionId, start, end, null);
	}

	public List<UserTracker> findBySessionId(String sessionId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				sessionId,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserTracker> list = (List<UserTracker>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_SESSIONID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_USERTRACKER_WHERE);

				if (sessionId == null) {
					query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_1);
				}
				else {
					if (sessionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_2);
					}
				}

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (sessionId != null) {
					qPos.add(sessionId);
				}

				list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserTracker>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_SESSIONID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserTracker findBySessionId_First(String sessionId,
		OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		List<UserTracker> list = findBySessionId(sessionId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("sessionId=");
			msg.append(sessionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTracker findBySessionId_Last(String sessionId,
		OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		int count = countBySessionId(sessionId);

		List<UserTracker> list = findBySessionId(sessionId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("sessionId=");
			msg.append(sessionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTracker[] findBySessionId_PrevAndNext(long userTrackerId,
		String sessionId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		UserTracker userTracker = findByPrimaryKey(userTrackerId);

		int count = countBySessionId(sessionId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_USERTRACKER_WHERE);

			if (sessionId == null) {
				query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_1);
			}
			else {
				if (sessionId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_3);
				}
				else {
					query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_2);
				}
			}

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			if (sessionId != null) {
				qPos.add(sessionId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userTracker);

			UserTracker[] array = new UserTrackerImpl[3];

			array[0] = (UserTracker)objArray[0];
			array[1] = (UserTracker)objArray[1];
			array[2] = (UserTracker)objArray[2];

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

	public List<UserTracker> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<UserTracker> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<UserTracker> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserTracker> list = (List<UserTracker>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(2 +
							(obc.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_USERTRACKER);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_USERTRACKER;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserTracker>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (UserTracker userTracker : findByCompanyId(companyId)) {
			remove(userTracker);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (UserTracker userTracker : findByUserId(userId)) {
			remove(userTracker);
		}
	}

	public void removeBySessionId(String sessionId) throws SystemException {
		for (UserTracker userTracker : findBySessionId(sessionId)) {
			remove(userTracker);
		}
	}

	public void removeAll() throws SystemException {
		for (UserTracker userTracker : findAll()) {
			remove(userTracker);
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_USERTRACKER_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_USERTRACKER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countBySessionId(String sessionId) throws SystemException {
		Object[] finderArgs = new Object[] { sessionId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_SESSIONID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_USERTRACKER_WHERE);

				if (sessionId == null) {
					query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_1);
				}
				else {
					if (sessionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_SESSIONID_SESSIONID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (sessionId != null) {
					qPos.add(sessionId);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_SESSIONID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USERTRACKER);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.UserTracker")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<UserTracker>> listenersList = new ArrayList<ModelListener<UserTracker>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<UserTracker>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_USERTRACKER = "SELECT userTracker FROM UserTracker userTracker";
	private static final String _SQL_SELECT_USERTRACKER_WHERE = "SELECT userTracker FROM UserTracker userTracker WHERE ";
	private static final String _SQL_COUNT_USERTRACKER = "SELECT COUNT(userTracker) FROM UserTracker userTracker";
	private static final String _SQL_COUNT_USERTRACKER_WHERE = "SELECT COUNT(userTracker) FROM UserTracker userTracker WHERE ";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "userTracker.companyId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "userTracker.userId = ?";
	private static final String _FINDER_COLUMN_SESSIONID_SESSIONID_1 = "userTracker.sessionId IS NULL";
	private static final String _FINDER_COLUMN_SESSIONID_SESSIONID_2 = "userTracker.sessionId = ?";
	private static final String _FINDER_COLUMN_SESSIONID_SESSIONID_3 = "(userTracker.sessionId IS NULL OR userTracker.sessionId = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userTracker.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserTracker exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserTracker exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(UserTrackerPersistenceImpl.class);
}