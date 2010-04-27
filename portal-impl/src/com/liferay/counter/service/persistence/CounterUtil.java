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

package com.liferay.counter.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

/**
 * <a href="CounterUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CounterUtil {

	public static List<String> getNames() throws SystemException {
		return getPersistence().getNames();
	}

	public static CounterPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CounterPersistence)PortalBeanLocatorUtil.locate(
				CounterPersistence.class.getName());
		}

		return _persistence;
	}

	public static long increment() throws SystemException {
		return getPersistence().increment();
	}

	public static long increment(String name) throws SystemException {
		return getPersistence().increment(name);
	}

	public static long increment(String name, int size)
		throws SystemException {

		return getPersistence().increment(name, size);
	}

	public static void rename(String oldName, String newName)
		throws SystemException {

		getPersistence().rename(oldName, newName);
	}

	public static void reset(String name) throws SystemException {
		getPersistence().reset(name);
	}

	public static void reset(String name, long size) throws SystemException {
		getPersistence().reset(name, size);
	}

	public void setPersistence(CounterPersistence persistence) {
		_persistence = persistence;
	}

	private static CounterPersistence _persistence;

}