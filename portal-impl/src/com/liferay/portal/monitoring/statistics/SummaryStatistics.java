/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.monitoring.statistics;

import com.liferay.portal.monitoring.MonitoringException;

/**
 * <a href="SummaryStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public interface SummaryStatistics {

	public long getAverageTime() throws MonitoringException;

	public long getAverageTimeByCompany(long companyId)
		throws MonitoringException;

	public long getAverageTimeByCompany(String webId)
		throws MonitoringException;

	public long getErrorCount() throws MonitoringException;

	public long getErrorCountByCompany(long companyId)
		throws MonitoringException;

	public long getErrorCountByCompany(String webId) throws MonitoringException;

	public long getMaxTime() throws MonitoringException;

	public long getMaxTimeByCompany(long companyId) throws MonitoringException;

	public long getMaxTimeByCompany(String webId) throws MonitoringException;

	public long getMinTime() throws MonitoringException;

	public long getMinTimeByCompany(long companyId) throws MonitoringException;

	public long getMinTimeByCompany(String webId) throws MonitoringException;

	public long getRequestCount() throws MonitoringException;

	public long getRequestCountByCompany(long companyId)
		throws MonitoringException;

	public long getRequestCountByCompany(String webId)
		throws MonitoringException;

	public long getSuccessCount() throws MonitoringException;

	public long getSuccessCountByCompany(long companyId)
		throws MonitoringException;

	public long getSuccessCountByCompany(String webId)
		throws MonitoringException;

	public long getTimeoutCount() throws MonitoringException;

	public long getTimeoutCountByCompany(long companyId)
		throws MonitoringException;

	public long getTimeoutCountByCompany(String webId)
		throws MonitoringException;

}