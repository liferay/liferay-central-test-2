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

package com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.expando.Expando512LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.Groups512LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.login.LoginTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.organizations.Organizations512LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.portletpermissions.PortletPermissions512LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.social.Social512LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.tags.Tags512LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.webcontent.WebContent512LatestTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="ViewSampleData512LatestTests.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class ViewSampleData512LatestTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(Expando512LatestTests.suite());
		testSuite.addTest(Groups512LatestTests.suite());
		testSuite.addTest(Organizations512LatestTests.suite());
		testSuite.addTest(PortletPermissions512LatestTests.suite());
		testSuite.addTest(Social512LatestTests.suite());
		testSuite.addTest(Tags512LatestTests.suite());
		testSuite.addTest(WebContent512LatestTests.suite());

		return testSuite;
	}

}