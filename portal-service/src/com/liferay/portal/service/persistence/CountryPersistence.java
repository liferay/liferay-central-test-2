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

import com.liferay.portal.model.Country;

/**
 * <a href="CountryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CountryPersistenceImpl
 * @see       CountryUtil
 * @generated
 */
public interface CountryPersistence extends BasePersistence<Country> {
	public void cacheResult(com.liferay.portal.model.Country country);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Country> countries);

	public com.liferay.portal.model.Country create(long countryId);

	public com.liferay.portal.model.Country remove(long countryId)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country updateImpl(
		com.liferay.portal.model.Country country, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country findByPrimaryKey(long countryId)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country fetchByPrimaryKey(long countryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country findByName(java.lang.String name)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country fetchByName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country fetchByName(java.lang.String name,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country findByA2(java.lang.String a2)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country fetchByA2(java.lang.String a2)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country fetchByA2(java.lang.String a2,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country findByA3(java.lang.String a3)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country fetchByA3(java.lang.String a3)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country fetchByA3(java.lang.String a3,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country findByActive_First(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country findByActive_Last(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country[] findByActive_PrevAndNext(
		long countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Country> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Country> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Country> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByName(java.lang.String name)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByA2(java.lang.String a2)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByA3(java.lang.String a3)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByA2(java.lang.String a2)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByA3(java.lang.String a3)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}