/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.assetpublisher.portlet;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportlet.AddPortletTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletmultiple.AddPortletMultipleTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletassetselectiondynamic.ConfigurePortletAssetSelectionDynamicTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletassetselectionmanual.ConfigurePortletAssetSelectionManualTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicassettypeblogsentry.ConfigurePortletDynamicAssetTypeBlogsEntryTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicassettypebookmarksentry.ConfigurePortletDynamicAssetTypeBookmarksEntryTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicassettypedldocument.ConfigurePortletDynamicAssetTypeDLDocumentTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicassettypeigimage.ConfigurePortletDynamicAssetTypeIGImageTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicassettypembmessage.ConfigurePortletDynamicAssetTypeMBMessageTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicassettypewebcontent.ConfigurePortletDynamicAssetTypeWebContentTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicassettypewikipage.ConfigurePortletDynamicAssetTypeWikiPageTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicdisplaystyleabstracts.ConfigurePortletDynamicDisplayStyleAbstractsTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicdisplaystylefullcontent.ConfigurePortletDynamicDisplayStyleFullContentTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicdisplaystyletable.ConfigurePortletDynamicDisplayStyleTableTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicdisplaystyletitlelist.ConfigurePortletDynamicDisplayStyleTitleListTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicmaxitemtodisplay5.ConfigurePortletDynamicMaxItemToDisplay5Tests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicpaginationtyperegular.ConfigurePortletDynamicPaginationTypeRegularTests;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicpaginationtypesimple.ConfigurePortletDynamicPaginationTypeSimpleTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletTests.suite());
		testSuite.addTest(AddPortletMultipleTests.suite());
		testSuite.addTest(ConfigurePortletAssetSelectionDynamicTests.suite());
		testSuite.addTest(ConfigurePortletAssetSelectionManualTests.suite());
		testSuite.addTest(
			ConfigurePortletDynamicAssetTypeBlogsEntryTests.suite());
		testSuite.addTest(
			ConfigurePortletDynamicAssetTypeBookmarksEntryTests.suite());
		testSuite.addTest(
			ConfigurePortletDynamicAssetTypeDLDocumentTests.suite());
		testSuite.addTest(
			ConfigurePortletDynamicAssetTypeIGImageTests.suite());
		testSuite.addTest(
			ConfigurePortletDynamicAssetTypeMBMessageTests.suite());
		testSuite.addTest(
			ConfigurePortletDynamicAssetTypeWebContentTests.suite());
		testSuite.addTest(
			ConfigurePortletDynamicAssetTypeWikiPageTests.suite());
		testSuite.addTest(
			ConfigurePortletDynamicDisplayStyleAbstractsTests.suite());
		testSuite.addTest(
			ConfigurePortletDynamicDisplayStyleFullContentTests.suite());
		testSuite.addTest(
			ConfigurePortletDynamicDisplayStyleTableTests.suite());
		testSuite.addTest(
			ConfigurePortletDynamicDisplayStyleTitleListTests.suite());
		testSuite.addTest(
			ConfigurePortletDynamicMaxItemToDisplay5Tests.suite());
		testSuite.addTest(
			ConfigurePortletDynamicPaginationTypeRegularTests.suite());
		testSuite.addTest(
			ConfigurePortletDynamicPaginationTypeSimpleTests.suite());

		return testSuite;
	}

}