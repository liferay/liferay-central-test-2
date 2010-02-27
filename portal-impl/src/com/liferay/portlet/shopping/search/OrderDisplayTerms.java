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

package com.liferay.portlet.shopping.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * <a href="OrderDisplayTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class OrderDisplayTerms extends DisplayTerms {

	public static final String NUMBER = "number";

	public static final String STATUS = "status";

	public static final String FIRST_NAME = "firstName";

	public static final String LAST_NAME = "lastName";

	public static final String EMAIL_ADDRESS = "emailAddress";

	public OrderDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		number = ParamUtil.getString(portletRequest, NUMBER);
		status = ParamUtil.getString(portletRequest, STATUS);
		firstName = ParamUtil.getString(portletRequest, FIRST_NAME);
		lastName = ParamUtil.getString(portletRequest, LAST_NAME);
		emailAddress = ParamUtil.getString(portletRequest, EMAIL_ADDRESS);
	}

	public String getNumber() {
		return number;
	}

	public String getStatus() {
		return status;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	protected String number;
	protected String status;
	protected String firstName;
	protected String lastName;
	protected String emailAddress;

}