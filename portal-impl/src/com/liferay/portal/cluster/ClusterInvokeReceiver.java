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