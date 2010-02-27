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

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * <a href="OrganizationDisplayTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class OrganizationDisplayTerms extends DisplayTerms {

	public static final String TYPE = "type";

	public static final String NAME = "name";

	public static final String STREET = "street";

	public static final String CITY = "city";

	public static final String ZIP = "zip";

	public static final String COUNTRY_ID = "countryId";

	public static final String REGION_ID = "regionId";

	public static final String PARENT_ORGANIZATION_ID = "parentOrganizationId";

	public OrganizationDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		type = ParamUtil.getString(portletRequest, TYPE);
		name = ParamUtil.getString(portletRequest, NAME);
		street = ParamUtil.getString(portletRequest, STREET);
		city = ParamUtil.getString(portletRequest, CITY);
		zip = ParamUtil.getString(portletRequest, ZIP);
		regionId = ParamUtil.getLong(portletRequest, REGION_ID);
		countryId = ParamUtil.getLong(portletRequest, COUNTRY_ID);
		parentOrganizationId = ParamUtil.getLong(
			portletRequest, PARENT_ORGANIZATION_ID);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getZip() {
		return zip;
	}

	public long getRegionId() {
		return regionId;
	}

	public long getCountryId() {
		return countryId;
	}

	public long getParentOrganizationId() {
		return parentOrganizationId;
	}

	protected String type;
	protected String name;
	protected String street;
	protected String city;
	protected String zip;
	protected long regionId;
	protected long countryId;
	protected long parentOrganizationId;

}