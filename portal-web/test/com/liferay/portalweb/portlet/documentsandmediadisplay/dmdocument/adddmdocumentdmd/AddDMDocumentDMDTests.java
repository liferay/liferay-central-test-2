/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument.adddmdocumentdmd;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument.deletedmdocumentsdmd.DeleteDMDocumentsDMDTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument.deletedmdocumentsdmd.ViewDeleteDMDocumentsDMDTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.dmfolder.adddmfolderdmd.TearDownDMDFolderTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.addportletdmd.AddPageDMDTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.addportletdmd.AddPortletDMDTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowactions.ConfigurePortletDMDShowActionsTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowactions.ViewPortletDMDShowActionsTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowfoldermenu.ConfigurePortletDMDShowFolderMenuTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowfoldermenu.ViewPortletDMDShowFolderMenuTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshownavigationlinks.ConfigurePortletDMDShowNavigationLinksTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshownavigationlinks.ViewPortletDMDShowNavigationLinksTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowsearch.ConfigurePortletDMDShowSearchTest;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowsearch.ViewPortletDMDShowSearchTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMDocumentDMDTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageDMDTest.class);
		testSuite.addTestSuite(AddPortletDMDTest.class);
		testSuite.addTestSuite(ConfigurePortletDMDShowFolderMenuTest.class);
		testSuite.addTestSuite(ConfigurePortletDMDShowActionsTest.class);
		testSuite.addTestSuite(ConfigurePortletDMDShowNavigationLinksTest.class);
		testSuite.addTestSuite(ConfigurePortletDMDShowSearchTest.class);
		testSuite.addTestSuite(ViewPortletDMDShowFolderMenuTest.class);
		testSuite.addTestSuite(ViewPortletDMDShowActionsTest.class);
		testSuite.addTestSuite(ViewPortletDMDShowNavigationLinksTest.class);
		testSuite.addTestSuite(ViewPortletDMDShowSearchTest.class);
		testSuite.addTestSuite(AddDMDocumentDMDTest.class);
		testSuite.addTestSuite(ViewDMDocumentDMDTest.class);
		testSuite.addTestSuite(DeleteDMDocumentsDMDTest.class);
		testSuite.addTestSuite(ViewDeleteDMDocumentsDMDTest.class);
		testSuite.addTestSuite(TearDownDMDFolderTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}