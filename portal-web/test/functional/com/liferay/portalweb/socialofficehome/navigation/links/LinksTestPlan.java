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

package com.liferay.portalweb.socialofficehome.navigation.links;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.navigation.links.viewlinkcontactscenter.ViewLinkContactsCenterTests;
import com.liferay.portalweb.socialofficehome.navigation.links.viewlinkhome.ViewLinkHomeTests;
import com.liferay.portalweb.socialofficehome.navigation.links.viewlinkmicroblogs.ViewLinkMicroblogsTests;
import com.liferay.portalweb.socialofficehome.navigation.links.viewlinkprivatemessaging.ViewLinkPrivateMessagingTests;
import com.liferay.portalweb.socialofficehome.navigation.links.viewlinktasks.ViewLinkTasksTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class LinksTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ViewLinkContactsCenterTests.suite());
		testSuite.addTest(ViewLinkHomeTests.suite());
		testSuite.addTest(ViewLinkMicroblogsTests.suite());
		testSuite.addTest(ViewLinkPrivateMessagingTests.suite());
		testSuite.addTest(ViewLinkTasksTests.suite());

		return testSuite;
	}

}