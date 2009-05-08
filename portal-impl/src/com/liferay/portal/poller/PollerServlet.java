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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
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

import java.util.Map;

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

		StringBuilder sb = new StringBuilder();

		Map<String, Object>[] pollerRequestEntries =
			(Map<String, Object>[])JSONFactoryUtil.deserialize(pollerRequest);

		for (int i = 0; i < pollerRequestEntries.length; i++) {
			Map<String, Object> pollerRequestEntry = pollerRequestEntries[i];

			PollerHeader pollerHeader = null;

			if (i == 0) {
				long companyId = GetterUtil.getLong(
					(String)pollerRequestEntry.get("companyId"));
				String userIdString = GetterUtil.getString(
					(String)pollerRequestEntry.get("userId"));
				long timestamp = GetterUtil.getLong(
					(String)pollerRequestEntry.get("timestamp"));
				long browserKey = GetterUtil.getLong(
					(String)pollerRequestEntry.get("browserKey"));
				boolean startPolling = GetterUtil.getBoolean(
					(String)pollerRequestEntry.get("startPolling"));

				long userId = getUserId(companyId, userIdString);

				if (userId == 0) {
					return null;
				}

				pollerHeader = new PollerHeader(userId, timestamp, browserKey);

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

				sb.append("[{\"userId\":");
				sb.append(userId);
				sb.append(",\"timestamp\":");
				sb.append(timestamp);
				sb.append(",\"suspendPolling\":");
				sb.append(suspendPolling);
				sb.append("\"}");
			}
			else {
				String portletId = (String)pollerRequestEntry.get(
					"portletId");
				Map<String, String> parameterMap =
					(Map<String, String>)pollerRequestEntry.get("data");

				try {
					process(pollerHeader, portletId, parameterMap, sb);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}

		sb.append(StringPool.CLOSE_BRACKET);

		return sb.toString();
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

	protected void process(
			PollerHeader pollerHeader, String portletId, Map<String,
			String> parameterMap, StringBuilder sb)
		throws Exception {

		PollerProcessor pollerProcessor =
			PollerProcessorUtil.getPollerProcessor(portletId);

		if (pollerProcessor == null) {
			_log.error("Poller processor not found for portlet " + portletId);

			return;
		}

		PollerRequest pollerRequest = new PollerRequest(
			pollerHeader, portletId, parameterMap);
		PollerResponse pollerResponse = new PollerResponse(portletId);

		pollerProcessor.process(pollerRequest, pollerResponse);

		pollerResponse.toString(sb);
	}

	private static final String _OPEN_HASH_MAP_WRAPPER =
		"{\"javaClass\":\"java.util.HashMap\",\"map\":{";

	private static Log _log = LogFactoryUtil.getLog(PollerServlet.class);

}