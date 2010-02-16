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

import com.liferay.portlet.asset.model.AssetCategory;

/**
 * <a href="AssetCategoryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryPersistenceImpl
 * @see       AssetCategoryUtil
 * @generated
 */
public interface AssetCategoryPersistence extends BasePersistence<AssetCategory> {
	public void cacheResult(
		com.liferay.portlet.asset.model.AssetCategory assetCategory);

	public void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories);

	public com.liferay.portlet.asset.model.AssetCategory create(long categoryId);

	public com.liferay.portlet.asset.model.AssetCategory remove(long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory updateImpl(
		com.liferay.portlet.asset.model.AssetCategory assetCategory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetCategory findByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory fetchByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetCategory findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory[] findByUuid_PrevAndNext(
		long categoryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetCategory fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByParentCategoryId(
		long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByParentCategoryId(
		long parentCategoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByParentCategoryId(
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetCategory findByParentCategoryId_First(
		long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory findByParentCategoryId_Last(
		long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory[] findByParentCategoryId_PrevAndNext(
		long categoryId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByVocabularyId(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByVocabularyId(
		long vocabularyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByVocabularyId(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetCategory findByVocabularyId_First(
		long vocabularyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory findByVocabularyId_Last(
		long vocabularyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory[] findByVocabularyId_PrevAndNext(
		long categoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_N(
		long parentCategoryId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_N(
		long parentCategoryId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_N(
		long parentCategoryId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetCategory findByP_N_First(
		long parentCategoryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory findByP_N_Last(
		long parentCategoryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory[] findByP_N_PrevAndNext(
		long categoryId, long parentCategoryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_V(
		long parentCategoryId, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_V(
		long parentCategoryId, long vocabularyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_V(
		long parentCategoryId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetCategory findByP_V_First(
		long parentCategoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory findByP_V_Last(
		long parentCategoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory[] findByP_V_PrevAndNext(
		long categoryId, long parentCategoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByN_V(
		java.lang.String name, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByN_V(
		java.lang.String name, long vocabularyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByN_V(
		java.lang.String name, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetCategory findByN_V_First(
		java.lang.String name, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory findByN_V_Last(
		java.lang.String name, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public com.liferay.portlet.asset.model.AssetCategory[] findByN_V_PrevAndNext(
		long categoryId, java.lang.String name, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException;

	public void removeByParentCategoryId(long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByVocabularyId(long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByP_N(long parentCategoryId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByP_V(long parentCategoryId, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByN_V(java.lang.String name, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByParentCategoryId(long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByVocabularyId(long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByP_N(long parentCategoryId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByP_V(long parentCategoryId, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByN_V(java.lang.String name, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int getAssetEntriesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsAssetEntries(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void clearAssetEntries(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void rebuildTree(long groupId, boolean force)
		throws com.liferay.portal.kernel.exception.SystemException;
}