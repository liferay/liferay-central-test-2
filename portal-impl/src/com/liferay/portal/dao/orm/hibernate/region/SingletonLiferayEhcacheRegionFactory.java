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

package com.liferay.portal.dao.orm.hibernate.region;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.QueryResultsRegion;
import org.hibernate.cache.RegionFactory;
import org.hibernate.cache.TimestampsRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cfg.Settings;

/**
 * @author Edward Han
 */
public class SingletonLiferayEhcacheRegionFactory implements RegionFactory {
	public static LiferayEhcacheRegionFactory getInstance() {
		return _wrappedRegionFactory;
	}

	public SingletonLiferayEhcacheRegionFactory(Properties properties) {
		synchronized (this) {
			if (_wrappedRegionFactory == null) {
				_wrappedRegionFactory = new LiferayEhcacheRegionFactory(
					properties);
			}
		}
	}

	public EntityRegion buildEntityRegion(
			String regionName, Properties properties,
			CacheDataDescription cacheDataDescription)
		throws CacheException {

		return _wrappedRegionFactory.buildEntityRegion(
			regionName, properties, cacheDataDescription);
	}

	public CollectionRegion buildCollectionRegion(
			String regionName, Properties properties,
			CacheDataDescription cacheDataDescription)
		throws CacheException {

		return _wrappedRegionFactory.buildCollectionRegion(
			regionName,properties, cacheDataDescription);
	}

	public QueryResultsRegion buildQueryResultsRegion(
			String regionName, Properties properties)
		throws CacheException {

		return _wrappedRegionFactory.buildQueryResultsRegion(
			regionName, properties);
	}

	public TimestampsRegion buildTimestampsRegion(
			String regionName, Properties properties)
		throws CacheException {

		return _wrappedRegionFactory.buildTimestampsRegion(
			regionName, properties);
	}

	public AccessType getDefaultAccessType() {
		return _wrappedRegionFactory.getDefaultAccessType();
	}

	public boolean isMinimalPutsEnabledByDefault() {
		return _wrappedRegionFactory.isMinimalPutsEnabledByDefault();
	}

	public long nextTimestamp() {
		return _wrappedRegionFactory.nextTimestamp();
	}

	public synchronized void start(
			Settings settings, Properties properties)
		throws CacheException {

		if (_instanceCounter.getAndIncrement() == 0) {
			_wrappedRegionFactory.start(settings, properties);
		}
	}

	public synchronized void stop() {
		if (_instanceCounter.decrementAndGet() == 0) {
			_wrappedRegionFactory.stop();
		}
	}

	private static AtomicInteger _instanceCounter = new AtomicInteger(0);
	private static LiferayEhcacheRegionFactory _wrappedRegionFactory;
}