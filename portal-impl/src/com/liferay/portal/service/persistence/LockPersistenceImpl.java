/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
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
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + lockId);
				}

				throw new NoSuchLockException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					lockId);
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
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + lockId);
			}

			throw new NoSuchLockException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				lockId);
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

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_LOCK_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

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
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Lock> list = (List<Lock>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_LOCK_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

	public Lock findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		List<Lock> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLockException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Lock findByUuid_Last(String uuid, OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		int count = countByUuid(uuid);

		List<Lock> list = findByUuid(uuid, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLockException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Lock[] findByUuid_PrevAndNext(long lockId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		Lock lock = findByPrimaryKey(lockId);

		Session session = null;

		try {
			session = openSession();

			Lock[] array = new LockImpl[3];

			array[0] = getByUuid_PrevAndNext(session, lock, uuid,
					orderByComparator, true);

			array[1] = lock;

			array[2] = getByUuid_PrevAndNext(session, lock, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Lock getByUuid_PrevAndNext(Session session, Lock lock,
		String uuid, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LOCK_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(lock);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Lock> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
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

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_LOCK_WHERE);

				if (expirationDate == null) {
					query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

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
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				expirationDate,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Lock> list = (List<Lock>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_EXPIRATIONDATE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_LOCK_WHERE);

				if (expirationDate == null) {
					query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

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
		OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		List<Lock> list = findByExpirationDate(expirationDate, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("expirationDate=");
			msg.append(expirationDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLockException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Lock findByExpirationDate_Last(Date expirationDate,
		OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		int count = countByExpirationDate(expirationDate);

		List<Lock> list = findByExpirationDate(expirationDate, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("expirationDate=");
			msg.append(expirationDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLockException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Lock[] findByExpirationDate_PrevAndNext(long lockId,
		Date expirationDate, OrderByComparator orderByComparator)
		throws NoSuchLockException, SystemException {
		Lock lock = findByPrimaryKey(lockId);

		Session session = null;

		try {
			session = openSession();

			Lock[] array = new LockImpl[3];

			array[0] = getByExpirationDate_PrevAndNext(session, lock,
					expirationDate, orderByComparator, true);

			array[1] = lock;

			array[2] = getByExpirationDate_PrevAndNext(session, lock,
					expirationDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Lock getByExpirationDate_PrevAndNext(Session session, Lock lock,
		Date expirationDate, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LOCK_WHERE);

		if (expirationDate == null) {
			query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}

			query.append(WHERE_LIMIT_2);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		if (expirationDate != null) {
			qPos.add(CalendarUtil.getTimestamp(expirationDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(lock);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Lock> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public Lock findByC_K(String className, String key)
		throws NoSuchLockException, SystemException {
		Lock lock = fetchByC_K(className, key);

		if (lock == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("className=");
			msg.append(className);

			msg.append(", key=");
			msg.append(key);

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

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_LOCK_WHERE);

				if (className == null) {
					query.append(_FINDER_COLUMN_C_K_CLASSNAME_1);
				}
				else {
					if (className.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_K_CLASSNAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_K_CLASSNAME_2);
					}
				}

				if (key == null) {
					query.append(_FINDER_COLUMN_C_K_KEY_1);
				}
				else {
					if (key.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_K_KEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_K_KEY_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

	public List<Lock> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Lock> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Lock> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Lock> list = (List<Lock>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (orderByComparator != null) {
					query = new StringBundler(2 +
							(orderByComparator.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_LOCK);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_LOCK;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
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

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_LOCK_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_LOCK_WHERE);

				if (expirationDate == null) {
					query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
				}
				else {
					query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_LOCK_WHERE);

				if (className == null) {
					query.append(_FINDER_COLUMN_C_K_CLASSNAME_1);
				}
				else {
					if (className.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_K_CLASSNAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_K_CLASSNAME_2);
					}
				}

				if (key == null) {
					query.append(_FINDER_COLUMN_C_K_KEY_1);
				}
				else {
					if (key.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_K_KEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_K_KEY_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

				Query q = session.createQuery(_SQL_COUNT_LOCK);

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

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupPermissionPersistence.class)
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PermissionPersistence.class)
	protected PermissionPersistence permissionPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_LOCK = "SELECT lock FROM Lock lock";
	private static final String _SQL_SELECT_LOCK_WHERE = "SELECT lock FROM Lock lock WHERE ";
	private static final String _SQL_COUNT_LOCK = "SELECT COUNT(lock) FROM Lock lock";
	private static final String _SQL_COUNT_LOCK_WHERE = "SELECT COUNT(lock) FROM Lock lock WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "lock.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "lock.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(lock.uuid IS NULL OR lock.uuid = ?)";
	private static final String _FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1 = "lock.expirationDate < NULL";
	private static final String _FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2 = "lock.expirationDate < ?";
	private static final String _FINDER_COLUMN_C_K_CLASSNAME_1 = "lock.className IS NULL AND ";
	private static final String _FINDER_COLUMN_C_K_CLASSNAME_2 = "lock.className = ? AND ";
	private static final String _FINDER_COLUMN_C_K_CLASSNAME_3 = "(lock.className IS NULL OR lock.className = ?) AND ";
	private static final String _FINDER_COLUMN_C_K_KEY_1 = "lock.key IS NULL";
	private static final String _FINDER_COLUMN_C_K_KEY_2 = "lock.key = ?";
	private static final String _FINDER_COLUMN_C_K_KEY_3 = "(lock.key IS NULL OR lock.key = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "lock.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Lock exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Lock exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(LockPersistenceImpl.class);
}