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

package com.liferay.portal.monitoring.statistics.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.monitoring.RequestStatus;
import com.liferay.portal.monitoring.statistics.DataSampleThreadLocal;
import com.liferay.portal.spring.aop.ChainableMethodAdvice;

import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Set;

import org.aopalliance.intercept.MethodInvocation;

/**
 * <a href="ServiceMonitorAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class ServiceMonitorAdvice extends ChainableMethodAdvice {

	public static ServiceMonitorAdvice getInstance() {
		return _instance;
	}

	public void addMonitoredClass(String monitoredClass) {
		_monitoredClasses.add(monitoredClass);
	}

	public void addMonitoredMethod(
			String className, String methodName, String[] parameterTypes)
		throws SystemException {

		try {
			MethodKey methodKey = MethodKey.create(
				className, methodName, parameterTypes);

			_monitoredMethods.add(methodKey);
		}
		catch (ClassNotFoundException e) {
			throw new SystemException("Unable to add method", e);
		}
	}

	public Set<String> getMonitoredClasses() {
		return _monitoredClasses;
	}

	public String getMonitoringDestinationName() {
		return _monitoringDestinationName;
	}

	public Set<MethodKey> getMonitoredMethods() {
		return _monitoredMethods;
	}

	public Object before(MethodInvocation methodInvocation)
		throws Throwable {

		if (!_active) {
			return null;
		}

		if (!_permissiveMode && (!isMonitored(methodInvocation))) {
			return null;
		}

		ServiceRequestDataSample serviceRequestDataSample =
			new ServiceRequestDataSample(methodInvocation);

		serviceRequestDataSample.prepare();

		_serviceRequestDataSampleThreadLocal.set(serviceRequestDataSample);

		return null;
	}

	public void afterReturning(MethodInvocation methodInvocation, Object result)
		throws Throwable {

		ServiceRequestDataSample serviceRequestDataSample =
			_serviceRequestDataSampleThreadLocal.get();

		if (serviceRequestDataSample != null) {
			serviceRequestDataSample.capture(RequestStatus.SUCCESS);
		}
	}

	public void afterThrowing(
			MethodInvocation methodInvocation, Throwable throwable)
		throws Throwable {

		ServiceRequestDataSample serviceRequestDataSample =
			_serviceRequestDataSampleThreadLocal.get();

		if (serviceRequestDataSample != null) {
			serviceRequestDataSample.capture(RequestStatus.ERROR);
		}
	}

	public void duringFinally(MethodInvocation methodInvocation) {

		ServiceRequestDataSample serviceRequestDataSample =
			_serviceRequestDataSampleThreadLocal.get();

		if (serviceRequestDataSample != null) {
			_serviceRequestDataSampleThreadLocal.remove();

			DataSampleThreadLocal.addDataSample(serviceRequestDataSample);

			MessageBusUtil.sendMessage(
				_monitoringDestinationName, serviceRequestDataSample);
		}
	}

	public boolean isActive() {
		return _active;
	}

	public boolean isPermissiveMode() {
		return _permissiveMode;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setMonitoredClasses(Set<String> monitoredClasses) {
		_monitoredClasses = monitoredClasses;
	}

	public void setMonitoringDestinationName(String monitoringDestinationName) {
		_monitoringDestinationName = monitoringDestinationName;
	}

	public void setMonitoredMethods(Set<MethodKey> monitoredMethods) {
		_monitoredMethods = monitoredMethods;
	}

	public void setPermissiveMode(boolean permissiveMode) {
		_permissiveMode = permissiveMode;
	}

	protected boolean isMonitored(MethodInvocation methodInvocation) {
		Method method = methodInvocation.getMethod();

		String className = method.getDeclaringClass().getName();

		if (_monitoredClasses.contains(className)) {
			return true;
		}

		String methodName = method.getName();
		Class[] types = method.getParameterTypes();

		MethodKey methodKey = new MethodKey(className, methodName, types);

		if (_monitoredMethods.contains(methodKey)) {
			return true;
		}

		return false;
	}

	private static ServiceMonitorAdvice _instance = new ServiceMonitorAdvice();
	private static ThreadLocal<ServiceRequestDataSample>
		_serviceRequestDataSampleThreadLocal =
			new AutoResetThreadLocal<ServiceRequestDataSample>(
				ServiceRequestDataSample.class +
				"._serviceRequestDataSampleThreadLocal");

	private String _monitoringDestinationName;
	private Set<String> _monitoredClasses = new HashSet<String>();
	private Set<MethodKey> _monitoredMethods = new HashSet<MethodKey>();
	private boolean _active;
	private boolean _permissiveMode;
}