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

package com.liferay.portal.cache.cluster;

import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEvent;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEventType;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterLinkUtil;
import com.liferay.portal.kernel.util.StringPool;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.CacheReplicator;

/**
 * @author Shuyang Zhou
 */
public class EhcachePortalCacheClusterReplicator implements CacheReplicator {

	public boolean alive() {
		return true;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void dispose() {
	}

	public boolean isReplicateUpdatesViaCopy() {
		return false;
	}

	public boolean notAlive() {
		return false;
	}

	public void notifyElementEvicted(Ehcache ehcache, Element element) {
		PortalCacheClusterLinkUtil.sendEvent(
			new PortalCacheClusterEvent(
				ehcache.getName(), element.getKey(),
				PortalCacheClusterEventType.EVICTED));
	}

	public void notifyElementExpired(Ehcache ehcache, Element element) {
		PortalCacheClusterLinkUtil.sendEvent(
			new PortalCacheClusterEvent(
				ehcache.getName(), element.getKey(),
				PortalCacheClusterEventType.EXPIRED));
	}

	public void notifyElementPut(Ehcache ehcache, Element element)
		throws CacheException {
		PortalCacheClusterLinkUtil.sendEvent(
			new PortalCacheClusterEvent(
				ehcache.getName(), element.getKey(),
				PortalCacheClusterEventType.PUT));
	}

	public void notifyElementRemoved(Ehcache ehcache, Element element)
		throws CacheException {
		PortalCacheClusterLinkUtil.sendEvent(
			new PortalCacheClusterEvent(
				ehcache.getName(), element.getKey(),
				PortalCacheClusterEventType.REMOVE));
	}

	public void notifyElementUpdated(Ehcache ehcache, Element element)
		throws CacheException {
		PortalCacheClusterLinkUtil.sendEvent(
			new PortalCacheClusterEvent(
				ehcache.getName(), element.getKey(),
				PortalCacheClusterEventType.UPDATE));
	}

	public void notifyRemoveAll(Ehcache ehcache) {
		PortalCacheClusterLinkUtil.sendEvent(
			new PortalCacheClusterEvent(
				ehcache.getName(), StringPool.BLANK,
				PortalCacheClusterEventType.REMOVEALL));
	}

}