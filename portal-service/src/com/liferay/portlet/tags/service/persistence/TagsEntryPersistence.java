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

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * <a href="TagsEntryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface TagsEntryPersistence extends BasePersistence {
	public void cacheResult(com.liferay.portlet.tags.model.TagsEntry tagsEntry);

	public void cacheResult(
		java.util.List<com.liferay.portlet.tags.model.TagsEntry> tagsEntries);

	public com.liferay.portlet.tags.model.TagsEntry create(long entryId);

	public com.liferay.portlet.tags.model.TagsEntry remove(long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException;

	public com.liferay.portlet.tags.model.TagsEntry remove(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(TagsEntry tagsEntry, boolean merge)</code>.
	 */
	public com.liferay.portlet.tags.model.TagsEntry update(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry)
		throws com.liferay.portal.SystemException;

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
	public com.liferay.portlet.tags.model.TagsEntry update(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsEntry updateImpl(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException;

	public com.liferay.portlet.tags.model.TagsEntry fetchByPrimaryKey(
		long entryId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> findByVocabularyId(
		long vocabularyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> findByVocabularyId(
		long vocabularyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> findByVocabularyId(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsEntry findByVocabularyId_First(
		long vocabularyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException;

	public com.liferay.portlet.tags.model.TagsEntry findByVocabularyId_Last(
		long vocabularyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException;

	public com.liferay.portlet.tags.model.TagsEntry[] findByVocabularyId_PrevAndNext(
		long entryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException;

	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> findByP_V(
		long parentEntryId, long vocabularyId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> findByP_V(
		long parentEntryId, long vocabularyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> findByP_V(
		long parentEntryId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsEntry findByP_V_First(
		long parentEntryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException;

	public com.liferay.portlet.tags.model.TagsEntry findByP_V_Last(
		long parentEntryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException;

	public com.liferay.portlet.tags.model.TagsEntry[] findByP_V_PrevAndNext(
		long entryId, long parentEntryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchEntryException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsEntry> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByVocabularyId(long vocabularyId)
		throws com.liferay.portal.SystemException;

	public void removeByP_V(long parentEntryId, long vocabularyId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByVocabularyId(long vocabularyId)
		throws com.liferay.portal.SystemException;

	public int countByP_V(long parentEntryId, long vocabularyId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk, int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public int getTagsAssetsSize(long pk)
		throws com.liferay.portal.SystemException;

	public boolean containsTagsAsset(long pk, long tagsAssetPK)
		throws com.liferay.portal.SystemException;

	public boolean containsTagsAssets(long pk)
		throws com.liferay.portal.SystemException;

	public void addTagsAsset(long pk, long tagsAssetPK)
		throws com.liferay.portal.SystemException;

	public void addTagsAsset(long pk,
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException;

	public void addTagsAssets(long pk, long[] tagsAssetPKs)
		throws com.liferay.portal.SystemException;

	public void addTagsAssets(long pk,
		java.util.List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws com.liferay.portal.SystemException;

	public void clearTagsAssets(long pk)
		throws com.liferay.portal.SystemException;

	public void removeTagsAsset(long pk, long tagsAssetPK)
		throws com.liferay.portal.SystemException;

	public void removeTagsAsset(long pk,
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException;

	public void removeTagsAssets(long pk, long[] tagsAssetPKs)
		throws com.liferay.portal.SystemException;

	public void removeTagsAssets(long pk,
		java.util.List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws com.liferay.portal.SystemException;

	public void setTagsAssets(long pk, long[] tagsAssetPKs)
		throws com.liferay.portal.SystemException;

	public void setTagsAssets(long pk,
		java.util.List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws com.liferay.portal.SystemException;
}