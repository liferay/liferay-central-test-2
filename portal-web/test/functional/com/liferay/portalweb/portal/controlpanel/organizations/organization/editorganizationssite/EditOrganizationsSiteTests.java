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

package com.liferay.portalweb.portal.controlpanel.organizations.organization.editorganizationssite;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.AddOrganization1Test;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.AddOrganization2Test;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.AddOrganization3Test;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.TearDownOrganizationTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.editorganizationsite.EditOrganization1SiteTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.editorganizationsite.EditOrganization2SiteTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.editorganizationsite.EditOrganization3SiteTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EditOrganizationsSiteTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddOrganization1Test.class);
		testSuite.addTestSuite(AddOrganization2Test.class);
		testSuite.addTestSuite(AddOrganization3Test.class);
		testSuite.addTestSuite(EditOrganization1SiteTest.class);
		testSuite.addTestSuite(EditOrganization2SiteTest.class);
		testSuite.addTestSuite(EditOrganization3SiteTest.class);
		testSuite.addTestSuite(ViewEditOrganizationsSiteTest.class);
		testSuite.addTestSuite(TearDownOrganizationTest.class);

		return testSuite;
	}
}