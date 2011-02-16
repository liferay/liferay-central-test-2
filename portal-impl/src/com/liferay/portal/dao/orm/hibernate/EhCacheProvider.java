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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.cache.ehcache.EhcacheConfigurationUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Properties;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.hibernate.EhCache;

import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CacheProvider;
import org.hibernate.cache.Timestamper;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@SuppressWarnings("deprecation")
public class EhCacheProvider implements CacheProvider {

	public static CacheManager getCacheManager() {
		return _cacheManager;
	}

	public Cache buildCache(String name, Properties properties)
		throws CacheException {

		Ehcache ehcache = _cacheManager.getEhcache(name);

		if (ehcache != null) {
			return new EhCache(ehcache);
		}

		synchronized (_cacheManager) {
			ehcache = _cacheManager.getEhcache(name);

			if (ehcache == null) {
				_cacheManager.addCache(name);

				ehcache = _cacheManager.getEhcache(name);

				ehcache.setStatisticsEnabled(
					PropsValues.EHCACHE_STATISTICS_ENABLED);
			}
		}

		return new EhCache(ehcache);
	}

	public boolean isMinimalPutsEnabledByDefault() {
		return _MINIMAL_PUTS_ENABLED_BY_DEFAULT;
	}

	public long nextTimestamp() {
		return Timestamper.next();
	}

	public void start(Properties properties) throws CacheException {
		if (_cacheManager != null) {
			return;
		}

		Configuration configuration = EhcacheConfigurationUtil.getConfiguration(
			PropsValues.NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME);

		_cacheManager = new CacheManager(configuration);
	}

	public void stop() {
		if (_cacheManager == null) {
			return;
		}

		_cacheManager.shutdown();

		_cacheManager = null;
	}

	private static final boolean _MINIMAL_PUTS_ENABLED_BY_DEFAULT = true;

	private static volatile CacheManager _cacheManager;

}