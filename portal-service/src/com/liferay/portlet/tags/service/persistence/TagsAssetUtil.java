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

package com.liferay.portlet.tags.service.persistence;

/**
 * <a href="TagsAssetUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsAssetUtil {
	public static void cacheResult(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset) {
		getPersistence().cacheResult(tagsAsset);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets) {
		getPersistence().cacheResult(tagsAssets);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portlet.tags.model.TagsAsset create(long assetId) {
		return getPersistence().create(assetId);
	}

	public static com.liferay.portlet.tags.model.TagsAsset remove(long assetId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchAssetException {
		return getPersistence().remove(assetId);
	}

	public static com.liferay.portlet.tags.model.TagsAsset remove(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(tagsAsset);
	}

	/**
	 * @deprecated Use <code>update(TagsAsset tagsAsset, boolean merge)</code>.
	 */
	public static com.liferay.portlet.tags.model.TagsAsset update(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(tagsAsset);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        tagsAsset the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when tagsAsset is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.tags.model.TagsAsset update(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(tagsAsset, merge);
	}

	public static com.liferay.portlet.tags.model.TagsAsset updateImpl(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(tagsAsset, merge);
	}

	public static com.liferay.portlet.tags.model.TagsAsset findByPrimaryKey(
		long assetId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchAssetException {
		return getPersistence().findByPrimaryKey(assetId);
	}

	public static com.liferay.portlet.tags.model.TagsAsset fetchByPrimaryKey(
		long assetId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(assetId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.tags.model.TagsAsset findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchAssetException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.tags.model.TagsAsset findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchAssetException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.tags.model.TagsAsset[] findByCompanyId_PrevAndNext(
		long assetId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchAssetException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(assetId, companyId, obc);
	}

	public static com.liferay.portlet.tags.model.TagsAsset findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchAssetException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static com.liferay.portlet.tags.model.TagsAsset fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	public static com.liferay.portlet.tags.model.TagsAsset fetchByC_C(
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

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> findAll(
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
			com.liferay.portlet.tags.NoSuchAssetException {
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

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getTagsEntries(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getTagsEntries(pk);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getTagsEntries(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getTagsEntries(pk, start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> getTagsEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getTagsEntries(pk, start, end, obc);
	}

	public static int getTagsEntriesSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getTagsEntriesSize(pk);
	}

	public static boolean containsTagsEntry(long pk, long tagsEntryPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsTagsEntry(pk, tagsEntryPK);
	}

	public static boolean containsTagsEntries(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsTagsEntries(pk);
	}

	public static void addTagsEntry(long pk, long tagsEntryPK)
		throws com.liferay.portal.SystemException {
		getPersistence().addTagsEntry(pk, tagsEntryPK);
	}

	public static void addTagsEntry(long pk,
		com.liferay.portlet.tags.model.TagsEntry tagsEntry)
		throws com.liferay.portal.SystemException {
		getPersistence().addTagsEntry(pk, tagsEntry);
	}

	public static void addTagsEntries(long pk, long[] tagsEntryPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addTagsEntries(pk, tagsEntryPKs);
	}

	public static void addTagsEntries(long pk,
		java.util.List<com.liferay.portlet.tags.model.TagsEntry> tagsEntries)
		throws com.liferay.portal.SystemException {
		getPersistence().addTagsEntries(pk, tagsEntries);
	}

	public static void clearTagsEntries(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearTagsEntries(pk);
	}

	public static void removeTagsEntry(long pk, long tagsEntryPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeTagsEntry(pk, tagsEntryPK);
	}

	public static void removeTagsEntry(long pk,
		com.liferay.portlet.tags.model.TagsEntry tagsEntry)
		throws com.liferay.portal.SystemException {
		getPersistence().removeTagsEntry(pk, tagsEntry);
	}

	public static void removeTagsEntries(long pk, long[] tagsEntryPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removeTagsEntries(pk, tagsEntryPKs);
	}

	public static void removeTagsEntries(long pk,
		java.util.List<com.liferay.portlet.tags.model.TagsEntry> tagsEntries)
		throws com.liferay.portal.SystemException {
		getPersistence().removeTagsEntries(pk, tagsEntries);
	}

	public static void setTagsEntries(long pk, long[] tagsEntryPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setTagsEntries(pk, tagsEntryPKs);
	}

	public static void setTagsEntries(long pk,
		java.util.List<com.liferay.portlet.tags.model.TagsEntry> tagsEntries)
		throws com.liferay.portal.SystemException {
		getPersistence().setTagsEntries(pk, tagsEntries);
	}

	public static TagsAssetPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(TagsAssetPersistence persistence) {
		_persistence = persistence;
	}

	private static TagsAssetPersistence _persistence;
}