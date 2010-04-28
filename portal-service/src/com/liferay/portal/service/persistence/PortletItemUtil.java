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
import com.liferay.portal.model.PortletItem;

import java.util.List;

/**
 * <a href="PortletItemUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletItemPersistence
 * @see       PortletItemPersistenceImpl
 * @generated
 */
public class PortletItemUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
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
	public static List<PortletItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PortletItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static PortletItem remove(PortletItem portletItem)
		throws SystemException {
		return getPersistence().remove(portletItem);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static PortletItem update(PortletItem portletItem, boolean merge)
		throws SystemException {
		return getPersistence().update(portletItem, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.PortletItem portletItem) {
		getPersistence().cacheResult(portletItem);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.PortletItem> portletItems) {
		getPersistence().cacheResult(portletItems);
	}

	public static com.liferay.portal.model.PortletItem create(
		long portletItemId) {
		return getPersistence().create(portletItemId);
	}

	public static com.liferay.portal.model.PortletItem remove(
		long portletItemId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(portletItemId);
	}

	public static com.liferay.portal.model.PortletItem updateImpl(
		com.liferay.portal.model.PortletItem portletItem, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(portletItem, merge);
	}

	public static com.liferay.portal.model.PortletItem findByPrimaryKey(
		long portletItemId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(portletItemId);
	}

	public static com.liferay.portal.model.PortletItem fetchByPrimaryKey(
		long portletItemId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(portletItemId);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, classNameId);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, classNameId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C(groupId, classNameId, start, end,
			orderByComparator);
	}

	public static com.liferay.portal.model.PortletItem findByG_C_First(
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_First(groupId, classNameId, orderByComparator);
	}

	public static com.liferay.portal.model.PortletItem findByG_C_Last(
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_Last(groupId, classNameId, orderByComparator);
	}

	public static com.liferay.portal.model.PortletItem[] findByG_C_PrevAndNext(
		long portletItemId, long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_PrevAndNext(portletItemId, groupId, classNameId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P_C(groupId, portletId, classNameId);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_C(groupId, portletId, classNameId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_C(groupId, portletId, classNameId, start, end,
			orderByComparator);
	}

	public static com.liferay.portal.model.PortletItem findByG_P_C_First(
		long groupId, java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_C_First(groupId, portletId, classNameId,
			orderByComparator);
	}

	public static com.liferay.portal.model.PortletItem findByG_P_C_Last(
		long groupId, java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_C_Last(groupId, portletId, classNameId,
			orderByComparator);
	}

	public static com.liferay.portal.model.PortletItem[] findByG_P_C_PrevAndNext(
		long portletItemId, long groupId, java.lang.String portletId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P_C_PrevAndNext(portletItemId, groupId, portletId,
			classNameId, orderByComparator);
	}

	public static com.liferay.portal.model.PortletItem findByG_N_P_C(
		long groupId, java.lang.String name, java.lang.String portletId,
		long classNameId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_N_P_C(groupId, name, portletId, classNameId);
	}

	public static com.liferay.portal.model.PortletItem fetchByG_N_P_C(
		long groupId, java.lang.String name, java.lang.String portletId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_N_P_C(groupId, name, portletId, classNameId);
	}

	public static com.liferay.portal.model.PortletItem fetchByG_N_P_C(
		long groupId, java.lang.String name, java.lang.String portletId,
		long classNameId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_N_P_C(groupId, name, portletId, classNameId,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByG_C(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C(groupId, classNameId);
	}

	public static void removeByG_P_C(long groupId, java.lang.String portletId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P_C(groupId, portletId, classNameId);
	}

	public static void removeByG_N_P_C(long groupId, java.lang.String name,
		java.lang.String portletId, long classNameId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_N_P_C(groupId, name, portletId, classNameId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByG_C(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C(groupId, classNameId);
	}

	public static int countByG_P_C(long groupId, java.lang.String portletId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P_C(groupId, portletId, classNameId);
	}

	public static int countByG_N_P_C(long groupId, java.lang.String name,
		java.lang.String portletId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_N_P_C(groupId, name, portletId, classNameId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PortletItemPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PortletItemPersistence)PortalBeanLocatorUtil.locate(PortletItemPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(PortletItemPersistence persistence) {
		_persistence = persistence;
	}

	private static PortletItemPersistence _persistence;
}