/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.assetpublisher;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="AssetPublisherTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetPublisherTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(SetupTest.class);
		testSuite.addTestSuite(AddPageTest.class);
		testSuite.addTestSuite(AddPortletTest.class);
		testSuite.addTestSuite(ViewBlogsTest.class);
		testSuite.addTestSuite(ViewBookmarkTest.class);
		testSuite.addTestSuite(ViewDocumentTest.class);
		testSuite.addTestSuite(ViewImageTest.class);
		testSuite.addTestSuite(ViewWebContentTest.class);
		testSuite.addTestSuite(ViewMessageBoardTest.class);
		testSuite.addTestSuite(ViewWikiTest.class);
		testSuite.addTestSuite(EditDisplayToTitleListTest.class);
		testSuite.addTestSuite(VerifyDisplayToTitleListTest.class);
		testSuite.addTestSuite(EditDisplayToTableTest.class);
		testSuite.addTestSuite(VerifyDisplayToTableTest.class);
		testSuite.addTestSuite(EditDisplayToFullContentTest.class);
		testSuite.addTestSuite(VerifyDisplayToFullContentTest.class);
		testSuite.addTestSuite(EditDisplayToAbstractsTest.class);
		testSuite.addTestSuite(VerifyDisplayToAbstractsTest.class);
		testSuite.addTestSuite(EditDynamicConfigurationBlogsTest.class);
		testSuite.addTestSuite(VerifyDynamicConfigurationBlogsTest.class);
		testSuite.addTestSuite(EnableBlogRatingsTest.class);
		testSuite.addTestSuite(AssertBlogRatingsTest.class);
		testSuite.addTestSuite(EnableBlogCommentsTest.class);
		testSuite.addTestSuite(AssertBlogCommentsTest.class);
		testSuite.addTestSuite(EnableBlogCommentsRatingsTest.class);
		testSuite.addTestSuite(AssertBlogCommentsRatingsTest.class);
		testSuite.addTestSuite(EditDynamicConfigurationBookmarkTest.class);
		testSuite.addTestSuite(VerifyDynamicConfigurationBookmarkTest.class);
		testSuite.addTestSuite(EditDynamicConfigurationDocumentTest.class);
		testSuite.addTestSuite(VerifyDynamicConfigurationDocumentTest.class);
		testSuite.addTestSuite(AssertDocumentRatingsTest.class);
		testSuite.addTestSuite(AssertDocumentCommentsTest.class);
		testSuite.addTestSuite(AssertDocumentCommentsRatingsTest.class);
		testSuite.addTestSuite(EditDynamicConfigurationImageTest.class);
		testSuite.addTestSuite(VerifyDynamicConfigurationImageTest.class);
		testSuite.addTestSuite(EditDynamicConfigurationWebContentTest.class);
		testSuite.addTestSuite(VerifyDynamicConfigurationWebContentTest.class);
		testSuite.addTestSuite(AssertWebContentRatingsTest.class);
		testSuite.addTestSuite(AssertWebContentCommentsTest.class);
		testSuite.addTestSuite(AssertWebContentCommentsRatingsTest.class);
		testSuite.addTestSuite(EditDynamicConfigurationMessageBoardTest.class);
		testSuite.addTestSuite(
			VerifyDynamicConfigurationMessageBoardTest.class);
		testSuite.addTestSuite(EditDynamicConfigurationWikiTest.class);
		testSuite.addTestSuite(VerifyDynamicConfigurationWikiTest.class);
		testSuite.addTestSuite(EditManualConfigurationTest.class);
		testSuite.addTestSuite(SelectBlogsTest.class);
		testSuite.addTestSuite(RemoveBlogsTest.class);
		testSuite.addTestSuite(SelectBookmarkTest.class);
		testSuite.addTestSuite(RemoveBookmarkTest.class);
		testSuite.addTestSuite(SelectDocumentTest.class);
		testSuite.addTestSuite(RemoveDocumentTest.class);
		testSuite.addTestSuite(SelectImageTest.class);
		testSuite.addTestSuite(RemoveImageTest.class);
		testSuite.addTestSuite(SelectWebContentTest.class);
		testSuite.addTestSuite(RemoveWebContentTest.class);
		testSuite.addTestSuite(RevertSettingsTest.class);
		testSuite.addTestSuite(AddBlogsTest.class);
		testSuite.addTestSuite(AddBookmarkTest.class);
		testSuite.addTestSuite(AddDocumentTest.class);
		testSuite.addTestSuite(AddImageTest.class);
		testSuite.addTestSuite(AddWebContentTest.class);
		testSuite.addTestSuite(SaveArchivedConfigurationTest.class);
		testSuite.addTestSuite(EditConfigurationTest.class);
		testSuite.addTestSuite(RestoreArchivedConfigurationTest.class);
		testSuite.addTestSuite(DeleteArchivedConfigurationTest.class);
		testSuite.addTestSuite(EditConfigurationTest.class);
		testSuite.addTestSuite(ImportLARTest.class);
		testSuite.addTestSuite(AssertImportLARTest.class);
		testSuite.addTestSuite(EditNumberAssetsShownTest.class);
		testSuite.addTestSuite(ConfigureSimplePaginationTest.class);
		testSuite.addTestSuite(VerifySimplePaginationTest.class);
		testSuite.addTestSuite(ConfigureRegularPaginationTest.class);
		testSuite.addTestSuite(VerifyRegularPaginationTest.class);
		testSuite.addTestSuite(TearDownTest.class);

		return testSuite;
	}

}