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

package com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.groupsorgs.GroupsOrgsTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.groupsroles.GroupsRolesTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.groupsusergroups.GroupsUserGroupsTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.pagelayout.PageLayoutTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.pagescope.PageScopeTests;
import com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.groups.usergroup.UserGroupTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class Groups6120LatestTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(GroupsOrgsTests.suite());
		testSuite.addTest(GroupsRolesTests.suite());
		testSuite.addTest(GroupsUserGroupsTests.suite());
		testSuite.addTest(PageLayoutTests.suite());
		testSuite.addTest(PageScopeTests.suite());
		testSuite.addTest(UserGroupTests.suite());

		return testSuite;
	}

}