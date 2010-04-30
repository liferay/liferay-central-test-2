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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.MethodWrapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * <a href="ClusterExecutorUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Tina Tian
 */
public class ClusterExecutorUtil {

	public static Map<Address, Future<?>> executeMulticastCall(
		MethodWrapper methodWrapper) {

		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return null;
		}

		return _clusterExecutor.executeMulticastCall(methodWrapper);
	}

	public static Future<?> executeUnicastCall(
		Address address, MethodWrapper methodWrapper) {

		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return null;
		}

		return _clusterExecutor.executeUnicastCall(address, methodWrapper);
	}

	public static Address getAddress(Message message) {
		return (Address)message.get(_ADDRESS);
	}

	public static ClusterExecutor getClusterExecutor() {
		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return null;
		}

		return _clusterExecutor;
	}

	public static List<Address> getControlAddresses() {
		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return Collections.EMPTY_LIST;
		}

		return _clusterExecutor.getControlAddresses();
	}

	public static Address getLocalControlAddresses() {
		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return null;
		}

		return _clusterExecutor.getLocalControlAddress();
	}

	public static boolean isShortcutLocalMethod() {
		if ((_clusterExecutor == null) || !_clusterExecutor.isEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn("ClusterExecutorUtil has not been initialized");
			}

			return true;
		}

		return _clusterExecutor.isShortcutLocalMethod();
	}

	public void setClusterExecutor(ClusterExecutor clusterExecutor) {
		_clusterExecutor = clusterExecutor;
	}

	private static final String _ADDRESS = "CLUSTER_CONTROL_ADDRESS";

	private static Log _log = LogFactoryUtil.getLog(ClusterExecutorUtil.class);

	private static ClusterExecutor _clusterExecutor;

}