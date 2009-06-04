/*
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

package com.liferay.portal.monitoring.statistics.portlet;

import com.liferay.portal.monitoring.RequestStatus;
import com.liferay.portal.monitoring.statistics.DataSampleProcessor;
import com.liferay.portal.monitoring.statistics.RequestStatistics;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="PortletStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * Captures statistics for a single portlet
 *
 * @author Karthik Sudarshan
 * @author Michael C. Han
 */
public class PortletStatistics
	implements DataSampleProcessor<PortletRequestDataSample> {

	public PortletStatistics(
		String portletId, String portletName, String displayName) {

		_portletId = portletId;
		_portletName = portletName;
		_displayName = displayName;

		_renderStatistics = new RequestStatistics(portletId);
		_actionStatistics = new RequestStatistics(portletId);
		_resourceStatistics = new RequestStatistics(portletId);
		_eventStatistics = new RequestStatistics(portletId);

		_requestTypeToStatistics =
			new HashMap<PortletRequestType, RequestStatistics>();

		_requestTypeToStatistics.put(
			PortletRequestType.ACTION, _actionStatistics);
		_requestTypeToStatistics.put(
			PortletRequestType.EVENT, _eventStatistics);
		_requestTypeToStatistics.put(
			PortletRequestType.RENDER, _renderStatistics);
		_requestTypeToStatistics.put(
			PortletRequestType.RESOURCE, _resourceStatistics);
	}

	public RequestStatistics getActionStatistics() {
		return _actionStatistics;
	}

	public String getDisplayName() {
		return _displayName;
	}

	public RequestStatistics getEventStatistics() {
		return _eventStatistics;
	}

	public String getPortletId() {
		return _portletId;
	}

	public String getPortletName() {
		return _portletName;
	}

	public RequestStatistics getRenderStatistics() {
		return _renderStatistics;
	}

	public RequestStatistics getResourceStatistics() {
		return _resourceStatistics;
	}

	public void processDataSample(
		PortletRequestDataSample portletRequestSample) {

		if (!_portletId.equals(portletRequestSample.getPortletId())) {
			return;
		}

		PortletRequestType requestType = portletRequestSample.getRequestType();

		RequestStatistics requestStatistics =
			_requestTypeToStatistics.get(requestType);

		if (requestStatistics == null) {
			throw new IllegalArgumentException(
				"No statistic found for: " + portletRequestSample);
		}

		RequestStatus requestStatus =
			portletRequestSample.getRequestStatus();

		if (requestStatus.equals(RequestStatus.ERROR)) {
			requestStatistics.getErrorStatistics().incrementCount();
		}
		else if (requestStatus.equals(RequestStatus.TIMEOUT)) {
			requestStatistics.getTimeCountStatistics().incrementCount();
		}
		else if (requestStatus.equals(RequestStatus.SUCCESS)) {
			requestStatistics.getSuccessStatistics().addDuration(
				portletRequestSample.getDuration());
		}
	}

	private RequestStatistics _actionStatistics;
	private RequestStatistics _eventStatistics;
	private RequestStatistics _renderStatistics;
	private RequestStatistics _resourceStatistics;
	private String _displayName;
	private String _portletId;
	private String _portletName;
	private Map<PortletRequestType, RequestStatistics> _requestTypeToStatistics;
}
