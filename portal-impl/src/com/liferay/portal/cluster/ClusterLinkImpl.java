/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.cluster.messaging.ClusterForwardMessageListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.IPDetector;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SocketUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.net.InetAddress;
import java.net.NetworkInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.jgroups.ChannelException;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

/**
 * <a href="ClusterLinkImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ClusterLinkImpl implements ClusterLink {

	public void afterPropertiesSet() {
		if (!PropsValues.CLUSTER_LINK_ENABLED) {
			return;
		}

		if (OSDetector.isUnix() && IPDetector.isSupportsV6() &&
			!IPDetector.isPrefersV4() && _log.isWarnEnabled()) {

			StringBundler sb = new StringBundler(4);

			sb.append("You are on an Unix server with IPv6 enabled. JGroups ");
			sb.append("may not work with IPv6. If you see a multicast ");
			sb.append("error, try adding java.net.preferIPv4Stack=true ");
			sb.append("as a JVM startup parameter.");

			_log.warn(sb.toString());
		}

		initSystemProperties();

		try {
			initBindAddress();
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Failed to initialize outgoing IP address", ioe);
			}
		}

		try {
			initChannels();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void destroy() {
		if (!PropsValues.CLUSTER_LINK_ENABLED) {
			return;
		}

		for (JChannel channel : _channels) {
			channel.close();
		}
	}

	public List<Address> getAddresses(Priority priority) {
		if (!PropsValues.CLUSTER_LINK_ENABLED) {
			return Collections.EMPTY_LIST;
		}

		JChannel channel = getChannel(priority);

		View view = channel.getView();

		Vector<org.jgroups.Address> jGroupsAddresses = view.getMembers();

		if (jGroupsAddresses == null) {
			return new ArrayList<Address>();
		}

		List<Address> addresses = new ArrayList<Address>(
			jGroupsAddresses.size());

		for (org.jgroups.Address address : jGroupsAddresses) {
			addresses.add(new AddressImpl(address));
		}

		return addresses;
	}

	public boolean isEnabled() {
		return PropsValues.CLUSTER_LINK_ENABLED;
	}

	public void sendMulticastMessage(Message message, Priority priority) {
		if (!PropsValues.CLUSTER_LINK_ENABLED) {
			return;
		}

		JChannel channel = getChannel(priority);

		try {
			channel.send(null, null, message);
		}
		catch (ChannelException ce) {
			_log.error("Unable to send multicast message " + message, ce);
		}
	}

	public void sendUnicastMessage(
		Address address, Message message, Priority priority) {

		if (!PropsValues.CLUSTER_LINK_ENABLED) {
			return;
		}

		org.jgroups.Address jGroupsAddress =
			(org.jgroups.Address)address.getRealAddress();

		JChannel channel = getChannel(priority);

		try {
			channel.send(jGroupsAddress, null, message);
		}
		catch (ChannelException ce) {
			_log.error("Unable to send unicast message:" + message, ce);
		}
	}

	public void setClusterForwardMessageListener(
		ClusterForwardMessageListener clusterForwardMessageListener) {

		_clusterForwardMessageListener = clusterForwardMessageListener;
	}

	protected JChannel createChannel(int index, String properties)
		throws ChannelException {

		JChannel channel = new JChannel(properties);

		channel.setReceiver(
			new ReceiverAdapter() {

				public void receive(org.jgroups.Message message) {
					if ((!_addresses.contains(message.getSrc())) ||
						(message.getDest() != null)) {

						_clusterForwardMessageListener.receive(
							(Message)message.getObject());
					}
					else {
						if (_log.isDebugEnabled()) {
							_log.debug("Block received message " + message);
						}
					}
				}

				public void viewAccepted(View view) {
					if (_log.isDebugEnabled()) {
						_log.debug("Cluster link accepted view " + view);
					}
				}

			}
		);

		channel.connect(_LIFERAY_CHANNEL + index);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Create a new channel with properties " +
					channel.getProperties());
		}

		return channel;
	}

	protected JChannel getChannel(Priority priority) {
		int channelIndex =
			priority.ordinal() * _channelCount / _MAX_CHANNEL_COUNT;

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Select channel number " + channelIndex + " for priority " +
					priority);
		}

		return _channels.get(channelIndex);
	}

	protected void initBindAddress() throws IOException {
		String autodetectAddress = PropsValues.CLUSTER_LINK_AUTODETECT_ADDRESS;

		if (Validator.isNull(autodetectAddress)) {
			return;
		}

		String host = autodetectAddress;
		int port = 80;

		int index = autodetectAddress.indexOf(StringPool.COLON);

		if (index != -1) {
			host = autodetectAddress.substring(0, index);
			port = GetterUtil.getInteger(
				autodetectAddress.substring(index + 1), port);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Autodetecting JGroups outgoing IP address and interface for " +
					host + ":" + port);
		}

		SocketUtil.BindInfo bindInfo = SocketUtil.getBindInfo(host, port);

		InetAddress inetAddress = bindInfo.getInetAddress();
		NetworkInterface networkInterface = bindInfo.getNetworkInterface();

		System.setProperty("jgroups.bind_addr", inetAddress.getHostAddress());
		System.setProperty(
			"jgroups.bind_interface", networkInterface.getName());

		if (_log.isInfoEnabled()) {
			_log.info(
				"Setting JGroups outgoing IP address to " +
					inetAddress.getHostAddress() + " and interface to " +
						networkInterface.getName());
		}
	}

	protected void initChannels() throws ChannelException {
		Properties properties = PropsUtil.getProperties(
			PropsKeys.CLUSTER_LINK_CHANNEL_PROPERTIES, true);

		_channelCount = properties.size();

		if ((_channelCount <= 0) || (_channelCount > _MAX_CHANNEL_COUNT)) {
			throw new IllegalArgumentException(
				"Channel count must be between 1 and " + _MAX_CHANNEL_COUNT);
		}

		_addresses = new ArrayList<org.jgroups.Address>(_channelCount);
		_channels = new ArrayList<JChannel>(_channelCount);

		List<String> keys = new ArrayList<String>(_channelCount);

		for (Object key : properties.keySet()) {
			keys.add((String)key);
		}

		Collections.sort(keys);

		for (int i = 0; i < keys.size(); i++) {
			String customName = keys.get(i);

			String value = properties.getProperty(customName);

			JChannel channel = createChannel(i, value);

			_addresses.add(channel.getLocalAddress());
			_channels.add(channel);
		}
	}

	protected void initSystemProperties() {
		for (String systemProperty :
				PropsValues.CLUSTER_LINK_CHANNEL_SYSTEM_PROPERTIES) {

			int index = systemProperty.indexOf(StringPool.COLON);

			if (index == -1) {
				continue;
			}

			String key = systemProperty.substring(0, index);
			String value = systemProperty.substring(index + 1);

			System.setProperty(key, value);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Setting system property {key=" + key + ", value=" +
						value + "}");
			}
		}
	}

	private static final String _LIFERAY_CHANNEL = "LIFERAY-CHANNEL-";

	private static final int _MAX_CHANNEL_COUNT = Priority.values().length;

	private static final Log _log =
		LogFactoryUtil.getLog(ClusterLinkImpl.class);

	private List<org.jgroups.Address> _addresses;
	private int _channelCount;
	private List<JChannel> _channels;
	private ClusterForwardMessageListener _clusterForwardMessageListener;

}