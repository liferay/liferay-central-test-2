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

import com.liferay.portal.kernel.cluster.ClusterException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;

import java.io.Serializable;

import org.jgroups.Address;
import org.jgroups.ChannelException;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

/**
 * <a href="ClusterInvokeReceiver.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ClusterInvokeReceiver extends ReceiverAdapter {

	public ClusterInvokeReceiver(JChannel channel) {
		_channel = channel;
	}

	public void receive(Message message) {
		Address sourceAddress = message.getSrc();
		Address localAddress = _channel.getLocalAddress();

		if ((!localAddress.equals(sourceAddress)) ||
			(message.getDest() != null)) {

			Message responseMessage = new Message();

			responseMessage.setDest(sourceAddress);
			responseMessage.setSrc(localAddress);

			Object payload = message.getObject();

			if (payload instanceof MethodWrapper) {
				MethodWrapper methodWrapper = (MethodWrapper)payload;

				try {
					Object returnValue = MethodInvoker.invoke(methodWrapper);

					if (returnValue instanceof Serializable) {
						responseMessage.setObject((Serializable)returnValue);
					}
					else {
						responseMessage.setObject(
							new ClusterException(
								"Return value is not Serializable"));
					}
				}
				catch (Exception e) {
					responseMessage.setObject(e);
				}
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn("Payload is not a MethodWrapper");
				}
			}

			try {
				_channel.send(responseMessage);
			}
			catch (ChannelException ce) {
				_log.error(
					"Unable to send response message " + responseMessage, ce);
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Block received message " + message);
			}
		}
	}

	public void viewAccepted(View view) {
		if (_log.isDebugEnabled()) {
			_log.debug("Accepted view " + view);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ClusterInvokeReceiver.class);

	private JChannel _channel;

}