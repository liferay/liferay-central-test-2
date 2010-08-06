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

package com.liferay.portal.kernel.management;

import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.model.ClusterGroup;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class ClusterManageActionWrapper implements ManageAction {

 	public ClusterManageActionWrapper(
		ClusterGroup clusterGroup, ManageAction manageAction) {

		_clusterGroup = clusterGroup;
		_manageAction = manageAction;
	}

	public void action() throws ManageActionException {
		try {
			doAction();
		}
		catch (SystemException se) {
			throw new ManageActionException(
				"Failed to execute cluster manage action", se);
		}
	}

	private void doAction() throws ManageActionException, SystemException {
		MethodHandler manageActionMethodHandler =
			PortalManagerUtil.createManageActionMethodHandler(_manageAction);

		ClusterRequest clusterRequest = null;

		if (_clusterGroup.isWholeCluster()) {
			clusterRequest = ClusterRequest.createMulticastRequest(
				manageActionMethodHandler);
		}
		else {
			verifyClusterGroup();

			clusterRequest = ClusterRequest.createUnicastRequest(
				manageActionMethodHandler,
				_clusterGroup.getClusterNodeIdsArray());
		}

		ClusterExecutorUtil.execute(clusterRequest);
	}

	private void verifyClusterGroup() throws ManageActionException {
		List<ClusterNode> clusterNodes = ClusterExecutorUtil.getClusterNodes();

		String[] requiredClusterNodesIds =
			_clusterGroup.getClusterNodeIdsArray();

		for (String requiredClusterNodeId : requiredClusterNodesIds) {
			for (ClusterNode clusterNode : clusterNodes) {
				String clusterNodeId = clusterNode.getClusterNodeId();

				if (clusterNodeId.equals(requiredClusterNodeId)) {
					clusterNodes.remove(clusterNode);

					continue;
				}
			}

			throw new ManageActionException(
				"Cluster node " + requiredClusterNodeId + " is not available");
		}
	}

	private ClusterGroup _clusterGroup;
	private ManageAction _manageAction;

}