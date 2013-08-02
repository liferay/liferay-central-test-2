/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.kaleo.assetpublisher;

import com.liferay.portalweb.kaleo.assetpublisher.blogsentry.BlogsEntryTestPlan;
import com.liferay.portalweb.kaleo.assetpublisher.dmdocument.DMDocumentTestPlan;
import com.liferay.portalweb.kaleo.assetpublisher.mbmessage.MBMessageTestPlan;
import com.liferay.portalweb.kaleo.assetpublisher.wcwebcontent.WCWebContentTestPlan;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetPublisherTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(BlogsEntryTestPlan.suite());
		testSuite.addTest(DMDocumentTestPlan.suite());
		testSuite.addTest(MBMessageTestPlan.suite());
		testSuite.addTest(WCWebContentTestPlan.suite());

		return testSuite;
	}

}