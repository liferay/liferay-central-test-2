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

package com.liferay.portlet.announcements.service.persistence;

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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.announcements.NoSuchDeliveryException;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AnnouncementsDeliveryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsDeliveryPersistence
 * @see       AnnouncementsDeliveryUtil
 * @generated
 */
public class AnnouncementsDeliveryPersistenceImpl extends BasePersistenceImpl<AnnouncementsDelivery>
	implements AnnouncementsDeliveryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AnnouncementsDeliveryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_T = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_U_T = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(AnnouncementsDelivery announcementsDelivery) {
		EntityCacheUtil.putResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			announcementsDelivery.getPrimaryKey(), announcementsDelivery);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				new Long(announcementsDelivery.getUserId()),
				
			announcementsDelivery.getType()
			}, announcementsDelivery);
	}

	public void cacheResult(List<AnnouncementsDelivery> announcementsDeliveries) {
		for (AnnouncementsDelivery announcementsDelivery : announcementsDeliveries) {
			if (EntityCacheUtil.getResult(
						AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsDeliveryImpl.class,
						announcementsDelivery.getPrimaryKey(), this) == null) {
				cacheResult(announcementsDelivery);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AnnouncementsDeliveryImpl.class.getName());
		EntityCacheUtil.clearCache(AnnouncementsDeliveryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(AnnouncementsDelivery announcementsDelivery) {
		EntityCacheUtil.removeResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			announcementsDelivery.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				new Long(announcementsDelivery.getUserId()),
				
			announcementsDelivery.getType()
			});
	}

	public AnnouncementsDelivery create(long deliveryId) {
		AnnouncementsDelivery announcementsDelivery = new AnnouncementsDeliveryImpl();

		announcementsDelivery.setNew(true);
		announcementsDelivery.setPrimaryKey(deliveryId);

		return announcementsDelivery;
	}

	public AnnouncementsDelivery remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public AnnouncementsDelivery remove(long deliveryId)
		throws NoSuchDeliveryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AnnouncementsDelivery announcementsDelivery = (AnnouncementsDelivery)session.get(AnnouncementsDeliveryImpl.class,
					new Long(deliveryId));

			if (announcementsDelivery == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + deliveryId);
				}

				throw new NoSuchDeliveryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					deliveryId);
			}

			return remove(announcementsDelivery);
		}
		catch (NoSuchDeliveryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementsDelivery remove(
		AnnouncementsDelivery announcementsDelivery) throws SystemException {
		for (ModelListener<AnnouncementsDelivery> listener : listeners) {
			listener.onBeforeRemove(announcementsDelivery);
		}

		announcementsDelivery = removeImpl(announcementsDelivery);

		for (ModelListener<AnnouncementsDelivery> listener : listeners) {
			listener.onAfterRemove(announcementsDelivery);
		}

		return announcementsDelivery;
	}

	protected AnnouncementsDelivery removeImpl(
		AnnouncementsDelivery announcementsDelivery) throws SystemException {
		announcementsDelivery = toUnwrappedModel(announcementsDelivery);

		Session session = null;

		try {
			session = openSession();

			if (announcementsDelivery.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AnnouncementsDeliveryImpl.class,
						announcementsDelivery.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(announcementsDelivery);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		AnnouncementsDeliveryModelImpl announcementsDeliveryModelImpl = (AnnouncementsDeliveryModelImpl)announcementsDelivery;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				new Long(announcementsDeliveryModelImpl.getOriginalUserId()),
				
			announcementsDeliveryModelImpl.getOriginalType()
			});

		EntityCacheUtil.removeResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			announcementsDelivery.getPrimaryKey());

		return announcementsDelivery;
	}

	public AnnouncementsDelivery updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery,
		boolean merge) throws SystemException {
		announcementsDelivery = toUnwrappedModel(announcementsDelivery);

		boolean isNew = announcementsDelivery.isNew();

		AnnouncementsDeliveryModelImpl announcementsDeliveryModelImpl = (AnnouncementsDeliveryModelImpl)announcementsDelivery;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, announcementsDelivery, merge);

			announcementsDelivery.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsDeliveryImpl.class,
			announcementsDelivery.getPrimaryKey(), announcementsDelivery);

		if (!isNew &&
				((announcementsDelivery.getUserId() != announcementsDeliveryModelImpl.getOriginalUserId()) ||
				!Validator.equals(announcementsDelivery.getType(),
					announcementsDeliveryModelImpl.getOriginalType()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
				new Object[] {
					new Long(announcementsDeliveryModelImpl.getOriginalUserId()),
					
				announcementsDeliveryModelImpl.getOriginalType()
				});
		}

		if (isNew ||
				((announcementsDelivery.getUserId() != announcementsDeliveryModelImpl.getOriginalUserId()) ||
				!Validator.equals(announcementsDelivery.getType(),
					announcementsDeliveryModelImpl.getOriginalType()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
				new Object[] {
					new Long(announcementsDelivery.getUserId()),
					
				announcementsDelivery.getType()
				}, announcementsDelivery);
		}

		return announcementsDelivery;
	}

	protected AnnouncementsDelivery toUnwrappedModel(
		AnnouncementsDelivery announcementsDelivery) {
		if (announcementsDelivery instanceof AnnouncementsDeliveryImpl) {
			return announcementsDelivery;
		}

		AnnouncementsDeliveryImpl announcementsDeliveryImpl = new AnnouncementsDeliveryImpl();

		announcementsDeliveryImpl.setNew(announcementsDelivery.isNew());
		announcementsDeliveryImpl.setPrimaryKey(announcementsDelivery.getPrimaryKey());

		announcementsDeliveryImpl.setDeliveryId(announcementsDelivery.getDeliveryId());
		announcementsDeliveryImpl.setCompanyId(announcementsDelivery.getCompanyId());
		announcementsDeliveryImpl.setUserId(announcementsDelivery.getUserId());
		announcementsDeliveryImpl.setType(announcementsDelivery.getType());
		announcementsDeliveryImpl.setEmail(announcementsDelivery.isEmail());
		announcementsDeliveryImpl.setSms(announcementsDelivery.isSms());
		announcementsDeliveryImpl.setWebsite(announcementsDelivery.isWebsite());

		return announcementsDeliveryImpl;
	}

	public AnnouncementsDelivery findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AnnouncementsDelivery findByPrimaryKey(long deliveryId)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = fetchByPrimaryKey(deliveryId);

		if (announcementsDelivery == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + deliveryId);
			}

			throw new NoSuchDeliveryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				deliveryId);
		}

		return announcementsDelivery;
	}

	public AnnouncementsDelivery fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AnnouncementsDelivery fetchByPrimaryKey(long deliveryId)
		throws SystemException {
		AnnouncementsDelivery announcementsDelivery = (AnnouncementsDelivery)EntityCacheUtil.getResult(AnnouncementsDeliveryModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsDeliveryImpl.class, deliveryId, this);

		if (announcementsDelivery == null) {
			Session session = null;

			try {
				session = openSession();

				announcementsDelivery = (AnnouncementsDelivery)session.get(AnnouncementsDeliveryImpl.class,
						new Long(deliveryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (announcementsDelivery != null) {
					cacheResult(announcementsDelivery);
				}

				closeSession(session);
			}
		}

		return announcementsDelivery;
	}

	public List<AnnouncementsDelivery> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AnnouncementsDelivery> findByUserId(long userId, int start,
		int end) throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<AnnouncementsDelivery> findByUserId(long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AnnouncementsDelivery> list = (List<AnnouncementsDelivery>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
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

				query.append(_SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsDelivery>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AnnouncementsDelivery findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchDeliveryException, SystemException {
		List<AnnouncementsDelivery> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDeliveryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsDelivery findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchDeliveryException, SystemException {
		int count = countByUserId(userId);

		List<AnnouncementsDelivery> list = findByUserId(userId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDeliveryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsDelivery[] findByUserId_PrevAndNext(long deliveryId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = findByPrimaryKey(deliveryId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsDelivery[] array = new AnnouncementsDeliveryImpl[3];

			array[0] = getByUserId_PrevAndNext(session, announcementsDelivery,
					userId, orderByComparator, true);

			array[1] = announcementsDelivery;

			array[2] = getByUserId_PrevAndNext(session, announcementsDelivery,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnnouncementsDelivery getByUserId_PrevAndNext(Session session,
		AnnouncementsDelivery announcementsDelivery, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(announcementsDelivery);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsDelivery> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public AnnouncementsDelivery findByU_T(long userId, String type)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = fetchByU_T(userId, type);

		if (announcementsDelivery == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchDeliveryException(msg.toString());
		}

		return announcementsDelivery;
	}

	public AnnouncementsDelivery fetchByU_T(long userId, String type)
		throws SystemException {
		return fetchByU_T(userId, type, true);
	}

	public AnnouncementsDelivery fetchByU_T(long userId, String type,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId), type };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_T,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE);

				query.append(_FINDER_COLUMN_U_T_USERID_2);

				if (type == null) {
					query.append(_FINDER_COLUMN_U_T_TYPE_1);
				}
				else {
					if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_U_T_TYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_U_T_TYPE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (type != null) {
					qPos.add(type);
				}

				List<AnnouncementsDelivery> list = q.list();

				result = list;

				AnnouncementsDelivery announcementsDelivery = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
						finderArgs, list);
				}
				else {
					announcementsDelivery = list.get(0);

					cacheResult(announcementsDelivery);

					if ((announcementsDelivery.getUserId() != userId) ||
							(announcementsDelivery.getType() == null) ||
							!announcementsDelivery.getType().equals(type)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
							finderArgs, announcementsDelivery);
					}
				}

				return announcementsDelivery;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
						finderArgs, new ArrayList<AnnouncementsDelivery>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (AnnouncementsDelivery)result;
			}
		}
	}

	public List<AnnouncementsDelivery> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AnnouncementsDelivery> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AnnouncementsDelivery> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AnnouncementsDelivery> list = (List<AnnouncementsDelivery>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_ANNOUNCEMENTSDELIVERY);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_ANNOUNCEMENTSDELIVERY;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AnnouncementsDelivery>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsDelivery>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUserId(long userId) throws SystemException {
		for (AnnouncementsDelivery announcementsDelivery : findByUserId(userId)) {
			remove(announcementsDelivery);
		}
	}

	public void removeByU_T(long userId, String type)
		throws NoSuchDeliveryException, SystemException {
		AnnouncementsDelivery announcementsDelivery = findByU_T(userId, type);

		remove(announcementsDelivery);
	}

	public void removeAll() throws SystemException {
		for (AnnouncementsDelivery announcementsDelivery : findAll()) {
			remove(announcementsDelivery);
		}
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

				query.append(_SQL_COUNT_ANNOUNCEMENTSDELIVERY_WHERE);

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

	public int countByU_T(long userId, String type) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId), type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_ANNOUNCEMENTSDELIVERY_WHERE);

				query.append(_FINDER_COLUMN_U_T_USERID_2);

				if (type == null) {
					query.append(_FINDER_COLUMN_U_T_TYPE_1);
				}
				else {
					if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_U_T_TYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_U_T_TYPE_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (type != null) {
					qPos.add(type);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_T, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_ANNOUNCEMENTSDELIVERY);

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
						"value.object.listener.com.liferay.portlet.announcements.model.AnnouncementsDelivery")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AnnouncementsDelivery>> listenersList = new ArrayList<ModelListener<AnnouncementsDelivery>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AnnouncementsDelivery>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = AnnouncementsDeliveryPersistence.class)
	protected AnnouncementsDeliveryPersistence announcementsDeliveryPersistence;
	@BeanReference(type = AnnouncementsEntryPersistence.class)
	protected AnnouncementsEntryPersistence announcementsEntryPersistence;
	@BeanReference(type = AnnouncementsFlagPersistence.class)
	protected AnnouncementsFlagPersistence announcementsFlagPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_ANNOUNCEMENTSDELIVERY = "SELECT announcementsDelivery FROM AnnouncementsDelivery announcementsDelivery";
	private static final String _SQL_SELECT_ANNOUNCEMENTSDELIVERY_WHERE = "SELECT announcementsDelivery FROM AnnouncementsDelivery announcementsDelivery WHERE ";
	private static final String _SQL_COUNT_ANNOUNCEMENTSDELIVERY = "SELECT COUNT(announcementsDelivery) FROM AnnouncementsDelivery announcementsDelivery";
	private static final String _SQL_COUNT_ANNOUNCEMENTSDELIVERY_WHERE = "SELECT COUNT(announcementsDelivery) FROM AnnouncementsDelivery announcementsDelivery WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "announcementsDelivery.userId = ?";
	private static final String _FINDER_COLUMN_U_T_USERID_2 = "announcementsDelivery.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_T_TYPE_1 = "announcementsDelivery.type IS NULL";
	private static final String _FINDER_COLUMN_U_T_TYPE_2 = "announcementsDelivery.type = ?";
	private static final String _FINDER_COLUMN_U_T_TYPE_3 = "(announcementsDelivery.type IS NULL OR announcementsDelivery.type = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "announcementsDelivery.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AnnouncementsDelivery exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AnnouncementsDelivery exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(AnnouncementsDeliveryPersistenceImpl.class);
}