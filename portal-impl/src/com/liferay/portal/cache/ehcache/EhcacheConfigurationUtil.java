/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.cache.cluster.EhcachePortalCacheClusterReplicatorFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.net.URL;

import net.sf.ehcache.config.CacheConfiguration.CacheEventListenerFactoryConfiguration;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;

/**
 * @author Shuyang Zhou
 * @author Edward Han
 */
public class EhcacheConfigurationUtil {

	public static Configuration getConfiguration(
		String configurationResourcePath) {

		return getConfiguration(configurationResourcePath, false);
	}

	public static Configuration getConfiguration(
		URL configurationResourceURL) {

		return getConfiguration(configurationResourceURL, false);
	}

	public static Configuration getConfiguration(
		String configurationResourcePath, boolean clusterAware) {

		return getConfiguration(configurationResourcePath, clusterAware, false);
	}

	public static Configuration getConfiguration(
		URL configurationResourceURL, boolean clusterAware) {

		return getConfiguration(configurationResourceURL, clusterAware, false);
	}

	public static Configuration getConfiguration(
		String configurationResourcePath, boolean clusterAware,
		boolean usingDefault) {

		if (Validator.isNull(configurationResourcePath)) {
			return null;
		}

		URL url = EhcacheConfigurationUtil.class.getResource(
			configurationResourcePath);

		return getConfiguration(url, clusterAware, usingDefault);
	}

	public static Configuration getConfiguration(
		URL configurationResourceURL, boolean clusterAware,
		boolean usingDefault) {

		if (Validator.isNull(configurationResourceURL)) {
			return null;
		}

		Configuration configuration = ConfigurationFactory.parseConfiguration(
			configurationResourceURL);

		boolean enableClusterLinkReplication =
			PropsValues.CLUSTER_LINK_ENABLED &&
				PropsValues.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED;

		if (clusterAware && (usingDefault || enableClusterLinkReplication)) {
			return _processDefaultClusterLinkReplication(
				configuration, usingDefault, enableClusterLinkReplication);
		}

		return configuration;
	}

	private static Configuration _processDefaultClusterLinkReplication(
		Configuration configuration, boolean usingDefault,
		boolean enableClusterLinkReplication) {

		boolean clearCachePeerProviderConfigurations =
			((usingDefault && enableClusterLinkReplication) ||
			 (usingDefault && !PropsValues.CLUSTER_LINK_ENABLED));

		if (clearCachePeerProviderConfigurations) {
			configuration.getCacheManagerPeerProviderFactoryConfiguration().
				clear();
			configuration.getCacheManagerPeerListenerFactoryConfigurations().
				clear();
		}

		CacheConfiguration defaultCacheConfiguration =
			configuration.getDefaultCacheConfiguration();

		_configureCacheEventListeners(
			enableClusterLinkReplication, clearCachePeerProviderConfigurations,
			usingDefault, defaultCacheConfiguration);

		for (CacheConfiguration cacheConfiguration :
				configuration.getCacheConfigurations().values()) {

			_configureCacheEventListeners(
				enableClusterLinkReplication,
				clearCachePeerProviderConfigurations, usingDefault,
				cacheConfiguration);
		}

		return configuration;
	}

	private static void _configureCacheEventListeners(
		boolean enableClusterLinkReplication,
		boolean clearCachePeerProviderConfigurations, boolean usingDefault,
		CacheConfiguration cacheConfiguration) {

		if (cacheConfiguration == null) {
			return;
		}

		boolean clearDefaultCacheListenerConfigurations =
			clearCachePeerProviderConfigurations ||
				(!usingDefault && !cacheConfiguration.isTerracottaClustered());

		if (clearDefaultCacheListenerConfigurations ) {

			_clearCacheEventListenerConfigurations(cacheConfiguration);

			if (enableClusterLinkReplication) {
				_enableClusterLinkReplication(cacheConfiguration);
			}
		}
	}

	private static void _clearCacheEventListenerConfigurations(
		CacheConfiguration cacheConfiguration) {

		cacheConfiguration.addBootstrapCacheLoaderFactory(null);

		cacheConfiguration.getCacheEventListenerConfigurations().clear();
	}

	private static void _enableClusterLinkReplication(
		CacheConfiguration cacheConfiguration) {

		CacheEventListenerFactoryConfiguration configuration =
			new CacheEventListenerFactoryConfiguration();

		configuration.setClass(
			EhcachePortalCacheClusterReplicatorFactory.class.getName());

		cacheConfiguration.addCacheEventListenerFactory(configuration);
	}

}