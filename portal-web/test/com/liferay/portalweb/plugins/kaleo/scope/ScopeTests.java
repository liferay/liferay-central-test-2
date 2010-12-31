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

package com.liferay.portalweb.plugins.kaleo.scope;

import com.liferay.portalweb.plugins.kaleo.scope.addwebcontentscopecommunity.AddWebContentScopeCommunityTests;
import com.liferay.portalweb.plugins.kaleo.scope.addwebcontentscopeglobal.AddWebContentScopeGlobalTests;
import com.liferay.portalweb.plugins.kaleo.scope.addwebcontentscopeguest.AddWebContentScopeGuestTests;
import com.liferay.portalweb.plugins.kaleo.scope.addwebcontentscopemycommunity.AddWebContentScopeMyCommunityTests;
import com.liferay.portalweb.plugins.kaleo.scope.addwebcontentscopeorganization.AddWebContentScopeOrganizationTests;
import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ScopeTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWebContentScopeCommunityTests.suite());
		testSuite.addTest(AddWebContentScopeGlobalTests.suite());
		testSuite.addTest(AddWebContentScopeGuestTests.suite());
		testSuite.addTest(AddWebContentScopeMyCommunityTests.suite());
		testSuite.addTest(AddWebContentScopeOrganizationTests.suite());

		return testSuite;
	}

}