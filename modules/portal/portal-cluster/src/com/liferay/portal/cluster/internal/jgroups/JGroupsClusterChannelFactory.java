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

package com.liferay.portal.cluster.internal.jgroups;

import com.liferay.portal.cluster.ClusterChannel;
import com.liferay.portal.cluster.ClusterChannelFactory;
import com.liferay.portal.cluster.ClusterReceiver;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.cluster.configuration.ClusterLinkConfiguration",
	immediate = true, service = ClusterChannelFactory.class
)
public class JGroupsClusterChannelFactory implements ClusterChannelFactory {

	@Override
	public ClusterChannel createClusterChannel(
		String channelProperties, String clusterName,
		ClusterReceiver clusterReceiver) {

		return new JGroupsClusterChannel(
			channelProperties, clusterName, clusterReceiver);
	}

}