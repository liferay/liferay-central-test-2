/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.announcements.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.portlet.announcements.model.AnnouncementsFlag;
import com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalService;
import com.liferay.portlet.announcements.service.AnnouncementsDeliveryService;
import com.liferay.portlet.announcements.service.AnnouncementsEntryLocalService;
import com.liferay.portlet.announcements.service.AnnouncementsEntryService;
import com.liferay.portlet.announcements.service.AnnouncementsFlagLocalService;
import com.liferay.portlet.announcements.service.persistence.AnnouncementsDeliveryPersistence;
import com.liferay.portlet.announcements.service.persistence.AnnouncementsEntryFinder;
import com.liferay.portlet.announcements.service.persistence.AnnouncementsEntryPersistence;
import com.liferay.portlet.announcements.service.persistence.AnnouncementsFlagPersistence;

import java.util.List;

/**
 * <a href="AnnouncementsFlagLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class AnnouncementsFlagLocalServiceBaseImpl
	implements AnnouncementsFlagLocalService, InitializingBean {
	public AnnouncementsFlag addAnnouncementsFlag(
		AnnouncementsFlag announcementsFlag) throws SystemException {
		announcementsFlag.setNew(true);

		return announcementsFlagPersistence.update(announcementsFlag, false);
	}

	public void deleteAnnouncementsFlag(long flagId)
		throws PortalException, SystemException {
		announcementsFlagPersistence.remove(flagId);
	}

	public void deleteAnnouncementsFlag(AnnouncementsFlag announcementsFlag)
		throws SystemException {
		announcementsFlagPersistence.remove(announcementsFlag);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return announcementsFlagPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return announcementsFlagPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public AnnouncementsFlag getAnnouncementsFlag(long flagId)
		throws PortalException, SystemException {
		return announcementsFlagPersistence.findByPrimaryKey(flagId);
	}

	public List<AnnouncementsFlag> getAnnouncementsFlags(int start, int end)
		throws SystemException {
		return announcementsFlagPersistence.findAll(start, end);
	}

	public int getAnnouncementsFlagsCount() throws SystemException {
		return announcementsFlagPersistence.countAll();
	}

	public AnnouncementsFlag updateAnnouncementsFlag(
		AnnouncementsFlag announcementsFlag) throws SystemException {
		announcementsFlag.setNew(false);

		return announcementsFlagPersistence.update(announcementsFlag, true);
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

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public void afterPropertiesSet() {
		if (announcementsDeliveryLocalService == null) {
			announcementsDeliveryLocalService = (AnnouncementsDeliveryLocalService)PortalBeanLocatorUtil.locate(AnnouncementsDeliveryLocalService.class.getName() +
					".impl");
		}

		if (announcementsDeliveryService == null) {
			announcementsDeliveryService = (AnnouncementsDeliveryService)PortalBeanLocatorUtil.locate(AnnouncementsDeliveryService.class.getName() +
					".impl");
		}

		if (announcementsDeliveryPersistence == null) {
			announcementsDeliveryPersistence = (AnnouncementsDeliveryPersistence)PortalBeanLocatorUtil.locate(AnnouncementsDeliveryPersistence.class.getName() +
					".impl");
		}

		if (announcementsEntryLocalService == null) {
			announcementsEntryLocalService = (AnnouncementsEntryLocalService)PortalBeanLocatorUtil.locate(AnnouncementsEntryLocalService.class.getName() +
					".impl");
		}

		if (announcementsEntryService == null) {
			announcementsEntryService = (AnnouncementsEntryService)PortalBeanLocatorUtil.locate(AnnouncementsEntryService.class.getName() +
					".impl");
		}

		if (announcementsEntryPersistence == null) {
			announcementsEntryPersistence = (AnnouncementsEntryPersistence)PortalBeanLocatorUtil.locate(AnnouncementsEntryPersistence.class.getName() +
					".impl");
		}

		if (announcementsEntryFinder == null) {
			announcementsEntryFinder = (AnnouncementsEntryFinder)PortalBeanLocatorUtil.locate(AnnouncementsEntryFinder.class.getName() +
					".impl");
		}

		if (announcementsFlagPersistence == null) {
			announcementsFlagPersistence = (AnnouncementsFlagPersistence)PortalBeanLocatorUtil.locate(AnnouncementsFlagPersistence.class.getName() +
					".impl");
		}

		if (counterLocalService == null) {
			counterLocalService = (CounterLocalService)PortalBeanLocatorUtil.locate(CounterLocalService.class.getName() +
					".impl");
		}

		if (counterService == null) {
			counterService = (CounterService)PortalBeanLocatorUtil.locate(CounterService.class.getName() +
					".impl");
		}
	}

	protected AnnouncementsDeliveryLocalService announcementsDeliveryLocalService;
	protected AnnouncementsDeliveryService announcementsDeliveryService;
	protected AnnouncementsDeliveryPersistence announcementsDeliveryPersistence;
	protected AnnouncementsEntryLocalService announcementsEntryLocalService;
	protected AnnouncementsEntryService announcementsEntryService;
	protected AnnouncementsEntryPersistence announcementsEntryPersistence;
	protected AnnouncementsEntryFinder announcementsEntryFinder;
	protected AnnouncementsFlagPersistence announcementsFlagPersistence;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
}