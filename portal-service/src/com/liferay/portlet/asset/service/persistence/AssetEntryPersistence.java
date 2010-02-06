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

import com.liferay.portlet.asset.model.AssetEntry;

/**
 * <a href="AssetEntryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetEntryPersistenceImpl
 * @see       AssetEntryUtil
 * @generated
 */
public interface AssetEntryPersistence extends BasePersistence<AssetEntry> {
	public void cacheResult(
		com.liferay.portlet.asset.model.AssetEntry assetEntry);

	public void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries);

	public com.liferay.portlet.asset.model.AssetEntry create(long entryId);

	public com.liferay.portlet.asset.model.AssetEntry remove(long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public com.liferay.portlet.asset.model.AssetEntry updateImpl(
		com.liferay.portlet.asset.model.AssetEntry assetEntry, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public com.liferay.portlet.asset.model.AssetEntry fetchByPrimaryKey(
		long entryId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetEntry findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public com.liferay.portlet.asset.model.AssetEntry findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public com.liferay.portlet.asset.model.AssetEntry[] findByCompanyId_PrevAndNext(
		long entryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public com.liferay.portlet.asset.model.AssetEntry findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public com.liferay.portlet.asset.model.AssetEntry fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetEntry fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int getAssetCategoriesSize(long pk)
		throws com.liferay.portal.SystemException;

	public boolean containsAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.SystemException;

	public boolean containsAssetCategories(long pk)
		throws com.liferay.portal.SystemException;

	public void addAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.SystemException;

	public void addAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.SystemException;

	public void addAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.SystemException;

	public void addAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.SystemException;

	public void clearAssetCategories(long pk)
		throws com.liferay.portal.SystemException;

	public void removeAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.SystemException;

	public void removeAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.SystemException;

	public void removeAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.SystemException;

	public void removeAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.SystemException;

	public void setAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.SystemException;

	public void setAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int getAssetTagsSize(long pk)
		throws com.liferay.portal.SystemException;

	public boolean containsAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.SystemException;

	public boolean containsAssetTags(long pk)
		throws com.liferay.portal.SystemException;

	public void addAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.SystemException;

	public void addAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException;

	public void addAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.SystemException;

	public void addAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.SystemException;

	public void clearAssetTags(long pk)
		throws com.liferay.portal.SystemException;

	public void removeAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.SystemException;

	public void removeAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException;

	public void removeAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.SystemException;

	public void removeAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.SystemException;

	public void setAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.SystemException;

	public void setAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.SystemException;
}