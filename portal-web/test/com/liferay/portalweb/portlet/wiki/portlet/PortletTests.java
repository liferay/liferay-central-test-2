/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.wiki.portlet;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.wiki.portlet.addportlet.AddPortletTests;
import com.liferay.portalweb.portlet.wiki.portlet.addportletduplicate.AddPortletDuplicateTests;
import com.liferay.portalweb.portlet.wiki.portlet.configureportletenablecomments.ConfigurePortletEnableCommentsTests;
import com.liferay.portalweb.portlet.wiki.portlet.configureportletenablecommentsratings.ConfigurePortletEnableCommentsRatingsTests;
import com.liferay.portalweb.portlet.wiki.portlet.configureportletvisiblewikis.ConfigurePortletVisibleWikisTests;
import com.liferay.portalweb.portlet.wiki.portlet.configureportletvisiblewikismultiple.ConfigurePortletVisibleWikisMultipleTests;
import com.liferay.portalweb.portlet.wiki.portlet.configureportletvisiblewikisnull.ConfigurePortletVisibleWikisNullTests;
import com.liferay.portalweb.portlet.wiki.portlet.removeportlet.RemovePortletTests;
import com.liferay.portalweb.portlet.wiki.portlet.restoreconfigureportletvisiblewikis.RestoreConfigurePortletVisibleWikisTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="PortletTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletTests.suite());
		testSuite.addTest(AddPortletDuplicateTests.suite());
		testSuite.addTest(ConfigurePortletEnableCommentsTests.suite());
		testSuite.addTest(ConfigurePortletEnableCommentsRatingsTests.suite());
		testSuite.addTest(ConfigurePortletVisibleWikisTests.suite());
		testSuite.addTest(ConfigurePortletVisibleWikisMultipleTests.suite());
		testSuite.addTest(ConfigurePortletVisibleWikisNullTests.suite());
		testSuite.addTest(RemovePortletTests.suite());
		testSuite.addTest(RestoreConfigurePortletVisibleWikisTests.suite());

		return testSuite;
	}

}