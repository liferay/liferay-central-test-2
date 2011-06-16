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

package com.liferay.portalweb.plugins.kaleo.assetpublisher.dldocument;

import com.liferay.portalweb.plugins.kaleo.assetpublisher.dldocument.viewdocumentassignedtome.ViewDocumentAssignedToMeTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.dldocument.viewdocumentassignedtomyroles.ViewDocumentAssignedToMyRolesTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.dldocument.viewdocumentcompleted.ViewDocumentCompletedTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.dldocument.viewdocumentrejected.ViewDocumentRejectedTests;
import com.liferay.portalweb.plugins.kaleo.assetpublisher.dldocument.viewdocumentresubmitted.ViewDocumentResubmittedTests;
import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DLDocumentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ViewDocumentAssignedToMeTests.suite());
		testSuite.addTest(ViewDocumentAssignedToMyRolesTests.suite());
		testSuite.addTest(ViewDocumentCompletedTests.suite());
		testSuite.addTest(ViewDocumentRejectedTests.suite());
		testSuite.addTest(ViewDocumentResubmittedTests.suite());

		return testSuite;
	}

}