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

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * <a href="AssetCategoryVocabularyPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface AssetCategoryVocabularyPersistence extends BasePersistence {
	public void cacheResult(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary assetCategoryVocabulary);

	public void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> assetCategoryVocabularies);

	public void clearCache();

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary create(
		long vocabularyId);

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary remove(
		long vocabularyId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryVocabularyException;

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary remove(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary assetCategoryVocabulary)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(AssetCategoryVocabulary assetCategoryVocabulary, boolean merge)</code>.
	 */
	public com.liferay.portlet.asset.model.AssetCategoryVocabulary update(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary assetCategoryVocabulary)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        assetCategoryVocabulary the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when assetCategoryVocabulary is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portlet.asset.model.AssetCategoryVocabulary update(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary assetCategoryVocabulary,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary updateImpl(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary assetCategoryVocabulary,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary findByPrimaryKey(
		long vocabularyId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryVocabularyException;

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary fetchByPrimaryKey(
		long vocabularyId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary findByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryVocabularyException;

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary fetchByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary fetchByG_N(
		long groupId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryVocabularyException;

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryVocabularyException;

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary[] findByGroupId_PrevAndNext(
		long vocabularyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryVocabularyException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryVocabularyException;

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryVocabularyException;

	public com.liferay.portlet.asset.model.AssetCategoryVocabulary[] findByCompanyId_PrevAndNext(
		long vocabularyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryVocabularyException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryVocabularyException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}