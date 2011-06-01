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

import net.sf.ehcache.hibernate.regions.EhcacheQueryResultsRegion;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.QueryResultsRegion;

/**
 * @author Edward Han
 */
public class QueryResultsRegionWrapper
	extends BaseRegionWrapper implements QueryResultsRegion {

	public QueryResultsRegionWrapper(
		EhcacheQueryResultsRegion ehcacheQueryResultsRegion) {

		super(ehcacheQueryResultsRegion);
	}

	public void evict(Object key) throws CacheException {
		getEhcacheQueryResultsRegion().evict(key);
	}

	public void evictAll() throws CacheException {
		getEhcacheQueryResultsRegion().evictAll();
	}

	public Object get(Object key) throws CacheException {
		return getEhcacheQueryResultsRegion().get(key);
	}

	public void invalidate() {
		getEhcacheQueryResultsRegion().evictAll();
	}

	public void put(Object key, Object value) throws CacheException {
		getEhcacheQueryResultsRegion().put(key, value);
	}

	protected EhcacheQueryResultsRegion getEhcacheQueryResultsRegion() {
		return (EhcacheQueryResultsRegion) getEhcacheDataRegion();
	}
}