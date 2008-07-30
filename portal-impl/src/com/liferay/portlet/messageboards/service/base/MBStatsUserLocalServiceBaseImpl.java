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

package com.liferay.portlet.messageboards.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.service.MBBanLocalService;
import com.liferay.portlet.messageboards.service.MBBanService;
import com.liferay.portlet.messageboards.service.MBCategoryLocalService;
import com.liferay.portlet.messageboards.service.MBCategoryService;
import com.liferay.portlet.messageboards.service.MBMessageFlagLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageService;
import com.liferay.portlet.messageboards.service.MBStatsUserLocalService;
import com.liferay.portlet.messageboards.service.MBThreadLocalService;
import com.liferay.portlet.messageboards.service.MBThreadService;
import com.liferay.portlet.messageboards.service.persistence.MBBanPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryFinder;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFinder;
import com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence;

import java.util.List;

/**
 * <a href="MBStatsUserLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class MBStatsUserLocalServiceBaseImpl
	implements MBStatsUserLocalService, InitializingBean {
	public MBStatsUser addMBStatsUser(MBStatsUser mbStatsUser)
		throws SystemException {
		mbStatsUser.setNew(true);

		return mbStatsUserPersistence.update(mbStatsUser, false);
	}

	public void deleteMBStatsUser(long statsUserId)
		throws PortalException, SystemException {
		mbStatsUserPersistence.remove(statsUserId);
	}

	public void deleteMBStatsUser(MBStatsUser mbStatsUser)
		throws SystemException {
		mbStatsUserPersistence.remove(mbStatsUser);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return mbStatsUserPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return mbStatsUserPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	public MBStatsUser getMBStatsUser(long statsUserId)
		throws PortalException, SystemException {
		return mbStatsUserPersistence.findByPrimaryKey(statsUserId);
	}

	public List<MBStatsUser> getMBStatsUsers(int start, int end)
		throws SystemException {
		return mbStatsUserPersistence.findAll(start, end);
	}

	public int getMBStatsUsersCount() throws SystemException {
		return mbStatsUserPersistence.countAll();
	}

	public MBStatsUser updateMBStatsUser(MBStatsUser mbStatsUser)
		throws SystemException {
		mbStatsUser.setNew(false);

		return mbStatsUserPersistence.update(mbStatsUser, true);
	}

	public MBBanLocalService getMBBanLocalService() {
		return mbBanLocalService;
	}

	public void setMBBanLocalService(MBBanLocalService mbBanLocalService) {
		this.mbBanLocalService = mbBanLocalService;
	}

	public MBBanService getMBBanService() {
		return mbBanService;
	}

	public void setMBBanService(MBBanService mbBanService) {
		this.mbBanService = mbBanService;
	}

	public MBBanPersistence getMBBanPersistence() {
		return mbBanPersistence;
	}

	public void setMBBanPersistence(MBBanPersistence mbBanPersistence) {
		this.mbBanPersistence = mbBanPersistence;
	}

	public MBCategoryLocalService getMBCategoryLocalService() {
		return mbCategoryLocalService;
	}

	public void setMBCategoryLocalService(
		MBCategoryLocalService mbCategoryLocalService) {
		this.mbCategoryLocalService = mbCategoryLocalService;
	}

	public MBCategoryService getMBCategoryService() {
		return mbCategoryService;
	}

	public void setMBCategoryService(MBCategoryService mbCategoryService) {
		this.mbCategoryService = mbCategoryService;
	}

	public MBCategoryPersistence getMBCategoryPersistence() {
		return mbCategoryPersistence;
	}

	public void setMBCategoryPersistence(
		MBCategoryPersistence mbCategoryPersistence) {
		this.mbCategoryPersistence = mbCategoryPersistence;
	}

	public MBCategoryFinder getMBCategoryFinder() {
		return mbCategoryFinder;
	}

	public void setMBCategoryFinder(MBCategoryFinder mbCategoryFinder) {
		this.mbCategoryFinder = mbCategoryFinder;
	}

	public MBDiscussionPersistence getMBDiscussionPersistence() {
		return mbDiscussionPersistence;
	}

	public void setMBDiscussionPersistence(
		MBDiscussionPersistence mbDiscussionPersistence) {
		this.mbDiscussionPersistence = mbDiscussionPersistence;
	}

	public MBMessageLocalService getMBMessageLocalService() {
		return mbMessageLocalService;
	}

	public void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {
		this.mbMessageLocalService = mbMessageLocalService;
	}

	public MBMessageService getMBMessageService() {
		return mbMessageService;
	}

	public void setMBMessageService(MBMessageService mbMessageService) {
		this.mbMessageService = mbMessageService;
	}

	public MBMessagePersistence getMBMessagePersistence() {
		return mbMessagePersistence;
	}

	public void setMBMessagePersistence(
		MBMessagePersistence mbMessagePersistence) {
		this.mbMessagePersistence = mbMessagePersistence;
	}

	public MBMessageFinder getMBMessageFinder() {
		return mbMessageFinder;
	}

	public void setMBMessageFinder(MBMessageFinder mbMessageFinder) {
		this.mbMessageFinder = mbMessageFinder;
	}

	public MBMessageFlagLocalService getMBMessageFlagLocalService() {
		return mbMessageFlagLocalService;
	}

	public void setMBMessageFlagLocalService(
		MBMessageFlagLocalService mbMessageFlagLocalService) {
		this.mbMessageFlagLocalService = mbMessageFlagLocalService;
	}

	public MBMessageFlagPersistence getMBMessageFlagPersistence() {
		return mbMessageFlagPersistence;
	}

	public void setMBMessageFlagPersistence(
		MBMessageFlagPersistence mbMessageFlagPersistence) {
		this.mbMessageFlagPersistence = mbMessageFlagPersistence;
	}

	public MBMessageFlagFinder getMBMessageFlagFinder() {
		return mbMessageFlagFinder;
	}

	public void setMBMessageFlagFinder(MBMessageFlagFinder mbMessageFlagFinder) {
		this.mbMessageFlagFinder = mbMessageFlagFinder;
	}

	public MBStatsUserPersistence getMBStatsUserPersistence() {
		return mbStatsUserPersistence;
	}

	public void setMBStatsUserPersistence(
		MBStatsUserPersistence mbStatsUserPersistence) {
		this.mbStatsUserPersistence = mbStatsUserPersistence;
	}

	public MBThreadLocalService getMBThreadLocalService() {
		return mbThreadLocalService;
	}

	public void setMBThreadLocalService(
		MBThreadLocalService mbThreadLocalService) {
		this.mbThreadLocalService = mbThreadLocalService;
	}

	public MBThreadService getMBThreadService() {
		return mbThreadService;
	}

	public void setMBThreadService(MBThreadService mbThreadService) {
		this.mbThreadService = mbThreadService;
	}

	public MBThreadPersistence getMBThreadPersistence() {
		return mbThreadPersistence;
	}

	public void setMBThreadPersistence(MBThreadPersistence mbThreadPersistence) {
		this.mbThreadPersistence = mbThreadPersistence;
	}

	public MBThreadFinder getMBThreadFinder() {
		return mbThreadFinder;
	}

	public void setMBThreadFinder(MBThreadFinder mbThreadFinder) {
		this.mbThreadFinder = mbThreadFinder;
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
		if (mbBanLocalService == null) {
			mbBanLocalService = (MBBanLocalService)PortalBeanLocatorUtil.locate(MBBanLocalService.class.getName() +
					".impl");
		}

		if (mbBanService == null) {
			mbBanService = (MBBanService)PortalBeanLocatorUtil.locate(MBBanService.class.getName() +
					".impl");
		}

		if (mbBanPersistence == null) {
			mbBanPersistence = (MBBanPersistence)PortalBeanLocatorUtil.locate(MBBanPersistence.class.getName() +
					".impl");
		}

		if (mbCategoryLocalService == null) {
			mbCategoryLocalService = (MBCategoryLocalService)PortalBeanLocatorUtil.locate(MBCategoryLocalService.class.getName() +
					".impl");
		}

		if (mbCategoryService == null) {
			mbCategoryService = (MBCategoryService)PortalBeanLocatorUtil.locate(MBCategoryService.class.getName() +
					".impl");
		}

		if (mbCategoryPersistence == null) {
			mbCategoryPersistence = (MBCategoryPersistence)PortalBeanLocatorUtil.locate(MBCategoryPersistence.class.getName() +
					".impl");
		}

		if (mbCategoryFinder == null) {
			mbCategoryFinder = (MBCategoryFinder)PortalBeanLocatorUtil.locate(MBCategoryFinder.class.getName() +
					".impl");
		}

		if (mbDiscussionPersistence == null) {
			mbDiscussionPersistence = (MBDiscussionPersistence)PortalBeanLocatorUtil.locate(MBDiscussionPersistence.class.getName() +
					".impl");
		}

		if (mbMessageLocalService == null) {
			mbMessageLocalService = (MBMessageLocalService)PortalBeanLocatorUtil.locate(MBMessageLocalService.class.getName() +
					".impl");
		}

		if (mbMessageService == null) {
			mbMessageService = (MBMessageService)PortalBeanLocatorUtil.locate(MBMessageService.class.getName() +
					".impl");
		}

		if (mbMessagePersistence == null) {
			mbMessagePersistence = (MBMessagePersistence)PortalBeanLocatorUtil.locate(MBMessagePersistence.class.getName() +
					".impl");
		}

		if (mbMessageFinder == null) {
			mbMessageFinder = (MBMessageFinder)PortalBeanLocatorUtil.locate(MBMessageFinder.class.getName() +
					".impl");
		}

		if (mbMessageFlagLocalService == null) {
			mbMessageFlagLocalService = (MBMessageFlagLocalService)PortalBeanLocatorUtil.locate(MBMessageFlagLocalService.class.getName() +
					".impl");
		}

		if (mbMessageFlagPersistence == null) {
			mbMessageFlagPersistence = (MBMessageFlagPersistence)PortalBeanLocatorUtil.locate(MBMessageFlagPersistence.class.getName() +
					".impl");
		}

		if (mbMessageFlagFinder == null) {
			mbMessageFlagFinder = (MBMessageFlagFinder)PortalBeanLocatorUtil.locate(MBMessageFlagFinder.class.getName() +
					".impl");
		}

		if (mbStatsUserPersistence == null) {
			mbStatsUserPersistence = (MBStatsUserPersistence)PortalBeanLocatorUtil.locate(MBStatsUserPersistence.class.getName() +
					".impl");
		}

		if (mbThreadLocalService == null) {
			mbThreadLocalService = (MBThreadLocalService)PortalBeanLocatorUtil.locate(MBThreadLocalService.class.getName() +
					".impl");
		}

		if (mbThreadService == null) {
			mbThreadService = (MBThreadService)PortalBeanLocatorUtil.locate(MBThreadService.class.getName() +
					".impl");
		}

		if (mbThreadPersistence == null) {
			mbThreadPersistence = (MBThreadPersistence)PortalBeanLocatorUtil.locate(MBThreadPersistence.class.getName() +
					".impl");
		}

		if (mbThreadFinder == null) {
			mbThreadFinder = (MBThreadFinder)PortalBeanLocatorUtil.locate(MBThreadFinder.class.getName() +
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

	protected MBBanLocalService mbBanLocalService;
	protected MBBanService mbBanService;
	protected MBBanPersistence mbBanPersistence;
	protected MBCategoryLocalService mbCategoryLocalService;
	protected MBCategoryService mbCategoryService;
	protected MBCategoryPersistence mbCategoryPersistence;
	protected MBCategoryFinder mbCategoryFinder;
	protected MBDiscussionPersistence mbDiscussionPersistence;
	protected MBMessageLocalService mbMessageLocalService;
	protected MBMessageService mbMessageService;
	protected MBMessagePersistence mbMessagePersistence;
	protected MBMessageFinder mbMessageFinder;
	protected MBMessageFlagLocalService mbMessageFlagLocalService;
	protected MBMessageFlagPersistence mbMessageFlagPersistence;
	protected MBMessageFlagFinder mbMessageFlagFinder;
	protected MBStatsUserPersistence mbStatsUserPersistence;
	protected MBThreadLocalService mbThreadLocalService;
	protected MBThreadService mbThreadService;
	protected MBThreadPersistence mbThreadPersistence;
	protected MBThreadFinder mbThreadFinder;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
}