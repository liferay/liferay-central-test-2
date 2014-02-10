/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jorge Ferrer
 */
public class GroupPortletSettingsTest extends CompanyPortletSettingsTest {

	@Before
	@Override
	public void setUp() throws Exception {
		portletSettings = new GroupPortletSettings(getPortletPreferences());
	}

	@Test
	public void testGetValueFromCompanyPortletPreferences() throws Exception {
		setCompanyPortletPreferences(
			_COMPANY_PORTLET_PREFERENCES_NAME,
			_COMPANY_PORTLET_PREFERENCES_VALUE);

		String value = portletSettings.getValue(
			_COMPANY_PORTLET_PREFERENCES_NAME, null);

		Assert.assertEquals(_COMPANY_PORTLET_PREFERENCES_VALUE, value);
	}

	protected void setCompanyPortletPreferences(String name, String value)
		throws Exception {

		GroupPortletSettings groupPortletSettings =
			(GroupPortletSettings)portletSettings;

		PortletPreferences companyPortletPreferences =
			new PortletPreferencesImpl();

		companyPortletPreferences.setValue(name, value);

		groupPortletSettings.setCompanyPortletPreferences(
			companyPortletPreferences);
	}

	private static final String _COMPANY_PORTLET_PREFERENCES_NAME =
		"companyPortletPreferencesName";

	private static final String _COMPANY_PORTLET_PREFERENCES_VALUE =
		"companyPortletPreferencesValue";

}