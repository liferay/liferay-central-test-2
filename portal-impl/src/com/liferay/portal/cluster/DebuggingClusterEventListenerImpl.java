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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * <a href="DebuggingClusterEventListenerImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * This is used for testing purposes only
 *
 * @author Tina Tian
 */
public class DebuggingClusterEventListenerImpl implements ClusterEventListener {

	public void processClusterEvent(ClusterEvent clusterEvent) {
		if (_log.isInfoEnabled()) {
			if (clusterEvent.getClusterEventType().equals(ClusterEventType.JOIN)) {
				_log.info("---JOIN----");
			}
			else {
				_log.info("-----Depart!----");
			}

			for (ClusterNode clusterNode: clusterEvent.getClusterNodes()) {
				_log.info("-----clusterNode: ");
				_log.info(clusterNode);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(DebuggingClusterEventListenerImpl.class);
}