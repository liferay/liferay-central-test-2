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

package com.liferay.portalweb.plugins.kaleo.assetpublisher;

import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewblogsentryassignedtome.ViewBlogsEntryAssignedToMeTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewblogsentryassignedtomyroles.ViewBlogsEntryAssignedToMyRolesTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewblogsentrycompleted.ViewBlogsEntryCompletedTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewblogsentryrejected.ViewBlogsEntryRejectedTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewblogsentryresubmitted.ViewBlogsEntryResubmittedTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewdocumentassignedtome.ViewDocumentAssignedToMeTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewdocumentassignedtomyroles.ViewDocumentAssignedToMyRolesTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewdocumentcompleted.ViewDocumentCompletedTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewdocumentrejected.ViewDocumentRejectedTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewdocumentresubmitted.ViewDocumentResubmittedTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewmessageassignedtome.ViewMessageAssignedToMeTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewmessageassignedtomyroles.ViewMessageAssignedToMyRolesTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewmessagecompleted.ViewMessageCompletedTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewmessagerejected.ViewMessageRejectedTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewmessageresubmitted.ViewMessageResubmittedTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewwebcontentassignedtome.ViewWebContentAssignedToMeTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewwebcontentassignedtomyroles.ViewWebContentAssignedToMyRolesTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewwebcontentcompleted.ViewWebContentCompletedTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewwebcontentrejected.ViewWebContentRejectedTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.viewwebcontentresubmitted.ViewWebContentResubmittedTests;
import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetPublisherTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ViewBlogsEntryAssignedToMeTests.suite());
		testSuite.addTest(ViewBlogsEntryAssignedToMyRolesTests.suite());
		testSuite.addTest(ViewBlogsEntryCompletedTests.suite());
		testSuite.addTest(ViewBlogsEntryRejectedTests.suite());
		testSuite.addTest(ViewBlogsEntryResubmittedTests.suite());
		testSuite.addTest(ViewDocumentAssignedToMeTests.suite());
		testSuite.addTest(ViewDocumentAssignedToMyRolesTests.suite());
		testSuite.addTest(ViewDocumentCompletedTests.suite());
		testSuite.addTest(ViewDocumentRejectedTests.suite());
		testSuite.addTest(ViewDocumentResubmittedTests.suite());
		testSuite.addTest(ViewMessageAssignedToMeTests.suite());
		testSuite.addTest(ViewMessageAssignedToMyRolesTests.suite());
		testSuite.addTest(ViewMessageCompletedTests.suite());
		testSuite.addTest(ViewMessageRejectedTests.suite());
		testSuite.addTest(ViewMessageResubmittedTests.suite());
		testSuite.addTest(ViewWebContentAssignedToMeTests.suite());
		testSuite.addTest(ViewWebContentAssignedToMyRolesTests.suite());
		testSuite.addTest(ViewWebContentCompletedTests.suite());
		testSuite.addTest(ViewWebContentRejectedTests.suite());
		testSuite.addTest(ViewWebContentResubmittedTests.suite());

		return testSuite;
	}

}