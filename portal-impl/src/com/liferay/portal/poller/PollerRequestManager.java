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
 * <a href="PollerRequestManager.java.html"><b><i>View Source</i></b></a>
 *
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