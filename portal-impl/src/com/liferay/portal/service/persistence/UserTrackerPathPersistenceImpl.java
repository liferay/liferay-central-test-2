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
import com.liferay.portal.NoSuchUserTrackerPathException;
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
import com.liferay.portal.model.UserTrackerPath;
import com.liferay.portal.model.impl.UserTrackerPathImpl;
import com.liferay.portal.model.impl.UserTrackerPathModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="UserTrackerPathPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerPathPersistence
 * @see       UserTrackerPathUtil
 * @generated
 */
public class UserTrackerPathPersistenceImpl extends BasePersistenceImpl<UserTrackerPath>
	implements UserTrackerPathPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = UserTrackerPathImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERTRACKERID = new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserTrackerId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERTRACKERID = new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserTrackerId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERTRACKERID = new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserTrackerId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(UserTrackerPath userTrackerPath) {
		EntityCacheUtil.putResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathImpl.class, userTrackerPath.getPrimaryKey(),
			userTrackerPath);
	}

	public void cacheResult(List<UserTrackerPath> userTrackerPaths) {
		for (UserTrackerPath userTrackerPath : userTrackerPaths) {
			if (EntityCacheUtil.getResult(
						UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
						UserTrackerPathImpl.class,
						userTrackerPath.getPrimaryKey(), this) == null) {
				cacheResult(userTrackerPath);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(UserTrackerPathImpl.class.getName());
		EntityCacheUtil.clearCache(UserTrackerPathImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public UserTrackerPath create(long userTrackerPathId) {
		UserTrackerPath userTrackerPath = new UserTrackerPathImpl();

		userTrackerPath.setNew(true);
		userTrackerPath.setPrimaryKey(userTrackerPathId);

		return userTrackerPath;
	}

	public UserTrackerPath remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
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
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						userTrackerPathId);
				}

				throw new NoSuchUserTrackerPathException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
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
		for (ModelListener<UserTrackerPath> listener : listeners) {
			listener.onBeforeRemove(userTrackerPath);
		}

		userTrackerPath = removeImpl(userTrackerPath);

		for (ModelListener<UserTrackerPath> listener : listeners) {
			listener.onAfterRemove(userTrackerPath);
		}

		return userTrackerPath;
	}

	protected UserTrackerPath removeImpl(UserTrackerPath userTrackerPath)
		throws SystemException {
		userTrackerPath = toUnwrappedModel(userTrackerPath);

		Session session = null;

		try {
			session = openSession();

			if (userTrackerPath.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(UserTrackerPathImpl.class,
						userTrackerPath.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(userTrackerPath);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathImpl.class, userTrackerPath.getPrimaryKey());

		return userTrackerPath;
	}

	public UserTrackerPath updateImpl(
		com.liferay.portal.model.UserTrackerPath userTrackerPath, boolean merge)
		throws SystemException {
		userTrackerPath = toUnwrappedModel(userTrackerPath);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, userTrackerPath, merge);

			userTrackerPath.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathImpl.class, userTrackerPath.getPrimaryKey(),
			userTrackerPath);

		return userTrackerPath;
	}

	protected UserTrackerPath toUnwrappedModel(UserTrackerPath userTrackerPath) {
		if (userTrackerPath instanceof UserTrackerPathImpl) {
			return userTrackerPath;
		}

		UserTrackerPathImpl userTrackerPathImpl = new UserTrackerPathImpl();

		userTrackerPathImpl.setNew(userTrackerPath.isNew());
		userTrackerPathImpl.setPrimaryKey(userTrackerPath.getPrimaryKey());

		userTrackerPathImpl.setUserTrackerPathId(userTrackerPath.getUserTrackerPathId());
		userTrackerPathImpl.setUserTrackerId(userTrackerPath.getUserTrackerId());
		userTrackerPathImpl.setPath(userTrackerPath.getPath());
		userTrackerPathImpl.setPathDate(userTrackerPath.getPathDate());

		return userTrackerPathImpl;
	}

	public UserTrackerPath findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public UserTrackerPath findByPrimaryKey(long userTrackerPathId)
		throws NoSuchUserTrackerPathException, SystemException {
		UserTrackerPath userTrackerPath = fetchByPrimaryKey(userTrackerPathId);

		if (userTrackerPath == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + userTrackerPathId);
			}

			throw new NoSuchUserTrackerPathException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				userTrackerPathId);
		}

		return userTrackerPath;
	}

	public UserTrackerPath fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public UserTrackerPath fetchByPrimaryKey(long userTrackerPathId)
		throws SystemException {
		UserTrackerPath userTrackerPath = (UserTrackerPath)EntityCacheUtil.getResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
				UserTrackerPathImpl.class, userTrackerPathId, this);

		if (userTrackerPath == null) {
			Session session = null;

			try {
				session = openSession();

				userTrackerPath = (UserTrackerPath)session.get(UserTrackerPathImpl.class,
						new Long(userTrackerPathId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (userTrackerPath != null) {
					cacheResult(userTrackerPath);
				}

				closeSession(session);
			}
		}

		return userTrackerPath;
	}

	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userTrackerId) };

		List<UserTrackerPath> list = (List<UserTrackerPath>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERTRACKERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_USERTRACKERPATH_WHERE);

				query.append(_FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userTrackerId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserTrackerPath>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERTRACKERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId,
		int start, int end) throws SystemException {
		return findByUserTrackerId(userTrackerId, start, end, null);
	}

	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userTrackerId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserTrackerPath> list = (List<UserTrackerPath>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERTRACKERID,
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

				query.append(_SQL_SELECT_USERTRACKERPATH_WHERE);

				query.append(_FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userTrackerId);

				list = (List<UserTrackerPath>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserTrackerPath>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERTRACKERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserTrackerPath findByUserTrackerId_First(long userTrackerId,
		OrderByComparator obc)
		throws NoSuchUserTrackerPathException, SystemException {
		List<UserTrackerPath> list = findByUserTrackerId(userTrackerId, 0, 1,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userTrackerId=");
			msg.append(userTrackerId);

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

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userTrackerId=");
			msg.append(userTrackerId);

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

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_USERTRACKERPATH_WHERE);

			query.append(_FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

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
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<UserTrackerPath> list = (List<UserTrackerPath>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_USERTRACKERPATH);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_USERTRACKERPATH;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<UserTrackerPath>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserTrackerPath>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserTrackerPath>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
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
		Object[] finderArgs = new Object[] { new Long(userTrackerId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERTRACKERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_USERTRACKERPATH_WHERE);

				query.append(_FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userTrackerId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERTRACKERID,
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

				Query q = session.createQuery(_SQL_COUNT_USERTRACKERPATH);

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
						"value.object.listener.com.liferay.portal.model.UserTrackerPath")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<UserTrackerPath>> listenersList = new ArrayList<ModelListener<UserTrackerPath>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<UserTrackerPath>)Class.forName(
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
	private static final String _SQL_SELECT_USERTRACKERPATH = "SELECT userTrackerPath FROM UserTrackerPath userTrackerPath";
	private static final String _SQL_SELECT_USERTRACKERPATH_WHERE = "SELECT userTrackerPath FROM UserTrackerPath userTrackerPath WHERE ";
	private static final String _SQL_COUNT_USERTRACKERPATH = "SELECT COUNT(userTrackerPath) FROM UserTrackerPath userTrackerPath";
	private static final String _SQL_COUNT_USERTRACKERPATH_WHERE = "SELECT COUNT(userTrackerPath) FROM UserTrackerPath userTrackerPath WHERE ";
	private static final String _FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2 = "userTrackerPath.userTrackerId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userTrackerPath.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserTrackerPath exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserTrackerPath exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(UserTrackerPathPersistenceImpl.class);
}