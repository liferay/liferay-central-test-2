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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.blogs.model.BlogsStatsUser;

import java.util.List;

/**
 * <a href="BlogsStatsUserUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsStatsUserPersistence
 * @see       BlogsStatsUserPersistenceImpl
 * @generated
 */
public class BlogsStatsUserUtil {
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
	public static BlogsStatsUser remove(BlogsStatsUser blogsStatsUser)
		throws SystemException {
		return getPersistence().remove(blogsStatsUser);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static BlogsStatsUser update(BlogsStatsUser blogsStatsUser,
		boolean merge) throws SystemException {
		return getPersistence().update(blogsStatsUser, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser) {
		getPersistence().cacheResult(blogsStatsUser);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> blogsStatsUsers) {
		getPersistence().cacheResult(blogsStatsUsers);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser create(
		long statsUserId) {
		return getPersistence().create(statsUserId);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser remove(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence().remove(statsUserId);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser updateImpl(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(blogsStatsUser, merge);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser findByPrimaryKey(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence().findByPrimaryKey(statsUserId);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser fetchByPrimaryKey(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(statsUserId);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser[] findByGroupId_PrevAndNext(
		long statsUserId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(statsUserId, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser[] findByUserId_PrevAndNext(
		long statsUserId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence()
				   .findByUserId_PrevAndNext(statsUserId, userId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence().findByG_U(groupId, userId);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser fetchByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_U(groupId, userId);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser fetchByG_U(
		long groupId, long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_U(groupId, userId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByG_E(
		long groupId, int entryCount)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_E(groupId, entryCount);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByG_E(
		long groupId, int entryCount, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_E(groupId, entryCount, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByG_E(
		long groupId, int entryCount, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_E(groupId, entryCount, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser findByG_E_First(
		long groupId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence().findByG_E_First(groupId, entryCount, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser findByG_E_Last(
		long groupId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence().findByG_E_Last(groupId, entryCount, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser[] findByG_E_PrevAndNext(
		long statsUserId, long groupId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence()
				   .findByG_E_PrevAndNext(statsUserId, groupId, entryCount, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByC_E(
		long companyId, int entryCount)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_E(companyId, entryCount);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByC_E(
		long companyId, int entryCount, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_E(companyId, entryCount, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByC_E(
		long companyId, int entryCount, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_E(companyId, entryCount, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser findByC_E_First(
		long companyId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence().findByC_E_First(companyId, entryCount, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser findByC_E_Last(
		long companyId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence().findByC_E_Last(companyId, entryCount, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsStatsUser[] findByC_E_PrevAndNext(
		long statsUserId, long companyId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		return getPersistence()
				   .findByC_E_PrevAndNext(statsUserId, companyId, entryCount,
			obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException {
		getPersistence().removeByG_U(groupId, userId);
	}

	public static void removeByG_E(long groupId, int entryCount)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_E(groupId, entryCount);
	}

	public static void removeByC_E(long companyId, int entryCount)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_E(companyId, entryCount);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	public static int countByG_E(long groupId, int entryCount)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_E(groupId, entryCount);
	}

	public static int countByC_E(long companyId, int entryCount)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_E(companyId, entryCount);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static BlogsStatsUserPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (BlogsStatsUserPersistence)PortalBeanLocatorUtil.locate(BlogsStatsUserPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(BlogsStatsUserPersistence persistence) {
		_persistence = persistence;
	}

	private static BlogsStatsUserPersistence _persistence;
}