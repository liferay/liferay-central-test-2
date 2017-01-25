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

package com.liferay.adaptive.media.image.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImage;
import com.liferay.adaptive.media.image.model.impl.AdaptiveMediaImageImpl;
import com.liferay.adaptive.media.image.model.impl.AdaptiveMediaImageModelImpl;
import com.liferay.adaptive.media.image.service.persistence.AdaptiveMediaImagePersistence;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
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
 * The persistence implementation for the adaptive media image service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AdaptiveMediaImagePersistence
 * @see com.liferay.adaptive.media.image.service.persistence.AdaptiveMediaImageUtil
 * @generated
 */
@ProviderType
public class AdaptiveMediaImagePersistenceImpl extends BasePersistenceImpl<AdaptiveMediaImage>
	implements AdaptiveMediaImagePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AdaptiveMediaImageUtil} to access the adaptive media image persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AdaptiveMediaImageImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			AdaptiveMediaImageModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the adaptive media images where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the adaptive media images where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @return the range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the adaptive media images where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByUuid(String uuid, int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the adaptive media images where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByUuid(String uuid, int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<AdaptiveMediaImage> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AdaptiveMediaImage adaptiveMediaImage : list) {
					if (!Objects.equals(uuid, adaptiveMediaImage.getUuid())) {
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

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AdaptiveMediaImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<AdaptiveMediaImage>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImage>)QueryUtil.list(q,
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
	 * Returns the first adaptive media image in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage findByUuid_First(String uuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByUuid_First(uuid,
				orderByComparator);

		if (adaptiveMediaImage != null) {
			return adaptiveMediaImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageException(msg.toString());
	}

	/**
	 * Returns the first adaptive media image in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByUuid_First(String uuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		List<AdaptiveMediaImage> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last adaptive media image in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage findByUuid_Last(String uuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByUuid_Last(uuid,
				orderByComparator);

		if (adaptiveMediaImage != null) {
			return adaptiveMediaImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageException(msg.toString());
	}

	/**
	 * Returns the last adaptive media image in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByUuid_Last(String uuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AdaptiveMediaImage> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the adaptive media images before and after the current adaptive media image in the ordered set where uuid = &#63;.
	 *
	 * @param adaptiveMediaImageId the primary key of the current adaptive media image
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImage[] findByUuid_PrevAndNext(
		long adaptiveMediaImageId, String uuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = findByPrimaryKey(adaptiveMediaImageId);

		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImage[] array = new AdaptiveMediaImageImpl[3];

			array[0] = getByUuid_PrevAndNext(session, adaptiveMediaImage, uuid,
					orderByComparator, true);

			array[1] = adaptiveMediaImage;

			array[2] = getByUuid_PrevAndNext(session, adaptiveMediaImage, uuid,
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

	protected AdaptiveMediaImage getByUuid_PrevAndNext(Session session,
		AdaptiveMediaImage adaptiveMediaImage, String uuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
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

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
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
			query.append(AdaptiveMediaImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(adaptiveMediaImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AdaptiveMediaImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the adaptive media images where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AdaptiveMediaImage adaptiveMediaImage : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(adaptiveMediaImage);
		}
	}

	/**
	 * Returns the number of adaptive media images where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching adaptive media images
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "adaptiveMediaImage.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "adaptiveMediaImage.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(adaptiveMediaImage.uuid IS NULL OR adaptiveMediaImage.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			AdaptiveMediaImageModelImpl.UUID_COLUMN_BITMASK |
			AdaptiveMediaImageModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the adaptive media image where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchAdaptiveMediaImageException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage findByUUID_G(String uuid, long groupId)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByUUID_G(uuid, groupId);

		if (adaptiveMediaImage == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchAdaptiveMediaImageException(msg.toString());
		}

		return adaptiveMediaImage;
	}

	/**
	 * Returns the adaptive media image where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the adaptive media image where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof AdaptiveMediaImage) {
			AdaptiveMediaImage adaptiveMediaImage = (AdaptiveMediaImage)result;

			if (!Objects.equals(uuid, adaptiveMediaImage.getUuid()) ||
					(groupId != adaptiveMediaImage.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<AdaptiveMediaImage> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					AdaptiveMediaImage adaptiveMediaImage = list.get(0);

					result = adaptiveMediaImage;

					cacheResult(adaptiveMediaImage);

					if ((adaptiveMediaImage.getUuid() == null) ||
							!adaptiveMediaImage.getUuid().equals(uuid) ||
							(adaptiveMediaImage.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, adaptiveMediaImage);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

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
			return (AdaptiveMediaImage)result;
		}
	}

	/**
	 * Removes the adaptive media image where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the adaptive media image that was removed
	 */
	@Override
	public AdaptiveMediaImage removeByUUID_G(String uuid, long groupId)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = findByUUID_G(uuid, groupId);

		return remove(adaptiveMediaImage);
	}

	/**
	 * Returns the number of adaptive media images where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching adaptive media images
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "adaptiveMediaImage.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "adaptiveMediaImage.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(adaptiveMediaImage.uuid IS NULL OR adaptiveMediaImage.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "adaptiveMediaImage.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			AdaptiveMediaImageModelImpl.UUID_COLUMN_BITMASK |
			AdaptiveMediaImageModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the adaptive media images where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the adaptive media images where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @return the range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the adaptive media images where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the adaptive media images where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<AdaptiveMediaImage> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AdaptiveMediaImage adaptiveMediaImage : list) {
					if (!Objects.equals(uuid, adaptiveMediaImage.getUuid()) ||
							(companyId != adaptiveMediaImage.getCompanyId())) {
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

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AdaptiveMediaImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<AdaptiveMediaImage>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImage>)QueryUtil.list(q,
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
	 * Returns the first adaptive media image in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (adaptiveMediaImage != null) {
			return adaptiveMediaImage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageException(msg.toString());
	}

	/**
	 * Returns the first adaptive media image in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		List<AdaptiveMediaImage> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last adaptive media image in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (adaptiveMediaImage != null) {
			return adaptiveMediaImage;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageException(msg.toString());
	}

	/**
	 * Returns the last adaptive media image in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AdaptiveMediaImage> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the adaptive media images before and after the current adaptive media image in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param adaptiveMediaImageId the primary key of the current adaptive media image
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImage[] findByUuid_C_PrevAndNext(
		long adaptiveMediaImageId, String uuid, long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = findByPrimaryKey(adaptiveMediaImageId);

		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImage[] array = new AdaptiveMediaImageImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, adaptiveMediaImage,
					uuid, companyId, orderByComparator, true);

			array[1] = adaptiveMediaImage;

			array[2] = getByUuid_C_PrevAndNext(session, adaptiveMediaImage,
					uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AdaptiveMediaImage getByUuid_C_PrevAndNext(Session session,
		AdaptiveMediaImage adaptiveMediaImage, String uuid, long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
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

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(AdaptiveMediaImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(adaptiveMediaImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AdaptiveMediaImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the adaptive media images where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AdaptiveMediaImage adaptiveMediaImage : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(adaptiveMediaImage);
		}
	}

	/**
	 * Returns the number of adaptive media images where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching adaptive media images
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "adaptiveMediaImage.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "adaptiveMediaImage.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(adaptiveMediaImage.uuid IS NULL OR adaptiveMediaImage.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "adaptiveMediaImage.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			AdaptiveMediaImageModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the adaptive media images where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the adaptive media images where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @return the range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the adaptive media images where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByGroupId(long groupId, int start,
		int end, OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the adaptive media images where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByGroupId(long groupId, int start,
		int end, OrderByComparator<AdaptiveMediaImage> orderByComparator,
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

		List<AdaptiveMediaImage> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AdaptiveMediaImage adaptiveMediaImage : list) {
					if ((groupId != adaptiveMediaImage.getGroupId())) {
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

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AdaptiveMediaImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<AdaptiveMediaImage>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImage>)QueryUtil.list(q,
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
	 * Returns the first adaptive media image in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage findByGroupId_First(long groupId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByGroupId_First(groupId,
				orderByComparator);

		if (adaptiveMediaImage != null) {
			return adaptiveMediaImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageException(msg.toString());
	}

	/**
	 * Returns the first adaptive media image in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByGroupId_First(long groupId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		List<AdaptiveMediaImage> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last adaptive media image in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage findByGroupId_Last(long groupId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (adaptiveMediaImage != null) {
			return adaptiveMediaImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageException(msg.toString());
	}

	/**
	 * Returns the last adaptive media image in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByGroupId_Last(long groupId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<AdaptiveMediaImage> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the adaptive media images before and after the current adaptive media image in the ordered set where groupId = &#63;.
	 *
	 * @param adaptiveMediaImageId the primary key of the current adaptive media image
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImage[] findByGroupId_PrevAndNext(
		long adaptiveMediaImageId, long groupId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = findByPrimaryKey(adaptiveMediaImageId);

		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImage[] array = new AdaptiveMediaImageImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, adaptiveMediaImage,
					groupId, orderByComparator, true);

			array[1] = adaptiveMediaImage;

			array[2] = getByGroupId_PrevAndNext(session, adaptiveMediaImage,
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

	protected AdaptiveMediaImage getByGroupId_PrevAndNext(Session session,
		AdaptiveMediaImage adaptiveMediaImage, long groupId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
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

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE);

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
			query.append(AdaptiveMediaImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(adaptiveMediaImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AdaptiveMediaImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the adaptive media images where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (AdaptiveMediaImage adaptiveMediaImage : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(adaptiveMediaImage);
		}
	}

	/**
	 * Returns the number of adaptive media images where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching adaptive media images
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGE_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "adaptiveMediaImage.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			AdaptiveMediaImageModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the adaptive media images where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the adaptive media images where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @return the range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the adaptive media images where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the adaptive media images where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<AdaptiveMediaImage> orderByComparator,
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

		List<AdaptiveMediaImage> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AdaptiveMediaImage adaptiveMediaImage : list) {
					if ((companyId != adaptiveMediaImage.getCompanyId())) {
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

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AdaptiveMediaImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<AdaptiveMediaImage>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImage>)QueryUtil.list(q,
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
	 * Returns the first adaptive media image in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage findByCompanyId_First(long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (adaptiveMediaImage != null) {
			return adaptiveMediaImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageException(msg.toString());
	}

	/**
	 * Returns the first adaptive media image in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByCompanyId_First(long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		List<AdaptiveMediaImage> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last adaptive media image in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage findByCompanyId_Last(long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (adaptiveMediaImage != null) {
			return adaptiveMediaImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageException(msg.toString());
	}

	/**
	 * Returns the last adaptive media image in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByCompanyId_Last(long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<AdaptiveMediaImage> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the adaptive media images before and after the current adaptive media image in the ordered set where companyId = &#63;.
	 *
	 * @param adaptiveMediaImageId the primary key of the current adaptive media image
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImage[] findByCompanyId_PrevAndNext(
		long adaptiveMediaImageId, long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = findByPrimaryKey(adaptiveMediaImageId);

		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImage[] array = new AdaptiveMediaImageImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, adaptiveMediaImage,
					companyId, orderByComparator, true);

			array[1] = adaptiveMediaImage;

			array[2] = getByCompanyId_PrevAndNext(session, adaptiveMediaImage,
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

	protected AdaptiveMediaImage getByCompanyId_PrevAndNext(Session session,
		AdaptiveMediaImage adaptiveMediaImage, long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
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

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE);

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
			query.append(AdaptiveMediaImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(adaptiveMediaImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AdaptiveMediaImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the adaptive media images where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (AdaptiveMediaImage adaptiveMediaImage : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(adaptiveMediaImage);
		}
	}

	/**
	 * Returns the number of adaptive media images where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching adaptive media images
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGE_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "adaptiveMediaImage.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CONFIGURATIONUUID =
		new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByConfigurationUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID =
		new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByConfigurationUuid", new String[] { String.class.getName() },
			AdaptiveMediaImageModelImpl.CONFIGURATIONUUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CONFIGURATIONUUID = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByConfigurationUuid", new String[] { String.class.getName() });

	/**
	 * Returns all the adaptive media images where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @return the matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByConfigurationUuid(
		String configurationUuid) {
		return findByConfigurationUuid(configurationUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the adaptive media images where configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @return the range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByConfigurationUuid(
		String configurationUuid, int start, int end) {
		return findByConfigurationUuid(configurationUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the adaptive media images where configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByConfigurationUuid(
		String configurationUuid, int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return findByConfigurationUuid(configurationUuid, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the adaptive media images where configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByConfigurationUuid(
		String configurationUuid, int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID;
			finderArgs = new Object[] { configurationUuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CONFIGURATIONUUID;
			finderArgs = new Object[] {
					configurationUuid,
					
					start, end, orderByComparator
				};
		}

		List<AdaptiveMediaImage> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AdaptiveMediaImage adaptiveMediaImage : list) {
					if (!Objects.equals(configurationUuid,
								adaptiveMediaImage.getConfigurationUuid())) {
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

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AdaptiveMediaImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
				}

				if (!pagination) {
					list = (List<AdaptiveMediaImage>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImage>)QueryUtil.list(q,
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
	 * Returns the first adaptive media image in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage findByConfigurationUuid_First(
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByConfigurationUuid_First(configurationUuid,
				orderByComparator);

		if (adaptiveMediaImage != null) {
			return adaptiveMediaImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("configurationUuid=");
		msg.append(configurationUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageException(msg.toString());
	}

	/**
	 * Returns the first adaptive media image in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByConfigurationUuid_First(
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		List<AdaptiveMediaImage> list = findByConfigurationUuid(configurationUuid,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last adaptive media image in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage findByConfigurationUuid_Last(
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByConfigurationUuid_Last(configurationUuid,
				orderByComparator);

		if (adaptiveMediaImage != null) {
			return adaptiveMediaImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("configurationUuid=");
		msg.append(configurationUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageException(msg.toString());
	}

	/**
	 * Returns the last adaptive media image in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByConfigurationUuid_Last(
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		int count = countByConfigurationUuid(configurationUuid);

		if (count == 0) {
			return null;
		}

		List<AdaptiveMediaImage> list = findByConfigurationUuid(configurationUuid,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the adaptive media images before and after the current adaptive media image in the ordered set where configurationUuid = &#63;.
	 *
	 * @param adaptiveMediaImageId the primary key of the current adaptive media image
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImage[] findByConfigurationUuid_PrevAndNext(
		long adaptiveMediaImageId, String configurationUuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = findByPrimaryKey(adaptiveMediaImageId);

		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImage[] array = new AdaptiveMediaImageImpl[3];

			array[0] = getByConfigurationUuid_PrevAndNext(session,
					adaptiveMediaImage, configurationUuid, orderByComparator,
					true);

			array[1] = adaptiveMediaImage;

			array[2] = getByConfigurationUuid_PrevAndNext(session,
					adaptiveMediaImage, configurationUuid, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AdaptiveMediaImage getByConfigurationUuid_PrevAndNext(
		Session session, AdaptiveMediaImage adaptiveMediaImage,
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
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

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE);

		boolean bindConfigurationUuid = false;

		if (configurationUuid == null) {
			query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_1);
		}
		else if (configurationUuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_3);
		}
		else {
			bindConfigurationUuid = true;

			query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_2);
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
			query.append(AdaptiveMediaImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindConfigurationUuid) {
			qPos.add(configurationUuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(adaptiveMediaImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AdaptiveMediaImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the adaptive media images where configurationUuid = &#63; from the database.
	 *
	 * @param configurationUuid the configuration uuid
	 */
	@Override
	public void removeByConfigurationUuid(String configurationUuid) {
		for (AdaptiveMediaImage adaptiveMediaImage : findByConfigurationUuid(
				configurationUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(adaptiveMediaImage);
		}
	}

	/**
	 * Returns the number of adaptive media images where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @return the number of matching adaptive media images
	 */
	@Override
	public int countByConfigurationUuid(String configurationUuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CONFIGURATIONUUID;

		Object[] finderArgs = new Object[] { configurationUuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGE_WHERE);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
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

	private static final String _FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_1 =
		"adaptiveMediaImage.configurationUuid IS NULL";
	private static final String _FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_2 =
		"adaptiveMediaImage.configurationUuid = ?";
	private static final String _FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_3 =
		"(adaptiveMediaImage.configurationUuid IS NULL OR adaptiveMediaImage.configurationUuid = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEVERSIONID =
		new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileVersionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID =
		new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileVersionId",
			new String[] { Long.class.getName() },
			AdaptiveMediaImageModelImpl.FILEVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEVERSIONID = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileVersionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the adaptive media images where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByFileVersionId(long fileVersionId) {
		return findByFileVersionId(fileVersionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the adaptive media images where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @return the range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByFileVersionId(long fileVersionId,
		int start, int end) {
		return findByFileVersionId(fileVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the adaptive media images where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByFileVersionId(long fileVersionId,
		int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return findByFileVersionId(fileVersionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the adaptive media images where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findByFileVersionId(long fileVersionId,
		int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID;
			finderArgs = new Object[] { fileVersionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEVERSIONID;
			finderArgs = new Object[] {
					fileVersionId,
					
					start, end, orderByComparator
				};
		}

		List<AdaptiveMediaImage> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImage>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AdaptiveMediaImage adaptiveMediaImage : list) {
					if ((fileVersionId != adaptiveMediaImage.getFileVersionId())) {
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

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE);

			query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AdaptiveMediaImageModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileVersionId);

				if (!pagination) {
					list = (List<AdaptiveMediaImage>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImage>)QueryUtil.list(q,
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
	 * Returns the first adaptive media image in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage findByFileVersionId_First(long fileVersionId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByFileVersionId_First(fileVersionId,
				orderByComparator);

		if (adaptiveMediaImage != null) {
			return adaptiveMediaImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileVersionId=");
		msg.append(fileVersionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageException(msg.toString());
	}

	/**
	 * Returns the first adaptive media image in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByFileVersionId_First(long fileVersionId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		List<AdaptiveMediaImage> list = findByFileVersionId(fileVersionId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last adaptive media image in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage findByFileVersionId_Last(long fileVersionId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByFileVersionId_Last(fileVersionId,
				orderByComparator);

		if (adaptiveMediaImage != null) {
			return adaptiveMediaImage;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileVersionId=");
		msg.append(fileVersionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageException(msg.toString());
	}

	/**
	 * Returns the last adaptive media image in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByFileVersionId_Last(long fileVersionId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		int count = countByFileVersionId(fileVersionId);

		if (count == 0) {
			return null;
		}

		List<AdaptiveMediaImage> list = findByFileVersionId(fileVersionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the adaptive media images before and after the current adaptive media image in the ordered set where fileVersionId = &#63;.
	 *
	 * @param adaptiveMediaImageId the primary key of the current adaptive media image
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImage[] findByFileVersionId_PrevAndNext(
		long adaptiveMediaImageId, long fileVersionId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = findByPrimaryKey(adaptiveMediaImageId);

		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImage[] array = new AdaptiveMediaImageImpl[3];

			array[0] = getByFileVersionId_PrevAndNext(session,
					adaptiveMediaImage, fileVersionId, orderByComparator, true);

			array[1] = adaptiveMediaImage;

			array[2] = getByFileVersionId_PrevAndNext(session,
					adaptiveMediaImage, fileVersionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AdaptiveMediaImage getByFileVersionId_PrevAndNext(
		Session session, AdaptiveMediaImage adaptiveMediaImage,
		long fileVersionId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
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

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE);

		query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

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
			query.append(AdaptiveMediaImageModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileVersionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(adaptiveMediaImage);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AdaptiveMediaImage> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the adaptive media images where fileVersionId = &#63; from the database.
	 *
	 * @param fileVersionId the file version ID
	 */
	@Override
	public void removeByFileVersionId(long fileVersionId) {
		for (AdaptiveMediaImage adaptiveMediaImage : findByFileVersionId(
				fileVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(adaptiveMediaImage);
		}
	}

	/**
	 * Returns the number of adaptive media images where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the number of matching adaptive media images
	 */
	@Override
	public int countByFileVersionId(long fileVersionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FILEVERSIONID;

		Object[] finderArgs = new Object[] { fileVersionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGE_WHERE);

			query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileVersionId);

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

	private static final String _FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2 = "adaptiveMediaImage.fileVersionId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_F = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_F",
			new String[] { String.class.getName(), Long.class.getName() },
			AdaptiveMediaImageModelImpl.CONFIGURATIONUUID_COLUMN_BITMASK |
			AdaptiveMediaImageModelImpl.FILEVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_F = new FinderPath(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_F",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the adaptive media image where configurationUuid = &#63; and fileVersionId = &#63; or throws a {@link NoSuchAdaptiveMediaImageException} if it could not be found.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the matching adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage findByC_F(String configurationUuid,
		long fileVersionId) throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByC_F(configurationUuid,
				fileVersionId);

		if (adaptiveMediaImage == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("configurationUuid=");
			msg.append(configurationUuid);

			msg.append(", fileVersionId=");
			msg.append(fileVersionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchAdaptiveMediaImageException(msg.toString());
		}

		return adaptiveMediaImage;
	}

	/**
	 * Returns the adaptive media image where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByC_F(String configurationUuid,
		long fileVersionId) {
		return fetchByC_F(configurationUuid, fileVersionId, true);
	}

	/**
	 * Returns the adaptive media image where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByC_F(String configurationUuid,
		long fileVersionId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { configurationUuid, fileVersionId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_F,
					finderArgs, this);
		}

		if (result instanceof AdaptiveMediaImage) {
			AdaptiveMediaImage adaptiveMediaImage = (AdaptiveMediaImage)result;

			if (!Objects.equals(configurationUuid,
						adaptiveMediaImage.getConfigurationUuid()) ||
					(fileVersionId != adaptiveMediaImage.getFileVersionId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_2);
			}

			query.append(_FINDER_COLUMN_C_F_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
				}

				qPos.add(fileVersionId);

				List<AdaptiveMediaImage> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_F, finderArgs,
						list);
				}
				else {
					AdaptiveMediaImage adaptiveMediaImage = list.get(0);

					result = adaptiveMediaImage;

					cacheResult(adaptiveMediaImage);

					if ((adaptiveMediaImage.getConfigurationUuid() == null) ||
							!adaptiveMediaImage.getConfigurationUuid()
												   .equals(configurationUuid) ||
							(adaptiveMediaImage.getFileVersionId() != fileVersionId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_F,
							finderArgs, adaptiveMediaImage);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, finderArgs);

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
			return (AdaptiveMediaImage)result;
		}
	}

	/**
	 * Removes the adaptive media image where configurationUuid = &#63; and fileVersionId = &#63; from the database.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the adaptive media image that was removed
	 */
	@Override
	public AdaptiveMediaImage removeByC_F(String configurationUuid,
		long fileVersionId) throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = findByC_F(configurationUuid,
				fileVersionId);

		return remove(adaptiveMediaImage);
	}

	/**
	 * Returns the number of adaptive media images where configurationUuid = &#63; and fileVersionId = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the number of matching adaptive media images
	 */
	@Override
	public int countByC_F(String configurationUuid, long fileVersionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_F;

		Object[] finderArgs = new Object[] { configurationUuid, fileVersionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGE_WHERE);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_2);
			}

			query.append(_FINDER_COLUMN_C_F_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
				}

				qPos.add(fileVersionId);

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

	private static final String _FINDER_COLUMN_C_F_CONFIGURATIONUUID_1 = "adaptiveMediaImage.configurationUuid IS NULL AND ";
	private static final String _FINDER_COLUMN_C_F_CONFIGURATIONUUID_2 = "adaptiveMediaImage.configurationUuid = ? AND ";
	private static final String _FINDER_COLUMN_C_F_CONFIGURATIONUUID_3 = "(adaptiveMediaImage.configurationUuid IS NULL OR adaptiveMediaImage.configurationUuid = '') AND ";
	private static final String _FINDER_COLUMN_C_F_FILEVERSIONID_2 = "adaptiveMediaImage.fileVersionId = ?";

	public AdaptiveMediaImagePersistenceImpl() {
		setModelClass(AdaptiveMediaImage.class);
	}

	/**
	 * Caches the adaptive media image in the entity cache if it is enabled.
	 *
	 * @param adaptiveMediaImage the adaptive media image
	 */
	@Override
	public void cacheResult(AdaptiveMediaImage adaptiveMediaImage) {
		entityCache.putResult(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class, adaptiveMediaImage.getPrimaryKey(),
			adaptiveMediaImage);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				adaptiveMediaImage.getUuid(), adaptiveMediaImage.getGroupId()
			}, adaptiveMediaImage);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_F,
			new Object[] {
				adaptiveMediaImage.getConfigurationUuid(),
				adaptiveMediaImage.getFileVersionId()
			}, adaptiveMediaImage);

		adaptiveMediaImage.resetOriginalValues();
	}

	/**
	 * Caches the adaptive media images in the entity cache if it is enabled.
	 *
	 * @param adaptiveMediaImages the adaptive media images
	 */
	@Override
	public void cacheResult(List<AdaptiveMediaImage> adaptiveMediaImages) {
		for (AdaptiveMediaImage adaptiveMediaImage : adaptiveMediaImages) {
			if (entityCache.getResult(
						AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
						AdaptiveMediaImageImpl.class,
						adaptiveMediaImage.getPrimaryKey()) == null) {
				cacheResult(adaptiveMediaImage);
			}
			else {
				adaptiveMediaImage.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all adaptive media images.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AdaptiveMediaImageImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the adaptive media image.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AdaptiveMediaImage adaptiveMediaImage) {
		entityCache.removeResult(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class, adaptiveMediaImage.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AdaptiveMediaImageModelImpl)adaptiveMediaImage,
			true);
	}

	@Override
	public void clearCache(List<AdaptiveMediaImage> adaptiveMediaImages) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AdaptiveMediaImage adaptiveMediaImage : adaptiveMediaImages) {
			entityCache.removeResult(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
				AdaptiveMediaImageImpl.class, adaptiveMediaImage.getPrimaryKey());

			clearUniqueFindersCache((AdaptiveMediaImageModelImpl)adaptiveMediaImage,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		AdaptiveMediaImageModelImpl adaptiveMediaImageModelImpl) {
		Object[] args = new Object[] {
				adaptiveMediaImageModelImpl.getUuid(),
				adaptiveMediaImageModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			adaptiveMediaImageModelImpl, false);

		args = new Object[] {
				adaptiveMediaImageModelImpl.getConfigurationUuid(),
				adaptiveMediaImageModelImpl.getFileVersionId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_F, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_F, args,
			adaptiveMediaImageModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AdaptiveMediaImageModelImpl adaptiveMediaImageModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					adaptiveMediaImageModelImpl.getUuid(),
					adaptiveMediaImageModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((adaptiveMediaImageModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					adaptiveMediaImageModelImpl.getOriginalUuid(),
					adaptiveMediaImageModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					adaptiveMediaImageModelImpl.getConfigurationUuid(),
					adaptiveMediaImageModelImpl.getFileVersionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, args);
		}

		if ((adaptiveMediaImageModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_F.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					adaptiveMediaImageModelImpl.getOriginalConfigurationUuid(),
					adaptiveMediaImageModelImpl.getOriginalFileVersionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, args);
		}
	}

	/**
	 * Creates a new adaptive media image with the primary key. Does not add the adaptive media image to the database.
	 *
	 * @param adaptiveMediaImageId the primary key for the new adaptive media image
	 * @return the new adaptive media image
	 */
	@Override
	public AdaptiveMediaImage create(long adaptiveMediaImageId) {
		AdaptiveMediaImage adaptiveMediaImage = new AdaptiveMediaImageImpl();

		adaptiveMediaImage.setNew(true);
		adaptiveMediaImage.setPrimaryKey(adaptiveMediaImageId);

		String uuid = PortalUUIDUtil.generate();

		adaptiveMediaImage.setUuid(uuid);

		adaptiveMediaImage.setCompanyId(companyProvider.getCompanyId());

		return adaptiveMediaImage;
	}

	/**
	 * Removes the adaptive media image with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param adaptiveMediaImageId the primary key of the adaptive media image
	 * @return the adaptive media image that was removed
	 * @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImage remove(long adaptiveMediaImageId)
		throws NoSuchAdaptiveMediaImageException {
		return remove((Serializable)adaptiveMediaImageId);
	}

	/**
	 * Removes the adaptive media image with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the adaptive media image
	 * @return the adaptive media image that was removed
	 * @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImage remove(Serializable primaryKey)
		throws NoSuchAdaptiveMediaImageException {
		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImage adaptiveMediaImage = (AdaptiveMediaImage)session.get(AdaptiveMediaImageImpl.class,
					primaryKey);

			if (adaptiveMediaImage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAdaptiveMediaImageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(adaptiveMediaImage);
		}
		catch (NoSuchAdaptiveMediaImageException nsee) {
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
	protected AdaptiveMediaImage removeImpl(
		AdaptiveMediaImage adaptiveMediaImage) {
		adaptiveMediaImage = toUnwrappedModel(adaptiveMediaImage);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(adaptiveMediaImage)) {
				adaptiveMediaImage = (AdaptiveMediaImage)session.get(AdaptiveMediaImageImpl.class,
						adaptiveMediaImage.getPrimaryKeyObj());
			}

			if (adaptiveMediaImage != null) {
				session.delete(adaptiveMediaImage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (adaptiveMediaImage != null) {
			clearCache(adaptiveMediaImage);
		}

		return adaptiveMediaImage;
	}

	@Override
	public AdaptiveMediaImage updateImpl(AdaptiveMediaImage adaptiveMediaImage) {
		adaptiveMediaImage = toUnwrappedModel(adaptiveMediaImage);

		boolean isNew = adaptiveMediaImage.isNew();

		AdaptiveMediaImageModelImpl adaptiveMediaImageModelImpl = (AdaptiveMediaImageModelImpl)adaptiveMediaImage;

		if (Validator.isNull(adaptiveMediaImage.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			adaptiveMediaImage.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (adaptiveMediaImage.isNew()) {
				session.save(adaptiveMediaImage);

				adaptiveMediaImage.setNew(false);
			}
			else {
				adaptiveMediaImage = (AdaptiveMediaImage)session.merge(adaptiveMediaImage);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AdaptiveMediaImageModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((adaptiveMediaImageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						adaptiveMediaImageModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { adaptiveMediaImageModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((adaptiveMediaImageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						adaptiveMediaImageModelImpl.getOriginalUuid(),
						adaptiveMediaImageModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						adaptiveMediaImageModelImpl.getUuid(),
						adaptiveMediaImageModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((adaptiveMediaImageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						adaptiveMediaImageModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { adaptiveMediaImageModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((adaptiveMediaImageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						adaptiveMediaImageModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { adaptiveMediaImageModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((adaptiveMediaImageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						adaptiveMediaImageModelImpl.getOriginalConfigurationUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CONFIGURATIONUUID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID,
					args);

				args = new Object[] {
						adaptiveMediaImageModelImpl.getConfigurationUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CONFIGURATIONUUID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID,
					args);
			}

			if ((adaptiveMediaImageModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						adaptiveMediaImageModelImpl.getOriginalFileVersionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
					args);

				args = new Object[] {
						adaptiveMediaImageModelImpl.getFileVersionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
					args);
			}
		}

		entityCache.putResult(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageImpl.class, adaptiveMediaImage.getPrimaryKey(),
			adaptiveMediaImage, false);

		clearUniqueFindersCache(adaptiveMediaImageModelImpl, false);
		cacheUniqueFindersCache(adaptiveMediaImageModelImpl);

		adaptiveMediaImage.resetOriginalValues();

		return adaptiveMediaImage;
	}

	protected AdaptiveMediaImage toUnwrappedModel(
		AdaptiveMediaImage adaptiveMediaImage) {
		if (adaptiveMediaImage instanceof AdaptiveMediaImageImpl) {
			return adaptiveMediaImage;
		}

		AdaptiveMediaImageImpl adaptiveMediaImageImpl = new AdaptiveMediaImageImpl();

		adaptiveMediaImageImpl.setNew(adaptiveMediaImage.isNew());
		adaptiveMediaImageImpl.setPrimaryKey(adaptiveMediaImage.getPrimaryKey());

		adaptiveMediaImageImpl.setUuid(adaptiveMediaImage.getUuid());
		adaptiveMediaImageImpl.setAdaptiveMediaImageId(adaptiveMediaImage.getAdaptiveMediaImageId());
		adaptiveMediaImageImpl.setGroupId(adaptiveMediaImage.getGroupId());
		adaptiveMediaImageImpl.setCompanyId(adaptiveMediaImage.getCompanyId());
		adaptiveMediaImageImpl.setCreateDate(adaptiveMediaImage.getCreateDate());
		adaptiveMediaImageImpl.setConfigurationUuid(adaptiveMediaImage.getConfigurationUuid());
		adaptiveMediaImageImpl.setFileVersionId(adaptiveMediaImage.getFileVersionId());
		adaptiveMediaImageImpl.setMimeType(adaptiveMediaImage.getMimeType());
		adaptiveMediaImageImpl.setHeight(adaptiveMediaImage.getHeight());
		adaptiveMediaImageImpl.setWidth(adaptiveMediaImage.getWidth());
		adaptiveMediaImageImpl.setSize(adaptiveMediaImage.getSize());

		return adaptiveMediaImageImpl;
	}

	/**
	 * Returns the adaptive media image with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the adaptive media image
	 * @return the adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImage findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAdaptiveMediaImageException {
		AdaptiveMediaImage adaptiveMediaImage = fetchByPrimaryKey(primaryKey);

		if (adaptiveMediaImage == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAdaptiveMediaImageException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return adaptiveMediaImage;
	}

	/**
	 * Returns the adaptive media image with the primary key or throws a {@link NoSuchAdaptiveMediaImageException} if it could not be found.
	 *
	 * @param adaptiveMediaImageId the primary key of the adaptive media image
	 * @return the adaptive media image
	 * @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImage findByPrimaryKey(long adaptiveMediaImageId)
		throws NoSuchAdaptiveMediaImageException {
		return findByPrimaryKey((Serializable)adaptiveMediaImageId);
	}

	/**
	 * Returns the adaptive media image with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the adaptive media image
	 * @return the adaptive media image, or <code>null</code> if a adaptive media image with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
				AdaptiveMediaImageImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AdaptiveMediaImage adaptiveMediaImage = (AdaptiveMediaImage)serializable;

		if (adaptiveMediaImage == null) {
			Session session = null;

			try {
				session = openSession();

				adaptiveMediaImage = (AdaptiveMediaImage)session.get(AdaptiveMediaImageImpl.class,
						primaryKey);

				if (adaptiveMediaImage != null) {
					cacheResult(adaptiveMediaImage);
				}
				else {
					entityCache.putResult(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
						AdaptiveMediaImageImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
					AdaptiveMediaImageImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return adaptiveMediaImage;
	}

	/**
	 * Returns the adaptive media image with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param adaptiveMediaImageId the primary key of the adaptive media image
	 * @return the adaptive media image, or <code>null</code> if a adaptive media image with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImage fetchByPrimaryKey(long adaptiveMediaImageId) {
		return fetchByPrimaryKey((Serializable)adaptiveMediaImageId);
	}

	@Override
	public Map<Serializable, AdaptiveMediaImage> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AdaptiveMediaImage> map = new HashMap<Serializable, AdaptiveMediaImage>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AdaptiveMediaImage adaptiveMediaImage = fetchByPrimaryKey(primaryKey);

			if (adaptiveMediaImage != null) {
				map.put(primaryKey, adaptiveMediaImage);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
					AdaptiveMediaImageImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AdaptiveMediaImage)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE_PKS_IN);

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

			for (AdaptiveMediaImage adaptiveMediaImage : (List<AdaptiveMediaImage>)q.list()) {
				map.put(adaptiveMediaImage.getPrimaryKeyObj(),
					adaptiveMediaImage);

				cacheResult(adaptiveMediaImage);

				uncachedPrimaryKeys.remove(adaptiveMediaImage.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AdaptiveMediaImageModelImpl.ENTITY_CACHE_ENABLED,
					AdaptiveMediaImageImpl.class, primaryKey, nullModel);
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
	 * Returns all the adaptive media images.
	 *
	 * @return the adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the adaptive media images.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @return the range of adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the adaptive media images.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findAll(int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the adaptive media images.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of adaptive media images
	 * @param end the upper bound of the range of adaptive media images (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of adaptive media images
	 */
	@Override
	public List<AdaptiveMediaImage> findAll(int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
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

		List<AdaptiveMediaImage> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImage>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ADAPTIVEMEDIAIMAGE;

				if (pagination) {
					sql = sql.concat(AdaptiveMediaImageModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AdaptiveMediaImage>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImage>)QueryUtil.list(q,
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
	 * Removes all the adaptive media images from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AdaptiveMediaImage adaptiveMediaImage : findAll()) {
			remove(adaptiveMediaImage);
		}
	}

	/**
	 * Returns the number of adaptive media images.
	 *
	 * @return the number of adaptive media images
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ADAPTIVEMEDIAIMAGE);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AdaptiveMediaImageModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the adaptive media image persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AdaptiveMediaImageImpl.class.getName());
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
	private static final String _SQL_SELECT_ADAPTIVEMEDIAIMAGE = "SELECT adaptiveMediaImage FROM AdaptiveMediaImage adaptiveMediaImage";
	private static final String _SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE_PKS_IN = "SELECT adaptiveMediaImage FROM AdaptiveMediaImage adaptiveMediaImage WHERE adaptiveMediaImageId IN (";
	private static final String _SQL_SELECT_ADAPTIVEMEDIAIMAGE_WHERE = "SELECT adaptiveMediaImage FROM AdaptiveMediaImage adaptiveMediaImage WHERE ";
	private static final String _SQL_COUNT_ADAPTIVEMEDIAIMAGE = "SELECT COUNT(adaptiveMediaImage) FROM AdaptiveMediaImage adaptiveMediaImage";
	private static final String _SQL_COUNT_ADAPTIVEMEDIAIMAGE_WHERE = "SELECT COUNT(adaptiveMediaImage) FROM AdaptiveMediaImage adaptiveMediaImage WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "adaptiveMediaImage.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AdaptiveMediaImage exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AdaptiveMediaImage exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AdaptiveMediaImagePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "size"
			});
}