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
import com.liferay.counter.service.CounterLocalServiceFactory;
import com.liferay.counter.service.CounterService;
import com.liferay.counter.service.CounterServiceFactory;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;

import com.liferay.portlet.announcements.model.AnnouncementFlag;
import com.liferay.portlet.announcements.service.AnnouncementEntryLocalService;
import com.liferay.portlet.announcements.service.AnnouncementEntryLocalServiceFactory;
import com.liferay.portlet.announcements.service.AnnouncementEntryService;
import com.liferay.portlet.announcements.service.AnnouncementEntryServiceFactory;
import com.liferay.portlet.announcements.service.AnnouncementFlagLocalService;
import com.liferay.portlet.announcements.service.persistence.AnnouncementEntryFinder;
import com.liferay.portlet.announcements.service.persistence.AnnouncementEntryFinderUtil;
import com.liferay.portlet.announcements.service.persistence.AnnouncementEntryPersistence;
import com.liferay.portlet.announcements.service.persistence.AnnouncementEntryUtil;
import com.liferay.portlet.announcements.service.persistence.AnnouncementFlagPersistence;
import com.liferay.portlet.announcements.service.persistence.AnnouncementFlagUtil;

import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * <a href="AnnouncementFlagLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class AnnouncementFlagLocalServiceBaseImpl
	implements AnnouncementFlagLocalService, InitializingBean {
	public AnnouncementFlag addAnnouncementFlag(
		AnnouncementFlag announcementFlag) throws SystemException {
		announcementFlag.setNew(true);

		return announcementFlagPersistence.update(announcementFlag);
	}

	public void deleteAnnouncementFlag(long flagId)
		throws PortalException, SystemException {
		announcementFlagPersistence.remove(flagId);
	}

	public void deleteAnnouncementFlag(AnnouncementFlag announcementFlag)
		throws PortalException, SystemException {
		announcementFlagPersistence.remove(announcementFlag);
	}

	public List<AnnouncementFlag> dynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
		return announcementFlagPersistence.findWithDynamicQuery(queryInitializer);
	}

	public List<AnnouncementFlag> dynamicQuery(
		DynamicQueryInitializer queryInitializer, int begin, int end)
		throws SystemException {
		return announcementFlagPersistence.findWithDynamicQuery(queryInitializer,
			begin, end);
	}

	public AnnouncementFlag updateAnnouncementFlag(
		AnnouncementFlag announcementFlag) throws SystemException {
		announcementFlag.setNew(false);

		return announcementFlagPersistence.update(announcementFlag, true);
	}

	public AnnouncementEntryLocalService getAnnouncementEntryLocalService() {
		return announcementEntryLocalService;
	}

	public void setAnnouncementEntryLocalService(
		AnnouncementEntryLocalService announcementEntryLocalService) {
		this.announcementEntryLocalService = announcementEntryLocalService;
	}

	public AnnouncementEntryService getAnnouncementEntryService() {
		return announcementEntryService;
	}

	public void setAnnouncementEntryService(
		AnnouncementEntryService announcementEntryService) {
		this.announcementEntryService = announcementEntryService;
	}

	public AnnouncementEntryPersistence getAnnouncementEntryPersistence() {
		return announcementEntryPersistence;
	}

	public void setAnnouncementEntryPersistence(
		AnnouncementEntryPersistence announcementEntryPersistence) {
		this.announcementEntryPersistence = announcementEntryPersistence;
	}

	public AnnouncementEntryFinder getAnnouncementEntryFinder() {
		return announcementEntryFinder;
	}

	public void setAnnouncementEntryFinder(
		AnnouncementEntryFinder announcementEntryFinder) {
		this.announcementEntryFinder = announcementEntryFinder;
	}

	public AnnouncementFlagPersistence getAnnouncementFlagPersistence() {
		return announcementFlagPersistence;
	}

	public void setAnnouncementFlagPersistence(
		AnnouncementFlagPersistence announcementFlagPersistence) {
		this.announcementFlagPersistence = announcementFlagPersistence;
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
		if (announcementEntryLocalService == null) {
			announcementEntryLocalService = AnnouncementEntryLocalServiceFactory.getImpl();
		}

		if (announcementEntryService == null) {
			announcementEntryService = AnnouncementEntryServiceFactory.getImpl();
		}

		if (announcementEntryPersistence == null) {
			announcementEntryPersistence = AnnouncementEntryUtil.getPersistence();
		}

		if (announcementEntryFinder == null) {
			announcementEntryFinder = AnnouncementEntryFinderUtil.getFinder();
		}

		if (announcementFlagPersistence == null) {
			announcementFlagPersistence = AnnouncementFlagUtil.getPersistence();
		}

		if (counterLocalService == null) {
			counterLocalService = CounterLocalServiceFactory.getImpl();
		}

		if (counterService == null) {
			counterService = CounterServiceFactory.getImpl();
		}
	}

	protected AnnouncementEntryLocalService announcementEntryLocalService;
	protected AnnouncementEntryService announcementEntryService;
	protected AnnouncementEntryPersistence announcementEntryPersistence;
	protected AnnouncementEntryFinder announcementEntryFinder;
	protected AnnouncementFlagPersistence announcementFlagPersistence;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
}