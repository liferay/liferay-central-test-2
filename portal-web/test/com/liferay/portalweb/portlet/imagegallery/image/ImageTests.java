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

package com.liferay.portalweb.portlet.imagegallery.image;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.imagegallery.image.addfolderimage.AddFolderImageTests;
import com.liferay.portalweb.portlet.imagegallery.image.addfolderimageimageinvalid.AddFolderImageImageInvalidTests;
import com.liferay.portalweb.portlet.imagegallery.image.addfolderimageimagenull.AddFolderImageImageNullTests;
import com.liferay.portalweb.portlet.imagegallery.image.addfolderimagemultiple.AddFolderImageMultipleTests;
import com.liferay.portalweb.portlet.imagegallery.image.addfolderimagenameduplicate.AddFolderImageNameDuplicateTests;
import com.liferay.portalweb.portlet.imagegallery.image.addfolderimagenamenull.AddFolderImageNameNullTests;
import com.liferay.portalweb.portlet.imagegallery.image.addsubfolderimage.AddSubfolderImageTests;
import com.liferay.portalweb.portlet.imagegallery.image.deletefolderimage.DeleteFolderImageTests;
import com.liferay.portalweb.portlet.imagegallery.image.deletesubfolderimage.DeleteSubfolderImageTests;
import com.liferay.portalweb.portlet.imagegallery.image.editfolderimage.EditFolderImageTests;
import com.liferay.portalweb.portlet.imagegallery.image.editsubfolderimage.EditSubfolderImageTests;
import com.liferay.portalweb.portlet.imagegallery.image.movefolderimagetofolder.MoveFolderImageToFolderTests;
import com.liferay.portalweb.portlet.imagegallery.image.searchfolderimage.SearchFolderImageTests;
import com.liferay.portalweb.portlet.imagegallery.image.searchfolderimagefolderdetails.SearchFolderImageFolderDetailsTests;
import com.liferay.portalweb.portlet.imagegallery.image.viewfolderimageeditingwindow.ViewFolderImageEditingWindowTests;
import com.liferay.portalweb.portlet.imagegallery.image.viewfolderimagemyimages.ViewFolderImageMyImagesTests;
import com.liferay.portalweb.portlet.imagegallery.image.viewfolderimagerecentimages.ViewFolderImageRecentImagesTests;
import com.liferay.portalweb.portlet.imagegallery.image.viewfolderimageslideshow.ViewFolderImageSlideShowTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="ImageTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ImageTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddFolderImageTests.suite());
		testSuite.addTest(AddFolderImageImageInvalidTests.suite());
		testSuite.addTest(AddFolderImageImageNullTests.suite());
		testSuite.addTest(AddFolderImageMultipleTests.suite());
		testSuite.addTest(AddFolderImageNameDuplicateTests.suite());
		testSuite.addTest(AddFolderImageNameNullTests.suite());
		testSuite.addTest(AddSubfolderImageTests.suite());
		testSuite.addTest(DeleteFolderImageTests.suite());
		testSuite.addTest(DeleteSubfolderImageTests.suite());
		testSuite.addTest(EditFolderImageTests.suite());
		testSuite.addTest(EditSubfolderImageTests.suite());
		testSuite.addTest(MoveFolderImageToFolderTests.suite());
		testSuite.addTest(SearchFolderImageTests.suite());
		testSuite.addTest(SearchFolderImageFolderDetailsTests.suite());
		testSuite.addTest(ViewFolderImageEditingWindowTests.suite());
		testSuite.addTest(ViewFolderImageMyImagesTests.suite());
		testSuite.addTest(ViewFolderImageRecentImagesTests.suite());
		testSuite.addTest(ViewFolderImageSlideShowTests.suite());

		return testSuite;
	}

}