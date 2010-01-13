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
 * <a href="PortletTests.java.html"><b><i>View Source</i></b></a>
 *
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