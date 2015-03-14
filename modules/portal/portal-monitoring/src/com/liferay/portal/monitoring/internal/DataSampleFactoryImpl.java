/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.monitoring.internal;

import com.liferay.portal.kernel.monitoring.DataSample;
import com.liferay.portal.kernel.monitoring.DataSampleFactory;
import com.liferay.portal.kernel.monitoring.MethodSignature;
import com.liferay.portal.kernel.monitoring.PortletRequestType;
import com.liferay.portal.monitoring.internal.statistics.portal.PortalRequestDataSample;
import com.liferay.portal.monitoring.internal.statistics.portlet.PortletRequestDataSample;
import com.liferay.portal.monitoring.internal.statistics.service.ServiceRequestDataSample;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = DataSampleFactory.class)
public class DataSampleFactoryImpl implements DataSampleFactory {

	@Override
	public DataSample createPortalRequestDataSample(
		long companyId, String remoteUser, String requestURI,
		String requestURL) {

		return new PortalRequestDataSample(
			companyId, remoteUser, requestURI, requestURL);
	}

	@Override
	public DataSample createPortletRequestDataSample(
		PortletRequestType requestType, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		return new PortletRequestDataSample(
			requestType, portletRequest, portletResponse);
	}

	@Override
	public DataSample createServiceRequestDataSample(
		MethodSignature methodSignature) {

		return new ServiceRequestDataSample(methodSignature);
	}

}