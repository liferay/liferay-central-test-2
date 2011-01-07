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

package com.liferay.portalweb.portal.controlpanel.communities.community;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.activatestagingcommunity.ActivateStagingCommunityTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.addcommunity.AddCommunityTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.addcommunitynamecomma.AddCommunityNameCommaTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.addcommunitynameduplicate.AddCommunityNameDuplicateTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.addcommunitynamenull.AddCommunityNameNullTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.addcommunitynamenumber.AddCommunityNameNumberTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.addcommunitynamestar.AddCommunityNameStarTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.assignmemberscommunity.AssignMembersCommunityTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.autoapprovependingmembers.AutoApprovePendingMembersTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.deactivatestagingcommunity.DeactivateStagingCommunityTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.deactivatestagingcommunitynavigatingstaging.DeactivateStagingCommunityNavigatingStagingTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.deletecommunity.DeleteCommunityTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.deletecommunityassignmembers.DeleteCommunityAssignMembersTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.deletecommunityguest.DeleteCommunityGuestTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.editcommunity.EditCommunityTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.joincommunityinactive.JoinCommunityInactiveTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.joincommunityopen.JoinCommunityOpenTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.joincommunityprivate.JoinCommunityPrivateTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.joincommunityrestricted.JoinCommunityRestrictedTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.leavecommunity.LeaveCommunityTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.removememberscommunity.RemoveMembersCommunityTests;
import com.liferay.portalweb.portal.controlpanel.communities.community.searchcommunity.SearchCommunityTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class CommunityTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ActivateStagingCommunityTests.suite());
		testSuite.addTest(AddCommunityTests.suite());
		testSuite.addTest(AddCommunityNameCommaTests.suite());
		testSuite.addTest(AddCommunityNameDuplicateTests.suite());
		testSuite.addTest(AddCommunityNameNullTests.suite());
		testSuite.addTest(AddCommunityNameNumberTests.suite());
		testSuite.addTest(AddCommunityNameStarTests.suite());
		testSuite.addTest(AssignMembersCommunityTests.suite());
		testSuite.addTest(AutoApprovePendingMembersTests.suite());
		testSuite.addTest(DeactivateStagingCommunityTests.suite());
		testSuite.addTest(
			DeactivateStagingCommunityNavigatingStagingTests.suite());
		testSuite.addTest(DeleteCommunityTests.suite());
		testSuite.addTest(DeleteCommunityAssignMembersTests.suite());
		testSuite.addTest(DeleteCommunityGuestTests.suite());
		testSuite.addTest(EditCommunityTests.suite());
		testSuite.addTest(JoinCommunityInactiveTests.suite());
		testSuite.addTest(JoinCommunityOpenTests.suite());
		testSuite.addTest(JoinCommunityPrivateTests.suite());
		testSuite.addTest(JoinCommunityRestrictedTests.suite());
		testSuite.addTest(LeaveCommunityTests.suite());
		testSuite.addTest(RemoveMembersCommunityTests.suite());
		testSuite.addTest(SearchCommunityTests.suite());

		return testSuite;
	}

}