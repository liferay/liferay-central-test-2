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
import com.liferay.portal.kernel.cluster.ClusterChannelFactory;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterException;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterReceiver;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PortalInetSocketAddressEventListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
@DoPrivileged
public class ClusterExecutorImpl
	implements ClusterExecutor, PortalInetSocketAddressEventListener {

	@Override
	public void addClusterEventListener(
		ClusterEventListener clusterEventListener) {

		if (!isEnabled()) {
			return;
		}

		_clusterEventListeners.addIfAbsent(clusterEventListener);
	}

	@Override
	public void destroy() {
		if (!isEnabled()) {
			return;
		}

		_clusterChannel.close();

		_executorService.shutdownNow();

		_clusterEventListeners.clear();
		_clusterNodeAddresses.clear();
		_futureClusterResponses.clear();
		_liveInstances.clear();
		_localAddress = null;
		_localClusterNode = null;
	}

	@Override
	public FutureClusterResponses execute(ClusterRequest clusterRequest) {
		if (!isEnabled()) {
			return null;
		}

		List<Address> addresses = prepareAddresses(clusterRequest);

		Set<String> clusterNodeIds = new HashSet<>();

		for (Address address : addresses) {
			ClusterNode clusterNode = _liveInstances.get(address);

			if (clusterNode != null) {
				clusterNodeIds.add(clusterNode.getClusterNodeId());
			}
		}

		FutureClusterResponses futureClusterResponses =
			new FutureClusterResponses(clusterNodeIds);

		if (!clusterRequest.isFireAndForget()) {
			String uuid = clusterRequest.getUuid();

			_futureClusterResponses.put(uuid, futureClusterResponses);
		}

		if (addresses.remove(_localAddress)) {
			ClusterNodeResponse clusterNodeResponse = executeClusterRequest(
				clusterRequest);

			if (!clusterRequest.isFireAndForget()) {
				futureClusterResponses.addClusterNodeResponse(
					clusterNodeResponse);
			}
		}

		if (clusterRequest.isMulticast()) {
			_clusterChannel.sendMulticastMessage(clusterRequest);
		}
		else {
			for (Address address : addresses) {
				_clusterChannel.sendUnicastMessage(clusterRequest, address);
			}
		}

		return futureClusterResponses;
	}

	@Override
	public List<ClusterEventListener> getClusterEventListeners() {
		if (!isEnabled()) {
			return Collections.emptyList();
		}

		return Collections.unmodifiableList(_clusterEventListeners);
	}

	@Override
	public List<ClusterNode> getClusterNodes() {
		if (!isEnabled()) {
			return Collections.emptyList();
		}

		return new ArrayList<>(_liveInstances.values());
	}

	@Override
	public ClusterNode getLocalClusterNode() {
		if (!isEnabled()) {
			return null;
		}

		return _localClusterNode;
	}

	@Override
	public void initialize() {
		if (!isEnabled()) {
			return;
		}

		_executorService = PortalExecutorManagerUtil.getPortalExecutor(
			ClusterExecutorImpl.class.getName());

		PortalUtil.addPortalInetSocketAddressEventListener(this);

		if (PropsValues.CLUSTER_LINK_DEBUG_ENABLED) {
			addClusterEventListener(new DebuggingClusterEventListenerImpl());
		}

		if (PropsValues.LIVE_USERS_ENABLED) {
			addClusterEventListener(new LiveUsersClusterEventListenerImpl());
		}

		_clusterReceiver = new ClusterRequestReceiver(this);

		_clusterChannel = _clusterChannelFactory.createClusterChannel(
			PropsValues.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL,
			_LIFERAY_CONTROL_CHANNEL_NAME, _clusterReceiver);

		_localAddress = _clusterChannel.getLocalAddress();

		_localClusterNode = new ClusterNode(
			PortalUUIDUtil.generate(), _clusterChannel.getBindInetAddress());

		if (Validator.isNotNull(PropsValues.PORTAL_INSTANCE_PROTOCOL)) {
			_localClusterNode.setPortalProtocol(
				PropsValues.PORTAL_INSTANCE_PROTOCOL);

			_localClusterNode.setPortalInetSocketAddress(
				getConfiguredPortalInetSocketAddress());
		}

		memberJoined(_localAddress, _localClusterNode);

		sendNotifyRequest();

		_clusterReceiver.openLatch();
	}

	@Override
	public boolean isClusterNodeAlive(String clusterNodeId) {
		if (!isEnabled()) {
			return false;
		}

		return _clusterNodeAddresses.containsKey(clusterNodeId);
	}

	@Override
	public boolean isEnabled() {
		return PropsValues.CLUSTER_LINK_ENABLED;
	}

	@Override
	public void portalLocalInetSocketAddressConfigured(
		InetSocketAddress inetSocketAddress, boolean secure) {

		if (!isEnabled() || (_localClusterNode.getPortalProtocol() != null)) {
			return;
		}

		_localClusterNode.setPortalInetSocketAddress(inetSocketAddress);

		if (secure) {
			_localClusterNode.setPortalProtocol(Http.HTTPS);
		}
		else {
			_localClusterNode.setPortalProtocol(Http.HTTP);
		}

		memberJoined(_localAddress, _localClusterNode);

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			_localClusterNode, true);

		_clusterChannel.sendMulticastMessage(clusterRequest);
	}

	@Override
	public void portalServerInetSocketAddressConfigured(
		InetSocketAddress inetSocketAddress, boolean secure) {
	}

	@Override
	public void removeClusterEventListener(
		ClusterEventListener clusterEventListener) {

		if (!isEnabled()) {
			return;
		}

		_clusterEventListeners.remove(clusterEventListener);
	}

	public void setClusterChannelFactory(
		ClusterChannelFactory clusterChannelFactory) {

		_clusterChannelFactory = clusterChannelFactory;
	}

	public void setClusterEventListeners(
		List<ClusterEventListener> clusterEventListeners) {

		if (!isEnabled()) {
			return;
		}

		_clusterEventListeners.addAllAbsent(clusterEventListeners);
	}

	protected ClusterNodeResponse executeClusterRequest(
		ClusterRequest clusterRequest) {

		Serializable payload = clusterRequest.getPayload();

		if (!(payload instanceof MethodHandler)) {
			return ClusterNodeResponse.createExceptionClusterNodeResponse(
				_localClusterNode, clusterRequest.getUuid(),
				new ClusterException(
					"Payload is not of type " + MethodHandler.class.getName()));
		}

		MethodHandler methodHandler = (MethodHandler)payload;

		ClusterInvokeThreadLocal.setEnabled(false);

		try {
			return ClusterNodeResponse.createResultClusterNodeResponse(
				_localClusterNode, clusterRequest.getUuid(),
				methodHandler.invoke());
		}
		catch (Exception e) {
			return ClusterNodeResponse.createExceptionClusterNodeResponse(
				_localClusterNode, clusterRequest.getUuid(), e);
		}
		finally {
			ClusterInvokeThreadLocal.setEnabled(true);
		}
	}

	protected void fireClusterEvent(ClusterEvent clusterEvent) {
		for (ClusterEventListener listener : _clusterEventListeners) {
			listener.processClusterEvent(clusterEvent);
		}
	}

	protected ClusterChannel getClusterChannel() {
		return _clusterChannel;
	}

	protected InetSocketAddress getConfiguredPortalInetSocketAddress() {
		if (Validator.isNull(PropsValues.PORTAL_INSTANCE_INET_SOCKET_ADDRESS)) {
			throw new IllegalArgumentException(
				"Portal instance host name and port needs to be set in the " +
					"property \"portal.instance.inet.socket.address\"");
		}

		String[] parts = StringUtil.split(
			PropsValues.PORTAL_INSTANCE_INET_SOCKET_ADDRESS, CharPool.COLON);

		if (parts.length != 2) {
			throw new IllegalArgumentException(
				"Unable to parse the portal instance host name and port from " +
					PropsValues.PORTAL_INSTANCE_INET_SOCKET_ADDRESS);
		}

		InetAddress hostInetAddress = null;

		try {
			hostInetAddress = InetAddress.getByName(parts[0]);
		}
		catch (UnknownHostException uhe) {
			throw new IllegalArgumentException(
				"Unable to parse the portal instance host name and port from " +
					PropsValues.PORTAL_INSTANCE_INET_SOCKET_ADDRESS, uhe);
		}

		int port = -1;

		try {
			port = GetterUtil.getIntegerStrict(parts[1]);
		}
		catch (NumberFormatException nfe) {
			throw new IllegalArgumentException(
				"Unable to parse portal InetSocketAddress port from " +
					PropsValues.PORTAL_INSTANCE_INET_SOCKET_ADDRESS, nfe);
		}

		return new InetSocketAddress(hostInetAddress, port);
	}

	protected ExecutorService getExecutorService() {
		return _executorService;
	}

	protected FutureClusterResponses getFutureClusterResponses(String uuid) {
		return _futureClusterResponses.get(uuid);
	}

	protected boolean memberJoined(
		Address joinAddress, ClusterNode clusterNode) {

		_liveInstances.put(joinAddress, clusterNode);

		Address previousAddress = _clusterNodeAddresses.put(
			clusterNode.getClusterNodeId(), joinAddress);

		if (previousAddress != null) {
			return false;
		}

		ClusterEvent clusterEvent = ClusterEvent.join(clusterNode);

		fireClusterEvent(clusterEvent);

		return true;
	}

	protected void memberRemoved(List<Address> departAddresses) {
		List<ClusterNode> departClusterNodes = new ArrayList<>();

		for (Address departAddress : departAddresses) {
			ClusterNode departClusterNode = _liveInstances.remove(
				departAddress);

			if (departClusterNode == null) {
				continue;
			}

			departClusterNodes.add(departClusterNode);

			_clusterNodeAddresses.remove(departClusterNode.getClusterNodeId());
		}

		if (departClusterNodes.isEmpty()) {
			return;
		}

		ClusterEvent clusterEvent = ClusterEvent.depart(departClusterNodes);

		fireClusterEvent(clusterEvent);
	}

	protected List<Address> prepareAddresses(ClusterRequest clusterRequest) {
		List<Address> addresses = null;

		if (clusterRequest.isMulticast()) {
			addresses = new ArrayList<>(_clusterNodeAddresses.values());
		}
		else {
			addresses = new ArrayList<>();

			for (String clusterNodeId :
					clusterRequest.getTargetClusterNodeIds()) {

				addresses.add(_clusterNodeAddresses.get(clusterNodeId));
			}
		}

		if (clusterRequest.isSkipLocal()) {
			addresses.remove(_localAddress);
		}

		return addresses;
	}

	protected void sendNotifyRequest() {
		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			_localClusterNode, true);

		_clusterChannel.sendMulticastMessage(clusterRequest);
	}

	private static final String _LIFERAY_CONTROL_CHANNEL_NAME =
		PropsValues.CLUSTER_LINK_CHANNEL_NAME_PREFIX + "control";

	private ClusterChannel _clusterChannel;
	private ClusterChannelFactory _clusterChannelFactory;
	private final CopyOnWriteArrayList<ClusterEventListener>
		_clusterEventListeners = new CopyOnWriteArrayList<>();
	private final Map<String, Address> _clusterNodeAddresses =
		new ConcurrentHashMap<>();
	private ClusterReceiver _clusterReceiver;
	private ExecutorService _executorService;
	private final Map<String, FutureClusterResponses> _futureClusterResponses =
		new ConcurrentReferenceValueHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);
	private final Map<Address, ClusterNode> _liveInstances =
		new ConcurrentHashMap<>();
	private Address _localAddress;
	private ClusterNode _localClusterNode;

}