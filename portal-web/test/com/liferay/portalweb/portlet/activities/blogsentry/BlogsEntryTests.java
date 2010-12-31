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

package com.liferay.portalweb.portlet.activities.blogsentry;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.activities.blogsentry.viewblogsentry.ViewBlogsEntryTests;
import com.liferay.portalweb.portlet.activities.blogsentry.viewblogsentryhtmlescapecharacters.ViewBlogsEntryHTMLEscapeCharactersTests;
import com.liferay.portalweb.portlet.activities.blogsentry.viewblogsentryxss.ViewBlogsEntryXSSTests;
import com.liferay.portalweb.portlet.activities.blogsentry.viewblogsentryxssopen.ViewBlogsEntryXSSOpenTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsEntryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ViewBlogsEntryTests.suite());
		testSuite.addTest(ViewBlogsEntryHTMLEscapeCharactersTests.suite());
		testSuite.addTest(ViewBlogsEntryXSSTests.suite());
		testSuite.addTest(ViewBlogsEntryXSSOpenTests.suite());

		return testSuite;
	}

}