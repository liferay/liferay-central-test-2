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

package com.liferay.portalweb.portlet.breadcrumb.lar.importlar;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.breadcrumb.portlet.addportletbreadcrumb.AddPageBreadcrumbTest;
import com.liferay.portalweb.portlet.breadcrumb.portlet.addportletbreadcrumb.AddPortletBreadcrumbTest;
import com.liferay.portalweb.portlet.breadcrumb.portlet.configureportletdisplaystyle1.AssertConfigurePortletDisplayStyle1Test;
import com.liferay.portalweb.portlet.breadcrumb.portlet.configureportletdisplaystyle2.AssertConfigurePortletDisplayStyle2Test;
import com.liferay.portalweb.portlet.breadcrumb.portlet.configureportletdisplaystyle2.ConfigurePortletDisplayStyle2Test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ImportLARTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageBreadcrumbTest.class);
		testSuite.addTestSuite(AddPortletBreadcrumbTest.class);
		testSuite.addTestSuite(AssertConfigurePortletDisplayStyle1Test.class);
		testSuite.addTestSuite(ConfigurePortletDisplayStyle2Test.class);
		testSuite.addTestSuite(AssertConfigurePortletDisplayStyle2Test.class);
		testSuite.addTestSuite(ImportLARTest.class);
		testSuite.addTestSuite(AssertImportLARTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}