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
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterException;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMessageType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WeakValueConcurrentHashMap;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PortalPortEventListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.net.InetAddress;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jgroups.ChannelException;
import org.jgroups.JChannel;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
public class ClusterExecutorImpl
	extends ClusterBase implements ClusterExecutor, PortalPortEventListener {

	public void addClusterEventListener(
		ClusterEventListener clusterEventListener) {
		if (!isEnabled()) {
			return;
		}

		_clusterEventListeners.addIfAbsent(clusterEventListener);
	}

	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		if (PropsValues.CLUSTER_EXECUTOR_DEBUG_ENABLED) {
			addClusterEventListener(new DebuggingClusterEventListenerImpl());
		}
	}

	public void destroy() {
		if (!isEnabled()) {
			return;
		}

		_controlChannel.close();
	}

	public FutureClusterResponses execute(ClusterRequest clusterRequest)
		throws SystemException {

		if (!isEnabled()) {
			return null;
		}

		List<Address> addresses = prepareAddresses(clusterRequest);

		FutureClusterResponses futureClusterResponses =
			new FutureClusterResponses(addresses);

		if (!clusterRequest.isFireAndForget()) {
			String uuid = clusterRequest.getUuid();

			_executionResultMap.put(uuid, futureClusterResponses);
		}

		if (!clusterRequest.isSkipLocal() && _shortcutLocalMethod &&
			addresses.remove(getLocalControlAddress())) {

			ClusterNodeResponse clusterNodeResponse = runLocalMethod(
				clusterRequest.getMethodHandler());

			clusterNodeResponse.setMulticast(clusterRequest.isMulticast());
			clusterNodeResponse.setUuid(clusterRequest.getUuid());

			futureClusterResponses.addClusterNodeResponse(clusterNodeResponse);
		}

		if (clusterRequest.isMulticast()) {
			sendMulticastRequest(clusterRequest);
		}
		else {
			sendUnicastRequest(clusterRequest, addresses);
		}

		return futureClusterResponses;
	}

	public List<ClusterEventListener> getClusterEventListeners() {
		if (!isEnabled()) {
			return Collections.EMPTY_LIST;
		}

		return Collections.unmodifiableList(_clusterEventListeners);
	}

	public List<ClusterNode> getClusterNodes() {
		if (!isEnabled()) {
			return Collections.EMPTY_LIST;
		}

		return new ArrayList<ClusterNode>(_addressMap.values());
	}

	public ClusterNode getLocalClusterNode() throws SystemException {
		if (!isEnabled()) {
			return null;
		}

		ClusterNode clusterNode = _addressMap.get(getLocalControlAddress());

		if (clusterNode == null) {
			_localClusterNodeId = PortalUUIDUtil.generate();

			clusterNode = new ClusterNode(_localClusterNodeId);

			clusterNode.setPort(PortalUtil.getPortalPort());

			try {
				InetAddress inetAddress = bindInetAddress;

				if (inetAddress == null) {
					inetAddress = InetAddressUtil.getLocalInetAddress();
				}

				clusterNode.setInetAddress(inetAddress);

				clusterNode.setHostName(inetAddress.getHostName());
			}
			catch (Exception e) {
				throw new SystemException(
					"Unable to determine local network address", e);
			}
		}

		return clusterNode;
	}

	public void initialize() {
		if (!isEnabled()) {
			return;
		}

		try {
			PortalUtil.addPortalPortEventListener(this);

			ClusterNode clusterNode = getLocalClusterNode();

			ClusterRequest clusterRequest = ClusterRequest.createClusterRequest(
				ClusterMessageType.NOTIFY, clusterNode);

			_controlChannel.send(null, null, clusterRequest);
		}
		catch (ChannelException ce) {
			_log.error("Unable to send multicast message ", ce);
		}
		catch (SystemException se) {
			_log.error("Unable to determine local network address", se);
		}
	}

	public boolean isClusterNodeAlive(String clusterNodeId) {
		if (!isEnabled()) {
			return false;
		}

		return _clusterNodeIdMap.containsKey(clusterNodeId);
	}

	public boolean isEnabled() {
		return PropsValues.CLUSTER_LINK_ENABLED;
	}

	public void portalPortConfigured(int port) {
		if (!isEnabled()) {
			return;
		}

		try {
			ClusterNode clusterNode = getLocalClusterNode();

			clusterNode.setPort(port);

			ClusterRequest clusterRequest = ClusterRequest.createClusterRequest(
				ClusterMessageType.UPDATE, clusterNode);

			_controlChannel.send(null, null, clusterRequest);
		}
		catch (Exception e) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to determine configure node port", e);
			}
		}
	}

	public void removeClusterEventListener(
		ClusterEventListener clusterEventListener) {

		if (!isEnabled()) {
			return;
		}

		_clusterEventListeners.remove(clusterEventListener);
	}

	public void setClusterEventListeners(
		List<ClusterEventListener> clusterEventListeners) {

		if (!isEnabled()) {
			return;
		}

		_clusterEventListeners.addAllAbsent(clusterEventListeners);
	}

	public void setShortcutLocalMethod(boolean shortcutLocalMethod) {
		if (!isEnabled()) {
			return;
		}

		_shortcutLocalMethod = shortcutLocalMethod;
	}

	protected void fireClusterEvent(ClusterEvent clusterEvent) {
		for (ClusterEventListener listener : _clusterEventListeners) {
			listener.processClusterEvent(clusterEvent);
		}
	}

	protected JChannel getControlChannel() {
		return _controlChannel;
	}

	protected FutureClusterResponses getExecutionResults(String uuid) {
		return _executionResultMap.get(uuid);
	}

	protected Address getLocalControlAddress() {
		return new AddressImpl(_controlChannel.getLocalAddress());
	}

	protected void initChannels() {
		Properties controlProperties = PropsUtil.getProperties(
			PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL, false);

		String controlProperty = controlProperties.getProperty(
			PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL);

		ClusterRequestReceiver clusterInvokeReceiver =
			new ClusterRequestReceiver(this);

		try {
			_controlChannel = createJChannel(
				controlProperty, clusterInvokeReceiver, _DEFAULT_CLUSTER_NAME);
		}
		catch (ChannelException ce) {
			_log.error(ce, ce);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected boolean isShortcutLocalMethod() {
		return _shortcutLocalMethod;
	}

	protected void memberJoined(Address joinAddress, ClusterNode clusterNode) {
		_addressMap.put(joinAddress, clusterNode);

		Address previousAddress = _clusterNodeIdMap.put(
			clusterNode.getClusterNodeId(), joinAddress);

		if ((previousAddress == null) &&
			!getLocalControlAddress().equals(joinAddress)) {

			ClusterEvent clusterEvent = ClusterEvent.join(clusterNode);

			fireClusterEvent(clusterEvent);
		}
	}

	protected void memberRemoved(List<Address> departAddresses) {
		List<ClusterNode> departingClusterNodes = new ArrayList<ClusterNode>();

		for (Address departAddress : departAddresses) {
			ClusterNode departingClusterNode = _addressMap.remove(
				departAddress);
			if (departingClusterNode != null) {
				departingClusterNodes.add(departingClusterNode);

				_clusterNodeIdMap.remove(
					departingClusterNode.getClusterNodeId());
			}
		}

		if (departingClusterNodes.isEmpty()) {
			return;
		}

		ClusterEvent clusterEvent = ClusterEvent.depart(departingClusterNodes);

		fireClusterEvent(clusterEvent);
	}

	protected List<Address> prepareAddresses(ClusterRequest clusterRequest) {
		boolean isMulticast = clusterRequest.isMulticast();

		List<Address> addresses = null;

		if (isMulticast) {
			addresses = getAddresses(_controlChannel);
		}
		else {
			Collection<String> clusterNodeIds =
				clusterRequest.getTargetClusterNodeIds();

			addresses = new ArrayList<Address>(clusterNodeIds.size());

			for (String clusterNodeId : clusterNodeIds) {
				Address address = _clusterNodeIdMap.get(clusterNodeId);

				addresses.add(address);
			}
		}

		return addresses;
	}

	protected ClusterNodeResponse runLocalMethod(MethodHandler methodHandler)
		throws SystemException {

		ClusterNodeResponse clusterNodeResponse = new ClusterNodeResponse();

		ClusterNode localClusterNode = getLocalClusterNode();

		clusterNodeResponse.setClusterNode(localClusterNode);
		clusterNodeResponse.setClusterMessageType(ClusterMessageType.EXECUTE);

		if (methodHandler == null) {
			clusterNodeResponse.setException(
				new ClusterException(
					"Payload is not of type " + MethodHandler.class.getName()));

			return clusterNodeResponse;
		}

		try {
			Object returnValue = methodHandler.invoke(true);

			if (returnValue instanceof Serializable) {
				clusterNodeResponse.setResult(returnValue);
			}
			else if (returnValue != null) {
				clusterNodeResponse.setException(
					new ClusterException("Return value is not serializable"));
			}
		}
		catch (Exception e) {
			clusterNodeResponse.setException(e);
		}

		return clusterNodeResponse;
	}

	protected void sendMulticastRequest(ClusterRequest clusterRequest)
		throws SystemException {

		try {
			_controlChannel.send(null, null, clusterRequest);
		}
		catch (ChannelException ce) {
			_log.error(
				"Unable to send multicast message " + clusterRequest, ce);

			throw new SystemException(
				"Unable to send multicast request", ce);
		}
	}

	protected void sendUnicastRequest(
			ClusterRequest clusterRequest, List<Address> addresses)
		throws SystemException {

		for (Address address : addresses) {
			org.jgroups.Address jGroupsAddress =
				(org.jgroups.Address)address.getRealAddress();

			try {
				_controlChannel.send(jGroupsAddress, null, clusterRequest);
			}
			catch (ChannelException ce) {
				_log.error(
					"Unable to send unicast message " + clusterRequest, ce);

				throw new SystemException(
					"Unable to send unicast request", ce);
			}
		}
	}

	private static final String _DEFAULT_CLUSTER_NAME =
		"LIFERAY-CONTROL-CHANNEL";

	private static Log _log = LogFactoryUtil.getLog(ClusterExecutorImpl.class);

	private Map<Address, ClusterNode> _addressMap =
		new ConcurrentHashMap<Address, ClusterNode>();
	private CopyOnWriteArrayList<ClusterEventListener> _clusterEventListeners =
		new CopyOnWriteArrayList<ClusterEventListener>();
	private Map<String, Address> _clusterNodeIdMap =
		new ConcurrentHashMap<String, Address>();
	private JChannel _controlChannel;
	private Map<String, FutureClusterResponses> _executionResultMap =
		new WeakValueConcurrentHashMap<String, FutureClusterResponses>();
	private String _localClusterNodeId;
	private boolean _shortcutLocalMethod;

}