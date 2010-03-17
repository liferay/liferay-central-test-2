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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Country;

import java.util.List;

/**
 * <a href="CountryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CountryPersistence
 * @see       CountryPersistenceImpl
 * @generated
 */
public class CountryUtil {
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
	public static Country remove(Country country) throws SystemException {
		return getPersistence().remove(country);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Country update(Country country, boolean merge)
		throws SystemException {
		return getPersistence().update(country, merge);
	}

	public static void cacheResult(com.liferay.portal.model.Country country) {
		getPersistence().cacheResult(country);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Country> countries) {
		getPersistence().cacheResult(countries);
	}

	public static com.liferay.portal.model.Country create(long countryId) {
		return getPersistence().create(countryId);
	}

	public static com.liferay.portal.model.Country remove(long countryId)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(countryId);
	}

	public static com.liferay.portal.model.Country updateImpl(
		com.liferay.portal.model.Country country, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(country, merge);
	}

	public static com.liferay.portal.model.Country findByPrimaryKey(
		long countryId)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(countryId);
	}

	public static com.liferay.portal.model.Country fetchByPrimaryKey(
		long countryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(countryId);
	}

	public static com.liferay.portal.model.Country findByName(
		java.lang.String name)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByName(name);
	}

	public static com.liferay.portal.model.Country fetchByName(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByName(name);
	}

	public static com.liferay.portal.model.Country fetchByName(
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByName(name, retrieveFromCache);
	}

	public static com.liferay.portal.model.Country findByA2(java.lang.String a2)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByA2(a2);
	}

	public static com.liferay.portal.model.Country fetchByA2(
		java.lang.String a2)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByA2(a2);
	}

	public static com.liferay.portal.model.Country fetchByA2(
		java.lang.String a2, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByA2(a2, retrieveFromCache);
	}

	public static com.liferay.portal.model.Country findByA3(java.lang.String a3)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByA3(a3);
	}

	public static com.liferay.portal.model.Country fetchByA3(
		java.lang.String a3)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByA3(a3);
	}

	public static com.liferay.portal.model.Country fetchByA3(
		java.lang.String a3, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByA3(a3, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActive(active);
	}

	public static java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActive(active, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByActive(active, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.Country findByActive_First(
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActive_First(active, orderByComparator);
	}

	public static com.liferay.portal.model.Country findByActive_Last(
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActive_Last(active, orderByComparator);
	}

	public static com.liferay.portal.model.Country[] findByActive_PrevAndNext(
		long countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByActive_PrevAndNext(countryId, active,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portal.model.Country> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Country> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Country> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByName(java.lang.String name)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByName(name);
	}

	public static void removeByA2(java.lang.String a2)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByA2(a2);
	}

	public static void removeByA3(java.lang.String a3)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByA3(a3);
	}

	public static void removeByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByActive(active);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByName(name);
	}

	public static int countByA2(java.lang.String a2)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByA2(a2);
	}

	public static int countByA3(java.lang.String a3)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByA3(a3);
	}

	public static int countByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByActive(active);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static CountryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CountryPersistence)PortalBeanLocatorUtil.locate(CountryPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(CountryPersistence persistence) {
		_persistence = persistence;
	}

	private static CountryPersistence _persistence;
}