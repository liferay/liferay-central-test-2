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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.cache.cluster.EhcachePortalCacheClusterReplicatorFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheConfiguration.CacheEventListenerFactoryConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.FactoryConfiguration;

/**
 * @author Shuyang Zhou
 * @author Edward Han
 */
public class EhcacheConfigurationUtil {

	public static Configuration getConfiguration(String configurationPath) {
		return getConfiguration(configurationPath, false);
	}

	public static Configuration getConfiguration(
		String configurationPath, boolean clusterAware) {

		return getConfiguration(configurationPath, clusterAware, false);
	}

	public static Configuration getConfiguration(
		String configurationPath, boolean clusterAware, boolean usingDefault) {

		if (Validator.isNull(configurationPath)) {
			return null;
		}

		URL configurationURL = EhcacheConfigurationUtil.class.getResource(
			configurationPath);

		return getConfiguration(configurationURL, clusterAware, usingDefault);
	}

	public static Configuration getConfiguration(URL configurationURL) {
		return getConfiguration(configurationURL, false);
	}

	public static Configuration getConfiguration(
		URL configurationURL, boolean clusterAware) {

		return getConfiguration(configurationURL, clusterAware, false);
	}

	public static Configuration getConfiguration(
		URL configurationURL, boolean clusterAware, boolean usingDefault) {

		if (configurationURL == null) {
			return null;
		}

		Configuration configuration = ConfigurationFactory.parseConfiguration(
			configurationURL);

		if (!clusterAware) {
			return configuration;
		}

		if (PropsValues.CLUSTER_LINK_ENABLED &&
			PropsValues.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED) {

			List<CacheConfiguration> cacheConfigurations =
				_getAllCacheConfigurations(configuration);

			_setBootstrapCacheLoaderConfigurations(cacheConfigurations);

			Map<CacheConfiguration, String> cacheEventListenerProperties =
				_clearEhcacheReplicatorConfigurations(
					configuration, cacheConfigurations);

			for (Map.Entry<CacheConfiguration, String> entry :
					cacheEventListenerProperties.entrySet()) {

				_enableClusterLinkReplication(entry.getKey(), entry.getValue());
			}
		}
		else if (usingDefault) {
			List<CacheConfiguration> cacheConfigurations =
				_getAllCacheConfigurations(configuration);

			_setBootstrapCacheLoaderConfigurations(cacheConfigurations);

			if (!PropsValues.CLUSTER_LINK_ENABLED) {
				_clearEhcacheReplicatorConfigurations(
					configuration, cacheConfigurations);
			}
		}

		return configuration;
	}

	private static String _clearCacheEventListenerConfigurations(
		CacheConfiguration cacheConfiguration) {

		List<?> cacheEventListenerConfigurations =
			cacheConfiguration.getCacheEventListenerConfigurations();

		String cacheEventListenerProperties = null;

		for (Object cacheEventListenerConfiguration :
				cacheEventListenerConfigurations) {

			CacheEventListenerFactoryConfiguration
				cacheEventListenerFactoryConfiguration =
					(CacheEventListenerFactoryConfiguration)
						cacheEventListenerConfiguration;

			String fullyQualifiedClassPath =
				cacheEventListenerFactoryConfiguration.
					getFullyQualifiedClassPath();

			if (fullyQualifiedClassPath.contains(
					"LiferayCacheEventListenerFactory") ||
				fullyQualifiedClassPath.contains(
					"net.sf.ehcache.distribution")) {

				cacheEventListenerProperties =
					cacheEventListenerFactoryConfiguration.getProperties();

				break;
			}
		}

		cacheEventListenerConfigurations.clear();

		return cacheEventListenerProperties;
	}

	@SuppressWarnings("rawtypes")
	private static Map<CacheConfiguration, String>
		_clearEhcacheReplicatorConfigurations(
			Configuration configuration,
			List<CacheConfiguration> cacheConfigurations) {

		List<FactoryConfiguration> factoryConfigurations =
			configuration.getCacheManagerPeerListenerFactoryConfigurations();

		factoryConfigurations.clear();

		factoryConfigurations =
			configuration.getCacheManagerPeerProviderFactoryConfiguration();

		factoryConfigurations.clear();

		Map<CacheConfiguration, String> cacheEventListenerProperties =
			new HashMap<CacheConfiguration, String>();

		for (CacheConfiguration cacheConfiguration : cacheConfigurations) {
			String properties = _clearCacheEventListenerConfigurations(
				cacheConfiguration);

			cacheEventListenerProperties.put(cacheConfiguration, properties);
		}

		return cacheEventListenerProperties;
	}

	private static void _enableClusterLinkReplication(
		CacheConfiguration cacheConfiguration,
		String cacheEventListenerProperties) {

		CacheEventListenerFactoryConfiguration
			cacheEventListenerFactoryConfiguration =
				new CacheEventListenerFactoryConfiguration();

		cacheEventListenerFactoryConfiguration.setClass(
			EhcachePortalCacheClusterReplicatorFactory.class.getName());
		cacheEventListenerFactoryConfiguration.setProperties(
			cacheEventListenerProperties);

		cacheConfiguration.addCacheEventListenerFactory(
			cacheEventListenerFactoryConfiguration);
	}

	private static List<CacheConfiguration> _getAllCacheConfigurations(
		Configuration configuration) {

		List<CacheConfiguration> cacheConfigurations =
			new ArrayList<CacheConfiguration>();

		CacheConfiguration defaultCacheConfiguration =
			configuration.getDefaultCacheConfiguration();

		if (defaultCacheConfiguration != null) {
			cacheConfigurations.add(defaultCacheConfiguration);
		}

		Map<String, CacheConfiguration> cacheConfigurationsMap =
			configuration.getCacheConfigurations();

		for (CacheConfiguration cacheConfiguration :
				cacheConfigurationsMap.values()) {

			if (cacheConfiguration != null) {
				cacheConfigurations.add(cacheConfiguration);
			}
		}

		return cacheConfigurations;
	}

	private static void _setBootstrapCacheLoaderConfigurations(
		List<CacheConfiguration> cacheConfigurations) {

		if (PropsValues.EHCACHE_BOOTSTRAP_CACHE_LOADER_ENABLED) {
			return;
		}

		for (CacheConfiguration cacheConfiguration : cacheConfigurations) {
			cacheConfiguration.addBootstrapCacheLoaderFactory(null);
		}
	}

}