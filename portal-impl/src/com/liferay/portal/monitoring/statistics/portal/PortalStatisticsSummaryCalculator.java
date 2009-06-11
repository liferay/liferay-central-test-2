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

package com.liferay.portal.monitoring.statistics.portal;

import com.liferay.portal.monitoring.MonitoringException;
import com.liferay.portal.monitoring.statistics.StatisticsSummaryCalculator;

import java.util.Collection;

/**
 * <a href="PortalInstancesStatisticsSummaryCalculator.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class PortalStatisticsSummaryCalculator
	implements StatisticsSummaryCalculator {

	public PortalStatisticsSummaryCalculator(ServerStatistics instancesStats) {
		
		_serverStatistics = instancesStats;
	}

	public long getAverageResponseTime(long companyId)
		throws MonitoringException {
		try {
			CompanyStatistics CompanyStatistics =
				_serverStatistics.getCompanyStatistics(
					companyId);

			return CompanyStatistics.
				getRequestStatistics().getRollingAverageTime();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get average response time for: " + companyId, e);
		}
	}

	public long getAverageResponseTime(String companyWebId)
		throws MonitoringException {
		try {
			CompanyStatistics CompanyStatistics =
				_serverStatistics.getCompanyStatistics(
					companyWebId);

			return CompanyStatistics.
				getRequestStatistics().getRollingAverageTime();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get average response time for: " + companyWebId, e);
		}
	}

	public long getErrorCount() throws MonitoringException {
		Collection<CompanyStatistics> allStatistics =
			_serverStatistics.getCompanyStatistics();

		int totalErrors = 0;

		for (CompanyStatistics statistics : allStatistics) {
			totalErrors += statistics.getRequestStatistics().getNumErrors();
		}

		return totalErrors;
	}

	public long getErrorCount(long companyId) throws MonitoringException {
		try {
			CompanyStatistics CompanyStatistics =
				_serverStatistics.getCompanyStatistics(
					companyId);

			return CompanyStatistics.
				getRequestStatistics().getNumErrors();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get num errors for: " + companyId, e);
		}
	}

	public long getErrorCount(String companyWebId)
		throws MonitoringException {
		try {
			CompanyStatistics CompanyStatistics =
				_serverStatistics.getCompanyStatistics(
					companyWebId);

			return CompanyStatistics.
				getRequestStatistics().getNumErrors();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get num errors for: " + companyWebId, e);
		}
	}

	public long getMaxResponseTime(long companyId) throws MonitoringException {
		try {
			CompanyStatistics CompanyStatistics =
				_serverStatistics.getCompanyStatistics(
					companyId);

			return CompanyStatistics.
				getRequestStatistics().getMaxTime();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get max response time for: " + companyId, e);
		}
	}

	public long getMaxResponseTime(String companyWebId)
		throws MonitoringException {
		try {
			CompanyStatistics CompanyStatistics =
				_serverStatistics.getCompanyStatistics(
					companyWebId);

			return CompanyStatistics.
				getRequestStatistics().getMaxTime();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get max response time for: " + companyWebId, e);
		}
	}

	public long getMinResponseTime(long companyId) throws MonitoringException {
		try {
			CompanyStatistics CompanyStatistics =
				_serverStatistics.getCompanyStatistics(
					companyId);

			return CompanyStatistics.
				getRequestStatistics().getMinTime();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get min response time for: " + companyId, e);
		}
	}

	public long getMinResponseTime(String companyWebId)
		throws MonitoringException {
		try {
			CompanyStatistics CompanyStatistics =
				_serverStatistics.getCompanyStatistics(
					companyWebId);

			return CompanyStatistics.
				getRequestStatistics().getMinTime();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get min response time for: " + companyWebId, e);
		}
	}

	public long getRequestCount() throws MonitoringException {
		Collection<CompanyStatistics> allStatistics =
			_serverStatistics.getCompanyStatistics();

		int totalRequests = 0;

		for (CompanyStatistics statistics : allStatistics) {
			totalRequests += statistics.getRequestStatistics().getNumRequests();
		}

		return totalRequests;
	}

	public long getRequestCount(long companyId) throws MonitoringException {
		try {
			CompanyStatistics CompanyStatistics =
				_serverStatistics.getCompanyStatistics(
					companyId);

			return CompanyStatistics.
				getRequestStatistics().getNumRequests();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get num requests for: " + companyId, e);
		}
	}

	public long getRequestCount(String companyWebId)
		throws MonitoringException {
		try {
			CompanyStatistics CompanyStatistics =
				_serverStatistics.getCompanyStatistics(
					companyWebId);

			return CompanyStatistics.
				getRequestStatistics().getNumRequests();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get num requests for: " + companyWebId, e);
		}
	}

	public long getSuccessCount() throws MonitoringException {
		Collection<CompanyStatistics> allStatistics =
			_serverStatistics.getCompanyStatistics();

		int totalSuccesses = 0;

		for (CompanyStatistics statistics : allStatistics) {
			totalSuccesses +=
				statistics.getRequestStatistics().getNumSuccesses();
		}

		return totalSuccesses;
	}

	public long getSuccessCount(long companyId)
		throws MonitoringException {
		try {
			CompanyStatistics CompanyStatistics =
				_serverStatistics.getCompanyStatistics(
					companyId);

			return CompanyStatistics.
				getRequestStatistics().getNumSuccesses();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get num successes for: " + companyId, e);
		}
	}

	public long getSuccessCount(String companyWebId)
		throws MonitoringException {
		try {
			CompanyStatistics CompanyStatistics =
				_serverStatistics.getCompanyStatistics(
					companyWebId);

			return CompanyStatistics.
				getRequestStatistics().getNumSuccesses();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get num successes for: " + companyWebId, e);
		}
	}

	public long getTimeoutCount() throws MonitoringException {
		Collection<CompanyStatistics> allStatistics =
			_serverStatistics.getCompanyStatistics();

		int totalTimeouts = 0;

		for (CompanyStatistics statistics : allStatistics) {
			totalTimeouts +=
				statistics.getRequestStatistics().getNumTimeouts();
		}

		return totalTimeouts;
	}

	public long getTimeoutCount(long companyId) throws MonitoringException {
		try {
			CompanyStatistics CompanyStatistics =
				_serverStatistics.getCompanyStatistics(
					companyId);

			return CompanyStatistics.
				getRequestStatistics().getNumSuccesses();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get num timeouts for: " + companyId, e);
		}
	}

	public long getTimeoutCount(String companyWebId)
		throws MonitoringException {
		try {
			CompanyStatistics CompanyStatistics =
				_serverStatistics.getCompanyStatistics(
					companyWebId);

			return CompanyStatistics.
				getRequestStatistics().getNumSuccesses();
		}
		catch (Exception e) {
			throw new MonitoringException(
				"Unable to get num timeouts for: " + companyWebId, e);
		}
	}

	private ServerStatistics _serverStatistics;
}
