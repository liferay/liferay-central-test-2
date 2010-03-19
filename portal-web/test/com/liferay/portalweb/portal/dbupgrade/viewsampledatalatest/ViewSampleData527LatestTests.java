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
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.expando.Expando527LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.Groups527LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.login.LoginTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.organizations.Organizations527LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.portletpermissions.PortletPermissions527LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.social.Social527LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.tags.Tags527LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.webcontent.WebContent527LatestTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="ViewSampleData527LatestTests.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class ViewSampleData527LatestTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(Expando527LatestTests.suite());
		testSuite.addTest(Groups527LatestTests.suite());
		testSuite.addTest(Organizations527LatestTests.suite());
		testSuite.addTest(PortletPermissions527LatestTests.suite());
		testSuite.addTest(Social527LatestTests.suite());
		testSuite.addTest(Tags527LatestTests.suite());
		testSuite.addTest(WebContent527LatestTests.suite());

		return testSuite;
	}

}