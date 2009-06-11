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

import com.liferay.portal.model.Company;
import com.liferay.portal.monitoring.MonitoringException;
import com.liferay.portal.monitoring.statistics.DataSampleProcessor;
import com.liferay.portal.service.CompanyLocalService;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * <a href="ServerStatistics.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 *
 */
public class ServerStatistics
	implements DataSampleProcessor<PortletRequestDataSample> {

	public ServerStatistics(CompanyLocalService companyLocalService) {
		_companyLocalService = companyLocalService;
		_companyStatisticsByCompanyId =
			new TreeMap<Long, CompanyStatistics>();
		_companyStatisticsByWebId =
			new TreeMap<String, CompanyStatistics>();
	}

	public Set<Long> getCompanyIds() {
		return _companyStatisticsByCompanyId.keySet();
	}

	public Set<String> getCompanyWebIds() {
		return _companyStatisticsByWebId.keySet();
	}

	public Collection<CompanyStatistics> getPortletContainerStatistics() {
		return Collections.unmodifiableCollection(_companyStatisticsByWebId.values());
	}

	public CompanyStatistics getPortletContainerStatistics(
			long companyId)
		throws MonitoringException {

		CompanyStatistics statistics =
			_companyStatisticsByCompanyId.get(companyId);

		if (statistics == null) {
			throw new MonitoringException(
				"No statistics found for: " + companyId);
		}

		return statistics;
	}

	public CompanyStatistics getPortletContainerStatistics(
			String webId)
		throws MonitoringException {

		CompanyStatistics statistics =
			_companyStatisticsByWebId.get(webId);

		if (statistics == null) {
			throw new MonitoringException(
				"No statistics found for : " + webId);
		}

		return statistics;
	}

	public Set<String> getPortletIds() {
		Set<String> portletIds = new HashSet<String>();

		for (CompanyStatistics containerStatistics :
			_companyStatisticsByWebId.values()) {

			portletIds.addAll(containerStatistics.getPortletIds());
		}

		return portletIds;
	}

	public void processDataSample(
		PortletRequestDataSample portletRequestDataSample)
		throws MonitoringException {
		long companyId = portletRequestDataSample.getCompanyId();

		CompanyStatistics companyStatistics = _companyStatisticsByCompanyId.get(
			companyId);

		if (companyStatistics == null) {
			try {
				Company company = _companyLocalService.getCompany(companyId);

				companyStatistics = register(company.getWebId());
			}
			catch (Exception e) {
				throw new IllegalStateException(
					"Unable to get company with company id " + companyId, e);
			}
		}

		companyStatistics.processDataSample(portletRequestDataSample);
	}

	public synchronized CompanyStatistics register(String webId) {
		CompanyStatistics companyStatistics = new CompanyStatistics(
			_companyLocalService, webId);

		_companyStatisticsByCompanyId.put(
			companyStatistics.getCompanyId(), companyStatistics);
		_companyStatisticsByWebId.put(webId, companyStatistics);

		return companyStatistics;
	}

	public synchronized void unregister(String webId) {
		CompanyStatistics companyStatistics = _companyStatisticsByWebId.remove(
			webId);

		if (companyStatistics != null) {
			_companyStatisticsByCompanyId.remove(
				companyStatistics.getCompanyId());
		}
	}

	public void reset() {
		for (long companyId : _companyStatisticsByCompanyId.keySet()) {
			reset(companyId);
		}
	}

	public void reset(long companyId) {
		CompanyStatistics companyStatistics =
			_companyStatisticsByCompanyId.get(companyId);

		if (companyStatistics == null) {
			return;
		}

		companyStatistics.reset();
	}

	public void reset(String companyWebId) {
		CompanyStatistics companyStatistics =
			_companyStatisticsByWebId.get(companyWebId);

		if (companyStatistics == null) {
			return;
		}

		companyStatistics.reset();
	}

	private CompanyLocalService _companyLocalService;
	private Map<Long, CompanyStatistics> _companyStatisticsByCompanyId;
	private Map<String, CompanyStatistics> _companyStatisticsByWebId;

}