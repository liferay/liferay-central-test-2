/*
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

package com.liferay.portal.monitoring.statistics.portletcontainer;

import com.liferay.portal.model.Company;
import com.liferay.portal.monitoring.statistics.DataSampleProcessor;
import com.liferay.portal.monitoring.statistics.portlet.PortletRequestDataSample;
import com.liferay.portal.service.CompanyLocalService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="GlobalPortletContainerStatistics.java.html"><b><i>View
 * Source</i></b></a>
 * <p/>
 * Captures portlet statistics across all portal instances
 *
 * @author Michael C. Han
 */
public class PortletContainersStatistics
	implements DataSampleProcessor<PortletRequestDataSample> {

	public PortletContainersStatistics(CompanyLocalService companyLocal) {
		_companyLocalService = companyLocal;

		_companyIdToContainer =
			new ConcurrentHashMap<Long, CompanyPortletContainerStatistics>();

		_webIdToContainer =
			new ConcurrentHashMap<String, CompanyPortletContainerStatistics>();
	}

	public CompanyPortletContainerStatistics register(String webId) {
		CompanyPortletContainerStatistics companyStatistics =
			new CompanyPortletContainerStatistics(_companyLocalService, webId);

		_companyIdToContainer.put(
			companyStatistics.getCompanyId(), companyStatistics);

		_webIdToContainer.put(webId, companyStatistics);

		return companyStatistics;
	}

	public void unregister(String webId) {
		CompanyPortletContainerStatistics statistics =
			_webIdToContainer.remove(webId);

		if (statistics != null) {
			_companyIdToContainer.remove(statistics.getCompanyId());
		}
	}

	public void processDataSample(PortletRequestDataSample dataSample) {
		long companyId = dataSample.getCompanyId();

		CompanyPortletContainerStatistics statistics =
			_companyIdToContainer.get(companyId);

		if (statistics == null) {
			try {
				Company company = _companyLocalService.getCompany(companyId);
				statistics = register(company.getWebId());
			}
			catch (Exception e) {
				throw new IllegalStateException(
					"Unable to get company defails for: " + companyId);
			}
		}
		
		statistics.processDataSample(dataSample);
	}

	private CompanyLocalService _companyLocalService;
	private Map<Long, CompanyPortletContainerStatistics> _companyIdToContainer;
	private Map<String, CompanyPortletContainerStatistics> _webIdToContainer;

}
