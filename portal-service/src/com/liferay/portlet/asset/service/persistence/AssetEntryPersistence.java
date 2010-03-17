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
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public com.liferay.portlet.asset.model.AssetEntry updateImpl(
		com.liferay.portlet.asset.model.AssetEntry assetEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public com.liferay.portlet.asset.model.AssetEntry fetchByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetEntry findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public com.liferay.portlet.asset.model.AssetEntry findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public com.liferay.portlet.asset.model.AssetEntry[] findByCompanyId_PrevAndNext(
		long entryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public com.liferay.portlet.asset.model.AssetEntry findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public com.liferay.portlet.asset.model.AssetEntry fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetEntry fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int getAssetCategoriesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsAssetCategories(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void clearAssetCategories(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int getAssetTagsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsAssetTags(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void clearAssetTags(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException;
}