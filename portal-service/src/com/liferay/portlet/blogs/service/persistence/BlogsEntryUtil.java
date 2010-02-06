/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.portlet.blogs.model.BlogsEntry;

import java.util.List;

/**
 * <a href="BlogsEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsEntryPersistence
 * @see       BlogsEntryPersistenceImpl
 * @generated
 */
public class BlogsEntryUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static BlogsEntry remove(BlogsEntry blogsEntry)
		throws SystemException {
		return getPersistence().remove(blogsEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static BlogsEntry update(BlogsEntry blogsEntry, boolean merge)
		throws SystemException {
		return getPersistence().update(blogsEntry, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry) {
		getPersistence().cacheResult(blogsEntry);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> blogsEntries) {
		getPersistence().cacheResult(blogsEntries);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry create(
		long entryId) {
		return getPersistence().create(entryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry remove(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry updateImpl(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(blogsEntry, merge);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry fetchByPrimaryKey(
		long entryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByUuid_PrevAndNext(
		long entryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByUuid_PrevAndNext(entryId, uuid, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByGroupId_PrevAndNext(
		long entryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByGroupId_PrevAndNext(entryId, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByCompanyId_PrevAndNext(
		long entryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(entryId, companyId, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_U(
		long companyId, long userId) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_U(companyId, userId);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_U(
		long companyId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_U(companyId, userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_U(
		long companyId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_U(companyId, userId, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByC_U_First(
		long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByC_U_First(companyId, userId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByC_U_Last(
		long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByC_U_Last(companyId, userId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByC_U_PrevAndNext(
		long entryId, long companyId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_U_PrevAndNext(entryId, companyId, userId, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_D(
		long companyId, java.util.Date displayDate)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_D(companyId, displayDate);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_D(
		long companyId, java.util.Date displayDate, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_D(companyId, displayDate, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_D(
		long companyId, java.util.Date displayDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_D(companyId, displayDate, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByC_D_First(
		long companyId, java.util.Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByC_D_First(companyId, displayDate, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByC_D_Last(
		long companyId, java.util.Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByC_D_Last(companyId, displayDate, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByC_D_PrevAndNext(
		long entryId, long companyId, java.util.Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_D_PrevAndNext(entryId, companyId, displayDate, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_S(
		long companyId, int status) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_S(companyId, status);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_S(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_S(companyId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_S(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_S(companyId, status, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByC_S_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByC_S_First(companyId, status, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByC_S_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByC_S_Last(companyId, status, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByC_S_PrevAndNext(
		long entryId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_S_PrevAndNext(entryId, companyId, status, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_UT(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByG_UT(groupId, urlTitle);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry fetchByG_UT(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_UT(groupId, urlTitle);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry fetchByG_UT(
		long groupId, java.lang.String urlTitle, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_UT(groupId, urlTitle, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_D(
		long groupId, java.util.Date displayDate)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_D(groupId, displayDate);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_D(
		long groupId, java.util.Date displayDate, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_D(groupId, displayDate, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_D(
		long groupId, java.util.Date displayDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_D(groupId, displayDate, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_D_First(
		long groupId, java.util.Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByG_D_First(groupId, displayDate, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_D_Last(
		long groupId, java.util.Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByG_D_Last(groupId, displayDate, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByG_D_PrevAndNext(
		long entryId, long groupId, java.util.Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_D_PrevAndNext(entryId, groupId, displayDate, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_S(
		long groupId, int status) throws com.liferay.portal.SystemException {
		return getPersistence().findByG_S(groupId, status);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_S(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_S(groupId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_S(groupId, status, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByG_S_First(groupId, status, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByG_S_Last(groupId, status, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByG_S_PrevAndNext(
		long entryId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_S_PrevAndNext(entryId, groupId, status, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_U_S(
		long companyId, long userId, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_U_S(companyId, userId, status);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_U_S(
		long companyId, long userId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_U_S(companyId, userId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_U_S(
		long companyId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_U_S(companyId, userId, status, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByC_U_S_First(
		long companyId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByC_U_S_First(companyId, userId, status, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByC_U_S_Last(
		long companyId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByC_U_S_Last(companyId, userId, status, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByC_U_S_PrevAndNext(
		long entryId, long companyId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_U_S_PrevAndNext(entryId, companyId, userId, status,
			obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_D_S(
		long companyId, java.util.Date displayDate, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_D_S(companyId, displayDate, status);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_D_S(
		long companyId, java.util.Date displayDate, int status, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_D_S(companyId, displayDate, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_D_S(
		long companyId, java.util.Date displayDate, int status, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_D_S(companyId, displayDate, status, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByC_D_S_First(
		long companyId, java.util.Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_D_S_First(companyId, displayDate, status, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByC_D_S_Last(
		long companyId, java.util.Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_D_S_Last(companyId, displayDate, status, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByC_D_S_PrevAndNext(
		long entryId, long companyId, java.util.Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_D_S_PrevAndNext(entryId, companyId, displayDate,
			status, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_D(
		long groupId, long userId, java.util.Date displayDate)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U_D(groupId, userId, displayDate);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_D(
		long groupId, long userId, java.util.Date displayDate, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_U_D(groupId, userId, displayDate, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_D(
		long groupId, long userId, java.util.Date displayDate, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_U_D(groupId, userId, displayDate, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_U_D_First(
		long groupId, long userId, java.util.Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_D_First(groupId, userId, displayDate, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_U_D_Last(
		long groupId, long userId, java.util.Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_D_Last(groupId, userId, displayDate, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByG_U_D_PrevAndNext(
		long entryId, long groupId, long userId, java.util.Date displayDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_D_PrevAndNext(entryId, groupId, userId,
			displayDate, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_S(
		long groupId, long userId, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U_S(groupId, userId, status);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_S(
		long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U_S(groupId, userId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_S(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_U_S(groupId, userId, status, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_U_S_First(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByG_U_S_First(groupId, userId, status, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_U_S_Last(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByG_U_S_Last(groupId, userId, status, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByG_U_S_PrevAndNext(
		long entryId, long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_S_PrevAndNext(entryId, groupId, userId, status,
			obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_D_S(
		long groupId, java.util.Date displayDate, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_D_S(groupId, displayDate, status);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_D_S(
		long groupId, java.util.Date displayDate, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_D_S(groupId, displayDate, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_D_S(
		long groupId, java.util.Date displayDate, int status, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_D_S(groupId, displayDate, status, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_D_S_First(
		long groupId, java.util.Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_D_S_First(groupId, displayDate, status, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_D_S_Last(
		long groupId, java.util.Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_D_S_Last(groupId, displayDate, status, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByG_D_S_PrevAndNext(
		long entryId, long groupId, java.util.Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_D_S_PrevAndNext(entryId, groupId, displayDate,
			status, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_D_S(
		long groupId, long userId, java.util.Date displayDate, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_U_D_S(groupId, userId, displayDate, status);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_D_S(
		long groupId, long userId, java.util.Date displayDate, int status,
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_U_D_S(groupId, userId, displayDate, status, start,
			end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_D_S(
		long groupId, long userId, java.util.Date displayDate, int status,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_U_D_S(groupId, userId, displayDate, status, start,
			end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_U_D_S_First(
		long groupId, long userId, java.util.Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_D_S_First(groupId, userId, displayDate, status,
			obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_U_D_S_Last(
		long groupId, long userId, java.util.Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_D_S_Last(groupId, userId, displayDate, status, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByG_U_D_S_PrevAndNext(
		long entryId, long groupId, long userId, java.util.Date displayDate,
		int status, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_D_S_PrevAndNext(entryId, groupId, userId,
			displayDate, status, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByC_U(long companyId, long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_U(companyId, userId);
	}

	public static void removeByC_D(long companyId, java.util.Date displayDate)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_D(companyId, displayDate);
	}

	public static void removeByC_S(long companyId, int status)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_S(companyId, status);
	}

	public static void removeByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		getPersistence().removeByG_UT(groupId, urlTitle);
	}

	public static void removeByG_D(long groupId, java.util.Date displayDate)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_D(groupId, displayDate);
	}

	public static void removeByG_S(long groupId, int status)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_S(groupId, status);
	}

	public static void removeByC_U_S(long companyId, long userId, int status)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_U_S(companyId, userId, status);
	}

	public static void removeByC_D_S(long companyId,
		java.util.Date displayDate, int status)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_D_S(companyId, displayDate, status);
	}

	public static void removeByG_U_D(long groupId, long userId,
		java.util.Date displayDate) throws com.liferay.portal.SystemException {
		getPersistence().removeByG_U_D(groupId, userId, displayDate);
	}

	public static void removeByG_U_S(long groupId, long userId, int status)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_U_S(groupId, userId, status);
	}

	public static void removeByG_D_S(long groupId, java.util.Date displayDate,
		int status) throws com.liferay.portal.SystemException {
		getPersistence().removeByG_D_S(groupId, displayDate, status);
	}

	public static void removeByG_U_D_S(long groupId, long userId,
		java.util.Date displayDate, int status)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_U_D_S(groupId, userId, displayDate, status);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByC_U(long companyId, long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_U(companyId, userId);
	}

	public static int countByC_D(long companyId, java.util.Date displayDate)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_D(companyId, displayDate);
	}

	public static int countByC_S(long companyId, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_S(companyId, status);
	}

	public static int countByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_UT(groupId, urlTitle);
	}

	public static int countByG_D(long groupId, java.util.Date displayDate)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_D(groupId, displayDate);
	}

	public static int countByG_S(long groupId, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_S(groupId, status);
	}

	public static int countByC_U_S(long companyId, long userId, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_U_S(companyId, userId, status);
	}

	public static int countByC_D_S(long companyId, java.util.Date displayDate,
		int status) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_D_S(companyId, displayDate, status);
	}

	public static int countByG_U_D(long groupId, long userId,
		java.util.Date displayDate) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_U_D(groupId, userId, displayDate);
	}

	public static int countByG_U_S(long groupId, long userId, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_U_S(groupId, userId, status);
	}

	public static int countByG_D_S(long groupId, java.util.Date displayDate,
		int status) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_D_S(groupId, displayDate, status);
	}

	public static int countByG_U_D_S(long groupId, long userId,
		java.util.Date displayDate, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .countByG_U_D_S(groupId, userId, displayDate, status);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static BlogsEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (BlogsEntryPersistence)PortalBeanLocatorUtil.locate(BlogsEntryPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(BlogsEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static BlogsEntryPersistence _persistence;
}