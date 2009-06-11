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
import com.liferay.portal.monitoring.statistics.StatisticsSummaryCalculator;

/**
 * <a href="PortletStatisticsSummaryCalculator.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 */
public interface PortletStatisticsSummaryCalculator
	extends StatisticsSummaryCalculator {

	public long getAverageResponseTime() throws MonitoringException;

	public long getAverageResponseTime(String portletId, long companyId)
		throws MonitoringException;

	public long getAverageResponseTime(String portletId, String companyWebId)
		throws MonitoringException;

	public long getAverageResponseTimeByPortlet(String portletId)
		throws MonitoringException;

	public long getErrorCount(String portletId, long companyId)
		throws MonitoringException;

	public long getErrorCount(String portletId, String companyWebId)
		throws MonitoringException;

	public long getErrorCountByPortlet(String portletId)
		throws MonitoringException;

	public long getMaxResponseTime() throws MonitoringException;

	public long getMaxResponseTime(String portletId, long companyId)
		throws MonitoringException;

	public long getMaxResponseTime(String portletId, String companyWebId)
		throws MonitoringException;

	public long getMaxResponseTimeByPortlet(String portletId)
		throws MonitoringException;

	public long getMinResponseTime() throws MonitoringException;

	public long getMinResponseTime(String portletId, long companyId)
		throws MonitoringException;

	public long getMinResponseTime(String portletId, String companyWebId)
		throws MonitoringException;

	public long getMinResponseTimeByPortlet(String portletId)
		throws MonitoringException;

	public long getRequestCount(String portletId, long companyId)
		throws MonitoringException;

	public long getRequestCount(String portletId, String companyWebId)
		throws MonitoringException;

	public long getRequestCountByPortlet(String portletId)
		throws MonitoringException;

	public long getSuccessCount(String portletId, long companyId)
		throws MonitoringException;

	public long getSuccessCount(String portletId, String companyWebId)
		throws MonitoringException;

	public long getSuccessCountByPortlet(String portletId)
		throws MonitoringException;

	public long getTimeoutCount(String portletId, long companyId)
		throws MonitoringException;

	public long getTimeoutCount(String portletId, String companyWebId)
		throws MonitoringException;

	public long getTimeoutCountByPortlet(String portletId)
		throws MonitoringException;
}
