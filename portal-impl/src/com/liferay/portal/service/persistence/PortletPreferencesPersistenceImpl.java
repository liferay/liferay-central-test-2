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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchPortletPreferencesException;
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
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.impl.PortletPreferencesImpl;
import com.liferay.portal.model.impl.PortletPreferencesModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="PortletPreferencesPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPreferencesPersistence
 * @see       PortletPreferencesUtil
 * @generated
 */
public class PortletPreferencesPersistenceImpl extends BasePersistenceImpl<PortletPreferences>
	implements PortletPreferencesPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = PortletPreferencesImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_PLID = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByPlid",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PLID = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByPlid",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByP_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByP_P",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_O_O_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByO_O_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_O_O_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByO_O_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_O_O_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByO_O_P_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_O_O_P_P = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByO_O_P_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(PortletPreferences portletPreferences) {
		EntityCacheUtil.putResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesImpl.class, portletPreferences.getPrimaryKey(),
			portletPreferences);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P,
			new Object[] {
				new Long(portletPreferences.getOwnerId()),
				new Integer(portletPreferences.getOwnerType()),
				new Long(portletPreferences.getPlid()),
				
			portletPreferences.getPortletId()
			}, portletPreferences);
	}

	public void cacheResult(List<PortletPreferences> portletPreferenceses) {
		for (PortletPreferences portletPreferences : portletPreferenceses) {
			if (EntityCacheUtil.getResult(
						PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
						PortletPreferencesImpl.class,
						portletPreferences.getPrimaryKey(), this) == null) {
				cacheResult(portletPreferences);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(PortletPreferencesImpl.class.getName());
		EntityCacheUtil.clearCache(PortletPreferencesImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(PortletPreferences portletPreferences) {
		EntityCacheUtil.removeResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesImpl.class, portletPreferences.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_O_O_P_P,
			new Object[] {
				new Long(portletPreferences.getOwnerId()),
				new Integer(portletPreferences.getOwnerType()),
				new Long(portletPreferences.getPlid()),
				
			portletPreferences.getPortletId()
			});
	}

	public PortletPreferences create(long portletPreferencesId) {
		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		portletPreferences.setNew(true);
		portletPreferences.setPrimaryKey(portletPreferencesId);

		return portletPreferences;
	}

	public PortletPreferences remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public PortletPreferences remove(long portletPreferencesId)
		throws NoSuchPortletPreferencesException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PortletPreferences portletPreferences = (PortletPreferences)session.get(PortletPreferencesImpl.class,
					new Long(portletPreferencesId));

			if (portletPreferences == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						portletPreferencesId);
				}

				throw new NoSuchPortletPreferencesException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					portletPreferencesId);
			}

			return remove(portletPreferences);
		}
		catch (NoSuchPortletPreferencesException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public PortletPreferences remove(PortletPreferences portletPreferences)
		throws SystemException {
		for (ModelListener<PortletPreferences> listener : listeners) {
			listener.onBeforeRemove(portletPreferences);
		}

		portletPreferences = removeImpl(portletPreferences);

		for (ModelListener<PortletPreferences> listener : listeners) {
			listener.onAfterRemove(portletPreferences);
		}

		return portletPreferences;
	}

	protected PortletPreferences removeImpl(
		PortletPreferences portletPreferences) throws SystemException {
		portletPreferences = toUnwrappedModel(portletPreferences);

		Session session = null;

		try {
			session = openSession();

			if (portletPreferences.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(PortletPreferencesImpl.class,
						portletPreferences.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(portletPreferences);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		PortletPreferencesModelImpl portletPreferencesModelImpl = (PortletPreferencesModelImpl)portletPreferences;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_O_O_P_P,
			new Object[] {
				new Long(portletPreferencesModelImpl.getOriginalOwnerId()),
				new Integer(portletPreferencesModelImpl.getOriginalOwnerType()),
				new Long(portletPreferencesModelImpl.getOriginalPlid()),
				
			portletPreferencesModelImpl.getOriginalPortletId()
			});

		EntityCacheUtil.removeResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesImpl.class, portletPreferences.getPrimaryKey());

		return portletPreferences;
	}

	public PortletPreferences updateImpl(
		com.liferay.portal.model.PortletPreferences portletPreferences,
		boolean merge) throws SystemException {
		portletPreferences = toUnwrappedModel(portletPreferences);

		boolean isNew = portletPreferences.isNew();

		PortletPreferencesModelImpl portletPreferencesModelImpl = (PortletPreferencesModelImpl)portletPreferences;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, portletPreferences, merge);

			portletPreferences.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesImpl.class, portletPreferences.getPrimaryKey(),
			portletPreferences);

		if (!isNew &&
				((portletPreferences.getOwnerId() != portletPreferencesModelImpl.getOriginalOwnerId()) ||
				(portletPreferences.getOwnerType() != portletPreferencesModelImpl.getOriginalOwnerType()) ||
				(portletPreferences.getPlid() != portletPreferencesModelImpl.getOriginalPlid()) ||
				!Validator.equals(portletPreferences.getPortletId(),
					portletPreferencesModelImpl.getOriginalPortletId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_O_O_P_P,
				new Object[] {
					new Long(portletPreferencesModelImpl.getOriginalOwnerId()),
					new Integer(portletPreferencesModelImpl.getOriginalOwnerType()),
					new Long(portletPreferencesModelImpl.getOriginalPlid()),
					
				portletPreferencesModelImpl.getOriginalPortletId()
				});
		}

		if (isNew ||
				((portletPreferences.getOwnerId() != portletPreferencesModelImpl.getOriginalOwnerId()) ||
				(portletPreferences.getOwnerType() != portletPreferencesModelImpl.getOriginalOwnerType()) ||
				(portletPreferences.getPlid() != portletPreferencesModelImpl.getOriginalPlid()) ||
				!Validator.equals(portletPreferences.getPortletId(),
					portletPreferencesModelImpl.getOriginalPortletId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P,
				new Object[] {
					new Long(portletPreferences.getOwnerId()),
					new Integer(portletPreferences.getOwnerType()),
					new Long(portletPreferences.getPlid()),
					
				portletPreferences.getPortletId()
				}, portletPreferences);
		}

		return portletPreferences;
	}

	protected PortletPreferences toUnwrappedModel(
		PortletPreferences portletPreferences) {
		if (portletPreferences instanceof PortletPreferencesImpl) {
			return portletPreferences;
		}

		PortletPreferencesImpl portletPreferencesImpl = new PortletPreferencesImpl();

		portletPreferencesImpl.setNew(portletPreferences.isNew());
		portletPreferencesImpl.setPrimaryKey(portletPreferences.getPrimaryKey());

		portletPreferencesImpl.setPortletPreferencesId(portletPreferences.getPortletPreferencesId());
		portletPreferencesImpl.setOwnerId(portletPreferences.getOwnerId());
		portletPreferencesImpl.setOwnerType(portletPreferences.getOwnerType());
		portletPreferencesImpl.setPlid(portletPreferences.getPlid());
		portletPreferencesImpl.setPortletId(portletPreferences.getPortletId());
		portletPreferencesImpl.setPreferences(portletPreferences.getPreferences());

		return portletPreferencesImpl;
	}

	public PortletPreferences findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PortletPreferences findByPrimaryKey(long portletPreferencesId)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = fetchByPrimaryKey(portletPreferencesId);

		if (portletPreferences == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					portletPreferencesId);
			}

			throw new NoSuchPortletPreferencesException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				portletPreferencesId);
		}

		return portletPreferences;
	}

	public PortletPreferences fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public PortletPreferences fetchByPrimaryKey(long portletPreferencesId)
		throws SystemException {
		PortletPreferences portletPreferences = (PortletPreferences)EntityCacheUtil.getResult(PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
				PortletPreferencesImpl.class, portletPreferencesId, this);

		if (portletPreferences == null) {
			Session session = null;

			try {
				session = openSession();

				portletPreferences = (PortletPreferences)session.get(PortletPreferencesImpl.class,
						new Long(portletPreferencesId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (portletPreferences != null) {
					cacheResult(portletPreferences);
				}

				closeSession(session);
			}
		}

		return portletPreferences;
	}

	public List<PortletPreferences> findByPlid(long plid)
		throws SystemException {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PortletPreferences> findByPlid(long plid, int start, int end)
		throws SystemException {
		return findByPlid(plid, start, end, null);
	}

	public List<PortletPreferences> findByPlid(long plid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(plid),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PLID,
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

				query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

				query.append(_FINDER_COLUMN_PLID_PLID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				list = (List<PortletPreferences>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletPreferences>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_PLID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public PortletPreferences findByPlid_First(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException, SystemException {
		List<PortletPreferences> list = findByPlid(plid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletPreferences findByPlid_Last(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException, SystemException {
		int count = countByPlid(plid);

		List<PortletPreferences> list = findByPlid(plid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletPreferences[] findByPlid_PrevAndNext(
		long portletPreferencesId, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);

		Session session = null;

		try {
			session = openSession();

			PortletPreferences[] array = new PortletPreferencesImpl[3];

			array[0] = getByPlid_PrevAndNext(session, portletPreferences, plid,
					orderByComparator, true);

			array[1] = portletPreferences;

			array[2] = getByPlid_PrevAndNext(session, portletPreferences, plid,
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

	protected PortletPreferences getByPlid_PrevAndNext(Session session,
		PortletPreferences portletPreferences, long plid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

		query.append(_FINDER_COLUMN_PLID_PLID_2);

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

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(portletPreferences);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortletPreferences> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<PortletPreferences> findByP_P(long plid, String portletId)
		throws SystemException {
		return findByP_P(plid, portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	public List<PortletPreferences> findByP_P(long plid, String portletId,
		int start, int end) throws SystemException {
		return findByP_P(plid, portletId, start, end, null);
	}

	public List<PortletPreferences> findByP_P(long plid, String portletId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(plid),
				
				portletId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

				query.append(_FINDER_COLUMN_P_P_PLID_2);

				if (portletId == null) {
					query.append(_FINDER_COLUMN_P_P_PORTLETID_1);
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_P_P_PORTLETID_3);
					}
					else {
						query.append(_FINDER_COLUMN_P_P_PORTLETID_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				if (portletId != null) {
					qPos.add(portletId);
				}

				list = (List<PortletPreferences>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletPreferences>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public PortletPreferences findByP_P_First(long plid, String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException, SystemException {
		List<PortletPreferences> list = findByP_P(plid, portletId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("plid=");
			msg.append(plid);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletPreferences findByP_P_Last(long plid, String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException, SystemException {
		int count = countByP_P(plid, portletId);

		List<PortletPreferences> list = findByP_P(plid, portletId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("plid=");
			msg.append(plid);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletPreferences[] findByP_P_PrevAndNext(
		long portletPreferencesId, long plid, String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);

		Session session = null;

		try {
			session = openSession();

			PortletPreferences[] array = new PortletPreferencesImpl[3];

			array[0] = getByP_P_PrevAndNext(session, portletPreferences, plid,
					portletId, orderByComparator, true);

			array[1] = portletPreferences;

			array[2] = getByP_P_PrevAndNext(session, portletPreferences, plid,
					portletId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortletPreferences getByP_P_PrevAndNext(Session session,
		PortletPreferences portletPreferences, long plid, String portletId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

		query.append(_FINDER_COLUMN_P_P_PLID_2);

		if (portletId == null) {
			query.append(_FINDER_COLUMN_P_P_PORTLETID_1);
		}
		else {
			if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_P_P_PORTLETID_3);
			}
			else {
				query.append(_FINDER_COLUMN_P_P_PORTLETID_2);
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

		qPos.add(plid);

		if (portletId != null) {
			qPos.add(portletId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(portletPreferences);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortletPreferences> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<PortletPreferences> findByO_O_P(long ownerId, int ownerType,
		long plid) throws SystemException {
		return findByO_O_P(ownerId, ownerType, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<PortletPreferences> findByO_O_P(long ownerId, int ownerType,
		long plid, int start, int end) throws SystemException {
		return findByO_O_P(ownerId, ownerType, plid, start, end, null);
	}

	public List<PortletPreferences> findByO_O_P(long ownerId, int ownerType,
		long plid, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(ownerId), new Integer(ownerType), new Long(plid),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_O_O_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(5 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

				query.append(_FINDER_COLUMN_O_O_P_OWNERID_2);

				query.append(_FINDER_COLUMN_O_O_P_OWNERTYPE_2);

				query.append(_FINDER_COLUMN_O_O_P_PLID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				qPos.add(plid);

				list = (List<PortletPreferences>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletPreferences>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_O_O_P,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public PortletPreferences findByO_O_P_First(long ownerId, int ownerType,
		long plid, OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException, SystemException {
		List<PortletPreferences> list = findByO_O_P(ownerId, ownerType, plid,
				0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("ownerId=");
			msg.append(ownerId);

			msg.append(", ownerType=");
			msg.append(ownerType);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletPreferences findByO_O_P_Last(long ownerId, int ownerType,
		long plid, OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException, SystemException {
		int count = countByO_O_P(ownerId, ownerType, plid);

		List<PortletPreferences> list = findByO_O_P(ownerId, ownerType, plid,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("ownerId=");
			msg.append(ownerId);

			msg.append(", ownerType=");
			msg.append(ownerType);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPortletPreferencesException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public PortletPreferences[] findByO_O_P_PrevAndNext(
		long portletPreferencesId, long ownerId, int ownerType, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = findByPrimaryKey(portletPreferencesId);

		Session session = null;

		try {
			session = openSession();

			PortletPreferences[] array = new PortletPreferencesImpl[3];

			array[0] = getByO_O_P_PrevAndNext(session, portletPreferences,
					ownerId, ownerType, plid, orderByComparator, true);

			array[1] = portletPreferences;

			array[2] = getByO_O_P_PrevAndNext(session, portletPreferences,
					ownerId, ownerType, plid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortletPreferences getByO_O_P_PrevAndNext(Session session,
		PortletPreferences portletPreferences, long ownerId, int ownerType,
		long plid, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

		query.append(_FINDER_COLUMN_O_O_P_OWNERID_2);

		query.append(_FINDER_COLUMN_O_O_P_OWNERTYPE_2);

		query.append(_FINDER_COLUMN_O_O_P_PLID_2);

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

		qPos.add(ownerId);

		qPos.add(ownerType);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(portletPreferences);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortletPreferences> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public PortletPreferences findByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = fetchByO_O_P_P(ownerId,
				ownerType, plid, portletId);

		if (portletPreferences == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("ownerId=");
			msg.append(ownerId);

			msg.append(", ownerType=");
			msg.append(ownerType);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPortletPreferencesException(msg.toString());
		}

		return portletPreferences;
	}

	public PortletPreferences fetchByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId) throws SystemException {
		return fetchByO_O_P_P(ownerId, ownerType, plid, portletId, true);
	}

	public PortletPreferences fetchByO_O_P_P(long ownerId, int ownerType,
		long plid, String portletId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(ownerId), new Integer(ownerType), new Long(plid),
				
				portletId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_O_O_P_P,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_PORTLETPREFERENCES_WHERE);

				query.append(_FINDER_COLUMN_O_O_P_P_OWNERID_2);

				query.append(_FINDER_COLUMN_O_O_P_P_OWNERTYPE_2);

				query.append(_FINDER_COLUMN_O_O_P_P_PLID_2);

				if (portletId == null) {
					query.append(_FINDER_COLUMN_O_O_P_P_PORTLETID_1);
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_O_O_P_P_PORTLETID_3);
					}
					else {
						query.append(_FINDER_COLUMN_O_O_P_P_PORTLETID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				qPos.add(plid);

				if (portletId != null) {
					qPos.add(portletId);
				}

				List<PortletPreferences> list = q.list();

				result = list;

				PortletPreferences portletPreferences = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P,
						finderArgs, list);
				}
				else {
					portletPreferences = list.get(0);

					cacheResult(portletPreferences);

					if ((portletPreferences.getOwnerId() != ownerId) ||
							(portletPreferences.getOwnerType() != ownerType) ||
							(portletPreferences.getPlid() != plid) ||
							(portletPreferences.getPortletId() == null) ||
							!portletPreferences.getPortletId().equals(portletId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P,
							finderArgs, portletPreferences);
					}
				}

				return portletPreferences;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_O_O_P_P,
						finderArgs, new ArrayList<PortletPreferences>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (PortletPreferences)result;
			}
		}
	}

	public List<PortletPreferences> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<PortletPreferences> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<PortletPreferences> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<PortletPreferences> list = (List<PortletPreferences>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_PORTLETPREFERENCES);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_PORTLETPREFERENCES;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<PortletPreferences>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<PortletPreferences>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByPlid(long plid) throws SystemException {
		for (PortletPreferences portletPreferences : findByPlid(plid)) {
			remove(portletPreferences);
		}
	}

	public void removeByP_P(long plid, String portletId)
		throws SystemException {
		for (PortletPreferences portletPreferences : findByP_P(plid, portletId)) {
			remove(portletPreferences);
		}
	}

	public void removeByO_O_P(long ownerId, int ownerType, long plid)
		throws SystemException {
		for (PortletPreferences portletPreferences : findByO_O_P(ownerId,
				ownerType, plid)) {
			remove(portletPreferences);
		}
	}

	public void removeByO_O_P_P(long ownerId, int ownerType, long plid,
		String portletId)
		throws NoSuchPortletPreferencesException, SystemException {
		PortletPreferences portletPreferences = findByO_O_P_P(ownerId,
				ownerType, plid, portletId);

		remove(portletPreferences);
	}

	public void removeAll() throws SystemException {
		for (PortletPreferences portletPreferences : findAll()) {
			remove(portletPreferences);
		}
	}

	public int countByPlid(long plid) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(plid) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PLID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_PORTLETPREFERENCES_WHERE);

				query.append(_FINDER_COLUMN_PLID_PLID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PLID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_P(long plid, String portletId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(plid), portletId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_PORTLETPREFERENCES_WHERE);

				query.append(_FINDER_COLUMN_P_P_PLID_2);

				if (portletId == null) {
					query.append(_FINDER_COLUMN_P_P_PORTLETID_1);
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_P_P_PORTLETID_3);
					}
					else {
						query.append(_FINDER_COLUMN_P_P_PORTLETID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				if (portletId != null) {
					qPos.add(portletId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByO_O_P(long ownerId, int ownerType, long plid)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(ownerId), new Integer(ownerType), new Long(plid)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_O_O_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_PORTLETPREFERENCES_WHERE);

				query.append(_FINDER_COLUMN_O_O_P_OWNERID_2);

				query.append(_FINDER_COLUMN_O_O_P_OWNERTYPE_2);

				query.append(_FINDER_COLUMN_O_O_P_PLID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				qPos.add(plid);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_O_O_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByO_O_P_P(long ownerId, int ownerType, long plid,
		String portletId) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(ownerId), new Integer(ownerType), new Long(plid),
				
				portletId
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_O_O_P_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_COUNT_PORTLETPREFERENCES_WHERE);

				query.append(_FINDER_COLUMN_O_O_P_P_OWNERID_2);

				query.append(_FINDER_COLUMN_O_O_P_P_OWNERTYPE_2);

				query.append(_FINDER_COLUMN_O_O_P_P_PLID_2);

				if (portletId == null) {
					query.append(_FINDER_COLUMN_O_O_P_P_PORTLETID_1);
				}
				else {
					if (portletId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_O_O_P_P_PORTLETID_3);
					}
					else {
						query.append(_FINDER_COLUMN_O_O_P_P_PORTLETID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ownerId);

				qPos.add(ownerType);

				qPos.add(plid);

				if (portletId != null) {
					qPos.add(portletId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_O_O_P_P,
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

				Query q = session.createQuery(_SQL_COUNT_PORTLETPREFERENCES);

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
						"value.object.listener.com.liferay.portal.model.PortletPreferences")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PortletPreferences>> listenersList = new ArrayList<ModelListener<PortletPreferences>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PortletPreferences>)Class.forName(
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
	private static final String _SQL_SELECT_PORTLETPREFERENCES = "SELECT portletPreferences FROM PortletPreferences portletPreferences";
	private static final String _SQL_SELECT_PORTLETPREFERENCES_WHERE = "SELECT portletPreferences FROM PortletPreferences portletPreferences WHERE ";
	private static final String _SQL_COUNT_PORTLETPREFERENCES = "SELECT COUNT(portletPreferences) FROM PortletPreferences portletPreferences";
	private static final String _SQL_COUNT_PORTLETPREFERENCES_WHERE = "SELECT COUNT(portletPreferences) FROM PortletPreferences portletPreferences WHERE ";
	private static final String _FINDER_COLUMN_PLID_PLID_2 = "portletPreferences.plid = ?";
	private static final String _FINDER_COLUMN_P_P_PLID_2 = "portletPreferences.plid = ? AND ";
	private static final String _FINDER_COLUMN_P_P_PORTLETID_1 = "portletPreferences.portletId IS NULL";
	private static final String _FINDER_COLUMN_P_P_PORTLETID_2 = "portletPreferences.portletId = ?";
	private static final String _FINDER_COLUMN_P_P_PORTLETID_3 = "(portletPreferences.portletId IS NULL OR portletPreferences.portletId = ?)";
	private static final String _FINDER_COLUMN_O_O_P_OWNERID_2 = "portletPreferences.ownerId = ? AND ";
	private static final String _FINDER_COLUMN_O_O_P_OWNERTYPE_2 = "portletPreferences.ownerType = ? AND ";
	private static final String _FINDER_COLUMN_O_O_P_PLID_2 = "portletPreferences.plid = ?";
	private static final String _FINDER_COLUMN_O_O_P_P_OWNERID_2 = "portletPreferences.ownerId = ? AND ";
	private static final String _FINDER_COLUMN_O_O_P_P_OWNERTYPE_2 = "portletPreferences.ownerType = ? AND ";
	private static final String _FINDER_COLUMN_O_O_P_P_PLID_2 = "portletPreferences.plid = ? AND ";
	private static final String _FINDER_COLUMN_O_O_P_P_PORTLETID_1 = "portletPreferences.portletId IS NULL";
	private static final String _FINDER_COLUMN_O_O_P_P_PORTLETID_2 = "portletPreferences.portletId = ?";
	private static final String _FINDER_COLUMN_O_O_P_P_PORTLETID_3 = "(portletPreferences.portletId IS NULL OR portletPreferences.portletId = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "portletPreferences.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PortletPreferences exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PortletPreferences exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(PortletPreferencesPersistenceImpl.class);
}