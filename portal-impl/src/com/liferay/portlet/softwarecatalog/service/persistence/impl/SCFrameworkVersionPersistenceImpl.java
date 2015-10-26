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

package com.liferay.portlet.softwarecatalog.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.service.persistence.impl.TableMapper;
import com.liferay.portal.service.persistence.impl.TableMapperFactory;

import com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl;
import com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the s c framework version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCFrameworkVersionPersistence
 * @see com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionUtil
 * @generated
 */
@ProviderType
public class SCFrameworkVersionPersistenceImpl extends BasePersistenceImpl<SCFrameworkVersion>
	implements SCFrameworkVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SCFrameworkVersionUtil} to access the s c framework version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SCFrameworkVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			SCFrameworkVersionModelImpl.GROUPID_COLUMN_BITMASK |
			SCFrameworkVersionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the s c framework versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c framework versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @return the range of matching s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c framework versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SCFrameworkVersion> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the s c framework versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SCFrameworkVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<SCFrameworkVersion> list = null;

		if (retrieveFromCache) {
			list = (List<SCFrameworkVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SCFrameworkVersion scFrameworkVersion : list) {
					if ((groupId != scFrameworkVersion.getGroupId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
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
	 * Returns the first s c framework version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c framework version
	 * @throws NoSuchFrameworkVersionException if a matching s c framework version could not be found
	 */
	@Override
	public SCFrameworkVersion findByGroupId_First(long groupId,
		OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws NoSuchFrameworkVersionException {
		SCFrameworkVersion scFrameworkVersion = fetchByGroupId_First(groupId,
				orderByComparator);

		if (scFrameworkVersion != null) {
			return scFrameworkVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFrameworkVersionException(msg.toString());
	}

	/**
	 * Returns the first s c framework version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	 */
	@Override
	public SCFrameworkVersion fetchByGroupId_First(long groupId,
		OrderByComparator<SCFrameworkVersion> orderByComparator) {
		List<SCFrameworkVersion> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c framework version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c framework version
	 * @throws NoSuchFrameworkVersionException if a matching s c framework version could not be found
	 */
	@Override
	public SCFrameworkVersion findByGroupId_Last(long groupId,
		OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws NoSuchFrameworkVersionException {
		SCFrameworkVersion scFrameworkVersion = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (scFrameworkVersion != null) {
			return scFrameworkVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFrameworkVersionException(msg.toString());
	}

	/**
	 * Returns the last s c framework version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	 */
	@Override
	public SCFrameworkVersion fetchByGroupId_Last(long groupId,
		OrderByComparator<SCFrameworkVersion> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SCFrameworkVersion> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c framework versions before and after the current s c framework version in the ordered set where groupId = &#63;.
	 *
	 * @param frameworkVersionId the primary key of the current s c framework version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c framework version
	 * @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 */
	@Override
	public SCFrameworkVersion[] findByGroupId_PrevAndNext(
		long frameworkVersionId, long groupId,
		OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws NoSuchFrameworkVersionException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, scFrameworkVersion,
					groupId, orderByComparator, true);

			array[1] = scFrameworkVersion;

			array[2] = getByGroupId_PrevAndNext(session, scFrameworkVersion,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCFrameworkVersion getByGroupId_PrevAndNext(Session session,
		SCFrameworkVersion scFrameworkVersion, long groupId,
		OrderByComparator<SCFrameworkVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scFrameworkVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCFrameworkVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c framework versions that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching s c framework versions that the user has permission to view
	 */
	@Override
	public List<SCFrameworkVersion> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c framework versions that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @return the range of matching s c framework versions that the user has permission to view
	 */
	@Override
	public List<SCFrameworkVersion> filterFindByGroupId(long groupId,
		int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c framework versions that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c framework versions that the user has permission to view
	 */
	@Override
	public List<SCFrameworkVersion> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<SCFrameworkVersion> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCFrameworkVersion.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SCFrameworkVersionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SCFrameworkVersionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<SCFrameworkVersion>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s c framework versions before and after the current s c framework version in the ordered set of s c framework versions that the user has permission to view where groupId = &#63;.
	 *
	 * @param frameworkVersionId the primary key of the current s c framework version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c framework version
	 * @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 */
	@Override
	public SCFrameworkVersion[] filterFindByGroupId_PrevAndNext(
		long frameworkVersionId, long groupId,
		OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws NoSuchFrameworkVersionException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(frameworkVersionId, groupId,
				orderByComparator);
		}

		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					scFrameworkVersion, groupId, orderByComparator, true);

			array[1] = scFrameworkVersion;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					scFrameworkVersion, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCFrameworkVersion filterGetByGroupId_PrevAndNext(
		Session session, SCFrameworkVersion scFrameworkVersion, long groupId,
		OrderByComparator<SCFrameworkVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCFrameworkVersion.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SCFrameworkVersionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SCFrameworkVersionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scFrameworkVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCFrameworkVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the s c framework versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SCFrameworkVersion scFrameworkVersion : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(scFrameworkVersion);
		}
	}

	/**
	 * Returns the number of s c framework versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching s c framework versions
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	/**
	 * Returns the number of s c framework versions that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching s c framework versions that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCFrameworkVersion.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "scFrameworkVersion.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			SCFrameworkVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			SCFrameworkVersionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the s c framework versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the s c framework versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @return the range of matching s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c framework versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<SCFrameworkVersion> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the s c framework versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<SCFrameworkVersion> orderByComparator,
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

		List<SCFrameworkVersion> list = null;

		if (retrieveFromCache) {
			list = (List<SCFrameworkVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SCFrameworkVersion scFrameworkVersion : list) {
					if ((companyId != scFrameworkVersion.getCompanyId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
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
	 * Returns the first s c framework version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c framework version
	 * @throws NoSuchFrameworkVersionException if a matching s c framework version could not be found
	 */
	@Override
	public SCFrameworkVersion findByCompanyId_First(long companyId,
		OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws NoSuchFrameworkVersionException {
		SCFrameworkVersion scFrameworkVersion = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (scFrameworkVersion != null) {
			return scFrameworkVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFrameworkVersionException(msg.toString());
	}

	/**
	 * Returns the first s c framework version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	 */
	@Override
	public SCFrameworkVersion fetchByCompanyId_First(long companyId,
		OrderByComparator<SCFrameworkVersion> orderByComparator) {
		List<SCFrameworkVersion> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c framework version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c framework version
	 * @throws NoSuchFrameworkVersionException if a matching s c framework version could not be found
	 */
	@Override
	public SCFrameworkVersion findByCompanyId_Last(long companyId,
		OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws NoSuchFrameworkVersionException {
		SCFrameworkVersion scFrameworkVersion = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (scFrameworkVersion != null) {
			return scFrameworkVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFrameworkVersionException(msg.toString());
	}

	/**
	 * Returns the last s c framework version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	 */
	@Override
	public SCFrameworkVersion fetchByCompanyId_Last(long companyId,
		OrderByComparator<SCFrameworkVersion> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<SCFrameworkVersion> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c framework versions before and after the current s c framework version in the ordered set where companyId = &#63;.
	 *
	 * @param frameworkVersionId the primary key of the current s c framework version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c framework version
	 * @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 */
	@Override
	public SCFrameworkVersion[] findByCompanyId_PrevAndNext(
		long frameworkVersionId, long companyId,
		OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws NoSuchFrameworkVersionException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, scFrameworkVersion,
					companyId, orderByComparator, true);

			array[1] = scFrameworkVersion;

			array[2] = getByCompanyId_PrevAndNext(session, scFrameworkVersion,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCFrameworkVersion getByCompanyId_PrevAndNext(Session session,
		SCFrameworkVersion scFrameworkVersion, long companyId,
		OrderByComparator<SCFrameworkVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

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
			query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scFrameworkVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCFrameworkVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the s c framework versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (SCFrameworkVersion scFrameworkVersion : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(scFrameworkVersion);
		}
	}

	/**
	 * Returns the number of s c framework versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching s c framework versions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "scFrameworkVersion.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			SCFrameworkVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			SCFrameworkVersionModelImpl.GROUPID_COLUMN_BITMASK |
			SCFrameworkVersionModelImpl.ACTIVE_COLUMN_BITMASK |
			SCFrameworkVersionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the s c framework versions where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findByG_A(long groupId, boolean active) {
		return findByG_A(groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the s c framework versions where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @return the range of matching s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findByG_A(long groupId, boolean active,
		int start, int end) {
		return findByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c framework versions where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findByG_A(long groupId, boolean active,
		int start, int end,
		OrderByComparator<SCFrameworkVersion> orderByComparator) {
		return findByG_A(groupId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the s c framework versions where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findByG_A(long groupId, boolean active,
		int start, int end,
		OrderByComparator<SCFrameworkVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] { groupId, active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] {
					groupId, active,
					
					start, end, orderByComparator
				};
		}

		List<SCFrameworkVersion> list = null;

		if (retrieveFromCache) {
			list = (List<SCFrameworkVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SCFrameworkVersion scFrameworkVersion : list) {
					if ((groupId != scFrameworkVersion.getGroupId()) ||
							(active != scFrameworkVersion.getActive())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

				if (!pagination) {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
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
	 * Returns the first s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c framework version
	 * @throws NoSuchFrameworkVersionException if a matching s c framework version could not be found
	 */
	@Override
	public SCFrameworkVersion findByG_A_First(long groupId, boolean active,
		OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws NoSuchFrameworkVersionException {
		SCFrameworkVersion scFrameworkVersion = fetchByG_A_First(groupId,
				active, orderByComparator);

		if (scFrameworkVersion != null) {
			return scFrameworkVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFrameworkVersionException(msg.toString());
	}

	/**
	 * Returns the first s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	 */
	@Override
	public SCFrameworkVersion fetchByG_A_First(long groupId, boolean active,
		OrderByComparator<SCFrameworkVersion> orderByComparator) {
		List<SCFrameworkVersion> list = findByG_A(groupId, active, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c framework version
	 * @throws NoSuchFrameworkVersionException if a matching s c framework version could not be found
	 */
	@Override
	public SCFrameworkVersion findByG_A_Last(long groupId, boolean active,
		OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws NoSuchFrameworkVersionException {
		SCFrameworkVersion scFrameworkVersion = fetchByG_A_Last(groupId,
				active, orderByComparator);

		if (scFrameworkVersion != null) {
			return scFrameworkVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFrameworkVersionException(msg.toString());
	}

	/**
	 * Returns the last s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	 */
	@Override
	public SCFrameworkVersion fetchByG_A_Last(long groupId, boolean active,
		OrderByComparator<SCFrameworkVersion> orderByComparator) {
		int count = countByG_A(groupId, active);

		if (count == 0) {
			return null;
		}

		List<SCFrameworkVersion> list = findByG_A(groupId, active, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s c framework versions before and after the current s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param frameworkVersionId the primary key of the current s c framework version
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c framework version
	 * @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 */
	@Override
	public SCFrameworkVersion[] findByG_A_PrevAndNext(long frameworkVersionId,
		long groupId, boolean active,
		OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws NoSuchFrameworkVersionException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = getByG_A_PrevAndNext(session, scFrameworkVersion,
					groupId, active, orderByComparator, true);

			array[1] = scFrameworkVersion;

			array[2] = getByG_A_PrevAndNext(session, scFrameworkVersion,
					groupId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCFrameworkVersion getByG_A_PrevAndNext(Session session,
		SCFrameworkVersion scFrameworkVersion, long groupId, boolean active,
		OrderByComparator<SCFrameworkVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

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
			query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scFrameworkVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCFrameworkVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching s c framework versions that the user has permission to view
	 */
	@Override
	public List<SCFrameworkVersion> filterFindByG_A(long groupId, boolean active) {
		return filterFindByG_A(groupId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @return the range of matching s c framework versions that the user has permission to view
	 */
	@Override
	public List<SCFrameworkVersion> filterFindByG_A(long groupId,
		boolean active, int start, int end) {
		return filterFindByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c framework versions that the user has permissions to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s c framework versions that the user has permission to view
	 */
	@Override
	public List<SCFrameworkVersion> filterFindByG_A(long groupId,
		boolean active, int start, int end,
		OrderByComparator<SCFrameworkVersion> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A(groupId, active, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCFrameworkVersion.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SCFrameworkVersionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SCFrameworkVersionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(active);

			return (List<SCFrameworkVersion>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s c framework versions before and after the current s c framework version in the ordered set of s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param frameworkVersionId the primary key of the current s c framework version
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s c framework version
	 * @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 */
	@Override
	public SCFrameworkVersion[] filterFindByG_A_PrevAndNext(
		long frameworkVersionId, long groupId, boolean active,
		OrderByComparator<SCFrameworkVersion> orderByComparator)
		throws NoSuchFrameworkVersionException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_PrevAndNext(frameworkVersionId, groupId, active,
				orderByComparator);
		}

		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = filterGetByG_A_PrevAndNext(session, scFrameworkVersion,
					groupId, active, orderByComparator, true);

			array[1] = scFrameworkVersion;

			array[2] = filterGetByG_A_PrevAndNext(session, scFrameworkVersion,
					groupId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCFrameworkVersion filterGetByG_A_PrevAndNext(Session session,
		SCFrameworkVersion scFrameworkVersion, long groupId, boolean active,
		OrderByComparator<SCFrameworkVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCFrameworkVersion.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SCFrameworkVersionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SCFrameworkVersionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(scFrameworkVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCFrameworkVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the s c framework versions where groupId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 */
	@Override
	public void removeByG_A(long groupId, boolean active) {
		for (SCFrameworkVersion scFrameworkVersion : findByG_A(groupId, active,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(scFrameworkVersion);
		}
	}

	/**
	 * Returns the number of s c framework versions where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching s c framework versions
	 */
	@Override
	public int countByG_A(long groupId, boolean active) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A;

		Object[] finderArgs = new Object[] { groupId, active };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

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

	/**
	 * Returns the number of s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching s c framework versions that the user has permission to view
	 */
	@Override
	public int filterCountByG_A(long groupId, boolean active) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A(groupId, active);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SCFrameworkVersion.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(active);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "scFrameworkVersion.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ACTIVE_2 = "scFrameworkVersion.active = ?";
	private static final String _FINDER_COLUMN_G_A_ACTIVE_2_SQL = "scFrameworkVersion.active_ = ?";

	public SCFrameworkVersionPersistenceImpl() {
		setModelClass(SCFrameworkVersion.class);
	}

	/**
	 * Caches the s c framework version in the entity cache if it is enabled.
	 *
	 * @param scFrameworkVersion the s c framework version
	 */
	@Override
	public void cacheResult(SCFrameworkVersion scFrameworkVersion) {
		entityCache.putResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionImpl.class, scFrameworkVersion.getPrimaryKey(),
			scFrameworkVersion);

		scFrameworkVersion.resetOriginalValues();
	}

	/**
	 * Caches the s c framework versions in the entity cache if it is enabled.
	 *
	 * @param scFrameworkVersions the s c framework versions
	 */
	@Override
	public void cacheResult(List<SCFrameworkVersion> scFrameworkVersions) {
		for (SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
			if (entityCache.getResult(
						SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
						SCFrameworkVersionImpl.class,
						scFrameworkVersion.getPrimaryKey()) == null) {
				cacheResult(scFrameworkVersion);
			}
			else {
				scFrameworkVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all s c framework versions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SCFrameworkVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the s c framework version.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SCFrameworkVersion scFrameworkVersion) {
		entityCache.removeResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionImpl.class, scFrameworkVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<SCFrameworkVersion> scFrameworkVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
			entityCache.removeResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
				SCFrameworkVersionImpl.class, scFrameworkVersion.getPrimaryKey());
		}
	}

	/**
	 * Creates a new s c framework version with the primary key. Does not add the s c framework version to the database.
	 *
	 * @param frameworkVersionId the primary key for the new s c framework version
	 * @return the new s c framework version
	 */
	@Override
	public SCFrameworkVersion create(long frameworkVersionId) {
		SCFrameworkVersion scFrameworkVersion = new SCFrameworkVersionImpl();

		scFrameworkVersion.setNew(true);
		scFrameworkVersion.setPrimaryKey(frameworkVersionId);

		return scFrameworkVersion;
	}

	/**
	 * Removes the s c framework version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param frameworkVersionId the primary key of the s c framework version
	 * @return the s c framework version that was removed
	 * @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 */
	@Override
	public SCFrameworkVersion remove(long frameworkVersionId)
		throws NoSuchFrameworkVersionException {
		return remove((Serializable)frameworkVersionId);
	}

	/**
	 * Removes the s c framework version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the s c framework version
	 * @return the s c framework version that was removed
	 * @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 */
	@Override
	public SCFrameworkVersion remove(Serializable primaryKey)
		throws NoSuchFrameworkVersionException {
		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)session.get(SCFrameworkVersionImpl.class,
					primaryKey);

			if (scFrameworkVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFrameworkVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(scFrameworkVersion);
		}
		catch (NoSuchFrameworkVersionException nsee) {
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
	protected SCFrameworkVersion removeImpl(
		SCFrameworkVersion scFrameworkVersion) {
		scFrameworkVersion = toUnwrappedModel(scFrameworkVersion);

		scFrameworkVersionToSCProductVersionTableMapper.deleteLeftPrimaryKeyTableMappings(0,
			scFrameworkVersion.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(scFrameworkVersion)) {
				scFrameworkVersion = (SCFrameworkVersion)session.get(SCFrameworkVersionImpl.class,
						scFrameworkVersion.getPrimaryKeyObj());
			}

			if (scFrameworkVersion != null) {
				session.delete(scFrameworkVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (scFrameworkVersion != null) {
			clearCache(scFrameworkVersion);
		}

		return scFrameworkVersion;
	}

	@Override
	public SCFrameworkVersion updateImpl(SCFrameworkVersion scFrameworkVersion) {
		scFrameworkVersion = toUnwrappedModel(scFrameworkVersion);

		boolean isNew = scFrameworkVersion.isNew();

		SCFrameworkVersionModelImpl scFrameworkVersionModelImpl = (SCFrameworkVersionModelImpl)scFrameworkVersion;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (scFrameworkVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				scFrameworkVersion.setCreateDate(now);
			}
			else {
				scFrameworkVersion.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!scFrameworkVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				scFrameworkVersion.setModifiedDate(now);
			}
			else {
				scFrameworkVersion.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (scFrameworkVersion.isNew()) {
				session.save(scFrameworkVersion);

				scFrameworkVersion.setNew(false);
			}
			else {
				scFrameworkVersion = (SCFrameworkVersion)session.merge(scFrameworkVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SCFrameworkVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((scFrameworkVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						scFrameworkVersionModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { scFrameworkVersionModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((scFrameworkVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						scFrameworkVersionModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { scFrameworkVersionModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((scFrameworkVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						scFrameworkVersionModelImpl.getOriginalGroupId(),
						scFrameworkVersionModelImpl.getOriginalActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);

				args = new Object[] {
						scFrameworkVersionModelImpl.getGroupId(),
						scFrameworkVersionModelImpl.getActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);
			}
		}

		entityCache.putResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionImpl.class, scFrameworkVersion.getPrimaryKey(),
			scFrameworkVersion, false);

		scFrameworkVersion.resetOriginalValues();

		return scFrameworkVersion;
	}

	protected SCFrameworkVersion toUnwrappedModel(
		SCFrameworkVersion scFrameworkVersion) {
		if (scFrameworkVersion instanceof SCFrameworkVersionImpl) {
			return scFrameworkVersion;
		}

		SCFrameworkVersionImpl scFrameworkVersionImpl = new SCFrameworkVersionImpl();

		scFrameworkVersionImpl.setNew(scFrameworkVersion.isNew());
		scFrameworkVersionImpl.setPrimaryKey(scFrameworkVersion.getPrimaryKey());

		scFrameworkVersionImpl.setFrameworkVersionId(scFrameworkVersion.getFrameworkVersionId());
		scFrameworkVersionImpl.setGroupId(scFrameworkVersion.getGroupId());
		scFrameworkVersionImpl.setCompanyId(scFrameworkVersion.getCompanyId());
		scFrameworkVersionImpl.setUserId(scFrameworkVersion.getUserId());
		scFrameworkVersionImpl.setUserName(scFrameworkVersion.getUserName());
		scFrameworkVersionImpl.setCreateDate(scFrameworkVersion.getCreateDate());
		scFrameworkVersionImpl.setModifiedDate(scFrameworkVersion.getModifiedDate());
		scFrameworkVersionImpl.setName(scFrameworkVersion.getName());
		scFrameworkVersionImpl.setUrl(scFrameworkVersion.getUrl());
		scFrameworkVersionImpl.setActive(scFrameworkVersion.isActive());
		scFrameworkVersionImpl.setPriority(scFrameworkVersion.getPriority());

		return scFrameworkVersionImpl;
	}

	/**
	 * Returns the s c framework version with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c framework version
	 * @return the s c framework version
	 * @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 */
	@Override
	public SCFrameworkVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFrameworkVersionException {
		SCFrameworkVersion scFrameworkVersion = fetchByPrimaryKey(primaryKey);

		if (scFrameworkVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFrameworkVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return scFrameworkVersion;
	}

	/**
	 * Returns the s c framework version with the primary key or throws a {@link NoSuchFrameworkVersionException} if it could not be found.
	 *
	 * @param frameworkVersionId the primary key of the s c framework version
	 * @return the s c framework version
	 * @throws NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	 */
	@Override
	public SCFrameworkVersion findByPrimaryKey(long frameworkVersionId)
		throws NoSuchFrameworkVersionException {
		return findByPrimaryKey((Serializable)frameworkVersionId);
	}

	/**
	 * Returns the s c framework version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the s c framework version
	 * @return the s c framework version, or <code>null</code> if a s c framework version with the primary key could not be found
	 */
	@Override
	public SCFrameworkVersion fetchByPrimaryKey(Serializable primaryKey) {
		SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)entityCache.getResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
				SCFrameworkVersionImpl.class, primaryKey);

		if (scFrameworkVersion == _nullSCFrameworkVersion) {
			return null;
		}

		if (scFrameworkVersion == null) {
			Session session = null;

			try {
				session = openSession();

				scFrameworkVersion = (SCFrameworkVersion)session.get(SCFrameworkVersionImpl.class,
						primaryKey);

				if (scFrameworkVersion != null) {
					cacheResult(scFrameworkVersion);
				}
				else {
					entityCache.putResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
						SCFrameworkVersionImpl.class, primaryKey,
						_nullSCFrameworkVersion);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
					SCFrameworkVersionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return scFrameworkVersion;
	}

	/**
	 * Returns the s c framework version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param frameworkVersionId the primary key of the s c framework version
	 * @return the s c framework version, or <code>null</code> if a s c framework version with the primary key could not be found
	 */
	@Override
	public SCFrameworkVersion fetchByPrimaryKey(long frameworkVersionId) {
		return fetchByPrimaryKey((Serializable)frameworkVersionId);
	}

	@Override
	public Map<Serializable, SCFrameworkVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SCFrameworkVersion> map = new HashMap<Serializable, SCFrameworkVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SCFrameworkVersion scFrameworkVersion = fetchByPrimaryKey(primaryKey);

			if (scFrameworkVersion != null) {
				map.put(primaryKey, scFrameworkVersion);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)entityCache.getResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
					SCFrameworkVersionImpl.class, primaryKey);

			if (scFrameworkVersion == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, scFrameworkVersion);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (SCFrameworkVersion scFrameworkVersion : (List<SCFrameworkVersion>)q.list()) {
				map.put(scFrameworkVersion.getPrimaryKeyObj(),
					scFrameworkVersion);

				cacheResult(scFrameworkVersion);

				uncachedPrimaryKeys.remove(scFrameworkVersion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
					SCFrameworkVersionImpl.class, primaryKey,
					_nullSCFrameworkVersion);
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
	 * Returns all the s c framework versions.
	 *
	 * @return the s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s c framework versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @return the range of s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c framework versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findAll(int start, int end,
		OrderByComparator<SCFrameworkVersion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the s c framework versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of s c framework versions
	 */
	@Override
	public List<SCFrameworkVersion> findAll(int start, int end,
		OrderByComparator<SCFrameworkVersion> orderByComparator,
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

		List<SCFrameworkVersion> list = null;

		if (retrieveFromCache) {
			list = (List<SCFrameworkVersion>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SCFRAMEWORKVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SCFRAMEWORKVERSION;

				if (pagination) {
					sql = sql.concat(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
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
	 * Removes all the s c framework versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SCFrameworkVersion scFrameworkVersion : findAll()) {
			remove(scFrameworkVersion);
		}
	}

	/**
	 * Returns the number of s c framework versions.
	 *
	 * @return the number of s c framework versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SCFRAMEWORKVERSION);

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

	/**
	 * Returns the primaryKeys of s c product versions associated with the s c framework version.
	 *
	 * @param pk the primary key of the s c framework version
	 * @return long[] of the primaryKeys of s c product versions associated with the s c framework version
	 */
	@Override
	public long[] getSCProductVersionPrimaryKeys(long pk) {
		long[] pks = scFrameworkVersionToSCProductVersionTableMapper.getRightPrimaryKeys(0,
				pk);

		return pks.clone();
	}

	/**
	 * Returns all the s c product versions associated with the s c framework version.
	 *
	 * @param pk the primary key of the s c framework version
	 * @return the s c product versions associated with the s c framework version
	 */
	@Override
	public List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk) {
		return getSCProductVersions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the s c product versions associated with the s c framework version.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the s c framework version
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @return the range of s c product versions associated with the s c framework version
	 */
	@Override
	public List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk, int start, int end) {
		return getSCProductVersions(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s c product versions associated with the s c framework version.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the s c framework version
	 * @param start the lower bound of the range of s c framework versions
	 * @param end the upper bound of the range of s c framework versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s c product versions associated with the s c framework version
	 */
	@Override
	public List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCProductVersion> orderByComparator) {
		return scFrameworkVersionToSCProductVersionTableMapper.getRightBaseModels(0,
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of s c product versions associated with the s c framework version.
	 *
	 * @param pk the primary key of the s c framework version
	 * @return the number of s c product versions associated with the s c framework version
	 */
	@Override
	public int getSCProductVersionsSize(long pk) {
		long[] pks = scFrameworkVersionToSCProductVersionTableMapper.getRightPrimaryKeys(0,
				pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the s c product version is associated with the s c framework version.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersionPK the primary key of the s c product version
	 * @return <code>true</code> if the s c product version is associated with the s c framework version; <code>false</code> otherwise
	 */
	@Override
	public boolean containsSCProductVersion(long pk, long scProductVersionPK) {
		return scFrameworkVersionToSCProductVersionTableMapper.containsTableMapping(0,
			pk, scProductVersionPK);
	}

	/**
	 * Returns <code>true</code> if the s c framework version has any s c product versions associated with it.
	 *
	 * @param pk the primary key of the s c framework version to check for associations with s c product versions
	 * @return <code>true</code> if the s c framework version has any s c product versions associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsSCProductVersions(long pk) {
		if (getSCProductVersionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersionPK the primary key of the s c product version
	 */
	@Override
	public void addSCProductVersion(long pk, long scProductVersionPK) {
		scFrameworkVersionToSCProductVersionTableMapper.addTableMapping(0, pk,
			scProductVersionPK);
	}

	/**
	 * Adds an association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersion the s c product version
	 */
	@Override
	public void addSCProductVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion) {
		scFrameworkVersionToSCProductVersionTableMapper.addTableMapping(0, pk,
			scProductVersion.getPrimaryKey());
	}

	/**
	 * Adds an association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersionPKs the primary keys of the s c product versions
	 */
	@Override
	public void addSCProductVersions(long pk, long[] scProductVersionPKs) {
		for (long scProductVersionPK : scProductVersionPKs) {
			scFrameworkVersionToSCProductVersionTableMapper.addTableMapping(0,
				pk, scProductVersionPK);
		}
	}

	/**
	 * Adds an association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersions the s c product versions
	 */
	@Override
	public void addSCProductVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions) {
		for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
			scFrameworkVersionToSCProductVersionTableMapper.addTableMapping(0,
				pk, scProductVersion.getPrimaryKey());
		}
	}

	/**
	 * Clears all associations between the s c framework version and its s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version to clear the associated s c product versions from
	 */
	@Override
	public void clearSCProductVersions(long pk) {
		scFrameworkVersionToSCProductVersionTableMapper.deleteLeftPrimaryKeyTableMappings(0,
			pk);
	}

	/**
	 * Removes the association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersionPK the primary key of the s c product version
	 */
	@Override
	public void removeSCProductVersion(long pk, long scProductVersionPK) {
		scFrameworkVersionToSCProductVersionTableMapper.deleteTableMapping(0,
			pk, scProductVersionPK);
	}

	/**
	 * Removes the association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersion the s c product version
	 */
	@Override
	public void removeSCProductVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion) {
		scFrameworkVersionToSCProductVersionTableMapper.deleteTableMapping(0,
			pk, scProductVersion.getPrimaryKey());
	}

	/**
	 * Removes the association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersionPKs the primary keys of the s c product versions
	 */
	@Override
	public void removeSCProductVersions(long pk, long[] scProductVersionPKs) {
		for (long scProductVersionPK : scProductVersionPKs) {
			scFrameworkVersionToSCProductVersionTableMapper.deleteTableMapping(0,
				pk, scProductVersionPK);
		}
	}

	/**
	 * Removes the association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersions the s c product versions
	 */
	@Override
	public void removeSCProductVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions) {
		for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
			scFrameworkVersionToSCProductVersionTableMapper.deleteTableMapping(0,
				pk, scProductVersion.getPrimaryKey());
		}
	}

	/**
	 * Sets the s c product versions associated with the s c framework version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersionPKs the primary keys of the s c product versions to be associated with the s c framework version
	 */
	@Override
	public void setSCProductVersions(long pk, long[] scProductVersionPKs) {
		Set<Long> newSCProductVersionPKsSet = SetUtil.fromArray(scProductVersionPKs);
		Set<Long> oldSCProductVersionPKsSet = SetUtil.fromArray(scFrameworkVersionToSCProductVersionTableMapper.getRightPrimaryKeys(
					0, pk));

		Set<Long> removeSCProductVersionPKsSet = new HashSet<Long>(oldSCProductVersionPKsSet);

		removeSCProductVersionPKsSet.removeAll(newSCProductVersionPKsSet);

		for (long removeSCProductVersionPK : removeSCProductVersionPKsSet) {
			scFrameworkVersionToSCProductVersionTableMapper.deleteTableMapping(0,
				pk, removeSCProductVersionPK);
		}

		newSCProductVersionPKsSet.removeAll(oldSCProductVersionPKsSet);

		for (long newSCProductVersionPK : newSCProductVersionPKsSet) {
			scFrameworkVersionToSCProductVersionTableMapper.addTableMapping(0,
				pk, newSCProductVersionPK);
		}
	}

	/**
	 * Sets the s c product versions associated with the s c framework version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the s c framework version
	 * @param scProductVersions the s c product versions to be associated with the s c framework version
	 */
	@Override
	public void setSCProductVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions) {
		try {
			long[] scProductVersionPKs = new long[scProductVersions.size()];

			for (int i = 0; i < scProductVersions.size(); i++) {
				com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion =
					scProductVersions.get(i);

				scProductVersionPKs[i] = scProductVersion.getPrimaryKey();
			}

			setSCProductVersions(pk, scProductVersionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SCFrameworkVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the s c framework version persistence.
	 */
	public void afterPropertiesSet() {
		scFrameworkVersionToSCProductVersionTableMapper = TableMapperFactory.getTableMapper("SCFrameworkVersi_SCProductVers",
				"companyId", "frameworkVersionId", "productVersionId", this,
				scProductVersionPersistence);
	}

	public void destroy() {
		entityCache.removeCache(SCFrameworkVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("SCFrameworkVersi_SCProductVers");
	}

	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	@BeanReference(type = SCProductVersionPersistence.class)
	protected SCProductVersionPersistence scProductVersionPersistence;
	protected TableMapper<SCFrameworkVersion, com.liferay.portlet.softwarecatalog.model.SCProductVersion> scFrameworkVersionToSCProductVersionTableMapper;
	private static final String _SQL_SELECT_SCFRAMEWORKVERSION = "SELECT scFrameworkVersion FROM SCFrameworkVersion scFrameworkVersion";
	private static final String _SQL_SELECT_SCFRAMEWORKVERSION_WHERE_PKS_IN = "SELECT scFrameworkVersion FROM SCFrameworkVersion scFrameworkVersion WHERE frameworkVersionId IN (";
	private static final String _SQL_SELECT_SCFRAMEWORKVERSION_WHERE = "SELECT scFrameworkVersion FROM SCFrameworkVersion scFrameworkVersion WHERE ";
	private static final String _SQL_COUNT_SCFRAMEWORKVERSION = "SELECT COUNT(scFrameworkVersion) FROM SCFrameworkVersion scFrameworkVersion";
	private static final String _SQL_COUNT_SCFRAMEWORKVERSION_WHERE = "SELECT COUNT(scFrameworkVersion) FROM SCFrameworkVersion scFrameworkVersion WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "scFrameworkVersion.frameworkVersionId";
	private static final String _FILTER_SQL_SELECT_SCFRAMEWORKVERSION_WHERE = "SELECT DISTINCT {scFrameworkVersion.*} FROM SCFrameworkVersion scFrameworkVersion WHERE ";
	private static final String _FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {SCFrameworkVersion.*} FROM (SELECT DISTINCT scFrameworkVersion.frameworkVersionId FROM SCFrameworkVersion scFrameworkVersion WHERE ";
	private static final String _FILTER_SQL_SELECT_SCFRAMEWORKVERSION_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN SCFrameworkVersion ON TEMP_TABLE.frameworkVersionId = SCFrameworkVersion.frameworkVersionId";
	private static final String _FILTER_SQL_COUNT_SCFRAMEWORKVERSION_WHERE = "SELECT COUNT(DISTINCT scFrameworkVersion.frameworkVersionId) AS COUNT_VALUE FROM SCFrameworkVersion scFrameworkVersion WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "scFrameworkVersion";
	private static final String _FILTER_ENTITY_TABLE = "SCFrameworkVersion";
	private static final String _ORDER_BY_ENTITY_ALIAS = "scFrameworkVersion.";
	private static final String _ORDER_BY_ENTITY_TABLE = "SCFrameworkVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SCFrameworkVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SCFrameworkVersion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SCFrameworkVersionPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"active"
			});
	private static final SCFrameworkVersion _nullSCFrameworkVersion = new SCFrameworkVersionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SCFrameworkVersion> toCacheModel() {
				return _nullSCFrameworkVersionCacheModel;
			}
		};

	private static final CacheModel<SCFrameworkVersion> _nullSCFrameworkVersionCacheModel =
		new CacheModel<SCFrameworkVersion>() {
			@Override
			public SCFrameworkVersion toEntityModel() {
				return _nullSCFrameworkVersion;
			}
		};
}