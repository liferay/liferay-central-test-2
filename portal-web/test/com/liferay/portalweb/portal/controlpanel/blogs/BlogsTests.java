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

package com.liferay.portalweb.portal.controlpanel.blogs;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddEntryTest.class);
		testSuite.addTestSuite(AddEntryCommentTest.class);
		testSuite.addTestSuite(AssertViewCountTest.class);
		testSuite.addTestSuite(AddRatingTest.class);
		testSuite.addTestSuite(AddSecondEntryTest.class);
		testSuite.addTestSuite(AddSecondEntryCommentTest.class);
		testSuite.addTestSuite(EditSecondEntryTest.class);
		testSuite.addTestSuite(EditSecondCommentTest.class);
		testSuite.addTestSuite(SearchBlogsTest.class);
		testSuite.addTestSuite(DeleteSecondCommentTest.class);
		testSuite.addTestSuite(DeleteSecondEntryTest.class);
		testSuite.addTestSuite(AddNullEntryTest.class);
		testSuite.addTestSuite(AddNullTitleTest.class);
		testSuite.addTestSuite(AddEscapeCharacterEntryTest.class);
		testSuite.addTestSuite(DeleteEscapeCharacterEntryTest.class);
		testSuite.addTestSuite(DeleteEntryTest.class);
		testSuite.addTestSuite(AddDraftEntryTest.class);
		testSuite.addTestSuite(PublishDraftEntryTest.class);
		testSuite.addTestSuite(ImportLARTest.class);
		testSuite.addTestSuite(AssertImportLARTest.class);
		testSuite.addTestSuite(TearDownBlogsEntryCPTest.class);

		return testSuite;
	}

}