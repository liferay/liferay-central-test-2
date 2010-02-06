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

package com.liferay.portalweb.portal.controlpanel.webcontent;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="WebContentTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WebContentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(AddArticleTest.class);
		testSuite.addTestSuite(AddArticle2Test.class);
		testSuite.addTestSuite(WorkflowTest.class);
		testSuite.addTestSuite(ApproveArticleTest.class);
		testSuite.addTestSuite(ExpireArticleTest.class);
		testSuite.addTestSuite(DeleteArticleTest.class);
		testSuite.addTestSuite(AddStructuresTest.class);
		testSuite.addTestSuite(AddTemplateTest.class);
		//testSuite.addTestSuite(AddFeedTest.class);
		testSuite.addTestSuite(SearchArticleTest.class);
		testSuite.addTestSuite(SearchStructuresTest.class);
		testSuite.addTestSuite(SearchTemplateTest.class);
		//testSuite.addTestSuite(AddNullArticleTest.class);
		testSuite.addTestSuite(AddNullTitleTest.class);
		testSuite.addTestSuite(AddEscapeCharacterArticleTest.class);
		testSuite.addTestSuite(DeleteEscapeCharacterArticleTest.class);
		testSuite.addTestSuite(RecentPageTest.class);
		testSuite.addTestSuite(CancelPopupTest.class);
		testSuite.addTestSuite(DeleteAllTest.class);
		testSuite.addTestSuite(ImportLARTest.class);
		testSuite.addTestSuite(AssertImportLARTest.class);
		testSuite.addTestSuite(AddAssociatedTemplateTest.class);
		testSuite.addTestSuite(AssertStructureTemplateAssociationTest.class);
		testSuite.addTestSuite(AddLocalizedStructureTest.class);
		testSuite.addTestSuite(AddLocalizedTemplateTest.class);
		testSuite.addTestSuite(AddLocalizedArticleTest.class);
		testSuite.addTestSuite(AssertSavedLocalizationTest.class);
		testSuite.addTestSuite(AssertWebContentDisplayLocalizationTest.class);
		testSuite.addTestSuite(TearDownTest.class);
		testSuite.addTestSuite(EndControlPanelTest.class);

		return testSuite;
	}

}