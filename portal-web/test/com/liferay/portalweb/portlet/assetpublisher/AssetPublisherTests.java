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

/**
 * <a href="AssetPublisherTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetPublisherTests extends BaseTests {

	public AssetPublisherTests() {
		addTestSuite(SetupTest.class);
		addTestSuite(AddPageTest.class);
		addTestSuite(AddPortletTest.class);
		addTestSuite(ViewBlogsTest.class);
		addTestSuite(ViewBookmarkTest.class);
		addTestSuite(ViewDocumentTest.class);
		addTestSuite(ViewImageTest.class);
		addTestSuite(ViewWebContentTest.class);
		addTestSuite(ViewMessageBoardTest.class);
		addTestSuite(ViewWikiTest.class);
		addTestSuite(EditDisplayToTitleListTest.class);
		addTestSuite(VerifyDisplayToTitleListTest.class);
		addTestSuite(EditDisplayToTableTest.class);
		addTestSuite(VerifyDisplayToTableTest.class);
		addTestSuite(EditDisplayToFullContentTest.class);
		addTestSuite(VerifyDisplayToFullContentTest.class);
		addTestSuite(EditDisplayToAbstractsTest.class);
		addTestSuite(VerifyDisplayToAbstractsTest.class);
		addTestSuite(EditDynamicConfigurationBlogsTest.class);
		addTestSuite(VerifyDynamicConfigurationBlogsTest.class);
		addTestSuite(EditDynamicConfigurationBookmarkTest.class);
		addTestSuite(VerifyDynamicConfigurationBookmarkTest.class);
		addTestSuite(EditDynamicConfigurationDocumentTest.class);
		addTestSuite(VerifyDynamicConfigurationDocumentTest.class);
		addTestSuite(EditDynamicConfigurationImageTest.class);
		addTestSuite(VerifyDynamicConfigurationImageTest.class);
		addTestSuite(EditDynamicConfigurationWebContentTest.class);
		addTestSuite(VerifyDynamicConfigurationWebContentTest.class);
		addTestSuite(EditDynamicConfigurationMessageBoardTest.class);
		addTestSuite(VerifyDynamicConfigurationMessageBoardTest.class);
		addTestSuite(EditDynamicConfigurationWikiTest.class);
		addTestSuite(VerifyDynamicConfigurationWikiTest.class);
		addTestSuite(EditManualConfigurationTest.class);
		addTestSuite(SelectBlogsTest.class);
		addTestSuite(RemoveBlogsTest.class);
		addTestSuite(SelectBookmarkTest.class);
		addTestSuite(RemoveBookmarkTest.class);
		addTestSuite(SelectDocumentTest.class);
		addTestSuite(RemoveDocumentTest.class);
		addTestSuite(SelectImageTest.class);
		addTestSuite(RemoveImageTest.class);
		addTestSuite(SelectWebContentTest.class);
		addTestSuite(RemoveWebContentTest.class);
		addTestSuite(RevertSettingsTest.class);
		addTestSuite(AddBlogsTest.class);
		addTestSuite(AddBookmarkTest.class);
		addTestSuite(AddDocumentTest.class);
		addTestSuite(AddImageTest.class);
		addTestSuite(AddWebContentTest.class);
		addTestSuite(SaveArchivedConfigurationTest.class);
		addTestSuite(EditConfigurationTest.class);
		addTestSuite(RestoreArchivedConfigurationTest.class);
		addTestSuite(DeleteArchivedConfigurationTest.class);
		addTestSuite(EditConfigurationTest.class);
		addTestSuite(ImportLARTest.class);
		addTestSuite(AssertImportLARTest.class);
		addTestSuite(EditNumberAssetsShownTest.class);
		addTestSuite(ConfigureSimplePaginationTest.class);
		addTestSuite(VerifySimplePaginationTest.class);
		addTestSuite(ConfigureRegularPaginationTest.class);
		addTestSuite(VerifyRegularPaginationTest.class);
		addTestSuite(TearDownTest.class);
	}

}