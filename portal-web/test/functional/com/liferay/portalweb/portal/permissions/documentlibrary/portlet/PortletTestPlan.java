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

package com.liferay.portalweb.portal.permissions.documentlibrary.portlet;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.permissions.documentlibrary.portlet.accessincontrolpanel.AccessInControlPanelTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.portlet.addtopage.AddToPageTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.portlet.configuration.ConfigurationTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.portlet.permissions.PermissionsTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.portlet.view.ViewTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AccessInControlPanelTests.suite());
		testSuite.addTest(AddToPageTests.suite());
		testSuite.addTest(ConfigurationTests.suite());
		testSuite.addTest(PermissionsTests.suite());
		testSuite.addTest(ViewTests.suite());

		return testSuite;
	}

}