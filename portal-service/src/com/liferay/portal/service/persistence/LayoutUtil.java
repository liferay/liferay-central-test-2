/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;

import java.util.List;

/**
 * <a href="LayoutUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutPersistence
 * @see       LayoutPersistenceImpl
 * @generated
 */
public class LayoutUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(Layout)
	 */
	public static void clearCache(Layout layout) {
		getPersistence().clearCache(layout);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Layout> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Layout> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static Layout remove(Layout layout) throws SystemException {
		return getPersistence().remove(layout);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Layout update(Layout layout, boolean merge)
		throws SystemException {
		return getPersistence().update(layout, merge);
	}

	public static void cacheResult(com.liferay.portal.model.Layout layout) {
		getPersistence().cacheResult(layout);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Layout> layouts) {
		getPersistence().cacheResult(layouts);
	}

	public static com.liferay.portal.model.Layout create(long plid) {
		return getPersistence().create(plid);
	}

	public static com.liferay.portal.model.Layout remove(long plid)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(plid);
	}

	public static com.liferay.portal.model.Layout updateImpl(
		com.liferay.portal.model.Layout layout, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(layout, merge);
	}

	public static com.liferay.portal.model.Layout findByPrimaryKey(long plid)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(plid);
	}

	public static com.liferay.portal.model.Layout fetchByPrimaryKey(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(plid);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.Layout findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	public static com.liferay.portal.model.Layout findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	public static com.liferay.portal.model.Layout[] findByGroupId_PrevAndNext(
		long plid, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(plid, groupId, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.Layout findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	public static com.liferay.portal.model.Layout findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	public static com.liferay.portal.model.Layout[] findByCompanyId_PrevAndNext(
		long plid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(plid, companyId,
			orderByComparator);
	}

	public static com.liferay.portal.model.Layout findByDLFolderId(
		long dlFolderId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByDLFolderId(dlFolderId);
	}

	public static com.liferay.portal.model.Layout fetchByDLFolderId(
		long dlFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByDLFolderId(dlFolderId);
	}

	public static com.liferay.portal.model.Layout fetchByDLFolderId(
		long dlFolderId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByDLFolderId(dlFolderId, retrieveFromCache);
	}

	public static com.liferay.portal.model.Layout findByIconImageId(
		long iconImageId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByIconImageId(iconImageId);
	}

	public static com.liferay.portal.model.Layout fetchByIconImageId(
		long iconImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByIconImageId(iconImageId);
	}

	public static com.liferay.portal.model.Layout fetchByIconImageId(
		long iconImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByIconImageId(iconImageId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByG_P(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, privateLayout);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByG_P(
		long groupId, boolean privateLayout, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, privateLayout, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P(groupId, privateLayout, start, end,
			orderByComparator);
	}

	public static com.liferay.portal.model.Layout findByG_P_First(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_First(groupId, privateLayout, orderByComparator);
	}

	public static com.liferay.portal.model.Layout findByG_P_Last(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_Last(groupId, privateLayout, orderByComparator);
	}

	public static com.liferay.portal.model.Layout[] findByG_P_PrevAndNext(
		long plid, long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_PrevAndNext(plid, groupId, privateLayout,
			orderByComparator);
	}

	public static com.liferay.portal.model.Layout findByG_P_L(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P_L(groupId, privateLayout, layoutId);
	}

	public static com.liferay.portal.model.Layout fetchByG_P_L(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_P_L(groupId, privateLayout, layoutId);
	}

	public static com.liferay.portal.model.Layout fetchByG_P_L(long groupId,
		boolean privateLayout, long layoutId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_P_L(groupId, privateLayout, layoutId,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_P(groupId, privateLayout, parentLayoutId, start,
			end);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_P(groupId, privateLayout, parentLayoutId, start,
			end, orderByComparator);
	}

	public static com.liferay.portal.model.Layout findByG_P_P_First(
		long groupId, boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_P_First(groupId, privateLayout, parentLayoutId,
			orderByComparator);
	}

	public static com.liferay.portal.model.Layout findByG_P_P_Last(
		long groupId, boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_P_Last(groupId, privateLayout, parentLayoutId,
			orderByComparator);
	}

	public static com.liferay.portal.model.Layout[] findByG_P_P_PrevAndNext(
		long plid, long groupId, boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_P_PrevAndNext(plid, groupId, privateLayout,
			parentLayoutId, orderByComparator);
	}

	public static com.liferay.portal.model.Layout findByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P_F(groupId, privateLayout, friendlyURL);
	}

	public static com.liferay.portal.model.Layout fetchByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_P_F(groupId, privateLayout, friendlyURL);
	}

	public static com.liferay.portal.model.Layout fetchByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_P_F(groupId, privateLayout, friendlyURL,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P_T(groupId, privateLayout, type);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_T(groupId, privateLayout, type, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_T(groupId, privateLayout, type, start, end,
			orderByComparator);
	}

	public static com.liferay.portal.model.Layout findByG_P_T_First(
		long groupId, boolean privateLayout, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_T_First(groupId, privateLayout, type,
			orderByComparator);
	}

	public static com.liferay.portal.model.Layout findByG_P_T_Last(
		long groupId, boolean privateLayout, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_T_Last(groupId, privateLayout, type,
			orderByComparator);
	}

	public static com.liferay.portal.model.Layout[] findByG_P_T_PrevAndNext(
		long plid, long groupId, boolean privateLayout, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_T_PrevAndNext(plid, groupId, privateLayout, type,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Layout> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByDLFolderId(long dlFolderId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByDLFolderId(dlFolderId);
	}

	public static void removeByIconImageId(long iconImageId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByIconImageId(iconImageId);
	}

	public static void removeByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P(groupId, privateLayout);
	}

	public static void removeByG_P_L(long groupId, boolean privateLayout,
		long layoutId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P_L(groupId, privateLayout, layoutId);
	}

	public static void removeByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	public static void removeByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P_F(groupId, privateLayout, friendlyURL);
	}

	public static void removeByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P_T(groupId, privateLayout, type);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByDLFolderId(long dlFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByDLFolderId(dlFolderId);
	}

	public static int countByIconImageId(long iconImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByIconImageId(iconImageId);
	}

	public static int countByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P(groupId, privateLayout);
	}

	public static int countByG_P_L(long groupId, boolean privateLayout,
		long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P_L(groupId, privateLayout, layoutId);
	}

	public static int countByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_P_P(groupId, privateLayout, parentLayoutId);
	}

	public static int countByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P_F(groupId, privateLayout, friendlyURL);
	}

	public static int countByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P_T(groupId, privateLayout, type);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LayoutPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutPersistence)PortalBeanLocatorUtil.locate(LayoutPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(LayoutPersistence persistence) {
		_persistence = persistence;
	}

	private static LayoutPersistence _persistence;
}