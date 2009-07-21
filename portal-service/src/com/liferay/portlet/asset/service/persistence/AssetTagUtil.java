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

	public static com.liferay.portlet.asset.model.AssetTag update(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(assetTag);
	}

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

	public static AssetTagPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(AssetTagPersistence persistence) {
		_persistence = persistence;
	}

	private static AssetTagPersistence _persistence;
}