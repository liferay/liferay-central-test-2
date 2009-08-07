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

import com.liferay.portal.kernel.cluster.PortalClusterUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * <a href="PortalClusterForwardMessageListener.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * PortalClusterForwardMessageListener is a PortalClusterMessageListener. It
 * listens to the cluster. When it receives a MessageBus message from the
 * cluster, it forwards the message to local MessageBus.
 * @author Shuyang Zhou
 */
public class PortalClusterForwardMessageListener implements
	PortalClusterMessageListener {

	/**
	 * This method is called when PortalClusterLink receive a cluster message
	 * whose payload is a MessageBus message.
	 * This listener forwards the MessageBus message to local MessageBus base on
	 * the MessageBus destination property of the message, if the property is
	 *null (which is actually impossible), then do nothing.
	 *
	 * @param message The received MessageBus message.
	 */
	public void receive(Message message) {
		String destination = message.getDestinationName();

		if (Validator.isNotNull(destination)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Forwarding portal cluster link message:" + message +
					" to " + destination);
			}
			PortalClusterUtil.setForwardMessage(message);
			MessageBusUtil.sendMessage(destination, message);
		}
		else {
			if (_log.isErrorEnabled()) {
				_log.error(
					"Forwarded portal cluster link message has no " +
					"destination:" + message);
			}
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(PortalClusterForwardMessageListener.class);

}