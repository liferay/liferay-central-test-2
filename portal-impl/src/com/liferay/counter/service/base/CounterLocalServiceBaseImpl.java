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

package com.liferay.counter.service.base;

import com.liferay.counter.model.Counter;
import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.persistence.CounterFinder;
import com.liferay.counter.service.persistence.CounterPersistence;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
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

import java.util.List;

import javax.sql.DataSource;

/**
 * <a href="CounterLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class CounterLocalServiceBaseImpl implements CounterLocalService {
	public Counter addCounter(Counter counter) throws SystemException {
		counter.setNew(true);

		return counterPersistence.update(counter, false);
	}

	public Counter createCounter(String name) {
		return counterPersistence.create(name);
	}

	public void deleteCounter(String name)
		throws PortalException, SystemException {
		counterPersistence.remove(name);
	}

	public void deleteCounter(Counter counter) throws SystemException {
		counterPersistence.remove(counter);
	}

	@SuppressWarnings("unchecked")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return counterPersistence.findWithDynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return counterPersistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return counterPersistence.findWithDynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return counterPersistence.countWithDynamicQuery(dynamicQuery);
	}

	public Counter getCounter(String name)
		throws PortalException, SystemException {
		return counterPersistence.findByPrimaryKey(name);
	}

	public List<Counter> getCounters(int start, int end)
		throws SystemException {
		return counterPersistence.findAll(start, end);
	}

	public int getCountersCount() throws SystemException {
		return counterPersistence.countAll();
	}

	public Counter updateCounter(Counter counter) throws SystemException {
		counter.setNew(false);

		return counterPersistence.update(counter, true);
	}

	public Counter updateCounter(Counter counter, boolean merge)
		throws SystemException {
		counter.setNew(false);

		return counterPersistence.update(counter, merge);
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterPersistence getCounterPersistence() {
		return counterPersistence;
	}

	public void setCounterPersistence(CounterPersistence counterPersistence) {
		this.counterPersistence = counterPersistence;
	}

	public CounterFinder getCounterFinder() {
		return counterFinder;
	}

	public void setCounterFinder(CounterFinder counterFinder) {
		this.counterFinder = counterFinder;
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
			DataSource dataSource = counterPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = CounterPersistence.class)
	protected CounterPersistence counterPersistence;
	@BeanReference(type = CounterFinder.class)
	protected CounterFinder counterFinder;
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