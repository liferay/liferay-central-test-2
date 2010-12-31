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

package com.liferay.portalweb.portlet.assetpublisher.webcontent;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.assetpublisher.webcontent.addwebcontent.AddWebContentTests;
import com.liferay.portalweb.portlet.assetpublisher.webcontent.ratewebcontent.RateWebContentTests;
import com.liferay.portalweb.portlet.assetpublisher.webcontent.removewebcontent.RemoveWebContentTests;
import com.liferay.portalweb.portlet.assetpublisher.webcontent.selectwebcontent.SelectWebContentTests;
import com.liferay.portalweb.portlet.assetpublisher.webcontent.viewcountwebcontent.ViewCountWebContentTests;
import com.liferay.portalweb.portlet.assetpublisher.webcontent.viewwebcontentabstracts.ViewWebContentAbstractsTests;
import com.liferay.portalweb.portlet.assetpublisher.webcontent.viewwebcontentdynamicassettypewebcontent.ViewWebContentDynamicAssetTypeWebContentTests;
import com.liferay.portalweb.portlet.assetpublisher.webcontent.viewwebcontentfullcontent.ViewWebContentFullContentTests;
import com.liferay.portalweb.portlet.assetpublisher.webcontent.viewwebcontenttable.ViewWebContentTableTests;
import com.liferay.portalweb.portlet.assetpublisher.webcontent.viewwebcontenttitlelist.ViewWebContentTitleListTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WebContentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWebContentTests.suite());
		testSuite.addTest(RateWebContentTests.suite());
		testSuite.addTest(RemoveWebContentTests.suite());
		testSuite.addTest(SelectWebContentTests.suite());
		testSuite.addTest(ViewCountWebContentTests.suite());
		testSuite.addTest(ViewWebContentAbstractsTests.suite());
		testSuite.addTest(
			ViewWebContentDynamicAssetTypeWebContentTests.suite());
		testSuite.addTest(ViewWebContentFullContentTests.suite());
		testSuite.addTest(ViewWebContentTableTests.suite());
		testSuite.addTest(ViewWebContentTitleListTests.suite());

		return testSuite;
	}

}