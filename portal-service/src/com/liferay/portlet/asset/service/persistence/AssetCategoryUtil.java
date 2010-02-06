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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.portlet.asset.model.AssetCategory;

import java.util.List;

/**
 * <a href="AssetCategoryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryPersistence
 * @see       AssetCategoryPersistenceImpl
 * @generated
 */
public class AssetCategoryUtil {
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
	public static AssetCategory remove(AssetCategory assetCategory)
		throws SystemException {
		return getPersistence().remove(assetCategory);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static AssetCategory update(AssetCategory assetCategory,
		boolean merge) throws SystemException {
		return getPersistence().update(assetCategory, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		getPersistence().cacheResult(assetCategory);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories) {
		getPersistence().cacheResult(assetCategories);
	}

	public static com.liferay.portlet.asset.model.AssetCategory create(
		long categoryId) {
		return getPersistence().create(categoryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory remove(
		long categoryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().remove(categoryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory updateImpl(
		com.liferay.portlet.asset.model.AssetCategory assetCategory,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(assetCategory, merge);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByPrimaryKey(categoryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory fetchByPrimaryKey(
		long categoryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(categoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory[] findByUuid_PrevAndNext(
		long categoryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByUuid_PrevAndNext(categoryId, uuid, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByParentCategoryId(
		long parentCategoryId) throws com.liferay.portal.SystemException {
		return getPersistence().findByParentCategoryId(parentCategoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByParentCategoryId(
		long parentCategoryId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByParentCategoryId(parentCategoryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByParentCategoryId(
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByParentCategoryId(parentCategoryId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByParentCategoryId_First(
		long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByParentCategoryId_First(parentCategoryId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByParentCategoryId_Last(
		long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByParentCategoryId_Last(parentCategoryId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory[] findByParentCategoryId_PrevAndNext(
		long categoryId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByParentCategoryId_PrevAndNext(categoryId,
			parentCategoryId, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByVocabularyId(
		long vocabularyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByVocabularyId(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByVocabularyId(
		long vocabularyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByVocabularyId(vocabularyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByVocabularyId(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByVocabularyId(vocabularyId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByVocabularyId_First(
		long vocabularyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByVocabularyId_First(vocabularyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByVocabularyId_Last(
		long vocabularyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByVocabularyId_Last(vocabularyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory[] findByVocabularyId_PrevAndNext(
		long categoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByVocabularyId_PrevAndNext(categoryId, vocabularyId, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_N(
		long parentCategoryId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByP_N(parentCategoryId, name);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_N(
		long parentCategoryId, java.lang.String name, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByP_N(parentCategoryId, name, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_N(
		long parentCategoryId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByP_N(parentCategoryId, name, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByP_N_First(
		long parentCategoryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByP_N_First(parentCategoryId, name, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByP_N_Last(
		long parentCategoryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByP_N_Last(parentCategoryId, name, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory[] findByP_N_PrevAndNext(
		long categoryId, long parentCategoryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_N_PrevAndNext(categoryId, parentCategoryId, name,
			obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_V(
		long parentCategoryId, long vocabularyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByP_V(parentCategoryId, vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_V(
		long parentCategoryId, long vocabularyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByP_V(parentCategoryId, vocabularyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_V(
		long parentCategoryId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByP_V(parentCategoryId, vocabularyId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByP_V_First(
		long parentCategoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_V_First(parentCategoryId, vocabularyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByP_V_Last(
		long parentCategoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_V_Last(parentCategoryId, vocabularyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory[] findByP_V_PrevAndNext(
		long categoryId, long parentCategoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_V_PrevAndNext(categoryId, parentCategoryId,
			vocabularyId, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByN_V(
		java.lang.String name, long vocabularyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_V(name, vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByN_V(
		java.lang.String name, long vocabularyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_V(name, vocabularyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByN_V(
		java.lang.String name, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_V(name, vocabularyId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByN_V_First(
		java.lang.String name, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByN_V_First(name, vocabularyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByN_V_Last(
		java.lang.String name, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByN_V_Last(name, vocabularyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory[] findByN_V_PrevAndNext(
		long categoryId, java.lang.String name, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByN_V_PrevAndNext(categoryId, name, vocabularyId, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByParentCategoryId(long parentCategoryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByParentCategoryId(parentCategoryId);
	}

	public static void removeByVocabularyId(long vocabularyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByVocabularyId(vocabularyId);
	}

	public static void removeByP_N(long parentCategoryId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByP_N(parentCategoryId, name);
	}

	public static void removeByP_V(long parentCategoryId, long vocabularyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByP_V(parentCategoryId, vocabularyId);
	}

	public static void removeByN_V(java.lang.String name, long vocabularyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByN_V(name, vocabularyId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	public static int countByParentCategoryId(long parentCategoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByParentCategoryId(parentCategoryId);
	}

	public static int countByVocabularyId(long vocabularyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByVocabularyId(vocabularyId);
	}

	public static int countByP_N(long parentCategoryId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByP_N(parentCategoryId, name);
	}

	public static int countByP_V(long parentCategoryId, long vocabularyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByP_V(parentCategoryId, vocabularyId);
	}

	public static int countByN_V(java.lang.String name, long vocabularyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByN_V(name, vocabularyId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getAssetEntries(pk);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getAssetEntries(pk, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getAssetEntries(pk, start, end, obc);
	}

	public static int getAssetEntriesSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getAssetEntriesSize(pk);
	}

	public static boolean containsAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsAssetEntry(pk, assetEntryPK);
	}

	public static boolean containsAssetEntries(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsAssetEntries(pk);
	}

	public static void addAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.SystemException {
		getPersistence().addAssetEntry(pk, assetEntryPK);
	}

	public static void addAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.SystemException {
		getPersistence().addAssetEntry(pk, assetEntry);
	}

	public static void addAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addAssetEntries(pk, assetEntryPKs);
	}

	public static void addAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.SystemException {
		getPersistence().addAssetEntries(pk, assetEntries);
	}

	public static void clearAssetEntries(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearAssetEntries(pk);
	}

	public static void removeAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAssetEntry(pk, assetEntryPK);
	}

	public static void removeAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAssetEntry(pk, assetEntry);
	}

	public static void removeAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAssetEntries(pk, assetEntryPKs);
	}

	public static void removeAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAssetEntries(pk, assetEntries);
	}

	public static void setAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setAssetEntries(pk, assetEntryPKs);
	}

	public static void setAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.SystemException {
		getPersistence().setAssetEntries(pk, assetEntries);
	}

	public static void rebuildTree(long groupId, boolean force)
		throws com.liferay.portal.SystemException {
		getPersistence().rebuildTree(groupId, force);
	}

	public static AssetCategoryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetCategoryPersistence)PortalBeanLocatorUtil.locate(AssetCategoryPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(AssetCategoryPersistence persistence) {
		_persistence = persistence;
	}

	private static AssetCategoryPersistence _persistence;
}