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
 */
public class EhcacheConfigurationUtil {

	public static Configuration getConfiguration(
		String configurationResourcePath) {

		if (Validator.isNull(configurationResourcePath)) {
			return null;
		}

		URL url = EhcacheConfigurationUtil.class.getResource(
			configurationResourcePath);

		Configuration configuration = ConfigurationFactory.parseConfiguration(
			url);

		if (PropsValues.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED) {
			configuration.getCacheManagerPeerProviderFactoryConfiguration().
				clear();
			configuration.getCacheManagerPeerListenerFactoryConfigurations().
				clear();

			CacheConfiguration defaultCacheConfiguration =
				configuration.getDefaultCacheConfiguration();

			_processCacheConfiguration(defaultCacheConfiguration);

			for (CacheConfiguration cacheConfiguration :
					configuration.getCacheConfigurations().values()) {

				_processCacheConfiguration(cacheConfiguration);
			}
		}

		return configuration;
	}

	private static void _processCacheConfiguration(
		CacheConfiguration cacheConfiguration) {

		cacheConfiguration.addBootstrapCacheLoaderFactory(null);

		cacheConfiguration.getCacheEventListenerConfigurations().clear();

		CacheEventListenerFactoryConfiguration configuration =
			new CacheEventListenerFactoryConfiguration();

		configuration.setClass(
			EhcachePortalCacheClusterReplicatorFactory.class.getName());

		cacheConfiguration.addCacheEventListenerFactory(configuration);
	}

}