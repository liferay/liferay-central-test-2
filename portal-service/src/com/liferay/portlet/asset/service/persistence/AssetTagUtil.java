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
 * <a href="AssetTagUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetTagUtil {
	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetTag assetTag) {
		getPersistence().cacheResult(assetTag);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags) {
		getPersistence().cacheResult(assetTags);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portlet.asset.model.AssetTag create(long tagId) {
		return getPersistence().create(tagId);
	}

	public static com.liferay.portlet.asset.model.AssetTag remove(long tagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().remove(tagId);
	}

	public static com.liferay.portlet.asset.model.AssetTag remove(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(assetTag);
	}

	/**
	 * @deprecated Use <code>update(AssetTag assetTag, boolean merge)</code>.
	 */
	public static com.liferay.portlet.asset.model.AssetTag update(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(assetTag);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        assetTag the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when assetTag is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.asset.model.AssetTag update(
		com.liferay.portlet.asset.model.AssetTag assetTag, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(assetTag, merge);
	}

	public static com.liferay.portlet.asset.model.AssetTag updateImpl(
		com.liferay.portlet.asset.model.AssetTag assetTag, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(assetTag, merge);
	}

	public static com.liferay.portlet.asset.model.AssetTag findByPrimaryKey(
		long tagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByPrimaryKey(tagId);
	}

	public static com.liferay.portlet.asset.model.AssetTag fetchByPrimaryKey(
		long tagId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(tagId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTag findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTag findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetTag[] findByGroupId_PrevAndNext(
		long tagId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByGroupId_PrevAndNext(tagId, groupId, obc);
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

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> getAssets(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getAssets(pk);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> getAssets(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getAssets(pk, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> getAssets(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getAssets(pk, start, end, obc);
	}

	public static int getAssetsSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getAssetsSize(pk);
	}

	public static boolean containsAsset(long pk, long assetPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsAsset(pk, assetPK);
	}

	public static boolean containsAssets(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsAssets(pk);
	}

	public static void addAsset(long pk, long assetPK)
		throws com.liferay.portal.SystemException {
		getPersistence().addAsset(pk, assetPK);
	}

	public static void addAsset(long pk,
		com.liferay.portlet.asset.model.Asset asset)
		throws com.liferay.portal.SystemException {
		getPersistence().addAsset(pk, asset);
	}

	public static void addAssets(long pk, long[] assetPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addAssets(pk, assetPKs);
	}

	public static void addAssets(long pk,
		java.util.List<com.liferay.portlet.asset.model.Asset> assets)
		throws com.liferay.portal.SystemException {
		getPersistence().addAssets(pk, assets);
	}

	public static void clearAssets(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearAssets(pk);
	}

	public static void removeAsset(long pk, long assetPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAsset(pk, assetPK);
	}

	public static void removeAsset(long pk,
		com.liferay.portlet.asset.model.Asset asset)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAsset(pk, asset);
	}

	public static void removeAssets(long pk, long[] assetPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAssets(pk, assetPKs);
	}

	public static void removeAssets(long pk,
		java.util.List<com.liferay.portlet.asset.model.Asset> assets)
		throws com.liferay.portal.SystemException {
		getPersistence().removeAssets(pk, assets);
	}

	public static void setAssets(long pk, long[] assetPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setAssets(pk, assetPKs);
	}

	public static void setAssets(long pk,
		java.util.List<com.liferay.portlet.asset.model.Asset> assets)
		throws com.liferay.portal.SystemException {
		getPersistence().setAssets(pk, assets);
	}

	public static AssetTagPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(AssetTagPersistence persistence) {
		_persistence = persistence;
	}

	private static AssetTagPersistence _persistence;
}