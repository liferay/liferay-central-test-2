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

import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * <a href="ClusterNodeResponse.java.html"><b><i>View Source</i></b></a>
 *
 * @author Tina Tian
 */
public class ClusterNodeResponse implements Serializable {

	public ClusterNode getClusterNode() {
		return _clusterNode;
	}

	public Exception getException() {
		return _exception;
	}

	public ClusterMessageType getMessageType() {
		return _messageType;
	}

	public Object getResult() {
		return _result;
	}

	public String getUuid() {
		return _uuid;
	}

	public boolean hasException() {
		return _exception != null;
	}

	public boolean isMulticast() {
		return _multicast;
	}

	public void setClusterNode(ClusterNode clusterNode) {
		_clusterNode = clusterNode;
	}

	public void setException(Exception exception) {
		_exception = exception;
	}

	public void setMessageType(ClusterMessageType type) {
		_messageType = type;
	}

	public void setMulticast(boolean multicast) {
		_multicast = multicast;
	}

	public void setResult(Object result) {
		_result = result;
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

		if ((_messageType != null) &&
			(_messageType.equals(ClusterMessageType.NOTIFY) ||
			_messageType.equals(ClusterMessageType.UPDATE))) {

			sb.append(", clusterNode=");
			sb.append(_clusterNode);
		}
		else {
			if (hasException()) {
				sb.append(", exception=");
				sb.append(_exception);
			}
			else {
				sb.append(", result=");
				sb.append(_result);
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private ClusterNode _clusterNode;
	private Exception _exception;
	private ClusterMessageType _messageType;
	private boolean _multicast;
	private Object _result;
	private String _uuid;

}