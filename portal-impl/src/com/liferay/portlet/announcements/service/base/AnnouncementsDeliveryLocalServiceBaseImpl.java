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

package com.liferay.portlet.announcements.service.base;

import com.liferay.counter.service.CounterLocalService;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalService;
import com.liferay.portlet.announcements.service.AnnouncementsDeliveryService;
import com.liferay.portlet.announcements.service.AnnouncementsEntryLocalService;
import com.liferay.portlet.announcements.service.AnnouncementsEntryService;
import com.liferay.portlet.announcements.service.AnnouncementsFlagLocalService;
import com.liferay.portlet.announcements.service.AnnouncementsFlagService;
import com.liferay.portlet.announcements.service.persistence.AnnouncementsDeliveryPersistence;
import com.liferay.portlet.announcements.service.persistence.AnnouncementsEntryFinder;
import com.liferay.portlet.announcements.service.persistence.AnnouncementsEntryPersistence;
import com.liferay.portlet.announcements.service.persistence.AnnouncementsFlagPersistence;

import java.util.List;

/**
 * <a href="AnnouncementsDeliveryLocalServiceBaseImpl.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class AnnouncementsDeliveryLocalServiceBaseImpl
	implements AnnouncementsDeliveryLocalService {
	public AnnouncementsDelivery addAnnouncementsDelivery(
		AnnouncementsDelivery announcementsDelivery) throws SystemException {
		announcementsDelivery.setNew(true);

		return announcementsDeliveryPersistence.update(announcementsDelivery,
			false);
	}

	public AnnouncementsDelivery createAnnouncementsDelivery(long deliveryId) {
		return announcementsDeliveryPersistence.create(deliveryId);
	}

	public void deleteAnnouncementsDelivery(long deliveryId)
		throws PortalException, SystemException {
		announcementsDeliveryPersistence.remove(deliveryId);
	}

	public void deleteAnnouncementsDelivery(
		AnnouncementsDelivery announcementsDelivery) throws SystemException {
		announcementsDeliveryPersistence.remove(announcementsDelivery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return announcementsDeliveryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return announcementsDeliveryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		return announcementsDeliveryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public int dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return announcementsDeliveryPersistence.countWithDynamicQuery(dynamicQuery);
	}

	public AnnouncementsDelivery getAnnouncementsDelivery(long deliveryId)
		throws PortalException, SystemException {
		return announcementsDeliveryPersistence.findByPrimaryKey(deliveryId);
	}

	public List<AnnouncementsDelivery> getAnnouncementsDeliveries(int start,
		int end) throws SystemException {
		return announcementsDeliveryPersistence.findAll(start, end);
	}

	public int getAnnouncementsDeliveriesCount() throws SystemException {
		return announcementsDeliveryPersistence.countAll();
	}

	public AnnouncementsDelivery updateAnnouncementsDelivery(
		AnnouncementsDelivery announcementsDelivery) throws SystemException {
		announcementsDelivery.setNew(false);

		return announcementsDeliveryPersistence.update(announcementsDelivery,
			true);
	}

	public AnnouncementsDelivery updateAnnouncementsDelivery(
		AnnouncementsDelivery announcementsDelivery, boolean merge)
		throws SystemException {
		announcementsDelivery.setNew(false);

		return announcementsDeliveryPersistence.update(announcementsDelivery,
			merge);
	}

	public AnnouncementsDeliveryLocalService getAnnouncementsDeliveryLocalService() {
		return announcementsDeliveryLocalService;
	}

	public void setAnnouncementsDeliveryLocalService(
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService) {
		this.announcementsDeliveryLocalService = announcementsDeliveryLocalService;
	}

	public AnnouncementsDeliveryService getAnnouncementsDeliveryService() {
		return announcementsDeliveryService;
	}

	public void setAnnouncementsDeliveryService(
		AnnouncementsDeliveryService announcementsDeliveryService) {
		this.announcementsDeliveryService = announcementsDeliveryService;
	}

	public AnnouncementsDeliveryPersistence getAnnouncementsDeliveryPersistence() {
		return announcementsDeliveryPersistence;
	}

	public void setAnnouncementsDeliveryPersistence(
		AnnouncementsDeliveryPersistence announcementsDeliveryPersistence) {
		this.announcementsDeliveryPersistence = announcementsDeliveryPersistence;
	}

	public AnnouncementsEntryLocalService getAnnouncementsEntryLocalService() {
		return announcementsEntryLocalService;
	}

	public void setAnnouncementsEntryLocalService(
		AnnouncementsEntryLocalService announcementsEntryLocalService) {
		this.announcementsEntryLocalService = announcementsEntryLocalService;
	}

	public AnnouncementsEntryService getAnnouncementsEntryService() {
		return announcementsEntryService;
	}

	public void setAnnouncementsEntryService(
		AnnouncementsEntryService announcementsEntryService) {
		this.announcementsEntryService = announcementsEntryService;
	}

	public AnnouncementsEntryPersistence getAnnouncementsEntryPersistence() {
		return announcementsEntryPersistence;
	}

	public void setAnnouncementsEntryPersistence(
		AnnouncementsEntryPersistence announcementsEntryPersistence) {
		this.announcementsEntryPersistence = announcementsEntryPersistence;
	}

	public AnnouncementsEntryFinder getAnnouncementsEntryFinder() {
		return announcementsEntryFinder;
	}

	public void setAnnouncementsEntryFinder(
		AnnouncementsEntryFinder announcementsEntryFinder) {
		this.announcementsEntryFinder = announcementsEntryFinder;
	}

	public AnnouncementsFlagLocalService getAnnouncementsFlagLocalService() {
		return announcementsFlagLocalService;
	}

	public void setAnnouncementsFlagLocalService(
		AnnouncementsFlagLocalService announcementsFlagLocalService) {
		this.announcementsFlagLocalService = announcementsFlagLocalService;
	}

	public AnnouncementsFlagService getAnnouncementsFlagService() {
		return announcementsFlagService;
	}

	public void setAnnouncementsFlagService(
		AnnouncementsFlagService announcementsFlagService) {
		this.announcementsFlagService = announcementsFlagService;
	}

	public AnnouncementsFlagPersistence getAnnouncementsFlagPersistence() {
		return announcementsFlagPersistence;
	}

	public void setAnnouncementsFlagPersistence(
		AnnouncementsFlagPersistence announcementsFlagPersistence) {
		this.announcementsFlagPersistence = announcementsFlagPersistence;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	public ResourceFinder getResourceFinder() {
		return resourceFinder;
	}

	public void setResourceFinder(ResourceFinder resourceFinder) {
		this.resourceFinder = resourceFinder;
	}

	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	protected void runSQL(String sql) throws SystemException {
		try {
			DB db = DBFactoryUtil.getDB();

			db.runSQL(sql);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = AnnouncementsDeliveryLocalService.class)
	protected AnnouncementsDeliveryLocalService announcementsDeliveryLocalService;
	@BeanReference(type = AnnouncementsDeliveryService.class)
	protected AnnouncementsDeliveryService announcementsDeliveryService;
	@BeanReference(type = AnnouncementsDeliveryPersistence.class)
	protected AnnouncementsDeliveryPersistence announcementsDeliveryPersistence;
	@BeanReference(type = AnnouncementsEntryLocalService.class)
	protected AnnouncementsEntryLocalService announcementsEntryLocalService;
	@BeanReference(type = AnnouncementsEntryService.class)
	protected AnnouncementsEntryService announcementsEntryService;
	@BeanReference(type = AnnouncementsEntryPersistence.class)
	protected AnnouncementsEntryPersistence announcementsEntryPersistence;
	@BeanReference(type = AnnouncementsEntryFinder.class)
	protected AnnouncementsEntryFinder announcementsEntryFinder;
	@BeanReference(type = AnnouncementsFlagLocalService.class)
	protected AnnouncementsFlagLocalService announcementsFlagLocalService;
	@BeanReference(type = AnnouncementsFlagService.class)
	protected AnnouncementsFlagService announcementsFlagService;
	@BeanReference(type = AnnouncementsFlagPersistence.class)
	protected AnnouncementsFlagPersistence announcementsFlagPersistence;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
	@BeanReference(type = ResourceService.class)
	protected ResourceService resourceService;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceFinder.class)
	protected ResourceFinder resourceFinder;
	@BeanReference(type = UserLocalService.class)
	protected UserLocalService userLocalService;
	@BeanReference(type = UserService.class)
	protected UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
}