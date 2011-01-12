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

package com.liferay.portalweb.plugins.kaleo.myworkflowtasks.workflowtask;

import com.liferay.portalweb.plugins.kaleo.myworkflowtasks.workflowtask.approvewebcontentscopecommunity.ApproveWebContentScopeCommunityTests;
import com.liferay.portalweb.plugins.kaleo.myworkflowtasks.workflowtask.approvewebcontentscopeglobal.ApproveWebContentScopeGlobalTests;
import com.liferay.portalweb.plugins.kaleo.myworkflowtasks.workflowtask.approvewebcontentscopeguest.ApproveWebContentScopeGuestTests;
import com.liferay.portalweb.plugins.kaleo.myworkflowtasks.workflowtask.approvewebcontentscopemycommunity.ApproveWebContentScopeMyCommunityTests;
import com.liferay.portalweb.plugins.kaleo.myworkflowtasks.workflowtask.approvewebcontentscopeorganization.ApproveWebContentScopeOrganizationTests;
import com.liferay.portalweb.plugins.kaleo.myworkflowtasks.workflowtask.assigntomewebcontentscopecommunity.AssignToMeWebContentScopeCommunityTests;
import com.liferay.portalweb.plugins.kaleo.myworkflowtasks.workflowtask.assigntomewebcontentscopeglobal.AssignToMeWebContentScopeGlobalTests;
import com.liferay.portalweb.plugins.kaleo.myworkflowtasks.workflowtask.assigntomewebcontentscopeguest.AssignToMeWebContentScopeGuestTests;
import com.liferay.portalweb.plugins.kaleo.myworkflowtasks.workflowtask.assigntomewebcontentscopemycommunity.AssignToMeWebContentScopeMyCommunityTests;
import com.liferay.portalweb.plugins.kaleo.myworkflowtasks.workflowtask.assigntomewebcontentscopeorganization.AssignToMeWebContentScopeOrganizationTests;
import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowTaskTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ApproveWebContentScopeCommunityTests.suite());
		testSuite.addTest(ApproveWebContentScopeGlobalTests.suite());
		testSuite.addTest(ApproveWebContentScopeGuestTests.suite());
		testSuite.addTest(ApproveWebContentScopeMyCommunityTests.suite());
		testSuite.addTest(ApproveWebContentScopeOrganizationTests.suite());
		testSuite.addTest(AssignToMeWebContentScopeCommunityTests.suite());
		testSuite.addTest(AssignToMeWebContentScopeGlobalTests.suite());
		testSuite.addTest(AssignToMeWebContentScopeGuestTests.suite());
		testSuite.addTest(AssignToMeWebContentScopeMyCommunityTests.suite());
		testSuite.addTest(AssignToMeWebContentScopeOrganizationTests.suite());

		return testSuite;
	}

}