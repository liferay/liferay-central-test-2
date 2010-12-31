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

package com.liferay.portal.kernel.cluster;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class ClusterNodeResponses implements Serializable {

	public static final ClusterNodeResponses EMPTY_CLUSTER_NODE_RESPONSES =
		new ClusterNodeResponses();

	public void addClusterResponse(ClusterNodeResponse clusterNodeResponse) {
		_clusterResponses.put(
			clusterNodeResponse.getClusterNode(), clusterNodeResponse);
	}

	public ClusterNodeResponse getClusterResponse(ClusterNode clusterNode) {
		return _clusterResponses.get(clusterNode);
	}

	public Map<ClusterNode, ClusterNodeResponse> getClusterResponses() {
		return Collections.unmodifiableMap(_clusterResponses);
	}

	public int size() {
		return _clusterResponses.size();
	}

	private Map<ClusterNode, ClusterNodeResponse> _clusterResponses =
		new HashMap<ClusterNode, ClusterNodeResponse>();

}