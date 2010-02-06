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

		testSuite.addTestSuite(ControlPanelTest.class);
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
		testSuite.addTestSuite(EndControlPanelTest.class);

		return testSuite;
	}

}