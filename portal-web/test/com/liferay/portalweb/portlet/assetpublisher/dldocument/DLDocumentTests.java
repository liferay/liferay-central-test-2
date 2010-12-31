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