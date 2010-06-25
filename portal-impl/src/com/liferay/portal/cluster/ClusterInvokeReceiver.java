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
import com.liferay.portal.kernel.cluster.ClusterException;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.ClusterResponse;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.Future;

import org.jgroups.ChannelException;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

/**
 * <a href="ClusterInvokeReceiver.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 * @author Tina Tian
 */
public class ClusterInvokeReceiver extends ReceiverAdapter {

	public ClusterInvokeReceiver(
		Map<String, Map<Address, Future<?>>> multicastResultMap,
		Map<String, Future<?>> unicastResultMap) {

		_multicastResultMap = multicastResultMap;
		_unicastResultMap = unicastResultMap;
	}

	public void receive(Message message) {
		org.jgroups.Address sourceAddress = message.getSrc();
		org.jgroups.Address localAddress = _channel.getLocalAddress();

		Object obj = message.getObject();

		if (obj == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Message content is null");
			}

			return;
		}

		if (localAddress.equals(sourceAddress) &&
			ClusterExecutorUtil.isShortcutLocalMethod()) {

			return;
		}

		if (obj instanceof ClusterRequest) {
			ClusterRequest clusterRequest = (ClusterRequest)obj;

			ClusterResponse clusterResponse = new ClusterResponseImpl();

			clusterResponse.setMulticast(clusterRequest.isMulticast());
			clusterResponse.setUuid(clusterRequest.getUuid());

			Object payload = clusterRequest.getPayload();

			if (payload instanceof MethodWrapper) {
				MethodWrapper methodWrapper = (MethodWrapper)payload;

				try {
					Object returnValue = MethodInvoker.invoke(methodWrapper);

					if (returnValue instanceof Serializable) {
						clusterResponse.setResult(returnValue);
					}
					else if (returnValue != null) {
						clusterResponse.setException(
							new ClusterException(
								"Return value is not serializable"));
					}
				}
				catch (Exception e) {
					clusterResponse.setException(e);
				}
			}
			else {
				clusterResponse.setException(
					new ClusterException(
						"Payload is not of type " +
							MethodWrapper.class.getName()));
			}

			try {
				_channel.send(sourceAddress, localAddress, clusterResponse);
			}
			catch (ChannelException ce) {
				_log.error(
					"Unable to send response message " + clusterResponse, ce);
			}
		}
		else if (obj instanceof ClusterResponse) {
			ClusterResponse clusterResponse = (ClusterResponse)obj;

			String uuid = clusterResponse.getUuid();

			if (clusterResponse.isMulticast() &&
				_multicastResultMap.containsKey(uuid)) {

				Map<Address, Future<?>> results = _multicastResultMap.get(uuid);

				Address address = new AddressImpl(sourceAddress);

				if (results.containsKey(address)) {
					FutureResult<Object> futureResult =
						(FutureResult<Object>)results.get(address);

					if (clusterResponse.hasException()) {
						futureResult.setException(
							clusterResponse.getException());
					}
					else {
						futureResult.setResult(clusterResponse.getResult());
					}
				}
				else {
					_log.error("New node coming from " + sourceAddress);
				}
			}
			else if (_unicastResultMap.containsKey(uuid)) {
				FutureResult<Object> futureResult =
					(FutureResult<Object>)_unicastResultMap.get(uuid);

				if (clusterResponse.hasException()) {
					futureResult.setException(clusterResponse.getException());
				}
				else {
					futureResult.setResult(clusterResponse.getResult());
				}
			}
			else {
				_log.error("Unknow UUID " + uuid + " from " + sourceAddress);
			}
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to process message content of type " +
						obj.getClass().getName());
			}

			return;
		}
	}

	public void setChannel(JChannel channel) {
		_channel = channel;
	}

	public void viewAccepted(View view) {
		if (_log.isInfoEnabled()) {
			_log.info("Accepted view " + view);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ClusterInvokeReceiver.class);

	private JChannel _channel;
	private Map<String, Map<Address, Future<?>>> _multicastResultMap;
	private Map<String, Future<?>> _unicastResultMap;

}