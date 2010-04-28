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

import com.liferay.portlet.documentlibrary.model.DLFileVersion;

import java.util.List;

/**
 * <a href="DLFileVersionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileVersionPersistence
 * @see       DLFileVersionPersistenceImpl
 * @generated
 */
public class DLFileVersionUtil {
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
	public static List<DLFileVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLFileVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static DLFileVersion remove(DLFileVersion dlFileVersion)
		throws SystemException {
		return getPersistence().remove(dlFileVersion);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static DLFileVersion update(DLFileVersion dlFileVersion,
		boolean merge) throws SystemException {
		return getPersistence().update(dlFileVersion, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion) {
		getPersistence().cacheResult(dlFileVersion);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> dlFileVersions) {
		getPersistence().cacheResult(dlFileVersions);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion create(
		long fileVersionId) {
		return getPersistence().create(fileVersionId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion remove(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence().remove(fileVersionId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(dlFileVersion, merge);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion findByPrimaryKey(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence().findByPrimaryKey(fileVersionId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion fetchByPrimaryKey(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(fileVersionId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByG_F_N(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F_N(groupId, folderId, name);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByG_F_N(
		long groupId, long folderId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F_N(groupId, folderId, name, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByG_F_N(
		long groupId, long folderId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F_N(groupId, folderId, name, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion findByG_F_N_First(
		long groupId, long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByG_F_N_First(groupId, folderId, name, orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion findByG_F_N_Last(
		long groupId, long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByG_F_N_Last(groupId, folderId, name, orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion[] findByG_F_N_PrevAndNext(
		long fileVersionId, long groupId, long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByG_F_N_PrevAndNext(fileVersionId, groupId, folderId,
			name, orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion findByG_F_N_V(
		long groupId, long folderId, java.lang.String name,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence().findByG_F_N_V(groupId, folderId, name, version);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion fetchByG_F_N_V(
		long groupId, long folderId, java.lang.String name,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_F_N_V(groupId, folderId, name, version);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion fetchByG_F_N_V(
		long groupId, long folderId, java.lang.String name,
		java.lang.String version, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_F_N_V(groupId, folderId, name, version,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByG_F_N_S(
		long groupId, long folderId, java.lang.String name, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F_N_S(groupId, folderId, name, status);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByG_F_N_S(
		long groupId, long folderId, java.lang.String name, int status,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F_N_S(groupId, folderId, name, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByG_F_N_S(
		long groupId, long folderId, java.lang.String name, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F_N_S(groupId, folderId, name, status, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion findByG_F_N_S_First(
		long groupId, long folderId, java.lang.String name, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByG_F_N_S_First(groupId, folderId, name, status,
			orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion findByG_F_N_S_Last(
		long groupId, long folderId, java.lang.String name, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByG_F_N_S_Last(groupId, folderId, name, status,
			orderByComparator);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion[] findByG_F_N_S_PrevAndNext(
		long fileVersionId, long groupId, long folderId, java.lang.String name,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		return getPersistence()
				   .findByG_F_N_S_PrevAndNext(fileVersionId, groupId, folderId,
			name, status, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByG_F_N(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_F_N(groupId, folderId, name);
	}

	public static void removeByG_F_N_V(long groupId, long folderId,
		java.lang.String name, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException {
		getPersistence().removeByG_F_N_V(groupId, folderId, name, version);
	}

	public static void removeByG_F_N_S(long groupId, long folderId,
		java.lang.String name, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_F_N_S(groupId, folderId, name, status);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByG_F_N(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_F_N(groupId, folderId, name);
	}

	public static int countByG_F_N_V(long groupId, long folderId,
		java.lang.String name, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_F_N_V(groupId, folderId, name, version);
	}

	public static int countByG_F_N_S(long groupId, long folderId,
		java.lang.String name, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_F_N_S(groupId, folderId, name, status);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DLFileVersionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLFileVersionPersistence)PortalBeanLocatorUtil.locate(DLFileVersionPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(DLFileVersionPersistence persistence) {
		_persistence = persistence;
	}

	private static DLFileVersionPersistence _persistence;
}