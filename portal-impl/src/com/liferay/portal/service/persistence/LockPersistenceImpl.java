/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.NoSuchModelException;
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
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LockImpl;
import com.liferay.portal.model.impl.LockModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <a href="LockPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LockPersistence
 * @see       LockUtil
 * @generated
 */
public class LockPersistenceImpl extends BasePersistenceImpl<Lock>
	implements LockPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = LockImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_EXPIRATIONDATE = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByExpirationDate", new String[] { Date.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_EXPIRATIONDATE = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByExpirationDate",
			new String[] {
				Date.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_EXPIRATIONDATE = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByExpirationDate", new String[] { Date.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_K = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_K",
			new String[] { String.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_K = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_K",
			new String[] { String.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Lock lock) {
		EntityCacheUtil.putResult(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockImpl.class, lock.getPrimaryKey(), lock);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K,
			new Object[] { lock.getClassName(), lock.getKey() }, lock);
	}

	public void cacheResult(List<Lock> locks) {
		for (Lock lock : locks) {
			if (EntityCacheUtil.getResult(LockModelImpl.ENTITY_CACHE_ENABLED,
						LockImpl.class, lock.getPrimaryKey(), this) == null) {
				cacheResult(lock);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(LockImpl.class.getName());
		EntityCacheUtil.clearCache(LockImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Lock create(long lockId) {
		Lock lock = new LockImpl();

		lock.setNew(true);
		lock.setPrimaryKey(lockId);

		String uuid = PortalUUIDUtil.generate();

		lock.setUuid(uuid);

		return lock;
	}

	public Lock remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Lock remove(long lockId) throws NoSuchLockException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Lock lock = (Lock)session.get(LockImpl.class, new Long(lockId));

			if (lock == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No Lock exists with the primary key " + lockId);
				}

				throw new NoSuchLockException(
					"No Lock exists with the primary key " + lockId);
			}

			return remove(lock);
		}
		catch (NoSuchLockException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Lock remove(Lock lock) throws SystemException {
		for (ModelListener<Lock> listener : listeners) {
			listener.onBeforeRemove(lock);
		}

		lock = removeImpl(lock);

		for (ModelListener<Lock> listener : listeners) {
			listener.onAfterRemove(lock);
		}

		return lock;
	}

	protected Lock removeImpl(Lock lock) throws SystemException {
		lock = toUnwrappedModel(lock);

		Session session = null;

		try {
			session = openSession();

			if (lock.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(LockImpl.class,
						lock.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(lock);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		LockModelImpl lockModelImpl = (LockModelImpl)lock;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_K,
			new Object[] {
				lockModelImpl.getOriginalClassName(),
				
			lockModelImpl.getOriginalKey()
			});

		EntityCacheUtil.removeResult(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockImpl.class, lock.getPrimaryKey());

		return lock;
	}

	public Lock updateImpl(com.liferay.portal.model.Lock lock, boolean merge)
		throws SystemException {
		lock = toUnwrappedModel(lock);

		boolean isNew = lock.isNew();

		LockModelImpl lockModelImpl = (LockModelImpl)lock;

		if (Validator.isNull(lock.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			lock.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, lock, merge);

			lock.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockImpl.class, lock.getPrimaryKey(), lock);

		if (!isNew &&
				(!Validator.equals(lock.getClassName(),
					lockModelImpl.getOriginalClassName()) ||
				!Validator.equals(lock.getKey(), lockModelImpl.getOriginalKey()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_K,
				new Object[] {
					lockModelImpl.getOriginalClassName(),
					
				lockModelImpl.getOriginalKey()
				});
		}

		if (isNew ||
				(!Validator.equals(lock.getClassName(),
					lockModelImpl.getOriginalClassName()) ||
				!Validator.equals(lock.getKey(), lockModelImpl.getOriginalKey()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K,
				new Object[] { lock.getClassName(), lock.getKey() }, lock);
		}

		return lock;
	}

	protected Lock toUnwrappedModel(Lock lock) {
		if (lock instanceof LockImpl) {
			return lock;
		}

		LockImpl lockImpl = new LockImpl();

		lockImpl.setNew(lock.isNew());
		lockImpl.setPrimaryKey(lock.getPrimaryKey());

		lockImpl.setUuid(lock.getUuid());
		lockImpl.setLockId(lock.getLockId());
		lockImpl.setCompanyId(lock.getCompanyId());
		lockImpl.setUserId(lock.getUserId());
		lockImpl.setUserName(lock.getUserName());
		lockImpl.setCreateDate(lock.getCreateDate());
		lockImpl.setClassName(lock.getClassName());
		lockImpl.setKey(lock.getKey());
		lockImpl.setOwner(lock.getOwner());
		lockImpl.setInheritable(lock.isInheritable());
		lockImpl.setExpirationDate(lock.getExpirationDate());

		return lockImpl;
	}

	public Lock findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Lock findByPrimaryKey(long lockId)
		throws NoSuchLockException, SystemException {
		Lock lock = fetchByPrimaryKey(lockId);

		if (lock == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No Lock exists with the primary key " + lockId);
			}

			throw new NoSuchLockException(
				"No Lock exists with the primary key " + lockId);
		}

		return lock;
	}

	public Lock fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Lock fetchByPrimaryKey(long lockId) throws SystemException {
		Lock lock = (Lock)EntityCacheUtil.getResult(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockImpl.class, lockId, this);

		if (lock == null) {
			Session session = null;

			try {
				session = openSession();

				lock = (Lock)session.get(LockImpl.class, new Long(lockId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (lock != null) {
					cacheResult(lock);
				}

				closeSession(session);
			}
		}

		return lock;
	}

	public List<Lock> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<Lock> list = (List<Lock>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT lock FROM Lock lock WHERE ");

				if (uuid == null) {
					query.append("lock.uuid IS NULL");
				}
				else {
					query.append("lock.uuid = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Lock>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Lock> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<Lock> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Lock> list = (List<Lock>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT lock FROM Lock lock WHERE ");

				if (uuid == null) {
					query.append("lock.uuid IS NULL");
				}
				else {
					query.append("lock.uuid = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("lock.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<Lock>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Lock>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Lock findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchLockException, SystemException {
		List<Lock> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Lock exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLockException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Lock findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchLockException, SystemException {
		int count = countByUuid(uuid);

		List<Lock> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Lock exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLockException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Lock[] findByUuid_PrevAndNext(long lockId, String uuid,
		OrderByComparator obc) throws NoSuchLockException, SystemException {
		Lock lock = findByPrimaryKey(lockId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT lock FROM Lock lock WHERE ");

			if (uuid == null) {
				query.append("lock.uuid IS NULL");
			}
			else {
				query.append("lock.uuid = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("lock.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, lock);

			Lock[] array = new LockImpl[3];

			array[0] = (Lock)objArray[0];
			array[1] = (Lock)objArray[1];
			array[2] = (Lock)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Lock> findByExpirationDate(Date expirationDate)
		throws SystemException {
		Object[] finderArgs = new Object[] { expirationDate };

		List<Lock> list = (List<Lock>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_EXPIRATIONDATE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT lock FROM Lock lock WHERE ");

				if (expirationDate == null) {
					query.append("lock.expirationDate < null");
				}
				else {
					query.append("lock.expirationDate < ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (expirationDate != null) {
					qPos.add(CalendarUtil.getTimestamp(expirationDate));
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Lock>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_EXPIRATIONDATE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Lock> findByExpirationDate(Date expirationDate, int start,
		int end) throws SystemException {
		return findByExpirationDate(expirationDate, start, end, null);
	}

	public List<Lock> findByExpirationDate(Date expirationDate, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				expirationDate,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Lock> list = (List<Lock>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_EXPIRATIONDATE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT lock FROM Lock lock WHERE ");

				if (expirationDate == null) {
					query.append("lock.expirationDate < null");
				}
				else {
					query.append("lock.expirationDate < ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("lock.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (expirationDate != null) {
					qPos.add(CalendarUtil.getTimestamp(expirationDate));
				}

				list = (List<Lock>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Lock>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_EXPIRATIONDATE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Lock findByExpirationDate_First(Date expirationDate,
		OrderByComparator obc) throws NoSuchLockException, SystemException {
		List<Lock> list = findByExpirationDate(expirationDate, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Lock exists with the key {");

			msg.append("expirationDate=" + expirationDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLockException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Lock findByExpirationDate_Last(Date expirationDate,
		OrderByComparator obc) throws NoSuchLockException, SystemException {
		int count = countByExpirationDate(expirationDate);

		List<Lock> list = findByExpirationDate(expirationDate, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Lock exists with the key {");

			msg.append("expirationDate=" + expirationDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLockException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Lock[] findByExpirationDate_PrevAndNext(long lockId,
		Date expirationDate, OrderByComparator obc)
		throws NoSuchLockException, SystemException {
		Lock lock = findByPrimaryKey(lockId);

		int count = countByExpirationDate(expirationDate);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT lock FROM Lock lock WHERE ");

			if (expirationDate == null) {
				query.append("lock.expirationDate < null");
			}
			else {
				query.append("lock.expirationDate < ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("lock.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (expirationDate != null) {
				qPos.add(CalendarUtil.getTimestamp(expirationDate));
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, lock);

			Lock[] array = new LockImpl[3];

			array[0] = (Lock)objArray[0];
			array[1] = (Lock)objArray[1];
			array[2] = (Lock)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Lock findByC_K(String className, String key)
		throws NoSuchLockException, SystemException {
		Lock lock = fetchByC_K(className, key);

		if (lock == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No Lock exists with the key {");

			msg.append("className=" + className);

			msg.append(", ");
			msg.append("key=" + key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLockException(msg.toString());
		}

		return lock;
	}

	public Lock fetchByC_K(String className, String key)
		throws SystemException {
		return fetchByC_K(className, key, true);
	}

	public Lock fetchByC_K(String className, String key,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { className, key };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_K,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT lock FROM Lock lock WHERE ");

				if (className == null) {
					query.append("lock.className IS NULL");
				}
				else {
					query.append("lock.className = ?");
				}

				query.append(" AND ");

				if (key == null) {
					query.append("lock.key IS NULL");
				}
				else {
					query.append("lock.key = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (className != null) {
					qPos.add(className);
				}

				if (key != null) {
					qPos.add(key);
				}

				List<Lock> list = q.list();

				result = list;

				Lock lock = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K,
						finderArgs, list);
				}
				else {
					lock = list.get(0);

					cacheResult(lock);

					if ((lock.getClassName() == null) ||
							!lock.getClassName().equals(className) ||
							(lock.getKey() == null) ||
							!lock.getKey().equals(key)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K,
							finderArgs, lock);
					}
				}

				return lock;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_K,
						finderArgs, new ArrayList<Lock>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Lock)result;
			}
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

	public List<Lock> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Lock> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Lock> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Lock> list = (List<Lock>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT lock FROM Lock lock ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("lock.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<Lock>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Lock>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Lock>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (Lock lock : findByUuid(uuid)) {
			remove(lock);
		}
	}

	public void removeByExpirationDate(Date expirationDate)
		throws SystemException {
		for (Lock lock : findByExpirationDate(expirationDate)) {
			remove(lock);
		}
	}

	public void removeByC_K(String className, String key)
		throws NoSuchLockException, SystemException {
		Lock lock = findByC_K(className, key);

		remove(lock);
	}

	public void removeAll() throws SystemException {
		for (Lock lock : findAll()) {
			remove(lock);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(lock) ");
				query.append("FROM Lock lock WHERE ");

				if (uuid == null) {
					query.append("lock.uuid IS NULL");
				}
				else {
					query.append("lock.uuid = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByExpirationDate(Date expirationDate)
		throws SystemException {
		Object[] finderArgs = new Object[] { expirationDate };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_EXPIRATIONDATE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(lock) ");
				query.append("FROM Lock lock WHERE ");

				if (expirationDate == null) {
					query.append("lock.expirationDate < null");
				}
				else {
					query.append("lock.expirationDate < ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (expirationDate != null) {
					qPos.add(CalendarUtil.getTimestamp(expirationDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_EXPIRATIONDATE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_K(String className, String key)
		throws SystemException {
		Object[] finderArgs = new Object[] { className, key };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_K,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(lock) ");
				query.append("FROM Lock lock WHERE ");

				if (className == null) {
					query.append("lock.className IS NULL");
				}
				else {
					query.append("lock.className = ?");
				}

				query.append(" AND ");

				if (key == null) {
					query.append("lock.key IS NULL");
				}
				else {
					query.append("lock.key = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (className != null) {
					qPos.add(className);
				}

				if (key != null) {
					qPos.add(key);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_K, finderArgs,
					count);

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

				Query q = session.createQuery(
						"SELECT COUNT(lock) FROM Lock lock");

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
						"value.object.listener.com.liferay.portal.model.Lock")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Lock>> listenersList = new ArrayList<ModelListener<Lock>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Lock>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence.impl")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence.impl")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence.impl")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence.impl")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence.impl")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence.impl")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence.impl")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence.impl")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence.impl")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence.impl")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence.impl")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence.impl")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence.impl")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence.impl")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence.impl")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence.impl")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence.impl")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence.impl")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence.impl")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence.impl")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence.impl")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence.impl")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence.impl")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence.impl")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence.impl")
	protected com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence.impl")
	protected com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static Log _log = LogFactoryUtil.getLog(LockPersistenceImpl.class);
}