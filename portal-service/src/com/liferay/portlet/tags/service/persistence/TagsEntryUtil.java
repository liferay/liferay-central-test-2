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
 * <a href="TagsEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsEntryUtil {
	public static void cacheResult(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry) {
		getPersistence().cacheResult(tagsEntry);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.tags.model.TagsEntry> tagsEntries) {
		getPersistence().cacheResult(tagsEntries);
	}

	public static com.liferay.portlet.tags.model.TagsEntry create(long entryId) {
		return getPersistence().create(entryId);
	}

	public static com.liferay.portlet.tags.model.TagsEntry remove(long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static com.liferay.portlet.tags.model.TagsEntry remove(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(tagsEntry);
	}

	/**
	 * @deprecated Use <code>update(TagsEntry tagsEntry, boolean merge)</code>.
	 */
	public static com.liferay.portlet.tags.model.TagsEntry update(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(tagsEntry);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        tagsEntry the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when tagsEntry is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.tags.model.TagsEntry update(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(tagsEntry, merge);
	}

	public static com.liferay.portlet.tags.model.TagsEntry updateImpl(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(tagsEntry, merge);
	}

	public static com.liferay.portlet.tags.model.TagsEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	public static com.liferay.portlet.tags.model.TagsEntry fetchByPrimaryKey(
		long entryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> findByVocabularyId(
		long vocabularyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByVocabularyId(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> findByVocabularyId(
		long vocabularyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByVocabularyId(vocabularyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> findByVocabularyId(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByVocabularyId(vocabularyId, start, end, obc);
	}

	public static com.liferay.portlet.tags.model.TagsEntry findByVocabularyId_First(
		long vocabularyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException {
		return getPersistence().findByVocabularyId_First(vocabularyId, obc);
	}

	public static com.liferay.portlet.tags.model.TagsEntry findByVocabularyId_Last(
		long vocabularyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException {
		return getPersistence().findByVocabularyId_Last(vocabularyId, obc);
	}

	public static com.liferay.portlet.tags.model.TagsEntry[] findByVocabularyId_PrevAndNext(
		long entryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException {
		return getPersistence()
				   .findByVocabularyId_PrevAndNext(entryId, vocabularyId, obc);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> findByP_V(
		long parentEntryId, long vocabularyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByP_V(parentEntryId, vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> findByP_V(
		long parentEntryId, long vocabularyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByP_V(parentEntryId, vocabularyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> findByP_V(
		long parentEntryId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByP_V(parentEntryId, vocabularyId, start, end, obc);
	}

	public static com.liferay.portlet.tags.model.TagsEntry findByP_V_First(
		long parentEntryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException {
		return getPersistence().findByP_V_First(parentEntryId, vocabularyId, obc);
	}

	public static com.liferay.portlet.tags.model.TagsEntry findByP_V_Last(
		long parentEntryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException {
		return getPersistence().findByP_V_Last(parentEntryId, vocabularyId, obc);
	}

	public static com.liferay.portlet.tags.model.TagsEntry[] findByP_V_PrevAndNext(
		long entryId, long parentEntryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException {
		return getPersistence()
				   .findByP_V_PrevAndNext(entryId, parentEntryId, vocabularyId,
			obc);
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

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsEntry> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByVocabularyId(long vocabularyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByVocabularyId(vocabularyId);
	}

	public static void removeByP_V(long parentEntryId, long vocabularyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByP_V(parentEntryId, vocabularyId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByVocabularyId(long vocabularyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByVocabularyId(vocabularyId);
	}

	public static int countByP_V(long parentEntryId, long vocabularyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByP_V(parentEntryId, vocabularyId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getTagsAssets(pk);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getTagsAssets(pk, start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getTagsAssets(pk, start, end, obc);
	}

	public static int getTagsAssetsSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getTagsAssetsSize(pk);
	}

	public static boolean containsTagsAsset(long pk, long tagsAssetPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsTagsAsset(pk, tagsAssetPK);
	}

	public static boolean containsTagsAssets(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsTagsAssets(pk);
	}

	public static void addTagsAsset(long pk, long tagsAssetPK)
		throws com.liferay.portal.SystemException {
		getPersistence().addTagsAsset(pk, tagsAssetPK);
	}

	public static void addTagsAsset(long pk,
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException {
		getPersistence().addTagsAsset(pk, tagsAsset);
	}

	public static void addTagsAssets(long pk, long[] tagsAssetPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addTagsAssets(pk, tagsAssetPKs);
	}

	public static void addTagsAssets(long pk,
		java.util.List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws com.liferay.portal.SystemException {
		getPersistence().addTagsAssets(pk, tagsAssets);
	}

	public static void clearTagsAssets(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearTagsAssets(pk);
	}

	public static void removeTagsAsset(long pk, long tagsAssetPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeTagsAsset(pk, tagsAssetPK);
	}

	public static void removeTagsAsset(long pk,
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException {
		getPersistence().removeTagsAsset(pk, tagsAsset);
	}

	public static void removeTagsAssets(long pk, long[] tagsAssetPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removeTagsAssets(pk, tagsAssetPKs);
	}

	public static void removeTagsAssets(long pk,
		java.util.List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws com.liferay.portal.SystemException {
		getPersistence().removeTagsAssets(pk, tagsAssets);
	}

	public static void setTagsAssets(long pk, long[] tagsAssetPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setTagsAssets(pk, tagsAssetPKs);
	}

	public static void setTagsAssets(long pk,
		java.util.List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws com.liferay.portal.SystemException {
		getPersistence().setTagsAssets(pk, tagsAssets);
	}

	public static TagsEntryPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(TagsEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static TagsEntryPersistence _persistence;
}