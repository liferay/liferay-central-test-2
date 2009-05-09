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

package com.liferay.portal.poller;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.poller.PollerHeader;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
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
 * <a href="PollerServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
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
		String pollerRequest = ParamUtil.getString(request, "pollerRequest");

		if (Validator.isNull(pollerRequest)) {
			return null;
		}

		pollerRequest = StringUtil.replace(
			pollerRequest,
			new String[] {
				StringPool.OPEN_CURLY_BRACE,
				StringPool.CLOSE_CURLY_BRACE,
			},
			new String[] {
				_OPEN_HASH_MAP_WRAPPER,
				StringPool.DOUBLE_CLOSE_CURLY_BRACE
			});

		JSONArray pollerResponseEntriesJSON = JSONFactoryUtil.createJSONArray();

		PollerHeader pollerHeader = null;

		Set<String> portletIdsWithEntries = new HashSet<String>();

		Map<String, Object>[] pollerRequestEntries =
			(Map<String, Object>[])JSONFactoryUtil.deserialize(pollerRequest);

		for (int i = 0; i < pollerRequestEntries.length; i++) {
			Map<String, Object> pollerRequestEntry = pollerRequestEntries[i];

			if (i == 0) {
				long companyId = GetterUtil.getLong(
					String.valueOf(pollerRequestEntry.get("companyId")));
				String userIdString = GetterUtil.getString(
					String.valueOf(pollerRequestEntry.get("userId")));
				long timestamp = GetterUtil.getLong(
					String.valueOf(pollerRequestEntry.get("timestamp")));
				long browserKey = GetterUtil.getLong(
					String.valueOf(pollerRequestEntry.get("browserKey")));
				boolean startPolling = GetterUtil.getBoolean(
					String.valueOf(pollerRequestEntry.get("startPolling")));
				String[] portletIds = StringUtil.split(
					String.valueOf(pollerRequestEntry.get("portletIds")));

				long userId = getUserId(companyId, userIdString);

				if (userId == 0) {
					return null;
				}

				pollerHeader = new PollerHeader(
					userId, timestamp, browserKey, portletIds);

				boolean suspendPolling = false;

				if (startPolling) {
					BrowserTrackerLocalServiceUtil.updateBrowserTracker(
						userId, browserKey);
				}
				else {
					BrowserTracker browserTracker =
						BrowserTrackerLocalServiceUtil.getBrowserTracker(
							userId, browserKey);

					if (browserTracker.getBrowserKey() != browserKey) {
						suspendPolling = true;
					}
				}

				JSONObject pollerResponseEntryJSON =
					JSONFactoryUtil.createJSONObject();

				pollerResponseEntryJSON.put("userId", userId);
				pollerResponseEntryJSON.put("timestamp", timestamp);
				pollerResponseEntryJSON.put("suspendPolling", suspendPolling);

				pollerResponseEntriesJSON.put(pollerResponseEntryJSON);
			}
			else {
				String portletId = (String)pollerRequestEntry.get(
					"portletId");
				Map<String, Object> oldParameterMap =
					(Map<String, Object>)pollerRequestEntry.get("data");

				Map<String, String> newParameterMap =
					new HashMap<String, String>();

				for (Map.Entry<String, Object> entry :
						oldParameterMap.entrySet()) {

					newParameterMap.put(
						entry.getKey(), String.valueOf(entry.getValue()));
				}

				JSONObject pollerResponseEntryJSON = null;

				try {
					pollerResponseEntryJSON = process(
						pollerHeader, portletId, newParameterMap);
				}
				catch (Exception e) {
					_log.error(e, e);
				}

				if (pollerResponseEntryJSON != null) {
					portletIdsWithEntries.add(portletId);

					pollerResponseEntriesJSON.put(pollerResponseEntryJSON);
				}
			}
		}

		for (String portletId : pollerHeader.getPortletIds()) {
			if (portletIdsWithEntries.contains(portletId)) {
				continue;
			}

			JSONObject pollerResponseEntryJSON = null;

			try {
				pollerResponseEntryJSON = process(
					pollerHeader, portletId, new HashMap<String, String>());
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			if (pollerResponseEntryJSON != null) {
				pollerResponseEntriesJSON.put(pollerResponseEntryJSON);
			}
		}

		return pollerResponseEntriesJSON.toString();
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

	protected JSONObject process(
			PollerHeader pollerHeader, String portletId, Map<String,
			String> parameterMap)
		throws Exception {

		PollerProcessor pollerProcessor =
			PollerProcessorUtil.getPollerProcessor(portletId);

		if (pollerProcessor == null) {
			_log.error("Poller processor not found for portlet " + portletId);

			return null;
		}

		PollerRequest pollerRequest = new PollerRequest(
			pollerHeader, portletId, parameterMap);
		PollerResponse pollerResponse = new PollerResponse(portletId);

		pollerProcessor.process(pollerRequest, pollerResponse);

		return pollerResponse.toJSONObject();
	}

	private static final String _OPEN_HASH_MAP_WRAPPER =
		"{\"javaClass\":\"java.util.HashMap\",\"map\":{";

	private static Log _log = LogFactoryUtil.getLog(PollerServlet.class);

}