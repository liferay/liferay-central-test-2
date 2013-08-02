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

package com.liferay.portalweb.socialoffice.users.sites;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialoffice.users.sites.assignmembersousersites.AssignMemberSOUserSitesTests;
import com.liferay.portalweb.socialoffice.users.sites.assignrolesosites.AssignRoleSOSitesTests;
import com.liferay.portalweb.socialoffice.users.sites.viewremoverolesosites.ViewRemoveRoleSOSitesTests;
import com.liferay.portalweb.socialoffice.users.sites.viewremoverolesososites.ViewRemoveRoleSOSOSitesTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SitesTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AssignMemberSOUserSitesTests.suite());
		testSuite.addTest(AssignRoleSOSitesTests.suite());
		testSuite.addTest(ViewRemoveRoleSOSitesTests.suite());
		testSuite.addTest(ViewRemoveRoleSOSOSitesTests.suite());

		return testSuite;
	}

}