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

package com.liferay.portal.cache.cluster.clusterlink;

import com.liferay.portal.kernel.cache.cluster.BasePortalCacheClusterChannel;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterLinkUtil;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Shuyang Zhou
 */
public class ClusterLinkPortalCacheClusterChannel
	extends BasePortalCacheClusterChannel {

	public ClusterLinkPortalCacheClusterChannel(
		String destination, Priority priority) {

		_destination = destination;
		_priority = priority;
	}

	public void dispatchEvent(PortalCacheClusterEvent portalCacheClusterEvent) {
		Message message = new Message();

		message.setDestinationName(_destination);
		message.setPayload(portalCacheClusterEvent);

		ClusterLinkUtil.sendMulticastMessage(message, _priority);
	}

	private String _destination;
	private Priority _priority;

}