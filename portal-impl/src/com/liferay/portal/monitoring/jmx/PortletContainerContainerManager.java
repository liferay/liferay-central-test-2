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

package com.liferay.portal.monitoring.jmx;

import com.liferay.portal.monitoring.MonitoringException;
import com.liferay.portal.monitoring.statistics.portlet.PortletStatisticsSummaryCalculator;
import com.liferay.portal.monitoring.statistics.portlet.ServerStatistics;

import java.util.Set;

/**
 * <a href="PortletActionRequestManager.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class PortletContainerContainerManager
	implements PortletContainerManagerMBean {

	public PortletContainerContainerManager(
		ServerStatistics containersStatistics,
		PortletStatisticsSummaryCalculator statisticsCalculator) {

		_portletContainsStatistics = containersStatistics;
		_statisticsCalculator = statisticsCalculator;
	}

	public long getAvgResponseTime() throws MonitoringException {
		return _statisticsCalculator.getAverageResponseTime();
	}

	public long getAvgResponseTime(long companyId)
		throws MonitoringException {
		return _statisticsCalculator.getAverageResponseTime(companyId);
	}

	public long getAvgResponseTimeByCompanyWebId(String companyWebId)
		throws MonitoringException {
		return _statisticsCalculator.getAverageResponseTime(companyWebId);
	}

	public long getAvgResponseTime(String portletId)
		throws MonitoringException {
		return _statisticsCalculator.getAverageResponseTimeByPortlet(portletId);
	}

	public long getAvgResponseTime(String portletId, long companyId)
		throws MonitoringException {
		return _statisticsCalculator.getAverageResponseTime(portletId, companyId);
	}

	public long getAvgResponseTime(String portletId, String companyWebId)
		throws MonitoringException {
		return _statisticsCalculator.getAverageResponseTime(portletId, companyWebId);
	}

	public Long[] getCompanyIds() throws MonitoringException {
		Set<Long> companyIds = _portletContainsStatistics.getCompanyIds();

		return companyIds.toArray(new Long[companyIds.size()]);
	}

	public String[] getCompanyWebIds() throws MonitoringException {
		Set<String> companyWebIds =
			_portletContainsStatistics.getCompanyWebIds();

		return companyWebIds.toArray(new String[companyWebIds.size()]);
	}

	public long getErrorCount() throws MonitoringException {
		return _statisticsCalculator.getErrorCount();
	}

	public long getErrorCount(String portletId)
		throws MonitoringException {
		return _statisticsCalculator.getErrorCountByPortlet(portletId);
	}

	public long getErrorCount(String portletId, long companyId)
		throws MonitoringException {
		return _statisticsCalculator.getErrorCount(portletId, companyId);
	}

	public long getErrorCount(String portletId, String companyWebId)
		throws MonitoringException {
		return _statisticsCalculator.getErrorCount(portletId, companyWebId);
	}

	public long getMaxResponseTime() throws MonitoringException {
		return _statisticsCalculator.getMaxResponseTime();
	}

	public long getMaxResponseTime(long companyId)
		throws MonitoringException {
		return _statisticsCalculator.getMaxResponseTime(companyId);
	}

	public long getMaxResponseTimeByCompanyWebId(String companyWebId)
		throws MonitoringException {
		return _statisticsCalculator.getMaxResponseTime(companyWebId);
	}

	public long getMaxResponseTime(String portletId)
		throws MonitoringException {
		return _statisticsCalculator.getMaxResponseTimeByPortlet(portletId);
	}

	public long getMaxResponseTime(String portletId, long companyId)
		throws MonitoringException {
		return _statisticsCalculator.getMaxResponseTime(portletId, companyId);
	}

	public long getMaxResponseTime(String portletId, String companyWebId)
		throws MonitoringException {
		return _statisticsCalculator.getMaxResponseTime(portletId, companyWebId);
	}

	public long getMinResponseTime() throws MonitoringException {
		return _statisticsCalculator.getMinResponseTime();
	}

	public long getMinResponseTime(long companyId)
		throws MonitoringException {
		return _statisticsCalculator.getMinResponseTime(companyId);
	}

	public long getMinResponseTimeByCompanyWebId(String companyWebId)
		throws MonitoringException {
		return _statisticsCalculator.getMinResponseTime(companyWebId);
	}

	public long getMinResponseTime(String portletId)
		throws MonitoringException {
		return _statisticsCalculator.getMinResponseTimeByPortlet(portletId);
	}

	public long getMinResponseTime(String portletId, long companyId)
		throws MonitoringException {
		return _statisticsCalculator.getMinResponseTime(portletId, companyId);
	}


	public long getMinResponseTime(String portletId, String companyWebId)
		throws MonitoringException {
		return _statisticsCalculator.getMinResponseTime(portletId, companyWebId);
	}

	public String[] getPortletIds() throws MonitoringException {
		Set<String> portletIds = _portletContainsStatistics.getPortletIds();

		return portletIds.toArray(new String[portletIds.size()]);
	}

	public long getRequestCount() throws MonitoringException {
		return _statisticsCalculator.getRequestCount();
	}

	public long getRequestCount(String portletId)
		throws MonitoringException {
		return _statisticsCalculator.getRequestCountByPortlet(portletId);
	}

	public long getRequestCount(String portletId, long companyId)
		throws MonitoringException {
		return _statisticsCalculator.getRequestCount(portletId, companyId);
	}

	public long getRequestCount(String portletId, String companyWebId)
		throws MonitoringException {
		return _statisticsCalculator.getRequestCount(portletId, companyWebId);
	}

	public long getTotalErrorCount() throws MonitoringException {
		return _statisticsCalculator.getErrorCount();
	}

	public long getTotalErrorCount(long companyId)
		throws MonitoringException {
		return _statisticsCalculator.getErrorCount(companyId);
	}

	public long getTotalErrorCount(String companyWebId)
		throws MonitoringException {
		return _statisticsCalculator.getErrorCount(companyWebId);
	}

	public long getTotalRequestCount() throws MonitoringException {
		return _statisticsCalculator.getRequestCount();
	}

	public long getTotalRequestCount(long companyId)
		throws MonitoringException {
		return _statisticsCalculator.getRequestCount(companyId);
	}

	public long getTotalRequestCount(String companyWebId)
		throws MonitoringException {
		return _statisticsCalculator.getRequestCount(companyWebId);
	}

	public long getTotalSuccessCount() throws MonitoringException {
		return _statisticsCalculator.getSuccessCount();
	}

	public long getTotalSuccessCount(long companyId)
		throws MonitoringException {
		return _statisticsCalculator.getSuccessCount(companyId);
	}

	public long getTotalSuccessCount(String companyWebId)
		throws MonitoringException {
		return _statisticsCalculator.getSuccessCount(companyWebId);
	}

	public long getTotalTimeoutCount() throws MonitoringException {
		return _statisticsCalculator.getTimeoutCount();
	}

	public long getTotalTimeoutCount(long companyId)
		throws MonitoringException {
		return _statisticsCalculator.getTimeoutCount(companyId);
	}

	public long getTotalTimeoutCount(String companyWebId)
		throws MonitoringException {
		return _statisticsCalculator.getTimeoutCount(companyWebId);
	}

	public void reset() {
		_portletContainsStatistics.reset();
	}

	public void reset(long companyId) {
		_portletContainsStatistics.reset(companyId);
	}

	public void reset(String companyWebId) {
		_portletContainsStatistics.reset(companyWebId);
	}

	private ServerStatistics _portletContainsStatistics;
	private PortletStatisticsSummaryCalculator _statisticsCalculator;
}
