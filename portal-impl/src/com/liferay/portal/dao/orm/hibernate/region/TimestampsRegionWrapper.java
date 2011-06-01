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

import net.sf.ehcache.hibernate.regions.EhcacheTimestampsRegion;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.TimestampsRegion;

/**
 * @author Edward Han
 */
public class TimestampsRegionWrapper
	extends BaseRegionWrapper implements TimestampsRegion {

	public TimestampsRegionWrapper(
		EhcacheTimestampsRegion ehcacheTimestampsRegion) {

		super(ehcacheTimestampsRegion);
	}

	public void evict(Object key) throws CacheException {
		getEhcacheTimestampsRegion().evict(key);
	}

	public void evictAll() throws CacheException {
		getEhcacheTimestampsRegion().evictAll();
	}

	public Object get(Object key) throws CacheException {
		return getEhcacheTimestampsRegion().get(key);
	}

	public void invalidate() {
		getEhcacheTimestampsRegion().evictAll();
	}

	public void put(Object key, Object value) throws CacheException {
		getEhcacheTimestampsRegion().put(key, value);
	}

	protected EhcacheTimestampsRegion getEhcacheTimestampsRegion() {
		return (EhcacheTimestampsRegion) getEhcacheDataRegion();
	}

}