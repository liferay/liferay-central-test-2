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

package com.liferay.portal.kernel.cluster.messaging;

import com.liferay.portal.kernel.cluster.PortalClusterAddress;
import com.liferay.portal.kernel.cluster.PortalClusterMessagePriority;
import com.liferay.portal.kernel.cluster.PortalClusterUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

/**
 * <a href="PortalClusterBridgeMessageListener.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * <p>
 * PortalClusterBridgeMessageListener is a MessageBus MessageListener. It
 * listens to MessageBus destination, when it receives a message, it bridges the
 * message to cluster.
 * </p>
 * @author Shuyang Zhou
 */
public class PortalClusterBridgeMessageListener implements MessageListener {

	/**
	 * Create a new PortalClusterBridgeMessageListener with
	 * PortalClusterMessagePriority. The PortalClusterMessagePriority will be
	 * used when bridging MessageBus message to cluster.
	 *
	 * @param messagePriority PortalClusterMessagePriority for this listener.
	 */
	public PortalClusterBridgeMessageListener(
		PortalClusterMessagePriority messagePriority) {

		_messagePriority = messagePriority;
	}

	/**
	 * <p>
	 * Bridge a MessageBus message to cluster with the
	 * PortalClusterMessagePriority of this listener if it is not a forward
	 * message, otherwise do nothing.
	 * </p>
	 *
	 * <p>
	 * If the MessageBus message has destination PortalClusterAddress property
	 * then do a unicast cluster message sending, otherwise do a multicast
	 * cluster message sending.
	 * </p>
	 * @param message
	 */
	public void receive(Message message) {
		//Don't bridge forward message,
		//otherwise will cause circular message sending
		if (!PortalClusterUtil.isForwardMessage(message)) {
			PortalClusterAddress portalClusterAddress =
				PortalClusterUtil.getPortalClusterAddress(message);

			if (portalClusterAddress == null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Bridging portal cluster link multicast message:" +
						message);
				}
				PortalClusterUtil.sendMulticastMessage(
					message, _messagePriority);
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Bridging portal cluster link unicast message:" +
						message + " to " + portalClusterAddress);
				}
				PortalClusterUtil.sendUnicastMessage(
					portalClusterAddress, message, _messagePriority);
			}
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(PortalClusterBridgeMessageListener.class);
	private final PortalClusterMessagePriority _messagePriority;

}