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

import com.liferay.portal.kernel.cluster.ClusterResponse;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * <a href="ClusterResponseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Tina Tian
 */
public class ClusterResponseImpl implements ClusterResponse {

	public Exception getException() {
		return _exception;
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

	public void setException(Exception exception) {
		_exception = exception;
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

		sb.append("{exception=");
		sb.append(_exception);
		sb.append(", multicast=");
		sb.append(_multicast);
		sb.append(", result=");
		sb.append(_result);
		sb.append(", uuid=");
		sb.append(_uuid);
		sb.append("}");

		return sb.toString();
	}

	private Exception _exception;
	private boolean _multicast;
	private Object _result;
	private String _uuid;

}