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
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.address.Address525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcements.Announcements525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcementsdelivery.AnnouncementsDelivery525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.bookmarks.Bookmarks525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.calendar.Calendar525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.community.Community525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.documentlibrary.DocumentLibrary525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.expando.Expando525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.Groups525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.login.LoginTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.organizations.Organizations525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.portletpermissions.PortletPermissions525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.shopping.Shopping525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.social.Social525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingcommunity.StagingCommunity525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingorganization.StagingOrganization525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.tags.Tags525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.webcontent.WebContent525LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.wiki.Wiki525LatestTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSampleData525LatestTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(Address525LatestTests.suite());
		testSuite.addTest(Announcements525LatestTests.suite());
		testSuite.addTest(AnnouncementsDelivery525LatestTests.suite());
		testSuite.addTest(Bookmarks525LatestTests.suite());
		testSuite.addTest(Calendar525LatestTests.suite());
		testSuite.addTest(Community525LatestTests.suite());
		testSuite.addTest(DocumentLibrary525LatestTests.suite());
		testSuite.addTest(Expando525LatestTests.suite());
		testSuite.addTest(Groups525LatestTests.suite());
		testSuite.addTest(Organizations525LatestTests.suite());
		testSuite.addTest(PortletPermissions525LatestTests.suite());
		testSuite.addTest(Shopping525LatestTests.suite());
		testSuite.addTest(Social525LatestTests.suite());
		testSuite.addTest(StagingCommunity525LatestTests.suite());
		testSuite.addTest(StagingOrganization525LatestTests.suite());
		testSuite.addTest(Tags525LatestTests.suite());
		testSuite.addTest(WebContent525LatestTests.suite());
		testSuite.addTest(Wiki525LatestTests.suite());

		return testSuite;
	}

}