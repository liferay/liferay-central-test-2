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

package com.liferay.portalweb.portal.controlpanel.categories.category.deletecategorymultiple;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.categories.category.addcategorymultiple.AddCategory1Test;
import com.liferay.portalweb.portal.controlpanel.categories.category.addcategorymultiple.AddCategory2Test;
import com.liferay.portalweb.portal.controlpanel.categories.category.addcategorymultiple.AddCategory3Test;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabulary.AddVocabularyTest;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabulary.TearDownVocabularyTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteCategoryMultipleTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddVocabularyTest.class);
		testSuite.addTestSuite(AddCategory1Test.class);
		testSuite.addTestSuite(AddCategory2Test.class);
		testSuite.addTestSuite(AddCategory3Test.class);
		testSuite.addTestSuite(DeleteCategoryMultipleTest.class);
		testSuite.addTestSuite(ViewDeleteCategoryMultipleTest.class);
		testSuite.addTestSuite(TearDownVocabularyTest.class);

		return testSuite;
	}
}