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
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

/**
 * <a href="CounterServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CounterServiceUtil {

	public static List<String> getNames() throws SystemException {
		return getService().getNames();
	}

	public static CounterService getService() {
		if (_service == null) {
			_service = (CounterService)PortalBeanLocatorUtil.locate(
				CounterService.class.getName());
		}

		return _service;
	}

	public static long increment() throws SystemException {
		return getService().increment();
	}

	public static long increment(String name) throws SystemException {
		return getService().increment(name);
	}

	public static long increment(String name, int size) throws SystemException {
		return getService().increment(name, size);
	}

	public static void rename(String oldName, String newName)
		throws SystemException {

		getService().rename(oldName, newName);
	}

	public static void reset(String name) throws SystemException {
		getService().reset(name);
	}

	public static void reset(String name, long size)
		throws SystemException {

		getService().reset(name, size);
	}

	public void setService(CounterService service) {
		_service = service;
	}

	private static CounterService _service;

}