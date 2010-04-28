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

package com.liferay.portlet.ratings.service.base;

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

import com.liferay.portlet.blogs.service.BlogsEntryLocalService;
import com.liferay.portlet.blogs.service.BlogsEntryService;
import com.liferay.portlet.blogs.service.BlogsStatsUserLocalService;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryFinder;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserFinder;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.service.RatingsEntryLocalService;
import com.liferay.portlet.ratings.service.RatingsEntryService;
import com.liferay.portlet.ratings.service.RatingsStatsLocalService;
import com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;

import java.util.List;

/**
 * <a href="RatingsEntryLocalServiceBaseImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class RatingsEntryLocalServiceBaseImpl
	implements RatingsEntryLocalService {
	public RatingsEntry addRatingsEntry(RatingsEntry ratingsEntry)
		throws SystemException {
		ratingsEntry.setNew(true);

		return ratingsEntryPersistence.update(ratingsEntry, false);
	}

	public RatingsEntry createRatingsEntry(long entryId) {
		return ratingsEntryPersistence.create(entryId);
	}

	public void deleteRatingsEntry(long entryId)
		throws PortalException, SystemException {
		ratingsEntryPersistence.remove(entryId);
	}

	public void deleteRatingsEntry(RatingsEntry ratingsEntry)
		throws SystemException {
		ratingsEntryPersistence.remove(ratingsEntry);
	}

	public List<RatingsEntry> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return ratingsEntryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<RatingsEntry> dynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return ratingsEntryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public List<RatingsEntry> dynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return ratingsEntryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return ratingsEntryPersistence.countWithDynamicQuery(dynamicQuery);
	}

	public RatingsEntry getRatingsEntry(long entryId)
		throws PortalException, SystemException {
		return ratingsEntryPersistence.findByPrimaryKey(entryId);
	}

	public List<RatingsEntry> getRatingsEntries(int start, int end)
		throws SystemException {
		return ratingsEntryPersistence.findAll(start, end);
	}

	public int getRatingsEntriesCount() throws SystemException {
		return ratingsEntryPersistence.countAll();
	}

	public RatingsEntry updateRatingsEntry(RatingsEntry ratingsEntry)
		throws SystemException {
		ratingsEntry.setNew(false);

		return ratingsEntryPersistence.update(ratingsEntry, true);
	}

	public RatingsEntry updateRatingsEntry(RatingsEntry ratingsEntry,
		boolean merge) throws SystemException {
		ratingsEntry.setNew(false);

		return ratingsEntryPersistence.update(ratingsEntry, merge);
	}

	public RatingsEntryLocalService getRatingsEntryLocalService() {
		return ratingsEntryLocalService;
	}

	public void setRatingsEntryLocalService(
		RatingsEntryLocalService ratingsEntryLocalService) {
		this.ratingsEntryLocalService = ratingsEntryLocalService;
	}

	public RatingsEntryService getRatingsEntryService() {
		return ratingsEntryService;
	}

	public void setRatingsEntryService(RatingsEntryService ratingsEntryService) {
		this.ratingsEntryService = ratingsEntryService;
	}

	public RatingsEntryPersistence getRatingsEntryPersistence() {
		return ratingsEntryPersistence;
	}

	public void setRatingsEntryPersistence(
		RatingsEntryPersistence ratingsEntryPersistence) {
		this.ratingsEntryPersistence = ratingsEntryPersistence;
	}

	public RatingsStatsLocalService getRatingsStatsLocalService() {
		return ratingsStatsLocalService;
	}

	public void setRatingsStatsLocalService(
		RatingsStatsLocalService ratingsStatsLocalService) {
		this.ratingsStatsLocalService = ratingsStatsLocalService;
	}

	public RatingsStatsPersistence getRatingsStatsPersistence() {
		return ratingsStatsPersistence;
	}

	public void setRatingsStatsPersistence(
		RatingsStatsPersistence ratingsStatsPersistence) {
		this.ratingsStatsPersistence = ratingsStatsPersistence;
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

	public BlogsEntryLocalService getBlogsEntryLocalService() {
		return blogsEntryLocalService;
	}

	public void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {
		this.blogsEntryLocalService = blogsEntryLocalService;
	}

	public BlogsEntryService getBlogsEntryService() {
		return blogsEntryService;
	}

	public void setBlogsEntryService(BlogsEntryService blogsEntryService) {
		this.blogsEntryService = blogsEntryService;
	}

	public BlogsEntryPersistence getBlogsEntryPersistence() {
		return blogsEntryPersistence;
	}

	public void setBlogsEntryPersistence(
		BlogsEntryPersistence blogsEntryPersistence) {
		this.blogsEntryPersistence = blogsEntryPersistence;
	}

	public BlogsEntryFinder getBlogsEntryFinder() {
		return blogsEntryFinder;
	}

	public void setBlogsEntryFinder(BlogsEntryFinder blogsEntryFinder) {
		this.blogsEntryFinder = blogsEntryFinder;
	}

	public BlogsStatsUserLocalService getBlogsStatsUserLocalService() {
		return blogsStatsUserLocalService;
	}

	public void setBlogsStatsUserLocalService(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {
		this.blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	public BlogsStatsUserPersistence getBlogsStatsUserPersistence() {
		return blogsStatsUserPersistence;
	}

	public void setBlogsStatsUserPersistence(
		BlogsStatsUserPersistence blogsStatsUserPersistence) {
		this.blogsStatsUserPersistence = blogsStatsUserPersistence;
	}

	public BlogsStatsUserFinder getBlogsStatsUserFinder() {
		return blogsStatsUserFinder;
	}

	public void setBlogsStatsUserFinder(
		BlogsStatsUserFinder blogsStatsUserFinder) {
		this.blogsStatsUserFinder = blogsStatsUserFinder;
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

	@BeanReference(type = RatingsEntryLocalService.class)
	protected RatingsEntryLocalService ratingsEntryLocalService;
	@BeanReference(type = RatingsEntryService.class)
	protected RatingsEntryService ratingsEntryService;
	@BeanReference(type = RatingsEntryPersistence.class)
	protected RatingsEntryPersistence ratingsEntryPersistence;
	@BeanReference(type = RatingsStatsLocalService.class)
	protected RatingsStatsLocalService ratingsStatsLocalService;
	@BeanReference(type = RatingsStatsPersistence.class)
	protected RatingsStatsPersistence ratingsStatsPersistence;
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
	@BeanReference(type = BlogsEntryLocalService.class)
	protected BlogsEntryLocalService blogsEntryLocalService;
	@BeanReference(type = BlogsEntryService.class)
	protected BlogsEntryService blogsEntryService;
	@BeanReference(type = BlogsEntryPersistence.class)
	protected BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(type = BlogsEntryFinder.class)
	protected BlogsEntryFinder blogsEntryFinder;
	@BeanReference(type = BlogsStatsUserLocalService.class)
	protected BlogsStatsUserLocalService blogsStatsUserLocalService;
	@BeanReference(type = BlogsStatsUserPersistence.class)
	protected BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(type = BlogsStatsUserFinder.class)
	protected BlogsStatsUserFinder blogsStatsUserFinder;
}