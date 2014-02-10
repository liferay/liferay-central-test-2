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
public class PortletInstancePortletSettingsTest
	extends GroupPortletSettingsTest {

	@Before
	@Override
	public void setUp() throws Exception {
		portletSettings = new PortletInstancePortletSettings(getSettings());
	}

	@Test
	public void testGetDefaultFromSiteDefaults() throws Exception {
		setSiteDefaults(_TEST_SITE_SETTING_NAME, _TEST_SITE_SETTING_VALUE);

		String value = portletSettings.getValue(_TEST_SITE_SETTING_NAME, null);

		Assert.assertEquals(_TEST_SITE_SETTING_VALUE, value);
	}

	protected void setSiteDefaults(String name, String value) throws Exception {
		PortletPreferences siteDefaultSettings = new PortletPreferencesImpl();

		siteDefaultSettings.setValue(name, value);

		PortletInstancePortletSettings instancePortletSettings =
			(PortletInstancePortletSettings)portletSettings;

		instancePortletSettings.setGroupPortletPreferences(siteDefaultSettings);
	}

	private static final String _TEST_SITE_SETTING_NAME = "testSiteSettingName";

	private static final String _TEST_SITE_SETTING_VALUE =
		"testSiteSettingValue";

}