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

package com.liferay.portal.monitoring.statistics.portal;

import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.monitoring.RequestStatus;
import com.liferay.portal.monitoring.statistics.DataSampleProcessor;
import com.liferay.portal.monitoring.statistics.RequestStatistics;
import com.liferay.portal.service.CompanyLocalService;

/**
 * <a href="CompanyStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * @author Rajesh Thiagarajan
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class CompanyStatistics
	implements DataSampleProcessor<PortalRequestDataSample> {

	public CompanyStatistics() {
		_companyId = CompanyConstants.SYSTEM;
		_webId = CompanyConstants.SYSTEM_STRING;
		_requestStatistics = new RequestStatistics(_webId);
	}

	public CompanyStatistics(
		CompanyLocalService companyLocalService, String webId) {

		try {
			Company company = companyLocalService.getCompanyByWebId(webId);

			_companyId = company.getCompanyId();
			_webId = webId;
			_requestStatistics = new RequestStatistics(_webId);
		}
		catch (Exception e) {
			throw new IllegalStateException(
				"Unable to get company with web id " + webId, e);
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getMaxTime() {
		return _maxTime;
	}

	public long getMinTime() {
		return _minTime;
	}

	public RequestStatistics getRequestStatistics() {
		return _requestStatistics;
	}

	public long getStartTime() {
		return _startTime;
	}

	public long getUptime() {
		return System.currentTimeMillis() - _startTime;
	}

	public String getWebId() {
		return _webId;
	}

	public void processDataSample(
		PortalRequestDataSample portalRequestDataSample) {

		if (portalRequestDataSample.getCompanyId() != _companyId) {
			return;
		}

		RequestStatus requestStatus =
			portalRequestDataSample.getRequestStatus();

		if (requestStatus.equals(RequestStatus.ERROR)) {
			_requestStatistics.incrementError();
		}
		else if (requestStatus.equals(RequestStatus.SUCCESS)) {
			_requestStatistics.incrementSuccessDuration(
				portalRequestDataSample.getDuration());
		}
		else if (requestStatus.equals(RequestStatus.TIMEOUT)) {
			_requestStatistics.incrementTimeout();
		}

		long duration = portalRequestDataSample.getDuration();

		if (_maxTime < duration) {
			_maxTime = duration;
		}
		else if (_minTime > duration) {
			_minTime = duration;
		}
	}

	public void reset() {
		_maxTime = 0;
		_minTime = 0;

		_requestStatistics.reset();
	}

	private long _companyId;
	private long _maxTime;
	private long _minTime;
	private RequestStatistics _requestStatistics;
	private long _startTime = System.currentTimeMillis();
	private String _webId;

}