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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.messageboards.model.MBMailingList;

import java.util.List;

/**
 * <a href="MBMailingListUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMailingListPersistence
 * @see       MBMailingListPersistenceImpl
 * @generated
 */
public class MBMailingListUtil {
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
	public static MBMailingList remove(MBMailingList mbMailingList)
		throws SystemException {
		return getPersistence().remove(mbMailingList);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static MBMailingList update(MBMailingList mbMailingList,
		boolean merge) throws SystemException {
		return getPersistence().update(mbMailingList, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList) {
		getPersistence().cacheResult(mbMailingList);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> mbMailingLists) {
		getPersistence().cacheResult(mbMailingLists);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList create(
		long mailingListId) {
		return getPersistence().create(mailingListId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList remove(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().remove(mailingListId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList updateImpl(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mbMailingList, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList findByPrimaryKey(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByPrimaryKey(mailingListId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByPrimaryKey(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(mailingListId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList[] findByUuid_PrevAndNext(
		long mailingListId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByUuid_PrevAndNext(mailingListId, uuid, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByActive(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActive(active);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActive(active, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActive(active, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList findByActive_First(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByActive_First(active, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList findByActive_Last(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByActive_Last(active, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList[] findByActive_PrevAndNext(
		long mailingListId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence()
				   .findByActive_PrevAndNext(mailingListId, active, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList findByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		return getPersistence().findByG_C(groupId, categoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_C(groupId, categoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBMailingList fetchByG_C(
		long groupId, long categoryId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C(groupId, categoryId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findAll(
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
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByActive(active);
	}

	public static void removeByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException {
		getPersistence().removeByG_C(groupId, categoryId);
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

	public static int countByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByActive(active);
	}

	public static int countByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C(groupId, categoryId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MBMailingListPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MBMailingListPersistence)PortalBeanLocatorUtil.locate(MBMailingListPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(MBMailingListPersistence persistence) {
		_persistence = persistence;
	}

	private static MBMailingListPersistence _persistence;
}