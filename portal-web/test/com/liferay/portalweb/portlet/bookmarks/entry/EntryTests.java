/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portalweb.portlet.bookmarks.entry;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.bookmarks.entry.addfolderentry.AddFolderEntryTests;
import com.liferay.portalweb.portlet.bookmarks.entry.addfolderentrymultiple.AddFolderEntryMultipleTests;
import com.liferay.portalweb.portlet.bookmarks.entry.addfolderentrynamenull.AddFolderEntryNameNullTests;
import com.liferay.portalweb.portlet.bookmarks.entry.addfolderentryurlinvalid.AddFolderEntryURLInvalidTests;
import com.liferay.portalweb.portlet.bookmarks.entry.addfolderentryurlnull.AddFolderEntryURLNullTests;
import com.liferay.portalweb.portlet.bookmarks.entry.addsubfolderentry.AddSubfolderEntryTests;
import com.liferay.portalweb.portlet.bookmarks.entry.deletefolderentry.DeleteFolderEntryTests;
import com.liferay.portalweb.portlet.bookmarks.entry.editfolderentry.EditFolderEntryTests;
import com.liferay.portalweb.portlet.bookmarks.entry.movefolderentrytofolder.MoveFolderEntryToFolderTests;
import com.liferay.portalweb.portlet.bookmarks.entry.movefolderentrytosubfolder.MoveFolderEntryToSubfolderTests;
import com.liferay.portalweb.portlet.bookmarks.entry.movesubfolderentrytofolder.MoveSubfolderEntryToFolderTests;
import com.liferay.portalweb.portlet.bookmarks.entry.movesubfolderentrytosubfolder.MoveSubfolderEntryToSubfolderTests;
import com.liferay.portalweb.portlet.bookmarks.entry.searchfolderentry.SearchFolderEntryTests;
import com.liferay.portalweb.portlet.bookmarks.entry.viewfolderentrymyentries.ViewFolderEntryMyEntriesTests;
import com.liferay.portalweb.portlet.bookmarks.entry.viewfolderentryrecententries.ViewFolderEntryRecentEntriesTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="EntryTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EntryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddFolderEntryTests.suite());
		testSuite.addTest(AddFolderEntryMultipleTests.suite());
		testSuite.addTest(AddFolderEntryNameNullTests.suite());
		testSuite.addTest(AddFolderEntryURLInvalidTests.suite());
		testSuite.addTest(AddFolderEntryURLNullTests.suite());
		testSuite.addTest(AddSubfolderEntryTests.suite());
		testSuite.addTest(DeleteFolderEntryTests.suite());
		testSuite.addTest(EditFolderEntryTests.suite());
		testSuite.addTest(MoveFolderEntryToFolderTests.suite());
		testSuite.addTest(MoveFolderEntryToSubfolderTests.suite());
		testSuite.addTest(MoveSubfolderEntryToFolderTests.suite());
		testSuite.addTest(MoveSubfolderEntryToSubfolderTests.suite());
		testSuite.addTest(SearchFolderEntryTests.suite());
		testSuite.addTest(ViewFolderEntryMyEntriesTests.suite());
		testSuite.addTest(ViewFolderEntryRecentEntriesTests.suite());

		return testSuite;
	}

}