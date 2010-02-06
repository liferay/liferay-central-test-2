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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.asset.model.AssetTagStats;

/**
 * <a href="AssetTagStatsPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagStatsPersistenceImpl
 * @see       AssetTagStatsUtil
 * @generated
 */
public interface AssetTagStatsPersistence extends BasePersistence<AssetTagStats> {
	public void cacheResult(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats);

	public void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetTagStats> assetTagStatses);

	public com.liferay.portlet.asset.model.AssetTagStats create(long tagStatsId);

	public com.liferay.portlet.asset.model.AssetTagStats remove(long tagStatsId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	public com.liferay.portlet.asset.model.AssetTagStats updateImpl(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetTagStats findByPrimaryKey(
		long tagStatsId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	public com.liferay.portlet.asset.model.AssetTagStats fetchByPrimaryKey(
		long tagStatsId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByTagId(
		long tagId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByTagId(
		long tagId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByTagId(
		long tagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetTagStats findByTagId_First(
		long tagId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	public com.liferay.portlet.asset.model.AssetTagStats findByTagId_Last(
		long tagId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	public com.liferay.portlet.asset.model.AssetTagStats[] findByTagId_PrevAndNext(
		long tagStatsId, long tagId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByClassNameId(
		long classNameId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByClassNameId(
		long classNameId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetTagStats findByClassNameId_First(
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	public com.liferay.portlet.asset.model.AssetTagStats findByClassNameId_Last(
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	public com.liferay.portlet.asset.model.AssetTagStats[] findByClassNameId_PrevAndNext(
		long tagStatsId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	public com.liferay.portlet.asset.model.AssetTagStats findByT_C(long tagId,
		long classNameId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	public com.liferay.portlet.asset.model.AssetTagStats fetchByT_C(
		long tagId, long classNameId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetTagStats fetchByT_C(
		long tagId, long classNameId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByTagId(long tagId)
		throws com.liferay.portal.SystemException;

	public void removeByClassNameId(long classNameId)
		throws com.liferay.portal.SystemException;

	public void removeByT_C(long tagId, long classNameId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagStatsException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByTagId(long tagId)
		throws com.liferay.portal.SystemException;

	public int countByClassNameId(long classNameId)
		throws com.liferay.portal.SystemException;

	public int countByT_C(long tagId, long classNameId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}