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

package com.liferay.portalweb.portlet.imagegallery.folder;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.imagegallery.folder.addfolder.AddFolderTests;
import com.liferay.portalweb.portlet.imagegallery.folder.addfoldermultiple.AddFolderMultipleTests;
import com.liferay.portalweb.portlet.imagegallery.folder.addfoldernameduplicate.AddFolderNameDuplicateTests;
import com.liferay.portalweb.portlet.imagegallery.folder.addfoldernameinvalid.AddFolderNameInvalidTests;
import com.liferay.portalweb.portlet.imagegallery.folder.addfoldernamenull.AddFolderNameNullTests;
import com.liferay.portalweb.portlet.imagegallery.folder.addsubfolder.AddSubfolderTests;
import com.liferay.portalweb.portlet.imagegallery.folder.addsubfoldermultiple.AddSubfolderMultipleTests;
import com.liferay.portalweb.portlet.imagegallery.folder.addsubfoldernameimagename.AddSubfolderNameImageNameTests;
import com.liferay.portalweb.portlet.imagegallery.folder.deletefolder.DeleteFolderTests;
import com.liferay.portalweb.portlet.imagegallery.folder.deletesubfolder.DeleteSubfolderTests;
import com.liferay.portalweb.portlet.imagegallery.folder.editfolder.EditFolderTests;
import com.liferay.portalweb.portlet.imagegallery.folder.editsubfolder.EditSubfolderTests;
import com.liferay.portalweb.portlet.imagegallery.folder.movesubfoldertofolder.MoveSubfolderToFolderTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="FolderTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class FolderTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddFolderTests.suite());
		testSuite.addTest(AddFolderMultipleTests.suite());
		testSuite.addTest(AddFolderNameDuplicateTests.suite());
		testSuite.addTest(AddFolderNameInvalidTests.suite());
		testSuite.addTest(AddFolderNameNullTests.suite());
		testSuite.addTest(AddSubfolderTests.suite());
		testSuite.addTest(AddSubfolderMultipleTests.suite());
		testSuite.addTest(AddSubfolderNameImageNameTests.suite());
		testSuite.addTest(DeleteFolderTests.suite());
		testSuite.addTest(DeleteSubfolderTests.suite());
		testSuite.addTest(EditFolderTests.suite());
		testSuite.addTest(EditSubfolderTests.suite());
		testSuite.addTest(MoveSubfolderToFolderTests.suite());

		return testSuite;
	}

}