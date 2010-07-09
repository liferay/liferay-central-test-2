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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsUtil;

import com.opensymphony.oscache.base.CacheEntry;

import java.util.Properties;

import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CacheProvider;
import org.hibernate.cache.Timestamper;
import org.hibernate.util.StringHelper;

/**
 * @author	   Mathias Bogaert
 * @author	   Brian Wing Shun Chan
 * @deprecated
 */
public class OSCacheProvider implements CacheProvider {

	public static final String OSCACHE_REFRESH_PERIOD = "refresh.period";

	public static final String OSCACHE_CRON = "cron";

	public Cache buildCache(String region, Properties properties)
		throws CacheException {

		int refreshPeriod = GetterUtil.getInteger(
			PropsUtil.get(StringHelper.qualify(region, OSCACHE_REFRESH_PERIOD)),
			CacheEntry.INDEFINITE_EXPIRY);

		String cron = PropsUtil.get(StringHelper.qualify(region, OSCACHE_CRON));

		return new CacheWrapper(new OSCache(refreshPeriod, cron, region));
	}

	public boolean isMinimalPutsEnabledByDefault() {
		return false;
	}

	public long nextTimestamp() {
		return Timestamper.next();
	}

	public void start(Properties properties) throws CacheException {
	}

	public void stop() {
	}

}