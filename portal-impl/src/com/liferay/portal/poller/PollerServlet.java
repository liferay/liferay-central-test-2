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

package com.liferay.portal.poller;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.poller.PollerHeader;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BrowserTracker;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.BrowserTrackerLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.Encryptor;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class PollerServlet extends HttpServlet {

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			String content = getContent(request);

			if (content == null) {
				PortalUtil.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					new NoSuchLayoutException(), request, response);
			}
			else {
				response.setContentType(ContentTypes.TEXT_PLAIN_UTF8);

				ServletResponseUtil.write(
					response, content.getBytes(StringPool.UTF8));
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, request,
				response);
		}
	}

	protected String getContent(HttpServletRequest request) throws Exception {
		String pollerRequestString = getPollerRequestString(request);

		if (Validator.isNull(pollerRequestString)) {
			return null;
		}

		Map<String, Object>[] pollerRequestChunks =
			(Map<String, Object>[])JSONFactoryUtil.deserialize(
				pollerRequestString);

		PollerHeader pollerHeader = getPollerHeader(pollerRequestChunks);

		if (pollerHeader == null) {
			return null;
		}

		boolean receiveRequest = isReceiveRequest(request);

		JSONArray pollerResponseChunksJSON = null;
		Set<String> portletIdsWithChunks = null;

		if (receiveRequest) {
			pollerResponseChunksJSON = JSONFactoryUtil.createJSONArray();
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

			JSONObject pollerResponseChunkJSON =
				JSONFactoryUtil.createJSONObject();

			pollerResponseChunkJSON.put("userId", pollerHeader.getUserId());
			pollerResponseChunkJSON.put(
				"initialRequest", pollerHeader.isInitialRequest());
			pollerResponseChunkJSON.put("suspendPolling", suspendPolling);

			pollerResponseChunksJSON.put(pollerResponseChunkJSON);
		}

		PollerRequestManager pollerRequestManager = new PollerRequestManager(
			pollerResponseChunksJSON, DestinationNames.POLLER,
			DestinationNames.POLLER_RESPONSE,
			PropsValues.POLLER_REQUEST_TIMEOUT);

		for (int i = 1; i < pollerRequestChunks.length; i++) {
			Map<String, Object> pollerRequestChunk = pollerRequestChunks[i];

			String portletId = (String)pollerRequestChunk.get("portletId");
			Map<String, String> parameterMap = getData(pollerRequestChunk);
			String chunkId = (String)pollerRequestChunk.get("chunkId");

			try {
				PollerRequest pollerRequest = process(
					portletIdsWithChunks, pollerHeader, portletId, parameterMap,
					chunkId, receiveRequest);

				pollerRequestManager.addPollerRequest(pollerRequest);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		pollerRequestManager.processRequests();

		if (!receiveRequest) {
			return StringPool.BLANK;
		}

		pollerRequestManager.clearRequests();

		for (String portletId : pollerHeader.getPortletIds()) {
			if (portletIdsWithChunks.contains(portletId)) {
				continue;
			}

			try {
				PollerRequest pollerRequest = process(
					portletIdsWithChunks, pollerHeader, portletId,
					new HashMap<String, String>(), null, receiveRequest);

				pollerRequestManager.addPollerRequest(pollerRequest);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		pollerRequestManager.processRequests();

		pollerResponseChunksJSON = pollerRequestManager.getPollerResponse();

		return pollerResponseChunksJSON.toString();
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

	protected String getPollerRequestString(HttpServletRequest request)
		throws Exception {

		String pollerRequestString = ParamUtil.getString(
			request, "pollerRequest");

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

	protected boolean isReceiveRequest(HttpServletRequest request)
		throws Exception {

		String path = GetterUtil.getString(request.getPathInfo());

		if (path.endsWith(_PATH_RECEIVE)) {
			return true;
		}
		else {
			return false;
		}
	}

	protected PollerRequest process(
			Set<String> portletIdsWithChunks, PollerHeader pollerHeader,
			String portletId, Map<String, String> parameterMap, String chunkId,
			boolean receiveRequest)
		throws Exception {

		PollerProcessor pollerProcessor =
			PollerProcessorUtil.getPollerProcessor(portletId);

		if (pollerProcessor == null) {
			_log.error("Poller processor not found for portlet " + portletId);

			return null;
		}

		PollerRequest pollerRequest = new PollerRequest(
			pollerHeader, portletId, parameterMap, chunkId, receiveRequest);

		if (receiveRequest) {
			portletIdsWithChunks.add(portletId);
		}

		return pollerRequest;
	}

	private static final String _ESCAPED_CLOSE_CURLY_BRACE =
		"[$CLOSE_CURLY_BRACE$]";

	private static final String _ESCAPED_OPEN_CURLY_BRACE =
		"[$OPEN_CURLY_BRACE$]";

	private static final String _OPEN_HASH_MAP_WRAPPER =
		"{\"javaClass\":\"java.util.HashMap\",\"map\":{";

	private static final String _PATH_RECEIVE = "/receive";

	private static Log _log = LogFactoryUtil.getLog(PollerServlet.class);

}