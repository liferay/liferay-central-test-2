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

package com.liferay.portlet.messageboards.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.service.MBBanLocalService;
import com.liferay.portlet.messageboards.service.MBBanService;
import com.liferay.portlet.messageboards.service.MBCategoryLocalService;
import com.liferay.portlet.messageboards.service.MBCategoryService;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalService;
import com.liferay.portlet.messageboards.service.MBMailingListLocalService;
import com.liferay.portlet.messageboards.service.MBMessageFlagLocalService;
import com.liferay.portlet.messageboards.service.MBMessageFlagService;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageService;
import com.liferay.portlet.messageboards.service.MBStatsUserLocalService;
import com.liferay.portlet.messageboards.service.MBThreadLocalService;
import com.liferay.portlet.messageboards.service.MBThreadService;
import com.liferay.portlet.messageboards.service.persistence.MBBanPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryFinder;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMailingListPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFinder;
import com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence;

import java.util.List;

/**
 * <a href="MBBanLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class MBBanLocalServiceBaseImpl implements MBBanLocalService {
	public MBBan addMBBan(MBBan mbBan) throws SystemException {
		mbBan.setNew(true);

		return mbBanPersistence.update(mbBan, false);
	}

	public MBBan createMBBan(long banId) {
		return mbBanPersistence.create(banId);
	}

	public void deleteMBBan(long banId) throws PortalException, SystemException {
		mbBanPersistence.remove(banId);
	}

	public void deleteMBBan(MBBan mbBan) throws SystemException {
		mbBanPersistence.remove(mbBan);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return mbBanPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return mbBanPersistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	public MBBan getMBBan(long banId) throws PortalException, SystemException {
		return mbBanPersistence.findByPrimaryKey(banId);
	}

	public List<MBBan> getMBBans(int start, int end) throws SystemException {
		return mbBanPersistence.findAll(start, end);
	}

	public int getMBBansCount() throws SystemException {
		return mbBanPersistence.countAll();
	}

	public MBBan updateMBBan(MBBan mbBan) throws SystemException {
		mbBan.setNew(false);

		return mbBanPersistence.update(mbBan, true);
	}

	public MBBan updateMBBan(MBBan mbBan, boolean merge)
		throws SystemException {
		mbBan.setNew(false);

		return mbBanPersistence.update(mbBan, merge);
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

	public MBDiscussionLocalService getMBDiscussionLocalService() {
		return mbDiscussionLocalService;
	}

	public void setMBDiscussionLocalService(
		MBDiscussionLocalService mbDiscussionLocalService) {
		this.mbDiscussionLocalService = mbDiscussionLocalService;
	}

	public MBDiscussionPersistence getMBDiscussionPersistence() {
		return mbDiscussionPersistence;
	}

	public void setMBDiscussionPersistence(
		MBDiscussionPersistence mbDiscussionPersistence) {
		this.mbDiscussionPersistence = mbDiscussionPersistence;
	}

	public MBMailingListLocalService getMBMailingListLocalService() {
		return mbMailingListLocalService;
	}

	public void setMBMailingListLocalService(
		MBMailingListLocalService mbMailingListLocalService) {
		this.mbMailingListLocalService = mbMailingListLocalService;
	}

	public MBMailingListPersistence getMBMailingListPersistence() {
		return mbMailingListPersistence;
	}

	public void setMBMailingListPersistence(
		MBMailingListPersistence mbMailingListPersistence) {
		this.mbMailingListPersistence = mbMailingListPersistence;
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

	public MBMessageFlagService getMBMessageFlagService() {
		return mbMessageFlagService;
	}

	public void setMBMessageFlagService(
		MBMessageFlagService mbMessageFlagService) {
		this.mbMessageFlagService = mbMessageFlagService;
	}

	public MBMessageFlagPersistence getMBMessageFlagPersistence() {
		return mbMessageFlagPersistence;
	}

	public void setMBMessageFlagPersistence(
		MBMessageFlagPersistence mbMessageFlagPersistence) {
		this.mbMessageFlagPersistence = mbMessageFlagPersistence;
	}

	public MBStatsUserLocalService getMBStatsUserLocalService() {
		return mbStatsUserLocalService;
	}

	public void setMBStatsUserLocalService(
		MBStatsUserLocalService mbStatsUserLocalService) {
		this.mbStatsUserLocalService = mbStatsUserLocalService;
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

	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBBanLocalService")
	protected MBBanLocalService mbBanLocalService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBBanService")
	protected MBBanService mbBanService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBBanPersistence")
	protected MBBanPersistence mbBanPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBCategoryLocalService")
	protected MBCategoryLocalService mbCategoryLocalService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBCategoryService")
	protected MBCategoryService mbCategoryService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence")
	protected MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBCategoryFinder")
	protected MBCategoryFinder mbCategoryFinder;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBDiscussionLocalService")
	protected MBDiscussionLocalService mbDiscussionLocalService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence")
	protected MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBMailingListLocalService")
	protected MBMailingListLocalService mbMailingListLocalService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMailingListPersistence")
	protected MBMailingListPersistence mbMailingListPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBMessageLocalService")
	protected MBMessageLocalService mbMessageLocalService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBMessageService")
	protected MBMessageService mbMessageService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence")
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessageFinder")
	protected MBMessageFinder mbMessageFinder;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBMessageFlagLocalService")
	protected MBMessageFlagLocalService mbMessageFlagLocalService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBMessageFlagService")
	protected MBMessageFlagService mbMessageFlagService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence")
	protected MBMessageFlagPersistence mbMessageFlagPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBStatsUserLocalService")
	protected MBStatsUserLocalService mbStatsUserLocalService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence")
	protected MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBThreadLocalService")
	protected MBThreadLocalService mbThreadLocalService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBThreadService")
	protected MBThreadService mbThreadService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence")
	protected MBThreadPersistence mbThreadPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBThreadFinder")
	protected MBThreadFinder mbThreadFinder;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService")
	protected CounterService counterService;
	@BeanReference(name = "com.liferay.portal.service.ResourceLocalService")
	protected ResourceLocalService resourceLocalService;
	@BeanReference(name = "com.liferay.portal.service.ResourceService")
	protected ResourceService resourceService;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceFinder")
	protected ResourceFinder resourceFinder;
	@BeanReference(name = "com.liferay.portal.service.UserLocalService")
	protected UserLocalService userLocalService;
	@BeanReference(name = "com.liferay.portal.service.UserService")
	protected UserService userService;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserFinder")
	protected UserFinder userFinder;
}