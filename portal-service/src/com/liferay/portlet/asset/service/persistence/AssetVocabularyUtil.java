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
 * <a href="AssetVocabularyUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetVocabularyUtil {
	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary) {
		getPersistence().cacheResult(assetVocabulary);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> assetVocabularies) {
		getPersistence().cacheResult(assetVocabularies);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary create(
		long vocabularyId) {
		return getPersistence().create(vocabularyId);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary remove(
		long vocabularyId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().remove(vocabularyId);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary remove(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(assetVocabulary);
	}

	/**
	 * @deprecated Use <code>update(AssetVocabulary assetVocabulary, boolean merge)</code>.
	 */
	public static com.liferay.portlet.asset.model.AssetVocabulary update(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(assetVocabulary);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        assetVocabulary the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when assetVocabulary is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.asset.model.AssetVocabulary update(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(assetVocabulary, merge);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary updateImpl(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(assetVocabulary, merge);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary findByPrimaryKey(
		long vocabularyId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().findByPrimaryKey(vocabularyId);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByPrimaryKey(
		long vocabularyId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary[] findByGroupId_PrevAndNext(
		long vocabularyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(vocabularyId, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary[] findByCompanyId_PrevAndNext(
		long vocabularyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(vocabularyId, companyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary findByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getPersistence().findByG_N(groupId, name);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_N(groupId, name);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary fetchByG_N(
		long groupId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_N(groupId, name, retrieveFromCache);
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

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		getPersistence().removeByG_N(groupId, name);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_N(groupId, name);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static AssetVocabularyPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(AssetVocabularyPersistence persistence) {
		_persistence = persistence;
	}

	private static AssetVocabularyPersistence _persistence;
}