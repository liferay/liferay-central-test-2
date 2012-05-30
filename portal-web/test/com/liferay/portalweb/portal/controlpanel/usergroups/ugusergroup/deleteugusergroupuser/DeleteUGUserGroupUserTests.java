/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.deleteugusergroupuser;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.usergroups.portlet.viewportletugcp.ViewPortletUGCPTest;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.addugusergroup.AddUGUserGroupTest;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.addugusergroup.TearDownUGUserGroupTest;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.assignmemberugusergroupuser.AssignMemberUGUserGroupUserTest;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.assignmemberugusergroupuser.RemoveMemberUGUserGroupUserTest;
import com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.assignmemberugusergroupuser.TearDownUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUserTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteUGUserGroupUserTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(ViewPortletUGCPTest.class);
		testSuite.addTestSuite(AddUGUserGroupTest.class);
		testSuite.addTestSuite(AssignMemberUGUserGroupUserTest.class);
		testSuite.addTestSuite(DeleteUGUserGroupUserTest.class);
		testSuite.addTestSuite(RemoveMemberUGUserGroupUserTest.class);
		testSuite.addTestSuite(TearDownUGUserGroupTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);

		return testSuite;
	}
}