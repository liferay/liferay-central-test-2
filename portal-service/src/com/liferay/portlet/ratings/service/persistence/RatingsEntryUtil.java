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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.ratings.model.RatingsEntry;

import java.util.List;

/**
 * <a href="RatingsEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RatingsEntryPersistence
 * @see       RatingsEntryPersistenceImpl
 * @generated
 */
public class RatingsEntryUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static RatingsEntry remove(RatingsEntry ratingsEntry)
		throws SystemException {
		return getPersistence().remove(ratingsEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static RatingsEntry update(RatingsEntry ratingsEntry, boolean merge)
		throws SystemException {
		return getPersistence().update(ratingsEntry, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry) {
		getPersistence().cacheResult(ratingsEntry);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> ratingsEntries) {
		getPersistence().cacheResult(ratingsEntries);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry create(
		long entryId) {
		return getPersistence().create(entryId);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry remove(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry updateImpl(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(ratingsEntry, merge);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, start, end, obc);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByC_C_First(classNameId, classPK, obc);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByC_C_Last(classNameId, classPK, obc);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry[] findByC_C_PrevAndNext(
		long entryId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_PrevAndNext(entryId, classNameId, classPK, obc);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry findByU_C_C(
		long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByU_C_C(userId, classNameId, classPK);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByU_C_C(
		long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_C_C(userId, classNameId, classPK);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByU_C_C(
		long userId, long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_C_C(userId, classNameId, classPK, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeByU_C_C(long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		getPersistence().removeByU_C_C(userId, classNameId, classPK);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countByU_C_C(long userId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_C_C(userId, classNameId, classPK);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static RatingsEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (RatingsEntryPersistence)PortalBeanLocatorUtil.locate(RatingsEntryPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(RatingsEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static RatingsEntryPersistence _persistence;
}