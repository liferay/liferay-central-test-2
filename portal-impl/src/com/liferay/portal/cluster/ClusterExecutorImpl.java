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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.ObjectValuePair;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
		if (PropsValues.CLUSTER_EXECUTOR_DEBUG_ENABLED) {
			addClusterEventListener(new DebuggingClusterEventListenerImpl());
		}

		super.afterPropertiesSet();
	}

	public void destroy() {
		if (!isEnabled()) {
			return;
		}

		_scheduledExecutorService.shutdownNow();
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
			addresses.remove(getLocalClusterNodeAddress())) {

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
			return Collections.emptyList();
		}

		return Collections.unmodifiableList(_clusterEventListeners);
	}

	public List<Address> getClusterNodeAddresses() {
		if (!isEnabled()) {
			return Collections.emptyList();
		}

		clearExpiredInstances();

		return new ArrayList<Address>(_clusterNodeIdMap.values());
	}

	public List<ClusterNode> getClusterNodes() {
		if (!isEnabled()) {
			return Collections.emptyList();
		}

		clearExpiredInstances();

		Set<ObjectValuePair<Address, ClusterNode>> aliveInstances =
			_checkInInstanceMap.keySet();

		List<ClusterNode> aliveClusterNodes = new ArrayList<ClusterNode>(
			aliveInstances.size());

		for(ObjectValuePair<Address, ClusterNode> aliveInstance :
			aliveInstances) {

			aliveClusterNodes.add(aliveInstance.getValue());
		}

		return aliveClusterNodes;
	}

	public ClusterNode getLocalClusterNode()  {
		if (!isEnabled()) {
			return null;
		}

		return _localClusterNode;
	}

	public Address getLocalClusterNodeAddress() {
		if (!isEnabled()) {
			return null;
		}

		return _localAddress;
	}

	public void initialize() {
		if (!isEnabled()) {
			return;
		}

		PortalUtil.addPortalPortEventListener(this);

		_localAddress = new AddressImpl(_controlChannel.getLocalAddress());

		try {
			initLocalClusterNode();
		}
		catch (SystemException se) {
			_log.error("Unable to determine local network address", se);
		}

		ObjectValuePair<Address, ClusterNode> localInstance =
			new ObjectValuePair<Address, ClusterNode>(
				_localAddress, _localClusterNode);

		_checkInInstanceMap.put(localInstance, Long.MAX_VALUE);
		_clusterNodeIdMap.put(
			_localClusterNode.getClusterNodeId(), _localAddress);

		_scheduledExecutorService = Executors.newScheduledThreadPool(
			1,
			new NamedThreadFactory(
				ClusterExecutorImpl.class.getName(), Thread.NORM_PRIORITY,
				Thread.currentThread().getContextClassLoader()));

		_scheduledExecutorService.scheduleWithFixedDelay(
			new CheckInTask(), 0, _CHECK_IN_INTERVAL, TimeUnit.MILLISECONDS);
	}

	public boolean isClusterNodeAlive(Address address) {
		if (!isEnabled()) {
			return false;
		}

		clearExpiredInstances();

		return _clusterNodeIdMap.containsValue(address);
	}

	public boolean isClusterNodeAlive(String clusterNodeId) {
		if (!isEnabled()) {
			return false;
		}

		clearExpiredInstances();

		return _clusterNodeIdMap.containsKey(clusterNodeId);
	}

	public boolean isEnabled() {
		return PropsValues.CLUSTER_LINK_ENABLED;
	}

	public void portalPortConfigured(int port) {
		if (!isEnabled()) {
			return;
		}

		_localClusterNode.setPort(port);
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

	protected void notify (
		Address address, ClusterNode clusterNode, long expirationTime) {

		clearExpiredInstances();

		if (System.currentTimeMillis() > expirationTime) {
			return;
		}

		ObjectValuePair<Address, ClusterNode> checkInInstance =
			new ObjectValuePair<Address, ClusterNode>(address, clusterNode);

		Long oldExpirationTime = _checkInInstanceMap.put(
			checkInInstance, expirationTime);

		if (oldExpirationTime != null ||
			((_localAddress != null) && _localAddress.equals(address))) {

			return;
		}

		_clusterNodeIdMap.put(clusterNode.getClusterNodeId(), address);

		ClusterEvent clusterEvent = ClusterEvent.join(clusterNode);

		fireClusterEvent(clusterEvent);
	}

	protected void clearExpiredInstances() {
		if (_checkInInstanceMap.isEmpty()) {
			return;
		}

		Iterator<Map.Entry<ObjectValuePair<Address, ClusterNode>, Long>>
			itr = _checkInInstanceMap.entrySet().iterator();

		long now = System.currentTimeMillis();

		while (itr.hasNext()) {
			Map.Entry<ObjectValuePair<Address, ClusterNode>, Long> entry =
				itr.next();

			long expirationTime = entry.getValue().longValue();
			ClusterNode departingClusterNode = entry.getKey().getValue();

			if (now < expirationTime ||
				_localClusterNode.equals(departingClusterNode)) {

				continue;
			}

			_clusterNodeIdMap.remove(departingClusterNode.getClusterNodeId());

			itr.remove();

			ClusterEvent clusterEvent = ClusterEvent.depart(
				departingClusterNode);

			fireClusterEvent(clusterEvent);
		}
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

	protected void initLocalClusterNode() throws SystemException{
		_localClusterNode = new ClusterNode(PortalUUIDUtil.generate());

		_localClusterNode.setPort(PortalUtil.getPortalPort());

		try {
			InetAddress inetAddress = bindInetAddress;

			if (inetAddress == null) {
				inetAddress = InetAddressUtil.getLocalInetAddress();
			}

			_localClusterNode.setInetAddress(inetAddress);

			_localClusterNode.setHostName(inetAddress.getHostName());
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to determine local network address", e);
		}
	}

	protected boolean isShortcutLocalMethod() {
		return _shortcutLocalMethod;
	}

	protected List<Address> prepareAddresses(ClusterRequest clusterRequest) {
		boolean isMulticast = clusterRequest.isMulticast();

		List<Address> addresses = null;

		if (isMulticast) {
			addresses = getAddresses(_controlChannel);
		}
		else {
			addresses = new ArrayList<Address>();

			Collection<Address> clusterNodeAddresses =
				clusterRequest.getTargetClusterNodeAddresses();

			if (clusterNodeAddresses != null) {
				addresses.addAll(clusterNodeAddresses);
			}

			Collection<String> clusterNodeIds =
				clusterRequest.getTargetClusterNodeIds();

			if (clusterNodeIds != null) {
				for (String clusterNodeId : clusterNodeIds) {
					Address address = _clusterNodeIdMap.get(clusterNodeId);

					addresses.add(address);
				}
			}
		}

		return addresses;
	}

	protected ClusterNodeResponse runLocalMethod(MethodHandler methodHandler)
		throws SystemException {

		ClusterNodeResponse clusterNodeResponse = new ClusterNodeResponse();

		ClusterNode localClusterNode = getLocalClusterNode();

		clusterNodeResponse.setAddress(getLocalClusterNodeAddress());
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

	private static final long _CHECK_IN_INTERVAL = GetterUtil.getLong(
			PropsUtil.get(PropsKeys.CLUSTER_EXECUTOR_CHECK_IN_INTERVAL));
	private static final String _DEFAULT_CLUSTER_NAME =
		"LIFERAY-CONTROL-CHANNEL";

	private static Log _log = LogFactoryUtil.getLog(ClusterExecutorImpl.class);

	private Map<ObjectValuePair<Address, ClusterNode>, Long>
		_checkInInstanceMap =
			new ConcurrentHashMap<
				ObjectValuePair<Address, ClusterNode>, Long>();
	private CopyOnWriteArrayList<ClusterEventListener> _clusterEventListeners =
		new CopyOnWriteArrayList<ClusterEventListener>();
	private Map<String, Address> _clusterNodeIdMap =
		new ConcurrentHashMap<String, Address>();
	private JChannel _controlChannel;
	private Map<String, FutureClusterResponses> _executionResultMap =
		new WeakValueConcurrentHashMap<String, FutureClusterResponses>();
	private Address _localAddress;
	private ClusterNode _localClusterNode;
	public ScheduledExecutorService _scheduledExecutorService;
	private boolean _shortcutLocalMethod;

	private class CheckInTask implements Runnable {

		public void run() {
			long expirationTime = System.currentTimeMillis() +
				_CHECK_IN_INTERVAL * 2;

			try {
				ClusterRequest clusterNotifyRequest =
					ClusterRequest.createClusterNotifyRequest(
						expirationTime, _localClusterNode);

				sendMulticastRequest(clusterNotifyRequest);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"unable to send check in request, will reschedule "
							+ "this task",
						e);
				}

				_scheduledExecutorService.scheduleWithFixedDelay(
					new CheckInTask(), 0, _CHECK_IN_INTERVAL,
					TimeUnit.MILLISECONDS);
			}
		}

	}

}