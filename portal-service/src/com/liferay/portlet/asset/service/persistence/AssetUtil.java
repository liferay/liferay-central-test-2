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

package com.liferay.portlet.asset.service.persistence;

/**
 * <a href="AssetUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetUtil {
	public static void cacheResult(com.liferay.portlet.asset.model.Asset asset) {
		getPersistence().cacheResult(asset);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.Asset> assets) {
		getPersistence().cacheResult(assets);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portlet.asset.model.Asset create(long assetId) {
		return getPersistence().create(assetId);
	}

	public static com.liferay.portlet.asset.model.Asset remove(long assetId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchAssetException {
		return getPersistence().remove(assetId);
	}

	public static com.liferay.portlet.asset.model.Asset remove(
		com.liferay.portlet.asset.model.Asset asset)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(asset);
	}

	/**
	 * @deprecated Use <code>update(Asset asset, boolean merge)</code>.
	 */
	public static com.liferay.portlet.asset.model.Asset update(
		com.liferay.portlet.asset.model.Asset asset)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(asset);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        asset the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when asset is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.asset.model.Asset update(
		com.liferay.portlet.asset.model.Asset asset, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(asset, merge);
	}

	public static com.liferay.portlet.asset.model.Asset updateImpl(
		com.liferay.portlet.asset.model.Asset asset, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(asset, merge);
	}

	public static com.liferay.portlet.asset.model.Asset findByPrimaryKey(
		long assetId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchAssetException {
		return getPersistence().findByPrimaryKey(assetId);
	}

	public static com.liferay.portlet.asset.model.Asset fetchByPrimaryKey(
		long assetId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(assetId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.Asset findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchAssetException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.asset.model.Asset findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchAssetException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.asset.model.Asset[] findByCompanyId_PrevAndNext(
		long assetId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchAssetException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(assetId, companyId, obc);
	}

	public static com.liferay.portlet.asset.model.Asset findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchAssetException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static com.liferay.portlet.asset.model.Asset fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	public static com.liferay.portlet.asset.model.Asset fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
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

	public static java.util.List<com.liferay.portlet.asset.model.Asset> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchAssetException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getAssetCategories(pk);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getAssetCategories(pk, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getAssetCategories(pk, start, end, obc);
	}

	public static int getAssetCategoriesSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getAssetCategoriesSize(pk);
	}

	public static boolean containsAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsAssetCategory(pk, assetCategoryPK);
	}

	public static boolean containsAssetCategories(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsAssetCategories(pk);
	}

	public static void addAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.SystemException {
		getPersistence().addAssetCategory(pk, assetCategoryPK);
	}

	public static void addAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.SystemException {
		getPersistence().addAssetCategory(pk, assetCategory);
	}

	public static void addAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addAssetCategories(pk, assetCategoryPKs);
	}

	public static void addAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.SystemException {
		getPersistence().addAssetCategories(pk, assetCategories);
	}

	public static void clearAssetCategories(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearAssetCategories(pk);
	}

	public static void removeAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAssetCategory(pk, assetCategoryPK);
	}

	public static void removeAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAssetCategory(pk, assetCategory);
	}

	public static void removeAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAssetCategories(pk, assetCategoryPKs);
	}

	public static void removeAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAssetCategories(pk, assetCategories);
	}

	public static void setAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setAssetCategories(pk, assetCategoryPKs);
	}

	public static void setAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.SystemException {
		getPersistence().setAssetCategories(pk, assetCategories);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getAssetTags(pk);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getAssetTags(pk, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getAssetTags(pk, start, end, obc);
	}

	public static int getAssetTagsSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getAssetTagsSize(pk);
	}

	public static boolean containsAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsAssetTag(pk, assetTagPK);
	}

	public static boolean containsAssetTags(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsAssetTags(pk);
	}

	public static void addAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.SystemException {
		getPersistence().addAssetTag(pk, assetTagPK);
	}

	public static void addAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException {
		getPersistence().addAssetTag(pk, assetTag);
	}

	public static void addAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addAssetTags(pk, assetTagPKs);
	}

	public static void addAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.SystemException {
		getPersistence().addAssetTags(pk, assetTags);
	}

	public static void clearAssetTags(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearAssetTags(pk);
	}

	public static void removeAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAssetTag(pk, assetTagPK);
	}

	public static void removeAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAssetTag(pk, assetTag);
	}

	public static void removeAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAssetTags(pk, assetTagPKs);
	}

	public static void removeAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAssetTags(pk, assetTags);
	}

	public static void setAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setAssetTags(pk, assetTagPKs);
	}

	public static void setAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.SystemException {
		getPersistence().setAssetTags(pk, assetTags);
	}

	public static AssetPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(AssetPersistence persistence) {
		_persistence = persistence;
	}

	private static AssetPersistence _persistence;
}