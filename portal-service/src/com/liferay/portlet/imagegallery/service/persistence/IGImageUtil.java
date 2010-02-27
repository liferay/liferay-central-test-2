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

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.imagegallery.model.IGImage;

import java.util.List;

/**
 * <a href="IGImageUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGImagePersistence
 * @see       IGImagePersistenceImpl
 * @generated
 */
public class IGImageUtil {
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
	public static IGImage remove(IGImage igImage) throws SystemException {
		return getPersistence().remove(igImage);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static IGImage update(IGImage igImage, boolean merge)
		throws SystemException {
		return getPersistence().update(igImage, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.imagegallery.model.IGImage igImage) {
		getPersistence().cacheResult(igImage);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.imagegallery.model.IGImage> igImages) {
		getPersistence().cacheResult(igImages);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage create(
		long imageId) {
		return getPersistence().create(imageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage remove(
		long imageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().remove(imageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage updateImpl(
		com.liferay.portlet.imagegallery.model.IGImage igImage, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(igImage, merge);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByPrimaryKey(
		long imageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByPrimaryKey(imageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage fetchByPrimaryKey(
		long imageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(imageId);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage[] findByUuid_PrevAndNext(
		long imageId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByUuid_PrevAndNext(imageId, uuid, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage[] findByGroupId_PrevAndNext(
		long imageId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByGroupId_PrevAndNext(imageId, groupId, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findBySmallImageId(smallImageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage fetchBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchBySmallImageId(smallImageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage fetchBySmallImageId(
		long smallImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchBySmallImageId(smallImageId, retrieveFromCache);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByLargeImageId(largeImageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage fetchByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByLargeImageId(largeImageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage fetchByLargeImageId(
		long largeImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByLargeImageId(largeImageId, retrieveFromCache);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByCustom1ImageId(
		long custom1ImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByCustom1ImageId(custom1ImageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage fetchByCustom1ImageId(
		long custom1ImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByCustom1ImageId(custom1ImageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage fetchByCustom1ImageId(
		long custom1ImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCustom1ImageId(custom1ImageId, retrieveFromCache);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByCustom2ImageId(
		long custom2ImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByCustom2ImageId(custom2ImageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage fetchByCustom2ImageId(
		long custom2ImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByCustom2ImageId(custom2ImageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage fetchByCustom2ImageId(
		long custom2ImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCustom2ImageId(custom2ImageId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId, start, end, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByG_U_First(groupId, userId, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByG_U_Last(groupId, userId, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage[] findByG_U_PrevAndNext(
		long imageId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .findByG_U_PrevAndNext(imageId, groupId, userId, obc);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderId);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderId, start, end);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderId, start, end, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByG_F_First(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByG_F_First(groupId, folderId, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByG_F_Last(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByG_F_Last(groupId, folderId, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage[] findByG_F_PrevAndNext(
		long imageId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .findByG_F_PrevAndNext(imageId, groupId, folderId, obc);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F_N(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F_N(groupId, folderId, name);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F_N(
		long groupId, long folderId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F_N(groupId, folderId, name, start, end);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F_N(
		long groupId, long folderId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F_N(groupId, folderId, name, start, end, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByG_F_N_First(
		long groupId, long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByG_F_N_First(groupId, folderId, name, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage findByG_F_N_Last(
		long groupId, long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByG_F_N_Last(groupId, folderId, name, obc);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage[] findByG_F_N_PrevAndNext(
		long imageId, long groupId, long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .findByG_F_N_PrevAndNext(imageId, groupId, folderId, name,
			obc);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findAll(
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
			com.liferay.portlet.imagegallery.NoSuchImageException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeBySmallImageId(long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		getPersistence().removeBySmallImageId(smallImageId);
	}

	public static void removeByLargeImageId(long largeImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		getPersistence().removeByLargeImageId(largeImageId);
	}

	public static void removeByCustom1ImageId(long custom1ImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		getPersistence().removeByCustom1ImageId(custom1ImageId);
	}

	public static void removeByCustom2ImageId(long custom2ImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		getPersistence().removeByCustom2ImageId(custom2ImageId);
	}

	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_U(groupId, userId);
	}

	public static void removeByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_F(groupId, folderId);
	}

	public static void removeByG_F_N(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_F_N(groupId, folderId, name);
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

	public static int countBySmallImageId(long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBySmallImageId(smallImageId);
	}

	public static int countByLargeImageId(long largeImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByLargeImageId(largeImageId);
	}

	public static int countByCustom1ImageId(long custom1ImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCustom1ImageId(custom1ImageId);
	}

	public static int countByCustom2ImageId(long custom2ImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCustom2ImageId(custom2ImageId);
	}

	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	public static int countByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_F(groupId, folderId);
	}

	public static int countByG_F_N(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_F_N(groupId, folderId, name);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static IGImagePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (IGImagePersistence)PortalBeanLocatorUtil.locate(IGImagePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(IGImagePersistence persistence) {
		_persistence = persistence;
	}

	private static IGImagePersistence _persistence;
}