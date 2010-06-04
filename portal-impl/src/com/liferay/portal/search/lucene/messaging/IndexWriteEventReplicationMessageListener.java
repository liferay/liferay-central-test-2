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

package com.liferay.portal.search.lucene.messaging;

import com.liferay.portal.kernel.cluster.messaging.ClusterBridgeMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

/**
 * <a href="IndexWriteEventReplicationMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class IndexWriteEventReplicationMessageListener
	implements MessageListener {


	public void setClusterBridgeMessageListener(
		ClusterBridgeMessageListener clusterBridgeListener) {

		_clusterBridgeListener = clusterBridgeListener;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void receive(Message message) {
		if (!_active) {

			return;
		}
		_clusterBridgeListener.receive(message);
	}

	private boolean _active = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INDEX_REPLICATE_LUCENE_WRITE_MESSAGE), false);
	private ClusterBridgeMessageListener _clusterBridgeListener;

}
