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

import com.liferay.portal.model.Region;

/**
 * <a href="RegionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RegionPersistenceImpl
 * @see       RegionUtil
 * @generated
 */
public interface RegionPersistence extends BasePersistence<Region> {
	public void cacheResult(com.liferay.portal.model.Region region);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Region> regions);

	public com.liferay.portal.model.Region create(long regionId);

	public com.liferay.portal.model.Region remove(long regionId)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Region updateImpl(
		com.liferay.portal.model.Region region, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Region findByPrimaryKey(long regionId)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Region fetchByPrimaryKey(long regionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Region findByCountryId_First(
		long countryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Region findByCountryId_Last(
		long countryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Region[] findByCountryId_PrevAndNext(
		long regionId, long countryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Region findByActive_First(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Region findByActive_Last(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Region[] findByActive_PrevAndNext(
		long regionId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Region findByC_A_First(long countryId,
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Region findByC_A_Last(long countryId,
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Region[] findByC_A_PrevAndNext(
		long regionId, long countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCountryId(long countryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_A(long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCountryId(long countryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_A(long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}