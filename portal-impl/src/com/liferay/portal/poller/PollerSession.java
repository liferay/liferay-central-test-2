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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Edward Han
 */
public class PollerSession {
	public PollerSession(String id) {
		_id = id;
	}

	public String getId() {
		return _id;
	}

	public synchronized boolean beginPortletProcessing(
		PollerRequestResponsePair pollerRequestResponsePair,
		String responseId) {

		String portletId = pollerRequestResponsePair.
			getPollerRequest().getPortletId();

		//do not process new request if there is a request already pending
		//this prevents flooding the server in the event of slow receive
		//requests.
		if (_pendingPortletIds.containsKey(portletId)) {
			return false;
		}

		_pendingPortletIds.put(portletId, responseId);

		_requestResponsePairs.put(portletId, pollerRequestResponsePair);

		return true;
	}

	public synchronized boolean completePortletProcessing(
		String responseId, String portletId) {

		if (responseId.equals(_pendingPortletIds.get(portletId))) {
			_pendingPortletIds.remove(portletId);

			_requestResponsePairs.remove(portletId);
		}

		return _pendingPortletIds.isEmpty();
	}

	private String _id;
	private Map<String, String> _pendingPortletIds =
		new HashMap<String, String>();
	private Map<String, PollerRequestResponsePair> _requestResponsePairs =
		new HashMap<String, PollerRequestResponsePair>();
}