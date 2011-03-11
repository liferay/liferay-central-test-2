/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.address.Address528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcements.Announcements528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcementsdelivery.AnnouncementsDelivery528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.calendar.Calendar528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.community.Community528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.documentlibrary.DocumentLibrary528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.expando.Expando528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.Groups528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.login.LoginTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.organizations.Organizations528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.portletpermissions.PortletPermissions528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.shopping.Shopping528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.social.Social528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingcommunity.StagingCommunity528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingorganization.StagingOrganization528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.tags.Tags528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.webcontent.WebContent528LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.wiki.Wiki528LatestTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSampleData528LatestTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(Address528LatestTests.suite());
		testSuite.addTest(Announcements528LatestTests.suite());
		testSuite.addTest(AnnouncementsDelivery528LatestTests.suite());
		testSuite.addTest(Calendar528LatestTests.suite());
		testSuite.addTest(Community528LatestTests.suite());
		testSuite.addTest(DocumentLibrary528LatestTests.suite());
		testSuite.addTest(Expando528LatestTests.suite());
		testSuite.addTest(Groups528LatestTests.suite());
		testSuite.addTest(Organizations528LatestTests.suite());
		testSuite.addTest(PortletPermissions528LatestTests.suite());
		testSuite.addTest(Shopping528LatestTests.suite());
		testSuite.addTest(Social528LatestTests.suite());
		testSuite.addTest(StagingCommunity528LatestTests.suite());
		testSuite.addTest(StagingOrganization528LatestTests.suite());
		testSuite.addTest(Tags528LatestTests.suite());
		testSuite.addTest(WebContent528LatestTests.suite());
		testSuite.addTest(Wiki528LatestTests.suite());

		return testSuite;
	}

}