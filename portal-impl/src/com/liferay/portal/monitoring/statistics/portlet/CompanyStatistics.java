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

package com.liferay.portal.monitoring.statistics.portlet;

import com.liferay.portal.model.Company;
import com.liferay.portal.monitoring.MonitoringException;
import com.liferay.portal.monitoring.statistics.DataSampleProcessor;
import com.liferay.portal.monitoring.statistics.RequestStatistics;
import com.liferay.portal.service.CompanyLocalService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="CompanyStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * @author Karthik Sudarshan
 * @author Michael C. Han
 *
 */
public class CompanyStatistics
	implements DataSampleProcessor<PortletRequestDataSample> {

	public CompanyStatistics(
		CompanyLocalService companyLocalService, String webId) {

		try {
			Company company = companyLocalService.getCompanyByWebId(webId);

			_companyId = company.getCompanyId();
			_webId = webId;
			_portletStatisticsByPortletId =
				new ConcurrentHashMap<String, PortletStatistics>();
		}
		catch (Exception e) {
			throw new IllegalStateException(
				"Unable to get company with web id " + webId, e);
		}
	}

	public Collection<RequestStatistics> getActionStatistics()
		throws MonitoringException {

		Collection<PortletStatistics> portletsStats =
			_portletStatisticsByPortletId.values();

		if (portletsStats.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<RequestStatistics> actionStatistics =
			new ArrayList<RequestStatistics>(portletsStats.size());

		for (PortletStatistics portletStats : portletsStats) {
			actionStatistics.add(portletStats.getActionStatistics());
		}

		return actionStatistics;
	}

	public RequestStatistics getActionStatistics(String portletId)
		throws MonitoringException {

		PortletStatistics portletStats =
			_portletStatisticsByPortletId.get(portletId);

		if (portletStats == null) {
			throw new MonitoringException(
				"No portlet found with id: " + portletId);
		}

		return portletStats.getActionStatistics();
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Collection<RequestStatistics> getEventStatistics()
		throws MonitoringException {

		Collection<PortletStatistics> portletsStats =
			_portletStatisticsByPortletId.values();

		if (portletsStats.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<RequestStatistics> eventStatistics =
			new ArrayList<RequestStatistics>(portletsStats.size());

		for (PortletStatistics portletStats : portletsStats) {
			eventStatistics.add(portletStats.getEventStatistics());
		}

		return eventStatistics;
	}

	public RequestStatistics getEventStatistics(String portletId)
		throws MonitoringException {

		PortletStatistics portletStats =
			_portletStatisticsByPortletId.get(portletId);

		if (portletStats == null) {
			throw new MonitoringException(
				"No portlet found with id: " + portletId);
		}

		return portletStats.getEventStatistics();

	}

	public long getMaxTime() {
		return _maxTime;
	}

	public long getMinTime() {
		return _minTime;
	}

	public Collection<String> getPortletIds() {
		return _portletStatisticsByPortletId.keySet();
	}

	public Collection<RequestStatistics> getRenderStatistics()
		throws MonitoringException {

		Collection<PortletStatistics> portletsStats =
			_portletStatisticsByPortletId.values();

		if (portletsStats.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<RequestStatistics> renderStatistics =
			new ArrayList<RequestStatistics>(portletsStats.size());

		for (PortletStatistics portletStats : portletsStats) {
			renderStatistics.add(portletStats.getRenderStatistics());
		}

		return renderStatistics;
	}

	public RequestStatistics getRenderStatistics(String portletId)
		throws MonitoringException {

		PortletStatistics portletStats =
			_portletStatisticsByPortletId.get(portletId);

		if (portletStats == null) {
			throw new MonitoringException(
				"No portlet found with id: " + portletId);
		}

		return portletStats.getRenderStatistics();

	}

	public Collection<RequestStatistics> getResourceStatistics()
		throws MonitoringException {

		Collection<PortletStatistics> portletsStats =
			_portletStatisticsByPortletId.values();

		if (portletsStats.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<RequestStatistics> resourceStatistics =
			new ArrayList<RequestStatistics>(portletsStats.size());

		for (PortletStatistics portletStats : portletsStats) {
			resourceStatistics.add(portletStats.getResourceStatistics());
		}

		return resourceStatistics;
	}
	
	public RequestStatistics getResourceStatistics(String portletId)
		throws MonitoringException {

		PortletStatistics portletStats =
			_portletStatisticsByPortletId.get(portletId);

		if (portletStats == null) {
			throw new MonitoringException(
				"No portlet found with id: " + portletId);
		}

		return portletStats.getResourceStatistics();

	}

	public String getWebId() {
		return _webId;
	}

	public void processDataSample(
		PortletRequestDataSample portletRequestDataSample)
		throws MonitoringException {

		if (portletRequestDataSample.getCompanyId() != _companyId) {
			return;
		}

		String portletId = portletRequestDataSample.getPortletId();

		PortletStatistics portletStatistics = _portletStatisticsByPortletId.get(
			portletId);

		if (portletStatistics == null) {
			portletStatistics = new PortletStatistics(
				portletId, portletRequestDataSample.getName(),
				portletRequestDataSample.getDisplayName());

			_portletStatisticsByPortletId.put(portletId, portletStatistics);
		}

		portletStatistics.processDataSample(portletRequestDataSample);

		long currentDuration = portletRequestDataSample.getDuration();

		if (_maxTime < currentDuration) {
			_maxTime = currentDuration;
		}
		else if (_minTime > currentDuration) {
			_minTime = currentDuration;
		}
	}

	public void reset() {
		_maxTime = 0;
		_minTime = 0;
		for (PortletStatistics portletStatistics : _portletStatisticsByPortletId.values()) {
			portletStatistics.reset();
		}
	}

	private long _companyId;
	private long _maxTime;
	private long _minTime;
	private Map<String, PortletStatistics> _portletStatisticsByPortletId;
	private String _webId;

}