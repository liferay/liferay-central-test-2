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
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SocketUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PortalPortEventListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.jgroups.ChannelException;
import org.jgroups.JChannel;

/**
 * <a href="ClusterExecutorImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Tina Tian
 */
public class ClusterExecutorImpl
	extends ClusterBase implements ClusterExecutor, PortalPortEventListener {

	public ClusterExecutorImpl() {
		_readWriteLock = new ReentrantReadWriteLock();
		_readLock = _readWriteLock.readLock();
		_writeLock = _readWriteLock.writeLock();
	}

	public void addClusterEventListener(
		ClusterEventListener clusterEventListener) {

		if (!isEnabled()) {
			return;
		}

		if (!_clusterEventListeners.contains(clusterEventListener)) {
			_clusterEventListeners.add(clusterEventListener);
		}

		return;
	}

	public void destroy() {
		if (!isEnabled()) {
			return;
		}

		_controlChannel.close();
	}

	public ClusterNodeResponses execute(ClusterRequest clusterRequest)
		throws SystemException {

		if (!isEnabled()) {
			return ClusterNodeResponses.EMPTY_CLUSTER_NODE_RESPONSES;
		}

		ClusterNodeResponses clusterNodeResponses = null;
		FutureClusterResponses clusterResults = null;

		try {
			clusterResults = doExecuteClusterRequest(clusterRequest);

			long timeout = clusterRequest.getTimeOut();

			if (timeout <= 0) {
				timeout = _defaultTimeOut;
			}

			clusterNodeResponses = clusterResults.get(
				timeout, clusterRequest.getTimeUnit());
		}
		catch (Exception e) {
			if (clusterResults != null) {
				clusterNodeResponses = clusterResults.getPartialResults();
			}
			else {
				throw new SystemException(
					"Unable to execute clustered request " + clusterRequest,
					e);
			}
		}
		finally {
			_executionResultMap.remove(clusterRequest.getUuid());
		}

		return clusterNodeResponses;
	}

	public List<ClusterEventListener> getClusterEventListeners() {
		return Collections.unmodifiableList(_clusterEventListeners);
	}

	public List<ClusterNode> getClusterNodes() {
		if (!isEnabled()) {
			return Collections.EMPTY_LIST;
		}

		_readLock.lock();

		try {
			Collection<ClusterNode> clusterNodes = _addressMap.values();

			return new ArrayList<ClusterNode>(clusterNodes);
		}
		finally {
			_readLock.unlock();
		}
	}

	public ClusterNode getLocalClusterNode() throws SystemException {
		if (!isEnabled()) {
			return null;
		}

		ClusterNode clusterNode = getClusterNode(getLocalControlAddress());

		if (clusterNode == null) {
			_localClusterNodeId = PortalUUIDUtil.generate();

			clusterNode = new ClusterNode(_localClusterNodeId);

			String autodetectAddress =
				PropsValues.CLUSTER_LINK_AUTODETECT_ADDRESS;

			clusterNode.setPort(PortalUtil.getPortalPort());

			try {
				InetAddress inetAddress = getHostInetAddress(autodetectAddress);

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

		return (getAddress(clusterNodeId) != null);
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

		_clusterEventListeners.remove(clusterEventListener);
	}

	public void setClusterEventListener(
		List<ClusterEventListener> clusterEventListeners) {

		for (ClusterEventListener clusterEventListener :
				clusterEventListeners) {

			addClusterEventListener(clusterEventListener);
		}
	}

	public void setDefaultTimeOut(long defaultTimeOut) {
		_defaultTimeOut = defaultTimeOut;
	}

	public void setShortcutLocalMethod(boolean shortcutLocalMethod) {
		_shortcutLocalMethod = shortcutLocalMethod;
	}

	protected FutureClusterResponses doExecuteClusterRequest(
			ClusterRequest clusterRequest)
		throws SystemException {

		boolean isMulticast = clusterRequest.isMulticast();

		List<Address> addresses = null;

		if (isMulticast) {
			addresses = getControlAddresses();
		}
		else {
			Collection<String> clusterNodeIds =
				clusterRequest.getTargetClusterNodeIds();

			addresses = new ArrayList<Address>(clusterNodeIds.size());

			for (String clusterNodeId : clusterNodeIds) {
				Address address = getAddress(clusterNodeId);

				addresses.add(address);
			}
		}

		Address localControlAddress = getLocalControlAddress();

		FutureClusterResponses futureClusterResponses =
			new FutureClusterResponses(addresses.size());

		_executionResultMap.put(
			clusterRequest.getUuid(), futureClusterResponses);

		for (Address address : addresses) {
			futureClusterResponses.addExpectedReplyAddress(address);

			if (_shortcutLocalMethod && address.equals(localControlAddress)) {
				ClusterNodeResponse clusterNodeResponse = runLocalMethod(
					clusterRequest.getMethodWrapper());

				futureClusterResponses.addClusterNodeResponse(
					clusterNodeResponse);
			}

			if (!isMulticast) {
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

		if (isMulticast) {
			try {
				_controlChannel.send(null, null, clusterRequest);
			}
			catch (ChannelException ce) {
				_log.error(
					"Unable to send unicast message " + clusterRequest, ce);

				throw new SystemException("Unable to send cluster request", ce);
			}
		}

		return futureClusterResponses;
	}

	protected void fireClusterEvent(ClusterEvent clusterEvent) {
		for (ClusterEventListener listener : _clusterEventListeners) {
			listener.processClusterEvent(clusterEvent);
		}
	}

	protected Address getAddress(String clusterNodeId) {
		_readLock.lock();

		Address address = null;

		try {
			if (_clusterNodeIdMap.containsKey(clusterNodeId)) {
				address = _clusterNodeIdMap.get(clusterNodeId);
			}
		}
		finally {
			_readLock.unlock();
		}

		return address;
	}

	protected ClusterNode getClusterNode(Address address) {
		_readLock.lock();

		ClusterNode clusterNode = null;

		try {
			if (_addressMap.containsKey(address)) {
				clusterNode = _addressMap.get(address);
			}
		}
		finally {
			_readLock.unlock();
		}

		return clusterNode;
	}

	protected List<Address> getControlAddresses() {
		if (!isEnabled()) {
			return Collections.EMPTY_LIST;
		}

		return getAddresses(_controlChannel);
	}

	protected JChannel getControlChannel() {
		return _controlChannel;
	}

	protected FutureClusterResponses getExecutionResults(String uuid) {
		return _executionResultMap.get(uuid);
	}

	protected InetAddress getHostInetAddress(String autoDetectAddress)
		throws Exception {

		InetAddress inetAddress = null;

		if (Validator.isNull(autoDetectAddress)) {
			inetAddress = getLocalInetAddress();
		}
		else {
			String host = autoDetectAddress;

			int port = 80;

			int index = autoDetectAddress.indexOf(StringPool.COLON);

			if (index != -1) {
				host = autoDetectAddress.substring(0, index);

				port = GetterUtil.getInteger(
					autoDetectAddress.substring(index + 1), port);
			}

			SocketUtil.BindInfo bindInfo = SocketUtil.getBindInfo(host, port);

			inetAddress = bindInfo.getInetAddress();
		}

		return inetAddress;
	}

	protected Address getLocalControlAddress() {
		return new AddressImpl(_controlChannel.getLocalAddress());
	}

	protected InetAddress getLocalInetAddress()
		throws SocketException, SystemException {

		List<NetworkInterface> networkInterfaces =
			new ArrayList<NetworkInterface>();

		Enumeration<NetworkInterface> enu1 =
			NetworkInterface.getNetworkInterfaces();

		while (enu1.hasMoreElements()) {
			networkInterfaces.add(enu1.nextElement());
		}

		if (networkInterfaces.isEmpty()) {
			throw new SystemException("No interface available");
		}

		for (NetworkInterface networkInterface : networkInterfaces) {
			if (isLoopback(networkInterface)) {
				continue;
			}

			Enumeration<InetAddress> enu2 = networkInterface.getInetAddresses();

			List<InetAddress> inetAddresses = new ArrayList<InetAddress>();

			while (enu2.hasMoreElements()) {
				inetAddresses.add(enu2.nextElement());
			}

			if (inetAddresses.isEmpty()) {
				continue;
			}

			for (InetAddress inetAddress : inetAddresses) {
				if (inetAddress instanceof Inet4Address) {
					return inetAddress;
				}
			}
		}

		throw new SystemException("No interface available");
	}

	protected void initChannels() {
		String controlProperty =
			PropsUtil.get(PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES_CONTROL);

		ClusterRequestReceiver clusterInvokeReceiver =
			new ClusterRequestReceiver(this);

		try {
			_controlChannel = createChannel(
				controlProperty, clusterInvokeReceiver, _DEFAULT_CLUSTER_NAME);
		}
		catch (ChannelException ce) {
			_log.error(ce, ce);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected boolean isLoopback(NetworkInterface networkInterface) {
		Enumeration<InetAddress> enu = networkInterface.getInetAddresses();

		while (enu.hasMoreElements()) {
			InetAddress inetAddress = enu.nextElement();

			if (inetAddress.isLoopbackAddress()) {
				return true;
			}
		}

		return false;
	}

	protected boolean isShortcutLocalMethod() {
		return _shortcutLocalMethod;
	}

	protected void memberJoined(Address joinAddress, ClusterNode clusterNode) {
		_writeLock.lock();

		try {
			boolean hasClusterNode = _clusterNodeIdMap.containsValue(
				joinAddress);

			boolean hasAddress = _addressMap.containsKey(joinAddress);

			if (hasClusterNode && hasAddress) {
				_addressMap.remove(joinAddress);

				_addressMap.put(joinAddress, clusterNode);
			}
			else if (!hasClusterNode && !hasAddress) {
				_addressMap.put(joinAddress, clusterNode);

				String clusterNodeId = clusterNode.getClusterNodeId();

				_clusterNodeIdMap.put(clusterNodeId, joinAddress);

				if (getLocalControlAddress().equals(joinAddress)) {
					return;
				}

				ClusterEvent clusterEvent = ClusterEvent.join(clusterNode);

				fireClusterEvent(clusterEvent);
			}
			else {
				_log.error(
					"Unable to join address " + joinAddress +
						" to cluster node " + clusterNode);
			}
		}
		finally {
			_writeLock.unlock();
		}
	}

	protected void memberRemoved(List<Address> departAddresses) {
		List<ClusterNode> departingClusterNodes = new ArrayList<ClusterNode>();

		for (Address departAddress : departAddresses) {
			ClusterNode clusterNode = getClusterNode(departAddress);

			if (clusterNode == null) {
				continue;
			}

			departingClusterNodes.add(clusterNode);

			_writeLock.lock();

			try {
				if (_clusterNodeIdMap.containsValue(departAddress)) {
					String clusterNodeId = clusterNode.getClusterNodeId();

					_clusterNodeIdMap.remove(clusterNodeId);

					_addressMap.remove(departAddress);
				}
				else {
					_log.error(
						"Unable to remove address " + departAddress +
							" from cluster node " + clusterNode);

					continue;
				}
			}
			finally {
				_writeLock.unlock();
			}
		}

		if (departingClusterNodes.isEmpty()) {
			return;
		}

		ClusterEvent clusterEvent = ClusterEvent.depart(departingClusterNodes);

		fireClusterEvent(clusterEvent);
	}

	protected ClusterNodeResponse runLocalMethod(MethodWrapper methodWrapper)
		throws SystemException {

		ClusterNodeResponse clusterNodeResponse = new ClusterNodeResponse();

		ClusterNode localClusterNode = getLocalClusterNode();

		clusterNodeResponse.setClusterNode(localClusterNode);

		try {
			Object returnValue = MethodInvoker.invoke(methodWrapper);

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

	private static final String _DEFAULT_CLUSTER_NAME =
		"LIFERAY-CONTROL-CHANNEL";

	private static Log _log = LogFactoryUtil.getLog(ClusterExecutorImpl.class);

	private Map<Address, ClusterNode> _addressMap =
		new HashMap<Address, ClusterNode>();
	private List<ClusterEventListener> _clusterEventListeners =
		new CopyOnWriteArrayList<ClusterEventListener>();
	private Map<String, Address> _clusterNodeIdMap =
		new HashMap<String, Address>();
	private JChannel _controlChannel;
	private long _defaultTimeOut = 100;
	private Map<String, FutureClusterResponses> _executionResultMap =
		new ConcurrentHashMap<String, FutureClusterResponses>();
	private String _localClusterNodeId;
	private final Lock _readLock;
	private final ReentrantReadWriteLock _readWriteLock;
	private boolean _shortcutLocalMethod;
	private final Lock _writeLock;

}