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

import com.liferay.portlet.asset.model.AssetEntry;

import java.util.List;

/**
 * <a href="AssetEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetEntryPersistence
 * @see       AssetEntryPersistenceImpl
 * @generated
 */
public class AssetEntryUtil {
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
	public static AssetEntry remove(AssetEntry assetEntry)
		throws SystemException {
		return getPersistence().remove(assetEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static AssetEntry update(AssetEntry assetEntry, boolean merge)
		throws SystemException {
		return getPersistence().update(assetEntry, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetEntry assetEntry) {
		getPersistence().cacheResult(assetEntry);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries) {
		getPersistence().cacheResult(assetEntries);
	}

	public static com.liferay.portlet.asset.model.AssetEntry create(
		long entryId) {
		return getPersistence().create(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetEntry remove(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateImpl(
		com.liferay.portlet.asset.model.AssetEntry assetEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(assetEntry, merge);
	}

	public static com.liferay.portlet.asset.model.AssetEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetEntry fetchByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetEntry findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetEntry findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetEntry[] findByCompanyId_PrevAndNext(
		long entryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(entryId, companyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetEntry findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static com.liferay.portlet.asset.model.AssetEntry fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	public static com.liferay.portlet.asset.model.AssetEntry fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetCategories(pk);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetCategories(pk, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetCategories(pk, start, end, obc);
	}

	public static int getAssetCategoriesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetCategoriesSize(pk);
	}

	public static boolean containsAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetCategory(pk, assetCategoryPK);
	}

	public static boolean containsAssetCategories(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetCategories(pk);
	}

	public static void addAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetCategory(pk, assetCategoryPK);
	}

	public static void addAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetCategory(pk, assetCategory);
	}

	public static void addAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetCategories(pk, assetCategoryPKs);
	}

	public static void addAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetCategories(pk, assetCategories);
	}

	public static void clearAssetCategories(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearAssetCategories(pk);
	}

	public static void removeAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetCategory(pk, assetCategoryPK);
	}

	public static void removeAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetCategory(pk, assetCategory);
	}

	public static void removeAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetCategories(pk, assetCategoryPKs);
	}

	public static void removeAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetCategories(pk, assetCategories);
	}

	public static void setAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetCategories(pk, assetCategoryPKs);
	}

	public static void setAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetCategories(pk, assetCategories);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetTags(pk);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetTags(pk, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetTags(pk, start, end, obc);
	}

	public static int getAssetTagsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetTagsSize(pk);
	}

	public static boolean containsAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetTag(pk, assetTagPK);
	}

	public static boolean containsAssetTags(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetTags(pk);
	}

	public static void addAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetTag(pk, assetTagPK);
	}

	public static void addAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetTag(pk, assetTag);
	}

	public static void addAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetTags(pk, assetTagPKs);
	}

	public static void addAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetTags(pk, assetTags);
	}

	public static void clearAssetTags(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearAssetTags(pk);
	}

	public static void removeAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetTag(pk, assetTagPK);
	}

	public static void removeAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetTag(pk, assetTag);
	}

	public static void removeAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetTags(pk, assetTagPKs);
	}

	public static void removeAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetTags(pk, assetTags);
	}

	public static void setAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetTags(pk, assetTagPKs);
	}

	public static void setAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetTags(pk, assetTags);
	}

	public static AssetEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetEntryPersistence)PortalBeanLocatorUtil.locate(AssetEntryPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(AssetEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static AssetEntryPersistence _persistence;
}