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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.messageboards.model.MBCategory;

import java.util.List;

/**
 * <a href="MBCategoryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBCategoryPersistence
 * @see       MBCategoryPersistenceImpl
 * @generated
 */
public class MBCategoryUtil {
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
	public static MBCategory remove(MBCategory mbCategory)
		throws SystemException {
		return getPersistence().remove(mbCategory);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static MBCategory update(MBCategory mbCategory, boolean merge)
		throws SystemException {
		return getPersistence().update(mbCategory, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory) {
		getPersistence().cacheResult(mbCategory);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBCategory> mbCategories) {
		getPersistence().cacheResult(mbCategories);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory create(
		long categoryId) {
		return getPersistence().create(categoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory remove(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().remove(categoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory updateImpl(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mbCategory, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByPrimaryKey(categoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory fetchByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(categoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory[] findByUuid_PrevAndNext(
		long categoryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByUuid_PrevAndNext(categoryId, uuid, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory[] findByGroupId_PrevAndNext(
		long categoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(categoryId, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory[] findByCompanyId_PrevAndNext(
		long categoryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(categoryId, companyId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentCategoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentCategoryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P(groupId, parentCategoryId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByG_P_First(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByG_P_First(groupId, parentCategoryId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory findByG_P_Last(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByG_P_Last(groupId, parentCategoryId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory[] findByG_P_PrevAndNext(
		long categoryId, long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence()
				   .findByG_P_PrevAndNext(categoryId, groupId,
			parentCategoryId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByG_P(long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P(groupId, parentCategoryId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByG_P(long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P(groupId, parentCategoryId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MBCategoryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MBCategoryPersistence)PortalBeanLocatorUtil.locate(MBCategoryPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(MBCategoryPersistence persistence) {
		_persistence = persistence;
	}

	private static MBCategoryPersistence _persistence;
}