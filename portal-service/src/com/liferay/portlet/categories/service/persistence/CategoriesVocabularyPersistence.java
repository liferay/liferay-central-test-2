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

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * <a href="CategoriesVocabularyPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface CategoriesVocabularyPersistence extends BasePersistence {
	public void cacheResult(
		com.liferay.portlet.categories.model.CategoriesVocabulary categoriesVocabulary);

	public void cacheResult(
		java.util.List<com.liferay.portlet.categories.model.CategoriesVocabulary> categoriesVocabularies);

	public com.liferay.portlet.categories.model.CategoriesVocabulary create(
		long vocabularyId);

	public com.liferay.portlet.categories.model.CategoriesVocabulary remove(
		long vocabularyId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchVocabularyException;

	public com.liferay.portlet.categories.model.CategoriesVocabulary remove(
		com.liferay.portlet.categories.model.CategoriesVocabulary categoriesVocabulary)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(CategoriesVocabulary categoriesVocabulary, boolean merge)</code>.
	 */
	public com.liferay.portlet.categories.model.CategoriesVocabulary update(
		com.liferay.portlet.categories.model.CategoriesVocabulary categoriesVocabulary)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        categoriesVocabulary the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when categoriesVocabulary is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portlet.categories.model.CategoriesVocabulary update(
		com.liferay.portlet.categories.model.CategoriesVocabulary categoriesVocabulary,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.categories.model.CategoriesVocabulary updateImpl(
		com.liferay.portlet.categories.model.CategoriesVocabulary categoriesVocabulary,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.categories.model.CategoriesVocabulary findByPrimaryKey(
		long vocabularyId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchVocabularyException;

	public com.liferay.portlet.categories.model.CategoriesVocabulary fetchByPrimaryKey(
		long vocabularyId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.categories.model.CategoriesVocabulary findByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchVocabularyException;

	public com.liferay.portlet.categories.model.CategoriesVocabulary fetchByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.categories.model.CategoriesVocabulary fetchByG_N(
		long groupId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.categories.model.CategoriesVocabulary> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.categories.model.CategoriesVocabulary> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.categories.model.CategoriesVocabulary> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.categories.model.CategoriesVocabulary findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchVocabularyException;

	public com.liferay.portlet.categories.model.CategoriesVocabulary findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchVocabularyException;

	public com.liferay.portlet.categories.model.CategoriesVocabulary[] findByGroupId_PrevAndNext(
		long vocabularyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchVocabularyException;

	public java.util.List<com.liferay.portlet.categories.model.CategoriesVocabulary> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.categories.model.CategoriesVocabulary> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.categories.model.CategoriesVocabulary> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.categories.model.CategoriesVocabulary findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchVocabularyException;

	public com.liferay.portlet.categories.model.CategoriesVocabulary findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchVocabularyException;

	public com.liferay.portlet.categories.model.CategoriesVocabulary[] findByCompanyId_PrevAndNext(
		long vocabularyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchVocabularyException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.categories.model.CategoriesVocabulary> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.categories.model.CategoriesVocabulary> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.categories.model.CategoriesVocabulary> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.categories.NoSuchVocabularyException;

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