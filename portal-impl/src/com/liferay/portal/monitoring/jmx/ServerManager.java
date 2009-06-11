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
import com.liferay.portal.monitoring.statistics.StatisticsSummaryCalculator;
import com.liferay.portal.monitoring.statistics.portal.ServerStatistics;

import java.util.Set;

/**
 * <a href="PortalInstancesManager.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class ServerManager implements ServerManagerMBean {

	public ServerManager(
		ServerStatistics instancesStats,
		StatisticsSummaryCalculator calculator) {
		_serverStatistics = instancesStats;
		_statisticsSummaryCalculator = calculator;
	}

	public long getAverageResponseTime(long companyId)
		throws MonitoringException {
		return _statisticsSummaryCalculator.getAverageResponseTime(companyId);
	}

	public long getAverageResponseTime(String companyWebId)
		throws MonitoringException {
		return _statisticsSummaryCalculator.getAverageResponseTime(companyWebId);
	}

	public Long[] getCompanyIds()
		throws MonitoringException {
		Set<Long> companyIds = _serverStatistics.getCompanyIds();
		
		return companyIds.toArray(new Long[companyIds.size()]);
	}

	public String[] getCompanyWebIds()
		throws MonitoringException {
		Set<String> companyWebIds =
			_serverStatistics.getCompanyWebIds();
		
		return companyWebIds.toArray(new String[companyWebIds.size()]);
	}

	public long getErrorCount(long companyId) throws MonitoringException {
		return _statisticsSummaryCalculator.getErrorCount(companyId);
	}

	public long getErrorCount(String companyWebId)
		throws MonitoringException {
		return _statisticsSummaryCalculator.getErrorCount(companyWebId);
	}

	public long getMaxResponseTime(long companyId) throws MonitoringException {
		return _statisticsSummaryCalculator.getMaxResponseTime(companyId);
	}

	public long getMaxResponseTime(String companyWebId)
		throws MonitoringException {
		return _statisticsSummaryCalculator.getMaxResponseTime(companyWebId);
	}

	public long getMinResponseTime(long companyId) throws MonitoringException {
		return _statisticsSummaryCalculator.getMinResponseTime(companyId);
	}

	public long getMinResponseTime(String companyWebId)
		throws MonitoringException {
		return _statisticsSummaryCalculator.getMinResponseTime(companyWebId);
	}

	public long getRequestCount(long companyId) throws MonitoringException {
		return _statisticsSummaryCalculator.getRequestCount(companyId);
	}

	public long getRequestCount(String companyWebId)
		throws MonitoringException {
		return _statisticsSummaryCalculator.getRequestCount(companyWebId);
	}

	public long getStartTime(long companyId) throws MonitoringException {
		return _serverStatistics.getCompanyStatistics(
			companyId).getStartTime();
	}

	public long getStartTime(String companyWebId) throws MonitoringException {
		return _serverStatistics.getCompanyStatistics(
			companyWebId).getStartTime();
	}

	public long getSuccessCount(long companyId)
		throws MonitoringException {
		return _statisticsSummaryCalculator.getSuccessCount(companyId);
	}

	public long getSuccessCount(String companyWebId)
		throws MonitoringException {
		return _statisticsSummaryCalculator.getSuccessCount(companyWebId);
	}

	public long getTimeoutCount(long companyId) throws MonitoringException {
		return _statisticsSummaryCalculator.getTimeoutCount(companyId);
	}

	public long getTimeoutCount(String companyWebId)
		throws MonitoringException {
		return _statisticsSummaryCalculator.getTimeoutCount(companyWebId);
	}

	public long getUpTime(long companyId) throws MonitoringException {
		return _serverStatistics.getCompanyStatistics(
			companyId).getUptime();
	}

	public long getUpTime(String companyWebId) throws MonitoringException {
		return _serverStatistics.getCompanyStatistics(
			companyWebId).getUptime();
	}

	public void reset() {
		_serverStatistics.reset();
	}

	public void reset(long companyId) {
		_serverStatistics.reset(companyId);
	}

	public void reset(String companyWebId) {
		_serverStatistics.reset(companyWebId);
	}

	private ServerStatistics _serverStatistics;
	private StatisticsSummaryCalculator _statisticsSummaryCalculator;
}
