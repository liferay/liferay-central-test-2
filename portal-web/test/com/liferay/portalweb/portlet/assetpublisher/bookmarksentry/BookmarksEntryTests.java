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

package com.liferay.portalweb.portlet.assetpublisher.bookmarksentry;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.assetpublisher.bookmarksentry.addbookmarksentry.AddBookmarksEntryTests;
import com.liferay.portalweb.portlet.assetpublisher.bookmarksentry.removebookmarksentry.RemoveBookmarksEntryTests;
import com.liferay.portalweb.portlet.assetpublisher.bookmarksentry.selectbookmarksentry.SelectBookmarksEntryTests;
import com.liferay.portalweb.portlet.assetpublisher.bookmarksentry.viewbookmarksentryabstracts.ViewBookmarksEntryAbstractsTests;
import com.liferay.portalweb.portlet.assetpublisher.bookmarksentry.viewbookmarksentrydynamicassettypebookmarksentry.ViewBookmarksEntryDynamicAssetTypeBookmarksEntryTests;
import com.liferay.portalweb.portlet.assetpublisher.bookmarksentry.viewbookmarksentryfullcontent.ViewBookmarksEntryFullContentTests;
import com.liferay.portalweb.portlet.assetpublisher.bookmarksentry.viewbookmarksentrytable.ViewBookmarksEntryTableTests;
import com.liferay.portalweb.portlet.assetpublisher.bookmarksentry.viewbookmarksentrytitlelist.ViewBookmarksEntryTitleListTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksEntryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddBookmarksEntryTests.suite());
		testSuite.addTest(RemoveBookmarksEntryTests.suite());
		testSuite.addTest(SelectBookmarksEntryTests.suite());
		testSuite.addTest(ViewBookmarksEntryAbstractsTests.suite());
		testSuite.addTest(
			ViewBookmarksEntryDynamicAssetTypeBookmarksEntryTests.suite());
		testSuite.addTest(ViewBookmarksEntryFullContentTests.suite());
		testSuite.addTest(ViewBookmarksEntryTableTests.suite());
		testSuite.addTest(ViewBookmarksEntryTitleListTests.suite());

		return testSuite;
	}

}