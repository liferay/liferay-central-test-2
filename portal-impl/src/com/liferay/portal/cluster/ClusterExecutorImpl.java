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

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterException;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import org.jgroups.ChannelException;
import org.jgroups.JChannel;

/**
 * <a href="ClusterExecutorImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Tina Tian
 */
public class ClusterExecutorImpl
	extends ClusterBase implements ClusterExecutor {

	public void destroy() {
		if (!PropsValues.CLUSTER_LINK_ENABLED) {
			return;
		}

		_controlChannel.close();
	}

	public Map<Address, Future<?>> executeMulticastCall(
		MethodWrapper methodWrapper) {

		if (!PropsValues.CLUSTER_LINK_ENABLED) {
			return null;
		}

		ClusterRequest clusterRequest = new ClusterRequestImpl();

		clusterRequest.setMulticast(true);
		clusterRequest.setPayload(methodWrapper);
		clusterRequest.setUuid(PortalUUIDUtil.generate());

		Map<Address, Future<?>> results = new HashMap<Address, Future<?>>();

		List<Address> addresses = getControlAddresses();
		Address localControlAddress = getLocalControlAddress();

		for (Address address : addresses) {
			if (_shortcutLocalMethod && address.equals(localControlAddress)) {
				results.put(address, runLocalMethod(methodWrapper));
			}
			else {
				results.put(address, new FutureResult<Object>());
			}
		}

		_multicastResultMap.put(clusterRequest.getUuid(), results);

		try {
			_controlChannel.send(null, null, clusterRequest);
		}
		catch (ChannelException ce) {
			_log.error("Unable to send unicast message " + clusterRequest, ce);
		}

		return results;
	}

	public Future<?> executeUnicastCall(
		Address address, MethodWrapper methodWrapper) {

		if (!PropsValues.CLUSTER_LINK_ENABLED) {
			return null;
		}

		org.jgroups.Address jGroupsAddress =
			(org.jgroups.Address)address.getRealAddress();

		ClusterRequest clusterRequest = new ClusterRequestImpl();

		clusterRequest.setMulticast(false);
		clusterRequest.setPayload(methodWrapper);
		clusterRequest.setUuid(PortalUUIDUtil.generate());

		if (_shortcutLocalMethod && address.equals(getLocalControlAddress())) {
			return runLocalMethod(methodWrapper);
		}

		FutureResult<Object> futureResult = new FutureResult<Object>();

		_unicastResultMap.put(clusterRequest.getUuid(), futureResult);

		try {
			_controlChannel.send(jGroupsAddress, null, clusterRequest);
		}
		catch (ChannelException ce) {
			_log.error("Unable to send unicast message " + clusterRequest, ce);
		}

		return futureResult;
	}

	public List<Address> getControlAddresses() {
		if (!PropsValues.CLUSTER_LINK_ENABLED) {
			return Collections.EMPTY_LIST;
		}

		return getAddresses(_controlChannel);
	}

	public Address getLocalControlAddress() {
		if (!PropsValues.CLUSTER_LINK_ENABLED) {
			return null;
		}

		return new AddressImpl(_controlChannel.getLocalAddress());
	}

	public boolean isShortcutLocalMethod() {
		return _shortcutLocalMethod;
	}

	public void setShortcutLocalMethod(boolean shortcutLocalMethod) {
		_shortcutLocalMethod = shortcutLocalMethod;
	}

	protected void initChannels() throws ChannelException {
		Properties controlProperties = PropsUtil.getProperties(
			PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL, false);

		String controlProperty = controlProperties.getProperty(
			PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL);

		ClusterInvokeReceiver clusterInvokeReceiver = new ClusterInvokeReceiver(
			_multicastResultMap, _unicastResultMap);

		_controlChannel = createChannel(
			controlProperty, clusterInvokeReceiver, _LIFERAY_CONTROL_CHANNEL);

		clusterInvokeReceiver.setChannel(_controlChannel);
	}

	protected FutureResult<Object> runLocalMethod(MethodWrapper methodWrapper) {
		FutureResult<Object> futureResult = new FutureResult<Object>();

		try {
			Object returnValue = MethodInvoker.invoke(methodWrapper);

			if (returnValue instanceof Serializable) {
				futureResult.setResult(returnValue);
			}
			else if (returnValue != null) {
				futureResult.setException(
					new ClusterException("Return value is not serializable"));
			}
			else {
				futureResult.setResult(null);
			}
		}
		catch (Exception e) {
			futureResult.setException(e);
		}

		return futureResult;
	}

	private static final String _LIFERAY_CONTROL_CHANNEL =
		"LIFERAY-CONTROL-CHANNEL";

	private static Log _log = LogFactoryUtil.getLog(ClusterExecutorImpl.class);

	private JChannel _controlChannel;
	private Map<String, Map<Address, Future<?>>> _multicastResultMap =
		new ConcurrentHashMap<String, Map<Address, Future<?>>>();
	private boolean _shortcutLocalMethod;
	private Map<String, Future<?>> _unicastResultMap =
		new ConcurrentHashMap<String, Future<?>>();

}