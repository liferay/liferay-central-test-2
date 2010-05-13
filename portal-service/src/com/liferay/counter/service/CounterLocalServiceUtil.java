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

package com.liferay.counter.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="CounterLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link CounterLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CounterLocalService
 * @generated
 */
public class CounterLocalServiceUtil {
	public static com.liferay.counter.model.Counter addCounter(
		com.liferay.counter.model.Counter counter)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addCounter(counter);
	}

	public static com.liferay.counter.model.Counter createCounter(
		java.lang.String name) {
		return getService().createCounter(name);
	}

	public static void deleteCounter(java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCounter(name);
	}

	public static void deleteCounter(com.liferay.counter.model.Counter counter)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCounter(counter);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.counter.model.Counter getCounter(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCounter(name);
	}

	public static java.util.List<com.liferay.counter.model.Counter> getCounters(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCounters(start, end);
	}

	public static int getCountersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCountersCount();
	}

	public static com.liferay.counter.model.Counter updateCounter(
		com.liferay.counter.model.Counter counter)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCounter(counter);
	}

	public static com.liferay.counter.model.Counter updateCounter(
		com.liferay.counter.model.Counter counter, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCounter(counter, merge);
	}

	public static java.util.List<java.lang.String> getNames()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNames();
	}

	public static long increment()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().increment();
	}

	public static long increment(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().increment(name);
	}

	public static long increment(java.lang.String name, int size)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().increment(name, size);
	}

	public static void rename(java.lang.String oldName, java.lang.String newName)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().rename(oldName, newName);
	}

	public static void reset(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().reset(name);
	}

	public static void reset(java.lang.String name, long size)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().reset(name, size);
	}

	public static CounterLocalService getService() {
		if (_service == null) {
			_service = (CounterLocalService)PortalBeanLocatorUtil.locate(CounterLocalService.class.getName());
		}

		return _service;
	}

	public void setService(CounterLocalService service) {
		_service = service;
	}

	private static CounterLocalService _service;
}