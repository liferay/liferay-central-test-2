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

package com.liferay.portal.cache.cluster.clusterlink.messaging;

import com.liferay.portal.cache.ehcache.EhcachePortalCacheManager;
import com.liferay.portal.dao.orm.hibernate.EhCacheProvider;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEvent;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEventType;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
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