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

package com.liferay.portalweb.portlet.assetpublisher.dldocument;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.adddldocument.AddDLDocumentTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.ratedldocument.RateDLDocumentTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.removedldocument.RemoveDLDocumentTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.selectdldocument.SelectDLDocumentTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.viewdldocumentabstracts.ViewDLDocumentAbstractsTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.viewdldocumentdynamicassettypedldocument.ViewDLDocumentDynamicAssetTypeDLDocumentTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.viewdldocumentfullcontent.ViewDLDocumentFullContentTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.viewdldocumenttable.ViewDLDocumentTableTests;
import com.liferay.portalweb.portlet.assetpublisher.dldocument.viewdldocumenttitlelist.ViewDLDocumentTitleListTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="DLDocumentTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLDocumentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDLDocumentTests.suite());
		testSuite.addTest(RateDLDocumentTests.suite());
		testSuite.addTest(RemoveDLDocumentTests.suite());
		testSuite.addTest(SelectDLDocumentTests.suite());
		testSuite.addTest(ViewDLDocumentAbstractsTests.suite());
		testSuite.addTest(
			ViewDLDocumentDynamicAssetTypeDLDocumentTests.suite());
		testSuite.addTest(ViewDLDocumentFullContentTests.suite());
		testSuite.addTest(ViewDLDocumentTableTests.suite());
		testSuite.addTest(ViewDLDocumentTitleListTests.suite());

		return testSuite;
	}

}