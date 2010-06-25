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

package com.liferay.portal.kernel.cluster;

import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <a href="ClusterRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Tina Tian
 */
public class ClusterRequest implements Serializable {

	public static ClusterRequest createMulticastRequest(
		MethodWrapper methodWrapper) {

		ClusterRequest request = new ClusterRequest();

		request.setUuid(PortalUUIDUtil.generate());
		request.setMethodWrapper(methodWrapper);
		request.setMessageType(ClusterMessageType.EXECUTE);
		request.setMulticast(true);

		return request;
	}

	public static ClusterRequest createMulticastRequest(
		MethodWrapper methodWrapper, long timeOut) {

		ClusterRequest request = new ClusterRequest();

		request.setUuid(PortalUUIDUtil.generate());
		request.setMethodWrapper(methodWrapper);
		request.setMessageType(ClusterMessageType.EXECUTE);
		request.setTimeOut(timeOut);
		request.setMulticast(true);

		return request;
	}

	public static ClusterRequest createUnicastRequest(
		MethodWrapper methodWrapper, String... targetClusterNodeIds) {

		ClusterRequest request = new ClusterRequest();

		request.setUuid(PortalUUIDUtil.generate());
		request.setMethodWrapper(methodWrapper);
		request.setMessageType(ClusterMessageType.EXECUTE);
		request.addTargetClusterNodes(targetClusterNodeIds);
		request.setMulticast(false);

		return request;
	}

	public static ClusterRequest createUnicastRequest(
		MethodWrapper methodWrapper, long timeOut,
		String... targetClusterNodeIds) {

		ClusterRequest request = new ClusterRequest();

		request.setUuid(PortalUUIDUtil.generate());
		request.setMethodWrapper(methodWrapper);
		request.setMessageType(ClusterMessageType.EXECUTE);
		request.setTimeOut(timeOut);
		request.addTargetClusterNodes(targetClusterNodeIds);
		request.setMulticast(false);

		return request;
	}

	public static ClusterRequest createClusterRequest(
		ClusterMessageType type, ClusterNode originatingClusterNode) {

		ClusterRequest request = new ClusterRequest();
		request.setUuid(PortalUUIDUtil.generate());
		request.setOriginatingClusterNode(originatingClusterNode);
		request.setMessageType(type);
		request.setMulticast(true);

		return request;
	}

	public void addTargetClusterNodes(String... targetClusterNodeIds) {
		if (_targetClusterNodeIds == null) {
			_targetClusterNodeIds = new HashSet<String>();
		}

		_targetClusterNodeIds.addAll(Arrays.asList(targetClusterNodeIds));
	}

	public ClusterNode getOriginatingClusterNode() {
		return _originatingClusterNode;
	}

	public ClusterMessageType getMessageType() {
		return _messageType;
	}

	public MethodWrapper getMethodWrapper() {
		return _methodWrapper;
	}

	public Collection<String> getTargetClusterNodeIds() {
		return _targetClusterNodeIds;
	}

	public long getTimeOut() {
		return _timeOut;
	}

	public TimeUnit getTimeUnit() {
		return _timeUnit;
	}

	public String getUuid() {
		return _uuid;
	}

	public boolean isMulticast() {
		return _multicast;
	}

	public void setOriginatingClusterNode(ClusterNode originatingClusterNode) {
		_originatingClusterNode = originatingClusterNode;
	}

	public void setMessageType(ClusterMessageType type) {
		_messageType = type;
	}

	public void setMethodWrapper(MethodWrapper methodWrapper) {
		_methodWrapper = methodWrapper;
	}

	public void setMulticast(boolean multicast) {
		_multicast = multicast;
	}

	public void setTimeOut(long timeOut) {
		_timeOut = timeOut;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		_timeUnit = timeUnit;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{uuid=");
		sb.append(_uuid);
		sb.append(", message type=");
		sb.append(_messageType);
		sb.append(", multicast=");
		sb.append(_multicast);

		if (_messageType.equals(ClusterMessageType.NOTIFY) ||
			_messageType.equals(ClusterMessageType.UPDATE)) {

			sb.append(", clusterNode=");
			sb.append(_originatingClusterNode);
		}
		else {
			sb.append(", methodWrapper =");
			sb.append(_methodWrapper);
		}

		sb.append("}");

		return sb.toString();
	}

	private ClusterRequest() {
	}

	private ClusterNode _originatingClusterNode;
	private ClusterMessageType _messageType;
	private MethodWrapper _methodWrapper;
	private boolean _multicast;
	private long _timeOut;
	private TimeUnit _timeUnit = TimeUnit.MILLISECONDS;
	private String _uuid;

	private Set<String> _targetClusterNodeIds;
}