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

package com.liferay.counter.service.persistence;

import com.liferay.counter.model.Counter;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

/**
 * <a href="CounterUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CounterPersistence
 * @see       CounterPersistenceImpl
 * @generated
 */
public class CounterUtil {
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
	public static Counter remove(Counter counter) throws SystemException {
		return getPersistence().remove(counter);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Counter update(Counter counter, boolean merge)
		throws SystemException {
		return getPersistence().update(counter, merge);
	}

	public static void cacheResult(com.liferay.counter.model.Counter counter) {
		getPersistence().cacheResult(counter);
	}

	public static void cacheResult(
		java.util.List<com.liferay.counter.model.Counter> counters) {
		getPersistence().cacheResult(counters);
	}

	public static com.liferay.counter.model.Counter create(
		java.lang.String name) {
		return getPersistence().create(name);
	}

	public static com.liferay.counter.model.Counter remove(
		java.lang.String name)
		throws com.liferay.counter.NoSuchCounterException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(name);
	}

	public static com.liferay.counter.model.Counter updateImpl(
		com.liferay.counter.model.Counter counter, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(counter, merge);
	}

	public static com.liferay.counter.model.Counter findByPrimaryKey(
		java.lang.String name)
		throws com.liferay.counter.NoSuchCounterException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(name);
	}

	public static com.liferay.counter.model.Counter fetchByPrimaryKey(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(name);
	}

	public static java.util.List<com.liferay.counter.model.Counter> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.counter.model.Counter> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.counter.model.Counter> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static CounterPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CounterPersistence)PortalBeanLocatorUtil.locate(CounterPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(CounterPersistence persistence) {
		_persistence = persistence;
	}

	private static CounterPersistence _persistence;
}