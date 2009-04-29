/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

/**
 * <a href="RegionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RegionUtil {
	public static void cacheResult(com.liferay.portal.model.Region region) {
		getPersistence().cacheResult(region);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Region> regions) {
		getPersistence().cacheResult(regions);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portal.model.Region create(long regionId) {
		return getPersistence().create(regionId);
	}

	public static com.liferay.portal.model.Region remove(long regionId)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(regionId);
	}

	public static com.liferay.portal.model.Region remove(
		com.liferay.portal.model.Region region)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(region);
	}

	/**
	 * @deprecated Use <code>update(Region region, boolean merge)</code>.
	 */
	public static com.liferay.portal.model.Region update(
		com.liferay.portal.model.Region region)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(region);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        region the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when region is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portal.model.Region update(
		com.liferay.portal.model.Region region, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(region, merge);
	}

	public static com.liferay.portal.model.Region updateImpl(
		com.liferay.portal.model.Region region, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(region, merge);
	}

	public static com.liferay.portal.model.Region findByPrimaryKey(
		long regionId)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(regionId);
	}

	public static com.liferay.portal.model.Region fetchByPrimaryKey(
		long regionId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(regionId);
	}

	public static java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCountryId(countryId);
	}

	public static java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCountryId(countryId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Region> findByCountryId(
		long countryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCountryId(countryId, start, end, obc);
	}

	public static com.liferay.portal.model.Region findByCountryId_First(
		long countryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCountryId_First(countryId, obc);
	}

	public static com.liferay.portal.model.Region findByCountryId_Last(
		long countryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCountryId_Last(countryId, obc);
	}

	public static com.liferay.portal.model.Region[] findByCountryId_PrevAndNext(
		long regionId, long countryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByCountryId_PrevAndNext(regionId, countryId, obc);
	}

	public static java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active) throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active);
	}

	public static java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Region> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active, start, end, obc);
	}

	public static com.liferay.portal.model.Region findByActive_First(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByActive_First(active, obc);
	}

	public static com.liferay.portal.model.Region findByActive_Last(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByActive_Last(active, obc);
	}

	public static com.liferay.portal.model.Region[] findByActive_PrevAndNext(
		long regionId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByActive_PrevAndNext(regionId, active, obc);
	}

	public static java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(countryId, active);
	}

	public static java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(countryId, active, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Region> findByC_A(
		long countryId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(countryId, active, start, end, obc);
	}

	public static com.liferay.portal.model.Region findByC_A_First(
		long countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_First(countryId, active, obc);
	}

	public static com.liferay.portal.model.Region findByC_A_Last(
		long countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_Last(countryId, active, obc);
	}

	public static com.liferay.portal.model.Region[] findByC_A_PrevAndNext(
		long regionId, long countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchRegionException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_A_PrevAndNext(regionId, countryId, active, obc);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Region> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Region> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Region> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCountryId(long countryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCountryId(countryId);
	}

	public static void removeByActive(boolean active)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByActive(active);
	}

	public static void removeByC_A(long countryId, boolean active)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_A(countryId, active);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCountryId(long countryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCountryId(countryId);
	}

	public static int countByActive(boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByActive(active);
	}

	public static int countByC_A(long countryId, boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_A(countryId, active);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static RegionPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(RegionPersistence persistence) {
		_persistence = persistence;
	}

	private static RegionPersistence _persistence;
}