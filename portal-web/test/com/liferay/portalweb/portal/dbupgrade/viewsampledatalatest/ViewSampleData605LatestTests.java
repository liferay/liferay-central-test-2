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
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.address.Address605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcements.Announcements605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcementsdelivery.AnnouncementsDelivery605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.calendar.Calendar605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.community.Community605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.documentlibrary.DocumentLibrary605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.expando.Expando605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.Groups605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.login.LoginTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.organizations.Organizations605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.portletpermissions.PortletPermissions605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.shopping.Shopping605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.social.Social605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingcommunity.StagingCommunity605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingorganization.StagingOrganization605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.tags.Tags605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.webcontent.WebContent605LatestTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.wiki.Wiki605LatestTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSampleData605LatestTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(Address605LatestTests.suite());
		testSuite.addTest(Announcements605LatestTests.suite());
		testSuite.addTest(AnnouncementsDelivery605LatestTests.suite());
		testSuite.addTest(Calendar605LatestTests.suite());
		testSuite.addTest(Community605LatestTests.suite());
		testSuite.addTest(DocumentLibrary605LatestTests.suite());
		testSuite.addTest(Expando605LatestTests.suite());
		testSuite.addTest(Groups605LatestTests.suite());
		testSuite.addTest(Organizations605LatestTests.suite());
		testSuite.addTest(PortletPermissions605LatestTests.suite());
		testSuite.addTest(Shopping605LatestTests.suite());
		testSuite.addTest(Social605LatestTests.suite());
		testSuite.addTest(StagingCommunity605LatestTests.suite());
		testSuite.addTest(StagingOrganization605LatestTests.suite());
		testSuite.addTest(Tags605LatestTests.suite());
		testSuite.addTest(WebContent605LatestTests.suite());
		testSuite.addTest(Wiki605LatestTests.suite());

		return testSuite;
	}

}