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

package com.liferay.portlet.categories.service.persistence;

/**
 * <a href="CategoriesEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CategoriesEntryUtil {
	public static void cacheResult(
		com.liferay.portlet.categories.model.CategoriesEntry categoriesEntry) {
		getPersistence().cacheResult(categoriesEntry);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> categoriesEntries) {
		getPersistence().cacheResult(categoriesEntries);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry create(
		long entryId) {
		return getPersistence().create(entryId);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry remove(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry remove(
		com.liferay.portlet.categories.model.CategoriesEntry categoriesEntry)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(categoriesEntry);
	}

	/**
	 * @deprecated Use <code>update(CategoriesEntry categoriesEntry, boolean merge)</code>.
	 */
	public static com.liferay.portlet.categories.model.CategoriesEntry update(
		com.liferay.portlet.categories.model.CategoriesEntry categoriesEntry)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(categoriesEntry);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        categoriesEntry the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when categoriesEntry is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.categories.model.CategoriesEntry update(
		com.liferay.portlet.categories.model.CategoriesEntry categoriesEntry,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(categoriesEntry, merge);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry updateImpl(
		com.liferay.portlet.categories.model.CategoriesEntry categoriesEntry,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(categoriesEntry, merge);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry fetchByPrimaryKey(
		long entryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findByVocabularyId(
		long vocabularyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByVocabularyId(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findByVocabularyId(
		long vocabularyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByVocabularyId(vocabularyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findByVocabularyId(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByVocabularyId(vocabularyId, start, end, obc);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry findByVocabularyId_First(
		long vocabularyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchEntryException {
		return getPersistence().findByVocabularyId_First(vocabularyId, obc);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry findByVocabularyId_Last(
		long vocabularyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchEntryException {
		return getPersistence().findByVocabularyId_Last(vocabularyId, obc);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry[] findByVocabularyId_PrevAndNext(
		long entryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchEntryException {
		return getPersistence()
				   .findByVocabularyId_PrevAndNext(entryId, vocabularyId, obc);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findByParentId(
		long parentEntryId) throws com.liferay.portal.SystemException {
		return getPersistence().findByParentId(parentEntryId);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findByParentId(
		long parentEntryId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByParentId(parentEntryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findByParentId(
		long parentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByParentId(parentEntryId, start, end, obc);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry findByParentId_First(
		long parentEntryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchEntryException {
		return getPersistence().findByParentId_First(parentEntryId, obc);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry findByParentId_Last(
		long parentEntryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchEntryException {
		return getPersistence().findByParentId_Last(parentEntryId, obc);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry[] findByParentId_PrevAndNext(
		long entryId, long parentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchEntryException {
		return getPersistence()
				   .findByParentId_PrevAndNext(entryId, parentEntryId, obc);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findByP_N(
		long parentEntryId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByP_N(parentEntryId, name);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findByP_N(
		long parentEntryId, java.lang.String name, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByP_N(parentEntryId, name, start, end);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findByP_N(
		long parentEntryId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByP_N(parentEntryId, name, start, end, obc);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry findByP_N_First(
		long parentEntryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchEntryException {
		return getPersistence().findByP_N_First(parentEntryId, name, obc);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry findByP_N_Last(
		long parentEntryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchEntryException {
		return getPersistence().findByP_N_Last(parentEntryId, name, obc);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry[] findByP_N_PrevAndNext(
		long entryId, long parentEntryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchEntryException {
		return getPersistence()
				   .findByP_N_PrevAndNext(entryId, parentEntryId, name, obc);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findByV_P(
		long vocabularyId, long parentEntryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByV_P(vocabularyId, parentEntryId);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findByV_P(
		long vocabularyId, long parentEntryId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByV_P(vocabularyId, parentEntryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findByV_P(
		long vocabularyId, long parentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByV_P(vocabularyId, parentEntryId, start, end, obc);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry findByV_P_First(
		long vocabularyId, long parentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchEntryException {
		return getPersistence().findByV_P_First(vocabularyId, parentEntryId, obc);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry findByV_P_Last(
		long vocabularyId, long parentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchEntryException {
		return getPersistence().findByV_P_Last(vocabularyId, parentEntryId, obc);
	}

	public static com.liferay.portlet.categories.model.CategoriesEntry[] findByV_P_PrevAndNext(
		long entryId, long vocabularyId, long parentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchEntryException {
		return getPersistence()
				   .findByV_P_PrevAndNext(entryId, vocabularyId, parentEntryId,
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

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.categories.model.CategoriesEntry> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByVocabularyId(long vocabularyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByVocabularyId(vocabularyId);
	}

	public static void removeByParentId(long parentEntryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByParentId(parentEntryId);
	}

	public static void removeByP_N(long parentEntryId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByP_N(parentEntryId, name);
	}

	public static void removeByV_P(long vocabularyId, long parentEntryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByV_P(vocabularyId, parentEntryId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByVocabularyId(long vocabularyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByVocabularyId(vocabularyId);
	}

	public static int countByParentId(long parentEntryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByParentId(parentEntryId);
	}

	public static int countByP_N(long parentEntryId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByP_N(parentEntryId, name);
	}

	public static int countByV_P(long vocabularyId, long parentEntryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByV_P(vocabularyId, parentEntryId);
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

	public static CategoriesEntryPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(CategoriesEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static CategoriesEntryPersistence _persistence;
}