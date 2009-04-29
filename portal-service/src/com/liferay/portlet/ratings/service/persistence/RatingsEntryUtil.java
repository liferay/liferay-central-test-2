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

package com.liferay.portlet.ratings.service.persistence;

/**
 * <a href="RatingsEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RatingsEntryUtil {
	public static void cacheResult(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry) {
		getPersistence().cacheResult(ratingsEntry);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> ratingsEntries) {
		getPersistence().cacheResult(ratingsEntries);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry create(
		long entryId) {
		return getPersistence().create(entryId);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry remove(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry remove(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(ratingsEntry);
	}

	/**
	 * @deprecated Use <code>update(RatingsEntry ratingsEntry, boolean merge)</code>.
	 */
	public static com.liferay.portlet.ratings.model.RatingsEntry update(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(ratingsEntry);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        ratingsEntry the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when ratingsEntry is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.ratings.model.RatingsEntry update(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(ratingsEntry, merge);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry updateImpl(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(ratingsEntry, merge);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByPrimaryKey(
		long entryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, start, end, obc);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByC_C_First(classNameId, classPK, obc);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByC_C_Last(classNameId, classPK, obc);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry[] findByC_C_PrevAndNext(
		long entryId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_PrevAndNext(entryId, classNameId, classPK, obc);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry findByU_C_C(
		long userId, long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		return getPersistence().findByU_C_C(userId, classNameId, classPK);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByU_C_C(
		long userId, long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByU_C_C(userId, classNameId, classPK);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry fetchByU_C_C(
		long userId, long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByU_C_C(userId, classNameId, classPK, retrieveFromCache);
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

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeByU_C_C(long userId, long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.ratings.NoSuchEntryException {
		getPersistence().removeByU_C_C(userId, classNameId, classPK);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countByU_C_C(long userId, long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByU_C_C(userId, classNameId, classPK);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static RatingsEntryPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(RatingsEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static RatingsEntryPersistence _persistence;
}