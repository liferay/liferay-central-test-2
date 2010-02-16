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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.documentlibrary.model.DLFileRank;

import java.util.List;

/**
 * <a href="DLFileRankUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileRankPersistence
 * @see       DLFileRankPersistenceImpl
 * @generated
 */
public class DLFileRankUtil {
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
	public static DLFileRank remove(DLFileRank dlFileRank)
		throws SystemException {
		return getPersistence().remove(dlFileRank);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static DLFileRank update(DLFileRank dlFileRank, boolean merge)
		throws SystemException {
		return getPersistence().update(dlFileRank, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank) {
		getPersistence().cacheResult(dlFileRank);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> dlFileRanks) {
		getPersistence().cacheResult(dlFileRanks);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank create(
		long fileRankId) {
		return getPersistence().create(fileRankId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank remove(
		long fileRankId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().remove(fileRankId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(dlFileRank, merge);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByPrimaryKey(
		long fileRankId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByPrimaryKey(fileRankId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank fetchByPrimaryKey(
		long fileRankId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(fileRankId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank[] findByUserId_PrevAndNext(
		long fileRankId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByUserId_PrevAndNext(fileRankId, userId, obc);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId, start, end, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByG_U_First(groupId, userId, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByG_U_Last(groupId, userId, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank[] findByG_U_PrevAndNext(
		long fileRankId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence()
				   .findByG_U_PrevAndNext(fileRankId, groupId, userId, obc);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByF_N(
		long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByF_N(folderId, name);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByF_N(
		long folderId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByF_N(folderId, name, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByF_N(
		long folderId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByF_N(folderId, name, start, end, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByF_N_First(
		long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByF_N_First(folderId, name, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByF_N_Last(
		long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByF_N_Last(folderId, name, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank[] findByF_N_PrevAndNext(
		long fileRankId, long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence()
				   .findByF_N_PrevAndNext(fileRankId, folderId, name, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByC_U_F_N(
		long companyId, long userId, long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		return getPersistence().findByC_U_F_N(companyId, userId, folderId, name);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank fetchByC_U_F_N(
		long companyId, long userId, long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_U_F_N(companyId, userId, folderId, name);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank fetchByC_U_F_N(
		long companyId, long userId, long folderId, java.lang.String name,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_U_F_N(companyId, userId, folderId, name,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_U(groupId, userId);
	}

	public static void removeByF_N(long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByF_N(folderId, name);
	}

	public static void removeByC_U_F_N(long companyId, long userId,
		long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileRankException {
		getPersistence().removeByC_U_F_N(companyId, userId, folderId, name);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	public static int countByF_N(long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByF_N(folderId, name);
	}

	public static int countByC_U_F_N(long companyId, long userId,
		long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_U_F_N(companyId, userId, folderId, name);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DLFileRankPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLFileRankPersistence)PortalBeanLocatorUtil.locate(DLFileRankPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(DLFileRankPersistence persistence) {
		_persistence = persistence;
	}

	private static DLFileRankPersistence _persistence;
}