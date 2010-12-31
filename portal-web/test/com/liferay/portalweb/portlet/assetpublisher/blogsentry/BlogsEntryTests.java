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

package com.liferay.portalweb.portlet.assetpublisher.blogsentry;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.assetpublisher.blogsentry.addblogsentry.AddBlogsEntryTests;
import com.liferay.portalweb.portlet.assetpublisher.blogsentry.rateblogsentry.RateBlogsEntryTests;
import com.liferay.portalweb.portlet.assetpublisher.blogsentry.removeblogsentry.RemoveBlogsEntryTests;
import com.liferay.portalweb.portlet.assetpublisher.blogsentry.selectblogsentry.SelectBlogsEntryTests;
import com.liferay.portalweb.portlet.assetpublisher.blogsentry.viewblogsentryabstracts.ViewBlogsEntryAbstractsTests;
import com.liferay.portalweb.portlet.assetpublisher.blogsentry.viewblogsentrydynamicassettypeblogsentry.ViewBlogsEntryDynamicAssetTypeBlogsEntryTests;
import com.liferay.portalweb.portlet.assetpublisher.blogsentry.viewblogsentryfullcontent.ViewBlogsEntryFullContentTests;
import com.liferay.portalweb.portlet.assetpublisher.blogsentry.viewblogsentrytable.ViewBlogsEntryTableTests;
import com.liferay.portalweb.portlet.assetpublisher.blogsentry.viewblogsentrytitlelist.ViewBlogsEntryTitleListTests;
import com.liferay.portalweb.portlet.assetpublisher.blogsentry.viewcountblogsentry.ViewCountBlogsEntryTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsEntryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddBlogsEntryTests.suite());
		testSuite.addTest(RateBlogsEntryTests.suite());
		testSuite.addTest(RemoveBlogsEntryTests.suite());
		testSuite.addTest(SelectBlogsEntryTests.suite());
		testSuite.addTest(ViewBlogsEntryAbstractsTests.suite());
		testSuite.addTest(
			ViewBlogsEntryDynamicAssetTypeBlogsEntryTests.suite());
		testSuite.addTest(ViewBlogsEntryFullContentTests.suite());
		testSuite.addTest(ViewBlogsEntryTableTests.suite());
		testSuite.addTest(ViewBlogsEntryTitleListTests.suite());
		testSuite.addTest(ViewCountBlogsEntryTests.suite());

		return testSuite;
	}

}