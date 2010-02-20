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

import com.liferay.portal.kernel.cluster.messaging.ClusterForwardMessageListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;

import java.util.List;

import org.jgroups.Address;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

/**
 * <a href="ClusterForwardReceiver.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ClusterForwardReceiver extends ReceiverAdapter {

	public ClusterForwardReceiver(
		List<Address> localTransportAddresses,
		ClusterForwardMessageListener clusterForwardMessageListener) {

		_localTransportAddresses = localTransportAddresses;
		_clusterForwardMessageListener = clusterForwardMessageListener;
	}

	public void receive(org.jgroups.Message message) {
		if ((!_localTransportAddresses.contains(message.getSrc()))
			|| (message.getDest() != null)) {

			_clusterForwardMessageListener.receive(
				(Message) message.getObject());
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Block received message " + message);
			}
		}
	}

	public void viewAccepted(View view) {
		if (_log.isDebugEnabled()) {
			_log.debug("ClusterForwardReceiver accepted view " + view);
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(ClusterForwardReceiver.class);

	private List<org.jgroups.Address> _localTransportAddresses;
	private ClusterForwardMessageListener _clusterForwardMessageListener;

}