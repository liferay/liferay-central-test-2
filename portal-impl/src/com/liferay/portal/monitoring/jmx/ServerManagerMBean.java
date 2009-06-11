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

/**
 * <a href="PortalManagerMBean.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Karthik Sudarshan 
 */
public interface ServerManagerMBean {

	public long getAverageResponseTime(long companyId)
		throws MonitoringException;

	public long getAverageResponseTime(String companyWebId)
		throws MonitoringException;

	public Long[] getCompanyIds()
		throws MonitoringException;

	public String[] getCompanyWebIds()
		throws MonitoringException;

	public long getErrorCount(long companyId) throws MonitoringException;

	public long getErrorCount(String companyWebId)
	throws MonitoringException;

	public long getMaxResponseTime(long companyId) throws MonitoringException;

	public long getMaxResponseTime(String companyWebId)
		throws MonitoringException;

	public long getMinResponseTime(long companyId) throws MonitoringException;

	public long getMinResponseTime(String companyWebId)
		throws MonitoringException;

	public long getRequestCount(long companyId) throws MonitoringException;

	public long getRequestCount(String companyWebId)
		throws MonitoringException;

	public long getStartTime(long companyId) throws MonitoringException;

	public long getStartTime(String companyWebId) throws MonitoringException;

	public long getSuccessCount(long companyId) throws MonitoringException;

	public long getSuccessCount(String companyWebId)
	throws MonitoringException;

	public long getTimeoutCount(long companyId) throws MonitoringException;

	public long getTimeoutCount(String companyWebId)
	throws MonitoringException;

	public long getUpTime(long companyId) throws MonitoringException;

	public long getUpTime(String companyWebId) throws MonitoringException;

	public void reset();
	
	public void reset(long companyId);

	public void reset(String companyWebId);
}
