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

package com.liferay.portal.kernel.cluster.messaging;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterLinkUtil;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

/**
 * <a href="ClusterBridgeMessageListener.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 */
public class ClusterBridgeMessageListener implements MessageListener {

	public void receive(Message message) {

		// Prevent circular message sending

		if (ClusterLinkUtil.isForwardMessage(message)) {
			return;
		}

		Address address = ClusterLinkUtil.getAddress(message);

		if (address == null) {
			if (_log.isInfoEnabled()) {
				_log.info("Bridging cluster link multicast message " + message);
			}

			ClusterLinkUtil.sendMulticastMessage(message, _priority);
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Bridging cluster link unicast message " + message +
						" to " + address);
			}

			ClusterLinkUtil.sendUnicastMessage(address, message, _priority);
		}
	}

	public void setPriority(Priority priority) {
		_priority = priority;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ClusterBridgeMessageListener.class);

	private Priority _priority;

}