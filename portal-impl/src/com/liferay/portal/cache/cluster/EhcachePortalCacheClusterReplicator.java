/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
 * <a href="EhcachePortalCacheClusterReplicator.java.html"><b><i>View Source</i>
 * </b></a>
 *
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