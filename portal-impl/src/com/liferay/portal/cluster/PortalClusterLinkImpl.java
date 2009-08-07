/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.cluster;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.cluster.PortalClusterAddress;
import com.liferay.portal.kernel.cluster.PortalClusterLink;
import com.liferay.portal.kernel.cluster.PortalClusterMessagePriority;
import com.liferay.portal.kernel.cluster.messaging.PortalClusterForwardMessageListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.io.IOException;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import org.jgroups.Address;
import org.jgroups.ChannelException;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

/**
 * <a href="PortalClusterLinkImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class PortalClusterLinkImpl implements PortalClusterLink {

	public PortalClusterLinkImpl(
		PortalClusterForwardMessageListener portalClusterForwardMessageListener)
		throws SystemException {

		_portalClusterForwardMessageListener =
			portalClusterForwardMessageListener;

		_setupSystemProperties();

		_autoDetectBindAddr();

		Properties customProperties =
			PropsUtil.getProperties(
				PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES, true);

		Set<Object> keySet=customProperties.keySet();
		List<String> customNames = new ArrayList<String>(keySet.size());
		for(Object key:keySet){
			customNames.add((String) key);
		}

		Collections.sort(customNames);

		_channelNumber = customNames.size();

		if (_channelNumber <= 0 || _channelNumber > MAX_CHANNEL_NUMBER) {
			throw new IllegalArgumentException(
				"Channel number should between [1 to " +
				MAX_CHANNEL_NUMBER + "]");
		}

		_channelList = new ArrayList<JChannel>(_channelNumber);
		_localAddressList = new ArrayList<Address>(_channelNumber);

		try {
			for (int i = 0; i < _channelNumber; i++) {

				JChannel channel = _createChannel(
					i, customProperties.getProperty(customNames.get(i)));
				_channelList.add(i, channel);
				_localAddressList.add(channel.getLocalAddress());
			}
		}
		catch (Exception e) {
			_log.error("Error initializing", e);
		}
	}

	public void sendMulticastMessage(
		Message message, PortalClusterMessagePriority messagePriority) {
		JChannel channel = _getChannel(messagePriority);
		try {
			channel.send(null, null, message);
		}
		catch (ChannelException ex) {
			_log.error("Unable to send multicast message:" + message, ex);
		}
	}

	public void sendUnicastMessage(
		PortalClusterAddress destinationAddress, Message message,
		PortalClusterMessagePriority messagePriority) {
		Address jgroupAddress = (Address) destinationAddress.getRealAddress();

		JChannel channel = _getChannel(messagePriority);
		try {
			channel.send(jgroupAddress, null, message);
		}
		catch (ChannelException ex) {
			_log.error("Unable to send multicast message:" + message, ex);
		}
	}

	public List<PortalClusterAddress> getAddresses() {

		Vector<Address> addresses = null;
		for (JChannel channel : _channelList) {
			if (channel != null) {
				addresses = channel.getView().getMembers();
				break;
			}
		}

		if (addresses == null) {
			return new ArrayList<PortalClusterAddress>();
		}

		List<PortalClusterAddress> portalClusterMemberAddresses =
			new ArrayList<PortalClusterAddress>(addresses.size());

		for (Address address : addresses) {
			portalClusterMemberAddresses.add(
				new PortalClusterAddressImpl(address));
		}

		return portalClusterMemberAddresses;
	}

	public void destory() {
		for (JChannel channel : _channelList) {
			channel.close();
		}
	}

	private void _setupSystemProperties() {
		String[] systemProps =
			PropsUtil.getArray(
			PropsKeys.CLUSTER_LINK_CHANNEL_SYSTEM_PROPERTIES);
		if (systemProps != null) {
			for (String props : systemProps) {
				int index = props.indexOf(":");
				if (index != -1) {
					String key = props.substring(0, index);
					String value = props.substring(index + 1);
					System.setProperty(key, value);
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Setting system property key:" + key +
							", value:" + value);
					}
				}
			}
		}
	}

	private void _autoDetectBindAddr() {

		String testAddress =
			PropsUtil.get(PropsKeys.CLUSTER_LINK_AUTODETECT_ADDRESS);
		if (Validator.isNotNull(testAddress)) {
			String testHost = null;
			int testPort = 80;
			int index = testAddress.indexOf(":");
			if (index != -1) {
				testHost = testAddress.substring(0, index);
				testPort =
					GetterUtil.getInteger(testAddress.substring(index + 1), 80);
			}
			else {
				testHost = testAddress;
			}
			try {
				String bind_addr = _getDefaultOutgoingIP(testHost, testPort);
				System.setProperty("jgroups.bind_addr", bind_addr);
				if (_log.isInfoEnabled()) {
					_log.info(
						"Set autodetected bind_addr system property:" +
						bind_addr);
				}
			}
			catch (IOException ex) {
				_log.warn("Failed to detect default outgoing ip address.", ex);
			}

		}
	}

	private String _getDefaultOutgoingIP(
		String testHost, int testPort) throws IOException {

		Socket socket = null;

		try {
			socket = new Socket(testHost, testPort);
			return socket.getLocalAddress().getHostAddress();
		}
		finally {
			if (socket != null) {
				try {
					socket.close();
				}
				catch (IOException ex) {
					//ignored
				}
			}
		}
	}

	private JChannel _createChannel(int index, String properties)
		throws ChannelException {
		final JChannel channel = new JChannel(properties);
		channel.setReceiver(new ReceiverAdapter() {

			@Override
			public void viewAccepted(View new_view) {
				if (_log.isInfoEnabled()) {
					_log.info("Portal cluster link view updated:" + new_view);
				}
			}

			@Override
			public void receive(org.jgroups.Message message) {
				if (!_localAddressList.contains(message.getSrc())) {
					_portalClusterForwardMessageListener.receive(
						(Message) message.getObject());
				}
				else {
					if (_log.isInfoEnabled()) {
						_log.info("Block receiving local message:" + message);
					}
				}
			}

		});

		channel.connect(LIFERAY_JGROUPS_CLUSTER + index);
		if (_log.isInfoEnabled()) {
			_log.info("Create a new channel with properties:" +
				channel.getProperties());
		}
		return channel;
	}

	private JChannel _getChannel(PortalClusterMessagePriority messagePriority) {

		int priorityIndex = messagePriority.ordinal();
		int channelIndex = priorityIndex * _channelNumber / MAX_CHANNEL_NUMBER;

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Select channel number:" + channelIndex +
				" for priority:" + messagePriority);
		}
		return _channelList.get(channelIndex);
	}

	public static final String LIFERAY_JGROUPS_CLUSTER =
		"LIFERAY_JGROUPS_CLUSTER-";
	public static final int MAX_CHANNEL_NUMBER =
		PortalClusterMessagePriority.values().length;

	private static final Log _log =
		LogFactoryUtil.getLog(PortalClusterLinkImpl.class);

	private final PortalClusterForwardMessageListener
		_portalClusterForwardMessageListener;
	private final int _channelNumber;
	private final List<JChannel> _channelList;
	private final List<Address> _localAddressList;

}