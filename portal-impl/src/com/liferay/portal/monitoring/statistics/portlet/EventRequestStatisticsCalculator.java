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

import com.liferay.portal.monitoring.MonitoringException;
import com.liferay.portal.monitoring.statistics.RequestStatistics;

import java.util.Collection;

/**
 * <a href="EventStatisticsCalculator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class EventRequestStatisticsCalculator
	implements PortletStatisticsSummaryCalculator {

	public EventRequestStatisticsCalculator(ServerStatistics statistics) {
		_ServerStatistics = statistics;
	}

	public long getAverageResponseTime() throws MonitoringException {
		Collection<CompanyStatistics> companyStatistics =
			_ServerStatistics.getPortletContainerStatistics();

		long avgTime = 0;
		long totalCount = 0;

		for (CompanyStatistics companyStatistic : companyStatistics) {
			Collection<RequestStatistics> portletsStatistics =
				companyStatistic.getEventStatistics();

			for (RequestStatistics portletStatistics : portletsStatistics) {
				avgTime += portletStatistics.getRollingAverageTime();

				totalCount++;
			}
		}

		return avgTime / totalCount;
	}

	public long getAverageResponseTime(long companyId)
		throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyId);

		long avgTime = 0;

		Collection<RequestStatistics> portletsStatistics =
			companyStatistics.getEventStatistics();
		for (RequestStatistics portletStatistics : portletsStatistics) {
			avgTime += portletStatistics.getRollingAverageTime();
		}

		return avgTime / portletsStatistics.size();
	}

	public long getAverageResponseTime(String companyWebId)
		throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyWebId);

		long avgTime = 0;

		Collection<RequestStatistics> portletsStatistics =
			companyStatistics.getEventStatistics();
		for (RequestStatistics portletStatistics : portletsStatistics) {
			avgTime += portletStatistics.getRollingAverageTime();
		}

		return avgTime / portletsStatistics.size();
	}

	public long getAverageResponseTime(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyId);

		return companyStatistics.getEventStatistics(
			portletId).getRollingAverageTime();
	}

	public long getAverageResponseTime(String portletId, String companyWebId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyWebId);

		return companyStatistics.getEventStatistics(
			portletId).getRollingAverageTime();
	}

	public long getAverageResponseTimeByPortlet(String portletId)
		throws MonitoringException {
		Collection<CompanyStatistics> companyStatistics =
			_ServerStatistics.getPortletContainerStatistics();

		long totalTime = 0;

		for (CompanyStatistics companyStatistic : companyStatistics) {
			totalTime +=
				companyStatistic.getEventStatistics(
					portletId).getRollingAverageTime();
		}

		return totalTime / companyStatistics.size();
	}

	public long getErrorCount() throws MonitoringException {
		Collection<CompanyStatistics> companyStatistics =
			_ServerStatistics.getPortletContainerStatistics();

		long numErrors = 0;

		for (CompanyStatistics companyStatistic : companyStatistics) {
			Collection<RequestStatistics> portletsStatistics =
				companyStatistic.getEventStatistics();
			for (RequestStatistics portletStatistics : portletsStatistics) {
				numErrors += portletStatistics.getNumErrors();
			}
		}

		return numErrors;
	}

	public long getErrorCount(long companyId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyId);

		long numErrors = 0;

		Collection<RequestStatistics> portletsStatistics =
			companyStatistics.getEventStatistics();
		for (RequestStatistics portletStatistics : portletsStatistics) {
			numErrors += portletStatistics.getNumErrors();
		}

		return numErrors;
	}

	public long getErrorCount(String companyWebId)
		throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyWebId);

		long numErrors = 0;

		Collection<RequestStatistics> portletsStatistics =
			companyStatistics.getEventStatistics();
		for (RequestStatistics portletStatistics : portletsStatistics) {
			numErrors += portletStatistics.getNumErrors();
		}

		return numErrors;
	}

	public long getErrorCount(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyId);

		return companyStatistics.getEventStatistics(portletId).getNumErrors();
	}

	public long getErrorCount(String portletId, String companyWebId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyWebId);

		return companyStatistics.getEventStatistics(portletId).getNumErrors();
	}

	public long getErrorCountByPortlet(String portletId)
		throws MonitoringException {

		Collection<CompanyStatistics> companyStatistics =
			_ServerStatistics.getPortletContainerStatistics();

		long numErrors = 0;

		for (CompanyStatistics companyStatistic : companyStatistics) {
			numErrors +=
				companyStatistic.getEventStatistics(portletId).getNumErrors();
		}

		return numErrors;
	}

	public long getMaxResponseTime() throws MonitoringException {
		Collection<CompanyStatistics> companyStatistics =
			_ServerStatistics.getPortletContainerStatistics();

		long maxTime = 0;

		for (CompanyStatistics companyStatistic : companyStatistics) {
			Collection<RequestStatistics> portletsStatistics =
				companyStatistic.getEventStatistics();

			for (RequestStatistics portletStatistics : portletsStatistics) {
				long tempMaxTime = portletStatistics.getMaxTime();

				if (tempMaxTime > maxTime) {
					maxTime = tempMaxTime;
				}
			}
		}

		return maxTime;
	}

	public long getMaxResponseTime(long companyId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyId);

		return companyStatistics.getMaxTime();
	}

	public long getMaxResponseTime(String companyWebId)
		throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyWebId);

		return companyStatistics.getMaxTime();
	}

	public long getMaxResponseTime(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyId);

		return companyStatistics.getEventStatistics(
			portletId).getMaxTime();
	}

	public long getMaxResponseTime(String portletId, String companyWebId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyWebId);

		return companyStatistics.getEventStatistics(
			portletId).getMaxTime();
	}

	public long getMaxResponseTimeByPortlet(String portletId)
		throws MonitoringException {
		Collection<CompanyStatistics> companyStatistics =
			_ServerStatistics.getPortletContainerStatistics();

		long maxTime = 0;

		for (CompanyStatistics companyStatistic : companyStatistics) {
			long tempMaxTime =
				companyStatistic.getEventStatistics(portletId).getMaxTime();
			if (tempMaxTime > maxTime) {
				maxTime = tempMaxTime;
			}
		}

		return maxTime;
	}

	public long getMinResponseTimeByPortlet(String portletId)
		throws MonitoringException {

		Collection<CompanyStatistics> companyStatistics =
			_ServerStatistics.getPortletContainerStatistics();

		long minTime = 0;

		for (CompanyStatistics companyStatistic : companyStatistics) {
			long tempMinTime =
				companyStatistic.getEventStatistics(portletId).getMinTime();
			if (tempMinTime < minTime) {
				minTime = tempMinTime;
			}
		}

		return minTime;
	}

	public long getMinResponseTime() throws MonitoringException {
		Collection<CompanyStatistics> companyStatistics =
			_ServerStatistics.getPortletContainerStatistics();

		long minTime = 0;

		for (CompanyStatistics companyStatistic : companyStatistics) {
			Collection<RequestStatistics> portletsStatistics =
				companyStatistic.getEventStatistics();

			for (RequestStatistics portletStatistics : portletsStatistics) {
				long tempMinTime = portletStatistics.getMinTime();

				if (tempMinTime < minTime) {
					minTime = tempMinTime;
				}
			}
		}

		return minTime;
	}

	public long getMinResponseTime(long companyId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyId);

		return companyStatistics.getMinTime();
	}

	public long getMinResponseTime(String companyWebId)
		throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyWebId);

		return companyStatistics.getMinTime();
	}

	public long getMinResponseTime(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyId);

		return companyStatistics.getEventStatistics(
			portletId).getMinTime();
	}

	public long getMinResponseTime(String portletId, String companyWebId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyWebId);

		return companyStatistics.getEventStatistics(
			portletId).getMinTime();
	}

	public long getRequestCount() throws MonitoringException {
		Collection<CompanyStatistics> companyStatistics =
			_ServerStatistics.getPortletContainerStatistics();

		long numRequests = 0;

		for (CompanyStatistics companyStatistic : companyStatistics) {
			Collection<RequestStatistics> portletsStatistics =
				companyStatistic.getEventStatistics();
			for (RequestStatistics portletStatistics : portletsStatistics) {
				numRequests += portletStatistics.getNumRequests();
			}
		}

		return numRequests;
	}

	public long getRequestCount(long companyId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyId);

		long numRequests = 0;

		Collection<RequestStatistics> portletsStatistics =
			companyStatistics.getEventStatistics();
		for (RequestStatistics portletStatistics : portletsStatistics) {
			numRequests += portletStatistics.getNumRequests();
		}

		return numRequests;
	}

	public long getRequestCount(String companyWebId)
		throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyWebId);

		long numRequests = 0;

		Collection<RequestStatistics> portletsStatistics =
			companyStatistics.getEventStatistics();
		for (RequestStatistics portletStatistics : portletsStatistics) {
			numRequests += portletStatistics.getNumRequests();
		}

		return numRequests;
	}

	public long getRequestCount(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyId);

		return companyStatistics.getEventStatistics(portletId)
			.getNumRequests();
	}

	public long getRequestCount(String portletId, String companyWebId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyWebId);

		return companyStatistics.getEventStatistics(portletId)
			.getNumRequests();
	}

	public long getRequestCountByPortlet(String portletId)
		throws MonitoringException {

		Collection<CompanyStatistics> companyStatistics =
			_ServerStatistics.getPortletContainerStatistics();

		long numRequests = 0;

		for (CompanyStatistics companyStatistic : companyStatistics) {
			numRequests +=
				companyStatistic.getEventStatistics(portletId)
					.getNumRequests();
		}

		return numRequests;
	}

	public long getSuccessCount() throws MonitoringException {
		Collection<CompanyStatistics> companyStatistics =
			_ServerStatistics.getPortletContainerStatistics();

		long numSuccesses = 0;

		for (CompanyStatistics companyStatistic : companyStatistics) {
			Collection<RequestStatistics> portletsStatistics =
				companyStatistic.getEventStatistics();
			for (RequestStatistics portletStatistics : portletsStatistics) {
				numSuccesses += portletStatistics.getNumSuccesses();
			}
		}

		return numSuccesses;
	}

	public long getSuccessCount(long companyId)
		throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyId);

		long numSuccesses = 0;

		Collection<RequestStatistics> portletsStatistics =
			companyStatistics.getEventStatistics();
		for (RequestStatistics portletStatistics : portletsStatistics) {
			numSuccesses += portletStatistics.getNumSuccesses();
		}

		return numSuccesses;
	}

	public long getSuccessCount(String companyWebId)
		throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyWebId);

		long numSuccesses = 0;

		Collection<RequestStatistics> portletsStatistics =
			companyStatistics.getEventStatistics();
		for (RequestStatistics portletStatistics : portletsStatistics) {
			numSuccesses += portletStatistics.getNumSuccesses();
		}

		return numSuccesses;
	}

	public long getSuccessCount(String portletId, long companyId)
		throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyId);

		return companyStatistics.getActionStatistics(portletId)
			.getNumSuccesses();
	}

	public long getSuccessCount(String portletId, String companyWebId)
		throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyWebId);

		return companyStatistics.getActionStatistics(portletId)
			.getNumSuccesses();
	}

	public long getSuccessCountByPortlet(String portletId)
		throws MonitoringException {

		Collection<CompanyStatistics> companyStatistics =
			_ServerStatistics.getPortletContainerStatistics();

		long numSuccesses = 0;

		for (CompanyStatistics companyStatistic : companyStatistics) {
			numSuccesses +=
				companyStatistic.getEventStatistics(portletId)
					.getNumSuccesses();
		}

		return numSuccesses;
	}

	public long getTimeoutCount() throws MonitoringException {
		Collection<CompanyStatistics> companyStatistics =
			_ServerStatistics.getPortletContainerStatistics();

		long numTimeouts = 0;

		for (CompanyStatistics companyStatistic : companyStatistics) {
			Collection<RequestStatistics> portletsStatistics =
				companyStatistic.getEventStatistics();
			for (RequestStatistics portletStatistics : portletsStatistics) {
				numTimeouts += portletStatistics.getNumTimeouts();
			}
		}

		return numTimeouts;
	}

	public long getTimeoutCount(long companyId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyId);

		long numTimeouts = 0;

		Collection<RequestStatistics> portletsStatistics =
			companyStatistics.getEventStatistics();
		for (RequestStatistics portletStatistics : portletsStatistics) {
			numTimeouts += portletStatistics.getNumTimeouts();
		}

		return numTimeouts;
	}

	public long getTimeoutCount(String companyWebId)
		throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyWebId);

		long numTimeouts = 0;

		Collection<RequestStatistics> portletsStatistics =
			companyStatistics.getEventStatistics();
		for (RequestStatistics portletStatistics : portletsStatistics) {
			numTimeouts += portletStatistics.getNumTimeouts();
		}

		return numTimeouts;
	}

	public long getTimeoutCount(String portletId, long companyId)
		throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyId);

		return companyStatistics.getActionStatistics(portletId)
			.getNumTimeouts();
	}

	public long getTimeoutCount(String portletId, String companyWebId)
		throws MonitoringException {
		CompanyStatistics companyStatistics =
			_ServerStatistics.getPortletContainerStatistics(
				companyWebId);

		return companyStatistics.getActionStatistics(portletId)
			.getNumTimeouts();
	}

	public long getTimeoutCountByPortlet(String portletId)
		throws MonitoringException {

		Collection<CompanyStatistics> companyStatistics =
			_ServerStatistics.getPortletContainerStatistics();

		long numTimeouts = 0;

		for (CompanyStatistics companyStatistic : companyStatistics) {
			numTimeouts +=
				companyStatistic.getEventStatistics(portletId)
					.getNumTimeouts();
		}

		return numTimeouts;
	}

	private ServerStatistics _ServerStatistics;
}
