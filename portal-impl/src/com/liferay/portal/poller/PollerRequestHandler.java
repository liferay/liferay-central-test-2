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

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.poller.DefaultPollerResponse;
import com.liferay.portal.kernel.poller.PollerException;
import com.liferay.portal.kernel.poller.PollerHeader;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.BrowserTracker;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.BrowserTrackerLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.util.Encryptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Edward Han
 */
public class PollerRequestHandler implements MessageListener {

	public PollerRequestHandler(
		String path, String pollerRequestString,
		PollerResponseWriter pollerResponseWriter,
		List<PollerRequestHandlerListener> listeners) {

		_listeners = listeners;
		_path = path;
		_pollerRequestString = fixPollerRequestString(pollerRequestString);
		_pollerResponseWriter = pollerResponseWriter;
		_receiveRequest = isReceiveRequest(_path);
	}

	public boolean isActive() {
		return _active;
	}

	public boolean processRequest() throws Exception {
		if (Validator.isNull(_pollerRequestString)) {
			return false;
		}

		Map<String, Object>[] pollerRequestChunks =
			(Map<String, Object>[])JSONFactoryUtil.deserialize(
				_pollerRequestString);

		PollerHeader pollerHeader = getPollerHeader(pollerRequestChunks);

		if (pollerHeader == null) {
			return false;
		}

		Set<String> portletIdsWithChunks = null;

		if (_receiveRequest) {
			portletIdsWithChunks = new HashSet<String>();

			boolean suspendPolling = false;

			if (pollerHeader.isStartPolling()) {
				BrowserTrackerLocalServiceUtil.updateBrowserTracker(
					pollerHeader.getUserId(), pollerHeader.getBrowserKey());
			}
			else {
				BrowserTracker browserTracker =
					BrowserTrackerLocalServiceUtil.getBrowserTracker(
						pollerHeader.getUserId(), pollerHeader.getBrowserKey());

				if (browserTracker.getBrowserKey() !=
						pollerHeader.getBrowserKey()) {

					suspendPolling = true;
				}
			}

			JSONObject pollerResponseChunkJSONObject =
				JSONFactoryUtil.createJSONObject();

			pollerResponseChunkJSONObject.put(
				"userId", pollerHeader.getUserId());
			pollerResponseChunkJSONObject.put(
				"initialRequest", pollerHeader.isInitialRequest());
			pollerResponseChunkJSONObject.put("suspendPolling", suspendPolling);

			_pollerResponseWriter.write(pollerResponseChunkJSONObject);
		}

		for (int i = 1; i < pollerRequestChunks.length; i++) {
			Map<String, Object> pollerRequestChunk = pollerRequestChunks[i];

			String portletId = (String)pollerRequestChunk.get("portletId");
			Map<String, String> parameterMap = getData(pollerRequestChunk);
			String chunkId = (String)pollerRequestChunk.get("chunkId");

			try {
				addPollerRequest(
					portletIdsWithChunks, pollerHeader, portletId, parameterMap,
					chunkId);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		processRequests();

		if (!_receiveRequest) {
			shutdown();

			return true;
		}

		clearRequests();

		for (String portletId : pollerHeader.getPortletIds()) {
			if (portletIdsWithChunks.contains(portletId)) {
				continue;
			}

			try {
				addPollerRequest(
					portletIdsWithChunks, pollerHeader, portletId,
					new HashMap<String, String>(), null);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		processRequests();

		return true;
	}

	public void receive(Message message) {
		if (!_responseIds.containsKey(message.getResponseId())) {
			return;
		}

		PollerResponse pollerResponse = (PollerResponse)message.getPayload();

		if (pollerResponse != null) {
			try {
				_pollerResponseWriter.write(pollerResponse);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		synchronized (this) {
			_responseCount++;

			if (_responseCount == _pollerRequestResponsePairs.size()) {
				try {
					shutdown();
				}
				catch (PollerException pe) {
					_log.error(pe, pe);
				}
			}
		}
	}

	public void shutdown() throws PollerException {
		synchronized (this) {
			if (_active) {
				if (_receiveRequest) {
					for (PollerRequestResponsePair pollerRequestResponsePair :
						_pollerRequestResponsePairs.values()) {

						pollerRequestResponsePair.getPollerResponse().close();
					}

					MessageBusUtil.unregisterMessageListener(
						DestinationNames.POLLER_RESPONSE, this);
				}

				_pollerResponseWriter.close();

				for (PollerRequestHandlerListener listener : _listeners) {
					listener.notifyHandlingComplete();
				}

				_active = false;
			}
		}
	}

	protected void addPollerRequest(
			Set<String> portletIdsWithChunks, PollerHeader pollerHeader,
			String portletId, Map<String, String> parameterMap, String chunkId)
		throws Exception {

		PollerProcessor pollerProcessor =
			PollerProcessorUtil.getPollerProcessor(portletId);

		if (pollerProcessor == null) {
			_log.error("Poller processor not found for portlet " + portletId);

			return;
		}

		PollerRequest pollerRequest = new PollerRequest(
			pollerHeader, portletId, parameterMap, chunkId, _receiveRequest);

		if (_receiveRequest) {
			portletIdsWithChunks.add(portletId);
		}

		PollerRequestResponsePair pollerRequestResponsePair =
			new PollerRequestResponsePair(pollerRequest);

		_pollerRequestResponsePairs.put(
			pollerRequest.getPortletId(), pollerRequestResponsePair);
	}

	protected void clearRequests() {
		_pollerRequestResponsePairs.clear();
		_responseIds.clear();
		_responseCount = 0;
	}

	protected String fixPollerRequestString(String pollerRequestString) {
		if (Validator.isNull(pollerRequestString)) {
			return null;
		}

		return StringUtil.replace(
			pollerRequestString,
			new String[] {
				StringPool.OPEN_CURLY_BRACE,
				StringPool.CLOSE_CURLY_BRACE,
				_ESCAPED_OPEN_CURLY_BRACE,
				_ESCAPED_CLOSE_CURLY_BRACE
			},
			new String[] {
				_OPEN_HASH_MAP_WRAPPER,
				StringPool.DOUBLE_CLOSE_CURLY_BRACE,
				StringPool.OPEN_CURLY_BRACE,
				StringPool.CLOSE_CURLY_BRACE
			});
	}

	protected Map<String, String> getData(
			Map<String, Object> pollerRequestChunk)
		throws Exception {

		Map<String, Object> oldParameterMap =
			(Map<String, Object>)pollerRequestChunk.get("data");

		Map<String, String> newParameterMap = new HashMap<String, String>();

		if (oldParameterMap == null) {
			return newParameterMap;
		}

		for (Map.Entry<String, Object> entry : oldParameterMap.entrySet()) {
			newParameterMap.put(
				entry.getKey(), String.valueOf(entry.getValue()));
		}

		return newParameterMap;
	}

	protected PollerHeader getPollerHeader(
			Map<String, Object>[] pollerRequestChunks)
		throws Exception {

		if (pollerRequestChunks.length < 1) {
			return null;
		}

		Map<String, Object> pollerRequestChunk = pollerRequestChunks[0];

		long companyId = GetterUtil.getLong(
			String.valueOf(pollerRequestChunk.get("companyId")));
		String userIdString = GetterUtil.getString(
			String.valueOf(pollerRequestChunk.get("userId")));
		long browserKey = GetterUtil.getLong(
			String.valueOf(pollerRequestChunk.get("browserKey")));
		String[] portletIds = StringUtil.split(
			String.valueOf(pollerRequestChunk.get("portletIds")));
		boolean initialRequest = GetterUtil.getBoolean(
			String.valueOf(pollerRequestChunk.get("initialRequest")));
		boolean startPolling = GetterUtil.getBoolean(
			String.valueOf(pollerRequestChunk.get("startPolling")));

		long userId = getUserId(companyId, userIdString);

		if (userId == 0) {
			return null;
		}

		return new PollerHeader(
			companyId, userId, browserKey, portletIds, initialRequest,
			startPolling);
	}

	protected long getUserId(long companyId, String userIdString) {
		long userId = 0;

		try {
			Company company = CompanyLocalServiceUtil.getCompany(companyId);

			userId = GetterUtil.getLong(
				Encryptor.decrypt(company.getKeyObj(), userIdString));
		}
		catch (Exception e) {
			_log.error(
				"Invalid credentials for company id " + companyId +
					" and user id " + userIdString);
		}

		return userId;
	}

	protected boolean isReceiveRequest(String path) {
		if ((path != null) && path.endsWith(_PATH_RECEIVE)) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void processRequests() {
		if (_receiveRequest) {
			MessageBusUtil.registerMessageListener(
				DestinationNames.POLLER_RESPONSE, this);
		}

		for (PollerRequestResponsePair pollerRequestResponsePair :
			_pollerRequestResponsePairs.values()) {

			PollerRequest pollerRequest =
				pollerRequestResponsePair.getPollerRequest();

			if (_receiveRequest) {
				PollerResponse pollerResponse = new DefaultPollerResponse(
					pollerRequest.getPortletId(), pollerRequest.getChunkId());

				pollerRequestResponsePair.setPollerResponse(pollerResponse);
			}

			Message message = new Message();

			message.setPayload(pollerRequestResponsePair);

			if (_receiveRequest) {
				message.setResponseDestinationName(
					DestinationNames.POLLER_RESPONSE);
			}

			String responseId = PortalUUIDUtil.generate();

			message.setResponseId(responseId);

			_responseIds.put(responseId, responseId);

			MessageBusUtil.sendMessage(DestinationNames.POLLER, message);
		}
	}

	private static final String _ESCAPED_CLOSE_CURLY_BRACE =
		"[$CLOSE_CURLY_BRACE$]";

	private static final String _ESCAPED_OPEN_CURLY_BRACE =
		"[$OPEN_CURLY_BRACE$]";

	private static final String _OPEN_HASH_MAP_WRAPPER =
		"{\"javaClass\":\"java.util.HashMap\",\"map\":{";

	private static final String _PATH_RECEIVE = "/receive";

	private static Log _log = LogFactoryUtil.getLog(
		PollerRequestHandler.class);

	private boolean _active = true;
	private List<PollerRequestHandlerListener> _listeners =
		new ArrayList<PollerRequestHandlerListener>();
	private String _path;
	private Map<String, PollerRequestResponsePair> _pollerRequestResponsePairs =
		new HashMap<String, PollerRequestResponsePair>();
	private String _pollerRequestString;
	private PollerResponseWriter _pollerResponseWriter;
	private boolean _receiveRequest;
	private int _responseCount;
	private Map<String, String> _responseIds = new HashMap<String, String>();
}