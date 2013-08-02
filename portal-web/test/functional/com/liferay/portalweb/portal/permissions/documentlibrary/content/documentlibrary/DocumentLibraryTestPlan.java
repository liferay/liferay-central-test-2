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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrary;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrary.adddocument.AddDocumentTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrary.adddocumenttype.AddDocumentTypeTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrary.addfolder.AddFolderTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrary.addshortcut.AddShortcutTests;
import com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrary.view.ViewTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentLibraryTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDocumentTests.suite());
		testSuite.addTest(AddDocumentTypeTests.suite());
		testSuite.addTest(AddFolderTests.suite());
		testSuite.addTest(AddShortcutTests.suite());
		testSuite.addTest(ViewTests.suite());

		return testSuite;
	}

}