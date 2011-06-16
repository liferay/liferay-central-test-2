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
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.address.Address529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcements.Announcements529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcementsdelivery.AnnouncementsDelivery529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.bookmarks.Bookmarks529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.calendar.Calendar529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.community.Community529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.documentlibrary.DocumentLibrary529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.expando.Expando529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.Groups529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.login.LoginTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.organizations.Organizations529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.portletpermissions.PortletPermissions529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.shopping.Shopping529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.social.Social529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingcommunity.StagingCommunity529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingorganization.StagingOrganization529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.tags.Tags529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.webcontent.WebContent529LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.wiki.Wiki529LatestTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSampleData529LatestTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(Address529LatestTests.suite());
		testSuite.addTest(Announcements529LatestTests.suite());
		testSuite.addTest(AnnouncementsDelivery529LatestTests.suite());
		testSuite.addTest(Bookmarks529LatestTests.suite());
		testSuite.addTest(Calendar529LatestTests.suite());
		testSuite.addTest(Community529LatestTests.suite());
		testSuite.addTest(DocumentLibrary529LatestTests.suite());
		testSuite.addTest(Expando529LatestTests.suite());
		testSuite.addTest(Groups529LatestTests.suite());
		testSuite.addTest(Organizations529LatestTests.suite());
		testSuite.addTest(PortletPermissions529LatestTests.suite());
		testSuite.addTest(Shopping529LatestTests.suite());
		testSuite.addTest(Social529LatestTests.suite());
		testSuite.addTest(StagingCommunity529LatestTests.suite());
		testSuite.addTest(StagingOrganization529LatestTests.suite());
		testSuite.addTest(Tags529LatestTests.suite());
		testSuite.addTest(WebContent529LatestTests.suite());
		testSuite.addTest(Wiki529LatestTests.suite());

		return testSuite;
	}

}