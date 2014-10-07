/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cache.AggregatedCacheListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheProvider;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEvent;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEventType;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

import java.io.Serializable;

import java.nio.ByteBuffer;

/**
 * @author Shuyang Zhou
 */
public class ClusterLinkPortalCacheClusterListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		byte[] data = (byte[])message.getPayload();

		Deserializer deserializer = new Deserializer(ByteBuffer.wrap(data));

		PortalCacheClusterEvent portalCacheClusterEvent =
			(PortalCacheClusterEvent)deserializer.readObject();

		if (portalCacheClusterEvent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Payload is null");
			}

			return;
		}

		handlePortalCacheClusterEvent(portalCacheClusterEvent);
	}

	protected void handlePortalCacheClusterEvent(
		PortalCacheClusterEvent portalCacheClusterEvent) {

		PortalCacheManager<? extends Serializable, ?> portalCacheManager =
			PortalCacheProvider.getPortalCacheManager(
				portalCacheClusterEvent.getPortalCacheManagerName());

		PortalCache<Serializable, Serializable> portalCache =
			(PortalCache<Serializable, Serializable>)
				portalCacheManager.getCache(
					portalCacheClusterEvent.getPortalCacheName());

		if (portalCache == null) {
			return;
		}

		PortalCacheClusterEventType portalCacheClusterEventType =
			portalCacheClusterEvent.getEventType();

		boolean remoteInvoke = AggregatedCacheListener.isRemoteInvoke();

		AggregatedCacheListener.setRemoteInvoke(true);

		try {
			if (portalCacheClusterEventType.equals(
					PortalCacheClusterEventType.REMOVE_ALL)) {

				portalCache.removeAll();
			}
			else if (portalCacheClusterEventType.equals(
						PortalCacheClusterEventType.PUT) ||
					 portalCacheClusterEventType.equals(
						PortalCacheClusterEventType.UPDATE)) {

				Serializable key = portalCacheClusterEvent.getElementKey();
				Serializable value = portalCacheClusterEvent.getElementValue();

				if (value == null) {
					portalCache.remove(key);
				}
				else {
					portalCache.put(
						key, value, portalCacheClusterEvent.getTimeToLive());
				}
			}
			else {
				portalCache.remove(portalCacheClusterEvent.getElementKey());
			}
		}
		finally {
			AggregatedCacheListener.setRemoteInvoke(remoteInvoke);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ClusterLinkPortalCacheClusterListener.class);

}