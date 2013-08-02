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

package com.liferay.portalweb.portal.controlpanel.categories.category.editvocabulary1categorytovocabulary2dad;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabulary.TearDownVocabularyTest;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularymultiple.AddVocabulary1Test;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularymultiple.AddVocabulary2Test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EditVocabulary1CategoryToVocabulary2DADTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddVocabulary1Test.class);
		testSuite.addTestSuite(AddVocabulary2Test.class);
		testSuite.addTestSuite(AddVocabulary1CategoryTest.class);
		testSuite.addTestSuite(EditVocabulary1CategoryToVocabulary2DADTest.class);
		testSuite.addTestSuite(ViewEditVocabulary1CategoryToVocabulary2DADTest.class);
		testSuite.addTestSuite(TearDownVocabularyTest.class);

		return testSuite;
	}
}