/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.dynamic.data.lists.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.exception.NoSuchRecordVersionException;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordVersionImpl;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordVersionModelImpl;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordVersionPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the ddl record version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersionPersistence
 * @see com.liferay.dynamic.data.lists.service.persistence.DDLRecordVersionUtil
 * @generated
 */
@ProviderType
public class DDLRecordVersionPersistenceImpl extends BasePersistenceImpl<DDLRecordVersion>
	implements DDLRecordVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDLRecordVersionUtil} to access the ddl record version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDLRecordVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED,
			DDLRecordVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED,
			DDLRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_RECORDID = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED,
			DDLRecordVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByRecordId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDID =
		new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED,
			DDLRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRecordId",
			new String[] { Long.class.getName() },
			DDLRecordVersionModelImpl.RECORDID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RECORDID = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRecordId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the ddl record versions where recordId = &#63;.
	 *
	 * @param recordId the record ID
	 * @return the matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByRecordId(long recordId) {
		return findByRecordId(recordId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the ddl record versions where recordId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordId the record ID
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @return the range of matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByRecordId(long recordId, int start,
		int end) {
		return findByRecordId(recordId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl record versions where recordId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordId the record ID
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByRecordId(long recordId, int start,
		int end, OrderByComparator<DDLRecordVersion> orderByComparator) {
		return findByRecordId(recordId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl record versions where recordId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordId the record ID
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByRecordId(long recordId, int start,
		int end, OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDID;
			finderArgs = new Object[] { recordId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_RECORDID;
			finderArgs = new Object[] { recordId, start, end, orderByComparator };
		}

		List<DDLRecordVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DDLRecordVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecordVersion ddlRecordVersion : list) {
					if ((recordId != ddlRecordVersion.getRecordId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DDLRECORDVERSION_WHERE);

			query.append(_FINDER_COLUMN_RECORDID_RECORDID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDLRecordVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordId);

				if (!pagination) {
					list = (List<DDLRecordVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDLRecordVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ddl record version in the ordered set where recordId = &#63;.
	 *
	 * @param recordId the record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record version
	 * @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion findByRecordId_First(long recordId,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = fetchByRecordId_First(recordId,
				orderByComparator);

		if (ddlRecordVersion != null) {
			return ddlRecordVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordId=");
		msg.append(recordId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordVersionException(msg.toString());
	}

	/**
	 * Returns the first ddl record version in the ordered set where recordId = &#63;.
	 *
	 * @param recordId the record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion fetchByRecordId_First(long recordId,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		List<DDLRecordVersion> list = findByRecordId(recordId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddl record version in the ordered set where recordId = &#63;.
	 *
	 * @param recordId the record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record version
	 * @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion findByRecordId_Last(long recordId,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = fetchByRecordId_Last(recordId,
				orderByComparator);

		if (ddlRecordVersion != null) {
			return ddlRecordVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordId=");
		msg.append(recordId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordVersionException(msg.toString());
	}

	/**
	 * Returns the last ddl record version in the ordered set where recordId = &#63;.
	 *
	 * @param recordId the record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion fetchByRecordId_Last(long recordId,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		int count = countByRecordId(recordId);

		if (count == 0) {
			return null;
		}

		List<DDLRecordVersion> list = findByRecordId(recordId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddl record versions before and after the current ddl record version in the ordered set where recordId = &#63;.
	 *
	 * @param recordVersionId the primary key of the current ddl record version
	 * @param recordId the record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record version
	 * @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	 */
	@Override
	public DDLRecordVersion[] findByRecordId_PrevAndNext(long recordVersionId,
		long recordId, OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = findByPrimaryKey(recordVersionId);

		Session session = null;

		try {
			session = openSession();

			DDLRecordVersion[] array = new DDLRecordVersionImpl[3];

			array[0] = getByRecordId_PrevAndNext(session, ddlRecordVersion,
					recordId, orderByComparator, true);

			array[1] = ddlRecordVersion;

			array[2] = getByRecordId_PrevAndNext(session, ddlRecordVersion,
					recordId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDLRecordVersion getByRecordId_PrevAndNext(Session session,
		DDLRecordVersion ddlRecordVersion, long recordId,
		OrderByComparator<DDLRecordVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDLRECORDVERSION_WHERE);

		query.append(_FINDER_COLUMN_RECORDID_RECORDID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
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

			String[] orderByFields = orderByComparator.getOrderByFields();

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
		}
		else {
			query.append(DDLRecordVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(recordId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddlRecordVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLRecordVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddl record versions where recordId = &#63; from the database.
	 *
	 * @param recordId the record ID
	 */
	@Override
	public void removeByRecordId(long recordId) {
		for (DDLRecordVersion ddlRecordVersion : findByRecordId(recordId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddlRecordVersion);
		}
	}

	/**
	 * Returns the number of ddl record versions where recordId = &#63;.
	 *
	 * @param recordId the record ID
	 * @return the number of matching ddl record versions
	 */
	@Override
	public int countByRecordId(long recordId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_RECORDID;

		Object[] finderArgs = new Object[] { recordId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDLRECORDVERSION_WHERE);

			query.append(_FINDER_COLUMN_RECORDID_RECORDID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_RECORDID_RECORDID_2 = "ddlRecordVersion.recordId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_R_R = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED,
			DDLRecordVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByR_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_R = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED,
			DDLRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_R",
			new String[] { Long.class.getName(), String.class.getName() },
			DDLRecordVersionModelImpl.RECORDSETID_COLUMN_BITMASK |
			DDLRecordVersionModelImpl.RECORDSETVERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_R = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_R",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the ddl record versions where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @return the matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByR_R(long recordSetId,
		String recordSetVersion) {
		return findByR_R(recordSetId, recordSetVersion, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl record versions where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @return the range of matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByR_R(long recordSetId,
		String recordSetVersion, int start, int end) {
		return findByR_R(recordSetId, recordSetVersion, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl record versions where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByR_R(long recordSetId,
		String recordSetVersion, int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		return findByR_R(recordSetId, recordSetVersion, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl record versions where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByR_R(long recordSetId,
		String recordSetVersion, int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_R;
			finderArgs = new Object[] { recordSetId, recordSetVersion };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_R_R;
			finderArgs = new Object[] {
					recordSetId, recordSetVersion,
					
					start, end, orderByComparator
				};
		}

		List<DDLRecordVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DDLRecordVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecordVersion ddlRecordVersion : list) {
					if ((recordSetId != ddlRecordVersion.getRecordSetId()) ||
							!Objects.equals(recordSetVersion,
								ddlRecordVersion.getRecordSetVersion())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DDLRECORDVERSION_WHERE);

			query.append(_FINDER_COLUMN_R_R_RECORDSETID_2);

			boolean bindRecordSetVersion = false;

			if (recordSetVersion == null) {
				query.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_1);
			}
			else if (recordSetVersion.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_3);
			}
			else {
				bindRecordSetVersion = true;

				query.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDLRecordVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordSetId);

				if (bindRecordSetVersion) {
					qPos.add(recordSetVersion);
				}

				if (!pagination) {
					list = (List<DDLRecordVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDLRecordVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ddl record version in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record version
	 * @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion findByR_R_First(long recordSetId,
		String recordSetVersion,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = fetchByR_R_First(recordSetId,
				recordSetVersion, orderByComparator);

		if (ddlRecordVersion != null) {
			return ddlRecordVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordSetId=");
		msg.append(recordSetId);

		msg.append(", recordSetVersion=");
		msg.append(recordSetVersion);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordVersionException(msg.toString());
	}

	/**
	 * Returns the first ddl record version in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion fetchByR_R_First(long recordSetId,
		String recordSetVersion,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		List<DDLRecordVersion> list = findByR_R(recordSetId, recordSetVersion,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddl record version in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record version
	 * @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion findByR_R_Last(long recordSetId,
		String recordSetVersion,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = fetchByR_R_Last(recordSetId,
				recordSetVersion, orderByComparator);

		if (ddlRecordVersion != null) {
			return ddlRecordVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordSetId=");
		msg.append(recordSetId);

		msg.append(", recordSetVersion=");
		msg.append(recordSetVersion);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordVersionException(msg.toString());
	}

	/**
	 * Returns the last ddl record version in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion fetchByR_R_Last(long recordSetId,
		String recordSetVersion,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		int count = countByR_R(recordSetId, recordSetVersion);

		if (count == 0) {
			return null;
		}

		List<DDLRecordVersion> list = findByR_R(recordSetId, recordSetVersion,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddl record versions before and after the current ddl record version in the ordered set where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordVersionId the primary key of the current ddl record version
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record version
	 * @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	 */
	@Override
	public DDLRecordVersion[] findByR_R_PrevAndNext(long recordVersionId,
		long recordSetId, String recordSetVersion,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = findByPrimaryKey(recordVersionId);

		Session session = null;

		try {
			session = openSession();

			DDLRecordVersion[] array = new DDLRecordVersionImpl[3];

			array[0] = getByR_R_PrevAndNext(session, ddlRecordVersion,
					recordSetId, recordSetVersion, orderByComparator, true);

			array[1] = ddlRecordVersion;

			array[2] = getByR_R_PrevAndNext(session, ddlRecordVersion,
					recordSetId, recordSetVersion, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDLRecordVersion getByR_R_PrevAndNext(Session session,
		DDLRecordVersion ddlRecordVersion, long recordSetId,
		String recordSetVersion,
		OrderByComparator<DDLRecordVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DDLRECORDVERSION_WHERE);

		query.append(_FINDER_COLUMN_R_R_RECORDSETID_2);

		boolean bindRecordSetVersion = false;

		if (recordSetVersion == null) {
			query.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_1);
		}
		else if (recordSetVersion.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_3);
		}
		else {
			bindRecordSetVersion = true;

			query.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
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

			String[] orderByFields = orderByComparator.getOrderByFields();

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
		}
		else {
			query.append(DDLRecordVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(recordSetId);

		if (bindRecordSetVersion) {
			qPos.add(recordSetVersion);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddlRecordVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLRecordVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddl record versions where recordSetId = &#63; and recordSetVersion = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 */
	@Override
	public void removeByR_R(long recordSetId, String recordSetVersion) {
		for (DDLRecordVersion ddlRecordVersion : findByR_R(recordSetId,
				recordSetVersion, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddlRecordVersion);
		}
	}

	/**
	 * Returns the number of ddl record versions where recordSetId = &#63; and recordSetVersion = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @return the number of matching ddl record versions
	 */
	@Override
	public int countByR_R(long recordSetId, String recordSetVersion) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_R_R;

		Object[] finderArgs = new Object[] { recordSetId, recordSetVersion };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDLRECORDVERSION_WHERE);

			query.append(_FINDER_COLUMN_R_R_RECORDSETID_2);

			boolean bindRecordSetVersion = false;

			if (recordSetVersion == null) {
				query.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_1);
			}
			else if (recordSetVersion.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_3);
			}
			else {
				bindRecordSetVersion = true;

				query.append(_FINDER_COLUMN_R_R_RECORDSETVERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordSetId);

				if (bindRecordSetVersion) {
					qPos.add(recordSetVersion);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_R_R_RECORDSETID_2 = "ddlRecordVersion.recordSetId = ? AND ";
	private static final String _FINDER_COLUMN_R_R_RECORDSETVERSION_1 = "ddlRecordVersion.recordSetVersion IS NULL";
	private static final String _FINDER_COLUMN_R_R_RECORDSETVERSION_2 = "ddlRecordVersion.recordSetVersion = ?";
	private static final String _FINDER_COLUMN_R_R_RECORDSETVERSION_3 = "(ddlRecordVersion.recordSetVersion IS NULL OR ddlRecordVersion.recordSetVersion = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_R_V = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED,
			DDLRecordVersionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByR_V",
			new String[] { Long.class.getName(), String.class.getName() },
			DDLRecordVersionModelImpl.RECORDID_COLUMN_BITMASK |
			DDLRecordVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_V = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_V",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the ddl record version where recordId = &#63; and version = &#63; or throws a {@link NoSuchRecordVersionException} if it could not be found.
	 *
	 * @param recordId the record ID
	 * @param version the version
	 * @return the matching ddl record version
	 * @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion findByR_V(long recordId, String version)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = fetchByR_V(recordId, version);

		if (ddlRecordVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("recordId=");
			msg.append(recordId);

			msg.append(", version=");
			msg.append(version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchRecordVersionException(msg.toString());
		}

		return ddlRecordVersion;
	}

	/**
	 * Returns the ddl record version where recordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param recordId the record ID
	 * @param version the version
	 * @return the matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion fetchByR_V(long recordId, String version) {
		return fetchByR_V(recordId, version, true);
	}

	/**
	 * Returns the ddl record version where recordId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param recordId the record ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion fetchByR_V(long recordId, String version,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { recordId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_R_V,
					finderArgs, this);
		}

		if (result instanceof DDLRecordVersion) {
			DDLRecordVersion ddlRecordVersion = (DDLRecordVersion)result;

			if ((recordId != ddlRecordVersion.getRecordId()) ||
					!Objects.equals(version, ddlRecordVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDLRECORDVERSION_WHERE);

			query.append(_FINDER_COLUMN_R_V_RECORDID_2);

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_R_V_VERSION_1);
			}
			else if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_R_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_R_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordId);

				if (bindVersion) {
					qPos.add(version);
				}

				List<DDLRecordVersion> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_R_V, finderArgs,
						list);
				}
				else {
					DDLRecordVersion ddlRecordVersion = list.get(0);

					result = ddlRecordVersion;

					cacheResult(ddlRecordVersion);

					if ((ddlRecordVersion.getRecordId() != recordId) ||
							(ddlRecordVersion.getVersion() == null) ||
							!ddlRecordVersion.getVersion().equals(version)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_R_V,
							finderArgs, ddlRecordVersion);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_R_V, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (DDLRecordVersion)result;
		}
	}

	/**
	 * Removes the ddl record version where recordId = &#63; and version = &#63; from the database.
	 *
	 * @param recordId the record ID
	 * @param version the version
	 * @return the ddl record version that was removed
	 */
	@Override
	public DDLRecordVersion removeByR_V(long recordId, String version)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = findByR_V(recordId, version);

		return remove(ddlRecordVersion);
	}

	/**
	 * Returns the number of ddl record versions where recordId = &#63; and version = &#63;.
	 *
	 * @param recordId the record ID
	 * @param version the version
	 * @return the number of matching ddl record versions
	 */
	@Override
	public int countByR_V(long recordId, String version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_R_V;

		Object[] finderArgs = new Object[] { recordId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDLRECORDVERSION_WHERE);

			query.append(_FINDER_COLUMN_R_V_RECORDID_2);

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_R_V_VERSION_1);
			}
			else if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_R_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_R_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordId);

				if (bindVersion) {
					qPos.add(version);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_R_V_RECORDID_2 = "ddlRecordVersion.recordId = ? AND ";
	private static final String _FINDER_COLUMN_R_V_VERSION_1 = "ddlRecordVersion.version IS NULL";
	private static final String _FINDER_COLUMN_R_V_VERSION_2 = "ddlRecordVersion.version = ?";
	private static final String _FINDER_COLUMN_R_V_VERSION_3 = "(ddlRecordVersion.version IS NULL OR ddlRecordVersion.version = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_R_S = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED,
			DDLRecordVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByR_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED,
			DDLRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			DDLRecordVersionModelImpl.RECORDID_COLUMN_BITMASK |
			DDLRecordVersionModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_S = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the ddl record versions where recordId = &#63; and status = &#63;.
	 *
	 * @param recordId the record ID
	 * @param status the status
	 * @return the matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByR_S(long recordId, int status) {
		return findByR_S(recordId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl record versions where recordId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordId the record ID
	 * @param status the status
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @return the range of matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByR_S(long recordId, int status,
		int start, int end) {
		return findByR_S(recordId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl record versions where recordId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordId the record ID
	 * @param status the status
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByR_S(long recordId, int status,
		int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		return findByR_S(recordId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl record versions where recordId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param recordId the record ID
	 * @param status the status
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByR_S(long recordId, int status,
		int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S;
			finderArgs = new Object[] { recordId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_R_S;
			finderArgs = new Object[] {
					recordId, status,
					
					start, end, orderByComparator
				};
		}

		List<DDLRecordVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DDLRecordVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecordVersion ddlRecordVersion : list) {
					if ((recordId != ddlRecordVersion.getRecordId()) ||
							(status != ddlRecordVersion.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DDLRECORDVERSION_WHERE);

			query.append(_FINDER_COLUMN_R_S_RECORDID_2);

			query.append(_FINDER_COLUMN_R_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDLRecordVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordId);

				qPos.add(status);

				if (!pagination) {
					list = (List<DDLRecordVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDLRecordVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ddl record version in the ordered set where recordId = &#63; and status = &#63;.
	 *
	 * @param recordId the record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record version
	 * @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion findByR_S_First(long recordId, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = fetchByR_S_First(recordId, status,
				orderByComparator);

		if (ddlRecordVersion != null) {
			return ddlRecordVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordId=");
		msg.append(recordId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordVersionException(msg.toString());
	}

	/**
	 * Returns the first ddl record version in the ordered set where recordId = &#63; and status = &#63;.
	 *
	 * @param recordId the record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion fetchByR_S_First(long recordId, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		List<DDLRecordVersion> list = findByR_S(recordId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddl record version in the ordered set where recordId = &#63; and status = &#63;.
	 *
	 * @param recordId the record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record version
	 * @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion findByR_S_Last(long recordId, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = fetchByR_S_Last(recordId, status,
				orderByComparator);

		if (ddlRecordVersion != null) {
			return ddlRecordVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordId=");
		msg.append(recordId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordVersionException(msg.toString());
	}

	/**
	 * Returns the last ddl record version in the ordered set where recordId = &#63; and status = &#63;.
	 *
	 * @param recordId the record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion fetchByR_S_Last(long recordId, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		int count = countByR_S(recordId, status);

		if (count == 0) {
			return null;
		}

		List<DDLRecordVersion> list = findByR_S(recordId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddl record versions before and after the current ddl record version in the ordered set where recordId = &#63; and status = &#63;.
	 *
	 * @param recordVersionId the primary key of the current ddl record version
	 * @param recordId the record ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record version
	 * @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	 */
	@Override
	public DDLRecordVersion[] findByR_S_PrevAndNext(long recordVersionId,
		long recordId, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = findByPrimaryKey(recordVersionId);

		Session session = null;

		try {
			session = openSession();

			DDLRecordVersion[] array = new DDLRecordVersionImpl[3];

			array[0] = getByR_S_PrevAndNext(session, ddlRecordVersion,
					recordId, status, orderByComparator, true);

			array[1] = ddlRecordVersion;

			array[2] = getByR_S_PrevAndNext(session, ddlRecordVersion,
					recordId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDLRecordVersion getByR_S_PrevAndNext(Session session,
		DDLRecordVersion ddlRecordVersion, long recordId, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DDLRECORDVERSION_WHERE);

		query.append(_FINDER_COLUMN_R_S_RECORDID_2);

		query.append(_FINDER_COLUMN_R_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
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

			String[] orderByFields = orderByComparator.getOrderByFields();

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
		}
		else {
			query.append(DDLRecordVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(recordId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddlRecordVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLRecordVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddl record versions where recordId = &#63; and status = &#63; from the database.
	 *
	 * @param recordId the record ID
	 * @param status the status
	 */
	@Override
	public void removeByR_S(long recordId, int status) {
		for (DDLRecordVersion ddlRecordVersion : findByR_S(recordId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddlRecordVersion);
		}
	}

	/**
	 * Returns the number of ddl record versions where recordId = &#63; and status = &#63;.
	 *
	 * @param recordId the record ID
	 * @param status the status
	 * @return the number of matching ddl record versions
	 */
	@Override
	public int countByR_S(long recordId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_R_S;

		Object[] finderArgs = new Object[] { recordId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDLRECORDVERSION_WHERE);

			query.append(_FINDER_COLUMN_R_S_RECORDID_2);

			query.append(_FINDER_COLUMN_R_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_R_S_RECORDID_2 = "ddlRecordVersion.recordId = ? AND ";
	private static final String _FINDER_COLUMN_R_S_STATUS_2 = "ddlRecordVersion.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_R_R_S = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED,
			DDLRecordVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU_R_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_R_R_S =
		new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED,
			DDLRecordVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_R_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			DDLRecordVersionModelImpl.USERID_COLUMN_BITMASK |
			DDLRecordVersionModelImpl.RECORDSETID_COLUMN_BITMASK |
			DDLRecordVersionModelImpl.RECORDSETVERSION_COLUMN_BITMASK |
			DDLRecordVersionModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_R_R_S = new FinderPath(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_R_R_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			});

	/**
	 * Returns all the ddl record versions where userId = &#63; and recordSetId = &#63; and recordSetVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param status the status
	 * @return the matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByU_R_R_S(long userId, long recordSetId,
		String recordSetVersion, int status) {
		return findByU_R_R_S(userId, recordSetId, recordSetVersion, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl record versions where userId = &#63; and recordSetId = &#63; and recordSetVersion = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param status the status
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @return the range of matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByU_R_R_S(long userId, long recordSetId,
		String recordSetVersion, int status, int start, int end) {
		return findByU_R_R_S(userId, recordSetId, recordSetVersion, status,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl record versions where userId = &#63; and recordSetId = &#63; and recordSetVersion = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param status the status
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByU_R_R_S(long userId, long recordSetId,
		String recordSetVersion, int status, int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		return findByU_R_R_S(userId, recordSetId, recordSetVersion, status,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl record versions where userId = &#63; and recordSetId = &#63; and recordSetVersion = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param status the status
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findByU_R_R_S(long userId, long recordSetId,
		String recordSetVersion, int status, int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_R_R_S;
			finderArgs = new Object[] {
					userId, recordSetId, recordSetVersion, status
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_R_R_S;
			finderArgs = new Object[] {
					userId, recordSetId, recordSetVersion, status,
					
					start, end, orderByComparator
				};
		}

		List<DDLRecordVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DDLRecordVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecordVersion ddlRecordVersion : list) {
					if ((userId != ddlRecordVersion.getUserId()) ||
							(recordSetId != ddlRecordVersion.getRecordSetId()) ||
							!Objects.equals(recordSetVersion,
								ddlRecordVersion.getRecordSetVersion()) ||
							(status != ddlRecordVersion.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_DDLRECORDVERSION_WHERE);

			query.append(_FINDER_COLUMN_U_R_R_S_USERID_2);

			query.append(_FINDER_COLUMN_U_R_R_S_RECORDSETID_2);

			boolean bindRecordSetVersion = false;

			if (recordSetVersion == null) {
				query.append(_FINDER_COLUMN_U_R_R_S_RECORDSETVERSION_1);
			}
			else if (recordSetVersion.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_U_R_R_S_RECORDSETVERSION_3);
			}
			else {
				bindRecordSetVersion = true;

				query.append(_FINDER_COLUMN_U_R_R_S_RECORDSETVERSION_2);
			}

			query.append(_FINDER_COLUMN_U_R_R_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDLRecordVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(recordSetId);

				if (bindRecordSetVersion) {
					qPos.add(recordSetVersion);
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<DDLRecordVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDLRecordVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ddl record version in the ordered set where userId = &#63; and recordSetId = &#63; and recordSetVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record version
	 * @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion findByU_R_R_S_First(long userId, long recordSetId,
		String recordSetVersion, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = fetchByU_R_R_S_First(userId,
				recordSetId, recordSetVersion, status, orderByComparator);

		if (ddlRecordVersion != null) {
			return ddlRecordVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", recordSetId=");
		msg.append(recordSetId);

		msg.append(", recordSetVersion=");
		msg.append(recordSetVersion);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordVersionException(msg.toString());
	}

	/**
	 * Returns the first ddl record version in the ordered set where userId = &#63; and recordSetId = &#63; and recordSetVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion fetchByU_R_R_S_First(long userId, long recordSetId,
		String recordSetVersion, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		List<DDLRecordVersion> list = findByU_R_R_S(userId, recordSetId,
				recordSetVersion, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddl record version in the ordered set where userId = &#63; and recordSetId = &#63; and recordSetVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record version
	 * @throws NoSuchRecordVersionException if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion findByU_R_R_S_Last(long userId, long recordSetId,
		String recordSetVersion, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = fetchByU_R_R_S_Last(userId,
				recordSetId, recordSetVersion, status, orderByComparator);

		if (ddlRecordVersion != null) {
			return ddlRecordVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", recordSetId=");
		msg.append(recordSetId);

		msg.append(", recordSetVersion=");
		msg.append(recordSetVersion);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecordVersionException(msg.toString());
	}

	/**
	 * Returns the last ddl record version in the ordered set where userId = &#63; and recordSetId = &#63; and recordSetVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record version, or <code>null</code> if a matching ddl record version could not be found
	 */
	@Override
	public DDLRecordVersion fetchByU_R_R_S_Last(long userId, long recordSetId,
		String recordSetVersion, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		int count = countByU_R_R_S(userId, recordSetId, recordSetVersion, status);

		if (count == 0) {
			return null;
		}

		List<DDLRecordVersion> list = findByU_R_R_S(userId, recordSetId,
				recordSetVersion, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddl record versions before and after the current ddl record version in the ordered set where userId = &#63; and recordSetId = &#63; and recordSetVersion = &#63; and status = &#63;.
	 *
	 * @param recordVersionId the primary key of the current ddl record version
	 * @param userId the user ID
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record version
	 * @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	 */
	@Override
	public DDLRecordVersion[] findByU_R_R_S_PrevAndNext(long recordVersionId,
		long userId, long recordSetId, String recordSetVersion, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = findByPrimaryKey(recordVersionId);

		Session session = null;

		try {
			session = openSession();

			DDLRecordVersion[] array = new DDLRecordVersionImpl[3];

			array[0] = getByU_R_R_S_PrevAndNext(session, ddlRecordVersion,
					userId, recordSetId, recordSetVersion, status,
					orderByComparator, true);

			array[1] = ddlRecordVersion;

			array[2] = getByU_R_R_S_PrevAndNext(session, ddlRecordVersion,
					userId, recordSetId, recordSetVersion, status,
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

	protected DDLRecordVersion getByU_R_R_S_PrevAndNext(Session session,
		DDLRecordVersion ddlRecordVersion, long userId, long recordSetId,
		String recordSetVersion, int status,
		OrderByComparator<DDLRecordVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_DDLRECORDVERSION_WHERE);

		query.append(_FINDER_COLUMN_U_R_R_S_USERID_2);

		query.append(_FINDER_COLUMN_U_R_R_S_RECORDSETID_2);

		boolean bindRecordSetVersion = false;

		if (recordSetVersion == null) {
			query.append(_FINDER_COLUMN_U_R_R_S_RECORDSETVERSION_1);
		}
		else if (recordSetVersion.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_U_R_R_S_RECORDSETVERSION_3);
		}
		else {
			bindRecordSetVersion = true;

			query.append(_FINDER_COLUMN_U_R_R_S_RECORDSETVERSION_2);
		}

		query.append(_FINDER_COLUMN_U_R_R_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
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

			String[] orderByFields = orderByComparator.getOrderByFields();

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
		}
		else {
			query.append(DDLRecordVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(recordSetId);

		if (bindRecordSetVersion) {
			qPos.add(recordSetVersion);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddlRecordVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDLRecordVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddl record versions where userId = &#63; and recordSetId = &#63; and recordSetVersion = &#63; and status = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param status the status
	 */
	@Override
	public void removeByU_R_R_S(long userId, long recordSetId,
		String recordSetVersion, int status) {
		for (DDLRecordVersion ddlRecordVersion : findByU_R_R_S(userId,
				recordSetId, recordSetVersion, status, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(ddlRecordVersion);
		}
	}

	/**
	 * Returns the number of ddl record versions where userId = &#63; and recordSetId = &#63; and recordSetVersion = &#63; and status = &#63;.
	 *
	 * @param userId the user ID
	 * @param recordSetId the record set ID
	 * @param recordSetVersion the record set version
	 * @param status the status
	 * @return the number of matching ddl record versions
	 */
	@Override
	public int countByU_R_R_S(long userId, long recordSetId,
		String recordSetVersion, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_R_R_S;

		Object[] finderArgs = new Object[] {
				userId, recordSetId, recordSetVersion, status
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DDLRECORDVERSION_WHERE);

			query.append(_FINDER_COLUMN_U_R_R_S_USERID_2);

			query.append(_FINDER_COLUMN_U_R_R_S_RECORDSETID_2);

			boolean bindRecordSetVersion = false;

			if (recordSetVersion == null) {
				query.append(_FINDER_COLUMN_U_R_R_S_RECORDSETVERSION_1);
			}
			else if (recordSetVersion.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_U_R_R_S_RECORDSETVERSION_3);
			}
			else {
				bindRecordSetVersion = true;

				query.append(_FINDER_COLUMN_U_R_R_S_RECORDSETVERSION_2);
			}

			query.append(_FINDER_COLUMN_U_R_R_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(recordSetId);

				if (bindRecordSetVersion) {
					qPos.add(recordSetVersion);
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_U_R_R_S_USERID_2 = "ddlRecordVersion.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_R_R_S_RECORDSETID_2 = "ddlRecordVersion.recordSetId = ? AND ";
	private static final String _FINDER_COLUMN_U_R_R_S_RECORDSETVERSION_1 = "ddlRecordVersion.recordSetVersion IS NULL AND ";
	private static final String _FINDER_COLUMN_U_R_R_S_RECORDSETVERSION_2 = "ddlRecordVersion.recordSetVersion = ? AND ";
	private static final String _FINDER_COLUMN_U_R_R_S_RECORDSETVERSION_3 = "(ddlRecordVersion.recordSetVersion IS NULL OR ddlRecordVersion.recordSetVersion = '') AND ";
	private static final String _FINDER_COLUMN_U_R_R_S_STATUS_2 = "ddlRecordVersion.status = ?";

	public DDLRecordVersionPersistenceImpl() {
		setModelClass(DDLRecordVersion.class);
	}

	/**
	 * Caches the ddl record version in the entity cache if it is enabled.
	 *
	 * @param ddlRecordVersion the ddl record version
	 */
	@Override
	public void cacheResult(DDLRecordVersion ddlRecordVersion) {
		entityCache.putResult(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionImpl.class, ddlRecordVersion.getPrimaryKey(),
			ddlRecordVersion);

		finderCache.putResult(FINDER_PATH_FETCH_BY_R_V,
			new Object[] {
				ddlRecordVersion.getRecordId(), ddlRecordVersion.getVersion()
			}, ddlRecordVersion);

		ddlRecordVersion.resetOriginalValues();
	}

	/**
	 * Caches the ddl record versions in the entity cache if it is enabled.
	 *
	 * @param ddlRecordVersions the ddl record versions
	 */
	@Override
	public void cacheResult(List<DDLRecordVersion> ddlRecordVersions) {
		for (DDLRecordVersion ddlRecordVersion : ddlRecordVersions) {
			if (entityCache.getResult(
						DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
						DDLRecordVersionImpl.class,
						ddlRecordVersion.getPrimaryKey()) == null) {
				cacheResult(ddlRecordVersion);
			}
			else {
				ddlRecordVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ddl record versions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDLRecordVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ddl record version.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDLRecordVersion ddlRecordVersion) {
		entityCache.removeResult(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionImpl.class, ddlRecordVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DDLRecordVersionModelImpl)ddlRecordVersion,
			true);
	}

	@Override
	public void clearCache(List<DDLRecordVersion> ddlRecordVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDLRecordVersion ddlRecordVersion : ddlRecordVersions) {
			entityCache.removeResult(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDLRecordVersionImpl.class, ddlRecordVersion.getPrimaryKey());

			clearUniqueFindersCache((DDLRecordVersionModelImpl)ddlRecordVersion,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		DDLRecordVersionModelImpl ddlRecordVersionModelImpl) {
		Object[] args = new Object[] {
				ddlRecordVersionModelImpl.getRecordId(),
				ddlRecordVersionModelImpl.getVersion()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_R_V, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_R_V, args,
			ddlRecordVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DDLRecordVersionModelImpl ddlRecordVersionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					ddlRecordVersionModelImpl.getRecordId(),
					ddlRecordVersionModelImpl.getVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_R_V, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_R_V, args);
		}

		if ((ddlRecordVersionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_R_V.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					ddlRecordVersionModelImpl.getOriginalRecordId(),
					ddlRecordVersionModelImpl.getOriginalVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_R_V, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_R_V, args);
		}
	}

	/**
	 * Creates a new ddl record version with the primary key. Does not add the ddl record version to the database.
	 *
	 * @param recordVersionId the primary key for the new ddl record version
	 * @return the new ddl record version
	 */
	@Override
	public DDLRecordVersion create(long recordVersionId) {
		DDLRecordVersion ddlRecordVersion = new DDLRecordVersionImpl();

		ddlRecordVersion.setNew(true);
		ddlRecordVersion.setPrimaryKey(recordVersionId);

		ddlRecordVersion.setCompanyId(companyProvider.getCompanyId());

		return ddlRecordVersion;
	}

	/**
	 * Removes the ddl record version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordVersionId the primary key of the ddl record version
	 * @return the ddl record version that was removed
	 * @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	 */
	@Override
	public DDLRecordVersion remove(long recordVersionId)
		throws NoSuchRecordVersionException {
		return remove((Serializable)recordVersionId);
	}

	/**
	 * Removes the ddl record version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ddl record version
	 * @return the ddl record version that was removed
	 * @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	 */
	@Override
	public DDLRecordVersion remove(Serializable primaryKey)
		throws NoSuchRecordVersionException {
		Session session = null;

		try {
			session = openSession();

			DDLRecordVersion ddlRecordVersion = (DDLRecordVersion)session.get(DDLRecordVersionImpl.class,
					primaryKey);

			if (ddlRecordVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRecordVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddlRecordVersion);
		}
		catch (NoSuchRecordVersionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected DDLRecordVersion removeImpl(DDLRecordVersion ddlRecordVersion) {
		ddlRecordVersion = toUnwrappedModel(ddlRecordVersion);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddlRecordVersion)) {
				ddlRecordVersion = (DDLRecordVersion)session.get(DDLRecordVersionImpl.class,
						ddlRecordVersion.getPrimaryKeyObj());
			}

			if (ddlRecordVersion != null) {
				session.delete(ddlRecordVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddlRecordVersion != null) {
			clearCache(ddlRecordVersion);
		}

		return ddlRecordVersion;
	}

	@Override
	public DDLRecordVersion updateImpl(DDLRecordVersion ddlRecordVersion) {
		ddlRecordVersion = toUnwrappedModel(ddlRecordVersion);

		boolean isNew = ddlRecordVersion.isNew();

		DDLRecordVersionModelImpl ddlRecordVersionModelImpl = (DDLRecordVersionModelImpl)ddlRecordVersion;

		Session session = null;

		try {
			session = openSession();

			if (ddlRecordVersion.isNew()) {
				session.save(ddlRecordVersion);

				ddlRecordVersion.setNew(false);
			}
			else {
				ddlRecordVersion = (DDLRecordVersion)session.merge(ddlRecordVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!DDLRecordVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { ddlRecordVersionModelImpl.getRecordId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_RECORDID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDID,
				args);

			args = new Object[] {
					ddlRecordVersionModelImpl.getRecordSetId(),
					ddlRecordVersionModelImpl.getRecordSetVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_R_R, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_R,
				args);

			args = new Object[] {
					ddlRecordVersionModelImpl.getRecordId(),
					ddlRecordVersionModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_R_S, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S,
				args);

			args = new Object[] {
					ddlRecordVersionModelImpl.getUserId(),
					ddlRecordVersionModelImpl.getRecordSetId(),
					ddlRecordVersionModelImpl.getRecordSetVersion(),
					ddlRecordVersionModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_U_R_R_S, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_R_R_S,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((ddlRecordVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddlRecordVersionModelImpl.getOriginalRecordId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RECORDID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDID,
					args);

				args = new Object[] { ddlRecordVersionModelImpl.getRecordId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RECORDID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RECORDID,
					args);
			}

			if ((ddlRecordVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddlRecordVersionModelImpl.getOriginalRecordSetId(),
						ddlRecordVersionModelImpl.getOriginalRecordSetVersion()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_R,
					args);

				args = new Object[] {
						ddlRecordVersionModelImpl.getRecordSetId(),
						ddlRecordVersionModelImpl.getRecordSetVersion()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_R,
					args);
			}

			if ((ddlRecordVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddlRecordVersionModelImpl.getOriginalRecordId(),
						ddlRecordVersionModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S,
					args);

				args = new Object[] {
						ddlRecordVersionModelImpl.getRecordId(),
						ddlRecordVersionModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_S,
					args);
			}

			if ((ddlRecordVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_R_R_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddlRecordVersionModelImpl.getOriginalUserId(),
						ddlRecordVersionModelImpl.getOriginalRecordSetId(),
						ddlRecordVersionModelImpl.getOriginalRecordSetVersion(),
						ddlRecordVersionModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_R_R_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_R_R_S,
					args);

				args = new Object[] {
						ddlRecordVersionModelImpl.getUserId(),
						ddlRecordVersionModelImpl.getRecordSetId(),
						ddlRecordVersionModelImpl.getRecordSetVersion(),
						ddlRecordVersionModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_R_R_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_R_R_S,
					args);
			}
		}

		entityCache.putResult(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDLRecordVersionImpl.class, ddlRecordVersion.getPrimaryKey(),
			ddlRecordVersion, false);

		clearUniqueFindersCache(ddlRecordVersionModelImpl, false);
		cacheUniqueFindersCache(ddlRecordVersionModelImpl);

		ddlRecordVersion.resetOriginalValues();

		return ddlRecordVersion;
	}

	protected DDLRecordVersion toUnwrappedModel(
		DDLRecordVersion ddlRecordVersion) {
		if (ddlRecordVersion instanceof DDLRecordVersionImpl) {
			return ddlRecordVersion;
		}

		DDLRecordVersionImpl ddlRecordVersionImpl = new DDLRecordVersionImpl();

		ddlRecordVersionImpl.setNew(ddlRecordVersion.isNew());
		ddlRecordVersionImpl.setPrimaryKey(ddlRecordVersion.getPrimaryKey());

		ddlRecordVersionImpl.setRecordVersionId(ddlRecordVersion.getRecordVersionId());
		ddlRecordVersionImpl.setGroupId(ddlRecordVersion.getGroupId());
		ddlRecordVersionImpl.setCompanyId(ddlRecordVersion.getCompanyId());
		ddlRecordVersionImpl.setUserId(ddlRecordVersion.getUserId());
		ddlRecordVersionImpl.setUserName(ddlRecordVersion.getUserName());
		ddlRecordVersionImpl.setCreateDate(ddlRecordVersion.getCreateDate());
		ddlRecordVersionImpl.setDDMStorageId(ddlRecordVersion.getDDMStorageId());
		ddlRecordVersionImpl.setRecordSetId(ddlRecordVersion.getRecordSetId());
		ddlRecordVersionImpl.setRecordSetVersion(ddlRecordVersion.getRecordSetVersion());
		ddlRecordVersionImpl.setRecordId(ddlRecordVersion.getRecordId());
		ddlRecordVersionImpl.setVersion(ddlRecordVersion.getVersion());
		ddlRecordVersionImpl.setDisplayIndex(ddlRecordVersion.getDisplayIndex());
		ddlRecordVersionImpl.setStatus(ddlRecordVersion.getStatus());
		ddlRecordVersionImpl.setStatusByUserId(ddlRecordVersion.getStatusByUserId());
		ddlRecordVersionImpl.setStatusByUserName(ddlRecordVersion.getStatusByUserName());
		ddlRecordVersionImpl.setStatusDate(ddlRecordVersion.getStatusDate());

		return ddlRecordVersionImpl;
	}

	/**
	 * Returns the ddl record version with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddl record version
	 * @return the ddl record version
	 * @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	 */
	@Override
	public DDLRecordVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRecordVersionException {
		DDLRecordVersion ddlRecordVersion = fetchByPrimaryKey(primaryKey);

		if (ddlRecordVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRecordVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddlRecordVersion;
	}

	/**
	 * Returns the ddl record version with the primary key or throws a {@link NoSuchRecordVersionException} if it could not be found.
	 *
	 * @param recordVersionId the primary key of the ddl record version
	 * @return the ddl record version
	 * @throws NoSuchRecordVersionException if a ddl record version with the primary key could not be found
	 */
	@Override
	public DDLRecordVersion findByPrimaryKey(long recordVersionId)
		throws NoSuchRecordVersionException {
		return findByPrimaryKey((Serializable)recordVersionId);
	}

	/**
	 * Returns the ddl record version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddl record version
	 * @return the ddl record version, or <code>null</code> if a ddl record version with the primary key could not be found
	 */
	@Override
	public DDLRecordVersion fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDLRecordVersionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		DDLRecordVersion ddlRecordVersion = (DDLRecordVersion)serializable;

		if (ddlRecordVersion == null) {
			Session session = null;

			try {
				session = openSession();

				ddlRecordVersion = (DDLRecordVersion)session.get(DDLRecordVersionImpl.class,
						primaryKey);

				if (ddlRecordVersion != null) {
					cacheResult(ddlRecordVersion);
				}
				else {
					entityCache.putResult(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
						DDLRecordVersionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
					DDLRecordVersionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ddlRecordVersion;
	}

	/**
	 * Returns the ddl record version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recordVersionId the primary key of the ddl record version
	 * @return the ddl record version, or <code>null</code> if a ddl record version with the primary key could not be found
	 */
	@Override
	public DDLRecordVersion fetchByPrimaryKey(long recordVersionId) {
		return fetchByPrimaryKey((Serializable)recordVersionId);
	}

	@Override
	public Map<Serializable, DDLRecordVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDLRecordVersion> map = new HashMap<Serializable, DDLRecordVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDLRecordVersion ddlRecordVersion = fetchByPrimaryKey(primaryKey);

			if (ddlRecordVersion != null) {
				map.put(primaryKey, ddlRecordVersion);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
					DDLRecordVersionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (DDLRecordVersion)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DDLRECORDVERSION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (DDLRecordVersion ddlRecordVersion : (List<DDLRecordVersion>)q.list()) {
				map.put(ddlRecordVersion.getPrimaryKeyObj(), ddlRecordVersion);

				cacheResult(ddlRecordVersion);

				uncachedPrimaryKeys.remove(ddlRecordVersion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DDLRecordVersionModelImpl.ENTITY_CACHE_ENABLED,
					DDLRecordVersionImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the ddl record versions.
	 *
	 * @return the ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl record versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @return the range of ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl record versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findAll(int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl record versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDLRecordVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl record versions
	 * @param end the upper bound of the range of ddl record versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ddl record versions
	 */
	@Override
	public List<DDLRecordVersion> findAll(int start, int end,
		OrderByComparator<DDLRecordVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<DDLRecordVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DDLRecordVersion>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DDLRECORDVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDLRECORDVERSION;

				if (pagination) {
					sql = sql.concat(DDLRecordVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDLRecordVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDLRecordVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the ddl record versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDLRecordVersion ddlRecordVersion : findAll()) {
			remove(ddlRecordVersion);
		}
	}

	/**
	 * Returns the number of ddl record versions.
	 *
	 * @return the number of ddl record versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDLRECORDVERSION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DDLRecordVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ddl record version persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DDLRecordVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_DDLRECORDVERSION = "SELECT ddlRecordVersion FROM DDLRecordVersion ddlRecordVersion";
	private static final String _SQL_SELECT_DDLRECORDVERSION_WHERE_PKS_IN = "SELECT ddlRecordVersion FROM DDLRecordVersion ddlRecordVersion WHERE recordVersionId IN (";
	private static final String _SQL_SELECT_DDLRECORDVERSION_WHERE = "SELECT ddlRecordVersion FROM DDLRecordVersion ddlRecordVersion WHERE ";
	private static final String _SQL_COUNT_DDLRECORDVERSION = "SELECT COUNT(ddlRecordVersion) FROM DDLRecordVersion ddlRecordVersion";
	private static final String _SQL_COUNT_DDLRECORDVERSION_WHERE = "SELECT COUNT(ddlRecordVersion) FROM DDLRecordVersion ddlRecordVersion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddlRecordVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDLRecordVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDLRecordVersion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DDLRecordVersionPersistenceImpl.class);
}