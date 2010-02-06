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

package com.liferay.portal.monitoring.jmx;

import com.liferay.portal.monitoring.MonitoringException;
import com.liferay.portal.monitoring.statistics.SummaryStatistics;

/**
 * <a href="PortalManagerMBean.java.html"><b><i>View Source</i></b></a>
 *
 * @author Karthik Sudarshan
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public interface PortalManagerMBean extends SummaryStatistics {

	public long[] getCompanyIds() throws MonitoringException;

	public long getUptime(long companyId) throws MonitoringException;

	public long getUptime(String companyWebId) throws MonitoringException;

	public String[] getWebIds() throws MonitoringException;

	public void reset();

	public void reset(long companyId);

	public void reset(String webId);

}