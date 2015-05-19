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

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.cache.PortalCacheManagerTypes;
import com.liferay.portal.kernel.cache.configurator.PortalCacheConfiguratorSettings;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.ClassLoaderUtil;

import java.io.Serializable;

import java.util.Map;

import javax.management.MBeanServer;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Tina Tian
 */
@Component(
	immediate = true,
	property = {
		"portal.cache.manager.name=" + PortalCacheManagerNames.MULTI_VM,
		"portal.cache.manager.type=" + PortalCacheManagerTypes.EHCACHE
	},
	service = PortalCacheManager.class
)
public class MultiVMEhcachePortalCacheManager
	<K extends Serializable, V extends Serializable>
		extends EhcachePortalCacheManager<K, V> {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		setClusterAware(true);
		setConfigFile(props.get(PropsKeys.EHCACHE_MULTI_VM_CONFIG_LOCATION));
		setDefaultConfigFile("/ehcache/liferay-multi-vm-clustered.xml");
		setMpiOnly(true);
		setName(PortalCacheManagerNames.MULTI_VM);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Class<?> clazz = getClass();

		ClassLoaderUtil.setContextClassLoader(
			AggregateClassLoader.getAggregateClassLoader(
				contextClassLoader, clazz.getClassLoader()));

		try {
			initialize();
		}
		finally {
			ClassLoaderUtil.setContextClassLoader(contextClassLoader);
		}
	}

	@Deactivate
	protected void deactivate() {
		destroy();
	}

	@Reference(unbind = "-")
	protected void setMBeanServer(MBeanServer mBeanServer) {
		this.mBeanServer = mBeanServer;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(portal.cache.manager.name=" + PortalCacheManagerNames.MULTI_VM + ")"
	)
	protected void setPortalCacheConfiguratorSettings(
		PortalCacheConfiguratorSettings portalCacheConfiguratorSettings) {

		reconfigure(portalCacheConfiguratorSettings);
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		this.props = props;
	}

	protected void unsetPortalCacheConfiguratorSettings(
		PortalCacheConfiguratorSettings portalCacheConfiguratorSettings) {
	}

}