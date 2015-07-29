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

package com.liferay.portal.kernel.cache;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.dependency.ServiceDependencyManager;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
@OSGiBeanProperties(service = MultiVMPoolUtil.class)
public class MultiVMPoolUtil {

	public static void clear() {
		getMultiVMPool().clear();
	}

	public static <K extends Serializable, V extends Serializable>
		PortalCache<K, V> getCache(String portalCacheName) {

		return (PortalCache<K, V>)getMultiVMPool().getCache(portalCacheName);
	}

	public static <K extends Serializable, V extends Serializable>
		PortalCache<K, V> getCache(String portalCacheName, boolean blocking) {

		return (PortalCache<K, V>)getMultiVMPool().getCache(
			portalCacheName, blocking);
	}

	public static <K extends Serializable, V extends Serializable>
		PortalCacheManager<K, V> getCacheManager() {

		return (PortalCacheManager<K, V>)getMultiVMPool().getCacheManager();
	}

	public static MultiVMPool getMultiVMPool() {
		PortalRuntimePermission.checkGetBeanProperty(MultiVMPoolUtil.class);

		MultiVMPool multiVMPool = _instance._serviceTracker.getService();

		if (multiVMPool == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("MultiVMPoolUtil has not been initialized");
			}

			return null;
		}

		return multiVMPool;
	}

	public static void removeCache(String portalCacheName) {
		getMultiVMPool().removeCache(portalCacheName);
	}

	private MultiVMPoolUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(MultiVMPool.class);

		_serviceTracker.open();

		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		serviceDependencyManager.registerDependencies(MultiVMPool.class);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MultiVMPoolUtil.class);

	private static final MultiVMPoolUtil _instance = new MultiVMPoolUtil();

	private final ServiceTracker<MultiVMPool, MultiVMPool> _serviceTracker;

}