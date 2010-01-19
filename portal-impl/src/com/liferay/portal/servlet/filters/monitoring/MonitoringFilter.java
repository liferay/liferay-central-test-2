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

package com.liferay.portal.servlet.filters.monitoring;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.monitoring.RequestStatus;
import com.liferay.portal.monitoring.statistics.DataSampleThreadLocal;
import com.liferay.portal.monitoring.statistics.portal.PortalRequestDataSample;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="MonitoringFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Rajesh Thiagarajan
 * @author Michael C. Han
 */
public class MonitoringFilter extends BasePortalFilter {

	public static boolean isMonitoringPortalRequest() {
		return _monitoringPortalRequest;
	}

	public static void setMonitoringPortalRequest(
		boolean monitoringPortalRequest) {

		_monitoringPortalRequest = monitoringPortalRequest;
	}

	protected boolean isFilterEnabled() {

		if (!super.isFilterEnabled()) {
			return false;
		}

		if (!_monitoringPortalRequest) {
			return false;
		}

		return true;
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws IOException, ServletException {

		long companyId = PortalUtil.getCompanyId(request);

		PortalRequestDataSample portalRequestDataSample =
			new PortalRequestDataSample(
				companyId, request.getRemoteUser(), request.getRequestURI(),
				request.getRequestURL().toString());

		try {
			portalRequestDataSample.prepare();

			processFilter(
				MonitoringFilter.class, request, response, filterChain);

			portalRequestDataSample.capture(RequestStatus.SUCCESS);
		}
		catch (Exception e) {
			portalRequestDataSample.capture(RequestStatus.ERROR);

			if (e instanceof IOException) {
				throw (IOException)e;
			}
			else if (e instanceof ServletException) {
				throw (ServletException)e;
			}
			else {
				throw new ServletException("Unable to execute request", e);
			}
		}
		finally {
			MessageBusUtil.sendMessage(
				DestinationNames.MONITORING, portalRequestDataSample);

			DataSampleThreadLocal.addDataSample(portalRequestDataSample);
		}
	}

	private static boolean _monitoringPortalRequest =
		PropsValues.MONITORING_PORTAL_REQUEST;

}