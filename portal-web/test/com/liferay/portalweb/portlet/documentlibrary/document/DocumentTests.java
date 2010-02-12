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

package com.liferay.portalweb.portlet.documentlibrary.document;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocument.AddFolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocumentdocumentinvalid.AddFolderDocumentDocumentInvalidTests;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocumentdocumentnull.AddFolderDocumentDocumentNullTests;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocumentmultiple.AddFolderDocumentMultipleTests;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocumenttitleduplicate.AddFolderDocumentTitleDuplicateTests;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocumenttitlenull.AddFolderDocumentTitleNullTests;
import com.liferay.portalweb.portlet.documentlibrary.document.addsubfolderdocument.AddSubfolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.deletefolderdocument.DeleteFolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.editfolderdocument.EditFolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.lockfolderdocument.LockFolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.movefolderdocumentduplicatetofolder.MoveFolderDocumentDuplicateToFolderTests;
import com.liferay.portalweb.portlet.documentlibrary.document.movefolderdocumenttofolder.MoveFolderDocumentToFolderTests;
import com.liferay.portalweb.portlet.documentlibrary.document.searchfolderdocument.SearchFolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.unlockfolderdocument.UnlockFolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.viewfolderdocumentmydocuments.ViewFolderDocumentMyDocumentsTests;
import com.liferay.portalweb.portlet.documentlibrary.document.viewfolderdocumentrecentdocuments.ViewFolderDocumentRecentDocumentsTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="DocumentTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DocumentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddFolderDocumentTests.suite());
		testSuite.addTest(AddFolderDocumentDocumentInvalidTests.suite());
		testSuite.addTest(AddFolderDocumentDocumentNullTests.suite());
		testSuite.addTest(AddFolderDocumentMultipleTests.suite());
		testSuite.addTest(AddFolderDocumentTitleDuplicateTests.suite());
		testSuite.addTest(AddFolderDocumentTitleNullTests.suite());
		testSuite.addTest(AddSubfolderDocumentTests.suite());
		//testSuite.addTest(CompareFolderDocumentVersionTests.suite());
		testSuite.addTest(DeleteFolderDocumentTests.suite());
		testSuite.addTest(EditFolderDocumentTests.suite());
		testSuite.addTest(LockFolderDocumentTests.suite());
		testSuite.addTest(MoveFolderDocumentDuplicateToFolderTests.suite());
		testSuite.addTest(MoveFolderDocumentToFolderTests.suite());
		testSuite.addTest(SearchFolderDocumentTests.suite());
		testSuite.addTest(UnlockFolderDocumentTests.suite());
		testSuite.addTest(ViewFolderDocumentMyDocumentsTests.suite());
		testSuite.addTest(ViewFolderDocumentRecentDocumentsTests.suite());

		return testSuite;
	}

}