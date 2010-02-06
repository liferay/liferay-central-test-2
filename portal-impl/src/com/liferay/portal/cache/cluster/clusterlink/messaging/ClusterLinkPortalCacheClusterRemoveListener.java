/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cache.cluster.clusterlink.messaging;

import com.liferay.portal.SystemException;
import com.liferay.portal.cache.ehcache.EhcachePortalCacheManager;
import com.liferay.portal.dao.orm.hibernate.EhCacheProvider;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEvent;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEventType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * <a href="ClusterLinkPortalCacheClusterRemoveListener.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ClusterLinkPortalCacheClusterRemoveListener
	implements MessageListener {

	public ClusterLinkPortalCacheClusterRemoveListener(
			EhcachePortalCacheManager ehcachePortalCacheManager)
		throws SystemException {

		_hibernateCacheManager = EhCacheProvider.getCacheManager();
		_portalCacheManager = ehcachePortalCacheManager.getEhcacheManager();
	}

	public void receive(Message message) {
		PortalCacheClusterEvent portalCacheClusterEvent =
			(PortalCacheClusterEvent)message.getPayload();

		if (portalCacheClusterEvent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Payload is null");
			}

			return;
		}

		String cacheName = portalCacheClusterEvent.getCacheName();

		Cache cache = _portalCacheManager.getCache(cacheName);

		if (cache == null) {
			cache = _hibernateCacheManager.getCache(cacheName);
		}

		if (cache != null) {
			PortalCacheClusterEventType portalCacheClusterEventType =
				portalCacheClusterEvent.getEventType();

			if (portalCacheClusterEventType.equals(
					PortalCacheClusterEventType.REMOVEALL)) {

				cache.removeAll(true);
			}
			else {
				cache.remove(portalCacheClusterEvent.getElementKey(), true);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ClusterLinkPortalCacheClusterRemoveListener.class);

	private CacheManager _hibernateCacheManager;
	private CacheManager _portalCacheManager;

}