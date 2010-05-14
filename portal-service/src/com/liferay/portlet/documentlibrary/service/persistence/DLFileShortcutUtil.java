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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.documentlibrary.model.DLFileShortcut;

import java.util.List;

/**
 * <a href="DLFileShortcutUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileShortcutPersistence
 * @see       DLFileShortcutPersistenceImpl
 * @generated
 */
public class DLFileShortcutUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(DLFileShortcut)
	 */
	public static void clearCache(DLFileShortcut dlFileShortcut) {
		getPersistence().clearCache(dlFileShortcut);
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
	public static List<DLFileShortcut> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLFileShortcut> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static DLFileShortcut remove(DLFileShortcut dlFileShortcut)
		throws SystemException {
		return getPersistence().remove(dlFileShortcut);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static DLFileShortcut update(DLFileShortcut dlFileShortcut,
		boolean merge) throws SystemException {
		return getPersistence().update(dlFileShortcut, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut) {
		getPersistence().cacheResult(dlFileShortcut);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> dlFileShortcuts) {
		getPersistence().cacheResult(dlFileShortcuts);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut create(
		long fileShortcutId) {
		return getPersistence().create(fileShortcutId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut remove(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().remove(fileShortcutId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(dlFileShortcut, merge);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByPrimaryKey(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().findByPrimaryKey(fileShortcutId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByPrimaryKey(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(fileShortcutId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByUuid_PrevAndNext(
		long fileShortcutId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByUuid_PrevAndNext(fileShortcutId, uuid,
			orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderId, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F(groupId, folderId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_First(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_First(groupId, folderId, orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_Last(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_Last(groupId, folderId, orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByG_F_PrevAndNext(
		long fileShortcutId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_PrevAndNext(fileShortcutId, groupId, folderId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F(groupId, folderId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F(groupId, folderId, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_F(groupId, folderId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S(
		long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F_S(groupId, folderId, status);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F_S(groupId, folderId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F_S(groupId, folderId, status, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_S_First(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_S_First(groupId, folderId, status,
			orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_F_S_Last(
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_S_Last(groupId, folderId, status,
			orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByG_F_S_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_F_S_PrevAndNext(fileShortcutId, groupId, folderId,
			status, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_S(
		long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F_S(groupId, folderId, status);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_S(
		long groupId, long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_F_S(groupId, folderId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_F_S(
		long groupId, long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_F_S(groupId, folderId, status, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_TF_TN(
		long groupId, long toFolderId, java.lang.String toName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_TF_TN(groupId, toFolderId, toName);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_TF_TN(
		long groupId, long toFolderId, java.lang.String toName, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_TF_TN(groupId, toFolderId, toName, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_TF_TN(
		long groupId, long toFolderId, java.lang.String toName, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_TF_TN(groupId, toFolderId, toName, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_TF_TN_First(
		long groupId, long toFolderId, java.lang.String toName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_TF_TN_First(groupId, toFolderId, toName,
			orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_TF_TN_Last(
		long groupId, long toFolderId, java.lang.String toName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_TF_TN_Last(groupId, toFolderId, toName,
			orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByG_TF_TN_PrevAndNext(
		long fileShortcutId, long groupId, long toFolderId,
		java.lang.String toName,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_TF_TN_PrevAndNext(fileShortcutId, groupId,
			toFolderId, toName, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_TF_TN(
		long groupId, long toFolderId, java.lang.String toName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_TF_TN(groupId, toFolderId, toName);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_TF_TN(
		long groupId, long toFolderId, java.lang.String toName, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_TF_TN(groupId, toFolderId, toName, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_TF_TN(
		long groupId, long toFolderId, java.lang.String toName, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_TF_TN(groupId, toFolderId, toName, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_TF_TN_S(
		long groupId, long toFolderId, java.lang.String toName, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_TF_TN_S(groupId, toFolderId, toName, status);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_TF_TN_S(
		long groupId, long toFolderId, java.lang.String toName, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_TF_TN_S(groupId, toFolderId, toName, status, start,
			end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findByG_TF_TN_S(
		long groupId, long toFolderId, java.lang.String toName, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_TF_TN_S(groupId, toFolderId, toName, status, start,
			end, orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_TF_TN_S_First(
		long groupId, long toFolderId, java.lang.String toName, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_TF_TN_S_First(groupId, toFolderId, toName, status,
			orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut findByG_TF_TN_S_Last(
		long groupId, long toFolderId, java.lang.String toName, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_TF_TN_S_Last(groupId, toFolderId, toName, status,
			orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut[] findByG_TF_TN_S_PrevAndNext(
		long fileShortcutId, long groupId, long toFolderId,
		java.lang.String toName, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		return getPersistence()
				   .findByG_TF_TN_S_PrevAndNext(fileShortcutId, groupId,
			toFolderId, toName, status, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_TF_TN_S(
		long groupId, long toFolderId, java.lang.String toName, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_TF_TN_S(groupId, toFolderId, toName, status);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_TF_TN_S(
		long groupId, long toFolderId, java.lang.String toName, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_TF_TN_S(groupId, toFolderId, toName, status,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> filterFindByG_TF_TN_S(
		long groupId, long toFolderId, java.lang.String toName, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_TF_TN_S(groupId, toFolderId, toName, status,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileShortcutException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_F(groupId, folderId);
	}

	public static void removeByG_F_S(long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_F_S(groupId, folderId, status);
	}

	public static void removeByG_TF_TN(long groupId, long toFolderId,
		java.lang.String toName)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_TF_TN(groupId, toFolderId, toName);
	}

	public static void removeByG_TF_TN_S(long groupId, long toFolderId,
		java.lang.String toName, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_TF_TN_S(groupId, toFolderId, toName, status);
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

	public static int countByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_F(groupId, folderId);
	}

	public static int filterCountByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_F(groupId, folderId);
	}

	public static int countByG_F_S(long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_F_S(groupId, folderId, status);
	}

	public static int filterCountByG_F_S(long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_F_S(groupId, folderId, status);
	}

	public static int countByG_TF_TN(long groupId, long toFolderId,
		java.lang.String toName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_TF_TN(groupId, toFolderId, toName);
	}

	public static int filterCountByG_TF_TN(long groupId, long toFolderId,
		java.lang.String toName)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_TF_TN(groupId, toFolderId, toName);
	}

	public static int countByG_TF_TN_S(long groupId, long toFolderId,
		java.lang.String toName, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_TF_TN_S(groupId, toFolderId, toName, status);
	}

	public static int filterCountByG_TF_TN_S(long groupId, long toFolderId,
		java.lang.String toName, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterCountByG_TF_TN_S(groupId, toFolderId, toName, status);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DLFileShortcutPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLFileShortcutPersistence)PortalBeanLocatorUtil.locate(DLFileShortcutPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(DLFileShortcutPersistence persistence) {
		_persistence = persistence;
	}

	private static DLFileShortcutPersistence _persistence;
}