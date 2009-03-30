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
 * <a href="TagsVocabularyUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsVocabularyUtil {
	public static void cacheResult(
		com.liferay.portlet.tags.model.TagsVocabulary tagsVocabulary) {
		getPersistence().cacheResult(tagsVocabulary);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> tagsVocabularies) {
		getPersistence().cacheResult(tagsVocabularies);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary create(
		long vocabularyId) {
		return getPersistence().create(vocabularyId);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary remove(
		long vocabularyId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		return getPersistence().remove(vocabularyId);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary remove(
		com.liferay.portlet.tags.model.TagsVocabulary tagsVocabulary)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(tagsVocabulary);
	}

	/**
	 * @deprecated Use <code>update(TagsVocabulary tagsVocabulary, boolean merge)</code>.
	 */
	public static com.liferay.portlet.tags.model.TagsVocabulary update(
		com.liferay.portlet.tags.model.TagsVocabulary tagsVocabulary)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(tagsVocabulary);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        tagsVocabulary the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when tagsVocabulary is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.tags.model.TagsVocabulary update(
		com.liferay.portlet.tags.model.TagsVocabulary tagsVocabulary,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(tagsVocabulary, merge);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary updateImpl(
		com.liferay.portlet.tags.model.TagsVocabulary tagsVocabulary,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(tagsVocabulary, merge);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary findByPrimaryKey(
		long vocabularyId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		return getPersistence().findByPrimaryKey(vocabularyId);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary fetchByPrimaryKey(
		long vocabularyId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(vocabularyId);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary findByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		return getPersistence().findByG_N(groupId, name);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary fetchByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_N(groupId, name);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> findByG_F(
		long groupId, boolean folksonomy)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_F(groupId, folksonomy);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> findByG_F(
		long groupId, boolean folksonomy, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_F(groupId, folksonomy, start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> findByG_F(
		long groupId, boolean folksonomy, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_F(groupId, folksonomy, start, end, obc);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary findByG_F_First(
		long groupId, boolean folksonomy,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		return getPersistence().findByG_F_First(groupId, folksonomy, obc);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary findByG_F_Last(
		long groupId, boolean folksonomy,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		return getPersistence().findByG_F_Last(groupId, folksonomy, obc);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary[] findByG_F_PrevAndNext(
		long vocabularyId, long groupId, boolean folksonomy,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		return getPersistence()
				   .findByG_F_PrevAndNext(vocabularyId, groupId, folksonomy, obc);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> findByC_F(
		long companyId, boolean folksonomy)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_F(companyId, folksonomy);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> findByC_F(
		long companyId, boolean folksonomy, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_F(companyId, folksonomy, start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> findByC_F(
		long companyId, boolean folksonomy, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_F(companyId, folksonomy, start, end, obc);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary findByC_F_First(
		long companyId, boolean folksonomy,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		return getPersistence().findByC_F_First(companyId, folksonomy, obc);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary findByC_F_Last(
		long companyId, boolean folksonomy,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		return getPersistence().findByC_F_Last(companyId, folksonomy, obc);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary[] findByC_F_PrevAndNext(
		long vocabularyId, long companyId, boolean folksonomy,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		return getPersistence()
				   .findByC_F_PrevAndNext(vocabularyId, companyId, folksonomy,
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

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		getPersistence().removeByG_N(groupId, name);
	}

	public static void removeByG_F(long groupId, boolean folksonomy)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_F(groupId, folksonomy);
	}

	public static void removeByC_F(long companyId, boolean folksonomy)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_F(companyId, folksonomy);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_N(groupId, name);
	}

	public static int countByG_F(long groupId, boolean folksonomy)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_F(groupId, folksonomy);
	}

	public static int countByC_F(long companyId, boolean folksonomy)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_F(companyId, folksonomy);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static TagsVocabularyPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(TagsVocabularyPersistence persistence) {
		_persistence = persistence;
	}

	private static TagsVocabularyPersistence _persistence;
}