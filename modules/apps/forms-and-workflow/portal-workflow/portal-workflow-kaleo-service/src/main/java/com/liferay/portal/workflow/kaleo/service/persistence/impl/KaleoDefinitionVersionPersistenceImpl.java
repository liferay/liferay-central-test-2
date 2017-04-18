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

package com.liferay.portal.workflow.kaleo.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionVersionException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoDefinitionVersionImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoDefinitionVersionModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoDefinitionVersionPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the kaleo definition version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoDefinitionVersionPersistence
 * @see com.liferay.portal.workflow.kaleo.service.persistence.KaleoDefinitionVersionUtil
 * @generated
 */
@ProviderType
public class KaleoDefinitionVersionPersistenceImpl extends BasePersistenceImpl<KaleoDefinitionVersion>
	implements KaleoDefinitionVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link KaleoDefinitionVersionUtil} to access the kaleo definition version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = KaleoDefinitionVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
			KaleoDefinitionVersionModelImpl.FINDER_CACHE_ENABLED,
			KaleoDefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
			KaleoDefinitionVersionModelImpl.FINDER_CACHE_ENABLED,
			KaleoDefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
			KaleoDefinitionVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
			KaleoDefinitionVersionModelImpl.FINDER_CACHE_ENABLED,
			KaleoDefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
			KaleoDefinitionVersionModelImpl.FINDER_CACHE_ENABLED,
			KaleoDefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			KaleoDefinitionVersionModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
			KaleoDefinitionVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the kaleo definition versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo definition versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDefinitionVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @return the range of matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDefinitionVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDefinitionVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<KaleoDefinitionVersion> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoDefinitionVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoDefinitionVersion kaleoDefinitionVersion : list) {
					if ((companyId != kaleoDefinitionVersion.getCompanyId())) {
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

			query.append(_SQL_SELECT_KALEODEFINITIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(KaleoDefinitionVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<KaleoDefinitionVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoDefinitionVersion>)QueryUtil.list(q,
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
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion findByCompanyId_First(long companyId,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException {
		KaleoDefinitionVersion kaleoDefinitionVersion = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (kaleoDefinitionVersion != null) {
			return kaleoDefinitionVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDefinitionVersionException(msg.toString());
	}

	/**
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByCompanyId_First(long companyId,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {
		List<KaleoDefinitionVersion> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion findByCompanyId_Last(long companyId,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException {
		KaleoDefinitionVersion kaleoDefinitionVersion = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (kaleoDefinitionVersion != null) {
			return kaleoDefinitionVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDefinitionVersionException(msg.toString());
	}

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByCompanyId_Last(long companyId,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<KaleoDefinitionVersion> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo definition versions before and after the current kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the current kaleo definition version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion[] findByCompanyId_PrevAndNext(
		long kaleoDefinitionVersionId, long companyId,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException {
		KaleoDefinitionVersion kaleoDefinitionVersion = findByPrimaryKey(kaleoDefinitionVersionId);

		Session session = null;

		try {
			session = openSession();

			KaleoDefinitionVersion[] array = new KaleoDefinitionVersionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					kaleoDefinitionVersion, companyId, orderByComparator, true);

			array[1] = kaleoDefinitionVersion;

			array[2] = getByCompanyId_PrevAndNext(session,
					kaleoDefinitionVersion, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoDefinitionVersion getByCompanyId_PrevAndNext(
		Session session, KaleoDefinitionVersion kaleoDefinitionVersion,
		long companyId,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_KALEODEFINITIONVERSION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(KaleoDefinitionVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(kaleoDefinitionVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<KaleoDefinitionVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo definition versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (KaleoDefinitionVersion kaleoDefinitionVersion : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(kaleoDefinitionVersion);
		}
	}

	/**
	 * Returns the number of kaleo definition versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo definition versions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KALEODEFINITIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "kaleoDefinitionVersion.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_N = new FinderPath(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
			KaleoDefinitionVersionModelImpl.FINDER_CACHE_ENABLED,
			KaleoDefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N = new FinderPath(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
			KaleoDefinitionVersionModelImpl.FINDER_CACHE_ENABLED,
			KaleoDefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_N",
			new String[] { Long.class.getName(), String.class.getName() },
			KaleoDefinitionVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			KaleoDefinitionVersionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
			KaleoDefinitionVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByC_N(long companyId, String name) {
		return findByC_N(companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDefinitionVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @return the range of matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByC_N(long companyId, String name,
		int start, int end) {
		return findByC_N(companyId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDefinitionVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByC_N(long companyId, String name,
		int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {
		return findByC_N(companyId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDefinitionVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByC_N(long companyId, String name,
		int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N;
			finderArgs = new Object[] { companyId, name };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_N;
			finderArgs = new Object[] {
					companyId, name,
					
					start, end, orderByComparator
				};
		}

		List<KaleoDefinitionVersion> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoDefinitionVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoDefinitionVersion kaleoDefinitionVersion : list) {
					if ((companyId != kaleoDefinitionVersion.getCompanyId()) ||
							!Objects.equals(name,
								kaleoDefinitionVersion.getName())) {
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

			query.append(_SQL_SELECT_KALEODEFINITIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(KaleoDefinitionVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				if (!pagination) {
					list = (List<KaleoDefinitionVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoDefinitionVersion>)QueryUtil.list(q,
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
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion findByC_N_First(long companyId, String name,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException {
		KaleoDefinitionVersion kaleoDefinitionVersion = fetchByC_N_First(companyId,
				name, orderByComparator);

		if (kaleoDefinitionVersion != null) {
			return kaleoDefinitionVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDefinitionVersionException(msg.toString());
	}

	/**
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByC_N_First(long companyId, String name,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {
		List<KaleoDefinitionVersion> list = findByC_N(companyId, name, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion findByC_N_Last(long companyId, String name,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException {
		KaleoDefinitionVersion kaleoDefinitionVersion = fetchByC_N_Last(companyId,
				name, orderByComparator);

		if (kaleoDefinitionVersion != null) {
			return kaleoDefinitionVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDefinitionVersionException(msg.toString());
	}

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByC_N_Last(long companyId, String name,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {
		int count = countByC_N(companyId, name);

		if (count == 0) {
			return null;
		}

		List<KaleoDefinitionVersion> list = findByC_N(companyId, name,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo definition versions before and after the current kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the current kaleo definition version
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion[] findByC_N_PrevAndNext(
		long kaleoDefinitionVersionId, long companyId, String name,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException {
		KaleoDefinitionVersion kaleoDefinitionVersion = findByPrimaryKey(kaleoDefinitionVersionId);

		Session session = null;

		try {
			session = openSession();

			KaleoDefinitionVersion[] array = new KaleoDefinitionVersionImpl[3];

			array[0] = getByC_N_PrevAndNext(session, kaleoDefinitionVersion,
					companyId, name, orderByComparator, true);

			array[1] = kaleoDefinitionVersion;

			array[2] = getByC_N_PrevAndNext(session, kaleoDefinitionVersion,
					companyId, name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoDefinitionVersion getByC_N_PrevAndNext(Session session,
		KaleoDefinitionVersion kaleoDefinitionVersion, long companyId,
		String name,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_KALEODEFINITIONVERSION_WHERE);

		query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_C_N_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_C_N_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_C_N_NAME_2);
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
			query.append(KaleoDefinitionVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindName) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(kaleoDefinitionVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<KaleoDefinitionVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo definition versions where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	@Override
	public void removeByC_N(long companyId, String name) {
		for (KaleoDefinitionVersion kaleoDefinitionVersion : findByC_N(
				companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(kaleoDefinitionVersion);
		}
	}

	/**
	 * Returns the number of kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching kaleo definition versions
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_N;

		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_KALEODEFINITIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "kaleoDefinitionVersion.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "kaleoDefinitionVersion.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "kaleoDefinitionVersion.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(kaleoDefinitionVersion.name IS NULL OR kaleoDefinitionVersion.name = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N_V = new FinderPath(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
			KaleoDefinitionVersionModelImpl.FINDER_CACHE_ENABLED,
			KaleoDefinitionVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_N_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			KaleoDefinitionVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			KaleoDefinitionVersionModelImpl.NAME_COLUMN_BITMASK |
			KaleoDefinitionVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N_V = new FinderPath(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
			KaleoDefinitionVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; or throws a {@link NoSuchDefinitionVersionException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion findByC_N_V(long companyId, String name,
		String version) throws NoSuchDefinitionVersionException {
		KaleoDefinitionVersion kaleoDefinitionVersion = fetchByC_N_V(companyId,
				name, version);

		if (kaleoDefinitionVersion == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", version=");
			msg.append(version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchDefinitionVersionException(msg.toString());
		}

		return kaleoDefinitionVersion;
	}

	/**
	 * Returns the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByC_N_V(long companyId, String name,
		String version) {
		return fetchByC_N_V(companyId, name, version, true);
	}

	/**
	 * Returns the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByC_N_V(long companyId, String name,
		String version, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, name, version };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_N_V,
					finderArgs, this);
		}

		if (result instanceof KaleoDefinitionVersion) {
			KaleoDefinitionVersion kaleoDefinitionVersion = (KaleoDefinitionVersion)result;

			if ((companyId != kaleoDefinitionVersion.getCompanyId()) ||
					!Objects.equals(name, kaleoDefinitionVersion.getName()) ||
					!Objects.equals(version, kaleoDefinitionVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_KALEODEFINITIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_V_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_V_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_V_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_V_NAME_2);
			}

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_C_N_V_VERSION_1);
			}
			else if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_C_N_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				if (bindVersion) {
					qPos.add(version);
				}

				List<KaleoDefinitionVersion> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_N_V,
						finderArgs, list);
				}
				else {
					KaleoDefinitionVersion kaleoDefinitionVersion = list.get(0);

					result = kaleoDefinitionVersion;

					cacheResult(kaleoDefinitionVersion);

					if ((kaleoDefinitionVersion.getCompanyId() != companyId) ||
							(kaleoDefinitionVersion.getName() == null) ||
							!kaleoDefinitionVersion.getName().equals(name) ||
							(kaleoDefinitionVersion.getVersion() == null) ||
							!kaleoDefinitionVersion.getVersion().equals(version)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_N_V,
							finderArgs, kaleoDefinitionVersion);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_N_V, finderArgs);

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
			return (KaleoDefinitionVersion)result;
		}
	}

	/**
	 * Removes the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the kaleo definition version that was removed
	 */
	@Override
	public KaleoDefinitionVersion removeByC_N_V(long companyId, String name,
		String version) throws NoSuchDefinitionVersionException {
		KaleoDefinitionVersion kaleoDefinitionVersion = findByC_N_V(companyId,
				name, version);

		return remove(kaleoDefinitionVersion);
	}

	/**
	 * Returns the number of kaleo definition versions where companyId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the number of matching kaleo definition versions
	 */
	@Override
	public int countByC_N_V(long companyId, String name, String version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_N_V;

		Object[] finderArgs = new Object[] { companyId, name, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_KALEODEFINITIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_V_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_V_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_V_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_V_NAME_2);
			}

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_C_N_V_VERSION_1);
			}
			else if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_C_N_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

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

	private static final String _FINDER_COLUMN_C_N_V_COMPANYID_2 = "kaleoDefinitionVersion.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_V_NAME_1 = "kaleoDefinitionVersion.name IS NULL AND ";
	private static final String _FINDER_COLUMN_C_N_V_NAME_2 = "kaleoDefinitionVersion.name = ? AND ";
	private static final String _FINDER_COLUMN_C_N_V_NAME_3 = "(kaleoDefinitionVersion.name IS NULL OR kaleoDefinitionVersion.name = '') AND ";
	private static final String _FINDER_COLUMN_C_N_V_VERSION_1 = "kaleoDefinitionVersion.version IS NULL";
	private static final String _FINDER_COLUMN_C_N_V_VERSION_2 = "kaleoDefinitionVersion.version = ?";
	private static final String _FINDER_COLUMN_C_N_V_VERSION_3 = "(kaleoDefinitionVersion.version IS NULL OR kaleoDefinitionVersion.version = '')";

	public KaleoDefinitionVersionPersistenceImpl() {
		setModelClass(KaleoDefinitionVersion.class);
	}

	/**
	 * Caches the kaleo definition version in the entity cache if it is enabled.
	 *
	 * @param kaleoDefinitionVersion the kaleo definition version
	 */
	@Override
	public void cacheResult(KaleoDefinitionVersion kaleoDefinitionVersion) {
		entityCache.putResult(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
			KaleoDefinitionVersionImpl.class,
			kaleoDefinitionVersion.getPrimaryKey(), kaleoDefinitionVersion);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_N_V,
			new Object[] {
				kaleoDefinitionVersion.getCompanyId(),
				kaleoDefinitionVersion.getName(),
				kaleoDefinitionVersion.getVersion()
			}, kaleoDefinitionVersion);

		kaleoDefinitionVersion.resetOriginalValues();
	}

	/**
	 * Caches the kaleo definition versions in the entity cache if it is enabled.
	 *
	 * @param kaleoDefinitionVersions the kaleo definition versions
	 */
	@Override
	public void cacheResult(
		List<KaleoDefinitionVersion> kaleoDefinitionVersions) {
		for (KaleoDefinitionVersion kaleoDefinitionVersion : kaleoDefinitionVersions) {
			if (entityCache.getResult(
						KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
						KaleoDefinitionVersionImpl.class,
						kaleoDefinitionVersion.getPrimaryKey()) == null) {
				cacheResult(kaleoDefinitionVersion);
			}
			else {
				kaleoDefinitionVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all kaleo definition versions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoDefinitionVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the kaleo definition version.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KaleoDefinitionVersion kaleoDefinitionVersion) {
		entityCache.removeResult(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
			KaleoDefinitionVersionImpl.class,
			kaleoDefinitionVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((KaleoDefinitionVersionModelImpl)kaleoDefinitionVersion,
			true);
	}

	@Override
	public void clearCache(List<KaleoDefinitionVersion> kaleoDefinitionVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (KaleoDefinitionVersion kaleoDefinitionVersion : kaleoDefinitionVersions) {
			entityCache.removeResult(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
				KaleoDefinitionVersionImpl.class,
				kaleoDefinitionVersion.getPrimaryKey());

			clearUniqueFindersCache((KaleoDefinitionVersionModelImpl)kaleoDefinitionVersion,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		KaleoDefinitionVersionModelImpl kaleoDefinitionVersionModelImpl) {
		Object[] args = new Object[] {
				kaleoDefinitionVersionModelImpl.getCompanyId(),
				kaleoDefinitionVersionModelImpl.getName(),
				kaleoDefinitionVersionModelImpl.getVersion()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_N_V, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_N_V, args,
			kaleoDefinitionVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		KaleoDefinitionVersionModelImpl kaleoDefinitionVersionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					kaleoDefinitionVersionModelImpl.getCompanyId(),
					kaleoDefinitionVersionModelImpl.getName(),
					kaleoDefinitionVersionModelImpl.getVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_N_V, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_N_V, args);
		}

		if ((kaleoDefinitionVersionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_N_V.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					kaleoDefinitionVersionModelImpl.getOriginalCompanyId(),
					kaleoDefinitionVersionModelImpl.getOriginalName(),
					kaleoDefinitionVersionModelImpl.getOriginalVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_N_V, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_N_V, args);
		}
	}

	/**
	 * Creates a new kaleo definition version with the primary key. Does not add the kaleo definition version to the database.
	 *
	 * @param kaleoDefinitionVersionId the primary key for the new kaleo definition version
	 * @return the new kaleo definition version
	 */
	@Override
	public KaleoDefinitionVersion create(long kaleoDefinitionVersionId) {
		KaleoDefinitionVersion kaleoDefinitionVersion = new KaleoDefinitionVersionImpl();

		kaleoDefinitionVersion.setNew(true);
		kaleoDefinitionVersion.setPrimaryKey(kaleoDefinitionVersionId);

		kaleoDefinitionVersion.setCompanyId(companyProvider.getCompanyId());

		return kaleoDefinitionVersion;
	}

	/**
	 * Removes the kaleo definition version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the kaleo definition version
	 * @return the kaleo definition version that was removed
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion remove(long kaleoDefinitionVersionId)
		throws NoSuchDefinitionVersionException {
		return remove((Serializable)kaleoDefinitionVersionId);
	}

	/**
	 * Removes the kaleo definition version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo definition version
	 * @return the kaleo definition version that was removed
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion remove(Serializable primaryKey)
		throws NoSuchDefinitionVersionException {
		Session session = null;

		try {
			session = openSession();

			KaleoDefinitionVersion kaleoDefinitionVersion = (KaleoDefinitionVersion)session.get(KaleoDefinitionVersionImpl.class,
					primaryKey);

			if (kaleoDefinitionVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDefinitionVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(kaleoDefinitionVersion);
		}
		catch (NoSuchDefinitionVersionException nsee) {
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
	protected KaleoDefinitionVersion removeImpl(
		KaleoDefinitionVersion kaleoDefinitionVersion) {
		kaleoDefinitionVersion = toUnwrappedModel(kaleoDefinitionVersion);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoDefinitionVersion)) {
				kaleoDefinitionVersion = (KaleoDefinitionVersion)session.get(KaleoDefinitionVersionImpl.class,
						kaleoDefinitionVersion.getPrimaryKeyObj());
			}

			if (kaleoDefinitionVersion != null) {
				session.delete(kaleoDefinitionVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (kaleoDefinitionVersion != null) {
			clearCache(kaleoDefinitionVersion);
		}

		return kaleoDefinitionVersion;
	}

	@Override
	public KaleoDefinitionVersion updateImpl(
		KaleoDefinitionVersion kaleoDefinitionVersion) {
		kaleoDefinitionVersion = toUnwrappedModel(kaleoDefinitionVersion);

		boolean isNew = kaleoDefinitionVersion.isNew();

		KaleoDefinitionVersionModelImpl kaleoDefinitionVersionModelImpl = (KaleoDefinitionVersionModelImpl)kaleoDefinitionVersion;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (kaleoDefinitionVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoDefinitionVersion.setCreateDate(now);
			}
			else {
				kaleoDefinitionVersion.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!kaleoDefinitionVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoDefinitionVersion.setModifiedDate(now);
			}
			else {
				kaleoDefinitionVersion.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (kaleoDefinitionVersion.isNew()) {
				session.save(kaleoDefinitionVersion);

				kaleoDefinitionVersion.setNew(false);
			}
			else {
				kaleoDefinitionVersion = (KaleoDefinitionVersion)session.merge(kaleoDefinitionVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!KaleoDefinitionVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					kaleoDefinitionVersionModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] {
					kaleoDefinitionVersionModelImpl.getCompanyId(),
					kaleoDefinitionVersionModelImpl.getName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((kaleoDefinitionVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						kaleoDefinitionVersionModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						kaleoDefinitionVersionModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((kaleoDefinitionVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						kaleoDefinitionVersionModelImpl.getOriginalCompanyId(),
						kaleoDefinitionVersionModelImpl.getOriginalName()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N,
					args);

				args = new Object[] {
						kaleoDefinitionVersionModelImpl.getCompanyId(),
						kaleoDefinitionVersionModelImpl.getName()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_N,
					args);
			}
		}

		entityCache.putResult(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
			KaleoDefinitionVersionImpl.class,
			kaleoDefinitionVersion.getPrimaryKey(), kaleoDefinitionVersion,
			false);

		clearUniqueFindersCache(kaleoDefinitionVersionModelImpl, false);
		cacheUniqueFindersCache(kaleoDefinitionVersionModelImpl);

		kaleoDefinitionVersion.resetOriginalValues();

		return kaleoDefinitionVersion;
	}

	protected KaleoDefinitionVersion toUnwrappedModel(
		KaleoDefinitionVersion kaleoDefinitionVersion) {
		if (kaleoDefinitionVersion instanceof KaleoDefinitionVersionImpl) {
			return kaleoDefinitionVersion;
		}

		KaleoDefinitionVersionImpl kaleoDefinitionVersionImpl = new KaleoDefinitionVersionImpl();

		kaleoDefinitionVersionImpl.setNew(kaleoDefinitionVersion.isNew());
		kaleoDefinitionVersionImpl.setPrimaryKey(kaleoDefinitionVersion.getPrimaryKey());

		kaleoDefinitionVersionImpl.setKaleoDefinitionVersionId(kaleoDefinitionVersion.getKaleoDefinitionVersionId());
		kaleoDefinitionVersionImpl.setGroupId(kaleoDefinitionVersion.getGroupId());
		kaleoDefinitionVersionImpl.setCompanyId(kaleoDefinitionVersion.getCompanyId());
		kaleoDefinitionVersionImpl.setUserId(kaleoDefinitionVersion.getUserId());
		kaleoDefinitionVersionImpl.setUserName(kaleoDefinitionVersion.getUserName());
		kaleoDefinitionVersionImpl.setStatusByUserId(kaleoDefinitionVersion.getStatusByUserId());
		kaleoDefinitionVersionImpl.setStatusByUserName(kaleoDefinitionVersion.getStatusByUserName());
		kaleoDefinitionVersionImpl.setStatusDate(kaleoDefinitionVersion.getStatusDate());
		kaleoDefinitionVersionImpl.setCreateDate(kaleoDefinitionVersion.getCreateDate());
		kaleoDefinitionVersionImpl.setModifiedDate(kaleoDefinitionVersion.getModifiedDate());
		kaleoDefinitionVersionImpl.setName(kaleoDefinitionVersion.getName());
		kaleoDefinitionVersionImpl.setTitle(kaleoDefinitionVersion.getTitle());
		kaleoDefinitionVersionImpl.setDescription(kaleoDefinitionVersion.getDescription());
		kaleoDefinitionVersionImpl.setContent(kaleoDefinitionVersion.getContent());
		kaleoDefinitionVersionImpl.setVersion(kaleoDefinitionVersion.getVersion());
		kaleoDefinitionVersionImpl.setStatus(kaleoDefinitionVersion.getStatus());

		return kaleoDefinitionVersionImpl;
	}

	/**
	 * Returns the kaleo definition version with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo definition version
	 * @return the kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDefinitionVersionException {
		KaleoDefinitionVersion kaleoDefinitionVersion = fetchByPrimaryKey(primaryKey);

		if (kaleoDefinitionVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDefinitionVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return kaleoDefinitionVersion;
	}

	/**
	 * Returns the kaleo definition version with the primary key or throws a {@link NoSuchDefinitionVersionException} if it could not be found.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the kaleo definition version
	 * @return the kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion findByPrimaryKey(
		long kaleoDefinitionVersionId) throws NoSuchDefinitionVersionException {
		return findByPrimaryKey((Serializable)kaleoDefinitionVersionId);
	}

	/**
	 * Returns the kaleo definition version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo definition version
	 * @return the kaleo definition version, or <code>null</code> if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
				KaleoDefinitionVersionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		KaleoDefinitionVersion kaleoDefinitionVersion = (KaleoDefinitionVersion)serializable;

		if (kaleoDefinitionVersion == null) {
			Session session = null;

			try {
				session = openSession();

				kaleoDefinitionVersion = (KaleoDefinitionVersion)session.get(KaleoDefinitionVersionImpl.class,
						primaryKey);

				if (kaleoDefinitionVersion != null) {
					cacheResult(kaleoDefinitionVersion);
				}
				else {
					entityCache.putResult(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
						KaleoDefinitionVersionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
					KaleoDefinitionVersionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return kaleoDefinitionVersion;
	}

	/**
	 * Returns the kaleo definition version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the kaleo definition version
	 * @return the kaleo definition version, or <code>null</code> if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByPrimaryKey(
		long kaleoDefinitionVersionId) {
		return fetchByPrimaryKey((Serializable)kaleoDefinitionVersionId);
	}

	@Override
	public Map<Serializable, KaleoDefinitionVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, KaleoDefinitionVersion> map = new HashMap<Serializable, KaleoDefinitionVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			KaleoDefinitionVersion kaleoDefinitionVersion = fetchByPrimaryKey(primaryKey);

			if (kaleoDefinitionVersion != null) {
				map.put(primaryKey, kaleoDefinitionVersion);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
					KaleoDefinitionVersionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (KaleoDefinitionVersion)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_KALEODEFINITIONVERSION_WHERE_PKS_IN);

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

			for (KaleoDefinitionVersion kaleoDefinitionVersion : (List<KaleoDefinitionVersion>)q.list()) {
				map.put(kaleoDefinitionVersion.getPrimaryKeyObj(),
					kaleoDefinitionVersion);

				cacheResult(kaleoDefinitionVersion);

				uncachedPrimaryKeys.remove(kaleoDefinitionVersion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(KaleoDefinitionVersionModelImpl.ENTITY_CACHE_ENABLED,
					KaleoDefinitionVersionImpl.class, primaryKey, nullModel);
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
	 * Returns all the kaleo definition versions.
	 *
	 * @return the kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDefinitionVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @return the range of kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDefinitionVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findAll(int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDefinitionVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findAll(int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator,
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

		List<KaleoDefinitionVersion> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoDefinitionVersion>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_KALEODEFINITIONVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_KALEODEFINITIONVERSION;

				if (pagination) {
					sql = sql.concat(KaleoDefinitionVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<KaleoDefinitionVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoDefinitionVersion>)QueryUtil.list(q,
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
	 * Removes all the kaleo definition versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoDefinitionVersion kaleoDefinitionVersion : findAll()) {
			remove(kaleoDefinitionVersion);
		}
	}

	/**
	 * Returns the number of kaleo definition versions.
	 *
	 * @return the number of kaleo definition versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_KALEODEFINITIONVERSION);

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
		return KaleoDefinitionVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo definition version persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(KaleoDefinitionVersionImpl.class.getName());
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
	private static final String _SQL_SELECT_KALEODEFINITIONVERSION = "SELECT kaleoDefinitionVersion FROM KaleoDefinitionVersion kaleoDefinitionVersion";
	private static final String _SQL_SELECT_KALEODEFINITIONVERSION_WHERE_PKS_IN = "SELECT kaleoDefinitionVersion FROM KaleoDefinitionVersion kaleoDefinitionVersion WHERE kaleoDefinitionVersionId IN (";
	private static final String _SQL_SELECT_KALEODEFINITIONVERSION_WHERE = "SELECT kaleoDefinitionVersion FROM KaleoDefinitionVersion kaleoDefinitionVersion WHERE ";
	private static final String _SQL_COUNT_KALEODEFINITIONVERSION = "SELECT COUNT(kaleoDefinitionVersion) FROM KaleoDefinitionVersion kaleoDefinitionVersion";
	private static final String _SQL_COUNT_KALEODEFINITIONVERSION_WHERE = "SELECT COUNT(kaleoDefinitionVersion) FROM KaleoDefinitionVersion kaleoDefinitionVersion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "kaleoDefinitionVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No KaleoDefinitionVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No KaleoDefinitionVersion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(KaleoDefinitionVersionPersistenceImpl.class);
}