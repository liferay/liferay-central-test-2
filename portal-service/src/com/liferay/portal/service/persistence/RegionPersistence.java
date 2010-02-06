/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Region updateImpl(
		com.liferay.portal.model.Region region, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Region findByPrimaryKey(long regionId)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Region fetchByPrimaryKey(long regionId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Region findByCountryId_First(
		long countryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Region findByCountryId_Last(
		long countryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Region[] findByCountryId_PrevAndNext(
		long regionId, long countryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Region findByActive_First(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Region findByActive_Last(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Region[] findByActive_PrevAndNext(
		long regionId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Region findByC_A_First(long countryId,
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Region findByC_A_Last(long countryId,
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Region[] findByC_A_PrevAndNext(
		long regionId, long countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findAll(int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Region> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByCountryId(long countryId)
		throws com.liferay.portal.SystemException;

	public void removeByActive(boolean active)
		throws com.liferay.portal.SystemException;

	public void removeByC_A(long countryId, boolean active)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByCountryId(long countryId)
		throws com.liferay.portal.SystemException;

	public int countByActive(boolean active)
		throws com.liferay.portal.SystemException;

	public int countByC_A(long countryId, boolean active)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}