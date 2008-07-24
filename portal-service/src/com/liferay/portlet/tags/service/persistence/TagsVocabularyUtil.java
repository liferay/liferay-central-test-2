/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsVocabulary> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary[] findByCompanyId_PrevAndNext(
		long vocabularyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(vocabularyId, companyId, obc);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary findByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		return getPersistence().findByC_N(companyId, name);
	}

	public static com.liferay.portlet.tags.model.TagsVocabulary fetchByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_N(companyId, name);
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

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tags.NoSuchVocabularyException {
		getPersistence().removeByC_N(companyId, name);
	}

	public static void removeByC_F(long companyId, boolean folksonomy)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_F(companyId, folksonomy);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_N(companyId, name);
	}

	public static int countByC_F(long companyId, boolean folksonomy)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_F(companyId, folksonomy);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void registerListener(
		com.liferay.portal.model.ModelListener listener) {
		getPersistence().registerListener(listener);
	}

	public static void unregisterListener(
		com.liferay.portal.model.ModelListener listener) {
		getPersistence().unregisterListener(listener);
	}

	public static TagsVocabularyPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(TagsVocabularyPersistence persistence) {
		_persistence = persistence;
	}

	private static TagsVocabularyUtil _getUtil() {
		if (_util == null) {
			_util = (TagsVocabularyUtil)com.liferay.portal.kernel.bean.PortalBeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = TagsVocabularyUtil.class.getName();
	private static TagsVocabularyUtil _util;
	private TagsVocabularyPersistence _persistence;
}