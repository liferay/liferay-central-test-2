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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.asset.model.AssetTagStats;

import java.util.List;

/**
 * <a href="AssetTagStatsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagStatsPersistence
 * @see       AssetTagStatsPersistenceImpl
 * @generated
 */
public class AssetTagStatsUtil {
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
	public static AssetTagStats remove(AssetTagStats assetTagStats)
		throws SystemException {
		return getPersistence().remove(assetTagStats);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static AssetTagStats update(AssetTagStats assetTagStats,
		boolean merge) throws SystemException {
		return getPersistence().update(assetTagStats, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats) {
		getPersistence().cacheResult(assetTagStats);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetTagStats> assetTagStatses) {
		getPersistence().cacheResult(assetTagStatses);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats create(
		long tagStatsId) {
		return getPersistence().create(tagStatsId);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats remove(
		long tagStatsId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().remove(tagStatsId);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats updateImpl(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(assetTagStats, merge);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats findByPrimaryKey(
		long tagStatsId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByPrimaryKey(tagStatsId);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats fetchByPrimaryKey(
		long tagStatsId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(tagStatsId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByTagId(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTagId(tagId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByTagId(
		long tagId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTagId(tagId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByTagId(
		long tagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTagId(tagId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats findByTagId_First(
		long tagId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByTagId_First(tagId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats findByTagId_Last(
		long tagId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByTagId_Last(tagId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats[] findByTagId_PrevAndNext(
		long tagStatsId, long tagId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByTagId_PrevAndNext(tagStatsId, tagId, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByClassNameId(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByClassNameId(classNameId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByClassNameId(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByClassNameId(classNameId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByClassNameId(classNameId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats findByClassNameId_First(
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByClassNameId_First(classNameId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats findByClassNameId_Last(
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByClassNameId_Last(classNameId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats[] findByClassNameId_PrevAndNext(
		long tagStatsId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence()
				   .findByClassNameId_PrevAndNext(tagStatsId, classNameId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats findByT_C(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByT_C(tagId, classNameId);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats fetchByT_C(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByT_C(tagId, classNameId);
	}

	public static com.liferay.portlet.asset.model.AssetTagStats fetchByT_C(
		long tagId, long classNameId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByT_C(tagId, classNameId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByTagId(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByTagId(tagId);
	}

	public static void removeByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByClassNameId(classNameId);
	}

	public static void removeByT_C(long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException {
		getPersistence().removeByT_C(tagId, classNameId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByTagId(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByTagId(tagId);
	}

	public static int countByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByClassNameId(classNameId);
	}

	public static int countByT_C(long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_C(tagId, classNameId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static AssetTagStatsPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetTagStatsPersistence)PortalBeanLocatorUtil.locate(AssetTagStatsPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(AssetTagStatsPersistence persistence) {
		_persistence = persistence;
	}

	private static AssetTagStatsPersistence _persistence;
}