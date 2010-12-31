/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.poller;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class PollerRequestManager implements MessageListener {

	public PollerRequestManager(
		JSONArray pollerResponseChunksJSON, String destinationName,
		String responseDestinationName, long timeout) {

		_pollerResponseChunksJSON = pollerResponseChunksJSON;
		_destinationName = destinationName;
		_responseDestinationName = responseDestinationName;
		_timeout = timeout;
	}

	public void addPollerRequest(PollerRequest pollerRequest) {
		if (pollerRequest == null) {
			return;
		}

		_pollerRequests.put(pollerRequest.getPortletId(), pollerRequest);
	}

	public void clearRequests() {
		_pollerRequests.clear();
		_responseIds.clear();
		_responseCount = 0;
	}

	public JSONArray getPollerResponse() {
		return _pollerResponseChunksJSON;
	}

	public void processRequests() {
		MessageBusUtil.registerMessageListener(_responseDestinationName, this);

		try {
			for (PollerRequest pollerRequest : _pollerRequests.values()) {
				Message message = new Message();

				message.setPayload(pollerRequest);
				message.setResponseDestinationName(_responseDestinationName);

				String responseId = PortalUUIDUtil.generate();

				message.setResponseId(responseId);

				_responseIds.put(responseId, responseId);

				MessageBusUtil.sendMessage(_destinationName, message);
			}

			synchronized (this) {
				if (_responseCount != _pollerRequests.size()) {
					try {
						this.wait(_timeout);
					}
					catch (InterruptedException ie) {
					}
				}
			}
		}
		finally {
			MessageBusUtil.unregisterMessageListener(
				_responseDestinationName, this);
		}
	}

	public void receive(Message message) {
		if (!_responseIds.containsKey(message.getResponseId())) {
			return;
		}

		if (_pollerResponseChunksJSON != null) {
			PollerResponse pollerResponse =
				(PollerResponse)message.getPayload();

			if (pollerResponse != null) {
				_pollerResponseChunksJSON.put(pollerResponse.toJSONObject());
			}
		}

		synchronized (this) {
			_responseCount++;

			if (_responseCount == _pollerRequests.size()) {
				notify();
			}
		}
	}

	private String _destinationName;
	private Map<String, PollerRequest> _pollerRequests =
		new HashMap<String, PollerRequest>();
	private JSONArray _pollerResponseChunksJSON;
	private int _responseCount;
	private String _responseDestinationName;
	private Map<String, String> _responseIds = new HashMap<String, String>();
	private long _timeout;

}