/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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