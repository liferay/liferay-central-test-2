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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterChannel;
import com.liferay.portal.kernel.cluster.ClusterReceiver;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.Serializable;

import java.net.InetAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Tina Tian
 */
public class TestClusterChannel implements ClusterChannel {

	public static void clearAllMessages() {
		_multicastMessages.clear();

		_unicastMessages.clear();
	}

	public static List<TestClusterChannel> getClusterChannels() {
		List<TestClusterChannel> clusterChannels = new ArrayList<>();

		for (Map<Address, TestClusterChannel> map : _clusters.values()) {
			clusterChannels.addAll(map.values());
		}

		return clusterChannels;
	}

	public static List<Serializable> getMulticastMessages() {
		return _multicastMessages;
	}

	public static List<ObjectValuePair<Serializable, Address>>
		getUnicastMessages() {

		return _unicastMessages;
	}

	public static void reset() {
		clearAllMessages();

		_clusters.clear();
	}

	public TestClusterChannel(
		String channelProperties, String clusterName,
		ClusterReceiver clusterReceiver) {

		_clusterName = clusterName;
		_clusterReceiver = clusterReceiver;

		_bindInetAddress = InetAddress.getLoopbackAddress();

		UUID uuid = UUID.randomUUID();

		_localAddress = new TestAddress(uuid.toString());

		Map<Address, TestClusterChannel> clusterChannels = _clusters.get(
			_clusterName);

		if (clusterChannels == null) {
			clusterChannels = new HashMap<>();

			_clusters.put(_clusterName, clusterChannels);
		}

		clusterChannels.put(_localAddress, this);
	}

	@Override
	public void close() {
		_closed = true;

		Map<Address, TestClusterChannel> clusterChannels = _clusters.get(
			_clusterName);

		clusterChannels.remove(_localAddress);
	}

	@Override
	public InetAddress getBindInetAddress() {
		return _bindInetAddress;
	}

	@Override
	public String getClusterName() {
		return _clusterName;
	}

	@Override
	public Address getLocalAddress() {
		return _localAddress;
	}

	public ClusterReceiver getReceiver() {
		return _clusterReceiver;
	}

	public boolean isClosed() {
		return _closed;
	}

	@Override
	public void sendMulticastMessage(Serializable message) {
		_multicastMessages.add(message);
	}

	@Override
	public void sendUnicastMessage(Serializable message, Address address) {
		_unicastMessages.add(new ObjectValuePair<>(message, address));
	}

	private static final Map<String, Map<Address, TestClusterChannel>>
		_clusters = new HashMap<>();
	private static final List<Serializable> _multicastMessages =
		new ArrayList<>();
	private static final List<ObjectValuePair<Serializable, Address>>
		_unicastMessages = new ArrayList<>();

	private final InetAddress _bindInetAddress;
	private boolean _closed;
	private final String _clusterName;
	private final ClusterReceiver _clusterReceiver;
	private final Address _localAddress;

}