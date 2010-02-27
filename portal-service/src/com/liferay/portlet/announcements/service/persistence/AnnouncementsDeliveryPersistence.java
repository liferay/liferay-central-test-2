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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.announcements.model.AnnouncementsDelivery;

/**
 * <a href="AnnouncementsDeliveryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsDeliveryPersistenceImpl
 * @see       AnnouncementsDeliveryUtil
 * @generated
 */
public interface AnnouncementsDeliveryPersistence extends BasePersistence<AnnouncementsDelivery> {
	public void cacheResult(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery);

	public void cacheResult(
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> announcementsDeliveries);

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery create(
		long deliveryId);

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery remove(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery findByPrimaryKey(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery fetchByPrimaryKey(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery[] findByUserId_PrevAndNext(
		long deliveryId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery findByU_T(
		long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery fetchByU_T(
		long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery fetchByU_T(
		long userId, java.lang.String type, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByU_T(long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByU_T(long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}