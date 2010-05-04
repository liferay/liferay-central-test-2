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

package com.liferay.portalweb.portal.controlpanel.settings;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="SettingsTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SettingsTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(EditGeneralSettingsTest.class);
		testSuite.addTestSuite(AssertAuthenticationCASTest.class);
		testSuite.addTestSuite(AssertAuthenticationGeneralTest.class);
		testSuite.addTestSuite(AssertAuthenticationLDAPTest.class);
		testSuite.addTestSuite(AssertAuthenticationNTLMTest.class);
		testSuite.addTestSuite(AssertAuthenticationOpenIDTest.class);
		testSuite.addTestSuite(AssertAuthenticationOpenSSOTest.class);
		testSuite.addTestSuite(AssertAuthenticationSiteMinderTest.class);
		testSuite.addTestSuite(AssertUsersFieldsSettingsTest.class);
		testSuite.addTestSuite(EnterReservedUserSNTest.class);
		testSuite.addTestSuite(AddReservedUserSNTest.class);
		testSuite.addTestSuite(EnterReservedUserEmailTest.class);
		testSuite.addTestSuite(AddReservedUserEmailTest.class);
		testSuite.addTestSuite(AssertUsersDefaultAssociationsTest.class);
		testSuite.addTestSuite(AssertMailHostNamesTest.class);
		testSuite.addTestSuite(AssertEmailNotificationSenderTest.class);
		testSuite.addTestSuite(
			AssertEmailNotificationAccountCreationTest.class);
		testSuite.addTestSuite(
			AssertEmailNotificationPasswordChangedTest.class);
		testSuite.addTestSuite(AddSettingsAddressTest.class);
		testSuite.addTestSuite(AddSettingsAddress2Test.class);
		testSuite.addTestSuite(AddSettingsPhoneNumberTest.class);
		testSuite.addTestSuite(AddSettingsPhoneNumber2Test.class);
		testSuite.addTestSuite(AddSettingsEmailAddressTest.class);
		testSuite.addTestSuite(AddSettingsEmailAddress2Test.class);
		testSuite.addTestSuite(AddSettingsWebsitesTest.class);
		testSuite.addTestSuite(AddSettingsWebsites2Test.class);
		testSuite.addTestSuite(AddNullSettingsStreetAddressTest.class);
		testSuite.addTestSuite(AddNullSettingsCityAddressTest.class);
		testSuite.addTestSuite(AddNullSettingsZipAddressTest.class);
		testSuite.addTestSuite(AddInvalidSettingsPhoneNumberTest.class);
		testSuite.addTestSuite(AddNullSettingsPhoneNumberTest.class);
		testSuite.addTestSuite(AddInvalidSettingsEmailTest.class);
		testSuite.addTestSuite(AddNullSettingsEmailTest.class);
		testSuite.addTestSuite(AddInvalidSettingsURLTest.class);
		testSuite.addTestSuite(AddNullSettingsURLTest.class);
		testSuite.addTestSuite(EditTimeZoneTest.class);
		testSuite.addTestSuite(AssertGoogleAppsTest.class);
		testSuite.addTestSuite(MonitoringTest.class);
		testSuite.addTestSuite(PluginsConfigurationTest.class);
		testSuite.addTestSuite(TearDownTest.class);

		return testSuite;
	}

}