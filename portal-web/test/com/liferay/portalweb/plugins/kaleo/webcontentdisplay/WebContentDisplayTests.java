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

package com.liferay.portalweb.plugins.kaleo.webcontentdisplay;

import com.liferay.portalweb.plugins.kaleo.webcontentdisplay.viewwebcontentassignedtome.ViewWebContentAssignedToMeTests;
import com.liferay.portalweb.plugins.kaleo.webcontentdisplay.viewwebcontentassignedtomyroles.ViewWebContentAssignedToMyRolesTests;
import com.liferay.portalweb.plugins.kaleo.webcontentdisplay.viewwebcontentcompleted.ViewWebContentCompletedTests;
import com.liferay.portalweb.plugins.kaleo.webcontentdisplay.viewwebcontentrejected.ViewWebContentRejectedTests;
import com.liferay.portalweb.plugins.kaleo.webcontentdisplay.viewwebcontentresubmitted.ViewWebContentResubmittedTests;
import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WebContentDisplayTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ViewWebContentAssignedToMeTests.suite());
		testSuite.addTest(ViewWebContentAssignedToMyRolesTests.suite());
		testSuite.addTest(ViewWebContentCompletedTests.suite());
		testSuite.addTest(ViewWebContentRejectedTests.suite());
		testSuite.addTest(ViewWebContentResubmittedTests.suite());

		return testSuite;
	}

}