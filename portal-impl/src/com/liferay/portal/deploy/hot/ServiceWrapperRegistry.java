/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.service.ServiceWrapper;
import com.liferay.portal.spring.aop.ServiceBeanAopCacheManagerUtil;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.lang.reflect.Method;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;

/**
 * @author Raymond Augé
 */
public class ServiceWrapperRegistry {

	public ServiceWrapperRegistry() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			ServiceWrapper.class.getName(),
			new ServiceWrapperServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void close() {
		_serviceTracker.close();
	}

	private static Log _log = LogFactoryUtil.getLog(
		ServiceWrapperRegistry.class);

	private ServiceTracker<ServiceWrapper<?>, ServiceBag> _serviceTracker;

	private class ServiceWrapperServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<ServiceWrapper<?>, ServiceBag> {

		@Override
		public ServiceBag addingService(
			ServiceReference<ServiceWrapper<?>> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ServiceWrapper<?> serviceWrapper = registry.getService(
				serviceReference);

			try {
				return _getServiceBag(serviceWrapper);
			}
			catch (Throwable t) {
				_log.error(t, t);
			}
			finally {
				ServiceBeanAopCacheManagerUtil.reset();
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<ServiceWrapper<?>> serviceReference,
			ServiceBag serviceHolder) {
		}

		@Override
		public void removedService(
			ServiceReference<ServiceWrapper<?>> serviceReference,
			ServiceBag serviceBag) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			try {
				serviceBag.replace();

				ServiceBeanAopCacheManagerUtil.reset();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		private <T> ServiceBag _getServiceBag(ServiceWrapper<T> serviceWrapper)
			throws Throwable {

			Class<?> clazz = serviceWrapper.getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			Method method = clazz.getMethod(
				"getWrappedService", new Class<?>[0]);

			Class<?> serviceTypeClass = method.getReturnType();

			Object serviceProxy = PortalBeanLocatorUtil.locate(
				serviceTypeClass.getName());

			if (!ProxyUtil.isProxyClass(serviceProxy.getClass())) {
				_log.error(
					"Service hooks require Spring to be configured to use " +
						"JdkDynamicProxy and will not work with CGLIB");

				return null;
			}

			AdvisedSupport advisedSupport =
				ServiceBeanAopProxy.getAdvisedSupport(serviceProxy);

			TargetSource targetSource = advisedSupport.getTargetSource();

			Object previousService = targetSource.getTarget();

			serviceWrapper.setWrappedService((T)previousService);

			return new ServiceBag(
				classLoader, advisedSupport, serviceTypeClass, serviceWrapper);
		}

	}

}