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
import com.liferay.portal.kernel.cache.BlockingPortalCache;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;

import java.net.URL;

import javax.management.MBeanServer;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.ObjectExistsException;
import net.sf.ehcache.config.CacheConfiguration.CacheEventListenerFactoryConfiguration;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.management.ManagementService;
import net.sf.ehcache.util.FailSafeTimer;

/**
 * @author Joseph Shum
 * @author Raymond Augé
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class EhcachePortalCacheManager implements PortalCacheManager {

	public void afterPropertiesSet() {
		Configuration configuration = getConfiguration();

		_cacheManager = new CacheManager(configuration);

		FailSafeTimer failSafeTimer = _cacheManager.getTimer();

		failSafeTimer.cancel();

		try {
			Field cacheManagerTimerField = ReflectionUtil.getDeclaredField(
				CacheManager.class, "cacheManagerTimer");

			cacheManagerTimerField.set(_cacheManager, null);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (PropsValues.EHCACHE_PORTAL_CACHE_MANAGER_JMX_ENABLED) {
			_managementService = new ManagementService(
				_cacheManager, _mBeanServer, _registerCacheManager,
				_registerCaches, _registerCacheConfigurations,
				_registerCacheStatistics);

			_managementService.init();
		}
	}

	public void clearAll() {
		_cacheManager.clearAll();
	}

	public void destroy() throws Exception {
		try {
			_cacheManager.shutdown();
		}
		finally {
			if (_managementService != null) {
				_managementService.dispose();
			}
		}
	}

	public PortalCache getCache(String name) {
		return getCache(name, false);
	}

	public PortalCache getCache(String name, boolean blocking) {
		Ehcache cache = _cacheManager.getEhcache(name);

		if (cache == null) {
			synchronized (_cacheManager) {
				cache = _cacheManager.getEhcache(name);

				if (cache == null) {
					try {
						_cacheManager.addCache(name);
					}
					catch (ObjectExistsException oee) {

						// LEP-7122

					}

					cache = _cacheManager.getEhcache(name);

					cache.setStatisticsEnabled(
						PropsValues.EHCACHE_STATISTICS_ENABLED);

					if (_log.isInfoEnabled()) {
						_log.info(
							"Cache name " + name + " is using implementation " +
								cache.getClass().getName());
					}
				}
			}
		}

		PortalCache portalCache = new EhcachePortalCache(cache);

		portalCache.setDebug(_debug);

		if (PropsValues.EHCACHE_BLOCKING_CACHE_ALLOWED && blocking) {
			portalCache = new BlockingPortalCache(portalCache);
		}

		return portalCache;
	}

	public CacheManager getEhcacheManager() {
		return _cacheManager;
	}

	public void removeCache(String name) {
		_cacheManager.removeCache(name);
	}

	public void setConfigPropertyKey(String configPropertyKey) {
		_configPropertyKey = configPropertyKey;
	}

	public void setDebug(boolean debug) {
		_debug = debug;
	}

	public void setMBeanServer(MBeanServer mBeanServer) {
		_mBeanServer = mBeanServer;
	}

	public void setRegisterCacheConfigurations(
		boolean registerCacheConfigurations) {

		_registerCacheConfigurations = registerCacheConfigurations;
	}

	public void setRegisterCacheManager(boolean registerCacheManager) {
		_registerCacheManager = registerCacheManager;
	}

	public void setRegisterCaches(boolean registerCaches) {
		_registerCaches = registerCaches;
	}

	public void setRegisterCacheStatistics(boolean registerCacheStatistics) {
		_registerCacheStatistics = registerCacheStatistics;
	}

	protected Configuration getConfiguration() {
		URL url = getClass().getResource(PropsUtil.get(_configPropertyKey));

		Configuration configuration = ConfigurationFactory.parseConfiguration(
			url);

		if (PropsValues.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED) {
			configuration.getCacheManagerPeerProviderFactoryConfiguration().
				clear();
			configuration.getCacheManagerPeerListenerFactoryConfigurations().
				clear();

			CacheConfiguration defaultCacheConfiguration =
				configuration.getDefaultCacheConfiguration();

			processCacheConfiguration(defaultCacheConfiguration);

			for (CacheConfiguration cacheConfiguration :
					configuration.getCacheConfigurations().values()) {

				processCacheConfiguration(cacheConfiguration);
			}
		}

		return configuration;
	}

	protected void processCacheConfiguration(
		CacheConfiguration cacheConfiguration) {

		cacheConfiguration.addBootstrapCacheLoaderFactory(null);

		cacheConfiguration.getCacheEventListenerConfigurations().clear();

		CacheEventListenerFactoryConfiguration configuration =
			new CacheEventListenerFactoryConfiguration();

		configuration.setClass(
			EhcachePortalCacheClusterReplicatorFactory.class.getName());

		cacheConfiguration.addCacheEventListenerFactory(configuration);
	}

	private static Log _log = LogFactoryUtil.getLog(
		EhcachePortalCacheManager.class);

	private String _configPropertyKey;
	private CacheManager _cacheManager;
	private boolean _debug;
	private ManagementService _managementService;
	private MBeanServer _mBeanServer;
	private boolean _registerCacheManager = true;
	private boolean _registerCaches = true;
	private boolean _registerCacheConfigurations = true;
	private boolean _registerCacheStatistics = true;

}