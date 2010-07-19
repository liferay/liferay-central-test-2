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

import com.liferay.portal.kernel.exception.SystemException;

import java.lang.reflect.Field;

import java.util.Properties;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.util.FailSafeTimer;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.CacheProvider;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@SuppressWarnings("deprecation")
public class EhCacheProvider extends CacheProviderWrapper {

	public static CacheManager getCacheManager() throws SystemException {
		try {
			Class<?> clazz = Class.forName(
				"net.sf.ehcache.hibernate.AbstractEhcacheProvider");

			Field field = clazz.getDeclaredField("manager");

			field.setAccessible(true);

			CacheManager cacheManager = (CacheManager)field.get(
				_cacheProvider);

			if (cacheManager == null) {
				throw new SystemException("CacheManager has been initialized");
			}

			return cacheManager;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public EhCacheProvider() {
		super("net.sf.ehcache.hibernate.EhCacheProvider");

		_cacheProvider = cacheProvider;
	}

	public void start(Properties properties) throws CacheException {
		super.start(properties);

		try {
			CacheManager cacheManager = getCacheManager();

			FailSafeTimer failSafeTimer = cacheManager.getTimer();

			failSafeTimer.cancel();
		}
		catch (SystemException se) {
			throw new CacheException(se);
		}
	}

	private static CacheProvider _cacheProvider;

}