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

package com.liferay.portalweb.stagingcommunity.documentlibrary.document;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.addstaginglocallivedldocumentdocx.AddStagingLocalLiveDLDocumentDocxTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.addstaginglocallivedldocumentdocxnodl.AddStagingLocalLiveDLDocumentDocxNoDLTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.copyfromliveaddstaginglivedldocumentdocxmp.CopyFromLiveAddStagingLiveDLDocumentDocxMPTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.copyfromlivedeletedlstaginglivedocumentdocxmp.CopyFromLiveDeleteDLStagingLiveDocumentDocxMPTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.deletedlstaginglocallivedocumentdocxactions.DeleteDLStagingLocalLiveDocumentDocxActionsTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.deletelivepagestaginglocallivedldocumentdocxdock.DeleteLivePageStagingLocalLiveDLDocumentDocxDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.deletestaginglocallivedldocumentdocxactions.DeleteStagingLocalLiveDLDocumentDocxActionsTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowdldocumentdocxdock.PublishToLiveNowDLDocumentDocxDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowdldocumentdocxnodatadock.PublishToLiveNowDLDocumentDocxNoDataDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowdldocumentdocxnodldock.PublishToLiveNowDLDocumentDocxNoDLDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowdldocumentdocxnopagesdock.PublishToLiveNowDLDocumentDocxNoPagesDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowdlpagedocumentdocxdock.PublishToLiveNowDLPageDocumentDocxDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowdlpagedocumentdocxnopagedock.PublishToLiveNowDLPageDocumentDocxNoPageDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowdlportletdocumentdocxdock.PublishToLiveNowDLPortletDocumentDocxDockTests;
import com.liferay.portalweb.stagingcommunity.documentlibrary.document.viewstaginglivenonelivedldocumentdocx.ViewStagingLiveNoneLiveDLDocumentDocxTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddStagingLocalLiveDLDocumentDocxTests.suite());
		testSuite.addTest(AddStagingLocalLiveDLDocumentDocxNoDLTests.suite());
		testSuite.addTest(
			CopyFromLiveAddStagingLiveDLDocumentDocxMPTests.suite());
		testSuite.addTest(
			CopyFromLiveDeleteDLStagingLiveDocumentDocxMPTests.suite());
		testSuite.addTest(
			DeleteDLStagingLocalLiveDocumentDocxActionsTests.suite());
		testSuite.addTest(
			DeleteLivePageStagingLocalLiveDLDocumentDocxDockTests.suite());
		testSuite.addTest(
			DeleteStagingLocalLiveDLDocumentDocxActionsTests.suite());
		testSuite.addTest(PublishToLiveNowDLDocumentDocxDockTests.suite());
		testSuite.addTest(
			PublishToLiveNowDLDocumentDocxNoDataDockTests.suite());
		testSuite.addTest(PublishToLiveNowDLDocumentDocxNoDLDockTests.suite());
		testSuite.addTest(
			PublishToLiveNowDLDocumentDocxNoPagesDockTests.suite());
		testSuite.addTest(PublishToLiveNowDLPageDocumentDocxDockTests.suite());
		testSuite.addTest(
			PublishToLiveNowDLPageDocumentDocxNoPageDockTests.suite());
		testSuite.addTest(
			PublishToLiveNowDLPortletDocumentDocxDockTests.suite());
		testSuite.addTest(ViewStagingLiveNoneLiveDLDocumentDocxTests.suite());

		return testSuite;
	}

}