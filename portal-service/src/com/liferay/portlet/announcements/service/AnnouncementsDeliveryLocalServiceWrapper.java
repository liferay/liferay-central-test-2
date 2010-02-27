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

package com.liferay.portlet.announcements.service;


/**
 * <a href="AnnouncementsDeliveryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AnnouncementsDeliveryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsDeliveryLocalService
 * @generated
 */
public class AnnouncementsDeliveryLocalServiceWrapper
	implements AnnouncementsDeliveryLocalService {
	public AnnouncementsDeliveryLocalServiceWrapper(
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService) {
		_announcementsDeliveryLocalService = announcementsDeliveryLocalService;
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery addAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.addAnnouncementsDelivery(announcementsDelivery);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery createAnnouncementsDelivery(
		long deliveryId) {
		return _announcementsDeliveryLocalService.createAnnouncementsDelivery(deliveryId);
	}

	public void deleteAnnouncementsDelivery(long deliveryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_announcementsDeliveryLocalService.deleteAnnouncementsDelivery(deliveryId);
	}

	public void deleteAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.kernel.exception.SystemException {
		_announcementsDeliveryLocalService.deleteAnnouncementsDelivery(announcementsDelivery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery getAnnouncementsDelivery(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.getAnnouncementsDelivery(deliveryId);
	}

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> getAnnouncementsDeliveries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.getAnnouncementsDeliveries(start,
			end);
	}

	public int getAnnouncementsDeliveriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.getAnnouncementsDeliveriesCount();
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery updateAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.updateAnnouncementsDelivery(announcementsDelivery);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery updateAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.updateAnnouncementsDelivery(announcementsDelivery,
			merge);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery addUserDelivery(
		long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.addUserDelivery(userId, type);
	}

	public void deleteDeliveries(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_announcementsDeliveryLocalService.deleteDeliveries(userId);
	}

	public void deleteDelivery(long deliveryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_announcementsDeliveryLocalService.deleteDelivery(deliveryId);
	}

	public void deleteDelivery(long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		_announcementsDeliveryLocalService.deleteDelivery(userId, type);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery getDelivery(
		long deliveryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.getDelivery(deliveryId);
	}

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> getUserDeliveries(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.getUserDeliveries(userId);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery getUserDelivery(
		long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.getUserDelivery(userId, type);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery updateDelivery(
		long userId, java.lang.String type, boolean email, boolean sms,
		boolean website)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDeliveryLocalService.updateDelivery(userId, type,
			email, sms, website);
	}

	public AnnouncementsDeliveryLocalService getWrappedAnnouncementsDeliveryLocalService() {
		return _announcementsDeliveryLocalService;
	}

	private AnnouncementsDeliveryLocalService _announcementsDeliveryLocalService;
}