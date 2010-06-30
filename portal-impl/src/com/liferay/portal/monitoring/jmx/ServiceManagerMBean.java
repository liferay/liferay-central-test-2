/*
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

package com.liferay.portal.monitoring.jmx;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.MethodKey;

import java.util.Set;

/**
 * <a href="ServiceManagerMBean.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public interface ServiceManagerMBean {
	Set<String> getMonitoredClasses();

	Set<MethodKey> getMonitoredMethods();

	boolean isActive();

	boolean isPermissiveMode();

	void setActive(boolean active);

	void addMonitoredClass(String monitoredClass);

	void addMonitoredMethod(
			String className, String methodName, String[] parameterTypes)
		throws SystemException;

	long getErrorCount(
			String className, String methodName, String[] parameterTypes)
		throws SystemException;

	long getMaxTime(
			String className, String methodName, String[] parameterTypes)
		throws SystemException;

	long getMinTime(
			String className, String methodName, String[] parameterTypes)
		throws SystemException;

	long getRequestCount(
			String className, String methodName, String[] parameterTypes)
		throws SystemException;

	void setPermissiveMode(boolean permissiveMode);
}