/**
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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Properties;

import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CacheProvider;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated
 */
public class CacheProviderWrapper implements CacheProvider {

	public CacheProviderWrapper(CacheProvider cacheProvider) {
		this.cacheProvider = cacheProvider;
	}

	public CacheProviderWrapper(String cacheProviderClassName) {
		try {
			cacheProvider = (CacheProvider)Class.forName(
				cacheProviderClassName).newInstance();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public Cache buildCache(String regionName, Properties properties)
		throws CacheException {

		return new CacheWrapper(
			cacheProvider.buildCache(regionName, properties));
	}

	public boolean isMinimalPutsEnabledByDefault() {
		return cacheProvider.isMinimalPutsEnabledByDefault();
	}

	public long nextTimestamp() {
		return cacheProvider.nextTimestamp();
	}

	public void start(Properties properties) throws CacheException {
		cacheProvider.start(properties);
	}

	public void stop() {
		cacheProvider.stop();
	}

	protected CacheProvider cacheProvider;

	private static Log _log = LogFactoryUtil.getLog(CacheProviderWrapper.class);

}