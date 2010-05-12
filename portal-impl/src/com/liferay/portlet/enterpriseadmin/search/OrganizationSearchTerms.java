/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.enterpriseadmin.search;

import com.liferay.portal.kernel.dao.search.DAOParamUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * <a href="OrganizationSearchTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class OrganizationSearchTerms extends OrganizationDisplayTerms {

	public OrganizationSearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		city = DAOParamUtil.getLike(portletRequest, CITY);
		countryId = ParamUtil.getLong(portletRequest, COUNTRY_ID);
		name = DAOParamUtil.getLike(portletRequest, NAME);
		parentOrganizationId = ParamUtil.getLong(
			portletRequest, PARENT_ORGANIZATION_ID);
		regionId = ParamUtil.getLong(portletRequest, REGION_ID);
		street = DAOParamUtil.getLike(portletRequest, STREET);
		type = DAOParamUtil.getString(portletRequest, TYPE);
		zip = DAOParamUtil.getLike(portletRequest, ZIP);
	}

	public Long getCountryIdObj() {
		if (countryId == 0) {
			return null;
		}
		else {
			return new Long(countryId);
		}
	}

	public Long getRegionIdObj() {
		if (regionId == 0) {
			return null;
		}
		else {
			return new Long(regionId);
		}
	}

}