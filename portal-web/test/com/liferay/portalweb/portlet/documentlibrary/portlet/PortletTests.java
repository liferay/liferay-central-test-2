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

package com.liferay.portalweb.portlet.documentlibrary.portlet;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.addportlet.AddPortletTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.addportletduplicate.AddPortletDuplicateTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletdisablecommentratings.ConfigurePortletDisableCommentRatingsTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletdocumentsperpage1.ConfigurePortletDocumentsPerPage1Tests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletenablecommentratings.ConfigurePortletEnableCommentRatingsTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletfoldersperpage1.ConfigurePortletFoldersPerPage1Tests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportlethidedocumentcolumns.ConfigurePortletHideDocumentColumnsTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportlethidedocumentsearch.ConfigurePortletHideDocumentSearchTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportlethidefolderbreadcrumbs.ConfigurePortletHideFolderBreadcrumbsTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportlethidefoldercolumns.ConfigurePortletHideFolderColumnsTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportlethidefoldersearch.ConfigurePortletHideFolderSearchTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportlethidefoldersubfolders.ConfigurePortletHideFolderSubfoldersTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletrootfolderremovefolder.ConfigurePortletRootFolderRemoveFolderTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletrootfolderremovesubfolder.ConfigurePortletRootFolderRemoveSubfolderTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletrootfolderselectfolder.ConfigurePortletRootFolderSelectFolderTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletrootfolderselectsubfolder.ConfigurePortletRootFolderSelectSubfolderTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletshowdocumentcolumns.ConfigurePortletShowDocumentColumnsTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletshowdocumentsearch.ConfigurePortletShowDocumentSearchTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletshowfolderbreadcrumbs.ConfigurePortletShowFolderBreadcrumbsTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletshowfoldercolumns.ConfigurePortletShowFolderColumnsTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletshowfoldersearch.ConfigurePortletShowFolderSearchTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.configureportletshowfoldersubfolders.ConfigurePortletShowFolderSubfoldersTests;
import com.liferay.portalweb.portlet.documentlibrary.portlet.removeportlet.RemovePortletTests;

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
		testSuite.addTest(ConfigurePortletDisableCommentRatingsTests.suite());
		testSuite.addTest(ConfigurePortletDocumentsPerPage1Tests.suite());
		testSuite.addTest(ConfigurePortletEnableCommentRatingsTests.suite());
		testSuite.addTest(ConfigurePortletFoldersPerPage1Tests.suite());
		testSuite.addTest(ConfigurePortletHideDocumentColumnsTests.suite());
		testSuite.addTest(ConfigurePortletHideDocumentSearchTests.suite());
		testSuite.addTest(ConfigurePortletHideFolderBreadcrumbsTests.suite());
		testSuite.addTest(ConfigurePortletHideFolderColumnsTests.suite());
		testSuite.addTest(ConfigurePortletHideFolderSearchTests.suite());
		testSuite.addTest(ConfigurePortletHideFolderSubfoldersTests.suite());
		testSuite.addTest(ConfigurePortletRootFolderRemoveFolderTests.suite());
		testSuite.addTest(
			ConfigurePortletRootFolderRemoveSubfolderTests.suite());
		testSuite.addTest(ConfigurePortletRootFolderSelectFolderTests.suite());
		testSuite.addTest(
			ConfigurePortletRootFolderSelectSubfolderTests.suite());
		testSuite.addTest(ConfigurePortletShowDocumentColumnsTests.suite());
		testSuite.addTest(ConfigurePortletShowDocumentSearchTests.suite());
		testSuite.addTest(ConfigurePortletShowFolderBreadcrumbsTests.suite());
		testSuite.addTest(ConfigurePortletShowFolderColumnsTests.suite());
		testSuite.addTest(ConfigurePortletShowFolderSearchTests.suite());
		testSuite.addTest(ConfigurePortletShowFolderSubfoldersTests.suite());
		testSuite.addTest(RemovePortletTests.suite());

		return testSuite;
	}

}